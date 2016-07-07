package org.adempiere.bpartner.service;

import java.math.BigDecimal;

import org.adempiere.util.ISingletonService;

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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IBPartnerStatsBL extends ISingletonService
{
	/**
	 * Calculate the future/simulated SOCreditStatus for the given {@link IBPartnerStats} object.
	 * No updating
	 * 
	 * @param stat
	 * @param additionalAmt
	 * @return
	 */
	String calculateSOCreditStatus(IBPartnerStats stat, BigDecimal additionalAmt);


	/**
	 * Logic to tell whether or not the given grandTotal makes the credit stop for the given BPartner stats.
	 * To be used in document preparing: invoices, payments, etc
	 * 
	 * @param stat
	 * @param grandTotal
	 * @return
	 */
	boolean isCreditStopSales(IBPartnerStats stat, BigDecimal grandTotal);


	/**
	 * Get Credit Watch % from the bpartner group of the given statistics
	 * 
	 * @param stats
	 * @return
	 */
	BigDecimal getCreditWatchRatio(IBPartnerStats stats);
}
