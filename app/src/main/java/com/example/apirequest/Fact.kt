package com.example.apirequest

import com.google.gson.annotations.SerializedName

data class Fact(
    @SerializedName("fact" ) var fact: String
)