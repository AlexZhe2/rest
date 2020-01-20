package com.example.rest.parser;

import com.example.rest.config.ParserConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@EnableScheduling
@Component
public class Parser {
    private ParserConfig config;
    private TaskExecutor executor;
    private ApplicationContext context;

    @Autowired
    public Parser(ParserConfig config,
                  @Qualifier("executor") TaskExecutor executor,
                  ApplicationContext context) {
        this.config = config;
        this.executor = executor;
        this.context = context;
    }
    @Scheduled(fixedRate = 60000)
    public void start(){
        System.out.println("Start");
//        for (int i = 0; i < 10; i++) {
//            ParserThread thread = context.getBean(ParserThread.class);
//            thread.setLink(config.getLink());
//            executor.execute(thread);
//        }
//        Jsoup
//        Document
//        Element
        try {
            Document document = Jsoup.connect(config.getLink()).get();
            Elements elements = document.select(".course-item a"); // по css селектору

            for (Element element: elements){
                ParserThread thread = context.getBean(ParserThread.class);
                thread.setLink(element.absUrl("href"));
                executor.execute(thread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
