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

import de.metas.document.archive.DocOutboundLogId;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.postfinance.PostFinanceStatus;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.archive.api.ArchiveAction;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.comparator.FixedOrderByKeyComparator;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class DocOutboundDAO implements IDocOutboundDAO
{

	public static final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_C_Doc_Outbound_Log getById(@NonNull final DocOutboundLogId docOutboundLogId)
	{
		return load(docOutboundLogId, I_C_Doc_Outbound_Log.class);
	}

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
	public final I_C_Doc_Outbound_Log_Line retrieveCurrentPDFArchiveLogLineOrNull(@NonNull final DocOutboundLogId docOutboundLogId)
	{
		return retrieveCurrentPDFArchiveLogLineOrNull(getById(docOutboundLogId));
	}

	@Override
	public void setPostFinanceExportStatus(@NonNull final DocOutboundLogId docOutboundLogId, @NonNull final PostFinanceStatus exportStatus)
	{
		final I_C_Doc_Outbound_Log logRecord = getById(docOutboundLogId);
		logRecord.setPostFinance_Export_Status(exportStatus.getCode());
		InterfaceWrapperHelper.save(logRecord);
	}

	@Override
	@Nullable
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
	public I_C_Doc_Outbound_Log retrieveLog(final IContextAware contextProvider, int bpartnerId, int AD_Table_ID)
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
	public List<I_C_Doc_Outbound_Log> retrieveLog(@NonNull final TableRecordReference tableRecordReference)
	{
		// Order by
		final IQueryOrderBy queryOrderBy = queryBL
				.createQueryOrderByBuilder(I_C_Doc_Outbound_Log.class)
				.addColumn(I_C_Doc_Outbound_Log.COLUMNNAME_AD_Archive_ID)
				.createQueryOrderBy();

		return queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMNNAME_AD_Table_ID, tableRecordReference.getAdTableId())
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMN_Record_ID, tableRecordReference.getRecord_ID())
				.create()
				.setOrderBy(queryOrderBy)
				.list(I_C_Doc_Outbound_Log.class);
	}

	@Override
	public void updatePOReferenceIfExists(
			@NonNull final TableRecordReference recordReference,
			@Nullable final String poReference)
	{
		final List<I_C_Doc_Outbound_Log> docOutboundLogs = retrieveLog(recordReference);
		if (Check.isEmpty(docOutboundLogs))
		{
			return;
		}

		docOutboundLogs.forEach(docOutboundLog -> {
			docOutboundLog.setPOReference(poReference);

			saveRecord(docOutboundLog);
		});
	}
	
	public static TableRecordReference extractRecordRef(@NonNull final I_AD_Archive archive)
	{
		return TableRecordReference.of(archive.getAD_Table_ID(), archive.getRecord_ID());
	}

}
