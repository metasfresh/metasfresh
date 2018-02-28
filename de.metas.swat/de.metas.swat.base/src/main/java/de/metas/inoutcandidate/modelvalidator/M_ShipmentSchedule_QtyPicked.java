package de.metas.inoutcandidate.modelvalidator;

import static org.adempiere.model.InterfaceWrapperHelper.getCtx;

import java.math.BigDecimal;

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

import java.util.Collections;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.picking.api.PickingConfigRepository;

@Interceptor(I_M_ShipmentSchedule_QtyPicked.class)
@Component
public class M_ShipmentSchedule_QtyPicked
{
	@Autowired
	private PickingConfigRepository pickingConfigRepo;

	/**
	 * Invalidates the {@link I_M_ShipmentSchedule} referenced by the given <code>shipmentScheduleQtyPicked</code>.
	 * <p>
	 * Note that a change in an {@link I_M_ShipmentSchedule_QtyPicked} records indicates that some qty is moved from <code>QtyToDeliver</code> to <code>QtyPistList</code>. the qtys that are available
	 * to be allocated to other shipment schedules are not changes by this. therefore, we only need to invalidate the single schedule referenced by the given parameter value.
	 * <p>
	 * Also note that as of this task, it is important to invalidate the shipment schedule if a <code>M_ShipmentSchedule_QtyPicked</code> changes, because we no longer have a DB-function to compute
	 * the picked quantity. Instead, we have the picked qty added to {@link I_M_ShipmentSchedule#COLUMNNAME_QtyPickList QtyPickList} by the regular shipment schedule updating mechanism.
	 * 
	 * @param shipmentScheduleQtyPicked
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_AFTER_DELETE })
	public void invalidateShipmentSchedule(final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		final List<I_M_ShipmentSchedule> singletonList = Collections.singletonList(shipmentScheduleQtyPicked.getM_ShipmentSchedule());
		final String trxName = InterfaceWrapperHelper.getTrxName(shipmentScheduleQtyPicked);

		Services.get(IShipmentSchedulePA.class).invalidate(singletonList, trxName);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE })
	public void validateOverdelivery(final I_M_ShipmentSchedule_QtyPicked schedQtyPicked)
	{
		final boolean isAllowOverdelivery = pickingConfigRepo.getPickingConfig().isAllowOverDelivery();

		if (isAllowOverdelivery)
		{
			// nothing to do
			return;
		}

		validateQtyPicked(schedQtyPicked);
	}

	private void validateQtyPicked(final I_M_ShipmentSchedule_QtyPicked schedQtyPicked)
	{
		final I_M_ShipmentSchedule schedule = schedQtyPicked.getM_ShipmentSchedule();

		final BigDecimal currentQtyToDeliver = schedule.getQtyToDeliver();

		final BigDecimal qtyPickedCandidate = schedQtyPicked.getQtyPicked();

		if (currentQtyToDeliver.compareTo(qtyPickedCandidate) < 0)
		{
			throw new AdempiereException(Services.get(IMsgBL.class).getMsg(getCtx(schedule), PickingConfigRepository.MSG_WEBUI_Picking_OverdeliveryNotAllowed));
		}

	}


}
