# 멀티스테이지 빌드를 사용하여 이미지 크기 최적화
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src

# Gradle 빌드 실행
RUN ./gradlew build -x test

# 실행용 경량 이미지
FROM eclipse-temurin:21-jre

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 애플리케이션 실행
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
