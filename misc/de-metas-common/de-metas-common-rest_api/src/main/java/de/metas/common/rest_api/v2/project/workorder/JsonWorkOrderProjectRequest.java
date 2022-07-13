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

import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@ToString
@EqualsAndHashCode
public class JsonWorkOrderProjectRequest
{
	@Getter
	JsonMetasfreshId projectId;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean projectIdSet;

	@Getter
	String value;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean valueSet;

	@Getter
	String name;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean nameSet;

	@ApiModelProperty(required = true)
	JsonMetasfreshId projectTypeId;


	@Getter
	JsonMetasfreshId priceListVersionId;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean priceListVersionIdSet;

	@Getter
	JsonMetasfreshId currencyId;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean currencyIdSet;

	@Getter
	JsonMetasfreshId salesRepId;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean salesRepIdSet;

	@Getter
	String description;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean descriptionSet;

	@Getter
	LocalDate dateContract;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean dateContractSet;

	@Getter
	LocalDate dateFinish;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean dateFinishSet;

	@Getter
	JsonMetasfreshId businessPartnerId;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean businessPartnerIdSet;

	@Getter
	String projectReferenceExt;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean projectReferenceExtSet;

	@Getter
	JsonMetasfreshId projectParentId;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean projectParentIdSet;

	@ApiModelProperty(required = true)
	String orgCode;

	@Getter
	Boolean isActive;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean activeSet;

	@ApiModelProperty(required = true)
	SyncAdvise syncAdvise;

	@Getter
	List<JsonWorkOrderStepRequest> steps;

	@ApiModelProperty(hidden = true)
	@Getter
	boolean stepsSet;

	public void setProjectId(final JsonMetasfreshId projectId)
	{
		this.projectId = projectId;
		this.projectIdSet = true;
	}

	public void setValue(final String value)
	{
		this.value = value;
		this.valueSet = true;
	}

	public void setName(final String name)
	{
		this.name = name;
		this.nameSet = true;
	}

	public void setProjectTypeId(final JsonMetasfreshId projectTypeId)
	{
		this.projectTypeId = projectTypeId;
	}

	public void setPriceListVersionId(final JsonMetasfreshId priceListVersionId)
	{
		this.priceListVersionId = priceListVersionId;
		this.priceListVersionIdSet = true;
	}

	public void setCurrencyId(final JsonMetasfreshId currencyId)
	{
		this.currencyId = currencyId;
		this.currencyIdSet = true;
	}

	public void setSalesRepId(final JsonMetasfreshId salesRepId)
	{
		this.salesRepId = salesRepId;
	}

	public void setDescription(final String description)
	{
		this.description = description;
		this.descriptionSet = true;
	}

	public void setDateContract(final LocalDate dateContract)
	{
		this.dateContract = dateContract;
		this.dateContractSet = true;
	}

	public void setDateFinish(final LocalDate dateFinish)
	{
		this.dateFinish = dateFinish;
		this.dateFinishSet = true;
	}

	public void setbPartnerId(final JsonMetasfreshId businessPartnerId)
	{
		this.businessPartnerId = businessPartnerId;
		this.businessPartnerIdSet = true;
	}

	public void setProjectReferenceExt(final String projectReferenceExt)
	{
		this.projectReferenceExt = projectReferenceExt;
		this.projectReferenceExtSet = true;
	}

	public void setProjectParentId(final JsonMetasfreshId projectParentId)
	{
		this.projectParentId = projectParentId;
		this.projectParentIdSet = true;
	}

	public void setOrgCode(final String orgCode)
	{
		this.orgCode = orgCode;
	}

	public void setIsActive(final Boolean active)
	{
		isActive = active;
		this.activeSet = true;
	}

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
	}

	public void setSteps(final List<JsonWorkOrderStepRequest> steps)
	{
		this.steps = steps;
		this.stepsSet = true;
	}

	@NonNull
	public JsonMetasfreshId getProjectTypeId()
	{
		return projectTypeId;
	}

	@NonNull
	public String getOrgCode()
	{
		return orgCode;
	}

	@NonNull
	public SyncAdvise getSyncAdvise()
	{
		return syncAdvise;
	}
}