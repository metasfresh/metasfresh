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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

import static de.metas.common.util.CoalesceUtil.coalesce;

@Value
public class JsonEnqueueForInvoicingRequest
{
	@Schema(required = true, //
			description = "Specifies the invoice candidates to be invoiced.")
	List<JsonInvoiceCandidateReference> invoiceCandidates;

	@Schema(description = "Optional invoices' document date", example = "2019-10-30")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	LocalDate dateInvoiced;

	@Schema(description = "Optional invoices' accounting date", example = "2019-10-30")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	LocalDate dateAcct;

	@Schema(description = "Optional customer's purchase order's documentno. POReference to be set to all invoice candidates, right before enqueueing them.")
	String poReference;

	@Schema(description = "This is needed when the user wants to invoice candidates that have their `DateToInvoice` sometime in the future.\n"
					+ "If this is not set and the DateToInvoice is in the future then an error will occur \"no invoicable ICs selected\n"
					+ "Default = `false`")
	Boolean ignoreInvoiceSchedule;

	@Schema(description = "Specifies whether invoice candidate that have no payment term shall be updated with the reference of another selected invoice candidate.\n"
					+ "Default = `true`")
	Boolean supplementMissingPaymentTermIds;

	@Schema(description = "If this parameter is activated, the invoices to be created receive the current users and locations of their business partners, regardless of the values in `Bill_Location_ID` and `Bill_User_ID` that are set in the queued billing candidates.\n"
					+ "Default = `false`")
	Boolean updateLocationAndContactForInvoice;

	@Schema(description = "When this parameter is set on true, the newly generated invoices are directly completed.\n"
					+ "Otherwise they are just prepared and left in the DocStatus IP (in progress). Default = `true`")
	Boolean completeInvoices;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonEnqueueForInvoicingRequest(
			@JsonProperty("invoiceCandidates") @Singular final List<JsonInvoiceCandidateReference> invoiceCandidates,
			@JsonProperty("dateInvoiced") @Nullable final LocalDate dateInvoiced,
			@JsonProperty("dateAcct") @Nullable final LocalDate dateAcct,
			@JsonProperty("poReference") @Nullable final String poReference,
			@JsonProperty("ignoreInvoiceSchedule") @Nullable final Boolean ignoreInvoiceSchedule,
			@JsonProperty("supplementMissingPaymentTermIds") @Nullable final Boolean supplementMissingPaymentTermIds,
			@JsonProperty("updateLocationAndContactForInvoice") @Nullable final Boolean updateLocationAndContactForInvoice,
			@JsonProperty("completeInvoices") @Nullable final Boolean completeInvoices)
	{
		this.invoiceCandidates = ImmutableList.copyOf(invoiceCandidates);
		this.poReference = poReference;
		this.dateAcct = dateAcct;
		this.dateInvoiced = dateInvoiced;
		this.ignoreInvoiceSchedule = coalesce(ignoreInvoiceSchedule, false);
		this.supplementMissingPaymentTermIds = coalesce(supplementMissingPaymentTermIds, true);
		this.updateLocationAndContactForInvoice = coalesce(updateLocationAndContactForInvoice, false);
		this.completeInvoices = coalesce(completeInvoices, true);
	}
}
