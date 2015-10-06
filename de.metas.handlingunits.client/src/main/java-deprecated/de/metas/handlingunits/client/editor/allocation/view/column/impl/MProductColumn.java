package de.metas.handlingunits.client.editor.allocation.view.column.impl;

import org.compiere.model.I_M_Product;

import de.metas.handlingunits.client.editor.view.column.impl.AbstractTreeTableColumn;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;

public final class MProductColumn extends AbstractTreeTableColumn<IHUDocumentTreeNode>
{
	public MProductColumn(final String columnHeader)
	{
		super(columnHeader);
	}

	@Override
	public Object getValue(final IHUDocumentTreeNode node)
	{
		return node.getDisplayName();
	}

	@Override
	public String getDisplayName(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		final I_M_HU_PI_Item_Product itemProduct = (I_M_HU_PI_Item_Product)value;
		final I_M_Product product = itemProduct.getM_Product();
		if (product == null)
		{
			return itemProduct.toString();
		}

		final StringBuilder name = new StringBuilder().append(product.getName()).append("#").append(itemProduct.getM_HU_PI_Item_Product_ID());
		return name.toString();
	}

	@Override
	public Class<?> getColumnType()
	{
		return String.class;
	}
}
