package de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl;

/*
 * #%L
 * de.metas.materialtracking
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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.document.service.IDocActionBL;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;

import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorReceipt;
import de.metas.materialtracking.spi.IHandlingUnitsInfoFactory;

/**
 * {@link IVendorReceipt} implementation which takes the values from the wrapped {@link I_C_Invoice_Candidate}.
 *
 * @author tsa
 *
 */
/* package */class InvoiceCandidateAsVendorReceipt implements IVendorReceipt
{
	private static final transient CLogger logger = CLogger.getCLogger(InvoiceCandidateAsVendorReceipt.class);
	
	// services
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IHandlingUnitsInfoFactory handlingUnitsInfoFactory = Services.get(IHandlingUnitsInfoFactory.class);

	private final List<I_C_Invoice_Candidate> invoiceCandidates = new ArrayList<I_C_Invoice_Candidate>();

	// Loaded informations
	private boolean _loaded = false;
	private I_M_Product _product = null;
	private BigDecimal _qtyReceived = null;
	private I_C_UOM _qtyReceivedUOM = null;
	private IHandlingUnitsInfo _handlingUnitsInfo = null;

	public InvoiceCandidateAsVendorReceipt(final I_C_Invoice_Candidate invoiceCandidate)
	{
		super();
		add(invoiceCandidate);
	}

	// final, because it's called from the constructor
	@Override
	public final void add(final I_C_Invoice_Candidate invoiceCandidate)
	{
		Check.assumeNotNull(invoiceCandidate, "invoiceCandidate not null");
		Check.assume(!invoiceCandidate.isToRecompute(), "invoiceCandidate is valid (IsToRecompute=false)");
		if (!invoiceCandidates.isEmpty())
		{
			checkConsistency(invoiceCandidates.get(0), invoiceCandidate);
		}
		invoiceCandidates.add(invoiceCandidate);
	}

	public InvoiceCandidateAsVendorReceipt()
	{
		super();
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("[");

		if (_loaded)
		{
			sb.append("product=").append(_product == null ? "-" : _product.getValue());
			sb.append(", qtyReceived=").append(_qtyReceived).append(_qtyReceivedUOM == null ? "" : _qtyReceivedUOM.getUOMSymbol());
			sb.append(", handlingUnitsInfo=").append(_handlingUnitsInfo);
		}
		else
		{
			sb.append("not loaded");
		}

		sb.append("]");
		return sb.toString();
	}

	@Override
	public I_M_Product getM_Product()
	{
		loadQtysIfNeeded();
		return _product;
	}

	@Override
	public BigDecimal getQtyReceived()
	{
		loadQtysIfNeeded();
		return _qtyReceived;
	}

	@Override
	public I_C_UOM getQtyReceivedUOM()
	{
		loadQtysIfNeeded();
		return _qtyReceivedUOM;
	}

	@Override
	public IHandlingUnitsInfo getHandlingUnitsInfo()
	{
		loadQtysIfNeeded();
		return _handlingUnitsInfo;
	}

	private final void loadQtysIfNeeded()
	{
		if (_loaded)
		{
			return;
		}

		final I_C_Invoice_Candidate firstInvoiceCandidate = invoiceCandidates.get(0);
		//
		// Vendor Product
		final I_M_Product product = firstInvoiceCandidate.getM_Product();
		Check.assumeNotNull(product, "product not null");
		final int productId = product.getM_Product_ID();
		final I_C_UOM productUOM = product.getC_UOM();
		Check.assumeNotNull(productUOM, "productUOM not null");

		// Define the conversion context (in case we need it)
		final IUOMConversionContext uomConversionCtx = uomConversionBL.createConversionContext(product);

		//
		// UOM
		final I_C_UOM qtyReceivedTotalUOM = firstInvoiceCandidate.getC_UOM();

		//
		// Iterate Receipt Lines linked to this invoice candidate, extract & aggregate informations from them
		BigDecimal qtyReceivedTotal = BigDecimal.ZERO;
		IHandlingUnitsInfo handlingUnitsInfoTotal = null;
		for (final I_C_Invoice_Candidate invoiceCandidate : invoiceCandidates)
		{
			checkConsistency(firstInvoiceCandidate, invoiceCandidate);

			final List<I_M_InOutLine> inoutLines = invoiceCandDAO.retrieveInOutLinesForCandidate(invoiceCandidate, I_M_InOutLine.class);
			for (final I_M_InOutLine inoutLine : inoutLines)
			{
				if (inoutLine.getM_Product_ID() != productId)
				{
					logger.log(Level.FINE, "Not counting {0} because its M_Product_ID={1} is not the ID of product {2}", new Object[] { inoutLine, inoutLine.getM_Product_ID(), product });
					continue;
				}
				
				// task 09117: we only may count iol that are not reversed, in progress of otherwise "not relevant"
				final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
				if (!docActionBL.isStatusOneOf(inoutLine.getM_InOut(), DocAction.STATUS_Completed, DocAction.STATUS_Closed))
				{
					logger.log(Level.FINE, "Not counting {0} because its M_InOut has docstatus {1}", new Object[] { inoutLine, inoutLine.getM_InOut().getDocStatus() });
					continue;
				}

				final BigDecimal qtyReceived = inoutLine.getMovementQty();
				final BigDecimal qtyReceivedConv = uomConversionBL.convertQty(uomConversionCtx, qtyReceived, productUOM, qtyReceivedTotalUOM);
				qtyReceivedTotal = qtyReceivedTotal.add(qtyReceivedConv);

				final IHandlingUnitsInfo handlingUnitsInfo = handlingUnitsInfoFactory.createFromModel(inoutLine);
				if (handlingUnitsInfo == null)
				{
					// do nothing
				}
				if (handlingUnitsInfoTotal == null)
				{
					handlingUnitsInfoTotal = handlingUnitsInfo;
				}
				else
				{
					handlingUnitsInfoTotal = handlingUnitsInfoTotal.add(handlingUnitsInfo);
				}
			}
		}
		//
		// Set loaded values
		_product = product;
		_qtyReceived = qtyReceivedTotal;
		_qtyReceivedUOM = qtyReceivedTotalUOM;
		_handlingUnitsInfo = handlingUnitsInfoTotal;
		_loaded = true;
	}

	private void checkConsistency(final I_C_Invoice_Candidate firstInvoiceCandidate, final I_C_Invoice_Candidate invoiceCandidate)
	{
		if (firstInvoiceCandidate == null)
		{
			return; // nothing to do
		}
		Check.assume(firstInvoiceCandidate.getM_Product_ID() == invoiceCandidate.getM_Product_ID(),
				"C_Invoice_Candidate {0} and {1} have the same M_Product_ID", firstInvoiceCandidate, invoiceCandidate);

	}
}
