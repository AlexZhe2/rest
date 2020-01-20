package com.example.rest.parser;

import com.example.rest.entity.Course;
import com.example.rest.repository.CourseRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
public class ParserThread implements Runnable {
    private String link;
    private CourseRepository courseRepository;

    @Autowired
    public ParserThread(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public void run() {
//        try {
//            Thread.sleep((long) Math.random());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try {
            Document document = Jsoup.connect(link).get();
            System.out.println(Thread.currentThread().getName() +
                    " : " + link);
//            private String title;
//            private String price;
//            private String duration
            Course course = new Course();

            String title = document.title();

            course.setTitle(title);

            Elements dot__title = document.select(".dot__title");

            for (Element element: dot__title) {
                if ("Длительность".equals(element.text())) {
                    course.setDuration(element.nextElementSibling().text());
                }
                if ("Стоимость".equals(element.text())) {
                    course.setPrice(element.nextElementSibling().text());
                }
            }

            System.out.println(course);
            courseRepository.save(course);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
