/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *                                                                            *
 * Copyright (C) 2005 Robert Klein. robeklein@hotmail.com                     *
 * Contributor(s): Low Heng Sin hengsin@avantz.com                            *
 *****************************************************************************/
package org.adempiere.pipo.handler;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.IDFinder;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.X_AD_Package_Exp_Detail;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * 
 * @author Robert Klein.
 * @author Low Heng Sin
 *
 */
public class DataElementHandler extends AbstractElementHandler {

	private PO genericPO = null;
	int AD_Backup_ID = -1;
	String objectStatus = null;
	String d_tablename = null;
	
	private DataRowElementHandler rowHandler = new DataRowElementHandler();
	private DataColumnElementHandler columnHandler = new DataColumnElementHandler();

	public DataElementHandler() {
	}

	@Override
	public void startElement(Properties ctx, Element element) throws SAXException {
		String elementValue = element.getElementValue();
		Attributes atts = element.attributes;
		if (elementValue.equals("adempieredata") || elementValue.equals("data")) {
			log.info(elementValue);
			if (atts.getValue("clientname") != null) {
				int AD_Client_ID = IDFinder.get_ID("AD_Client", atts.getValue("clientname"), getClientId(ctx), getTrxName(ctx));
				Env.setContext(ctx, "AD_Client_ID", AD_Client_ID);
				log.info("adempieredata: client set to "+AD_Client_ID+" "+atts.getValue("clientname"));
			}
		}
		else if (elementValue.equals("dtable")) {
			log.info(elementValue+" "+atts.getValue("name"));
			d_tablename = atts.getValue("name");
		}
		// row element, adempieredata
		else if (elementValue.equals("drow")) {
			rowHandler.startElement(ctx, element);
		}		
		// column element, adempieredata
		else if (elementValue.equals("dcolumn")) {
			columnHandler.startElement(ctx, element);
		}
	}

	@Override
	public void endElement(Properties ctx, Element element) throws SAXException {
		String elementValue = element.getElementValue();
		if (elementValue.equals("drow")) {
			rowHandler.endElement(ctx, element);
		}
	}

	class DataRowElementHandler extends AbstractElementHandler {

		@Override
		public void startElement(Properties ctx, Element element) throws SAXException {
			String elementValue = element.getElementValue();
			Attributes atts = element.attributes;
			log.info(elementValue+" "+atts.getValue("name"));
			
			String d_rowname = atts.getValue("name");
				   	    
			// name can be null if there are keyXname attributes.
			if (!d_rowname.equals("")){
				int id = get_ID(ctx, d_tablename, d_rowname);
				genericPO = TableModelLoader.instance.getPO(ctx, d_tablename, id, getTrxName(ctx));
				if (id > 0){
					if (genericPO == null || genericPO.get_ID() != id)
						throw new SAXException("id not found");
					AD_Backup_ID = copyRecord(ctx,d_tablename,genericPO);
					objectStatus = "Update";			
				}
				else{
					objectStatus = "New";
					AD_Backup_ID =0;
				}
			}
			// keyXname and lookupkeyXname.
			else {
				String sql = "select * from "+d_tablename;
				String whereand = " where";
				String CURRENT_KEY = "key1name";
				if (atts.getValue(CURRENT_KEY) != null && !atts.getValue(CURRENT_KEY).equals("")) {
					sql = sql+whereand+" "+atts.getValue(CURRENT_KEY)+"="+atts.getValue("lookup"+CURRENT_KEY);
					whereand = " and";
				}
				CURRENT_KEY = "key2name";		
				if (atts.getValue(CURRENT_KEY) != null && !atts.getValue(CURRENT_KEY).equals("")) {
					sql = sql+whereand+" "+atts.getValue(CURRENT_KEY)+"="+atts.getValue("lookup"+CURRENT_KEY);
					whereand = " and";
				}
				if (whereand.equals(" where"))
					log.warn("no name or keyXname attribute defined.");
				// Load GenericPO from rs, in fact ID could not exist e.g. Attribute Value
				try {
					PreparedStatement pstmt = DB.prepareStatement(sql, getTrxName(ctx));
					
					ResultSet rs = pstmt.executeQuery();
					if (rs.next()) {
						objectStatus = "Update";	
						genericPO = TableModelLoader.instance.getPO(ctx, d_tablename, rs, getTrxName(ctx));
						rs.close();
						pstmt.close();
						pstmt = null;
					}
					else {
						rs.close();
						rs = null;
						pstmt.close();
						pstmt = null;
						objectStatus = "New";
						genericPO = TableModelLoader.instance.newPO(ctx, d_tablename, getTrxName(ctx));
						// set keyXname.
						CURRENT_KEY = "key1name";
						if (!atts.getValue(CURRENT_KEY).equals("")) {
							String colName = atts.getValue(CURRENT_KEY);
							String valueObject = atts.getValue("lookup"+CURRENT_KEY);
							if (colName.endsWith("_ID") && valueObject.contains("SELECT"))
								valueObject = DB.getSQLValueString(getTrxName(ctx), valueObject);
							genericPO.set_ValueOfColumn(colName, valueObject);
						}
						CURRENT_KEY = "key2name";
						if (!atts.getValue(CURRENT_KEY).equals("")) {
							String colName = atts.getValue(CURRENT_KEY);
							String valueObject = atts.getValue("lookup"+CURRENT_KEY);
							if (colName.endsWith("_ID") && valueObject.contains("SELECT"))
								valueObject = DB.getSQLValueString(getTrxName(ctx), valueObject);
							genericPO.set_ValueOfColumn(colName, valueObject);
						}
					}
					
				}
				catch (Exception e) {
					log.warn("keyXname attribute. init from rs error."+e);
					throw new SAXException(e.getMessage());
				}
			}
			
			// for debug GenericPO.
			if (false) {
				POInfo poInfo = POInfo.getPOInfo(d_tablename);
				if (poInfo == null)
					log.info("poInfo is null.");
				for (int i = 0; i < poInfo.getColumnCount(); i++) {
					log.info(d_tablename+" column: "+poInfo.getColumnName(i));
				}
			}
			// globalqss: set AD_Client_ID to the client setted in adempieredata
			if (getClientId(ctx) > 0 && genericPO.getAD_Client_ID() != getClientId(ctx))
				genericPO.set_ValueOfColumn("AD_Client_ID", getClientId(ctx));
			// if new. TODO: no defaults for keyXname.
			if (!d_rowname.equals("") && ((Integer)(genericPO.get_Value(d_tablename+"_ID"))).intValue() == 0) {
				log.info("new genericPO, table: "+d_tablename+" name:"+d_rowname);
				genericPO.set_ValueOfColumn("Name", d_rowname);
				// Set defaults.
				//TODO: get defaults from configuration
				HashMap defaults = new HashMap();
				HashMap thisDefault = (HashMap)defaults.get(d_tablename);
				if (thisDefault != null) {
					Iterator iter = thisDefault.values().iterator();
					ArrayList thisValue = null;
					while (iter.hasNext()) {
						thisValue = (ArrayList)iter.next();
						if (((String)(thisValue.get(2))).equals("String"))
							genericPO.set_ValueOfColumn((String)thisValue.get(0), (String)thisValue.get(1));
						else if (((String)(thisValue.get(2))).equals("Integer"))
							genericPO.set_ValueOfColumn((String)thisValue.get(0), Integer.valueOf((String)thisValue.get(1)));
						else if (((String)(thisValue.get(2))).equals("Boolean"))
							genericPO.set_ValueOfColumn((String)thisValue.get(0), new Boolean(((String)thisValue.get(1)).equals("true") ? true : false));
					}
				}
			}
		}

		@Override
		public void endElement(Properties ctx, Element element) throws SAXException {
			if (genericPO != null) {
				if (genericPO.save(getTrxName(ctx))== true)
					record_log (ctx, 1, genericPO.get_TableName(),"Data", genericPO.get_ID(),AD_Backup_ID, objectStatus,d_tablename,get_IDWithColumn(ctx, "AD_Table", "TableName", d_tablename));
				else {
					record_log (ctx, 0, genericPO.get_TableName(),"Data", genericPO.get_ID(),AD_Backup_ID, objectStatus,d_tablename,get_IDWithColumn(ctx, "AD_Table", "TableName", d_tablename));
					throw new POSaveFailedException("GenericPO");
				}
				
				genericPO = null;
			}
		}

		@Override
		public void create(Properties ctx, TransformerHandler document)
				throws SAXException {
		}

	}
	
	class DataColumnElementHandler extends AbstractElementHandler {

		@Override
		public void startElement(Properties ctx, Element element) throws SAXException {
			String elementValue = element.getElementValue();
			Attributes atts = element.attributes;
			log.info(elementValue+" "+atts.getValue("name"));
			String columnName = atts.getValue("name");	    	
			int tableid = get_IDWithColumn(ctx, "AD_Table", "TableName", d_tablename);
			int id =get_IDWithMasterAndColumn (ctx,"AD_Column", "ColumnName", columnName, "AD_Table", tableid);
			StringBuffer sql = new StringBuffer ("SELECT IsUpdateable FROM AD_column WHERE AD_Column_ID = ?");
			String isUpdateable = DB.getSQLValueString(getTrxName(ctx), sql.toString(),id);
			sql = new StringBuffer ("SELECT IsKey FROM AD_column WHERE AD_Column_ID = ?");
			String isKey = DB.getSQLValueString(getTrxName(ctx), sql.toString(),id);
			if (("New".equals(objectStatus)) || (isKey.equals("N") && 
					isUpdateable.equals("Y") &&
					(!atts.getValue("name").equals("CreatedBy")||!atts.getValue("name").equals("UpdatedBy")))) {
				if (atts.getValue("value") != null && !atts.getValue("value").equals("null")) {
					if (atts.getValue("class").equals("String") || atts.getValue("class").equals("Text")
							|| atts.getValue("class").equals("List")|| atts.getValue("class").equals("Yes-No")				
							|| atts.getValue("class").equals("Button")
							|| atts.getValue("class").equals("Memo")|| atts.getValue("class").equals("Text Long")
							|| atts.getValue("name").equals("AD_Language") || atts.getValue("name").equals("EntityType")) {
						genericPO.set_ValueOfColumn(atts.getValue("name").toString(), atts.getValue("value").toString());
					}
					else if (atts.getValue("class").equals("Number") || atts.getValue("class").equals("Amount")
							|| atts.getValue("class").equals("Quantity")|| atts.getValue("class").equals("Costs+Prices")){
						genericPO.set_ValueOfColumn(atts.getValue("name").toString(), new BigDecimal(atts.getValue("value")));
					}
					else if (atts.getValue("class").equals("Integer") || atts.getValue("class").equals("ID")
							|| atts.getValue("class").equals("Table Direct")|| atts.getValue("class").equals("Table")
							|| atts.getValue("class").equals("Location (Address)")|| atts.getValue("class").equals("Account")
							|| atts.getValue("class").equals("Color)")|| atts.getValue("class").equals("Search")						
							|| atts.getValue("class").equals("Locator (WH)")|| atts.getValue("class").equals("Product Attribute")) {
						genericPO.set_ValueOfColumn(atts.getValue("name").toString(), Integer.valueOf(atts.getValue("value")));
					}	
					else if (atts.getValue("class").equals("Boolean")) {
						genericPO.set_ValueOfColumn(atts.getValue("name"), new Boolean(atts.getValue("value").equals("true") ? true : false));
					}
					else if (atts.getValue("class").equals("Date") || atts.getValue("class").equals("Date+Time")
							|| atts.getValue("class").equals("Time")) {
						genericPO.set_ValueOfColumn(atts.getValue("name").toString(), Timestamp.valueOf(atts.getValue("value")));
					}//Binary,  Radio, RowID, Image not supported
				} else { // value is null 
					if (atts.getValue("lookupname") != null && !"".equals(atts.getValue("lookupname"))) {
						// globalqss - bring support from XML2AD to lookupname
						String m_tablename = atts.getValue("name").substring(0, atts.getValue("name").length()-3);
						genericPO.set_ValueOfColumn(atts.getValue("name"), new Integer(getIDbyName(ctx, m_tablename, atts.getValue("lookupname"))));
					} else if (atts.getValue("lookupvalue") != null && !"".equals(atts.getValue("lookupvalue"))) {
						// globalqss - bring support from XML2AD to lookupvalue
						String m_tablename = atts.getValue("name").substring(0, atts.getValue("name").length()-3);
						genericPO.set_ValueOfColumn(atts.getValue("name"), new Integer(getIDbyValue(ctx, m_tablename, atts.getValue("lookupvalue"))));
					}

				}
			}
		}

		@Override
		public void endElement(Properties ctx, Element element) throws SAXException {
		}

		@Override
		public void create(Properties ctx, TransformerHandler document)
				throws SAXException {
		}

	}

	@Override
	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		String sql = Env.getContext(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_SQLStatement);
		int table_id = Env.getContextAsInt(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_AD_Table_ID);
		Statement stmt = DB.createStatement();
		AttributesImpl atts = new AttributesImpl();
		document.startElement("","","data",atts);		
		try {
			ResultSet rs = stmt.executeQuery(sql);		
			ResultSetMetaData meta = rs.getMetaData(); 
			int columns = meta.getColumnCount(); 
			int i = 1;
			String col_Name = null;
			String sql1 = "SELECT TableName FROM AD_Table WHERE AD_Table_ID=?";
			String table_Name = DB.getSQLValueString(null,sql1,table_id);
			atts.clear();
			atts.addAttribute("","","name","CDATA",table_Name);
			document.startElement("","","dtable",atts);
			while (rs.next()){
				atts.clear();				
				int key1 = 0;
				String nameatts = ""; 
				for (i=1 ;i <= columns;i++){
					col_Name = meta.getColumnName(i).toUpperCase();
					if (col_Name.equals("NAME") && rs.getObject("name") != null)
						nameatts = ""+rs.getObject("name");					
					String sql2 = "SELECT ColumnName FROM AD_Column "
						+ "WHERE isKey = 'Y' AND "
						+ "AD_Table_ID = ? AND "
						+ "Upper(ColumnName)= '"+col_Name+"'";
					String cName = DB.getSQLValueString(null,sql2,table_id);
					if (cName != null){
						if (cName.toUpperCase().equals(col_Name) && key1 == 0  ){
							atts.addAttribute("","","key1name","CDATA",cName);
							atts.addAttribute("","","lookupkey1name","CDATA",""+rs.getObject(col_Name));
							key1 = 1;							
						}
						else if (cName.toUpperCase().equals(col_Name) && key1 == 1 ){
							atts.addAttribute("","","key2name","CDATA",cName);
							atts.addAttribute("","","lookupkey2name","CDATA",""+rs.getObject(col_Name));
							key1 = 2;
						}
					}
				}
				atts.addAttribute("","","name","CDATA",nameatts);
				if ( key1 == 0 ){
					atts.addAttribute("","","key1name","CDATA","");
					atts.addAttribute("","","lookupkey1name","CDATA","");
					key1 = 1;
				}	
				if ( key1 == 1 ){
					atts.addAttribute("","","key2name","CDATA","");
					atts.addAttribute("","","lookupkey2name","CDATA","");
				}	
				document.startElement("","","drow",atts);				
				for (i=1 ;i <= columns;i++){
					atts.clear();
					col_Name = meta.getColumnName(i).toUpperCase();
					String sql2 = "Select A.ColumnName, B.Name "
						+ "From AD_Column A, AD_Reference B " 
						+ "Where Upper(A.columnname) = ? and " 
						+ "A.AD_TABLE_ID = ? and " 
						+ "A.AD_Reference_ID = B.AD_Reference_ID";
					PreparedStatement pstmt = null;
					try
					{
						pstmt = DB.prepareStatement(sql2, getTrxName(ctx));
						pstmt.setString(1, col_Name);
						pstmt.setInt(2, table_id);
						ResultSet rs1 = pstmt.executeQuery();						
						while (rs1.next()){
							//added 9/3/05
							atts.clear();
							atts.addAttribute("","","name","CDATA", rs1.getString("ColumnName"));							
							atts.addAttribute("","","class","CDATA", rs1.getString("Name"));
							if (rs1.getString("Name").equals("Date")||rs1.getString("Name").equals("Date+Time")||rs1.getString("Name").equals("Time"))
								atts.addAttribute("","","value","CDATA", "" + rs.getTimestamp(i));
							else
								atts.addAttribute("","","value","CDATA", "" + rs.getObject(i));
							
							if (!rs1.getString("ColumnName").equals("Created")&&!rs1.getString("ColumnName").equals("CreatedBy")&&
									!rs1.getString("ColumnName").equals("Updated")&&!rs1.getString("ColumnName").equals("UpdatedBy")){
								document.startElement("","","dcolumn",atts);
								document.endElement("","","dcolumn");
							}
						}					
						rs1.close();
						pstmt.close();
						pstmt = null;
					}	
					catch (Exception e)	{
						log.error("getData", e);
					}
				}
				document.endElement("","","drow");	
			}
			rs.close();
			stmt.close();
			stmt = null;
			document.endElement("","","dtable");
		}	
		
		catch (Exception e)	{
			log.error("getData", e);
		}
		
		document.endElement("","","data");
		
	}

}
