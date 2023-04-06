/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.payment.paymentterm.impl;

import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.BaseLineType;
import de.metas.payment.paymentterm.CalculationMethod;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;

@Builder
@Value
public final class PaymentTerm
{
	@NonNull PaymentTermId id;
	@NonNull OrgId orgId;
	@NonNull ClientId clientId;
	@NonNull CalculationMethod calculationMethod;
	@NonNull BaseLineType baseLineType;

	@Nullable String value;
	@Nullable String name;
	@Nullable Percent discount;
	@Nullable Percent discount2;
	@Nullable String netDay;

	int discountDays;
	int discountDays2;
	int graceDays;
	int netDays;
	boolean allowOverrideDueDate;
	boolean _default;

	/**
	 * Computes the due date based on the calculation method and net days of this payment term.
	 *
	 * @param baseLineDate the base line date to compute the due date from
	 * @return the computed due date
	 * @throws AdempiereException if the calculation method is unknown
	 */
	public Timestamp computeDueDate(@NonNull final Timestamp baseLineDate)
	{
		final Timestamp computedDate;

		switch (calculationMethod)
		{
			case BaseLineDatePlusXDays:
				computedDate= TimeUtil.addDays(baseLineDate, netDays);
				break;
			case BaseLineDatePlusXDaysAndThenEndOfMonth:
				final Timestamp computedBLDate = TimeUtil.addDays(baseLineDate, netDays);
				computedDate = TimeUtil.getMonthLastDay(computedBLDate);
				break;
			case EndOfTheMonthOfBaselineDatePlusXDays:
				final Timestamp endOfMonthDate = TimeUtil.getMonthLastDay(baseLineDate);
				computedDate = TimeUtil.addDays(endOfMonthDate, netDays);
				break;
			default:
				throw new AdempiereException("Unknown calculation method for payment term " + id);
		}

		return computedDate;
	}

	public boolean isBaseLineTypeInvoiceDate()
	{
		return baseLineType.isInvoiceDate();
	}
}