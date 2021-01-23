package ru.anbazhan.bindingvalidation

data class EditBottomMessage(
    val text: String = "",
    val type: EditBottomMessageType = EditBottomMessageType.NONE
) {

    companion object {
        val SUCCESS = EditBottomMessage()
    }
}

enum class EditBottomMessageType {

    NONE,

    ERROR,

    HELPER
}

fun EditBottomMessageType?.orHelper() = this ?: EditBottomMessageType.HELPER

fun EditBottomMessageType?.orError() = this ?: EditBottomMessageType.ERROR
