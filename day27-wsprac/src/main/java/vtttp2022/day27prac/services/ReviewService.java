package vtttp2022.day27prac.services;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import vtttp2022.day27prac.models.Edit;
import vtttp2022.day27prac.models.Review;
import vtttp2022.day27prac.repositories.GameRepo;
import vtttp2022.day27prac.repositories.ReviewRepo;

@Service
public class ReviewService {
    
    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private ReviewRepo reviewRepo;

    public boolean checkGIDExists(Integer gid) {
        return gameRepo.checkGIDExists(gid);
    }

    public boolean checkRIDExists(String reviewId) {
        return reviewRepo.checkRIDExists(reviewId);
    }

    public boolean insertReview(Review r) {
        r.setName(gameRepo.getGameNameByGID(r.getGid()));

        return reviewRepo.insertReview(r.toDocument());
    }

    public boolean updateReview(String reviewId, Edit e) {
        return reviewRepo.updateReview(reviewId, e.toDocument());
    }

    public JsonObject retrieveLatestReviewById(String reviewId) {
        Document reviewDoc = reviewRepo.retrieveReviewById(reviewId);
        Review r = Review.create(reviewDoc);
        boolean withLatestEdit;
        if (r.getEditList() == null) {
            withLatestEdit = false;
        } else {
            withLatestEdit = true;
        }

        JsonObject resp = r.toJSON(withLatestEdit);

        return resp;
    }

    public JsonObject retrieveReviewHistoryById(String reviewId) {
        Document reviewDoc = reviewRepo.retrieveReviewById(reviewId);
        Review r = Review.create(reviewDoc);
        JsonObject resp = r.toJSON();

        return resp;
    }
}
