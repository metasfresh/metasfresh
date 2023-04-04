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
public class PaymentTerm
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

	public Timestamp computeDueDate(@NonNull final Timestamp baseLineDate)
	{
		int netDays = getNetDays();

		@NonNull CalculationMethod calculationMethod = getCalculationMethod();
		switch (calculationMethod)
		{
			case BaseLineDatePlusXDays:
				return TimeUtil.addDays(baseLineDate, netDays);
			case BaseLineDatePlusXDaysAndThenEndOfMonth:
				final Timestamp computedBLDate = TimeUtil.addDays(baseLineDate, netDays);
				return TimeUtil.getMonthLastDay(computedBLDate);
			case EndOfTheMonthOfBaselineDatePlusXDays:
				final Timestamp endOfMonthDate = TimeUtil.getMonthLastDay(baseLineDate);
				return TimeUtil.addDays(endOfMonthDate, netDays);
			default:
				throw new AdempiereException("Unknown type for " + this);
		}

	}
}

