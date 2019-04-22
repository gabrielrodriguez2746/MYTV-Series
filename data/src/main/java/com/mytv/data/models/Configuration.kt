package com.mytv.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuration")
data class Configuration(
    @ColumnInfo(name = "original_name")
    @PrimaryKey
    val baseUrl: String,
    @ColumnInfo(name = "backdrop_image_configuration")
    val backdropImageConfiguration: List<String>,
    @ColumnInfo(name = "poster_image_configuration")
    val posterImageConfiguration: List<String>)