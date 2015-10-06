package de.metas.handlingunits.tree.node.hu.impl;

import java.util.Collections;
import java.util.List;

import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.model.context.menu.impl.CompositeHUTreeNodeCMActionProvider;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNodeProduct;
import de.metas.handlingunits.tree.node.impl.AbstractTreeNode;

public abstract class AbstractHUTreeNode extends AbstractTreeNode<IHUTreeNode> implements IHUTreeNode
{
	private IHUTreeNodeProduct product;
	private boolean readonly = false;
	private final CompositeHUTreeNodeCMActionProvider actionProvider = new CompositeHUTreeNodeCMActionProvider();
	
	private static final boolean DEFAULT_Selected = true;
	private boolean selected = DEFAULT_Selected;

	public AbstractHUTreeNode()
	{
		super();
	}

	@Override
	public IHUTreeNodeProduct getProduct()
	{
		return product;
	}

	@Override
	public void setProduct(final IHUTreeNodeProduct product)
	{
		this.product = product;
	}

	@Override
	public List<IHUTreeNodeProduct> getAvailableProducts()
	{
		return Collections.emptyList();
	}

	@Override
	public boolean isReadonly()
	{
		return readonly;
	}

	@Override
	public void setReadonly(final boolean readonly)
	{
		this.readonly = readonly;
	}

	@Override
	public final IHUTreeNodeCMActionProvider getHUTreeNodeCMActionProvider()
	{
		return actionProvider;
	}

	@Override
	public final void addHUTreeNodeCMActionProvider(IHUTreeNodeCMActionProvider provider)
	{
		actionProvider.addProvider(provider);
	}

	@Override
	public void setDisplayName(String displayName)
	{
		throw new UnsupportedOperationException("method not implemented");
	}

	@Override
	public boolean isSelected()
	{
		return selected;
	}

	@Override
	public void setSelected(final boolean selected)
	{
		this.selected = selected;
	}
}
