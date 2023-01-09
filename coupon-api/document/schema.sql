#################################################################
#### MariaDB ####
#################################################################
/* 쿠폰 테이블 */
CREATE TABLE `coupon_info` (
    `id` varchar(255) NOT NULL,
    `coupon_name` varchar(100), 	/* 쿠폰 이름 */
    `discount` int(20), 	       /* 할인율 혹은 할인되는 가격 */
    `created_at` datetime,
    `modified_at` datetime,
    `start_dt` datetime,
    `end_dt` datetime,
    `rate_yn` varchar(50), /* 가격 OR 할인율 */
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 사용자 쿠폰 테이블 */
CREATE TABLE `user_coupon`(
    `id` VARCHAR(255) NOT NULL,
    `policy_id` VARCHAR(1000), 	/* 쿠폰 아이디 */
    `user_id` VARCHAR(255), 	/* 사용자 아이디 */
    `issued_at` datetime,		/* 쿠폰 발행 시간 */
    `used_at` datetime,		    /* 쿠폰 사용 시간 */
    `status` VARCHAR(50),		/* 쿠폰 사용 여부 */
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 사용자 테이블 */
CREATE TABLE `users` (
    `id` varchar(100) ,
    `login_id` varchar(100) ,
    `username` varchar(100) ,
    `password` varchar(255) ,
    `state` varchar(50) ,
    `created_at` datetime,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

## insert sql ##
INSERT INTO coupon_info
(id, coupon_name, discount, created_at , modified_at , start_dt, end_dt, rate_yn)
VALUES('95be217a-a553-4f4f-88a4-f2dd58f14fd9', '복숭아 세일방송', 40, NOW(), NULL, '2023-01-02 13:57:20.286000', '2023-01-15 13:57:20.286000', 'Y');

INSERT INTO coupon_info
(id, coupon_name, discount, created_at , modified_at , start_dt, end_dt, rate_yn)
VALUES('coupon:time-attack:1:date-time:1:issued:users', '복숭아 세일방송', 40, NOW(), NULL, '2023-01-02 13:57:20.286000', '2023-01-15 13:57:20.286000', 'Y');


#################################################################
#### Oracle ####
#################################################################
/* 쿠폰 테이블 */
CREATE TABLE "DEMO"."COUPON_INFO"(
"ID" VARCHAR2(255 CHAR) NOT NULL ENABLE,
"C_NAME" VARCHAR2(100), 	/* 쿠폰 이름 */
"DISCOUNT" NUMBER(10), 	/* 할인율 혹은 할인되는 가격 */
"REG_DATE" TIMESTAMP (6),
"MOD_DATE" TIMESTAMP (6),
"START_DT" DATE,
"END_DT" DATE,
"RATE_YN" VARCHAR2(10 CHAR), /* 가격 OR 할인율 */
PRIMARY KEY ("ID")
USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
TABLESPACE "USERS"  ENABLE
) SEGMENT CREATION IMMEDIATE
PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
NOCOMPRESS LOGGING
STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
TABLESPACE "USERS" ;


/* 사용자 쿠폰 테이블 */
CREATE TABLE "DEMO"."USER_COUPON"(
"ID" VARCHAR2(255 CHAR) NOT NULL ENABLE,
"POLICY_ID" VARCHAR2(1000), 	/* 쿠폰 아이디 */
"USER_ID" VARCHAR2(255 CHAR), 			/* 사용자 아이디 */
"ISSUED_AT" TIMESTAMP (6),      /* 쿠폰 발행 시간 */
"USED_AT" TIMESTAMP (6),		/* 쿠폰 사용 시간 */
"STATUS" VARCHAR2(10 CHAR),		/* 쿠폰 사용 여부 */
PRIMARY KEY ("ID")
USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
TABLESPACE "USERS"  ENABLE
) SEGMENT CREATION IMMEDIATE
PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
NOCOMPRESS LOGGING
STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
TABLESPACE "USERS" ;

## insert sql ##

INSERT INTO "C##NSMALL".COUPON
(ID, C_NAME, DISCOUNT, REG_DATE, MOD_DATE, START_DT, END_DT, RATE_YN)
VALUES('95be217a-a553-4f4f-88a4-f2dd58f14fd9', '복숭아 세일방송', 40, TIMESTAMP '2022-07-19 13:57:20.286000', NULL, TIMESTAMP '2022-07-30 13:57:20.286000', TIMESTAMP '2022-07-30 13:57:20.286000', 'Y');
