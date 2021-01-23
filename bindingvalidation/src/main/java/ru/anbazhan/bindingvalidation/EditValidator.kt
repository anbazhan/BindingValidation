package ru.anbazhan.bindingvalidation

interface EditValidator<T> {

    val text: String
    val type: EditBottomMessageType

    fun validate(value: T): Boolean
}
