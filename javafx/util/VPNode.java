package vporel.javafx.util;

import javafx.scene.Node;

public class VPNode {
	
	/**
	 * Fonction qui met pour un noeud les propriétés "visible" et "managed" à false
	 * @param node
	 */
	public static void hide(Node... nodes) {
		for(Node node:nodes) {
			node.setVisible(false);
			node.setManaged(false);
		}
	}
	
	/**
	 * Fonction qui met pour un noeud les propriétés "visible" et "managed" à true
	 * @param node
	 */
	public static void show(Node... nodes) {
		for(Node node:nodes) {
			node.setVisible(true);
			node.setManaged(true);
		}
	}
	
	/**
	 * Si la condition est vérifié, les éléments seront affichés, sinon ils seront masqués
	 * @param condition
	 * @param nodes
	 */
	public static void showIf(boolean condition, Node... nodes) {
		if(condition)
			show(nodes);
		else
			hide(nodes);
	}
}
