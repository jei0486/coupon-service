package com.shoppingmall.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoppingmall.entity.UserCouponEntity;
import com.shoppingmall.enums.CouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.shoppingmall.entity.QCouponEntity.couponEntity;
import static com.shoppingmall.entity.QUserCouponEntity.userCouponEntity;

@RequiredArgsConstructor
@Repository
public class UserCouponRepositoryImpl implements UserCouponRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<UserCouponEntity> findAllByStatusAndEndDtBetween(CouponStatus status, LocalDateTime startDate, LocalDateTime endDate){
        return jpaQueryFactory.select(userCouponEntity)
                .from(userCouponEntity)
                .join(couponEntity)
                    .on(couponEntity.id.eq(userCouponEntity.coupon.id))
                .where(userCouponEntity.status.eq(status),
                        couponEntity.endDt.between(startDate,endDate)
                        )
                .fetch();
    }


    @Override
    public Optional<UserCouponEntity> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<UserCouponEntity> findAll() {
        return null;
    }

    @Override
    public List<UserCouponEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<UserCouponEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<UserCouponEntity> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(UserCouponEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserCouponEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends UserCouponEntity> S save(S entity) {
        return null;
    }

    @Override
    public <S extends UserCouponEntity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends UserCouponEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends UserCouponEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<UserCouponEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public UserCouponEntity getOne(String s) {
        return null;
    }

    @Override
    public UserCouponEntity getById(String s) {
        return null;
    }

    @Override
    public <S extends UserCouponEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends UserCouponEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends UserCouponEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends UserCouponEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserCouponEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends UserCouponEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends UserCouponEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
