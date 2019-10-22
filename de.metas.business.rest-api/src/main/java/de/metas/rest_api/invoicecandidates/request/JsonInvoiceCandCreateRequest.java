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
			value = "These are the invoices json objects</code>.\n")
	List<JsonInvoiceCandidate> jsonInvoices;

	@ApiModelProperty(value = "Date of the invoice.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	private LocalDate dateInvoiced;

	@ApiModelProperty(value = "Accounting date.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	private LocalDate dateAcct;

	@ApiModelProperty(value = "External reference (document number) on a remote system. Not neccesarily unique, but but the external user will want to filter recrods using it")
	private String poReference;

	@ApiModelProperty(required = true, value = "Ignore invoice schedule.")
	private Boolean ignoreInvoiceSchedule;

	@ApiModelProperty(required = true,//
			value = "Specifies whether RKn with an empty payment term (for example, empties returns) should automatically accept the payment term of another RK.")
	private Boolean supplementMissingPaymentTermIds;

	@ApiModelProperty(required = true,//
			value = "If this parameter is activated, the invoices to be created receive the current users and locations of their business partners, regardless of the values in Bill_Location and Bill_User that are set in the queued billing candidates.")
	private Boolean updateLocationAndContactForInvoice;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonInvoiceCandCreateRequest(
			@JsonProperty("jsonInvoices") @Singular final List<JsonInvoiceCandidate> jsonInvoices,
			@JsonProperty("dateInvoiced") final LocalDate dateInvoiced,
			@JsonProperty("dateAcct") final LocalDate dateAcct,
			@JsonProperty("poReference") final String poReference,
			@JsonProperty("ignoreInvoiceSchedule") final @NonNull Boolean ignoreInvoiceSchedule,
			@JsonProperty("supplementMissingPaymentTermIds") final @NonNull Boolean supplementMissingPaymentTermIds,
			@JsonProperty("updateLocationAndContactForInvoice") final @NonNull Boolean updateLocationAndContactForInvoice)
	{
		this.jsonInvoices = ImmutableList.copyOf(jsonInvoices);
		this.poReference = poReference;
		this.dateAcct = dateAcct;
		this.dateInvoiced = dateInvoiced;
		this.ignoreInvoiceSchedule = ignoreInvoiceSchedule;
		this.supplementMissingPaymentTermIds = supplementMissingPaymentTermIds;
		this.updateLocationAndContactForInvoice = updateLocationAndContactForInvoice;
	}
}
