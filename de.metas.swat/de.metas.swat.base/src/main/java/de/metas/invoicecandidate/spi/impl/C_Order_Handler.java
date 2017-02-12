package de.metas.invoicecandidate.spi.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.service.IOrderDAO;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IC_OrderLine_HandlerDAO;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * Handles {@link I_C_Order}s. Mainly all invoice candidates creation logic is delegated to {@link C_OrderLine_Handler}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_Order_Handler extends AbstractInvoiceCandidateHandler
{
	@Override
	public boolean isCreateMissingCandidatesAutomatically()
	{
		return true;
	}

	@Override
	public boolean isCreateMissingCandidatesAutomatically(final Object model)
	{
		return true;
	}

	/**
	 * Loads the request's order's order lines and returns a line of order line requests. The given request is not part of the returned list.
	 *
	 * @see C_OrderLine_Handler#getModelForInvoiceCandidateGenerateScheduling(Object)
	 */
	@Override
	public List<InvoiceCandidateGenerateRequest> expandRequest(final InvoiceCandidateGenerateRequest request)
	{
		final IC_OrderLine_HandlerDAO orderLineHandlerDAO = Services.get(IC_OrderLine_HandlerDAO.class);
		final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);

		final I_C_Order order = request.getModel(I_C_Order.class);

		//
		// Retrieve order lines
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final String trxName = InterfaceWrapperHelper.getTrxName(order);
		final List<I_C_OrderLine> orderLines = orderLineHandlerDAO.retrieveMissingOrderLinesQuery(ctx, trxName)
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.create()
				.list(I_C_OrderLine.class);
		if (orderLines.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Retrieve order line handlers
		final List<IInvoiceCandidateHandler> orderLineHandlers = invoiceCandidateHandlerBL.retrieveImplementationsForTable(ctx, org.compiere.model.I_C_OrderLine.Table_Name);

		//
		// Create the order line requests and return them
		return InvoiceCandidateGenerateRequest.of(orderLineHandlers, orderLines);
	}

	/**
	 * @return empty iterator
	 */
	@Override
	public Iterator<I_C_Order> retrieveAllModelsWithMissingCandidates(final Properties ctx, final int limit, final String trxName)
	{
		return Collections.emptyIterator();
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(final InvoiceCandidateGenerateRequest request)
	{
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(model, I_C_Order.class);
		invalidateCandidatesFor(order);
	}

	private void invalidateCandidatesFor(final I_C_Order order)
	{
		// services
		final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final List<IInvoiceCandidateHandler> invalidators = invoiceCandidateHandlerBL.retrieveImplementationsForTable(ctx, I_C_OrderLine.Table_Name);

		for (final I_C_OrderLine ol : orderDAO.retrieveOrderLines(order))
		{
			for (final IInvoiceCandidateHandler invalidator : invalidators)
			{
				invalidator.invalidateCandidatesFor(ol);
			}
		}
	}

	@Override
	public String getSourceTable()
	{
		return I_C_Order.Table_Name;
	}

	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	@Override
	public void setOrderedData(final I_C_Invoice_Candidate ic)
	{
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void setPriceActual(final I_C_Invoice_Candidate ic)
	{
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void setC_UOM_ID(final I_C_Invoice_Candidate ic)
	{
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void setPriceEntered(final I_C_Invoice_Candidate ic)
	{
		throw new IllegalStateException("Not supported");
	}
}
