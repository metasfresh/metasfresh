/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v2.project.workorder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class JsonWorkOrderResourceResponse
{
	@NonNull
	@JsonProperty("woResourceId")
	JsonMetasfreshId woResourceId;

	@NonNull
	@JsonProperty("stepId")
	JsonMetasfreshId stepId;

	@NonNull
	@JsonProperty("assignDateFrom")
	String assignDateFrom;

	@NonNull
	@JsonProperty("assignDateTo")
	String assignDateTo;

	@Nullable
	@JsonProperty("isActive")
	Boolean isActive;

	@Nullable
	@JsonProperty("resourceId")
	JsonMetasfreshId resourceId;

	@Nullable
	@JsonProperty("isAllDay")
	Boolean isAllDay;

	@Nullable
	@JsonProperty("duration")
	BigDecimal duration;

	@Nullable
	@JsonProperty("durationUnit")
	String durationUnit;

	@Nullable
	@JsonProperty("budgetProjectId")
	JsonMetasfreshId budgetProjectId;

	@Nullable
	@JsonProperty("projectResourceBudgetId")
	JsonMetasfreshId projectResourceBudgetId;

	@JsonCreator
	@Builder
	public JsonWorkOrderResourceResponse(
			@JsonProperty("woResourceId") @NonNull final JsonMetasfreshId woResourceId,
			@JsonProperty("stepId") @NonNull final JsonMetasfreshId stepId,
			@JsonProperty("assignDateFrom") @NonNull final String assignDateFrom,
			@JsonProperty("assignDateTo") @NonNull final String assignDateTo,
			@JsonProperty("isActive") @Nullable final Boolean isActive,
			@JsonProperty("resourceId") @Nullable final JsonMetasfreshId resourceId,
			@JsonProperty("isAllDay") @Nullable final Boolean isAllDay,
			@JsonProperty("duration") final @Nullable BigDecimal duration,
			@JsonProperty("durationUnit") final @Nullable String durationUnit,
			@JsonProperty("budgetProjectId") final @Nullable JsonMetasfreshId budgetProjectId,
			@JsonProperty("projectResourceBudgetId") final @Nullable JsonMetasfreshId projectResourceBudgetId
	)
	{
		this.woResourceId = woResourceId;
		this.stepId = stepId;
		this.assignDateFrom = assignDateFrom;
		this.assignDateTo = assignDateTo;
		this.isActive = isActive;
		this.resourceId = resourceId;
		this.isAllDay = isAllDay;
		this.duration = duration;
		this.durationUnit = durationUnit;
		this.budgetProjectId = budgetProjectId;
		this.projectResourceBudgetId = projectResourceBudgetId;
	}
}