/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxmlcarreras;

import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import piramboia.Carreras;

/**
 *
 * @author arpor
 */
public class FXMLDocumentController implements Initializable {
    
    Locale esLocale = new Locale("es");
    
    // Create EntityManagerFactory for a persistence unit called em1.
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JFXMLCarrerasPU");
    
    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<Carreras> TableView;
    @FXML
    private TextField numSec;
    @FXML
    private DatePicker fecha;
    @FXML
    private TextField horaInicio;
    @FXML
    private TextField horaFin;
    @FXML
    private TextField kms;
    @FXML
    private TextArea recorrido;
    @FXML
    private TextField tipoEjercicio;
    @FXML
    private TextField peso;
    @FXML
    private TableColumn<Carreras, Integer> numSecColumn;
    @FXML
    private TableColumn<Carreras, Date> fechaColumn;
    @FXML
    private TableColumn<Carreras, Date> horaInicioColumn;
    @FXML
    private TableColumn<Carreras, Date> horaFinColumn;
    @FXML
    private TableColumn<Carreras, Double> kmsColumn;
    @FXML
    private TableColumn<Carreras, String> recorridoColumn;
    @FXML
    private TableColumn<Carreras, String> tipoEjercicioColumn;
    @FXML
    private TableColumn<Carreras, Double> pesoColumn;
        
    @FXML
    private void insertButton() {
        Carreras c = new Carreras();
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
                
        try {     
            c.setFecha(this.convertDatePickerToDate(fecha));
            c.setHoraInicio(this.convertTextFieldToTime(horaInicio,fecha));
            c.setHoraFin(this.convertTextFieldToTime(horaFin,fecha));            
            c.setKms(Double.parseDouble(kms.toString()));
            c.setRecorrido(recorrido.toString());
            c.setTipoDeEjercicio(tipoEjercicio.toString());
            c.setPeso(Double.parseDouble(peso.toString()));  
            
            em.persist(c);
            
        } finally {
            tx.commit();
            em.close();
        }
        
        showCarreras();
    }

    @FXML
    private void updateButton() {
        Carreras c = getCarrera();
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
                
        try {            
            c.setFecha(this.convertDatePickerToDate(fecha));
            c.setHoraInicio(this.convertTextFieldToTime(horaInicio,fecha));
            c.setHoraFin(this.convertTextFieldToTime(horaFin,fecha));            
            c.setKms(Double.parseDouble(kms.toString()));
            c.setRecorrido(recorrido.toString());
            c.setTipoDeEjercicio(tipoEjercicio.toString());
            c.setPeso(Double.parseDouble(peso.toString()));
            c = em.merge(c);
        } finally {
            tx.commit();
            em.close();
        }
        
        showCarreras();
    }

    @FXML
    private void deleteButton() {
        Carreras c = getCarrera();
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
                
        try {            
            em.remove(c);
        } finally {
            tx.commit();
            em.close();
        }
        
        showCarreras();
    }

    public Carreras getCarrera() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Carreras carrera;
        
        try {            
            carrera = (Carreras) em.createQuery("select o from Carreras as o where o.numSec = :numSec").setParameter("numSec", numSec).getSingleResult();            
        } finally {
            tx.commit();
            em.close();
        }
        
        return carrera;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showCarreras();
    }   

    public ObservableList<Carreras> getCarrerasList() {        
        ObservableList<Carreras> carrerasList = FXCollections.observableArrayList();
        List<Carreras> li;
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {            
            li = (List) em.createQuery("select o from Carreras as o").getResultList();            
        } finally {
            tx.commit();
            em.close();
        }
        
        li.forEach(carrera->carrerasList.add(carrera));
        
        return carrerasList;
    }

    // I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
    public void showCarreras() {
        ObservableList<Carreras> list = getCarrerasList();

        numSecColumn.setCellValueFactory(new PropertyValueFactory<>("numSec"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        horaInicioColumn.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        horaFinColumn.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
        kmsColumn.setCellValueFactory(new PropertyValueFactory<>("kms"));
        recorridoColumn.setCellValueFactory(new PropertyValueFactory<>("recorrido"));
        tipoEjercicioColumn.setCellValueFactory(new PropertyValueFactory<>("tipoDeEjercicio"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("peso"));
                      
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        fechaColumn.setCellFactory(column -> {
            return new TableCell<Carreras, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        LocalDate ld = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(item));
                        setText(fm.format(ld));
                    }
                }
            };
        });

        DateTimeFormatter fm2 = DateTimeFormatter.ofPattern("hh:mm:ss");

        horaInicioColumn.setCellFactory(column -> {
            return new TableCell<Carreras, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        LocalTime lt = LocalTime.parse(new SimpleDateFormat("hh:mm:ss").format(item));
                        setText(fm2.format(lt));
                    }
                }
            };
        });
        
        horaFinColumn.setCellFactory(column -> {
            return new TableCell<Carreras, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        LocalTime lt = LocalTime.parse(new SimpleDateFormat("hh:mm:ss").format(item));
                        setText(fm2.format(lt));
                    }
                }
            };
        });
        
        kmsColumn.setCellFactory(column -> {
            return new TableCell<Carreras, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(NumberFormat.getInstance(esLocale).format(item));
                    }
                }
            };
        });        
        
        pesoColumn.setCellFactory(column -> {
            return new TableCell<Carreras, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(NumberFormat.getInstance(esLocale).format(item));
                    }
                }
            };
        });        
        
        TableView.setItems(list);
    }
    
    public Date convertDatePickerToDate(DatePicker datePicker){
        LocalDate ld = datePicker.getValue();
        Calendar cal = Calendar.getInstance();
        cal.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth());
        Date date = cal.getTime();
        return date;
    }
    
    public Date convertTextFieldToTime(TextField tf, DatePicker dp){        
        LocalDate ld = dp.getValue();
        int hou = Integer.parseInt(tf.toString().substring(0, 2));
        int min = Integer.parseInt(tf.toString().substring(3, 5));
        int sec = Integer.parseInt(tf.toString().substring(6, 8));
        Calendar cal = Calendar.getInstance();
        cal.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth(), hou, min, sec);
        Date d = cal.getTime();
        return d;
    }
    
    public void selectRow(){
        ObservableList<Carreras> list;
        
        list=TableView.getSelectionModel().getSelectedItems();
        
        numSec.setText(list.get(0).getNumSec().toString());        
        fecha.setValue(new java.sql.Date(list.get(0).getFecha().getTime()).toLocalDate());
        
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("hh:mm:ss");        
        LocalTime lt = LocalTime.parse(new SimpleDateFormat("hh:mm:ss").format(list.get(0).getHoraInicio()));
        horaInicio.setText(fm.format(lt));
        lt = LocalTime.parse(new SimpleDateFormat("hh:mm:ss").format(list.get(0).getHoraFin()));
        horaFin.setText(fm.format(lt));
                
        kms.setText(NumberFormat.getInstance(esLocale).format(list.get(0).getKms()));
        recorrido.setText(list.get(0).getRecorrido());
        tipoEjercicio.setText(list.get(0).getTipoDeEjercicio());
        peso.setText(NumberFormat.getInstance(esLocale).format(list.get(0).getPeso()));
    }
}