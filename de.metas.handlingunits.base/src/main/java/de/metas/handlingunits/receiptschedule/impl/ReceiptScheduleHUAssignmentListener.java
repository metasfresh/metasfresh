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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import de.metas.handlingunits.HUAssignmentListenerAdapter;
import de.metas.handlingunits.exceptions.HUNotAssignableException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;

/**
 * Dedicated assignment listener used then HUs are assigned to receipt schedules.
 *
 * @author tsa
 *
 */
public final class ReceiptScheduleHUAssignmentListener extends HUAssignmentListenerAdapter
{
	public static final transient ReceiptScheduleHUAssignmentListener instance = new ReceiptScheduleHUAssignmentListener();

	private ReceiptScheduleHUAssignmentListener()
	{
		super();
	}

	private I_M_ReceiptSchedule getReceiptScheduleOrNull(final Object model)
	{
		if (!InterfaceWrapperHelper.isInstanceOf(model, I_M_ReceiptSchedule.class))
		{
			// does not apply
			return null;
		}

		return InterfaceWrapperHelper.create(model, I_M_ReceiptSchedule.class);
	}

	/**
	 * Makes sure that only HUs which have <code>HUStatus=Planning</code> are allowed to be assigned to an Receipt Schedule. 
	 * If the given <code>model</code> is <code>null</code> or not an {@link I_M_ReceiptSchedule}, the method just returns.
	 */
	@Override
	public void assertAssignable(final I_M_HU hu, final Object model, final String trxName) throws HUNotAssignableException
	{
		final I_M_ReceiptSchedule receiptSchedule = getReceiptScheduleOrNull(model);
		if (receiptSchedule == null)
		{
			// does not apply
			return;
		}

		//
		// Only HUs which have HUStatus=Planning are allowed to be assigned to an Receipt Schedule
		final String huStatus = hu.getHUStatus();
		if (!X_M_HU.HUSTATUS_Planning.equals(huStatus))
		{
			throw new HUNotAssignableException("@HUStatus@ <> Planning", model, hu);
		}
	}

	/**
	 * Updates the given assigned {@code hu}'s {@code M_Locator_ID} from the I_M_ReceiptSchedule that is given {@code model}.
	 * 
	 */
	@Override
	public void onHUAssigned(final I_M_HU hu, final Object model, final String trxName)
	{
		final I_M_ReceiptSchedule receiptSchedule = getReceiptScheduleOrNull(model);
		if (receiptSchedule == null)
		{
			// does not apply
			return;
		}

		//
		// Update HU's locator (if needed)
		final I_M_Warehouse warehouse = Services.get(IReceiptScheduleBL.class).getM_Warehouse_Effective(receiptSchedule);
		final I_M_Locator locator = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse);
		hu.setM_Locator(locator);
		InterfaceWrapperHelper.save(hu, trxName);
	}
}
