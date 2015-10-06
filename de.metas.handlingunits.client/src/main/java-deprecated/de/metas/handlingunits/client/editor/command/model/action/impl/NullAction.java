package de.metas.handlingunits.client.editor.command.model.action.impl;

import de.metas.handlingunits.client.editor.command.model.action.IAction;

public final class NullAction implements IAction
{
	public static final NullAction instance = new NullAction();

	private NullAction()
	{
		super();
	}

	@Override
	public String getActionName()
	{
		return null;
	}

	@Override
	public void doIt()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void undoIt()
	{
		throw new UnsupportedOperationException();
	}
}
