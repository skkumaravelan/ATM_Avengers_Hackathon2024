# ATM_Avengers_Hackathon2024
## Author: SK Kumaravelan ##
This repo has been created for hackathon 2024 and all related working prototype has been pushed.

## Prerequisites for running the bat and jar files:

1. Java 11+
2. Apache Maven 3.8.6
3. Docker installed ( Go to the source code where docker compose file is located & execute docker compose up -d)

## Order for running the bat and jar files:
Build jar files and then you can use the following bat files (update your jar names accordingly & start the bat files)

1. `Start_Docker_Compose.bat`
2. `8001_run_eurekha_service_registry.bat`
3. `8002_run_config_server.bat`
4. `8003_run_microservice-1.bat`
5. `8004_run_gateway.bat`

## Prerequisites for running the UI React code:
Once all APIs are running, start the UI code.
1. npm - 9.8.1
2. node v18.17.1
