# BookedIn
BookedIn is a simple community for readers. Make an account, share and review what you have been reading recently and see what others have been reading.

## Introduction
The main idea for BookedIn is to be a small hub for readers. Add books to your collection, review and share them. See what others have been reading and what is popular.

BookedIn will be built mostly using Java SpringBoot. Thymeleaf will be used for template engine. BookedIn will use external SQL database (tba) to store data about users, books, reviews etc. Google Books API will be used to search for books:

https://developers.google.com/books/docs/v1/getting_started

https://developers.google.com/books/docs/v1/reference/volumes/list


This documentation describes Minimum Viable Product (MVP) if BookedIn.

## Specification

### User Groups
- Regular User
- Admin User

### User Stories

1. As a user I need to be able to create an account and log in.
2. As a user I need to be able to search for books to add to my library.
3. As a user I want to be able to see all books in my library as a list.
4. As a user I need to be able to determine a status for a book in my library (want to read / currently reading / finished).
5. As a user I want to be able to write and review for book I have finished.
6. As a user I want to be able to be able to search for other users and follow them.
7. As a user I want to be able to see a list of who i'm following in my profile page.
8. As a user I want to be able to stop following a user.
9. As a user I want to be able to update my password if needed.
10. As an admin user I want to be able to remove possibly offensive reviews.

## User Interface
**Views when logged out**
- Login view
  - Input username and password or else create a new account
- Create new account view
  - Create new account, afterwards redirect to Login view

**Views when logged in**
- When logged in, all views will incorporate a navbar
- Profile view
  - Default view, see your own profile information and library of books
- Search books view
  - View for searching books and adding them to your library
- Add review view
  - Accessed propbably from library view. Add review and rating for a book.
- Following list view
  - See a list of who you're following, links to their profiles and possibility of stopping following them
- Settings/ change password view
  - Change password and maybe add profile icon and other information about yourself
- (Activity view - see recent activity from people you follow) --> If time
- (Top Books view - see most popular/trending books) --> If time

## Database
External SQL Database will be used.

![Image of db](https://github.com/house3119/BookedIn/blob/main/BookedIn_db.pdf)

> ### _Users_
> _Information about individual users._
>
> Field | Type | Description
> ------ | ------ | ------
> User_id | int, PK, autonumber, not null | User id and primary key.
> Username | varchar(100), not null | Username
> Avatar_url | varchar(200) | URL for avatar
> Password_hash | varchar(60), not null | Password hash
> Country | number, FK | User country, reference to Countries table
> Age | number | User age
> Account_type | number, FK, not null | User account type, reference to Account_types table.
> 
>> ### _Countries_
> _Information about account types._
>
> Field | Type | Description
> ------ | ------ | ------
> Country_id | int, PK, autonumber, not null | Country id and primary key.
> Name | varchar(100), not null | Name of the country
> 
> ### _Account_types_
> _Information about account types._
>
> Field | Type | Description
> ------ | ------ | ------
> Account_type_id | int, PK, autonumber, not null | Account type id and primary key.
> Account_type | varchar(100), not null | Account type (admin, user etc.)
> Description | varchar(200) | Description of account type
>
> ### _Books_
> _Books saved to database._
>
> Field | Type | Description
> ------ | ------ | ------
> Book_id | varchar(80), PK, not null | Book id taken from Google API
> Title | varchar(100), not null | Title of the book
> Author | varchar(100), not null | Author of the book
> Published | number, not null | Year published
> Description | varchar(3000), not null | Description of the book
> Isbn | varchar(13), not null | ISBN of the book
> Page_count | number, not null | Pagecount of the book
> Language | varchar(2), not null | Language of the book, reference to Languages table
> Img_link | varchar(200), not null | Image link for the book
>
>  ### _Languages_
> _Information on languages._
>
> Field | Type | Description
> ------ | ------ | ------
> Language_id | varchar(2), PK, not null | Language id
> Language | varchar(40), not null | Language name
> 
>  ### _UsersBooks_
> _Information on who has added which books to their library._
>
> Field | Type | Description
> ------ | ------ | ------
> Row_id | int, autonumber, PK, not null | Row identifier and PK
> User_id | int, FK, not null | Reference to user
> Book_id | int, FK, not null | Reference to a book
> Date | date, not null, auto | When was added
>
>  ### _Reviews_
> _Information on reviews._
>
> Field | Type | Description
> ------ | ------ | ------
> Review_id | int, autonumber, PK, not null | Review id and PK
> User_id | int, FK, not null | Reference to user
> Book_id | int, FK, not null | Reference to a book
> Date | date, not null, auto | When was added
> Review | varchar(3000), not null | Text review
> Rating | int (1-5), not null | Rating
> 
> ### _Following_
> _Information on users following each other._
>
> Field | Type | Description
> ------ | ------ | ------
> Row_id | int, autonumber, PK, not null | Row identifier and PK
> Follower_id | int, FK, not null | Reference to user who is following
> Followed_id | int, FK, not null | Reference to user who is being followed
> Date | date, not null, auto | When was added


