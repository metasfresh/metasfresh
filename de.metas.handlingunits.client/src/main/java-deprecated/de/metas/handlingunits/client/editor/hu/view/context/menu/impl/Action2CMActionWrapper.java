package de.metas.handlingunits.client.editor.hu.view.context.menu.impl;

import org.adempiere.util.Check;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMAction;
import de.metas.handlingunits.client.editor.hu.view.context.menu.ICMActionGroup;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

/**
 * Wraps a given {@link IAction} and makes it behave like an {@link ICMAction}.
 * 
 * @author tsa
 * 
 */
public class Action2CMActionWrapper implements ICMAction
{
	private final IAction action;
	private final String id;
	private final ICMActionGroup actionGroup;

	public Action2CMActionWrapper(final IAction action,
			final String id,
			final ICMActionGroup actionGroup)
	{
		super();

		Check.assumeNotNull(action, "action not null");
		this.action = action;
		this.id = id;
		this.actionGroup = actionGroup;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public String getName()
	{
		return action.getActionName();
	}

	@Override
	public ICMActionGroup getCMActionGroup()
	{
		return actionGroup;
	}

	@Override
	public boolean isAvailable(HUEditorModel model, IHUTreeNode node)
	{
		return true;
	}

	@Override
	public void execute(HUEditorModel model, IHUTreeNode node)
	{
		model.getHistory().execute(action);
	}

}
