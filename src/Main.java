import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;


public class Main {

	public String addCharactersAz(String characterKey,Map<String, String> azCharacters,String ied) {
		return ied.replaceAll(characterKey, azCharacters.get(characterKey));
	}
	
	public String clearString(String str) {
		return str.replaceAll("    ", "").replaceAll("  ", "");
	}
	
	public  Set<String> findCharactersAz(String ied,Map<String, String> azCharacters) {
		
		Set<String>keys=azCharacters.keySet();
		Set<String>findedKeys=new HashSet<>();
		
		for (String key : keys) {
			if(ied.indexOf(key)!=-1) {
				findedKeys.add(key);
			}
		}
		return findedKeys;
		
	}
	public void addToIndeksler(String rayon,String kend,String code) {
		
	}
	public String createLink(int i) {
		i+=78;
		if(i==86)i++;
		if(i==102)i++;
		String link="http://www.azerpost.az/?options=content&id=";
		return link.concat(String.valueOf(i));
	}
	
	public static void main(String[] args) throws Exception {
		
		Main m=new Main();
		//create az characters
		Map<String,String> azCharacters=new HashMap<String, String>();
		azCharacters.put("&#601;", 	"ə");
		azCharacters.put("&#399;",	"Ə");
		azCharacters.put("&#305;" ,"i");
		azCharacters.put("&#287;",	"ğ");
		azCharacters.put("&Ccedil;","Ç");
		azCharacters.put("&ccedil;", "ç");
		azCharacters.put("&#304;",	"İ");
		azCharacters.put("&#351;", "ş");
		azCharacters.put("&uuml;", "ü");
		azCharacters.put("&ouml;", "ö");
		azCharacters.put("&#350;", "Ş");
		azCharacters.put("&Uuml;", "Ü");
		azCharacters.put("&Ouml;", "Ö");
		
		FileWriter fileWriter=null;
		
		
		
		try {
			fileWriter=new FileWriter("C:\\Users\\hayda\\eclipse-workspace\\GetPostCodes - forgithub\\indeksler.json");
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
		//String link="http://www.azerpost.az/?options=content&id=78";
		String[] rayonlar = {"Ağdam","Ağdaş","Ağcabədi","Bərdə","Balakən","Beyləqan","Biləsuvar","Oğuz","Qazax","",
							 "Qax","Qobustan","Qəbələ","Quba","Qusar","Qubadli","Dəvəçi","Daşkəsən","Zərdab","Zaqatala",
							 "Zəngilan","Əli-Bayramli","Yevlax","Yardimli","","İsmayilli","İmişli","Kürdəmir","Kəlbəcər",
							 "Gəncə","Gədəbəy","Göyçay","Goranboy","Lənkəran","Lerik", "Laçin","Masalli","Mingəçevir","Naftalan",
							 "Neftçala","Saatli","Sabirabad","Salyan","Samux","Siyəzən","Ucar","Füzuli","Xaçmaz","Xanlar","Xocali",
							 "Xocavənd","Xankəndi","Tovuz","TərTər","Haciqabul","Cəlilabad","Cəbrayil","Şamaxi","Şəmkir","Şəki","Xizi",
							 "Şuşa","Digah Şilavar","İsmayilbəyli"};
		
		for(int i=0;i<rayonlar.length;i++) {
			Connection conn=new Connection();
			conn.createConnection(m.createLink(i));
			BufferedReader reader=conn.getStream();
			String line=null;
			String ied=null;
			String code=null;
			try {
				while((line=reader.readLine()) != null) {
					
					if(ied!=null && code!=null) {
						ied=null;
						code=null;
					}
					if(line.contains(" <td style=\"text-align: left;\">&nbsp;&nbsp;&nbsp;")) {
						ied=line;
						ied=ied.replace(" <td style=\"text-align: left;\">&nbsp;&nbsp;&nbsp;", "")
									.replace(" P&#350;</td>","" ).replace("</td>", "");
						Set<String>findedKeys=m.findCharactersAz(ied, azCharacters);	
						for (String key : findedKeys) {
							ied=m.addCharactersAz(key, azCharacters, ied);
						}
						ied=m.clearString(ied);
						
						System.out.println(ied);
						
					}
					if(line.contains(" <td style=\"text-align: center;\">")) {
						code=line;
						code=code.replace("<td style=\"text-align: center;\">", "").replace("</td>", "");
						code=m.clearString(code);
						
						System.out.println(code);
					}
					if(ied!=null && code!=null) {
						JSONObject jobject=new JSONObject();
						jobject.put("indeks", code);
						jobject.put("adres",ied);
						jobject.put("rayon",rayonlar[i]);			
						fileWriter.write(jobject.toString());
						fileWriter.flush();
						fileWriter.write(",\n");
						fileWriter.flush();
						
					}
				}
			} catch (IOException e) {
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
}