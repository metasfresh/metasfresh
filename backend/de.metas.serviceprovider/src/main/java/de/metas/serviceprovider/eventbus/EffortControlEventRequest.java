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

package de.metas.serviceprovider.eventbus;

import de.metas.organization.ClientAndOrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.function.Function;

@Value
@Builder
@Jacksonized
public class EffortControlEventRequest
{
	@NonNull
	ClientAndOrgId clientAndOrgId;

	@NonNull
	ProjectId projectId;

	@NonNull
	ActivityId costCenterId;

	@NonNull
	Duration deltaPendingEffortSum;

	@NonNull
	Duration deltaEffortSum;

	@NonNull
	BigDecimal deltaBudget;

	@NonNull
	BigDecimal deltaInvoiceableHours;

	@NonNull
	public <T> T mapDeltaPendingEffort(@NonNull final Function<Duration, T> mapper)
	{
		return mapper.apply(deltaPendingEffortSum);
	}

	@NonNull
	public <T> T mapDeltaEffortSum(@NonNull final Function<Duration, T> mapper)
	{
		return mapper.apply(deltaEffortSum);
	}
}
