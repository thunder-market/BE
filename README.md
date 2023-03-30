## 항해99 13기 클론 코딩 프로젝트 5조 Backend github

## ⚡ 프로젝트 주제
번개장터

* 실제로 서비스하고 있는 번개장터를 클론코딩하는 프로젝트

📅 프로젝트 기간 : 2023.03.24 ~ 2023.03.30

## Back End Team
|김건율|장진혁|박문주|
|:---:|:---:|:---:|
|@ChoonB|@jangjh45|@parkmj4312|
|BE|BE|BE|

## 🛠 개발 환경 | 개발 도구 
<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/>  <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=spring&logoColor=white"/>  <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat-square&logo=SpringSecurity&logoColor=white"/>  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>  <img src="https://img.shields.io/badge/IntelliJIDEA-000000?style=flat-square&logo=IntelliJIDEA&logoColor=white"/>  <img src="https://img.shields.io/badge/github-181717?style=flat-square&logo=github&logoColor=white"/>  <img src="https://img.shields.io/badge/git-F05032?style=flat-square&logo=git&logoColor=white"/>  <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=Postman&logoColor=white"/> 

<img src="https://img.shields.io/badge/AmazonEC2-FF9900?style=flat-square&logo=AmazonEC2&logoColor=white"/>  <img src="https://img.shields.io/badge/AmazonS3-569A31?style=flat-square&logo=AmazonS3&logoColor=white"/>  <img src="https://img.shields.io/badge/AmazonRDS-527FFF?style=flat-square&logo=AmazonRDS&logoColor=white"/>  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>  <img src="https://img.shields.io/badge/Ubuntu-E95420?style=flat-square&logo=Ubuntu&logoColor=white"/>

## [📋 팀 노션, API 명세서](https://1nxeo.notion.site/1nxeo/5-f98df340feb84a709bb63b666bd85a26)

## 📲 프로젝트 기능
1. 메인 화면 (중고 상품 조회) 
  
   * 모든 사용자는 판매중인 전체 상품을 조회할 수 있습니다.
   
   * 판매 완료된 상품은 조회 대상에서 제외됩니다.
 
   * 회원가입과 로그인을 헤더에 설정하였습니다.
   
   * 상품 등록 후 지난 시간을 알 수 있습니다.
   
   * 번개페이 사용가능 여부를 알 수 있습니다.

2. 회원가입 로그인 

   * JWTWebToken + spring boot Security 를 적용하여 구현하였습니다.
   
   * 카카오 소셜로그인을 구현하였습니다.
   
   * 이메일 중복, 닉네임 중복 체크를 구현하였습니다.

3. 판매하기 (상품등록)

   * 상품에 관련된 이미지 1장 업로드 가능합니다. 이미지는 AWS S3로 저장하여 관리됩니다.
    
   * 제목, 상품설명, 카테고리, 판매 상태, 교환 가능 여부, 수량 선택 가능합니다.

4. 상품 상세 조회

   * 상품에 대한 정보를 조회 가능합니다.
    
   * 상품을 등록한 작성자만 상품 수정, 삭제가능합니다.
    
   * 로그인한 사용자만 찜 기능을 이용할 수 있습니다.
    
   * 바로구매를 누르면 판매완료가 됩니다. 
    
   * 조회한 상품의 카테고리와 동일한 상품 6개를 연관상품으로 조회합니다.
## 📋 ERD
![스크린샷 2023-03-30 오후 8 43 23](https://user-images.githubusercontent.com/99319021/228825544-6942a811-4577-4b35-b0d8-49a28da6d686.png)


## ⚽ 트러블 슈팅

 1. 카카오 소셜 로그인
   * 백엔드에서 API를 만들고 프론트에서 카카오 로그인 버튼을 만들어 링크에 직접연결했으나 연결 실패
   
   * Redirect URI를 프론트 페이지에 직접 링크시켜 연결해서 회원가입까진 성공했으나 JWT 토큰 생성 실패
   
   * 좀 더 알아본 결과 Redirect URI는 프론트에서 구현해서 카카오로 부터 코드를 받고 다시 백엔드 API로 코드를 보내
   로그인 처리를 하고 토큰을 보내는 것이 좋다고 판단하여 이 방식으로 구현 성공.
   
   2. 이미지 업로드 구현
   * 이미지 업로드 기능에대해 알아보니 DB에 저장하는 방식, 서버에 저장하는 방식, 외부 저장소를 사용하는 방식 등으로 나뉘어져 있음
   
   * 먼저 서버에 저장하는 방식으로 시도해보고 기능 작동에는 성공하였지만, 매번 서버에서 그림을 불러오는 방식이 부하가 있어 보여 외부저장소를 사용하는 방식으로 변경
   
   * AWS S3에 이미지를 업로드해 저장하고 이미지를 불러올 때는 S3 URI에 저장된 파일 명만 붙여 불러오는 방식을 사용.
   
   * 처음엔 이미지 업로드 API와 상품 작성 API를 따로 구현하였으나 이미지만 업로드하고 상품은 작성하지 않을 경우 남은 이미지 처리가 곤란해 API 병합
   
   * 그 과정에서 이미지는 MultipartFile이라 @RequestParam으로 들어오고 상품 정보는 JSON이라 @RequestBody로 들어오는데 같이 받지 못해 실패.
   
   * @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
   @RequestParam("image") MultipartFile image, @RequestPart("dto") ProductRequestDto 로 Request 받기 성공
   
   3. AWS 에러
   * 사용하던 AWS Access 키가 노출되어 IAM 사용자 권한이 제대로 작동하지 않아 S3 변경.
   
   * CI/CD 도중 Script 문제로 서버에 2개의 빌드 파일이 돌아가 EC2가 다운되어 서버 인스턴스 변경
