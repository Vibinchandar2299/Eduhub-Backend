
package com.example.eduhub_backend.course;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;
import java.util.ArrayList;

import com.example.eduhub_backend.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/course")
public class CourseController {

    static List<Course> courseList = new ArrayList<>();

    static {
        courseList.add(new Course("CS101", "Programming Fundamentals", 4));
        courseList.add(new Course("CS102", "Data Structures", 4));
        courseList.add(new Course("CS201", "Database Systems", 3));
        courseList.add(new Course("CS202", "Operating Systems", 4));
        courseList.add(new Course("CS301", "Software Engineering", 3));
    }
     @GetMapping("/get-courses")
    public ResponseEntity<List<Course>> getCourses() {
        return ResponseEntity.ok(courseList);
}  
    @GetMapping("/get-course/{code}")
	public ResponseEntity<Course>getCourse(@PathVariable String code) {
          return courseList.stream()
				.filter(course -> course.getCourseCode().equals(code))
				.findFirst()
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	@GetMapping("/search/get-course")
	public ResponseEntity<Course> searchCourse(@RequestParam String code) {
		return courseList.stream()
				.filter(course -> course.getCourseCode().equals(code))
				.findFirst()
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	@PostMapping("/create")
	public ResponseEntity<Course> createCourse(@RequestBody Course newCourse) {
		courseList.add(newCourse);
		return ResponseEntity.ok(newCourse);
	}
	@PutMapping("/update/{code}")
	public ResponseEntity<Course> updateCourse(@PathVariable String code, @RequestBody Course updateCourse) {
      Course course = courseList.stream()
				.filter(c -> c.getCourseCode().equalsIgnoreCase(code))
				.findFirst()
				.orElseThrow(()-> new ResourceNotFoundException("Course","Course code",code));
				
				course.setSubjectName(updateCourse.getSubjectName());
				course.setCredits(updateCourse.getCredits());
		return null;
	}
	@DeleteMapping("/delete-course/{code}")
	public ResponseEntity<String> deleteCourse(@PathVariable String code) {
		Course course = courseList.stream()
				.filter(c -> c.getCourseCode().equalsIgnoreCase(code))
				.findFirst()
				.orElseThrow(()-> new ResourceNotFoundException("Course","Course code",code));
		courseList.remove(course);
		    return ResponseEntity.ok("Course deleted successfully");
	}
}
