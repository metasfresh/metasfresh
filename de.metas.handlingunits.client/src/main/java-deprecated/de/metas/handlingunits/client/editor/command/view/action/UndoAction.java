package de.metas.handlingunits.client.editor.command.view.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import de.metas.handlingunits.client.editor.command.model.history.IHistory;

public class UndoAction extends AbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8411148626847423541L;

	private final IHistory history;

	public UndoAction(final IHistory history)
	{
		super(SwingActionType.Undo.toString());

		this.history = history;
		setEnabled(false);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		history.undo();
	}
}
