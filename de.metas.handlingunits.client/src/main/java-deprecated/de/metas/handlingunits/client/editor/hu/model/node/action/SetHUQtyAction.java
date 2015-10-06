package de.metas.handlingunits.client.editor.hu.model.node.action;

import java.math.BigDecimal;

import org.adempiere.util.Check;

import de.metas.handlingunits.client.editor.allocation.model.HUDocumentsModel;
import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode.IQtyRequest;
import de.metas.handlingunits.tree.node.allocation.impl.HUDocumentLineTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemMITreeNode;

public class SetHUQtyAction implements IAction
{
	private final HUEditorModel model;
	private final HUItemMITreeNode node;

	private final HUDocumentLineTreeNode oldDocumentLineNode;
	private HUDocumentLineTreeNode newDocumentLineNode;

	private final IQtyRequest oldQtyRequest;
	private IQtyRequest newQtyRequest;
	private final BigDecimal newQty;

	private boolean allowDo = true;

	public SetHUQtyAction(final HUEditorModel model, final HUItemMITreeNode node, final BigDecimal qty)
	{
		super();

		this.model = model;
		this.node = node;

		oldDocumentLineNode = node.getHUDocumentLineTreeNode();
		oldQtyRequest = node.getQtyRequest();
		newQty = node.getQtyAvailableToLoad(qty);
		
		if (oldQtyRequest != null && newQty != null && oldQtyRequest.getQty().compareTo(newQty) == 0)
		{
			// TODO: do nothing
		}

		allowDo = true;
	}

	@Override
	public String getActionName()
	{
		return "Set HU Qty Action";
	}

	@Override
	public void doIt()
	{
		Check.assume(allowDo, "doIt shall not be executed before");
		allowDo = false;

		if (oldQtyRequest != null)
		{
			Check.assumeNotNull(oldDocumentLineNode, "oldDocumentLineNode not null");
			oldDocumentLineNode.releaseQty(oldQtyRequest);
		}

		if (newQty != null && newQty.signum() != 0)
		{
			newDocumentLineNode = node.getHUDocumentLineTreeNode();
			Check.assumeNotNull(newDocumentLineNode, "newDocumentLineNode not null");

			if (newQtyRequest == null)
			{
				newQtyRequest = newDocumentLineNode.requestQty(newQty);
			}
			else
			{
				// case: it's at least the second time when we are executing this doIt
				newDocumentLineNode.requestQty(newQtyRequest);
			}
		}
		else
		{
			newQtyRequest = null;
		}
		node.setQtyRequest(newQtyRequest);

		fireChangeEvents();
	}

	@Override
	public void undoIt()
	{
		Check.assume(!allowDo, "doIt shall be executed before");
		allowDo = true;

		if (newQtyRequest != null)
		{
			newDocumentLineNode.releaseQty(newQtyRequest);
		}

		if (oldQtyRequest != null)
		{
			oldDocumentLineNode.requestQty(oldQtyRequest);
		}
		node.setQtyRequest(oldQtyRequest);

		fireChangeEvents();
	}

	private void fireChangeEvents()
	{
		// notify tree that model was changed
		model.fireNodeChanged(node);

		final HUDocumentsModel allocationModel = model.getHUDocumentsModel();
		allocationModel.fireNodeChanged(oldDocumentLineNode);
		allocationModel.fireNodeChanged(newDocumentLineNode);
	}

}
