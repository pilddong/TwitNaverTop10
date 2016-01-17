package functions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverTopKeywords {
	String pageUrl="http://www.naver.com"; 
	
    //Document doc = Jsoup.connect( pageUrl ).get(); 
    
    Document doc;
    //Document doc = Jsoup.parse(new URL(pageUrl).openStream(),"UTF-8", pageUrl);
    
    HashSet<String> searchlist;
    
    public HashSet<String> getKeywords() {
    	try {
			doc = Jsoup.parse(new URL(pageUrl), 1000);
			
			//System.out.println(doc.head()); 
	        String selector="#realrank li a"; // css selector  

	        Elements rcw = doc.select( selector ); 

	        /*
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        long nowmills = System.currentTimeMillis(); 
	        String now = sdf.format(new Date(nowmills)); 
	        System.out.println("실검색어 가져온 시간 : "+now); 
	        */
	        
	        searchlist = new HashSet<String>();
	        
	        
	        // 검색어 추출
	        for (Element el : rcw) { 
	            String id = el.parent().attr("id"); 
	            String no = el.parent().attr("value"); 
	            String newORup = el.parent().attr("class"); 
	            String title =el.attr("title"); 
	            String link =el.attr("href"); 
	            
	            

	            if(!id.equals("lastrank")){ 
	            	/*
	            	for(String s : title.split(" ")) {
	            		
	            		searchlist.add(s);
	            	}
	            	*/
	            	searchlist.add(title);

	            	//System.out.println(title);
	            } 
	            
	            
	        } 
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return searchlist;
    	
    	
    }

}
