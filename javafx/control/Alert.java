package VPLibrary.javafx.control;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

public class Alert extends javafx.scene.control.Alert{
	public Alert(AlertType alertType, String title, String headerText, String contentText) {
		super(alertType);
		this.setTitle(title);
		this.setHeaderText(headerText);
		this.setContentText(contentText);

		if(alertType.equals(AlertType.CONFIRMATION)) {
			this.initModality(Modality.APPLICATION_MODAL);
			((Button) this.getDialogPane().lookupButton(ButtonType.OK)).setText("Oui");
			((Button) this.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Non");
		}
		
		
	}
	public Alert(AlertType alertType, String title, String headerText) {
		this(alertType, title, headerText, "");
	}

	/**
	 * Cr�e une instance de CustomAlert et affiche directement la bo�te de dialogue
	 * Ici le type est automatique une AlertType.INFORMATION
	 * @param title
	 * @param headerText
	 * @param contentText
	 */
	public static void info(String title, String headerText, String contentText) {
		new Alert(AlertType.INFORMATION, title, headerText, contentText).show();;
	}
	
	/**
	 * Cr�e une instance de CustomAlert et affiche directement la bo�te de dialogue
	 * Ici le type est automatique une AlertType.CONFIRMATION
	 * 
	 * Les boutons types fournis sont ButtonType.OK et ButtonType.CANCEL
	 * @param title
	 * @param headerText
	 * @param contentText
	 */
	public static ButtonType confirm(String title, String headerText, String contentText) {
		return new Alert(AlertType.CONFIRMATION, title, headerText, contentText).showAndWait().get();
	}
}
