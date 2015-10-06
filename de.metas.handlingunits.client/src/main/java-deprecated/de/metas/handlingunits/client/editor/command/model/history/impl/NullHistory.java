package de.metas.handlingunits.client.editor.command.model.history.impl;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.command.model.history.IHistory;

/**
 * Direct {@link IAction} executor (without history)
 * 
 * @author tsa
 * 
 */
public class NullHistory implements IHistory
{

	@Override
	public void undo()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void redo()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void execute(IAction action)
	{
		action.doIt();
	}

}
