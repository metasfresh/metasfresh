package de.metas.handlingunits.client.editor.hu.model.node.action;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.IHUTreeNodeProduct;

public class SetHUProductAction implements IAction
{
	private final HUEditorModel model;
	private final IHUTreeNode node;

	private final IHUTreeNodeProduct oldProduct;

	private final IHUTreeNodeProduct newProduct;

	private boolean isFirstRun;

	public SetHUProductAction(final HUEditorModel model, final IHUTreeNode node, final IHUTreeNodeProduct product)
	{
		super();

		this.model = model;
		this.node = node;

		oldProduct = node.getProduct();

		newProduct = product;

		isFirstRun = true;
	}

	@Override
	public String getActionName()
	{
		return "Set HU Product Action";
	}

	@Override
	public void doIt()
	{
		// set new product
		node.setProduct(newProduct);

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
		// set old product
		node.setProduct(oldProduct);

		// notify tree that model was changed
		model.fireNodeChanged(node);
	}
}
