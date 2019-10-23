package de.metas.inoutcandidate.modelvalidator;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.ModelValidator;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.IDocumentBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_IolCandHandler_Log;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.storage.IStorageBL;
import de.metas.storage.IStorageSegment;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Shipment Schedule module: M_ShipmentSchedule
 *
 * @author tsa
 *
 */
@Validator(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule
{
	private static final String MSG_DECREASE_QTY_ORDERED_BELOW_QTY_ALREADY_DELIVERED_IS_NOT_ALLOWED = //
			"de.metas.inoutcandidate.DecreaseQtyOrderedBelowQtyAlreadyDeliveredIsNotAllowed";

	/**
	 * Does some sanity checks on the given <code>schedule</code>
	 *
	 * @param schedule
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(final I_M_ShipmentSchedule schedule)
	{
		// task 09005: make sure the correct qtyOrdered is taken from the shipmentSchedule
		final BigDecimal qtyOrderedEffective = Services.get(IShipmentScheduleEffectiveBL.class).computeQtyOrdered(schedule);

		// task 07355: we allow QtyOrdered == 0, because an order could be closed before a delivery was made
		Check.errorIf(qtyOrderedEffective.signum() < 0,
				"M_ShipmentSchedule {} has QtyOrderedEffective {} (less than 0!)", schedule, qtyOrderedEffective);

		Check.errorIf(schedule.getQtyReserved().signum() < 0,
				"M_ShipmentSchedule {} has QtyReserved {} (less than 0!)", schedule, schedule.getQtyReserved());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updateHeaderAggregationKey(final I_M_ShipmentSchedule schedule)
	{
		 Services.get(IShipmentScheduleBL.class).updateHeaderAggregationKey(schedule);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updateBPartnerAddressOverride(final I_M_ShipmentSchedule schedule)
	{
		if (InterfaceWrapperHelper.isValueChanged(schedule, I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID)
				|| InterfaceWrapperHelper.isValueChanged(schedule, I_M_ShipmentSchedule.COLUMNNAME_C_BP_Location_Override_ID)
				|| InterfaceWrapperHelper.isValueChanged(schedule, I_M_ShipmentSchedule.COLUMNNAME_AD_User_Override_ID)
				|| Check.isEmpty(schedule.getBPartnerAddress_Override(), true))
		{
			final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
			schedule.setBPartnerAddress_Override(null);
			shipmentScheduleBL.updateBPArtnerAddressOverrideIfNotYetSet(schedule);
		}
	}

	/**
	 * If a shipment schedule is deleted, then this method makes sure that all {@link I_M_IolCandHandler_Log} records which refer to the same record as the schedule are also deleted.<br>
	 * Otherwise, that referenced record would never be considered again by {@link de.metas.inoutcandidate.spi.ShipmentScheduleHandler#retrieveModelsWithMissingCandidates(Properties, String)}.
	 *
	 * @param schedule
	 * @task 08288
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteHandlerLog(final I_M_ShipmentSchedule schedule)
	{
		retrieveAllHandlerLogs(schedule).delete();
	}

	private static IQuery<I_M_IolCandHandler_Log> retrieveAllHandlerLogs(final I_M_ShipmentSchedule shipmentSchedule)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_IolCandHandler_Log.class, shipmentSchedule)
				.addEqualsFilter(I_M_IolCandHandler_Log.COLUMNNAME_AD_Table_ID, shipmentSchedule.getAD_Table_ID())
				.addEqualsFilter(I_M_IolCandHandler_Log.COLUMN_Record_ID, shipmentSchedule.getRecord_ID())
				.create();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteShipmentScheduleQtyPicked(final I_M_ShipmentSchedule schedule)
	{
		final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);

		// Retrieve the M_ShipmentSchedule_QtyPicked entries for this schedule
		final List<I_M_ShipmentSchedule_QtyPicked> allocations = shipmentScheduleAllocDAO.retrieveAllQtyPickedRecords(schedule, I_M_ShipmentSchedule_QtyPicked.class);
		for (final I_M_ShipmentSchedule_QtyPicked alloc : allocations)
		{
			// delete the qtyPicked entries
			InterfaceWrapperHelper.delete(alloc);
		}
	}

	@ModelChange( //
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = {
					I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_ID,
					I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID
			})
	public void invalidateIfBusinessPartnerChanged(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		// If shipment schedule updater is currently running in this thread, it means that updater changed this record so there is NO need to invalidate it again.
		if (Services.get(IShipmentScheduleUpdater.class).isRunning())
		{
			return;
		}

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final BPartnerId newBPartnerId = shipmentScheduleEffectiveBL.getBPartnerId(shipmentSchedule);
		final BPartnerId oldBpartnerId = getOldBPartnerId(shipmentSchedule);
		if (Objects.equals(newBPartnerId, oldBpartnerId))
		{
			return;
		}

		invalidateForOldAndNewBPartners(shipmentSchedule, oldBpartnerId);
	}

	private BPartnerId getOldBPartnerId(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final I_M_ShipmentSchedule oldShipmentSchedule = InterfaceWrapperHelper.createOld(shipmentSchedule, I_M_ShipmentSchedule.class);
		final BPartnerId oldBpartnerId = shipmentScheduleEffectiveBL.getBPartnerId(oldShipmentSchedule);

		return oldBpartnerId;
	}

	private void invalidateForOldAndNewBPartners(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			final BPartnerId oldBpartnerId)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final BPartnerId newBPartnerId = shipmentScheduleEffectiveBL.getBPartnerId(shipmentSchedule);

		final IStorageBL storageBL = Services.get(IStorageBL.class);
		final IStorageSegment storageSegment = storageBL.createStorageSegmentBuilder()
				.addM_Product_ID(shipmentSchedule.getM_Product_ID())
				.addC_BPartner_ID(BPartnerId.toRepoId(newBPartnerId))
				.addC_BPartner_ID(BPartnerId.toRepoId(oldBpartnerId))
				.addM_AttributeSetInstance_ID(shipmentSchedule.getM_AttributeSetInstance_ID())
				.addWarehouseId(shipmentScheduleEffectiveBL.getWarehouseId(shipmentSchedule))
				.build();

		final IShipmentScheduleInvalidateRepository invalidSchedulesRepo = Services.get(IShipmentScheduleInvalidateRepository.class);
		invalidSchedulesRepo.invalidateStorageSegments(ImmutableList.of(storageSegment));
	}

	/**
	 * Note: it's important the the schedule is only invalidated on certain value changes.
	 * For example, a change of lock status or valid status may not cause an invalidation
	 *
	 * @param schedule
	 */
	@ModelChange( //
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = { //
					I_M_ShipmentSchedule.COLUMNNAME_M_AttributeSetInstance_ID, // task 08746: found by lili
					I_M_ShipmentSchedule.COLUMNNAME_AllowConsolidateInOut,
					I_M_ShipmentSchedule.COLUMNNAME_PriorityRule_Override,
					I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override,
					I_M_ShipmentSchedule.COLUMNNAME_DeliveryRule_Override,
					I_M_ShipmentSchedule.COLUMNNAME_BPartnerAddress_Override,
					I_M_ShipmentSchedule.COLUMNNAME_PreparationDate_Override,
					I_M_ShipmentSchedule.COLUMNNAME_IsClosed
			})
	public void invalidate(final I_M_ShipmentSchedule schedule)
	{
		// If shipment schedule updater is currently running in this thread, it means that updater changed this record so there is NO need to invalidate it again.
		if (Services.get(IShipmentScheduleUpdater.class).isRunning())
		{
			return;
		}

		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

		final IShipmentScheduleInvalidateBL invalidSchedulesService = Services.get(IShipmentScheduleInvalidateBL.class);
		invalidSchedulesService.invalidateShipmentSchedule(shipmentScheduleId); // 08746: make sure that at any rate, the sched itself is invalidated
		invalidSchedulesService.invalidateSegmentForShipmentSchedule(schedule);
	}

	@ModelChange( //
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = I_M_ShipmentSchedule.COLUMNNAME_HeaderAggregationKey)
	public void invalidateSchedulesWithOldAndNewHeaderAggregationKey(final I_M_ShipmentSchedule schedule)
	{
		// If shipment schedule updater is currently running in this thread, it means that updater changed this record so there is NO need to invalidate it again.
		if (Services.get(IShipmentScheduleUpdater.class).isRunning())
		{
			return;
		}

		// note: scheduleOld.getHeaderAggregationKey() being empty is also covered in the shipmentSchedulePA method
		final I_M_ShipmentSchedule scheduleOld = InterfaceWrapperHelper.createOld(schedule, I_M_ShipmentSchedule.class);
		final Set<String> headerAggregationKeys = new HashSet<>();
		headerAggregationKeys.add(scheduleOld.getHeaderAggregationKey());
		headerAggregationKeys.add(schedule.getHeaderAggregationKey());

		final IShipmentScheduleInvalidateRepository invalidSchedulesRepo = Services.get(IShipmentScheduleInvalidateRepository.class);
		invalidSchedulesRepo.invalidateForHeaderAggregationKeys(headerAggregationKeys);
	}

	/**
	 * Updates the given candidate's QtyOrdered.
	 * <p>
	 * IMPORTANT: we do not want to prohibit over-deliveries. That's why this method shall not be fired if e.g. QtyDelivered changed.
	 *
	 * @param shipmentSchedule
	 */
	@ModelChange( //
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { //
					I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Calculated,
					I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override
			})
	public void updateQtyOrdered(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		shipmentScheduleBL.updateQtyOrdered(shipmentSchedule);

		final BigDecimal qtyDelivered = shipmentSchedule.getQtyDelivered();
		final BigDecimal qtyOrdered = shipmentSchedule.getQtyOrdered();

		if (qtyDelivered.compareTo(qtyOrdered) > 0)
		{
			throw new AdempiereException(MSG_DECREASE_QTY_ORDERED_BELOW_QTY_ALREADY_DELIVERED_IS_NOT_ALLOWED, new Object[] { qtyDelivered });
		}

		updateQtyOrderedOfOrderLineAndReserveStock(shipmentSchedule);
	}

	private void updateQtyOrderedOfOrderLineAndReserveStock(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		if (shipmentSchedule.getAD_Table_ID() != getTableId(I_C_OrderLine.class))
		{
			return;
		}
		final I_C_OrderLine orderLine = shipmentSchedule.getC_OrderLine();
		if (orderLine == null)
		{
			return;
		}

		final BigDecimal qtyOrdered = shipmentSchedule.getQtyOrdered();
		if (orderLine.getQtyOrdered().compareTo(qtyOrdered) == 0)
		{
			return; // avoid unnecessary changes
		}

		final I_C_Order order = orderLine.getC_Order();
		final boolean orderNotCompleted = !Services.get(IDocumentBL.class).isDocumentCompleted(order);
		if (orderNotCompleted)
		{
			// issue https://github.com/metasfresh/metasfresh/issues/3815
			return; // don't update e.g. an order that was closed just now, while the async shipment-schedule creation took place.
		}

		// note: don't try to suppress shipment schedule invalidation, because maybe other scheds also need updating when this order's qtyOrdered changes.
		orderLine.setQtyOrdered(qtyOrdered);

		final MOrder orderPO = LegacyAdapters.convertToPO(order);
		final MOrderLine orderLinePO = LegacyAdapters.convertToPO(orderLine);

		orderPO.reserveStock(MDocType.get(orderPO.getCtx(), order.getC_DocType_ID()), ImmutableList.of(orderLinePO));

		InterfaceWrapperHelper.save(orderLine);
	}
}
