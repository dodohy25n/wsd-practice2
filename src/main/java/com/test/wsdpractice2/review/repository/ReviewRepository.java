package com.test.wsdpractice2.review.repository;

import com.test.wsdpractice2.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
