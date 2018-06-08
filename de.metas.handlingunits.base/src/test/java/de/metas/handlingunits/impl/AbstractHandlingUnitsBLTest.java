package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_InOut;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.model.validator.M_InOut;
import de.metas.inout.IInOutDAO;
import de.metas.materialtransaction.IMTransactionDAO;

public abstract class AbstractHandlingUnitsBLTest extends AbstractHUTest
{
	//
	// Services
	protected HandlingUnitsBL huBL;
	protected IHUInOutDAO huInOutDAO;

	//
	// Model Validators
	protected M_InOut inOutMV;

	// HU Definitions
	protected I_M_HU_PI huDefIFCO;
	protected I_M_HU_PI huDefPalet;

	@Override
	protected void initialize()
	{
		POJOLookupMap.get().setCopyOnSave(true);
		POJOWrapper.setPrintReferencedModels(false);

		huBL = new HandlingUnitsBL();
		Services.registerService(IHandlingUnitsBL.class, huBL);

		huInOutDAO = Services.get(IHUInOutDAO.class);

		inOutMV = new de.metas.handlingunits.model.validator.M_InOut();
		inOutMV.init();
	}

	protected void createHandlingUnitsMasterData(
			final BigDecimal tomatosPerIFCO,
			final int ifcosPerPalet)
	{
		//
		// Handling Units Definition
		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(itemMA, pTomato, tomatosPerIFCO, uomEach);

			helper.createHU_PI_Item_PackingMaterial(huDefIFCO, pmIFCO);
		}

		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, new BigDecimal(ifcosPerPalet));
			helper.createHU_PI_Item_PackingMaterial(huDefPalet, pmPallets);
		}
	}

	protected I_M_InOut createReceipt()
	{
		final I_M_InOut receipt = InterfaceWrapperHelper.newInstance(I_M_InOut.class, helper.contextProvider);
		receipt.setIsSOTrx(false);
		receipt.setDocStatus(X_M_InOut.DOCSTATUS_Completed);
		receipt.setProcessed(true);
		receipt.setMovementDate(helper.getTodayTimestamp());
		InterfaceWrapperHelper.save(receipt);

		if (Check.isEmpty(receipt.getDocumentNo(), true))
		{
			receipt.setDocumentNo("Doc" + receipt.getM_InOut_ID());
			InterfaceWrapperHelper.save(receipt);
		}

		return receipt;
	}

	protected I_M_Transaction reverseMTransaction(final I_M_Transaction mtrx, final I_M_InOutLine reversalLine)
	{
		final I_M_InOut receiptReversal = reversalLine.getM_InOut();

		final I_M_Transaction mtrxReversal = InterfaceWrapperHelper.newInstance(I_M_Transaction.class, mtrx);
		InterfaceWrapperHelper.copyValues(mtrx, mtrxReversal);
		mtrxReversal.setMovementQty(mtrxReversal.getMovementQty().negate());
		mtrxReversal.setMovementDate(receiptReversal.getMovementDate());
		mtrxReversal.setM_InOutLine_ID(reversalLine.getM_InOutLine_ID());
		InterfaceWrapperHelper.save(mtrxReversal);

		return mtrxReversal;
	}

	protected I_M_InOut reverse(final I_M_InOut receipt)
	{
		final I_M_InOut reversal = InterfaceWrapperHelper.newInstance(I_M_InOut.class, receipt);
		InterfaceWrapperHelper.copyValues(receipt, reversal, false);
		reversal.setDocumentNo(receipt.getDocumentNo() + "_reversal");
		reversal.setDocStatus(X_M_InOut.DOCSTATUS_Reversed);
		reversal.setReversal_ID(receipt.getM_InOut_ID());
		InterfaceWrapperHelper.save(reversal);

		receipt.setReversal_ID(reversal.getM_InOut_ID());
		receipt.setDocStatus(X_M_InOut.DOCSTATUS_Reversed);
		InterfaceWrapperHelper.save(receipt);

		for (final org.compiere.model.I_M_InOutLine line : Services.get(IInOutDAO.class).retrieveLines(receipt))
		{
			final I_M_InOutLine reversalLine = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class, line);
			InterfaceWrapperHelper.copyValues(line, reversalLine, false);
			reversalLine.setM_InOut_ID(reversal.getM_InOut_ID());
			reversalLine.setReversalLine_ID(line.getM_InOutLine_ID());
			reversalLine.setMovementQty(line.getMovementQty().negate());
			reversalLine.setQtyEntered(line.getQtyEntered().negate());
			InterfaceWrapperHelper.save(reversalLine);

			//
			// Create M_Transaction reversals
			for (final I_M_Transaction mtrx : Services.get(IMTransactionDAO.class).retrieveReferenced(line))
			{
				reverseMTransaction(mtrx, reversalLine);
			}

			//
			// Update original line
			line.setReversalLine_ID(reversalLine.getM_InOutLine_ID());
			InterfaceWrapperHelper.save(line);
		}

		return reversal;
	}
}
