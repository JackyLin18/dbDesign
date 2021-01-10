package com.dbDesign.Jacky.handler;

import com.dbDesign.Jacky.model.entity.Administrator;
import com.dbDesign.Jacky.model.entity.Student;
import com.dbDesign.Jacky.model.entity.Teacher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName ViewHandler
 * @Author Jacky
 * @Description
 **/
@Controller
public class ViewHandler {
    @RequestMapping(value = {"/login", "/index", "/"})
    public String login() {
        return "login";
    }

    @RequestMapping("/registerStudent")
    public String registerStudent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Administrator administrator =
                (Administrator) session.getAttribute("administrator");
        if (administrator == null) {
            return "login";
        }
        return "registerStudent";
    }

    @RequestMapping("/registerTeacher")
    public String registerTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Administrator administrator =
                (Administrator) session.getAttribute("administrator");
        if (administrator == null) {
            return "login";
        }
        return "registerTeacher";
    }

    @RequestMapping("/studentMain")
    public String studentMain(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "login";
        }
        return "studentMain";
    }

    @RequestMapping("/teacherMain")
    public String teacherMain(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) {
            return "login";
        }
        return "teacherMain";
    }

    @RequestMapping("/administratorMain")
    public String administratorMain(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Administrator administrator =
                (Administrator) session.getAttribute("administrator");
        if (administrator == null) {
            return "login";
        }
        return "administratorMain";
    }

    @RequestMapping("/studentOwnMessage")
    public String studentOwnMessage() {
        return "studentOwnMessage";
    }

    @RequestMapping("/teacherOwnMessage")
    public String teacherOwnMessage() {
        return "teacherOwnMessage";
    }

    @RequestMapping("/studentSearch")
    public String studentSearch(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        Administrator administrator = (Administrator) session.getAttribute("administrator");
        if (teacher == null && administrator == null) {
            return "login";
        }
        return "studentSearch";
    }

    @RequestMapping("/teacherSearch")
    public String teacherSearch(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        Administrator administrator = (Administrator) session.getAttribute("administrator");
        if (student == null && teacher == null && administrator == null) {
            return "login";
        }
        return "teacherSearch";
    }

    @RequestMapping("/courseSearch")
    public String courseSearch() {
        return "courseSearch";
    }

    @RequestMapping("/departmentSearch")
    public String departmentSearch() {
        return "departmentSearch";
    }

    @RequestMapping("/courseDo")
    public String courseDo(HttpServletRequest request) {
        Administrator administrator = (Administrator)
                request.getSession().getAttribute("administrator");
        if (administrator == null) {
            return "login";
        }
        return "courseDo";
    }

    @RequestMapping("/courseMessage")
    public String courseMessage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Administrator administrator = (Administrator) session.getAttribute("administrator");
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (administrator == null && teacher == null) {
            return "login";
        }
        return "courseMessage";
    }

    @RequestMapping("/teacherCourse")
    public String teacherCourse(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) {
            return "login";
        }
        return "teacherCourse";
    }

    @RequestMapping("/studentCourse")
    public String studentCourse(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            return "login";
        }
        return "studentCourse";
    }

    @RequestMapping("/studentList")
    public String studentList(@RequestParam Integer courseId, HttpServletRequest request) {
        request.setAttribute("courseId", courseId);
        return "studentList";
    }

    @RequestMapping("/allStudentList")
    public String allStudentList(HttpServletRequest request) {
        Administrator administrator = (Administrator)
                request.getSession().getAttribute("administrator");
        if (administrator == null) {
            return "login";
        }
        return "allStudentList";
    }

    @RequestMapping("/allTeacherList")
    public String allTeacherList(HttpServletRequest request) {
        Administrator administrator = (Administrator)
                request.getSession().getAttribute("administrator");
        if (administrator == null) {
            return "login";
        }
        return "allTeacherList";
    }

    @RequestMapping("/allStudentHistory")
    public String allStudentHistory(HttpServletRequest request) {
        Administrator administrator = (Administrator)
                request.getSession().getAttribute("administrator");
        if (administrator == null) {
            return "login";
        }
        return "allStudentHistory";
    }

    @RequestMapping("/studentMessage")
    public String studentMessage(@RequestParam Integer id, HttpServletRequest request) {
        Administrator administrator = (Administrator)
                request.getSession().getAttribute("administrator");
        if (administrator == null) {
            return "login";
        }
        request.setAttribute("studentId", id);
        return "studentMessage";
    }

    @RequestMapping("/teacherMessage")
    public String teacherMessage(@RequestParam Integer id, HttpServletRequest request) {
        Administrator administrator = (Administrator)
                request.getSession().getAttribute("administrator");
        if (administrator == null) {
            return "login";
        }
        request.setAttribute("teacherId", id);
        return "teacherMessage";
    }

    @RequestMapping("/allDepartmentList")
    public String allDepartmentList(HttpServletRequest request) {
        Administrator administrator = (Administrator)
                request.getSession().getAttribute("administrator");
        if (administrator == null) {
            return "login";
        }
        return "allDepartmentList";
    }

    @RequestMapping("/gradeSearch")
    public String gradeSearch(HttpServletRequest request) {
        Administrator administrator = (Administrator)
                request.getSession().getAttribute("administrator");
        if (administrator == null) {
            return "login";
        }
        return "gradeSearch";
    }

    @RequestMapping("/gradeReportList")
    public String gradeReportList(HttpServletRequest request) {
        Administrator administrator = (Administrator)
                request.getSession().getAttribute("administrator");
        if (administrator == null) {
            return "login";
        }
        return "gradeReportList";
    }
}
