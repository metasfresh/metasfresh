package de.metas.rest_api.invoicecandidates.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandCreateRequest;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidates;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandCreateRequest.JsonInvoiceCandCreateRequestBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
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
public class JsonInvoiceCand
{

	List<JsonInvoiceCandidates> jsonInvoices;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	private LocalDate dateInvoiced;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	private LocalDate dateAcct;

	private String poReference;

	private Boolean onlyApprovedForInvoicing;

	private Boolean ignoreInvoiceSchedule;

	private Boolean supplementMissingPaymentTermIds;

	private Boolean updateLocationAndContactForInvoice;
	
	private BigDecimal check_NetAmtToInvoice;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonInvoiceCand(
			@JsonProperty("dateInvoiced") final LocalDate dateInvoiced,
			@JsonProperty("dateAcct") final LocalDate dateAcct,
			@JsonProperty("poReference") final String poReference,
			@JsonProperty("onlyApprovedForInvoicing") final @NonNull Boolean onlyApprovedForInvoicing,
			@JsonProperty("ignoreInvoiceSchedule") final @NonNull Boolean ignoreInvoiceSchedule,
			@JsonProperty("supplementMissingPaymentTermIds") final @NonNull Boolean supplementMissingPaymentTermIds,
			@JsonProperty("updateLocationAndContactForInvoice") final @NonNull Boolean updateLocationAndContactForInvoice,
			@JsonProperty("check_NetAmtToInvoice") final BigDecimal check_NetAmtToInvoice,
			List<JsonInvoiceCandidates> jsonInvoices)
	{
		this.jsonInvoices = jsonInvoices;
		this.poReference = poReference;
		this.check_NetAmtToInvoice = check_NetAmtToInvoice;
		this.dateAcct = dateAcct;
		this.dateInvoiced = dateInvoiced;
		this.onlyApprovedForInvoicing = onlyApprovedForInvoicing;
		this.ignoreInvoiceSchedule = ignoreInvoiceSchedule;
		this.supplementMissingPaymentTermIds = supplementMissingPaymentTermIds;
		this.updateLocationAndContactForInvoice = updateLocationAndContactForInvoice;
	}

}
