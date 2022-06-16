package vplibrary.javafx.util;

import javafx.scene.Node;

public class VPNode {
	
	/**
	 * Fonction qui met pour un noeud les propri�t�s "visible" et "managed" � false
	 * @param node
	 */
	public static void hide(Node... nodes) {
		for(Node node:nodes) {
			node.setVisible(false);
			node.setManaged(false);
		}
	}
	
	/**
	 * Fonction qui met pour un noeud les propri�t�s "visible" et "managed" � true
	 * @param node
	 */
	public static void show(Node... nodes) {
		for(Node node:nodes) {
			node.setVisible(true);
			node.setManaged(true);
		}
	}
	
	/**
	 * Si la condition est v�rifi�, les �l�ments seront affich�s, sinon ils seront masqu�s
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
