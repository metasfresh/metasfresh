package de.metas.handlingunits.client.editor.hu.model.node.action;

import java.math.BigDecimal;

import org.adempiere.util.Check;
import org.adempiere.util.collections.Predicate;

import de.metas.handlingunits.client.editor.attr.helper.StorageHelper;
import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.command.model.action.impl.CompositeAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUItemMITreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUTreeNode;

/**
 * Remove HU
 * 
 * @author tsa
 */
public class RemoveHUAction implements IAction
{
	private final HUEditorModel model;
	private final HUTreeNode node;

	private final IHUTreeNode parentNode;
	private final int index;

	private final CompositeAction beforeRemoveAction = new CompositeAction();

	public RemoveHUAction(final HUEditorModel model, final HUTreeNode node)
	{
		super();

		Check.assumeNotNull(model, "model not null");
		this.model = model;

		Check.assumeNotNull(node, "node not null");
		this.node = node;

		parentNode = node.getParent();
		index = parentNode.getChildIndex(node);

		//
		// Build remove actions
		model.navigatePostOrder(node, new Predicate<IHUTreeNode>()
		{

			@Override
			public boolean evaluate(final IHUTreeNode node)
			{
				if (node instanceof HUItemMITreeNode)
				{
					final HUItemMITreeNode itemNode = (HUItemMITreeNode)node;
					final SetHUQtyAction setZeroQtyAction = new SetHUQtyAction(model, itemNode, BigDecimal.ZERO);
					beforeRemoveAction.addAction(setZeroQtyAction);
				}
				return false;
			}
		});
	}

	@Override
	public String getActionName()
	{
		return "Remove HU";
	}

	@Override
	public void doIt()
	{
		// propagate empty values after node deletion
		StorageHelper.propagateEmptyValues(node);

		beforeRemoveAction.doIt();

		// remove node from parent
		model.removeNodeFromParent(node);
	}

	@Override
	public void undoIt()
	{
		// add node to parent
		model.insertNodeInto(node, parentNode, index);

		// propagate attribute values after node insertion
		StorageHelper.propagateNodeValues(node);

		beforeRemoveAction.undoIt();
	}
}
