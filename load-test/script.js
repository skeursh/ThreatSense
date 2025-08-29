import http from "k6/http";
import { check, sleep } from "k6";

export const options = {
  vus: 20,        // 20 virtual users
  duration: "30s" // run for 30 seconds
};

export default function () {
  const payload = JSON.stringify({
    srcIP: `10.0.0.${Math.floor(Math.random() * 255)}`,
    destIP: "8.8.8.8",
    port: 3389,
    protocol: "TCP",
    bytes: Math.floor(Math.random() * 5000),
    timestamp: new Date().toISOString()
  });

  const res = http.post("http://host.docker.internal:8080/logs", payload, {
    headers: { "Content-Type": "application/json" }
  });

  check(res, { "status is 200": (r) => r.status === 200 });
  sleep(0.1);
}
