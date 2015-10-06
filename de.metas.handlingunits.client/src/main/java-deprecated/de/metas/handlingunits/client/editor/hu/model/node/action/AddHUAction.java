package de.metas.handlingunits.client.editor.hu.model.node.action;

import de.metas.handlingunits.client.editor.attr.helper.StorageHelper;
import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemHUTreeNode;

/**
 * Creates and adds a new {@link I_M_HU} to given HU Item material line
 * 
 * @author tsa
 */
public class AddHUAction implements IAction
{
	private final HUEditorModel model;
	private final IHUTreeNode node;

	private final IHUTreeNode childNode;

	public AddHUAction(final HUEditorModel model, final IHUTreeNode node, final I_M_HU_PI huPI)
	{
		super();

		this.model = model;
		this.node = node;

		childNode = createChildNode(huPI);
	}

	private IHUTreeNode createChildNode(final I_M_HU_PI huPI)
	{
		final I_M_HU_Item parentItem;
		if (node instanceof HUItemHUTreeNode)
		{
			final HUItemHUTreeNode itemNode = (HUItemHUTreeNode)node;
			parentItem = itemNode.getM_HU_Item();
		}
		else
		{
			parentItem = null;
		}

		return model.getHUTreeNodeFactory()
				.createNodeRecursively(model.getGlobalContextProvider(), huPI, parentItem);
	}

	@Override
	public String getActionName()
	{
		return "Add HU";
	}

	@Override
	public void doIt()
	{
		// add node to parent
		model.addNode(childNode, node);

		// // allocate old qtys
		// QtyHelper.allocateOldQtysTopDownAndRefresh(model, node.getChildren());

		// propagate attribute values after node insertion
		StorageHelper.propagateNodeValues(childNode);
	}

	@Override
	public void undoIt()
	{
		// propagate empty values after node deletion
		StorageHelper.propagateEmptyValues(childNode);

		// FIXME: make sure there are no Qty on HU node
		// // allocate empty qtys
		// QtyHelper.allocateEmptyQtysTopDownAndRefresh(model, childNode.getChildren());

		// remove node from parent
		model.removeNodeFromParent(childNode);
	}
}
