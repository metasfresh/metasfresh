package de.metas.rest_api.invoicecandidates.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
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
public final class JsonInvoiceCandCreateRequest
{
	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = "<code>These are the invoice candidates json objects</code>.\n")
	List<JsonInvoiceCandidate> invoiceCandidates;

	@ApiModelProperty(value = "Date of the invoice.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	private LocalDate dateInvoiced;

	@ApiModelProperty(value = "Accounting date.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	private LocalDate dateAcct;

	@ApiModelProperty(value = "External customer's purchase order's documentno. POReference to be set to all invoice candidates, right before enqueueing them.")
	private String poReference;

	@ApiModelProperty(required = true, value = "This is needed when the user wants to invoice sth that is not yet delivered, the DateToInvoice is sometime in the future."
			+ "If this is not set and the DateToInvoice is in the future then an error will occur \"no invoicable ICs selected)")
	private Boolean ignoreInvoiceSchedule;

	@ApiModelProperty(required = true,//
			value = "Specifies whether invoice candidate that have no payment term shall be updated with the reference of another selected invoice candidate.")
	private Boolean supplementMissingPaymentTermIds;

	@ApiModelProperty(required = true,//
			value = "If this parameter is activated, the invoices to be created receive the current users and locations of their business partners, regardless of the values in Bill_Location and Bill_User that are set in the queued billing candidates.")
	private Boolean updateLocationAndContactForInvoice;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonInvoiceCandCreateRequest(
			@JsonProperty("jsonInvoices") @Singular final List<JsonInvoiceCandidate> invoiceCandidates,
			@JsonProperty("dateInvoiced") final LocalDate dateInvoiced,
			@JsonProperty("dateAcct") final LocalDate dateAcct,
			@JsonProperty("poReference") final String poReference,
			@JsonProperty("ignoreInvoiceSchedule") final Boolean ignoreInvoiceSchedule,
			@JsonProperty("supplementMissingPaymentTermIds") final Boolean supplementMissingPaymentTermIds,
			@JsonProperty("updateLocationAndContactForInvoice") final Boolean updateLocationAndContactForInvoice)
	{
		this.invoiceCandidates = ImmutableList.copyOf(invoiceCandidates);
		this.poReference = poReference;
		this.dateAcct = dateAcct;
		this.dateInvoiced = dateInvoiced;
		this.ignoreInvoiceSchedule = ignoreInvoiceSchedule;
		this.supplementMissingPaymentTermIds = supplementMissingPaymentTermIds;
		this.updateLocationAndContactForInvoice = updateLocationAndContactForInvoice;
	}
}
