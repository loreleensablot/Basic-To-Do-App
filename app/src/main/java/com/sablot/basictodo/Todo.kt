package com.sablot.basictodo

data class Todo(
    val title: String,
    var isChecked: Boolean = false,
    var category: String? = null
)