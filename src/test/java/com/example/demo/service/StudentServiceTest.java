package com.example.demo.service;

import com.example.demo.dto.StudentRequestDto;
import com.example.demo.dto.StudentResponseDto;
import com.example.demo.entity.School;
import com.example.demo.entity.Student;
import com.example.demo.exception.StudentNotFound;
import com.example.demo.repository.SchoolRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.transformer.StudentTransformer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    //1. which service we want to test
    @InjectMocks
    private StudentService studentService;

    //2. declare the dependencies
    @Mock   // 3.
    private  StudentRepository studentRepository;
    @Mock
    private  StudentTransformer studentTransformer;
    @Mock
    private  SchoolRepository schoolRepository;

//    @BeforeEach
//    @Disabled
//    void setUp() {
//        // 4.
//        MockitoAnnotations.openMocks(this);
//    }

    // 5.
    @Test
    void should_successfully_save_Student() throws Exception {
        // Given :- what is given to us (i.e in the parameter) and what else we required
             /*StudentRequestDto*/
        StudentRequestDto requestDto = new StudentRequestDto(
                "Ajay",
                "Yadav",
                27,
                "B.TechMe005",
                1L
        );
           /*also this class need the object of student*/
        Student student = Student.builder().firstName("Ajay").lastName("Yadav").age(27).rollNo("B.TechMe005").build();
        Student expectedStudent = Student.builder().id(1).firstName("Ajay").lastName("Yadav").age(27).rollNo("B.TechMe005").build();
        School school = School.builder().id(1L).address("Gurgaon").build();
        /*MOCK THE CALLS*/
        //1. Mock the StudentTransformer call
        when(studentTransformer.studentRequestDtoToStudent(requestDto)).thenReturn(student);
        // 2. Mock the call the student repository
        when(studentRepository.save(student)).thenReturn(expectedStudent);
        // 3. studentTransformer.studentToStudentResponseDto
        when(studentTransformer.studentToStudentResponseDto(expectedStudent))
                .thenReturn(new StudentResponseDto("Ajay","Yadav",27,"B.TechMe005"));

        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));


        // When
        /* it means when we call our studentService.addStudent() we are expecting the object of type StudentResponseDto,
        * so we will create the object of StudentResponseDto*/
        StudentResponseDto studentResponseDto = studentService.addStudent(requestDto);

        // Then
        assertEquals(requestDto.getFirstName(),studentResponseDto.getFirstName());
        assertEquals(requestDto.getLastName(),studentResponseDto.getLastName());
        assertEquals(requestDto.getAge(),studentResponseDto.getAge());

        // we want to be sure that our method is called only once or not, to improve our code performance with Mocikto.verify()
        Mockito.verify(studentTransformer,Mockito.times(1)).studentRequestDtoToStudent(requestDto);
        Mockito.verify(studentRepository,Mockito.times(1)).save(student);
        Mockito.verify(studentTransformer,Mockito.times(1)).studentToStudentResponseDto(expectedStudent);
    }


    @Test
    void should_return_all_students(){
        // given
        List<Student> studentList = new ArrayList<>();
        studentList.add(Student.builder().firstName("Ajay").lastName("Yadav").age(27).rollNo("B.TechMe005").build());

        /*MOCK THE CALLS*/
        when(studentRepository.findAll()).thenReturn(studentList);
        // means we are passing any object of Student class , (becoz its a list , so we can pass any object of student class)
        when(studentTransformer.studentToStudentResponseDto(ArgumentMatchers.any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "Ajay",
                        "Yadav",
                        27,
                        "B.TechMe005"
                ));


        // when
        List<StudentResponseDto> responseDtoList = studentService.getAllStudents();

        // then
        assertEquals(studentList.size(),responseDtoList.size());

        // verify
        Mockito.verify(studentRepository,Mockito.times(1)).findAll();
    }



    @Test
    void should_find_student_by_given_id(){
        // given
        Long studentId = 1L;
        Student student = Student.builder().firstName("Ajay").lastName("Yadav").age(27).rollNo("B.TechMe005").build();

        // Mock the calls
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentTransformer.studentToStudentResponseDto(student)).thenReturn(new StudentResponseDto(
                "Ajay",
                "Yadav",
                27,
                "B.TechMe005"
        ));

        // when
        StudentResponseDto responseDto = studentService.getStudentById(studentId);

        // then
        assertEquals(student.getFirstName(),responseDto.getFirstName());
        assertEquals(student.getLastName(),responseDto.getLastName());
        assertEquals(student.getAge(),responseDto.getAge());

        // verify
        Mockito.verify(studentRepository,Mockito.times(1)).findById(studentId);
    }


    @Test
    void should_throw_StudentNotFound_when_student_not_found() {
        // Given
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // When and Then
        StudentNotFound exception = assertThrows(StudentNotFound.class, () -> studentService.getStudentById(studentId));
        assertEquals("Student Not Found", exception.getMessage());
    }


    @Test
    void should_return_all_students_by_firstName(){
        // given
        String firstName = "Ajay";
        List<Student> studentList = new ArrayList<>();
        studentList.add(Student.builder().firstName("Ajay").lastName("Yadav").age(27).rollNo("B.TechMe005").build());
        studentList.add(Student.builder().firstName("Ajay").lastName("Singh").age(29).rollNo("B.TechMe003").build());

        // mock
        when(studentRepository.findAllStudentByFirstNameContaining(firstName))
                .thenReturn(studentList);
        when(studentTransformer.studentToStudentResponseDto(ArgumentMatchers.any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "Ajay",
                        "Yadav",
                        27,
                        "B.TechMe005"
                ));

        // when
        List<StudentResponseDto> responseDtoList = studentService.getStudentByName(firstName);

        // then
        assertEquals(responseDtoList.size(),studentList.size());
        Assertions.assertThat(responseDtoList)
                .isNotNull()
                .hasSize(2);

        // verify
        Mockito.verify(studentRepository,Mockito.times(1)).findAllStudentByFirstNameContaining(firstName);
    }

}






// Need to Read

/*Test Isolation :- it means we have to run and  write the test for the StudentService
* in isolation of its dependencies, (i.e studentRepository and studentTransformer)
*
* in simple way :- i want to execute and write test case for student service ,
*  but i don't necessarily need to have the real instance/object of my  studentRepository and studentTransformer
*
* so inorder to achieve the Test Isolation,we need to mock the objects on which our service depends,
* */


/*
 * 1. first declare the class/service/controller which you want to test
 * 2. declare the dependencies, and add the annotation @mock, to mock the objects, on which our service depends.
 *
 * 3. use @InjectMocks on the service object.
 *     @InjectMocks :- ye wo dependency dhundega, jispe @Mock laga hai , or jo hamari service class mein chahiye,
 *                     unko mock karke , unki dependency inject kar lega , apne mein(service class)
 *
 *    This means when we create the instance or to initialize the student service, the mocking framework look for
 *    2 objects , annotated with @mock annotations, one of them is of studentRepository type and other is of studentTransformer type.
 *
 * 4. now we need to tell the mockito framework to starts the mock for this current class, which we do in the setUp method
 *
 * 5. create the test method, for which you want to write the test case
 * 6. initialize the classes for which you want to write the test cases, using before each
 * */


/* When we are running the save Student method , we are getting the null pointer exception,
*
*  this is happening because the studentService method is trying to call the studentTransformer and studentRepository,
*  but here we are trying to mock these two dependencies, we don't have the real instances of these 2 dependencies, we are
*  using the mock, because we are trying run the studentService test in isolation.
*
*  -> SO WE NEED TO MOCK THE CALLS :-
*       Q. :- so which calls we need to mock ??
*       Ans:- we need to mock every call , that uses another service or another dependency in our studentService,
*             (means jitni bhi dependencies mere studentService method mein use hui hai , unn sabhi ke calls ko mock karna hai )
*             in this case we have
*              a. studentTransformer.studentRequestDtoToStudent()
*              b. studentRepository.save()
*              c. studentTransformer.studentToStudentResponseDto()
*
* */