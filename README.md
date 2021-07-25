                            SocialMedia

This service allows user to see news feed of its own as well as own followings.
In order to run this server first run below command

Java Version Used: **JDK 11**
            
    mvn clean install -u

service will be up at port **9002**

Since we are using H2 database so once the service is up data base is accessible through

    localhost:9002/h2-console

    _username/password is available in properties file._

We have three entity:

    1) User

    2) Post

    3) Users_Follower

Available APIs

1) create user (POST)
   
    
    **`localhost:9002/users`**
   
It accepts User details in request body, Sample:-
   

    {
        "firstName": "first_name",
        "lastName": "last_name",
        "email": "email@gmail.com"
   }
    

2) Find User (GET)

        localhost:9002/users/{userId}

3) Post Feed (POST)
   

    localhost:9002/users/{userId}/posts
   

Request Body:- 

    {
        "content": "Testing NEWS feed"
    }


4) See News Feed (GET)
    
It shows user's data aw well as followings data sorted based on createdAt time descending order.


    localhost:9002/users/{userId}/feeds


5) Follow User(PATCH)
   

    localhost:9002/users/{followerId)}/follows/{followeeId}
   

6) Unfollow User(PATCH)
   

    localhost:9002/users/{followerId)}/unfollows/{followeeId}
   

7) See followings(GET)


    localhost:9002/users/{userId}/followers