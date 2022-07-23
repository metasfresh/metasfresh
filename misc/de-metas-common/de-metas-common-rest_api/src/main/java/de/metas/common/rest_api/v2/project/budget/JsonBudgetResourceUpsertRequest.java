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
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.money.JsonMoney;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.RESOURCE_IDENTIFIER_DOC;

@Getter
@ToString
@EqualsAndHashCode
public class JsonBudgetResourceUpsertRequest
{
	@ApiModelProperty(position = 10,
			required = true,
			value = RESOURCE_IDENTIFIER_DOC) //
	@Setter
	String resourceIdentifier;

	JsonMetasfreshId uomTimeId;

	@ApiModelProperty(hidden = true)
	boolean uomTimeIdSet;

	LocalDate dateFinishPlan;

	@ApiModelProperty(hidden = true)
	boolean dateFinishPlanSet;

	LocalDate dateStartPlan;

	@ApiModelProperty(hidden = true)
	boolean dateStartPlanSet;

	JsonMoney plannedAmt;

	@ApiModelProperty(hidden = true)
	boolean plannedAmtSet;

	BigDecimal plannedDuration;

	@ApiModelProperty(hidden = true)
	boolean plannedDurationSet;

	BigDecimal pricePerTimeUOM;

	@ApiModelProperty(hidden = true)
	boolean pricePerTimeUOMSet;

	String description;

	@ApiModelProperty(hidden = true)
	boolean descriptionSet;

	JsonMetasfreshId resourceGroupId;

	@ApiModelProperty(hidden = true)
	boolean resourceGroupIdSet;

	@ApiModelProperty(value = "If not specified but required (e.g. because a new contact is created), then `true` is assumed")
	Boolean isActive;

	@ApiModelProperty(hidden = true)
	boolean activeSet;

	@Setter
	@ApiModelProperty(required = true)
	JsonExternalId externalId;

	@ApiModelProperty(required = true)
	SyncAdvise syncAdvise;

	public void setUomTimeId(final JsonMetasfreshId uomTimeId)
	{
		this.uomTimeId = uomTimeId;
		uomTimeIdSet = true;
	}

	public void setDateFinishPlan(final LocalDate dateFinishPlan)
	{
		this.dateFinishPlan = dateFinishPlan;
		dateFinishPlanSet = true;
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

	public void setPricePerTimeUOM(final BigDecimal pricePerTimeUOM)
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
		isActive = active;
		this.activeSet = true;
	}

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
	}
}