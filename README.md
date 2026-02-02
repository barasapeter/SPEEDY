# SPEEDY

ssh into the instance
Install Java (JDK 17)
```
sudo apt update
sudo apt install -y openjdk-17-jdk
java -version
```

Install PostgreSQL
```
sudo apt install -y postgresql postgresql-contrib
```

Switch to postgres user
```
sudo -i -u postgres
psql
```

Create database
```
CREATE DATABASE speedy;
```

Update user `postgres` password
```
\password postgres
```

Enter new password and confirm.


Configure PostgreSQL for Local Connections

Edit

sudo nano /etc/postgresql/*/main/pg_hba.conf


Ensure
```
local   all             all                                     md5
```

Restart
```
sudo systemctl restart postgresql
```


Build Spring Boot App
On your local machine
```
./mvnw clean package
```


You’ll get
```
target/myapp-0.0.1.jar
```

Copy JAR to EC2
```
scp -i pem-key.pem target/myapp-0.0.1.jar ubuntu@EC2_PUBLIC_IP:/home/ubuntu/app.jar
```