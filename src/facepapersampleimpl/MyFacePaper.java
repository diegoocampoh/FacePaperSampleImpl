/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepapersampleimpl;

import java.util.logging.Level;
import java.util.logging.Logger;
import uy.edu.ucu.p2.facebook.api.FacePaperImpl;
import uy.edu.ucu.p2.facebook.api.FacepaperConnector;
import uy.edu.ucu.p2.facebook.api.IFacePaper;
import uy.edu.ucu.p2.facebook.api.ILista;
import uy.edu.ucu.p2.facebook.api.INodoPost;
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
    public ILista<INodoPost> obtenerNoticias() {
       
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return obtiene del conector tus Posts (getFeed) y cargar la lista de NodoPost
     */
    @Override
    public ILista<INodoPost> obtenerMuro() {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
