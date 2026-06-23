# Dynamic QR Code Platform

## Overview

Dynamic QR Code Platform is a full-stack web application that allows users to generate, manage, and update QR codes without regenerating them. The application supports text-based and image-based QR codes, user authentication using JWT, cloud image storage through Cloudinary, and a personalized dashboard for managing generated QR codes.

---

## Features

### Authentication

* User Registration
* User Login
* JWT-based Authentication
* Protected Endpoints

### QR Code Generation

* Generate QR Codes from Text
* Generate QR Codes from Images
* Dynamic QR Architecture
* QR Code Download

### Dashboard

* View All Generated QR Codes
* User-Specific QR Management
* Edit Existing Dynamic Text QR Codes
* Download Generated QR Codes

### Cloud Storage

* Cloudinary Integration
* Secure Image Hosting
* QR Image Storage

---

## Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* JWT Authentication
* MySQL

### Third-Party Services

* Cloudinary
* ZXing QR Code Library

---

## Project Architecture

User → Frontend → Spring Boot API → MySQL Database

User → Frontend → Spring Boot API → Cloudinary

QR Scan → Dynamic Endpoint → Database Lookup → Redirect Content

---

## API Endpoints

### Authentication

POST /sign_up

POST /login

### QR Generation

POST /encodeText

POST /encodeImage

### Dashboard

GET /dashboard

### Dynamic QR

GET /qr/{id}

### QR Management

PUT /{id}/editText
PUT /{id}/editImage

---

## Database Design

### User

* id
* name
* email
* password

### QR Entity

* id
* text
* imageURL
* place-id
* qrURL
* redirectURL
* user_id

Relationship:

User (1) → (Many) QR Codes

---

## Setup Instructions

### Clone Repository

git clone <repository-url>

cd Dynamic-QR-Platform

### Configure Database

Create a MySQL database and update application properties.

### Configure Cloudinary

Add:

* Cloud Name
* API Key
* API Secret

### Run Application

mvn spring-boot:run

Application runs on:

http://localhost:8080

---

## Future Improvements

* Scan Analytics
* QR Expiration
* Password Protected QR Codes
* QR Scanner Integration
* Deployment on Render/Railway
* Add Frontend
* Admin Dashboard

---

## Author

Janhvi Pandey

B.Tech CSE Student

Passionate about Backend Development, Spring Boot, and Problem Solving.
