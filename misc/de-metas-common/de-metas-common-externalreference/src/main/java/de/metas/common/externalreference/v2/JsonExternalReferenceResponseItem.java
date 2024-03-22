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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class JsonExternalReferenceResponseItem
{
	public static JsonExternalReferenceResponseItem of(
			@NonNull final JsonExternalReferenceLookupItem lookupItem,
			@NonNull final JsonMetasfreshId metasfreshId)
	{
		return new JsonExternalReferenceResponseItem(lookupItem, metasfreshId, null, null, null, null, null);
	}

	public static JsonExternalReferenceResponseItem of(
			@NonNull final JsonExternalReferenceLookupItem lookupItem)
	{
		return new JsonExternalReferenceResponseItem(lookupItem, null, null, null, null, null, null);
	}

	@ApiModelProperty(required = true, value = "Object used to lookup the item to update. If no existing record is found, one is created. When creating a new record, the properties from the lookup-item are preferred to their counterparts from this object")
	@NonNull
	JsonExternalReferenceLookupItem lookupItem;

	@ApiModelProperty(value = "Translates to `S_ExternalReference.S_ExternalReference`. Mandatory if a new record is created - either here or in the `lookupItem`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String externalReference;

	@ApiModelProperty(value = "Translates to `S_ExternalReference.Record_ID`. Mandatory if a new record is created - either here or in the `lookupItem`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId metasfreshId;

	@ApiModelProperty(value = "Translates to `S_ExternalReference.Version`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String version;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String externalReferenceUrl;

	@ApiModelProperty(value = "Translates to `S_ExternalReference.ExternalSystem`. Mandatory if a new record is created.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonExternalSystemName systemName;

	@ApiModelProperty(value = "Translates to `S_ExternalReference.S_ExternalReference_ID`. Ignored in upserts, only filled by metasfresh in lookups")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId externalReferenceId;

	@JsonCreator
	@Builder
	private JsonExternalReferenceResponseItem(
			@JsonProperty("lookupItem") @NonNull final JsonExternalReferenceLookupItem lookupItem,
			@JsonProperty("metasfreshId") @Nullable final JsonMetasfreshId metasfreshId,
			@JsonProperty("externalReference") @Nullable final String externalReference,
			@JsonProperty("version") @Nullable final String version,
			@JsonProperty("externalReferenceUrl") @Nullable final String externalReferenceUrl,
			@JsonProperty("systemName") @Nullable final JsonExternalSystemName systemName,
			@JsonProperty("externalReferenceId") @Nullable final JsonMetasfreshId externalReferenceId)
	{
		this.lookupItem = lookupItem;
		this.metasfreshId = metasfreshId;
		this.externalReference = externalReference;
		this.version = version;
		this.externalReferenceUrl = externalReferenceUrl;
		this.systemName = systemName;
		this.externalReferenceId = externalReferenceId;
	}
}
