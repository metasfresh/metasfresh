package de.metas.handlingunits.client.editor.command.view.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;
import de.metas.handlingunits.client.editor.hu.view.swing.HUEditorPanel;

public class SaveAction extends AbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8411148626847423541L;

	private final HUEditorPanel huEditor;

	public SaveAction(final HUEditorPanel huEditor)
	{
		super(SwingActionType.Save.toString());

		Check.assumeNotNull(huEditor, "huEditor not null");
		this.huEditor = huEditor;

		setEnabled(true);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		final int windowNo = Env.getWindowNo(huEditor);
		try
		{
			huEditor.setEnabled(false);
			
			huEditor.save();
			
			Services.get(IClientUI.class).info(windowNo, "Saved");
		}
		catch (final Exception ex)
		{
			Services.get(IClientUI.class).warn(windowNo, ex);
		}
		finally
		{
			huEditor.setEnabled(true);
		}
	}
}
