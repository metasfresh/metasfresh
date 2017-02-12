package de.metas.inout.impl;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.comparator.ComparatorChain;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_InOut;

import de.metas.adempiere.model.I_M_PriceList;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.invoice.IMatchInvDAO;
import de.metas.product.IProductPA;

public class InOutBL implements IInOutBL
{
	public static final String SYSCONFIG_CountryAttribute = "de.metas.swat.CountryAttribute";

	@Override
	public IPricingContext createPricingCtx(final org.compiere.model.I_M_InOutLine inOutLine)
	{
		Check.assumeNotNull(inOutLine, "Param 'inOutLine' is not null");

		final IProductPA productPA = Services.get(IProductPA.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(inOutLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(inOutLine);

		final I_M_InOut inOut = inOutLine.getM_InOut();
		final IInOutBL inOutBL = Services.get(IInOutBL.class);
		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		boolean isSOTrx = inOut.isSOTrx();

		final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(inOutLine.getM_Product_ID(),
				inOut.getC_BPartner_ID(),
				inOutLine.getC_UOM_ID(),
				inOutLine.getQtyEntered(),
				isSOTrx);

		I_M_PricingSystem pricingSystem = getPricingSystemOrNull(inOut, isSOTrx);

		if (pricingSystem == null)
		{
			if (inOutBL.isReturnMovementType(inOut.getMovementType()))
			{
				// 08358
				// in case no pricing system was found for the current IsSOTrx AND we are dealing with leergut inouts
				// we are allowed to take the pricing system from the other opposite SOTrx, since the boxes have the same prices
				// either they are bought or sold
				isSOTrx = !isSOTrx;
				pricingSystem = getPricingSystemOrNull(inOut, isSOTrx);
			}
		}

		if (pricingSystem == null)
		{
			throw new AdempiereException("@NotFound@ @M_PricingSystem_ID@"
					+ "\n @M_InOut_ID@: " + inOut
					+ "\n @C_BPartner_ID@: " + inOut.getC_BPartner().getValue());
		}

		final int pricingSystemId = pricingSystem.getM_PricingSystem_ID();

		Check.assume(pricingSystemId > 0, "No pricing system found for M_InOut_ID={}", inOut.getM_InOut_ID());

		final I_M_PriceList priceList = productPA.retrievePriceListByPricingSyst(ctx, pricingSystemId, inOut.getC_BPartner_Location_ID(), isSOTrx, trxName);

		Check.errorIf(priceList == null,
				"No price list found for M_InOutLine_ID {}; M_InOut.M_PricingSystem_ID={}, M_InOut.C_BPartner_Location_ID={}, M_InOut.IsSOTrx={}",
				inOutLine.getM_InOutLine_ID(), pricingSystemId, inOut.getC_BPartner_Location_ID(), isSOTrx);

		pricingCtx.setM_PricingSystem_ID(pricingSystemId);
		pricingCtx.setM_PriceList_ID(priceList.getM_PriceList_ID());
		pricingCtx.setPriceDate(inOut.getDateOrdered());
		pricingCtx.setC_Currency_ID(priceList.getC_Currency_ID());
		// note: the qty was already passed to the pricingCtx upon creation, further up.

		return pricingCtx;
	}

	@Override
	public IPricingResult getProductPrice(final IPricingContext pricingCtx)
	{
		final IPricingBL pricingBL = Services.get(IPricingBL.class);
		final IPricingResult result = pricingBL.calculatePrice(pricingCtx);
		if (!result.isCalculated())
		{
			throw new ProductNotOnPriceListException(pricingCtx, -1);
		}
		return result;
	}

	@Override
	public IPricingResult getProductPrice(final org.compiere.model.I_M_InOutLine inOutLine)
	{
		final IPricingContext pricingCtx = createPricingCtx(inOutLine);
		return getProductPrice(pricingCtx);
	}

	@Override
	public I_M_PricingSystem getPricingSystem(final I_M_InOut inOut, final boolean throwEx)
	{
		final I_M_PricingSystem pricingSystem = getPricingSystemOrNull(inOut, inOut.isSOTrx());

		if (pricingSystem == null)
		{
			if (throwEx)
			{
				throw new AdempiereException("@NotFound@ @M_PricingSystem_ID@"
						+ "\n @C_BPartner_ID@: " + inOut.getC_BPartner().getValue());
			}
		}
		return pricingSystem;
	}

	/**
	 * Find the pricing system based on the soTrx. This method will be used in the rare cases when we are not relying upon the SOTrx of the inout, because we need the pricing system for the opposite
	 * SOTrx nature.
	 *
	 * @param inOut
	 * @param isSOTrx
	 * @return
	 */
	private I_M_PricingSystem getPricingSystemOrNull(final I_M_InOut inOut, final boolean isSOTrx)
	{
		if (inOut.getC_Order_ID() > 0 && inOut.getC_Order().getM_PricingSystem_ID() > 0)
		{
			return inOut.getC_Order().getM_PricingSystem();
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(inOut);
		final String trxName = InterfaceWrapperHelper.getTrxName(inOut);

		final int pricingSystemId = Services.get(IBPartnerDAO.class).retrievePricingSystemId(ctx, inOut.getC_BPartner_ID(), isSOTrx, trxName);

		if (pricingSystemId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(ctx, pricingSystemId, I_M_PricingSystem.class, trxName);
	}

	private boolean isReversal(final int recordId, final int recordReversalId)
	{
		if (recordId <= 0)
		{
			// InOut was not already saved.
			// Consider it as NOT reversal
			return false;
		}

		if (recordReversalId <= 0)
		{
			// no reversal was set, so for sure this is not a reversal
			return false;
		}

		Check.assume(recordId != recordReversalId, "record id({}) and reversal record id({}) shall not be the same", recordId, recordReversalId);

		if (recordId < recordReversalId)
		{
			// this document was created before the linked reversal
			// so this is the orginal document and the other one is the actual reversal
			return false;
		}

		// At this point we have: inOutId > reversalInOutId
		// So our document is the actual reversal
		// and the linked reversal is the original document
		return true;
	}

	@Override
	public boolean isReversal(final I_M_InOut inout)
	{
		Check.assumeNotNull(inout, "inout not null");

		final int recordId = inout.getM_InOut_ID();
		final int recordReversalId = inout.getReversal_ID();
		return isReversal(recordId, recordReversalId);
	}

	@Override
	public boolean isReversal(final org.compiere.model.I_M_InOutLine inoutLine)
	{
		Check.assumeNotNull(inoutLine, "inoutLine not null");

		final int recordId = inoutLine.getM_InOutLine_ID();
		final int recordReversalId = inoutLine.getReversalLine_ID();
		return isReversal(recordId, recordReversalId);
	}

	@Override
	public I_M_InOutLine newInOutLine(final I_M_InOut inout)
	{
		return newInOutLine(inout, I_M_InOutLine.class);
	}

	@Override
	public <T extends I_M_InOutLine> T newInOutLine(final I_M_InOut inout, final Class<T> modelClass)
	{
		final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

		final T line = InterfaceWrapperHelper.newInstance(modelClass, inout);
		line.setAD_Org_ID(inout.getAD_Org_ID());
		line.setM_InOut(inout);

		final I_M_Warehouse warehouse = inout.getM_Warehouse();
		final I_M_Locator locator = warehouseBL.getDefaultLocator(warehouse);
		if (locator != null)
		{
			line.setM_Locator_ID(locator.getM_Locator_ID());
		}
		return line;
	}

	@Override
	public boolean getSOTrxFromMovementType(final String movementType)
	{
		if (X_M_InOut.MOVEMENTTYPE_CustomerShipment.equals(movementType)
				|| X_M_InOut.MOVEMENTTYPE_CustomerReturns.equals(movementType))
		{
			return true;
		}
		else if (X_M_InOut.MOVEMENTTYPE_VendorReceipts.equals(movementType)
				|| X_M_InOut.MOVEMENTTYPE_VendorReturns.equals(movementType))
		{
			return false;
		}
		else
		{
			throw new AdempiereException("Unsupported MovementType: " + movementType);
		}
	}

	@Override
	public boolean isReturnMovementType(final String movementType)
	{
		return X_M_InOut.MOVEMENTTYPE_CustomerReturns.equals(movementType)
				|| X_M_InOut.MOVEMENTTYPE_VendorReturns.equals(movementType);
	}

	@Override
	public BigDecimal getEffectiveStorageChange(final I_M_InOutLine iol)
	{

		final String movementType = iol.getM_InOut().getMovementType();
		final BigDecimal multiplier;

		if (X_M_InOut.MOVEMENTTYPE_CustomerReturns.equals(movementType)
				|| X_M_InOut.MOVEMENTTYPE_VendorReceipts.equals(movementType))
		{
			multiplier = BigDecimal.ONE; // storage increase
		}
		else if (X_M_InOut.MOVEMENTTYPE_CustomerShipment.equals(movementType)
				|| X_M_InOut.MOVEMENTTYPE_VendorReturns.equals(movementType))
		{
			multiplier = BigDecimal.ONE.negate(); // storage decrease
		}
		else
		{
			Check.errorIf(true, "iol={} has an M_InOut with Unexpected MovementType={}", iol, movementType);
			return BigDecimal.ZERO; // won't normally be reached.
		}

		return iol.getMovementQty().multiply(multiplier);
	}

	@Override
	public List<I_M_InOutLine> sortLines(final I_M_InOut inOut)
	{
		//
		// Services
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		final HashMap<Integer, Integer> inoutLineId2orderId = new HashMap<Integer, Integer>();

		final List<I_M_InOutLine> lines = inOutDAO.retrieveLines(inOut);
		for (int i = 0; i < lines.size(); i++)
		{
			final I_M_InOutLine iol = lines.get(i);

			final int currentOrderID = iol.getC_OrderLine_ID() > 0
					? iol.getC_OrderLine().getC_Order_ID()
					: 0;

			final int currentLineID = iol.getM_InOutLine_ID();

			// if this is not a comment line, then store its C_Order_ID in the map
			if (currentOrderID != 0)
			{
				inoutLineId2orderId.put(currentLineID, currentOrderID);
				continue;
			}

			int valueIdToUse = -1;

			// If this is a comment line, then iterate further, to find the next not-comment line
			for (int j = 1; i + j < lines.size(); j++)
			{
				final I_M_InOutLine nextLine = lines.get(i + j);

				final int nextID = nextLine.getC_OrderLine_ID() > 0
						? nextLine.getC_OrderLine().getC_Order_ID()
						: 0;

				if (nextID != 0)   // If this is a valid ID, put it into the Map.
				{
					valueIdToUse = nextID;
					break;
				}
			}

			inoutLineId2orderId.put(currentLineID, valueIdToUse);
		}

		Check.assume(inoutLineId2orderId.size() == lines.size(), "Every line's id has been added to map '" + inoutLineId2orderId + "'");

		final ComparatorChain<I_M_InOutLine> mainComparator = new ComparatorChain<>();

		//
		// DocLine Sort Preference criteria goes first
		// final Comparator<I_M_InOutLine> docLineSortComparator = getDocLineSortComparator(inOut);
		// mainComparator.addComparator(docLineSortComparator); // commented out: this is only called in one place; if needed, see history / I_C_DocLine_Sort

		//
		// Sort by order lines
		final Comparator<I_M_InOutLine> orderLineComparator = getOrderLineComparator(inoutLineId2orderId);
		mainComparator.addComparator(orderLineComparator);

		Collections.sort(lines, mainComparator);

		return lines;
	}

	private static Comparator<I_M_InOutLine> getOrderLineComparator(final HashMap<Integer, Integer> inoutLineId2orderId)
	{
		return new Comparator<I_M_InOutLine>()
		{
			@Override
			public int compare(final I_M_InOutLine line1, final I_M_InOutLine line2)
			{
				// InOut_ID
				final int order_ID1 = inoutLineId2orderId.get(line1.getM_InOutLine_ID());
				final int order_ID2 = inoutLineId2orderId.get(line2.getM_InOutLine_ID());

				if (order_ID1 > order_ID2)
				{
					return 1;
				}
				if (order_ID1 < order_ID2)
				{
					return -1;
				}

				// LineNo
				final int line1No = line1.getLine();
				final int line2No = line2.getLine();

				if (line1No > line2No)
				{
					return 1;
				}
				if (line1No < line2No)
				{
					return -1;
				}
				return 0;
			}
		};
	}

	@Override
	public void deleteMatchInvs(final I_M_InOut inout)
	{
		final List<I_M_MatchInv> matchInvs = Services.get(IMatchInvDAO.class).retrieveForInOut(inout);
		for (final I_M_MatchInv matchInv : matchInvs)
		{
			matchInv.setProcessed(false);
			InterfaceWrapperHelper.delete(matchInv);
		}
	}

	@Override
	public void deleteMatchInvsForInOutLine(final I_M_InOutLine iol)
	{
		//
		// Delete M_MatchInvs (08627)
		for (final I_M_MatchInv matchInv : Services.get(IMatchInvDAO.class).retrieveForInOutLine(iol))
		{
			matchInv.setProcessed(false); // delete it even if it's processed, because all M_MatchInv are processed on save new.
			InterfaceWrapperHelper.delete(matchInv);
		}
	}
}
