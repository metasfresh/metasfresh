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

package de.metas.common.rest_api.v2.attachment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@JsonDeserialize(builder = JsonAttachmentResponse.JsonAttachmentResponseBuilder.class)
public class JsonAttachmentResponse
{
	@NonNull
	@JsonProperty("target")
	List<JsonExternalReferenceTarget> target;

	@NonNull
	@JsonProperty("attachmentId")
	String attachmentId;

	@NonNull
	@JsonProperty("filename")
	String filename;

	@Nullable
	@JsonProperty("mimeType")
	String mimeType;
}
