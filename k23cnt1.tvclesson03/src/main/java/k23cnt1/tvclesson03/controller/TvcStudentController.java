package k23cnt1.tvclesson03.controller;

import k23cnt1.tvclesson03.entity.TvcStudent;
import k23cnt1.tvclesson03.service.TvcServiceStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TvcStudentController {
    @Autowired
    public TvcServiceStudent tvcServiceStudent;

    @GetMapping("/student-list")
    public List<TvcStudent> getAllStudents() {
        return  tvcServiceStudent.getTvcStudents();
    }

    @GetMapping("/student/{tvcId}")
    public TvcStudent getTvcStudentById(@PathVariable String tvcId)
    {
        Long param = Long.parseLong(tvcId);
        return  tvcServiceStudent.getTvcStudent(param);
    }

}
