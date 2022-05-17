# mini_webService
"스프링 부트와 AWS로 혼자 구현하는 웹 서비스" 를 따라서 클론코딩

**(기존의 내용을 따라 하되, 몇몇 기술이나, 기능은 바꿔서 내가 원하는대로 해볼예정, 가능하다면 기능도 추가해볼것임)**

## 개발일지

### (1일차)

- 프로젝트 생성 및 깃 연동

- spring initializr를 이용해서 생성함.

### (2일차)

- jpa를 이용해서 게시물 엔티티들 생성함

- spring jpa data를 이용해서 제대로 조회가 되는지 테스트 코드 작성중임

- 테스트 코드를 작성까지는 끝냈으나, h2디비연동에 설정하지 않은 테이블들이 생겨나서 확인중임

- `commit` : [3efcfe7](https://github.com/lsh9672/mini_webService/commit/3efcfe7c67fd2f3618d0fa4a196e64c9b4da62ce)

### (3일차)

- 3장까지 끝냄

- 서비스 계층, 레파지토리 계층을 만들고, api용 컨트롤러를 만듦

- 작성한 각각의 코드에 대해서 테스트 코드를 작성함

- 추가적으로, 책에서는 조회에 대해서는 브라우저를 이용해서만 테스트 했기 때문에, 직접 조회테스트를 작성함.

- 모든 엔티티에 공통적으로 들어갈, 생성시간, 변경시간을 auditing 기능을 이용해서 처리함.

- `commit` : [a1eec0c](https://github.com/lsh9672/mini_webService/commit/a1eec0c595916eb8a4700cc6c3d395921cf74e0d)

### (4일차)

- 4장까지 진행

- 4장에서 조회,수정, 삭제 화면을 만듦

- 책에서는 머스테치를 썼지만, 스프링 부트에서 권장하는 타임리프 사용하는 것이 좋다고 생각해서 전부 타임리프로 바꿈

- 머스테치에서는 레이아웃을 사용해서 중복부분을 한번에 관리하지만, 타임리프로 만들때는 fragment를 사용함

- 현재 수정, 삭제 api는 테스트하지 않았기 때문에 다음에 5장을 진행하면서 이 테스트를 만들예정

- `commit` : [029f3ff](https://github.com/lsh9672/mini_webService/commit/029f3fff440dbae2ae2967299a4ba5710b8a7a03)

### (5일차)

- 4장테스트와 5장 도입부 진행(oauth)

- 책에서 안한, 레파지토리의 전체조회, 수정, 삭제, 컨트롤러의 삭제, 서비스계층 조회,전체조회, 수정, 삭제에 대한 테스트 코드를 작성함.

- google oauth연동을 위해 인증정보를 등록하고 yaml에 등록함.

- 4장 추가 테스트 코드 `commit` : [b871bee](https://github.com/lsh9672/mini_webService/commit/b871beee0b7a6eda08e0499b37b91739121bac5d)

### (6일차)

- 5장 진행

- OAuth기능 구현(구글 로그인)

- 인증 버튼을 화면에 보여줘야 하는데, 머스테치에서 타임리프로 변경중에 있음

- OAuth기능 구현 `commit` : [3768fd4](https://github.com/lsh9672/mini_webService/commit/3768fd4031473ca99c15b94c9818b5703a436f52)

### (7일차)

- 5장 마무리

- OAuth기능 완성(네이버,구글)

- 세션 코드를 어노테이션 기반으로 리펙토링함

- 책에서 머스테치로 처리하던 로그인 여부에 따른 버튼출력을 타임리프로 변경함

- 스프링 시큐리티를 테스트에 적용해서 기존에 작성한 테스트가 깨지는 것을 수정함.

- OAuth관련 설정을 properties파일에서 좀 더 가독성 좋은 yml파일로 수정함.

- 7일차 코드 `commit` : [aa54a21](https://github.com/lsh9672/mini_webService/commit/aa54a218d5d8127fc949fbbf9a948b8e988ee48c)

### (8일차)

- 6장 끝냄

- aws에 EC2(AMI 2)를 만들고 고정ip(EIP) 설정까지 함

- 로컬에서 ssh로 접속하기 위한 설정을 하고, 현재 사용중인 java 11버전을 설치함

- 책에서는 자바8을 설치하는데, 이는 이미 저장소에 있지만 11은 없기 때문에 따로 설치해야 됨

- 책에서는 os를 아마존에서 지원하는 AMI 1을 썼지만, 현재(2022.05.10)는 AMI 1이 없어지고 AMI 2 만 남아있음

- AMI 2로 진행할 경우, 책에서 진행하는 호스트이름 변경시에 `/etc/sysconfig/network 파일만 변경하면 적용이 안되어서 추가적인 방법으로 설정했음

- 자바 11설치 참고 :[java11 install](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html)

- 호스트 이름 변경 참고 : [change hostname](https://bbeomgeun.tistory.com/157)

- AMI 1에 대한 지원 종료관련 : [AMI 2](https://aws.amazon.com/ko/about-aws/whats-new/2019/03/announcing_the_amazon_linux_2_preupgrade_assistant/)

### (9일차)

- 7장 끝

- AWS에 관계형디비인 RDS를 만듦(mariaDB)

- 보안규칙을 설정하고, 추후에 이모지 저장을 위해 utf8이 아닌 utf8mb4로 지정해줌

- 이전에 생성한 EC2와 보안규칙을 연결해 서로 연결이 되도록 하였고 EC2에서 접근확인함.

- 인텔리제이에서도 DB에 접근설정을 하는데, 책에서 쓰는 DB Navigator의 경우, 툴자체의 문제인지 RDS에 접속이 안됨(구글링 해봐도 원인을 모름)

- 인텔리제이 유료버전이 제공하는 database tool window를 이용해서 연결하고, 테이블 생성 테스트까지 진행함.

- 참고한 블로그 : [db tool](https://wadekang.tistory.com/35) 

### (10일차)

- 8장 끝

- aws에 프로젝트를 배포하고 스크립트 작성

- RDS에 테스트시 만들었던 테이블을 sql로 작성해서 생성함

- 운영환경용 yml파일을 별도로 만들어서 배포함

- 실행환경이 aws로 바뀌었기 때문에 oauth에 설정해둔 허용 url들을 이에 맞춰 변경함.

- 스프링 부트 2.5이상부터는 빌드시에 ~plain.jar라는 파일이 하나더 생기는데, 이는 의존성을 가져온것이 아닌, 프로젝트내에 존재하는 자원들로만 jar를 생성하기 때문에, 이것을 실행하면 문제가 됨. 따라서 `build.gradle`에 설정을 추가해서 이 파일이 생기지 안도록 함

- 문제해결 참고 : [jar 관련](https://velog.io/@zerodin/EC2-%EC%84%9C%EB%B2%84%EC%97%90-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EB%A5%BC-%EB%B0%B0%ED%8F%AC%ED%95%B4-%EB%B3%B4%EC%9E%90#%EC%99%B8%EB%B6%80-security-%ED%8C%8C%EC%9D%BC-%EB%93%B1%EB%A1%9D%ED%95%98%EA%B8%B0)

### (11일차)

- 9장 끝

- AWS의 S3,code deploy를 이용해서 배포 구축

- 빌드는 책에서 travis를 사용했지만, github action으로 구축함

- travis는 일정 크래딧을 다쓰기전까지만 무료이고, 초기 구축시에 github action보다 좀 더 까다롭기도 하고, 무엇보다 깃허브와의 연동은 github action이 더 좋으면서 편리하게 제공하기 때문에 이를 선택함.

- 구축후에 자동 빌드, 배포가 되는지 확인함.

- 참고한 블로그 : [github action 적용](https://velog.io/@tigger/%EB%B0%B0%ED%8F%AC-%EC%9E%90%EB%8F%99%ED%99%94-%EA%B5%AC%EC%84%B1)

### (12일차)

- 10장 끝

- nginx의 리버스 프록시 기능을 이용해서 무중단 배포 구성완료

- health check를 위한 컨트롤러를 만들고 테스트 코드 작성

- 작성한 코드 및 스크립트 `commit` : [8296681](https://github.com/lsh9672/mini_webService/commit/8296681819466a48893584eabb30af5882a794ff)
