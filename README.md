# Triapp

This is a semester-long project for CSCE548 -- Building Secure Software.

Triapp is an app that tracks data for people who are training for a triathlon. It holds data related to its users, workouts, and specific data related to the type of workout (run, bike, swim).

This project is still in development. Currently, it contains data, business, and service layers. Soon, a client layer will be added, and then tests will be run to analyze the app's security.

## Hosting the Database

To run Triapp you should have:

- Java 21+
- Maven 3.6+
- MySQL 8+
(In the future I hope to set up deployment with Docker to ease setup for users)

Clone this repo by navigating to your desired directory location and running:
`git clone https://github.com/ColeTM/triapp.git`

Make sure you set the MySQL credentials in `src\main\resources\application.properties`. You can also create a new connection on your device using the default credentials, `username=triapp_user` and `password=triapp_pass`.

Create the project's database by running the script provided at `sql\schema.sql`. This can be done by running the following command from the MySQL command line client while logged in as the correct user:
```PowerShell
SOURCE [device\path\to\project]\schema.sql.
```
There is also a sample dataset provided. If you would like to run it, you can use the same command replacing `schema.sql` with `seed.sql`.

At this point, you can host the database by running `TriappApplication.java` using your IDE. This also launches `InteractiveConsoleApp.java` which allows you to edit the database in the console with the given commands. To view the data in your browser, go to `http://localhost:8080/api/` and append either `users`, `workouts`, `runs`, `bikes`, or `swims` to view whichever data type you would like. If you edit the database in the console while you have the browser open, you can refresh the page to see the changes.


## DISCLAIMER:

Along with learning how to build secure software, this class also serves as an introduction to "vibe coding". Because of this learning objective and the fact that it is unreasonable to manually create a project of this caliber the amount of time we have, most of the code is written by ChatGPT, as is encouraged in the project instructions.