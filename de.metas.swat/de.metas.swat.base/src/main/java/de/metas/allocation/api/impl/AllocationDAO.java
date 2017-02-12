package de.metas.allocation.api.impl;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.X_C_Payment;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.allocation.api.IAllocationDAO;

public class AllocationDAO implements IAllocationDAO
{

	@Override
	public final BigDecimal retrieveOpenAmt(final org.compiere.model.I_C_Invoice invoice, final boolean creditMemoAdjusted)
	{
		if (invoice.isPaid())
		{
			return BigDecimal.ZERO;
		}

		final BigDecimal openAmt;
		final BigDecimal allocated = retrieveAllocatedAmt(invoice);
		if (allocated != null)
		{
			// subtracting the absolute allocated amount
			openAmt = invoice.getGrandTotal().subtract(allocated.abs());
		}
		else
		{
			openAmt = invoice.getGrandTotal();
		}

		if (creditMemoAdjusted && Services.get(IInvoiceBL.class).isCreditMemo(invoice))
		{
			return openAmt.negate();
		}
		return openAmt;
	}

	@Override
	public final List<I_C_AllocationLine> retrieveAllocationLines(final org.compiere.model.I_C_Invoice invoice)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_AllocationLine.class, invoice)
				.addEqualsFilter(I_C_AllocationLine.COLUMN_C_Invoice_ID, invoice.getC_Invoice_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy()
				.addColumn(I_C_AllocationLine.COLUMN_C_AllocationLine_ID)
				.endOrderBy()
				.create()
				.list(I_C_AllocationLine.class);
	}

	@Override
	public final List<I_C_AllocationLine> retrieveLines(final I_C_AllocationHdr allocHdr)
	{
		final int allocationHdrId = allocHdr.getC_AllocationHdr_ID();
		final Properties ctx = InterfaceWrapperHelper.getCtx(allocHdr);
		final String trxName = InterfaceWrapperHelper.getTrxName(allocHdr);

		final boolean retrieveAll = false;
		return retrieveLines(ctx, allocationHdrId, retrieveAll, trxName);
	}

	@Override
	public final List<I_C_AllocationLine> retrieveAllLines(final I_C_AllocationHdr allocHdr)
	{
		final int allocationHdrId = allocHdr.getC_AllocationHdr_ID();
		final Properties ctx = InterfaceWrapperHelper.getCtx(allocHdr);
		final String trxName = InterfaceWrapperHelper.getTrxName(allocHdr);

		final boolean retrieveAll = true;
		return retrieveLines(ctx, allocationHdrId, retrieveAll, trxName);
	}

	@Cached(cacheName = I_C_AllocationLine.Table_Name + "#By#" + I_C_AllocationLine.COLUMNNAME_C_AllocationHdr_ID + "#retrieveAll")
	/* package */ List<I_C_AllocationLine> retrieveLines(final @CacheCtx Properties ctx,
			final int allocationHdrId,
			final boolean retrieveAll,
			final @CacheTrx String trxName)
	{
		final IQueryBuilder<I_C_AllocationLine> builder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_AllocationLine.class, ctx, trxName)
				.addEqualsFilter(I_C_AllocationLine.COLUMN_C_AllocationHdr_ID, allocationHdrId);

		if (!retrieveAll)
		{
			builder
					.addOnlyActiveRecordsFilter()
					.addOnlyContextClient();
		}
		return builder
				.orderBy()
				.addColumn(I_C_AllocationLine.COLUMN_C_AllocationLine_ID)
				.endOrderBy()
				.create()
				.list(I_C_AllocationLine.class);
	}

	@Override
	public final List<I_C_Payment> retrieveAvailablePayments(I_C_Invoice invoice)
	{
		final IQueryBuilder<I_C_Payment> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Payment.class, invoice)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		queryBuilder.orderBy()
				.addColumn(I_C_Payment.COLUMN_DateTrx)
				.addColumn(I_C_Payment.COLUMN_C_Payment_ID);

		queryBuilder.addEqualsFilter(I_C_Payment.COLUMN_C_BPartner_ID, invoice.getC_BPartner_ID());
		queryBuilder.addEqualsFilter(I_C_Payment.COLUMN_DocStatus, X_C_Payment.DOCSTATUS_Completed);
		queryBuilder.addEqualsFilter(I_C_Payment.COLUMN_Processed, true);

		// Matching DocType
		final boolean isReceipt = invoice.isSOTrx();
		queryBuilder.addEqualsFilter(I_C_Payment.COLUMN_IsReceipt, isReceipt);

		queryBuilder.addEqualsFilter(I_C_Payment.COLUMN_IsAutoAllocateAvailableAmt, true);

		queryBuilder.addEqualsFilter(I_C_Payment.COLUMN_IsAllocated, false);

		return queryBuilder.create()
				.list(I_C_Payment.class);
	}

	@Override
	public BigDecimal retrieveAllocatedAmt(org.compiere.model.I_C_Invoice invoice)
	{
		final int invoiceId = invoice.getC_Invoice_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);

		return retrieveAllocatedAmt(invoiceId, trxName);
	}

	private BigDecimal retrieveAllocatedAmt(final int invoiceId, final String trxName)
	{
		BigDecimal retValue = null;
		final String sql = "SELECT SUM(currencyConvert(al.Amount+al.DiscountAmt+al.WriteOffAmt,"
				+ "ah.C_Currency_ID, i.C_Currency_ID,ah.DateTrx,COALESCE(i.C_ConversionType_ID,0), al.AD_Client_ID,al.AD_Org_ID)) "
				+ "FROM C_AllocationLine al"
				+ " INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID=ah.C_AllocationHdr_ID)"
				+ " INNER JOIN C_Invoice i ON (al.C_Invoice_ID=i.C_Invoice_ID) "
				+ "WHERE al.C_Invoice_ID=?"
				+ " AND ah.IsActive='Y' AND al.IsActive='Y'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{

			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, invoiceId);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = rs.getBigDecimal(1);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// log.debug("getAllocatedAmt - " + retValue);
		// ? ROUND(COALESCE(v_AllocatedAmt,0), 2);
		// metas: tsa: 01955: please let the retValue to be NULL if there were no allocation found!
		return retValue;
	}

	@Override
	public BigDecimal retrieveAllocatedAmtIgnoreGivenPaymentIDs(final org.compiere.model.I_C_Invoice invoice, final Set<Integer> paymentIDsToIgnore)
	{
		BigDecimal retValue = null;

		String sql = "SELECT SUM(currencyConvert(al.Amount+al.DiscountAmt+al.WriteOffAmt,"
				+ "ah.C_Currency_ID, i.C_Currency_ID,ah.DateTrx,COALESCE(i.C_ConversionType_ID,0), al.AD_Client_ID,al.AD_Org_ID)) "
				+ "FROM C_AllocationLine al"
				+ " INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID=ah.C_AllocationHdr_ID)"
				+ " INNER JOIN C_Invoice i ON (al.C_Invoice_ID=i.C_Invoice_ID) "
				+ "WHERE al.C_Invoice_ID=?"
				+ " AND ah.IsActive='Y' AND al.IsActive='Y'";
		if (paymentIDsToIgnore != null && !paymentIDsToIgnore.isEmpty())                             // make sure that the set is not empty
		{
			sql += " AND (al.C_Payment_ID NOT IN (-1";

			for (final Integer paymentIdToExclude : paymentIDsToIgnore)
			{
				if (paymentIdToExclude == null)
				{
					continue; // guard agains NPE
				}
				sql += ", " + paymentIdToExclude;
			}
			sql += ") OR al.C_Payment_ID IS NULL )";
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			final String trxName = InterfaceWrapperHelper.getTrxName(invoice);

			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, invoice.getC_Invoice_ID());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = rs.getBigDecimal(1);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// log.debug("getAllocatedAmt - " + retValue);
		// ? ROUND(NVL(v_AllocatedAmt,0), 2);
		// metas: tsa: 01955: please let the retValue to be NULL if there were no allocation found!
		return retValue == null ? BigDecimal.ZERO : retValue;
	}

	@Override
	public List<I_C_AllocationHdr> retrievePostedWithoutFactAcct(final Properties ctx, final Date startTime)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		// Exclude the entries that don't have either Amount, DiscountAmt, WriteOffAmt or OverUnderAmt. These entries will produce 0 in posting
		final ICompositeQueryFilter<I_C_AllocationLine> nonZeroFilter = queryBL.createCompositeQueryFilter(I_C_AllocationLine.class).setJoinOr()
				.addNotEqualsFilter(I_C_AllocationLine.COLUMNNAME_Amount, Env.ZERO)
				.addNotEqualsFilter(I_C_AllocationLine.COLUMNNAME_DiscountAmt, Env.ZERO)
				.addNotEqualsFilter(I_C_AllocationLine.COLUMNNAME_WriteOffAmt, Env.ZERO)
				.addNotEqualsFilter(I_C_AllocationLine.COLUMNNAME_OverUnderAmt, Env.ZERO)
				.addNotEqualsFilter(I_C_AllocationLine.COLUMN_PaymentWriteOffAmt, Env.ZERO);

		// exclude credit memos, adjustment charges and reversals
		final IQuery<I_C_AllocationHdr> approvedAmtFilter = queryBL.createQueryBuilder(I_C_AllocationHdr.class, ctx, trxName)
				.addNotEqualsFilter(I_C_AllocationHdr.COLUMN_ApprovalAmt, Env.ZERO)
				.create();

		// the allocation header must have lines with the payment set or have a non 0 approvalAmt to be eligible for posting
		final ICompositeQueryFilter<I_C_AllocationLine> paymentFilter = queryBL.createCompositeQueryFilter(I_C_AllocationLine.class).setJoinOr()
				.addNotEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Payment_ID, null)
				.addInSubQueryFilter(I_C_AllocationLine.COLUMN_C_AllocationHdr_ID, I_C_AllocationHdr.COLUMN_C_AllocationHdr_ID, approvedAmtFilter);

		final IQueryBuilder<I_C_AllocationLine> queryBuilder = queryBL.createQueryBuilder(I_C_AllocationLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.filter(nonZeroFilter)
				.filter(paymentFilter);

		// Check if there are fact accounts created for each document
		final IQuery<I_Fact_Acct> factAcctQuery = queryBL.createQueryBuilder(I_Fact_Acct.class, ctx, trxName)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_AllocationHdr.class))
				.create();

		// Query builder for the allocation header
		final IQueryBuilder<I_C_AllocationHdr> allocationHdrQuery = queryBuilder
				.andCollect(I_C_AllocationHdr.COLUMN_C_AllocationHdr_ID, I_C_AllocationHdr.class);
		
		// Only the documents created after the given start time
		if (startTime != null)
		{
			allocationHdrQuery.addCompareFilter(I_C_AllocationHdr.COLUMNNAME_Created, Operator.GREATER_OR_EQUAL, startTime);
		}

		return allocationHdrQuery
				.addEqualsFilter(I_C_AllocationHdr.COLUMNNAME_Posted, true) // Posted
				.addEqualsFilter(I_C_AllocationHdr.COLUMNNAME_Processed, true) // Processed
				.addInArrayOrAllFilter(I_GL_Journal.COLUMNNAME_DocStatus, DocAction.STATUS_Closed, DocAction.STATUS_Completed) // DocStatus in ('CO', 'CL')
				.addNotInSubQueryFilter(I_C_AllocationHdr.COLUMNNAME_C_AllocationHdr_ID, I_Fact_Acct.COLUMNNAME_Record_ID, factAcctQuery) // has no accounting
				.create()
				.list(I_C_AllocationHdr.class);

	}
}
