
# Stage 2: Build the native image
FROM oracle/graalvm-ce:19.0.2 as builder
WORKDIR /home/app
COPY pom.xml .
COPY src ./src
RUN yum install -y wget && yum install -y unzip && \
    wget http://ftp.wayne.edu/apache/maven/maven-3/3.6.1/binaries/apache-maven-3.6.1-bin.zip && \
    unzip apache-maven-3.6.1-bin.zip && mv  apache-maven-3.6.1 maven
RUN gu install native-image
RUN /home/app/maven/bin/mvn package -Pnative

FROM registry.access.redhat.com/ubi8/ubi-minimal
WORKDIR /work/
COPY --from=builder /home/app/target/*-runner /work/application
RUN chmod 775 /work
EXPOSE 8080
CMD ["./application", "-Dquarkus.http.host=0.0.0.0", "-Dquarkus.http.port=${PORT}" ]