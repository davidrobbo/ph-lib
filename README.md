# Tech test

## Back-End Test Instructions:

Please create a GitHub repository containing code for a book lending library. Use the stories below for requirements.
This is just a text-based exercise - no GUI code is required.
We also don’t expect a DB or persistence layer - storing data in memory is fine. Just prove it works by calling the relevant functions from other code.
Please don’t spend any more than 2 to 3 hours on this. We don’t expect a complete solution, but something that we can use as the basis for a follow-on conversation.
When you are ready, please send us a link to your repository.

## Context

I have many books which I would like to share with my community. That sounds like a book-lending library. Please write some software to help me do that.

## Stories

 - As a library user, I would like to be able to find books by my favourite author, so that I know if they are available in the library.
 - As a library user, I would like to be able to find books by title, so that I know if they are available in the library.
 - As a library user, I would like to be able to find books by ISBN, so that I know if they are available in the library.
 - As a library user, I would like to be able to borrow a book, so I can read it at home.
 - As the library owner, I would like to know how many books are being borrowed, so I can see how many are outstanding.
 - As a library user, I should be to prevented from borrowing reference books, so that they are always available.

## Assumptions/justifications

 - ISBN "identifies a book's edition, publisher, and physical properties like trim size, page count, and binding type", therefore 2 copies of the same book can share an ISBN
 - A natural way by which a library user would wish to borrow a book would be either by:
   - providing a title and author, and not caring about the isbn
   - providing an isbn
 - Optimizing for time complexity (i.e. findBy<Whatever> in O(1)) not required
   - Therefore I favoured for simple data structure and List.filter in repository layer (seemed good enough)
   - If optimizing for time complexity considered paramount, would look to maintain Maps to enable O(1) time
 - Synchronizing on BookServiceImpl.borrowBook simplest way to ensure thread-safe book borrowing (seemed good enough)
   - More performant mutex or Read/Write locks can be introduced if any issues around performance
 - A library user should be able to find books even if already borrowed so they know what's available (but should be prevented from borrowing if already borrowed or a reference copy)
 - The tests satisfy the following - 'Just prove it works by calling the relevant functions from other code.'
   - Unsure if I've misunderstood the expectation that the library code should be exposed (i.e.) by HTTP, if so, I would wire up the service layer behind a Spring Boot RestController
 - While on the face of it, it may seem there is duplication in data classes (BookJpa vs Book), my intention here is to show the separate layers of the application with separation of concerns, on the assumption that it will lead to easier refactoring and extension of the codebase in due course
 - "As the library owner, I would like to know how many books are being borrowed, so I can see how many are outstanding." - took to mean the value is in knowing the number outstanding, as opposed to the contents/books outstanding, hence service layer need only expose a count

## How to run it

Ensure you have java 17 installed, then unit tests can be run from the command line with the following command:

```bash
./gradlew test
```