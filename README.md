# Adaptive RPS Game

A small Rock–Paper–Scissors game with a Java Spring Boot API and a browser-based frontend. The backend keeps track of the moves played during the current run and uses the player's most frequent move to choose a counter. During the first three rounds, it chooses a move at random.

## Features

- Play Rock, Paper, or Scissors in a browser.
- Send each move to a Spring Boot REST endpoint.
- See the computer's move and the round result.
- Experiment with a simple frequency-based move predictor.

## Tech stack

- Java 17
- Spring Boot and Spring Web MVC
- HTML, CSS, and vanilla JavaScript
- Maven Wrapper

## Run locally

You need Java 17 or newer. Start the backend first:

```bash
cd adaptive-rps-game/backend
./mvnw spring-boot:run
```

On Windows PowerShell, run:

```powershell
cd adaptive-rps-game/backend
.\mvnw.cmd spring-boot:run
```

Then, in a second terminal, serve the frontend:

```bash
cd adaptive-rps-game/frontend
python -m http.server 8000
```

Open [http://localhost:8000](http://localhost:8000). Keep the backend running at `http://localhost:8080` while playing.

## Run backend tests

From `adaptive-rps-game/backend`:

```bash
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```

The current test suite only checks that the Spring application context loads.

## How the prediction works

The API starts by choosing randomly while fewer than three moves have been recorded. After that, it predicts the player's most frequently used move and plays the move that beats it. The history is kept in memory by the running backend, so it resets when the backend restarts and is shared by clients connected to the same backend.

This is a learning prototype; the predictor is intentionally simple and does not yet learn sequences or maintain separate histories per player.

## Project layout

```
adaptive-rps-game/
├── backend/   # Spring Boot API and tests
└── frontend/  # Static browser interface
```

The game endpoint is `POST /api/game/play`. It accepts JSON such as `{"move":"ROCK"}` and returns the computer's move and the result.
