/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.references.related_documents;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.document.references.related_documents.generic.DefaultGenericZoomInfoDescriptorsRepository;
import de.metas.document.references.related_documents.generic.GenericZoomInfoDescriptor;
import de.metas.document.references.related_documents.generic.GenericZoomInfoDescriptorsRepository;
import de.metas.document.references.related_documents.generic.TargetColumnInfo;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMap;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMapRepository;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.lang.Priority;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Window;
import org.compiere.model.I_M_RMA;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Generic provider of zoom targets.
 *
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and RelationTypes
 */
@Component
		/* package */ class GenericZoomProvider implements IZoomProvider
{
	private static final Logger logger = LogManager.getLogger(GenericZoomProvider.class);

	private final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;

	private final CCache<String, ImmutableList<GenericZoomInfoDescriptor>> descriptorsBySourceKeyColumnName = CCache.<String, ImmutableList<GenericZoomInfoDescriptor>>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(1000) // NOTE this is confusing BUT in case of LRU, initialCapacity is used as maxSize
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.tableName(I_AD_Window.Table_Name)
			.additionalTableNameToResetFor(I_AD_Table.Table_Name)
			.additionalTableNameToResetFor(I_AD_Column.Table_Name)
			.build();

	private final GenericZoomInfoDescriptorsRepository genericZoomInfoDescriptorsRepository = new DefaultGenericZoomInfoDescriptorsRepository();

	private final Priority zoomInfoPriority = Priority.LOWEST;

	GenericZoomProvider(
			@NonNull final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository)
	{
		this.customizedWindowInfoMapRepository = customizedWindowInfoMapRepository;
	}

	@Override
	public ImmutableList<ZoomInfoCandidateGroup> retrieveZoomInfos(
			@NonNull final IZoomSource source,
			@Nullable final AdWindowId targetWindowId)
	{
		final List<GenericZoomInfoDescriptor> zoomInfoDescriptors = getZoomInfoDescriptors(source.getKeyColumnNameOrNull());
		if (zoomInfoDescriptors.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<ZoomInfoCandidateGroup> result = ImmutableList.builder();
		for (final GenericZoomInfoDescriptor zoomInfoDescriptor : zoomInfoDescriptors)
		{
			final AdWindowId windowId = zoomInfoDescriptor.getTargetWindowId();
			if (targetWindowId != null && !AdWindowId.equals(targetWindowId, windowId))
			{
				continue;
			}

			final ZoomInfoCandidateGroup.ZoomInfoCandidateGroupBuilder groupBuilder = ZoomInfoCandidateGroup.builder();
			for (final TargetColumnInfo columnInfo : zoomInfoDescriptor.getTargetColumns())
			{
				final MQuery query = buildMQuery(zoomInfoDescriptor, columnInfo, source);
				final ZoomInfoRecordsCountSupplier recordsCountSupplier = createRecordsCountSupplier(query, zoomInfoDescriptor, source.getTableName());

				groupBuilder.candidate(
						ZoomInfoCandidate.builder()
								.id(ZoomInfoId.ofString(Joiner.on("-")
																.skipNulls()
																.join("generic", windowId.getRepoId(), columnInfo.getColumnName())))
								.internalName(zoomInfoDescriptor.getTargetWindowInternalName())
								.targetWindow(ZoomTargetWindow.ofAdWindowIdAndCategory(windowId, columnInfo.getColumnName()))
								.priority(zoomInfoPriority)
								.query(query)
								.windowCaption(zoomInfoDescriptor.getName())
								.filterByFieldCaption(columnInfo.getCaption())
								.recordsCountSupplier(recordsCountSupplier)
								.build());
			}

			result.add(groupBuilder.build());
		}

		return result.build();
	}

	private List<GenericZoomInfoDescriptor> getZoomInfoDescriptors(final String sourceKeyColumnName)
	{
		if (sourceKeyColumnName == null)
		{
			return ImmutableList.of();
		}

		return descriptorsBySourceKeyColumnName.getOrLoad(sourceKeyColumnName, this::retrieveZoomInfoDescriptors);
	}

	private ImmutableList<GenericZoomInfoDescriptor> retrieveZoomInfoDescriptors(@NonNull final String sourceKeyColumnName)
	{
		final CustomizedWindowInfoMap customizedWindowInfoMap = customizedWindowInfoMapRepository.get();
		return genericZoomInfoDescriptorsRepository
				.getZoomInfoDescriptors(sourceKeyColumnName)
				.stream()
				.filter(descriptor -> customizedWindowInfoMap.isTopLevelCustomizedWindow(descriptor.getTargetWindowId()))
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

	private static MQuery buildMQuery(
			final GenericZoomInfoDescriptor zoomInfoDescriptor,
			final TargetColumnInfo targetColumn,
			final IZoomSource source)
	{
		final String targetTableName = zoomInfoDescriptor.getTargetTableName();
		final String targetColumnName = targetColumn.getColumnName();

		//
		// Zoom by dynamic references AD_Table_ID/Record_ID
		// task "Zoomable Record_IDs" (03921)
		if (targetColumn.isDynamic())
		{
			final MQuery query = new MQuery(targetTableName);
			query.addRestriction("AD_Table_ID", Operator.EQUAL, source.getAD_Table_ID());
			query.addRestriction("Record_ID", Operator.EQUAL, source.getRecord_ID());
			query.setZoomTableName(targetTableName);
			// query.setZoomColumnName(po.get_KeyColumns()[0]);
			query.setZoomValue(source.getRecord_ID());

			return query;
		}
		else
		{
			final MQuery query = new MQuery(targetTableName);
			if (targetColumn.isVirtualTargetColumnName())
			{
				// TODO: find a way to specify restriction's ColumnName and ColumnSql
				final String columnSql = targetColumn.getVirtualColumnSql();
				query.addRestriction("(" + columnSql + ") = " + source.getRecord_ID());
			}
			else
			{
				query.addRestriction(targetColumnName, Operator.EQUAL, source.getRecord_ID());
			}

			if (!Check.isBlank(zoomInfoDescriptor.getTabSqlWhereClause()))
			{
				query.addRestriction(zoomInfoDescriptor.getTabSqlWhereClause());
			}

			query.setZoomTableName(targetTableName);
			query.setZoomColumnName(source.getKeyColumnNameOrNull());
			query.setZoomValue(source.getRecord_ID());

			return query;
		}
	}

	private static ZoomInfoRecordsCountSupplier createRecordsCountSupplier(
			final MQuery query,
			final GenericZoomInfoDescriptor zoomInfoDescriptor,
			final String sourceTableName)
	{
		final String sql = buildCountSQL(query, zoomInfoDescriptor, sourceTableName);
		return () -> {
			try
			{
				return DB.getSQLValueEx(ITrx.TRXNAME_None, sql);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed counting records in {} for {} using SQL: {}", sourceTableName, zoomInfoDescriptor, sql, ex);
				return 0;
			}
		};
	}

	private static String buildCountSQL(
			final MQuery query,
			final GenericZoomInfoDescriptor zoomInfoDescriptor,
			final String sourceTableName)
	{
		String sqlCount = "SELECT COUNT(1) FROM " + query.getTableName()
				+ " WHERE " + query.getWhereClause(false)
				+ " AND AD_Client_ID IN (" + ClientId.SYSTEM.getRepoId() + ", " + ClientId.METASFRESH.getRepoId() + ")";

		SOTrx soTrx = zoomInfoDescriptor.getSoTrx();
		if (soTrx != null && zoomInfoDescriptor.isTargetHasIsSOTrxColumn())
		{
			//
			// For RMA, Material Receipt window should be loaded for
			// IsSOTrx=true and Shipment for IsSOTrx=false
			// TODO: fetch the additional SQL from window's first tab where clause
			final AdWindowId AD_Window_ID = zoomInfoDescriptor.getTargetWindowId();
			if (I_M_RMA.Table_Name.equals(sourceTableName) && (AD_Window_ID.getRepoId() == 169 || AD_Window_ID.getRepoId() == 184))
			{
				soTrx = soTrx.invert();
			}

			// TODO: handle the case when IsSOTrx is a virtual column

			sqlCount += " AND IsSOTrx=" + DB.TO_BOOLEAN(soTrx.toBoolean());
		}

		return sqlCount;
	}
}
