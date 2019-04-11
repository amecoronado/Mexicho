/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuukul.proyecto.web;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.tuukul.modelo.Tema;
import com.tuukul.modelo.UtilidadTema;
/**
 *
 * @author coronado
 */
@ManagedBean
@RequestScoped
public class ControladorTema {
    private Tema tema = new Tema();//objeto Tema
    private UtilidadTema m_tema = new UtilidadTema();//objeto ManejadorTema
    private String titulo_tema;
    private String descripcion_tema;
    private String color_tema;

    public String getTitulo_tema() {
        return titulo_tema;
    }

    public void setTitulo_tema(String titulo_tema) {
        this.titulo_tema = titulo_tema;
    }

    public String getDescripcion_tema() {
        return descripcion_tema;
    }

    public void setDescripcion_tema(String descripcion_tema) {
        this.descripcion_tema = descripcion_tema;
    }

    public String getColor_tema() {
        return color_tema;
    }

    public void setColor_tema(String color_tema) {
        this.color_tema = color_tema;
    }
    
    
    public ControladorTema(){
        FacesContext.getCurrentInstance()
                .getViewRoot()
                .setLocale(new Locale("es-Mx"));
    }

    public Tema getTema(){
        return this.tema;
    }
    
    public void setTema(Tema tema){
        tema=this.tema;
    }
    
    /**Método agregarTema()
     * Agrega un nuevo tema haciendo uso del manejador de tema.
     * @return 
     */
    public String agregarTema(){
        FacesContext context = FacesContext.getCurrentInstance();
        com.tuukul.modelo.Usuario usuario_actual = (com.tuukul.modelo.Usuario) context.getExternalContext().getSessionMap().get("usuario");
        if(usuario_actual != null && !usuario_actual.getRol().equals("Comentarista")){
            tema.setId_tema(m_tema.generaId());
            tema.setId_usuario(usuario_actual.getId_usuario());
            m_tema.save(tema);
            tema = null;
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "El tema se agregó con éxito", ""));
        }
        else{
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Sólo un informador puede agregar temas.", ""));
        }
        
        return null;
    }  
    
    /**Método eliminarTema()
     * Elimina el tema haciendo uso del manejador de tema.
     */
    public void eliminarTema(){
        m_tema.delete(tema);
        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "El tema se eliminó con éxito", ""));
    } 
    
    /**Método getBuscarTema()
     * 
     * @return 
     */
    public String buscarTema(){ 
        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Buscando el tema...", ""));
        Tema t = m_tema.buscar(tema);
        if(t == null){
            FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "El tema no se encontró.", ""));
            return "";
        }
        else{
            titulo_tema = t.getTitulo_tema();
            descripcion_tema = t.getDescripcion_tema();
            color_tema = t.getColor_tema();
            System.out.println(titulo_tema+"\n"+descripcion_tema+"\n"+color_tema);
            return "BuscarTema.xhtml?faces-redirect=true";
        }
        //System.out.println(t.getTitulo_tema()+"\n"+t.getDescripcion_tema()+"\n"+t.getColor_tema());
        
    }
   
}
