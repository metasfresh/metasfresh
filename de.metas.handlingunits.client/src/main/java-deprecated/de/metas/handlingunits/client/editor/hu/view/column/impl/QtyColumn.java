package de.metas.handlingunits.client.editor.hu.view.column.impl;

import java.math.BigDecimal;

import org.adempiere.util.Check;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.node.action.SetHUQtyAction;
import de.metas.handlingunits.client.editor.view.column.impl.AbstractTreeTableColumn;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemMITreeNode;

public final class QtyColumn extends AbstractTreeTableColumn<IHUTreeNode>
{
	private final HUEditorModel model;

	public QtyColumn(final String columnHeader, final HUEditorModel model)
	{
		super(columnHeader);

		this.model = model;
	}

	@Override
	public boolean isEditable(final IHUTreeNode node)
	{
		if (!(node instanceof HUItemMITreeNode))
		{
			return false;
		}

		if (node.isReadonly())
		{
			return false;
		}

		if (node.getProduct() == null)
		{
			return false;
		}
		return true;
	}

	@Override
	public Object getValue(final IHUTreeNode node)
	{
		return node.getQty();
	}

	@Override
	public void setValue(final IHUTreeNode node, final Object value)
	{
		Check.assumeInstanceOf(node, HUItemMITreeNode.class, "node");
		final HUItemMITreeNode itemNode = (HUItemMITreeNode)node;

		final BigDecimal qty = convertToBigDecimal(value);
		final BigDecimal nodeQty = itemNode.getQty();
		if (Check.isEmpty(nodeQty) && Check.isEmpty(qty))
		{
			return;
		}

		if (!Check.isEmpty(nodeQty) && nodeQty.equals(qty))
		{
			return;
		}

		final IAction action = new SetHUQtyAction(model, itemNode, qty);
		model.getHistory().execute(action);
	}

	private BigDecimal convertToBigDecimal(final Object value)
	{
		final BigDecimal qty;
		if (value == null)
		{
			qty = null;
		}
		else if (value instanceof BigDecimal)
		{
			qty = (BigDecimal)value;
		}
		else
		{
			final String qtyStr = value.toString();
			if (qtyStr.trim().isEmpty())
			{
				qty = BigDecimal.ZERO;
			}
			else
			{
				qty = new BigDecimal(qtyStr);
			}
		}

		return qty;
	}

	@Override
	public Class<?> getColumnType()
	{
		return BigDecimal.class;
	}
}
