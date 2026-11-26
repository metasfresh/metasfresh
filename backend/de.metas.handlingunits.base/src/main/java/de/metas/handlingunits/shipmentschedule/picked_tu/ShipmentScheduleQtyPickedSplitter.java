package de.metas.handlingunits.shipmentschedule.picked_tu;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Write side of the candidate creation: lets a {@code M_ShipmentSchedule_QtyPicked} row stand for one part of its quantity
 * (the row itself for the first part, a row split off it for every further part), saves it and makes the candidate of it.
 */
@RequiredArgsConstructor
class ShipmentScheduleQtyPickedSplitter
{
	@NonNull private final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
	@NonNull private final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	@NonNull private final IHUContext huContext;
	@NonNull private final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse;

	/** an instance serves one call, so what it read stays valid */
	private final Map<ShipmentScheduleId, I_M_ShipmentSchedule> schedulesById = new HashMap<>();

	/**
	 * The part of a row's quantity that becomes one candidate.
	 */
	@Value
	static class CandidatePart
	{
		@NonNull Quantity qty;
		/** the CU the quantity is exactly; {@code null} if it cannot be attributed to one CU */
		@Nullable I_M_HU wholeCU;
		/** the CU whose attributes the candidate (and so the shipment line) takes */
		@NonNull I_M_HU attributeSourceCU;
	}

	/**
	 * @return the candidate of the part; the row of the part is saved
	 */
	ShipmentScheduleWithHU saveRowAndCreateCandidate(
			@NonNull final I_M_ShipmentSchedule_QtyPicked source,
			final boolean isFirstPart,
			@NonNull final CandidatePart part,
			@Nullable final Quantity catchWeightShare)
	{
		final I_M_ShipmentSchedule_QtyPicked row = rowForPart(source, isFirstPart, part.getQty(), catchWeightShare);
		if (part.getWholeCU() != null)
		{
			row.setVHU_ID(part.getWholeCU().getM_HU_ID());
		}
		row.setQtyPicked(part.getQty().toBigDecimal());
		huShipmentScheduleDAO.saveQtyPicked(row);
		return ShipmentScheduleWithHU.ofShipmentScheduleQtyPickedForVHU(row, huContext, part.getAttributeSourceCU(), part.getQty(), qtyTypeToUse);
	}

	/**
	 * @return the source row itself for the first part (it keeps its identity), a new row split off the source for every further part
	 */
	private I_M_ShipmentSchedule_QtyPicked rowForPart(
			@NonNull final I_M_ShipmentSchedule_QtyPicked source,
			final boolean isFirstPart,
			@NonNull final Quantity partQty,
			@Nullable final Quantity catchWeightShare)
	{
		if (isFirstPart)
		{
			if (catchWeightShare != null)
			{
				source.setQtyDeliveredCatch(catchWeightShare.toBigDecimal());
			}
			return source;
		}
		else
		{
			return splitOff(source, partQty, catchWeightShare);
		}
	}

	/**
	 * Creates (does not save) a row for a part of the source row's quantity.
	 * Copies the columns that tell where and by which pick the quantity was picked: TU, LU, picking job schedule and the
	 * picked-on-the-fly flag, so the part is found by the queries that look for them. {@code QtyTU} and {@code QtyLU} are not
	 * copied: they count the TUs and LUs of the whole pick and would multiply them.
	 */
	private I_M_ShipmentSchedule_QtyPicked splitOff(
			@NonNull final I_M_ShipmentSchedule_QtyPicked source,
			@NonNull final Quantity partQty,
			@Nullable final Quantity catchWeightShare)
	{
		final I_M_ShipmentSchedule schedule = getSchedule(ShipmentScheduleId.ofRepoId(source.getM_ShipmentSchedule_ID()));
		final ProductId productId = ProductId.ofRepoId(schedule.getM_Product_ID());
		final StockQtyAndUOMQty partStockAndCatchQty = catchWeightShare != null
				? StockQtyAndUOMQtys.create(partQty.toBigDecimal(), productId, catchWeightShare.toBigDecimal(), UomId.ofRepoIdOrNull(source.getCatch_UOM_ID()))
				: StockQtyAndUOMQtys.ofQtyInStockUOM(partQty, productId);
		final I_M_ShipmentSchedule_QtyPicked part = shipmentScheduleAllocBL.createNewQtyPickedRecordNoSave(
				schedule,
				partStockAndCatchQty,
				I_M_ShipmentSchedule_QtyPicked.class);
		part.setM_TU_HU_ID(source.getM_TU_HU_ID());
		part.setM_LU_HU_ID(source.getM_LU_HU_ID());
		part.setM_Picking_Job_Schedule_ID(source.getM_Picking_Job_Schedule_ID());
		part.setIsAnonymousHuPickedOnTheFly(source.isAnonymousHuPickedOnTheFly());
		return part;
	}

	private I_M_ShipmentSchedule getSchedule(@NonNull final ShipmentScheduleId scheduleId)
	{
		return schedulesById.computeIfAbsent(scheduleId, shipmentScheduleBL::getById);
	}
}

