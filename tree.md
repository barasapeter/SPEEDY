# Folder structure
```
src/
└─ main/
├─ java/
│   └─ com.barasa.speedy/
│          ├─ config/
│          │     ├─ WebConfig.java
│          │     └─ SecurityConfig.java
│          │
│          ├─ common/
│          │     ├─ exception/
│          │     │     ├─ GlobalExceptionHandler.java
│          │     │     └─ ResourceNotFoundException.java
│          │     ├─ util/
│          │     └─ annotations/
│          │
│          ├─ user/
│          │     ├─ domain/
│          │     │     ├─ User.java
│          │     │     ├─ UserRepository.java
│          │     │     └─ UserService.java
│          │     ├─ infrastructure/
│          │     │     ├─ UserEntity.java (JPA specifics)
│          │     │     └─ UserJpaRepository.java
│          │     ├─ web/
│          │     │     ├─ UserController.java
│          │     │     └─ dto/
│          │     │             ├─ CreateUserRequest.java
│          │     │             └─ UserResponse.java
│          │     └─ application/
│          │           └─ UserUseCases.java
│          │
│          ├─ shop/
│          │     ├─ domain/
│          │     │     ├─ Shop.java
│          │     │     ├─ ShopRepository.java
│          │     │     └─ ShopService.java
│          │     ├─ infrastructure/
│          │     │     ├─ ShopEntity.java
│          │     │     └─ ShopJpaRepository.java
│          │     ├─ web/
│          │     │     ├─ ShopController.java
│          │     │     └─ dto/
│          │     │             ├─ CreateShopRequest.java
│          │     │             └─ ShopResponse.java
│          │     └─ application/
│          │           └─ ShopUseCases.java
│          │
│          ├─ bike/
│          │     ├─ domain/
│          │     │     ├─ Bike.java
│          │     │     ├─ BikeRepository.java
│          │     │     └─ BikeService.java
│          │     ├─ infrastructure/
│          │     │     ├─ BikeEntity.java
│          │     │     └─ BikeJpaRepository.java
│          │     ├─ web/
│          │     │     ├─ BikeController.java
│          │     │     └─ dto/
│          │     │             ├─ RegisterBikeRequest.java
│          │     │             └─ BikeResponse.java
│          │     └─ application/
│          │           └─ BikeUseCases.java
│          │
│          ├─ session/
│          │     ├─ domain/
│          │     │     ├─ Session.java
│          │     │     ├─ SessionReport.java
│          │     │     ├─ SessionRepository.java
│          │     │     └─ SessionService.java
│          │     ├─ infrastructure/
│          │     │     ├─ SessionEntity.java
│          │     │     ├─ SessionReportEntity.java
│          │     │     └─ SessionJpaRepository.java
│          │     ├─ web/
│          │     │     ├─ SessionController.java
│          │     │     └─ dto/
│          │     │             ├─ StartSessionRequest.java
│          │     │             ├─ EndSessionRequest.java
│          │     │             └─ SessionSummaryResponse.java
│          │     └─ application/
│          │           └─ SessionUseCases.java
│          │
│          └─ SpeedyApplication.java
│
└─ resources/
├─ application.yml
├─ static/
└─ templates/
```