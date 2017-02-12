package org.compiere.grid.ed;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.ui.AbstractContextMenuAction;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;

import de.metas.document.IDocumentLocationBL;
import de.metas.document.model.IDocumentBillLocation;

public class BillToAddressRefresh extends AbstractContextMenuAction
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
		final GridTab gridTab = getGridTab();
		if (gridTab == null)
		{
			log.error("Couldn't get grid tab");
			return false;
		}
		return true;
	}

	@Override
	public boolean isRunnable()
	{
		return true;
	}

	@Override
	public void run()
	{
		final IDocumentLocationBL documentLocationBL = Services.get(IDocumentLocationBL.class);
		final IDocumentBillLocation location = InterfaceWrapperHelper.create(getGridTab(), IDocumentBillLocation.class);
		documentLocationBL.setBillToAddress(location);	

	}

}
