## myJDBCDriver
Partial implementation of custom JDBC Driver with an example server storing data about one table `Books`.  
It uses TCP connection to communicate with server which can provide information about data.

## DbService
Implementation of server listening on TCP socket. It receives request about stored data in one table `Books` and also request to insert data into this table.  
The messages' form:
```
"SELECT * from Books where title = <TITLE>;"
"SELECT * from Books where author = <AUTHOR>;"
"INSERT INTO Books (title, author) VALUES (<TITLE>, <AUTHOR>);"
```  

### Database schema
```
{
  "Book":
  {
    "author": String,
    "title": String
  }
}
```

## DbRunner
Runs DbService on port 5433.

## DbClient
An example of using myJDBCDriver with DbService.