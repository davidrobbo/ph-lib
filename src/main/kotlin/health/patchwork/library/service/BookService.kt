package health.patchwork.library.service

interface BookService {
    fun findByAuthor(author: String): List<Book>
    fun findByTitle(title: String): List<Book>
    fun findByISBN(isbn: String): List<Book>
    fun borrowBook(author: String, title: String): Book?
    fun borrowBook(isbn: String): Book?
    fun getUnavailableBookCount(): Int
}