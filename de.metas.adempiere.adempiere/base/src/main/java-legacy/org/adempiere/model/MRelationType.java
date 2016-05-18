/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2009 www.metas.de                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.model;

import static org.compiere.model.I_AD_Ref_Table.COLUMNNAME_AD_Reference_ID;
import static org.compiere.model.I_AD_Ref_Table.COLUMNNAME_OrderByClause;
import static org.compiere.model.I_AD_Ref_Table.COLUMNNAME_WhereClause;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.PORelationException;
import org.adempiere.model.ZoomInfoFactory.IZoomSource;
import org.adempiere.model.ZoomInfoFactory.POZoomSource;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.Lookup;
import org.compiere.model.MColumn;
import org.compiere.model.MQuery;
import org.compiere.model.MRefTable;
import org.compiere.model.MReference;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Formal definition for a set of data record pairs
 * 
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and RelationTypes
 */
public class MRelationType extends X_AD_RelationType implements IZoomProvider
{

	private static final Logger logger = LogManager.getLogger(MRelationType.class);

	/**
	 * Selection for those relation types whose AD_Reference(s) might match a given PO. Only evaluates the table and key
	 * column of the reference's AD_Ref_Table entries.
	 * <p>
	 * <b>Warning:</b> Doesn't support POs with more or less than one key column.
	 */
	final static String SQL = //
	"  SELECT " //
			+ "    rt.AD_RelationType_ID AS " + COLUMNNAME_AD_RelationType_ID //
			+ ",   rt.Name AS " + COLUMNNAME_Name //
			+ ",   rt.IsDirected AS " + COLUMNNAME_IsDirected //
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

	final static String SQL_WINDOW_NAME = "SELECT Name FROM AD_Window WHERE AD_WINDOW_ID=?";

	final static String SQL_WINDOW_NAME_TRL = "SELECT Name FROM AD_Window_Trl WHERE AD_WINDOW_ID=?";

	/**
	 * 
	 */
	private static final long serialVersionUID = 5486148151201672913L;

	private int destinationRefId;

	public MRelationType(Properties ctx, int AD_RelationType_ID, String trxName)
	{
		super(ctx, AD_RelationType_ID, trxName);
	}

	public MRelationType(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Returns the types that define a relation which contains the given PO. Explicit types are returned even if they
	 * don't actually contain the given PO.
	 * 
	 * @param po
	 * @return
	 */
	public static List<IZoomProvider> retrieveZoomProviders(final IZoomSource source)
	{
		final String keyColumn = source.getKeyColumnName();
		if (keyColumn == null)
		{

			logger.error("{} does not have a single key column", source);
			PORelationException.throwWrongKeyColumnCount(source);
		}

		final int colId = MColumn.getColumn_ID(source.getTableName(), keyColumn);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final Object[] sqlParams = new Object[]{source.getAD_Table_ID(), colId};
		try
		{
			pstmt = DB.prepareStatement(SQL, source.getTrxName());
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final List<IZoomProvider> result = evalResultSet(source, rs);
			logger.info("There are {} matching types for {}", result.size(), source);

			return result;
		}
		catch (SQLException e)
		{
			throw new DBException(e, SQL, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private String getDestinationRoleDisplay()
	{
		checkDestinationRefId();

		final Integer colIdx;
		final String keyValue;

		if (destinationRefId == getAD_Reference_Source_ID())
		{

			colIdx = this.get_ColumnIndex(COLUMNNAME_Role_Source);
			keyValue = getRole_Source();
		}
		else
		{
			colIdx = this.get_ColumnIndex(COLUMNNAME_Role_Target);
			keyValue = getRole_Target();
		}

		if (Check.isEmpty(keyValue))
		{
			return "";
		}
		final Lookup lookup = this.get_ColumnLookup(colIdx);
		return lookup.getDisplay(keyValue);
	}

	private String retrieveWindowName(final int windowId)
	{

		final boolean baseLanguage = Env.isBaseLanguage(Env.getCtx(), "AD_Window");
		final String sql = baseLanguage ? SQL_WINDOW_NAME : SQL_WINDOW_NAME_TRL;

		final PreparedStatement pstmt = DB.prepareStatement(sql, null);
		ResultSet rs = null;
		try
		{
			pstmt.setInt(1, windowId);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				return rs.getString(1);
			}
			return null;
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private static List<IZoomProvider> evalResultSet(final IZoomSource source, final ResultSet rs) throws SQLException
	{
		final List<IZoomProvider> result = new ArrayList<>();

		final Set<Integer> alreadySeen = new HashSet<>();

		while (rs.next())
		{
			final int relTypeId = rs.getInt(COLUMNNAME_AD_RelationType_ID);
			if (!alreadySeen.add(relTypeId))
			{
				continue;
			}

			final MRelationType newType = new MRelationType(source.getCtx(), relTypeId, source.getTrxName());

			// figure out which AD_reference is the destination relative to the given po and windowID

			newType.findDestinationRefId(source);

			final int sourceRefId;
			if (newType.destinationRefId == newType.getAD_Reference_Target_ID())
			{
				sourceRefId = newType.getAD_Reference_Source_ID();
			}
			else
			{
				sourceRefId = newType.getAD_Reference_Target_ID();
			}

			final String sourceWhereClause = MReference.retrieveRefTable(source.getCtx(), sourceRefId, source.getTrxName()).getWhereClause();
			if (Check.isEmpty(sourceWhereClause) || whereClauseMatches(source, sourceWhereClause))
			{
				result.add(newType);
			}
		}
		return result;
	}

	@Deprecated
	private int findDestinationRefId(final PO po, final int windowId)
	{
		final IZoomSource source = POZoomSource.of(po, windowId);
		return findDestinationRefId(source); 
	}

	private int findDestinationRefId(final IZoomSource source)
	{
		destinationRefId = -1;

		if (isDirected())
		{
			// the type is directed, so our destination is always the *target* reference
			destinationRefId = getAD_Reference_Target_ID();
		}
		else if (retrieveSourceTableName().equals(retrieveTargetTableName()))
		{
			// this relation type is from one table to the same table
			// use the window-id to distinguish
			final I_AD_Ref_Table sourceRefTable = MReference.retrieveRefTable(source.getCtx(), getAD_Reference_Source_ID(), source.getTrxName());

			if (source.getAD_Window_ID() == retrieveWindowID(source, sourceRefTable))
			{
				destinationRefId = getAD_Reference_Target_ID();
			}
			else
			{
				destinationRefId = getAD_Reference_Source_ID();
			}
		}
		else
		{

			if (source.getTableName().equals(retrieveSourceTableName()))
			{
				destinationRefId = getAD_Reference_Target_ID();
			}
			else
			{
				destinationRefId = getAD_Reference_Source_ID();
			}
		}

		assert destinationRefId != -1;
		return destinationRefId;
	}

	private static boolean whereClauseMatches(final IZoomSource source, final String where)
	{
		if (Check.isEmpty(where, true))
		{
			logger.debug("whereClause is empty. Returning true");
			return true;
		}

		final String parsedWhere = parseWhereClause(source, where, false);
		if (Check.isEmpty(parsedWhere))
		{
			return false;
		}
		final String keyColumn = source.getKeyColumnName();
		Check.assumeNotEmpty(keyColumn, "keyColumn is not empty for {}", source);
		
		final StringBuilder whereClause = new StringBuilder();
		whereClause.append(parsedWhere);
		whereClause.append(" AND ( ");
		whereClause.append(keyColumn);
		whereClause.append("=" + source.getRecord_ID() + " )");

		final PO result = new Query(source.getCtx(), source.getTableName(), whereClause.toString(), source.getTrxName())
			.setOrderBy(keyColumn)
			.first();
		final boolean match = result != null;

		logger.debug("whereClause='{}' matches source='{}': {}", parsedWhere, source, match);
		return match;
	}

	@Deprecated
	public static String parseWhereClause(final PO po, final String where, final boolean throwEx)
	{
		final IZoomSource source = POZoomSource.of(po); 
		return parseWhereClause(source, where, throwEx);
	}

	public static String parseWhereClause(final IZoomSource source, final String where, final boolean throwEx)
	{
		logger.debug("building private ctx instance containing the PO's String and int values");

		final Properties privateCtx = Env.deriveCtx(source.getCtx());

		if (source instanceof POZoomSource)
		{
			final PO po = ((POZoomSource)source).getPO();
			final POInfo poInfo = POInfo.getPOInfo(source.getTableName());
			for (int i = 0; i < poInfo.getColumnCount(); i++)
			{
				final Object val;
				final int dispType = poInfo.getColumnDisplayType(i);
				if (DisplayType.isID(dispType))
				{
					// make sure we get a 0 instead of a null for foreign keys
					val = po.get_ValueAsInt(i);
				}
				else
				{
					val = po.get_Value(i);
				}
	
				if (val == null)
				{
					continue;
				}
	
				if (val instanceof Integer)
				{
					Env.setContext(privateCtx, "#" + po.get_ColumnName(i), (Integer)val);
				}
				else if (val instanceof String)
				{
					Env.setContext(privateCtx, "#" + po.get_ColumnName(i), (String)val);
				}
			}
		}

		final String parsedWhere = Env.parseContext(privateCtx, Env.WINDOW_None, where, false);
		if (Check.isEmpty(parsedWhere) && throwEx)
		{
			throw new AdempiereException("parsedWhere is empty; where='" + where + "'; ctx=" + Env.getEntireContext(privateCtx));
		}
		logger.debug("whereClause='{}'; parsedWhere='{}'", where, parsedWhere);

		return parsedWhere;
	}

	public void checkDestinationRefId()
	{
		if (destinationRefId == 0)
		{
			throw new IllegalStateException(
					"Can't create a destination query when I don't know which one of the two AD_Reference_ID is the destination.");
		}
	}

	/**
	 * 
	 * @param po
	 * @return
	 */
	@Override
	public List<ZoomInfoFactory.ZoomInfo> retrieveZoomInfos(final IZoomSource source)
	{
		checkDestinationRefId();

		final MRefTable refTable = MReference.retrieveRefTable(getCtx(), destinationRefId, get_TrxName());

		final MQuery query = mkQuery(source, refTable);
		evaluateQuery(query);

		final int windowId = retrieveWindowID(source, refTable);

		String display = getDestinationRoleDisplay();
		if (Check.isEmpty(display))
		{
			display = retrieveWindowName(windowId);
		}
		assert !Check.isEmpty(display);

		return Collections.singletonList(ZoomInfoFactory.ZoomInfo.of(windowId, query, display));
	}

	@Deprecated
	private MQuery mkQuery(final PO po, final I_AD_Ref_Table refTable)
	{
		final IZoomSource source = POZoomSource.of(po);
		return mkQuery(source, refTable);
	}

	private MQuery mkQuery(final IZoomSource source, final I_AD_Ref_Table refTable)
	{
		final StringBuilder queryWhereClause = new StringBuilder();
		final String refTableWhereClause = refTable.getWhereClause();
		if (!Check.isEmpty(refTableWhereClause))
		{
			queryWhereClause.append(parseWhereClause(source, refTableWhereClause, true));
		}
		else
		{
			if (!isExplicit())
			{
				throw new AdempiereException("RefTable " + refTable + " has no whereClause, so RelationType " + this + " needs to be explicit");
			}
			queryWhereClause.append(" TRUE ");
		}
		if (isExplicit())
		{
			// "explicit" means that the where clause only defines a superset of possible relation elements.
			// Therefore, we now need to append the actually existing elements to the wehre clause.

			final String destinationKeyCol = MColumn.getColumnName(getCtx(), refTable.getAD_Key());

			queryWhereClause.append(" AND ").append(destinationKeyCol).append(" IN ( -99 ");

			final List<PO> targets = MRelation.retrieveDestinations(getCtx(), this, source.getAD_Table_ID(), source.getAD_Table_ID(), get_TrxName());
			for (final PO target : targets)
			{
				assert target.get_Table_ID() == refTable.getAD_Table_ID() : "target=" + target + "; refTable=" + refTable;
				queryWhereClause.append(", ");
				queryWhereClause.append(target.get_ID());
			}

			queryWhereClause.append(" )");
		}

		final MQuery query = new MQuery();

		query.addRestriction(queryWhereClause.toString());
		query.setZoomTableName(retrieveDestinationTableName());
		query.setZoomColumnName(retrieveDestinationKeyColName());

		return query;
	}

	@Deprecated
	private int retrieveWindowID(final PO po, final I_AD_Ref_Table refTable)
	{
		final IZoomSource source = POZoomSource.of(po);
		return retrieveWindowID(source, refTable);
	}
	
	private int retrieveWindowID(final IZoomSource source, final I_AD_Ref_Table refTable)
	{

		MTable table = null;

		int windowId = refTable.getAD_Window_ID();
		if (windowId == 0)
		{

			final int tableId = refTable.getAD_Table_ID();
			table = MTable.get(source.getCtx(), tableId);

			if (Env.isSOTrx(source.getCtx()))
			{
				windowId = table.getAD_Window_ID();
			}
			else
			{
				windowId = table.getPO_Window_ID();
			}
		}

		if (windowId == 0)
		{
			PORelationException.throwMissingWindowId(source, getAD_Reference_Target().getName(), table.getName(), Env.isSOTrx(source.getCtx()));
		}
		return windowId;

	}

	public static MRelationType retrieveForInternalName(final Properties ctx, final String internalName, final String trxName)
	{
		// TODO us197:
		// * Make 'InternalName' unique
		final String wc = COLUMNNAME_InternalName + "=?";

		return new Query(ctx, Table_Name, wc, trxName)
				.setParameters(internalName)
				.setOnlyActiveRecords(true)
				.firstOnly();
	}

	private static void evaluateQuery(final MQuery query)
	{
		final String sqlCommon =
				" FROM " + query.getZoomTableName() + " WHERE " + query.getWhereClause(false);

		final String sqlCount = "SELECT COUNT(*) " + sqlCommon;

		final int count = DB.getSQLValueEx(null, sqlCount);
		query.setRecordCount(count);

		if (count > 0)
		{
			final String sqlFirstKey = "SELECT " + query.getZoomColumnName() + sqlCommon;

			final int firstKey = DB.getSQLValueEx(null, sqlFirstKey);
			query.setZoomValue(firstKey);
		}
	}

	private String retrieveSourceTableName()
	{

		return retrieveTableName(getAD_Reference_Source_ID());
	}

	private String retrieveTargetTableName()
	{
		return retrieveTableName(getAD_Reference_Target_ID());
	}

	public String retrieveDestinationTableName()
	{
		return retrieveTableName(destinationRefId);
	}

	private String retrieveTableName(final int refId)
	{
		return MReference.retrieveRefTable(getCtx(), refId, get_TrxName()).getAD_Table().getTableName();
	}

	public String retrieveDestinationKeyColName()
	{
		final int keyColumnId = MReference.retrieveRefTable(getCtx(), destinationRefId, get_TrxName()).getAD_Key();
		return MColumn.getColumnName(getCtx(), keyColumnId);
	}

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer("MRelationType[");
		//
		sb.append(get_ID());
		sb.append(", InternalName=").append(getInternalName());
		sb.append(", Directed=").append(isDirected());
		sb.append(", Explicit=").append(isExplicit());

		sb.append(", AD_Reference_Destination_RefId=").append(destinationRefId);

		sb.append(", AD_Reference_Source_ID=").append(getAD_Reference_Source_ID());
		sb.append(", Role_Source=").append(getRole_Source());

		sb.append(", AD_Reference_Target_ID=").append(getAD_Reference_Target_ID());
		sb.append(", Role_Target=").append(getRole_Target()); //

		sb.append("]");

		return sb.toString();
	}

	/**
	 * 
	 * @param <T>
	 *            The po type to return. Note: As the caller has 'type' and 'sourcePO' specified, the destination POs'
	 *            table is clear. Therefore, the caller also knows the po type. If not, they can still use 'PO' iteself.
	 * @param sourcePO
	 * @return
	 */
	public <T extends PO> List<T> retrieveDestinations(final PO sourcePO)
	{
		if (this.isExplicit())
		{
			return MRelation.retrieveDestinations(sourcePO.getCtx(), this, sourcePO, sourcePO.get_TrxName());
		}
		final int destinationRefId = findDestinationRefId(sourcePO, -1);
		final MRefTable destinationRefTable = MReference.retrieveRefTable(getCtx(), destinationRefId, get_TrxName());

		final MQuery query = mkQuery(sourcePO, destinationRefTable);

		return new Query(sourcePO.getCtx(), query.getZoomTableName(), query.getWhereClause(false), sourcePO.get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(query.getZoomColumnName())
				.list();
	}
	
	public <T> List<T> retrieveDestinations(final PO sourcePO, final Class<T> clazz)
	{
		final List<PO> list = retrieveDestinations(sourcePO);
		final List<T> result = new ArrayList<T>(list.size());
		for (PO po : list)
		{
			T o = InterfaceWrapperHelper.create(po, clazz);
			result.add(o);
		}
		
		return result;
	}
}
