package de.metas.handlingunits.tree.node.allocation.impl;

import java.math.BigDecimal;

import org.compiere.model.I_M_Product;

import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.model.context.menu.impl.NullHUTreeNodeCMActionProvider;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;
import de.metas.handlingunits.tree.node.impl.AbstractTreeNode;

public class RootHUDocumentTreeNode extends AbstractTreeNode<IHUDocumentTreeNode> implements IHUDocumentTreeNode
{
	@Override
	public String getDisplayName()
	{
		return "root";
	}

	@Override
	public I_M_Product getProduct()
	{
		return null;
	}

	@Override
	public BigDecimal getQty()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getQtyAllocated()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public IQtyRequest requestQty(final BigDecimal qty)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void requestQty(final IQtyRequest qtyRequest)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void releaseQty(final IQtyRequest qtyRequest)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getQtyRemaining()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public IQtyRequest unlockQty(final BigDecimal qty)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public IHUTreeNodeCMActionProvider getHUTreeNodeCMActionProvider()
	{
		return NullHUTreeNodeCMActionProvider.instance;
	}
}
