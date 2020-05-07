package org.adempiere.bpartner.service;

import java.math.BigDecimal;

import org.adempiere.util.Check;

import lombok.Builder;
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

@Value
public class BPartnerStats
{
	int recordId;

	BigDecimal openItems;

	BigDecimal actualLifeTimeValue;

	BigDecimal soCreditUsed;

	String soCreditStatus;

	@Builder
	public BPartnerStats(
			final int recordId,
			@NonNull final BigDecimal openItems,
			@NonNull final BigDecimal actualLifeTimeValue,
			@NonNull final BigDecimal soCreditUsed,
			@NonNull final String soCreditStatus)
	{
		Check.assume(recordId > 0, "Given parameter recordId is > 0");
		this.recordId = recordId;

		this.openItems = openItems;
		this.actualLifeTimeValue = actualLifeTimeValue;
		this.soCreditUsed = soCreditUsed;
		this.soCreditStatus = soCreditStatus;
	}

	public BigDecimal getSOCreditUsed()
	{
		return soCreditUsed;
	}

	public String getSOCreditStatus()
	{
		return soCreditStatus;
	}
}
