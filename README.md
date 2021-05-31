# alore-booking

1. Create empty database
2. Update environment variables
3. Run Project
4. Use swagger to test APIs (Swagger URL: **http://localhost:8080/alore/swagger-ui.html**)

```
Environment Variables:
DB_URL=jdbc:mysql://localhost:3306/database_name?&autoReconnect=True;
DB_USERNAME=username;
DB_PASSWORD=password
```

API Base URL: **http://localhost:8080/alore**


<b>User Service</b>
```
1. Add User
Method: POST
URL: {baseUrl}/user/add
Sample Payload:
{
  "city": "Ariyalur",
  "email": "name@domain.com",
  "gender": "MALE"
}
```

```
2. Update User
Method: POST
URL: {baseUrl}/user/update
Sample Payload:
{
"city": "Chennai",
"email": "name@domain.com",
"gender": "MALE"
}
```

```
3. Get User
Method: GET
URL: {baseUrl}/user/{email}/get
```

```
4. Delete User
Method: POST
URL: {baseUrl}/user/{email}/delete
```

---

<b>Hotel Service</b>
```
1. Add Hotel
Method: POST
URL: {baseUrl}/hotel/add
Sample Payload:
{
  "acAvailable": 1,
  "city": "Ariyalur",
  "costPerRoom": 2000,
  "hotelName": "My Hotel",
  "mealsIncluded": 1,
  "restaurantAvailable": 1,
  "totalRooms": 100,
  "wifiAvailable": 0
}

Note: Unique hotelId will be there in response. For further operation holderId needed. 
```

```
2. Update Hotel By Date
Method: POST
URL: {baseUrl}/hotel/update
Sample Payload:
{
  "hotelId": 2,
  "acAvailable": 1,
  "city": "Ariyalur",
  "costPerRoom": 2000,
  "hotelName": "My Hotel",
  "mealsIncluded": 0,
  "restaurantAvailable": 1,
  "totalRooms": 100,
  "wifiAvailable": 0
}
```

```
3. Get Hotel
Method: GET
URL: {baseUrl}/hotel/get/{email}
```

```
4. Delete Hotel
Method: POST
URL: {baseUrl}/hotel/{hotelId}/delete
```

```
5. Add Hotel Review
Method: POST
URL: {baseUrl}/hotel/review/add
Sample Payload:
{
  "comments": "Goo Hotel",
  "email": "name@domain.com",
  "hotelId": 2,
  "rating": 7
}
```

```
5. Add Hotel Review
Method: POST
URL: {baseUrl}/hotel/review/add
Sample Payload:
{
  "comments": "Goo Hotel",
  "email": "name@domain.com",
  "hotelId": 2,
  "rating": 7
}
```

```
6. Delete Hotel Review
Method: POST
URL: {baseUrl}/hotel/review/delete
Sample Payload:
{
  "email": "name@domain.com",
  "hotelId": 2
}
```

```
7. Get Hotel Reviews
Method: GET
URL: {baseUrl}/hotel/{hotelId}/reviews
Parameters:
    gender [Optional]
    city [Optional]
```

```
8. Search Hotels
Method: GET
URL: {baseUrl}/hotel/search
Parameters:
    keyterm [Optional]
    gender [Optional]
    airConditioning [Optional]
    city [Optional]
    meals [Optional]
    restaurant [Optional]
    sortBy [Optional]
    wifi [Optional]
```