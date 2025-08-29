from locust import HttpUser, task, between

class LogUser(HttpUser):
    wait_time = between(1, 3)  # seconds between requests

    @task
    def get_logs(self):
        self.client.get("/logs")
