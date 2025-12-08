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
import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@JsonDeserialize(builder = JsonAttachmentRequest.JsonAttachmentRequestBuilder.class)
public class JsonAttachmentRequest
{
	@NonNull
	@JsonProperty("orgCode")
	String orgCode;

	@NonNull
	@JsonProperty("targets")
	List<JsonExternalReferenceTarget> targets;

	@NonNull
	@JsonProperty("attachment")
	JsonAttachment attachment;

	@NonNull
	@JsonProperty("references")
	List<JsonTableRecordReference> references;

	@Builder
	public JsonAttachmentRequest(
			@NonNull @JsonProperty("orgCode") final String orgCode,
			@NonNull @JsonProperty("attachment") final JsonAttachment attachment,
			@Nullable @JsonProperty("targets") final List<JsonExternalReferenceTarget> targets,
			@Nullable @JsonProperty("references") @Singular final List<JsonTableRecordReference> references)
	{
		if (targets == null && references == null)
		{
			throw new RuntimeException("targets and references cannot be null at the same time. At least one must be provided!");
		}

		this.orgCode = orgCode;
		this.attachment = attachment;

		this.targets = CoalesceUtil.coalesce(targets, ImmutableList.of());
		this.references = CoalesceUtil.coalesce(references, ImmutableList.of());
	}
}
