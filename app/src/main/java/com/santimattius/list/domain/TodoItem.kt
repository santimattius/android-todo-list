package com.santimattius.list.domain

import java.util.*

data class TodoItem(val id: UUID, val title: String, val description: String) {

    companion object {

        fun empty() = TodoItem(
            id = UUID.randomUUID(),
            title = "",
            description = ""
        )
    }
}
