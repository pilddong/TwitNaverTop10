package functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
/**
 * @see "http://www.tistory.com/guide/api/post"
 * API 二쇱냼http://twitiwt.tistory.com/api ID1955909�몄쬆�ㅼ쭏臾명븯湲캞ZDHYXG7
 */
public class TistoryClient {
    private static final String ACCESS_TOKEN = "0a32bc282c3d7b45380a599dc8260d89_fcba3b2fb06751d5782bf2c59645d96d";

    private static final String CLIENT_ID = "552841ab0fef581d89d5d79282216340";
    private static final String SECRET_KEY = "552841ab0fef581d89d5d7928221634091bc68e95c3b22a5a6fcc70ec0708693adf15709";
    private static final String REDIRECT_URI = "http://localhost:8080/TwitterMiningEngine/result.jsp";

    private static final String TARGET_URL = "twitiwt";
    private static final String WRITE_API_URL = "https://www.tistory.com/apis/post/write";
    private static final String MODIFY_API_URL = "https://www.tistory.com/apis/post/modify";
    private static final String CATEGORY_LIST_API_URL = "https://www.tistory.com/apis/category/list";

    // 而ㅽ뵾 諛�뼱二쇨린
    private static final String DONATE = "<iframe src=\"http://gift.blog.daum.net/widget?entryId=0&amp;setNo=2043\" width=\"100%\" height=\"250\" frameborder=\"0\" border=\"0\" scrolling=\"no\" allowtransparency=\"true\" ;=\"\"></iframe>";

    private static final String OUTPUT = "json";

    private static final String GAME_CATEGORY = "584194";

    private static final String USER_AGENT = "Mozilla/5.0";

    public void getAccessToken() {
        String clientId = "552841ab0fef581d89d5d79282216340";
        String clientSecret = "552841ab0fef581d89d5d7928221634091bc68e95c3b22a5a6fcc70ec0708693adf15709";
        String redirectUri = "http://168.131.153.174:8080/TwitterMiningEngine/result.jsp";
        String grantType = "authorization_code";

//        String requestUrl = "https://www.tistory.com/oauth/access_token/?code=" + authorization_code +
//                "&client_id=" + CLIENT_ID +
//                "&client_secret=" + SECRET_KEY +
//                "&redirect_uri=" + REDIRECT_URI +
//                "&grant_type=" + grantType;
    }
    /**
     * write tistory article
     * @param tistoryBrainDotsArticle
     */
    public void write(TistoryBrainDotsArticle tistoryBrainDotsArticle) throws IOException {
        // common validation check
        checkCommonValidation(tistoryBrainDotsArticle);

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(WRITE_API_URL);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        // String title = "釉뚮젅���꾪듃 �ㅽ뀒�댁� " + tistoryBrainDotsArticle.getStage() + " �대━���숈쁺��;
        String title = tistoryBrainDotsArticle.getTitle();


        String content = tistoryBrainDotsArticle.getContent();
        String category = tistoryBrainDotsArticle.getCategory();
        //        "<h2>釉뚮젅���꾪듃 �ㅽ뀒�댁� " + tistoryBrainDotsArticle.getTitle() + " �대━��/h2>" +
        //                "<div>" + tistoryBrainDotsArticle.getYoutube() + "</div>\n" +
        //                "<div>" + tistoryBrainDotsArticle.getStrategy() + "</div>\n" +
        //                "<div>" + DONATE + "</div>\n";

        //Set<String> tagSets = getBrainDotsTags();

        //tagSets.add(tistoryBrainDotsArticle.getTitle());

        //String tags = Joiner.on(",").join(tagSets);
        String tags = tistoryBrainDotsArticle.getTag();

        List urlParameters = Lists.newArrayList();
        urlParameters.add(new BasicNameValuePair("access_token", ACCESS_TOKEN));
        urlParameters.add(new BasicNameValuePair("targetUrl", TARGET_URL)); // �곗뒪�좊━ 二쇱냼. http://xxx.tistory.com �쇨꼍��xxx 留��낅젰, 2李⑤룄硫붿씤��寃쎌슦 http://�쒓굅��url �낅젰
        urlParameters.add(new BasicNameValuePair("output", OUTPUT));    // output type

        urlParameters.add(new BasicNameValuePair("title", title));  // �쒕ぉ
        urlParameters.add(new BasicNameValuePair("content", content));  // �댁슜
        urlParameters.add(new BasicNameValuePair("category", category));   // 移댄뀒怨좊━
        urlParameters.add(new BasicNameValuePair("tag", tags)); // �쒓렇


        if (tistoryBrainDotsArticle.getVisibility() != null) {
            urlParameters.add(new BasicNameValuePair("visibility", tistoryBrainDotsArticle.getVisibility()));
            System.out.println(tistoryBrainDotsArticle.getVisibility());
        }

        post.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));

        HttpResponse response = client.execute(post);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
    }

    /*
    public void modify(TistoryBrainDotsArticle tistoryBrainDotsArticle) throws Exception {
        // validation check for modify
        if (tistoryBrainDotsArticle.getPostId() == null) {
            throw new RuntimeException("postId needed");
        }

        // common validation check
        checkCommonValidation(tistoryBrainDotsArticle);


        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(MODIFY_API_URL);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        String title = "釉뚮젅���꾪듃 �ㅽ뀒�댁� " + tistoryBrainDotsArticle.getStage() + " �대━���숈쁺��;

       String content =
                "<h2>釉뚮젅���꾪듃 �ㅽ뀒�댁� " + tistoryBrainDotsArticle.getStage() + " �대━��/h2>" +
                "<div>" + tistoryBrainDotsArticle.getYoutube() + "</div>" +
                "<div>" + tistoryBrainDotsArticle.getStrategy() + "</div>" +
                "<div>" + DONATE + "</div>\n";

        Set<String> tagSets = getBrainDotsTags();

        tagSets.add(tistoryBrainDotsArticle.getStage());

        String tags = Joiner.on(",").join(tagSets);

        List urlParameters = Lists.newArrayList();
        urlParameters.add(new BasicNameValuePair("access_token", ACCESS_TOKEN));
        urlParameters.add(new BasicNameValuePair("targetUrl", TARGET_URL)); //
        urlParameters.add(new BasicNameValuePair("title", title));  // �쒕ぉ
        urlParameters.add(new BasicNameValuePair("content", content));  // �댁슜
        urlParameters.add(new BasicNameValuePair("category", GAME_CATEGORY));   // 移댄뀒怨좊━
        urlParameters.add(new BasicNameValuePair("tag", tags)); // �쒓렇
        urlParameters.add(new BasicNameValuePair("output", OUTPUT));    // output type

        urlParameters.add(new BasicNameValuePair("postId", tistoryBrainDotsArticle.getPostId()));   // only modify

        post.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));

        HttpResponse response = client.execute(post);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
    }
    */

    protected void checkCommonValidation(TistoryBrainDotsArticle tistoryBrainDotsArticle) {
       /* if (tistoryBrainDotsArticle.getYoutube() == null) {
            throw new RuntimeException("youtube needed");
        }

        if (tistoryBrainDotsArticle.getStage() == null) {
            throw new RuntimeException("stage needed");
        }

        if (tistoryBrainDotsArticle.getStrategy() == null) {
            throw new RuntimeException("strategy needed");
        }
        */
        
        if (tistoryBrainDotsArticle.getTitle() == null) {
            throw new RuntimeException("title needed");
        }
        if (tistoryBrainDotsArticle.getContent() == null) {
            throw new RuntimeException("content needed");
        }
    }


    /**
     * brain dots tags
     * @return tags set
     */
    private Set<String> getBrainDotsTags() {
        Set<String> tagSets = Sets.newHashSet();
        tagSets.add("釉뚮젅�몃룄��");
        tagSets.add("braindots");
        tagSets.add("brain dots");
        tagSets.add("寃뚯엫");
        tagSets.add("game");
        tagSets.add("�쇱쫹");
        tagSets.add("puzzle");
        tagSets.add("怨듬왂");
        tagSets.add("strategy");
        tagSets.add("�대━��");
        tagSets.add("clear");
        tagSets.add("癒몃━");
        tagSets.add("brain");

        return tagSets;
    }

    /**
     * get category list
     * @throws IOException
     */
    public void categoryList() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        String output = "json";
        HttpGet request = new HttpGet(CATEGORY_LIST_API_URL + "?access_token=" + ACCESS_TOKEN + "&targetUrl=" + TARGET_URL + "&output=" + output);

        // add header
        request.setHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
    }
}