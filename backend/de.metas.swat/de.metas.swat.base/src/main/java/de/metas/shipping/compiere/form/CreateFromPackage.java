/******************************************************************************
 * Copyright (C) 2009 Metas                                                   *
 * Copyright (C) 2009 Carlos Ruiz                                             *
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
package de.metas.shipping.compiere.form;

/*
 * #%L
 * de.metas.swat.base
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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.grid.CreateFrom;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.metas.i18n.Msg;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.interfaces.I_M_Package;
import de.metas.shipping.model.MMShipperTransportation;

/**
 *  Create Shipping Packages for Shipper Transportation
 *
 *  @author Carlos Ruiz
 */
public class CreateFromPackage extends CreateFrom 
{
	/**
	 *  Protected Constructor
	 *  @param mTab MTab
	 */
	public CreateFromPackage(GridTab mTab)
	{
		super(mTab);
		log.info(mTab.toString());
	}   //  VCreateFromInvoice

	/**
	 *  Dynamic Init
	 *  @return true if initialized
	 */
	public boolean dynInit() throws Exception
	{
		log.info("");
		setTitle(Msg.translate(Env.getCtx(), "M_Package_ID") + " .. " + Msg.translate(Env.getCtx(), "CreateFrom"));
		
		return true;
	}   //  dynInit
	
	/**************************************************************************
	 *	Construct SQL Where Clause and define parameters
	 *  (setParameters needs to set parameters)
	 *  Includes first AND
	 *  @return sql where clause
	 */
	public String getSQLWhere(Object Shipper, Object Country, Object DateFrom, Object DateTo)
	{
		StringBuffer sql = new StringBuffer("WHERE NOT EXISTS (SELECT 1 FROM m_shippingpackage sp " +
	    		"JOIN m_shippertransportation st ON (sp.m_shippertransportation_id=st.m_shippertransportation_id) " +
	    		"WHERE st.docstatus NOT IN ('VO','RE') AND sp.m_package_id=p.m_package_id)");
		
		if (Shipper != null)
			sql.append(" AND p.M_Shipper_ID=?");
		//
		if (Country != null)
			sql.append(" AND l.C_Country_ID=?");
		//
		if (DateFrom != null || DateTo != null)
		{
			Timestamp from = (Timestamp) DateFrom;
			Timestamp to = (Timestamp) DateTo;
			if (from == null && to != null)
				sql.append(" AND TRUNC(p.ShipDate) <= ?");
			else if (from != null && to == null)
				sql.append(" AND TRUNC(p.ShipDate) >= ?");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(p.ShipDate) BETWEEN ? AND ?");
		}
		//
		log.debug(sql.toString());
		
		final String orgWhere = Env.getUserRolePermissions().getOrgWhere(false);
		if (!Check.isEmpty(orgWhere, true))
		{
			sql.append(" AND (p.");
			sql.append(orgWhere);
			sql.append(")");
		}
		return sql.toString();
	}	//	getSQLWhere
	
	/**
	 *  Set Parameters for Query.
	 *  (as defined in getSQLWhere)
	 *  @param pstmt statement
	 *  @param forCount for counting records
	 *  @throws SQLException
	 */
	void setParameters(PreparedStatement pstmt, boolean forCount, 
			Object Shipper, Object Country, Object DateFrom, Object DateTo) 
	throws SQLException
	{
		int index = 1;
		
		if (Shipper != null)
		{
			Integer sh = (Integer) Shipper;
			pstmt.setInt(index++, sh.intValue());
			log.debug("Shipper=" + sh);
		}

		if (Country != null)
		{
			Integer co = (Integer) Country;
			pstmt.setInt(index++, co.intValue());
			log.debug("Country=" + co);
		}
		//
		if (DateFrom != null || DateTo != null)
		{
			Timestamp from = (Timestamp) DateFrom;
			Timestamp to = (Timestamp) DateTo;
			log.debug("Date From=" + from + ", To=" + to);
			if (from == null && to != null)
				pstmt.setTimestamp(index++, to);
			else if (from != null && to == null)
				pstmt.setTimestamp(index++, from);
			else if (from != null && to != null)
			{
				pstmt.setTimestamp(index++, from);
				pstmt.setTimestamp(index++, to);
			}
		}

	}   //  setParameters
	
	protected Vector<Vector<Object>> getPackageData(Object Shipper, Object Country, Object DateFrom, Object DateTo)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		String sql = "SELECT p.m_package_id, p.documentno, p.m_shipper_id, s.name, p.shipdate, p.m_inout_id, io.documentno, l.c_country_id, c.name, COALESCE(p.packagenettotal,0) " +
				"FROM m_package p " +
				"LEFT JOIN m_shipper s ON (p.m_shipper_id = s.m_shipper_id) " +
				"LEFT JOIN m_inout io ON (p.m_inout_id = io.m_inout_id) " +
				"LEFT JOIN c_bpartner_location bpl ON (io.c_bpartner_location_id = bpl.c_bpartner_location_id) " +
				"LEFT JOIN c_location l ON (l.c_location_id = bpl.c_location_id) " +
				"LEFT JOIN c_country c ON (l.c_country_id = c.c_country_id)";

		sql = sql + getSQLWhere(Shipper, Country, DateFrom, DateTo) + " ORDER BY p.m_package_id";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			setParameters(pstmt, false, Shipper, Country, DateFrom, DateTo);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(6);
				line.add(new Boolean(false));       //  0-Selection
				KeyNamePair pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				line.add(pp);                       //  1-M_Package_ID
				pp = new KeyNamePair(rs.getInt(3), rs.getString(4));
				line.add(pp);                       //  2-M_Shipper_ID
				line.add(rs.getTimestamp(5));       //  3-ShipDate
				pp = new KeyNamePair(rs.getInt(6), rs.getString(7));
				line.add(pp);                       //  4-M_InOut_ID
				pp = new KeyNamePair(rs.getInt(8), rs.getString(9));
				line.add(pp);                       //  5-C_Country_ID
				line.add(rs.getBigDecimal(10));      // 6-PackageNetTotal
				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return data;
	}
	
	public void info()
	{
		
	}
	
	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);      //  0-Selection
		miniTable.setColumnClass(1, String.class, true);        //  1-Package
		miniTable.setColumnClass(2, String.class, true);        //  2-Shipper
		miniTable.setColumnClass(3, Timestamp.class, true);     //  3-ShipDate
		miniTable.setColumnClass(4, String.class, true);        //  4-InOut
		miniTable.setColumnClass(5, String.class, true);        //  5-Country
		miniTable.setColumnClass(6, BigDecimal.class, true);    //  6-PackageNetTotal
		//  Table UI
		miniTable.autoSize();
	}

	/**
	 *  Save Statement - Insert Data
	 *  @return true if saved
	 */
	@Override
	public boolean save(final IMiniTable miniTable, final String trxName)
	{
		//  fixed values
		int M_ShipperTransportation_ID = ((Integer)getGridTab().getValue("M_ShipperTransportation_ID")).intValue();
		MMShipperTransportation st = new MMShipperTransportation (Env.getCtx(), M_ShipperTransportation_ID, trxName);
		log.info(st.toString());

		//  Lines
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair)miniTable.getValueAt(i, 1);   //  1-M_Package_ID
				int M_Package_ID = pp.getKey();

				log.debug("Line Package=" + M_Package_ID);
				//	
				final I_M_Package mpackage = InterfaceWrapperHelper.create(Env.getCtx(), M_Package_ID, I_M_Package.class, trxName);
				Services.get(IShipperTransportationBL.class).createShippingPackage(st, mpackage);
			}   //   if selected
		}   //  for all rows
		return true;
	}   //  save
	
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(6);
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add(Msg.translate(Env.getCtx(), "M_Package_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "M_Shipper_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "ShipDate"));
		columnNames.add(Msg.translate(Env.getCtx(), "M_InOut_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_Country_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "PackageNetTotal"));
	    
	    return columnNames;
	}
}
