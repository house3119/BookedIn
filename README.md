# BookedIn
BookedIn is a community for readers. Make an account, share what you have been reading recently and see what others have been reading.

## Introduction
The main idea for BookedIn is to be a hub for readers. Add books to your collection, review and share them. See what others have been reading and what is popular.

BookedIn will be built mostly using Java SpringBoot. Thymeleaf will be used for template engine. BookedIn will use external SQL database (tba) to store data about users, books, reviews etc. Google Books API will be used to search for books:

https://developers.google.com/books/docs/v1/getting_started

https://developers.google.com/books/docs/v1/reference/volumes/list


This documentation describes Minimum Viable Product (MVP) if BookedIn.

## Specification

### User Groups
- Regular User
- Admin

### User Stories

1. As a user I need to be able to create an account and log in.
2. As a user I need to be able to search for books to add to my library.
3. As a user I want to be able to see all books in my library as a list.
4. As a user I need to be able to determine a status for a book in my library (want to read / currently reading / finished).
5. As a user I want to be able to write and review for book I have finished.
6. As a user I want to be able to be able to search for other users and add them to my friend list.
7. As a user I want to be able to see a list of my friends in my profile page.
8. As a user I want to be able to remove a friend from my friend list.
9. As a user I want to be able to update my password if needed.
10. as an admin I want to be able to remove possibly offensive reviews.

## User Interface
- Login view
  - Input username and password or else create a new account
- Create new account view
  - Create new account, afterwards redirect to Login view
- Profile view
  - Default view, see your own profile information and library of books
- Search books view
  - View for searching books and adding them to your library
- Add review view
  - Accesses from library view. Add review and rating for a book.
- Friend list view
  - See a list of your friends, links to their profiles and possibility of removing them as a friend
- Settings/ change password view
  - Change password and maybe add profile icon and other information about yourself
- (Activity view - see recent activity from your friends) --> If I have time
- (Top Books view - see most popular/trending books) --> If I have time

## Database
External SQL Database will be used.

Tables:
- Users --> Information about individual users, name, passwordhash, user icon url, user status etc.
- UsersBooks --> Information about which user has which book in their bookself (needed as relation between Users and Books is ManyToMany)
- Books --> Information about books
- Friends --> Information about who has added who to friends
- Reviews --> Information about single review: text, rating, user who wrote (fk), book (fk), likes

