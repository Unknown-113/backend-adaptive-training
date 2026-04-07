# CyberRangeCZ Platform Adaptive Training
This project represents back-end for managing adaptive training in CyberRangeCZ Platform.

# Repository Structure

The repository is structured with packages as follows:

### .api
Objects that are used for communication between the front-end and the back-end.

- model definitions for transferred and received DTOs.
- mappers
- validation

### .definition
Objects used for defining internal working of the application.

- annotations for swagger documentation
- configuration of frameworks and libraries
- exceptions

### .persistence
Objects used for communication with the database.

- entities and their enums
- repositories

### .rest
Objects used for handling HTTP requests.

- controllers
- facades
- transaction and security annotations

### .service
Objects used for business logic.

### .startup
Startup hooks.



* Swagger for local environment is available [here](http://localhost:8080/adaptive-training/api/v1/swagger-ui.html#/)
* H2 in-memory DB can be accessed [here](http://localhost:8080/adaptive-training/api/v1/h2-console)

## Trainee Exam Access (Terminal)

Thí sinh truy cập bài thi từ terminal bằng 3 lệnh curl:

**Bước 1 — Lấy token**
```bash
export TOKEN=$(curl -k -s -X POST https://<server>/keycloak/realms/CRCZP/protocol/openid-connect/token \
  -d "username=<username>" \
  -d "password=<password>" \
  -d "grant_type=password" \
  -d "client_id=CRCZP-Client" | jq -r '.access_token')
```

**Bước 2 — Vào bài thi**
```bash
export RUN_ID=$(curl -k -s -X POST "https://<server>/adaptive-training/api/v1/training-runs?accessToken=<access_token>" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.training_run_id')
```

**Bước 3 — Lấy deep link và mở trên browser**
```bash
curl -k -s -X POST "https://<server>/adaptive-training/api/v1/training-runs/$RUN_ID/deep-link" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.deep_link_url'
```

Mở URL trả về trên browser — tự động đăng nhập và vào thẳng giao diện bài thi.
