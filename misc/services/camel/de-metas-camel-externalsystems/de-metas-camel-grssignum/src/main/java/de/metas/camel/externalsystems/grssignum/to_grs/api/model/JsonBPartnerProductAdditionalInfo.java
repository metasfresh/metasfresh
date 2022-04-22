/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@JsonDeserialize(builder = JsonBPartnerProductAdditionalInfo.JsonBPartnerProductAdditionalInfoBuilder.class)
public class JsonBPartnerProductAdditionalInfo
{
	@Nullable
	@JsonProperty("ATTACHMENT")
	List<JsonAttachment> attachments;

	@Builder
	public JsonBPartnerProductAdditionalInfo(@JsonProperty("ATTACHMENT") @NonNull final List<JsonAttachment> attachments)
	{
		this.attachments = attachments;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	static class JsonBPartnerProductAdditionalInfoBuilder
	{
	}
}
