
import java.awt.Robot;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import functions.Filtering;
import functions.NaverTopKeywords;
import functions.TistoryBrainDotsArticle;
import functions.TistoryClient;
import functions.WordCount;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;   

public class TwitNaverTop10 { 
    public static void main(String[] args) throws Exception {
    	
    	
    	// ������
    	//for(int i = 0; i < 130; i++) Set_TimerOn(1);
    	//
	
    	ConfigurationBuilder cb = new ConfigurationBuilder();
    	cb.setDebugEnabled(true)
    	  .setOAuthConsumerKey("P2ODm5hwh3iCE3pPU1TtFQ")
    	  .setOAuthConsumerSecret("IRrvPNwvrW8LbLubZqiyT8E3Tq9o6R9HoGRyf5g")
    	  .setOAuthAccessToken("597128010-r7YjO2GJK9Isukys2h9UXOGU1PrzFqaSobg1kCFw")
    	  .setOAuthAccessTokenSecret("yM6bBQdbNj5uYjArkPp1aTuNIdWiBdCr0TbYN9ebSGSYr");
    	TwitterFactory tf = new TwitterFactory(cb.build());
    	Twitter twitter = tf.getInstance();
    	
    	NaverTopKeywords ntk = new NaverTopKeywords();
    	
    	HashSet<String> searchlist = new HashSet<>();
    	
    	searchlist.addAll(ntk.getKeywords());
    	
    	
    	Filtering filtering = new Filtering();
    	
    	SimpleDateFormat sdf;
        long nowmillss; 
        String nows; 
	    	
	    while(true) {	
	    	/*
	        String pageUrl="http://www.naver.com"; 
	
	        //Document doc = Jsoup.connect( pageUrl ).get(); 
	        
	        Document doc = Jsoup.parse(new URL(pageUrl), 1000);
	        //Document doc = Jsoup.parse(new URL(pageUrl).openStream(),"UTF-8", pageUrl);
	        
	       
	        //System.out.println(doc.head()); 
	        String selector="#realrank li a"; // css selector  
	
	        Elements rcw = doc.select( selector ); 
	
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        long nowmills = System.currentTimeMillis(); 
	        String now = sdf.format(new Date(nowmills)); 
	        System.out.println("�ǰ˻��� ������ �ð� : "+now); 
	        ArrayList<String> searchlist = new ArrayList<String>();
	        
	        // �˻��� ����
	        for (Element el : rcw) { 
	            String id = el.parent().attr("id"); 
	            String no = el.parent().attr("value"); 
	            String newORup = el.parent().attr("class"); 
	            String title =el.attr("title"); 
	            String link =el.attr("href"); 
	            
	            
	
	            if(!id.equals("lastrank")){ 
	            	//
	            	//for(String s : title.split(" ")) {
	            		
	            	//	searchlist.add(s);
	            	//}
	            	
	            	searchlist.add(title);
	
	            	System.out.println(title);
	            } 
	            
	            
	        } 
	        // �˻��� ���� ��
	        
	        */
	        
	        System.out.println(searchlist);
	        
	        ArrayList<WordCount> words;
	        
	        // �˻������� �ϳ��� ��� for��
	        for(String searchItem : searchlist) {
		        	try {
		        	
		        	System.out.println(searchItem);
		        
		        	String str = "";
		       	 	Query query = new Query(searchItem + " exclude:hashtags");
		        	//Query query = new Query(searchItem);
		       	 	
		            query.setCount(200);	
		            query.setLang("ko");
		
		            int repeat = 10;
		
		            QueryResult result;
		
		   	         result = twitter.search(query);
		   	         
		   	         List<Status> tweets = result.getTweets();
		   	         
		   	         for(int i = 0; i < repeat; i++) {
		   	      
		   		         if((query = result.nextQuery()) == null) continue;
		   		         result = twitter.search(query);
		
		   		         tweets.addAll(result.getTweets());
		
		   	         }
		   	         //HashMap<String, Integer> AdultList = new HashMap<>();
		   	         
		   	         HashMap<String, Integer> TextDupCheckListTitle = new HashMap<>();
	   	    		
		             for (int i = 0; i < tweets.size(); i++) {
		   	         String s = new String(tweets.get(i).getText());
		   	    		
		   	    		if(filter19(s)) {
		   	    			tweets.remove(i--);
		   	    			continue;
		   	    		}
		   	    		
		   	    		//if(tweets.get(i).isRetweet() == true) continue;
		   	    		
		   	    		while(s.contains("https://t.co/")) {
	
		        			int j = s.indexOf("https://t.co/");
		        			int last_char = j + 23;
		        			if(s.length() < (j+23)) last_char = s.length();
	
		        			s = s.replace(s.substring(j, last_char), "");
	
		        			
		        		}
		   	    		String removeAlphaNumeric = removeAlphaNumeric(s);
		   	    		
		   	    		if(TextDupCheckListTitle.get(removeAlphaNumeric) != null) continue;
		   	  			TextDupCheckListTitle.put(removeAlphaNumeric, 1);
		   	  			//System.out.println(s);
		   	  			
		   	  			
		   	    		/*
		   	    		if(tweets.get(i).getURLEntities().length != 0) {
		   	    			tweets.remove(i--);
		   	    			continue;
		        		}
		        		*/
		   	    		
		   	    		/*
		   	    		String adultCheck = tweets.get(i).getText();
		   	    		
		   	    		if(NaverAdultCheck(adultCheck)) {
		   	    			tweets.remove(i--);
		   	    			continue;
		   	    		}
		   	    		*/
		   	    		/*
		   	    		for(String CheckSplit : adultCheck.split(" ")) {
		   	    			String CSRemoved = removePunctuations(CheckSplit);
		   	    			
							if(AdultList.get(CSRemoved) != null) {
		   	    				continue;
		   	    			}
		   	    			if(NaverAdultCheck(CSRemoved)) {
			   	    			tweets.remove(i--);
			   	    			break;
		   	    			}
		   	    			AdultList.put(CSRemoved, 1);
		   	    		}
		   	    		*/
		        		
		        		//System.out.println(s);
		   	  		//String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		   	       // s = s.replaceAll(match, " ");
		   	    		str+=s.replaceAll("[(]|[)]", " ") + "\n";
		            }
	
		    		
		            //for (Status tweet : tweets) {
		           	 
		            	//if(tweet.isRetweet() == true) continue;
		
		           		/*
		            	if(tweet.getURLEntities().length != 0) {
		
		           			continue;
		           		}
		           		*/
		            	
		
		            	//str+=tweet.getText() + "\n";
		
		            //}
		            
		            //Filtering filtering = new Filtering();
		            
		            Scanner scan = new Scanner(str);
		            HashMap<String, Integer> count = new HashMap<String, Integer>();
		            // �ܾ� ī��Ʈ �ϱ�
		            while (scan.hasNext()) {
		            	
		                String word = removePunctuations(scan.next());
		                word = remove_comma(word);
		                word = remove_broadcast(word);
		                if (filtering.filteringList().contains(word)) continue;
		                if (word.equals("")) continue;
		                Integer n = count.get(word);
		                count.put(word, (n == null) ? 1 : n + 1);
		            }
		            scan.close();
		            PriorityQueue<WordCount> pq = new PriorityQueue<WordCount>();
		            for (Entry<String, Integer> entry : count.entrySet()) {
		                pq.add(new WordCount(entry.getKey(), entry.getValue()));
		            }
		            words = new ArrayList<WordCount>();
		            while (!pq.isEmpty()) {
		                WordCount wc = pq.poll();
		                if (wc.word.length() > 1) words.add(wc);
		            }
		            // �ܾ�ī��Ʈ ��
		
		            
		            String tags = searchItem + ",";
		        	String title_side = "";
		        	int tag_count = 0;
		            
		        	// ī��Ʈ �̿� ���� �� �±� �����
		            for (WordCount wc : words) {
		            	
		            	boolean dup = false;
		        		if (wc.word.contains("http")) continue;
		        		if (wc.word.contains(searchItem)) continue;
		        		for(String split : searchItem.split(" ")) {
		        			if (wc.word.contains(split)) dup = true;
		        			else if (wc.word.contains(split.toLowerCase())) dup = true;
		        			else if (wc.word.contains(split.toUpperCase())) dup = true;
		        		}
		        		if (dup) continue;
		        		if(searchItem.contains(wc.word)) {
		        			continue;
		        		}
		        		if(tag_count++ < 30) tags = tags + wc.word + ",";
		        		if(tag_count < 10) title_side = title_side + wc.word + " ";
		        		else if(tag_count == 10) title_side = title_side + wc.word;
		        		
		        		
		        	}
		            // ī��Ʈ �̿� ���� �� �±� ����� ��
		            
		            ArrayList<String> PhotoList = new ArrayList<String>();
		        	
		        	String tistory_content = "";
		        	
		        	// ���� ����
		        	for (Status tweet : tweets) {
		        		
		        		String s = new String(tweet.getText());
		        		
		        		s = remove_comma(s);
		        		
		        		if(filter19(s)) continue;
		        		
		        		if(tweet.getURLEntities().length != 0) continue;
		        		
		        		if(tweet.getMediaEntities().length != 0)
		        		{
		
		        			for(MediaEntity me : tweet.getMediaEntities())
		        			if(me.getType().equals("photo")) {
		        				//System.out.println("2");
		        				if(!PhotoList.contains(me.getMediaURL())) {
		        					PhotoList.add(me.getMediaURL());
		        					
		        				}
		        			}
		        			
		
		        		}
		        		
		        		if(PhotoList.size() > 0) break;
		
		        	}
		        	// ���� ���� ��
		        	
		        	int item_num = 0; 
		    		int photo_position = 0;
		    		
		    		boolean content_check = false;
	
	    			HashMap<String, Integer> TextDupCheckList = new HashMap<>();
	    			
		    		tistory_content = tistory_content + "<div class=\"tt_article_useless_p_margin\"><table align=\"center\" style=\"color: rgb(0, 0, 0); font-family: 'Malgun Gothic'; border-collapse: collapse; width:100%;\"><tbody>";
		        	boolean AdCheck = false;
		    		for (Status tweet : tweets) {
		    			//if(tweet.isRetweet() == true) continue;
		    			if(tweet.getURLEntities().length != 0) continue;
		
		    			String s = new String(tweet.getText());
		    			
		    			while(s.contains("https://t.co/")) {
		
		        			int i = s.indexOf("https://t.co/");
		        			int last_char = i + 23;
		        			if(s.length() < (i+23)) last_char = s.length();
		
		        			s = s.replace(s.substring(i, last_char), "");
		
		        			
		        		}
		    			
		    			while(s.contains("http://t.co/")) {
		    				
		        			int i = s.indexOf("http://t.co/");
		        			int last_char = i + 23;
		        			if(s.length() < (i+23)) last_char = s.length();
		
		        			s = s.replace(s.substring(i, last_char), "");
		
		        			
		        		}
		    			
		    			while(s.contains("@")) {
		    				
		        			int i = s.indexOf("@");
		        			int last_char = i + 5;
		        			if(s.length() < (i+5)) last_char = s.length();
		
		        			s = s.replace(s.substring(i, last_char), "****");
		
		        			
		        		}
		        		
		    			s = remove_comma(s);
		    			String removeAll = removeAlphaNumeric(s);
		    			if(TextDupCheckList.get(removeAll) != null) continue;
		    			TextDupCheckList.put(removeAll, 1);
		    			
		    			content_check = true;
		    			
		    			tistory_content = tistory_content + "<tr><td width=\"60\" align=\"center\" style=\"border: 1px solid rgb(204, 204, 204); font-size: 10px; padding: 1px;\">";
		    			tistory_content = tistory_content + "<a href=\"https://twitter.com/" + (String)tweet.getUser().getScreenName() + "\" target=\"_blank\" title=\"" + tweet.getUser().getScreenName() + "\"> ";
		    			tistory_content = tistory_content + "<img src=\"" + tweet.getUser().getProfileImageURL() + "\" border=0 title=\"" + tweet.getUser().getName() + "&#13;@" + tweet.getUser().getScreenName() + "\" alt=\"" + tweet.getUser().getScreenName() + "\" onerror=\"this.src='http://abs.twimg.com/sticky/default_profile_images/default_profile_4_normal.png';\"><br>";
		  
		    			tistory_content = tistory_content + "</a></td><td style=\"border: 1px solid rgb(204, 204, 204); padding: 5px;\">";
		    			tistory_content = tistory_content + "<font title=\"" + tweet.getCreatedAt() + "\" style=\"CURSOR:hand;\">" + s + "</font>";
		    			tistory_content = tistory_content + "</td> </tr>";
		
		    			item_num++;
		    			if(item_num == 14) {
		    				AdCheck = true;
		    				tistory_content = tistory_content + "</tbody></table><table align=\"center\" style=\"color: rgb(0, 0, 0); font-family: 'Malgun Gothic'; border-collapse: collapse; width:100%;\"><tbody><tr><td style=\"text-align:center; padding:1px;\"><p style=\"text-align:center;\"><style>.twitsideAd { width: 336px; height: 280px; margin-top: 15px; margin-bottom: 15px; }@media(max-width: 768px) { .twitsideAd { width: 300px; height: 250px; margin-top: 15px; margin-bottom: 15px; } }</style><script async=\"\" src=\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script><ins class=\"adsbygoogle twitsideAd\" style=\"display:inline-block\" data-ad-client=\"ca-pub-8716569569929004\" data-ad-slot=\"9043697974\" data-ad-format=\"auto\"></ins><script>(adsbygoogle = window.adsbygoogle || []).push({});</script></p></td></tr></tbody></table><table align=\"center\" style=\"color: rgb(0, 0, 0); font-family: 'Malgun Gothic'; border-collapse: collapse; width:100%;\"><tbody>";
		    			}
		    				
		    			else if((item_num % 7) == 0){
		
		    				if(photo_position < PhotoList.size()) {
		    					
		    							tistory_content = tistory_content + "</tbody></table><p style=\"text-align: center; clear: none; float: none;\"><span class=\"imageblock\" style=\"display:inline-block;;height:auto;max-width:100%;\"><span dir=\"";
		    							
		    							tistory_content = tistory_content + PhotoList.get(photo_position).toString() + "\" rel=\"lightbox\" target=\"_blank\"><img src=\"";
		    							tistory_content = tistory_content + PhotoList.get(photo_position).toString() + "\" style=\"max-width:100%;height:auto\" alt=\"" + searchItem + " " + title_side + " " + tweet.getUser().getScreenName() + "\"></span></span></p><table align=\"center\" style=\"color: rgb(0, 0, 0); font-family: 'Malgun Gothic'; border-collapse: collapse; width:100%;\"><tbody>";
		    				
		    							photo_position++;
		    							
		
		    				}   	
		    				/*
		    				else {
		    					tistory_content = tistory_content + "</tbody></table><p style=\"text-align: center; clear: none; float: none;\"><table align=\"center\" style=\"color: rgb(0, 0, 0); font-family: 'Malgun Gothic'; border-collapse: collapse; width:100%;\"><tbody>";
		    				}
		    				*/
		    				
		    			}
			
		        	}
		    		if(!AdCheck) tistory_content = tistory_content + "</tbody></table><table align=\"center\" style=\"color: rgb(0, 0, 0); font-family: 'Malgun Gothic'; border-collapse: collapse; width:100%;\"><tbody><tr><td style=\"text-align:center; padding:1px;\"><p style=\"text-align:center;\"><br><br><br>Ads</p><p style=\"text-align:center;\"><style>.twitsideAd { width: 336px; height: 280px; margin-top: 15px; margin-bottom: 15px; }@media(max-width: 768px) { .twitsideAd { width: 300px; height: 250px; margin-top: 15px; margin-bottom: 15px; } }</style><script async=\"\" src=\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script><ins class=\"adsbygoogle twitsideAd\" style=\"display:inline-block\" data-ad-client=\"ca-pub-8716569569929004\" data-ad-slot=\"9043697974\" data-ad-format=\"auto\"></ins><script>(adsbygoogle = window.adsbygoogle || []).push({});</script></p></td></tr></tbody></table><table align=\"center\" style=\"color: rgb(0, 0, 0); font-family: 'Malgun Gothic'; border-collapse: collapse; width:100%;\"><tbody>";
		    		if(photo_position < PhotoList.size()) tistory_content = tistory_content + "</tbody></table><p><br><br><br></p><p style=\"text-align: center; clear: none; float: none;\">" + searchItem + "</p><table align=\"center\" style=\"color: rgb(0, 0, 0); font-family: 'Malgun Gothic'; border-collapse: collapse; width:100%;\"><tbody>";
		    		for(int i = 0; i < 5; i++) {
						if(photo_position < PhotoList.size()) {
		
							tistory_content = tistory_content + "</tbody></table><p style=\"text-align: center; clear: none; float: none;\"><span class=\"imageblock\" style=\"display:inline-block;;height:auto;max-width:100%;\"><span dir=\"";
							
							tistory_content = tistory_content + PhotoList.get(photo_position).toString() + "\" rel=\"lightbox\" target=\"_blank\"><img src=\"";
							tistory_content = tistory_content + PhotoList.get(photo_position).toString() + "\" style=\"max-width:100%;height:auto\" alt=\"" + searchItem + " " + title_side + "\"></span></span></p><table align=\"center\" style=\"color: rgb(0, 0, 0); font-family: 'Malgun Gothic'; border-collapse: collapse;\"><tbody>";
							
							photo_position++;
							
						}
					}
		    		
		    		
		    		tistory_content = tistory_content + "</tbody></table><p><br></p></div>";
		
					TistoryClient TC = new TistoryClient();
					TistoryBrainDotsArticle tistoryBrainDotsArticle = new TistoryBrainDotsArticle();
				
				    //tistoryBrainDotsArticle.setTitle("[" + searchItem + "] " + title_side);
					tistoryBrainDotsArticle.setTitle(searchItem + " " + title_side);
				    tistoryBrainDotsArticle.setContent(tistory_content);
				    tistoryBrainDotsArticle.setTag((String)tags);
				   // tistoryBrainDotsArticle.setStrategy(list[2]);
				    tistoryBrainDotsArticle.setVisibility("3");
				    
				    tistoryBrainDotsArticle.setCategory("622318");
			
				    if(content_check) TC.write(tistoryBrainDotsArticle);
				    
				    Set_TimerOn(1);
				    Set_TimerOn(1);
				    Set_TimerOn(1);
		
		        }
		        	
	        	 catch (TwitterException te) {
	        	       te.printStackTrace();
	        	       System.out.println("Failed to search tweets: " + te.getMessage());
	        	       //System.exit(-1);
	        	   }
		        	
		        
		         
		         
		    }
	        
	        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        nowmillss = System.currentTimeMillis(); 
	        nows = sdf.format(new Date(nowmillss)); 
	        System.out.println("�ð� : "+nows);
	        
	        
	        searchlist = new HashSet<>();
	        
	        for(int j = 0; j < 6; j++) {
	        	for(int i = 0; i < 30; i++) Set_TimerOn(1);
	        	searchlist.addAll(ntk.getKeywords());
	        	System.out.println(searchlist);
	        }
	        
	        
	    }
   
        
        
    } 
    public static String remove_comma(String s) {
    	s = s.replaceAll("@", "");
		s = s.replaceAll("#", "");
		s = s.replaceAll("��", "");
		s = s.replaceAll("��", "");
		s = s.replaceAll("��", "");
		s = s.replaceAll("��", "");
		s = s.replaceAll("��", "");
		s = s.replaceAll("��", "");
		//s = s.replaceAll("(", "");
		//s = s.replaceAll(")", "");
		//s = s.replaceAll("[", "");
		//s = s.replaceAll("]", "");
		s = s.replace('(', ' ');
		s = s.replace(')', ' ');
		s = s.replace('[', ' ');
		s = s.replace(']', ' ');
		s = s.replaceAll("RT", "");
		s = s.replaceAll("rt", "");
    	return s;
    	
    	
    }
    public static String remove_broadcast(String s) {
    	s = s.replaceAll("KBS", "");
		s = s.replaceAll("SBS", "");
		s = s.replaceAll("MBC", "");
		s = s.replaceAll("JTBC", "");
		s = s.replaceAll("tvN", "");
		
    	return s;
    	
    }
    
    public static boolean filter19(String s) {
    	boolean check = false;
    	

    		//String s = new String(tweet.getText());
    		//if(s.contains("1383")) check = true;
    	if(s.contains("�η�")) check = true;
		else if(s.contains("Ǯ�η�")) check = true;
		else if(s.contains("����Ǯ�η�")) check = true;
		else if(s.contains("����")) check = true;
		else if(s.contains("�ٵ���")) check = true;
		//if(s.contains("https://t.co/")) check = true;
		else if(s.contains("��������Ʈ")) check = true;
		else if(s.contains("�����ι���")) check = true;
		else if(s.contains("������Ŀ��")) check = true;
		else if(s.contains("���������")) check = true;
		else if(s.contains("����Ǯ�̷�")) check = true;
		else if(s.contains("��������")) check = true;
		else if(s.contains("�����̷���")) check = true;
		else if(s.contains("��������")) check = true;
		else if(s.contains("���������")) check = true;
		else if(s.contains("�缳����")) check = true;
		else if(s.contains("����������")) check = true;
		else if(s.contains("�缳�������Ʈ��õ")) check = true;
		else if(s.contains("���ӵ��ٸ�����Ʈ")) check = true;
		else if(s.contains("���ӵ��ٸ�")) check = true;
		else if(s.contains("��ٸ�����Ʈ")) check = true;
		else if(s.contains("��ٸ���������õ")) check = true;
		else if(s.contains("��ī��")) check = true;
		else if(s.contains("Ǯ���")) check = true;
		else if(s.contains("�Ҷ��")) check = true;
		else if(s.contains("ī����")) check = true;
		else if(s.contains("�ߵ�")) check = true;
		else if(s.contains("�ٵ���")) check = true;
		else if(s.contains("����")) check = true;
		else if(s.contains("��õ��")) check = true;
		else if(s.contains("����")) check = true;
		else if(s.contains("��")) check = true;
		else if(s.contains("bot")) check = true;
		else if(s.contains("�����߱���")) check = true;
		else if(s.contains("010")) check = true;
		else if(s.contains("�����ȸ�")) check = true;
		else if(s.contains("�Ｚ�ȸ�")) check = true;
		else if(s.contains("�е��ȸ�")) check = true;
		else if(s.contains("�����ȸ�")) check = true;
		else if(s.contains("����ȸ�")) check = true;
		else if(s.contains("���̵��ȸ�")) check = true;
		else if(s.contains("���빮�ȸ�")) check = true;
		else if(s.contains("���ξȸ�")) check = true;
		else if(s.contains("�ϻ�ȸ�")) check = true;
		else if(s.contains("���ʾȸ�")) check = true;
		else if(s.contains("��赿�ȸ�")) check = true;
		else if(s.contains("���ȸ�")) check = true;
		else if(s.contains("��Ǿȸ�")) check = true;
		else if(s.contains("û��ȸ�")) check = true;
		else if(s.contains("��赿�ȸ�")) check = true;
		else if(s.contains("���γ��� ��")) check = true;
		else if(s.contains("���̻���")) check = true;
		else if(s.contains("����Ӵ�")) check = true;
		else if(s.contains("�߻�")) check = true;
		else if(s.contains("�ɱ׷��ռ�")) check = true;
		else if(s.contains("�������Ǹ���")) check = true;
		else if(s.contains("�ٽú���")) check = true;
		else if(s.contains("�ֽ��ּ�")) check = true;
		else if(s.contains("����ó»")) check = true;
		
		else if(s.contains("�߸������Ʈ")) check = true;
		else if(s.contains("�ö��̰���")) check = true;
		else if(s.contains("��ũ���渶��")) check = true;
		else if(s.contains("���ͳݰ渶")) check = true;
		else if(s.contains("�Ϸ������")) check = true;
		else if(s.contains("�ķ����°�")) check = true;
		else if(s.contains("�򵿽ǽð����ھ�")) check = true;
		else if(s.contains("337����")) check = true;
		
		else if(s.contains("ȣ������")) check = true;
		else if(s.contains("337����")) check = true;
		
		else if(s.contains("�Ҿװ���")) check = true;
		else if(s.contains("��ǰ�Ǹ���")) check = true;
		else if(s.contains("��ǰ������ȭ")) check = true;
		else if(s.contains("����ϻ�ǰ������ȭ")) check = true;
		else if(s.contains("�����̿������ȭ")) check = true;
		else if(s.contains("�Ҿװ�������")) check = true;
		else if(s.contains("�������Ҿװ���")) check = true;
		else if(s.contains("������̵�")) check = true;
		
		else if(s.contains("����Į����")) check = true;
		else if(s.contains("337����")) check = true;
		
		else if(s.contains("����ä�귣��")) check = true;
		else if(s.contains("�渶�м�")) check = true;
		else if(s.contains("������")) check = true;
		else if(s.contains("��Ʈ�渶")) check = true;
		else if(s.contains("�ϳ�ĳ��Ż")) check = true;
		else if(s.contains("���̺����")) check = true;
		else if(s.contains("��ũ���渶")) check = true;
		else if(s.contains("�ֽķζ�")) check = true;
		
		else if(s.contains("ȫ�Ḷī������")) check = true;
		else if(s.contains("�����ѳ�����")) check = true;
		
		else if(s.contains("�Ϻ����̹��渶")) check = true;
		else if(s.contains("�ĺ���")) check = true;
		else if(s.contains("�缳�渶")) check = true;
		else if(s.contains("Ȧ������Ʈ")) check = true;
		else if(s.contains("��Ŀ����Ʈ")) check = true;
		
		else if(s.contains("�ǳ��渶")) check = true;
		else if(s.contains("���ͳ��Ƕ�")) check = true;
		

		else if(s.contains("����ũ��������")) check = true;
		else if(s.contains("������")) check = true;
		else if(s.contains("�Ҿװ���")) check = true;   
		else if(s.contains("�Ǹ�")) check = true;  
		else if(s.contains("����")) check = true;
		else if(s.contains("�ű԰���")) check = true;  
		else if(s.contains("chbb")) check = true;
		else if(s.contains("JAM-44")) check = true;
		else if(s.contains("��ʴ�ǥ")) check = true;
		else if(s.contains("�����ǿ�")) check = true;
		else if(s.contains("�渶")) check = true;
		else if(s.contains("����")) check = true;
		//else if(s.contains("����")) check = true;
		else if(s.contains("����")) check = true;
		else if(s.contains("sex")) check = true;
		else if(s.contains("SEX")) check = true;
		else if(s.contains("4U")) check = true;
		else if(s.contains("Sex")) check = true;
    	
		else if(s.contains("����")) check = true;
		else if(s.contains("�ù�")) check = true;
		else if(s.contains("�ù�")) check = true;
		else if(s.contains("����")) check = true;
		else if(s.contains("����")) check = true;
		else if(s.contains("KST")) check = true;
		else if(s.contains("��Ŀ")) check = true;
    	
		else { // ���� 
			String filter = "���� ��© ���θ�� ���� ������ �������� �ޱ� �븮Ƽ���� ������ ������ �������� mp3 ������ �˶��� �������� �źҳ������ ������ �ĺ��� �ǸӴ� SUK33 ������ ����TV �����ּ� to-jt ���� �������� ������ ������ī�� �����̿��� ������ �߱����ּ����� ���̽ý����� ���������� �ݿ��� �¶��ε��� ��ũ������ ��õ�ּ� �㹮ȭ ��������� Ȧ¦ ���� �������� 19�ٸ�� dominostory �߱� ���� �絵 ���� ���� ��������� ���̽�ä�� ���Ѵ�ȭ �������� �̽��� ���θ���� ��ȭ���� ������ url href www object embed script swf meta onload onclick onmouseover sex porn poker viagra cialis lama ��ī�� ī���� �ٴ��̾߱� �߸��� ���� ��Ŀ ���� ������ ���� �ſ�ҷ� ���Ǹ��� ���δ��� �������� handa10 viagra cialis drug woori10 ���ҿ��� s���̾ zot10 ���������� �������� sedek S.E.X tara2010 rana58 Partner airen69 nana58 best58 bojo69 OneLove kading �� �䷻Ʈ ���� CS olleh BSTODAY �����ܾ� â�� �����̾���� ������ xaza ���ӵ� ����� usd79 magnet �������� �Ͳ� ������ �ܵ� �������� ���� ���� poltra �ȸ� ������ �ÿ���Ű ���׷� ���������� ��ī�� ���� �д�ȸ� �����ȸ� ���ھȸ� ��ž�ȸ� �̱ݾȸ� �����ȸ� ��õ�ȸ� �����ȸ� �۳��ȸ� ����ȸ� ��õ�ȸ� �ϻ�ȸ� ���佺Ÿ�ȸ� �鼮���ȸ� ȭ���ȸ� ���þȸ� ���ο��ȸ� ���߻�ȸ� �ȸ��� �ȸ��� �ȸ��ı� �޾ȸ� �����žȸ� ���̾ȸ� ��۾ȸ� ���߾ȸ� ���ξȸ� ��Ÿ�ȸ� �ȸ��ü� ���κ���ȸ� �ް��� ��Ƽ�� �Ǹ� Ű���� O1O OIO ��Ʊ׶� Ŭ����Ÿ�� ���̺��� �ʿ����� ��â���� �����ϵ�� ��������� �����㹮ȭ ����������� �����ϵ��ھ� �����Ľ� �������� ������ŷ �������� ������ī�� ��Ÿ�� �����뷡�� �߳� ���� ���� �渶 ��Ŀ Ȧ�� ��ģ�� ��ģ�� ���� �ù� ���� 18�� 18�� 18���� 18�� 18�� 18�� 18�� 18�� ���� ���� ���� ���� ������ ������ ������ ������ ���� ���� ���� ���� ��8 ���� ���� ���� ���� ������ ���ζ� ���η� ���η� ���θ� ���� ����� ���� ���� ���Ƕ� ���� ���� ���� �� �ư��� �ư��� ��â ���� ��� ��� ���ֱ� ���� ���� ������ ������ ������ ������ ���� ���� ���� ���� �� �� �� ��� ��� ��� ���� ���� ���� ���̶� �λ� �� ��ť ��ť ��ť �������� ����Ű ���ڽ� ���� �Ի��� �Ի��� ���� �� ���� ���̷� �ϱ͹� �ϱ�� �Ϲ� ���� ������ ������ ������ ������ ������ ������ ���� ���� ��ť ��ť ��ť �߸��� ���� ���� ���� ���� ���� ����ŷ ���� �ù� �úζ� �úη� �úθ� �ú� �ú�� ���� ���� ���� �ǹ� ��8 �ʽ� ��â �;� �Գ� ��� �س� �߾� �� ��� ������ �겥 �꿢 ���� ���� �ù� ���� �ù�";
			for(String filterSplit : filter.split(" ")) {
				if(s.equals("")) continue;
				if(s.contains(filterSplit)) {
					check = true;
					break;
				}
			}
		}
    
    		
    		//if(s.contains("��������Ʈ")) check = true;
    		//if(s.contains("��������Ʈ")) check = true;
    		
    		//if(s.contains("MAMAredcarpet")) check = true;
    		//if(s.contains("MAMAredcarpert")) check = true;
    			
    	return check;
    }
    

    private static String removePunctuations(String str) {
        //return str.replaceAll("\\p{Punct}|\\p{Digit}", "");
    	String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        str = str.replaceAll(match, "");
        //return str.replaceAll("\\p{Punct}", ""); //\p{ASCII}
        String s = str.replaceAll("\\p{Punct}|\\p{ASCII}", "");
        if(!s.equals("")) return str.replaceAll("\\p{Punct}", "");
        else return s;
        
    }
    
    private static String removeAlphaNumeric(String str) {
        //return str.replaceAll("\\p{Punct}|\\p{Digit}", "");
    	//String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        //str = str.replaceAll(match, "");
        //return str.replaceAll("\\p{Punct}", ""); //\p{ASCII}
        return str.replaceAll("\\p{Alnum}|\\p{Blank}|\\p{Punct}", "");
        
    }
    
    public static void Set_TimerOn(int nTimer)  throws Exception  // nTimer - ���� : ��
    {
         int nDelayTime;
         nDelayTime = nTimer * 1000 * 60; // �и��� ������ �µ��� *1000�� ���ش�.
        

         Robot tRobot = new Robot();
        tRobot.delay(nDelayTime);   // delay() �Լ��� �̿��Ͽ� nDelayTime �и��� ���� ���μ����� sleep ���·� �����.
   }
    

    public static boolean NaverAdultCheck(String keyword) {
    	
		try {
			
			
			String pageUrl="http://openapi.naver.com/search?key=a3f9dcd4651edc8c8ba95da143269e6a&target=adult&query="+keyword; 
			Document doc;
			
	        //doc = Jsoup.connect( pageUrl ).get(); 
	    	
			doc = Jsoup.parse(new URL(pageUrl).openStream(),"UTF-8", pageUrl);
			// Document doc = Jsoup.parse(
	        //Document doc = Jsoup.parse(new URL(pageUrl).openStream(),"UTF-8", pageUrl);
	        
	       
	        //System.out.println(doc.head()); 
	        String selector="result > item > adult"; // css selector  

	        Elements rcw = doc.select( selector ); 
	        String check = "";
	        // �˻��� ����
	        for (Element el : rcw) { 
	            check = el.text(); 
	            System.out.print(check);
	        }
	        if(check.equals("1")) {
	        	System.out.println("adult �˻��� : " + check);
	        	return true;
	        }
			return false;
		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException �˻� : " + keyword);
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException �˻� : " + keyword);
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return false;
       
    	
    }
}