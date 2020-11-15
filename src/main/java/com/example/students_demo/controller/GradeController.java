package com.example.students_demo.controller;


import com.example.students_demo.model.Grade;
import com.example.students_demo.model.GradeSubject;
import com.example.students_demo.model.Student;
import com.example.students_demo.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/grade")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    //############## FORM ##############\\
    @GetMapping("/form")
    public String getGradeForm(Model model, @RequestParam("studentId") Long studentId){
        model.addAttribute("addedGrade", new Grade());
        model.addAttribute("allSubjects", GradeSubject.values());
        model.addAttribute("studentId", studentId);
        return "grade_form";
    }
    @PostMapping("")
    public String submitFormData(Grade grade, @RequestParam("studentIdParam") Long studentId){
        Optional<Student> studentOptional = gradeService.findById(studentId);
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();
            grade.setStudent(student);
            gradeService.save(grade);
        }
        return "redirect:/grade/list";
    }


    //############## DELETE ##############\\
    @GetMapping("/{id}")
    public String deleteGrade(@RequestParam("id") Long gradeId) {
        Optional<Grade> gradeOptional = gradeService.findGradeById(gradeId);
        if (gradeOptional.isPresent()) {
            Grade g = gradeOptional.get();
            gradeService.deleteById(gradeId);
            return "redirect:/student/" + g.getStudent().getId();
        }
        return "redirect:/student";
    }

    //############## GET ##############\\


    //############## LIST = STUDENT DETAILS = DONE ##############\\
}