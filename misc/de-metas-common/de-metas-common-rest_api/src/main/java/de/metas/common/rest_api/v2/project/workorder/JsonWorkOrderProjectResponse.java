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

import com.fasterxml.jackson.annotation.JsonFormat;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonWorkOrderProjectResponse
{
	@NonNull
	JsonMetasfreshId projectId;

	@NonNull
	String value;

	@NonNull
	String name;

	@Nullable
	JsonExternalId externalId;
	
	@NonNull
	JsonMetasfreshId projectTypeId;

	@Nullable
	JsonMetasfreshId priceListVersionId;

	@NonNull
	String currencyCode;

	@Nullable
	JsonMetasfreshId salesRepId;

	@Nullable
	String description;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateContract;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateFinish;

	@Nullable
	JsonMetasfreshId bpartnerId;

	@Nullable
	String projectReferenceExt;

	@Nullable
	JsonMetasfreshId projectParentId;

	@NonNull
	String orgCode;

	@Nullable
	Boolean isActive;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateOfProvisionByBPartner;

	@Nullable
	String woOwner;

	@Nullable
	String poReference;

	@Nullable
	String bpartnerDepartment;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate bpartnerTargetDate;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate woProjectCreatedDate;

	@Nullable
	List<JsonWorkOrderStepResponse> steps;

	@Nullable
	List<JsonWorkOrderObjectsUnderTestResponse> objectsUnderTest;
}