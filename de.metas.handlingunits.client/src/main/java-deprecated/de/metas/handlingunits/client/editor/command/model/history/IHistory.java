package de.metas.handlingunits.client.editor.command.model.history;

import de.metas.handlingunits.client.editor.command.model.action.IAction;

public interface IHistory
{
	/**
	 * Undo last action.
	 */
	void undo();

	/**
	 * Redo last action.
	 */
	void redo();

	/**
	 * Add performed action to the undo queue and clear redo queue.
	 * 
	 * @param action
	 */
	void execute(IAction action);
}
