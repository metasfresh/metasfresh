/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.invoicecandidate.api.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_Currency;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

class GetInvoiceCandidatesAmtSelectionSummaryCommand
{
	private static final Logger logger = LogManager.getLogger(GetInvoiceCandidatesAmtSelectionSummaryCommand.class);

	private static final String COLUMNNAME_IsPackingMaterial = "IsPackingMaterial";
	private static final String COLUMNNAME_Count = "Count";

	private static final String I_M_HU_PackingMaterial_Table_Name = "M_HU_PackingMaterial";
	private static final String I_M_HU_PackingMaterial_COLUMNNAME_M_Product_ID = "M_Product_ID";

	@Nullable
	final String extraWhereClause;

	// TODO: would be nice to use de.metas.ui.web.view.descriptor.SqlAndParams but that is in module webui-api, and here we don't have access to it
	public GetInvoiceCandidatesAmtSelectionSummaryCommand(@Nullable final String extraWhereClause)
	{
		this.extraWhereClause = extraWhereClause;
	}

	@NonNull
	public InvoiceCandidatesAmtSelectionSummary execute()
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
				+ "SELECT 1 FROM " + I_M_HU_PackingMaterial_Table_Name + " pm"
				+ " WHERE "
				+ "pm." + I_M_HU_PackingMaterial_COLUMNNAME_M_Product_ID + " = " + I_C_Invoice_Candidate.Table_Name + "." + I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID
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
		sql.append("(" + I_C_Invoice_Candidate.COLUMNNAME_Processed + " = 'N')"); // avoid bad perf problems when no filter-whereclause was given

		if (Check.isEmpty(extraWhereClause))
		{
			// we might have deactivated candidates on individual DBs after support cases/fixes
			sql.append(" AND (" + I_C_Invoice_Candidate.COLUMNNAME_IsActive + " = 'Y')");
		}
		else
		{
			sql.append(" AND (").append(extraWhereClause).append(")");
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

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			final InvoiceCandidatesAmtSelectionSummary.Builder summaryBuilder = InvoiceCandidatesAmtSelectionSummary.builder();

			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				final BigDecimal netAmtTotal = rs.getBigDecimal(I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice);
				final boolean isPackingMaterial = rs.getBoolean(COLUMNNAME_IsPackingMaterial);
				final boolean isApprovedForInvoicing = StringUtils.toBoolean(rs.getString(I_C_Invoice_Candidate.COLUMNNAME_ApprovalForInvoicing));
				final String curSymbol = rs.getString(I_C_Currency.COLUMNNAME_CurSymbol);
				final boolean isToRecompute = StringUtils.toBoolean(rs.getString(I_C_Invoice_Candidate.COLUMNNAME_IsToRecompute));
				final int countToRecompute = rs.getInt(COLUMNNAME_Count);

				summaryBuilder
						.addTotalNetAmt(netAmtTotal, isApprovedForInvoicing, isPackingMaterial)
						.addCurrencySymbol(curSymbol);
				if (isToRecompute)
				{
					summaryBuilder.addCountToRecompute(countToRecompute);
				}
			}

			return summaryBuilder.build();
		}
		catch (final Exception ex)
		{
			logger.warn("Failed fetching summary. Returning empty. SQL={}", sql, ex);

			return InvoiceCandidatesAmtSelectionSummary.EMPTY;
		}
		finally
		{
			DB.close(rs, pstmt);
		}

	}
}
