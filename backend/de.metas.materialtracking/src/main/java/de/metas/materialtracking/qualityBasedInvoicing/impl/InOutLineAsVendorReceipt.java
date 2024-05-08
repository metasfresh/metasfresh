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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutBL;
import de.metas.logging.LogManager;
import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorReceipt;
import de.metas.materialtracking.spi.IHandlingUnitsInfoFactory;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * {@link IVendorReceipt} implementation which takes the values from the wrapped {@link I_M_InOutLine}.
 *
 */
/* package */class InOutLineAsVendorReceipt implements IVendorReceipt<I_M_InOutLine>
{

	private static final Logger logger = LogManager.getLogger(InOutLineAsVendorReceipt.class);

	// services
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IHandlingUnitsInfoFactory handlingUnitsInfoFactory = Services.get(IHandlingUnitsInfoFactory.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);

	private final List<I_M_InOutLine> inOutLines = new ArrayList<>();

	// Loaded informations
	private boolean _loaded = false;
	private I_M_Product _product = null;
	private BigDecimal _qtyReceived = null;
	private I_C_UOM _qtyReceivedUOM = null;
	private IHandlingUnitsInfo _handlingUnitsInfo = null;
	private I_M_PriceList_Version plv;

	public InOutLineAsVendorReceipt(@NonNull final I_M_Product vendorProduct)
	{
		this._product = vendorProduct;
	}

	@Override
	public void add(@NonNull final I_M_InOutLine inOutLine)
	{
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
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

		if (_loaded)
		{
			return;
		}

		final I_M_InOutLine firstInOutLine = inOutLines.get(0);
		//
		// Vendor Product
		final int productId = _product.getM_Product_ID();
		final UomId productUomId = UomId.ofRepoIdOrNull(_product.getC_UOM_ID());
		Check.assumeNotNull(productUomId, "UomId of product={} may not be null", _product);

		// Define the conversion context (in case we need it)
		final UOMConversionContext uomConversionCtx = UOMConversionContext.of(ProductId.ofRepoId(_product.getM_Product_ID()));

		//
		// UOM
		final UomId qtyReceivedTotalUomId = UomId.ofRepoId(firstInOutLine.getC_UOM_ID());

		//
		// Iterate Receipt Lines linked to this invoice candidate, extract & aggregate informations from them
		BigDecimal qtyReceivedTotal = BigDecimal.ZERO;
		IHandlingUnitsInfo handlingUnitsInfoTotal = null;

		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		for (final I_M_InOutLine inoutLine : inOutLines)
		{
			if (inoutLine.getM_Product_ID() != productId)
			{
				loggable.addLog("Not counting {} because its M_Product_ID={} is not the ID of product {}", new Object[] { inoutLine, inoutLine.getM_Product_ID(), _product });
				continue;
			}

			final I_M_InOut inOutRecord = inoutLine.getM_InOut();

			// task 09117: we only may count iol that are not reversed, in progress of otherwise "not relevant"
			final I_M_InOut inout = inoutLine.getM_InOut();
			final DocStatus inoutDocStatus = DocStatus.ofCode(inout.getDocStatus());
			if (!inoutDocStatus.isCompletedOrClosed())
			{
				loggable.addLog("Not counting {} because its M_InOut has docstatus {}", inoutLine, inOutRecord.getDocStatus());
				continue;
			}

			final BigDecimal qtyReceived = inoutBL.negateIfReturnMovmenType(inoutLine, inoutLine.getMovementQty());
			logger.debug("M_InOut_ID={} has MovementType={}; -> proceeding with qtyReceived={}", inOutRecord.getM_InOut_ID(), inOutRecord.getMovementType(), qtyReceived);

			final BigDecimal qtyReceivedConv = uomConversionBL.convertQty(uomConversionCtx, qtyReceived, productUomId, qtyReceivedTotalUomId);
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
		_qtyReceivedUOM = uomDAO.getById(qtyReceivedTotalUomId);
		_handlingUnitsInfo = handlingUnitsInfoTotal;
		_loaded = true;
	}
}
