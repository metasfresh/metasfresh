package de.metas.handlingunits.client.editor.command.model.history.impl;

import java.util.ArrayList;
import java.util.List;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.command.model.action.impl.NullAction;
import de.metas.handlingunits.client.editor.command.model.history.IHistory;
import de.metas.handlingunits.client.editor.command.view.action.SwingActionType;
import de.metas.handlingunits.client.editor.command.view.history.IHistorySupport;
import de.metas.handlingunits.client.editor.command.view.history.impl.HistorySupport;

public class History implements IHistory
{
	private final List<IAction> undoable = new ArrayList<IAction>();
	private final List<IAction> redoable = new ArrayList<IAction>();

	private final HistorySupport historySupport;

	public History()
	{
		super();

		historySupport = new HistorySupport(this);
	}

	@Override
	public void undo()
	{
		dequeueUndoAction();
	}

	private void dequeueUndoAction()
	{
		if (undoable.isEmpty())
		{
			// TODO log warning "NOTHING TO UNDO"
			return;
		}
		final int lastActionIndex = undoable.size() - 1;

		final IAction action = undoable.get(lastActionIndex);
		action.undoIt();

		redoable.add(action);
		historySupport.updateView(SwingActionType.Redo, action);

		undoable.remove(lastActionIndex);
		historySupport.updateView(SwingActionType.Undo, getLastAction(undoable));
	}

	@Override
	public void redo()
	{
		dequeueRedoAction();
	}

	private void dequeueRedoAction()
	{
		if (redoable.isEmpty())
		{
			// TODO log warning "NOTHING TO REDO"
			return;
		}
		final int lastActionIndex = redoable.size() - 1;

		final IAction action = redoable.get(lastActionIndex);
		action.doIt();

		undoable.add(action);
		historySupport.updateView(SwingActionType.Undo, action);

		redoable.remove(lastActionIndex);
		historySupport.updateView(SwingActionType.Redo, getLastAction(redoable));
	}

	@Override
	public void execute(final IAction action)
	{
		// add undo-able action
		undoable.add(action);
		// clear redo-able actions
		redoable.clear();
		// execute action
		action.doIt();

		historySupport.updateView(SwingActionType.Undo, action);
		historySupport.updateView(SwingActionType.Redo, getLastAction(redoable));
	}

	private IAction getLastAction(final List<IAction> actionList)
	{
		if (!actionList.isEmpty())
		{
			return actionList.get(actionList.size() - 1);
		}
		return NullAction.instance;
	}

	public IHistorySupport getHistorySupport()
	{
		return historySupport;
	}
}
