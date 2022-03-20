/*
 * #%L
 * de-metas-common-manufacturing
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

package de.metas.common.handlingunits;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class JsonSetClearanceStatusRequest
{
	@NonNull
	@JsonProperty("huIdentifier")
	JsonHUIdentifier huIdentifier;

	@NonNull
	@JsonProperty("clearanceStatus")
	JsonClearanceStatus clearanceStatus;

	@Nullable
	@JsonProperty("clearanceNote")
	String clearanceNote;

	@Builder
	public JsonSetClearanceStatusRequest(
			@JsonProperty("huIdentifier") @NonNull final JsonSetClearanceStatusRequest.JsonHUIdentifier jsonHuIdentifier,
			@JsonProperty("clearanceStatus") @NonNull final JsonClearanceStatus clearanceStatus,
			@JsonProperty("clearanceNote") @Nullable final String clearanceNote)
	{
		this.huIdentifier = jsonHuIdentifier;
		this.clearanceStatus = clearanceStatus;
		this.clearanceNote = clearanceNote;
	}

	@Value
	public static class JsonHUIdentifier
	{
		@Nullable
		@JsonProperty("metasfreshId")
		JsonMetasfreshId metasfreshId;

		@Nullable
		@JsonProperty("qrCode")
		String qrCode;

		@Builder
		public JsonHUIdentifier(
				@JsonProperty("metasfreshId") @Nullable final JsonMetasfreshId metasfreshId,
				@JsonProperty("qrCode") @Nullable final String qrCode)
		{
			Check.assume(qrCode == null || metasfreshId == null, "metasfreshId and qrCode cannot be set at the same time!");
			Check.assume(qrCode != null || metasfreshId != null, "metasfreshId or qrCode must be set!");

			this.metasfreshId = metasfreshId;
			this.qrCode = qrCode;
		}

		@NonNull
		public static JsonHUIdentifier ofJsonMetasfreshId(@NonNull final JsonMetasfreshId metasfreshId)
		{
			return JsonHUIdentifier.builder()
					.metasfreshId(metasfreshId)
					.build();
		}
	}
}
