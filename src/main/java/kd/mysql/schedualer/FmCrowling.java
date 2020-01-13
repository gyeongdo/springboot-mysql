package kd.mysql.schedualer;

import kd.mysql.ApiDao;
import kd.mysql.domain.FmBoard;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FmCrowling {

    @Autowired
    ApiDao apiDao;

    public void getFmList() throws IOException {

        String serachParam = "무한도전";
        String url = "https://www.fmkorea.com/?vid=&mid=best&category=&listStyle=webzine&search_keyword="+ serachParam +"&search_target=title";

        Document doc = null;
        HashMap<String, String> headers = new HashMap();
        List<String> html_tag = new ArrayList<>();
        List<String> img_tag_arr = new ArrayList<>();

        Connection.Response response = Jsoup.connect(url)
                .headers(headers)
                .timeout(20000)
                .execute();

        if (response.statusCode() == 200) {
            doc = response.parse();
        }

        // 총 페이지 읽어오기
        String totalPageStr = doc.select("div.bd_go_page.tg_cnt2.wrp").text().replaceAll("[^0-9]", "");
        int totalPage = Integer.parseInt(totalPageStr);

        if( totalPage != 0){
            for(int i = 1; i <= totalPage; i++){
                url = url + "&page=" +i;
                pagingCrowling(url);
            }
        }
    }

    public void pagingCrowling(String url) throws IOException {

        Document doc = null;
        HashMap<String, String> headers = new HashMap();
        List<String> html_tag = new ArrayList<>();
        List<String> img_tag_arr = new ArrayList<>();

        Connection.Response response = Jsoup.connect(url)
                .headers(headers)
                .timeout(20000)
                .execute();

        if (response.statusCode() == 200) {
            doc = response.parse();
        }

        // 총 페이지 읽어오기
        String totalPageStr = doc.select("div.bd_go_page.tg_cnt2.wrp").text().replaceAll("[^0-9]", "");
        int totalPage = Integer.parseInt(totalPageStr);



        Elements tr = doc.select("div.fm_best_widget._bd_pc ul");
        long no = 1;
        for( Element td : tr.select("li")){
            FmBoard fmBoard = new FmBoard();
            if(!td.select("div a").attr("href").equals("") ){
                fmBoard.setUrl(("https://www.fmkorea.com" + td.select("div a").attr("href")));
                fmBoard.setBoardNo(no);
                no++;
                apiDao.save(fmBoard);
            }
        }

        doc = null;
        headers = null;
        response = null;

    }

    public void getFmListDetail() throws IOException, InterruptedException {

        List<FmBoard> fmBoards = apiDao.fmBoards();

        for(int i=403; i<800; i++){
            if( fmBoards.get(i).getContent() == null ){
//                if( !fmBoards.get(i).getContent().equals("") || fmBoards.get(i).getContent() != null ){
                    Thread.sleep(40000);
                    pagingCrowlingDetail(fmBoards.get(i));
//                }
            }
        }
    }

    public void pagingCrowlingDetail(FmBoard fmBoard) throws IOException {

        Document doc = null;
        HashMap<String, String> headers = new HashMap();

        if( !fmBoard.getUrl().equals("") ){
//            System.setProperty("http.proxyHost", "125.141.219.117");
//            System.setProperty("http.proxyPort", "80");
            Connection.Response response = Jsoup.connect(fmBoard.getUrl())
                    .headers(headers)
                    .timeout(200000)
                    .execute();

            if (response.statusCode() == 200) {
                doc = response.parse();
            }

            // 총 페이지 읽어오기
            String tr = doc.select("div.xe_content").toString();
            String title = doc.select("span.np_18px_span").text();
            LocalDateTime now = LocalDateTime.now();
            fmBoard.setContent(tr);
            fmBoard.setTitle(title);
            fmBoard.setCreatedDate(now);
            apiDao.saveContent(fmBoard);


            doc = null;
            headers = null;
            response = null;
        }
    }


    public void getTest() throws IOException, InterruptedException {

        Document doc = null;
        HashMap<String, String> headers = new HashMap();


        Connection.Response response = Jsoup.connect("https://www.fmkorea.com/index.php?mid=best&listStyle=webzine&document_srl=2380553204&search_keyword=%EB%AC%B4%ED%95%9C%EB%8F%84%EC%A0%84&search_target=title&page=3#comment_")
                .headers(headers)
                .timeout(200000)
                .execute();

        if (response.statusCode() == 200) {
            doc = response.parse();
        }
        FmBoard fmBoard = new FmBoard();

        String tr = doc.select("div.xe_content").toString();
        String title = doc.select("span.np_18px_span").text();

        LocalDateTime now = LocalDateTime.now();
        fmBoard.setContent(tr);
        fmBoard.setTitle(title);

        apiDao.saveContent(fmBoard);


        doc = null;
        headers = null;
        response = null;

    }


}
