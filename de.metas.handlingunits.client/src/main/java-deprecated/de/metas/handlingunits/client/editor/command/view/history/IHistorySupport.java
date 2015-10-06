package de.metas.handlingunits.client.editor.command.view.history;

import javax.swing.AbstractAction;

import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.command.view.action.SwingActionType;

public interface IHistorySupport
{
	<T extends IAction> void updateView(SwingActionType actionType, T modelAction);

	AbstractAction getRedoAction();

	AbstractAction getUndoAction();
}
