package vn.fs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.fs.entities.Comment;
import vn.fs.entities.Favorite;

/**
 * @author Quang Thang
 *
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    @Query(value = "SELECT * FROM comments WHERE product_id = ?", nativeQuery = true)
    List<Comment> selectComment(Long productId);
}
