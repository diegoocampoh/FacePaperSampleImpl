/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ucu.p2.facebook.api;

import facebook4j.Post;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import uy.edu.ucu.p2.facebook.adt.ILista;
import uy.edu.ucu.p2.facebook.adt.INodoPost;
import uy.edu.ucu.p2.facebook.api.exceptions.FacePaperException;
import uy.edu.ucu.p2.facebook.server.Command;

/**
 *
 * @author gonzalo
 */
public class FacePaperTest {

    private IFacePaper facePaper;
    private Date today = new Date();
    private FacePaperMockForTest mock;

    public FacePaperTest() {
        facePaper = new IFacePaper() {
            @Override
            public FacepaperConnector getFacepaperConnector() {
                return FacePaperTest.this.mock;
            }

            @Override
            public ILista<INodoPost> obtenerNoticias() throws FacePaperException {
          
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public ILista<INodoPost> obtenerMuro() throws FacePaperException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int calcularMeGustaUltimaSemana() throws FacePaperException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void conectar(Command ready) throws FacePaperException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

    @Before
    public void setUp() {
        this.mock = new FacePaperMockForTest(today);
    }

    /**
     * Test of getFacepaperConnector method, of class IFacePaper.
     */
    @Test
    public void testGetFacepaperConnector() {
        Assert.assertEquals(mock, facePaper.getFacepaperConnector());
    }

    /**
     * Test of obtenerNoticias method, of class IFacePaper.
     */
    @Test
    public void testObtenerNoticias() {
        try {
            for (Post post : mock.getHome(25)) {
                ILista<INodoPost> lista = facePaper.obtenerNoticias();
                INodoPost nodo = lista.buscar(post.getId());
                if (post.getType().equals("mobile_status_update")) {

                    Assert.assertNotNull(nodo);
                    Assert.assertEquals(post.getFrom().getName(), nodo.getAutor());
                    Assert.assertEquals(post.getCreatedTime(), nodo.getFecha());
                    Assert.assertEquals(post.getMessage(), nodo.getTexto());
                    Assert.assertEquals(post.getLikes().getCount().intValue(), nodo.getCantidadLikes());
                } else {
                    Assert.assertNull(nodo);
                }
            }
            for (Post post : mock.getFeed(Integer.MAX_VALUE)) {

                ILista<INodoPost> lista = facePaper.obtenerMuro();
                INodoPost nodo = lista.buscar(post.getId());
                if (post.getType().equals("mobile_status_update")) {

                    Assert.assertNotNull(nodo);
                    Assert.assertEquals(post.getFrom().getName(), nodo.getAutor());
                    Assert.assertEquals(post.getCreatedTime(), nodo.getFecha());
                    Assert.assertEquals(post.getDescription(), nodo.getTexto());
                    Assert.assertEquals(post.getLikes().getCount().intValue(), nodo.getCantidadLikes());
                } else {
                    Assert.assertNull(nodo);
                }
            }
        } catch (FacePaperException ex) {
            Logger.getLogger(FacePaperTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Exception!");
        }
    }

    /**
     * Test of obtenerMuro method, of class IFacePaper.
     */
    @Test
    public void testObtenerMuro() {
        try {
            for (Post post : mock.getFeed(Integer.MAX_VALUE)) {

                ILista<INodoPost> lista = facePaper.obtenerMuro();
                INodoPost nodo = lista.buscar(post.getId());
                if (post.getType().equals("mobile_status_update")) {

                    Assert.assertNotNull(nodo);
                    Assert.assertEquals(post.getFrom().getName(), nodo.getAutor());
                    Assert.assertEquals(post.getCreatedTime(), nodo.getFecha());
                    Assert.assertEquals(post.getDescription(), nodo.getTexto());
                    Assert.assertEquals(post.getLikes().getCount().intValue(), nodo.getCantidadLikes());
                } else {
                    Assert.assertNull(nodo);
                }
            }

        } catch (FacePaperException ex) {
            Logger.getLogger(FacePaperTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Exception!");
        }
    }

    /**
     * Test of calcularMeGustaUltimaSemana method, of class IFacePaper.
     */
    @Test
    public void testCalcularMeGustaUltimaSemana() {
        try {
            Assert.assertEquals(cantidadMeGusta(), facePaper.calcularMeGustaUltimaSemana());
        } catch (FacePaperException ex) {
            Logger.getLogger(FacePaperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int cantidadMeGusta() {
        try {
            int cantidadMeGusta = 0;
            for (Post feed : mock.getFeed(Integer.MAX_VALUE)) {
                if (feed.getCreatedTime().equals(today)) {
                    cantidadMeGusta += feed.getLikes().getCount();
                }
            }
            return cantidadMeGusta;
        } catch (FacePaperException ex) {
            Logger.getLogger(FacePaperTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Excepción!");
        }
        return 0;
    }
}
