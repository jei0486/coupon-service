# 쿠폰 서비스

### 요구사항 (목적)
- 사용자에게 쿠폰을 제공하는 서비스를 개발
- 기능 명세에 대한 개발 및 성능 테스트

### 기능명세
- 쿠폰을 지급하는 API 구현 (O)
- 사용자에게 지급된 쿠폰을 조회하는 API 구현  (O)
- 지급된 쿠폰을 사용하는 API 구현
- 지급된 쿠폰을 사용 취소하는 API 구현
- 발급된 쿠폰중 당일 만료된 전체 쿠폰 목록 조회하는 API 구현
- 발급 된 쿠폰 중 만료 예정인 쿠폰 목록 조회하는 API 구현
- 쿠폰 만료일 1일전 알림 배치

### 프로젝트 정의
- common-coupon : 공통 dto, entity
- api-coupon    : api , kafka publishing , db read
- kafka-coupon  : kafka consume , db write

### 성능테스트 목표
- TPS 10만건
  - 성능개선을 위한 설정 변경 (압축, batch,size)


## 웹 어플리케이션 개발시 고려되는 방법

### polling (client pull)
  - 클라이언트가 일정한 주기로 서버에 업데이트 요청을 보내는 방법.
  - 지속적인 HTTP 요청이 발생하기 때문에 리소스 낭비가 발생한다.

### websocket (server push)
 - 실시간 양방향 통신을 위한 스펙으로 서버와 브라우저가 지속적으로 연결된 TCP 라인을 통해 실시간으로 데이터를 주고 받을 수 있도록 하는 HTML5 사양
 - 연결 지향 양방향 전이중 통신이 가능하며 채팅, 게임, 주식, 차트 등에 사용된다.
 - polling 은 주기적으로 HTTP 요청을 수행하지만, websocket 은 연결을 유지하여 서버와 클라이언트 간 양방향 통신이 가능하다.

### SSE (server push)
- 이벤트가 서버 -> 클라이언트 방향으로만 흐르는 단방향 통신채널이다.
- SSE 는 클라이언트가 polling  과 같이 주기적으로 http 요청을 보낼 필요없이 http 연결을 통해 서버에서 클라이언트로 데이터를 보낼 수 있다.

## FeignClient
- https://docs.spring.io/spring-cloud-openfeign/docs/3.0.0-SNAPSHOT/reference/html/#spring-cloud-feign
- 넷플릭스에서 개발한, RestTemplate 를 대체할수 있는 라이브러리 
- MSA환경에서는 각 서비스 간의 호출이 기존의 단일 시스템일 때보다 증가하게 됩니다. 이러한 상황에서 Feign은 API 간의 호출을 편하게 해 주기 위해서 만들어졌습니다.
- https://techblog.woowahan.com/2630/
