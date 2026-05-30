# syntax=docker/dockerfile:1.7

# ===== Stage 1: Build =====
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /workspace

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw
RUN mvn -B -q dependency:go-offline

COPY src src
RUN mvn -B -q -DskipTests package \
    && mkdir -p target/extracted \
    && java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted

# ===== Stage 2: Runtime =====
FROM eclipse-temurin:17-jre-jammy AS runtime

RUN groupadd --system spring && useradd --system --gid spring --home /app spring
WORKDIR /app

COPY --from=build --chown=spring:spring /workspace/target/extracted/dependencies/         ./
COPY --from=build --chown=spring:spring /workspace/target/extracted/spring-boot-loader/   ./
COPY --from=build --chown=spring:spring /workspace/target/extracted/snapshot-dependencies/ ./
COPY --from=build --chown=spring:spring /workspace/target/extracted/application/          ./

USER spring:spring

EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher"]
