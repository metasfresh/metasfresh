package org.adempiere.bpartner.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.bpartner.service.IBPartnerTotalOpenBalanceUpdater;
import org.compiere.model.MBPartner;

/**
 * Synchronous implementation; note that there is also an async implementation which sets up a work package to to the job later and in background. 
 *
 */
public class BPartnerTotalOpenBalanceUpdater implements IBPartnerTotalOpenBalanceUpdater
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@SuppressWarnings("deprecation")
	@Override
	public void updateTotalOpenBalances(final Properties ctx, final Set<Integer> bpartnerIds, final String trxName)
	{
		if (bpartnerIds == null || bpartnerIds.isEmpty())
		{
			return;
		}

		final Iterator<Integer> it = bpartnerIds.iterator();
		while (it.hasNext())
		{
			final int C_BPartner_ID = it.next();
			MBPartner bp = new MBPartner(ctx, C_BPartner_ID, trxName);
			bp.setTotalOpenBalance();		// recalculates from scratch
			// bp.setSOCreditStatus(); // called automatically
			if (bp.save())
				logger.debug("Updated: {}", bp);
			else
				logger.error("BP not updated - " + bp);
		}
	}

}
