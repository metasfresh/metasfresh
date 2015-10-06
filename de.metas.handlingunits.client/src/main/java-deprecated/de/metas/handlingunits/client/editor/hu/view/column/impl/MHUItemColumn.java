package de.metas.handlingunits.client.editor.hu.view.column.impl;

import de.metas.handlingunits.client.editor.view.column.impl.AbstractTreeTableColumn;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

public final class MHUItemColumn extends AbstractTreeTableColumn<IHUTreeNode>
{
	public MHUItemColumn(final String columnHeader)
	{
		super(columnHeader);
	}

	@Override
	public Object getValue(final IHUTreeNode node)
	{
		return node.getDisplayName();
	}

	@Override
	public Class<?> getColumnType()
	{
		return String.class;
	}
}
