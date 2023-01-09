package com.shoppingmall.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserCouponEntity is a Querydsl query type for UserCouponEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserCouponEntity extends EntityPathBase<UserCouponEntity> {

    private static final long serialVersionUID = -689534534L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserCouponEntity userCouponEntity = new QUserCouponEntity("userCouponEntity");

    public final QCouponEntity coupon;

    public final StringPath id = createString("id");

    public final DateTimePath<java.time.LocalDateTime> issued_at = createDateTime("issued_at", java.time.LocalDateTime.class);

    public final EnumPath<com.shoppingmall.enums.CouponStatus> status = createEnum("status", com.shoppingmall.enums.CouponStatus.class);

    public final DateTimePath<java.time.LocalDateTime> used_at = createDateTime("used_at", java.time.LocalDateTime.class);

    public final StringPath user_id = createString("user_id");

    public QUserCouponEntity(String variable) {
        this(UserCouponEntity.class, forVariable(variable), INITS);
    }

    public QUserCouponEntity(Path<? extends UserCouponEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserCouponEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserCouponEntity(PathMetadata metadata, PathInits inits) {
        this(UserCouponEntity.class, metadata, inits);
    }

    public QUserCouponEntity(Class<? extends UserCouponEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coupon = inits.isInitialized("coupon") ? new QCouponEntity(forProperty("coupon")) : null;
    }

}

