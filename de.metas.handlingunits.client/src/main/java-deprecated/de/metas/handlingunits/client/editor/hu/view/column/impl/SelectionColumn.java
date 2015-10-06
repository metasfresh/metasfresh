package de.metas.handlingunits.client.editor.hu.view.column.impl;

import org.adempiere.util.Check;
import org.jdesktop.swingx.table.TableColumnExt;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.node.action.SelectNodeAction;
import de.metas.handlingunits.client.editor.view.column.impl.AbstractTreeTableColumn;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.RootHUTreeNode;

public class SelectionColumn extends AbstractTreeTableColumn<IHUTreeNode>
{
	private final HUEditorModel model;

	public SelectionColumn(final String columnHeader, final HUEditorModel model)
	{
		super(columnHeader);

		this.model = model;
	}

	@Override
	public void configureTableColumn(final TableColumnExt column)
	{
		super.configureTableColumn(column);

		column.setMinWidth(30);
		column.setMaxWidth(30);
		column.setWidth(30);
		column.setResizable(false);
	}

	@Override
	public Class<?> getColumnType()
	{
		return Boolean.class;
	}

	@Override
	public boolean isVisible(final IHUTreeNode node)
	{
		if (node instanceof RootHUTreeNode)
		{
			return true;
		}
		if (node instanceof HUTreeNode)
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean isEditable(final IHUTreeNode node)
	{
		if (!isVisible(node))
		{
			return false;
		}

		if (node.isReadonly())
		{
			return false;
		}

		return true;
	}

	@Override
	public Object getValue(final IHUTreeNode node)
	{
		return node.isSelected();
	}

	@Override
	public void setValue(final IHUTreeNode node, final Object value)
	{
		Check.assumeNotNull(value, "value not null");
		final boolean selected = convertToBoolean(value);

		final IAction action = new SelectNodeAction(model, node, selected);
		model.getHistory().execute(action);
	}

	private boolean convertToBoolean(Object value)
	{
		if (value == null)
		{
			return false;
		}
		else if (value instanceof Boolean)
		{
			return (Boolean)value;
		}
		else
		{
			throw new IllegalArgumentException("Cannot convert value to boolean: " + value + " (class=" + value.getClass() + ")");
		}
	}
}
