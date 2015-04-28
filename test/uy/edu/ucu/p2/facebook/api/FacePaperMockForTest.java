/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uy.edu.ucu.p2.facebook.api;

import facebook4j.Post;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import uy.edu.ucu.p2.facebook.adt.Autor;
import uy.edu.ucu.p2.facebook.api.exceptions.FacePaperException;
import uy.edu.ucu.p2.facebook.server.Command;

/**
 *
 * @author gonzalo
 */
public class FacePaperMockForTest implements FacepaperConnector {
    
    private List<Post> posts = new LinkedList<Post>();
    private List<Post> feed = new LinkedList<Post>();
    private List<Autor> authors = new LinkedList<Autor>();
    private Date today;
    public FacePaperMockForTest(Date today)
    {
        this.today=today;
        Random random = new Random();
        int folderNumber = random.nextInt(21);
        try {
            FileReader reader = new FileReader("./test/AmigosPosts/"+folderNumber+"/amigos.csv");
            BufferedReader bReader = new BufferedReader(reader);
            String line = "";
            while((line=bReader.readLine())!=null)
            {
                String[] splitLine = line.split("\\|");
                authors.add(new Autor(splitLine[0], splitLine[1]));
            }
            bReader.close();
            reader.close();
            reader = new FileReader("./test/AmigosPosts/"+folderNumber+"/posts.csv");
            bReader = new BufferedReader(reader);
            line = "";
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
            int tipo=0;
            while((line=bReader.readLine())!=null)
            {
                String[] splitLine = line.split("\\|");
                final int authorId = Integer.parseInt(splitLine[0]);
                final String texto = splitLine[1];
                final Date fechaCreacion = dt.parse(splitLine[2]);
                final int id = random.nextInt();
                String nombreAutor="";
                for(Autor autor : authors)
                {
                    if(autor.getEtiqueta().equals(""+authorId))
                    {
                        nombreAutor=autor.getNombre();
                        break;
                    }
                }
                if(tipo%4==0)
                {
                posts.add(new MockPost(authorId, id, texto, fechaCreacion, nombreAutor, "video"));
                feed.add(new MockFeed(112220112, id, texto, fechaCreacion, "Yo", "video"));
                } else {
                    posts.add(new MockPost(authorId, id, texto, fechaCreacion, nombreAutor, "mobile_status_update"));
                feed.add(new MockFeed(112220112, id, texto, fechaCreacion, "Yo", "mobile_status_update"));
                }
                tipo++;
            }
            
            feed.add(new MockFeed(112220112, 112220113, "Post de última semana", today, "Yo", "video"));
            feed.add(new MockFeed(112220112, 112220114, "Post de última semana2", today, "Yo", "video"));
            feed.add(new MockFeed(112220112, 112220115, "Post de última semana3", today, "Yo", "video"));
            feed.add(new MockFeed(112220112, 112220116, "Post de última semana4", today, "Yo", "video"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FacePaperMockForTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacePaperMockForTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FacePaperMockForTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @Override
    public void conectar(Command ready) throws FacePaperException {
        // do nothing
    }


    @Override
    public Post[] getHome(int limite) throws FacePaperException {
        if(limite<posts.size())
        {
           Post[] p = new Post[limite];
           for(int i =0; i < limite; i++)
           {
               p[i]=posts.get(i);
           }
           return p;
        } else {
           Post[] p = new Post[posts.size()];
           for(int i =0; i < posts.size(); i++)
           {
               p[i]=posts.get(i);
           }
           return p;
        }
       
        
    }

    @Override
    public Post[] getFeed(int limite) throws FacePaperException {
        if(limite<feed.size())
        {
           Post[] p = new Post[limite];
           for(int i =0; i < limite; i++)
           {
               p[i]=feed.get(i);
           }
           return p;
        } else {
           Post[] p = new Post[feed.size()];
           for(int i =0; i < feed.size(); i++)
           {
               p[i]=feed.get(i);
           }
           return p;
        }
    }
    
}
