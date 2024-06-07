package health.patchwork.library.service

import health.patchwork.library.repository.BookJpa
import health.patchwork.library.repository.BookRepository

class BookServiceImpl(private val repository: BookRepository) : BookService {

    private val isAvailableAndNotAReference =
        { bookJpa: BookJpa -> bookJpa.isAvailable && !bookJpa.isReference }

    private val toBook = { bookJpa: BookJpa -> Book(bookJpa) }

    override fun findByAuthor(author: String): List<Book> = repository
        .findByAuthor(author)
        .map(toBook)

    override fun findByTitle(title: String): List<Book> = repository
        .findByTitle(title)
        .map(toBook)

    override fun findByISBN(isbn: String): List<Book> = repository
        .findByISBN(isbn)
        .map(toBook)

    @Synchronized
    override fun borrowBook(author: String, title: String): Book? {
        val bookJpa: BookJpa? = repository
            .findByAuthorAndTitle(author, title)
            .firstOrNull(isAvailableAndNotAReference)
        return borrowBook(bookJpa)
    }

    @Synchronized
    override fun borrowBook(isbn: String): Book? {
        val bookJpa: BookJpa? = repository
            .findByISBN(isbn)
            .firstOrNull(isAvailableAndNotAReference)
        return borrowBook(bookJpa)
    }

    override fun getUnavailableBookCount(): Int = repository.getUnavailableBooks().size

    private fun borrowBook(bookJpa: BookJpa?): Book? {
        if (bookJpa != null) {
            repository.setAvailable(bookJpa, false)
            return Book(bookJpa)
        }
        return null
    }
}