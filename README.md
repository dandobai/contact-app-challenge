# Contact App Challenge

A full-stack contact management application built with **Spring Boot (Kotlin)** on the backend and **Vue 3 + Pinia + TailwindCSS** on the frontend. Supports contact CRUD operations, image uploads (S3), and user profile avatar management.

---

## Features

### Backend (Spring Boot + Kotlin)
- REST API for contacts management
- User authentication (JWT)
- Image upload to AWS S3
- User avatar upload + retrieval
- Contact image upload + retrieval
- Clean service-based architecture

### Frontend (Vue 3 + Pinia + Tailwind)
- Contact list with CRUD operations
- Contact modal with image preview
- User avatar upload modal
- Global auth store with token handling
- S3-based image rendering
- Clean, dark UI with TailwindCSS

---

## Tech Stack

| Layer | Technologies |
|-------|--------------|
| **Backend** | Kotlin, Spring Boot, Spring Web, Spring Security, AWS SDK |
| **Frontend** | Vue 3, Pinia, TypeScript, TailwindCSS |
| **Storage** | AWS S3, SQLite (dev) / PostgreSQL (prod) |
| **Build** | Gradle (Kotlin DSL), Vite |

---

## Setup & Installation

### 1. Clone the repository

    git clone [https://github.com/](https://github.com/)<your-username>/contact-app-challenge.git
    cd contact-app-challenge

### Backend Setup

**1. Install dependencies**
Navigate to the backend folder and build the project:

    cd backend
    ./gradlew build

**2. Configure environment variables**
Create a `.env` file in the project root or set environment variables:

    AWS_ACCESS_KEY=your-access-key
    AWS_SECRET_KEY=your-secret-key
    AWS_S3_BUCKET=your-bucket-name
    AWS_REGION=eu-north-1
    JWT_SECRET=your-secure-jwt-secret

**3. Run the backend**

    ./gradlew bootRun

The backend runs on: `http://localhost:8080`

### Frontend Setup

**1. Install dependencies**
Navigate to the frontend folder:

    cd frontend
    npm install

**2. Run the dev server**

    npm run dev

The frontend runs on: `http://localhost:5173`

---

## Image Upload Flow

**Contact Images**
- Uploaded via `ContactModal`.
- Stored in S3 under `/contacts/{id}`.
- Displayed automatically in the contact list and details.

**User Avatar**
- Uploaded via `UserImageModal`.
- Stored in S3 under `/users/{userId}`.
- Fetched on login and displayed in the header.

---

## Project Structure

    contact-app-challenge/
    ├── backend/
    │   ├── src/main/kotlin/com/dandobai/backend/
    │   │   ├── auth/         # Authentication logic (Controller, Service)
    │   │   ├── contact/      # Contact CRUD logic
    │   │   ├── image/        # S3 Image upload service
    │   │   └── config/       # Security & App config
    │   └── build.gradle.kts
    │
    └── frontend/
        ├── src/
        │   ├── components/
        │   │   ├── contact/  # Contact-related components
        │   │   ├── user/     # User profile components
        │   │   └── layout/   # Header, Layout wrappers
        │   ├── stores/       # Pinia state stores (Auth, Contacts)
        │   ├── views/        # Main pages (Home, Login, Register)
        │   └── App.vue
        └── package.json

---

## Development Notes

- **File Upload:** Uses `FormData` + `MultipartFile` for seamless transmission.
- **S3 URLs:** Constructed dynamically based on the stored key.
- **State Management:** Pinia stores handle all API interactions to keep components clean.
- **Modals:** Utilizes `v-if` to ensure clean mount/unmount behavior and state reset.
- **Reactivity:** Header avatar updates immediately after upload using reactive state.

---

## License

MIT — free to use, modify, and learn from.

---

## Author

**Dániel Dobai** Backend developer & architect with full-stack experience.