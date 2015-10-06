package de.metas.handlingunits.client.editor.hu.view.column.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;
import org.compiere.model.I_M_Product;
import org.compiere.util.Util;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.node.action.SetHUProductAction;
import de.metas.handlingunits.client.editor.view.column.impl.AbstractTreeTableColumn;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNodeProduct;

public final class MProductColumn extends AbstractTreeTableColumn<IHUTreeNode>
{
	private final HUEditorModel model;

	public MProductColumn(final String columnHeader, final HUEditorModel model)
	{
		super(columnHeader);

		this.model = model;
	}

	@Override
	public boolean isEditable(final IHUTreeNode node)
	{
		if (node.isReadonly())
		{
			return false;
		}
		
		return !node.getAvailableProducts().isEmpty();
	}

	@Override
	public IHUTreeNodeProduct getValue(final IHUTreeNode node)
	{
		return node.getProduct();
	}

	@Override
	public void setValue(final IHUTreeNode node, final Object value)
	{
		final IHUTreeNodeProduct productNew = (IHUTreeNodeProduct)value;

		final IHUTreeNodeProduct productOld = node.getProduct();
		if (Util.equals(productOld, productNew))
		{
			return;
		}

		final IAction action = new SetHUProductAction(model, node, productNew);
		model.getHistory().execute(action);
	}

	@Override
	public boolean isCombobox()
	{
		return true;
	}

	@Override
	public List<Object> getAvailableValuesList(final IHUTreeNode node)
	{
		final List<IHUTreeNodeProduct> availableProducts = node.getAvailableProducts();
		final List<Object> retValue = new ArrayList<Object>(availableProducts);
		return retValue;
	}

	@Override
	public String getDisplayName(final Object value)
	{
		if (value == null)
		{
			return null;
		}

		Check.assume(value instanceof IHUTreeNodeProduct, "{0} instanceof {1}", value, IHUTreeNodeProduct.class);
		final IHUTreeNodeProduct itemProduct = (IHUTreeNodeProduct)value;

		final I_M_Product product = itemProduct.getM_Product();
		if (product == null)
		{
			return itemProduct.toString();
		}

		return product.getName();
	}

	@Override
	public Class<?> getColumnType()
	{
		return String.class;
	}
}
