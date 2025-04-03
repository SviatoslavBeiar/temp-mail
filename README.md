# Temp-Mail Service

A simple temporary email service built with Spring Boot that allows you to generate disposable email addresses, receive incoming emails via SMTP, and check your inbox through REST endpoints.

## Technologies

- **Spring Boot** – REST API, scheduling tasks, dependency injection
- **SubEthaSMTP** – Built-in SMTP server
- **Java 11+** – Core programming language
- **Maven** – Build tool
- **Ngrok / Inlets (optional)** – Publicly expose local services
- **Telnet/Swaks** – SMTP testing

## Features

- **Temporary Email Generation:** Disposable addresses with expiration
- **SMTP Support:** Receive and handle incoming emails
- **Inbox via REST:** Check emails using REST API
- **Auto-cleanup:** Scheduled cleanup of expired emails

## Setup

### Requirements

- Java 11+
- Maven
- Ngrok or Inlets (optional, for public access)

### Running the Application Locally


3. REST API endpoints:

- Generate email:
  ```
  GET http://localhost:8080/temp-email/create
  ```

- Check inbox:
  ```
  GET http://localhost:8080/temp-email/{email}/inbox
  ```

## Local SMTP Testing

### Using Telnet

Connect and send email:

```bash
telnet localhost 2525
HELO localhost
MAIL FROM:<sender@example.com>
RCPT TO:<generated-email@yourdomain.com>
DATA
Subject: Test Email

This is a test email.
.
QUIT
```

### Using Swaks

```bash
swaks --to generated-email@yourdomain.com --from sender@example.com --server localhost:2525 --header "Subject: Test" --body "Hello!"
```

Check inbox via REST endpoint.

## Testing with Real Websites

Your temp-mail service must be publicly accessible:

### Option 1: Deploy to Cloud (Recommended)

- Deploy your app (Heroku, AWS, DigitalOcean).
- Configure domain and MX records to point to your server.

### Option 2: Using Ngrok

- Expose HTTP:
  ```bash
  ngrok http 8080
  ```

- Expose SMTP:
  ```bash
  ngrok tcp 2525
  ```

Use generated ngrok URLs for external tests.

### Option 3: Using Inlets (VPS required)

Server-side setup:

```bash
curl -sLS https://get.inlets.dev | sh
sudo mv inlets /usr/local/bin

inlets server --port=2525 --token="secret"
```

Local setup:

```bash
curl -sLS https://get.inlets.dev | sh
sudo mv inlets /usr/local/bin

inlets client --remote=<SERVER_IP>:2525 --upstream=localhost:2525 --token="secret"
```

Use the server IP for SMTP and HTTP access.





