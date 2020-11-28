package de.metas.rest_api.invoicecandidates.request;

import static de.metas.common.util.CoalesceUtil.coalesce;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
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
public final class JsonEnqueueForInvoicingRequest
{
	@ApiModelProperty(position = 10, required = true, //
			value = "Specifies the invoice candidtes to be invoiced.")
	List<JsonInvoiceCandidateReference> invoiceCandidates;

	@ApiModelProperty(position = 20, value = "Optional invoices' document date", example = "2019-10-30")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	private LocalDate dateInvoiced;

	@ApiModelProperty(position = 30, value = "Optional invoices' accounting date", example = "2019-10-30")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	private LocalDate dateAcct;

	@ApiModelProperty(position = 40, value = "Optional customer's purchase order's documentno. POReference to be set to all invoice candidates, right before enqueueing them.")
	private String poReference;

	@ApiModelProperty(position = 50, required = false, //
			value = "This is needed when the user wants to invoice candidates that have their `DateToInvoice` sometime in the future.\n"
					+ "If this is not set and the DateToInvoice is in the future then an error will occur \"no invoicable ICs selected\n"
					+ "Default = `false`")
	private Boolean ignoreInvoiceSchedule;

	@ApiModelProperty(position = 60, required = false,//
			value = "Specifies whether invoice candidate that have no payment term shall be updated with the reference of another selected invoice candidate.\n"
					+ "Default = `true`")
	private Boolean supplementMissingPaymentTermIds;

	@ApiModelProperty(position = 70, required = false,//
			value = "If this parameter is activated, the invoices to be created receive the current users and locations of their business partners, regardless of the values in `Bill_Location_ID` and `Bill_User_ID` that are set in the queued billing candidates.\n"
					+ "Default = `false`")
	private Boolean updateLocationAndContactForInvoice;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonEnqueueForInvoicingRequest(
			@JsonProperty("invoiceCandidates") @Singular final List<JsonInvoiceCandidateReference> invoiceCandidates,
			@JsonProperty("dateInvoiced") @Nullable final LocalDate dateInvoiced,
			@JsonProperty("dateAcct") @Nullable final LocalDate dateAcct,
			@JsonProperty("poReference") @Nullable final String poReference,
			@JsonProperty("ignoreInvoiceSchedule") @Nullable final Boolean ignoreInvoiceSchedule,
			@JsonProperty("supplementMissingPaymentTermIds") @Nullable final Boolean supplementMissingPaymentTermIds,
			@JsonProperty("updateLocationAndContactForInvoice") @Nullable final Boolean updateLocationAndContactForInvoice)
	{
		this.invoiceCandidates = ImmutableList.copyOf(invoiceCandidates);
		this.poReference = poReference;
		this.dateAcct = dateAcct;
		this.dateInvoiced = dateInvoiced;
		this.ignoreInvoiceSchedule = coalesce(ignoreInvoiceSchedule, false);
		this.supplementMissingPaymentTermIds = coalesce(supplementMissingPaymentTermIds, true);
		this.updateLocationAndContactForInvoice = coalesce(updateLocationAndContactForInvoice, false);
	}
}
