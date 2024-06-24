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

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.Result;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromShipmentSchedules;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Ini;

/**
 * Auswahl Liefern: Enqueue selected {@link I_M_ShipmentSchedule}s and let {@link GenerateInOutFromShipmentSchedules} process them.
 *
 * @author tsa
 * task http://dewiki908/mediawiki/index.php/07042_Simple_InOut-Creation_from_shipment-schedule_%28109342691288%29
 */
public class M_ShipmentSchedule_EnqueueSelection
		extends JavaProcess
		implements IProcessPrecondition
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Param(parameterName = "QuantityType", mandatory = true)
	private M_ShipmentSchedule_QuantityTypeToUse quantityType;

	@Param(parameterName = "IsCompleteShipments", mandatory = true)
	private boolean isCompleteShipments;

	@Param(parameterName = "IsShipToday", mandatory = true)
	private boolean isShipToday; // introduced in task #2940

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final boolean foundAtLeastOneUnprocessedSchedule = context.streamSelectedModels(I_M_ShipmentSchedule.class)
				.anyMatch(sched -> sched.isActive() && !sched.isProcessed());

		return ProcessPreconditionsResolution.acceptIf(foundAtLeastOneUnprocessedSchedule);
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_M_ShipmentSchedule> queryFilters = createShipmentSchedulesQueryFilters();

		Check.assumeNotNull(queryFilters, "Shipment Schedule queryFilters shall not be null");

		final ShipmentScheduleWorkPackageParameters workPackageParameters = ShipmentScheduleWorkPackageParameters.builder()
				.adPInstanceId(getPinstanceId())
				.queryFilters(queryFilters)
				.quantityType(quantityType)
				.completeShipments(isCompleteShipments)
				.isShipmentDateToday(isShipToday)
				.build();

		final Result result = new ShipmentScheduleEnqueuer()
				.setContext(getCtx(), getTrxName())
				.createWorkpackages(workPackageParameters);

		return "@Created@: " + result.getEnqueuedPackagesCount() + " @" + I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID + "@; @Skip@ " + result.getSkippedPackagesCount();
	}

	private IQueryFilter<I_M_ShipmentSchedule> createShipmentSchedulesQueryFilters()
	{
		final ICompositeQueryFilter<I_M_ShipmentSchedule> filters = queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class);

		filters.addOnlyActiveRecordsFilter();

		//
		// Filter only selected shipment schedules
		if (Ini.isSwingClient())
		{
			final IQueryFilter<I_M_ShipmentSchedule> selectionFilter = getProcessInfo().getQueryFilterOrElseTrue();
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
