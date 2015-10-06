package de.metas.handlingunits.tree.node.allocation.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.compiere.model.I_M_Product;

import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.client.editor.hu.view.context.menu.impl.ConvertHUDocumentNodeToHUNodesCMAction;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.RootHUTreeNode;
import de.metas.handlingunits.tree.node.impl.AbstractTreeNode;

public class HUDocumentTreeNode extends AbstractTreeNode<IHUDocumentTreeNode> implements IHUDocumentTreeNode
{
	private final IHUDocument document;

	private final IHUTreeNodeCMActionProvider actionProvider = new IHUTreeNodeCMActionProvider()
	{
		@Override
		public List<ICMAction> retrieveCMActions(final HUEditorModel model, final IHUTreeNode node)
		{
			if (node instanceof RootHUTreeNode)
			{
				final List<ICMAction> actions = new ArrayList<ICMAction>();

				//
				// Transfer HU action
				final I_M_HU innerHU = document.getInnerHandlingUnit();
				if (innerHU != null)
				{
					final ICMAction action = new ConvertHUDocumentNodeToHUNodesCMAction(innerHU, HUDocumentTreeNode.this);
					actions.add(action);
				}

				return actions;
			}
			else
			{
				return Collections.emptyList();
			}
		}

	};

	public HUDocumentTreeNode(final IHUDocument document)
	{
		super();

		this.document = document;

		for (final IHUDocumentLine line : document.getLines())
		{
			addChild(new HUDocumentLineTreeNode(line));
		}
	}

	@Override
	public String getDisplayName()
	{
		return document.getDisplayName();
	}

	@Override
	public I_M_Product getProduct()
	{
		return null;
	}

	@Override
	public BigDecimal getQty()
	{
		BigDecimal totalQty = BigDecimal.ZERO;
		for (final IHUDocumentTreeNode lineTreeNode : getChildren())
		{
			totalQty = totalQty.add(lineTreeNode.getQty());
		}
		return totalQty;
	}

	@Override
	public BigDecimal getQtyAllocated()
	{
		BigDecimal totalQtyAllocated = BigDecimal.ZERO;
		for (final IHUDocumentTreeNode lineTreeNode : getChildren())
		{
			totalQtyAllocated = totalQtyAllocated.add(lineTreeNode.getQtyAllocated());
		}
		return totalQtyAllocated;
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
		BigDecimal totalQtyRemaining = BigDecimal.ZERO;
		for (final IHUDocumentTreeNode lineTreeNode : getChildren())
		{
			totalQtyRemaining = totalQtyRemaining.add(lineTreeNode.getQtyRemaining());
		}
		return totalQtyRemaining;
	}

	@Override
	public IQtyRequest unlockQty(final BigDecimal qty)
	{
		throw new UnsupportedOperationException();
	}

	public IHUDocument getHUDocument()
	{
		return document;
	}

	@Override
	public IHUTreeNodeCMActionProvider getHUTreeNodeCMActionProvider()
	{
		return actionProvider;
	}
}
