# MYTV-Series

MYTVSeries is a simple app to show a list of top rated Tv series   

* * *

## Pending
- MYTVSeries will allow you to select between you favorites series and find it in a quick tab. Also on the detail of each 
TVSeries this app will show you recommendations per each item.  
- Servers errors will not make the app crash but there are not provided feedback implemented.
    
* * *

## Integrations
MYTVSeries has integrations for CD an CI
- CI integration has been made with [CircleCI](https://circleci.com/) running all unit test as a condition to merge.
- CD integration runs with [bitrise](https://www.bitrise.io/). It has the api key for the project on its secrets `env` variables
and it compile a build for distribution after a push on master branch. 
To compile the app after clone the repo yo need to provide you `api_key` on the `api_key.xml` file. 
    
## Distribution
App is distributed to [Play Store Beta Channel](https://play.google.com/apps/testing/com.mytv.series) or to a list of tester
with Bitrise (user get an email notifying about the new available version).
    
## Architecture
The project follows a MVVM architecture defined by this components

* [Activity/Fragments](#activity/Fragments)
* [ViewModel](#viewmodel)
* [Model](#model)
* [Repository](#repository)

The project make each feature independent and the project holds on three base module `base`, `data`, `baseui`. The feature module 
should just know this three and all the dependencies as mapper and repository will be provided by the `app` module through 
`Dagger`

* * *

- ### Activity/Fragments

**Responsibilities:** This class is responsible only for rendering and view binding. Create `Views`.

**What don't do:** 
* Don't make any async call like Service Calls, get data from persistence or any heavy operation. 
* Don't use singletons to save this reference.
* Don't allocate a reference of this class in any static method like listeners or asyncTasks
````
#!kotlin
class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(findViewById(R.id.tbHome))
        navController = Navigation.findNavController(this, R.id.navController)
        NavigationUI.setupWithNavController(findViewById<BottomNavigationView>(R.id.bnHome), navController)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item?.let {
            NavigationUI.onNavDestinationSelected(it, navController) || super.onOptionsItemSelected(item)
        } ?: super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

}
````


* * *

- ### ViewModel

**Responsibilities:** A ViewModel provides the data for a specific UI component, such as a fragment or activity, and handles the communication with the business part of data handling, such as calling other components to load the data or forwarding user modifications. The ViewModel does not know about the View.
**What don't do:** 
* Don't reference the View for example `Activity` or `Fragment` never.

````
#!kotlin
class TVSeriesViewModel @Inject constructor(
    private val widgetMapper: @JvmSuppressWildcards BaseMapper<TVSeries, TVSeriesWidgetModel>,
    private val repository: @JvmSuppressWildcards BaseRepository<Int, TVSeries>
) : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()

    private val clickedItemSubject: PublishSubject<Int> = PublishSubject.create()

    private val _eventsLiveData = MutableLiveData<TvSeriesViewModelEvents>()
    val eventsLiveData : LiveData<TvSeriesViewModelEvents> get() = _eventsLiveData

    private val dataController: PageKeyedDataSource<Int, TVSeries> by DataController()
    private val dataFactory: DataFactory by DataFactoryImpl()
    private val configuration: PagedList.Config
        get() {
            return PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(30)
                .setPageSize(20)
                .build()
        }

    val itemsLiveData: LiveData<PagedList<RecyclerItem<TVSeriesWidgetModel, String>>> by lazy {
        LivePagedListBuilder<Int, RecyclerItem<TVSeriesWidgetModel, String>>(dataFactory, configuration).build()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        dataFactory.registerToItemsChanges()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        compositeDisposable.clear()
    }

    fun onItemClicked(id: Int) {
        clickedItemSubject.onNext(id)
    }

    sealed class TvSeriesViewModelEvents {
        class OnItemClicked(val id: Int) : TvSeriesViewModelEvents()
    }
}
````

* * *

- ### Models

**Responsibilities:** The Models are the entities necessaries to work with this module, represent a business logic unit and are the result of the `Repository`.

The models should be data classes.

**What don't do:** 
* Don't perform any kind of logic here.

````
#!kotlin
@Entity(tableName = "tv_series")
data class TVSeries(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "original_name")
    val originalName: String,
    val name: String,
    val popularity: Double,
    val overview: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @ColumnInfo(name = "first_air_date")
    val firstAirDate: String
)
````


- ### Repository

**Responsibilities:** Module responsible for handling data operations. They provide a clean API to the rest of the app. 
They know where to get the data from and what API calls to make when data is updated. 
You can consider them as mediators between different data sources (persistent model, web service, cache, etc.).

````
#!kotlin
class ConfigurationRepository @Inject constructor(
    private val configurationService: ConfigurationService,
    private val mapper: @JvmSuppressWildcards BaseMapper<JsonElement, Configuration>
) : @JvmSuppressWildcards BaseRepository<Any, Configuration> {

    private lateinit var cachedValue : Configuration
    private val cachedSubject = BehaviorSubject.create<Configuration>()

    override fun getSingleListData(parameters: Any): Single<List<Configuration>> = Single.just(emptyList())

    override fun getObservableData(): Observable<Configuration> {
        return cachedSubject
    }

    override fun getCompletableData(): Completable {
        return configurationService.getConfiguration()
            .subscribeOn(Schedulers.io())
            .map { mapper.getFromElement(it.getAsJsonObject("images")) }
            .flatMapCompletable { configuration ->
                Completable.fromAction {
                    cachedValue = configuration
                }
            }.doOnComplete {
                cachedSubject.onNext(cachedValue)
            }
    }

    override fun getSyncData(): Configuration {
        return cachedValue
    }
}
````

* * *



