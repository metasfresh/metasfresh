package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.GenericPO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MDunningRunLine;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.PO;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;

import de.metas.adempiere.model.ILineNetAmtAware;
import de.metas.commission.model.IInstanceTrigger;
import de.metas.commission.model.I_C_AdvComCorrection;
import de.metas.commission.model.I_C_AdvCommissionPayrollLine;
import de.metas.commission.model.I_C_Sponsor_CondLine;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCAdvComCorrection;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.inout.IInOutDAO;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;

public class FieldAccessBL implements IFieldAccessBL
{
	@Override
	public BigDecimal getQty(final Object po)
	{

		BigDecimal qty = null;

		if (InterfaceWrapperHelper.isInstanceOf(po, I_C_OrderLine.class))
		{
			qty = InterfaceWrapperHelper.create(po, I_C_OrderLine.class).getQtyOrdered();
		}
		else if (InterfaceWrapperHelper.isInstanceOf(po, I_C_InvoiceLine.class))
		{

			qty = InterfaceWrapperHelper.create(po, I_C_InvoiceLine.class).getQtyInvoiced();

		}
		else if (InterfaceWrapperHelper.isInstanceOf(po, I_M_InOutLine.class))
		{

			qty = InterfaceWrapperHelper.create(po, I_M_InOutLine.class).getMovementQty();
		}

		return negateIfCreditMemo(po, qty);
	}

	@Override
	public int getCurrencyId(final PO po)
	{

		if (po instanceof I_C_OrderLine)
		{
			return ((I_C_OrderLine)po).getC_Currency_ID();
		}

		if (po instanceof I_C_InvoiceLine)
		{
			return ((I_C_InvoiceLine)po).getC_Invoice().getC_Currency_ID();
		}

		throw new IllegalArgumentException("po is a " + po.getClass());
	}

	@Override
	public int getTaxId(final PO po)
	{

		if (po instanceof org.compiere.model.I_C_OrderLine)
		{
			return ((org.compiere.model.I_C_OrderLine)po).getC_Tax_ID();
		}

		if (po instanceof org.compiere.model.I_C_InvoiceLine)
		{
			return ((org.compiere.model.I_C_InvoiceLine)po).getC_Tax_ID();
		}
		throw new IllegalArgumentException("po is " + po.getClass());
	}

	/**
	 * 
	 * @param po
	 * @param failFast if <code>false</code> and po has none of the listed classes, <code>Updated</code> is returned.
	 * @return depending on po's class, the following values are returned:
	 *         <ul>
	 *         <li>MOrderLine -> DateOrdered</li>
	 *         <li>MInvoiceLine -> DateInvoiced</li>
	 *         <li>MInOutLine -> MovementDate</li>
	 *         <li>MDunningRunLine -> DunningDate</li>
	 *         <li>MAllocationLine -> DateTrx</li>
	 *         </ul>
	 *         As a fallback (if <code>failFast==false</code>), Updated is returned.
	 * @throws IllegalArgumentException if failFast is true and po is none of the abovementioned
	 */
	public Timestamp getDateDoc(final PO po, final boolean failFast)
	{

		if (po instanceof MOrderLine)
		{

			return ((MOrderLine)po).getC_Order().getDateOrdered();

		}
		else if (po instanceof MInvoiceLine)
		{

			return ((MInvoiceLine)po).getC_Invoice().getDateInvoiced();

		}
		else if (po instanceof MInOutLine)
		{

			return ((MInOutLine)po).getM_InOut().getMovementDate();

		}
		else if (po instanceof MDunningRunLine)
		{

			return ((MDunningRunLine)po).getC_DunningRunEntry()
					.getC_DunningRun().getDunningDate();

		}
		else if (po instanceof MAllocationLine)
		{

			return ((MAllocationLine)po).getC_Payment().getDateTrx();
		}

		if (failFast)
		{
			throw new IllegalArgumentException("po is a " + po.getClass());
		}
		return po.getUpdated();
	}

	@Override
	public int getWarehouseId(final PO po)
	{

		if (po instanceof MOrderLine)
		{

			return ((MOrderLine)po).getM_Warehouse_ID();

		}
		else if (po instanceof MInOutLine)
		{

			return ((MInOutLine)po).getM_Warehouse_ID();
		}
		throw new IllegalArgumentException("po is a " + po.getClass());
	}

	@Override
	public int getTaxCategoryId(final PO po)
	{
		final String tableName = po.get_TableName();
		if (I_C_OrderLine.Table_Name.equals(tableName))
		{
			final de.metas.interfaces.I_C_OrderLine orderLine = InterfaceWrapperHelper.create(po, de.metas.interfaces.I_C_OrderLine.class);
			return Services.get(IOrderLineBL.class).getC_TaxCategory_ID(orderLine);
		}

		if (I_M_InOutLine.Table_Name.equals(tableName))
		{
			final I_M_InOutLine inOutLine = InterfaceWrapperHelper.create(po, I_M_InOutLine.class);

			final I_C_OrderLine orderLine = inOutLine.getC_OrderLine();
			final de.metas.interfaces.I_C_OrderLine orderLineNew = InterfaceWrapperHelper.create(orderLine, de.metas.interfaces.I_C_OrderLine.class);

			return Services.get(IOrderLineBL.class).getC_TaxCategory_ID(orderLineNew);
		}

		if (I_C_InvoiceLine.Table_Name.equals(tableName))
		{
			final de.metas.adempiere.model.I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.create(po, de.metas.adempiere.model.I_C_InvoiceLine.class);
			//return Services.get(IInvoiceLineBL.class).getC_TaxCategory_ID(invoiceLine);
			return invoiceLine.getC_TaxCategory_ID();
		}

		if (I_C_AdvCommissionPayrollLine.Table_Name.equals(tableName))
		{
			final I_C_AdvCommissionPayrollLine acpLine = InterfaceWrapperHelper.create(po, I_C_AdvCommissionPayrollLine.class);
			return acpLine.getC_TaxCategory_ID();
		}

		if (I_C_AdvComCorrection.Table_Name.equals(tableName))
		{
			final MCAdvComCorrection corr = (MCAdvComCorrection)po;
			return getTaxCategoryId(corr.retrievePO());
		}

		throw new IllegalArgumentException("Unsupported PO Class: '" + po.getClass() + "'");
	}

	@Override
	public BigDecimal getCommissionPoints(final Object po, final boolean ex)
	{

		final IInstanceTrigger it = getInstanceTrigger(po, ex);
		if (it == null)
		{
			return BigDecimal.ZERO;
		}
		return it.getCommissionPoints();
	}

	@Override
	public BigDecimal getCommissionPointsSum(final PO po, final boolean ex)
	{

		final IInstanceTrigger it = getInstanceTrigger(po, ex);
		if (it == null)
		{
			return BigDecimal.ZERO;
		}
		return negateIfCreditMemo(po, it.getCommissionPointsSum());
	}

	private IInstanceTrigger getInstanceTrigger(final Object po, final boolean ex)
	{
		try
		{
			// note: InterfaceWrapperHelper.isInstanceOf returns always false, unless the given class has a table_name
			return InterfaceWrapperHelper.create(po, IInstanceTrigger.class);
		}
		catch (final IllegalArgumentException e)
		{
			if (ex)
			{
				throw new IllegalArgumentException("po is a " + po.getClass());
			}
			else
			{
				return null;
			}
		}
	}

	@Override
	public BigDecimal getLineNetAmtOrNull(final Object po)
	{
		final ILineNetAmtAware netAmtAware = InterfaceWrapperHelper.create(po, ILineNetAmtAware.class);
		if (netAmtAware == null)
		{
			return null;
		}
		return netAmtAware.getLineNetAmt();
	}

	@Override
	public List<Object> retrieveLines(final Object po, final boolean throwEx)
	{
		final List<Object> lines = new ArrayList<Object>();

		if (InterfaceWrapperHelper.isInstanceOf(po, I_C_Order.class))
		{
			for (final I_C_OrderLine ol : Services.get(IOrderDAO.class).retrieveOrderLines(InterfaceWrapperHelper.create(po, I_C_Order.class)))
			{
				lines.add(ol);
			}
		}
		else if (InterfaceWrapperHelper.isInstanceOf(po, I_C_Invoice.class))
		{
			for (final I_C_InvoiceLine il : Services.get(IInvoiceDAO.class).retrieveLines(InterfaceWrapperHelper.create(po, I_C_Invoice.class)))
			{
				lines.add(il);
			}

		}
		else if (InterfaceWrapperHelper.isInstanceOf(po, I_M_InOut.class))
		{
			for (final I_M_InOutLine iol : Services.get(IInOutDAO.class).retrieveLines(InterfaceWrapperHelper.create(po, I_M_InOut.class)))
			{
				lines.add(iol);
			}
		}
		else
		{
			if (throwEx)
			{
				throw new IllegalArgumentException(po
						+ " must be instanceof MOrder or MInvoice");
			}
			else
			{
				lines.add(po);
			}
		}
		return lines;
	}

	/**
	 * Supports MInvoiceLine, MOrderLine and {@link GenericPO} with C_OrderLine and C_InvoiceLine.
	 */
	@Override
	@Cached
	public Object retrieveHeader(final Object poLine)
	{
		Check.assume(poLine != null, "Param 'poLine' is not null");

		final Object header;
		if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_OrderLine.class))
		{
			header = InterfaceWrapperHelper.create(poLine, I_C_OrderLine.class).getC_Order();
		}
		else if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_InvoiceLine.class))
		{
			header = InterfaceWrapperHelper.create(poLine, I_C_InvoiceLine.class).getC_Invoice();
		}
		else if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_AllocationLine.class))
		{
			header = InterfaceWrapperHelper.create(poLine, I_C_AllocationLine.class).getC_AllocationHdr();
		}
		else if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_AllocationHdr.class))
		{
			header = InterfaceWrapperHelper.create(poLine, I_C_AllocationHdr.class);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_AdvCommissionPayrollLine.class))
		{
			header = InterfaceWrapperHelper.create(poLine, I_C_AdvCommissionPayrollLine.class).getC_AdvCommissionPayroll();
		}
		else if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_Sponsor_SalesRep.class))
		{
			header = InterfaceWrapperHelper.create(poLine, I_C_Sponsor_SalesRep.class);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_Sponsor_CondLine.class))
		{
			header = InterfaceWrapperHelper.create(poLine, I_C_Sponsor_CondLine.class).getC_Sponsor_Cond();
		}
		else if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_AdvComCorrection.class))
		{
			header = InterfaceWrapperHelper.create(poLine, I_C_AdvComCorrection.class);
		}
		else
		{
			throw new IllegalArgumentException(poLine
					+ " must be instanceof MOrderLine, MInvoiceLine, MAllocationLine, MCAdvCommissionPayrollLine, MAllocationHdr, MCSponsorSalesRep or X_C_Sponsor_CondLine");
		}

		return header;
	}

	@Override
	@Cached
	public I_M_Product getProduct(final PO po)
	{

		if (po instanceof MOrderLine)
		{

			return ((MOrderLine)po).getM_Product();

		}
		else if (po instanceof MInvoiceLine)
		{

			return ((MInvoiceLine)po).getM_Product();

		}
		else if (po instanceof MInOutLine)
		{

			return ((MInOutLine)po).getM_Product();
		}

		throw new IllegalArgumentException(po
				+ " must be instanceof MOrderLine, MInOutLine or MInvoiceLine");
	}

	@Override
	public BigDecimal getDiscount(final Object model, final boolean throwEx)
	{
		final BigDecimal priceList = InterfaceWrapperHelper.getValueOrNull(model, I_C_OrderLine.COLUMNNAME_PriceList);
		final BigDecimal priceActual = InterfaceWrapperHelper.getValueOrNull(model, I_C_OrderLine.COLUMNNAME_PriceActual);

		if (priceList == null || priceActual == null)
		{
			if (throwEx)
			{
				throw new IllegalArgumentException(model.toString());
			}
			else
			{
				return null;
			}
		}

		final BigDecimal discountAmt = priceList.subtract(priceActual);

		if (discountAmt.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		Check.assume(priceList.signum() >= 0, "PriceList {} shall be >= 0 for {}", priceList, model);
		// assert priceList.signum() >= 0 : po;

		final BigDecimal discount;
		if (priceList.signum() == 0)
		{
			discount = BigDecimal.ZERO;
		}
		else
		{
			discount = discountAmt
					.setScale(2, RoundingMode.HALF_UP) // important: make sure that the scale is not below 2, because the division result is rounded to this scale
					.divide(priceList, RoundingMode.HALF_UP)
					.multiply(Env.ONEHUNDRED);
		}
		return discount.setScale(2, RoundingMode.HALF_UP);
	}

	@Override
	public String getDocStatus(final PO po, final boolean throwEx)
	{

		if (po instanceof MOrder)
		{

			return ((MOrder)po).getDocStatus();

		}
		else if (po instanceof MInvoice)
		{

			return ((MInvoice)po).getDocStatus();
		}

		if (throwEx)
		{
			throw new IllegalArgumentException(
					"Expecting MOrder or MInvoice. Actual: " + po);
		}

		return null;
	}

	private BigDecimal negateIfCreditMemo(final Object poLine, final BigDecimal amt)
	{
		final boolean isCreditMemo = isCreditMemo(poLine);

		return isCreditMemo ? amt.negate() : amt;
	}

	@Override
	public boolean isCreditMemo(final Object poLine)
	{
		if (!InterfaceWrapperHelper.isInstanceOf(poLine, I_C_InvoiceLine.class))
		{
			return false;
		}

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(poLine, I_C_InvoiceLine.class).getC_Invoice();

		if (!invoice.isSOTrx())
		{
			return false;
		}

		final I_C_DocType docType = invoice.getC_DocType();
		return X_C_DocType.DOCBASETYPE_ARCreditMemo.equals(docType.getDocBaseType());
	}

	@Override
	public boolean isSOTrx(final PO po)
	{
		if (po instanceof MOrder)
		{
			return ((MOrder)po).isSOTrx();
		}
		else if (po instanceof MInvoice)
		{
			return ((MInvoice)po).isSOTrx();
		}
		throw new IllegalArgumentException("Expecting MOrder or MInvoice. Actual: " + po);
	}
}
