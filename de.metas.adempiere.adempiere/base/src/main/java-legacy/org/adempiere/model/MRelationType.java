/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 2009 www.metas.de *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
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
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.PORelationException;
import org.adempiere.model.ZoomInfoFactory.IZoomSource;
import org.adempiere.model.ZoomInfoFactory.POZoomSource;
import org.adempiere.model.ZoomInfoFactory.ZoomInfo;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.I_AD_Table;
import org.compiere.model.Lookup;
import org.compiere.model.MColumn;
import org.compiere.model.MQuery;
import org.compiere.model.MRefTable;
import org.compiere.model.MReference;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

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
	 * column of the reference's AD_Ref_Table entries. See {@link #retrieveTypes(PO, int)}.
	 * <p>
	 * <b>Warning:</b> Doesn't support POs with more or less than one key column.
	 */
	final static String SQL =              //
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
	 * @param po used to retrieve all relation matching types. A matching relation type needs to fulfill the following:
	 *            <ul>
	 *            <li>if directed, then its {@link #getAD_Reference_Source()} needs to be an {@link I_AD_Reference} record
	 *            whose {@link I_AD_Ref_Table}'s {@value I_AD_Ref_Table#COLUMNNAME_AD_Table_ID} has the given <code>po</code>'s table
	 *            and whose {@link I_AD_Ref_Table#COLUMNNAME_AD_Key} has the given <code>po</code>'s (first) key column.</li>
	 *            <li>if not directed, then also its {@link #getAD_Reference_Target()} is evaluated in a way similar to the source reference.</li>
	 *            </ul>
	 * @param AD_Window_ID sometimes needed with undirected relations types, in order to decide which is the "destination" (as opposed to "soruce" or target") reference.
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
		final Object[] sqlParams = new Object[] { source.getAD_Table_ID(), colId };
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

	/**
	 * Loads actual {@link MRelationType}s from the given result set and verifies if there are any actual zoom targets for each of the given relation types. Those types which have a zoom target are returned.
	 *
	 * @param po needed to identify the destination (as opposed to "source" or "target") <code>AD_Window_ID</code> if a relation type is not directed.
	 * @param AD_Window_ID same as <code>po</code>
	 * @param rs result set containing the candidate relation types
	 * @return a list of relation types whose
	 * @throws SQLException
	 */
	private static List<IZoomProvider> evalResultSet(final IZoomSource source, final ResultSet rs) throws SQLException	{
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

			// figure out which AD_reference is the destination relative to the given po and AD_Window_ID
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

	/**
	 *
	 * @param po for non-directed relation types both the <code>po</code> and <code>AD_Window_ID</code> are used to decide if we need to return the <code>AD_Reference_ID</code> or the relation type's source reference or of its target reference.
	 * @param AD_Window_ID
	 * @return
	 */
	@Deprecated
	private int findDestinationRefId(final PO po, final int windowId)	{
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

		Check.errorIf(destinationRefId == -1, "No destinationRefId was found for AD_Window_ID={} and IZoomSource={}", source.getAD_Window_ID(), source);
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

		final int id = new Query(source.getCtx(), source.getTableName(), whereClause.toString(), source.getTrxName())
			.setOrderBy(keyColumn)
			.firstId(); // using firstId might be a bit cheaper, because the DBMS might not have to load the actual row.
		final boolean match = id > 0;

		logger.debug("whereClause='{}' matches source='{}': {}", parsedWhere, source, match);
		return match;
	}

	/**
	 * Parses given <code>where</code>
	 * 
	 * @param source zoom source
	 * @param where where clause to be parsed
	 * @param throwEx true if an exception shall be thrown in case the parsing failed.
	 * @return parsed where clause or empty string in case parsing failed and throwEx is <code>false</code>
	 */
	public static String parseWhereClause(final IZoomSource source, final String where, final boolean throwEx)
	{
		final IStringExpression whereExpr = IStringExpression.compileOrDefault(where, IStringExpression.NULL);
		if(whereExpr.isNullExpression())
		{
			return "";
		}
		
		final Evaluatee evalCtx = source.createEvaluationContext();
		final OnVariableNotFound onVariableNotFound = throwEx ? OnVariableNotFound.Fail : OnVariableNotFound.ReturnNoResult;
		final String whereParsed = whereExpr.evaluate(evalCtx, onVariableNotFound);
		if(whereExpr.isNoResult(whereParsed))
		{
			// NOTE: logging as debug instead of warning because this might be a standard use case,
			// i.e. we are checking if a given where clause has some results, so the method was called with throwEx=false
			// and if we reached this point it means one of the context variables were not present and it has no default value.
			// This is perfectly normal, a default value is really not needed because we don't want to execute an SQL command
			// which would return no result. It's much more efficient to stop here.
			logger.debug("Could not parse where clause:\n{} \n EvalCtx: {} \n ZoomSource: {}", where, evalCtx, source);
			return "";
		}
		
		return whereParsed;
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
	 * @return a list with one {@link ZoomInfo} for this relation type.
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
		Check.errorIf(Check.isEmpty(display), "Found no display string for, refTable={}, AD_Window_ID={}", refTable, source.getAD_Window_ID());

		return ImmutableList.of(ZoomInfoFactory.ZoomInfo.of(windowId, query, display));
	}

	@Deprecated
	private MQuery mkQuery(final PO po, final I_AD_Ref_Table refTable)
	{
		final IZoomSource source = POZoomSource.of(po);
		return mkQuery(source, refTable);
	}

	private MQuery mkQuery(final IZoomSource source, final I_AD_Ref_Table refTable)
	{
		// FIXME: avoid direct SQL where clause because it's not friendly with webui
		
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

			final List<PO> targets = MRelation.retrieveDestinations(getCtx(), this, source.getAD_Table_ID(), source.getRecord_ID(), get_TrxName());
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

	/**
	 *
	 * @param po
	 * @param refTable
	 *
	 * @return the <code>AD_Window_ID</code> for the given <code>refTable</code> or <code>PO</code>
	 * @throws PORelationException if no <code>AD_Window_ID</code> can be found.
	 */
	@Deprecated
	public int retrieveWindowID(final PO po, final MRefTable refTable)
	{
		final IZoomSource source = POZoomSource.of(po);
		return retrieveWindowID(source, refTable);
	}

	private int retrieveWindowID(final IZoomSource source, final I_AD_Ref_Table refTable)
	{
		int windowId = refTable.getAD_Window_ID();
		if(windowId > 0)
		{
			return windowId;
		}
		
		
		final I_AD_Table table = refTable.getAD_Table();
		final boolean isSOTrx = source.isSOTrx();
		if (isSOTrx)
		{
			windowId = table.getAD_Window_ID();
		}
		else
		{
			windowId = table.getPO_Window_ID();
		}
		if (windowId <= 0)
		{
			PORelationException.throwMissingWindowId(getAD_Reference_Target().getName(), table.getName(), isSOTrx);
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
		final String sqlCommon = " FROM " + query.getZoomTableName() + " WHERE " + query.getWhereClause(false);

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
