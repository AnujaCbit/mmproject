package lk.mmp.api.services.Impl;

import lk.mmp.api.component.StudentReqRes;
import lk.mmp.api.repository.StudentRepository;
import lk.mmp.api.services.StudentService;
import lk.mmp.core.dao.CourseDAO;
import lk.mmp.core.dao.StudentDAO;
import lk.mmp.core.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StudentDAO createAndUpdateStudent(StudentDAO studentDAO) {
        Student student = modelMapper.map(studentDAO, Student.class);
        Student savedStudent = studentRepository.save(student);
        log.info("Student saved or updated");

        return modelMapper.map(savedStudent, StudentDAO.class);
    }

    @Override
    public StudentDAO getExistingStudent(StudentDAO studentDAO) {
        Optional<Student> student = studentRepository.findById(studentDAO.getId());
        return modelMapper.map(student.get(), StudentDAO.class);
    }

    @Override
    public List<StudentDAO> getAllStudents() {
        List<Student> all = studentRepository.findAll();
        log.info("All students fetched");
        return all.stream().
                map(student -> modelMapper.map(student, StudentDAO.class)).
                collect(Collectors.toList());
    }

    @Override
    public StudentReqRes getStudentCources(long id) {
        Student student = studentRepository.getOne(id);
        return modelMapper.map(student, StudentReqRes.class);
    }

}
