package de.metas.invoice.rest.controller;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.invoice.rest.model.JsonSalesInvoicePaymentStatus;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
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
@RequestMapping(SalesInvoiceRestController.ENDPOINT)
@Profile(Profiles.PROFILE_App)
public class SalesInvoiceRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/sales/invoice/paymentstatus";

	public SalesInvoiceRestController()
	{
	}

	@GetMapping("/{invoiceDocumentNo}")
	public JsonSalesInvoicePaymentStatus retrievePaymentStatus(
			@PathVariable("invoiceDocumentNo") final String invoiceDocumentNo)
	{
		// TODO if invoiceDocumentNo is empty, return 404

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.call(ITrx.TRXNAME_ThreadInherited, () -> retrievePaymentStatus0(invoiceDocumentNo));
	}

	private JsonSalesInvoicePaymentStatus retrievePaymentStatus0(@NonNull final String invoiceDocumentNo)
	{
		// TODO restrict the search to the session's Org_ID if that orgId is "any", this might still return a list of invoices
		// if there is no matching invoice, return 404
		return null;
	}

	//
}
