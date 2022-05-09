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
