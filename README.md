

-  **Swagger 문서** : [http://43.201.60.238:8080/swagger-ui/index.html](http://43.201.60.238:8080/swagger-ui/index.html)
-  **GitHub Repository** : [https://github.com/k1130k/intern-assignment.git](https://github.com/k1130k/intern-assignment.git)
-  **API 엔드포인트 URL** : `http://43.201.60.238:8080

---

## 프로젝트 설명

회원가입과 로그인 기능을 구현하고, 로그인 시 JWT 토큰을 발급하여 인증 시스템을 구축하였습니다.

JWT 토큰은 Access Token 형태로 클라이언트에 전달되며, 이후 보호된 API 요청 시 `Authorization` 헤더에 포함되어 전송됩니다.

역할 기반 인가 기능을 통해 일반 사용자(User)와 관리자(Admin)를 구분하고, 관리자는 사용자에게 권한을 부여할 수 있습니다.

모든 API는 Swagger(OpenAPI)를 통해 문서화되어 있으며, 실제 동작 환경인 AWS EC2에 배포되어 외부에서도 접근 가능합니다.

---

# API 명세서

**Swagger 문서** 참고 :  [http://43.201.60.238:8080/swagger-ui/index.html](http://43.201.60.238:8080/swagger-ui/index.html)

## 회원가입API

### 회원가입 성공

![image](https://github.com/user-attachments/assets/33295664-5f7f-484e-acc0-fd9ad16b7f7d)

### 회원가입 실패 (이미 가입된 사용자)

![image](https://github.com/user-attachments/assets/ef826ddb-46b3-476b-a5b9-dcb8cf206e22)

## 로그인API

### 로그인성공

![image](https://github.com/user-attachments/assets/85696897-e96f-4bcc-8236-4da923ec9e15)

### 로그인 실패 (잘못된 계정 정보)

![image](https://github.com/user-attachments/assets/c5a07b7f-3646-49a2-8e8b-0e3d42259ed4)

## 관리자 권한 부여 API

### 관리자 권한 부여 성공

![image](https://github.com/user-attachments/assets/319d3a98-5f65-4879-8db7-13fbf0e08093)

### 권한이 부족한 경우 (접근 제한)

![image](https://github.com/user-attachments/assets/5d512aa3-e2f2-4824-9a0d-32fae1377717)

## 토큰

### 토큰이 유효하지 않거나 만료된 경우

![image](https://github.com/user-attachments/assets/e4a271b7-7886-4038-a2a4-6b551e40017c)

### 권한이 부족한 경우

![image](https://github.com/user-attachments/assets/5d512aa3-e2f2-4824-9a0d-32fae1377717)





