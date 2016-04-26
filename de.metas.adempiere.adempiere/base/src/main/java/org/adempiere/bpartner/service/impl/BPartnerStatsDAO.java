package org.adempiere.bpartner.service.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;
import org.compiere.model.X_C_BPartner_Stats;
import org.compiere.util.DB;
import org.compiere.util.Env;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class BPartnerStatsDAO implements IBPartnerStatsDAO
{
	@Override
	public I_C_BPartner_Stats retrieveBPartnerStats(final I_C_BPartner partner)
	{

		I_C_BPartner_Stats stat = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Stats.class, partner)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Stats.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID())
				.create()
				.firstOnly(I_C_BPartner_Stats.class);

		if (stat == null)
		{
			stat = createBPartnerStats(partner);
		}

		return stat;
	}

	/**
	 * * Create the C_BPartner_Stats entry for the given bpartner.
	 * 
	 * @param partner
	 * @return
	 */
	private I_C_BPartner_Stats createBPartnerStats(final I_C_BPartner partner)
	{
		final I_C_BPartner_Stats stat = InterfaceWrapperHelper.newInstance(I_C_BPartner_Stats.class);

		stat.setC_BPartner(partner);
		stat.setSOCreditStatus(X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck);
		stat.setActualLifeTimeValue(Env.ZERO);
		stat.setSO_CreditUsed(Env.ZERO);
		stat.setTotalOpenBalance(Env.ZERO);

		InterfaceWrapperHelper.save(stat);

		return stat;
	}

	@Override
	public BigDecimal retrieveTotalOpenBalance(I_C_BPartner_Stats stats)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(stats);

		BigDecimal totalOpenBalance = null;

		// Legacy sql

		// AZ Goodwill -> BF2041226 : only count completed/closed docs.
		final Object[] sqlParams = new Object[] { stats.getC_BPartner_ID() };
		final String sql = "SELECT "
				// SO Credit Used
				+ "COALESCE((SELECT SUM(currencyBase(invoiceOpen(i.C_Invoice_ID,i.C_InvoicePaySchedule_ID),i.C_Currency_ID,i.DateInvoiced, i.AD_Client_ID,i.AD_Org_ID)) FROM C_Invoice_v i "
				+ "WHERE i.C_BPartner_ID=bp.C_BPartner_ID AND i.IsSOTrx='Y' AND i.IsPaid='N' AND i.DocStatus IN ('CO','CL')),0), "
				// Balance (incl. unallocated payments)
				+ "COALESCE((SELECT SUM(currencyBase(invoiceOpen(i.C_Invoice_ID,i.C_InvoicePaySchedule_ID),i.C_Currency_ID,i.DateInvoiced, i.AD_Client_ID,i.AD_Org_ID)*i.MultiplierAP) FROM C_Invoice_v i "
				+ "WHERE i.C_BPartner_ID=bp.C_BPartner_ID AND i.IsPaid='N' AND i.DocStatus IN ('CO','CL')),0) - "
				+ "COALESCE((SELECT SUM(currencyBase(Paymentavailable(p.C_Payment_ID),p.C_Currency_ID,p.DateTrx,p.AD_Client_ID,p.AD_Org_ID)) FROM C_Payment_v p "
				+ "WHERE p.C_BPartner_ID=bp.C_BPartner_ID AND p.IsAllocated='N'"
				+ " AND p.C_Charge_ID IS NULL AND p.DocStatus IN ('CO','CL')),0) "
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

				totalOpenBalance = rs.getBigDecimal(2);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return totalOpenBalance;
	}

	@Override
	public BigDecimal retrieveSOCreditUsed(I_C_BPartner_Stats stats)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(stats);

		BigDecimal SO_CreditUsed = null;

		// Legacy sql

		final Object[] sqlParams = new Object[] { stats.getC_BPartner_ID() };
		// AZ Goodwill -> BF2041226 : only count completed/closed docs.
		final String sql = "SELECT "
				// SO Credit Used
				+ "COALESCE((SELECT SUM(currencyBase(invoiceOpen(i.C_Invoice_ID,i.C_InvoicePaySchedule_ID),i.C_Currency_ID,i.DateInvoiced, i.AD_Client_ID,i.AD_Org_ID)) FROM C_Invoice_v i "
				+ "WHERE i.C_BPartner_ID=bp.C_BPartner_ID AND i.IsSOTrx='Y' AND i.IsPaid='N' AND i.DocStatus IN ('CO','CL')),0), "
				// Balance (incl. unallocated payments)
				+ "COALESCE((SELECT SUM(currencyBase(invoiceOpen(i.C_Invoice_ID,i.C_InvoicePaySchedule_ID),i.C_Currency_ID,i.DateInvoiced, i.AD_Client_ID,i.AD_Org_ID)*i.MultiplierAP) FROM C_Invoice_v i "
				+ "WHERE i.C_BPartner_ID=bp.C_BPartner_ID AND i.IsPaid='N' AND i.DocStatus IN ('CO','CL')),0) - "
				+ "COALESCE((SELECT SUM(currencyBase(Paymentavailable(p.C_Payment_ID),p.C_Currency_ID,p.DateTrx,p.AD_Client_ID,p.AD_Org_ID)) FROM C_Payment_v p "
				+ "WHERE p.C_BPartner_ID=bp.C_BPartner_ID AND p.IsAllocated='N'"
				+ " AND p.C_Charge_ID IS NULL AND p.DocStatus IN ('CO','CL')),0) "
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
				SO_CreditUsed = rs.getBigDecimal(1);
			}

		}
		catch (SQLException e)
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
	public BigDecimal retrieveActualLifeTimeValue(I_C_BPartner_Stats stat)
	{
		BigDecimal actualLifeTimeValue = null;
		final Object[] sqlParams = new Object[] { stat.getC_BPartner_ID() };

		// Legacy sql

		// AZ Goodwill -> BF2041226 : only count completed/closed docs.
		String sql = "SELECT "
				+ "COALESCE ((SELECT SUM(currencyBase(i.GrandTotal,i.C_Currency_ID,i.DateInvoiced, i.AD_Client_ID,i.AD_Org_ID)) FROM C_Invoice_v i "
				+ "WHERE i.C_BPartner_ID=bp.C_BPartner_ID AND i.IsSOTrx='Y' AND i.DocStatus IN ('CO','CL')),0) "
				+ "FROM C_BPartner bp " + "WHERE C_BPartner_ID=?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			pstmt = DB.prepareStatement(sql, InterfaceWrapperHelper.getTrxName(stat));

			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				actualLifeTimeValue = rs.getBigDecimal(1);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return actualLifeTimeValue;
	}
}
