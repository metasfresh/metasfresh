package de.metas.handlingunits.tree.node.hu.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import de.metas.handlingunits.tree.node.hu.IHUTreeNodeProduct;

public final class RootHUTreeNode extends AbstractHUTreeNode
{
	@Override
	public String getDisplayName()
	{
		return "root";
	}

	@Override
	public IHUTreeNodeProduct getProduct()
	{
		return null;
	}

	@Override
	public void setProduct(final IHUTreeNodeProduct product)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<IHUTreeNodeProduct> getAvailableProducts()
	{
		return Collections.emptyList();
	}

	@Override
	public BigDecimal getQty()
	{
		return null;
	}
}
