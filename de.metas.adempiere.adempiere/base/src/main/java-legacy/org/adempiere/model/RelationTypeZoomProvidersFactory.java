package org.adempiere.model;

import static org.compiere.model.I_AD_Ref_Table.COLUMNNAME_AD_Reference_ID;
import static org.compiere.model.I_AD_Ref_Table.COLUMNNAME_OrderByClause;
import static org.compiere.model.I_AD_Ref_Table.COLUMNNAME_WhereClause;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.service.IADReferenceDAO.ADRefListItem;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.PORelationException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.I_AD_RelationType;
import org.compiere.model.POInfo;
import org.compiere.model.X_AD_Reference;
import org.compiere.model.X_AD_RelationType;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;

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

public final class RelationTypeZoomProvidersFactory
{
	private static final Logger logger = LogManager.getLogger(RelationTypeZoomProvidersFactory.class);
	private static final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

	public static final transient RelationTypeZoomProvidersFactory instance = new RelationTypeZoomProvidersFactory();

	private final static String SQL_Default_RelationType = createSelectFrom()
			+ createSharedWhereClause()
			+ createDefaultRelationTypeSpecificWhereClause()
			+ createOrderBy();

	private final static String SQL_TableRecordIDReference_RelationType = createSelectFrom()
			+ createSharedWhereClause()
			+ createTableRecordIDReferenceRelationTypeSpecificWhereClause()
			+ createOrderBy();

	private static String createSelectFrom()
	{
		return "  SELECT " //
				+ "    rt.AD_RelationType_ID AS " + I_AD_RelationType.COLUMNNAME_AD_RelationType_ID
				+ ",   rt.Name AS " + I_AD_RelationType.COLUMNNAME_Name
				+ ",   rt.IsDirected AS " + I_AD_RelationType.COLUMNNAME_IsDirected
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

	private final CCache<String, List<RelationTypeZoomProvider>> sourceTableName2zoomProviders = CCache.newLRUCache(I_AD_RelationType.Table_Name + "#ZoomProvidersBySourceTableName", 100, 0);


	public List<RelationTypeZoomProvider> getZoomProvidersByZoomOriginTableName(final String zoomOriginTableName)
	{
		return sourceTableName2zoomProviders.getOrLoad(zoomOriginTableName, () -> retrieveZoomProvidersBySourceTableName(zoomOriginTableName));
	}

	public List<RelationTypeZoomProvider> retrieveZoomProvidersBySourceTableName(final String zoomOriginTableName)
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
		final int keyColumnId = zoomOriginPOInfo.getAD_Column_ID(keyColumnName);

		final Object[] sqlParamsDefaultRelationType = new Object[] { adTableId, keyColumnId };

		final List<RelationTypeZoomProvider> relationTypeZoomProviders = new ArrayList<>();

		relationTypeZoomProviders.addAll(runRelationTypeSQLQuery(SQL_Default_RelationType, sqlParamsDefaultRelationType));

		relationTypeZoomProviders.addAll(runRelationTypeSQLQuery(SQL_TableRecordIDReference_RelationType, null));

		logger.info("There are {} matching types for {}", relationTypeZoomProviders.size(), zoomOriginTableName);
		
		return relationTypeZoomProviders;
	}

	private static List<RelationTypeZoomProvider> runRelationTypeSQLQuery(final String sqlQuery, final Object[] sqlParams)
	{

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			pstmt = DB.prepareStatement(sqlQuery, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final List<RelationTypeZoomProvider> result = retrieveZoomProviders(rs);
			
			return result;
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

	private static List<RelationTypeZoomProvider> retrieveZoomProviders(final ResultSet rs) throws SQLException
	{

		final Properties ctx = Env.getCtx();

		final List<RelationTypeZoomProvider> result = new ArrayList<>();
		final Set<Integer> alreadySeen = new HashSet<>();

		while (rs.next())
		{
			final int adRelationTypeId = rs.getInt(I_AD_RelationType.COLUMNNAME_AD_RelationType_ID);
			if (!alreadySeen.add(adRelationTypeId))
			{
				continue;
			}

			final I_AD_RelationType relationType = InterfaceWrapperHelper.create(ctx, adRelationTypeId, I_AD_RelationType.class, ITrx.TRXNAME_None);

			try
			{
				final RelationTypeZoomProvider zoomProviderForRelType = findZoomProvider(relationType);

				if (zoomProviderForRelType == null)
				{
					continue;
				}

				result.add(zoomProviderForRelType);
			}
			catch (Exception ex)
			{
				logger.warn("Failed creating zoom provider for {}. Skipped.", relationType, ex);
			}
		}
		return result;

	}
	

	@VisibleForTesting
	protected static RelationTypeZoomProvider findZoomProvider(final I_AD_RelationType relationType)
	{
		final ADRefListItem roleSourceItem = adReferenceDAO.retrieveListItemOrNull(X_AD_RelationType.ROLE_SOURCE_AD_Reference_ID, relationType.getRole_Source());
		final ITranslatableString roleSourceDisplayName = roleSourceItem == null ? null : roleSourceItem.getName();

		final ADRefListItem roleTargetItem = adReferenceDAO.retrieveListItemOrNull(X_AD_RelationType.ROLE_TARGET_AD_Reference_ID, relationType.getRole_Target());
		final ITranslatableString roleTargetDisplayName = roleTargetItem == null ? null : roleTargetItem.getName();

		final RelationTypeZoomProvider zoomProvider = RelationTypeZoomProvider.builder()
				.setDirected(relationType.isDirected())
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

		return zoomProvider;
	}
	

	public RelationTypeZoomProvider getZoomProviderBySourceTableNameAndInternalName(final String zoomOriginTableName, final String internalName)
	{
		return getZoomProvidersByZoomOriginTableName(zoomOriginTableName)
				.stream()
				.filter(zoomProvider -> Objects.equals(internalName, zoomProvider.getInternalName()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No zoom provider found for sourceTableName=" + zoomOriginTableName + ", internalName=" + internalName));
	}
}
