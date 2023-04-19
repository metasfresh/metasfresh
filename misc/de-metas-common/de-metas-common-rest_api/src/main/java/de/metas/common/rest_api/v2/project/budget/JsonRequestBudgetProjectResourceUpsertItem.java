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

import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.money.JsonMoney;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.RESOURCE_IDENTIFIER_DOC;

@Getter
@ToString
@EqualsAndHashCode
public class JsonRequestBudgetProjectResourceUpsertItem
{
	@Schema(description = RESOURCE_IDENTIFIER_DOC, required = true)
	private String resourceIdentifier;

	@Schema(description = "Corresponding to `C_Project_Resource_Budget.ExternalId`")
	private JsonExternalId externalId;

	@Schema(hidden = true)
	private boolean externalIdSet;

	@Schema(description = "Corresponding to `C_Project_Resource_Budget.C_UOM_Time_ID`")
	private JsonMetasfreshId uomTimeId;

	@Schema(hidden = true)
	private boolean uomTimeIdSet;

	@Schema(description = "Corresponding to `C_Project_Resource_Budget.DateFinishPlan`")
	private LocalDate dateFinishPlan;

	@Schema(hidden = true)
	private boolean dateFinishPlanSet;

	@Schema(description = "Corresponding to `C_Project_Resource_Budget.DateStartPlan`")
	private LocalDate dateStartPlan;

	@Schema(hidden = true)
	private boolean dateStartPlanSet;

	@Schema(description = "Corresponding to `C_Project_Resource_Budget.PlannedAmt`")
	private JsonMoney plannedAmt;

	@Schema(hidden = true)
	private boolean plannedAmtSet;

	@Schema(description = "Corresponding to `C_Project_Resource_Budget.PlannedDuration`")
	private BigDecimal plannedDuration;

	@Schema(hidden = true)
	private boolean plannedDurationSet;

	@Schema(description = "Corresponding to `C_Project_Resource_Budget.PricePerTimeUOM`")
	private JsonMoney pricePerTimeUOM;

	@Schema(hidden = true)
	private boolean pricePerTimeUOMSet;

	@Schema(description = "Corresponding to `C_Project_Resource_Budget.Description`")
	private String description;

	@Schema(hidden = true)
	private boolean descriptionSet;

	@Schema(description = "Corresponding to `C_Project_Resource_Budget.S_Resource_Group_ID`")
	private JsonMetasfreshId resourceGroupId;

	@Schema(hidden = true)
	private boolean resourceGroupIdSet;

	@Schema(description = "Corresponding to `C_Project_Resource_Budget.IsActive`")
	private Boolean active;

	@Schema(hidden = true)
	private boolean activeSet;

	public void setUomTimeId(final JsonMetasfreshId uomTimeId)
	{
		this.uomTimeId = uomTimeId;
		this.uomTimeIdSet = true;
	}

	public void setDateFinishPlan(final LocalDate dateFinishPlan)
	{
		this.dateFinishPlan = dateFinishPlan;
		this.dateFinishPlanSet = true;
	}

	public void setDateStartPlan(final LocalDate dateStartPlan)
	{
		this.dateStartPlan = dateStartPlan;
		this.dateStartPlanSet = true;
	}

	public void setPlannedAmt(final JsonMoney plannedAmt)
	{
		this.plannedAmt = plannedAmt;
		this.plannedAmtSet = true;
	}

	public void setPlannedDuration(final BigDecimal plannedDuration)
	{
		this.plannedDuration = plannedDuration;
		this.plannedDurationSet = true;
	}

	public void setPricePerTimeUOM(final JsonMoney pricePerTimeUOM)
	{
		this.pricePerTimeUOM = pricePerTimeUOM;
		this.pricePerTimeUOMSet = true;
	}

	public void setDescription(final String description)
	{
		this.description = description;
		this.descriptionSet = true;
	}

	public void setResourceGroupId(final JsonMetasfreshId resourceGroupId)
	{
		this.resourceGroupId = resourceGroupId;
		this.resourceGroupIdSet = true;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setExternalId(final JsonExternalId externalId)
	{
		this.externalId = externalId;
		this.externalIdSet = true;
	}
}
