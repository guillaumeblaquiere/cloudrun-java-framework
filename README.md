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

## Micronaut application (with graalvm)

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

## Servlet + Jetty

For building
```bash
cd servlet
gcloud builds submit
```

To deploy on Cloud Run
```bash
gcloud beta run deploy servlet --image gcr.io/<projectID>/servlet
```

Then simply perform a get on the URL/api to perform a test.
```bash
curl https://servlet-<hash>.run.app/api/
#If deployed privately
curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://servlet-<hash>.run.app/api/
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

`hey -c 1 -n 2500 -q 5 -H "Authorization: Bearer $(gcloud auth print-identity-token)" <url>`


| Runner        | Cold start duration| Memory usage | Container size | Average Response time |
| ------------- |:-------------:|:-----:|:-----:|:-----:|
| SpringBoot| 15s to 18s (10s*) | 128Mb |56Mb (63Mb*)|114ms|
| SpringBoot-webflux| 12s (9s*) | 128Mb |58Mb (64Mb*)|115ms|
| Micronaut| 8s to 12s | 128Mb |131Mb|122ms|
| Micronaut + graalvm | 2s |128Mb |22Mb|114ms|
| Servlet | 1.5s - 1.9s |127Mb |43Mb|115ms|
\* Values with JIB maven plugin.

The container size can be optimized by selecting a slim/distroless image in dockerfile. The size haven't impact on the cold start.

### Example 

You can reduce from 150Mb to 56Mb the size of the Springboot image by selecting `openjdk8:jdk8u202-b08-alpine-slim` instead of `openjdk8` (without tag). 

# License

This library is licensed under Apache 2.0. Full license text is available in
[LICENSE](https://github.com/guillaumeblaquiere/cloudrun-java-framework/tree/master/LICENSE).