FROM gradle:5.3.1 as gradle
COPY --chown=gradle . /home/app
WORKDIR /home/app
RUN gradle assemble --no-daemon

FROM adoptopenjdk/openjdk8:jdk8u202-b08-alpine-slim
COPY --from=gradle /home/app/build/libs/*.jar hello-world.jar
EXPOSE 8080
CMD java -noverify -jar hello-world.jar