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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.PORelationException;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_RelationType;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class RelationTypeZoomProvidersFactory
{
	private static final Logger logger = LogManager.getLogger(RelationTypeZoomProvidersFactory.class);

	public static final transient RelationTypeZoomProvidersFactory instance = new RelationTypeZoomProvidersFactory();

	/**
	 * Selection for those relation types whose AD_Reference(s) might match a given PO. Only evaluates the table and key
	 * column of the reference's AD_Ref_Table entries. See {@link #retrieveTypes(PO, int)}.
	 * <p>
	 * <b>Warning:</b> Doesn't support POs with more or less than one key column.
	 */
	private final static String SQL =                 //
	"  SELECT " //
			+ "    rt.AD_RelationType_ID AS " + I_AD_RelationType.COLUMNNAME_AD_RelationType_ID //
			+ ",   rt.Name AS " + I_AD_RelationType.COLUMNNAME_Name //
			+ ",   rt.IsDirected AS " + I_AD_RelationType.COLUMNNAME_IsDirected //
			+ ",   ref.AD_Reference_ID AS " + COLUMNNAME_AD_Reference_ID //
			+ ",   tab.WhereClause AS " + COLUMNNAME_WhereClause //
			+ ",   tab.OrderByClause AS " + COLUMNNAME_OrderByClause //
			+ "  FROM" //
			+ "    AD_RelationType rt, AD_Reference ref, AD_Ref_Table tab" //
			+ "  WHERE " //
			+ "    rt.IsActive='Y'" //
			+ "    AND ref.IsActive='Y'" //
			+ "    AND ref.ValidationType='T'" // must have table validation
			+ "    AND (" // join the source AD_Reference
			+ "      rt.AD_Reference_Source_ID=ref.AD_Reference_ID" //
			+ "      OR (" // not directed? -> also join the target AD_Reference
			+ "        rt.IsDirected='N' " //
			+ "        AND rt.AD_Reference_Target_ID=ref.AD_Reference_ID" //
			+ "      )" //
			+ "    )" //
			+ "    AND tab.IsActive='Y'" // Join the AD_Reference's AD_Ref_Table
			+ "    AND tab.AD_Reference_ID=ref.AD_Reference_ID" //
			+ "    AND tab.AD_Table_ID=?" //
			+ "    AND tab.AD_Key=?" //
			+ "  ORDER BY rt.Name";

	private final CCache<String, List<RelationTypeZoomProvider>> sourceTableName2zoomProviders = CCache.newLRUCache(I_AD_RelationType.Table_Name + "#ZoomProvidersBySourceTableName", 100, 0);

	private RelationTypeZoomProvidersFactory()
	{
		super();
	}

	public List<RelationTypeZoomProvider> getZoomProvidersBySourceTableName(final String sourceTableName)
	{
		return sourceTableName2zoomProviders.getOrLoad(sourceTableName, () -> retrieveZoomProvidersBySourceTableName(sourceTableName));
	}

	public RelationTypeZoomProvider getZoomProviderBySourceTableNameAndInternalName(final String sourceTableName, final String internalName)
	{
		return getZoomProvidersBySourceTableName(sourceTableName)
				.stream()
				.filter(zoomProvider -> Objects.equals(internalName, zoomProvider.getInternalName()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No zoom provider found for sourceTableName=" + sourceTableName + ", internalName=" + internalName));
	}

	private List<RelationTypeZoomProvider> retrieveZoomProvidersBySourceTableName(final String tableName)
	{
		Check.assumeNotEmpty(tableName, "tableName is not empty");

		final POInfo poInfo = POInfo.getPOInfo(tableName);
		final String keyColumnName = poInfo.getKeyColumnName();
		if (keyColumnName == null)
		{
			logger.error("{} does not have a single key column", tableName);
			throw PORelationException.throwWrongKeyColumnCount(tableName, poInfo.getKeyColumnNames());
		}

		final int adTableId = poInfo.getAD_Table_ID();
		final int keyColumnId = poInfo.getAD_Column_ID(keyColumnName);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final Object[] sqlParams = new Object[] { adTableId, keyColumnId };
		try
		{
			pstmt = DB.prepareStatement(SQL, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final List<RelationTypeZoomProvider> result = retrieveZoomProviders(rs);
			logger.info("There are {} matching types for {}", result.size(), tableName);

			return result;
		}
		catch (final SQLException e)
		{
			throw new DBException(e, SQL, sqlParams);
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

			result.add(RelationTypeZoomProvider.builder()
					.setDirected(relationType.isDirected())
					.setExplicit(relationType.isExplicit())
					.setAD_RelationType_ID(relationType.getAD_RelationType_ID())
					.setInternalName(relationType.getInternalName())
					//
					.setSource_Reference_AD(relationType.getAD_Reference_Source_ID())
					.setSourceRoleDisplayName(relationType.getRole_Source())
					//
					.setTarget_Reference_AD(relationType.getAD_Reference_Target_ID())
					.setTargetRoleDisplayName(relationType.getRole_Target())
					//
					.build());
		}
		return result;
	}
}
