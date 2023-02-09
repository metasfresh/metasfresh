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
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;

@Value
public class JsonExternalReferenceItem
{
	@NonNull
	public static JsonExternalReferenceItem of(
			@NonNull final JsonExternalReferenceLookupItem lookupItem,
			@NonNull final JsonMetasfreshId metasfreshId)
	{
		return JsonExternalReferenceItem.builder()
				.lookupItem(lookupItem)
				.metasfreshId(metasfreshId)
				.build();
	}

	@NonNull
	public static JsonExternalReferenceItem of(
			@NonNull final JsonExternalReferenceLookupItem lookupItem)
	{
		return JsonExternalReferenceItem.builder()
				.lookupItem(lookupItem)
				.build();
	}

	@NonNull
	JsonExternalReferenceLookupItem lookupItem;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String externalReference;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@With
	JsonMetasfreshId metasfreshId;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String version;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String externalReferenceUrl;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonExternalSystemName systemName;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId externalReferenceId;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId externalSystemConfigId;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Boolean isReadOnlyMetasfresh;

	@JsonCreator
	@Builder
	private JsonExternalReferenceItem(
			@JsonProperty("lookupItem") @NonNull final JsonExternalReferenceLookupItem lookupItem,
			@JsonProperty("externalReference") @Nullable final String externalReference,
			@JsonProperty("metasfreshId") @Nullable final JsonMetasfreshId metasfreshId,
			@JsonProperty("version") @Nullable final String version,
			@JsonProperty("externalReferenceUrl") @Nullable final String externalReferenceUrl,
			@JsonProperty("systemName") @Nullable final JsonExternalSystemName systemName,
			@JsonProperty("externalReferenceId") @Nullable final JsonMetasfreshId externalReferenceId,
			@JsonProperty("externalSystemConfigId") @Nullable final JsonMetasfreshId externalSystemConfigId,
			@JsonProperty("isReadOnlyMetasfresh") @Nullable final Boolean isReadOnlyMetasfresh)
	{
		this.lookupItem = lookupItem;
		this.metasfreshId = metasfreshId;
		this.externalReference = externalReference;
		this.version = version;
		this.externalReferenceUrl = externalReferenceUrl;
		this.systemName = systemName;
		this.externalReferenceId = externalReferenceId;
		this.externalSystemConfigId = externalSystemConfigId;
		this.isReadOnlyMetasfresh = isReadOnlyMetasfresh;
	}
}