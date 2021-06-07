AutomatedTellerMachine
===============================

'AutomatedTellerMachine' is back-end system for Indian Banks. It is a restful API with below features.

Application uses Apache Derby embedded DB. 
createschema.sql and insertdata.sql are used to create schema and setup accounts. 

1. Create Account
2. Withdraw money
3. Deposit money
4. Check balance

Main method
=========================
Please execute main method in ATMApplication.java to start the embedded tomcat server.

'AutomatedTellerMachine' provides a token based authentication. How ever authentication is disabled by default.
by setting property ignoreAuth to true in env.properties. Default value is true.

Path variables
=========================
token - String
accountnumber - long
amount - int
accountname - String
pin - int


Response codes
=========================
200 - ok
400 - Bad Request
500 - Internal Server Error
