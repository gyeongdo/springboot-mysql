package kd.mysql;

import kd.mysql.schedualer.FmCrowling;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
public class ApiController {

    @Autowired
    private ApiDao apiDao;

    @Autowired
    FmCrowling fmCrowling;

    @GetMapping(path = "/fmGetList")
    public String fmGetList() throws IOException {

        fmCrowling.getFmList();

        return String.format("%s %s", apiDao.selectName(), LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @GetMapping(path = "/fmGetListDetail")
    public String fmGetListDetail() throws IOException {

        fmCrowling.getFmListDetail();

        return String.format("%s %s", apiDao.selectName(), LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }





}