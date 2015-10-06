package org.compiere.grid.ed;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.logging.Level;

import org.adempiere.document.model.IDocumentDeliveryLocation;
import org.adempiere.document.service.IDocumentLocationBL;
import org.adempiere.model.GridTabWrapper;
import org.adempiere.ui.AbstractContextMenuAction;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;

public class DeliveryToAddressRefresh extends AbstractContextMenuAction
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
			log.log(Level.SEVERE, "Couldn't get grid tab");
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
		final IDocumentDeliveryLocation location = GridTabWrapper.create(getGridTab(), IDocumentDeliveryLocation.class);
		documentLocationBL.setDeliveryToAddress(location);
	}

}
