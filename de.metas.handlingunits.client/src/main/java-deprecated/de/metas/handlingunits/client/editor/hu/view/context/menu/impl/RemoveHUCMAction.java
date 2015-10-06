package de.metas.handlingunits.client.editor.hu.view.context.menu.impl;

import org.adempiere.util.Check;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.model.node.action.RemoveHUAction;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMActionGroup;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUTreeNode;

public class RemoveHUCMAction implements ICMAction
{
	public static final RemoveHUCMAction instance = new RemoveHUCMAction();
	private static final ICMActionGroup group = new CMActionGroup("Remove HU");

	private final String id;

	private RemoveHUCMAction()
	{
		super();
		this.id = getClass().getName();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public String getName()
	{
		return "Remove";
	}

	@Override
	public ICMActionGroup getCMActionGroup()
	{
		return group;
	}

	@Override
	public boolean isAvailable(final HUEditorModel model, final IHUTreeNode node)
	{
		if (node.isReadonly())
		{
			return false;
		}

		if (!(node instanceof HUTreeNode))
		{
			return false;
		}

		Check.assumeNotNull(node.getParent(), "node parent not null");

		return true;
	}

	@Override
	public void execute(final HUEditorModel model, final IHUTreeNode node)
	{
		final IAction action = new RemoveHUAction(model, (HUTreeNode)node);
		model.getHistory().execute(action);
	}
}
