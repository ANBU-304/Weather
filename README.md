🌤️ Weather Data Analysis API
A powerful REST API built with Spring Boot for analyzing 20 years of historical weather data for Delhi. Process large CSV datasets and query weather patterns, temperature trends, and statistical insights with ease.

Java
Spring Boot
MySQL
PRs Welcome

📖 About The Project
Weather Data Analysis API is a comprehensive solution for managing and analyzing historical weather data. Whether you're a data scientist, researcher, or just curious about weather patterns, this API provides powerful endpoints to:

📊 Process large weather datasets from CSV files
🔍 Query weather conditions for any date or time period
📈 Analyze temperature trends and statistics
🌡️ Track historical weather patterns across multiple years
Perfect for academic projects, weather analysis, or learning Spring Boot!

✨ Key Features
🚀 Fast CSV Processing: Batch processing with configurable chunk sizes for optimal performance
🔍 Flexible Queries: Search by date, month, year, or custom ranges
📊 Statistical Analysis: Get min, max, and median temperatures for any period
🗄️ Robust Storage: MySQL database with optimized indexing
📝 Clean API Design: RESTful endpoints following industry best practices
🛡️ Error Handling: Comprehensive error messages and logging
📦 Modular Architecture: Well-organized, maintainable codebase
🎯 What Can You Do?
Bash

# Find out: What was the weather like on your birthday in 2020?
GET /api/weather/date/2020-07-15

# Discover: How hot does Delhi get in summer?
GET /api/weather/stats/2020/7

# Analyze: Temperature trends across an entire year
GET /api/weather/stats/2020

# Explore: All weather data for monsoon season
GET /api/weather/month/7
🛠️ Built With
Technology	Purpose
Spring Boot	Backend framework
Java	Programming language
MySQL	Database
Maven	Build tool
Hibernate	ORM
OpenCSV	CSV processing
Lombok	Code generation
🚀 Quick Start
Prerequisites
Make sure you have these installed:

Bash

☕ Java 21 or higher
🗄️ MySQL 8.0+
📦 Maven 3.6+
Installation
1️⃣ Clone the repository

Bash

git clone https://github.com/ANBU-304/Weather.git
cd Weather
2️⃣ Set up MySQL Database

SQL

CREATE DATABASE weather;
3️⃣ Configure the application

Edit src/main/resources/application.properties:

properties

spring.datasource.url=jdbc:mysql://localhost:3306/weather
spring.datasource.username=root
spring.datasource.password=yourpassword

# Update this with your CSV file path
weather.csv.file-path=E:/path/to/your/weather_data.csv
4️⃣ Build and run

Bash

mvn clean install
mvn spring-boot:run
🎉 The API is now running at http://localhost:8087

📡 API Reference
Base URL
text

http://localhost:8087/api/weather
Quick Start Endpoints
🏥 Health Check
http

GET /test
✅ Verify the API is up and running

📁 Load Data
http

POST /process-csv
📤 Process and import CSV data into the database

Response:

JSON

{
    "success": true,
    "message": "CSV processed successfully",
    "recordsProcessed": 15000
}
📊 Get Record Count
http

GET /count
📈 Check how many weather records are loaded

🌤️ Weather Queries
By Specific Date
http

GET /date/{date}
Example:

Bash

GET /api/weather/date/2020-07-15
Response:

JSON

{
    "month": 7,
    "recordCount": 24,
    "data": [
        {
            "dateTime": "2020-07-15T00:00:00",
            "conditions": "Clear",
            "temperature": 28.5,
            "humidity": 65.0,
            "pressure": 1013.25
        }
    ]
}
By Month (All Years)
http

GET /month/{month}
Example:

Bash

GET /api/weather/month/7  # All July data
By Year and Month
http

GET /{year}/{month}
Example:

Bash

GET /api/weather/2020/7  # July 2020
📊 Statistical Analysis
Yearly Statistics
http

GET /stats/{year}
Example:

Bash

GET /api/weather/stats/2020
Response:

JSON

{
    "year": 2020,
    "statistics": [
        {
            "month": 1,
            "monthName": "JANUARY",
            "temperatureStats": {
                "high": 25.5,
                "median": 18.2,
                "minimum": 10.1
            }
        }
    ]
}
Monthly Statistics
http

GET /stats/{year}/{month}
Example:

Bash

GET /api/weather/stats/2020/7
Response:

JSON

{
    "year": 2020,
    "month": 7,
    "statistics": {
        "high": 42.5,
        "median": 35.2,
        "minimum": 28.1
    }
}
💻 Usage Examples
Using cURL
Bash

# Test the API
curl http://localhost:8087/api/weather/test

# Process CSV data
curl -X POST http://localhost:8087/api/weather/process-csv

# Get weather for a specific date
curl http://localhost:8087/api/weather/date/2020-01-15

# Get monthly statistics
curl http://localhost:8087/api/weather/stats/2020/7
Using Postman
Import the collection from postman/Weather-API.postman_collection.json
Set base URL: http://localhost:8087
Run requests sequentially:
✅ Test API
📁 Process CSV
🔍 Query Data
Using JavaScript
JavaScript

// Fetch weather data
async function getWeather(date) {
    const response = await fetch(
        `http://localhost:8087/api/weather/date/${date}`
    );
    const data = await response.json();
    console.log(data);
}

getWeather('2020-07-15');
Using Python
Python

import requests

# Get weather statistics
response = requests.get('http://localhost:8087/api/weather/stats/2020/7')
data = response.json()
print(data)
📂 Project Structure
text

Weather/
├── src/
│   ├── main/
│   │   ├── java/com/securin/weather/
│   │   │   ├── WeatherApplication.java       # Main entry point
│   │   │   ├── controller/
│   │   │   │   └── WeatherController.java    # API endpoints
│   │   │   ├── service/
│   │   │   │   ├── CsvProcessingService.java # CSV logic
│   │   │   │   └── WeatherService.java       # Business logic
│   │   │   ├── repository/
│   │   │   │   └── WeatherRecordRepository.java
│   │   │   ├── entity/
│   │   │   │   └── WeatherRecord.java        # Database model
│   │   │   └── dto/                          # Response objects
│   │   └── resources/
│   │       └── application.properties        # Configuration
│   └── test/                                 # Unit tests
├── data/                                     # Sample CSV files
├── postman/                                  # Postman collection
├── pom.xml                                   # Dependencies
└── README.md
🎨 CSV Data Format
Your CSV file should follow this format:

csv

datetime,conditions,temperature,dewpoint,heatindex,windchill,humidity,pressure,precipitation,visibility,winddirectiondegrees,winddirection,windspeed,windgust,fog,rain,snow,hail,thunder,tornado
2020-01-01 00:00:00,Clear,15.5,10.2,15.5,14.0,65.0,1013.25,0.0,10.0,180,S,5.5,8.0,0,0,0,0,0,0
2020-01-01 01:00:00,Partly Cloudy,14.8,9.5,14.8,13.2,68.0,1013.50,0.0,9.5,175,S,5.2,7.5,0,0,0,0,0,0
Required Columns:

datetime - Format: YYYY-MM-DD HH:MM:SS
temperature - Numeric value
humidity - Percentage (0-100)
pressure - In hPa or mb
⚙️ Configuration
Application Properties
properties

# Server Configuration
server.port=8087

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/weather
spring.datasource.username=root
spring.datasource.password=1234

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# CSV Processing
weather.csv.batch-size=1000
weather.csv.file-path=E:/Assessment 2/testset.csv

# File Upload
spring.servlet.multipart.max-file-size=100MB
spring.servlet.multipart.max-request-size=100MB
🐛 Troubleshooting
❌ Database Connection Failed
Error: Access denied for user 'root'@'localhost'

Fix:

Bash

# Verify MySQL is running
mysql -u root -p

# Update credentials in application.properties
❌ CSV File Not Found
Error: File not found at path

Fix:

Check file path in application.properties
Use forward slashes: E:/folder/file.csv
Verify file exists
❌ Port Already in Use
Error: Port 8087 is already in use

Fix:

properties

# Change port in application.properties
server.port=8088
❌ Out of Memory
Fix:

Bash

# Increase heap size
java -Xmx2048m -jar weather-api.jar
🧪 Running Tests
Bash

# Run all tests
mvn test

# Run specific test
mvn test -Dtest=WeatherControllerTest

# Run with coverage
mvn test jacoco:report
🚀 Deployment
Using JAR
Bash

# Build
mvn clean package

# Run
java -jar target/weather-api-1.0.0.jar
Using Docker
Dockerfile

# Dockerfile
FROM openjdk:17-jdk-slim
COPY target/weather-api-1.0.0.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "/app.jar"]
Bash

# Build image
docker build -t weather-api .

# Run container
docker run -p 8087:8087 weather-api
🎯 Roadmap
 CSV data import
 Basic weather queries
 Statistical analysis
 Authentication & Authorization
 Data caching with Redis
 GraphQL support
 Real-time weather integration
 Weather prediction ML model
 Docker Compose setup
 CI/CD pipeline
 API documentation with Swagger
 Multi-city support
🤝 Contributing
Contributions make the open-source community amazing! Any contributions you make are greatly appreciated.

Fork the Project
Create your Feature Branch (git checkout -b feature/AmazingFeature)
Commit your Changes (git commit -m 'Add some AmazingFeature')
Push to the Branch (git push origin feature/AmazingFeature)
Open a Pull Request
Contribution Guidelines
Write clear commit messages
Add tests for new features
Update documentation
Follow Java coding conventions
Keep PRs focused and small
👨‍💻 Author
ANBU-304

🐙 GitHub: @ANBU-304
📧 Feel free to reach out for questions or collaborations!
🙏 Acknowledgments
Spring Boot - Amazing framework
OpenCSV - CSV processing library
MySQL - Reliable database
Lombok - Reducing boilerplate
All contributors who help improve this project
📞 Support
Need help? Here's how to get support:

📖 Check the documentation
🐛 Search existing issues
💬 Ask a question in discussions
🆕 Create a new issue
💡 Pro Tips
🔄 Use batch processing for large datasets
📊 Index your database for faster queries
🗜️ Compress CSV files before processing
🔍 Use query parameters for filtering
📝 Enable logging for debugging
🚀 Use connection pooling for better performance
📈 API Endpoints Summary
Method	Endpoint	Description
GET	/test	Health check
GET	/info	API information
GET	/csv-config	Check CSV configuration
POST	/process-csv	Import CSV data
GET	/count	Get record count
GET	/date/{date}	Weather by date
GET	/month/{month}	Weather by month
GET	/{year}/{month}	Weather by year-month
GET	/stats/{year}	Yearly statistics
GET	/stats/{year}/{month}	Monthly statistics
DELETE	/clear	Clear all data
<div align="center">
If you found this project helpful, please consider giving it a ⭐!

Made with ❤️ and ☕ using Spring Boot

⬆ Back to Top

</div>
