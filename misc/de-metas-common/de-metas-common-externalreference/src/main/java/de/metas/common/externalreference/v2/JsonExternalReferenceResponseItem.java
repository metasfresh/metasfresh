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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Value
public class JsonExternalReferenceResponseItem
{
	@NonNull
	public static JsonExternalReferenceResponseItem of(
			@NonNull final JsonExternalReferenceLookupItem lookupItem,
			@NonNull final JsonMetasfreshId metasfreshId)
	{
		return JsonExternalReferenceResponseItem.builder()
				.lookupItem(lookupItem)
				.metasfreshId(metasfreshId)
				.build();
	}

	@NonNull
	public static JsonExternalReferenceResponseItem of(
			@NonNull final JsonExternalReferenceLookupItem lookupItem)
	{
		return JsonExternalReferenceResponseItem.builder()
				.lookupItem(lookupItem)
				.build();
	}

	@Schema(requiredMode = REQUIRED, description = "Object used to lookup the item in question.")
	@NonNull
	JsonExternalReferenceLookupItem lookupItem;

	@Schema(description = "Translates to `S_ExternalReference.S_ExternalReference`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String externalReference;

	@Schema(description = "Translates to `S_ExternalReference.Record_ID`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId metasfreshId;

	@Schema(description = "Translates to `S_ExternalReference.Version`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String version;

	@Schema(description = "Translates to `S_ExternalReference.ExternalReferenceURL`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String externalReferenceUrl;

	@Schema(description = "Translates to `S_ExternalReference.ExternalSystem`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonExternalSystemName systemName;

	@Schema(description = "Translates to `S_ExternalReference.S_ExternalReference_ID`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId externalReferenceId;

	@Schema(description = "Translates to `S_ExternalReference.ExternalSystem_Config_ID`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId externalSystemConfigId;

	/**
	 * Note: may be null if no external reference was found for the respective lookup request item.
	 */
	@Schema(description = "Translates to `S_ExternalReference.IsReadOnlyInMetasfresh`.")
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Boolean readOnlyMetasfresh;
	
	@JsonCreator
	@Builder
	private JsonExternalReferenceResponseItem(
			@JsonProperty("lookupItem") @NonNull final JsonExternalReferenceLookupItem lookupItem,
			@JsonProperty("externalReference") @Nullable final String externalReference,
			@JsonProperty("metasfreshId") @Nullable final JsonMetasfreshId metasfreshId,
			@JsonProperty("version") @Nullable final String version,
			@JsonProperty("externalReferenceUrl") @Nullable final String externalReferenceUrl,
			@JsonProperty("systemName") @Nullable final JsonExternalSystemName systemName,
			@JsonProperty("externalReferenceId") @Nullable final JsonMetasfreshId externalReferenceId,
			@JsonProperty("externalSystemConfigId") @Nullable final JsonMetasfreshId externalSystemConfigId,
			@JsonProperty("readOnlyMetasfresh") @Nullable final Boolean readOnlyMetasfresh)
	{
		this.lookupItem = lookupItem;
		this.metasfreshId = metasfreshId;
		this.externalReference = externalReference;
		this.version = version;
		this.externalReferenceUrl = externalReferenceUrl;
		this.systemName = systemName;
		this.externalReferenceId = externalReferenceId;
		this.externalSystemConfigId = externalSystemConfigId;
		this.readOnlyMetasfresh = readOnlyMetasfresh;
	}
}