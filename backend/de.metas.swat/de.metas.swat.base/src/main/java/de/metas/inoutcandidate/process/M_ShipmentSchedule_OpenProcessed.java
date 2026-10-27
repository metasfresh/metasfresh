package de.metas.inoutcandidate.process;

import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.util.TrxRunnable;

import java.util.Iterator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class M_ShipmentSchedule_OpenProcessed extends JavaProcess implements IProcessPrecondition
{
	// Services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	private static final AdMessageKey MSG_SHIPMENT_SCHEDULES_ALL_NOT_PROCESSED = AdMessageKey.of("M_ShipmentSchedule_OpenProcessed.ShipmentSchedulesAllNotProcessed");
	private static final AdMessageKey MSG_SHIPMENT_SCHEDULES_SKIP_OPEN = AdMessageKey.of("M_ShipmentSchedule_OpenProcessed.SkipOpen_1P");

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		// Make sure at least one shipment schedule is processed
		final boolean someSchedsAreProcessed = context.streamSelectedModels(I_M_ShipmentSchedule.class)
				.anyMatch(I_M_ShipmentSchedule::isProcessed);

		if (!someSchedsAreProcessed)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_SHIPMENT_SCHEDULES_ALL_NOT_PROCESSED));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{

		final Iterator<I_M_ShipmentSchedule> scheds = getSelectedShipmentSchedules();

		int counter = 0;

		for (final I_M_ShipmentSchedule shipmentSchedule : IteratorUtils.asIterable(scheds))
		{
			if (!shipmentSchedule.isProcessed())
			{
				addLog(msgBL.getMsg(getCtx(), MSG_SHIPMENT_SCHEDULES_SKIP_OPEN, new Object[] { shipmentSchedule.getM_ShipmentSchedule_ID() }));
				continue;
			}

			openInTrx(shipmentSchedule);
			counter++;
		}

		return "@Processed@: " + counter;
	}

	private Iterator<I_M_ShipmentSchedule> getSelectedShipmentSchedules()
	{
		final IQueryFilter<I_M_ShipmentSchedule> selectedSchedsFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		return queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.filter(selectedSchedsFilter)
				.create()
				.iterate(I_M_ShipmentSchedule.class);
	}

	private void openInTrx(final I_M_ShipmentSchedule shipmentSchedule)
	{
		Services.get(ITrxManager.class)
				.runInNewTrx((TrxRunnable)localTrxName -> {
					InterfaceWrapperHelper.setThreadInheritedTrxName(shipmentSchedule);
					shipmentScheduleBL.openShipmentSchedule(shipmentSchedule);
				});
	}

}
