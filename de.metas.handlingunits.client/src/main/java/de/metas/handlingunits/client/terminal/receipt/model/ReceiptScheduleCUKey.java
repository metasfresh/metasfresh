package de.metas.handlingunits.client.terminal.receipt.model;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.client.terminal.lutuconfig.model.CUKey;
import de.metas.handlingunits.client.terminal.receiptschedule.model.ReceiptScheduleTableRow;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleQtysBL;

/**
 * CU Key based on a given receipt schedule.
 *
 * @author tsa
 *
 */
public class ReceiptScheduleCUKey extends CUKey
{
	// services
	private final transient IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	// Parameters
	private final ReceiptScheduleTableRow receiptScheduleRow;
	private final I_M_ReceiptSchedule receiptSchedule;
	private final I_C_UOM uom;

	public ReceiptScheduleCUKey(final ITerminalContext terminalContext, final ReceiptScheduleTableRow row)
	{
		super(terminalContext, row.getM_Product());
		receiptScheduleRow = row;
		receiptSchedule = row.getM_ReceiptSchedule();

		final BigDecimal qtyToMove = Services.get(IReceiptScheduleQtysBL.class).getQtyToMove(receiptSchedule);
		setSuggestedQty(qtyToMove.signum() >= 0 ? qtyToMove : BigDecimal.ZERO);

		uom = receiptSchedule.getC_UOM();
	}

	@Override
	protected String createId()
	{
		return de.metas.inoutcandidate.model.I_M_ReceiptSchedule.Table_Name
				+ "#"
				+ String.valueOf(receiptSchedule.getM_ReceiptSchedule_ID());
	}

	@Override
	protected String createName()
	{
		return receiptScheduleRow.getProductNameAndASI();
	}

	/**
	 * Keep in sync with {@code de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator.createAllocationRequest(Quantity)}
	 * @return
	 */
	public I_M_HU createVHU()
	{
		//
		// Create allocation request for the quantity user entered
		final IAllocationRequest allocationRequest = createAllocationRequest();
		if (allocationRequest == null || allocationRequest.isZeroQty())
		{
			return null;
		}
		
		// task 09717
		// make sure the attributes are initialized in case of multiple row selection, also
		final List<I_M_ReceiptSchedule> list = ImmutableList.of(receiptSchedule);
		Services.get(IHUReceiptScheduleBL.class).setInitialAttributeValueDefaults(allocationRequest, list);

		//
		// Allocation Source: our receipt schedule
		final IAllocationSource allocationSource = huReceiptScheduleBL.createAllocationSource(receiptSchedule);

		//
		// Allocation Destination: HU producer which will create 1 VHU
		final ITerminalContext terminalContext = getTerminalContext();
		final Properties ctx = terminalContext.getCtx();
		final HUProducerDestination huProducer = new HUProducerDestination(handlingUnitsDAO.retrieveVirtualPI(ctx));
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

	private final IAllocationRequest createAllocationRequest()
	{
		// Get Qty
		final BigDecimal qty = getSuggestedQty();
		if (qty == null || qty.signum() <= 0)
		{
			// nothing to do
			return null;
		}

		final ITerminalContext terminalContext = getTerminalContext();
		final Properties ctx = terminalContext.getCtx();
		final IMutableHUContext huContextInitial = Services.get(IHUContextFactory.class).createMutableHUContextForProcessing(ctx);
		final IAllocationRequest allocationRequest = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContextInitial)
				.setDateAsToday()
				.setProduct(getM_Product())
				.setQuantity(new Quantity(qty, uom))
				.setFromReferencedModel(receiptSchedule)
				.setForceQtyAllocation(true)
				.create();

		return allocationRequest;
	}
}
