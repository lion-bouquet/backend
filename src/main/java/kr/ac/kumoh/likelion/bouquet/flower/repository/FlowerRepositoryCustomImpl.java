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
        if (colorId == null) {
            return null;
        }
        // 서브쿼리를 사용하여 특정 colorId를 가진 꽃의 ID 목록을 찾고, 해당 ID를 가진 꽃들을 필터링
        return flower.id.in(
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

        // seasonStart와 seasonEnd 필드에서 '월'을 제거하고 숫자로 변환
        NumberTemplate<Integer> startMonth = Expressions.numberTemplate(Integer.class,
                "function('cast', function('replace', {0}, '월', '') as int)", flower.seasonStart);
        NumberTemplate<Integer> endMonth = Expressions.numberTemplate(Integer.class,
                "function('cast', function('replace', {0}, '월', '') as int)", flower.seasonEnd);

        // 시작 월 <= 종료 월 (예: 3월 ~ 5월)
        BooleanExpression normalRange = startMonth.loe(endMonth)
                .and(startMonth.loe(month).and(endMonth.goe(month)));

        // 시작 월 > 종료 월 (예: 11월 ~ 2월)
        BooleanExpression crossYearRange = startMonth.gt(endMonth)
                .and(startMonth.loe(month).or(endMonth.goe(month)));

        // seasonStart 또는 seasonEnd가 비어있지 않은 경우에만 필터링
        return flower.seasonStart.isNotNull()
                .and(flower.seasonStart.isNotEmpty())
                .and(flower.seasonEnd.isNotNull())
                .and(flower.seasonEnd.isNotEmpty())
                .and(normalRange.or(crossYearRange));
    }
}
