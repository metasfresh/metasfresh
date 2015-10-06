package de.metas.handlingunits.client.editor.command.model.action.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.util.Check;

import de.metas.handlingunits.client.editor.command.model.action.IAction;

public class CompositeAction implements IAction
{
	private final List<IAction> actions = new ArrayList<IAction>();

	private boolean allowDoIt = true;

	public void addAction(final IAction action)
	{
		Check.assumeNotNull(action, "action not null");
		actions.add(action);
	}

	@Override
	public String getActionName()
	{
		return "(composite)";
	}

	@Override
	public void doIt()
	{
		Check.assume(allowDoIt, "DoIt was not executed before");
		allowDoIt = false;

		for (final IAction action : actions)
		{
			action.doIt();
		}
	}

	@Override
	public void undoIt()
	{
		Check.assume(!allowDoIt, "DoIt was executed before");
		allowDoIt = true;

		final List<IAction> actionsReversed = new ArrayList<IAction>(actions);
		Collections.reverse(actionsReversed);

		for (final IAction action : actionsReversed)
		{
			action.undoIt();
		}
	}

}
