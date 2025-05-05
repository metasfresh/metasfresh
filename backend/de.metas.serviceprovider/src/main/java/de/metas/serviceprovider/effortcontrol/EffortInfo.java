/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.serviceprovider.effortcontrol;

import de.metas.serviceprovider.issue.Status;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Duration;

@Value
@Builder
public class EffortInfo
{
	@NonNull
	EffortTarget effortTarget;

	@NonNull
	Status issueStatus;

	@NonNull
	Duration effortSum;

	@NonNull
	BigDecimal budget;

	@NonNull
	BigDecimal invoiceableHours;

	@NonNull
	public BigDecimal getPendingEffortInSeconds()
	{
		if (isInvoiced())
		{
			return BigDecimal.ZERO;
		}

		return getEffortSumInSeconds();
	}

	@NonNull
	public BigDecimal getEffortSumInSeconds()
	{
		return BigDecimal.valueOf(effortSum.getSeconds());
	}

	public boolean isInvoiced()
	{
		return issueStatus.equals(Status.INVOICED);
	}
}
