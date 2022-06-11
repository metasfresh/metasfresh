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

package de.metas.common.rest_api.v2.project;

import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(description = "Contains the project to be upserted.")
public class JsonRequestProjectUpsertItem
{
	@ApiModelProperty(position = 10, value = "Corresponding to `C_Project.C_Project_ID`")
	private JsonMetasfreshId projectId;

	@ApiModelProperty(position = 20, value = "Corresponding to `C_Project.AD_Org_ID`", required = true)
	private JsonMetasfreshId orgId;

	@ApiModelProperty(position = 30, value = "Corresponding to `C_Project.Name`", required = true)
	private String name;

	@ApiModelProperty(position = 40, value = "Corresponding to `C_Project.C_Currency_Id.Iso_Code`", required = true)
	private String currencyCode;

	@ApiModelProperty(position = 50, value = "Corresponding to `C_Project.Value`")
	private String value;

	@ApiModelProperty(hidden = true)
	private boolean valueSet;

	@ApiModelProperty(position = 60, value = "Corresponding to `C_Project.Description`")
	private String description;

	@ApiModelProperty(hidden = true)
	private boolean descriptionSet;

	@ApiModelProperty(position = 70, value = "Corresponding to `C_Project.C_Project_Parent_ID`")
	private JsonMetasfreshId projectParentId;

	@ApiModelProperty(hidden = true)
	private boolean projectParentIdSet;

	@ApiModelProperty(position = 80, value = "Corresponding to `C_Project.C_ProjectType_ID`")
	private JsonMetasfreshId projectTypeId;

	@ApiModelProperty(hidden = true)
	private boolean projectTypeIdSet;

	@ApiModelProperty(position = 90, value = "Corresponding to `C_Project.R_Project_Status_ID`")
	private JsonMetasfreshId projectStatusId;

	@ApiModelProperty(hidden = true)
	private boolean projectStatusIdSet;

	@ApiModelProperty(position = 100, value = "Corresponding to `C_Project.C_BPartner_ID`")
	private JsonMetasfreshId bpartnerId;

	@ApiModelProperty(hidden = true)
	private boolean bpartnerIdSet;

	@ApiModelProperty(position = 110, value = "Corresponding to `C_Project.SalesRep_ID`")
	private JsonMetasfreshId salesRepId;

	@ApiModelProperty(hidden = true)
	private boolean salesRepIdSet;

	@ApiModelProperty(position = 120, value = "Corresponding to `C_Project.DateContract`")
	private LocalDate dateContract;

	@ApiModelProperty(hidden = true)
	private boolean dateContractSet;

	@ApiModelProperty(position = 130, value = "Corresponding to `C_Project.DateFinish`")
	private LocalDate dateFinish;

	@ApiModelProperty(hidden = true)
	private boolean dateFinishSet;

	@ApiModelProperty(position = 140, value = "Corresponding to `C_Project.isActive`")
	private Boolean active;

	@ApiModelProperty(hidden = true)
	private boolean activeSet;

	public void setValue(final String value)
	{
		this.value = value;
		this.valueSet = true;
	}

	public void setDescription(final String description)
	{
		this.description = description;
		this.descriptionSet = true;
	}

	public void setProjectParentId(final JsonMetasfreshId projectParentId)
	{
		this.projectParentId = projectParentId;
		this.projectParentIdSet = true;
	}

	public void setProjectTypeId(final JsonMetasfreshId projectTypeId)
	{
		this.projectTypeId = projectTypeId;
		this.projectTypeIdSet = true;
	}

	public void setProjectStatusId(final JsonMetasfreshId projectStatusId)
	{
		this.projectStatusId = projectStatusId;
		this.projectStatusIdSet = true;
	}

	public void setBpartnerId(final JsonMetasfreshId bpartnerId)
	{
		this.bpartnerId = bpartnerId;
		this.bpartnerIdSet = true;
	}

	public void setSalesRepId(final JsonMetasfreshId salesRepId)
	{
		this.salesRepId = salesRepId;
		this.salesRepIdSet = true;
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

	public void setActive(final Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}
}
