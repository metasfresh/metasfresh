package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.document.IDocumentLocationBL;
import de.metas.document.model.IDocumentLocation;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IInOutProducer;
import de.metas.inoutcandidate.api.IReceiptScheduleAllocBuilder;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IReceiptScheduleQtysBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.inoutcandidate.spi.IReceiptScheduleListener;
import de.metas.inoutcandidate.spi.impl.CompositeReceiptScheduleListener;
import de.metas.interfaces.I_C_BPartner;

public class ReceiptScheduleBL implements IReceiptScheduleBL
{
	private final CompositeReceiptScheduleListener listeners = new CompositeReceiptScheduleListener();
	private final IAggregationKeyBuilder<I_M_ReceiptSchedule> headerAggregationKeyBuilder = new ReceiptScheduleHeaderAggregationKeyBuilder();

	@Override
	public void addReceiptScheduleListener(IReceiptScheduleListener listener)
	{
		listeners.addReceiptScheduleListener(listener);
	}

	@Override
	public IAggregationKeyBuilder<I_M_ReceiptSchedule> getHeaderAggregationKeyBuilder()
	{
		return headerAggregationKeyBuilder;
	}

	@Override
	public int getM_Warehouse_Effective_ID(final I_M_ReceiptSchedule rs)
	{
		final int warehouseId = rs.getM_Warehouse_Override_ID();
		if (warehouseId > 0)
		{
			return warehouseId;
		}

		return rs.getM_Warehouse_ID();
	}

	@Override
	public I_M_Warehouse getM_Warehouse_Effective(final I_M_ReceiptSchedule rs)
	{
		if (rs.getM_Warehouse_Override_ID() > 0)
		{
			return rs.getM_Warehouse_Override();
		}
		return rs.getM_Warehouse();
	}

	@Override
	public I_M_Locator getM_Locator_Effective(final I_M_ReceiptSchedule rs)
	{
		final I_M_Warehouse warehouse = getM_Warehouse_Effective(rs);
		return Services.get(IWarehouseBL.class).getDefaultLocator(warehouse);
	}

	@Override
	public int getM_AttributeSetInstance_Effective_ID(final I_M_ReceiptSchedule rs)
	{
		// TODO: introduce M_AttributeSetInstance_Override_ID
		return rs.getM_AttributeSetInstance_ID();
	}

	@Override
	public I_M_AttributeSetInstance getM_AttributeSetInstance_Effective(final I_M_ReceiptSchedule rs)
	{
		// TODO: introduce M_AttributeSetInstance_Override_ID
		return rs.getM_AttributeSetInstance();
	}

	@Override
	public void setM_AttributeSetInstance_Effective(final I_M_ReceiptSchedule rs, final I_M_AttributeSetInstance asi)
	{
		// TODO: introduce M_AttributeSetInstance_Override_ID
		rs.setM_AttributeSetInstance(asi);
	}

	@Override
	public BigDecimal getQtyOrdered(final I_M_ReceiptSchedule rs)
	{
		return Services.get(IReceiptScheduleQtysBL.class).getQtyOrdered(rs);
	}

	@Override
	public BigDecimal getQtyMoved(final I_M_ReceiptSchedule rs)
	{
		return Services.get(IReceiptScheduleQtysBL.class).getQtyMoved(rs);
	}

	@Override
	public BigDecimal getQtyMovedWithIssues(final I_M_ReceiptSchedule rs)
	{
		return Services.get(IReceiptScheduleQtysBL.class).getQtyMovedWithIssues(rs);
	}

	@Override
	public BigDecimal getQtyToMove(final I_M_ReceiptSchedule rs)
	{
		return Services.get(IReceiptScheduleQtysBL.class).getQtyToMove(rs);
	}

	/**
	 * Same as {@link #getQtyToMove(I_M_ReceiptSchedule)} but return the quantity in required UOM.
	 *
	 * @param rs
	 * @param uom
	 * @return qty to move (in <code>uom</code>).
	 */
	public BigDecimal getQtyToMove(final I_M_ReceiptSchedule rs, final I_C_UOM uom)
	{
		final BigDecimal qtyToMove = getQtyToMove(rs);
		final BigDecimal qtyToMoveConv = Services.get(IUOMConversionBL.class)
				.convertQty(rs.getM_Product(), qtyToMove, rs.getC_UOM(), uom);
		return qtyToMoveConv;
	}

	@Override
	public int getC_BPartner_Location_Effective_ID(final I_M_ReceiptSchedule rs)
	{
		final int bpLocationOverrideId = rs.getC_BP_Location_Override_ID();
		if (bpLocationOverrideId > 0)
		{
			return bpLocationOverrideId;
		}

		return rs.getC_BPartner_Location_ID();
	}

	@Override
	public I_C_BPartner_Location getC_BPartner_Location_Effective(final I_M_ReceiptSchedule sched)
	{
		final I_C_BPartner_Location location = InterfaceWrapperHelper.create(
				sched.getC_BP_Location_Override_ID() <= 0 ? sched.getC_BPartner_Location() : sched.getC_BP_Location_Override(),
				I_C_BPartner_Location.class);
		return location;
	}

	@Override
	public int getC_BPartner_Effective_ID(final I_M_ReceiptSchedule rs)
	{
		final int bPartnerOverrideId = rs.getC_BPartner_Override_ID();
		if (bPartnerOverrideId > 0)
		{
			return bPartnerOverrideId;
		}

		return rs.getC_BPartner_ID();
	}

	@Override
	public I_C_BPartner getC_BPartner_Effective(final I_M_ReceiptSchedule sched)
	{
		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(
				sched.getC_BPartner_Override_ID() <= 0 ? sched.getC_BPartner() : sched.getC_BPartner_Override(),
				I_C_BPartner.class);
		return bPartner;
	}

	@Override
	public int getAD_User_Effective_ID(final I_M_ReceiptSchedule rs)
	{
		final int adUserOverrideID = rs.getAD_User_Override_ID();
		if (adUserOverrideID > 0)
		{
			return adUserOverrideID;
		}

		return rs.getAD_User_ID();
	}

	@Override
	public I_AD_User getAD_User_Effective(final I_M_ReceiptSchedule rs)
	{
		final I_AD_User user = InterfaceWrapperHelper.create(
				rs.getAD_User_Override_ID() <= 0 ? rs.getAD_User() : rs.getAD_User_Override(),
				I_AD_User.class);
		return user;
	}

	private IDocumentLocation asDocumentLocation(final I_M_ReceiptSchedule receiptSchedule)
	{
		return new IDocumentLocation()
		{

			@Override
			public void setBPartnerAddress(final String address)
			{
				receiptSchedule.setBPartnerAddress(address);
			}

			@Override
			public int getC_BPartner_Location_ID()
			{
				return receiptSchedule.getC_BPartner_Location_ID();
			}

			@Override
			public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location()
			{
				return receiptSchedule.getC_BPartner_Location();
			}

			@Override
			public int getC_BPartner_ID()
			{
				return receiptSchedule.getC_BPartner_ID();
			}

			@Override
			public org.compiere.model.I_C_BPartner getC_BPartner()
			{
				return receiptSchedule.getC_BPartner();
			}

			@Override
			public String getBPartnerAddress()
			{
				return receiptSchedule.getBPartnerAddress();
			}

			@Override
			public int getAD_User_ID()
			{
				return receiptSchedule.getAD_User_ID();
			}

			@Override
			public org.compiere.model.I_AD_User getAD_User()
			{
				return receiptSchedule.getAD_User();
			}
		};
	}

	private IDocumentLocation asDocumentLocationEffective(final I_M_ReceiptSchedule receiptSchedule)
	{
		return new IDocumentLocation()
		{

			@Override
			public void setBPartnerAddress(final String address)
			{
				receiptSchedule.setBPartnerAddress_Override(address);
			}

			@Override
			public int getC_BPartner_Location_ID()
			{
				return getC_BPartner_Location_Effective_ID(receiptSchedule);
			}

			@Override
			public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location()
			{
				return getC_BPartner_Location_Effective(receiptSchedule);
			}

			@Override
			public int getC_BPartner_ID()
			{
				return getC_BPartner_Effective_ID(receiptSchedule);
			}

			@Override
			public org.compiere.model.I_C_BPartner getC_BPartner()
			{
				return getC_BPartner_Effective(receiptSchedule);
			}

			@Override
			public String getBPartnerAddress()
			{
				return receiptSchedule.getBPartnerAddress_Override();
			}

			@Override
			public int getAD_User_ID()
			{
				return getAD_User_Effective_ID(receiptSchedule);
			}

			@Override
			public org.compiere.model.I_AD_User getAD_User()
			{
				return getAD_User_Effective(receiptSchedule);
			}
		};
	}

	@Override
	public void updateBPartnerAddress(final I_M_ReceiptSchedule receiptSchedule)
	{
		final IDocumentLocation documentLocation = asDocumentLocation(receiptSchedule);
		Services.get(IDocumentLocationBL.class).setBPartnerAddress(documentLocation);
	}

	@Override
	public void updateBPartnerAddressOverride(final I_M_ReceiptSchedule receiptSchedule)
	{
		final IDocumentLocation documentLocation = asDocumentLocationEffective(receiptSchedule);
		Services.get(IDocumentLocationBL.class).setBPartnerAddress(documentLocation);
	}

	@Override
	public void generateInOuts(final Properties ctx, final Iterator<I_M_ReceiptSchedule> receiptSchedules, final InOutGenerateResult result, final boolean complete)
	{
		final IInOutProducer processor = createInOutProducer(result, complete);

		generateInOuts(ctx, processor, receiptSchedules);
	}

	@Override
	public void generateInOuts(final Properties ctx,
			final IInOutProducer producer,
			final Iterator<I_M_ReceiptSchedule> receiptSchedules)
	{
		final ITrxItemProcessorExecutorService executorService = Services.get(ITrxItemProcessorExecutorService.class);
		final ITrxItemProcessorContext processorCtx = executorService.createProcessorContext(ctx, null);
		final ITrxItemProcessorExecutor<I_M_ReceiptSchedule, InOutGenerateResult> executor = executorService.createExecutor(processorCtx, producer);

		executor.execute(receiptSchedules);
	}

	@Override
	public IInOutProducer createInOutProducer(final InOutGenerateResult resultInitial, final boolean complete)
	{
		final InOutProducer processor = new InOutProducer(resultInitial, complete);
		return processor;
	}

	@Override
	public I_M_ReceiptSchedule_Alloc createRsaIfNotExists(
			final I_M_ReceiptSchedule receiptSchedule,
			final I_M_InOutLine receiptLine)

	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(receiptSchedule);
		Check.assume(Env.getAD_Client_ID(ctx) == receiptSchedule.getAD_Client_ID(), "AD_Client_ID of " + receiptSchedule + " and of its CTX are the same");

		final I_M_ReceiptSchedule_Alloc existingRsa = Services.get(IReceiptScheduleDAO.class).retrieveRsaForRs(receiptSchedule, receiptLine);
		if (existingRsa != null)
		{
			return existingRsa;// nothing to do
		}

		final BigDecimal qtyToAllocate = receiptLine.getMovementQty(); // UOM=Product's UOM

		return createReceiptScheduleAlloc(receiptSchedule, receiptLine, qtyToAllocate);
	}

	@Override
	public IReceiptScheduleAllocBuilder createReceiptScheduleAlloc()
	{
		return new ReceiptScheduleAllocBuilder();
	}

	/**
	 *
	 * @param receiptSchedule
	 * @param receiptLine
	 * @param qtyToAllocate quantity to allocate (in {@link I_M_ReceiptSchedule}'s UOM)
	 * @return receipt schedule allocation; never return null
	 */
	private final I_M_ReceiptSchedule_Alloc createReceiptScheduleAlloc(
			final I_M_ReceiptSchedule receiptSchedule,
			final I_M_InOutLine receiptLine,
			final BigDecimal qtyToAllocate)
	{
		final IContextAware context = InterfaceWrapperHelper.getContextAware(receiptLine);
		// Determine QtyWithIssues based on receipt line's IsInDispute flag.
		final BigDecimal qtyWithIssues = receiptLine.isInDispute() ? qtyToAllocate : BigDecimal.ZERO;

		return createReceiptScheduleAlloc()
				.setContext(context)
				.setM_ReceiptSchedule(receiptSchedule)
				.setM_InOutLine(receiptLine)
				.setQtyToAllocate(qtyToAllocate)
				.setQtyWithIssues(qtyWithIssues)
				.buildAndSave();
		// //
		// // Make sure receipt schedule and receipt line have same products
		// if (receiptSchedule.getM_Product_ID() != receiptLine.getM_Product_ID())
		// {
		// throw new AdempiereException("Receipt schedule and receipt line have different products."
		// + "\nReceipt Line: " + receiptLine
		// + "\nReceipt Line Product: " + receiptLine.getM_Product()
		// + "\nReceipt Schedule: " + receiptSchedule
		// + "\nReceipt Schedule Product: " + receiptSchedule.getM_Product());
		// }
		//
		// //
		// // Make sure receipt schedule and receipt line have same UOMs
		// if (receiptSchedule.getC_UOM_ID() != receiptLine.getC_UOM_ID())
		// {
		// throw new AdempiereException("Different UOMs on receipt schedule and receipt line is not supported."
		// + "\nReceipt Schedule: " + receiptSchedule
		// + "\nReceipt Schedule UOM: " + receiptSchedule.getC_UOM()
		// + "\nReceipt Line: " + receiptLine
		// + "\nReceipt Line UOM: " + receiptLine.getC_UOM());
		// }
		//
		// final BigDecimal qtyWithIssues = receiptLine.isInDispute() ? qtyToAllocate : BigDecimal.ZERO;
		//
		// final I_M_ReceiptSchedule_Alloc rsa = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule_Alloc.class, receiptLine);
		// rsa.setAD_Org_ID(receiptSchedule.getAD_Org_ID());
		// rsa.setM_ReceiptSchedule(receiptSchedule);
		// // newRsa.setM_InOut_ID(receiptLine.getM_InOut_ID()); // virtual column
		// rsa.setM_InOutLine(receiptLine);
		// rsa.setQtyAllocated(qtyToAllocate);
		// rsa.setQtyWithIssues(qtyWithIssues);
		//
		// InterfaceWrapperHelper.save(rsa);
		// return rsa;
	}

	@Override
	public List<I_M_ReceiptSchedule_Alloc> createReceiptScheduleAllocations(
			final List<? extends I_M_ReceiptSchedule> receiptSchedules,
			final I_M_InOutLine receiptLine)

	{
		Check.assumeNotNull(receiptLine, "receipt line not null");
		Check.assumeNotEmpty(receiptSchedules, "receipt schedules not empty");
		BigDecimal qtyToAllocateRemaining = receiptLine.getQtyEntered();
		if (qtyToAllocateRemaining.signum() == 0)
		{
			// Receipt Line with ZERO qty???
			// could be, but we will skip the allocations because there is nothing to allocate
			return Collections.emptyList();
		}

		final I_C_UOM qtyToAllocateUOM = receiptLine.getC_UOM();

		//
		// Iterate receipt schedules and try to allocate on them as much as possible
		final List<I_M_ReceiptSchedule_Alloc> allocs = new ArrayList<I_M_ReceiptSchedule_Alloc>();
		final List<Integer> orderLineIds = new ArrayList<Integer>();
		I_M_ReceiptSchedule lastReceiptSchedule = null;
		for (final I_M_ReceiptSchedule rs : receiptSchedules)
		{
			// Do we still have something to allocate?
			if (qtyToAllocateRemaining.signum() == 0)
			{
				break;
			}

			//
			// Calculate how much we can allocate on current receipt schedule
			// i.e. try Remaining Qty To Allocate, but not more then how much is open on this receipt schedule
			final BigDecimal rsQtyOpen = getQtyToMove(rs, qtyToAllocateUOM); // how much we can maxium allocate on this receipt schedule
			final BigDecimal rsQtyToAllocate = qtyToAllocateRemaining.min(rsQtyOpen);
			if (rsQtyToAllocate.signum() == 0)
			{
				//
				// Remember our last receipt schedule
				// This will be needed in case of over delivery
				lastReceiptSchedule = rs;

				// nothing to allocate on this receipt schedule
				continue;
			}

			//
			// Create allocation
			final I_M_ReceiptSchedule_Alloc rsa = createReceiptScheduleAlloc(rs, receiptLine, rsQtyToAllocate);
			allocs.add(rsa);

			//
			// Update Remaining Qty To Allocate
			qtyToAllocateRemaining = qtyToAllocateRemaining.subtract(rsQtyToAllocate);

			//
			// Remember receipt schedule's order line
			final int orderLineId = rs.getC_OrderLine_ID();
			if (orderLineId > 0 && !orderLineIds.contains(orderLineId))
			{
				orderLineIds.add(orderLineId);
			}

			//
			// Remember our last receipt schedule
			lastReceiptSchedule = rs;
		}

		//
		// Case: after iterating all receipt schedules it seems we could not allocate everything
		// => we allocate remaining qty to last receipt scheduler, no matter if open qty is smaller (over delivery)
		if (qtyToAllocateRemaining.signum() != 0)
		{
			final I_M_ReceiptSchedule_Alloc rsa = createReceiptScheduleAlloc(lastReceiptSchedule, receiptLine, qtyToAllocateRemaining);
			allocs.add(rsa);
		}

		//
		// Update Receipt Line's link to Order Line
		// (only if there was one and only one order line involved)
		if (orderLineIds.size() == 1)
		{
			receiptLine.setC_OrderLine_ID(orderLineIds.get(0));
		}

		//
		// Return created allocations
		return allocs;
	}

	@Override
	public void updatePreparationTime(I_M_ReceiptSchedule receiptSchedule)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(receiptSchedule.getC_Order(), I_C_Order.class);
		if (order != null)
		{
			final de.metas.tourplanning.model.I_M_ReceiptSchedule rSched = InterfaceWrapperHelper.create(receiptSchedule, de.metas.tourplanning.model.I_M_ReceiptSchedule.class);
			rSched.setPreparationTime(order.getPreparationDate());
		}
	}

	@Override
	public I_M_ReceiptSchedule_Alloc reverseAllocation(final I_M_ReceiptSchedule_Alloc rsa)
	{
		Check.assumeNotNull(rsa, "rsa not null");

		final I_M_ReceiptSchedule_Alloc reversal = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule_Alloc.class, rsa);

		InterfaceWrapperHelper.copyValues(rsa, reversal);
		reversal.setM_ReceiptSchedule_ID(rsa.getM_ReceiptSchedule_ID());
		reversal.setAD_Org_ID(rsa.getAD_Org_ID());
		reversal.setIsActive(rsa.isActive());

		reversal.setQtyAllocated(rsa.getQtyAllocated().negate());

		return reversal;
	}

	@Override
	public void close(final I_M_ReceiptSchedule rs)
	{
		Check.assumeNotNull(rs, "rs not null");

		// Make sure receipt schedule was not already processed
		if (isClosed(rs))
		{
			throw new AdempiereException("@Closed@=@Y@ (" + rs + ")");
		}

		listeners.onBeforeClose(rs);

		// Mark the receipt schedule as closed (i.e. processed)
		rs.setProcessed(true);
		InterfaceWrapperHelper.save(rs);

		// this is already called by a model validator when the receipt schedule is saved
		// Services.get(IReceiptScheduleQtysBL.class).onReceiptScheduleChanged(receiptSchedule);

		listeners.onAfterClose(rs);
		InterfaceWrapperHelper.save(rs); // see javadoc on why we same two times
	}

	@Override
	public void reopen(final I_M_ReceiptSchedule receiptSchedule)
	{
		Check.assumeNotNull(receiptSchedule, "rs not null");

		//
		// Make sure receipt schedule is closed/processed
		if (!isClosed(receiptSchedule))
		{
			throw new AdempiereException("@Closed@=@N@ (" + receiptSchedule + ")");
		}

		listeners.onBeforeReopen(receiptSchedule);
		InterfaceWrapperHelper.refresh(receiptSchedule); // because

		// Mark the receipt schedule as not closed (i.e. not processed)
		receiptSchedule.setProcessed(false);
		InterfaceWrapperHelper.save(receiptSchedule);

		// this is already called by a model validator when the receipt schedule is saved
		// Services.get(IReceiptScheduleQtysBL.class).onReceiptScheduleChanged(receiptSchedule);

		listeners.onAfterReopen(receiptSchedule);
		InterfaceWrapperHelper.save(receiptSchedule);
	}

	@Override
	public boolean isClosed(final I_M_ReceiptSchedule receiptSchedule)
	{
		return receiptSchedule.isProcessed();
	}
}
