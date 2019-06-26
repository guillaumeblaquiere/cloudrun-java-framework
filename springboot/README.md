# Overview

Simple hello world with different framework to test the cold start and the memory footprint.
Simple hello world is not a generic point of view but a base for comparison

# Disclaimer

I'm not expert in all framework and how to tweak to improve performance.
I simply use the out-of-the-box code and runners.

## Spring Boot application

### Packaging, deployment and tests

For building
```bash
cd springboot
gcloud builds submit
```

To deploy on Cloud Run
```bash
gcloud beta run deploy springboot --image gcr.io/<projectID>/springboot
```

Then simply perform a get on the URL/api to perform a test.
```bash
curl https://springboot-<hash>.run.app/api/
#If deployed privately
curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://springboot-<hash>.run.app/api/
```

## Micronaut application (without graalvm)

For building
```bash
cd micronaut
gcloud builds submit
```

To deploy on Cloud Run
```bash
gcloud beta run deploy micronaut --image gcr.io/<projectID>/micronaut
```

Then simply perform a get on the URL/api to perform a test.
```bash
curl https://micronaut-<hash>.run.app/api/
#If deployed privately
curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://micronaut-<hash>.run.app/api/
```

## Micronaut application (without graalvm)

For building
```bash
cd micronaut-graalvm
gcloud builds submit --timeout 30m
```

To deploy on Cloud Run
```bash
gcloud beta run deploy micronaut-graalvm --image gcr.io/<projectID>/micronaut-graalvm
```

Then simply perform a get on the URL/api to perform a test.
```bash
curl https://micronaut-graalvm-<hash>.run.app/api/
#If deployed privately
curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://micronaut-graalvm-<hash>.run.app/api/
```

# Observed result

| Runner        | Cold start duration| Memory usage | Container size |
| ------------- |:-------------:|:-----:|:-----:|
| SpringBoot| 15s to 18s | 13.1Mb |56Mb|
| Micronaut| 8s to 12s | 46.5Mb |131Mb|
| Micronaut + graalvm | 2s |7.5Mb |22Mb|

The container size can be optimzed by selecting a slim/distroless image in dockerfile 

For example, you can reduce from 150Mb to 56Mb the size of the Springboot image by selecting `openjdk8:jdk8u202-b08-alpine-slim` instead of `openjdk8` (without tag)
