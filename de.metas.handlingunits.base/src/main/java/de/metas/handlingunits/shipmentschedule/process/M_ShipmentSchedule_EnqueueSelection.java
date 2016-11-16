package de.metas.handlingunits.shipmentschedule.process;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.Services;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.Result;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromShipmentSchedules;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;
import de.metas.process.ProcessExecutionResult.ShowProcessLogs;

/**
 * Auswahl Liefern: Enqueue selected {@link I_M_ShipmentSchedule}s and let {@link GenerateInOutFromShipmentSchedules} process them.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/07042_Simple_InOut-Creation_from_shipment-schedule_%28109342691288%29
 */
public class M_ShipmentSchedule_EnqueueSelection extends SvrProcess
{
	public static final String PARAM_IsUseQtyPicked = "IsUseQtyPicked";
	public static final String PARAM_IsCompleteShipments = "IsCompleteShipments";

	private boolean p_IsUseQtyPicked = false;
	private boolean p_IsCompleteShipments = false;

	@Override
	protected void prepare()
	{
		// don't FUD the user with AD_PInstance_Log records unless there is an actual error
		setShowProcessLogs(ShowProcessLogs.OnError);

		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameter() == null)
			{
				continue;
			}
			final String parameterName = para.getParameterName();

			if (PARAM_IsUseQtyPicked.equals(parameterName))
			{
				p_IsUseQtyPicked = para.getParameterAsBoolean();
			}
			if (PARAM_IsCompleteShipments.equals(parameterName))
			{
				p_IsCompleteShipments = para.getParameterAsBoolean();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_M_ShipmentSchedule> queryFilters = createShipmentSchedulesQueryFilters();

		final Result result = new ShipmentScheduleEnqueuer()
				.setContext(getCtx(), getTrxName())
				.createWorkpackages(getAD_PInstance_ID(), queryFilters, p_IsUseQtyPicked, p_IsCompleteShipments);

		return "@Created@: " + result.getEneuedPackagesCount() + " @" + I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID + "@; @Skip@ " +result.getSkippedPackagesCount();
	}

	private IQueryFilter<I_M_ShipmentSchedule> createShipmentSchedulesQueryFilters()
	{
		final ICompositeQueryFilter<I_M_ShipmentSchedule> filters = Services.get(IQueryBL.class).createCompositeQueryFilter(I_M_ShipmentSchedule.class);

		filters.addOnlyActiveRecordsFilter();

		//
		// Filter only selected shipment schedules
		final IQueryFilter<I_M_ShipmentSchedule> selectionFilter = getProcessInfo().getQueryFilter();
		filters.addFilter(selectionFilter);

		//
		// Filter only those which are not yet processed
		filters.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_Processed, false);

		return filters;
	}
}
