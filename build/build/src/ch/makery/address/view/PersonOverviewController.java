package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * O construtor.
     * O construtor � chamado antes do m�todo inicialize().
     */
    public PersonOverviewController() {
    }

    /**
     * Inicializa a classe controller. Este m�todo � chamado automaticamente
     *  ap�s o arquivo fxml ter sido carregado.
     */
    @FXML
    private void initialize() {
    	// Inicializa a tablea de pessoa com duas colunas.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        
     // Limpa os detalhes da pessoa.
        showPersonDetails(null);

        // Detecta mudan�as de sele��o e mostra os detalhes da pessoa quando houver mudan�a.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));     
    }

    /**
     * � chamado pela aplica��o principal para dar uma refer�ncia de volta a si mesmo.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Adiciona os dados da observable list na tabela
        personTable.setItems(mainApp.getPersonData());
    }


	private void showPersonDetails(Person person) {
	    if (person != null) {
	        // Preenche as labels com informa��es do objeto person.
	        firstNameLabel.setText(person.getFirstName());
	        lastNameLabel.setText(person.getLastName());
	        streetLabel.setText(person.getStreet());
	        postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
	        cityLabel.setText(person.getCity());
	        birthdayLabel.setText(DateUtil.format(person.getBirthday()));
	        // birthdayLabel.setText(...);
	    } 
	    else {
	        // Person � null, remove todo o texto.
	        firstNameLabel.setText("");
	        lastNameLabel.setText("");
	        streetLabel.setText("");
	        postalCodeLabel.setText("");
	        cityLabel.setText("");
	        birthdayLabel.setText("");
	    }
	}
	/**
	 * Chamado quando o usu�rio clica no bot�o delete.
	 */
	@FXML
	private void handleDeletePerson() {
	    int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
	    //Verifica��o para saber se alguma pessoa foi selecionada na c�lula. 
	    //Quando ninguem � selecionado, a Index fica -1 e o console da erro, logo:
	    if(selectedIndex>=0) {
	    	personTable.getItems().remove(selectedIndex);
	    }
	    else {
	    	Alert alert = new Alert(AlertType.WARNING);
	    		alert.setTitle("Nenhuma sele��o");
	    		alert.setHeaderText("Nenhuma pessoa selecionada!");
	    		alert.setContentText("Por favor, selecione uma pessoa para remover");
	    		alert.showAndWait(); //Mostra a caixa de alerta
	    }
	    
	}
	/**
	 * Chamado quando o usu�rio clica no bot�o novo. Abre uma janela para editar
	 * detalhes da nova pessoa.
	 */
	@FXML
	private void handleNewPerson() {
	    Person tempPerson = new Person();
	    boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
	    if (okClicked) {
	        mainApp.getPersonData().add(tempPerson);
	    }
	}

	/**
	 * Chamado quando o usu�rio clica no bot�o edit. Abre a janela para editar
	 * detalhes da pessoa selecionada.
	 */
	@FXML
	private void handleEditPerson() {
	    Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
	    if (selectedPerson != null) {
	        boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
	        if (okClicked) {
	            showPersonDetails(selectedPerson);
	        }

	    } else {
	        // Nada seleciondo.
	        Alert alert = new Alert(AlertType.WARNING);
	        	alert.setTitle("Nenhuma sele��o");
	        	alert.setHeaderText("Nenhuma Pessoa Selecionada");
	        	alert.setContentText("Por favor, selecione uma pessoa na tabela.");
	        	alert.showAndWait();
	    }
	}
}