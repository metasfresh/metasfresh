package de.metas.inoutcandidate.modelvalidator;

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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.api.IInOutCandHandlerDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.model.I_M_IolCandHandler_Log;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;

/**
 * Shipment Schedule module: M_ShipmentSchedule
 *
 * @author tsa
 *
 */
@Validator(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule
{
	private static final String ERR_QtyDeliveredGreatedThanQtyOrdered = "ERR_QtyDeliveredGreatedThanQtyOrdered";

	/**
	 * Does some sanity checks on the given <code>schedule</code>
	 *
	 * @param schedule
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(final I_M_ShipmentSchedule schedule)
	{
		// task 09005: make sure the correct qtyOrdered is taken from the shipmentSchedule
		final BigDecimal qtyOrderedEffective = Services.get(IShipmentScheduleEffectiveBL.class).getQtyOrdered(schedule);

		// task 07355: we allow QtyOrdered == 0, because an order could be closed before a delivery was made
		Check.errorIf(qtyOrderedEffective.signum() < 0,
				"M_ShipmentSchedule {} has QtyOrderedEffective {} (less than 0!)", schedule, qtyOrderedEffective);

		Check.errorIf(schedule.getQtyReserved().signum() < 0,
				"M_ShipmentSchedule {} has QtyReserved {} (less than 0!)", schedule, schedule.getQtyReserved());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updateHeaderAggregationKey(final I_M_ShipmentSchedule schedule)
	{
		final IAggregationKeyBuilder<I_M_ShipmentSchedule> shipmentScheduleKeyBuilder = Services.get(IShipmentScheduleBL.class).mkShipmentHeaderAggregationKeyBuilder();
		final String headerAggregationKey = shipmentScheduleKeyBuilder.buildKey(schedule);
		schedule.setHeaderAggregationKey(headerAggregationKey);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updateBPArtnerAddressOverride(final I_M_ShipmentSchedule schedule)
	{
		if (InterfaceWrapperHelper.isValueChanged(schedule, I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID)
				|| InterfaceWrapperHelper.isValueChanged(schedule, I_M_ShipmentSchedule.COLUMNNAME_C_BP_Location_Override_ID)
				|| InterfaceWrapperHelper.isValueChanged(schedule, I_M_ShipmentSchedule.COLUMNNAME_AD_User_Override_ID)
				|| Check.isEmpty(schedule.getBPartnerAddress_Override(), true))
		{
			final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
			final Properties ctx = InterfaceWrapperHelper.getCtx(schedule);
			final String trxName = InterfaceWrapperHelper.getTrxName(schedule);
			shipmentScheduleBL.updateBPArtnerAddressOverride(ctx, schedule, trxName);
		}
	}

	/**
	 * If a shipment schedule is deleted, then this method makes sure that all {@link I_M_IolCandHandler_Log} records which refer to the same record as the schedule are also deleted.<br>
	 * Otherwise, that referenced record would never be considered again by {@link de.metas.inoutcandidate.spi.IInOutCandHandler#retrieveModelsWithMissingCandidates(Properties, String)}.
	 *
	 * @param schedule
	 * @task 08288
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteHandlerLog(final I_M_ShipmentSchedule schedule)
	{
		final IInOutCandHandlerDAO inOutCandHandlerDAO = Services.get(IInOutCandHandlerDAO.class);

		final List<I_M_IolCandHandler_Log> allHandlerLogs = inOutCandHandlerDAO.retrieveAllHandlerLogs(schedule);
		for (final I_M_IolCandHandler_Log log : allHandlerLogs)
		{
			InterfaceWrapperHelper.delete(log);
		}
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

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE })
	public void invalidate(final I_M_ShipmentSchedule schedule)
	{
		//
		// If shipment schedule updater is currently running in this thread, it means that updater changed this record
		// so there is NO need to invalidate it again.
		if (Services.get(IShipmentScheduleUpdater.class).isRunning())
		{
			return;
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(schedule);

		// Note: it's important the the schedule is only invalidated on certain value changes.
		// For example, a change of lock status or valid status may not cause an invalidation

		if (InterfaceWrapperHelper.isValueChanged(schedule, I_M_ShipmentSchedule.COLUMNNAME_M_AttributeSetInstance_ID) // task 08746: found by lili
				|| InterfaceWrapperHelper.isValueChanged(schedule, I_M_ShipmentSchedule.COLUMNNAME_AllowConsolidateInOut)
				|| InterfaceWrapperHelper.isValueChanged(schedule, I_M_ShipmentSchedule.COLUMNNAME_PriorityRule_Override)
				|| InterfaceWrapperHelper.isValueChanged(schedule, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override)
				|| InterfaceWrapperHelper.isValueChanged(schedule, I_M_ShipmentSchedule.COLUMNNAME_DeliveryRule_Override)
				|| InterfaceWrapperHelper.isValueChanged(schedule, I_M_ShipmentSchedule.COLUMNNAME_BPartnerAddress_Override))
		{
			Services.get(IShipmentSchedulePA.class).invalidate( // 08746: make sure that at any rate, the sched itself is invalidated
					Collections.singletonList(schedule),
					trxName);
			Services.get(IShipmentScheduleInvalidateBL.class).invalidateSegmentForShipmentSchedule(schedule);
		}

		if (InterfaceWrapperHelper.isValueChanged(schedule, I_M_ShipmentSchedule.COLUMNNAME_HeaderAggregationKey))
		{
			// note: scheduleOld.getHeaderAggregationKey() being empty is also covered in the shipmentSchedulePA method
			final I_M_ShipmentSchedule scheduleOld = InterfaceWrapperHelper.createOld(schedule, I_M_ShipmentSchedule.class);
			final Set<String> headerAggregationKeys = new HashSet<String>();
			headerAggregationKeys.add(scheduleOld.getHeaderAggregationKey());
			headerAggregationKeys.add(schedule.getHeaderAggregationKey());

			final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
			shipmentSchedulePA.invalidateForHeaderAggregationKeys(headerAggregationKeys, trxName);
		}
	}

	/**
	 * Updates the given candidate's QtyOrdered and
	 *
	 * @param shipmentSchedule
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Calculated, I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override })
	public void updateQtyOrdered(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		shipmentScheduleBL.updateQtyOrdered(shipmentSchedule);

		final BigDecimal qtyDelivered = shipmentSchedule.getQtyDelivered();

		// task 09005: make sure the correct qtyOrdered is taken from the shipmentSchedule
		final BigDecimal qtyOrdered = shipmentScheduleEffectiveBL.getQtyOrdered(shipmentSchedule);
		if (qtyDelivered.compareTo(qtyOrdered) > 0)
		{
			throw new AdempiereException(ERR_QtyDeliveredGreatedThanQtyOrdered, new Object[] { qtyDelivered });
		}

		if (adTableDAO.retrieveTableId(I_C_OrderLine.Table_Name) != shipmentSchedule.getAD_Table_ID())
		{
			return;
		}

		final I_C_OrderLine orderLine = shipmentSchedule.getC_OrderLine();
		if (orderLine == null)
		{
			return;
		}

		if (orderLine.getQtyOrdered().compareTo(qtyOrdered) == 0)
		{
			return; // avoid unnecessary changes
		}

		// note: don't try to suppress shipment schedule invalidation, because maybe other scheds also need updating when this order's qtyOrdered changes.
		orderLine.setQtyOrdered(qtyOrdered);

		final I_C_Order order = orderLine.getC_Order();

		final MOrder orderPO = LegacyAdapters.convertToPO(order);
		final MOrderLine orderLinePO = LegacyAdapters.convertToPO(orderLine);

		orderPO.reserveStock(MDocType.get(orderPO.getCtx(), order.getC_DocType_ID()), new MOrderLine[] { orderLinePO });

		InterfaceWrapperHelper.save(orderLine);
	}
}
