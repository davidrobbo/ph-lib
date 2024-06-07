package health.patchwork.library.service

import health.patchwork.library.repository.BookJpa
import health.patchwork.library.repository.BookRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.*
import org.mockito.kotlin.reset

class BookServiceImplTest {

    private val repository = mock<BookRepository>()

    private val service = BookServiceImpl(repository = repository)

    @BeforeEach
    fun beforeEach() {
        reset(repository)
    }

    @Test
    fun findByAuthor() {
        `when`(repository.findByAuthor("foobar")).thenReturn(emptyList())
        assert(emptyList<Book>() == service.findByAuthor("foobar"))

        `when`(repository.findByAuthor("author_a")).thenReturn(listOf(
            BookJpa(author = "author_a", title = "title", isbn = "isbn", isReference = false, isAvailable = false)
        ))
        assert(service.findByAuthor("author_a") == listOf(
            Book(author = "author_a", title = "title", isbn = "isbn", isReference = false, isAvailable = false)
        ))
    }

    @Test
    fun findByTitle() {
        `when`(repository.findByTitle("foobar")).thenReturn(emptyList())
        assert(emptyList<Book>() == service.findByTitle("foobar"))

        `when`(repository.findByTitle("title_a")).thenReturn(listOf(
            BookJpa(author = "author_a", title = "title_a", isbn = "isbn", isReference = false, isAvailable = false)
        ))
        assert(service.findByTitle("title_a") == listOf(
            Book(author = "author_a", title = "title_a", isbn = "isbn", isReference = false, isAvailable = false)
        ))
    }

    @Test
    fun findByISBN() {
        `when`(repository.findByISBN("foobar")).thenReturn(emptyList())
        assert(emptyList<Book>() == service.findByISBN("foobar"))

        `when`(repository.findByISBN("isbn_a")).thenReturn(listOf(
            BookJpa(author = "author_a", title = "title_a", isbn = "isbn_a", isReference = false, isAvailable = false)
        ))
        assert(service.findByISBN("isbn_a") == listOf(
            Book(author = "author_a", title = "title_a", isbn = "isbn_a", isReference = false, isAvailable = false)
        ))
    }

    @Test
    fun borrowBook() {
        val bookJpa = BookJpa(
            author = "author_a", title = "title_a", isbn = "isbn_a", isReference = false, isAvailable = true
        )
        `when`(repository.findByISBN("isbn_a")).thenReturn(listOf(bookJpa))
        `when`(repository.findByAuthorAndTitle(author = "author_a", title = "title_a")).thenReturn(listOf(bookJpa))
        doNothing().`when`(repository).setAvailable(bookJpa, false)

        val book = Book(
            author = "author_a", title = "title_a", isbn = "isbn_a", isReference = false, isAvailable = true
        )
        assert(book == service.borrowBook("isbn_a"))
        assert(book == service.borrowBook(author = "author_a", title = "title_a"))
    }

    @Test
    fun borrowBookCannotIfReference() {
        val bookJpa = BookJpa(
            author = "author_a", title = "title_a", isbn = "isbn_a", isReference = true, isAvailable = true
        )
        `when`(repository.findByISBN("isbn_a")).thenReturn(listOf(bookJpa))
        `when`(repository.findByAuthorAndTitle(author = "author_a", title = "title_a")).thenReturn(listOf(bookJpa))
        doNothing().`when`(repository).setAvailable(bookJpa, false)

        assertNull(service.borrowBook("isbn_a"))
        assertNull(service.borrowBook(author = "author_a", title = "title_a"))
    }

    @Test
    fun borrowBookCannotIfAlreadyBorrowed() {
        val bookJpa = BookJpa(
            author = "author_a", title = "title_a", isbn = "isbn_a", isReference = false, isAvailable = false
        )
        `when`(repository.findByISBN("isbn_a")).thenReturn(listOf(bookJpa))
        `when`(repository.findByAuthorAndTitle(author = "author_a", title = "title_a")).thenReturn(listOf(bookJpa))
        doNothing().`when`(repository).setAvailable(bookJpa, false)

        assertNull(service.borrowBook("isbn_a"))
        assertNull(service.borrowBook(author = "author_a", title = "title_a"))
    }

    @Test
    fun getUnavailableBookCount() {
        `when`(repository.getUnavailableBooks()).thenReturn(emptyList())
        assertEquals(0, service.getUnavailableBookCount())

        val bookJpa = BookJpa(
            author = "author_a", title = "title_a", isbn = "isbn_a", isReference = false, isAvailable = false
        )
        `when`(repository.getUnavailableBooks()).thenReturn(listOf(bookJpa))
        assertEquals(1, service.getUnavailableBookCount())
    }
}