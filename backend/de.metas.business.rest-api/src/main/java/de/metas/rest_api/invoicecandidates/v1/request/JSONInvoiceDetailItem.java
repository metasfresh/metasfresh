/*
 * #%L
 * de.metas.business.rest-api
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

package de.metas.rest_api.invoicecandidates.v1.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class JSONInvoiceDetailItem
{
	Integer seqNo;

	String label;

	String description;

	LocalDate date;
	
	BigDecimal price;
	
	String note;

	@JsonCreator
	@Builder
	public JSONInvoiceDetailItem(
			@JsonProperty("seqNo") @Nullable final Integer seqNo,
			@JsonProperty("label") @Nullable final String label,
			@JsonProperty("description") @Nullable final String description,
			@JsonProperty("date") @Nullable final LocalDate date,
	        @JsonProperty("price") @Nullable final BigDecimal price,
	        @JsonProperty("note") @Nullable final String note)
	{
		this.seqNo = seqNo;
		this.label = label;
		this.description = description;
		this.date = date;
		this.price = price;
		this.note = note;
	}
}
