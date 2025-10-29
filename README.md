# SoftwareDesignProj

## Running the Project

### Prerequisites

- **Java 21**
- **Maven** (or use the included Maven wrapper `mvnw`)
- **Node.js** (version 18 or higher recommended)
- **pnpm** package manager

### Quick Start (Recommended)

Install dependencies in the root directory (first time only):

```bash
pnpm install
```

Then run both backend and frontend with one command:

```bash
pnpm dev
```

This will start:

- Backend (Spring Boot) on `http://localhost:8080`
- Frontend (React + Vite) on `http://localhost:5173`

Press `CTRL+C` to stop both services.

### Running Services Individually

If you want to run the services separately:

**Backend:**

```bash
cd backend
./mvnw spring-boot:run
```

**Frontend:**

```bash
cd frontend
pnpm install  # first time only
pnpm dev
```

## Version Control Guidelines

This project follows structured GitHub-based version control practices to ensure clean, collaborative, and efficient development.
Below are the guidelines for working within this repository.

### Branching Strategy

- **Main branch**: The `main` branch contains the official version of the project that is intended for TA review and submissions (e.g., proto, midterm, final).
  - This branch is only merged to when a milestone has been reached.
  - Each submission is marked with a name corresponding its version (e.g., proto, midterm, final).
- **Development branch**: The `dev` branch serves as the active development branch. It integrates completed feature branches and acts as the staging area before merging into `main`.
- **Feature branches**: Each new feature, bug fix, or update should be developed in its own descriptive branch:
  - `feat/your-feature-name`: for new features
  - `fix/your-bug-description`: for bug fixes
  - `updt/your-update-description`: for updates or improvements
  - `test/your-test-description`: for testing-related work

### Workflow

1. **Start from the `dev` branch**

```bash
git checkout dev
git pull
git checkout -b type/your-branch-name
```

2. Develop your feature and commit regularly with descriptive messages

```bash
git commit -m "brief description of change"
```

3. Push your branch and open a pull request to merge it into `dev`
4. Ensure checks pass and request review
5. After receiving approval from another developer, merge into `dev`

### Best practices

- Always pull the latest changes from `dev` before starting a new feature branch
- Delete feature branches after merging
- Tag official submission commits for easy identification by the TA
