package ru.anbazhan.bindingvalidation.extensions

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import ru.anbazhan.bindingvalidation.message.EditMessageType
import ru.anbazhan.bindingvalidation.message.EditMessage

/**
 * DataBinding адаптер для обработки подсказки
 */
@BindingAdapter("android:errorText")
fun TextInputLayout.bindErrorText(editBottomMessage: EditMessage?) {
    editBottomMessage?.let {
        when (editBottomMessage.type) {
            EditMessageType.ERROR -> {
                helperText = null
                error = editBottomMessage.text
            }
            EditMessageType.HELPER, EditMessageType.NONE -> {
                error = null
                helperText = editBottomMessage.text
            }
        }
        isHelperTextEnabled = !helperText.isNullOrBlank()
        isErrorEnabled = !error.isNullOrBlank()
    }
}