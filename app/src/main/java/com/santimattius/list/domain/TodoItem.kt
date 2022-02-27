package com.santimattius.list.domain

import java.util.*

data class TodoItem(
    val id: UUID,
    val title: String,
    val description: String,
    val date: Date = Calendar.getInstance().time,
) {

    companion object {

        fun empty() = TodoItem(
            id = UUID.randomUUID(),
            title = "",
            description = ""
        )
    }
}

fun TodoItem.isEmpty(): Boolean {
    return title.withoutContent() || description.withoutContent()
}

fun String.withoutContent(): Boolean {
    return isEmpty() || isBlank()
}

fun String.withContent() = withoutContent().not()