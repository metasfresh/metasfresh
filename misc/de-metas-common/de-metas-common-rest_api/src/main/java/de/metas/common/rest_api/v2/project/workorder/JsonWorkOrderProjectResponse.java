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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
	JsonMetasfreshId specialistConsultantId;

	@Nullable
	String internalPriority;

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

	@Builder
	@JsonCreator
	public JsonWorkOrderProjectResponse(
			@JsonProperty("projectId") @NonNull JsonMetasfreshId projectId,
			@JsonProperty("value") @NonNull String value,
			@JsonProperty("name") @NonNull String name,
			@JsonProperty("externalId") @Nullable JsonExternalId externalId,
			@JsonProperty("projectTypeId") @NonNull JsonMetasfreshId projectTypeId,
			@JsonProperty("priceListVersionId") @Nullable JsonMetasfreshId priceListVersionId,
			@JsonProperty("currencyCode") @NonNull String currencyCode,
			@JsonProperty("salesRepId") @Nullable JsonMetasfreshId salesRepId,
			@JsonProperty("description") @Nullable String description,
			@JsonProperty("dateContract") LocalDate dateContract,
			@JsonProperty("dateFinish") LocalDate dateFinish,
			@JsonProperty("bpartnerId") @Nullable JsonMetasfreshId bpartnerId,
			@JsonProperty("projectReferenceExt") @Nullable String projectReferenceExt,
			@JsonProperty("projectParentId") @Nullable JsonMetasfreshId projectParentId,
			@JsonProperty("orgCode") @NonNull String orgCode,
			@JsonProperty("isActive") @Nullable Boolean isActive,
			@JsonProperty("dateOfProvisionByBPartner") LocalDate dateOfProvisionByBPartner,
			@JsonProperty("woOwner") @Nullable String woOwner,
			@JsonProperty("poReference") @Nullable String poReference,
			@JsonProperty("bpartnerDepartment") @Nullable String bpartnerDepartment,
			@JsonProperty("specialistConsultantId") @Nullable JsonMetasfreshId specialistConsultantId,
			@JsonProperty("internalPriority") @Nullable String internalPriority,
			@JsonProperty("bpartnerTargetDate") LocalDate bpartnerTargetDate,
			@JsonProperty("woProjectCreatedDate") LocalDate woProjectCreatedDate,
			@JsonProperty("steps") @Nullable List<JsonWorkOrderStepResponse> steps,
			@JsonProperty("objectsUnderTest") @Nullable List<JsonWorkOrderObjectsUnderTestResponse> objectsUnderTest
	)
	{
		this.projectId = projectId;
		this.value = value;
		this.name = name;
		this.externalId = externalId;
		this.projectTypeId = projectTypeId;
		this.priceListVersionId = priceListVersionId;
		this.currencyCode = currencyCode;
		this.salesRepId = salesRepId;
		this.description = description;
		this.dateContract = dateContract;
		this.dateFinish = dateFinish;
		this.bpartnerId = bpartnerId;
		this.bpartnerDepartment = bpartnerDepartment;
		this.projectReferenceExt = projectReferenceExt;
		this.projectParentId = projectParentId;
		this.orgCode = orgCode;
		this.isActive = isActive;
		this.dateOfProvisionByBPartner = dateOfProvisionByBPartner;
		this.woOwner = woOwner;
		this.poReference = poReference;
		this.specialistConsultantId = specialistConsultantId;
		this.internalPriority = internalPriority;
		this.bpartnerTargetDate = bpartnerTargetDate;
		this.woProjectCreatedDate = woProjectCreatedDate;
		this.steps = steps;
		this.objectsUnderTest = objectsUnderTest;
	}
}