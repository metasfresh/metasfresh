/**
 *
 */
package de.metas.picking.terminal;

import org.compiere.apps.form.FormFrame;

import de.metas.adempiere.form.terminal.ITerminalBasePanel;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

public interface IPickingTerminalPanel extends ITerminalBasePanel
{
	public abstract void init(int windowNo, FormFrame frame);

	static enum ResetFilters
	{
		No, Yes, IfNoResult,
	}

	@Override
	ITerminalContext getTerminalContext();

	@Override
	FormFrame getComponent();

	@Override
	void logout();

	void next();

	void requestFocus();

	void refreshLines(ResetFilters resetFilters);

	void setResultHtml(String resultHtml);

	void createPackingDetails();
}
