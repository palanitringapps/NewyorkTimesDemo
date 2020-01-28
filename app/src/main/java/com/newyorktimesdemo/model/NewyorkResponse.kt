package com.newyorktimesdemo.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewyorkArticleResponse(

    val status: String,
    val copyright: String,
    val num_results: Int,
    val results: ArrayList<Results>
)

data class MediaMetadata(

    val url: String,
    val format: String,
    val height: Int,
    val width: Int
) : Serializable

data class Media(

    val type: String,
    val subtype: String,
    val caption: String,
    val copyright: String,
    val approved_for_syndication: Int,
    @SerializedName("media-metadata")
    val mediametadata: List<MediaMetadata>
) : Serializable

data class Results(

    val url: String?,
    val adx_keywords: String?,
    val column: Any?,
    val section: String?,
    val byline: String?,
    val type: String?,
    val title: String?,
    val abstract: String?,
    val published_date: String?,
    val source: String?,
    val id: Number?,
    val asset_id: Number?,
    val views: Number?,
    val media: List<Media>?
) : Serializable