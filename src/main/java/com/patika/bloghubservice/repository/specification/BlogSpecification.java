package com.patika.bloghubservice.repository.specification;

import com.patika.bloghubservice.dto.request.BlogSearchRequest;
import com.patika.bloghubservice.model.Blog;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlogSpecification {

    public static Specification<Blog> initSpecification(BlogSearchRequest request) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicateList = new ArrayList<>();

            if (request.getTitle() != null) {
                predicateList.add(criteriaBuilder.like(root.get("title"), "%" + request.getTitle() + "%")); //column name değil. attribute name olmalu
            }

            if (request.getLikeCount() != 0) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("likeCount"), request.getLikeCount())); //column name değil. attribute name olmalu
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };

    }
}
