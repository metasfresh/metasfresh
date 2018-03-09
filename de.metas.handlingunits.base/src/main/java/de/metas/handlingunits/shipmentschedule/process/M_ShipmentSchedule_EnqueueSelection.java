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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Ini;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.Result;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromShipmentSchedules;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;

/**
 * Auswahl Liefern: Enqueue selected {@link I_M_ShipmentSchedule}s and let {@link GenerateInOutFromShipmentSchedules} process them.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/07042_Simple_InOut-Creation_from_shipment-schedule_%28109342691288%29
 */
public class M_ShipmentSchedule_EnqueueSelection
		extends JavaProcess
		implements IProcessPrecondition
{

	@Param(parameterName = "IsUseQtyPicked", mandatory = true)
	private boolean isUseQtyPicked;

	@Param(parameterName = "IsCompleteShipments", mandatory = true)
	private boolean isCompleteShipments;

	@Param(parameterName = "IsShipToday", mandatory = true)
	private boolean isShipToday; // introduced in task #2940

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize() <= 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No items are selected");
		}

		final boolean foundAtLeastOneUnprocessedSchedule = context.getSelectedModels(I_M_ShipmentSchedule.class).stream()
				.filter(sched -> sched.isActive())
				.filter(sched -> !sched.isProcessed())
				.findAny()
				.isPresent();

		return ProcessPreconditionsResolution.acceptIf(foundAtLeastOneUnprocessedSchedule);
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_M_ShipmentSchedule> queryFilters = createShipmentSchedulesQueryFilters();

		Check.assumeNotNull(queryFilters, "Shipment Schedule queryFiletrs shall not be null");

		final ShipmentScheduleWorkPackageParameters workPackageParameters = ShipmentScheduleWorkPackageParameters.builder()
				.adPInstanceId(getAD_PInstance_ID())
				.queryFilters(queryFilters)
				.useQtyPickedRecords(isUseQtyPicked)
				.completeShipments(isCompleteShipments)
				.isShipmentDateToday(isShipToday)
				.build();

		final Result result = new ShipmentScheduleEnqueuer()
				.setContext(getCtx(), getTrxName())
				.createWorkpackages(workPackageParameters);

		return "@Created@: " + result.getEneuedPackagesCount() + " @" + I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID + "@; @Skip@ " + result.getSkippedPackagesCount();
	}

	private IQueryFilter<I_M_ShipmentSchedule> createShipmentSchedulesQueryFilters()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_M_ShipmentSchedule> filters = queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class);

		filters.addOnlyActiveRecordsFilter();

		//
		// Filter only selected shipment schedules
		if (Ini.isClient())
		{
			final IQueryFilter<I_M_ShipmentSchedule> selectionFilter = getProcessInfo().getQueryFilter();
			filters.addFilter(selectionFilter);
		}
		else
		{
			final IQueryFilter<I_M_ShipmentSchedule> selectionFilter = getProcessInfo().getQueryFilterOrElse(null);
			if (selectionFilter == null)
			{
				throw new AdempiereException("@NoSelection@");
			}
			filters.addFilter(selectionFilter);
		}

		//
		// Filter only those which are not yet processed
		filters.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_Processed, false);

		return filters;
	}
}
