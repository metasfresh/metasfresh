package de.metas.materialtracking.qualityBasedInvoicing.impl;

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
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorReceipt;
import de.metas.materialtracking.spi.IHandlingUnitsInfoFactory;

/**
 * {@link IVendorReceipt} implementation which takes the values from the wrapped {@link I_M_InOutLine}.
 *
 * @author tsa
 *
 */
/* package */class InOutLineAsVendorReceipt implements IVendorReceipt<I_M_InOutLine>
{
	private static final transient Logger logger = LogManager.getLogger(InOutLineAsVendorReceipt.class);

	// services
	// private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IHandlingUnitsInfoFactory handlingUnitsInfoFactory = Services.get(IHandlingUnitsInfoFactory.class);

	private final List<I_M_InOutLine> inOutLines = new ArrayList<I_M_InOutLine>();

	// Loaded informations
	private boolean _loaded = false;
	private I_M_Product _product = null;
	private BigDecimal _qtyReceived = null;
	private I_C_UOM _qtyReceivedUOM = null;
	private IHandlingUnitsInfo _handlingUnitsInfo = null;
	private I_M_PriceList_Version plv;

	public InOutLineAsVendorReceipt(final I_M_Product vendorProduct)
	{
		Check.assumeNotNull(vendorProduct, "Param 'vendorProduct' is not null");
		this._product = vendorProduct;
	}

	@Override
	public void add(final I_M_InOutLine inOutLine)
	{
		Check.assumeNotNull(inOutLine, "inOutLine not null");
		if (inOutLine.getM_Product_ID() != _product.getM_Product_ID())
		{
			return; // nothing to do
		}
		inOutLines.add(inOutLine);
	}

	@Override
	public List<I_M_InOutLine> getModels()
	{
		return inOutLines;
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

	@Override
	public I_M_PriceList_Version getPLV()
	{
		return plv;
	}

	/* package */void setPlv(I_M_PriceList_Version plv)
	{
		this.plv = plv;
	}

	private final void loadQtysIfNeeded()
	{
		if (_loaded)
		{
			return;
		}

		final I_M_InOutLine firstInOutLine = inOutLines.get(0);
		//
		// Vendor Product
		final int productId = _product.getM_Product_ID();
		final I_C_UOM productUOM = _product.getC_UOM();
		Check.assumeNotNull(productUOM, "productUOM not null");

		// Define the conversion context (in case we need it)
		final IUOMConversionContext uomConversionCtx = uomConversionBL.createConversionContext(_product);

		//
		// UOM
		final I_C_UOM qtyReceivedTotalUOM = firstInOutLine.getC_UOM();

		//
		// Iterate Receipt Lines linked to this invoice candidate, extract & aggregate informations from them
		BigDecimal qtyReceivedTotal = BigDecimal.ZERO;
		IHandlingUnitsInfo handlingUnitsInfoTotal = null;

		for (final I_M_InOutLine inoutLine : inOutLines)
		{
			if (inoutLine.getM_Product_ID() != productId)
			{
				logger.debug("Not counting {} because its M_Product_ID={} is not the ID of product {}", new Object[] { inoutLine, inoutLine.getM_Product_ID(), _product });
				continue;
			}

			// task 09117: we only may count iol that are not reversed, in progress of otherwise "not relevant"
			final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
			if (!docActionBL.isDocumentStatusOneOf(inoutLine.getM_InOut(), IDocument.STATUS_Completed, IDocument.STATUS_Closed))
			{
				logger.debug("Not counting {} because its M_InOut has docstatus {}", new Object[] { inoutLine, inoutLine.getM_InOut().getDocStatus() });
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

		//
		// Set loaded values
		_qtyReceived = qtyReceivedTotal;
		_qtyReceivedUOM = qtyReceivedTotalUOM;
		_handlingUnitsInfo = handlingUnitsInfoTotal;
		_loaded = true;
	}
}
