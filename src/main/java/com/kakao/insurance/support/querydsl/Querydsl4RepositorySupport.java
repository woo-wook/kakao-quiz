package com.kakao.insurance.support.querydsl;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;

public abstract class Querydsl4RepositorySupport {
    private final Class domainClass;
    private Querydsl querydsl;
    private EntityManager entityManager;
    public JPAQueryFactory queryFactory;

    public Querydsl4RepositorySupport(Class<?> domainClass) {
        Assert.notNull(domainClass, "Domain class must not be null!");
        this.domainClass = domainClass;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        Assert.notNull(entityManager, "EntityManager must not be null!");
        JpaEntityInformation entityInformation =
                JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);

        SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
        EntityPath path = resolver.createPath(entityInformation.getJavaType());

        this.entityManager = entityManager;
        this.querydsl = new Querydsl(entityManager, new PathBuilder<>(path.getType(), path.getMetadata()));
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @PostConstruct
    public void validate() {
        Assert.notNull(entityManager, "EntityManager must not be null!");
        Assert.notNull(querydsl, "Querydsl must not be null!");
        Assert.notNull(queryFactory, "QueryFactory must not be null!");
    }

    protected JPAQueryFactory getQueryFactory() {
        return queryFactory;
    }

    protected Querydsl getQuerydsl() {
        return querydsl;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected <T> JPAQuery<T> select(Expression<T> expr) {
        return getQueryFactory().select(expr);
    }

    protected <T> JPAQuery<T> selectFrom(EntityPath<T> from) {
        return getQueryFactory().selectFrom(from);
    }

    protected <T> Page<T> applyPagination(Pageable pageable,
                                          Function<JPAQueryFactory, JPAQuery> contentQuery) {

        JPAQuery jpaQuery = contentQuery.apply(getQueryFactory());
        List<T> content = getQuerydsl().applyPagination(pageable,
                jpaQuery).fetch();

        return PageableExecutionUtils.getPage(content, pageable,
                jpaQuery::fetchCount);
    }

    protected <T> Page<T> applyPagination(Pageable pageable,
                                          Function<JPAQueryFactory, JPAQuery> contentQuery,
                                          Function<JPAQueryFactory, JPAQuery> countQuery) {

        JPAQuery jpaContentQuery = contentQuery.apply(getQueryFactory());
        List<T> content = getQuerydsl().applyPagination(pageable, jpaContentQuery).fetch();

        JPAQuery countResult = countQuery.apply(getQueryFactory());
        return PageableExecutionUtils.getPage(content, pageable,
                countResult::fetchCount);
    }

    /**
     * path = data (String)
     * @param path
     * @param data
     * @return
     */
    protected BooleanExpression whereClauseEquals(StringPath path, String data) {
        if (StringUtils.isNullOrEmpty(data)) {
            return null;
        }

        return path.eq(data);
    }

    /**
     * path = data (Number)
     * @param path
     * @param data
     * @param <T>
     * @return
     */
    protected <T extends Number & Comparable<?>> BooleanExpression whereClauseEquals(NumberPath<T> path, T data) {
        if (data == null) {
            return null;
        }

        return path.eq(data);
    }

    /**
     * path = data (Boolean)
     * @param path
     * @param data
     * @return
     */
    protected BooleanExpression whereClauseEquals(BooleanPath path, Boolean data) {
        if(data == null) {
            return null;
        }

        return path.eq(data);
    }

    /**
     * path = data (DateTime)
     * @param path
     * @param data
     * @param <T>
     * @return
     */
    protected <T extends Comparable> BooleanExpression whereClauseEquals(DateTimePath<T> path, T data) {
        if(data == null) {
            return null;
        }

        return path.eq(data);
    }

    /**
     * path = data (Enum)
     * @param path
     * @param data
     * @param <T>
     * @return
     */
    protected <T extends Enum<T>> BooleanExpression whereClauseEquals(EnumPath<T> path, T data) {
        if(data == null) {
            return null;
        }

        return path.eq(data);
    }


    /**
     * path IN (data) (String)
     * @param path
     * @param data
     * @return
     */
    protected BooleanExpression whereClauseExists(StringPath path, List<String> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        return path.in(data);
    }

    /**
     * path IN (data) (Number)
     * @param path
     * @param data
     * @param <T>
     * @return
     */
    protected <T extends Number & Comparable<?>> BooleanExpression whereClauseExists(NumberPath<T> path, List<T> data) {
        if (data == null) {
            return null;
        }

        return path.in(data);
    }

    /**
     * path IN (data) (DateTime)
     * @param path
     * @param data
     * @param <T>
     * @return
     */
    protected <T extends Comparable> BooleanExpression whereClauseExists(DateTimePath<T> path, List<T> data) {
        if(data == null) {
            return null;
        }

        return path.in(data);
    }

    /**
     * path LIKE '%data%' (String)
     * @param path
     * @param data
     * @return
     */
    protected BooleanExpression whereClauseContains(StringPath path, String data) {
        if (StringUtils.isNullOrEmpty(data)) {
            return null;
        }

        return path.contains(data);
    }

    /**
     * path between start and end (Date)
     * @param path
     * @param start
     * @param end
     * @param <T>
     * @return
     */
    protected <T extends Comparable> BooleanExpression whereClauseBetween(DateTimePath<T> path, T start, T end) {
        if(start == null && end == null) {
            return null;
        }

        return path.between(start, end);
    }

    /**
     * path between start and end (String)
     * @param path
     * @param start
     * @param end
     * @return
     */
    protected BooleanExpression whereClauseBetween(StringPath path, String start, String end) {
        if(start == null && end == null) {
            return null;
        }

        return path.between(start, end);
    }
}
