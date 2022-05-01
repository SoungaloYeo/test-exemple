package com.testexample.testexample;

import com.testexample.testexample.student.Gender;
import com.testexample.testexample.student.Student;
import com.testexample.testexample.student.StudentRepository;
import com.testexample.testexample.student.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    private Student studentOne;
    private Student studentTwo;


    @BeforeEach
    void setUp() {
        studentOne = Student.builder().email("one@gmail.com").gender(Gender.MALE).name("Karl").build();
        studentTwo = Student.builder().email("two@gmail.com").gender(Gender.FEMALE).name("Max").build();

        when(studentRepository.findAll()).thenReturn(Arrays.asList(studentOne, studentTwo));

        studentService = new StudentService(studentRepository);
    }

    @Test
    @DisplayName("trying to read all MALE students it's  Ok and return One student")
    void canGetAllStudents() {

        var allStudents = this.studentService.getAllStudents();

        Assertions.assertAll(
                ()-> Assertions.assertEquals(1, allStudents.size()),
                ()-> Assertions.assertTrue(allStudents.stream().findFirst().isPresent()),
                ()-> Assertions.assertEquals(allStudents.stream().findFirst().get().getEmail(), studentOne.getEmail())
        );
    }



}