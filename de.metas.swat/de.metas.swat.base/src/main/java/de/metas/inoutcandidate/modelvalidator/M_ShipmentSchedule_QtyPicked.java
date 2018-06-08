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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collections;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;

@Validator(I_M_ShipmentSchedule_QtyPicked.class)
public class M_ShipmentSchedule_QtyPicked
{

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
}
