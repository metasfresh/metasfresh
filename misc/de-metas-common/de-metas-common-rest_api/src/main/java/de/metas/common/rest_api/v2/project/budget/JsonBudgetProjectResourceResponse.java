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

package de.metas.common.rest_api.v2.project.budget;

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
public class JsonBudgetProjectResourceResponse
{
	@NonNull
	@JsonProperty("budgetProjectResourceId")
	JsonMetasfreshId budgetProjectResourceId;

	@NonNull
	@JsonProperty("projectId")
	JsonMetasfreshId projectId;

	@NonNull
	@JsonProperty("uomTimeId")
	JsonMetasfreshId uomTimeId;

	@NonNull
	@JsonProperty("dateStartPlan")
	String dateStartPlan;

	@NonNull
	@JsonProperty("dateFinishPlan")
	String dateFinishPlan;

	@Nullable
	@JsonProperty("description")
	String description;

	@NonNull
	@JsonProperty("plannedAmt")
	BigDecimal plannedAmt;

	@NonNull
	@JsonProperty("currencyId")
	JsonMetasfreshId currencyId;

	@NonNull
	@JsonProperty("plannedDuration")
	BigDecimal plannedDuration;

	@NonNull
	@JsonProperty("pricePerTimeUOM")
	BigDecimal pricePerTimeUOM;

	@Nullable
	@JsonProperty("resourceGroupId")
	JsonMetasfreshId resourceGroupId;

	@Nullable
	@JsonProperty("resourceId")
	JsonMetasfreshId resourceId;

	@Nullable
	@JsonProperty("externalId")
	String externalId;

	@Nullable
	@JsonProperty("isActive")
	Boolean isActive;

	@JsonCreator
	@Builder
	public JsonBudgetProjectResourceResponse(
			@JsonProperty("budgetProjectResourceId") @NonNull final JsonMetasfreshId budgetProjectResourceId,
			@JsonProperty("projectId") @NonNull final JsonMetasfreshId projectId,
			@JsonProperty("uomTimeId") @NonNull final JsonMetasfreshId uomTimeId,
			@JsonProperty("dateStartPlan") @NonNull final String dateStartPlan,
			@JsonProperty("dateFinishPlan") @NonNull final String dateFinishPlan,
			@JsonProperty("description") @Nullable final String description,
			@JsonProperty("plannedAmt") @NonNull final BigDecimal plannedAmt,
			@JsonProperty("currencyId") @NonNull final JsonMetasfreshId currencyId,
			@JsonProperty("plannedDuration") @NonNull final BigDecimal plannedDuration,
			@JsonProperty("pricePerTimeUOM") @NonNull final BigDecimal pricePerTimeUOM,
			@JsonProperty("resourceGroupId") @Nullable final JsonMetasfreshId resourceGroupId,
			@JsonProperty("resourceId") @Nullable final JsonMetasfreshId resourceId,
			@JsonProperty("externalId") @Nullable final String externalId,
			@JsonProperty("isActive") @Nullable final Boolean isActive)
	{
		this.budgetProjectResourceId = budgetProjectResourceId;
		this.projectId = projectId;
		this.uomTimeId = uomTimeId;
		this.dateStartPlan = dateStartPlan;
		this.dateFinishPlan = dateFinishPlan;
		this.description = description;
		this.plannedAmt = plannedAmt;
		this.currencyId = currencyId;
		this.plannedDuration = plannedDuration;
		this.pricePerTimeUOM = pricePerTimeUOM;
		this.resourceGroupId = resourceGroupId;
		this.resourceId = resourceId;
		this.externalId = externalId;
		this.isActive = isActive;
	}
}