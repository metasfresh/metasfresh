/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.process;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Types;
import org.compiere.db.AdempiereDatabase;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.M_Element;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;


/**
 * Create Columns of Table or View
 * <p>
 * Change log:
 * <ul>
 * <li>2007-01-29 - teo_sarca - [ 1640908 ] TableCreateColumns hangs sometimes... 
 * </ul>
 * 
 *  @author Jorg Janke
 *  @version $Id: TableCreateColumns.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class TableCreateColumns extends SvrProcess
{
	/** Entity Type			*/
	private String	p_EntityType = "C";	//	ENTITYTYPE_Customization
	/** Table				*/
	private int		p_AD_Table_ID = 0;
	/** CheckAllDBTables	*/
	private boolean	p_AllTables = false;
	
	/** Column Count	*/
	private int 	m_count = 0;

	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare ()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("EntityType"))
				p_EntityType = (String)para[i].getParameter();
			else if (name.equals("AllTables"))
				p_AllTables = "Y".equals(para[i].getParameter());
			else
				log.error("Unknown Parameter: " + name);
		}
		p_AD_Table_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		if (p_AD_Table_ID == 0)
			throw new AdempiereSystemError("@NotFound@ @AD_Table_ID@ " + p_AD_Table_ID);
		log.info("EntityType=" + p_EntityType
			+ ", AllTables=" + p_AllTables
			+ ", AD_Table_ID=" + p_AD_Table_ID);
		//
		Connection conn = null;
		
		try {
			conn = DB.getConnectionRO();
			AdempiereDatabase db = DB.getDatabase();
			DatabaseMetaData md = conn.getMetaData();
			String catalog = db.getCatalog();
			String schema = db.getSchema();
			
			if (p_AllTables)
				addTable (md, catalog, schema);
			else
			{
				MTable table = new MTable (getCtx(), p_AD_Table_ID, get_TrxName());
				if (table == null || table.get_ID() == 0)
					throw new AdempiereSystemError("@NotFound@ @AD_Table_ID@ " + p_AD_Table_ID);
				log.info(table.getTableName() + ", EntityType=" + p_EntityType);
				String tableName = table.getTableName();
				// globalqss 2005-10-24
				if (DB.isPostgreSQL())
				    tableName = tableName.toLowerCase();
				// end globalqss 2005-10-24
				ResultSet rs = md.getColumns(catalog, schema, tableName, null);
				addTableColumn(rs, table);
			}
			
			return "#" + m_count;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {}
			}
		}
	}	//	doIt

	/**
	 * 	Add Table
	 *	@param md meta data
	 *	@param catalog catalog
	 *	@param schema schema
	 *	@throws Exception
	 */
	private void addTable (DatabaseMetaData md, String catalog, String schema) throws Exception
	{
		// globalqss 2005-10-25
		// ResultSet rs = md.getTables(catalog, schema, null, null);
		ResultSet rs;
		if (DB.isPostgreSQL())
			rs = md.getTables(catalog, schema, null, new String [] {"TABLE", "VIEW"});
		else
			rs = md.getTables(catalog, schema, null, null);
		// end globalqss 2005-10-25
		while (rs.next())
		{
			String tableName = rs.getString("TABLE_NAME");
			String tableType = rs.getString("TABLE_TYPE");
			
			//	Try to find
			MTable table = MTable.get(getCtx(), tableName);
			//	Create new ?
			if (table == null)
			{
				String tn = tableName.toUpperCase();
				if (tn.startsWith("T_SELECTION")	//	temp table
					|| tn.endsWith("_VT")			//	print trl views
					|| tn.endsWith("_V")			//	views
					|| tn.endsWith("_V1")			//	views
					|| tn.startsWith("A_A")			//	asset tables not yet
					|| tn.startsWith("A_D")			//	asset tables not yet
					|| tn.indexOf('$') != -1		//	oracle system tables
					|| tn.indexOf("EXPLAIN") != -1	//	explain plan
					)
				{
					log.debug("Ignored: " + tableName + " - " + tableType);
					continue;
				}
				//
				log.info(tableName + " - " + tableType);
				
				//	Create New
				table = new MTable(getCtx(), 0, get_TrxName());
				table.setEntityType (p_EntityType);
				table.setName (tableName);
				table.setTableName (tableName);
				table.setIsView("VIEW".equals(tableType));
				if (!table.save())
					continue;
			}
			//	Check Columns
			// globalqss 2005-10-24
			if (DB.isPostgreSQL())
			    tableName = tableName.toLowerCase();
			// end globalqss 2005-10-24
			ResultSet rsC = md.getColumns(catalog, schema, tableName, null);
			addTableColumn(rsC, table);
		}
	}	//	addTable
	
	
	/**
	 * 	Add Table Column
	 *	@param rs result set with meta data
	 *	@param table table
	 *	@throws Exception
	 */
	private void addTableColumn (ResultSet rs, MTable table) throws Exception
	{
		String tableName = table.getTableName ();
		// globalqss 2005-10-24
		if (DB.isPostgreSQL())
		    tableName = tableName.toLowerCase();
		// end globalqss 2005-10-24
		while (rs.next ())
		{
			String tn = rs.getString ("TABLE_NAME");
			if (!tableName.equalsIgnoreCase (tn))
				continue;
			String columnName = rs.getString ("COLUMN_NAME");
			MColumn column = table.getColumn (columnName);
			if (column != null)
				continue;
			int dataType = rs.getInt ("DATA_TYPE");
			String typeName = rs.getString ("TYPE_NAME");
			String nullable = rs.getString ("IS_NULLABLE");
			int size = rs.getInt ("COLUMN_SIZE");
			int digits = rs.getInt ("DECIMAL_DIGITS");
			//
			log.info(columnName + " - DataType=" + dataType + " " + typeName
				+ ", Nullable=" + nullable + ", Size=" + size + ", Digits="
				+ digits);
			//
			column = new MColumn (table);
			column.set_TrxName(get_TrxName()); // teo_sarca: bug [ 1640908 ] 
			column.setEntityType (p_EntityType);
			//
			M_Element element = M_Element.get (getCtx (), columnName);
			if (element == null)
			{
				element = new M_Element (getCtx (), columnName, p_EntityType,
					get_TrxName ());
				//contribution from teo_sarca, bug [ 1637912 ]
				//create column and element using the same case as the table, 
				//and the name & description from table
				if (columnName.equalsIgnoreCase (table.getTableName() + "_ID")) {
					element.setColumnName(table.getTableName() + "_ID");
					element.setName(table.getName());
					element.setPrintName(table.getName());
				}
				element.save ();
			}
			column.setColumnName (element.getColumnName ());
			column.setName (element.getName ());
			column.setDescription (element.getDescription ());
			column.setHelp (element.getHelp ());
			column.setAD_Element_ID (element.getAD_Element_ID ());
			//
			column.setIsMandatory ("NO".equals (nullable));
			// Key
			if (columnName.equalsIgnoreCase (tableName + "_ID"))
			{
				column.setIsKey (true);
				column.setAD_Reference_ID (DisplayType.ID);
				column.setIsUpdateable(false);
			}
			// Account
			// bug [ 1637912 ] 
			else if (columnName.toUpperCase ().endsWith("_ACCT")
				&& size == 10)
				column.setAD_Reference_ID (DisplayType.Account);
			// Account
			else if (columnName.equalsIgnoreCase ("C_Location_ID"))
				column.setAD_Reference_ID (DisplayType.Location);
			// Product Attribute
			else if (columnName.equalsIgnoreCase ("M_AttributeSetInstance_ID"))
				column.setAD_Reference_ID (DisplayType.PAttribute);
			// SalesRep_ID (=User)
			else if (columnName.equalsIgnoreCase ("SalesRep_ID"))
			{
				column.setAD_Reference_ID (DisplayType.Table);
				column.setAD_Reference_Value_ID (190);
			}
			// ID
			else if (columnName.toUpperCase().endsWith ("_ID"))
				column.setAD_Reference_ID (DisplayType.TableDir);
			// Date
			else if (dataType == Types.DATE || dataType == Types.TIME
				|| dataType == Types.TIMESTAMP
				// || columnName.toUpperCase().indexOf("DATE") != -1
				|| columnName.equalsIgnoreCase ("Created")
				|| columnName.equalsIgnoreCase ("Updated"))
				column.setAD_Reference_ID (DisplayType.DateTime);
			// CreatedBy/UpdatedBy (=User)
			else if (columnName.equalsIgnoreCase ("CreatedBy")
				|| columnName.equalsIgnoreCase ("UpdatedBy"))
			{
				column.setAD_Reference_ID (DisplayType.Table);
				column.setAD_Reference_Value_ID (110);
				column.setIsUpdateable(false);
			}
			//	Entity Type
			else if (columnName.equalsIgnoreCase ("EntityType"))
			{
				column.setAD_Reference_ID (DisplayType.Table);
				column.setAD_Reference_Value_ID (389);
			}
			// CLOB
			else if (dataType == Types.CLOB)
				column.setAD_Reference_ID (DisplayType.TextLong);
			// BLOB
			else if (dataType == Types.BLOB)
				column.setAD_Reference_ID (DisplayType.Binary);
			// Amount
			else if (columnName.toUpperCase ().indexOf ("AMT") != -1)
				column.setAD_Reference_ID (DisplayType.Amount);
			// Qty
			else if (columnName.toUpperCase ().indexOf ("QTY") != -1)
				column.setAD_Reference_ID (DisplayType.Quantity);
			// Boolean
			else if (size == 1
				&& (columnName.toUpperCase ().startsWith ("IS") || dataType == Types.CHAR))
				column.setAD_Reference_ID (DisplayType.YesNo);
			// List
			else if (size < 4 && dataType == Types.CHAR)
				column.setAD_Reference_ID (DisplayType.List);
			// Name, DocumentNo
			else if (columnName.equalsIgnoreCase ("Name")
				|| columnName.equals ("DocumentNo"))
			{
				column.setAD_Reference_ID (DisplayType.String);
				column.setIsIdentifier (true);
				column.setSeqNo (1);
			}
			// String, Text
			else if (dataType == Types.CHAR || dataType == Types.VARCHAR
				|| typeName.startsWith ("NVAR")
				|| typeName.startsWith ("NCHAR"))
			{
				if (typeName.startsWith("N"))	//	MultiByte	
					size /= 2;
				if (size > 255)
					column.setAD_Reference_ID (DisplayType.Text);
				else
					column.setAD_Reference_ID (DisplayType.String);
			}
			// Number
			else if (dataType == Types.INTEGER || dataType == Types.SMALLINT
				|| dataType == Types.DECIMAL || dataType == Types.NUMERIC)
			{
				if (size == 10)
					column.setAD_Reference_ID (DisplayType.Integer);
				else
					column.setAD_Reference_ID (DisplayType.Number);
			}
			//	??
			else
				column.setAD_Reference_ID (DisplayType.String);
			
			column.setFieldLength (size);
			if (column.isUpdateable()
				&& (table.isView()
					|| columnName.equalsIgnoreCase("AD_Client_ID") 
					|| columnName.equalsIgnoreCase("AD_Org_ID")
					|| columnName.toUpperCase().startsWith("CREATED") 
					|| columnName.toUpperCase().equals("UPDATED") ))
				column.setIsUpdateable(false);

			//	Done
			if (column.save ())
			{
				addLog (0, null, null, table.getTableName() + "." + column.getColumnName());
				m_count++;
			}
		}	//	while columns
		
	}	//	addTableColumn

	
}	//	TableCreateColumns
