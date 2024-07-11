/*
 * #%L
 * de-metas-common-externalsystem
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

package de.metas.common.externalsystem.status;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@JsonDeserialize(builder = JsonStatusRequest.JsonStatusRequestBuilder.class)
public class JsonStatusRequest
{
	@NonNull
	@JsonProperty("status")
	JsonExternalStatus status;

	@Nullable
	@JsonProperty("adIssueId")
	JsonMetasfreshId adIssueId;

	@Nullable
	@JsonProperty("adPInstanceId")
	JsonMetasfreshId pInstanceId;

	@Nullable
	@JsonProperty("message")
	String message;

	@Builder
	@JsonCreator
	private JsonStatusRequest(
			@JsonProperty("status") @NonNull final JsonExternalStatus status,
			@JsonProperty("adIssueId") @Nullable final JsonMetasfreshId adIssueId,
			@JsonProperty("adPInstanceId") @Nullable final JsonMetasfreshId pInstanceId,
			@JsonProperty("message") @Nullable final String message)
	{
		this.status = status;
		this.adIssueId = adIssueId;
		this.pInstanceId = pInstanceId;
		this.message = message;
	}
}
