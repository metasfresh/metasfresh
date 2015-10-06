package de.metas.flatrate.api.impl;

/*
 * #%L
 * de.metas.contracts
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
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MTable;
import org.compiere.model.POInfo;
import org.compiere.model.Query;
import org.compiere.util.DB;

import de.metas.flatrate.api.IContractsDAO;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_SubscriptionProgress;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

public class ContractsDAO implements IContractsDAO
{
	@Override
	public  List<I_C_Flatrate_Term> retrieveCFlatrateTermsWithMissingCandidates(final Properties ctx, final int limit, final String trxName)
	{
		final POInfo poInfo = POInfo.getPOInfo(I_C_Flatrate_Term.Table_Name);
		final String sqlTypeCondiditons = poInfo.getColumn(poInfo.getColumnIndex(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions)).getColumnSQL();

		final StringBuilder wc = new StringBuilder();

		// only terms that are completed
		wc.append(I_C_Flatrate_Term.COLUMNNAME_DocStatus + "=" + DB.TO_STRING(X_C_Flatrate_Term.DOCSTATUS_Completed) + " AND \n");
		
		// only subscription contract terms
		wc.append("( " + sqlTypeCondiditons + ") " + "=" + DB.TO_STRING(X_C_Flatrate_Term.TYPE_CONDITIONS_Abonnement) + " AND \n");

		// only terms that don't have their own C_OrderLine (? := 0)
		wc.append(I_C_Flatrate_Term.COLUMNNAME_C_OrderLine_Term_ID + " IS NULL AND \n");
		
		// 04027: do not return terms whose conditions has IsCreateNoInvoice='Y'
		wc.append( "NOT EXISTS (\n"+
				" select 1 from " + I_C_Flatrate_Conditions.Table_Name + " fc \n" +
				" where "+
				"		fc."+ I_C_Flatrate_Conditions.COLUMNNAME_IsActive + "=" + DB.TO_STRING("Y") + " AND \n" +
				"		fc."+ I_C_Flatrate_Conditions.COLUMNNAME_C_Flatrate_Conditions_ID + "=" + I_C_Flatrate_Term.Table_Name + "." + I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID + " AND \n" + 
				"		fc."+ I_C_Flatrate_Conditions.COLUMNNAME_IsCreateNoInvoice + "=" + DB.TO_STRING("Y") +
				" ) AND \n") ;

		// only terms that are not yet referenced by an C_Invoice_Candidate that is active and has the same AD_Client_ID
		wc.append("NOT EXISTS (\n" +
				"    select 1 from " + I_C_Invoice_Candidate.Table_Name + " ic \n" +
				"    where " +
				"       ic." + I_C_Invoice_Candidate.COLUMNNAME_IsActive + "=" + DB.TO_STRING("Y") + " AND \n" +
				"       ic." + I_C_Invoice_Candidate.COLUMNNAME_AD_Client_ID + "=" + I_C_Flatrate_Term.Table_Name + "." + I_C_Flatrate_Term.COLUMNNAME_AD_Client_ID + " AND \n" +
				"       ic." + I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID + "=? AND \n" +
				"       ic." + I_C_Invoice_Candidate.COLUMNNAME_Record_ID + "=" + I_C_Flatrate_Term.Table_Name + "." + I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + " \n" +
				")");
		
		// 03799
		//now()>= (select noticedate from c_flatrate_term  term2 where 
		//term2.c_flatrateterm_next_id = c_flatrate_term_id)
		//or now() >= (startdate)
		wc.append(" AND \n" +
				"( " +

				"( " +
				" now() >= " +
				" (select " + I_C_Flatrate_Term.COLUMNNAME_NoticeDate +
				" FROM " + I_C_Flatrate_Term.Table_Name + " term2 \n" +
				" WHERE term2." + I_C_Flatrate_Term.COLUMNNAME_C_FlatrateTerm_Next_ID +
				" = " + I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + ") " +
				" ) \n" +

				" OR \n" +

				"( " +
				" now() >= (" + I_C_Flatrate_Term.COLUMNNAME_StartDate + ") "+
				" )" +

				" )");
		

		final List<I_C_Flatrate_Term> termsWithMissingCandidates =
				new Query(ctx, I_C_Flatrate_Term.Table_Name, wc.toString(), trxName)
						.setParameters(MTable.getTable_ID(I_C_Flatrate_Term.Table_Name)) // C_Invoice_Candidate.AD_Table_ID
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.setLimit(limit)
						.setOrderBy(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
						.list(I_C_Flatrate_Term.class);
		
		return termsWithMissingCandidates;

	}

	@Override
	public boolean termHasAPredecessor(I_C_Flatrate_Term term)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);
		
		final String wc =I_C_Flatrate_Term.COLUMNNAME_C_FlatrateTerm_Next_ID + " =? ";
		
		final int termID = term.getC_Flatrate_Term_ID();
		
		final I_C_Flatrate_Term predecessor =
				new Query(ctx, I_C_Flatrate_Term.Table_Name, wc, trxName)
						.setParameters(termID)
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.setOrderBy(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
						.first(I_C_Flatrate_Term.class);
		
		if(predecessor != null)
		{
			return true;
		}
		return false;
	}

	@Override
	public BigDecimal retrieveSubscriptionProgressQtyForTerm(final I_C_Flatrate_Term term)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final BigDecimal qty = new Query(ctx, I_C_SubscriptionProgress.Table_Name, I_C_SubscriptionProgress.COLUMNNAME_C_Flatrate_Term_ID + "=?", trxName)
				.setParameters(term.getC_Flatrate_Term_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.sum(I_C_SubscriptionProgress.COLUMNNAME_Qty);

		return qty;
	}
}
