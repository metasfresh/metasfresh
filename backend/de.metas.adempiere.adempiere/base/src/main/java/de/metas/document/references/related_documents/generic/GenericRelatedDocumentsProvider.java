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

package de.metas.document.references.related_documents.generic;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsCountSupplier;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
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
public class GenericRelatedDocumentsProvider implements IRelatedDocumentsProvider
{
	private static final Logger logger = LogManager.getLogger(GenericRelatedDocumentsProvider.class);

	private final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;

	private final CCache<String, ImmutableList<GenericRelatedDocumentDescriptor>> descriptorsBySourceKeyColumnName = CCache.<String, ImmutableList<GenericRelatedDocumentDescriptor>>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(1000) // NOTE this is confusing BUT in case of LRU, initialCapacity is used as maxSize
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.tableName(I_AD_Window.Table_Name)
			.additionalTableNameToResetFor(I_AD_Table.Table_Name)
			.additionalTableNameToResetFor(I_AD_Column.Table_Name)
			.build();

	private final GenericRelatedDocumentDescriptorsRepository genericRelatedDocumentDescriptorsRepository = new DefaultGenericRelatedDocumentDescriptorsRepository();

	private final Priority relatedDocumentsPriority = Priority.LOWEST;

	GenericRelatedDocumentsProvider(
			@NonNull final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository)
	{
		this.customizedWindowInfoMapRepository = customizedWindowInfoMapRepository;
	}

	@Override
	public ImmutableList<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		final List<GenericRelatedDocumentDescriptor> descriptors = getDescriptors(fromDocument.getKeyColumnNameOrNull());
		if (descriptors.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<RelatedDocumentsCandidateGroup> result = ImmutableList.builder();
		for (final GenericRelatedDocumentDescriptor descriptor : descriptors)
		{
			final AdWindowId windowId = descriptor.getTargetWindowId();
			if (targetWindowId != null && !AdWindowId.equals(targetWindowId, windowId))
			{
				continue;
			}

			final RelatedDocumentsCandidateGroup.RelatedDocumentsCandidateGroupBuilder groupBuilder = RelatedDocumentsCandidateGroup.builder();
			for (final GenericTargetColumnInfo columnInfo : descriptor.getTargetColumns())
			{
				final MQuery query = buildMQuery(descriptor, columnInfo, fromDocument);
				final RelatedDocumentsCountSupplier recordsCountSupplier = createRecordsCountSupplier(query, descriptor, fromDocument.getTableName());

				groupBuilder.candidate(
						RelatedDocumentsCandidate.builder()
								.id(RelatedDocumentsId.ofString(Joiner.on("-")
																.skipNulls()
																.join("generic", windowId.getRepoId(), columnInfo.getColumnName())))
								.internalName(descriptor.getTargetWindowInternalName())
								.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowIdAndCategory(windowId, columnInfo.getColumnName()))
								.priority(relatedDocumentsPriority)
								.query(query)
								.windowCaption(descriptor.getName())
								.filterByFieldCaption(columnInfo.getCaption())
								.documentsCountSupplier(recordsCountSupplier)
								.build());
			}

			result.add(groupBuilder.build());
		}

		return result.build();
	}

	private List<GenericRelatedDocumentDescriptor> getDescriptors(@Nullable final String sourceKeyColumnName)
	{
		if (sourceKeyColumnName == null)
		{
			return ImmutableList.of();
		}

		return descriptorsBySourceKeyColumnName.getOrLoad(sourceKeyColumnName, this::retrieveDescriptors);
	}

	private ImmutableList<GenericRelatedDocumentDescriptor> retrieveDescriptors(@NonNull final String sourceKeyColumnName)
	{
		final CustomizedWindowInfoMap customizedWindowInfoMap = customizedWindowInfoMapRepository.get();
		return genericRelatedDocumentDescriptorsRepository
				.getRelatedDocumentDescriptors(sourceKeyColumnName)
				.stream()
				.filter(descriptor -> customizedWindowInfoMap.isTopLevelCustomizedWindow(descriptor.getTargetWindowId()))
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

	private static MQuery buildMQuery(
			final GenericRelatedDocumentDescriptor descriptor,
			final GenericTargetColumnInfo targetColumn,
			final IZoomSource fromDocument)
	{
		final String targetTableName = descriptor.getTargetTableName();
		final String targetColumnName = targetColumn.getColumnName();

		//
		// Zoom by dynamic references AD_Table_ID/Record_ID
		// task "Zoomable Record_IDs" (03921)
		if (targetColumn.isDynamic())
		{
			final MQuery query = new MQuery(targetTableName);
			query.addRestriction("AD_Table_ID", Operator.EQUAL, fromDocument.getAD_Table_ID());
			query.addRestriction("Record_ID", Operator.EQUAL, fromDocument.getRecord_ID());
			query.setZoomTableName(targetTableName);
			// query.setZoomColumnName(po.get_KeyColumns()[0]);
			query.setZoomValue(fromDocument.getRecord_ID());

			return query;
		}
		else
		{
			final MQuery query = new MQuery(targetTableName);
			if (targetColumn.isVirtualTargetColumnName())
			{
				// TODO: find a way to specify restriction's ColumnName and ColumnSql
				final String columnSql = targetColumn.getVirtualColumnSql();
				query.addRestriction("(" + columnSql + ") = " + fromDocument.getRecord_ID());
			}
			else
			{
				query.addRestriction(targetColumnName, Operator.EQUAL, fromDocument.getRecord_ID());
			}

			if (!Check.isBlank(descriptor.getTabSqlWhereClause()))
			{
				query.addRestriction(descriptor.getTabSqlWhereClause());
			}

			query.setZoomTableName(targetTableName);
			query.setZoomColumnName(fromDocument.getKeyColumnNameOrNull());
			query.setZoomValue(fromDocument.getRecord_ID());

			return query;
		}
	}

	private static RelatedDocumentsCountSupplier createRecordsCountSupplier(
			final MQuery query,
			final GenericRelatedDocumentDescriptor descriptor,
			final String sourceTableName)
	{
		final String sql = buildCountSQL(query, descriptor, sourceTableName);
		return () -> {
			try
			{
				return DB.getSQLValueEx(ITrx.TRXNAME_None, sql);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed counting records in {} for {} using SQL: {}", sourceTableName, descriptor, sql, ex);
				return 0;
			}
		};
	}

	private static String buildCountSQL(
			final MQuery query,
			final GenericRelatedDocumentDescriptor descriptor,
			final String sourceTableName)
	{
		String sqlCount = "SELECT COUNT(1) FROM " + query.getTableName()
				+ " WHERE " + query.getWhereClause(false)
				+ " AND AD_Client_ID IN (" + ClientId.SYSTEM.getRepoId() + ", " + ClientId.METASFRESH.getRepoId() + ")";

		SOTrx soTrx = descriptor.getSoTrx();
		if (soTrx != null && descriptor.isTargetHasIsSOTrxColumn())
		{
			//
			// For RMA, Material Receipt window should be loaded for
			// IsSOTrx=true and Shipment for IsSOTrx=false
			// TODO: fetch the additional SQL from window's first tab where clause
			final AdWindowId AD_Window_ID = descriptor.getTargetWindowId();
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
