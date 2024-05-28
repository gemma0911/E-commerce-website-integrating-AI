package vn.fs.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.fs.entities.Comment;
import vn.fs.entities.Product;
import vn.fs.repository.CommentRepository;
import vn.fs.repository.ProductRepository;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProductRepository productRepository;
    
    @PostMapping(value = "/addComment")
    @ResponseBody
    public String addComment(@RequestBody Map<String, Object> requestData, ModelMap model) {
        try {
            Map<String, Object> commentData = (Map<String, Object>) requestData.get("comment");
            String content = (String) commentData.get("content");
            Long productId = ((Number) ((Map<String, Object>) commentData.get("product")).get("product_id")).longValue();
            
            Optional<Product> productOptional = productRepository.findById(productId);
            
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                
                Comment comment = new Comment();
                comment.setContent(content);
                comment.setProduct(product);
                commentRepository.save(comment);
                
                return "redirect:/" + productId;
            } else {
                return "redirect:/products";
            }
        } catch (Exception e) {
            return "redirect:/products";
        }
    }

}
