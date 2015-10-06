package de.metas.handlingunits.client.editor.view.column.impl;

import java.util.Collections;
import java.util.List;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.util.Env;
import org.jdesktop.swingx.table.TableColumnExt;

import de.metas.handlingunits.client.editor.view.column.ITreeTableColumn;
import de.metas.handlingunits.tree.node.ITreeNode;

public abstract class AbstractTreeTableColumn<T extends ITreeNode<T>> implements ITreeTableColumn<T>
{
	// column state
	private final String columnHeader;
	private final String columnHeaderTrl;

	// column options
	private final boolean combobox;
	private final boolean editable;

	public AbstractTreeTableColumn(final String columnHeader)
	{
		this(columnHeader, false, false);
	}

	private AbstractTreeTableColumn(final String columnHeader, final boolean isComboBox, final boolean isEditable)
	{
		super();

		this.columnHeader = columnHeader;
		this.columnHeaderTrl = Services.get(IMsgBL.class).translate(Env.getCtx(), columnHeader);

		this.combobox = isComboBox;
		this.editable = isEditable;
	}

	@Override
	public void configureTableColumn(final TableColumnExt column)
	{
		// nothing at this level
	}

	@Override
	public String getColumnHeader()
	{
		return this.columnHeader;
	}

	@Override
	public String getDisplayColumnHeader()
	{
		return this.columnHeaderTrl;
	}

	@Override
	public boolean isCombobox()
	{
		return this.combobox;
	}

	@Override
	public List<Object> getAvailableValuesList(final T node)
	{
		return Collections.emptyList();
	}

	@Override
	public boolean isVisible(final T node)
	{
		return true;
	}

	@Override
	public boolean isEditable(final T node)
	{
		return this.editable;
	}

	@Override
	public void setValue(final T node, final Object value)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public abstract Object getValue(final T node);

	@Override
	public String getDisplayName(final Object value)
	{
		if (value == null)
		{
			return null;
		}

		return value.toString();
	}

	@Override
	public String getDisplayName(final T node)
	{
		return node.getDisplayName();
	}

	@Override
	public int getAlignment()
	{
		return AbstractTreeTableColumn.DEFAULT_ALIGNMENT;
	}
}
