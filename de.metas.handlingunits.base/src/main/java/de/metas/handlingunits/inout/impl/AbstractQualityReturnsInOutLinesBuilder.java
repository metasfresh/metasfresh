package de.metas.handlingunits.inout.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.IReference;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.inout.IQualityReturnsInOutLinesBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
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

public abstract class AbstractQualityReturnsInOutLinesBuilder implements IQualityReturnsInOutLinesBuilder
{

	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final transient IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final transient IAttributeSetInstanceBL asiBL = Services.get(IAttributeSetInstanceBL.class);
	private final transient IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	// referenced inout header
	private final IReference<I_M_InOut> _inoutRef;

	/**
	 * Map on product keys and their inout lines
	 */
	private final Map<ArrayKey, I_M_InOutLine> _inOutLines = new LinkedHashMap<>();

	protected AbstractQualityReturnsInOutLinesBuilder(final IReference<I_M_InOut> inoutRef)
	{
		super();

		Check.assumeNotNull(inoutRef, "inoutRef not null");
		_inoutRef = inoutRef;
	}

	private final I_M_InOut getM_InOut()
	{
		return _inoutRef.getValue();
	}

	@Override
	public AbstractQualityReturnsInOutLinesBuilder addHUProductStorage(final IHUProductStorage productStorage, final I_M_InOutLine originInOutLine)
	{
		final I_M_InOut inout = getM_InOut();
		InterfaceWrapperHelper.save(inout); // make sure inout header is saved

		updateInOutLine(productStorage, originInOutLine);

		return this;
	}

	/**
	 * Extract the product from the product storage together with its quantity.
	 * If there is already an inout line for this product, update the existing qty based on the new one. If the inout line for this product does not exist yet, create it.
	 * In the end assign the handling unit to the inout line and mark the HU as shipped.
	 * 
	 * @param productStorage
	 * @param originInOutLine
	 */
	private void updateInOutLine(final IHUProductStorage productStorage, final I_M_InOutLine originInOutLine)
	{
		// services
		final IProductBL productBL = Services.get(IProductBL.class);
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);

		// Skip it if product storage is empty
		if (productStorage.isEmpty())
		{
			return;
		}

		final I_M_Product product = productStorage.getM_Product();

		if (originInOutLine.getM_Product_ID() != product.getM_Product_ID())
		{
			return;
		}

		final I_M_HU hu = productStorage.getM_HU();

		final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(hu);

		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(ctxAware);

		final I_C_UOM productUOM = productBL.getStockingUOM(product);
		final BigDecimal qtyToMoveTotal = productStorage.getQty(productUOM);

		final BigDecimal qualityDiscountPerc = huAttributesBL.getQualityDiscountPercent(productStorage.getM_HU());
		final BigDecimal qtyToMoveInDispute = qtyToMoveTotal.multiply(qualityDiscountPerc);
		final BigDecimal qtyToMove = qtyToMoveTotal.subtract(qtyToMoveInDispute);

		final I_M_InOutLine inOutLine = getCreateInOutLine(originInOutLine, product);

		if (originInOutLine.getM_InOut_ID() != getM_InOut().getM_InOut_ID())
		{
			//
			// Adjust movement line's qty to move
			final BigDecimal inOutLine_Qty_Old = inOutLine.getMovementQty();
			final BigDecimal inOutLine_Qty_New = inOutLine_Qty_Old.add(qtyToMove);
			inOutLine.setMovementQty(inOutLine_Qty_New);

			//
			// Also set the qty entered
			inOutLine.setQtyEntered(inOutLine_Qty_New);

			// Make sure the inout line is saved
			InterfaceWrapperHelper.save(inOutLine);
		}

		// handle Qty with Quality Issues

		if (qtyToMoveInDispute.signum() != 0)
		{
			final I_M_InOutLine inOutLineInDispute = getCreateInOutLineInDispute(originInOutLine, product);

			final BigDecimal inOutLine_Qty_Old = inOutLineInDispute.getMovementQty();
			final BigDecimal inOutLine_Qty_New = inOutLine_Qty_Old.add(qtyToMoveInDispute);
			inOutLineInDispute.setMovementQty(inOutLine_Qty_New);

			//
			// Also set the qty entered
			inOutLineInDispute.setQtyEntered(inOutLine_Qty_New);

			// Make sure the inout line is saved
			InterfaceWrapperHelper.save(inOutLineInDispute);
		}

		//
		// Assign the HU to the inout line and mark it as shipped
		{

			final String trxName = ITrx.TRXNAME_ThreadInherited;

			final I_M_HU huTopLevel = handlingUnitsBL.getTopLevelParent(hu);
			final I_M_HU luHU = handlingUnitsBL.getLoadingUnitHU(hu);
			final I_M_HU tuHU = handlingUnitsBL.getTransportUnitHU(hu);

			huAssignmentBL.createTradingUnitDerivedAssignmentBuilder(InterfaceWrapperHelper.getCtx(hu), inOutLine, huTopLevel, luHU, tuHU, trxName)
					.build();

			// mark hu as shipped ( if vendor return) or Active (if customer return)
			setHUStatus(huContext, hu);

		}
	}

	protected abstract void setHUStatus(IHUContext huContext, I_M_HU hu);

	/**
	 * Search the inout lines map (_inOutLines) and check if there is already a line for the given product. In case it already exists, return it. Otherwise, create a line for this product.
	 * 
	 * @param product
	 * @return
	 */
	private I_M_InOutLine getCreateInOutLine(final I_M_InOutLine originInOutLine, final I_M_Product product)
	{
		final I_M_InOut inout = getM_InOut();

		if (inout.getM_InOut_ID() <= 0)
		{
			// nothing created
			return null;
		}

		//
		// Check if we already have a movement line for our key
		final ArrayKey inOutLineKey = mkInOutLineKey(originInOutLine, product);
		final I_M_InOutLine existingInOutLine = _inOutLines.get(inOutLineKey);

		// return the existing inout line if found
		if (existingInOutLine != null)
		{
			return existingInOutLine;
		}

		// #1306 verify if the origin line belongs to the manually created return ( _inOutRef)
		if (originInOutLine.getM_InOut_ID() == inout.getM_InOut_ID())
		{
			_inOutLines.put(inOutLineKey, originInOutLine);
			return originInOutLine;
		}

		//
		// Create a new inOut line for the product

		final I_M_InOutLine newInOutLine = inOutBL.newInOutLine(inout, I_M_InOutLine.class);
		newInOutLine.setAD_Org_ID(inout.getAD_Org_ID());
		newInOutLine.setM_InOut_ID(inout.getM_InOut_ID());

		newInOutLine.setM_Product(originInOutLine.getM_Product());

		newInOutLine.setC_UOM(originInOutLine.getC_UOM());

		newInOutLine.setReturn_Origin_InOutLine(originInOutLine);

		// make sure the attributes in the return line is the same as in the origin line
		asiBL.cloneASI(newInOutLine, originInOutLine);

		// NOTE: we are not saving the inOut line

		//
		// Add the movement line to our map (to not created it again)
		_inOutLines.put(inOutLineKey, newInOutLine);

		return newInOutLine;
	}

	private I_M_InOutLine getCreateInOutLineInDispute(final I_M_InOutLine originInOutLine, final I_M_Product product)
	{
		final I_M_InOutLine originInOutLineInDispute = InterfaceWrapperHelper.create(inOutDAO.retrieveLineWithQualityDiscount(originInOutLine), I_M_InOutLine.class);

		if (originInOutLineInDispute == null)
		{
			return null;
		}

		final I_M_InOutLine inoutLineInDispute = getCreateInOutLine(originInOutLineInDispute, product);

		inoutLineInDispute.setIsInDispute(true);
		inoutLineInDispute.setQualityDiscountPercent(originInOutLineInDispute.getQualityDiscountPercent());
		inoutLineInDispute.setQualityNote(originInOutLineInDispute.getQualityNote());
		return inoutLineInDispute;

	}

	public Collection<I_M_InOutLine> getInOutLines()
	{
		return _inOutLines.values();
	}

	/**
	 * Make a unique key for the given product. This will serve in mapping the inout lines to their products.
	 * 
	 * @param product
	 * @return
	 */
	private static ArrayKey mkInOutLineKey(final I_M_InOutLine originInOutLine, final I_M_Product product)
	{
		return Util.mkKey(originInOutLine.getM_InOutLine_ID(), product.getM_Product_ID());
	}

	@Override
	public boolean isEmpty()
	{
		return _inOutLines.isEmpty();
	}
}
