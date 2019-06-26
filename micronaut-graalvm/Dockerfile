FROM gradle:5.3.1 as gradle
COPY --chown=gradle . /home/app
WORKDIR /home/app
RUN gradle assemble

# Stage 2: Build the native image
FROM oracle/graalvm-ce:19.0.0 as graalvm
COPY --from=gradle /home/app/build/libs/*.jar /home/app/
WORKDIR /home/app
RUN gu install native-image
RUN native-image --no-server -cp micronaut-graalvm-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/micronaut-graalvm .
ENTRYPOINT ["./micronaut-graalvm"]