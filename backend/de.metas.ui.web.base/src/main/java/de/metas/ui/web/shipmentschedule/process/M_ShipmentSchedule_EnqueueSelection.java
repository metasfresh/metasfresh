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

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.blockstatus.BPartnerBlockStatusService;
import de.metas.forex.ForexContractService;
import de.metas.forex.process.utils.ForexContractParameters;
import de.metas.forex.process.utils.ForexContracts;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.Result;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromShipmentSchedules;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.NestedParams;
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
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ForeignExchangeContract;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Auswahl Liefern: Enqueue selected {@link I_M_ShipmentSchedule}s and let {@link GenerateInOutFromShipmentSchedules} process them.
 *
 * @author tsa
 * @implSpec <a href="http://dewiki908/mediawiki/index.php/07042_Simple_InOut-Creation_from_shipment-schedule_%28109342691288%29">task</a>
 */
public class M_ShipmentSchedule_EnqueueSelection
		extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider, IProcessParametersCallout
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final ForexContractService forexContractService = SpringContextHolder.instance.getBean(ForexContractService.class);
	private final BPartnerBlockStatusService bPartnerBlockStatusService = SpringContextHolder.instance.getBean(BPartnerBlockStatusService.class);
	private final LookupDataSource forexContractLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_C_ForeignExchangeContract.Table_Name);

	@Param(parameterName = "QuantityType", mandatory = true)
	private M_ShipmentSchedule_QuantityTypeToUse quantityType;

	@Param(parameterName = "IsCompleteShipments", mandatory = true)
	private boolean isCompleteShipments;

	@Param(parameterName = "IsShipToday", mandatory = true)
	private boolean isShipToday; // introduced in task #2940

	@Param(parameterName = "ShipmentDate")
	private LocalDate shipmentDate;

	@NestedParams
	private final ForexContractParameters p_FECParams = ForexContractParameters.newInstance();

	private final Supplier<ForexContracts> forexContractsSupplier = Suppliers.memoize(this::retrieveContracts);

	@Nullable
	private ForexContracts getContracts()
	{
		return forexContractsSupplier.get();
	}

	@Nullable
	private ForexContracts retrieveContracts()
	{
		final ImmutableSet<OrderId> salesOrderIds = shipmentScheduleBL.getOrderIds(getShipmentSchedulesQueryFilters());
		if (salesOrderIds.size() != 1)
		{
			return null;
		}

		final OrderId salesOrderId = salesOrderIds.iterator().next();

		return ForexContracts.builder()
				.orderCurrencyId(orderBL.getCurrencyId(salesOrderId))
				.forexContracts(forexContractService.getContractsByOrderId(salesOrderId))
				.build();
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final boolean foundAtLeastOneUnprocessedSchedule = context.streamSelectedModels(I_M_ShipmentSchedule.class)
				.anyMatch(sched -> sched.isActive() &&
						!sched.isProcessed() &&
						!bPartnerBlockStatusService.isBPartnerBlocked(BPartnerId.ofRepoId(sched.getC_BPartner_ID())));

		return ProcessPreconditionsResolution.acceptIf(foundAtLeastOneUnprocessedSchedule);
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return p_FECParams.getParameterDefaultValue(parameter.getColumnName(), getContracts());
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		p_FECParams.updateOnParameterChanged(parameterName, getContracts());
	}

	@ProcessParamLookupValuesProvider(parameterName = ForexContractParameters.PARAM_C_ForeignExchangeContract_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	public LookupValuesList getAvailableForexContracts()
	{
		final ForexContracts contracts = getContracts();
		return contracts != null
				? forexContractLookup.findByIdsOrdered(contracts.getForexContractIds())
				: LookupValuesList.EMPTY;
	}

	@Override
	protected String doIt()
	{
		final Result result = new ShipmentScheduleEnqueuer()
				.setContext(getCtx(), getTrxName())
				.createWorkpackages(
						ShipmentScheduleWorkPackageParameters.builder()
								.adPInstanceId(getPinstanceId())
								.queryFilters(getShipmentSchedulesQueryFilters())
								.quantityType(quantityType)
								.completeShipments(isCompleteShipments)
								.isShipmentDateToday(isShipToday)
								.fixedShipmentDate(getFixedShipmentDate().orElse(null))
								.forexContractRef(p_FECParams.getForexContractRef())
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

	@NonNull
	private Optional<LocalDate> getFixedShipmentDate()
	{
		return isShipToday ? Optional.empty() : Optional.ofNullable(shipmentDate);
	}
}
