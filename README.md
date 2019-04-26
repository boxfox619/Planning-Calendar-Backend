# Planning-Calendar-Backend

### 프로젝트 실행 방법
https://8y7lkefwd2.execute-api.ap-northeast-2.amazonaws.com/kakao-calendar/task?year=2019&month=4
아래 명령어 실행 후 람다 등록
```
mvn clean package shade:shade -DskipTests
```

### Tech Stacks
- kotlin
- Postgresql
- JOOQ
- rxJava
- AWS Lambda
- API Gateway
- AWS RDS
- mockito
- junit
- gson

### Database schema
```
create table task(
 id SERIAL PRIMARY KEY,
 name varchar NOT NULL,
 day date NOT NULL,
 startHour smallint NOT NULL,
 endHour smallint NOT NULL
);
```
