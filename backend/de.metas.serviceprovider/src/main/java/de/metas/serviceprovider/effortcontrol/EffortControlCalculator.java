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

import de.metas.serviceprovider.effortcontrol.repository.EffortControl;
import de.metas.serviceprovider.timebooking.Effort;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value(staticConstructor = "of")
public class EffortControlCalculator
{
	@NonNull
	EffortControl effortControl;

	@NonNull
	List<EffortInfo> effortInfos;

	@NonNull
	public EffortControl calculate()
	{
		return effortControl.toBuilder()
				.pendingEffortSum(computePendingEffortSum())
				.effortSum(computeEffortSum())
				.budget(computeBudget())
				.invoiceableHours(computeInvoiceableHours())
				.build();
	}

	@NonNull
	private Effort computePendingEffortSum()
	{
		return effortInfos.stream()
				.map(EffortInfo::getPendingEffortInSeconds)
				.map(BigDecimal::longValueExact)
				.map(Effort::ofSeconds)
				.reduce(Effort.ZERO, Effort::addNullSafe);
	}

	@NonNull
	private Effort computeEffortSum()
	{
		return effortInfos.stream()
				.map(EffortInfo::getEffortSumInSeconds)
				.map(BigDecimal::longValueExact)
				.map(Effort::ofSeconds)
				.reduce(Effort.ZERO, Effort::addNullSafe);
	}

	@NonNull
	private BigDecimal computeBudget()
	{
		return effortInfos.stream()
				.map(EffortInfo::getBudget)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@NonNull
	private BigDecimal computeInvoiceableHours()
	{
		return effortInfos.stream()
				.map(EffortInfo::getInvoiceableHours)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
