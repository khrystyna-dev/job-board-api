# 🙋 JOB-BOARD-APP

![job.png](job.png)

### ⚡️ Project description
This is a RESTful web application that allows to parse job data from the free resource https://www.arbeitnow.com/api/job-board-api.

The following functionality is implemented in the project:
- pagination (loading pages with job data) and job sorting
- saving data to the H2 database
- periodic data update using Spring Scheduler and CronJob
- receiving the latest most popular vacancies
- obtaining statistics from locations (city and number of vacancies).
The program is covered by integration tests. Interaction with the program happens through the Postman.

### 🎯 Endpoints
The web app provides the following endpoints:
- <b>GET:</b> `/jobs` - This endpoint will allow to get all job.
- <b>GET:</b> `/jobs/city` - get statistic by city.
- <b>GET:</b> `/jobs/recent` - get recent job.
- <b>GET:</b> `/jobs/{id}` - get job by id.
- <b>GET:</b> `/jobs/top-viewed` - get top viewed job.


### 🔥 Getting Started
To get started with the project follow these steps:
1. Clone the repository: git clone https://github.com/khrystyna-dev/job-board-api.git
2. Install Postman for sending requests.
3. Run the application.
4. To view the data in the database, stop the program and run H2.
5. Use this URL in Postman to test the app: http://localhost:8080/
