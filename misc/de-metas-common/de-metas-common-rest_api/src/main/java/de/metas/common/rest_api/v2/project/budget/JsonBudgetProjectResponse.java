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
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Value
public class JsonBudgetProjectResponse
{
	@Schema( //
			required = true,
			description = "This translates to `C_Project.C_Project_ID`.")
	@NonNull
	JsonMetasfreshId projectId;

	@Schema(required = true, description = "This translates to `C_Project.AD_Org_ID.Value`.")
	@NonNull
	String orgCode;

	@Schema(description = "This translates to `C_Project.External`.")
	JsonExternalId externalId;
	
	@Schema(required = true, description = "This translates to `C_Project.C_Currency_ID.Iso_Code`.")
	@NonNull
	String currencyCode;

	@Schema(required = true, description = "This translates to `C_Project.Name`.")
	@NonNull
	String name;

	@Schema(required = true, description = "This translates to `C_Project.Value`.")
	@NonNull
	String value;

	@Schema(description = "This translates to `C_BPartner.IsActive`.")
	@Nullable
	Boolean isActive;

	@Schema(description = "This translates to `C_Project.M_PriceList_Version_ID`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId priceListVersionId;

	@Schema(description = "This translates to `C_Project.Description`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String description;

	@Schema(description = "This translates to `C_Project.C_Project_Parent_ID`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId projectParentId;

	@Schema(required = true, description = "This translates to `C_Project.C_ProjectType_ID`.")
	@NonNull
	JsonMetasfreshId projectTypeId;

	@Schema(description = "This translates to `C_Project.C_Project_Reference_Ext`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String projectReferenceExt;

	@Schema(description = "This translates to `C_Project.C_BPartner_ID`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId bpartnerId;

	@Schema(description = "This translates to `C_Project.SalesRep_ID`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId salesRepId;

	@Schema(description = "This translates to `C_Project.DateContract`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateContract;

	@Schema(description = "This translates to `C_Project.DateFinish`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateFinish;

	@NonNull
	Map<String, Object> extendedProps;

	@NonNull
	List<JsonBudgetProjectResourceResponse> projectResources;

	@Builder
	@Jacksonized
	public JsonBudgetProjectResponse(
			@NonNull final JsonMetasfreshId projectId,
			@NonNull final String orgCode,
			@Nullable final JsonExternalId externalId, 
			@NonNull final String currencyCode,
			@NonNull final String name,
			@NonNull final String value,
			@Nullable final Boolean isActive,
			@Nullable final JsonMetasfreshId priceListVersionId,
			@Nullable final String description,
			@Nullable final JsonMetasfreshId projectParentId,
			@NonNull final JsonMetasfreshId projectTypeId,
			@Nullable final String projectReferenceExt,
			@Nullable final JsonMetasfreshId bpartnerId,
			@Nullable final JsonMetasfreshId salesRepId,
			@Nullable final LocalDate dateContract,
			@Nullable final LocalDate dateFinish,
			@NonNull final Map<String, Object> extendedProps,
			@NonNull @Singular final List<JsonBudgetProjectResourceResponse> projectResources)
	{
		this.projectId = projectId;
		this.orgCode = orgCode;
		this.externalId = externalId;
		this.currencyCode = currencyCode;
		this.name = name;
		this.value = value;
		this.isActive = isActive;
		this.priceListVersionId = priceListVersionId;
		this.description = description;
		this.projectParentId = projectParentId;
		this.projectTypeId = projectTypeId;
		this.projectReferenceExt = projectReferenceExt;
		this.bpartnerId = bpartnerId;
		this.salesRepId = salesRepId;
		this.dateContract = dateContract;
		this.dateFinish = dateFinish;
		this.extendedProps = extendedProps;
		this.projectResources = projectResources;
	}
}