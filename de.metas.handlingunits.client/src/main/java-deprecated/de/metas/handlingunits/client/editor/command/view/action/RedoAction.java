package de.metas.handlingunits.client.editor.command.view.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import de.metas.handlingunits.client.editor.command.model.history.IHistory;

public class RedoAction extends AbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7906873105545065189L;

	private final IHistory history;

	public RedoAction(final IHistory history)
	{
		super(SwingActionType.Redo.toString());

		this.history = history;
		setEnabled(false);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		history.redo();
	}
}
