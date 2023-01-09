### 카프카 용어
1. 브로커(Broker) : 아파치 카프카 애플리케이션이 설치되어 있는 서버 또는 노드
2. 토픽 (Topic) : 프로듀서(Producer)와 컨슈머(Consumer)들이 카프카로 보낸 자신들의 메시지를 구분하기 위한 고유의 이름입니다.
3. 프로듀서 (Producer) : 메시지를 생산하여 브로커의 토픽 이름으로 보내는 서버 또는 애플리케이션
    1. 메시지를 생산 (Produce)해서 Kafka 의 Topic 으로 메시지를 보내는 애플리케이션
4. 컨슈머 (Consumer) : 브로커의 토픽 이름으로 저장된 메시지를 가져가는 서버 또는 애플리케이션
    1. Topic 의 메시지를 가져와서 소비 (Consume) 하는 애플리케이션
    2. Consumer Group : Topic 의 메시지를 사용하기 위해 협력하는 Consumer 들의 집합
        - 하나의 Consumer 는 하나의 Consumer Group 에 포함되며,
          Consumer Group 내의 Consumer 들은 협력하여 Topic 의 메시지를 분산 병렬 처리함

5. 파티션 (Partition) : 병렬처리가 가능하도록 토픽을 나눌 수 있고, 많은 양의 메시지 처리를 위해 파티션의 수를 늘려줄 수 있습니다.
6. 주키퍼 (ZooKeeper) : 분산 애플리케이션을 위한 코디네이션 시스템입니다. 분산된 애플리케이션의 정보를 중앙에 집중하고 구성 관리, 그룹, 네이밍, 동기화 등의 서비스를 수행합니다.

### Redis Transaction Command

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
redis> SADDcoupon:time-attack:1:date-time:1:issued:users 1
(integer)1 // 해당 value가 존재하면 0 아니면 1 리턴
# 트랜잭션 실행
redis> EXEC

### Kafka Config
partitions
replicas

### Ch01-02.Topic-Partition-Segment
- Producer 와 Consumer 는 서로 알지 못하며, Producer 와 Consumer 는 각각 고유의 속도로 Commit Log 에 Write 및 Read 를 수행
- 다른 Consumer Group 에 속한 Consumer 들은 서로 관련이 없으며, Commit Log 에 있는 Event(Message) 를 동시에 다른 위치에서 Read 할수 있음
    - Commit Log : 추가만 가능하고 변경 불가능한 데이터 스트럭처 데이터 (event) 는 항상 로그 끝에 추가되고 변경되지않음

    - Kafka Offset Commit Log 에서 Event 위치
      - 
        - Producer 가 Write 하는 LOG-END-OFFSET 과 Consumer Group 의 Consumer 가 Read 하고 처리한 후에
          Commit 한 CURRENT-OFFSET 과의 차이 (Consumer Lag) 가 발생할 수 있음
- Topic, Partition,Segment, Logical View
  - 
    - Topic : Kafka 안에서 메시지가 저장되는 장소, 논리적인 표현
    - Partition : Commit Log, 하나의 Topic 은 하나 이상의 Partition 으로 구성
      병렬처리 (Throughput 향상) 를 위해서 다수의 Partition 사용
    - Segment : 메시지 (데이터) 가 저장되는 실제 물리 File
      Segment File 이 지정된 크기보다 크거나 지정된 기간보다 오래되면 새 파일이 열리고 메시지는 새파일에 추가됨

- Topic, Partition,Segment, Physical View
  - 
    - Topic 생성시 Partition 개수를 지정하고, 각 Partition 은 Broker 들에 분산되며 Segment File 들로 구성됨
    - Rolling Strategy : log.segment.bytes (default 1 GB) , log.roll.hours (default 168 hours)

- Active Segment Partition 당 하나의 Active Segment
  - 
    - Partition 당 오직 하나의 Segment 가 활성화(Active) 되어 있음 >> 데이터가 계속 쓰여지고 있는 중

- Topic,Partition, Segment 의 특징
  - 
    - Topic 생성시 Partition 개수를 지정, 개수 변경 가능하나 운영시에는 변경 권장하지 않음
    - Partition 번호는 0 부터 시작하고 오름차순
    - Topic 내의 Partition 들은 서로 독립적임
    - Event(Message) 의 위치를 나타내는 offset 이 존재
    - Offset 은 하나의 Partition 에서만 의미를 가짐
      Partition 0 의 offset 1 은 Partition 1의 offset 1 과 다름
    - Offset 값은 계속 증가하고 0 으로 돌아가지 않음
    - Event(Message) 의 순서는 하나의 Partition 내에서만 보장
    - Partition 에 저장된 데이터(Message) 는 변경이 불가능(Immutable)
    - Partition 에 Write 되는 데이터는 맨 끝에 추가되어 저장됨
    - Partition 은 Segment File 들로 구성됨
      Rolling 정책 : log.segment.bytes (default 1GB) , log.roll.hours (default 168 hours)


### Ch01-03.Broker-Zookeeper

- Kafka Broker Topic 과 Partition 을 유지 및 관리
  - 
    - Kafka Broker 는 Partition 에 대한 Read 및 Write 를 관리하는 소프트웨어
    - Kafka Server 라고 부르기도 함
    - Topic 내의 Partition 들을 분산, 유지 및 관리
    - 각각의 Broker 들을 ID 로 식별됨 (단, ID 는 숫자)
    - Topic 의 일부 Partition 들을 포함
        - Topic 데이터의 일부분(Partition) 을 갖을 뿐 데이터 전체를 갖고 있지 않음
    - Kafka Cluster : 여러개의 Broker 들로 구성됨
    - Client 는 특정 Broker 에 연결하면 전체 클러스터에 연결됨
    - 최소 3대 이상의 Broker 를 하나의 Cluster 로 구성해야함
        - 4대 이상을 권장함

- Kafka Broker ID 와 Partition ID 의 관계 > 아무런 관계 없음
  - 
    - Broker ID 와 Partition ID 간에는 아무런 관계가 없음
        - 어느 순서에나 있을수 있음
    - Topic 을 구성하는  Partition 들은 여러 Broker  상에 분산됨
    - Topic 생성시 Kafka 가 자동으로 Topic 을 구성하는 전체 Partition 들을 모든 Broker 에게 할당해주고 분배해줌

- BootStrap Servers Broker Servers 를 의미
  - 
    - 모든 Kafka Broker 는 Bootstrap (부트스트랩) 서버라고 부름
    - 하나의 Broker 에만 연결하면 Cluster 전체에 연결됨
        - 하지만, 특정 Broker 장애를 대비하여, 전체 Broker List (IP,Port) 를 파라미터로 입력 권장
    - 각각의 Broker 는 모든 Broker,Topic,Partition 에 대해 알고 있음 (Metadata)

- Zookeeper Broker 를 관리
  - 
    - Zookeeper 는 Broker 를 관리 (Broker 들의 목록 / 설정을 관리) 하는 소프트웨어
    - Zookeeper 는 변경사항에 대해 Kafka 에 알림
        - Topic 생성/ 제거 , Broker 추가 / 제거 등
    - Zookeeper 없이는 Kafka 가 작동할수 없음
        - KIP-500 을 통해서 Zookeeper 제거가 진행중
        - 2022년에 Zookeeper 를 제거한 정식버전 출시 예정
    - Zookeeper 는 홀수의 서버로 작동하게 설계돼있음 (최소3 권장 5)
    - Zookeeper 에는 Leader(writes)가 있고 나머지 서버는 Follower(reads)

- Zookeeper 와 Broker 는 서로 다르다
  - 
    - Broker 는 Partition 에 대한 Read 및 Write 를 관리하는 소프트웨어
    - Broker 는 Topic 내의 Partition 들을 분산, 유지 및 관리
    - 최소 3대 이상의 Broker 를 하나의 Cluster 로 구성해야함
        - 4대 이상을 권장함
    - Zookeeper 는 Broker 를 관리 (Broker 들의 목록 / 설정을 관리) 하는 소프트웨어
    - Zookeeper 는 홀수의 서버로 작동하게 설계되어 있음 (최소 3 , 권장 5)

## Ch01-04.Producer
- Producer 와 Consumer 의 분리 (Decoupling) , 기본 동작방식
  - 
    - Producer 와 Consumer 는 서로 알지 못하며, Producer 와 Consumer 는 각각 고유의 속도로 Commit Log 에 Write 및 Read 를 수행

- Record (Message) 구조
  - 
    - Header , Key , Value
    - Message == Record == Event == Data
        - Record
            - Headers (Topic , Partition , Timestamp , etc) == Metadata
            - Body (Business Relevant Data : key + value)
                - Key 와 Value 는 Avro , Json 등 다양함

- Serializer / Deserializer To / From Byte Array
  -
    - Kafka 는 Record (데이터) 를 Byte Array 로 저장
    - Producer / Consumer
    - JSON String Avro Protobuf >> Serializer >> Byte Array >> Deserializer

- Producer Sample Code Serializer
    - Key 와 Value 용 Serializer 를 각각 설정

      private Properties props = new Properties();
      props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"broker101:9092,broker102:9092");
      props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,org.apache.kafka.common.serialization.StringSerializer.class);
      props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,io.confluent.kafka.serializers.KafkaAvroSerializer.class);

      KafkaProducer producer = new KafkaProducer(props);

- Partitioner 의 역할
  - 
    - 메시지를 Topic 의 어떤 Partition 으로 보낼지 결정
    - Partition = Hash(Key) % Number of Partitions
    - Producer Partitioner Topic A ( Partition 0 ~ 2 )
    - 전제조건 ::: Key 가 null 이 아닐경우 !!!

- Summary
  - 
    - Message == Record == Event == Data
    - Message 는 Header 와 Key 그리고 value 로 구성
    - Kafka 는 Record (데이터)를 Byte Array 로 저장
    - Producer 는 Serializer , Consumer 는 Deserializer 를 사용
    - Producer 는 Message 의 Key 존재 여부에 따라서 Partitioner 를 통한 메시지 처리 방식이 다름

## Ch01-05.Consumer
- Consuming from Kafka
  - 
    - Partition 으로부터 Record 를 가져옴 (Poll)
    - Consumer 는 각각 고유의 속도로 Commit Log 로 부터 순서대로 Read(Poll) 를 수행
    - 다른 Consumer Group 에 속한 Consumer 들은 서로 관련이 없으며, Commit Log (Partition) 에 있는 Event (Message) 를 동시에 다른 위치에서 Read 할수 있음

- Multi-Partitions with Single Consumer
  - 
    - 4개의 Partition 으로 구성된 Topic 의 데이터를 사용하는 Single Consumer 가 있는경우,
      이 Consumer 는 Topic 의 모든 Partition 에서 모든 Record 를 Consume 함
    - 하나의 Consumer 는 각 Partition 에서의 Consumer Offset 을 별도로 유지 (기록) 하면서 모든 Partition 에서 Consume 함



- Summary
  - 
    - Consumer 가 자동이나 수동으로 데이터를 읽은 위치를 commit 하여 다시 읽음을 방지
    - __consumer_offsets  라는 Internal Topic 에서 Consumer Offset 을 저장하여 관리
    - 동일한 group.id 로 구성된 모든 Consumer 들은 하나의 Consumer Group 을 형성
    - 다른 Consumer Group 의 Consumer 들은 분리되어 독립적으로 작동
    - 동일한 Key 를 가진 메시지는 동일한 Partition 에만 전달되어 Key 레벨의 순서 보장 가능
    - Key 선택이 잘못되면 작업부하가 고르지않을 수 있음
    - Consumer Group 내의 다른 Consumer 가 실패한 Consumer 를 대신하여 Partition 에서 데이터를 가져와서 처리함

## Ch01-06.Replication
- 다른 Broker 에서 Partition 을 새로 만들 수 있으면 장애 해결
- 메시지 (데이터) 및 Offset 정보의 유실?

- Replication(복제) of Partition
    - 장애를 대비하기위한 기술
        - Partition 을 복제 하여 다른 Broker 상에서 복제물 (Replicas) 을 만들어 장애를 미리 대비함
        - 원본 데이터를 갖고 있는 Partition > Leader
        - 복제 데이터를 갖고 있는 Partition > Follower
        - Producer 는 Leader 에만 Writer 하고 Consumer 는 Leader 로부터만 Read 함
        - Follower 는 Broker 장애시 안정성을 제공하기 위해서만 존재
        - Follower 는 Leader 의 Commit Log 에서 데이터를 가져오기 요청(Fetch Request) 으로 복제
            - Apache Kafka 2.4 > Follower Fetching (Read)

- Leader 장애
  - 
    - 새로운 Leader 를 선출
        - Leader 에 장애가 발생한다면?
        - Kafka 클러스터는 Follower 중에서 새로운 Leader 를 선출
        - Clients (Producer / Consumer) 는 자동으로 새 Leader 로 전환

- Partition Leader 에 대한 자동 분산
  - 
    - Hot Spot 방지
    - 하나의 Broker 에만 Partition 의 Leader 들이 몰려있다면?
    - 특정 Broker 에만 Client (Producer / Consumer) 으로 인해 부하 집중
    - auto.leader.rebalance.enable : 불균형 인지 계속 체크하는 옵션

