package vplibrary.javafx.container;

import java.util.Optional;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomStage extends Stage{
	private double MoveStartX, MoveStartY;
	public Button minimizeBtn = new Button(), maximizeBtn = new Button(), closeBtn = new Button();
	
	private boolean canExitSystem = false;
	
	public CustomStage(Scene scene, AnchorPane topBar) {
		this.initStyle(StageStyle.UNDECORATED);
		this.setScene(scene);
		
		//Create Buttons
		FontIcon minimizeBtnIcon = new FontIcon("fa-minus"), maximizeBtnIcon = new FontIcon("fa-square-o"), closeBtnIcon = new FontIcon("fa-times");
		minimizeBtnIcon.getStyleClass().add("icon");
		maximizeBtnIcon.getStyleClass().add("icon");
		closeBtnIcon.getStyleClass().add("icon");
		minimizeBtn.getStyleClass().add("top-btn");
		minimizeBtn.setId("minimize-window-btn");
		minimizeBtn.setGraphic(minimizeBtnIcon);
		minimizeBtn.setOnAction(e -> { setIconified(true); });
		maximizeBtn.getStyleClass().add("top-btn");
		maximizeBtn.setId("maximize-window-btn");
		maximizeBtn.setGraphic(maximizeBtnIcon);
		this.maximizedProperty().addListener((opts, oldVal, newVal) -> {
			if(newVal == false) 
				((FontIcon) maximizeBtn.getGraphic()).setIconLiteral("fa-square-o");
			else 
				((FontIcon) maximizeBtn.getGraphic()).setIconLiteral("fa-clone");
			
		});
		maximizeBtn.setOnAction(e -> { 
			if(isMaximized()) {
				setMaximized(false);
			}else {
				setMaximized(true);
			}
		});
		closeBtn.getStyleClass().add("top-btn");
		closeBtn.setId("close-window-btn");
		closeBtn.setGraphic(closeBtnIcon);
		closeBtn.setOnAction(e -> { 
			close(); 
		});
		this.setOnCloseRequest(event -> {
			if(canExitSystem) {
				Alert confirmation = new Alert(AlertType.CONFIRMATION);
				confirmation.setTitle("Confirmation");
				confirmation.setHeaderText("Confirmation de fermeture");
				confirmation.setContentText("Voulez-vous vraiment quitter le programme ? ");
				Optional<ButtonType> option = confirmation.showAndWait();
				if(option.get() == ButtonType.OK) { 
					System.exit(0);
				}else {
					event.consume();
				}
			}
		});
		
		//Icon
		Image icon = new Image(getClass().getResource("/resources/images/icon.png").toExternalForm());
		this.getIcons().add(icon);
		
		HBox topBtnsPane = new HBox();
		topBtnsPane.getChildren().addAll(minimizeBtn, maximizeBtn, closeBtn);
		topBar.getChildren().add(topBtnsPane);
		AnchorPane.setRightAnchor(topBtnsPane, 0.0);
		AnchorPane.setBottomAnchor(topBtnsPane, 0.0);
		AnchorPane.setTopAnchor(topBtnsPane, 0.0);
		
		//Double click on topbar
		topBar.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2) {
				maximizeBtn.fire();
			}
		});
		
		//Move stage
		/**/
		topBar.setOnMousePressed(event -> {
			MoveStartX = event.getSceneX();
			MoveStartY = event.getSceneY();
		});
		
		topBar.setOnMouseDragged(event -> {
			this.setX(event.getScreenX() - MoveStartX);
			this.setY(event.getScreenY() - MoveStartY);
		});
		/**/
		
		//Resize stage
		ResizeListener listener = new ResizeListener(this);
		this.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, listener);
		this.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
		this.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
		this.getScene().addEventHandler(MouseEvent.MOUSE_EXITED, listener);
		this.getScene().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, listener);
	}
	
	public boolean canExitSystem() {
		return canExitSystem;
	}
	
	public void setCanExitSystem(boolean val) {
		this.canExitSystem = val;
	}
	
	public void setRegionForMove(Region region) {
		region.setOnMousePressed(event -> {
			MoveStartX = event.getSceneX();
			MoveStartY = event.getSceneY();
		});
		
		region.setOnMouseDragged(event -> {
			this.setX(event.getScreenX() - MoveStartX);
			this.setY(event.getScreenY() - MoveStartY);
		});
	}
	
	public void hideMinimizeButton() {
		minimizeBtn.setVisible(false);
		minimizeBtn.setManaged(false);
	}
	
	public void hideMaximizeButton() {
		maximizeBtn.setVisible(false);
		maximizeBtn.setManaged(false);
	}
	
	public void hideCloseButton() {
		closeBtn.setVisible(false);
		closeBtn.setManaged(false);
	}
	public void hideAllButtons() {
		hideMinimizeButton();
		hideMaximizeButton();
		hideCloseButton();
	}
	
	public static Stage getStage(Node node) {
		return (Stage) node.getScene().getWindow();
	}
	
	public static void resizeWithContent(Pane root) {
		root.autosize();
		
		getStage(root).setMinHeight(root.getBoundsInLocal().getHeight());
		getStage(root).setMinWidth(root.getBoundsInLocal().getWidth());
		getStage(root).centerOnScreen();
	
	}
	
	private class ResizeListener implements EventHandler<MouseEvent>{
		private Stage stage;
		private Cursor cursor = Cursor.DEFAULT;
		private int border = 5;
		private double resizeStartX = 0.0, resizeStartY = 0.0;
		public ResizeListener(Stage stage) {
			this.stage = stage;
		}
		@Override
		public void handle(MouseEvent mouseEvent) {
			if(isResizable()) {
				// TODO Auto-generated method stub
				EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
				Scene scene = stage.getScene();
				
				double mouseEventX = mouseEvent.getSceneX(), mouseEventY = mouseEvent.getSceneY();
				double sceneWidth = scene.getWidth(), sceneHeight = scene.getHeight();
				if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
	                if (mouseEventX < border && mouseEventY < border) {
	                    cursor = Cursor.NW_RESIZE;
	                } else if (mouseEventX < border && mouseEventY > sceneHeight - border) {
	                    cursor = Cursor.SW_RESIZE;
	                } else if (mouseEventX > sceneWidth - border && mouseEventY < border) {
	                    cursor = Cursor.NE_RESIZE;
	                } else if (mouseEventX > sceneWidth - border && mouseEventY > sceneHeight - border) {
	                    cursor = Cursor.SE_RESIZE;
	                } else if (mouseEventX < border) {
	                    cursor = Cursor.W_RESIZE;
	                } else if (mouseEventX > sceneWidth - border) {
	                    cursor = Cursor.E_RESIZE;
	                } else if (mouseEventY < border) {
	                    cursor = Cursor.N_RESIZE;
	                } else if (mouseEventY > sceneHeight - border) {
	                    cursor = Cursor.S_RESIZE;
	                } else {
	                    cursor = Cursor.DEFAULT;
	                }
	                scene.setCursor(cursor);
	            } else if (MouseEvent.MOUSE_EXITED.equals(mouseEventType) || MouseEvent.MOUSE_EXITED_TARGET.equals(mouseEventType)) {
	                scene.setCursor(Cursor.DEFAULT);
	            } else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
	                resizeStartX = stage.getWidth() - mouseEventX;
	                resizeStartY = stage.getHeight() - mouseEventY;
	            } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {
	                if (!Cursor.DEFAULT.equals(cursor)) {
	                    if (!Cursor.W_RESIZE.equals(cursor) && !Cursor.E_RESIZE.equals(cursor)) {
	                        double minHeight = stage.getMinHeight() > (border * 2) ? stage.getMinHeight() : (border * 2);
	                        double maxHeight = stage.getMaxHeight();
	                        if (Cursor.NW_RESIZE.equals(cursor) || Cursor.N_RESIZE.equals(cursor) || Cursor.NE_RESIZE.equals(cursor)) {
	                            double newHeight = stage.getHeight() - (mouseEvent.getScreenY() - stage.getY());
	                            if (newHeight >= minHeight && newHeight <= maxHeight) {
	                                stage.setHeight(newHeight);
	                                stage.setY(mouseEvent.getScreenY());
	                            } else {
	                                newHeight = Math.min(Math.max(newHeight, minHeight), maxHeight);
	                                // y1 + h1 = y2 + h2
	                                // y1 = y2 + h2 - h1
	                                stage.setY(stage.getY() + stage.getHeight() - newHeight);
	                                stage.setHeight(newHeight);
	                            }
	                        } else {
	                            stage.setHeight(Math.min(Math.max(mouseEventY + resizeStartY, minHeight), maxHeight));
	                        }
	                    }
	
	                    if (!Cursor.N_RESIZE.equals(cursor) && !Cursor.S_RESIZE.equals(cursor)) {
	                        double minWidth = stage.getMinWidth() > (border * 2) ? stage.getMinWidth() : (border * 2);
	                        double maxWidth = stage.getMaxWidth();
	                        if (Cursor.NW_RESIZE.equals(cursor) || Cursor.W_RESIZE.equals(cursor) || Cursor.SW_RESIZE.equals(cursor)) {
	                            double newWidth = stage.getWidth() - (mouseEvent.getScreenX() - stage.getX());
	                            if (newWidth >= minWidth && newWidth <= maxWidth) {
	                                stage.setWidth(newWidth);
	                                stage.setX(mouseEvent.getScreenX());
	                            } else {
	                                newWidth = Math.min(Math.max(newWidth, minWidth), maxWidth);
	                                // x1 + w1 = x2 + w2
	                                // x1 = x2 + w2 - w1
	                                stage.setX(stage.getX() + stage.getWidth() - newWidth);
	                                stage.setWidth(newWidth);
	                            }
	                        } else {
	                            stage.setWidth(Math.min(Math.max(mouseEventX + resizeStartX, minWidth), maxWidth));
	                        }
	                    }
	                }
	            }
			}
        }
	}
}
