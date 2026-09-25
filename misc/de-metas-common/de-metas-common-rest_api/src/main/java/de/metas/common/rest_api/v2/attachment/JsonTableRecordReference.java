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

package de.metas.common.rest_api.v2.attachment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Addresses a metasfresh record, either by its table's name or by its {@code AD_Table_ID}.
 * Exactly one of the two has to be given; supplying both or neither is rejected.
 */
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = JsonTableRecordReference.JsonTableRecordReferenceBuilder.class)
public class JsonTableRecordReference
{
	@Nullable
	@JsonProperty("tableName")
	String tableName;

	@Nullable
	@JsonProperty("adTableId")
	Integer adTableId;

	@NonNull
	@JsonProperty("recordId")
	JsonMetasfreshId recordId;

	@Builder
	public JsonTableRecordReference(
			@Nullable @JsonProperty("tableName") final String tableName,
			@Nullable @JsonProperty("adTableId") final Integer adTableId,
			@NonNull @JsonProperty("recordId") final JsonMetasfreshId recordId)
	{
		// note that a blank tableName counts as "not given"; otherwise it would slip through to the
		// record-reference lookup, which then fails with a much less helpful error.
		final boolean hasTableName = !Check.isBlank(tableName);
		final boolean hasAdTableId = adTableId != null;

		if (hasTableName == hasAdTableId)
		{
			throw Check.mkEx("Exactly one of tableName and adTableId needs to be provided;"
					+ " tableName=" + tableName + ", adTableId=" + adTableId);
		}

		this.tableName = hasTableName ? tableName : null;
		this.adTableId = adTableId;
		this.recordId = recordId;
	}
}

