package de.metas.handlingunits.client.editor.view.column;

import java.util.List;

import org.jdesktop.swingx.table.TableColumnExt;

import de.metas.handlingunits.tree.node.ITreeNode;

public interface ITreeTableColumn<T extends ITreeNode<T>>
{
	public static final int DEFAULT_ALIGNMENT = -1;

	void configureTableColumn(TableColumnExt column);

	String getColumnHeader();

	String getDisplayColumnHeader();

	String getDisplayName(Object value);

	String getDisplayName(T node);

	boolean isVisible(T node);

	boolean isEditable(T node);

	void setValue(T node, Object value);

	Object getValue(T node);

	boolean isCombobox();

	List<Object> getAvailableValuesList(T node);

	Class<?> getColumnType();

	int getAlignment();
}
