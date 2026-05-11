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

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.LUTUCUPair;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.AddQtyPickedRequest;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.util.CatchWeightHelper;
import de.metas.inout.IInOutBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * After a dropship receipt is completed, allocates each receipt HU to the corresponding
 * SO-line shipment schedule via {@link IHUShipmentScheduleBL#addQtyPickedAndUpdateHU}.
 *
 * <p>Gate: receipt must be a PO-receipt ({@code isSOTrx=false}) and the PO's order must
 * have {@code isDropShip=true}.  For each receipt line the listener walks
 * {@code C_PO_OrderLine_Alloc} to find the SO line, resolves its shipment schedule,
 * then registers each assigned top-level HU as picked.
 *
 * <p>Errors within per-line processing are caught and logged so they never abort
 * the receipt completion.
 */
@Component
@Interceptor(I_M_InOut.class)
public class M_InOut_DropshipReceiptListener
{
	private static final Logger logger = LogManager.getLogger(M_InOut_DropshipReceiptListener.class);

	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void onAfterComplete(@NonNull final I_M_InOut inout)
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
			try
			{
				processReceiptLine(inout, baseLine);
			}
			catch (final RuntimeException ex)
			{
				// Per task spec: never abort receipt completion — log and continue
				final String msg = "Failed allocating dropship receipt HUs to SO shipment schedule for line "
						+ baseLine.getM_InOutLine_ID() + ": " + ex.getMessage();
				Loggables.addLog(msg);
				logger.warn(msg, ex);
			}
		}
	}

	private void processReceiptLine(
			@NonNull final I_M_InOut inout,
			@NonNull final org.compiere.model.I_M_InOutLine baseLine)
	{
		// Skip packing-material lines and lines without an order line
		final int orderLineRepoId = baseLine.getC_OrderLine_ID();
		if (orderLineRepoId <= 0)
		{
			return;
		}

		final OrderLineId poOrderLineId = OrderLineId.ofRepoId(orderLineRepoId);

		// Walk C_PO_OrderLine_Alloc to find the SO line
		final List<org.compiere.model.I_C_PO_OrderLine_Alloc> allocRecords = queryBL
				.createQueryBuilder(org.compiere.model.I_C_PO_OrderLine_Alloc.class, inout)
				.addEqualsFilter(org.compiere.model.I_C_PO_OrderLine_Alloc.COLUMNNAME_C_PO_OrderLine_ID, poOrderLineId.getRepoId())
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		if (allocRecords.isEmpty())
		{
			logger.debug("No C_PO_OrderLine_Alloc found for C_PO_OrderLine_ID={}; skipping line {}",
					poOrderLineId, baseLine.getM_InOutLine_ID());
			return;
		}

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

		for (final org.compiere.model.I_C_PO_OrderLine_Alloc allocRecord : allocRecords)
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
		final int vhuRepoId = vhu.getM_HU_ID();

		// Idempotency: skip if QtyPicked already exists for (schedule, VHU)
		final boolean alreadyPicked = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, contextProvider)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, schedule.getM_ShipmentSchedule_ID())
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_VHU_ID, vhuRepoId)
				.create()
				.anyMatch();

		if (alreadyPicked)
		{
			logger.debug("Skipping already-picked HU {} for schedule {} (VHU_ID={})",
					hu.getM_HU_ID(), schedule.getM_ShipmentSchedule_ID(), vhuRepoId);
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
