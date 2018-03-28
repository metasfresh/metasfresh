package org.adempiere.bpartner.service;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

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
	 */
	@Builder
	@Value
	public static class CalculateSOCreditStatusRequest
	{
		@NonNull
		BPartnerStats stat;
		@NonNull
		@Default
		BigDecimal additionalAmt = BigDecimal.ZERO;
		@NonNull
		Timestamp date;
		@Default
		boolean forceCheckCreditStatus = false;
	}

	/**
	 * Calculate the future/simulated SOCreditStatus for the given {@link BPartnerStats} object at a certain date
	 * <br>
	 * The computation can be forced with the flag <code>forceCheckCreditStatus</code><br>
	 * If the status is <code>CreditStop</code>, the status can be recomputed only if flag <code>forceCheckCreditStatus</code> is on Y
	 * <br><b>No updating</b>
	 *
	 * @param stat
	 * @param additionalAmt
	 * @param date
	 * @return
	 */
	String calculateSOCreditStatus(CalculateSOCreditStatusRequest request);


	/**
	 * Logic to tell whether or not the given grandTotal makes the credit stop for the given BPartner stats.
	 * To be used in document preparing: invoices, payments, etc
	 *
	 * @param stat
	 * @param grandTotal
	 * @param date
	 * @return
	 */
	boolean isCreditStopSales(BPartnerStats stat, BigDecimal grandTotal, Timestamp date);


	/**
	 * Get Credit Watch % from the bpartner group of the given statistics
	 *
	 * @param stats
	 * @return
	 */
	BigDecimal getCreditWatchRatio(BPartnerStats stats);


	void resetCreditStatusFromBPGroup(I_C_BPartner bpartner);
}
