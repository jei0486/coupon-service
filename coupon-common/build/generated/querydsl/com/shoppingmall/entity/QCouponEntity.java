package com.shoppingmall.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCouponEntity is a Querydsl query type for CouponEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCouponEntity extends EntityPathBase<CouponEntity> {

    private static final long serialVersionUID = -2063342257L;

    public static final QCouponEntity couponEntity = new QCouponEntity("couponEntity");

    public final StringPath c_name = createString("c_name");

    public final NumberPath<Integer> discount = createNumber("discount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> endDt = createDateTime("endDt", java.time.LocalDateTime.class);

    public final StringPath id = createString("id");

    public final ComparablePath<Character> rate_yn = createComparable("rate_yn", Character.class);

    public final DateTimePath<java.time.LocalDateTime> reg_date = createDateTime("reg_date", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> startDt = createDateTime("startDt", java.time.LocalDateTime.class);

    public QCouponEntity(String variable) {
        super(CouponEntity.class, forVariable(variable));
    }

    public QCouponEntity(Path<? extends CouponEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCouponEntity(PathMetadata metadata) {
        super(CouponEntity.class, metadata);
    }

}

