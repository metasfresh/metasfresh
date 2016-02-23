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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_M_RMA;
import org.compiere.model.MColumn;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * Generic provider of zoom targets. Contains pieces of {@link org.compiere.apps.AZoomAcross}
 * methods <code>getZoomTargets</code> and <code>addTarget</code>
 *
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194  ] Advanced Zoom and RelationTypes
 *
 */
public class GenericZoomProvider implements IZoomProvider {

	private static final CLogger logger = CLogger
			.getCLogger(GenericZoomProvider.class);

	@Override
	public List<ZoomInfoFactory.ZoomInfo> retrieveZoomInfos(PO po) {

		String sql = "SELECT DISTINCT ws.AD_Window_ID,ws.Name, wp.AD_Window_ID,wp.Name, t.TableName "
				+ "FROM AD_Table t ";
		boolean baseLanguage = Env.isBaseLanguage(Env.getCtx(), "AD_Window");
		if (baseLanguage)
			sql += "INNER JOIN AD_Window ws ON (t.AD_Window_ID=ws.AD_Window_ID)"
					+ " LEFT OUTER JOIN AD_Window wp ON (t.PO_Window_ID=wp.AD_Window_ID) ";
		else
			sql += "INNER JOIN AD_Window_Trl ws ON (t.AD_Window_ID=ws.AD_Window_ID AND ws.AD_Language=?)"
					+ " LEFT OUTER JOIN AD_Window_Trl wp ON (t.PO_Window_ID=wp.AD_Window_ID AND wp.AD_Language=?) ";
		//
		//@formatter:off
		sql += "WHERE t.TableName NOT LIKE 'I%'" // No Import
				//
				// Consider first window tab or any tab if our column has AllowZoomTo set
				+ " AND EXISTS ("
					+ "SELECT 1 FROM AD_Tab tt "
						+ "WHERE (tt.AD_Window_ID=ws.AD_Window_ID OR tt.AD_Window_ID=wp.AD_Window_ID)"
						+ " AND tt.AD_Table_ID=t.AD_Table_ID"
						+ " AND ("
							// First Tab
							+ " tt.SeqNo=10"
							// Or tab contains our column and AllowZoomTo=Y
							+ " OR EXISTS (SELECT 1 FROM AD_Column c where c.AD_Table_ID=t.AD_Table_ID AND ColumnName=? AND "+I_AD_Column.COLUMNNAME_AllowZoomTo+"='Y')" // #1
						+ ")"
				+ ")"
				//
				// Consider tables which have a reference to our column
				+ " AND (" // metas
					+ " t.AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Column WHERE ColumnName=? AND IsKey='N' AND IsParent='N') " // #2
					// metas: begin: support for "Zoomable Record_IDs" (03921)
					+ " OR ("
						+ " t.AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Column WHERE ColumnName='AD_Table_ID' AND IsKey='N' AND IsParent='N')"
						+ " AND t.AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Column WHERE ColumnName='Record_ID' AND IsKey='N' AND IsParent='N' AND "+I_AD_Column.COLUMNNAME_AllowZoomTo+"='Y')"
					+ ") "
				+ ") "
				// metas: end: support for "Zoomable Record_IDs" (03921)
				+ "ORDER BY 2";
		//@formatter:on

		final PreparedStatement pstmt = DB.prepareStatement(sql, null);
		ResultSet rs = null;
		try {

			int index = 1;
			if (!baseLanguage) {
				pstmt.setString(index++, Env.getAD_Language(Env.getCtx()));
				pstmt.setString(index++, Env.getAD_Language(Env.getCtx()));
			}
			pstmt.setString(index++, po.get_TableName() + "_ID");
			pstmt.setString(index++, po.get_TableName() + "_ID");
			rs = pstmt.executeQuery();

			final List<ZoomInfoFactory.ZoomInfo> result = new ArrayList<ZoomInfoFactory.ZoomInfo>();
			while (rs.next()) {

				int AD_Window_ID = rs.getInt(1);
				String Name = rs.getString(2);
				int PO_Window_ID = rs.getInt(3);
				String targetTableName = rs.getString(5);

				if (PO_Window_ID == 0) {

					final MQuery query = evaluateQuery(targetTableName,
							AD_Window_ID, Name, null, po);
					result.add(new ZoomInfoFactory.ZoomInfo(AD_Window_ID,
							query, Name));

				} else {
					final MQuery query = evaluateQuery(targetTableName,
							AD_Window_ID, Name, Boolean.TRUE, po);
					result.add(new ZoomInfoFactory.ZoomInfo(AD_Window_ID,
							query, Name));
					// // PO
				}
				if (PO_Window_ID != 0) {

					Name = rs.getString(4);
					final MQuery query = evaluateQuery(targetTableName,
							PO_Window_ID, Name, Boolean.FALSE, po);

					result.add(new ZoomInfoFactory.ZoomInfo(PO_Window_ID,
							query, Name));
				}
			}
			return result;

		} catch (SQLException e) {
			throw new DBException(e, sql);
		} finally {
			DB.close(rs, pstmt);
		}
	}

	private static MQuery evaluateQuery(String targetTableName,
			int AD_Window_ID, String Name, Boolean isSO, final PO po) {

		MTable targetTable = MTable.get(Env.getCtx(), targetTableName);
		if (targetTable == null)
			return MQuery.getNoRecordQuery(targetTableName, false);
		MColumn targetColumn = targetTable.getColumn(po.get_TableName()+"_ID");
		// metas: begin: support for "Zoomable Record_IDs" (03921)
		if (targetColumn == null
				&& targetTable.getColumn("AD_Table_ID") != null
				&& targetTable.getColumn("Record_ID") != null)
		{
			final MQuery query = new MQuery(targetTableName);
			query.addRestriction("AD_Table_ID", MQuery.EQUAL, po.get_Table_ID());
			query.addRestriction("Record_ID", MQuery.EQUAL, po.get_ID());
			query.setZoomTableName(targetTableName);
			//query.setZoomColumnName(po.get_KeyColumns()[0]);
			query.setZoomValue(po.get_ID());

			final int count = DB.getSQLValue(ITrx.TRXNAME_None, "SELECT COUNT(*) FROM " + targetTableName + " WHERE "+ query.getWhereClause(false));
			query.setRecordCount(count > 0 ? count : 0);

			return query;
		}
		// metas: end

		if (targetColumn == null)
			return MQuery.getNoRecordQuery(targetTableName, false);

		final MQuery query = new MQuery();

		if (targetColumn.isVirtualColumn())
			query.addRestriction("("+targetColumn.getColumnSQL()+") = "+po.get_ID());
		else
			query.addRestriction(po.get_TableName() + "_ID=" + po.get_ID());
		query.setZoomTableName(targetTableName);
		query.setZoomColumnName(po.get_KeyColumns()[0]);
		query.setZoomValue(po.get_ID());

		String sql = "SELECT COUNT(*) FROM " + targetTableName + " WHERE "
				+ query.getWhereClause(false);
		String sqlAdd = "";
		if (isSO != null) {
			/*
			 * For RMA, Material Receipt window should be loaded for
			 * IsSOTrx=true and Shipment for IsSOTrx=false
			 */

			if (I_M_RMA.Table_Name.equals(po.get_TableName())
					&& (AD_Window_ID == 169 || AD_Window_ID == 184)) {
				isSO = !isSO;
			}
			sqlAdd = " AND IsSOTrx=" + (isSO.booleanValue() ? "'Y'" : "'N'");
		}
		int count = DB.getSQLValue(null, sql + sqlAdd);
		if (count < 0 && isSO != null) // error try again w/o SO
			count = DB.getSQLValue(null, sql);

		query.setRecordCount(count);

		return query;
	} // checkTarget

}
