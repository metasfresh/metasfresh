package org.adempiere.model;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ILookupDAO.ITableRefInfo;
import org.adempiere.ad.table.TableRecordIdDescriptor;
import org.adempiere.ad.table.api.ITableRecordIdDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.PORelationException;
import org.adempiere.model.ZoomInfoFactory.IZoomSource;
import org.adempiere.model.ZoomInfoFactory.POZoomSource;
import org.adempiere.model.ZoomInfoFactory.ZoomInfo;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.MQuery;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.adempiere.service.IColumnBL;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class RelationTypeZoomProvider implements IZoomProvider
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final Logger logger = LogManager.getLogger(RelationTypeZoomProvider.class);

	private final boolean directed;
	private final String zoomInfoId;
	private final String internalName;
	private final boolean isTableRecordIdTarget;

	private final ZoomProviderDestination source;
	private final ZoomProviderDestination target;

	private RelationTypeZoomProvider(final Builder builder)
	{
		directed = builder.isDirected();
		zoomInfoId = builder.getZoomInfoId();
		internalName = builder.getInternalName();

		isTableRecordIdTarget = builder.isTableRecordIdTarget();

		source = isTableRecordIdTarget ? null
				: ZoomProviderDestination.builder()
						.adReferenceId(builder.getSource_Reference_ID())
						.tableRefInfo(builder.getSourceTableRefInfoOrNull())
						.roleDisplayName(builder.getTargetRoleDisplayName())
						.tableRecordIdTarget(isTableRecordIdTarget)
						.build();

		target =

				ZoomProviderDestination.builder()
						.adReferenceId(builder.getTarget_Reference_ID())
						.tableRefInfo(builder.getTargetTableRefInfoOrNull())
						.roleDisplayName(builder.getTargetRoleDisplayName())
						.tableRecordIdTarget(isTableRecordIdTarget)
						.build();

	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("zoomInfoId", zoomInfoId)
				.add("internalName", internalName)
				.add("directed", directed)
				.add("source", source)
				.add("target", target)
				.add("isReferenceTarget", isTableRecordIdTarget)
				.toString();
	}

	@Override
	public List<ZoomInfo> retrieveZoomInfos(final IZoomSource zoomOrigin, final int targetAD_Window_ID, final boolean checkRecordsCount)
	{
		final int adWindowId;
		final ITranslatableString display;

		// #2340 Reference Target relation type: There is no source, only a target that contains the table and
		// the reference target column to be linked with the zoomSource
		if (isTableRecordIdTarget)
		{
			final ZoomProviderDestination referenceTarget = getTarget();

			adWindowId = getRefTableAD_Window_ID(referenceTarget.getTableRefInfo(), zoomOrigin.isSOTrx());

			display = referenceTarget.getRoleDisplayName(adWindowId);
		}

		else
		{

			final IPair<ZoomProviderDestination, ZoomProviderDestination> sourceAndTarget = findSourceAndTargetEffective(zoomOrigin);

			final ZoomProviderDestination source = sourceAndTarget.getLeft();
			Check.assumeNotNull(source, "Source cannot be left empty");

			final ZoomProviderDestination target = sourceAndTarget.getRight();

			if (!source.matchesAsSource(zoomOrigin))
			{
				logger.trace("Skip {} because {} is not matching source={}", this, zoomOrigin, source);
				return ImmutableList.of();
			}

			adWindowId = getRefTableAD_Window_ID(target.getTableRefInfo(), zoomOrigin.isSOTrx());

			display = target.getRoleDisplayName(adWindowId);
		}

		if (targetAD_Window_ID > 0 && targetAD_Window_ID != adWindowId)
		{
			return ImmutableList.of();
		}

		final MQuery query = mkZoomOriginQuery(zoomOrigin);

		if (checkRecordsCount)
		{
			updateRecordsCountAndZoomValue(query);
		}

		return ImmutableList.of(ZoomInfo.of(getZoomInfoId(), adWindowId, query, display));
	}

	public boolean isDirected()
	{
		return directed;
	}

	public boolean isTableRecordIdTarget()
	{
		return isTableRecordIdTarget;
	}

	private String getZoomInfoId()
	{
		return zoomInfoId;
	}

	String getInternalName()
	{
		return internalName;
	}

	private ZoomProviderDestination getTarget()
	{
		return target;
	}

	private ZoomProviderDestination getSource()
	{
		return source;
	}

	/**
	 * @return effective source and target pair
	 */
	private IPair<ZoomProviderDestination, ZoomProviderDestination> findSourceAndTargetEffective(final IZoomSource zoomSource)
	{
		final ZoomProviderDestination target = getTarget();
		final ZoomProviderDestination source = getSource();

		Check.assumeNotNull(source, "The Source cannot be null");

		if (isDirected())
		{
			// the type is directed, so our destination is always the *target* reference
			return ImmutablePair.of(source, target);
		}
		else if (source.getTableName().equals(target.getTableName()))
		{
			// this relation type is from one table to the same table
			// use the window-id to distinguish
			if (zoomSource.getAD_Window_ID() == getRefTableAD_Window_ID(source.getTableRefInfo(), zoomSource.isSOTrx()))
			{
				return ImmutablePair.of(source, target);
			}
			else
			{
				return ImmutablePair.of(target, source);
			}
		}
		else
		{
			if (zoomSource.getTableName().equals(source.getTableName()))
			{
				return ImmutablePair.of(source, target);
			}
			else
			{
				return ImmutablePair.of(target, source);
			}
		}

	}

	private MQuery mkZoomOriginQuery(final IZoomSource zoomOrigin)
	{
		final String queryWhereClause = createZoomOriginQueryWhereClause(zoomOrigin);

		final ITableRefInfo refTable = getTarget().getTableRefInfo();

		final String tableName = refTable.getTableName();
		final String columnName = refTable.getKeyColumn();

		final MQuery query = new MQuery();
		query.addRestriction(queryWhereClause);
		query.setZoomTableName(tableName);
		query.setZoomColumnName(columnName);

		return query;
	}

	private String createZoomOriginQueryWhereClause(final IZoomSource zoomOrigin)
	{
		// services
		final ITableRecordIdDAO tableRecordIdDAO = Services.get(ITableRecordIdDAO.class);

		final StringBuilder queryWhereClause = new StringBuilder();

		final ITableRefInfo refTable = getTarget().getTableRefInfo();

		final String targetTableName = zoomOrigin.getTableName();
		final String originTableName = refTable.getTableName();

		if (isTableRecordIdTarget)
		{
			String originRecordIdName = null;

			final List<TableRecordIdDescriptor> tableRecordIdDescriptors = tableRecordIdDAO.retrieveTableRecordIdReferences(originTableName);

			for (final TableRecordIdDescriptor tableRecordIdDescriptor : tableRecordIdDescriptors)
			{
				if (targetTableName.equals(tableRecordIdDescriptor.getTargetTableName()))
				{
					originRecordIdName = tableRecordIdDescriptor.getRecordIdColumnName();

					break;
				}
			}

			if (originRecordIdName != null)
			{
				final String tableIdColumnName = Services.get(IColumnBL.class).getTableIdColumnName(originTableName, originRecordIdName).orElse(null);
				Check.assumeNotEmpty(tableIdColumnName, "The table {} must have an AD_Table_ID column", originTableName);

				queryWhereClause

						.append(zoomOrigin.getAD_Table_ID())
						.append(" = ")
						.append(originTableName)
						.append(".")
						.append(tableIdColumnName)
						.append(" AND ")
						.append(zoomOrigin.getRecord_ID())
						.append(" = ")
						.append(originTableName)
						.append(".")
						.append(originRecordIdName);
			}
			else
			{
				queryWhereClause.append(" 1=2 ");
			}

		}
		else
		{

			final String refTableWhereClause = refTable.getWhereClause();

			if (!Check.isEmpty(refTableWhereClause))
			{
				queryWhereClause.append(parseWhereClause(zoomOrigin, refTableWhereClause, true));
			}
			else
			{
				throw new AdempiereException("RefTable " + refTable + " has no whereClause, so RelationType " + this + " needs to be explicit");
			}
		}

		return queryWhereClause.toString();
	}

	/**
	 * Parses given <code>where</code>
	 *
	 * @param zoomOrigin zoom source
	 * @param where where clause to be parsed
	 * @param throwEx true if an exception shall be thrown in case the parsing failed.
	 * @return parsed where clause or empty string in case parsing failed and throwEx is <code>false</code>
	 */
	private static String parseWhereClause(@NonNull final IZoomSource zoomOrigin, final String where, final boolean throwEx)
	{
		final IStringExpression whereExpr = IStringExpression.compileOrDefault(where, IStringExpression.NULL);
		if (whereExpr.isNullExpression())
		{
			return "";
		}

		final Evaluatee evalCtx = zoomOrigin.createEvaluationContext();
		final OnVariableNotFound onVariableNotFound = throwEx ? OnVariableNotFound.Fail : OnVariableNotFound.ReturnNoResult;
		final String whereParsed = whereExpr.evaluate(evalCtx, onVariableNotFound);
		if (whereExpr.isNoResult(whereParsed))
		{
			// NOTE: logging as debug instead of warning because this might be a standard use case,
			// i.e. we are checking if a given where clause has some results, so the method was called with throwEx=false
			// and if we reached this point it means one of the context variables were not present and it has no default value.
			// This is perfectly normal, a default value is really not needed because we don't want to execute an SQL command
			// which would return no result. It's much more efficient to stop here.
			logger.debug("Could not parse where clause:\n{} \n EvalCtx: {} \n ZoomSource: {}", where, evalCtx, zoomOrigin);
			return "";
		}

		return whereParsed;
	}

	private void updateRecordsCountAndZoomValue(final MQuery query)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final String sqlCommon = " FROM " + query.getZoomTableName() + " WHERE " + query.getWhereClause(false);

		final String sqlCount = "SELECT COUNT(*) " + sqlCommon;
		final int count = DB.getSQLValueEx(ITrx.TRXNAME_None, sqlCount);

		if (count > 0)
		{
			final String sqlFirstKey = "SELECT " + query.getZoomColumnName() + sqlCommon;

			final int firstKey = DB.getSQLValueEx(ITrx.TRXNAME_None, sqlFirstKey);
			query.setZoomValue(firstKey);
		}

		final Duration countDuration = Duration.ofNanos(stopwatch.stop().elapsed(TimeUnit.NANOSECONDS));
		query.setRecordCount(count, countDuration);

		Loggables.get().addLog("RelationTypeZoomProvider {} took {}", this, countDuration);
	}

	/**
	 * Retrieve destinations for the zoom origin given as parameter.
	 * NOTE: This is not suitable for TableRecordIdTarget relation types, only for the default kind!
	 *
	 * @param ctx
	 * @param zoomOriginPO
	 * @param clazz
	 * @param trxName
	 * @return
	 */
	public <T> List<T> retrieveDestinations(final Properties ctx, final PO zoomOriginPO, final Class<T> clazz, final String trxName)
	{
		final IZoomSource zoomOrigin = POZoomSource.of(zoomOriginPO, -1);

		final MQuery query = mkZoomOriginQuery(zoomOrigin);

		return new Query(ctx, query.getZoomTableName(), query.getWhereClause(false), trxName)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(query.getZoomColumnName())
				.list(clazz);
	}

	@Value
	private static final class ZoomProviderDestination
	{
		private final int adReferenceId;
		private final ITableRefInfo tableRefInfo;
		private final ITranslatableString roleDisplayName;

		/**
		 * {@code true} means that this instance is based on an {@code AD_RelationType} whose target is a {@code AD_Table_ID/Record_ID} reference.
		 */
		private final boolean tableRecordIdTarget;

		@lombok.Builder
		private ZoomProviderDestination(
				final int adReferenceId,
				@NonNull final ITableRefInfo tableRefInfo,
				@Nullable final ITranslatableString roleDisplayName,
				@NonNull final Boolean tableRecordIdTarget)
		{
			Check.assume(adReferenceId > 0, "adReferenceId > 0");
			this.adReferenceId = adReferenceId;
			this.tableRefInfo = tableRefInfo;
			this.roleDisplayName = roleDisplayName;
			this.tableRecordIdTarget = tableRecordIdTarget;
		}

		public String getTableName()
		{
			return tableRefInfo.getTableName();
		}

		public ITranslatableString getRoleDisplayName(final int fallbackAD_Window_ID)
		{
			if (roleDisplayName != null)
			{
				return roleDisplayName;
			}

			// Fallback to window name
			final ITranslatableString windowName = Services.get(IADWindowDAO.class).retrieveWindowName(fallbackAD_Window_ID);
			Check.errorIf(windowName == null, "Found no display string for, destination={}, AD_Window_ID={}", this, fallbackAD_Window_ID);
			return windowName;
		}

		public boolean matchesAsSource(final IZoomSource zoomSource)
		{
			if (tableRecordIdTarget)
			{
				// the source always matches if the target is ReferenceTarget
				logger.debug("matchesAsSource - return true because tableRecordIdTarget=true; this={}", this);
				return true;
			}

			if (!zoomSource.isGenericZoomOrigin())
			{
				logger.warn("matchesAsSource - return false because zoomSource.isGenericZoomOrigin()==false; this={}; zoomSource={}", this, zoomSource);
				return false;
			}

			final String keyColumnName = zoomSource.getKeyColumnNameOrNull();
			if (Check.isEmpty(keyColumnName, true))
			{
				logger.warn("matchesAsSource - return false because zoomSource.getKeyColumnNameOrNull()==null; this={}; zoomSource={}", this, zoomSource);
				return false;
			}

			final String whereClause = tableRefInfo.getWhereClause();
			if (Check.isEmpty(whereClause, true))
			{
				logger.debug("matchesAsSource - return true because tableRefInfo.whereClause is empty; this={}", this);
				return true;
			}

			final String parsedWhere = parseWhereClause(zoomSource, whereClause, false);
			if (Check.isEmpty(parsedWhere))
			{
				return false;
			}

			final StringBuilder whereClauseEffective = new StringBuilder();
			whereClauseEffective.append(parsedWhere);
			whereClauseEffective.append(" AND ( ").append(keyColumnName).append("=").append(zoomSource.getRecord_ID()).append(" )");

			final boolean match = new Query(zoomSource.getCtx(), zoomSource.getTableName(), whereClauseEffective.toString(), zoomSource.getTrxName())
					.match();

			logger.debug("whereClause='{}' matches source='{}': {}", parsedWhere, zoomSource, match);
			return match;
		}
	}

	/**
	 * @return the <code>AD_Window_ID</code>
	 * @throws PORelationException if no <code>AD_Window_ID</code> can be found.
	 */
	private int getRefTableAD_Window_ID(final ITableRefInfo tableRefInfo, final boolean isSOTrx)
	{
		int windowId = tableRefInfo.getZoomAD_Window_ID_Override();
		if (windowId > 0)
		{
			return windowId;
		}

		if (isSOTrx)
		{
			windowId = tableRefInfo.getZoomSO_Window_ID();
		}
		else
		{
			windowId = tableRefInfo.getZoomPO_Window_ID();
		}
		if (windowId <= 0)
		{
			throw PORelationException.throwMissingWindowId(tableRefInfo.getName(), tableRefInfo.getTableName(), isSOTrx);
		}

		return windowId;
	}

	@ToString(exclude = "lookupDAO")
	public static final class Builder
	{
		private final transient ILookupDAO lookupDAO = Services.get(ILookupDAO.class);

		private Boolean directed;
		private String internalName;
		private int adRelationTypeId;
		private boolean isTableRecordIDTarget = false;

		private int sourceReferenceId = -1;
		private ITranslatableString sourceRoleDisplayName;
		private ITableRefInfo sourceTableRefInfo = null; // lazy

		private int targetReferenceId = -1;
		private ITranslatableString targetRoleDisplayName;
		private ITableRefInfo targetTableRefInfo = null; // lazy

		private Builder()
		{
			super();
		}

		public RelationTypeZoomProvider buildOrNull()
		{
			if (!isTableRecordIdTarget() && getSourceTableRefInfoOrNull() == null)
			{

				logger.info("Skip building {} because source tableRefInfo is null", this);
				return null;

			}
			if (getTargetTableRefInfoOrNull() == null)
			{
				logger.info("Skip building {} because target tableRefInfo is null", this);
				return null;
			}

			return new RelationTypeZoomProvider(this);
		}

		public Builder setAD_RelationType_ID(final int adRelationTypeId)
		{
			this.adRelationTypeId = adRelationTypeId;
			return this;
		}

		private int getAD_RelationType_ID()
		{
			Check.assume(adRelationTypeId > 0, "adRelationTypeId > 0");
			return adRelationTypeId;
		}

		private String getZoomInfoId()
		{
			return "relationType-" + getAD_RelationType_ID();
		}

		public Builder setInternalName(final String internalName)
		{
			this.internalName = internalName;
			return this;
		}

		private String getInternalName()
		{
			if (Check.isEmpty(internalName, true))
			{
				return getZoomInfoId();
			}
			return internalName;
		}

		public Builder setDirected(final boolean directed)
		{
			this.directed = directed;
			return this;
		}

		private boolean isDirected()
		{
			Check.assumeNotNull(directed, "Parameter directed is not null");
			return directed;
		}

		public Builder setSource_Reference_ID(final int sourceReferenceId)
		{
			this.sourceReferenceId = sourceReferenceId;
			sourceTableRefInfo = null; // reset
			return this;
		}

		private int getSource_Reference_ID()
		{
			Check.assume(sourceReferenceId > 0, "sourceReferenceId > 0");
			return sourceReferenceId;
		}

		private ITableRefInfo getSourceTableRefInfoOrNull()
		{
			if (sourceTableRefInfo == null)
			{
				sourceTableRefInfo = lookupDAO.retrieveTableRefInfo(getSource_Reference_ID());
			}
			return sourceTableRefInfo;
		}

		public Builder setSourceRoleDisplayName(final ITranslatableString sourceRoleDisplayName)
		{
			this.sourceRoleDisplayName = sourceRoleDisplayName;
			return this;
		}

		private ITranslatableString getSourceRoleDisplayName()
		{
			return sourceRoleDisplayName;
		}

		public Builder setTarget_Reference_AD(final int targetReferenceId)
		{
			this.targetReferenceId = targetReferenceId;
			targetTableRefInfo = null; // lazy
			return this;
		}

		private int getTarget_Reference_ID()
		{
			Check.assume(targetReferenceId > 0, "targetReferenceId > 0");
			return targetReferenceId;
		}

		private ITableRefInfo getTargetTableRefInfoOrNull()
		{
			if (targetTableRefInfo == null)
			{
				targetTableRefInfo = lookupDAO.retrieveTableRefInfo(getTarget_Reference_ID());
			}
			return targetTableRefInfo;
		}

		public Builder setTargetRoleDisplayName(final ITranslatableString targetRoleDisplayName)
		{
			this.targetRoleDisplayName = targetRoleDisplayName;
			return this;
		}

		public ITranslatableString getTargetRoleDisplayName()
		{
			return targetRoleDisplayName;
		}

		public Builder setIsTableRecordIdTarget(final boolean isReferenceTarget)
		{
			isTableRecordIDTarget = isReferenceTarget;
			return this;
		}

		private boolean isTableRecordIdTarget()
		{
			return isTableRecordIDTarget;
		}
	}
}
