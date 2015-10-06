package de.metas.handlingunits.client.editor.command.view.history.impl;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.command.model.action.impl.NullAction;
import de.metas.handlingunits.client.editor.command.model.history.IHistory;
import de.metas.handlingunits.client.editor.command.view.action.RedoAction;
import de.metas.handlingunits.client.editor.command.view.action.SwingActionType;
import de.metas.handlingunits.client.editor.command.view.action.UndoAction;
import de.metas.handlingunits.client.editor.command.view.history.IHistorySupport;

public class HistorySupport implements IHistorySupport
{
	private final AbstractAction undoAction;
	private final AbstractAction redoAction;

	public HistorySupport(final IHistory history)
	{
		super();

		undoAction = new UndoAction(history);
		redoAction = new RedoAction(history);
	}

	@Override
	public <T extends IAction> void updateView(final SwingActionType actionType, final T modelAction)
	{
		final AbstractAction viewAction;

		switch (actionType)
		{
			case Undo:
				viewAction = undoAction;
				break;
			case Redo:
				viewAction = redoAction;
				break;
			default:
				throw new IllegalArgumentException("Invalid SwingActionType: " + actionType);
		}

		viewAction.putValue(Action.NAME, buildActionName(actionType.toString(), modelAction.getActionName()));

		final boolean isExecutable = !modelAction.equals(NullAction.instance);

		if (isExecutable && !viewAction.isEnabled())
		{
			viewAction.setEnabled(true);
		}
		else if (!isExecutable && viewAction.isEnabled())
		{
			viewAction.setEnabled(false);
		}
	}

	private String buildActionName(final String baseName, final String actionName)
	{
		return actionName == null ? baseName : baseName + " (" + actionName + ")";
	}

	@Override
	public AbstractAction getUndoAction()
	{
		return undoAction;
	}

	@Override
	public AbstractAction getRedoAction()
	{
		return redoAction;
	}
}
