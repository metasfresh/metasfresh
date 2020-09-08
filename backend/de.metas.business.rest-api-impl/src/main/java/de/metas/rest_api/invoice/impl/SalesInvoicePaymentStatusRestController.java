package de.metas.rest_api.invoice.impl;

import java.time.LocalDate;

import org.springframework.context.annotation.Profile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.organization.OrgIdNotFoundException;
import de.metas.rest_api.invoice.SalesInvoicePaymentStatus;
import de.metas.rest_api.invoice.SalesInvoicePaymentStatusResponse;
import de.metas.rest_api.invoice.impl.SalesInvoicePaymentStatusRepository.PaymentStatusQuery;
import de.metas.util.Check;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@RestController
@RequestMapping(SalesInvoicePaymentStatusRestController.ENDPOINT)
@Profile(Profiles.PROFILE_App)
public class SalesInvoicePaymentStatusRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/sales/invoice/paymentstatus";

	private SalesInvoicePaymentStatusRepository salesInvoicePaymentStatusRepository;

	public SalesInvoicePaymentStatusRestController(@NonNull final SalesInvoicePaymentStatusRepository salesInvoicePaymentStatusRepository)
	{
		this.salesInvoicePaymentStatusRepository = salesInvoicePaymentStatusRepository;
	}

	@ApiOperation(value = "Gets regular sales invoice(s) for the given org and document number prefix, together with their payment status.", notes = "Does *not* get sales credit memos and all kinds of purchase invoices.")
	@GetMapping("{orgCode}/{invoiceDocumentNoPrefix}")
	public ResponseEntity<SalesInvoicePaymentStatusResponse> retrievePaymentStatus(
			@ApiParam(required = true, value = "Organisation for which we retrieve the payment status.<br>Either `AD_Org.Value` or the GLN of a location of the org's business partner.") //
			@PathVariable("orgCode") final String orgCode,

			@ApiParam(required = true, value = "Invoice document number prefix of the invoice(s) for which we retrieve the payment status") //
			@PathVariable("invoiceDocumentNoPrefix") final String invoiceDocumentNoPrefix)
	{
		final PaymentStatusQuery query = PaymentStatusQuery
				.builder()
				.orgValue(orgCode)
				.invoiceDocumentNoPrefix(invoiceDocumentNoPrefix)
				.build();
		try
		{
			final ImmutableList<SalesInvoicePaymentStatus> result = salesInvoicePaymentStatusRepository.getBy(query);
			return new ResponseEntity<>(new SalesInvoicePaymentStatusResponse(result), HttpStatus.OK);
		}
		catch (final OrgIdNotFoundException e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Gets regular sales invoice(s) for the given org and invoice date range, together with their payment status.",  notes = "Does *not* get sales credit memos and all kinds of purchase invoices.")
	@GetMapping("{orgCode}")
	public ResponseEntity<SalesInvoicePaymentStatusResponse> retrievePaymentStatus(
			@ApiParam(required = true, value = "Organisation for which we retrieve the payment status.<br>Either `AD_Org.Value` or the GLN of a location of the org's business partner.") //
			@PathVariable("orgCode") final String orgCode,

			@ApiParam(required = true, example = "2019-02-01", value = "Return the status for invoices that have `C_Invoice.DateInvoiced` greater </b>or equal</b> to the given date at 00:00") //
			@RequestParam("startDateIncl") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate startDate,

			@ApiParam(required = true, example = "2019-03-01", value = "Return the status for invoices that have `C_Invoice.DateInvoiced` less than the given date at 00:00") //
			@RequestParam("endDateExcl") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate)
	{
		if (Check.isEmpty(orgCode, true) || startDate == null || endDate == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		final PaymentStatusQuery query = PaymentStatusQuery
				.builder()
				.orgValue(orgCode)
				.dateInvoicedFrom(startDate)
				.dateInvoicedTo(endDate)
				.build();
		try
		{
			final ImmutableList<SalesInvoicePaymentStatus> result = salesInvoicePaymentStatusRepository.getBy(query);
			return new ResponseEntity<>(new SalesInvoicePaymentStatusResponse(result), HttpStatus.OK);
		}
		catch (final OrgIdNotFoundException e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
