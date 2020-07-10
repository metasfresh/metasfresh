package de.metas.invoice.service.impl;

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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.Adempiere;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_C_LandedCost;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.TypedAccessor;

public class PlainInvoiceDAO extends AbstractInvoiceDAO
{
	private final POJOLookupMap db = POJOLookupMap.get();

	public PlainInvoiceDAO()
	{
		Adempiere.assertUnitTestMode();
	}

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

	private List<I_C_AllocationLine> retrieveAllocationLines(final org.compiere.model.I_C_Invoice invoice)
	{
		Adempiere.assertUnitTestMode();

		return db.getRecords(I_C_AllocationLine.class, pojo -> {
			if (pojo == null)
			{
				return false;
			}

			if (pojo.getC_Invoice_ID() != invoice.getC_Invoice_ID())
			{
				return false;
			}

			return true;
		});

	}

	private BigDecimal retrieveAllocatedAmt(final org.compiere.model.I_C_Invoice invoice, final TypedAccessor<BigDecimal> amountAccessor)
	{
		Adempiere.assertUnitTestMode();

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);

		BigDecimal sum = BigDecimal.ZERO;
		for (final I_C_AllocationLine line : retrieveAllocationLines(invoice))
		{
			final I_C_AllocationHdr ah = line.getC_AllocationHdr();
			final BigDecimal lineAmt = amountAccessor.getValue(line);

			if ((null != ah) && (ah.getC_Currency_ID() != invoice.getC_Currency_ID()))
			{
				final BigDecimal lineAmtConv = Services.get(ICurrencyBL.class).convert(
						lineAmt, // Amt
						CurrencyId.ofRepoId(ah.getC_Currency_ID()), // CurFrom_ID
						CurrencyId.ofRepoId(invoice.getC_Currency_ID()), // CurTo_ID
						TimeUtil.asLocalDate(ah.getDateTrx()), // ConvDate
						CurrencyConversionTypeId.ofRepoIdOrNull(invoice.getC_ConversionType_ID()),
						ClientId.ofRepoId(line.getAD_Client_ID()),
						OrgId.ofRepoId(line.getAD_Org_ID()));

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
		Adempiere.assertUnitTestMode();

		return retrieveAllocatedAmt(invoice, o -> {
			final I_C_AllocationLine line = (I_C_AllocationLine)o;
			return line.getWriteOffAmt();
		});
	}

	@Override
	public List<I_C_InvoiceTax> retrieveTaxes(org.compiere.model.I_C_Invoice invoice)
	{
		throw new UnsupportedOperationException();
	}
}
