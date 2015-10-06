package de.metas.handlingunits.client.editor.allocation.view.column.impl;

import java.math.BigDecimal;

import de.metas.handlingunits.client.editor.view.column.impl.AbstractTreeTableColumn;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;

public final class QtyRemainingColumn extends AbstractTreeTableColumn<IHUDocumentTreeNode>
{
	public QtyRemainingColumn(final String columnHeader)
	{
		super(columnHeader);
	}

	@Override
	public Object getValue(final IHUDocumentTreeNode node)
	{
		return node.getQtyRemaining();
	}

	@Override
	public Class<?> getColumnType()
	{
		return BigDecimal.class;
	}
}
