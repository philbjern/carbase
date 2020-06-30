# carbase

## About
Carbase is a full stack web appliction for accessing database of cars and their users. Server application was build using Spring Boot, app exposes basic CRUD API and communicate with MySQL database using Spring Data. Web client app was build with Vue.js.

Features:

* Listing all users and cars

* Editing user profiles and car info

* Adding new users and cars

* Deleting users and cars

## Demo
App demo

![Application demo](video/showcase.gif)

## Installation
* Clone repository `git clone https://github.com/fbiernat/carbase.git`

* Run database setup script `carbase-server/src/main/resources/carbase-setup.sql`

* Open `carbase-server` project in IDE, and run `CarbaseApplication.java`

* Go to `carbase-vue` directory and run `npm install`

* Run `npm run dev` to start development server

* Go to web browser and open `localhost:8080` to access web application
