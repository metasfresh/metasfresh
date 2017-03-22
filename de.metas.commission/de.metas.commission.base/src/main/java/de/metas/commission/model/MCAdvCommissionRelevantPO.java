package de.metas.commission.model;

/*
 * #%L
 * de.metas.commission.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ILookupDAO.ITableRefInfo;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheIgnore;
import de.metas.logging.LogManager;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */
public class MCAdvCommissionRelevantPO extends X_C_AdvCommissionRelevantPO
{
	private static final Logger logger = LogManager.getLogger(MCAdvCommissionRelevantPO.class);

	final static String SQL_FOR_TABLE_ID = //
	"  SELECT cr.* " //
			+ "  FROM " //
			+ I_C_AdvCommissionRelevantPO.Table_Name
			+ " cr, AD_Reference ref, AD_Ref_Table tab" //
			+ "  WHERE " //
			+ "    cr.IsActive='Y'" //
			+ "    AND ref.IsActive='Y'" //
			+ "    AND ref.ValidationType='T'" // must have table validation
			+ "    AND tab.IsActive='Y'"
			+ "    AND cr.AD_Reference_ID=ref.AD_Reference_ID"
			+ "    AND tab.AD_Reference_ID=ref.AD_Reference_ID" //
			+ "    AND tab.AD_Table_ID=?";

	final static String SQL = //
	"  SELECT " //
			+ "    cr.Name AS "
			+ I_C_AdvCommissionRelevantPO.COLUMNNAME_Name //
			+ ",   cr.C_AdvCommissionRelevantPO_ID AS "
			+ I_C_AdvCommissionRelevantPO.COLUMNNAME_C_AdvCommissionRelevantPO_ID //
			+ ",   tab.WhereClause AS "
			+ I_AD_Ref_Table.COLUMNNAME_WhereClause //
			+ ",   cr.SeqNo AS "
			+ I_C_AdvCommissionRelevantPO.COLUMNNAME_SeqNo //
			+ "  FROM" //
			+ "    "
			+ I_C_AdvCommissionRelevantPO.Table_Name
			+ " cr, AD_Reference ref, AD_Ref_Table tab" //
			+ "  WHERE " //
			+ "    cr.IsActive='Y'" //
			+ "    AND ref.IsActive='Y'" //
			+ "    AND ref.ValidationType='T'" // must have table validation
			+ "    AND tab.IsActive='Y'"
			+ "    AND cr.AD_Reference_ID=ref.AD_Reference_ID"
			+ "    AND tab.AD_Reference_ID=ref.AD_Reference_ID" //
			+ "    AND tab.AD_Table_ID=?";

	/**
	 * 
	 */
	private static final long serialVersionUID = 5003425150516677287L;

	public MCAdvCommissionRelevantPO(final Properties ctx,
			final int C_AdvCommissionRelevantPO_ID, final String trxName)
	{
		super(ctx, C_AdvCommissionRelevantPO_ID, trxName);
	}

	public MCAdvCommissionRelevantPO(final Properties ctx, final ResultSet rs,
			final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Cached
	public static List<MCAdvCommissionRelevantPO> retrieveForTableId(
			@CacheCtx final Properties ctx, final int tableId,
			@CacheIgnore final String trxName)
	{
		final PreparedStatement pstmt = DB.prepareStatement(MCAdvCommissionRelevantPO.SQL_FOR_TABLE_ID, trxName);
		ResultSet rs = null;
		try
		{
			final List<MCAdvCommissionRelevantPO> result = new ArrayList<MCAdvCommissionRelevantPO>();

			pstmt.setInt(1, tableId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				result.add(new MCAdvCommissionRelevantPO(ctx, rs, trxName));
			}
			return result;
		}
		catch (final SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	/**
	 * 
	 * @param po
	 * @return
	 */
	/*
	 * IMPORTANT: don't annotate with @Cached, because for e.g. warehouse order, the result of this method is supposed to be different during successive calls with the same PO instance!
	 */
	public static MCAdvCommissionRelevantPO retrieveIfRelevant(final Object po)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(po);

		if (po instanceof I_C_AdvCommissionFactCand
				|| tableName == null
				|| tableName.startsWith("AD_"))
		{
			return null;
		}

		for (final MCAdvCommissionRelevantPO relevantPO : retrieveForTableId(InterfaceWrapperHelper.getCtx(po), MTable.getTable_ID(tableName), InterfaceWrapperHelper.getTrxName(po)))
		{
			if (relevantPO.whereClauseMatches(po))
			{
				MCAdvCommissionRelevantPO.logger.debug(I_C_AdvCommissionRelevantPO.Table_Name + " entry with name '" + relevantPO.getName() + "' matches PO " + po);
				return relevantPO;
			}
		}
		return null;
	}

	boolean whereClauseMatches(final Object model)
	{
		final String where = getSQLWhere();

		if (Check.isEmpty(where, true))
		{
			MCAdvCommissionRelevantPO.logger.debug("whereClause is empty. Returning true");
			return true;
		}

		final PO po = InterfaceWrapperHelper.getPO(model);

		final String[] keyColumns = po.get_KeyColumns();
		if (keyColumns.length != 1)
		{
			throw new AdempiereException(po + " has " + keyColumns.length
					+ " key columns; Should have one. " + this
					+ " fails to match where clause");
		}
		final String whereClause = keyColumns[0] + "=" + po.get_ID() + " AND ( " + where + " ) ";

		final boolean match = new Query(po.getCtx(), po.get_TableName(), whereClause, po.get_TrxName()).setOnlyActiveRecords(true).match();

		MCAdvCommissionRelevantPO.logger.debug("whereClause='" + whereClause + "' matches po='" + po + "':" + match);
		return match;
	}

	/**
	 * Returnes all entries, ordered by their SeqNo.
	 * 
	 * @param trxName
	 * @return
	 */
	public static List<MCAdvCommissionRelevantPO> retrieveAll(
			final String trxName)
	{

		return new Query(Env.getCtx(), I_C_AdvCommissionRelevantPO.Table_Name, "", trxName).setOrderBy(
				I_C_AdvCommissionRelevantPO.COLUMNNAME_SeqNo).setOnlyActiveRecords(true).list();
	}

	public String getTableName()
	{
		final ITableRefInfo tableRefInfo = Services.get(ILookupDAO.class).retrieveTableRefInfo(getAD_Reference_ID());
		return tableRefInfo.getTableName();
	}

	public String getSQLWhere()
	{
		final ITableRefInfo tableRefInfo = Services.get(ILookupDAO.class).retrieveTableRefInfo(getAD_Reference_ID());
		return tableRefInfo.getWhereClause();
	}

	@Override
	public String toString()
	{

		final StringBuffer sb = new StringBuffer();
		sb.append("MAdvCommissionRelevantPO[Id=");
		sb.append(get_ID());
		sb.append(", Name=");
		sb.append(getName());
		sb.append(", AD_Reference_ID=");
		sb.append(getAD_Reference_ID());
		sb.append("]");

		return sb.toString();
	}

	@Override
	protected final boolean beforeSave(final boolean newRecord)
	{

		final int tableId = get_Table_ID();

		if (checkForTableId(tableId, MTable.getTable_ID(I_C_AdvCommissionFactCand.Table_Name)))
		{
			return false;
		}
		if (checkForTableId(tableId, MTable.getTable_ID(I_C_AdvCommissionFact.Table_Name)))
		{
			return false;
		}
		if (MTable.getTableName(getCtx(), tableId).startsWith("AD_"))
		{
			return false;
		}
		return true;
	}

	private boolean checkForTableId(final int tableId, final int illegalTableId)
	{

		if (tableId == illegalTableId)
		{
			log.error("Illegal Table " + I_C_AdvCommissionFactCand.Table_Name);
			return true;
		}
		return false;
	}

	/**
	 * Deletes the referring MCAdvComRelevantPOType records.
	 */
	@Override
	protected boolean beforeDelete()
	{

		for (final MCAdvComRelevantPOType type : MCAdvComRelevantPOType
				.retrieveFor(this))
		{

			type.deleteEx(false);
		}
		MCAdvCommissionRelevantPO.logger.info("Deleted MCAdvComRelevantPOTypes of " + this);
		return true;
	}

}
