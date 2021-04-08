/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.rest_api.invoicecandidates.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class JsonWorkPackageStatus
{
	JsonMetasfreshId metasfreshId;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String error;

	Instant enqueued;

	boolean readyForProcessing;

	@Builder
	@JsonCreator
	public JsonWorkPackageStatus(@JsonProperty("metasfreshId") @NonNull final JsonMetasfreshId metasfreshId,
			@JsonProperty("error") @Nullable final String error,
			@JsonProperty("enqueued") final Instant enqueued,
			@JsonProperty("readyForProcessing") final boolean readyForProcessing)
	{
		this.metasfreshId = metasfreshId;
		this.error = error;
		this.enqueued = enqueued;
		this.readyForProcessing = readyForProcessing;
	}
}