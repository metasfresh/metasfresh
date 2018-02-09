package org.adempiere.bpartner.service.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.BPartnerCreditLimiRepository;
import org.adempiere.bpartner.service.IBPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsBL;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;
import org.compiere.model.X_C_BPartner_Stats;
import org.compiere.util.DB;
import org.compiere.util.Env;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class BPartnerStatsDAO implements IBPartnerStatsDAO
{
	@Override
	public IBPartnerStats retrieveBPartnerStats(final I_C_BPartner partner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(partner);
		I_C_BPartner_Stats stat = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Stats.class, ctx, ITrx.TRXNAME_ThreadInherited) // using current trx, because we will save in current trx too
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Stats.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID())
				.create()
				.firstOnly(I_C_BPartner_Stats.class);

		if (stat == null)
		{
			stat = createBPartnerStats(partner);
		}

		final IBPartnerStats bpStats = BPartnerStats.of(stat);

		return bpStats;
	}

	/**
	 * Create the C_BPartner_Stats entry for the given bpartner.
	 *
	 * @param partner
	 * @return
	 */
	private I_C_BPartner_Stats createBPartnerStats(final I_C_BPartner partner)
	{
		final I_C_BPartner_Stats stat = InterfaceWrapperHelper.newInstance(I_C_BPartner_Stats.class);

		stat.setC_BPartner(partner);
		stat.setSOCreditStatus(X_C_BPartner_Stats.SOCREDITSTATUS_CreditOK);
		stat.setActualLifeTimeValue(BigDecimal.ZERO);
		stat.setSO_CreditUsed(BigDecimal.ZERO);
		stat.setOpenItems(BigDecimal.ZERO);

		InterfaceWrapperHelper.save(stat);

		return stat;
	}

	@Override
	public BigDecimal retrieveOpenItems(@NonNull final IBPartnerStats bpStats)
	{
		final I_C_BPartner_Stats stats = getC_BPartner_Stats(bpStats);

		final String trxName = ITrx.TRXNAME_ThreadInherited;

		BigDecimal openItems = null;

		final Object[] sqlParams = new Object[] { stats.getC_BPartner_ID() };
		final String sql = "SELECT "
				+ "currencyBase(openamt,C_Currency_ID,DateInvoiced,AD_Client_ID,AD_Org_ID) from de_metas_endcustomer_fresh_reports.OpenItems_Report(now()::date) where  C_BPartner_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{

				openItems = rs.getBigDecimal(1);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return openItems;
	}

	@Override
	public BigDecimal retrieveSOCreditUsed(@NonNull final IBPartnerStats bpStats)
	{
		final I_C_BPartner_Stats stats = getC_BPartner_Stats(bpStats);
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		final Properties ctx = InterfaceWrapperHelper.getCtx(stats);

		BigDecimal SO_CreditUsed = null;

		final Object[] sqlParams = new Object[] {stats.getC_BPartner_ID() };
		final String sql = "SELECT "
				// open invoices
				+ "COALESCE((SELECT SUM(currencyBase(invoiceOpen(i.C_Invoice_ID,i.C_InvoicePaySchedule_ID),i.C_Currency_ID,i.DateInvoiced, i.AD_Client_ID,i.AD_Org_ID)) FROM C_Invoice_v i "
				+ "WHERE i.C_BPartner_ID=bp.C_BPartner_ID AND i.IsSOTrx='Y' AND i.IsPaid='N' AND i.DocStatus IN ('CO','CL')),0), "
				// unallocated payments
				+ "COALESCE((SELECT SUM(currencyBase(Paymentavailable(p.C_Payment_ID),p.C_Currency_ID,p.DateTrx,p.AD_Client_ID,p.AD_Org_ID)) FROM C_Payment_v p "
				+ "WHERE p.C_BPartner_ID=bp.C_BPartner_ID AND p.IsAllocated='N'"
				+ " AND p.C_Charge_ID IS NULL AND p.DocStatus IN ('CO','CL')),0)*(-1), "
				// open invoice candidates
				+ "COALESCE((SELECT SUM(currencyBase(ic.NetAmtToInvoice,ic.C_Currency_ID,ic.DateOrdered, ic.AD_Client_ID,ic.AD_Org_ID)) FROM C_Invoice_Candidate ic "
				+ "WHERE ic.Bill_BPartner_ID=bp.C_BPartner_ID AND ic.Processed='N'),0) "
				+ "FROM C_BPartner bp " + "WHERE C_BPartner_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				SO_CreditUsed = rs.getBigDecimal(1).add(rs.getBigDecimal(2)).add(rs.getBigDecimal(3));
			}

		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return SO_CreditUsed;
	}


	@Override
	public void updateSOCreditUsed(final IBPartnerStats bpStats)
	{
		final BigDecimal SO_CreditUsed = retrieveSOCreditUsed(bpStats);
		final I_C_BPartner_Stats stats = getC_BPartner_Stats(bpStats);
		stats.setSO_CreditUsed(SO_CreditUsed);
		InterfaceWrapperHelper.save(stats);
	}

	@Override
	public void updateActualLifeTimeValue(final IBPartnerStats bpStats)
	{
		final I_C_BPartner_Stats stats = getC_BPartner_Stats(bpStats);

		BigDecimal actualLifeTimeValue = null;
		final Object[] sqlParams = new Object[] { stats.getC_BPartner_ID() };

		// Legacy sql

		// AZ Goodwill -> BF2041226 : only count completed/closed docs.
		final String sql = "SELECT "
				+ "COALESCE ((SELECT SUM(currencyBase(i.GrandTotal,i.C_Currency_ID,i.DateInvoiced, i.AD_Client_ID,i.AD_Org_ID)) FROM C_Invoice_v i "
				+ "WHERE i.C_BPartner_ID=bp.C_BPartner_ID AND i.IsSOTrx='Y' AND i.DocStatus IN ('CO','CL')),0) "
				+ "FROM C_BPartner bp " + "WHERE C_BPartner_ID=?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);

			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				actualLifeTimeValue = rs.getBigDecimal(1);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		stats.setActualLifeTimeValue(actualLifeTimeValue);
		InterfaceWrapperHelper.save(stats);
	}

	@Override
	public void updateSOCreditStatus(final IBPartnerStats bpStats)
	{

		final IBPartnerStatsBL bpartnerStatsBL = Services.get(IBPartnerStatsBL.class);
		final I_C_BPartner partner = retrieveC_BPartner(bpStats);

		final BPartnerCreditLimiRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimiRepository.class);
		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(partner.getC_BPartner_ID());

		final String initialCreditStatus = bpStats.getSOCreditStatus();

		String creditStatusToSet;

		// Nothing to do
		if (X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck.equals(initialCreditStatus)
				|| X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop.equals(initialCreditStatus)
				|| BigDecimal.ZERO.compareTo(creditLimit) == 0)
		{
			return;
		}

		// Above Credit Limit
		if (creditLimit.compareTo(retrieveSOCreditUsed(bpStats)) < 0)
		{
			creditStatusToSet = X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold;
		}
		else
		{
			// Above Watch Limit
			final BigDecimal watchAmt = creditLimit.multiply(bpartnerStatsBL.getCreditWatchRatio(bpStats));

			if (watchAmt.compareTo(bpStats.getOpenItems()) < 0)
			{
				creditStatusToSet = X_C_BPartner_Stats.SOCREDITSTATUS_CreditWatch;
			}
			else
			{
				// is OK
				creditStatusToSet = X_C_BPartner_Stats.SOCREDITSTATUS_CreditOK;
			}
		}

		setSOCreditStatus(bpStats, creditStatusToSet);
	}

	@Override
	public void updateOpenItems(final IBPartnerStats bpStats)
	{

		// load the statistics
		final I_C_BPartner_Stats stats = getC_BPartner_Stats(bpStats);

		final BigDecimal openItems = retrieveOpenItems(bpStats);

		// update the statistics with the up tp date openItems
		stats.setOpenItems(openItems);

		// save in db
		InterfaceWrapperHelper.save(stats);
	}

	@Override
	public void setSOCreditStatus(final IBPartnerStats bpStats, final String soCreditStatus)
	{
		final I_C_BPartner_Stats stats = getC_BPartner_Stats(bpStats);

		stats.setSOCreditStatus(soCreditStatus);

		InterfaceWrapperHelper.save(stats);

	}

	@Override
	public I_C_BPartner retrieveC_BPartner(final IBPartnerStats bpStats)
	{
		final I_C_BPartner_Stats stats = getC_BPartner_Stats(bpStats);

		return stats.getC_BPartner();
	}

	private I_C_BPartner_Stats getC_BPartner_Stats(final IBPartnerStats bpStats)
	{
		final BPartnerStats bpStatsInstance;

		if (InterfaceWrapperHelper.isInstanceOf(bpStats, BPartnerStats.class))
		{
			bpStatsInstance = (BPartnerStats)bpStats;
		}

		else
		{
			// in case there is another implementation of IBPartnerStats, create a new BPartnerStats instance

			final int bpartnerID = bpStats.getC_BPartner_ID();

			final I_C_BPartner partner = InterfaceWrapperHelper.create(Env.getCtx(), bpartnerID, I_C_BPartner.class, ITrx.TRXNAME_ThreadInherited);

			bpStatsInstance = (BPartnerStats)retrieveBPartnerStats(partner);

		}

		return bpStatsInstance.getC_BPartner_Stats();
	}

}
