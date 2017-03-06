package de.metas.ui.web.handlingunits.process;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.context.annotation.Profile;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.WebRestApiApplication;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * Process used to receive HUs for more then one receipt schedule.
 *
 * It creates one VHU for each receipt schedule, using it's remaining quantity to move.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @task https://github.com/metasfresh/metasfresh-webui/issues/182
 *
 */
@Profile(WebRestApiApplication.PROFILE_Webui)
public class WEBUI_M_ReceiptSchedule_GeneratePlanningHUs_MultiRow extends JavaProcess implements IProcessPrecondition
{
	private final transient IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final transient IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize() <= 1)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select more than one row");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final List<I_M_HU> hus = retrieveSelectedRecordsQueryBuilder(I_M_ReceiptSchedule.class)
				.create()
				.stream(I_M_ReceiptSchedule.class)
				.map(this::createPlanningVHU)
				.filter(hu -> hu != null)
				.collect(GuavaCollectors.toImmutableList());

		getResult().setRecordsToOpen(TableRecordReference.ofList(hus));

		return MSG_OK;
	}

	private I_M_HU createPlanningVHU(final I_M_ReceiptSchedule receiptSchedule)
	{
		//
		// Create allocation request for the quantity user entered
		final IAllocationRequest allocationRequest = createAllocationRequest(receiptSchedule);
		if (allocationRequest == null || allocationRequest.isZeroQty())
		{
			return null;
		}

		// task 09717
		// make sure the attributes are initialized in case of multiple row selection, also
		huReceiptScheduleBL.setInitialAttributeValueDefaults(allocationRequest, ImmutableList.of(receiptSchedule));

		//
		// Allocation Source: our receipt schedule
		final IAllocationSource allocationSource = huReceiptScheduleBL.createAllocationSource(receiptSchedule);

		//
		// Allocation Destination: HU producer which will create 1 VHU
		final HUProducerDestination huProducer = new HUProducerDestination(handlingUnitsDAO.retrieveVirtualPI(getCtx()));
		huProducer.setMaxHUsToCreate(1); // we want one VHU

		//
		// Transfer Qty
		final HULoader loader = new HULoader(allocationSource, huProducer);
		loader.setAllowPartialUnloads(false);
		loader.setAllowPartialLoads(false);
		loader.load(allocationRequest);

		//
		// Get created VHU and return it
		final List<I_M_HU> hus = huProducer.getCreatedHUs();
		if (hus == null || hus.size() != 1)
		{
			throw new HUException("One and only one VHU was expected but we got: " + hus);
		}
		final I_M_HU vhu = hus.get(0);
		InterfaceWrapperHelper.setTrxName(vhu, ITrx.TRXNAME_None);
		return vhu;
	}

	private final IAllocationRequest createAllocationRequest(final I_M_ReceiptSchedule rs)
	{
		// Get Qty
		final BigDecimal qty = receiptScheduleBL.getQtyToMove(rs);
		if (qty == null || qty.signum() <= 0)
		{
			// nothing to do
			return null;
		}

		final IMutableHUContext huContextInitial = Services.get(IHUContextFactory.class).createMutableHUContextForProcessing(getCtx());
		final IAllocationRequest allocationRequest = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContextInitial)
				.setDateAsToday()
				.setProduct(rs.getM_Product())
				.setQuantity(new Quantity(qty, rs.getC_UOM()))
				.setFromReferencedModel(rs)
				.setForceQtyAllocation(true)
				.create();

		return allocationRequest;
	}

}
