FROM openjdk:8-jdk-alpine AS build
WORKDIR /
COPY . ./
RUN ./gradlew --no-daemon --stacktrace clean bootJar

FROM openjdk:8-jre-alpine
WORKDIR /

COPY --from=build /build/libs/*.jar app.jar

CMD java -jar app.jar