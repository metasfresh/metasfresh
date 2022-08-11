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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
@Jacksonized
public class JsonBudgetProjectResourceResponse
{
	@ApiModelProperty( //
			required = true, //
			value = "This translates to C_Project_Resource_Budget.C_Project_Resource_Budget_ID")
	@NonNull
	JsonMetasfreshId budgetProjectResourceId;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.C_Project_ID`.")
	@NonNull
	JsonMetasfreshId projectId;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.C_UOM_Time_ID`.")
	@NonNull
	JsonMetasfreshId uomTimeId;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.DateStartPlan`.")
	@NonNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateStartPlan;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.DateFinishPlan`.")
	@NonNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateFinishPlan;

	@ApiModelProperty(value = "This translates to `C_Project_Resource_Budget.Description`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String description;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.PlannedAmt`.")
	@NonNull
	BigDecimal plannedAmt;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.C_Currency_ID.Iso_Code`.")
	@NonNull
	String currencyCode;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.PlannedDuration`.")
	@NonNull
	BigDecimal plannedDuration;

	@ApiModelProperty(required = true, value = "This translates to `C_Project_Resource_Budget.PricePerTimeUOM`.")
	@NonNull
	BigDecimal pricePerTimeUOM;

	@ApiModelProperty(value = "This translates to `C_Project_Resource_Budget.S_Resource_Group_ID`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId resourceGroupId;

	@ApiModelProperty(value = "This translates to `C_Project_Resource_Budget.S_Resource_ID`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId resourceId;

	@ApiModelProperty(value = "This translates to `C_Project_Resource_Budget.ExternalId`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String externalId;

	@ApiModelProperty(value = "This translates to `C_Project_Resource_Budget.IsActive`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Boolean isActive;
}