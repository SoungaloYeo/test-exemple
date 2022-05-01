package com.testexample.testexample;

import com.testexample.testexample.student.Gender;
import com.testexample.testexample.student.Student;
import com.testexample.testexample.student.StudentRepository;
import com.testexample.testexample.student.StudentService;
import com.testexample.testexample.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    public static final Long STUDENT_ID = 25L;
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    private Student studentOne;
    private Student studentTwo;


    @BeforeEach
    void setUp() {
        studentOne = Student.builder().email("one@gmail.com").gender(Gender.MALE).name("Karl").build();
        studentTwo = Student.builder().email("two@gmail.com").gender(Gender.FEMALE).name("Max").build();

        studentService = new StudentService(studentRepository);
    }

    @Test
    @DisplayName("trying to read all MALE students it's  Ok and return One student")
    void canGetAllStudents() {

        when(studentRepository.findAll()).thenReturn(Arrays.asList(studentOne, studentTwo));
        var allStudents = this.studentService.getAllStudents();

        Assertions.assertAll(
                ()-> Assertions.assertEquals(1, allStudents.size()),
                ()-> Assertions.assertTrue(allStudents.stream().findFirst().isPresent()),
                ()-> Assertions.assertEquals(allStudents.stream().findFirst().get().getEmail(), studentOne.getEmail())
        );
    }

    @Test
    @DisplayName("when deleting a existing student")
    void whenDeletingAExistingStudent() {

        studentOne.setId(STUDENT_ID);
        when(studentRepository.existsById(STUDENT_ID)).thenReturn(Boolean.TRUE);
        doNothing().when(studentRepository).deleteById(isA(Long.class));

        studentService.deleteStudent(STUDENT_ID);
        verify(studentRepository, times(1)).deleteById(STUDENT_ID);
    }

    @Test
    @DisplayName("when deleting a none existing student")
    void whenDeletingANoneExistingStudent() {

        studentOne.setId(STUDENT_ID);
        when(studentRepository.existsById(STUDENT_ID)).thenReturn(Boolean.FALSE);


        assertThrows(StudentNotFoundException.class, () -> {
            studentService.deleteStudent(STUDENT_ID);
        });

        verify(studentRepository, times(0)).deleteById(STUDENT_ID);
    }

}