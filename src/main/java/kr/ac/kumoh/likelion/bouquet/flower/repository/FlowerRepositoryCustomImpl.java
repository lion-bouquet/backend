package kr.ac.kumoh.likelion.bouquet.flower.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.kumoh.likelion.bouquet.flower.domain.Flower;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.ac.kumoh.likelion.bouquet.flower.domain.QFlower.flower;
import static kr.ac.kumoh.likelion.bouquet.flower.domain.QMatchingColor.matchingColor;

/**
 * FlowerRepositoryCustom의 QueryDSL 구현체
 */
@RequiredArgsConstructor
public class FlowerRepositoryCustomImpl implements FlowerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Flower> findFlowersByCriteria(Long colorId, Integer month) {
        return queryFactory
                .selectFrom(flower)
                .where(
                        colorIdEq(colorId),
                        monthBetween(month)
                )
                .fetch();
    }

    private BooleanExpression colorIdEq(Long colorId) {
        // 서브쿼리를 사용하여 특정 colorId를 가진 꽃의 ID 목록을 찾고, 해당 ID를 가진 꽃들을 필터링
        return colorId == null
                ? null
                : flower.id.in(
                queryFactory
                        .select(matchingColor.flower.id)
                        .from(matchingColor)
                        .where(matchingColor.color.id.eq(colorId))
        );
    }

    private BooleanExpression monthBetween(Integer month) {
        if (month == null) {
            return null;
        }

        // HQL에서 지원하는 substring() + cast(as integer) 사용
        NumberTemplate<Integer> startMonth = Expressions.numberTemplate(Integer.class,
                "cast(substring({0},1,2) as integer)", flower.seasonStart);
        NumberTemplate<Integer> endMonth   = Expressions.numberTemplate(Integer.class,
                "cast(substring({0},1,2) as integer)", flower.seasonEnd);

        // 시작 월 <= 종료 월 (예: 3월 ~ 5월)
        BooleanExpression normalRange = startMonth.loe(endMonth)
                .and(startMonth.loe(month).and(endMonth.goe(month)));

        // 시작 월 > 종료 월 (예: 11월 ~ 2월)
        BooleanExpression crossYearRange = startMonth.gt(endMonth)
                .and(startMonth.loe(month).or(endMonth.goe(month)));

        // seasonStart/End 널·빈 문자열 아닌 것만 필터
        return flower.seasonStart.isNotNull()
                .and(flower.seasonEnd.isNotNull())
                .and(normalRange.or(crossYearRange));
    }
}
