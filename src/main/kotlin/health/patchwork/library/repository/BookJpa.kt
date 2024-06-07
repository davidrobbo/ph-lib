package health.patchwork.library.repository

data class BookJpa(
    val author: String,
    val title: String,
    val isbn: String,
    val isReference: Boolean,
    var isAvailable: Boolean // todo consider class w/private set over data class
)
