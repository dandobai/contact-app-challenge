# Contact App Challenge

A full-stack contact management application built with Spring Boot (Kotlin) and Vue 3 (TypeScript). Featuring AWS S3 image storage, JWT authentication, and a modern UI.

---

## Tech Stack

- Backend: Kotlin, Spring Boot 3, Spring Security (JWT), AWS SDK (S3), SQLite
- Frontend: Vue 3, Pinia, TypeScript, TailwindCSS, Vite
- Infrastructure: Docker, Nginx (Planned for EC2 deployment)

---

## Project Structure

### Backend (Kotlin)
The backend follows a domain-driven service architecture:

backend/src/main/kotlin/com/dandobai/backend/
|-- auth/               # Authentication controllers and JWT security logic
|-- common/             # Shared logic
|   |-- exception/      # Global error handling and custom exceptions
|-- config/             # AWS S3 and Security configurations
|-- contact/            # Contact CRUD operations and business logic
|-- image/              # S3 service for handling file uploads
|-- user/               # User profile and avatar management

### Frontend (Vue 3)
The frontend is organized into modular components and state stores:

frontend/src/
|-- api/                # Axios configuration and API service calls
|-- assets/             # Static files and fonts
|-- components/         # UI Components
|   |-- contact/        # Contact-related modals and lists
|   |-- user/           # User profile and image modals
|   |-- layout/         # Header and navigation elements
|-- router/             # Vue Router navigation rules
|-- stores/             # Pinia stores for reactive state (Auth, Contacts)
|-- types/              # TypeScript interfaces and types
|-- views/              # Main page components (Home, Login, Dashboard)

---

## Setup and Installation

### 1. Backend Setup
1. Navigate to the backend directory.
2. Create a .env file with your AWS and JWT credentials.
3. Build and run:
   Command: ./gradlew bootRun

### 2. Frontend Setup
1. Navigate to the frontend directory.
2. Install dependencies:
   Command: npm install
3. Start the development server:
   Command: npm run dev

---

## Image Storage (AWS S3)

The application communicates directly with AWS S3 for image management:
- Contact Photos: Stored under /contacts/ folder.
- User Avatars: Stored under /users/ folder.
- Images are retrieved via S3 URLs.

---

## Author

Daniel Dobai - Full-stack Developer