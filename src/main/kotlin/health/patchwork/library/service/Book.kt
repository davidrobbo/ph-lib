package health.patchwork.library.service

import health.patchwork.library.repository.BookJpa

data class Book(
    val author: String,
    val title: String,
    val isbn: String,
    val isReference: Boolean,
    val isAvailable: Boolean
) {
    constructor(bookJpa: BookJpa) : this(
        author = bookJpa.author,
        title = bookJpa.title,
        isbn = bookJpa.isbn,
        isReference = bookJpa.isReference,
        isAvailable = bookJpa.isAvailable
    )
}
