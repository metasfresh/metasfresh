package de.metas.rest_api.invoicecandidates.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.rest_api.utils.MetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonInvoiceStatus
{
	MetasfreshId metasfreshId;

	@NonNull
	String documentNo;

	@NonNull
	String docStatus;

	LocalDate dateInvoiced;

	boolean pdfAvailable ;

	@Builder
	@JsonCreator
	public JsonInvoiceStatus(
			@JsonProperty("metasfreshId") final MetasfreshId metasfreshId,
			@JsonProperty("documentNo") @NonNull final String documentNo,
			@JsonProperty("docStatus") @NonNull final String docStatus,
			@JsonProperty("dateInvoiced") final LocalDate dateInvoiced,
			@JsonProperty("pdfAvailable") final boolean pdfAvailable)
	{
		this.metasfreshId = metasfreshId;
		this.documentNo = documentNo;
		this.docStatus = docStatus;
		this.dateInvoiced = dateInvoiced;
		this.pdfAvailable = pdfAvailable;
	}
}
