FROM azul/zulu-openjdk:21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM azul/zulu-openjdk:21
WORKDIR /app
COPY --from=build /app/target/ecommerce-backEnd-0.0.1-SNAPSHOT.jar /app/ecommerce-backEnd-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/app/ecommerce-backEnd-0.0.1-SNAPSHOT.jar"]
