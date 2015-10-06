package de.metas.handlingunits.client.form;

import java.awt.Cursor;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.model.GridTab;
import org.compiere.process.ProcessInfo;
import org.compiere.swing.CPanel;
import org.compiere.util.ASyncProcess;

import de.metas.adempiere.form.IClientUI;
import de.metas.handlingunits.client.editor.hu.view.swing.HUEditorFrame;
import de.metas.handlingunits.document.IDataSource;
import de.metas.handlingunits.document.IHUDocumentFactoryService;

public class HUEditorForm extends CPanel implements FormPanel, ASyncProcess, ISvrProcessPrecondition
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8268421647396562674L;

	private HUEditorFrame editor;
	private boolean locked = false;

	@Override
	public boolean isPreconditionApplicable(final GridTab gridTab)
	{
		final String tableName = gridTab.getTableName();
		return Services.get(IHUDocumentFactoryService.class).isSupported(tableName);
	}

	@Override
	public void init(final int WindowNo, final FormFrame frame)
	{
		editor = new HUEditorFrame(frame);

		try
		{
			final IDataSource dataSource = Services.get(IHUDocumentFactoryService.class)
					.createDataSource(frame.getProcessInfo());
			editor.setDataDource(dataSource);
		}
		catch (final Exception e)
		{
			Services.get(IClientUI.class).warn(WindowNo, e);
			dispose();
			
		}
	}
	
	@Override
	public void dispose()
	{
		if (editor != null)
		{
			editor.dispose();
			editor = null;
		}
	}

	@Override
	public void lockUI(final ProcessInfo pi)
	{
		getParent().setEnabled(false);
		locked = true;
	}

	@Override
	public void unlockUI(final ProcessInfo pi)
	{
		getParent().setCursor(Cursor.getDefaultCursor());
		setCursor(Cursor.getDefaultCursor());
		getParent().setEnabled(true);

		// statusBar.setStatusLine(pi.getSummary());

		locked = false;
	}

	@Override
	public boolean isUILocked()
	{
		return locked;
	}

	@Override
	public void executeASync(final ProcessInfo pi)
	{
		Check.assume(false, "Method executeASync is not called");
	}
}
