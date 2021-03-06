package dad.javafx.components;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;
import java.util.ResourceBundle;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class DateChooser extends HBox  implements Initializable{

	//model
	private ObjectProperty<Meses> monthProperty = new SimpleObjectProperty<Meses>();
	private IntegerProperty dayProperty = new SimpleIntegerProperty();
	private IntegerProperty yearProperty = new SimpleIntegerProperty();
	private StringProperty conversorYear = new SimpleStringProperty();
	private ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<LocalDate>();
	
	
	//view
    @FXML
    private HBox view;

    @FXML
    private ComboBox<Integer> daysCombo;

    @FXML
    private ComboBox<Meses> monthsCombo;

    @FXML
    private ComboBox<Integer> yearsCombo;
    
    public DateChooser() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DateChooserView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void initialize(URL location, ResourceBundle resources) {
		
		dayProperty.bind(daysCombo.getSelectionModel().selectedItemProperty());
		monthProperty.bind(monthsCombo.getSelectionModel().selectedItemProperty());
		yearProperty.bind(yearsCombo.getSelectionModel().selectedItemProperty());
		
		ListProperty<Integer> daysList = new SimpleListProperty<Integer>(FXCollections.observableArrayList());
		ListProperty<Meses> monthsList = new SimpleListProperty<Meses>(FXCollections.observableArrayList());
		ListProperty<Integer> yearsList = new SimpleListProperty<Integer>(FXCollections.observableArrayList());
		
		daysCombo.itemsProperty().bind(daysList);
		monthsCombo.itemsProperty().bind(monthsList);
		yearsCombo.itemsProperty().bind(yearsList);
		for(int i = 1900; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
			yearsList.add(i);
		}
		
		monthsList.addAll(Meses.values());
		dateProperty.addListener((o,ov,nv) ->{
			dayProperty.set(nv.getDayOfMonth());	
			monthProperty.set(Meses.valueOf(nv.getMonth().toString()));
			yearProperty.set(nv.getYear());
			
		});
		monthProperty.addListener((o,ov,nv) -> {
			monthsCombo.getSelectionModel().select(nv);
			daysList.clear();
			int i;
			for(i = 0; i <= Meses.numDias(nv);i++) {
				daysList.add(i);
			}if(nv.equals(Meses.Febrero) && Year.of(yearProperty.get()).isLeap())
					daysList.add(i+1);
			
		});
		yearProperty.addListener((o,ov,nv) -> {
			yearsCombo.getSelectionModel().select(nv.intValue());
			daysList.clear();
			int i;
			for(i = 0; i <= Meses.numDias(monthProperty.get());i++) {
				daysList.add(i);
			}if(monthProperty.get().equals(Meses.Febrero) && Year.of(nv.intValue()).isLeap())
					daysList.add(i+1);
		});
		dayProperty.addListener((o,ov,nv) -> {
			daysCombo.getSelectionModel().select(nv.intValue());
		});
		iniciar();
		
	}
	public void iniciar() {
		monthsCombo.getSelectionModel().select(0);
		yearsCombo.getSelectionModel().select(0);
	}
	private enum Meses{
		Enero,Febrero,Marzo,Abril,Mayo,Junio,Julio,Agosto,
		Septiembre,Octubre,Noviembre,Diciembre;
		public static int numDias(Meses mes) {
			switch(mes) {
			case Febrero:return 28;
			case Abril:return 30;
			case Junio:return 30;
			case Septiembre:return 30;
			case Noviembre:return 30;
			default: return 31;
			}
		}
	}
	public final ObjectProperty<LocalDate> datePropertyProperty() {
		return this.dateProperty;
	}
	
	public final LocalDate getDateProperty() {
		return LocalDate.of(yearProperty.get(), monthProperty.get().ordinal()+1, dayProperty.get());
	}
	
	public final void setDateProperty(final LocalDate dateProperty) {
		Meses[] meses = Meses.values();
		monthsCombo.getSelectionModel().select(meses[dateProperty.getMonthValue()-1]);
		yearsCombo.getSelectionModel().select( new Integer(dateProperty.getYear()));
		daysCombo.getSelectionModel().select(dateProperty.getDayOfMonth());
	}
	

}
