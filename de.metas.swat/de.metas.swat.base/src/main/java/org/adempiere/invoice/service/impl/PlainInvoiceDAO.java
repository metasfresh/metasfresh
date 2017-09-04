package org.adempiere.invoice.service.impl;

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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.TypedAccessor;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_C_LandedCost;
import org.compiere.model.MInvoiceLine;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.currency.ICurrencyBL;

public class PlainInvoiceDAO extends AbstractInvoiceDAO
{
	private final POJOLookupMap db = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return db;
	}

	@Override
	public I_C_InvoiceLine createInvoiceLine(org.compiere.model.I_C_Invoice invoice)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<I_C_LandedCost> retrieveLandedCosts(I_C_InvoiceLine invoiceLine, String whereClause, String trxName)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public I_C_LandedCost createLandedCost(String trxName)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public I_C_InvoiceLine createInvoiceLine(String trxName)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<MInvoiceLine> retrieveReferringLines(Properties ctx, int invoiceLineId, String trxName)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<I_C_Invoice> retrieveOpenInvoicesByOrg(final I_AD_Org adOrg)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(adOrg);

		final List<I_C_Invoice> result = db.getRecords(I_C_Invoice.class, new IQueryFilter<I_C_Invoice>()
		{

			@Override
			public boolean accept(I_C_Invoice pojo)
			{
				if (adOrg.getAD_Org_ID() <= 0)
				{
					return false;
				}

				if (pojo.getAD_Org_ID() != adOrg.getAD_Org_ID())
				{
					return false;
				}

				if (pojo.isPaid())
				{
					return false;
				}

				if (pojo.getAD_Client_ID() != 0 && pojo.getAD_Client_ID() != Env.getAD_Client_ID(ctx))
				{
					return false;
				}

				if (!pojo.isActive())
				{
					return false;
				}

				return true;
			}

		});

		return result.iterator();
	}

	private List<I_C_AllocationLine> retrieveAllocationLines(final org.compiere.model.I_C_Invoice invoice)
	{
		return db.getRecords(I_C_AllocationLine.class, new IQueryFilter<I_C_AllocationLine>()
		{
			@Override
			public boolean accept(I_C_AllocationLine pojo)
			{
				if (pojo == null)
				{
					return false;
				}

				if (pojo.getC_Invoice_ID() != invoice.getC_Invoice_ID())
				{
					return false;
				}

				return true;
			}
		});

	}

	private BigDecimal retrieveAllocatedAmt(final org.compiere.model.I_C_Invoice invoice, final TypedAccessor<BigDecimal> amountAccessor)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);

		BigDecimal sum = BigDecimal.ZERO;
		for (final I_C_AllocationLine line : retrieveAllocationLines(invoice))
		{
			final I_C_AllocationHdr ah = line.getC_AllocationHdr();
			final BigDecimal lineAmt = amountAccessor.getValue(line);

			if ((null != ah) && (ah.getC_Currency_ID() != invoice.getC_Currency_ID()))
			{
				final BigDecimal lineAmtConv = Services.get(ICurrencyBL.class).convert(ctx,
						lineAmt, // Amt
						ah.getC_Currency_ID(), // CurFrom_ID
						invoice.getC_Currency_ID(), // CurTo_ID
						ah.getDateTrx(), // ConvDate
						invoice.getC_ConversionType_ID(),
						line.getAD_Client_ID(), line.getAD_Org_ID());

				sum = sum.add(lineAmtConv);
			}
			else
			{
				sum = sum.add(lineAmt);
			}
		}

		return sum;
	}

	public BigDecimal retrieveWriteOffAmt(final org.compiere.model.I_C_Invoice invoice)
	{
		return retrieveAllocatedAmt(invoice, new TypedAccessor<BigDecimal>()
		{

			@Override
			public BigDecimal getValue(Object o)
			{
				final I_C_AllocationLine line = (I_C_AllocationLine)o;
				return line.getWriteOffAmt();
			}
		});
	}

	@Override
	public List<I_C_InvoiceTax> retrieveTaxes(org.compiere.model.I_C_Invoice invoice)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public I_C_Invoice retrieveInvoiceByInvoiceNoAndBPartnerID(final Properties ctx, final String invoiceNo, final int bPartnerID)
	{
		return db.getFirstOnly(I_C_Invoice.class, new IQueryFilter<I_C_Invoice>()
		{

			@Override
			public boolean accept(I_C_Invoice pojo)
			{
				if (bPartnerID <= 0)
				{
					return false;
				}

				if (invoiceNo == null)
				{
					return false;
				}

				if (pojo.getDocumentNo() != invoiceNo)
				{
					return false;
				}

				if (pojo.getC_BPartner_ID() != bPartnerID)
				{
					return false;
				}

				if (pojo.getAD_Client_ID() != 0 && pojo.getAD_Client_ID() != Env.getAD_Client_ID(ctx))
				{
					return false;
				}

				return true;
			}

		});
	}
}
