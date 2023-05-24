package de.metas.dunning.api.impl;

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

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.dunning.api.IDunningCandidateQuery;
import de.metas.dunning.api.IDunningCandidateQuery.ApplyAccessFilter;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.model.I_C_Dunning_Candidate_Invoice_v1;
import de.metas.organization.OrgId;
import de.metas.security.permissions.Access;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_InvoicePaySchedule;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DunningDAO extends AbstractDunningDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@Override
	public <T> T newInstance(IDunningContext dunningContext, Class<T> interfaceClass)
	{
		final Properties ctx = dunningContext.getCtx();
		final String trxName = dunningContext.getTrxName();
		final T model = InterfaceWrapperHelper.create(ctx, interfaceClass, trxName);
		return model;
	}

	@Override
	public void save(Object model)
	{
		InterfaceWrapperHelper.save(model);
	}

	@Override
	public List<I_C_Dunning> retrieveDunnings()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilderOutOfTrx(I_C_Dunning.class)
				.addOnlyActiveRecordsFilter()
				// no need to filter by AD_Client, because C_Dunning doesn't support AD_Cient_ID=0 records
				.create()
				.list();
	}

	@Override
	protected List<I_C_Dunning_Candidate> retrieveDunningCandidates(final IDunningContext context, final IDunningCandidateQuery query)
	{
		return createDunningCandidatesSqlQuery(context, query)
				.list(I_C_Dunning_Candidate.class);
	}

	@Override
	protected I_C_Dunning_Candidate retrieveDunningCandidate(final IDunningContext context, final IDunningCandidateQuery query)
	{
		return createDunningCandidatesSqlQuery(context, query)
				.firstOnly(I_C_Dunning_Candidate.class);
	}

	@Override
	protected Iterator<I_C_Dunning_Candidate> retrieveDunningCandidatesIterator(final IDunningContext context, final IDunningCandidateQuery query)
	{
		return createDunningCandidatesSqlQuery(context, query)
				.setOption(Query.OPTION_IteratorBufferSize, 1000) // reducing the number of selects by increasing the buffer/page size
				.iterate(I_C_Dunning_Candidate.class);
	}

	private Query createDunningCandidatesSqlQuery(final IDunningContext context, final IDunningCandidateQuery query)
	{
		final Properties ctx = context.getCtx();
		final String trxName = context.getTrxName();

		final StringBuilder wc = new StringBuilder();
		final List<Object> params = new ArrayList<>();

		if (query.getAD_Table_ID() > 0 && query.getRecord_ID() >= 0)
		{
			if (wc.length() > 0)
			{
				wc.append(" AND ");
			}
			wc.append(I_C_Dunning_Candidate.COLUMNNAME_AD_Table_ID).append("=?")
					.append(" AND ").append(I_C_Dunning_Candidate.COLUMNNAME_Record_ID).append("=?");
			params.add(query.getAD_Table_ID());
			params.add(query.getRecord_ID());
		}

		final List<I_C_DunningLevel> dunningLevels = query.getC_DunningLevels();
		if (dunningLevels != null && !dunningLevels.isEmpty())
		{
			if (wc.length() > 0)
			{
				wc.append(" AND ");
			}
			wc.append(I_C_Dunning_Candidate.COLUMNNAME_C_DunningLevel_ID).append(" IN (-1");
			for (final I_C_DunningLevel level : dunningLevels)
			{
				wc.append(",?");
				params.add(level.getC_DunningLevel_ID());
			}
			wc.append(")");
		}

		if (query.getProcessed() != null)
		{
			if (wc.length() > 0)
			{
				wc.append(" AND ");
			}
			wc.append(I_C_Dunning_Candidate.COLUMNNAME_Processed).append("=?");
			params.add(query.getProcessed());
		}

		if (query.getWriteOff() != null)
		{
			if (wc.length() > 0)
			{
				wc.append(" AND ");
			}
			wc.append(I_C_Dunning_Candidate.COLUMNNAME_IsWriteOff).append("=?");
			params.add(query.getWriteOff());
		}

		final Query sqlQuery = new Query(ctx, I_C_Dunning_Candidate.Table_Name, wc.toString(), trxName)
				.setParameters(params)
				.setOnlyActiveRecords(query.isActive())
				.addWhereClause(true, query.getAdditionalWhere()); // it's ok if 'AdditionalWhere' is "" or null

		if (query.isApplyClientSecurity())
		{
			sqlQuery.setClient_ID();
		}

		// 04766: allowing to have no application of access filters at all
		if (ApplyAccessFilter.ACCESS_FILTER_RW.equals(query.getApplyAccessFilter()))
		{
			sqlQuery.setRequiredAccess(Access.WRITE);
		}
		else if (ApplyAccessFilter.ACCESS_FILTER_RO.equals(query.getApplyAccessFilter()))
		{
			sqlQuery.setRequiredAccess(Access.READ);
		}
		// 04766: end

		sqlQuery.setOrderBy(I_C_Dunning_Candidate.COLUMNNAME_C_Dunning_Candidate_ID);

		return sqlQuery;
	}

	@Override
	public Iterator<I_C_DunningDoc> retrieveNotProcessedDocumentsIterator(final IDunningContext dunningContext)
	{
		final Properties ctx = dunningContext.getCtx();
		final String trxName = dunningContext.getTrxName();

		final String whereClause = I_C_Dunning_Candidate.COLUMNNAME_Processed + "=?";
		final Iterator<I_C_DunningDoc> iterator = new Query(ctx, I_C_DunningDoc.Table_Name, whereClause, trxName)
				.setParameters(false)
				.setClient_ID()
				.setOption(Query.OPTION_IteratorBufferSize, 1000) // reducing the number of selects by increasing the buffer/page size
				.iterate(I_C_DunningDoc.class);
		return iterator;
	}

	@Override
	public Iterator<I_C_DunningDoc_Line_Source> retrieveDunningDocLineSourcesToWriteOff(final IDunningContext dunningContext)
	{
		final List<Object> params = new ArrayList<>();
		final StringBuilder whereClause = new StringBuilder();

		whereClause.append(I_C_DunningDoc_Line_Source.COLUMNNAME_Processed).append("=?");
		params.add(true);

		whereClause.append(" AND ").append(I_C_DunningDoc_Line_Source.COLUMNNAME_IsWriteOff).append("=?");
		params.add(true);

		whereClause.append(" AND ").append(I_C_DunningDoc_Line_Source.COLUMNNAME_IsWriteOffApplied).append("=?");
		params.add(false);

		return new Query(dunningContext.getCtx(), I_C_DunningDoc_Line_Source.Table_Name, whereClause.toString(), dunningContext.getTrxName())
				.setParameters(params)
				.setOrderBy(I_C_DunningDoc_Line_Source.COLUMNNAME_C_DunningDoc_Line_Source_ID)
				.setRequiredAccess(Access.WRITE) // in order to write off, we need to update the C_DunningDoc_Line_Source
				.setOption(Query.OPTION_IteratorBufferSize, 1000) // reducing the number of selects by increasing the buffer/page size
				.iterate(I_C_DunningDoc_Line_Source.class);
	}

	/**
	 * Deletes all active, unprocessed candidates of the given level via DELETE sql statment.
	 *
	 * @param context
	 * @param dunningLevel
	 * @return the number of deleted candidates
	 */
	@Override
	public int deleteNotProcessedCandidates(final IDunningContext context, final I_C_DunningLevel dunningLevel)
	{
		final StringBuilder deleteSQL = new StringBuilder("DELETE FROM " + I_C_Dunning_Candidate.Table_Name +
				" WHERE "
				+ I_C_Dunning_Candidate.COLUMNNAME_IsActive + "='Y' AND "
				+ I_C_Dunning_Candidate.COLUMNNAME_AD_Client_ID + "=? AND "
				+ I_C_Dunning_Candidate.COLUMNNAME_Processed + "='N' AND "
				+ I_C_Dunning_Candidate.COLUMNNAME_C_DunningLevel_ID + "=?");

		final List<Object> params = Stream.of(Env.getAD_Client_ID(context.getCtx()), dunningLevel.getC_DunningLevel_ID())
				.collect(Collectors.toList());

		final RecomputeDunningCandidatesQuery recomputeDunningCandidatesQuery = context.getRecomputeDunningCandidatesQuery();
		if (recomputeDunningCandidatesQuery != null && recomputeDunningCandidatesQuery.getOrgId() != null)
		{
			deleteSQL.append(" AND " + I_C_Dunning_Candidate.COLUMNNAME_AD_Org_ID + "=?");
			params.add(recomputeDunningCandidatesQuery.getOrgId());
		}

		final int[] result = { 0 };

		trxManager.run(context.getTrxName(), context.getTrxRunnerConfig(),
				localTrxName -> result[0] = DB.executeUpdateEx(deleteSQL.toString(), params.toArray(), localTrxName));
		return result[0];
	}

	/**
	 * Deletes all active, unprocessed candidates matching the given recomputeDunningCandidatesQuery and dunningLevel
	 *
	 * @return the number of deleted candidates
	 */
	@Override
	public int deleteTargetObsoleteCandidates(
			@NonNull final RecomputeDunningCandidatesQuery recomputeDunningCandidatesQuery,
			@NonNull final I_C_DunningLevel dunningLevel)
	{
		final IQueryBuilder<I_C_Dunning_Candidate> queryBuilder = queryBL.createQueryBuilder(I_C_Dunning_Candidate.class);

		final IQueryFilter<I_C_Dunning_Candidate> onlyTargetRecordsFilter = recomputeDunningCandidatesQuery.getOnlyTargetRecordsFilter();
		if (onlyTargetRecordsFilter != null)
		{
			final ICompositeQueryFilter<I_C_Dunning_Candidate> deletableTargetRecordsFilter = getDeletableTargetRecordsFilter(onlyTargetRecordsFilter);

			queryBuilder.filter(deletableTargetRecordsFilter);
		}

		final OrgId orgId = recomputeDunningCandidatesQuery.getOrgId();
		if (orgId != null)
		{
			queryBuilder.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_AD_Org_ID, orgId);
		}

		return queryBuilder
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_C_DunningLevel_ID, dunningLevel.getC_DunningLevel_ID())
				.create()
				.deleteDirectly();
	}

	@Override
	public List<I_C_Dunning_Candidate> retrieveProcessedDunningCandidatesForRecord(final Properties ctx, final int tableId, final int recordId, final String trxName)
	{
		final StringBuilder whereClause = new StringBuilder();

		whereClause.append(I_C_Dunning_Candidate.COLUMNNAME_AD_Table_ID).append("=?")
				.append(" AND ")
				.append(I_C_Dunning_Candidate.COLUMNNAME_Record_ID).append("=?")
				.append(" AND ")
				.append(I_C_Dunning_Candidate.COLUMNNAME_IsDunningDocProcessed).append("='Y'");

		return new Query(ctx, I_C_Dunning_Candidate.Table_Name, whereClause.toString(), trxName)
				.setParameters(tableId, recordId)
				.list(I_C_Dunning_Candidate.class);
	}

	@NonNull
	private ICompositeQueryFilter<I_C_Dunning_Candidate> getDeletableTargetRecordsFilter(@NonNull final IQueryFilter<I_C_Dunning_Candidate> onlyTargetRecordsFilter)
	{
		final IQuery<I_C_Dunning_Candidate_Invoice_v1> invoiceBasedDunningCandidatesQuery = queryBL
				.createQueryBuilder(I_C_Dunning_Candidate_Invoice_v1.class)
				.addEqualsFilter(I_C_Dunning_Candidate_Invoice_v1.COLUMNNAME_C_InvoicePaySchedule_ID, null)
				.create();
		final ICompositeQueryFilter<I_C_Dunning_Candidate> invoiceBasedObsoleteCandidatesFilter = queryBL.createCompositeQueryFilter(I_C_Dunning_Candidate.class)
				.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_AD_Table_ID, tableDAO.retrieveTableId(I_C_Invoice.Table_Name))
				.addNotInSubQueryFilter(I_C_Dunning_Candidate.COLUMNNAME_Record_ID, I_C_Dunning_Candidate_Invoice_v1.COLUMNNAME_C_Invoice_ID, invoiceBasedDunningCandidatesQuery);

		final IQuery<I_C_Dunning_Candidate_Invoice_v1> payScheduleBasedCandidatesQuery = queryBL
				.createQueryBuilder(I_C_Dunning_Candidate_Invoice_v1.class)
				.addNotNull(I_C_Dunning_Candidate_Invoice_v1.COLUMN_C_InvoicePaySchedule_ID)
				.create();
		final ICompositeQueryFilter<I_C_Dunning_Candidate> payScheduleObsoleteCandidatesFilter = queryBL.createCompositeQueryFilter(I_C_Dunning_Candidate.class)
				.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_AD_Table_ID, tableDAO.retrieveTableId(I_C_InvoicePaySchedule.Table_Name))
				.addNotInSubQueryFilter(I_C_Dunning_Candidate.COLUMNNAME_Record_ID, I_C_Dunning_Candidate_Invoice_v1.COLUMNNAME_C_InvoicePaySchedule_ID, payScheduleBasedCandidatesQuery);

		final ICompositeQueryFilter<I_C_Dunning_Candidate> deletableRecordsFilter = queryBL.createCompositeQueryFilter(I_C_Dunning_Candidate.class)
				.setJoinOr()
				.addFilter(invoiceBasedObsoleteCandidatesFilter)
				.addFilter(payScheduleObsoleteCandidatesFilter);

		return queryBL
				.createCompositeQueryFilter(I_C_Dunning_Candidate.class)
				.setJoinAnd()
				.addFilter(deletableRecordsFilter)
				.addFilter(onlyTargetRecordsFilter);
	}
}
