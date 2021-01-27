package ru.anbazhan.bindingvalidation.message

data class EditMessage(
    val text: String = "",
    val type: EditMessageType = EditMessageType.NONE
) {

    companion object {
        val SUCCESS = EditMessage()
    }
}

enum class EditMessageType {

    NONE,

    ERROR,

    HELPER
}

fun EditMessageType?.orHelper() = this ?: EditMessageType.HELPER

fun EditMessageType?.orError() = this ?: EditMessageType.ERROR
