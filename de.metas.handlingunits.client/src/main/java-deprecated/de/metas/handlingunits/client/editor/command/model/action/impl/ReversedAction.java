package de.metas.handlingunits.client.editor.command.model.action.impl;

import org.adempiere.util.Check;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.handlingunits.client.editor.command.model.action.IAction;

/**
 * Wraps a given {@link IAction} and it's executing the reversal of it.
 * <ul>
 * <li>when {@link #doIt()} is called then {@link #undoIt()} will be called in wrapped action
 * <li>when {@link #undoIt()} is called then {@link #doIt()} will be called in wrapped action
 * </ul>
 * 
 * @author tsa
 * 
 */
public final class ReversedAction implements IAction
{
	private final IAction action;

	public ReversedAction(final IAction action)
	{
		super();

		Check.assumeNotNull(action, "action not null");
		this.action = action;
	}

	@Override
	public String getActionName()
	{
		return Msg.translate(Env.getCtx(), "Undo")
				+ " "
				+ action.getActionName();
	}

	private boolean doItExecuted = false;

	@Override
	public void doIt()
	{
		Check.assume(!doItExecuted, "doIt not executed");

		action.undoIt();

		doItExecuted = true;
	}

	@Override
	public void undoIt()
	{
		Check.assume(doItExecuted, "doIt executed");

		action.doIt();

		doItExecuted = false;
	}

}
