package org.adempiere.bpartner.service.impl;

import java.math.BigDecimal;

import org.adempiere.bpartner.service.IBPartnerStats;
import org.compiere.model.I_C_BPartner_Stats;

import com.google.common.base.MoreObjects;

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

class BPartnerStats implements IBPartnerStats
{
	public static IBPartnerStats of(final I_C_BPartner_Stats stats)
	{
		return new BPartnerStats(stats);
	}

	/**
	 * This field is added just in case there will be another implementation of IBPartnerStats which will not include the I_C_BPartner_Stats field.
	 * Using this ID, it would still be possible to load the data for further processing
	 */
	private int bpartnerID;

	private final BigDecimal totalOpenBalance;

	private final BigDecimal actualLifeTimeValue;

	private BigDecimal soCreditUsed;

	private String soCreditStatus;

	private I_C_BPartner_Stats stats;

	private BPartnerStats(final I_C_BPartner_Stats stats)
	{
		super();

		this.stats = stats;
		totalOpenBalance = stats.getTotalOpenBalance();
		actualLifeTimeValue = stats.getActualLifeTimeValue();
		soCreditUsed = stats.getSO_CreditUsed();
		soCreditStatus = stats.getSOCreditStatus();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("totalOpenBalance", totalOpenBalance)
				.toString();
	}

	@Override
	public BigDecimal getTotalOpenBalance()
	{
		return totalOpenBalance;
	}

	@Override
	public BigDecimal getActualLifeTimeValue()
	{
		return actualLifeTimeValue;
	}

	@Override
	public BigDecimal getSOCreditUsed()
	{
		return soCreditUsed;
	}

	@Override
	public String getSOCreditStatus()
	{
		return soCreditStatus;
	}

	protected I_C_BPartner_Stats getC_BPartner_Stats()
	{
		return stats;
	}
	
	@Override
	public int getC_BPartner_ID()
	{
		return bpartnerID;
	}

}
