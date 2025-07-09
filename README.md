# CodingCollaborator-React-Spring-Boot-


Deployed Link:    https://coding-collaborator-ai-compiler.vercel.app
## Video Demonstration:

https://github.com/user-attachments/assets/110a9a26-f3f9-4a18-9510-8b096f7e03ab

<p align="center">
    <img src="https://raw.githubusercontent.com/PKief/vscode-material-icon-theme/ec559a9f6bfd399b82bb44393651661b08aaf7ba/icons/folder-markdown-open.svg" align="center" width="30%">
</p>
<p align="center"><h1 align="center">Real-Time Coding Collaborator with AI Code Suggestions and OnBoard Compiler</h1></p>
<p align="center">
    <img src="https://img.shields.io/github/license/adityaRaj369/CodeFuse?style=default&logo=opensourceinitiative&logoColor=white&color=0080ff" alt="license">
    <img src="https://img.shields.io/github/last-commit/adityaRaj369/CodeFuse?style=default&logo=git&logoColor=white&color=0080ff" alt="last-commit">
    <img src="https://img.shields.io/github/languages/top/adityaRaj369/CodeFuse?style=default&color=0080ff" alt="repo-top-language">
    <img src="https://img.shields.io/github/languages/count/adityaRaj369/CodeFuse?style=default&color=0080ff" alt="repo-language-count">
</p>
<p align="center">
    <a href="https://coding-collaborator-ai-compiler.vercel.app" target="_blank">
        <img src="https://img.shields.io/badge/Deployed%20Link-Visit%20Now-0080ff?style=default&logo=vercel" alt="Deployed Link">
    </a>
</p>


## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
  - [Directory Structure](#directory-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Usage](#usage)
  - [Deployment](#deployment)
  - [Testing](#testing)
- [Project Roadmap](#project-roadmap)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## Overview

**CodeFuse** is a real-time, interactive coding platform designed for collaborative programming. It enables multiple developers to write, edit, and execute code simultaneously in a shared environment. With features like AI-powered code suggestions, On Editor Code Compilations , Google authentication, and a Dracula-themed code editor, CodeFuse enhances productivity and teamwork for developers of all levels.

## Features

- **Real-Time Collaboration**: Multiple users can edit code simultaneously in a shared room, with changes synced instantly via Socket.IO.
- **AI-Powered Code Suggestions**: Integrated with Google Gemini AI to provide real-time, context-aware code completions for languages like JavaScript, Python, Java, C++, and C.
- **Code Execution**: Run code directly in the editor using Judge0 API, with support for input and output display.
- **Code Saving**: Save code snippets to MongoDB, associated with user emails, with options to retrieve and delete saved codes.
- **Google Authentication**: Secure login using Firebase Google Authentication for seamless user access.
- **Syntax Highlighting**: Dracula-themed CodeMirror editor with support for JavaScript, Python, Java, C++, and C, including auto-close tags/brackets and line numbers.
- **Room Management**: Generate unique room IDs for collaboration sessions and share them easily with a copy-to-clipboard feature.
- **Active Users Display**: Shows all active users in a room with their usernames, emails, and profile photos.
- **Responsive UI**: Modern, vampire-themed UI with modals for saving codes and viewing saved snippets.
- **Cross-Platform Support**: Deployed on Vercel (frontend); Spring Boot application as backend.

## Project Structure

### Directory Structure

```plaintext
CodeFuse/
├── client/             # Frontend application (React.js)
│   ├── public/         # Static files
│   └── src/            # React components and pages
│       ├── components/ # Reusable UI components
│       ├── pages/      # Application pages
│       └── utils/      # Helper functions and utilities
├── codeColab/             # Backend application (Spring Boot)
│   └── src/
│       └── main/
│           ├── java/
│           └── resources/
└── README.md           # Project documentation
```

---

## Features

### Prerequisites

- **Node.js** (v14 or higher)
- **npm** (v6 or higher) or **yarn**
- **MongoDB** (running instance for backend database)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/adityaRaj369/CodingCollaborator-React-Spring-Boot-.git
   ```

2. Install dependencies for frontend:

   ```bash
   cd client
   npm install
   ```

3. Run the Spring Boot backend:

   - Open the Spring Boot app in an IDE or run:
     ```bash
     ./mvnw spring-boot:run
     ```

4. Start the frontend:

   ```bash
   cd client
   npm start
   ```

5. Open your browser and navigate to `http://localhost:3000`.

### Testing

Run tests for the frontend:

```bash
cd client
npm test
```

Run backend tests using your IDE or Maven:

```bash
./mvnw test
```
---

## Project Roadmap

- **Phase 1:** Implement core collaboration features (real-time editing, chat, and syntax highlighting).
- **Phase 2:** Add authentication and authorization.
- **Phase 3:** Integrate with GitHub and add version control features.
- **Phase 4:** Deploy to a cloud platform (e.g., AWS , Azure , render, vercel).

---

## Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and open a pull request.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Acknowledgments

- Inspired by tools like **Visual Studio Live Share** and **CodeSandbox**.
- Thanks to the open-source community for their support and contributions.
