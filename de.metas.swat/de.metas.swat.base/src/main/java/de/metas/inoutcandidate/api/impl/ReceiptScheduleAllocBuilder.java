package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Optional;

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

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IReceiptScheduleAllocBuilder;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Check;
import lombok.NonNull;

public class ReceiptScheduleAllocBuilder implements IReceiptScheduleAllocBuilder
{
	private I_M_ReceiptSchedule _receiptSchedule;
	private I_M_InOutLine _receiptLine;
	private StockQtyAndUOMQty _qtyToAllocate;
	private StockQtyAndUOMQty _qtyWithIssues;
	private IContextAware _context;

	protected ReceiptScheduleAllocBuilder()
	{
	}

	@Override
	public I_M_ReceiptSchedule_Alloc buildAndSave()
	{
		final I_M_ReceiptSchedule_Alloc rsa = build();
		InterfaceWrapperHelper.save(rsa);
		return rsa;
	}

	@Override
	public I_M_ReceiptSchedule_Alloc build()
	{
		final I_M_ReceiptSchedule_Alloc rsa = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule_Alloc.class, getContext());
		build(rsa);
		return rsa;
	}

	@OverridingMethodsMustInvokeSuper
	protected void build(final I_M_ReceiptSchedule_Alloc rsa)
	{
		//
		// Mandatory fields
		final I_M_ReceiptSchedule receiptSchedule = getM_ReceiptSchedule();
		rsa.setIsActive(true);
		rsa.setAD_Org_ID(receiptSchedule.getAD_Org_ID());
		rsa.setM_ReceiptSchedule(receiptSchedule);

		//
		// Quantities
		final StockQtyAndUOMQty qtyToAllocate = getQtyToAllocate();
		final StockQtyAndUOMQty qtyWithIssues = getQtyWithIssues();

		final ProductId receiptProductId = ProductId.ofRepoId(receiptSchedule.getM_Product_ID());
		Check.assume(receiptProductId.equals(qtyToAllocate.getProductId()), "ReceiptSchedules productId={}, needs to match qtyToAllocate={}", receiptProductId.getRepoId(), qtyToAllocate);
		Check.assume(receiptProductId.equals(qtyToAllocate.getProductId()), "ReceiptSchedules productId={}, needs to match qtyWithIssues={}", receiptProductId.getRepoId(), qtyWithIssues);

		rsa.setQtyAllocated(qtyToAllocate.getStockQty().toBigDecimal());
		rsa.setQtyWithIssues(qtyWithIssues.getStockQty().toBigDecimal());

		final Optional<Quantity> catchQty = qtyToAllocate.getUOMQtyOpt();
		if (catchQty.isPresent())
		{
			rsa.setQtyAllocatedInCatchUOM(catchQty.get().toBigDecimal());
			rsa.setCatch_UOM_ID(catchQty.get().getUomId().getRepoId());
		}

		//
		// Get and validate receipt line if any
		final I_M_InOutLine receiptLine = getM_InOutLine();
		rsa.setM_InOutLine(receiptLine);
		// rsa.setM_InOut_ID(receiptLine.getM_InOut_ID()); // virtual column
		if (receiptLine != null)
		{
			//
			// Make sure receipt schedule and receipt line have same products
			if (receiptSchedule.getM_Product_ID() != receiptLine.getM_Product_ID())
			{
				throw new AdempiereException("Receipt schedule and receipt line have different products."
						+ "\nReceipt Line: " + receiptLine
						+ "\nReceipt Line M_Product_ID: " + receiptLine.getM_Product_ID()
						+ "\nReceipt Schedule: " + receiptSchedule
						+ "\nReceipt Schedule Product: " + loadOutOfTrx(receiptSchedule.getM_Product_ID(), I_M_Product.class));
			}

			//
			// Make sure receipt schedule and receipt line have same UOMs
			if (receiptSchedule.getC_UOM_ID() != receiptLine.getC_UOM_ID())
			{
				throw new AdempiereException("Different UOMs on receipt schedule and receipt line is not supported."
						+ "\nReceipt Schedule: " + receiptSchedule
						+ "\nReceipt Schedule UOM: " + loadOutOfTrx(receiptSchedule.getC_UOM_ID(), I_C_UOM.class)
						+ "\nReceipt Line: " + receiptLine
						+ "\nReceipt Line C_UOM_ID: " + receiptLine.getC_UOM_ID());
			}
		}
	}

	@Override
	public ReceiptScheduleAllocBuilder setContext(@NonNull final IContextAware context)
	{
		this._context = context;
		return this;
	}

	private final IContextAware getContext()
	{
		Check.assumeNotNull(_context, "_context not null");
		return _context;
	}

	protected I_M_ReceiptSchedule getM_ReceiptSchedule()
	{
		Check.assumeNotNull(_receiptSchedule, "receiptSchedule not null");
		return _receiptSchedule;
	}

	@Override
	public ReceiptScheduleAllocBuilder setM_ReceiptSchedule(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		this._receiptSchedule = receiptSchedule;
		return this;
	}

	private I_M_InOutLine getM_InOutLine()
	{
		return _receiptLine;
	}

	@Override
	public ReceiptScheduleAllocBuilder setM_InOutLine(@NonNull final I_M_InOutLine receiptLine)
	{
		this._receiptLine = receiptLine;
		return this;
	}

	private StockQtyAndUOMQty getQtyToAllocate()
	{
		Check.assumeNotNull(_qtyToAllocate, "qtyToAllocate not null");
		return _qtyToAllocate;
	}

	@Override
	public ReceiptScheduleAllocBuilder setQtyToAllocate(@NonNull final StockQtyAndUOMQty qtyToAllocate)
	{
		this._qtyToAllocate = qtyToAllocate;
		return this;
	}

	@Override
	public ReceiptScheduleAllocBuilder setQtyWithIssues(@NonNull final StockQtyAndUOMQty qtyWithIssues)
	{
		this._qtyWithIssues = qtyWithIssues;
		return this;
	}

	private StockQtyAndUOMQty getQtyWithIssues()
	{
		if (_qtyWithIssues == null)
		{
			return _qtyToAllocate.toZero();
		}
		return _qtyWithIssues;
	}
}
