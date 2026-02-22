# SPEEDY

A Spring Boot application for local Bike Hire Businesses Around Campus For fast and efficient Service delivery.

**Live Demo:** https://comp493-barasa-peter.duckdns.org/

---

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
  - [Java Installation](#java-installation)
  - [PostgreSQL Setup](#postgresql-setup)
- [Database Configuration](#database-configuration)
- [Application Deployment](#application-deployment)
  - [Building the Application](#building-the-application)
  - [Deploying to Server](#deploying-to-server)
- [Production Configuration](#production-configuration)
- [Nginx Reverse Proxy Setup](#nginx-reverse-proxy-setup)
- [SSL/HTTPS Configuration](#sslhttps-configuration)
- [Service Management](#service-management)

---

## Prerequisites

- Ubuntu/Debian-based server
- SSH access to your server
- Domain name (for production deployment)
- Maven (for building the application)

---

## Installation

### Java Installation

SSH into your instance VPS and install Java JDK 25:

```bash
sudo apt update
sudo apt install -y openjdk-25-jdk
java -version
```

### PostgreSQL Setup

Install PostgreSQL database server:

```bash
sudo apt install -y postgresql postgresql-contrib
```

---

## Database Configuration

### Create Database

Switch to the PostgreSQL user and access the database:

```bash
sudo -i -u postgres
psql
```

Create the application database:

```sql
CREATE DATABASE speedy;
```

### Set PostgreSQL Password

Update the `postgres` user password:

```sql
\password postgres
```

Enter and confirm your new password when prompted.

### Configure Local Connections

Edit the PostgreSQL configuration file:

```bash
sudo nano /etc/postgresql/*/main/pg_hba.conf
```

Ensure the following line is present for local connections:

```
local   all             all                                     md5
```

Restart PostgreSQL to apply changes:

```bash
sudo systemctl restart postgresql
```

---

## Application Deployment

### Building the Application

On your **local machine**, build the Spring Boot application:

```bash
./mvnw clean package
```

This generates the JAR file at:

```
target/myapp-0.0.1.jar
```

### Deploying to Server

#### Option 1: Copy Pre-built JAR

Copy the JAR file to your EC2 instance:

```bash
scp -i pem-key.pem target/myapp-0.0.1.jar ubuntu@EC2_PUBLIC_IP:/home/ubuntu/app.jar
```

#### Option 2: Build on Server

Clone the repository on your server:

```bash
git clone https://github.com/barasapeter/SPEEDY.git
```

Install required dependencies:

```bash
sudo apt install -y openjdk-17-jdk maven nginx
```

Verify installations:

```bash
java -version
nginx -v
```

### Move JAR to Production Directory

**Important:** Never run production applications from a git directory.

```bash
sudo mkdir -p /opt/speedy
sudo mv ~/SPEEDY/speedy-0.0.1-SNAPSHOT.jar /opt/speedy/speedy.jar
sudo chown -R ubuntu:ubuntu /opt/speedy
```

---

## Production Configuration

### Application Properties

**DO NOT hardcode credentials in your source code.**

Create a production configuration file:

```bash
nano application-prod.properties
```

Add the following configuration:

```properties
spring.application.name=speedy

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/db-name
spring.datasource.username=username
spring.datasource.password=your-password

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Session Management
spring.session.jdbc.initialize-schema=always

# Security
spring.security.user.name=name-here
spring.security.user.password=secret-here

# Server Configuration
server.address=0.0.0.0
server.port=8080

# M-Pesa Integration (Environment Variables)
mpesa.consumer-key=${MPESA_CONSUMER_KEY}
mpesa.consumer-secret=${MPESA_CONSUMER_SECRET}
mpesa.shortcode=${MPESA_SHORTCODE}
mpesa.online-passkey=${MPESA_ONLINE_PASSKEY}
mpesa.base-url=${MPESA_BASE_URL}
mpesa.callback-url=${MPESA_CALLBACK_URL}
```

### Create Systemd Service

Create a service file for automatic startup:

```bash
sudo nano /etc/systemd/system/speedy.service
```

Paste the following configuration:

```ini
[Unit]
Description=Speedy Spring Boot Application
After=network.target

[Service]
User=ubuntu
WorkingDirectory=/opt/speedy
ExecStart=/usr/bin/java -jar /opt/speedy/speedy.jar
Restart=always
RestartSec=5
SuccessExitStatus=143

Environment=SPRING_PROFILES_ACTIVE=prod

[Install]
WantedBy=multi-user.target
```

Enable and start the service:

```bash
sudo systemctl daemon-reload
sudo systemctl enable speedy
sudo systemctl start speedy
```

Check service status:

```bash
systemctl status speedy
```

---

## Nginx Reverse Proxy Setup

### Bind Spring Boot to Localhost (Security Best Practice)

Edit your Spring configuration:

```bash
nano ~/SPEEDY/src/main/resources/application.properties
```

Add or update:

```properties
server.address=127.0.0.1
server.port=8080
```

Rebuild and redeploy:

```bash
./mvnw clean package -DskipTests
sudo mv target/speedy-0.0.1-SNAPSHOT.jar /opt/speedy/speedy.jar
sudo systemctl restart speedy
```

### Configure Nginx

Create Nginx configuration:

```bash
sudo nano /etc/nginx/sites-available/speedy
```

Add the following (replace `comp493-barasa-peter.duckdns.org` with your actual domain):

```nginx
server {
    listen 80;
    server_name comp493-barasa-peter.duckdns.org www.comp493-barasa-peter.duckdns.org;

    location / {
        proxy_pass http://127.0.0.1:8080;
        proxy_http_version 1.1;

        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

Enable the site:

```bash
sudo ln -s /etc/nginx/sites-available/speedy /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl reload nginx
```

Your application should now be accessible at:

```
http://comp493-barasa-peter.duckdns.org
```

---

## SSL/HTTPS Configuration

### Install Certbot

Install Certbot for Let's Encrypt SSL certificates (FREE):

```bash
sudo apt install -y certbot python3-certbot-nginx
```

### Obtain SSL Certificate

Run Certbot to automatically configure SSL:

```bash
sudo certbot --nginx -d comp493-barasa-peter.duckdns.org -d www.comp493-barasa-peter.duckdns.org
```

Follow the prompts to complete the SSL setup.

### Test Auto-Renewal

Verify that certificate auto-renewal works:

```bash
sudo certbot renew --dry-run
```

Your application is now accessible via HTTPS:

```
https://comp493-barasa-peter.duckdns.org
```

---

## Service Management

### Useful Commands

Check service status:

```bash
systemctl status speedy
```

View logs:

```bash
sudo journalctl -u speedy -f
```

Restart service:

```bash
sudo systemctl restart speedy
```

Stop service:

```bash
sudo systemctl stop speedy
```

Start service:

```bash
sudo systemctl start speedy
```

---

## Security Notes

- Never commit sensitive credentials to version control
- Use environment variables for API keys and secrets
- Keep your system and dependencies updated
- Regularly monitor application logs
- Use strong passwords for database and admin accounts

---

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---

## License

This project is licensed under the MIT License.

---

## Support

For issues and questions, please open an issue on the [GitHub repository](https://github.com/barasapeter/SPEEDY).
