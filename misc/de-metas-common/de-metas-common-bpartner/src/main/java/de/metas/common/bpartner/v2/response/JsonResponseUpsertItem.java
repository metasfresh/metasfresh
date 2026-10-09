/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.v2.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Schema(description = "Response to a a single mater data upsert request entity")
@Value
public class JsonResponseUpsertItem
{
	@Schema(enumAsRef = true, description = "SyncOutcome: \n" +
			"* `CREATED`\n" +
			"* `UPDATED` - Master data was updated; note that it's possible that nothing really changed due to the update.\n" +
			"* `NOTHING_DONE` - E.g. if a location already exists and the sync advise is to not change existing entities.\n" +
			"")
	public enum SyncOutcome
	{
		CREATED,

		UPDATED,

		NOTHING_DONE
	}

	@Schema(description = "The identifier that was specified in the repective upsert request")
	String identifier;

	@Schema(description = "The metasfresh-ID of the upserted record.\n"
			+ "Can be null if the respective resource did not exist and the sync-advise indicated to do nothing.")
	JsonMetasfreshId metasfreshId;

	SyncOutcome syncOutcome;

	String resourceName;

	List<JsonResponseUpsertItem> includedResources;

	@Builder
	@JsonCreator
	public JsonResponseUpsertItem(
			@JsonProperty("identifier") @NonNull final String identifier,
			@JsonProperty("metasfreshId") @Nullable final JsonMetasfreshId metasfreshId,
			@JsonProperty("syncOutcome") @NonNull final SyncOutcome syncOutcome,
			@JsonInclude(JsonInclude.Include.NON_NULL) @JsonProperty("resourceName") @Nullable final String resourceName,
			@JsonInclude(JsonInclude.Include.NON_EMPTY) @JsonProperty("includedResources") @Singular final List<JsonResponseUpsertItem> includedResources)
	{
		this.identifier = identifier;
		this.metasfreshId = metasfreshId;
		this.syncOutcome = syncOutcome;
		this.resourceName = resourceName;
		this.includedResources = includedResources;
	}

}
