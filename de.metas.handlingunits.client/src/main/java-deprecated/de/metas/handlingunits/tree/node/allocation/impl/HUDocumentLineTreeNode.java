package de.metas.handlingunits.tree.node.allocation.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IdentityHashSet;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import de.metas.handlingunits.api.IHandlingUnitsDAO;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.context.menu.IHUTreeNodeCMActionProvider;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.client.editor.hu.view.context.menu.impl.AddHUCMAction;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.tree.factory.IHUTreeNodeFactory;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemMITreeNode;
import de.metas.handlingunits.tree.node.hu.impl.RootHUTreeNode;
import de.metas.handlingunits.tree.node.impl.AbstractTreeNode;

public class HUDocumentLineTreeNode extends AbstractTreeNode<IHUDocumentTreeNode> implements IHUDocumentTreeNode
{
	private final IHUDocumentLine documentLine;

	private final IdentityHashSet<IQtyRequest> qtyRequests = new IdentityHashSet<IQtyRequest>();
	private final LockedQty qtyLocked;
	private BigDecimal qtyAllocated = BigDecimal.ZERO;
	private I_C_UOM uom;

	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	public void setC_UOM(I_C_UOM uom)
	{
		this.uom = uom;
	}

	private class QtyRequest implements IQtyRequest
	{
		private final BigDecimal qty;

		public QtyRequest(final BigDecimal qty)
		{
			super();
			Check.assumeNotNull(qty, "qty not null");
			this.qty = qty;
		}

		@Override
		public BigDecimal getQty()
		{
			return qty;
		}

		@Override
		public String toString()
		{
			return getClass().getSimpleName() + "["
					+ "qty=" + qty
					+ "]";
		}
	}

	private static class LockedQty implements IQtyRequest
	{
		private BigDecimal qty;

		public LockedQty(final BigDecimal qtyInitial)
		{
			Check.assumeNotNull(qtyInitial, "qtyInitial not null");
			qty = qtyInitial;
		}

		@Override
		public String toString()
		{
			return getClass().getSimpleName() + "["
					+ "qty=" + qty
					+ "]";
		}

		public void subtractQty(final BigDecimal qty)
		{
			this.qty = this.qty.subtract(qty);
		}

		@Override
		public BigDecimal getQty()
		{
			return qty;
		}

	}

	private final IHUTreeNodeCMActionProvider actionProvider = new IHUTreeNodeCMActionProvider()
	{
		@Override
		public List<ICMAction> retrieveCMActions(final HUEditorModel model, final IHUTreeNode node)
		{
			if (node instanceof RootHUTreeNode)
			{
				final List<ICMAction> actions = new ArrayList<ICMAction>();

				//
				// Add HU PI actions
				final List<I_M_HU_PI> huPIsToUse;
				final List<I_M_HU_PI> huPIs = documentLine.getAvailableHandlingUnitPIs();
				if (huPIs == IHUDocumentLine.ANY_HU_PI)
				{
					huPIsToUse = Services.get(IHandlingUnitsDAO.class).retrieveAvailablePIs(Env.getCtx());
				}
				else
				{
					huPIsToUse = huPIs;
				}

				for (final I_M_HU_PI huPI : huPIsToUse)
				{
					actions.add(new AddHUCMAction(huPI));
				}

				return actions;
			}
			else
			{
				return Collections.emptyList();
			}
		}

	};

	public HUDocumentLineTreeNode(final IHUDocumentLine line)
	{
		super();

		Check.assumeNotNull(line, "line not null");
		documentLine = line;

		qtyLocked = new LockedQty(documentLine.getQtyAllocated());
		addQtyRequest(qtyLocked);
		
		setC_UOM(documentLine.getC_UOM());
	}

	@Override
	public String getDisplayName()
	{
		return documentLine.getDisplayName();
	}

	@Override
	public I_M_Product getProduct()
	{
		return documentLine.getM_Product();
	}

	@Override
	public BigDecimal getQty()
	{
		return documentLine.getQty();
	}

	@Override
	public BigDecimal getQtyAllocated()
	{
		return qtyAllocated;
	}

	@Override
	public IQtyRequest requestQty(final BigDecimal qty)
	{
		final BigDecimal qtyMax = getQtyRemaining();
		final IQtyRequest qtyRequest;
		if (qty == null)
		{
			qtyRequest = new QtyRequest(BigDecimal.ZERO);
		}
		else if (qty.compareTo(qtyMax) <= 0)
		{
			qtyRequest = new QtyRequest(qty);
		}
		else
		{
			qtyRequest = new QtyRequest(qtyMax);
		}

		requestQty(qtyRequest);

		return qtyRequest;
	}

	@Override
	public IQtyRequest unlockQty(final BigDecimal qty)
	{
		final BigDecimal qtyMax = qtyLocked.getQty();
		final IQtyRequest qtyRequest;
		if (qty == null)
		{
			qtyRequest = new QtyRequest(BigDecimal.ZERO);
		}
		else if (qty.compareTo(qtyMax) <= 0)
		{
			qtyRequest = new QtyRequest(qty);
		}
		else
		{
			qtyRequest = new QtyRequest(qtyMax);
		}

		qtyLocked.subtractQty(qtyRequest.getQty());
		qtyAllocated = qtyAllocated.subtract(qtyRequest.getQty());

		requestQty(qtyRequest);

		return qtyRequest;
	}

	@Override
	public void requestQty(final IQtyRequest qtyRequest)
	{
		if (qtyRequest == null)
		{
			// just skip null values, but tolerate them
			return;
		}

		addQtyRequest(qtyRequest);
	}

	private void addQtyRequest(final IQtyRequest qtyRequest)
	{
		if (qtyRequests.contains(qtyRequest))
		{
			throw new AdempiereException("" + qtyRequest + " was already requested");
		}

		qtyRequests.add(qtyRequest);
		qtyAllocated = qtyAllocated.add(qtyRequest.getQty());
	}

	@Override
	public void releaseQty(final IQtyRequest qtyRequest)
	{
		if (qtyRequest == null)
		{
			// allow null values for cleaner code
			return;
		}
		if (!qtyRequests.remove(qtyRequest))
		{
			throw new AdempiereException("Request " + qtyRequest + " was not found");
		}

		qtyAllocated = qtyAllocated.subtract(qtyRequest.getQty());
	}

	@Override
	public BigDecimal getQtyRemaining()
	{
		// FIXME: consider UOM
		return documentLine.getQty().subtract(qtyAllocated);
	}

	/**
	 * 
	 * @return document line; never return null
	 */
	public IHUDocumentLine getHUDocumentLine()
	{
		return documentLine;
	}

	public Object getTrxReferencedModel()
	{
		return documentLine.getTrxReferencedModel();
	}

	public boolean isReadonly()
	{
		return documentLine.isReadOnly();
	}

	@Override
	public IHUTreeNodeCMActionProvider getHUTreeNodeCMActionProvider()
	{
		return actionProvider;
	}
	
	public IHUTreeNode convertInnerHUToTreeNode(final HUEditorModel model)
	{
		final IHUTreeNodeFactory nodeFactory = model.getHUTreeNodeFactory();
		
		final IHUDocumentLine documentLine = getHUDocumentLine();
		final I_M_HU_Item huItem = documentLine.getInnerHUItem();

		if (huItem == null)
		{
			return null;
		}
		final HUItemMITreeNode huItemNode = (HUItemMITreeNode)nodeFactory.createNode(huItem);

		//
		// Allocate Document Line Node to HU Item Node
		{
			final BigDecimal qty = getQtyRemaining();
			final IQtyRequest qtyRequest = requestQty(qty);
			// FIXME: if qtyRequest != qty throw exception and deallocate everything
			huItemNode.setHUDocumentTreeNodeInitial(this);
			huItemNode.setHUDocumentTreeNode(this);
			huItemNode.setQtyRequestInitial(qtyRequest);
			huItemNode.setQtyRequest(qtyRequest);
		}
		
		model.getHUDocumentsModel().fireNodeChanged(this);

		return huItemNode;
	}

}
