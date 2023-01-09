### 카프카 용어
1. 브로커(Broker) :
   아파치 카프카 애플리케이션이 설치되어 있는 서버 또는 노드

2. 토픽 (Topic) : 프로듀서(Producer)와 컨슈머(Consumer)들이 카프카로 보낸 자신들의 메시지를 구분하기 위한 고유의 이름입니다.
3. 프로듀서 (Producer) : 메시지를 생산하여 브로커의 토픽 이름으로 보내는 서버 또는 애플리케이션
    1. 메시지를 생산 (Produce)해서 Kafka 의 Topic 으로 메시지를 보내는 애플리케이션

4. 컨슈머 (Consumer) : 브로커의 토픽 이름으로 저장된 메시지를 가져가는 서버 또는 애플리케이션
    1. Topic 의 메시지를 가져와서 소비 (Consume) 하는 애플리케이션
    2. Consumer Group : Topic 의 메시지를 사용하기 위해 협력하는 Consumer 들의 집합
        - 하나의 Consumer 는 하나의 Consumer Group 에 포함되며,
          Consumer Group 내의 Consumer 들은 협력하여 Topic 의 메시지를 분산 병렬 처리함

5. 파티션 (Partition) : 병렬처리가 가능하도록 토픽을 나눌 수 있고, 많은 양의 메시지 처리를 위해 파티션의 수를 늘려줄 수 있습니다.

6. 주키퍼 (ZooKeeper) : 분산 애플리케이션을 위한 코디네이션 시스템입니다.
   분산된 애플리케이션의 정보를 중앙에 집중하고 구성 관리, 그룹, 네이밍, 동기화 등의 서비스를 수행합니다.

### Redis Transaction Command

### 3) SET 자료형 + Redis Transaction 을 통한 지급 재고 관리
# KEY 정보
쿠폰정책ID : 쿠폰 정책 PK ID
일자별ID : 일자별 이벤트 시간 PK ID
# VALUE 정보
사용자ID : 사용자 PK ID
key : coupon:time-attack:{쿠폰정책ID}:date-time:{일자별ID}:issued:users
value : 사용자 PK ID
# 트랜잭션 시작
redis> MULTI
# 실시간 지급 수량 확인
redis> SCARD coupon:time-attack:1:date-time:1:issued:users
(integer)1
# 쿠폰 지급 처리 / 사용자ID : 1
redis> SADD coupon:time-attack:1:date-time:1:issued:users 1
(integer)1 // 해당 value가 존재하면 0 아니면 1 리턴
# 트랜잭션 실행
redis> EXEC


### Kafka Topic 정보
# Topic 명 : TimeAttackCouponIssue
# Body 정보 :
{
policyId: 1,
userId: 1,
// issuedAt: "2022–02–26T11:31:42"
}

### 순서 보장

카프카 Topic 은 순서를 보장하지 않지만, Partition 내에서는 순서가 보장된다.
Topic 의 Partition 을 1개, 컨슈머를 1개로 설정하면 순서는 보장 될 것이다.
이렇게 쓸거면 Kafka 를 쓸 이유가 없을 것 같다. > RabbitMQ , SQS

# ./bin/kafka-consumer-groups.sh --describe --topic TimeAttackCouponIssue --bootstrap-server 172.30.10.31:9092
# ./bin/kafka-console-consumer.sh --topic TimeAttackCouponIssue --from-beginning --bootstrap-server 172.30.10.31:9092

./bin/kafka-console-consumer.sh --topic TimeAttackCouponIssue --from-beginning --bootstrap-server 127.0.0.1:9092

coupon 도메인
- admin 영역과 고객 영역 - 오버엔지니어링이 아닌지?
- 쿠폰 정책 crud
- 쿠폰 발급 요청 / 재고 처리 / 쿠폰 발급
- 사용자 쿠폰 조회
- 사용자 쿠폰 사용 / 취소
- 쿠폰 만료 처리 (Batch)