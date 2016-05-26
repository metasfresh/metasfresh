package org.adempiere.bpartner.service.impl;

import java.math.BigDecimal;

import org.adempiere.bpartner.service.IBPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsBL;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;
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
	final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

	@Override
	public void setSOCreditStatus(final I_C_BPartner_Stats stat, final String soCreditStatus)
	{
		stat.setSOCreditStatus(soCreditStatus);

		InterfaceWrapperHelper.save(stat);
	}

	@Override
	public String getSOCreditStatus(final I_C_BPartner_Stats stats)
	{
		final IBPartnerStats bpStats = BPartnerStats.of(stats);

		return bpStats.getSoCreditStatus();
	}

	@Override
	public void updateSOCreditStatus(final I_C_BPartner_Stats stat)
	{
		final I_C_BPartner partner = stat.getC_BPartner();

		final IBPartnerStats stats = BPartnerStats.of(stat);

		final BigDecimal creditLimit = partner.getSO_CreditLimit();

		final String initialCreditStatus = stat.getSOCreditStatus();

		String creditStatusToSet;

		// Nothing to do
		if (X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck.equals(initialCreditStatus)
				|| X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(initialCreditStatus)
				|| Env.ZERO.compareTo(creditLimit) == 0)
			return;

		// Above Credit Limit
		if (creditLimit.compareTo(calculateTotalOpenBalance(stat)) < 0)
			creditStatusToSet = X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold;
		else
		{
			// Above Watch Limit
			BigDecimal watchAmt = creditLimit.multiply(getCreditWatchRatio(stat));
			if (watchAmt.compareTo(stats.getTotalOpenBalance()) < 0)
				creditStatusToSet = X_C_BPartner_Stats.SOCREDITSTATUS_CreditWatch;
			else
				// is OK
				creditStatusToSet = X_C_BPartner_Stats.SOCREDITSTATUS_CreditOK;
		}

		stat.setSOCreditStatus(creditStatusToSet);

		InterfaceWrapperHelper.save(stat);
	}

	@Override
	public String calculateSOCreditStatus(final I_C_BPartner_Stats stat, final BigDecimal additionalAmt)
	{
		final I_C_BPartner partner = stat.getC_BPartner();
		final IBPartnerStats bpStats = BPartnerStats.of(stat);

		final String initialCreditStatus = bpStats.getSoCreditStatus();

		if (additionalAmt == null || additionalAmt.signum() == 0)
		{
			return initialCreditStatus;
		}
		//
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
		if (creditLimit.compareTo(calculateTotalOpenBalance(stat)) < 0)
		{
			return X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold;
		}

		// Above Watch Limit
		BigDecimal watchAmt = creditLimit.multiply(getCreditWatchRatio(stat));
		if (watchAmt.compareTo(bpStats.getTotalOpenBalance()) < 0)
		{
			return X_C_BPartner_Stats.SOCREDITSTATUS_CreditWatch;
		}

		// is OK
		return X_C_BPartner_Stats.SOCREDITSTATUS_CreditOK;
	}

	/**
	 * Get Credit Watch % from the bpartner group
	 * 
	 * @param stats
	 * @return
	 */
	private BigDecimal getCreditWatchRatio(final I_C_BPartner_Stats stats)
	{
		final I_C_BPartner partner = stats.getC_BPartner();
		final I_C_BP_Group bpGroup = partner.getC_BP_Group();
		final BigDecimal creditWatchPercent = bpGroup.getCreditWatchPercent();

		if (creditWatchPercent.signum() == 0)
		{
			return new BigDecimal(0.90);
		}

		return creditWatchPercent.divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public BigDecimal getTotalOpenBalance(final I_C_BPartner_Stats stats)
	{
		final IBPartnerStats bpStats = BPartnerStats.of(stats);

		return bpStats.getTotalOpenBalance();
	}

	@Override
	public void updateSOCreditUsed(final I_C_BPartner_Stats stat)
	{
		final BigDecimal soCreditUsed = bpartnerStatsDAO.retrieveSOCreditUsed(stat);

		stat.setSO_CreditUsed(soCreditUsed);

		InterfaceWrapperHelper.save(stat);
	}

	@Override
	public void updateTotalOpenBalance(final I_C_BPartner_Stats stat)
	{
		final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

		final BigDecimal totalOpenBalance = bpartnerStatsDAO.retrieveTotalOpenBalance(stat);

		stat.setTotalOpenBalance(totalOpenBalance);

		final String soCreditStatus = calculateSOCreditStatus(stat, Env.ZERO);

		setSOCreditStatus(stat, soCreditStatus);

		InterfaceWrapperHelper.save(stat);
	}

	@Override
	public BigDecimal calculateTotalOpenBalance(final I_C_BPartner_Stats stats)
	{
		final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

		BigDecimal TotalOpenBalance = bpartnerStatsDAO.retrieveTotalOpenBalance(stats);

		return TotalOpenBalance;
	}

	@Override
	public BigDecimal getSOCreditUsed(final I_C_BPartner_Stats stats)
	{
		final IBPartnerStats bpStats = BPartnerStats.of(stats);

		return bpStats.getSoCreditUsed();
	}

	@Override
	public BigDecimal getActualLifeTimeValue(final I_C_BPartner_Stats stats)
	{
		final IBPartnerStats bpStats = BPartnerStats.of(stats);

		return bpStats.getActualLifeTimeValue();
	}

	@Override
	public void updateActualLifeTimeValue(final I_C_BPartner_Stats stat)
	{
		final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

		BigDecimal actualLifeTimeValue = bpartnerStatsDAO.retrieveActualLifeTimeValue(stat);

		if (actualLifeTimeValue != null)
		{
			stat.setActualLifeTimeValue(actualLifeTimeValue);
		}

		InterfaceWrapperHelper.save(stat);
	}

	@Override
	public boolean isCreditStopSales(final I_C_BPartner_Stats stat, final BigDecimal grandTotal)
	{

		final String futureCreditStatus = calculateSOCreditStatus(stat, grandTotal);

		if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(futureCreditStatus))
		{
			return true;
		}

		return false;
	}
}
