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

package de.metas.rest_api.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.ZonedDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class JsonPurchaseOrder
{
	@JsonProperty("dateOrdered")
	ZonedDateTime dateOrdered;

	@JsonProperty("datePromised")
	ZonedDateTime datePromised;

	@JsonProperty("docStatus")
	String docStatus;

	@JsonProperty("documentNo")
	String documentNo;

	@JsonProperty("metasfreshId")
	JsonMetasfreshId metasfreshId;

	@JsonProperty("pdfAvailable")
	boolean pdfAvailable;

	@Builder
	@JsonCreator
	public JsonPurchaseOrder(@JsonProperty("dateOrdered") @NonNull final ZonedDateTime dateOrdered,
			@JsonProperty("datePromised") @NonNull final ZonedDateTime datePromised,
			@JsonProperty("docStatus") @NonNull final String docStatus,
			@JsonProperty("documentNo") @NonNull final String documentNo,
			@JsonProperty("metasfreshId") @NonNull final JsonMetasfreshId metasfreshId,
			@JsonProperty("pdfAvailable") final boolean pdfAvailable)
	{
		this.dateOrdered = dateOrdered;
		this.datePromised = datePromised;
		this.docStatus = docStatus;
		this.documentNo = documentNo;
		this.metasfreshId = metasfreshId;
		this.pdfAvailable = pdfAvailable;
	}
}
