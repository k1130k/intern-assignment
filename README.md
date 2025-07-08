

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

## 트러블 슈팅 & 개선 사항

---

- ec2 서버 실행 후 수 분이 지나면 서버 응답 없음 현상

  문제 원인
  
AWS EC2 인스턴스에서 Spring Boot 애플리케이션을 실행했지만,
수 분 뒤 서버가 자동 종료되어 더 이상 API 응답이 되지 않는 현상 발생.
→ 서버가 꺼진 게 아니라, 터미널 세션 종료 시 백그라운드 프로세스까지 함께 종료된 상황

해결 방법

EC2 인스턴스에 swap 메모리를 설정해 메모리 부족 상황을 보완하고,
nohup java -jar 또는 screen, tmux 등으로 백그라운드에서 안전하게 서버가 계속 유지되도록 실행

블로그 자세한 내용:
https://gurofirefist.tistory.com/15





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





