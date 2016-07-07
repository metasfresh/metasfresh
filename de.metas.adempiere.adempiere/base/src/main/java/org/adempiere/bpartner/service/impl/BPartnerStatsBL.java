package org.adempiere.bpartner.service.impl;

import java.math.BigDecimal;

import org.adempiere.bpartner.service.IBPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsBL;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.X_C_BPartner_Stats;
import org.compiere.util.Env;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class BPartnerStatsBL implements IBPartnerStatsBL
{
	@Override
	public String calculateSOCreditStatus(final IBPartnerStats bpStats, final BigDecimal additionalAmt)
	{

		final IBPartnerStatsDAO bpStatsDAO = Services.get(IBPartnerStatsDAO.class);

		final String initialCreditStatus = bpStats.getSOCreditStatus();

		if (additionalAmt == null || additionalAmt.signum() == 0)
		{
			return initialCreditStatus;
		}

		// get credit limit from BPartner
		final I_C_BPartner partner = bpStatsDAO.retrieveC_BPartner(bpStats);

		BigDecimal creditLimit = partner.getSO_CreditLimit();

		// Nothing to do
		if (X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck.equals(initialCreditStatus)
				|| X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(initialCreditStatus)
				|| Env.ZERO.compareTo(creditLimit) == 0)
		{
			return initialCreditStatus;
		}

		// Above (reduced) Credit Limit
		creditLimit = creditLimit.subtract(additionalAmt);
		if (creditLimit.compareTo(bpStatsDAO.retrieveTotalOpenBalance(bpStats)) < 0)
		{
			return X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold;
		}

		// Above Watch Limit
		BigDecimal watchAmt = creditLimit.multiply(getCreditWatchRatio(bpStats));
		if (watchAmt.compareTo(bpStats.getTotalOpenBalance()) < 0)
		{
			return X_C_BPartner_Stats.SOCREDITSTATUS_CreditWatch;
		}

		// is OK
		return X_C_BPartner_Stats.SOCREDITSTATUS_CreditOK;
	}


	@Override
	public boolean isCreditStopSales(final IBPartnerStats stat, final BigDecimal grandTotal)
	{
		final String futureCreditStatus = calculateSOCreditStatus(stat, grandTotal);

		if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(futureCreditStatus))
		{
			return true;
		}

		return false;
	}
	
	@Override
	public BigDecimal getCreditWatchRatio(final IBPartnerStats stats)
	{
		// bp group will be taken from the stats' bpartner
		final I_C_BPartner partner = Services.get(IBPartnerStatsDAO.class).retrieveC_BPartner(stats);

		final I_C_BP_Group bpGroup = partner.getC_BP_Group();
		final BigDecimal creditWatchPercent = bpGroup.getCreditWatchPercent();

		if (creditWatchPercent.signum() == 0)
		{
			return new BigDecimal(0.90);
		}

		return creditWatchPercent.divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP);
	}
}
