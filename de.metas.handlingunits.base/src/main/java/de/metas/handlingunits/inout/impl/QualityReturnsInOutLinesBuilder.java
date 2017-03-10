package de.metas.handlingunits.inout.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.ImmutableReference;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inout.IInOutBL;
import de.metas.product.IProductBL;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class QualityReturnsInOutLinesBuilder
{
	private final IReference<I_M_InOut> _inoutRef;


	private final Map<ArrayKey, I_M_InOutLine> _inOutLines = new HashMap<>();

	public static QualityReturnsInOutLinesBuilder newBuilder( final IReference<I_M_InOut> inoutRef)
	{
		return new QualityReturnsInOutLinesBuilder(inoutRef);
	}

	public static QualityReturnsInOutLinesBuilder newBuilder(final I_M_InOut inout)
	{
		Check.assumeNotNull(inout, "Parameter inout is not null");
		final IReference<I_M_InOut> inoutRef = ImmutableReference.valueOf(inout);
		return new QualityReturnsInOutLinesBuilder(inoutRef);
	}

	private QualityReturnsInOutLinesBuilder(final IReference<I_M_InOut> inoutRef)
	{
		super();

		Check.assumeNotNull(inoutRef, "inoutRef not null");
		_inoutRef = inoutRef;
	}
	
	private final I_M_InOut getM_InOut()
	{
		return _inoutRef.getValue();
	}
	
	public QualityReturnsInOutLinesBuilder addHUProductStorage(final IHUProductStorage productStorage)
	{
		updateInOutLine(productStorage);
		
		return this;
	}

	private void updateInOutLine(final IHUProductStorage productStorage)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);

		// Skip it if product storage is empty
		if (productStorage.isEmpty())
		{
			return;
		}

		final I_M_Product product = productStorage.getM_Product();
		final I_M_InOutLine inOutLine = getCreateInOutLine(product);

		final I_C_UOM productUOM = productBL.getStockingUOM(product);
		final BigDecimal qtyToMove = productStorage.getQty(productUOM);

		//
		// Adjust movement line's qty to move
		final BigDecimal movementLine_Qty_Old = inOutLine.getMovementQty();
		final BigDecimal movementLine_Qty_New = movementLine_Qty_Old.add(qtyToMove);
		inOutLine.setMovementQty(movementLine_Qty_New);

		// Make sure movement line it's saved
		InterfaceWrapperHelper.save(inOutLine);

		// Assign the HU to movement line
		{
			final I_M_HU hu = productStorage.getM_HU();
			final boolean isTransferPackingMaterials = true;
			final String trxName = ITrx.TRXNAME_ThreadInherited;
			huAssignmentBL.assignHU(inOutLine, hu, isTransferPackingMaterials, trxName);

			// toDO: Check if this is fine
			hu.setHUStatus(X_M_HU.HUSTATUS_Shipped);
		}

	}

	private I_M_InOutLine getCreateInOutLine(final I_M_Product product)
	{
//		if (_inoutRef.isInitialized())
//		{
//			// nothing created
//			return null;
//		}
		final I_M_InOut inout = _inoutRef.getValue();
		if (inout.getM_InOut_ID() <= 0)
		{
			// nothing created
			return null;
		}

		//
		// Check if we already have a movement line for our key
		final ArrayKey inOutLineKey = mkInOutLineKey(product);
		I_M_InOutLine inOutLine = _inOutLines.get(inOutLineKey);
		if (inOutLine != null)
		{
			return inOutLine;
		}

		//
		// Create a new inOut line

		inOutLine = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class, inout);
		inOutLine.setAD_Org_ID(inout.getAD_Org_ID());
		inOutLine.setM_InOut_ID(inout.getM_InOut_ID());

		// inOutLine.setIsPackagingMaterial(false);

		inOutLine.setM_Product(product);
		// movementLine.setMovementQty(qty);

		// NOTE: we are not saving the inOut line

		//
		// Add the movement line to our map (to not created it again)
		_inOutLines.put(inOutLineKey, inOutLine);

		return inOutLine;
	}

	private ArrayKey mkInOutLineKey(final I_M_Product product)
	{
		return Util.mkKey(product.getM_Product_ID());
	}

	public boolean isEmpty()
	{
		return _inOutLines.isEmpty();
	}

}
