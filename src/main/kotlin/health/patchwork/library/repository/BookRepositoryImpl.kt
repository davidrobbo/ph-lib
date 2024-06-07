package health.patchwork.library.repository

class BookRepositoryImpl(private val books: List<BookJpa>) : BookRepository {

    override fun findByAuthor(author: String): List<BookJpa> = books
        .filter { book -> book.author == author }

    override fun findByTitle(title: String): List<BookJpa> = books
        .filter { book -> book.title == title }

    override fun findByAuthorAndTitle(author: String, title: String): List<BookJpa> = books
        .filter { book -> book.author == author && book.title == title }

    override fun findByISBN(isbn: String): List<BookJpa> = books
        .filter { book -> book.isbn == isbn }

    override fun setAvailable(bookJpa: BookJpa, isAvailable: Boolean): Unit {
        val book: BookJpa? = books.find { book -> book == bookJpa }
        if (book != null) {
            book.isAvailable = isAvailable
        }
    }

    override fun getUnavailableBooks(): List<BookJpa> = books.filter { book -> !book.isAvailable }
}