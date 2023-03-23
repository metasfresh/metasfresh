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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(builder = JsonResponseProject.JsonResponseProjectBuilder.class)
public class JsonResponseProject
{
	@Schema(required = true,
			description = "This translates to `C_Project.C_Project_ID`.")
	@NonNull
	@JsonProperty("id")
	JsonMetasfreshId id;

	@Schema(required = true, description = "This translates to `C_Project.AD_Org_ID`.")
	@NonNull
	@JsonProperty("orgId")
	JsonMetasfreshId orgId;

	@Schema(required = true, description = "This translates to `C_Project.C_Currency_Id.Iso_Code`.")
	@NonNull
	@JsonProperty("currencyCode")
	String currencyCode;

	@Schema(required = true, description = "This translates to `C_Project.Name`.")
	@NonNull
	@JsonProperty("name")
	String name;

	@Schema(required = true, description = "This translates to `C_Project.Value`.")
	@NonNull
	@JsonProperty("value")
	String value;

	@Schema(description = "This translates to `C_BPartner.IsActive`.")
	@JsonProperty("active")
	boolean active;

	@Schema(description = "This translates to `C_Project.C_BPartner_Location_ID`.")
	@Nullable
	@JsonProperty("bpartnerLocationId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId bpartnerLocationId;

	@Schema(description = "This translates to `C_Project.M_PriceList_Version_ID`.")
	@Nullable
	@JsonProperty("priceListVersionId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId priceListVersionId;

	@Schema(description = "This translates to `C_Project.M_Warehouse_ID`.")
	@Nullable
	@JsonProperty("warehouseId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId warehouseId;

	@Schema(description = "This translates to `C_Project.AD_User_ID`.")
	@Nullable
	@JsonProperty("contactId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId contactId;

	@Schema(description = "This translates to `C_Project.Description`.")
	@Nullable
	@JsonProperty("description")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String description;

	@Schema(description = "This translates to `C_Project.C_Project_Parent_ID`.")
	@Nullable
	@JsonProperty("projectParentId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId projectParentId;

	@Schema(description = "This translates to `C_Project.C_ProjectType_ID`.")
	@Nullable
	@JsonProperty("projectTypeId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId projectTypeId;

	@Schema(description = "This translates to `C_Project.ProjectCategory`.")
	@Nullable
	@JsonProperty("projectCategory")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String projectCategory;

	@Schema(description = "This translates to `C_Project.R_StatusCategory_ID`.")
	@NonNull
	@JsonProperty("requestStatusCategoryId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId requestStatusCategoryId;

	@Schema(description = "This translates to `C_Project.R_Project_Status_ID`.")
	@Nullable
	@JsonProperty("projectStatusId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId projectStatusId;

	@Schema(description = "This translates to `C_Project.C_BPartner_ID`.")
	@Nullable
	@JsonProperty("bpartnerId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId bpartnerId;

	@Schema(description = "This translates to `C_Project.SalesRep_ID`.")
	@Nullable
	@JsonProperty("salesRepId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId salesRepId;

	@Schema(description = "This translates to `C_Project.DateContract`.")
	@Nullable
	@JsonProperty("dateContract")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateContract;

	@Schema(description = "This translates to `C_Project.DateFinish`.")
	@Nullable
	@JsonProperty("dateFinish")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateFinish;
}
