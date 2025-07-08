

-  **Swagger 문서** : [http://43.201.60.238:8080/swagger-ui/index.html](http://43.201.60.238:8080/swagger-ui/index.html)
-  **GitHub Repository** : [https://github.com/k1130k/intern-assignment.git](https://github.com/k1130k/intern-assignment.git)
-  **API 엔드포인트 URL** : `http://43.201.60.238:8080

---

## 프로젝트 개요

- 회원가입/로그인 → JWT 발급 → Access/Refresh 토큰 처리
  
- 역할 기반 인가: User / Admin 구분 및 접근 제한
  
- Swagger(OpenAPI) 문서화
  
- AWS EC2 배포로 실서비스 접근 가능

---

## 주요 기술 스택

- Java 17, Spring Boot, Spring Security, JWT, AWS EC2, Swagger

---

## 트러블 슈팅 & 개선 사항

- ec2 서버 실행 후 수 분이 지나면 서버 응답 없음 현상

문제 원인
  
AWS EC2 인스턴스에서 Spring Boot 애플리케이션을 실행했지만,
수 분 뒤 서버가 자동 종료되어 더 이상 API 응답이 되지 않는 현상 발생.

해결 방법

EC2 인스턴스에 swap 메모리를 설정해 메모리 부족 상황을 보완하였다.

블로그 자세한 내용:
https://gurofirefist.tistory.com/15

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





