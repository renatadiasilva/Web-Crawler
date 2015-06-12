package pt.uc.dei.aor.paj;

import java.util.Date;

public class RunWebCrawler {
	
	public static void main(String[] args) throws Exception {
		
		//chamar o parser -> dá origem ao XML
		
		//enviar XML (como String) transformações?
		//passar xml ou a string como argumento
		Publisher p = new Publisher();
		p.publish();  // p.publish(stringXML);
		
		int[] count = {12, 20, 40, 7, 10, 33, 14};
		//enviar o texto da estatística como argumento
		String stats = "Statistics "+new Date()+"\n";
		stats += "Americas -"+count[0];
		stats += "Africa -"+count[1];
		stats += "Asia -"+count[2];
		stats += "China -"+count[3];
		stats += "Europe -"+count[4];
		stats += "Middle East -"+count[5];
		stats += "U.S. -"+count[6];
		
//		Publisher p = new Publisher();  //é necessário?
//		p.publish(stats);
		
		//OK
	}
}