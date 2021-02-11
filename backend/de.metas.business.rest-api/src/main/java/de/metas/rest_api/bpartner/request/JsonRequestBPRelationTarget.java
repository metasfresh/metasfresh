/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.bpartner.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.SwaggerDocConstants;
import de.metas.rest_api.common.JsonBPRelationRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@ApiModel
public class JsonRequestBPRelationTarget
{
	@ApiModelProperty(required = true, //
			dataType = "java.lang.String", //
			value = SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC)
	String targetBpartnerIdentifier;
	//not needed when bp lookup by GLN
	@ApiModelProperty
	@Nullable
	String targetLocationIdentifier;

	@ApiModelProperty
	@NonNull String name;
	@ApiModelProperty
	@Nullable
	String description;
	@ApiModelProperty
	@Nullable
	String externalId;
	@ApiModelProperty
	JsonBPRelationRole role;

	//following if left null, will be defaulted when creating, or left unchanged if updating
	@ApiModelProperty
	@Nullable
	Boolean billTo;
	@ApiModelProperty
	@Nullable
	Boolean fetchedFrom;
	@ApiModelProperty
	@Nullable
	Boolean handOverLocation;
	@ApiModelProperty
	@Nullable
	Boolean payFrom;
	@ApiModelProperty
	@Nullable
	Boolean remitTo;
	@ApiModelProperty
	@Nullable
	Boolean shipTo;

	@ApiModelProperty
	@Nullable
	Boolean active;

	@JsonCreator
	@Builder(toBuilder = true)
	public JsonRequestBPRelationTarget(@NonNull @JsonProperty("targetBpartnerIdentifier") final String targetBpartnerIdentifier,
			@Nullable @JsonProperty("targetLocationIdentifier") final String targetLocationIdentifier,
			@NonNull @JsonProperty("name") final String name,
			@Nullable @JsonProperty("description") final String description,
			@Nullable @JsonProperty("externalId") final String externalId,
			@Nullable @JsonProperty("role") final JsonBPRelationRole role,
			@Nullable @JsonProperty("billTo") final Boolean billTo,
			@Nullable @JsonProperty("fetchedFrom") final Boolean fetchedFrom,
			@Nullable @JsonProperty("payFrom") final Boolean payFrom,
			@Nullable @JsonProperty("handOverLocation") final Boolean handOverLocation,
			@Nullable @JsonProperty("remitTo") final Boolean remitTo,
			@Nullable @JsonProperty("shipTo") final Boolean shipTo,
			@Nullable @JsonProperty("active") final Boolean active)
	{
		this.targetBpartnerIdentifier = targetBpartnerIdentifier;
		this.targetLocationIdentifier = targetLocationIdentifier;
		this.name = name;
		this.description = description;
		this.externalId = externalId;
		this.role = role;
		this.billTo = billTo;
		this.fetchedFrom = fetchedFrom;
		this.payFrom = payFrom;
		this.handOverLocation = handOverLocation;
		this.remitTo = remitTo;
		this.shipTo = shipTo;
		this.active = active;
	}
}
