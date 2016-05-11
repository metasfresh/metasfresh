package org.adempiere.bpartner.service;

import java.math.BigDecimal;

import org.adempiere.util.ISingletonService;
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IBPartnerStatsBL extends ISingletonService
{

	/**
	 * Set the ActualLifeTime given as parameter in the bpartner statistics and save. 
	 * 
	 * @param stat
	 * @param actualLifeTimeValue
	 */
	void setActualLifeTimeValue(I_C_BPartner_Stats stat, BigDecimal actualLifeTimeValue);

	/**
	 * Set the CreditUsed given as parameter in the bpartner statistics and save. 
	 * 
	 * @param stat
	 * @param creditUsed
	 */
	void setSOCreditUsed(I_C_BPartner_Stats stat, BigDecimal creditUsed);

	/**
	 * Set the SOCreditStatus given as parameter in the bpartner statistics and save. 
	 * 
	 * @param stat
	 * @param soCreditStatus
	 */
	void setSOCreditStatus(I_C_BPartner_Stats stat, String soCreditStatus);

	/**
	 * Set the TotalOpenBalance given as parameter in the bpartner statistics and save. 
	 * 
	 * @param stat
	 * @param totalOpenBalance
	 */
	void setTotalOpenBalance(I_C_BPartner_Stats stat, BigDecimal totalOpenBalance);

	/**
	 * Get the current SOCrditStatus value form the given bp stats
	 * 
	 * @param stats
	 * @return
	 */
	String getSOCreditStatus(I_C_BPartner_Stats stats);

	/**
	 * Get the current TotalOpenBalance value form the given bp stats
	 * 
	 * @param stats
	 * @return
	 */
	BigDecimal getTotalOpenBalance(I_C_BPartner_Stats stats);

	/**
	 * Get the current ActualLifeTimeValue value form the given bp stats
	 * 
	 * @param stats
	 * @return
	 */
	BigDecimal getActualLifeTimeValue(I_C_BPartner_Stats stats);

	/**
	 * Get the current SOCreditUsed value form the given bp stats
	 * 
	 * @param partner
	 * @return
	 */
	BigDecimal getSOCreditUsed(I_C_BPartner_Stats stats);

	/**
	 * Set the ActualLifeTimeValue value in the {@link I_C_BPartner_Stats} entry
	 * 
	 * @param stats
	 */
	void updateActualLifeTimeValue(I_C_BPartner_Stats stats);

	/**
	 * Update the TotalOpenBalance for the given {@link I_C_BPartner_Stats} entry based on the legacy sql
	 * 
	 * @param stat
	 */
	void updateTotalOpenBalance(I_C_BPartner_Stats stat);

	/**
	 * Update the SOCreditStatus for the given {@link I_C_BPartner_Stats} entry based on the legacy sql
	 * 
	 * @param stat
	 */
	void updateSOCreditStatus(I_C_BPartner_Stats stat);

	/**
	 * Calculate the future/simulated TotalOpenBalance for the given {@link I_C_BPartner_Stats} entry.
	 * No updating
	 * 
	 * @param stats
	 * @return
	 */
	BigDecimal calculateTotalOpenBalance(I_C_BPartner_Stats stats);

	/**
	 * Calculate the future/simulated SOCreditStatus for the given {@link I_C_BPartner_Stats} entry.
	 * No updating
	 * 
	 * @param stat
	 * @param additionalAmt
	 * @return
	 */
	String calculateSOCreditStatus(I_C_BPartner_Stats stat, BigDecimal additionalAmt);

	/**
	 * Logic to tell whether or not the given grandTotal makes the credit stop for the given BPartner stats.
	 * To be used in document preparing: invoices, payments, etc
	 * 
	 * @param stat
	 * @param grandTotal
	 * @return
	 */
	boolean isCreditStopSales(I_C_BPartner_Stats stat, BigDecimal grandTotal);

}
