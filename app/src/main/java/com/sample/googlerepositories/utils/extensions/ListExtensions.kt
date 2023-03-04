package com.sample.googlerepositories.utils.extensions

/**
 * changes [List] of type [T] to [ArrayList] of type [T]
 * @return return [ArrayList] of type [T]
 */
inline fun <reified T : Any> List<T>.toArrayList(): ArrayList<T> {
    val arrayList = arrayListOf<T>()
    this.forEach {
        arrayList.add(it)
    }
    return arrayList
}