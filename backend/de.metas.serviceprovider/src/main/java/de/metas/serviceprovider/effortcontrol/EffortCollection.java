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
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.Status;
import de.metas.serviceprovider.timebooking.Effort;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Value(staticConstructor = "of")
public class EffortCollection
{
	@NonNull
	EffortControl effortControl;

	@NonNull
	List<IssueEntity> effortIssues;

	@NonNull
	public EffortControl computeEffortControl()
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
		return effortIssues.stream()
				.filter(issue -> !Status.INVOICED.equals(issue.getStatus()))
				.map(IssueEntity::getIssueEffort)
				.reduce(Effort.ZERO, Effort::addNullSafe);
	}

	@NonNull
	private Effort computeEffortSum()
	{
		return effortIssues.stream()
				.map(IssueEntity::getIssueEffort)
				.reduce(Effort.ZERO, Effort::addNullSafe);
	}

	@NonNull
	private BigDecimal computeBudget()
	{
		return effortIssues.stream()
				.filter(IssueEntity::isEffortIssue)
				.map(IssueEntity::getBudgetedEffort)
				.filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@NonNull
	private BigDecimal computeInvoiceableHours()
	{
		return effortIssues.stream()
				.filter(issue -> !issue.isEffortIssue())
				.map(IssueEntity::getInvoiceableHours)
				.filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
