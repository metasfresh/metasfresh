package de.metas.invoice.rest.controller;

import static org.compiere.util.TimeUtil.asZonedDateTime;
import static org.compiere.util.Util.coalesce;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.adempiere.service.OrgIdNotFoundException;
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
import de.metas.invoice.rest.model.SalesInvoicePaymentStatus;
import de.metas.invoice.rest.model.SalesInvoicePaymentStatusRepository;
import de.metas.invoice.rest.model.SalesInvoicePaymentStatusRepository.PaymentStatusQuery;
import de.metas.util.Check;
import de.metas.util.web.MetasfreshRestAPIConstants;
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
	private static final String TIME_ZONE_ZURICH = "Europe/Zurich";

	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/sales/invoice/paymentstatus";

	private SalesInvoicePaymentStatusRepository salesInvoicePaymentStatusRepository;

	public SalesInvoicePaymentStatusRestController(@NonNull final SalesInvoicePaymentStatusRepository salesInvoicePaymentStatusRepository)
	{
		this.salesInvoicePaymentStatusRepository = salesInvoicePaymentStatusRepository;
	}

	@GetMapping("{orgCode}/{invoiceDocumentNo}")
	public ResponseEntity<List<SalesInvoicePaymentStatus>> retrievePaymentStatus(
			@ApiParam(required = true, value = "AD_Org.Value of the organisation for which we retrieve the payment status") //
			@PathVariable("orgCode") final String orgCode,

			@ApiParam(required = true, value = "Invoice document number of the invoice(s) for which we retrieve the payment status") //
			@PathVariable("invoiceDocumentNo") final String invoiceDocumentNo)
	{
		if (Check.isEmpty(orgCode, true) || Check.isEmpty(invoiceDocumentNo, true))
		{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		final PaymentStatusQuery query = PaymentStatusQuery
				.builder()
				.orgValue(orgCode)
				.invoiceDocumentNo(invoiceDocumentNo)
				.build();

		final ImmutableList<SalesInvoicePaymentStatus> result = salesInvoicePaymentStatusRepository.getBy(query);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("{orgCode}")
	public ResponseEntity<List<SalesInvoicePaymentStatus>> retrievePaymentStatus(
			@ApiParam(required = true, value = "`AD_Org.Value` of the organisation for which we retrieve the payment status") //
			@PathVariable("orgCode") final String orgCode,

			@ApiParam(required = true, example = "2019-02-01", value = "Return the status for invoices that have `C_Invoice.DateInvoiced` greater </b>or equal</b> to the given date at 00:00") //
			@RequestParam("startDateIncl") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate startDate,

			@ApiParam(required = true, example = "2019-03-01", value = "Return the status for invoices that have `C_Invoice.DateInvoiced` less than the given date at 00:00") //
			@RequestParam("endDateExcl") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate,

			@ApiParam(required = false, defaultValue = TIME_ZONE_ZURICH, value = "Time zone of the date parameters. See the javadoc for `ZoneId.of(String)` or the IANA Time Zone Database (TZDB) for allowed values") //
			@RequestParam("timeZoneId") final String timeZoneId)
	{
		if (Check.isEmpty(orgCode, true) || startDate == null || endDate == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		final ZoneId zoneId = ZoneId.of(coalesce(timeZoneId, TIME_ZONE_ZURICH));

		final PaymentStatusQuery query = PaymentStatusQuery
				.builder()
				.orgValue(orgCode)
				.dateInvoicedFrom(asZonedDateTime(startDate, zoneId))
				.dateInvoicedTo(asZonedDateTime(endDate, zoneId))
				.build();
		try
		{
			final ImmutableList<SalesInvoicePaymentStatus> result = salesInvoicePaymentStatusRepository.getBy(query);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch (final OrgIdNotFoundException e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
