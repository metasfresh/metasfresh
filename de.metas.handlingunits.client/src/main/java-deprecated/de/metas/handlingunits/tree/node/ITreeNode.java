package de.metas.handlingunits.tree.node;

import java.util.List;

public interface ITreeNode<T extends ITreeNode<T>>
{
	List<T> getChildren();

	T getParent();

	/**
	 * NOTE: called by API. Don't call it directly.
	 * 
	 * @param parent
	 */
	void setParent(T parent);

	/**
	 * NOTE: called by API. Don't call it directly.
	 * 
	 * @param newChild
	 * @param index
	 */
	void insertChild(T newChild, int index);

	/**
	 * NOTE: called by API. Don't call it directly.
	 * 
	 * @param newChild
	 */
	void addChild(T newChild);

	int getChildIndex(T node);

	T getChildAt(int index);

	/**
	 * NOTE: called by API. Don't call it directly.
	 * 
	 * @param child
	 * @return true if removed
	 */
	boolean removeChild(T child);

	String getDisplayName();
}
