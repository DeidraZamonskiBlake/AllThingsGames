package com.wcci.final_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wcci.final_project.dto.ReviewPayload;
import com.wcci.final_project.entity.Game;
import com.wcci.final_project.entity.Review;
import com.wcci.final_project.entity.User;
import com.wcci.final_project.service.GameService;
import com.wcci.final_project.service.ReviewService;
import com.wcci.final_project.service.UserService;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody ReviewPayload reviewPayload) {
        Review review = new Review();

        Long reviewGameId = reviewPayload.getGameId();
        Long reviewUserId = reviewPayload.getUserId();

        Game reviewGame = gameService.getGameById(reviewGameId);
        User user = userService.getUserById(reviewUserId);

        review.setText(reviewPayload.getText());
        
        if (reviewGame == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        review.setGame(gameService.getGameById(reviewGameId));

        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        review.setUser(user);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> findReviewById(@PathVariable Long id) {
        Review foundReview = reviewService.getReviewById(id);
        if (foundReview == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(foundReview);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> modifyReview(@PathVariable Long id, @RequestBody ReviewPayload reviewPayload) {
        Review existingReview = reviewService.getReviewById(id);

        Long reviewGameId = reviewPayload.getGameId();
        Long reviewUserId = reviewPayload.getUserId();

        Game reviewGame = gameService.getGameById(reviewGameId);
        User user = userService.getUserById(reviewUserId);

        existingReview.setText(reviewPayload.getText());
        
        if (reviewGame == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        existingReview.setGame(gameService.getGameById(reviewGameId));

        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        existingReview.setUser(user);

        return new ResponseEntity<>(existingReview, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReview(@PathVariable Long id) {
        boolean isDeleted = reviewService.deleteReview(id);

        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        return ResponseEntity.noContent().build();
    }

}