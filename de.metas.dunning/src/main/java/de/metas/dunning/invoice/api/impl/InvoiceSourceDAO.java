package de.metas.dunning.invoice.api.impl;

/*
 * #%L
 * de.metas.dunning
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


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;

import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.invoice.api.IInvoiceSourceDAO;
import de.metas.dunning.model.I_C_Dunning_Candidate_Invoice_v1;

public class InvoiceSourceDAO implements IInvoiceSourceDAO
{
	@Override
	public Timestamp retrieveDueDate(final org.compiere.model.I_C_Invoice invoice)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		return DB.getSQLValueTSEx(trxName, "SELECT paymentTermDueDate(?,?)", invoice.getC_PaymentTerm_ID(), invoice.getDateInvoiced());
	}

	@Override
	public int retrieveDueDays(final org.compiere.model.I_C_Invoice invoice, final Date date)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		return DB.getSQLValueEx(trxName, "SELECT paymentTermDueDays(?,?,?)", invoice.getC_PaymentTerm_ID(), invoice.getDateInvoiced(), date);
	}

	@Override
	public int retrieveDueDays(final int paymentTermId, final Date dateInvoiced, final Date date)
	{
		return DB.getSQLValueEx(Trx.TRXNAME_None, "SELECT paymentTermDueDays(?,?,?)", paymentTermId, dateInvoiced, date);
	}

	@Override
	public Iterator<I_C_Dunning_Candidate_Invoice_v1> retrieveDunningCandidateInvoices(final IDunningContext context)
	{
		final Properties ctx = context.getCtx();
		final String trxName = context.getTrxName();

		final int adClientId = Env.getAD_Client_ID(ctx);

		Check.assumeNotNull(context.getDunningDate(), "Context shall have DunningDate set: {0}", context);
		final Date dunningDate = TimeUtil.getDay(context.getDunningDate());

		final I_C_DunningLevel dunningLevel = context.getC_DunningLevel();
		Check.assumeNotNull(dunningLevel, "Context shall have DuningLevel set: {0}", context);

		final List<Object> params = new ArrayList<Object>();
		final StringBuilder whereClause = new StringBuilder();

		// Only for current tenant
		whereClause.append(" AD_Client_ID = ? ");
		params.add(adClientId);

		// Validate Dunning Grace (if any)
		whereClause.append(" AND (")
				.append("DunningGrace IS NULL").append(" OR ").append("DunningGrace < ?")
				.append(")");
		params.add(dunningDate);

		// Dunning Level is for current assigned Dunning
		whereClause.append(" AND EXISTS (SELECT 1 FROM C_DunningLevel dl WHERE dl.C_DunningLevel_ID = ? AND dl.C_Dunning_ID = " + I_C_Dunning_Candidate_Invoice_v1.Table_Name + "." + "C_Dunning_ID) ");
		params.add(dunningLevel.getC_DunningLevel_ID());

		return new Query(ctx, I_C_Dunning_Candidate_Invoice_v1.Table_Name, whereClause.toString(), trxName)
				.setParameters(params)
				.setOrderBy(I_C_Dunning_Candidate_Invoice_v1.COLUMNNAME_C_Invoice_ID
						+ ", " + I_C_Dunning_Candidate_Invoice_v1.COLUMNNAME_C_InvoicePaySchedule_ID)
				.setOption(Query.OPTION_IteratorBufferSize, 1000) // iterator shall load 1000 records at a time
				.iterate(I_C_Dunning_Candidate_Invoice_v1.class, false); // guaranteed=false
	}
}
