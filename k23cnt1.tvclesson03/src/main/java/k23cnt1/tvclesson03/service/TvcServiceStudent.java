package k23cnt1.tvclesson03.service;

import k23cnt1.tvclesson03.entity.TvcStudent;
import org.springframework.stereotype.Service;

import  java.util.*;

/**
 * Service class: TvcServiceStudent
 * <p>Lớp dịch vụ thực hiện các chức năng thao tác với List
 Object Student</p>
 *
 * @author Chung Trịnh
 * @version 1.0
 * @Date 10/11/2025
 */

@Service
public class TvcServiceStudent {
    List<TvcStudent> tvcStudents ;

    public  TvcServiceStudent(){
        tvcStudents = List.of(
                new TvcStudent(1L, "Trịnh Văn Chung", 46, true, "Hà Nội", "0978611889", "chungtrinhj@gmail.com"),
                new TvcStudent(2L, "Trần Thị Bình", 21, false, "Hải Phòng", "0987654321", "binh.tran@example.com"),
                new TvcStudent(3L, "Lê Minh Cường", 22, true, "Đà Nẵng", "0905123456", "cuong.le@example.com"),
                new TvcStudent(4L, "Phạm Thảo Vy", 19, false, "TP. Hồ Chí Minh", "0978123456", "vy.pham@example.com"),
                new TvcStudent(5L, "Võ Đức Hoàng", 23, true, "Cần Thơ", "0932123456", "hoang.vo@example.com"),
                new TvcStudent(6L, "Hoàng Lan Chi", 20, false, "Nam Định", "0945123123", "chi.hoang@example.com"),
                new TvcStudent(7L, "Ngô Văn Tuấn", 24, true, "Thanh Hóa", "0915456789", "tuan.ngo@example.com"),
                new TvcStudent(8L, "Đặng Mai Hương", 22, false, "Nghệ An", "0967345678", "huong.dang@example.com"),
                new TvcStudent(9L, "Lý Quốc Bảo", 21, true, "Bình Dương", "0902345678", "bao.ly@example.com"),
                new TvcStudent(10L, "Đỗ Thị Hạnh", 20, false, "Vĩnh Long", "0923456789", "hanh.do@example.com")
        );
    }
    // Các phương thức
    // Lấy toàn bộ danh sách sinh viên
    public List<TvcStudent> getTvcStudents() {
        return  tvcStudents;
    }

    // Lấy sinh viên theo id
    public TvcStudent getTvcStudent(Long tvcId) {
        return tvcStudents.stream()
                .filter(student -> student
                        .getTvcId() == tvcId)
                .findFirst().orElse(null);
    }

}
