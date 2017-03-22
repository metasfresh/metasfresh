package de.metas.banking.payment.impl;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;

import de.metas.banking.payment.IPaymentRequestDAO;
import de.metas.payment.model.I_C_Payment_Request;

/**
 * @author al
 */
public class PaymentRequestDAO implements IPaymentRequestDAO
{
	private final IQueryBuilder<I_C_Payment_Request> retrieveRequestForInvoiceQuery(final I_C_Invoice invoice)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_Payment_Request> queryBuilder = queryBL.createQueryBuilder(I_C_Payment_Request.class, invoice)
				.addEqualsFilter(I_C_Payment_Request.COLUMN_C_Invoice_ID, invoice.getC_Invoice_ID())
				.addOnlyActiveRecordsFilter() // NOTE: very important, BL relies on this (i.e. assumes it gets only active requests)
				.addOnlyContextClient();
		return queryBuilder;
	}

	@Override
	public I_C_Payment_Request retrieveSingularRequestOrNull(final I_C_Invoice invoice)
	{
		return retrieveRequestForInvoiceQuery(invoice)
				.create()
				.firstOnly(I_C_Payment_Request.class);
	}

	@Override
	public boolean hasPaymentRequests(final I_C_Invoice invoice)
	{
		return retrieveRequestForInvoiceQuery(invoice)
				.create()
				.match();
	}
	
	@Override
	public boolean checkPaymentRequestsExists(final Properties ctx, final Set<Integer> paymentRequestIds)
	{
		Check.assumeNotEmpty(paymentRequestIds, "paymentRequestIds not empty");
		
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<Integer> actualPaymentRequestIdsList = queryBL.createQueryBuilder(I_C_Payment_Request.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter() // NOTE: very important, BL relies on this (i.e. assumes it gets only active requests)
				.addOnlyContextClient()
				.addInArrayOrAllFilter(I_C_Payment_Request.COLUMN_C_Payment_Request_ID, paymentRequestIds)
				.create()
				.listIds();
		
		final Set<Integer> actualPaymentRequestIds = new HashSet<>(actualPaymentRequestIdsList);
		
		return paymentRequestIds.equals(actualPaymentRequestIds);
	}
}
