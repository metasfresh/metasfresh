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

import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.CoalesceUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.PROJECT_IDENTIFIER_DOC;

@Getter
@ToString
@EqualsAndHashCode
public class JsonWorkOrderProjectUpsertRequest
{
	@ApiModelProperty(position = 10,
			required = true,
			value = PROJECT_IDENTIFIER_DOC) //
	@Setter
	String identifier;

	String value;

	@ApiModelProperty(hidden = true)
	boolean valueSet;

	String name;

	@ApiModelProperty(hidden = true)
	boolean nameSet;

	@ApiModelProperty(required = true)
	JsonMetasfreshId projectTypeId;

	JsonMetasfreshId priceListVersionId;

	@ApiModelProperty(hidden = true)
	boolean priceListVersionIdSet;

	JsonMetasfreshId currencyId;

	@ApiModelProperty(hidden = true)
	boolean currencyIdSet;

	JsonMetasfreshId salesRepId;

	@ApiModelProperty(hidden = true)
	boolean salesRepIdSet;

	String description;

	@ApiModelProperty(hidden = true)
	boolean descriptionSet;

	LocalDate dateContract;

	@ApiModelProperty(hidden = true)
	boolean dateContractSet;

	LocalDate dateFinish;

	@ApiModelProperty(hidden = true)
	boolean dateFinishSet;

	JsonMetasfreshId businessPartnerId;

	@ApiModelProperty(hidden = true)
	boolean businessPartnerIdSet;

	String projectReferenceExt;

	@ApiModelProperty(hidden = true)
	boolean projectReferenceExtSet;

	JsonMetasfreshId projectParentId;

	@ApiModelProperty(hidden = true)
	boolean projectParentIdSet;

	@ApiModelProperty(required = true)
	String orgCode;

	@ApiModelProperty(hidden = true)
	boolean orgCodeSet;

	@ApiModelProperty(value = "If not specified but required (e.g. because a new project is created), then `true` is assumed")
	Boolean isActive;

	@ApiModelProperty(hidden = true)
	boolean activeSet;

	@ApiModelProperty(required = true)
	SyncAdvise syncAdvise;

	@ApiModelProperty(hidden = true)
	private boolean syncAdviseSet;

	String bpartnerDepartment;

	@ApiModelProperty(hidden = true)
	boolean bpartnerDepartmentSet;

	private String woOwner;

	@ApiModelProperty(hidden = true)
	private boolean woOwnerSet;

	private String poReference;

	@ApiModelProperty(hidden = true)
	private boolean poReferenceSet;

	private LocalDate bpartnerTargetDate;

	@ApiModelProperty(hidden = true)
	private boolean bpartnerTargetDateSet;

	private LocalDate woProjectCreatedDate;

	@ApiModelProperty(hidden = true)
	private boolean woProjectCreatedDateSet;

	private String specialistConsultantId;

	@ApiModelProperty(hidden = true)
	private boolean specialistConsultantIdSet;

	private LocalDate dateOfProvisionByBPartner;

	@ApiModelProperty(hidden = true)
	private boolean dateOfProvisionByBPartnerSet;

	private List<JsonWorkOrderStepUpsertRequest> steps;

	private List<JsonWorkOrderObjectUnderTestUpsertRequest> objectsUnderTest;

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
		this.salesRepIdSet = true;
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

	public void setBusinessPartnerId(final JsonMetasfreshId businessPartnerId)
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
		this.orgCodeSet = true;
	}

	public void setIsActive(final Boolean active)
	{
		isActive = active;
		this.activeSet = true;
	}

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
		this.syncAdviseSet = true;
	}

	public void setSteps(final List<JsonWorkOrderStepUpsertRequest> steps)
	{
		this.steps = CoalesceUtil.coalesce(steps, ImmutableList.of());
	}

	public void setBpartnerDepartment(final String bpartnerDepartment)
	{
		this.bpartnerDepartment = bpartnerDepartment;
		this.bpartnerDepartmentSet = true;
	}

	public void setWoOwner(final String woOwner)
	{
		this.woOwner = woOwner;
		this.woOwnerSet = true;
	}

	public void setPOReference(final String poReference)
	{
		this.poReference = poReference;
		this.poReferenceSet = true;
	}

	public void setBPartnerTargetDate(final LocalDate bpartnerTargetDate)
	{
		this.bpartnerTargetDate = bpartnerTargetDate;
		this.bpartnerTargetDateSet = true;
	}

	public void setWOProjectCreatedDate(final LocalDate woProjectCreatedDate)
	{
		this.woProjectCreatedDate = woProjectCreatedDate;
		this.woProjectCreatedDateSet = true;
	}

	public void setSpecialistConsultantId(final String specialistConsultantId)
	{
		this.specialistConsultantId = specialistConsultantId;
		this.specialistConsultantIdSet = true;
	}

	public void setDateOfProvisionByBPartner(final LocalDate dateOfProvisionByBPartner)
	{
		this.dateOfProvisionByBPartner = dateOfProvisionByBPartner;
		this.dateOfProvisionByBPartnerSet = true;
	}

	public void setObjectsUnderTest(final List<JsonWorkOrderObjectUnderTestUpsertRequest> objectsUnderTest)
	{
		this.objectsUnderTest = CoalesceUtil.coalesce(objectsUnderTest, ImmutableList.of());
	}
}