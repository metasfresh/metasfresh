package de.metas.handlingunits.receiptschedule.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.hutransaction.IHUTrxListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 *
 * Added to the system via {@link IHUTrxBL#addListener(de.metas.handlingunits.hutransaction.IHUTrxListener)}.
 *
 * NOTE: can be accessed only via {@link #instance} to make sure we are not registering it twice.
 *
 * @author tsa
 *
 */
public final class ReceiptScheduleHUTrxListener implements IHUTrxListener
{
	public static final ReceiptScheduleHUTrxListener instance = new ReceiptScheduleHUTrxListener();

	private ReceiptScheduleHUTrxListener()
	{
	}

	/**
	 * Creates {@link I_M_ReceiptSchedule_Alloc}s and {@link I_M_HU_Assignment}s. according to the processed {@link I_M_HU_Trx_Line},
	 * if that line's referenced object is a receipt schedule.
	 * <p>
	 * Will not delete pre-existing allocations in the process.
	 *
	 */
	@Override
	public void trxLineProcessed(final IHUContext huContext, final I_M_HU_Trx_Line trxLine)
	{
		final IHUTrxBL trxBL = Services.get(IHUTrxBL.class);

		//
		// Make sure this transaction is about receipt schedules
		// If not, it means its not the subject of this listener
		final I_M_ReceiptSchedule receiptSchedule = trxBL.getReferencedObjectOrNull(trxLine, I_M_ReceiptSchedule.class);
		if (receiptSchedule == null)
		{
			return;
		}

		// Make sure HU Item is present
		// If not, it means this is the transaction which is changing the receipt schedule qty
		// but we are looking for that one which is changing the HU Item because we need that one too
		final I_M_HU_Item huItem = trxLine.getM_HU_Item();
		if (huItem == null)
		{
			return;
		}

		createReceiptScheduleAllocFromTrxLine(trxLine, receiptSchedule, huItem);
	}

	private void createReceiptScheduleAllocFromTrxLine(
			@NonNull final I_M_HU_Trx_Line trxLine,
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final I_M_HU_Item huItem)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final BigDecimal qtyToAllocateOnHU = trxLine.getQty();
		final I_C_UOM uom = IHUTrxBL.extractUOMOrNull(trxLine);

		//
		// Handling Unit which was actually involved in this HU transaction (i.e. our TU)
		final I_M_HU hu = huItem.getM_HU();
		final I_M_HU tuHU;
		final I_M_HU luHU;
		final I_M_HU_Item vhuItem = trxLine.getVHU_Item();
		final I_M_HU vhu = vhuItem == null ? null : vhuItem.getM_HU();

		//
		// Case: our HU is a LU
		if (handlingUnitsBL.isLoadingUnit(hu))
		{
			// Make sure this is just a dummy transaction used to LU assignments, attribute transfers etc
			Check.assume(qtyToAllocateOnHU.signum() == 0, "Transactions about LUs shall always have Qty=0: {}", trxLine);
			return;
		}
		//
		// Case: our HU is a TU or a top level VHU
		else
		{
			tuHU = hu;
			luHU = handlingUnitsBL.getLoadingUnitHU(tuHU);
		}

		//
		// Create TU/LU allocation to Receipt Schedule
		final ReceiptScheduleHUAllocations huAllocations = new ReceiptScheduleHUAllocations(receiptSchedule);
		// "\nluHU\n"+de.metas.handlingunits.HUXmlConverter.toString(de.metas.handlingunits.HUXmlConverter.toXml(luHU))+"\ntuHU\n"+de.metas.handlingunits.HUXmlConverter.toString(de.metas.handlingunits.HUXmlConverter.toXml(tuHU))+"\nvhu\n"+de.metas.handlingunits.HUXmlConverter.toString(de.metas.handlingunits.HUXmlConverter.toXml(vhu))
		//
		// 07698: do not delete old allocations when creating them from transaction line
		final boolean deleteOldTUAllocations = false;
		final ProductId productId = ProductId.ofRepoId(receiptSchedule.getM_Product_ID());
		final StockQtyAndUOMQty qtyToAllocate = StockQtyAndUOMQtys.createConvert(Quantity.of(qtyToAllocateOnHU, uom), productId, (Quantity)null/* uomQty */);

		huAllocations.allocate(luHU, tuHU, vhu, qtyToAllocate, deleteOldTUAllocations);
	}

	/**
	 * Change all {@link I_M_ReceiptSchedule_Alloc}s which are about the given TU and set the new LU to them.
	 */
	@Override
	public void huParentChanged(final I_M_HU hu, final I_M_HU_Item parentHUItemOld)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		// If it's not an TU or VHU, we shall do nothing
		if (!handlingUnitsBL.isTransportUnitOrVirtual(hu))
		{
			return;
		}

		final I_M_HU tuHU = hu;
		final IHUReceiptScheduleDAO huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);
		huReceiptScheduleDAO.updateAllocationLUForTU(tuHU);
	}

	/**
	 * On split, update transaction's referenced model if missing.
	 *
	 * After that, we relly on {@link #trxLineProcessed(IHUContext, I_M_HU_Trx_Line)} business logic, to create the proper {@link I_M_ReceiptSchedule_Alloc}s.
	 */
	@Override
	public void onSplitTransaction(final IHUContext huContext, final IHUTransactionCandidate unloadTrx, final IHUTransactionCandidate loadTrx)
	{
		final I_M_ReceiptSchedule referencedModel = findReceiptScheduleFromSplitTransactions(huContext, unloadTrx, loadTrx);
		if (referencedModel == null)
		{
			return;
		}

		unloadTrx.setReferencedModel(referencedModel);
		loadTrx.setReferencedModel(referencedModel);
	}

	@Nullable
	private I_M_ReceiptSchedule findReceiptScheduleFromSplitTransactions(final IHUContext huContext, final IHUTransactionCandidate unloadTrx, final IHUTransactionCandidate loadTrx)
	{
		//
		// If referenced model of the load transaction is a Receipt Schedule, use that
		final Object loadReferencedModel = loadTrx.getReferencedModel();
		if (InterfaceWrapperHelper.isInstanceOf(loadReferencedModel, I_M_ReceiptSchedule.class))
		{
			return InterfaceWrapperHelper.create(loadReferencedModel, I_M_ReceiptSchedule.class);
		}

		//
		// If referenced model of the unload transaction is a Receipt Schedule, use that
		final Object unloadReferencedModel = unloadTrx.getReferencedModel();
		if (InterfaceWrapperHelper.isInstanceOf(unloadReferencedModel, I_M_ReceiptSchedule.class))
		{
			return InterfaceWrapperHelper.create(unloadReferencedModel, I_M_ReceiptSchedule.class);
		}

		//
		// Find the receipt schedule of the VHU from where we split
		final I_M_HU fromVHU = unloadTrx.getVHU();
		if(X_M_HU.HUSTATUS_Planning.equals(fromVHU.getHUStatus()))
		{
			final IHUReceiptScheduleDAO huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);
			return huReceiptScheduleDAO.retrieveReceiptScheduleForVHU(fromVHU);
		}

		// Fallback
		return null;
	}

}
