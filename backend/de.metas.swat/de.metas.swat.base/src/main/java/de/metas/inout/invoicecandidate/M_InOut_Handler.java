package de.metas.inout.invoicecandidate;

import com.google.common.collect.ImmutableList;
import de.metas.inout.IInOutDAO;
import de.metas.inout.model.I_M_InOut;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_M_InOutLine;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_DocType;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Handles {@link I_M_InOut}s. Mainly all invoice candidates creation logic is delegated to {@link M_InOutLine_Handler}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class M_InOut_Handler extends AbstractInvoiceCandidateHandler
{
	// services
	private final transient IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	@Override
	public CandidatesAutoCreateMode getGeneralCandidatesAutoCreateMode()
	{
		return CandidatesAutoCreateMode.CREATE_CANDIDATES;
	}

	@Override
	public CandidatesAutoCreateMode getSpecificCandidatesAutoCreateMode(final Object model)
	{
		return CandidatesAutoCreateMode.CREATE_CANDIDATES;
	}

	/**
	 * @see M_InOutLine_Handler#getModelForInvoiceCandidateGenerateScheduling(Object)
	 */
	@Override
	public List<InvoiceCandidateGenerateRequest> expandRequest(@NonNull final InvoiceCandidateGenerateRequest request)
	{
		final I_M_InOut inout = request.getModel(I_M_InOut.class);

		//
		// Don't create InvoiceCandidates for DocSubType Saldokorrektur (FRESH-454)
		final I_C_DocType docType = load(inout.getC_DocType_ID(), I_C_DocType.class);
		final String docSubType = docType.getDocSubType();
		if (de.metas.interfaces.I_C_DocType.DOCSUBTYPE_InOutAmountCorrection.equals(docSubType))
		{
			return ImmutableList.of();
		}

		//
		// Retrieve inout lines
		final List<I_M_InOutLine> inoutLines = inOutDAO.retrieveLinesWithoutOrderLine(inout, I_M_InOutLine.class);
		if (inoutLines.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Retrieve inout line handlers
		final Properties ctx = InterfaceWrapperHelper.getCtx(inout);
		final List<IInvoiceCandidateHandler> inoutLineHandlers = Services.get(IInvoiceCandidateHandlerBL.class).retrieveImplementationsForTable(ctx, org.compiere.model.I_M_InOutLine.Table_Name);

		//
		// Create the inout line requests
		return InvoiceCandidateGenerateRequest.ofAll(inoutLineHandlers, inoutLines);
	}

	/**
	 * @return empty iterator
	 */
	@Override
	public Iterator<I_M_InOut> retrieveAllModelsWithMissingCandidates(final QueryLimit limit_IGNORED)
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
		final I_M_InOut inout = InterfaceWrapperHelper.create(model, I_M_InOut.class);
		invalidateCandidatesForInOut(inout);

		invalidateFreightCostCandidateIfNeeded(inout);
	}

	private void invalidateFreightCostCandidateIfNeeded(final I_M_InOut inout)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final OrderId orderId = OrderId.ofRepoIdOrNull(inout.getC_Order_ID());

		if (orderId == null)
		{
			// nothing to do
			return;
		}

		invoiceCandDAO.invalidateUninvoicedFreightCostCandidate(orderId);
	}

	private void invalidateCandidatesForInOut(final I_M_InOut inout)
	{
		//
		// Retrieve inout line handlers
		final Properties ctx = InterfaceWrapperHelper.getCtx(inout);
		final List<IInvoiceCandidateHandler> inoutLineHandlers = Services.get(IInvoiceCandidateHandlerBL.class).retrieveImplementationsForTable(ctx, org.compiere.model.I_M_InOutLine.Table_Name);

		for (final IInvoiceCandidateHandler handler : inoutLineHandlers)
		{
			for (final org.compiere.model.I_M_InOutLine line : inOutDAO.retrieveLines(inout))
			{
				handler.invalidateCandidatesFor(line);
			}
		}
	}

	@Override
	public String getSourceTable()
	{
		return org.compiere.model.I_M_InOut.Table_Name;
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
	public PriceAndTax calculatePriceAndTax(final I_C_Invoice_Candidate ic)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		throw new IllegalStateException("Not supported");
	}

}
