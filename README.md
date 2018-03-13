# User registration API Kata

Develop a basic user registration API :

- a POST /users : create a user and send him a confirmation email
- a GET /users/{userId} : get an existing user


# Constraints :

- process with a TDD outside in approache : start with the acceptance test, and create step by step lower level unit tests plus associated implementation 
- each functional code must be covered by unit tests
- use mocks to test only one class at a time

To send an email, you are supposed to use this external API :

```
POST /email
{
    "email": "roy.fielding@acmee.com",
    "object": "Subscription OK",
    "content": "You has been successfully registered with the login royfd"
}

=> HTTP 201 CREATED

```

# Steps


1) First, create this acceptance test

And then implement the functional code to make it pass

```
POST /users
{
    "id": 1,
    "login": "royf",
    "email": "roy.fielding@acmee.com"
}

=> HTTP 201 CREATED

```

2) Then, change the acceptance test to add this step.

And then implement the functional code to make it pass

```
GET /users/1

=> HTTP 200 OK
{
    "id": 1,
    "login": "royf",
    "email": "roy.fielding@acmee.com"
}


```

3) Finally, change the acceptance test to verify in the end that the email external service is called. And make it pass