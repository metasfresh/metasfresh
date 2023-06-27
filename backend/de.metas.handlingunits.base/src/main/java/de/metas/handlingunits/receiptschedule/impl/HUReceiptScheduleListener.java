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


import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.ReceiptScheduleListenerAdapter;
import de.metas.util.Services;

public class HUReceiptScheduleListener extends ReceiptScheduleListenerAdapter
{
	public static final transient HUReceiptScheduleListener instance = new HUReceiptScheduleListener();

	private HUReceiptScheduleListener()
	{
		super();
	}

	@Override
	public void onBeforeClose(final I_M_ReceiptSchedule receiptSchedule)
	{
		// Services
		final IHUReceiptScheduleDAO huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);
		final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);

		final String trxName = InterfaceWrapperHelper.getTrxName(receiptSchedule);
		final List<I_M_ReceiptSchedule_Alloc> allocs = huReceiptScheduleDAO.retrieveAllHandlingUnitAllocations(receiptSchedule, trxName);

		huReceiptScheduleBL.destroyHandlingUnits(allocs, trxName);
	}
}
