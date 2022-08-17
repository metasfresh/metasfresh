/*
 * #%L
 * de.metas.business
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

package de.metas.project.workorder.data;

import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.util.lang.ExternalId;
import de.metas.workflow.WFDurationUnit;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder(toBuilder = true)
public class WOProjectResource
{
	@NonNull
	OrgId orgId;

	@NonNull
	WOProjectResourceId woProjectResourceId;

	@NonNull
	WOProjectStepId woProjectStepId;

	@NonNull
	Instant assignDateFrom;

	@NonNull
	Instant assignDateTo;

	@NonNull
	ResourceId resourceId;

	@Nullable
	Boolean isActive;

	@Nullable
	Boolean isAllDay;

	@Nullable
	BigDecimal duration;

	@Nullable
	WFDurationUnit durationUnit;

	@Nullable
	ProjectId budgetProjectId;

	@Nullable
	BudgetProjectResourceId projectResourceBudgetId;

	@Nullable
	ExternalId externalId;

	@Nullable
	String testFacilityGroupName;
}