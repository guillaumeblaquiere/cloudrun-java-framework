# Overview

Simple hello world with different framework to test the cold start and the memory footprint.
Simple hello world is not a generic point of view but a base for comparison

# Disclaimer

I'm not expert in all framework and how to tweak to improve performance.
I simply use the out-of-the-box code and runners.

## Spring Boot application

For building
```bash
cd springboot
gcloud builds submit

#Use JIB, replace the PROJECT_ID by your project id
mvn compile jib:build
```

To deploy on Cloud Run
```bash
gcloud beta run deploy springboot --image gcr.io/${PROJECT_ID}/springboot
```

Then simply perform a get on the URL/api to perform a test.
```bash
curl https://springboot-<hash>.run.app/api/
#If deployed privately
curl -H "Authorization: Bearer $(gcloud config config-helper --format='value(credential.id_token)')" https://springboot-<hash>.run.app/api/
```

## Spring Boot optimized application

For building
```bash
cd springboot-webflux
gcloud builds submit

#Use JIB, replace the PROJECT_ID by your project id
mvn compile jib:build
```

To deploy on Cloud Run
```bash
gcloud beta run deploy springboot --image gcr.io/${PROJECT_ID}/springboot-webflux --platform managed
```

Then simply perform a get on the URL/api to perform a test.
```bash
curl https://springboot-<hash>.run.app/api/
#If deployed privately
curl -H "Authorization: Bearer $(gcloud config config-helper --format='value(credential.id_token)')" https://springboot-<hash>.run.app/api/
```

## Micronaut application (without graalvm)

For building
```bash
cd micronaut
gcloud builds submit

#Use JIB, replace the PROJECT_ID by your project id
./gradlew jib
```

To deploy on Cloud Run
```bash
gcloud beta run deploy micronaut --image gcr.io/${PROJECT_ID}/micronaut --platform managed
```

Then simply perform a get on the URL/api to perform a test.
```bash
curl https://micronaut-<hash>.run.app/api/
#If deployed privately
curl -H "Authorization: Bearer $(gcloud config config-helper --format='value(credential.id_token)')" https://micronaut-<hash>.run.app/api/
```

## Micronaut application (with graalvm)

For building
```bash
cd micronaut-graalvm
gcloud builds submit --timeout 30m
```

To deploy on Cloud Run
```bash
gcloud beta run deploy micronaut-graalvm --image gcr.io/${PROJECT_ID}/micronaut-graalvm --platform managed
```

Then simply perform a get on the URL/api to perform a test.
```bash
curl https://micronaut-graalvm-<hash>.run.app/api/
#If deployed privately
curl -H "Authorization: Bearer $(gcloud config config-helper --format='value(credential.id_token)')" https://micronaut-graalvm-<hash>.run.app/api/
```

## Servlet + Jetty

For building
```bash
cd servlet
gcloud builds submit

#Use JIB, replace the PROJECT_ID by your project id
./gradlew jib
```

To deploy on Cloud Run
```bash
gcloud beta run deploy servlet --image gcr.io/${PROJECT_ID}/servlet --platform managed
```

Then simply perform a get on the URL/api to perform a test.
```bash
curl https://servlet-<hash>.run.app/api/
#If deployed privately
curl -H "Authorization: Bearer $(gcloud config config-helper --format='value(credential.id_token)')" https://servlet-<hash>.run.app/api/
```

# Observed result

The result are observed on GCP console. 
- Cloud Run Metrics for memory usage
- Cloud Run Log for cold start
- Container registry for container size
- Hey benchmark for average response time **from the cloud shell**

Benchmark is performed with [hey](https://github.com/rakyll/hey) with 
- 1 concurrency request
- 2500 requests from **cloudshell** 
- secure Cloud Run
- 5 requests per second

The aim is to limit the test to 1 container and to have a nice memory usage graph. 
If you perform only 1 request, the graph take the memory value when it want and it's not relevant

`hey -c 1 -n 2500 -q 5 -H "Authorization: Bearer $(gcloud config config-helper --format='value(credential.id_token)')" <url>`


| Runner        | Cold start duration| Memory usage | Container size | Average Response time |
| ------------- |:-------------:|:-----:|:-----:|:-----:|
| SpringBoot| 17s (10s*) | 128Mb |56Mb (63Mb*)|114ms|
| SpringBoot-webflux| 9s (5s*) | 128Mb |58Mb (64Mb*)|115ms|
| Micronaut| 10s (7s*) | 128Mb |131Mb (60Mb*)|122ms|
| Micronaut + graalvm | 2s |128Mb |22Mb|114ms|
| Servlet | 1.6s (1.8s*) |127Mb |43Mb (50Mb*)|115ms|
\* Values with [JIB](https://github.com/GoogleContainerTools/jib) plugin.

The container size can be optimized by selecting a slim/distroless image in dockerfile. The size haven't impact on the cold start.

### Example 

You can reduce from 150Mb to 56Mb the size of the Springboot image by selecting `openjdk8:jdk8u202-b08-alpine-slim` instead of `openjdk8` (without tag). 

# License

This library is licensed under Apache 2.0. Full license text is available in
[LICENSE](https://github.com/guillaumeblaquiere/cloudrun-java-framework/tree/master/LICENSE).