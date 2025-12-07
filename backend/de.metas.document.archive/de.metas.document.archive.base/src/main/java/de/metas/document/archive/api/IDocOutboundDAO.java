package de.metas.document.archive.api;

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
import de.metas.document.archive.DocOutboundLogId;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.model.X_C_Doc_Outbound_Log_Line;
import de.metas.document.engine.DocStatus;
import de.metas.util.ISingletonService;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.api.ArchiveAction;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface IDocOutboundDAO extends ISingletonService
{
	Stream<I_C_Doc_Outbound_Log> streamByIdsInOrder(@NonNull List<DocOutboundLogId> ids);

	/**
	 * Retrieve {@link I_C_Doc_Outbound_Log} for give archive (AD_Table_ID and Record_ID fields will be used for matching)
	 *
	 * @return {@link I_C_Doc_Outbound_Log} record or null if not found
	 */
	I_C_Doc_Outbound_Log retrieveLog(ArchiveId archiveId);

	@NonNull
	I_C_Doc_Outbound_Log retrieveLog(@NonNull DocOutboundLogId logId);
	
	@Nullable
	I_C_Doc_Outbound_Log retrieveLog(@NonNull TableRecordReference tableRecordReference);

	/**
	 * Find among the given <code>log</code>'s {@link I_C_Doc_Outbound_Log_Line}s the latest one with action <code>PDF</code> (i.e highest ID)
	 *
	 * @return log line
	 */
	I_C_Doc_Outbound_Log_Line retrieveCurrentPDFArchiveLogLineOrNull(I_C_Doc_Outbound_Log log);

	/**
	 * Decorate query builder with PDF archive log line filters
	 */
	void addPDFArchiveLogLineFilters(IQueryBuilder<I_C_Doc_Outbound_Log_Line> queryBuilder);

	/**
	 * Retrieves last created {@link I_C_Doc_Outbound_Log} for given bpartner and table
	 */
	I_C_Doc_Outbound_Log retrieveLog(final IContextAware contextProvider, int bpartnerId, int AD_Table_ID);

	@NonNull
	ImmutableList<I_C_Doc_Outbound_Log> retrieveLogs(@NonNull IQueryFilter<I_C_Doc_Outbound_Log> filter, boolean isFilterCurrentMailSet);

	void updatePOReferenceIfExists(@NonNull TableRecordReference recordReference, @Nullable String poReference);

	void updateLogAndLinesDocStatus(@NonNull TableRecordReference tableRecordReference, @Nullable DocStatus docStatus);

	@NonNull
	ImmutableList<LogWithLines> retrieveLogsWithLines(@NonNull final ImmutableList<I_C_Doc_Outbound_Log> logs);

	@Value
	@Builder(toBuilder = true)
	class LogWithLines
	{
		@NonNull I_C_Doc_Outbound_Log log;
		@NonNull @Singular List<I_C_Doc_Outbound_Log_Line> lines;

		public Optional<I_C_Doc_Outbound_Log_Line> findCurrentPDFArchiveLogLine()
		{
			return getLines().stream()
					.filter(line -> ArchiveAction.PDF_EXPORT.getCode().equals(line.getAction()))
					.max(Comparator.comparingInt(I_C_Doc_Outbound_Log_Line::getC_Doc_Outbound_Log_Line_ID));
		}

		public boolean wasEmailSentAtLeastOnce()
		{
			return getLines().stream()
					.anyMatch(line -> ArchiveAction.EMAIL.getCode().equals(line.getAction()) &&
							X_C_Doc_Outbound_Log_Line.STATUS_Email_Success.equals(line.getStatus()));
		}
	}
}
