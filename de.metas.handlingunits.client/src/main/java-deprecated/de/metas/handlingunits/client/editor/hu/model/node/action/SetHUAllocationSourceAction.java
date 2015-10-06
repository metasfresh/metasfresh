package de.metas.handlingunits.client.editor.hu.model.node.action;

import java.math.BigDecimal;
import java.util.List;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.tree.node.allocation.IHUDocumentTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNodeProduct;
import de.metas.handlingunits.tree.node.hu.impl.HUItemMITreeNode;

public class SetHUAllocationSourceAction implements IAction
{
	private final HUEditorModel model;
	private final HUItemMITreeNode node;

	private final IHUDocumentTreeNode oldDocumentNode;
	private final IHUDocumentTreeNode newDocumentNode;

	private final SetHUProductAction productAction;
	private final SetHUQtyAction qtyAction;

	private boolean isFirstRun;

	public SetHUAllocationSourceAction(final HUEditorModel model, final HUItemMITreeNode node, final IHUDocumentTreeNode documentNode)
	{
		super();

		this.model = model;
		this.node = node;

		oldDocumentNode = node.getHUDocumentLineTreeNode();
		newDocumentNode = documentNode;

		// Set Product Action
		final IHUTreeNodeProduct newProduct = SetHUAllocationSourceAction.suggestProduct(node, newDocumentNode);
		productAction = new SetHUProductAction(model, node, newProduct);
		// Set Qty to ZERO
		qtyAction = new SetHUQtyAction(model, node, BigDecimal.ZERO);

		isFirstRun = true;
	}

	private static IHUTreeNodeProduct suggestProduct(final HUItemMITreeNode node, final IHUDocumentTreeNode documentNode)
	{
		final List<IHUTreeNodeProduct> products = node.getAvailableProducts(documentNode);
		if (products == null)
		{
			return null;
		}
		else if (products.size() > 0)
		{
			return products.get(0);
		}
		else
		{
			return null;
		}
	}

	@Override
	public String getActionName()
	{
		return "Set HU Allocation Source";
	}

	@Override
	public void doIt()
	{
		// set new allocation node
		node.setHUDocumentTreeNode(newDocumentNode);

		if (oldDocumentNode != null)
		{
			qtyAction.doIt();
		}

		productAction.doIt();

		if (!isFirstRun)
		{
			// notify tree that model was changed
			model.fireNodeChanged(node);
			return;
		}
		isFirstRun = false;
	}

	@Override
	public void undoIt()
	{
		// set old allocation node
		node.setHUDocumentTreeNode(oldDocumentNode);

		// set old product
		productAction.undoIt();

		if (oldDocumentNode != null)
		{
			qtyAction.undoIt();
		}

		// notify tree that model was changed
		model.fireNodeChanged(node);
	}
}
