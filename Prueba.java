





import java.io.FileOutputStream;



import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.util.Timer;
import java.util.TimerTask;



public class Prueba {

	
	
	 public static void main(String args[]) {
	    
		 
		 TimerTask timerTask = new TimerTask() {
		 
		 
			 public void run() {
		 
				 try
				 {
					 Document doc = Jsoup.connect("http://www.blocket.se/bostad/uthyres/stockholm?q=&sort=&ss=&se=&ros=3&roe=5&mre=14&is=1&l=0&md=th").get();
		                             
					 Element media = doc.select("div[itemtype=http://schema.org/Offer").first();
		 
		            
					 FileInputStream fstream = new FileInputStream("/home/paco/Escritorio/paquitotexto.txt");
			            // Creamos el objeto de entrada
			            DataInputStream entrada = new DataInputStream(fstream);
			            // Creamos el Buffer de Lectura
			            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
			            String strLinea=buffer.readLine();
			            entrada.close();
					 
					 
					 
		 			if (!media.attr("id").equals(strLinea)){
		          
		 				 Properties props = new Properties();
						 props.setProperty("mail.smtp.host", "smtp.gmail.com");
						 props.setProperty("mail.smtp.starttls.enable", "true");
						 props.setProperty("mail.smtp.port", "587");
						 props.setProperty("mail.smtp.user", "fgomezruti@gmail.com");
						 props.setProperty("mail.smtp.auth", "true");

						 // Preparamos la sesion
						 Session session = Session.getDefaultInstance(props);

						 // Construimos el mensaje
						 MimeMessage message = new MimeMessage(session);
						 message.setFrom(new InternetAddress("fatimagomezruti@gmail.com"));
						 message.addRecipient(Message.RecipientType.TO,new InternetAddress("fgomezruti@gmail.com"));message.setSubject("Nueva oferta de pisos");
						 
						 //Selección
						 Element fecha=media.getElementsByClass("list_date").first();
						 Element numrooms=media.getElementsByClass("li_detail_params").get(0);
						 Element price=media.getElementsByClass("li_detail_params").get(1);
						 Element size=media.getElementsByClass("li_detail_params").get(2);
						 
						 Element link = media.select("a").first();
						 String linkHref = link.attr("href");
						 
						 
						 //Segundo link
						 Document docu2 = Jsoup.connect(linkHref).get();
						 Element sec=docu2.select("div.body").first();
						 Element calle = docu2.select("meta[itemprop=streetAddress").first();
						 Element zona = docu2.select("meta[itemprop=addressLocality").first();
						 
						 Elements fotox=docu2.select("li.thumb_element");
						 Elements tlf=docu2.select("span#phonenumber_link");
						 
						 //TRY
						 
						 //System.out.println(linkHref);
						 
						 if ((fotox.isEmpty())&&(tlf.isEmpty())){
							 message.setText(
									 	"Enlace: "+linkHref+"\nId del anuncio: "+media.attr("id")+"\n\nHora de publicacion: "+fecha.ownText()+"\n\nNumero de dormitorios: "+numrooms.ownText()+"\n\nPrecio: "+price.ownText()+"\n\nTamaño: "+size.ownText()+"\n\nDescripción: \n"+sec.ownText()+"\n\nCalle: "+calle.attr("content")+"\n\nZona: "+zona.attr("content")+"\n\n\n\nNO HAY FOTOS\n\n\n\nCONTACTO SÓLO EMAIL");
						 }
						 else if((fotox.isEmpty())&&(!(tlf.isEmpty()))){
							 message.setText(
									 	"Enlace: "+linkHref+"\nId del anuncio: "+media.attr("id")+"\n\nHora de publicacion: "+fecha.ownText()+"\n\nNumero de dormitorios: "+numrooms.ownText()+"\n\nPrecio: "+price.ownText()+"\n\nTamaño: "+size.ownText()+"\n\nDescripción: \n"+sec.ownText()+"\n\nCalle: "+calle.attr("content")+"\n\nZona: "+zona.attr("content")+"\n\n\n\nNO HAY FOTOS\n\n\n\nTIENE TELÉFONO DE CONTACTO");
						 }
						 else if(((!fotox.isEmpty()))&&(tlf.isEmpty())){
							 message.setText(
									 	"Enlace: "+linkHref+"\nId del anuncio: "+media.attr("id")+"\n\nHora de publicacion: "+fecha.ownText()+"\n\nNumero de dormitorios: "+numrooms.ownText()+"\n\nPrecio: "+price.ownText()+"\n\nTamaño: "+size.ownText()+"\n\nDescripción: \n"+sec.ownText()+"\n\nCalle: "+calle.attr("content")+"\n\nZona: "+zona.attr("content")+"\n\n\n\nHAY FOTOS\n\n\n\nCONTACTO SÓLO EMAIL");
						 }
						 else{
							 message.setText(
									 	"Enlace: "+linkHref+"\nId del anuncio: "+media.attr("id")+"\n\nHora de publicacion: "+fecha.ownText()+"\n\nNumero de dormitorios: "+numrooms.ownText()+"\n\nPrecio: "+price.ownText()+"\n\nTamaño: "+size.ownText()+"\n\nDescripción: \n"+sec.ownText()+"\n\nCalle: "+calle.attr("content")+"\n\nZona: "+zona.attr("content")+"\n\n\n\nHAY FOTOS\n\n\n\nTIENE TELÉFONO DE CONTACTO"); 
						 }
						 
						
						 // Enviamos correo
						 Transport t = session.getTransport("smtp");
						 t.connect("fatimagomezruti@gmail.com", "610518094");
						 t.sendMessage(message, message.getAllRecipients());
						 // Cierre.
						 t.close();
						
	         
						 FileWriter fichero = null;
					        PrintWriter pw = null;
					        fichero = new FileWriter("/home/paco/Escritorio/paquitotexto.txt");
						 
						 
					 pw=new PrintWriter(fichero);
					 pw.println(media.attr("id"));
					 fichero.close();
		 			}//if
		 			
				 
		}//try
		 
		 catch (Exception e)
		 { //Catch de excepciones
	            System.err.println("Ocurrio un error: " + e.getMessage());
         }//cath
			 
	}//run
		 };
		  // se crea un objeto del tipo timer
         Timer timer = new Timer();

         // se indica la tarea a ejecutar, 
         //el retardo y cada cuando se tiene que repetir
         timer.scheduleAtFixedRate(timerTask, 0, 300000);
}//main
}//class  


