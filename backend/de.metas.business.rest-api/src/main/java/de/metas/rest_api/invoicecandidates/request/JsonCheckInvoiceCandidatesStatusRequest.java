package de.metas.rest_api.invoicecandidates.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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
public class JsonCheckInvoiceCandidatesStatusRequest
{
	@ApiModelProperty(position = 10, required = true, //
			value = "Specifies the invoice candidates to return the invoicing status of.")
	List<JsonInvoiceCandidateReference> invoiceCandidates;

	@JsonCreator
	public JsonCheckInvoiceCandidatesStatusRequest(
			@JsonProperty("invoiceCandidates") @Singular @NonNull final List<JsonInvoiceCandidateReference> invoiceCandidates)
	{
		this.invoiceCandidates = invoiceCandidates;
	}
}
