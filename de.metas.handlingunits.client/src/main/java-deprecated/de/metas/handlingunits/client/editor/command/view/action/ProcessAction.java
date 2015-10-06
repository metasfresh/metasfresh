package de.metas.handlingunits.client.editor.command.view.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;
import de.metas.handlingunits.client.editor.hu.view.swing.HUEditorPanel;

public class ProcessAction extends AbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8411148626847423541L;

	private final HUEditorPanel huEditor;

	public ProcessAction(final HUEditorPanel huEditor)
	{
		super(SwingActionType.Process.toString());

		Check.assumeNotNull(huEditor, "huEditor not null");
		this.huEditor = huEditor;

		setEnabled(true);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		boolean processed = false;
		try
		{
			huEditor.processDataSource();
			processed = true;
		}
		catch (final Exception ex)
		{
			final int windowNo = Env.getWindowNo(huEditor);
			Services.get(IClientUI.class).warn(windowNo, ex);
		}

		// Close containing window (if any)
		if (processed)
		{
			final JFrame frame = Env.getFrame(huEditor);
			if (frame != null)
			{
				frame.dispose();
			}
		}
	}
}
