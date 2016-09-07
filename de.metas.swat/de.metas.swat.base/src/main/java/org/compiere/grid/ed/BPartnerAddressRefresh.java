package org.compiere.grid.ed;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.ui.AbstractContextMenuAction;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;

import de.metas.document.IDocumentLocationBL;
import de.metas.document.model.IDocumentLocation;

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
		final IDocumentLocation location = InterfaceWrapperHelper.create(getGridTab(), IDocumentLocation.class);
		documentLocationBL.setBPartnerAddress(location);
	}

}
