package vporel.javafx.control;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Predicate;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import vporel.form.Field;
import vporel.form.Form;
import vporel.form.FormFromAnnotations;
import vporel.form.NullFormObjectException;
import vporel.hibernate.HasId;
import vporel.util.VPArray;

/**
 * Classe s'utilisant uniquement avec des entités, les colonnes d'entête sont les attributs de ces entités
 * 
 * Dans cette classe, il est important que l'utilisateur passe les éléments du tableau dans le contructeur
 * et n'appelle la méthode setItems que si les éléments ne seront pas soumis à un filtre
 * Si cela se produit, le mécanisme de filtrage mis en place ne sera plus utilisable 
 * et l'utilisateur devra s'en occuper lui-même extérieurement
 * 
 * Pour ajouter un élément à la liste, utiliser plutôt la fonction getActualList instead
 * 
 * @author VPOREL-DEV
 *
 * @param <Entity>
 */
public class AutoTableView<Entity> extends TableView<Entity>{
	private Class<Entity> entityClass;
	private Form<Entity> virtualForm;
	private Callback<TableCell, Button>[] actionBtnsCallbacks;
	private ObservableList<Entity> actualList;
	private ObservableMap<String, Predicate<Entity>> predicates;
	private FilteredList<Entity> filteredList;
	private String[] hiddenColumns = null;
	private Map<String, String> extraColumns = null;
	
	/**
	 * Les champs de l'entité qui doivent être affichés doivent avoir l'annotation Field de vporel.form.annotations
	 * @param entityClass
	 * @param items
	 * @param actionBtnsCallbacks
	 */
	public AutoTableView(Class<Entity> entityClass, ObservableList<Entity> items, Callback<TableCell, Button>... actionBtnsCallbacks) {
		this(entityClass, items, null, null, actionBtnsCallbacks);
	}
	
	/**
	 * Les champs de l'entité qui doivent être affichés doivent avoir l'annotation Field de vporel.form.annotations
	 * @param entityClass
	 * @param items
	 * @param hiddenColumns
	 * @param actionBtnsCallbacks
	 */
	public AutoTableView(Class<Entity> entityClass, ObservableList<Entity> items, String[] hiddenColumns, Callback<TableCell, Button>... actionBtnsCallbacks) {
		this(entityClass, items, hiddenColumns, null, actionBtnsCallbacks);
	}
	
	public AutoTableView(Class<Entity> entityClass, ObservableList<Entity> items, Map<String, String> extraColumns, Callback<TableCell, Button>... actionBtnsCallbacks) {
		this(entityClass, items, null, extraColumns, actionBtnsCallbacks);
	}
	
	/**
	 * Les champs de l'entité qui doivent être affichés doivent avoir l'annotation Field de vporel.form.annotations
	 * @param entityClass
	 * @param items
	 * @param hiddenColumns Les propriétés de l'entité à ne pas afficher
	 * @param extraColumns Map<String, String> name, label
	 * @param actionBtnsCallbacks
	 */
	public AutoTableView(Class<Entity> entityClass, ObservableList<Entity> items, String[] hiddenColumns, Map<String, String> extraColumns, Callback<TableCell, Button>... actionBtnsCallbacks) {
		this.entityClass = entityClass;
		this.hiddenColumns = hiddenColumns;
		this.actionBtnsCallbacks = actionBtnsCallbacks;
		this.extraColumns = extraColumns;
		this.actualList = items;
		this.filteredList = new FilteredList<>(actualList);
		predicates = FXCollections.observableHashMap();
		try {
			virtualForm = new FormFromAnnotations<Entity>((Entity) entityClass.getDeclaredConstructor().newInstance());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | NullFormObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createColumns();
	    this.setItems(filteredList);
	    filteredList.predicateProperty().bind(Bindings.createObjectBinding(() -> {
	    	return new Predicate<Entity>() {

				@Override
				public boolean test(Entity t) {
					boolean valid = true;
					for(String key:predicates.keySet()) {
						if(!predicates.get(key).test(t)) {
			    			valid = false;
			    			break;
						}
					}
					return valid;
				}
			};
	    	
	    }, predicates));
		this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.getStyleClass().add("list");
	}
	
	public void hideActionButtons() {
		if(actionBtnsCallbacks.length > 0)
			this.getColumns().get(this.getColumns().size()-1).setVisible(false);
	}
	
	public ObservableList<Entity> getActualList(){
		return actualList;
	}
	
	public void addPredicate(String field, Predicate<Entity> predicate) {
		predicates.put(field, predicate);
	}
	
	public void createColumns() {
		ArrayList<Field<?>> fields = virtualForm.getFields();
		if(HasId.class.isAssignableFrom(entityClass) && (hiddenColumns == null || (hiddenColumns != null  && !VPArray.in(hiddenColumns, "id")))) {
			TableColumn<Entity, Integer> idCol = new TableColumn<Entity, Integer>("#Id");
			idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
			this.getColumns().add(idCol);
		}
		fields.forEach(field -> {
			if((hiddenColumns == null || (hiddenColumns != null  && !VPArray.in(hiddenColumns, field.getName())))) {
				
				TableColumn<Entity, String> col = new TableColumn<Entity, String>(field.getLabel());
				 col.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
				this.getColumns().add(col);
			}
		});
		if(extraColumns != null) {
			extraColumns.forEach((name, label) -> {
				TableColumn<Entity, String> col = new TableColumn<Entity, String>(label);
				 col.setCellValueFactory(new PropertyValueFactory<>(name));
				this.getColumns().add(col);
			});
		}
		if(actionBtnsCallbacks.length > 0) {
			Callback<TableColumn<Entity, String>, TableCell<Entity, String>> cellFactory = (object) -> {
				final TableCell<Entity, String> cell = new TableCell<>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if(empty) {
							setGraphic(null);
						}else {
							HBox buttons = new HBox(2);
							buttons.setAlignment(Pos.CENTER);
							for(Callback<TableCell, Button> btnCallBack:actionBtnsCallbacks) {
								if(btnCallBack != null) {
									Button btn = btnCallBack.call(this);
									buttons.getChildren().add(btn);
								}
							}
							buttons.getStyleClass().add("action-buttons-pane");
							setGraphic(buttons);
						}
						setText(null);
					}
				};
				return cell;
			};
			TableColumn<Entity, String> actionBtnsCol = new TableColumn<Entity, String>("Actions");
			actionBtnsCol.setCellFactory(cellFactory);
			this.getColumns().add(actionBtnsCol);
		}
	}
	
	/**
	 * Ici il n'y a aucun texte
	 * Le graphic(icon) est généré à partir de la librairie Ikonli
	 * @param eventName
	 * @param fontAwesomeLiteral Nom de l'icon dans Font Awesome (cette icon aura la classe 'icon')
	 * @return
	 */
	public static Callback<TableCell, Button> createButtonCallback(String eventName, String fontAwesomeLiteral) {
		return createButtonCallback(eventName, "", fontAwesomeLiteral);
	}
	
	/**
	 * Le graphic(icon) est généré à partir de la librairie Ikonli
	 * @param eventName
	 * @param text
	 * @param fontAwesomeLiteral Nom de l'icon dans Font Awesome (cette icon aura la classe 'icon')
	 * @return
	 */
	public static Callback<TableCell, Button> createButtonCallback(String eventName, String text, String fontAwesomeLiteral) {
		Callback<TableCell, Button> callback = (tableCell) -> {
			Button btn = new Button();
			btn.setText(text);
			FontIcon icon = new FontIcon(fontAwesomeLiteral);
			icon.getStyleClass().add("icon");
			btn.setGraphic(icon);
			btn.setOnAction(e -> {
				Object object = tableCell.getTableView().getItems().get(tableCell.getIndex());
				tableCell.getTableView().fireEvent(new ButtonActionEvent(object, eventName));
			});
			return btn;
		};
		return callback;
	}
	
	
	public static class ButtonActionEvent extends Event{
		public static EventType<ButtonActionEvent> BUTTON_ACTION_EVENT_TYPE = new EventType<ButtonActionEvent>("DETAIL_BUTTON_ACTION_EVENT");
		
		private Object object;
		/**
		 * Propriété spécifique permettant de savoir quel bouton a déclenché l'évènement sur la liste
		 */
		private String name;
		
		public ButtonActionEvent(Object object, String name) {
			super(BUTTON_ACTION_EVENT_TYPE);
			this.object = object;
			this.name = name;
		}

		public Object getObject() {
			return object;
		}
		
		public String getName() {
			return name;
		}
		
	}
	
}
