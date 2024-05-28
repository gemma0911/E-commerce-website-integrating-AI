package vn.fs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.fs.entities.Category;
import vn.fs.entities.voucher;

@Repository
public interface VoucherRepository extends JpaRepository<voucher, Long> {

}
