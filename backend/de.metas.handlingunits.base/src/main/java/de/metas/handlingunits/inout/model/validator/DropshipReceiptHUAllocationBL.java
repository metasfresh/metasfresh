package de.metas.handlingunits.inout.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.LUTUCUPair;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.AddQtyPickedRequest;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.util.CatchWeightHelper;
import de.metas.inout.IInOutBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_PO_OrderLine_Alloc;
import org.compiere.model.I_M_InOut;
import org.slf4j.Logger;

import java.util.List;

/**
 * After a dropship receipt is completed, allocates each receipt HU to the corresponding
 * SO-line shipment schedule via {@link IHUShipmentScheduleBL#addQtyPickedAndUpdateHU}.
 *
 * <p>Gate: receipt must be a PO-receipt ({@code isSOTrx=false}) and the PO's order must
 * have {@code isDropShip=true}.  For each receipt line the BL walks
 * {@code C_PO_OrderLine_Alloc} to find the SO line, resolves its shipment schedule,
 * then registers each assigned top-level HU as picked.
 *
 * <p>Errors within per-line processing are caught and logged so they never abort
 * the receipt completion.
 *
 * <p>This is a plain POJO; it is instantiated directly by {@link M_InOut_DropshipReceiptListener}
 * and is not Spring-managed.
 */
public class DropshipReceiptHUAllocationBL
{
	private static final Logger logger = LogManager.getLogger(DropshipReceiptHUAllocationBL.class);

	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Allocates the HUs from the given dropship receipt to the corresponding SO-line shipment schedules.
	 * Gate checks (isSOTrx, order null, isDropShip) are performed here; callers need not pre-filter.
	 *
	 * @param inout the completed M_InOut (purchase receipt) to process
	 */
	public void allocateReceiptHUsToSOSchedules(@NonNull final I_M_InOut inout)
	{
		// Gate 1: must be an inbound receipt (purchase), not a shipment
		if (inout.isSOTrx())
		{
			return;
		}

		// Gate 2: the PO must be a dropship order
		final I_C_Order order = inout.getC_Order();
		if (order == null || !order.isDropShip())
		{
			return;
		}

		final List<org.compiere.model.I_M_InOutLine> lines = inOutBL.retrieveLines(inout, org.compiere.model.I_M_InOutLine.class);
		for (final org.compiere.model.I_M_InOutLine baseLine : lines)
		{
			final int orderLineRepoId = baseLine.getC_OrderLine_ID();
			if (orderLineRepoId <= 0)
			{
				continue;
			}
			final OrderLineId poOrderLineId = OrderLineId.ofRepoId(orderLineRepoId);

			final List<I_C_PO_OrderLine_Alloc> allocRecords = retrieveAllocRecords(poOrderLineId, inout);
			if (allocRecords.isEmpty())
			{
				logger.debug("No C_PO_OrderLine_Alloc found for C_PO_OrderLine_ID={}; skipping line {}",
						poOrderLineId, baseLine.getM_InOutLine_ID());
				continue;
			}

			// Invariant: createDropshipPOForSO produces a 1:1 SO-line → PO-line mapping via the aggregator,
			// so each PO line has exactly one C_PO_OrderLine_Alloc to one SO line. If multiple allocs ever
			// appear, the per-HU CatchWeightHelper.extractQtys logic would allocate the FULL HU qty to each
			// SO schedule (not split), so we fail loudly rather than silently double-allocate.
			Check.assume(allocRecords.size() <= 1,
					"Expected at most 1 C_PO_OrderLine_Alloc per PO line but got {} for C_PO_OrderLine_ID={}",
					allocRecords.size(), poOrderLineId);

			try
			{
				processReceiptLine(inout, baseLine, allocRecords);
			}
			catch (final RuntimeException ex)
			{
				// Errors per-line must never abort the overall receipt completion.
				final String msg = "Failed allocating dropship receipt HUs to SO shipment schedule for line "
						+ baseLine.getM_InOutLine_ID() + ": " + ex.getMessage();
				Loggables.addLog(msg);
				logger.warn(msg, ex);
			}
		}
	}

	private List<I_C_PO_OrderLine_Alloc> retrieveAllocRecords(
			@NonNull final OrderLineId poOrderLineId,
			@NonNull final I_M_InOut inout)
	{
		return queryBL
				.createQueryBuilder(I_C_PO_OrderLine_Alloc.class, inout)
				.addEqualsFilter(I_C_PO_OrderLine_Alloc.COLUMNNAME_C_PO_OrderLine_ID, poOrderLineId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	private void processReceiptLine(
			@NonNull final I_M_InOut inout,
			@NonNull final org.compiere.model.I_M_InOutLine baseLine,
			@NonNull final List<I_C_PO_OrderLine_Alloc> allocRecords)
	{
		// Retrieve HUs assigned to this receipt line
		final I_M_InOutLine huLine = InterfaceWrapperHelper.create(baseLine, I_M_InOutLine.class);
		final List<I_M_HU> assignedHUs = huAssignmentDAO.retrieveTopLevelHUsForModel(huLine);
		if (assignedHUs.isEmpty())
		{
			logger.debug("No HUs assigned to receipt line {}; skipping", baseLine.getM_InOutLine_ID());
			return;
		}

		final ProductId productId = ProductId.ofRepoId(baseLine.getM_Product_ID());
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(inout);

		for (final I_C_PO_OrderLine_Alloc allocRecord : allocRecords)
		{
			final int soOrderLineRepoId = allocRecord.getC_SO_OrderLine_ID();
			if (soOrderLineRepoId <= 0)
			{
				continue;
			}

			final OrderLineId soOrderLineId = OrderLineId.ofRepoId(soOrderLineRepoId);

			// Resolve SO line → shipment schedule
			final I_M_ShipmentSchedule schedule;
			try
			{
				schedule = shipmentScheduleBL.getByOrderLineId(soOrderLineId);
			}
			catch (final Exception ex)
			{
				logger.debug("No shipment schedule found for SO line {}; skipping alloc record {}",
						soOrderLineId, allocRecord.getC_PO_OrderLine_Alloc_ID(), ex);
				continue;
			}

			// Process each assigned HU
			for (final I_M_HU hu : assignedHUs)
			{
				try
				{
					processHUForSchedule(hu, schedule, productId, contextProvider);
				}
				catch (final RuntimeException ex)
				{
					final String msg = "Failed allocating HU " + hu.getM_HU_ID()
							+ " to schedule " + schedule.getM_ShipmentSchedule_ID()
							+ " for SO line " + soOrderLineId + ": " + ex.getMessage();
					Loggables.addLog(msg);
					logger.warn(msg, ex);
				}
			}
		}
	}

	private void processHUForSchedule(
			@NonNull final I_M_HU hu,
			@NonNull final I_M_ShipmentSchedule schedule,
			@NonNull final ProductId productId,
			@NonNull final IContextAware contextProvider)
	{
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(contextProvider);

		// Resolve VHU for idempotency check
		final LUTUCUPair lutuPair = handlingUnitsBL.getTopLevelParentAsLUTUCUPair(hu);
		final I_M_HU vhu = lutuPair.getVHU();
		if (vhu == null)
		{
			logger.warn("Cannot determine VHU for HU {}; skipping schedule allocation to avoid duplicate-risk on VHU_ID=0", hu.getM_HU_ID());
			Loggables.addLog("Skipped dropship receipt HU {0}: no VHU found", hu.getM_HU_ID());
			return;
		}
		final HuId vhuId = HuId.ofRepoId(vhu.getM_HU_ID());

		// Idempotency: skip if QtyPicked already exists for (schedule, VHU)
		final boolean alreadyPicked = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, contextProvider)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, schedule.getM_ShipmentSchedule_ID())
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_VHU_ID, vhuId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();

		if (alreadyPicked)
		{
			logger.debug("Skipping already-picked HU {} for schedule {} (VHU_ID={})",
					hu.getM_HU_ID(), schedule.getM_ShipmentSchedule_ID(), vhuId);
			return;
		}

		// Build qtyPicked from the HU's actual storage for the given product
		final StockQtyAndUOMQty qtyPicked = CatchWeightHelper.extractQtys(huContext, productId, hu);

		huShipmentScheduleBL.addQtyPickedAndUpdateHU(AddQtyPickedRequest.builder()
				.shipmentSchedule(schedule)
				.qtyPicked(qtyPicked)
				.hu(hu)
				.huContext(huContext)
				.anonymousHuPickedOnTheFly(false)
				.build());
	}
}
