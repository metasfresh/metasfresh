package de.metas.document.archive.api.impl;

/*
 * #%L
 * de.metas.document.archive.base
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.document.archive.DocOutboundLogId;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.engine.DocStatus;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.api.ArchiveAction;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.comparator.FixedOrderByKeyComparator;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class DocOutboundDAO implements IDocOutboundDAO
{

	private final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public Stream<I_C_Doc_Outbound_Log> streamByIdsInOrder(@NonNull final List<DocOutboundLogId> ids)
	{
		if (ids.isEmpty())
		{
			return Stream.empty();
		}

		return queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addInArrayFilter(I_C_Doc_Outbound_Log.COLUMNNAME_C_Doc_Outbound_Log_ID, ids)
				.create()
				.stream()
				.sorted(FixedOrderByKeyComparator.notMatchedAtTheEnd(ids, DocOutboundDAO::extractId));
	}

	private static DocOutboundLogId extractId(final I_C_Doc_Outbound_Log record)
	{
		return DocOutboundLogId.ofRepoId(record.getC_Doc_Outbound_Log_ID());
	}

	@Override
	public final I_C_Doc_Outbound_Log_Line retrieveCurrentPDFArchiveLogLineOrNull(final I_C_Doc_Outbound_Log log)
	{
		if (log == null)
		{
			return null;
		}

		final IQueryBuilder<I_C_Doc_Outbound_Log_Line> queryBuilder = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log_Line.class, log)
				.addEqualsFilter(I_C_Doc_Outbound_Log_Line.COLUMN_C_Doc_Outbound_Log_ID, log.getC_Doc_Outbound_Log_ID());
		addPDFArchiveLogLineFilters(queryBuilder);

		//
		// We're interested in the latest log line matching PDF Export only
		return queryBuilder.create()
				.first(I_C_Doc_Outbound_Log_Line.class);
	}

	@Override
	public final void addPDFArchiveLogLineFilters(final IQueryBuilder<I_C_Doc_Outbound_Log_Line> queryBuilder)
	{
		Check.assumeNotNull(queryBuilder, "queryBuilder not null");

		queryBuilder
				.addOnlyActiveRecordsFilter()
				//
				// Filter records which actually have an AD_Archive
				//
				.addCompareFilter(I_C_Doc_Outbound_Log_Line.COLUMN_AD_Archive_ID, Operator.GREATER, 0)
				//
				// Filter the ones with PDF export
				//
				.addEqualsFilter(I_C_Doc_Outbound_Log_Line.COLUMN_Action, ArchiveAction.PDF_EXPORT.getCode());

		//
		// Order by latest log line first
		queryBuilder.orderBy()
				.addColumn(I_C_Doc_Outbound_Log_Line.COLUMN_C_Doc_Outbound_Log_Line_ID, Direction.Descending, Nulls.Last);
	}

	@Override
	public I_C_Doc_Outbound_Log retrieveLog(final IContextAware contextProvider, final int bpartnerId, final int AD_Table_ID)
	{
		final IQueryBuilder<I_C_Doc_Outbound_Log> queryBuilder = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class, contextProvider)
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMNNAME_AD_Table_ID, AD_Table_ID);

		// Order by
		final IQueryOrderBy queryOrderBy = queryBL
				.createQueryOrderByBuilder(I_C_Doc_Outbound_Log.class)
				.addColumnDescending(I_C_Doc_Outbound_Log.COLUMNNAME_Created)
				.createQueryOrderBy();

		return queryBuilder
				.create()
				.setOrderBy(queryOrderBy)
				.first(I_C_Doc_Outbound_Log.class);
	}

	@Override
	public final I_C_Doc_Outbound_Log retrieveLog(@NonNull final ArchiveId archiveId)
	{
		final I_AD_Archive archiveRecord = archiveDAO.retrieveArchive(archiveId);
		final TableRecordReference tableRecordReference = TableRecordReference.ofReferenced(archiveRecord);
		return retrieveLog(tableRecordReference);
	}

	@Override
	@NonNull
	public I_C_Doc_Outbound_Log retrieveLog(@NonNull final DocOutboundLogId logId)
	{
		return InterfaceWrapperHelper.load(logId, I_C_Doc_Outbound_Log.class);
	}

	@Override
	@Nullable
	public I_C_Doc_Outbound_Log retrieveLog(@NonNull final TableRecordReference tableRecordReference)
	{
		return queryBL
				.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMNNAME_AD_Table_ID, tableRecordReference.getAdTableId())
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMN_Record_ID, tableRecordReference.getRecord_ID())
				.create()
				.firstOnly(I_C_Doc_Outbound_Log.class);
	}

	@Override
	@NonNull
	public ImmutableList<I_C_Doc_Outbound_Log> retrieveLogs(@NonNull final IQueryFilter<I_C_Doc_Outbound_Log> filter, final boolean isFilterCurrentMailSet)
	{
		final IQueryBuilder<I_C_Doc_Outbound_Log> builder = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addOnlyActiveRecordsFilter()
				.filter(filter);
		if(isFilterCurrentMailSet)
		{
			builder.addNotNull(I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailAddress);
		}
		return builder.create().listImmutable(I_C_Doc_Outbound_Log.class);
	}

	@Override
	public void updatePOReferenceIfExists(
			@NonNull final TableRecordReference recordReference,
			@Nullable final String poReference)
	{
		final I_C_Doc_Outbound_Log docOutboundLog = retrieveLog(recordReference);
		if (docOutboundLog == null)
		{
			return;
		}

		docOutboundLog.setPOReference(poReference);

		saveRecord(docOutboundLog);
	}

	public void updateLogAndLinesDocStatus(@NonNull final TableRecordReference tableRecordReference, @Nullable final DocStatus docStatus)
	{
		updateLogDocStatus(tableRecordReference, docStatus);
		updateLogLineDocStatus(tableRecordReference, docStatus);
	}

	private void updateLogDocStatus(@NonNull final TableRecordReference tableRecordReference, @Nullable final DocStatus docStatus)
	{
		final ICompositeQueryUpdater<I_C_Doc_Outbound_Log> queryUpdater = queryBL.createCompositeQueryUpdater(I_C_Doc_Outbound_Log.class)
				.addSetColumnValue(I_C_Doc_Outbound_Log.COLUMNNAME_DocStatus, DocStatus.toCodeOrNull(docStatus));

		queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMNNAME_AD_Table_ID, tableRecordReference.getAdTableId())
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMN_Record_ID, tableRecordReference.getRecord_ID())
				.create()
				.update(queryUpdater);
	}

	private void updateLogLineDocStatus(@NonNull final TableRecordReference tableRecordReference, @Nullable final DocStatus docStatus)
	{
		final ICompositeQueryUpdater<I_C_Doc_Outbound_Log_Line> queryUpdaterLogLine = queryBL.createCompositeQueryUpdater(I_C_Doc_Outbound_Log_Line.class)
				.addSetColumnValue(I_C_Doc_Outbound_Log_Line.COLUMNNAME_DocStatus, DocStatus.toCodeOrNull(docStatus));

		queryBL.createQueryBuilder(I_C_Doc_Outbound_Log_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Doc_Outbound_Log_Line.COLUMNNAME_AD_Table_ID, tableRecordReference.getAdTableId())
				.addEqualsFilter(I_C_Doc_Outbound_Log_Line.COLUMN_Record_ID, tableRecordReference.getRecord_ID())
				.create()
				.update(queryUpdaterLogLine);
	}

	@Override
	@NonNull
	public ImmutableList<LogWithLines> retrieveLogsWithLines(@NonNull final ImmutableList<I_C_Doc_Outbound_Log> logs)
	{
		if (logs.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableSet<DocOutboundLogId> ids = logs.stream()
				.map(log -> DocOutboundLogId.ofRepoId(log.getC_Doc_Outbound_Log_ID()))
				.collect(ImmutableSet.toImmutableSet());

		final Map<Integer, List<I_C_Doc_Outbound_Log_Line>> linesByLogId = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log_Line.class)
				.addInArrayFilter(I_C_Doc_Outbound_Log_Line.COLUMNNAME_C_Doc_Outbound_Log_ID, ids)
				.create()
				.list()
				.stream()
				.collect(Collectors.groupingBy(I_C_Doc_Outbound_Log_Line::getC_Doc_Outbound_Log_ID));

		return logs.stream()
				.map(log -> LogWithLines.builder()
						.log(log)
						.lines(linesByLogId.getOrDefault(log.getC_Doc_Outbound_Log_ID(), Collections.emptyList()))
						.build())
				.collect(ImmutableList.toImmutableList());
	}
}
