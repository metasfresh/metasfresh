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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.PROJECT_IDENTIFIER_DOC;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(description = "Contains the project and its resources to be upserted.")
public class JsonBudgetProjectUpsertRequest
{
	@ApiModelProperty(position = 10, value = PROJECT_IDENTIFIER_DOC, required = true)
	@Setter
	private String projectIdentifier;

	@ApiModelProperty(position = 20, value = "Corresponding to `C_Project.C_ProjectType_ID`", required = true)
	@Setter
	private JsonMetasfreshId projectTypeId;

	@ApiModelProperty(position = 30, value = "Corresponding to `C_Project.AD_Org_ID`")
	private String orgCode;

	@ApiModelProperty(hidden = true)
	private boolean orgCodeSet;

	@ApiModelProperty(position = 40, value = "Corresponding to `C_Project.Value`")
	private String value;

	@ApiModelProperty(hidden = true)
	private boolean valueSet;

	@ApiModelProperty(position = 50, value = "Corresponding to `C_Project.Name`")
	private String name;

	@ApiModelProperty(hidden = true)
	private boolean nameSet;

	@ApiModelProperty(position = 60, value = "Corresponding to `C_Project.M_PriceList_Version_ID`")
	private JsonMetasfreshId priceListVersionId;

	@ApiModelProperty(hidden = true)
	private boolean priceListVersionIdSet;

	@ApiModelProperty(position = 70, value = "Corresponding to `C_Project.C_Currency_ID.Iso_Code`")
	private String currencyCode;

	@ApiModelProperty(hidden = true)
	private boolean currencyCodeSet;

	@ApiModelProperty(position = 80, value = "Corresponding to `C_Project.SalesRep_ID`")
	private JsonMetasfreshId salesRepId;

	@ApiModelProperty(hidden = true)
	private boolean salesRepIdSet;

	@ApiModelProperty(position = 90, value = "Corresponding to `C_Project.Description`")
	private String description;

	@ApiModelProperty(hidden = true)
	private boolean descriptionSet;

	@ApiModelProperty(position = 100, value = "Corresponding to `C_Project.DateContract`")
	private LocalDate dateContract;

	@ApiModelProperty(hidden = true)
	private boolean dateContractSet;

	@ApiModelProperty(position = 110, value = "Corresponding to `C_Project.DateFinish`")
	private LocalDate dateFinish;

	@ApiModelProperty(hidden = true)
	private boolean dateFinishSet;

	@ApiModelProperty(position = 120, value = "Corresponding to `C_Project.C_BPartner_ID`")
	private JsonMetasfreshId bpartnerId;

	@ApiModelProperty(hidden = true)
	private boolean bpartnerIdSet;

	@ApiModelProperty(position = 130, value = "Corresponding to `C_Project.C_Project_Reference_Ext`")
	private String projectReferenceExt;

	@ApiModelProperty(hidden = true)
	private boolean projectReferenceExtSet;

	JsonExternalId externalId;

	@ApiModelProperty(hidden = true)
	boolean externalIdSet;
	
	@ApiModelProperty(position = 140, value = "Corresponding to `C_Project.C_Project_Parent_ID`")
	private String projectParentIdentifier;

	@ApiModelProperty(hidden = true)
	private boolean projectParentIdentifierSet;

	@ApiModelProperty(position = 150, value = "Corresponding to `C_Project.IsActive`")
	private Boolean active;

	@ApiModelProperty(hidden = true)
	private boolean activeSet;

	@ApiModelProperty(position = 160, required = true)
	@Setter
	private SyncAdvise syncAdvise;

	@ApiModelProperty(position = 170)
	@Setter
	private Map<String, Object> extendedProps = new HashMap<>();

	@ApiModelProperty(position = 180, value = "Corresponding to `C_Project_Resource_Budget`")
	@Setter
	private List<JsonRequestBudgetProjectResourceUpsertItem> resources = new ArrayList<>();

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

	public void setPriceListVersionId(final JsonMetasfreshId priceListVersionId)
	{
		this.priceListVersionId = priceListVersionId;
		this.priceListVersionIdSet = true;
	}

	public void setCurrencyCode(final String currencyCode)
	{
		this.currencyCode = currencyCode;
		this.currencyCodeSet = true;
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

	public void setBpartnerId(final JsonMetasfreshId bpartnerId)
	{
		this.bpartnerId = bpartnerId;
		this.bpartnerIdSet = true;
	}

	public void setProjectReferenceExt(final String projectReferenceExt)
	{
		this.projectReferenceExt = projectReferenceExt;
		this.projectReferenceExtSet = true;
	}

	public void setExternalId(final JsonExternalId externalId)
	{
		this.externalId = externalId;
		this.externalIdSet = true;
	}
	
	public void setProjectParentIdentifier(final String projectParentIdentifier)
	{
		this.projectParentIdentifier = projectParentIdentifier;
		this.projectParentIdentifierSet = true;
	}

	public void setIsActive(final Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setOrgCode(final String orgCode)
	{
		this.orgCode = orgCode;
		this.orgCodeSet = true;
	}
}