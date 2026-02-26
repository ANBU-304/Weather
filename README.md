🌤️ Weather Data Analysis API

A powerful REST API built with Spring Boot for analyzing 20 years of historical weather data for Delhi. Process large CSV datasets and query weather patterns, temperature trends, and statistical insights with ease.

Tech Stack: Java • Spring Boot • MySQL • Maven • Hibernate
Status: Actively Maintained
PRs Welcome 🚀

📖 About The Project

Weather Data Analysis API is a scalable backend system designed to process and analyze historical weather data efficiently.

It enables you to:

📊 Process large CSV datasets (20+ years of data)

🔍 Query weather conditions by date, month, or custom range

📈 Analyze yearly & monthly temperature trends

🌡️ Retrieve min, max & median temperature statistics

🗄️ Store optimized data in MySQL with indexing

Perfect for:

Academic projects

Data analysis practice

Backend portfolio showcase

Spring Boot learning

✨ Key Features

🚀 Batch CSV Processing (configurable chunk size)

🔍 Flexible Querying (date/month/year filters)

📊 Statistical Analysis Engine

🗄️ Optimized MySQL Storage

📝 Clean RESTful API Design

🛡️ Global Exception Handling

📦 Layered Architecture (Controller → Service → Repository)

🛠️ Built With
Technology	Purpose
Spring Boot	Backend framework
Java 21	Programming language
MySQL 8	Database
Hibernate / JPA	ORM
OpenCSV	CSV Processing
Maven	Build tool
Lombok	Boilerplate reduction
🚀 Quick Start
✅ Prerequisites

☕ Java 21+

🗄️ MySQL 8+

📦 Maven 3.6+

1️⃣ Clone the Repository
git clone https://github.com/ANBU-304/Weather.git
cd Weather
2️⃣ Create Database
CREATE DATABASE weather;
3️⃣ Configure application.properties
server.port=8087

spring.datasource.url=jdbc:mysql://localhost:3306/weather
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

weather.csv.batch-size=1000
weather.csv.file-path=E:/path/to/weather_data.csv
4️⃣ Run the Application
mvn clean install
mvn spring-boot:run

API runs at:

http://localhost:8087/api/weather
📡 API Reference
🔹 Health Check
GET /api/weather/test
🔹 Process CSV
POST /api/weather/process-csv

Response:

{
  "success": true,
  "message": "CSV processed successfully",
  "recordsProcessed": 15000
}
🔹 Get Weather by Date
GET /api/weather/date/2020-07-15
🔹 Monthly Data (All Years)
GET /api/weather/month/7
🔹 Year + Month
GET /api/weather/2020/7
📊 Statistical Endpoints
Yearly Statistics
GET /api/weather/stats/2020
Monthly Statistics
GET /api/weather/stats/2020/7
📂 Project Structure
Weather/
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
├── resources/
└── WeatherApplication.java

Clean layered architecture:

Controller → Service → Repository → Database

🐳 Docker Deployment
Build
mvn clean package
Run with Docker
docker build -t weather-api .
docker run -p 8087:8087 weather-api
🎯 Roadmap

✅ CSV Import

✅ Statistical Analysis

🔲 Authentication (JWT)

🔲 Redis Caching

🔲 Swagger/OpenAPI Documentation

🔲 GraphQL Support

🔲 Multi-city Support

🔲 CI/CD Pipeline

🔲 ML-based Weather Prediction

👨‍💻 Author

ANBU MANI P
Electronics & Communication Engineering
Backend Developer | Spring Boot Enthusiast

GitHub: https://github.com/ANBU-304
