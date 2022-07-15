# kakao-insurance-quiz

### 1. 프로그램 실행 및 테스트 방법

------------

#### 소스 코드 가져오기
```
git clone https://github.com/kakao-insurance-quiz/20220707-yhw.git
``` 

#### 명령줄에서 실행하기
```
./gradlew build
cd build/libs
java -jar kakao-insurance-quiz-0.0.1-SNAPSHOT.jar
``` 

#### IntelliJ에서 실행하기
> IntelliJ IDEA 에서 해당 프로젝트를 가져오려면 아래 방법을 통해 가져올 수 있습니다.
> > File -> New -> Project from Existing Sources -> Clone 디렉토리로 이동 -> build.gradle 선택

> 해당 방법을 통해 프로젝트를 가져왔다면, KakaoInsuranceQuizApplication.java로 프로젝트를 실행할 수 있습니다.

#### 테스트 지원
> 해당 프로젝트는 Swagger를 통한 테스트를 지원합니다.
> > Swagger : http://localhost:9010/swagger-ui/index.html


### 2. API 명세

------------

[API 명세](API.md) 파일을 참조하세요.