package de.metas.cache.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.ad.table.api.ColumnSqlSourceDescriptor;
import org.adempiere.ad.table.api.ColumnSqlSourceDescriptor.FetchTargetRecordsMethod;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import javax.annotation.Nullable;
import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@UtilityClass
final class ColumnSqlCacheInvalidateRequestFactories
{
	@Nullable
	public static ModelCacheInvalidateRequestFactory ofDescriptorOrNull(@NonNull final ColumnSqlSourceDescriptor descriptor)
	{
		final FetchTargetRecordsMethod fetchTargetRecordsMethod = descriptor.getFetchTargetRecordsMethod();
		final String targetTableName = descriptor.getTargetTableName();
		if (fetchTargetRecordsMethod == FetchTargetRecordsMethod.SQL)
		{
			return FetchFromSQL.builder()
					.targetTableName(targetTableName)
					.sqlToGetTargetRecordIdBySourceRecordId(descriptor.getSqlToGetTargetRecordIdBySourceRecordId())
					.build();
		}
		else if (fetchTargetRecordsMethod == FetchTargetRecordsMethod.LINK_COLUMN)
		{
			final POInfo targetTableInfo = POInfo.getPOInfoNotNull(targetTableName);
			final String targetTableKeyColumnName = targetTableInfo.getSingleKeyColumnName();
			final String sourceLinkColumnName = descriptor.getSourceLinkColumnNameNotNull();

			if (!targetTableInfo.isPhysicalColumn(sourceLinkColumnName))
			{
				throw AdempiereException.newWithPlainMessage("Column " + targetTableName + "." + sourceLinkColumnName + " it's expected to exist and be a physical column");
			}
			//noinspection UnnecessaryLocalVariable
			final String targetLinkColumnName = sourceLinkColumnName;

			return FetchUsingSourceLinkColumn.builder()
					.targetTableName(targetTableName)
					.targetKeyColumnName(targetTableKeyColumnName)
					.targetLinkColumnName(targetLinkColumnName)
					.sourceLinkColumnName(sourceLinkColumnName)
					.build();
		}
		else
		{
			throw AdempiereException.newWithPlainMessage("Fetch method not supported for " + descriptor);
		}
	}

	@ToString
	@EqualsAndHashCode
	private static class FetchFromSQL implements ModelCacheInvalidateRequestFactory
	{
		private final String targetTableName;
		private final IStringExpression sqlToGetTargetRecordIdBySourceRecordId;

		private static final String EVAL_CTXNAME_Record_ID = "Record_ID";

		@Builder
		private FetchFromSQL(
				@NonNull final String targetTableName,
				@Nullable final String sqlToGetTargetRecordIdBySourceRecordId)
		{
			Check.assumeNotEmpty(targetTableName, "targetTableName is not empty");

			this.targetTableName = targetTableName;
			this.sqlToGetTargetRecordIdBySourceRecordId = Check.isNotBlank(sqlToGetTargetRecordIdBySourceRecordId)
					? StringExpressionCompiler.instance.compile(sqlToGetTargetRecordIdBySourceRecordId)
					: null;
		}

		@Override
		public List<CacheInvalidateRequest> createRequestsFromModel(@NonNull final ICacheSourceModel sourceModel, final ModelCacheInvalidationTiming timing_NOTNUSED)
		{
			final int sourceRecordId = sourceModel.getRecordId();
			final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(sourceModel.getTableName());
			if (sourceRecordId < InterfaceWrapperHelper.getFirstValidIdByColumnName(keyColumnName))
			{
				return ImmutableList.of();
			}

			if (sqlToGetTargetRecordIdBySourceRecordId == null)
			{
				return ImmutableList.of(CacheInvalidateRequest.allRecordsForTable(targetTableName));

			}
			else
			{
				final ImmutableSet<Integer> targetRecordIds = getTargetRecordIds(sourceRecordId);
				return targetRecordIds.stream()
						.map(targetRecordId -> CacheInvalidateRequest.rootRecord(targetTableName, targetRecordId))
						.collect(ImmutableList.toImmutableList());
			}
		}

		private ImmutableSet<Integer> getTargetRecordIds(final int sourceRecordId)
		{
			final ImmutableSet.Builder<Integer> targetRecordIds = ImmutableSet.builder();
			final Evaluatee evalCtx = Evaluatees.mapBuilder()
					.put(EVAL_CTXNAME_Record_ID, sourceRecordId)
					.build();

			DB.forEachRow(
					sqlToGetTargetRecordIdBySourceRecordId.evaluate(evalCtx, OnVariableNotFound.Fail),
					ImmutableList.of(),
					rs -> {
						final int targetRecordId = rs.getInt(1);
						if (targetRecordId > 0)
						{
							targetRecordIds.add(targetRecordId);
						}
					});

			return targetRecordIds.build();
		}
	}

	@ToString
	@EqualsAndHashCode
	private static class FetchUsingSourceLinkColumn implements ModelCacheInvalidateRequestFactory
	{
		@NonNull private final String targetTableName;
		@NonNull private final String targetKeyColumnName;
		@NonNull private final String targetLinkColumnName;
		@NonNull private final String sourceLinkColumnName;

		private final boolean isTargetLinkColumnSameAsKeyColumn;
		@Nullable private final String sqlGetTargetRecordIdByLinkId;

		@Builder
		private FetchUsingSourceLinkColumn(
				@NonNull final String targetTableName,
				@NonNull final String targetKeyColumnName,
				@NonNull final String targetLinkColumnName,
				@NonNull final String sourceLinkColumnName)
		{
			this.targetTableName = targetTableName;
			this.targetKeyColumnName = targetKeyColumnName;
			this.targetLinkColumnName = targetLinkColumnName;
			this.sourceLinkColumnName = sourceLinkColumnName;

			if (targetLinkColumnName.equals(targetKeyColumnName))
			{
				this.isTargetLinkColumnSameAsKeyColumn = true;
				this.sqlGetTargetRecordIdByLinkId = null;
			}
			else
			{
				this.isTargetLinkColumnSameAsKeyColumn = false;
				this.sqlGetTargetRecordIdByLinkId = "SELECT " + targetKeyColumnName + " FROM " + targetTableName + " WHERE " + targetLinkColumnName + "=?";
			}
		}

		@Override
		public List<CacheInvalidateRequest> createRequestsFromModel(@NonNull final ICacheSourceModel sourceModel, final ModelCacheInvalidationTiming timing_NOTUSED)
		{
			final int linkId = sourceModel.getValueAsInt(sourceLinkColumnName, -1);
			return getTargetRecordIdsByLinkId(linkId)
					.stream()
					.map(targetRecordId -> CacheInvalidateRequest.rootRecord(targetTableName, targetRecordId))
					.collect(ImmutableList.toImmutableList());
		}

		private ImmutableSet<Integer> getTargetRecordIdsByLinkId(final int linkId)
		{
			if (linkId < 0)
			{
				return ImmutableSet.of();
			}

			if (isTargetLinkColumnSameAsKeyColumn)
			{
				return ImmutableSet.of(linkId);
			}

			final ImmutableSet.Builder<Integer> targetRecordIds = ImmutableSet.builder();
			DB.forEachRow(
					Check.assumeNotNull(this.sqlGetTargetRecordIdByLinkId, "sqlGetTargetRecordIdByLinkId shall not be null"),
					ImmutableList.of(linkId),
					rs -> {
						final int targetRecordId = rs.getInt(1);
						if (targetRecordId > 0)
						{
							targetRecordIds.add(targetRecordId);
						}
					});
			return targetRecordIds.build();
		}

	}

}
