package de.metas.ui.web.shipmentschedule.process;

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

import com.google.common.collect.ImmutableSet;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractService;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.Result;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromShipmentSchedules;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ForeignExchangeContract;

import javax.annotation.Nullable;

/**
 * Auswahl Liefern: Enqueue selected {@link I_M_ShipmentSchedule}s and let {@link GenerateInOutFromShipmentSchedules} process them.
 *
 * @author tsa
 * @implSpec <a href="http://dewiki908/mediawiki/index.php/07042_Simple_InOut-Creation_from_shipment-schedule_%28109342691288%29">task</a>
 */
public class M_ShipmentSchedule_EnqueueSelection
		extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final ForexContractService forexContractService = SpringContextHolder.instance.getBean(ForexContractService.class);
	private final LookupDataSource forexContractLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_C_ForeignExchangeContract.Table_Name);

	@Param(parameterName = "QuantityType", mandatory = true)
	private M_ShipmentSchedule_QuantityTypeToUse quantityType;

	@Param(parameterName = "IsCompleteShipments", mandatory = true)
	private boolean isCompleteShipments;

	@Param(parameterName = "IsShipToday", mandatory = true)
	private boolean isShipToday; // introduced in task #2940

	private static final String PARAM_IsFEC = "IsFEC";
	@Param(parameterName = PARAM_IsFEC)
	private boolean isForexContract;

	private static final String PARAM_C_ForeignExchangeContract_ID = I_C_ForeignExchangeContract.COLUMNNAME_C_ForeignExchangeContract_ID;
	@Param(parameterName = PARAM_C_ForeignExchangeContract_ID)
	private ForexContractId forexContractId;

	private ImmutableSet<ForexContractId> _eligibleForexContractIds;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final boolean foundAtLeastOneUnprocessedSchedule = context.getSelectedModels(I_M_ShipmentSchedule.class)
				.stream()
				.anyMatch(sched -> sched.isActive() && !sched.isProcessed());

		return ProcessPreconditionsResolution.acceptIf(foundAtLeastOneUnprocessedSchedule);
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_IsFEC.equals(parameter.getColumnName()))
		{
			return !getEligibleForexContractIds().isEmpty();
		}
		else if (PARAM_C_ForeignExchangeContract_ID.equals(parameter.getColumnName()))
		{
			final ImmutableSet<ForexContractId> forexContractIds = getEligibleForexContractIds();
			return forexContractIds.size() == 1 ? forexContractIds.iterator().next() : null;
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_C_ForeignExchangeContract_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	public LookupValuesList getEligibleForexContracts()
	{
		return forexContractLookup.findByIdsOrdered(getEligibleForexContractIds());
	}

	@Override
	protected String doIt()
	{
		if (isForexContract && forexContractId == null)
		{
			throw new FillMandatoryException(PARAM_C_ForeignExchangeContract_ID);
		}

		final Result result = new ShipmentScheduleEnqueuer()
				.setContext(getCtx(), getTrxName())
				.createWorkpackages(
						ShipmentScheduleWorkPackageParameters.builder()
								.adPInstanceId(getPinstanceId())
								.queryFilters(getShipmentSchedulesQueryFilters())
								.quantityType(quantityType)
								.completeShipments(isCompleteShipments)
								.isShipmentDateToday(isShipToday)
								.forexContractId(isForexContract ? forexContractId : null)
								.build());

		return "@Created@: " + result.getEnqueuedPackagesCount() + " @" + I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID + "@; @Skip@ " + result.getSkippedPackagesCount();
	}

	private IQueryFilter<I_M_ShipmentSchedule> getShipmentSchedulesQueryFilters()
	{
		final ICompositeQueryFilter<I_M_ShipmentSchedule> filters = queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class);

		filters.addOnlyActiveRecordsFilter();

		//
		// Filter only selected shipment schedules
		final IQueryFilter<I_M_ShipmentSchedule> selectionFilter = getProcessInfo().getQueryFilterOrElse(null);
		if (selectionFilter == null)
		{
			throw new AdempiereException("@NoSelection@");
		}
		filters.addFilter(selectionFilter);

		//
		// Filter only those which are not yet processed
		filters.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Processed, false);

		return filters;
	}

	private ImmutableSet<ForexContractId> getEligibleForexContractIds()
	{
		ImmutableSet<ForexContractId> eligibleForexContractIds = this._eligibleForexContractIds;
		if (eligibleForexContractIds == null)
		{
			eligibleForexContractIds = this._eligibleForexContractIds = retrieveEligibleForexContractIds();
		}
		return eligibleForexContractIds;
	}

	private ImmutableSet<ForexContractId> retrieveEligibleForexContractIds()
	{
		final ImmutableSet<OrderId> orderIds = shipmentScheduleBL.getOrderIds(getShipmentSchedulesQueryFilters());
		if (orderIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return forexContractService.getContractIdsByOrderIds(orderIds);
	}

}
