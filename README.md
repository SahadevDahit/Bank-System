Features

---

1. There will be the two types of users i.e. admin and user
2. Spring Security with database login and signup with security filterChain
3. JSON WEB TOKEN (JWT) validation for authentication and authorization
4. Only admin is allowed to create and update the bank details
5. Account creation and update as well 
6. There will be two types of transaction of money

      1.  Transaction provides the features of withdraw and deposit but according to the credit limit

      2.  Transfer of money from sender to receiver account with sufficient amount  along with  pin  code verification

All the API available in the postman collection in the repsitory

![diagram-export-5-30-2024-1_36_10-PM](https://github.com/SahadevDahit/Bank-System/assets/81854544/f59586b9-7994-4d84-889a-78b528111a76)

```jsx
// code  that generate this diagram in eraser.io
users [icon: user, color: blue] {
    id string pk
    email string
    password string
    address string
    phoneNumber string
    dob Date
    role string 
    status boolean
}

bank [icon: bank, color: blue] {
    id string pk
    name string
    address string
    location string
    contact string
    email string
    status boolean
}

Account [icon: aws-account] {
    id string pk
    accountNo string
    account_type string
    balance BigDecimal
    date_created Date
    credit_limit BigDecimal
    pincode string
    status boolean
}

Transaction [icon: bank] {
    id string pk
    transaction_type string
    amount BigDecimal
    transaction_date Date
}

transfer [icon: bank, color: green] {
    id string pk
    senderAccount timestamp
    receiverAccount timestamp
    amount BigDecimal
    transaction_date Date
}

users.id>bank.user_id (fk)
users.id>Account.users_id (fk)
Transaction.accountNo<>Account.accountNo

transfer.senderAccount <>Account.accountNo
transfer.receiverAccount <>Account.accountNo
```

Future commit

1. Frontend 
2. CI/CD deployment in the cloud and DevOps Enviroment

Keep in touch…………………………………..

Thanks for your time………………………………………
