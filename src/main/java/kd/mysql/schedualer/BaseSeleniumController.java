package kd.mysql.schedualer;


import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class BaseSeleniumController {

    public static final String IMAGE_DESTINATION_FOLDER = "/Users/veneziar/Downloads/dev/gd-restapi/src/main/resources/static/images/";

    public static final String SELENIUM_CHROMEDRIVER = "/Users/veneziar/Downloads/dev/gd-restapi/src/main/resources/selenium/chromedriver_mac64";

    public static final String API_PATH = "/api";

//    @Autowired
//    private SlackSenderManager slackSenderManager;

    //12:51 1/8 7:23

    public void setCookie(String url, WebDriver driver){

        System.setProperty("webdriver.chrome.driver", SELENIUM_CHROMEDRIVER);
        ChromeOptions chromeOptions = new ChromeOptions();

        // 쿠키 꺼내기 & 추가
        Cookie readed_document = driver.manage().getCookieNamed("idntm5");
        readed_document.getExpiry();

        Cookie ck = new Cookie.Builder("notice_layer","humor_")
//                .domain("www.fmkorea.com")
                .expiresOn(readed_document.getExpiry())
                .isSecure(false)
                .build();
        driver.manage().addCookie(ck);
    }

    // 슬랙에 성공 메시지 보내기
//    public void sendSlackSuccess(String url){
//        LocalDateTime date = LocalDateTime.now();
//        SlackMessageDto.Basic dto = null;
//        dto = dto.builder()
//                .text( " ~ success : " + date )
//                .build();
//
//        System.out.println("dto : " + dto);
//        slackSenderManager.send(SlackTarget.CH_BOT, dto);
//    }



}
