/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepapersampleimpl;

import facebook4j.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uy.edu.ucu.p2.facebook.adt.Autor;
import uy.edu.ucu.p2.facebook.adt.ILista;
import uy.edu.ucu.p2.facebook.adt.Lista;
import uy.edu.ucu.p2.facebook.adt.NodoPost;
import uy.edu.ucu.p2.facebook.api.FacePaperImpl;
import uy.edu.ucu.p2.facebook.api.FacepaperConnector;
import uy.edu.ucu.p2.facebook.api.IFacePaper;
import uy.edu.ucu.p2.facebook.adt.INodoPost;
import uy.edu.ucu.p2.facebook.api.exceptions.FacePaperException;
import uy.edu.ucu.p2.facebook.server.Command;

/**
 *
 * @author diego
 */
public class MyFacePaper implements IFacePaper{
    
    private FacepaperConnector facepaperConnector = new FacePaperImpl();
    private boolean conectado = false;

       
    private void conectado(){
        this.conectado = true;
    }
    
    @Override
    public FacepaperConnector getFacepaperConnector() {
        return this.facepaperConnector;
    }

    /**
     * 
     * @return obtiene del conector las noticias (getHome) y cargar la lista de NodoPost
     */
    @Override
    public ILista<INodoPost> obtenerNoticias() throws FacePaperException {
       
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return obtiene del conector tus Posts (getFeed) y cargar la lista de NodoPost
     */
    @Override
    public ILista<INodoPost> obtenerMuro() throws FacePaperException{
        Post[] posts = this.getFacepaperConnector().getHome(123);
        ILista<INodoPost> result = new Lista<INodoPost>();
      
        List<List> a  = new ArrayList<List>();
        
        for (Post p: posts){
            NodoPost nodo = new NodoPost(p.getId(), null, 0, null,p.getDescription());
            result.insertar(nodo);
        }
        
        return result;
      
    }

    /**
     * 
     * @return obtiene del conector tus posts, de tus posts los Me Gusta (getLikes), los suma y retorna tal suma
     */
    @Override
    public int calcularMeGustaUltimaSemana() {
        if (this.conectado){
            return 100;
        }else{
            return -1;
        }
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public void conectar(Command cmnd) throws FacePaperException {
        facepaperConnector.conectar(cmnd);
    }
    
}
