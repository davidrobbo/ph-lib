package health.patchwork.library.repository

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class BookRepositoryImplTest {

    @Test
    fun findByAuthor() {
        val book1ByAuthorA = BookJpa(
            author = "author_a", title = "title_a", isbn = "isbn_a", isReference = false, isAvailable = true
        )
        val book2ByAuthorA = BookJpa(
            author = "author_a", title = "title_b", isbn = "isbn_b", isReference = false, isAvailable = true
        )
        val book3ByAuthorB = BookJpa(
            author = "author_b", title = "title_c", isbn = "isbn_c", isReference = false, isAvailable = true
        )
        val repository = BookRepositoryImpl(books = listOf(book1ByAuthorA, book2ByAuthorA, book3ByAuthorB))

        assert(listOf(book1ByAuthorA, book2ByAuthorA) == repository.findByAuthor(author = "author_a"))
        assert(listOf(book3ByAuthorB) == repository.findByAuthor(author = "author_b"))
        assert(emptyList<BookJpa>() == repository.findByAuthor(author = "foobar"))
    }

    @Test
    fun findByTitle() {
        val book1WithTitleA = BookJpa(
            author = "author_a", title = "title_a", isbn = "isbn_a", isReference = false, isAvailable = true
        )
        val book2WithTitleB = BookJpa(
            author = "author_a", title = "title_b", isbn = "isbn_b", isReference = false, isAvailable = true
        )
        val book3WithTitleA = BookJpa(
            author = "author_b", title = "title_a", isbn = "isbn_c", isReference = false, isAvailable = true
        )
        val repository = BookRepositoryImpl(books = listOf(book1WithTitleA, book2WithTitleB, book3WithTitleA))

        assert(listOf(book1WithTitleA, book3WithTitleA) == repository.findByTitle(title = "title_a"))
        assert(listOf(book2WithTitleB) == repository.findByTitle(title = "title_b"))
        assert(emptyList<BookJpa>() == repository.findByTitle(title = "foobar"))
    }

    @Test
    fun findByAuthorAndTitle() {
        val book1AuthorATitleA = BookJpa(
            author = "author_a", title = "title_a", isbn = "isbn_a", isReference = false, isAvailable = true
        )
        val book2AuthorBTitleB = BookJpa(
            author = "author_b", title = "title_b", isbn = "isbn_b", isReference = false, isAvailable = true
        )
        val repository = BookRepositoryImpl(books = listOf(book1AuthorATitleA, book2AuthorBTitleB))

        assert(listOf(book1AuthorATitleA) == repository.findByAuthorAndTitle(author = "author_a", title = "title_a"))
        assert(listOf(book2AuthorBTitleB) == repository.findByAuthorAndTitle(author = "author_b", title = "title_b"))
        assert(emptyList<BookJpa>() == repository.findByAuthorAndTitle(author = "author_a", title = "foobar"))
        assert(emptyList<BookJpa>() == repository.findByAuthorAndTitle(author = "foobar", title = "title_a"))
    }

    @Test
    fun findByISBN() {
        val book1WithIsbnA = BookJpa(
            author = "author_a", title = "title_a", isbn = "isbn_a", isReference = false, isAvailable = true
        )
        val book2WithIsbnA = BookJpa(
            author = "author_a", title = "title_b", isbn = "isbn_a", isReference = false, isAvailable = true
        )
        val book3WithIsbnB = BookJpa(
            author = "author_b", title = "title_c", isbn = "isbn_b", isReference = false, isAvailable = true
        )
        val repository = BookRepositoryImpl(books = listOf(book1WithIsbnA, book2WithIsbnA, book3WithIsbnB))

        assert(listOf(book1WithIsbnA, book2WithIsbnA) == repository.findByISBN(isbn = "isbn_a"))
        assert(listOf(book3WithIsbnB) == repository.findByISBN(isbn = "isbn_b"))
        assert(emptyList<BookJpa>() == repository.findByISBN(isbn = "foobar"))
    }

    @Test
    fun setAvailable() {
        val libraryBook = BookJpa(
            author = "author_a", title = "title_a", isbn = "isbn_a", isReference = false, isAvailable = true
        )
        val nonLibraryBook = BookJpa(
            author = "author_b", title = "title_b", isbn = "isbn_b", isReference = false, isAvailable = true
        )
        val repository = BookRepositoryImpl(books = listOf(libraryBook))

        repository.setAvailable(libraryBook, false)
        assertFalse(libraryBook.isAvailable)

        repository.setAvailable(libraryBook, true)
        assertTrue(libraryBook.isAvailable)

        repository.setAvailable(nonLibraryBook, false)
        assertTrue(nonLibraryBook.isAvailable)
        assertTrue(libraryBook.isAvailable)
    }

    @Test
    fun getUnavailableBooks() {
        val availableBook = BookJpa(
            author = "author_a", title = "title_a", isbn = "isbn_a", isReference = false, isAvailable = true
        )
        val unavailableBook = BookJpa(
            author = "author_b", title = "title_b", isbn = "isbn_b", isReference = false, isAvailable = false
        )
        val repository = BookRepositoryImpl(books = listOf(availableBook, unavailableBook))

        assert(listOf(unavailableBook) == repository.getUnavailableBooks())

        repository.setAvailable(availableBook, false)

        assert(listOf(availableBook, unavailableBook) == repository.getUnavailableBooks())
    }
}