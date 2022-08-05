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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.RESOURCE_IDENTIFIER_DOC;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class JsonBudgetProjectResourceResponse
{
	@ApiModelProperty( //
			required = true, //
			value = RESOURCE_IDENTIFIER_DOC)
	@NonNull
	@JsonProperty("budgetProjectResourceId")
	JsonMetasfreshId budgetProjectResourceId;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.C_Project_ID`.")
	@NonNull
	@JsonProperty("projectId")
	JsonMetasfreshId projectId;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.C_UOM_Time_ID`.")
	@NonNull
	@JsonProperty("uomTimeId")
	JsonMetasfreshId uomTimeId;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.DateStartPlan`.")
	@NonNull
	@JsonProperty("dateStartPlan")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateStartPlan;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.DateFinishPlan`.")
	@NonNull
	@JsonProperty("dateFinishPlan")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateFinishPlan;

	@ApiModelProperty(value = "This translates to `C_Project_Resource_Budget.Description`.")
	@Nullable
	@JsonProperty("description")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String description;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.PlannedAmt`.")
	@NonNull
	@JsonProperty("plannedAmt")
	BigDecimal plannedAmt;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.C_Currency_ID`.")
	@NonNull
	@JsonProperty("currencyId")
	JsonMetasfreshId currencyId;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.PlannedDuration`.")
	@NonNull
	@JsonProperty("plannedDuration")
	BigDecimal plannedDuration;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.PricePerTimeUOM`.")
	@NonNull
	@JsonProperty("pricePerTimeUOM")
	BigDecimal pricePerTimeUOM;

	@ApiModelProperty(value = "This translates to `C_Project_Resource_Budget.S_Resource_Group_ID`.")
	@Nullable
	@JsonProperty("resourceGroupId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId resourceGroupId;

	@ApiModelProperty(value = "This translates to `C_Project_Resource_Budget.S_Resource_ID`.")
	@Nullable
	@JsonProperty("resourceId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId resourceId;

	@ApiModelProperty(value = "This translates to `C_Project_Resource_Budget.ExternalId`.")
	@Nullable
	@JsonProperty("externalId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String externalId;

	@ApiModelProperty(value = "This translates to `C_Project_Resource_Budget.IsActive`.")
	@Nullable
	@JsonProperty("isActive")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Boolean isActive;

	@JsonCreator
	@Builder
	public JsonBudgetProjectResourceResponse(
			@JsonProperty("budgetProjectResourceId") @NonNull final JsonMetasfreshId budgetProjectResourceId,
			@JsonProperty("projectId") @NonNull final JsonMetasfreshId projectId,
			@JsonProperty("uomTimeId") @NonNull final JsonMetasfreshId uomTimeId,
			@JsonProperty("dateStartPlan") @NonNull final LocalDate dateStartPlan,
			@JsonProperty("dateFinishPlan") @NonNull final LocalDate dateFinishPlan,
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