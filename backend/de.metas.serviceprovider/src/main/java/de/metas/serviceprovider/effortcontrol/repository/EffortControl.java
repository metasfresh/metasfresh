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

package de.metas.serviceprovider.effortcontrol.repository;

import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.serviceprovider.timebooking.Effort;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;
import org.joda.time.DateTimeConstants;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Value
public class EffortControl
{
	@NonNull
	EffortControlId effortControlId;

	@NonNull
	OrgId orgId;

	@NonNull
	ProjectId projectId;

	@NonNull
	ActivityId costCenterId;

	@NonNull
	@Setter(AccessLevel.NONE)
	Boolean isOverBudget;

	@NonNull
	Instant updated;

	@NonNull
	Effort pendingEffortSum;

	@NonNull
	Effort effortSum;

	@NonNull
	BigDecimal budget;

	@NonNull
	BigDecimal invoiceableHours;

	@Builder(toBuilder = true)
	public EffortControl(
			@NonNull final EffortControlId effortControlId,
			@NonNull final OrgId orgId,
			@NonNull final ProjectId projectId,
			@NonNull final ActivityId costCenterId,
			@NonNull final Instant updated,
			@NonNull final Effort pendingEffortSum,
			@NonNull final Effort effortSum,
			@NonNull final BigDecimal budget,
			@NonNull final BigDecimal invoiceableHours)
	{
		this.effortControlId = effortControlId;
		this.orgId = orgId;
		this.projectId = projectId;
		this.costCenterId = costCenterId;
		this.updated = updated;
		this.pendingEffortSum = pendingEffortSum;
		this.effortSum = effortSum;
		this.budget = budget;
		this.invoiceableHours = invoiceableHours;

		this.isOverBudget = (effortSum.getSeconds() / DateTimeConstants.SECONDS_PER_HOUR) > budget.longValueExact();
	}

	public static boolean equals(@Nullable final EffortControl effort1, @Nullable final EffortControl effort2)
	{
		return Objects.equals(effort1, effort2);
	}
}
