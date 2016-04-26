package org.adempiere.bpartner.service.impl;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.adempiere.bpartner.service.IBPartnerSOCreditStatusUpdater;
import org.adempiere.bpartner.service.IBPartnerStatsBL;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class BPartnerSOCreditStatusUpdater implements IBPartnerSOCreditStatusUpdater
{
	@Override
	public void updateSOCreditStatus(Properties ctx, Set<Integer> bpartnerIds, String trxName)
	{
		// Services
		final IBPartnerStatsBL bpartnerStatsBL = Services.get(IBPartnerStatsBL.class);
		if (bpartnerIds == null || bpartnerIds.isEmpty())
		{
			return;
		}
		final Iterator<Integer> it = bpartnerIds.iterator();

		while (it.hasNext())
		{
			final I_C_BPartner partner = InterfaceWrapperHelper.create(ctx, it.next(), I_C_BPartner.class, trxName);

			final I_C_BPartner_Stats stats = Services.get(IBPartnerStatsDAO.class).retrieveBPartnerStats(partner);
			
			bpartnerStatsBL.updateSOCreditStatus(stats);
		}
	}
}
