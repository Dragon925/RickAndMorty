package com.github.dragon925.rickandmorty.data.sources.network.dto

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName(COUNT) val count: Int,
    @SerializedName(PAGES) val pages: Int,
    @SerializedName(NEXT)  val next: String?,
    @SerializedName(PREV)  val prev: String?
) {
    companion object {
        const val COUNT = "count"
        const val PAGES = "pages"
        const val NEXT = "next"
        const val PREV = "prev"
    }
}
