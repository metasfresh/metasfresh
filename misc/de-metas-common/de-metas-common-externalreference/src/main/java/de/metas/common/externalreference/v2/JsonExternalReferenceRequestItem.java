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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@ToString
@EqualsAndHashCode
public class JsonExternalReferenceRequestItem
{
	@NonNull
	public static JsonExternalReferenceRequestItem of(
			@NonNull final JsonExternalReferenceLookupItem lookupItem,
			@NonNull final JsonMetasfreshId metasfreshId)
	{
		return JsonExternalReferenceRequestItem.builder()
				.lookupItem(lookupItem)
				.metasfreshId(metasfreshId)
				.build();
	}
	

	@Schema(requiredMode =  REQUIRED, description = "Object used to lookup the item to update. If no existing record is found, one is created. When creating a new record, the properties from the lookup-item are preferred to their counterparts from this object")
	JsonExternalReferenceLookupItem lookupItem;

	@Schema(description = "Translates to `S_ExternalReference.ExternalReference`. Mandatory if a new record is created - either here or in the `lookupItem`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String externalReference;

	@Schema(hidden = true)
	@JsonIgnore
	private boolean externalReferenceSet;

	@Schema(description = "Translates to `S_ExternalReference.Record_ID`. Mandatory if a new record is created - either here or in the `lookupItem`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId metasfreshId;

	@Schema(hidden = true)
	@JsonIgnore
	private boolean metasfreshIdSet;

	@Schema(description = "Translates to `S_ExternalReference.Version`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String version;

	@Schema(hidden = true)
	@JsonIgnore
	private boolean versionSet;

	@Schema(description = "Translates to `S_ExternalReference.ExternalReferenceURL`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String externalReferenceUrl;

	@Schema(hidden = true)
	@JsonIgnore
	private boolean externalReferenceUrlSet;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId externalSystemConfigId;

	@Schema(hidden = true)
	@JsonIgnore
	private boolean externalSystemConfigIdSet;
	
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Boolean readOnlyMetasfresh;

	@Schema(hidden = true)
	@JsonIgnore
	private boolean readOnlyMetasfreshSet;

	/**
	 * This builder is not used by jackson, but when we internally invoke the service. 
	 */
	@Builder
	private JsonExternalReferenceRequestItem(
			@NonNull final JsonExternalReferenceLookupItem lookupItem,
			@Nullable final JsonMetasfreshId metasfreshId,
			@Nullable final String externalReference,
			@Nullable final String version,
			@Nullable final String externalReferenceUrl,
			@Nullable final JsonMetasfreshId externalSystemConfigId,
			@Nullable final Boolean isReadOnlyMetasfresh)
	{
		this.lookupItem = lookupItem;
		
		this.metasfreshId = metasfreshId;
		this.metasfreshIdSet = metasfreshId != null;
		
		this.externalReference = externalReference;
		this.externalReferenceSet = externalReference != null;
		
		this.version = version;
		this.versionSet = version != null;
		
		this.externalReferenceUrl = externalReferenceUrl;
		this.externalReferenceUrlSet = externalReferenceUrl != null;
		
		this.externalSystemConfigId = externalSystemConfigId;
		this.externalSystemConfigIdSet = externalSystemConfigId != null;
		
		this.readOnlyMetasfresh = isReadOnlyMetasfresh;
		this.readOnlyMetasfreshSet = isReadOnlyMetasfresh != null;
	}

	/**
	 * Used by jackson to create an empty instance which can then be filled using the setters.
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
	
	@JsonProperty("externalSystemConfigId")
	public void setExternalSystemConfigId(@Nullable final JsonMetasfreshId externalSystemConfigId)
	{
		this.externalSystemConfigId = externalSystemConfigId;
		this.externalSystemConfigIdSet = true;
	}

	@JsonProperty("readOnlyMetasfresh")
	public void setReadOnlyMetasfresh(final boolean readOnlyMetasfresh)
	{
		this.readOnlyMetasfresh = readOnlyMetasfresh;
		this.readOnlyMetasfreshSet = true;
	}	
}
