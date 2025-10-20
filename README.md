# Medical Prescription Management System

A comprehensive medical prescription management system built with Spring Boot, designed for doctors to manage patient prescriptions, medical history, and generate PDF prescriptions.

## Features

### Core Features
- **Doctor Dashboard**: Overview of prescriptions, patients, and statistics
- **Patient Management**: Complete patient information and medical history
- **Prescription Management**: Create, view, and manage medical prescriptions
- **PDF Generation**: Generate and download prescription PDFs
- **Disease History**: Track patient disease history and treatments
- **Medicine Database**: Comprehensive medicine information and inventory

### Technical Features
- **REST API**: Complete RESTful API for all operations
- **Database Integration**: MySQL database with JPA/Hibernate
- **PDF Generation**: iText7 for professional prescription PDFs
- **Responsive UI**: HTML/CSS frontend with mobile-friendly design
- **Data Validation**: Comprehensive input validation and error handling

## Technology Stack

- **Backend**: Spring Boot 3.2.x, Java 21 LTS (recommended)
- **Database**: MySQL 8.0
- **Frontend**: HTML5, CSS3, Thymeleaf
- **PDF Generation**: iText7
- **Build Tool**: Maven
- **ORM**: Spring Data JPA with Hibernate

## Prerequisites

Before running the application, ensure you have:

1. Java 21 (LTS). Java 24 can be installed side-by-side, but for best compatibility use Java 21 to build/run.
2. MySQL 8.0 or higher
3. Git (for cloning the repository)
4. You do NOT need Maven installed globally — use the included Maven Wrapper

## Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd prescription-management
```

### 2. Database Setup

Create a MySQL database:
```sql
CREATE DATABASE medical_prescription_db;
```

Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build and Run (Windows PowerShell)

Use the Maven Wrapper included in this repository:

```powershell
# Verify Maven/Java versions (should show Java 21)
.\mvnw.cmd -v

# Build the application
.\mvnw.cmd clean package -DskipTests

# Run the application
java -jar target\prescription-management-0.0.1-SNAPSHOT.jar
```

If your shell is currently using a different Java version, temporarily point it to JDK 21 for this session:

```powershell
$env:JAVA_HOME = "C:\\Program Files\\Eclipse Adoptium\\jdk-21.0.8.9-hotspot"
$env:Path = "$env:JAVA_HOME\\bin;$env:Path"
java -version
```

The application will start on `http://localhost:8080`

## Usage

### Accessing the System

1. Navigate to `http://localhost:8080`
2. The system will redirect to the doctor dashboard
3. Sample data is automatically loaded on first startup

### Key Features Usage

#### 1. Doctor Dashboard
- View prescription statistics
- Access recent prescriptions
- Quick navigation to all features

#### 2. Creating Prescriptions
- Select patient from dropdown
- Enter diagnosis and symptoms
- Add multiple medicines with dosage instructions
- Set follow-up dates

#### 3. PDF Generation
- View prescriptions in browser
- Download PDF files
- Professional prescription format

#### 4. Patient Management
- View complete patient information
- Access prescription history
- Track disease history

## API Endpoints

### Patient API (`/api/patients`)
- `GET /api/patients` - Get all patients
- `GET /api/patients/{id}` - Get patient by ID
- `POST /api/patients` - Create new patient
- `PUT /api/patients/{id}` - Update patient
- `DELETE /api/patients/{id}` - Delete patient
- `GET /api/patients/search?searchTerm=` - Search patients

### Prescription API (`/api/prescriptions`)
- `GET /api/prescriptions` - Get all prescriptions
- `GET /api/prescriptions/{id}` - Get prescription by ID
- `POST /api/prescriptions` - Create new prescription
- `PUT /api/prescriptions/{id}` - Update prescription
- `DELETE /api/prescriptions/{id}` - Delete prescription
- `GET /api/prescriptions/doctor/{doctorId}` - Get prescriptions by doctor

### PDF Generation (`/pdf`)
- `GET /pdf/prescription/{id}` - View prescription PDF
- `GET /pdf/prescription/{id}/download` - Download prescription PDF

## Database Schema

### Core Entities

#### Doctors
- Personal information and medical credentials
- License number and specialization

#### Patients
- Complete patient demographics
- Medical history and allergies
- Emergency contact information

#### Prescriptions
- Diagnosis and symptoms
- Doctor and patient relationships
- Creation and follow-up dates

#### Medicines
- Medicine details and dosage forms
- Pricing and inventory information
- Side effects and contraindications

#### Prescription Medicines
- Medicine prescriptions with dosage
- Frequency and duration
- Special instructions

#### Disease History
- Patient disease tracking
- Treatment history
- Disease status and severity

## Configuration

### Application Properties
Key configuration options in `application.properties`:

```properties
# Database Configuration
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/medical_prescription_db}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:password}

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server Configuration
server.port=8080
```

## Development

### Project Structure
```
src/
├── main/
│   ├── java/com/medical/prescription/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # Web and API controllers
│   │   ├── entity/          # JPA entities
│   │   ├── repository/      # Data repositories
│   │   ├── service/         # Business logic services
│   │   └── PrescriptionManagementApplication.java
│   └── resources/
│       ├── static/css/      # CSS stylesheets
│       ├── templates/       # Thymeleaf templates
│       └── application.properties
└── test/                    # Test classes
```

### Adding New Features

1. **New Entity**: Create entity class in `entity` package
2. **Repository**: Create repository interface extending `JpaRepository`
3. **Service**: Create service interface and implementation
4. **Controller**: Create web or API controller
5. **Frontend**: Add HTML templates and CSS styles

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials or set environment variables DB_URL/DB_USERNAME/DB_PASSWORD
   - Ensure database exists

2. **Port Already in Use**
   - Change server port in `application.properties`
   - Or stop other applications using port 8080

3. **PDF Generation Issues**
   - Check iText7 dependencies
   - Verify file permissions

### Logs
Application logs are available in the console. Enable debug logging:
```properties
logging.level.com.medical.prescription=DEBUG
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions:
- Create an issue in the repository
- Check the troubleshooting section
- Review the API documentation

## Future Enhancements

- User authentication and authorization
- Email prescription delivery
- Medicine inventory management
- Appointment scheduling
- Mobile application
- Advanced reporting and analytics

## Notes on Java 24

- The project can compile and may run on Java 24, but Spring Boot 3.2.x is officially tested with Java 21.
- If you must run on Java 24 and encounter issues, consider upgrading Spring Boot to 3.4.x.


