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

package de.metas.rest_api.invoicecandidates.request;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
public class JSONInvoiceDetailItem
{
	Integer seqNo;

	String label;

	String description;

	LocalDate date;

	@JsonCreator
	@Builder
	public JSONInvoiceDetailItem(
			@JsonProperty("seqNo") @Nullable final Integer seqNo,
			@JsonProperty("label") @Nullable final String label,
			@JsonProperty("description") @Nullable final String description,
			@JsonProperty("date") @Nullable final LocalDate date)
	{
		this.seqNo = seqNo;
		this.label = label;
		this.description = description;
		this.date = date;
	}
}
