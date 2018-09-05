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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.invoice.api.IInvoiceSourceDAO;
import de.metas.dunning.model.I_C_Dunning_Candidate_Invoice_v1;
import de.metas.payment.paymentterm.PaymentTermId;
import lombok.NonNull;

public class InvoiceSourceDAO implements IInvoiceSourceDAO
{
	@Override
	public Timestamp retrieveDueDate(final org.compiere.model.I_C_Invoice invoice)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		return DB.getSQLValueTSEx(trxName, "SELECT paymentTermDueDate(?,?)", invoice.getC_PaymentTerm_ID(), invoice.getDateInvoiced());
	}

	@Override
	public int retrieveDueDays(
			@NonNull final PaymentTermId paymentTermId,
			final Date dateInvoiced,
			final Date date)
	{
		return DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT paymentTermDueDays(?,?,?)", paymentTermId.getRepoId(), dateInvoiced, date);
	}

	@Override
	public Iterator<I_C_Dunning_Candidate_Invoice_v1> retrieveDunningCandidateInvoices(final IDunningContext context)
	{
		final Properties ctx = context.getCtx();
		final String trxName = context.getTrxName();
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_C_DunningLevel dunningLevel = context.getC_DunningLevel();
		Check.assumeNotNull(dunningLevel, "Context shall have DuningLevel set: {}", context);

		Check.assumeNotNull(context.getDunningDate(), "Context shall have DunningDate set: {}", context);
		final Date dunningDate = TimeUtil.getDay(context.getDunningDate());

		final ICompositeQueryFilter<I_C_Dunning_Candidate_Invoice_v1> dunningGraceFilter = queryBL
				.createCompositeQueryFilter(I_C_Dunning_Candidate_Invoice_v1.class)
				.setJoinOr()
				.addEqualsFilter(I_C_Dunning_Candidate_Invoice_v1.COLUMN_DunningGrace, null)
				.addCompareFilter(I_C_Dunning_Candidate_Invoice_v1.COLUMN_DunningGrace, Operator.LESS, dunningDate);

		return queryBL.createQueryBuilder(I_C_Dunning.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx)
				.addEqualsFilter(I_C_Dunning.COLUMNNAME_C_Dunning_ID, dunningLevel.getC_Dunning_ID()) // Dunning Level is for current assigned Dunning

				.andCollectChildren(I_C_Dunning_Candidate_Invoice_v1.COLUMN_C_Dunning_ID)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx)
				.filter(dunningGraceFilter) // Validate Dunning Grace (if any)

				.orderBy()
				.addColumn(I_C_Dunning_Candidate_Invoice_v1.COLUMN_C_Invoice_ID).endOrderBy()
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000 /* iterator shall load 1000 records at a time */)
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false /* the result is not changing while the iterator is iterated */)
				.create()
				.iterate(I_C_Dunning_Candidate_Invoice_v1.class);

		// final int adClientId = Env.getAD_Client_ID(ctx);
		//
		// final List<Object> params = new ArrayList<Object>();
		// final StringBuilder whereClause = new StringBuilder();
		//
		// // Only for current tenant
		// whereClause.append(" AD_Client_ID = ? ");
		// params.add(adClientId);
		//
		// whereClause.append(" AND (")
		// .append("DunningGrace IS NULL").append(" OR ").append("DunningGrace < ?")
		// .append(")");
		// params.add(dunningDate);
		//
		// whereClause.append(" AND EXISTS (SELECT 1 FROM C_DunningLevel dl WHERE dl.C_DunningLevel_ID = ? AND dl.C_Dunning_ID = " + I_C_Dunning_Candidate_Invoice_v1.Table_Name + "." + "C_Dunning_ID) ");
		// params.add(dunningLevel.getC_DunningLevel_ID());
		//
		// return new Query(ctx, I_C_Dunning_Candidate_Invoice_v1.Table_Name, whereClause.toString(), trxName)
		// .setParameters(params)
		// .setOrderBy(I_C_Dunning_Candidate_Invoice_v1.COLUMNNAME_C_Invoice_ID
		// + ", " + I_C_Dunning_Candidate_Invoice_v1.COLUMNNAME_C_InvoicePaySchedule_ID)
		// .setOption(Query.OPTION_IteratorBufferSize, 1000) // iterator shall load 1000 records at a time
		// .iterate(I_C_Dunning_Candidate_Invoice_v1.class, false); // guaranteed=false
	}
}
