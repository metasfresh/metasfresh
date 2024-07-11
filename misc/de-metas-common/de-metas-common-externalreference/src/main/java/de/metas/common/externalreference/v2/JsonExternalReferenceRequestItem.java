/*
 * #%L
 * de-metas-common-externalreference
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

package de.metas.common.externalreference.v2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

@Getter
@ToString
@EqualsAndHashCode
public class JsonExternalReferenceRequestItem
{
	public static JsonExternalReferenceRequestItem of(
			@NonNull final JsonExternalReferenceLookupItem lookupItem,
			@NonNull final JsonMetasfreshId metasfreshId)
	{
		return new JsonExternalReferenceRequestItem(lookupItem, metasfreshId, null, null, null);
	}


	@ApiModelProperty(required = true, value = "Object used to lookup the item to update. If no existing record is found, one is created. When creating a new record, the properties from the lookup-item are preferred to their counterparts from this object")
	@NonNull
	JsonExternalReferenceLookupItem lookupItem;

	@ApiModelProperty(value = "Translates to `S_ExternalReference.ExternalReference`. Mandatory if a new record is created - either here or in the `lookupItem`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String externalReference;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private boolean externalReferenceSet;

	@ApiModelProperty(value = "Translates to `S_ExternalReference.Record_ID`. Mandatory if a new record is created - either here or in the `lookupItem`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId metasfreshId;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private boolean metasfreshIdSet;

	@ApiModelProperty(value = "Translates to `S_ExternalReference.Version`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String version;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private boolean versionSet;

	@ApiModelProperty(value = "Translates to `S_ExternalReference.ExternalReferenceURL`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String externalReferenceUrl;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private boolean externalReferenceUrlSet;

	@Builder
	private JsonExternalReferenceRequestItem(
			@NonNull final JsonExternalReferenceLookupItem lookupItem,
			@Nullable final JsonMetasfreshId metasfreshId,
			@Nullable final String externalReference,
			@Nullable final String version,
			@Nullable final String externalReferenceUrl)
	{
		this.lookupItem = lookupItem;
		this.metasfreshId = metasfreshId;
		this.externalReference = externalReference;
		this.version = version;
		this.externalReferenceUrl = externalReferenceUrl;
	}

	/**
	 * USer by jackon to create an empty instance which can then be filled using the setters.
	 * Note that we have the setters in order to distinguish between property={@code null} and property not specified.
	 */
	public JsonExternalReferenceRequestItem()
	{
	}
	
	@JsonProperty("lookupItem")
	public void setLookupItem(@NonNull final JsonExternalReferenceLookupItem lookupItem)
	{
		this.lookupItem = lookupItem;
	}
	
	@JsonProperty("externalReference")
	public void setExternalReference(@Nullable final String externalReference)
	{
		this.externalReference = externalReference;
		this.externalReferenceSet = true;
	}

	@JsonProperty("metasfreshId")
	public void setMetasfreshId(@Nullable final JsonMetasfreshId metasfreshId)
	{
		this.metasfreshId = metasfreshId;
		this.metasfreshIdSet = true;
	}

	@JsonProperty("version")
	//@JsonSetter
	public void setVersion(@Nullable final String version)
	{
		this.version = version;
		this.versionSet = true;
	}

	@JsonProperty("externalReferenceUrl")
	public void setExternalReferenceUrl(@Nullable final String externalReferenceUrl)
	{
		this.externalReferenceUrl = externalReferenceUrl;
		this.externalReferenceUrlSet = true;
	}
}
