package org.compiere.grid.ed;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.ui.AbstractContextMenuAction;
import org.compiere.model.GridTab;

import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import de.metas.util.Services;

public class BPartnerAddressRefresh extends AbstractContextMenuAction
{

	@Override
	public String getName()
	{
		return "Refresh";
	}

	@Override
	public String getIcon()
	{
		return "Refresh16";
	}

	@Override
	public boolean isAvailable()
	{
		return true;
	}

	@Override
	public boolean isRunnable()
	{
		final GridTab gridTab = getGridTab();
		return gridTab != null;
	}

	@Override
	public void run()
	{
		final IDocumentLocationBL documentLocationBL = Services.get(IDocumentLocationBL.class);
		final IDocumentLocationAdapter location = InterfaceWrapperHelper.create(getGridTab(), IDocumentLocationAdapter.class);
		documentLocationBL.setBPartnerAddress(location);
	}

}
