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

package de.metas.document.references.related_documents.relation_type;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ADRefListItem;
import de.metas.ad_reference.ADReferenceService;
import de.metas.cache.CCache;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMapRepository;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.PORelationException;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.I_AD_RelationType;
import org.compiere.model.POInfo;
import org.compiere.model.X_AD_Reference;
import org.compiere.model.X_AD_RelationType;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.compiere.model.I_AD_Ref_Table.COLUMNNAME_AD_Reference_ID;
import static org.compiere.model.I_AD_Ref_Table.COLUMNNAME_OrderByClause;
import static org.compiere.model.I_AD_Ref_Table.COLUMNNAME_WhereClause;

@Component
public final class RelationTypeRelatedDocumentsProvidersFactory implements IRelatedDocumentsProvider
{
	private static final Logger logger = LogManager.getLogger(RelationTypeRelatedDocumentsProvidersFactory.class);
	private final ADReferenceService adReferenceService;
	private final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;

	private final CCache<String, ImmutableList<SpecificRelationTypeRelatedDocumentsProvider>> providersBySourceTableName = CCache.newLRUCache(I_AD_RelationType.Table_Name + "#ZoomProvidersBySourceTableName", 100, 0);

	private final static String SQL_Default_RelationType = createSelectFrom()
			+ createSharedWhereClause()
			+ createDefaultRelationTypeSpecificWhereClause()
			+ createOrderBy();

	private final static String SQL_TableRecordIDReference_RelationType = createSelectFrom()
			+ createSharedWhereClause()
			+ createTableRecordIDReferenceRelationTypeSpecificWhereClause()
			+ createOrderBy();

	public RelationTypeRelatedDocumentsProvidersFactory(
			@NonNull final ADReferenceService adReferenceService,
			@NonNull final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository)
	{
		this.adReferenceService = adReferenceService;
		this.customizedWindowInfoMapRepository = customizedWindowInfoMapRepository;
	}

	private static String createSelectFrom()
	{
		return "  SELECT " //
				+ "    rt.AD_RelationType_ID AS " + I_AD_RelationType.COLUMNNAME_AD_RelationType_ID
				+ ",   rt.Name AS " + I_AD_RelationType.COLUMNNAME_Name
				+ ",   ref.AD_Reference_ID AS " + COLUMNNAME_AD_Reference_ID
				+ ",   tab.WhereClause AS " + COLUMNNAME_WhereClause
				+ ",   tab.OrderByClause AS " + COLUMNNAME_OrderByClause
				+ "  FROM "
				+ I_AD_RelationType.Table_Name + " rt, "
				+ I_AD_Reference.Table_Name + " ref, "
				+ I_AD_Ref_Table.Table_Name + " tab ";
	}

	private static String createSharedWhereClause()
	{
		return "  WHERE "
				+ "    rt.IsActive='Y'"
				+ "    AND ref.IsActive='Y'" //
				+ "    AND ref.ValidationType = '" + X_AD_Reference.VALIDATIONTYPE_TableValidation + "'"
				+ "    AND tab.IsActive='Y'"
				+ "    AND tab.AD_Reference_ID=ref.AD_Reference_ID ";
	}

	private static String createDefaultRelationTypeSpecificWhereClause()
	{
		return "    AND rt." + I_AD_RelationType.COLUMNNAME_IsTableRecordIdTarget + " = 'N'"
				+ "    AND tab.AD_Table_ID=?"
				+ "    AND tab.AD_Key=?"
				+ "    AND (" // join the source AD_Reference
				+ "      rt.AD_Reference_Source_ID=ref.AD_Reference_ID" //
				+ "      OR (" // not directed? -> also join the target AD_Reference
				+ "        rt.IsDirected='N' "
				+ "        AND rt.AD_Reference_Target_ID=ref.AD_Reference_ID"
				+ "      )"
				+ "    )";
	}

	private static String createTableRecordIDReferenceRelationTypeSpecificWhereClause()
	{
		return "    AND rt." + I_AD_RelationType.COLUMNNAME_IsTableRecordIdTarget + " = 'Y'"
				+ "   AND  rt." + I_AD_RelationType.COLUMNNAME_AD_Reference_Source_ID + " is null"
				+ "   AND   rt." + I_AD_RelationType.COLUMNNAME_AD_Reference_Target_ID + " =ref.AD_Reference_ID ";

	}

	private static String createOrderBy()
	{
		return "  ORDER BY rt.Name ";
	}

	@Override
	public List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		return getRelatedDocumentsProvidersBySourceDocumentTableName(fromDocument.getTableName())
				.stream()
				.flatMap(provider -> streamRelatedDocumentsCandidatesNoFail(provider, fromDocument, targetWindowId))
				.collect(ImmutableList.toImmutableList());
	}

	private Stream<RelatedDocumentsCandidateGroup> streamRelatedDocumentsCandidatesNoFail(
			@NonNull final SpecificRelationTypeRelatedDocumentsProvider provider,
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		try
		{
			return provider.retrieveRelatedDocumentsCandidates(fromDocument, targetWindowId).stream();
		}
		catch (final Exception ex)
		{
			logger.warn("Failed retrieving candidates from {} for {}/{}", provider, fromDocument, targetWindowId, ex);
			return Stream.empty();
		}
	}

	private ImmutableList<SpecificRelationTypeRelatedDocumentsProvider> getRelatedDocumentsProvidersBySourceDocumentTableName(final String sourceTableName)
	{
		return providersBySourceTableName.getOrLoad(sourceTableName, this::retrieveRelatedDocumentsProvidersBySourceTableName);
	}

	public ImmutableList<SpecificRelationTypeRelatedDocumentsProvider> retrieveRelatedDocumentsProvidersBySourceTableName(final String zoomOriginTableName)
	{
		Check.assumeNotEmpty(zoomOriginTableName, "tableName is not empty");

		final POInfo zoomOriginPOInfo = POInfo.getPOInfo(zoomOriginTableName);
		final String keyColumnName = zoomOriginPOInfo.getKeyColumnName();

		if (keyColumnName == null)
		{
			logger.error("{} does not have a single key column", zoomOriginTableName);
			throw PORelationException.throwWrongKeyColumnCount(zoomOriginTableName, zoomOriginPOInfo.getKeyColumnNames());
		}

		final int adTableId = zoomOriginPOInfo.getAD_Table_ID();
		final AdColumnId keyColumnId = zoomOriginPOInfo.getAD_Column_ID(keyColumnName);

		final Object[] sqlParamsDefaultRelationType = new Object[] { adTableId, keyColumnId };

		final ArrayList<SpecificRelationTypeRelatedDocumentsProvider> providers = new ArrayList<>();
		providers.addAll(runRelationTypeSQLQuery(SQL_Default_RelationType, sqlParamsDefaultRelationType));
		providers.addAll(runRelationTypeSQLQuery(SQL_TableRecordIDReference_RelationType, null));

		logger.debug("There are {} matching types for {}", providers.size(), zoomOriginTableName);

		return ImmutableList.copyOf(providers);
	}

	private List<SpecificRelationTypeRelatedDocumentsProvider> runRelationTypeSQLQuery(final String sqlQuery, @Nullable final Object[] sqlParams)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlQuery, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			return retrieveRelatedDocumentsProviders(rs);
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sqlQuery, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private List<SpecificRelationTypeRelatedDocumentsProvider> retrieveRelatedDocumentsProviders(final ResultSet rs) throws SQLException
	{
		final ArrayList<SpecificRelationTypeRelatedDocumentsProvider> result = new ArrayList<>();
		final Set<Integer> alreadySeen = new HashSet<>();

		while (rs.next())
		{
			final int adRelationTypeId = rs.getInt(I_AD_RelationType.COLUMNNAME_AD_RelationType_ID);
			if (!alreadySeen.add(adRelationTypeId))
			{
				continue;
			}

			final I_AD_RelationType relationType = loadOutOfTrx(adRelationTypeId, I_AD_RelationType.class);

			try
			{
				final SpecificRelationTypeRelatedDocumentsProvider providerForRelType = findRelatedDocumentsProvider(relationType);
				if (providerForRelType == null)
				{
					continue;
				}

				result.add(providerForRelType);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed creating related documents provider for {}. Skipped.", relationType, ex);
			}
		}

		return result;
	}

	@VisibleForTesting
	@Nullable
	SpecificRelationTypeRelatedDocumentsProvider findRelatedDocumentsProvider(@NonNull final I_AD_RelationType relationType)
	{
		final ADRefListItem roleSourceItem = adReferenceService.retrieveListItemOrNull(X_AD_RelationType.ROLE_SOURCE_AD_Reference_ID, relationType.getRole_Source());
		final ITranslatableString roleSourceDisplayName = roleSourceItem == null ? null : roleSourceItem.getName();

		final ADRefListItem roleTargetItem = adReferenceService.retrieveListItemOrNull(X_AD_RelationType.ROLE_TARGET_AD_Reference_ID, relationType.getRole_Target());
		final ITranslatableString roleTargetDisplayName = roleTargetItem == null ? null : roleTargetItem.getName();

		return SpecificRelationTypeRelatedDocumentsProvider.builder()
				.setAdReferenceService(adReferenceService)
				.setCustomizedWindowInfoMap(customizedWindowInfoMapRepository.get())
				.setAD_RelationType_ID(relationType.getAD_RelationType_ID())
				.setInternalName(relationType.getInternalName())
				//
				.setSource_Reference_ID(relationType.getAD_Reference_Source_ID())
				.setSourceRoleDisplayName(roleSourceDisplayName)
				//
				.setTarget_Reference_AD(relationType.getAD_Reference_Target_ID())
				.setTargetRoleDisplayName(roleTargetDisplayName)
				//
				.setIsTableRecordIdTarget(relationType.isTableRecordIdTarget())
				//
				.buildOrNull();
	}

	public SpecificRelationTypeRelatedDocumentsProvider getRelatedDocumentsProviderBySourceTableNameAndInternalName(final String zoomOriginTableName, final String internalName)
	{
		return getRelatedDocumentsProvidersBySourceDocumentTableName(zoomOriginTableName)
				.stream()
				.filter(provider -> Objects.equals(internalName, provider.getInternalName()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No related documents provider found for sourceTableName=" + zoomOriginTableName + ", internalName=" + internalName));
	}
}
