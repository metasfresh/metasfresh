/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.fulltextsearch.indexer.queue;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.elasticsearch.model.I_ES_FTS_Index_Queue;
import de.metas.error.AdIssueId;
import de.metas.fulltextsearch.config.FTSConfigId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class ModelToIndexRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final int INSERT_BATCHSIZE = 500;
	private static final String INSERT_SQL =
			"INSERT INTO " + I_ES_FTS_Index_Queue.Table_Name
					+ "("
					+ I_ES_FTS_Index_Queue.COLUMNNAME_ES_FTS_Config_ID
					+ ", " + I_ES_FTS_Index_Queue.COLUMNNAME_EventType
					+ ", " + I_ES_FTS_Index_Queue.COLUMNNAME_AD_Table_ID
					+ ", " + I_ES_FTS_Index_Queue.COLUMNNAME_Record_ID
					+ ")"
					+ " VALUES (?, ?, ?, ?)";

	public void addToQueue(@NonNull final List<ModelToIndexEnqueueRequest> requests)
	{
		if (requests.isEmpty())
		{
			return;
		}

		PreparedStatement pstmt = null;
		int insertCount = 0;
		try
		{
			for (final ModelToIndexEnqueueRequest request : requests)
			{
				if (pstmt == null)
				{
					pstmt = DB.prepareStatement(INSERT_SQL, ITrx.TRXNAME_None);
				}

				DB.setParameters(pstmt,
						request.getFtsConfigId().getRepoId(),
						request.getEventType().getCode(),
						request.getSourceModelRef().getAdTableId(),
						request.getSourceModelRef().getRecord_ID());
				pstmt.addBatch();
				insertCount++;

				if (insertCount >= INSERT_BATCHSIZE)
				{
					pstmt.executeBatch();
					pstmt.close();
					pstmt = null;
					insertCount = 0;
				}
			}

			if (pstmt != null)
			{
				pstmt.executeBatch();
				pstmt.close();
			}
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, INSERT_SQL);
		}
	}

	public String newProcessingTag()
	{
		return UUID.randomUUID().toString();
	}

	public ImmutableList<ModelToIndex> tagAndRetrieve(
			@NonNull final String processingTag,
			final int maxSize)
	{
		final int updateCount = queryForTag(null)
				.addEqualsFilter(I_ES_FTS_Index_Queue.COLUMNNAME_Processed, false)
				.setLimit(QueryLimit.ofInt(maxSize))
				//
				.create()
				.updateDirectly()
				.addSetColumnValue(I_ES_FTS_Index_Queue.COLUMNNAME_ProcessingTag, processingTag)
				.addSetColumnValue(I_ES_FTS_Index_Queue.COLUMNNAME_Updated, SystemTime.asInstant())
				.execute();
		if (updateCount <= 0)
		{
			return ImmutableList.of();
		}

		return queryForTag(processingTag)
				.create()
				.stream()
				.map(ModelToIndexRepository::toModelToIndex)
				.collect(ImmutableList.toImmutableList());
	}

	private static ModelToIndex toModelToIndex(final I_ES_FTS_Index_Queue record)
	{
		return ModelToIndex.builder()
				.ftsConfigId(FTSConfigId.ofRepoId(record.getES_FTS_Config_ID()))
				.eventType(ModelToIndexEventType.ofCode(record.getEventType()))
				.sourceModelRef(TableRecordReference.ofOrNull(record.getAD_Table_ID(), record.getRecord_ID()))
				.build();
	}

	private IQueryBuilder<I_ES_FTS_Index_Queue> queryForTag(@Nullable final String processingTag)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_ES_FTS_Index_Queue.class)
				.addEqualsFilter(I_ES_FTS_Index_Queue.COLUMNNAME_ProcessingTag, processingTag);
	}

	public void markAsProcessed(@NonNull final String processingTag)
	{
		queryForTag(processingTag)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_ES_FTS_Index_Queue.COLUMNNAME_Processed, true)
				.addSetColumnValue(I_ES_FTS_Index_Queue.COLUMNNAME_Updated, SystemTime.asInstant())
				.execute();
	}

	public void markAsError(@NonNull final String processingTag, @NonNull final AdIssueId adIssueId)
	{
		queryForTag(processingTag)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_ES_FTS_Index_Queue.COLUMNNAME_Processed, true)
				.addSetColumnValue(I_ES_FTS_Index_Queue.COLUMNNAME_IsError, true)
				.addSetColumnValue(I_ES_FTS_Index_Queue.COLUMNNAME_AD_Issue_ID, adIssueId)
				.addSetColumnValue(I_ES_FTS_Index_Queue.COLUMNNAME_Updated, SystemTime.asInstant())
				.execute();
	}

	public void untag(final String processingTag)
	{
		queryForTag(processingTag)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_ES_FTS_Index_Queue.COLUMNNAME_ProcessingTag, null)
				.addSetColumnValue(I_ES_FTS_Index_Queue.COLUMNNAME_Processed, false)
				.addSetColumnValue(I_ES_FTS_Index_Queue.COLUMNNAME_IsError, false)
				.addSetColumnValue(I_ES_FTS_Index_Queue.COLUMNNAME_AD_Issue_ID, null)
				.addSetColumnValue(I_ES_FTS_Index_Queue.COLUMNNAME_Updated, SystemTime.asInstant())
				.execute();
	}
}
