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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_M_InOut;
import org.compiere.process.DocAction;
import org.compiere.util.Env;

import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.event.InOutProcessedEventBus;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IInOutProducer;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;

/**
 * Class responsible for converting {@link I_M_ReceiptSchedule}s to {@link I_M_InOut} receipts.
 * 
 * @author tsa
 * 
 */
public class InOutProducer implements IInOutProducer
{
	//
	// Services
	// (protected to be accessible for extending classes too)
	protected final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	protected final IInOutBL inoutBL = Services.get(IInOutBL.class);
	protected final IAggregationKeyBuilder<I_M_ReceiptSchedule> headerAggregationKeyBuilder = receiptScheduleBL.getHeaderAggregationKeyBuilder();

	private static final String DYNATTR_HeaderAggregationKey = InOutProducer.class.getName() + "#HeaderAggregationKey";

	private ITrxItemProcessorContext processorCtx;

	private final InOutGenerateResult result;
	private final boolean complete;

	/**
	 * If <code>false</code> (the default), then a new InOut is created with the current date from {@link Env#getDate(Properties)}. Otherwise it is created with the DatePromised value of the receipt
	 * schedule's C_Order. To be used e.g. when doing migration work.
	 * 
	 * @task 08648
	 */
	private final boolean createReceiptWithDatePromised;

	private I_M_InOut _currentReceipt = null;
	private int _currentReceiptLinesCount = 0;
	private I_M_ReceiptSchedule currentReceiptSchedule = null;
	private I_M_ReceiptSchedule previousReceiptSchedule = null;
	private final Set<Integer> _currentOrderIds = new HashSet<Integer>();

	/**
	 * List of {@link Runnable}s to be executed after current receipt is processed
	 */
	private final List<Runnable> afterProcessRunnables = new ArrayList<Runnable>();

	/**
	 * Calls {@link #InOutProducer(InOutGenerateResult, boolean, boolean)} with <code>createReceiptWithDatePromised == false</code>.
	 * 
	 * @param result
	 * @param complete
	 */
	public InOutProducer(final InOutGenerateResult result, final boolean complete)
	{
		this(result, complete, false); // createReceiptWithDatePromised = false
	}

	/**
	 * 
	 * @param result
	 * @param complete
	 * @param createReceiptWithDatePromised if <code>false</code> (the default), then a new InOut is created with the current date from {@link Env#getDate(Properties)}. Otherwise it is created with
	 *            the DatePromised value of the receipt schedule's C_Order. To be used e.g. when doing migration work.
	 */
	public InOutProducer(final InOutGenerateResult result, final boolean complete, final boolean createReceiptWithDatePromised)
	{
		super();

		Check.assumeNotNull(result, "result not null");
		this.result = result;

		this.complete = complete;
		this.createReceiptWithDatePromised = createReceiptWithDatePromised;
	}

	@Override
	public final void setTrxItemProcessorCtx(final ITrxItemProcessorContext processorCtx)
	{
		this.processorCtx = processorCtx;
	}

	@Override
	public final InOutGenerateResult getResult()
	{
		return result;
	}

	protected final Properties getCtx()
	{
		return processorCtx.getCtx();
	}
	
	protected final String getTrxName()
	{
		return processorCtx.getTrxName();
	}

	@Override
	public final void process(final I_M_ReceiptSchedule rs) throws Exception
	{
		Check.assumeNull(currentReceiptSchedule, "currentReceiptSchedule shall be null");
		currentReceiptSchedule = rs;

		final String rsTrxNameOld = InterfaceWrapperHelper.getTrxName(rs);
		try
		{
			//
			// Make sure receipt schedule is in our current transaction.
			// This is important if we want to retrieve objects from it and then we want to change & save them.
			// NOTE: usually the item to process is out of transaction
			InterfaceWrapperHelper.setTrxName(rs, ITrx.TRXNAME_ThreadInherited);

			final List<? extends I_M_InOutLine> lines = createCurrentReceiptLines();
			for (final I_M_InOutLine line : lines)
			{
				receiptScheduleBL.createRsaIfNotExists(rs, line);
			}

			addToCurrentReceiptLines(lines);

			//
			// Collect C_Order_ID
			final int orderId = rs.getC_Order_ID();
			if (orderId > 0)
			{
				_currentOrderIds.add(orderId);
			}
		}
		finally
		{
			//
			// Restore receipt schedule's old transaction
			InterfaceWrapperHelper.setTrxName(rs, rsTrxNameOld);

			//
			// Clear current receipt schedule
			currentReceiptSchedule = null;
		}

		previousReceiptSchedule = rs;
	}

	@Override
	public final boolean isSameChunk(final I_M_ReceiptSchedule rs)
	{
		if (previousReceiptSchedule == null)
		{
			// First receipt schedule to process => ofc it's a new chunk
			return false;
		}

		return !isNewReceiptRequired(previousReceiptSchedule, rs);
	}

	/**
	 * 
	 * @param previousReceiptSchedule
	 * @param receiptSchedule
	 * @return true if given receipt schedules shall not be part of the same receipt
	 */
	// package level because of JUnit tests
	/* package */final boolean isNewReceiptRequired(final I_M_ReceiptSchedule previousReceiptSchedule, final I_M_ReceiptSchedule receiptSchedule)
	{
		Check.assumeNotNull(previousReceiptSchedule, "previousReceiptSchedule not null");
		Check.assumeNotNull(receiptSchedule, "receiptSchedule not null");

		//
		// If previous and current receipt schedule does not have the same header aggregation key,
		// then for sure they shall be on different receipts
		if (!headerAggregationKeyBuilder.isSame(previousReceiptSchedule, receiptSchedule))
		{
			return true;
		}

		//
		// Make sure we are talking about same C_Order because this is defenetelly needed in case of a DropShip (08402)
		// NOTE: the standard HeaderAggregationKeyBuilder is checking for same C_Order_ID but we are doing it here to make it obvious
		// NOTE2: in future maybe we can enforce this rule only if previous or current receipt schedule is about a DropShip order.
		if (previousReceiptSchedule.getC_Order_ID() != receiptSchedule.getC_Order_ID())
		{
			return true;
		}

		//
		// If we reach this point we can safely consider the receipt schedules to be in the same receipt
		return false;
	}

	private final void addToCurrentReceiptLines(final List<? extends I_M_InOutLine> lines)
	{
		Check.assumeNotNull(_currentReceipt, "current receipt not null");

		if (lines == null || lines.isEmpty())
		{
			return;
		}

		_currentReceiptLinesCount += lines.size();
	}

	private final int getCurrentReceiptLinesCount()
	{
		return _currentReceiptLinesCount;
	}

	@Override
	public final void newChunk(final I_M_ReceiptSchedule rs)
	{
		final I_M_InOut receipt = createReceiptHeader(rs);
		setCurrentReceipt(receipt);
	}

	@Override
	public final void completeChunk()
	{
		final List<? extends I_M_InOutLine> bottomReceiptLines = createBottomReceiptLines();
		addToCurrentReceiptLines(bottomReceiptLines);

		//
		// Process current receipt (if it has lines).
		final I_M_InOut receipt = getCurrentReceipt();
		final int currentReceiptLinesCount = getCurrentReceiptLinesCount();
		if (currentReceiptLinesCount > 0)
		{
			processReceipt(receipt);
			result.addInOut(receipt);

			// Notify the user that a new receipt was created (task 09334)
			InOutProcessedEventBus.newInstance()
					.queueEventsUntilTrxCommit(getTrxName())
					.notify(receipt);
		}
		else
		{
			// if the receipt has no lines, make sure we get rid of it
			InterfaceWrapperHelper.delete(receipt);
		}

		//
		// Reset status
		resetCurrentReceipt();
	}

	/**
	 * Create bottom receipt lines, right before completing the receipt.
	 * 
	 * NOTE: at this level this method does nothing but you are free to implement your functionality.
	 * 
	 * @return created lines
	 */
	protected List<? extends I_M_InOutLine> createBottomReceiptLines()
	{
		return Collections.emptyList();
		// nothing
	}

	/**
	 * Sets current receipt on which next lines will be added
	 * 
	 * @param currentReceipt
	 */
	private void setCurrentReceipt(final I_M_InOut currentReceipt)
	{
		Check.assumeNotNull(currentReceipt, "currentReceipt not null");

		resetCurrentReceipt();
		this._currentReceipt = currentReceipt;

		// Make sure the after-process list of runnables is empty
		afterProcessRunnables.clear();
	}

	private void processReceipt(final I_M_InOut currentReceipt)
	{
		// In case we have only one C_Order_ID, set it to Receipt header
		if (_currentOrderIds.size() == 1)
		{
			final int orderId = _currentOrderIds.iterator().next();
			currentReceipt.setC_Order_ID(orderId);
		}

		//
		// Save receipt
		InterfaceWrapperHelper.save(currentReceipt);

		//
		// Shall we also complete it?
		if (!complete)
		{
			return;
		}

		//
		// Process current receipt
		Services.get(IDocActionBL.class).processEx(currentReceipt, DocAction.ACTION_Complete, DocAction.STATUS_Completed);

		//
		// Invoke all after-process runnables
		for (final Runnable runnable : afterProcessRunnables)
		{
			runnable.run();
		}
		afterProcessRunnables.clear();
	}

	private void resetCurrentReceipt()
	{
		_currentReceipt = null;
		_currentReceiptLinesCount = 0;
		_currentOrderIds.clear();
	}

	@Override
	public final void cancelChunk()
	{
		// NOTE: no need to delete current receipt if not null because when this method is called the API is preparing to rollback
		resetCurrentReceipt();
	}

	/**
	 * Gets current receipt.
	 * 
	 * If there is no current receipt, an exception will be thrown.
	 * 
	 * @return current receipt; never return null.
	 */
	protected final I_M_InOut getCurrentReceipt()
	{
		Check.assumeNotNull(_currentReceipt, "currentReceipt not null");
		return _currentReceipt;
	}

	/**
	 * Same as {@link #getCurrentReceipt()} but it will wrapped to given model interface.
	 * 
	 * @param inoutType
	 * @return current receipt; never returns null
	 * @see #getCurrentReceipt()
	 */
	protected final <InOutType extends I_M_InOut> InOutType getCurrentReceipt(final Class<InOutType> inoutType)
	{
		return InterfaceWrapperHelper.create(getCurrentReceipt(), inoutType);
	}

	/**
	 * Gets current HeaderAggregationKey used to aggregate current chunk.
	 * 
	 * @return header aggregation key
	 */
	protected final String getCurrentHeaderAggregationKey()
	{
		final I_M_InOut currentReceipt = getCurrentReceipt();
		final String headerAggregationKey = InterfaceWrapperHelper.getDynAttribute(currentReceipt, DYNATTR_HeaderAggregationKey);
		Check.assumeNotEmpty(headerAggregationKey, "HeaderAggregationKey should be set for {}", currentReceipt);
		return headerAggregationKey;
	}

	/**
	 * Gets current receipt schedule (i.e. the receipt schedule which is currently processing, see {@link #process(I_M_ReceiptSchedule)})
	 * 
	 * @return current receipt schedule; never return null
	 */
	protected final I_M_ReceiptSchedule getCurrentReceiptSchedule()
	{
		Check.assumeNotNull(currentReceiptSchedule, "currentReceiptSchedule not null");
		return currentReceiptSchedule;
	}

	private final I_M_InOut createReceiptHeader(final I_M_ReceiptSchedule rs)
	{
		final Properties ctx = processorCtx.getCtx();
		final String trxName = processorCtx.getTrx().getTrxName();

		final I_M_InOut receiptHeader = InterfaceWrapperHelper.create(ctx, I_M_InOut.class, trxName);
		receiptHeader.setAD_Org_ID(rs.getAD_Org_ID());

		//
		// Document Type
		{
			receiptHeader.setMovementType(X_M_InOut.MOVEMENTTYPE_VendorReceipts);
			receiptHeader.setIsSOTrx(false);

			// this is the doctype of the sched's source record (e.g. "Bestellung")
			// receiptHeader.setC_DocType_ID(rs.getC_DocType_ID());
			final int receiptDocTypeId = Services.get(IDocTypeDAO.class).getDocTypeId(ctx, X_C_DocType.DOCBASETYPE_MaterialReceipt, rs.getAD_Client_ID(), rs.getAD_Org_ID(), trxName);
			receiptHeader.setC_DocType_ID(receiptDocTypeId);
		}

		//
		// BPartner, Location & Contact
		{
			final int bpartnerId = receiptScheduleBL.getC_BPartner_Effective_ID(rs);
			final int bpartnerLocationId = receiptScheduleBL.getC_BPartner_Location_Effective_ID(rs);
			final int bpartnerContactId = receiptScheduleBL.getAD_User_Effective_ID(rs);

			receiptHeader.setC_BPartner_ID(bpartnerId);
			receiptHeader.setC_BPartner_Location_ID(bpartnerLocationId);
			receiptHeader.setAD_User_ID(bpartnerContactId);
		}

		//
		// Document Dates
		{
			receiptHeader.setDateOrdered(rs.getDateOrdered());

			final Timestamp movementDate;
			if (createReceiptWithDatePromised)
			{
				if (rs.getC_Order_ID() > 0) // guarding against NPE, but actually C_Order_ID is always set.
				{
					// task: 08648: see the javadoc of createReceiptWithDatePromised
					movementDate = rs.getC_Order().getDatePromised();
				}
				else
				{
					movementDate = Env.getDate(ctx);
				}
			}
			else
			{
				// Use Login Date as movement date because some roles will rely on the fact that they can override it (08247)
				movementDate = Env.getDate(ctx);
			}
			receiptHeader.setMovementDate(movementDate);
			receiptHeader.setDateAcct(movementDate);
		}

		//
		// Warehouse
		{
			final int warehouseId = receiptScheduleBL.getM_Warehouse_Effective_ID(rs);
			receiptHeader.setM_Warehouse_ID(warehouseId);
		}

		//
		// Set's HeaderAggregationKey
		{
			final String headerAggregationKey = headerAggregationKeyBuilder.buildKey(rs);
			InterfaceWrapperHelper.setDynAttribute(receiptHeader, DYNATTR_HeaderAggregationKey, headerAggregationKey);
		}

		//
		// DropShip informations (08402)
		final I_C_Order order = rs.getC_Order();
		if (order != null && order.isDropShip())
		{
			receiptHeader.setIsDropShip(true);
			receiptHeader.setDropShip_BPartner_ID(order.getDropShip_BPartner_ID());
			receiptHeader.setDropShip_Location_ID(order.getDropShip_Location_ID());
			receiptHeader.setDropShip_User_ID(order.getDropShip_User_ID());
		}
		else
		{
			receiptHeader.setIsDropShip(false);
		}

		//
		// Save & Return
		InterfaceWrapperHelper.save(receiptHeader);
		return receiptHeader;
	}

	/**
	 * Helper method to create a new receipt line, linked to current receipt.
	 * 
	 * @return newly created receipt line
	 */
	protected final I_M_InOutLine newReceiptLine()
	{
		final I_M_InOut receipt = getCurrentReceipt();
		final I_M_InOutLine line = inoutBL.newInOutLine(receipt, I_M_InOutLine.class);
		return line;
	}

	/**
	 * Helper method to update receipt line with values from given {@link I_M_ReceiptSchedule}.
	 * 
	 * @param line
	 * @param rs
	 */
	protected void updateReceiptLine(final I_M_InOutLine line, final I_M_ReceiptSchedule rs)
	{
		final I_M_InOut inout = getCurrentReceipt();

		//
		// Product & ASI
		line.setM_Product_ID(rs.getM_Product_ID());
		final I_M_AttributeSetInstance rsASI = receiptScheduleBL.getM_AttributeSetInstance_Effective(rs);
		if (rsASI == null || rsASI.getM_AttributeSetInstance_ID() == 0)
		{
			line.setM_AttributeSetInstance_ID(IAttributeDAO.M_AttributeSetInstance_ID_None);
		}
		else
		{
			// Do a deep copy of receipt schedule's ASI to prevent changing back to (receipt schedule, order line) in case receipt line's ASI is changed (07317)
			final I_M_AttributeSetInstance asi = Services.get(IAttributeDAO.class).copy(rsASI);
			line.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
		}

		//
		// Line Warehouse & Locator
		{
			final I_M_Warehouse warehouse = inout.getM_Warehouse();
			final I_M_Locator locator = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse);
			line.setM_Locator_ID(locator.getM_Locator_ID());
		}

		//
		// Line Destination Warehouse (where materials are moved automatically after receipt is completed)
		if (inout.isDropShip())
		{
			// In case of drop shipment there shall be no destination warehouse (08402)
			line.setM_Warehouse_Dest(null);
		}
		else
		{
			final int warehouseDestId = rs.getM_Warehouse_Dest_ID();
			line.setM_Warehouse_Dest_ID(warehouseDestId);
		}

		//
		// Quantities
		final BigDecimal qtyToMove = receiptScheduleBL.getQtyToMove(rs);
		line.setQtyEntered(qtyToMove);
		line.setMovementQty(qtyToMove);
		line.setC_UOM_ID(rs.getC_UOM_ID());

		//
		// Order Line Link
		line.setC_OrderLine_ID(rs.getC_OrderLine_ID());
	}

	/**
	 * Create receipt line(s) from current receipt schedule (see {@link #getCurrentReceiptSchedule()}).
	 * 
	 * NOTE: this method can be overriden by extending classes.
	 * 
	 * @return created receipt lines
	 */
	protected List<? extends I_M_InOutLine> createCurrentReceiptLines()
	{
		final I_M_ReceiptSchedule rs = getCurrentReceiptSchedule();

		final I_M_InOutLine line = newReceiptLine();
		updateReceiptLine(line, rs);
		InterfaceWrapperHelper.save(line);

		return Collections.singletonList(line);
	}

	protected final void addAfterProcessRunnable(final Runnable runnable)
	{
		Check.assumeNotNull(runnable, "runnable not null");
		afterProcessRunnables.add(runnable);
	}
}
