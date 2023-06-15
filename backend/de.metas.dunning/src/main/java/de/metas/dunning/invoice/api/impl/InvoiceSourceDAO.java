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

import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;

import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.invoice.api.IInvoiceSourceDAO;
import de.metas.dunning.model.I_C_Dunning_Candidate_Invoice_v1;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

import javax.annotation.Nullable;

public class InvoiceSourceDAO implements IInvoiceSourceDAO
{
	@Override
	public int computeDueDays(@NonNull final Date dueDate, @Nullable final Date date)
	{

		final Date payDate =  date != null ? date : SystemTime.asDate();
		return TimeUtil.getDaysBetween(dueDate, payDate);
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
				.addCompareFilter(I_C_Dunning_Candidate_Invoice_v1.COLUMN_DueDate, Operator.LESS, dunningDate)
				.filter(dunningGraceFilter) // Validate Dunning Grace (if any)

				.orderBy()
				.addColumn(I_C_Dunning_Candidate_Invoice_v1.COLUMN_C_Invoice_ID).endOrderBy()
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000 /* iterator shall load 1000 records at a time */)
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false /* the result is not changing while the iterator is iterated */)
				.create()
				.iterate(I_C_Dunning_Candidate_Invoice_v1.class);
	}
}
