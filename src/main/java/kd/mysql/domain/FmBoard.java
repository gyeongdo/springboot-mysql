package kd.mysql.domain;


import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class FmBoard {

    @Id
    @GeneratedValue
    private Long id;

    private Long boardNo;

    private String title;

    private String content;

    private String url;

    private LocalDateTime createdDate;

    private String time;



}