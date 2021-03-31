package de.metas.rest_api.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.v1.attachment.JsonAttachmentType;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JsonSalesOrderAttachment
{
	@JsonProperty("salesOrderId")
	private String salesOrderId;

	@JsonProperty("id")
	private final int id;

	@JsonProperty("type")
	private final JsonAttachmentType type;

	@JsonProperty("filename")
	private final String filename;

	@JsonProperty("mimeType")
	private final String mimeType;

	@JsonProperty("url")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String url;

	@JsonCreator
	@Builder
	private JsonSalesOrderAttachment(
			@JsonProperty("salesOrderId") final String salesOrderId,
			@JsonProperty("id") final int id,
			@JsonProperty("type") final JsonAttachmentType type,
			@JsonProperty("filename") final String filename,
			@JsonProperty("mimeType") final String mimeType,
			@JsonProperty("url") final String url)
	{
		this.salesOrderId = salesOrderId;
		this.id = id;
		this.type = type;
		this.filename = filename;
		this.mimeType = mimeType;
		this.url = url;
	}

}
