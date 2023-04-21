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
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Contains the project and its resources to be upserted.")
public class JsonBudgetProjectUpsertRequest
{
	@Schema(description = PROJECT_IDENTIFIER_DOC, required = true)
	@Setter
	private String projectIdentifier;

	@Schema(description = "Corresponding to `C_Project.C_ProjectType_ID`", required = true)
	@Setter
	private JsonMetasfreshId projectTypeId;

	@Schema(description = "Corresponding to `C_Project.AD_Org_ID`")
	private String orgCode;

	@Schema(hidden = true)
	private boolean orgCodeSet;

	@Schema(description = "Corresponding to `C_Project.Value`")
	private String value;

	@Schema(hidden = true)
	private boolean valueSet;

	@Schema(description = "Corresponding to `C_Project.Name`")
	private String name;

	@Schema(hidden = true)
	private boolean nameSet;

	@Schema(description = "Corresponding to `C_Project.M_PriceList_Version_ID`")
	private JsonMetasfreshId priceListVersionId;

	@Schema(hidden = true)
	private boolean priceListVersionIdSet;

	@Schema(description = "Corresponding to `C_Project.C_Currency_ID.Iso_Code`")
	private String currencyCode;

	@Schema(hidden = true)
	private boolean currencyCodeSet;

	@Schema(description = "Corresponding to `C_Project.SalesRep_ID`")
	private JsonMetasfreshId salesRepId;

	@Schema(hidden = true)
	private boolean salesRepIdSet;

	@Schema(description = "Corresponding to `C_Project.Description`")
	private String description;

	@Schema(hidden = true)
	private boolean descriptionSet;

	@Schema(description = "Corresponding to `C_Project.DateContract`")
	private LocalDate dateContract;

	@Schema(hidden = true)
	private boolean dateContractSet;

	@Schema(description = "Corresponding to `C_Project.DateFinish`")
	private LocalDate dateFinish;

	@Schema(hidden = true)
	private boolean dateFinishSet;

	@Schema(description = "Corresponding to `C_Project.C_BPartner_ID`")
	private JsonMetasfreshId bpartnerId;

	@Schema(hidden = true)
	private boolean bpartnerIdSet;

	@Schema(description = "Corresponding to `C_Project.C_Project_Reference_Ext`")
	private String projectReferenceExt;

	@Schema(hidden = true)
	private boolean projectReferenceExtSet;

	JsonExternalId externalId;

	@Schema(hidden = true)
	boolean externalIdSet;
	
	@Schema(description = "Corresponding to `C_Project.C_Project_Parent_ID`")
	private String projectParentIdentifier;

	@Schema(hidden = true)
	private boolean projectParentIdentifierSet;

	@Schema(description = "Corresponding to `C_Project.IsActive`")
	private Boolean active;

	@Schema(hidden = true)
	private boolean activeSet;

	@Schema(required = true)
	@Setter
	private SyncAdvise syncAdvise;

	@Schema
	@Setter
	private Map<String, Object> extendedProps = new HashMap<>();

	@Schema(description = "Corresponding to `C_Project_Resource_Budget`")
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