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

package de.metas.common.bpartner.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.pentabyte.springfox.ApiEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@ApiModel("Response to a a single mater data upsert request entity")
@Value
public class JsonResponseUpsertItem
{
	public enum SyncOutcome
	{
		CREATED,

		@ApiEnum("Master data was updated; note that it's possible that nothing really changed due to the update.")
		UPDATED,

		@ApiEnum("E.g. if a location already exists and the sync advise is to not change existing entities.")
		NOTHING_DONE
	}

	@ApiModelProperty(value = "The identifier that was specified in the repective upsert request",//
			position = 10)
	String identifier;

	@ApiModelProperty(value = "The metasfresh-ID of the upserted record.\n"
			+ "Can be null if the respective resource did not exist and the sync-advise indicated to do nothing.",//
			position = 20, dataType = "java.lang.Long")
	JsonMetasfreshId metasfreshId;

	SyncOutcome syncOutcome;

	@Builder
	@JsonCreator
	public JsonResponseUpsertItem(
			@JsonProperty("identifier") @NonNull final String identifier,
			@JsonProperty("metasfreshId") @Nullable final JsonMetasfreshId metasfreshId,
			@JsonProperty("syncOutcome") @NonNull final SyncOutcome syncOutcome)
	{
		this.identifier = identifier;
		this.metasfreshId = metasfreshId;
		this.syncOutcome = syncOutcome;
	}

}
