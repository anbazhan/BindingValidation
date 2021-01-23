package ru.anbazhan.bindingvalidation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

open class CombineListLiveData<T, S>(
    sourceList: List<LiveData<T>>,
    private val combineArg: (arg1: List<T?>) -> S
) : MediatorLiveData<S>() {

    protected val items: MutableList<T?> = sourceList.map { it.value }.toMutableList()

    init {
        for ((index, source) in sourceList.withIndex()) {
            super.addSource(source) {
                items[index] = it
                this.value = combineArg(items)
            }
        }
    }

    override fun <S : Any?> addSource(source: LiveData<S>, onChanged: Observer<in S>) {
        throw UnsupportedOperationException()
    }

    override fun <T : Any?> removeSource(toRemote: LiveData<T>) {
        throw UnsupportedOperationException()
    }
}
