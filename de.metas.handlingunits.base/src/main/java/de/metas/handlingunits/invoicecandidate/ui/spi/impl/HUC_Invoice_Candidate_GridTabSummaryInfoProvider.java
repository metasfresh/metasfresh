package de.metas.handlingunits.invoicecandidate.ui.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ui.api.IGridTabSummaryInfo;
import org.adempiere.ui.spi.IGridTabSummaryInfoProvider;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;
import org.compiere.model.GridTable;
import org.compiere.model.I_C_Currency;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.handlingunits.invoicecandidate.ui.spi.impl.HUInvoiceCandidatesSelectionSummaryInfo.Builder;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.logging.LogManager;

/**
 * Provides the summary message which is displayed at the window's bottom, when we deal with invoice candidates tab.
 *
 * @author al
 *
 */
public class HUC_Invoice_Candidate_GridTabSummaryInfoProvider implements IGridTabSummaryInfoProvider
{
	private static final String COLUMNNAME_IsPackingMaterial = "IsPackingMaterial";
	private static final String COLUMNNAME_Count = "Count";

	private final Logger logger = LogManager.getLogger(getClass());

	@Override
	public final IGridTabSummaryInfo getSummaryInfo(final GridTab gridTab)
	{
		if (gridTab == null)
		{
			return IGridTabSummaryInfo.NULL;
		}

		final GridTable gridTable = gridTab.getMTable();
		final String icWhereClause = gridTable.getSelectWhereClauseFinal();

		final HUInvoiceCandidatesSelectionSummaryInfo summary = getInvoiceCandidatesSelectionSummary(icWhereClause);
		return summary;
	}

	private final HUInvoiceCandidatesSelectionSummaryInfo getInvoiceCandidatesSelectionSummary(final String icWhereClause)
	{
		// NOTE: we chose to do it with hard-coded SQL because the have little time to implement it better and maintain the performance, because this method is called very frequently.
		// NOTE2: before changing to a more Java approach, you should check and improve how this method is called
		// and somehow to tell to API to not call it each time the current grid tab record is navigated up/down

		final StringBuilder sql = new StringBuilder();
		//
		// NetAmtToInvoice
		sql.append("SELECT COALESCE(SUM("
				+ I_C_Invoice_Candidate.Table_Name
				+ "."
				+ I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice + "), 0) as "
				+ I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice);
		//
		// IsPackingMaterial
		sql.append(", EXISTS("
				+ "SELECT 1 FROM " + I_M_HU_PackingMaterial.Table_Name + " pm"
				+ " WHERE "
				+ "pm." + I_M_HU_PackingMaterial.COLUMNNAME_M_Product_ID + " = " + I_C_Invoice_Candidate.Table_Name + "." + I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID
				+ ") as "
				+ COLUMNNAME_IsPackingMaterial);
		//
		// ApprovalForInvoicing
		sql.append(", "
				+ I_C_Invoice_Candidate.Table_Name
				+ "." + I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing);
		//
		// C_Currency_ID
		sql.append(", COALESCE((SELECT cur."
				+ I_C_Currency.COLUMNNAME_CurSymbol
				+ " FROM "
				+ I_C_Currency.Table_Name
				+ " cur WHERE cur."
				+ I_C_Currency.COLUMNNAME_C_Currency_ID
				+ "="
				+ I_C_Invoice_Candidate.Table_Name
				+ "."
				+ I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID
				+ "), NULL) as " + I_C_Currency.COLUMNNAME_CurSymbol);
		//
		// IsToRecompute
		sql.append(", ( case when exists (select 1 from "
				+ I_C_Invoice_Candidate_Recompute.Table_Name
				+ " icr "
				+ " where icr."
				+ I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + " = "
				+ I_C_Invoice_Candidate.Table_Name
				+ "."
				+ I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + ") then 'Y' else 'N' end ) as "
				+ I_C_Invoice_Candidate.COLUMNNAME_IsToRecompute);
		//
		// Count
		sql.append(", COUNT("
				+ I_C_Invoice_Candidate.Table_Name
				+ "."
				+ I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + ") as "
				+ COLUMNNAME_Count);
		sql.append(" FROM "
				+ I_C_Invoice_Candidate.Table_Name);
		sql.append(" WHERE ");
		sql.append(" 1 = 1 ");
		if (icWhereClause != null)
		{
			sql.append(" AND (").append(icWhereClause).append(")");
		}

		// FRESH-580
		// Apply the default filter to the SQL

		final String sqlDefaultFilter = Services.get(IInvoiceCandDAO.class).getSQLDefaultFilter(Env.getCtx());

		if (!sqlDefaultFilter.isEmpty())
		{
			sql.append(" AND (").append(sqlDefaultFilter).append(")");
		}

		sql.append(" GROUP BY "
				+ I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing
				+ ", "
				+ I_C_Currency.COLUMNNAME_CurSymbol
				+ ", "
				+ I_C_Invoice_Candidate.COLUMNNAME_IsToRecompute
				+ ", "
				+ I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID
				+ ", "
				+ COLUMNNAME_IsPackingMaterial);

		final Builder summaryBuilder = HUInvoiceCandidatesSelectionSummaryInfo.builder();

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				final BigDecimal netAmtTotal = rs.getBigDecimal(I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice);
				final Boolean isPackingMaterial = rs.getBoolean(COLUMNNAME_IsPackingMaterial);
				final Boolean isApprovedForInvoicing = "Y".equals(rs.getString(I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing));
				final String curSymbol = rs.getString(I_C_Currency.COLUMNNAME_CurSymbol);
				final boolean isToRecompute = "Y".equals(rs.getString(I_C_Invoice_Candidate.COLUMNNAME_IsToRecompute));
				final int countToRecompute = rs.getInt(COLUMNNAME_Count);

				summaryBuilder.addTotalNetAmt(netAmtTotal, isApprovedForInvoicing, isPackingMaterial);
				summaryBuilder.addCurrencySymbol(curSymbol);
				if (isToRecompute)
				{
					summaryBuilder.addCountToRecompute(countToRecompute);
				}
			}
		}
		catch (final Exception e)
		{
			logger.error(sql.toString(), e);

			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			pstmt = null;
			rs = null;
		}

		final HUInvoiceCandidatesSelectionSummaryInfo summary = summaryBuilder.build();
		return summary;
	}
}
