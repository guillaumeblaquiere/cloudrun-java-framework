FROM gradle:5.3.1 as gradle
COPY --chown=gradle . /home/app
WORKDIR /home/app
RUN gradle assemble --no-daemon

FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY --from=gradle /home/app/build/libs/*.jar hello-world.jar
EXPOSE 8080
CMD java  -XX:TieredStopAtLevel=1 -XX:MaxRAM=256m -Dcom.sun.management.jmxremote -Dserver.port=${PORT} -noverify -jar hello-world.jar