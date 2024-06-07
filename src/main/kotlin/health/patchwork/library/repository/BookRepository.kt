package health.patchwork.library.repository

interface BookRepository {
    fun findByAuthor(author: String): List<BookJpa>
    fun findByTitle(title: String): List<BookJpa>
    fun findByAuthorAndTitle(author: String, title: String): List<BookJpa>
    fun findByISBN(isbn: String): List<BookJpa>
    fun setAvailable(bookJpa: BookJpa, isAvailable: Boolean): Unit
    fun getUnavailableBooks(): List<BookJpa>
}