/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *
 * Copyright (C) 2005 Robert Klein. robeklein@hotmail.com
 * Contributor(s): Low Heng Sin hengsin@avantz.com
 *                 Teo Sarca, teo.sarca@gmail.com
 *****************************************************************************/
package org.adempiere.pipo.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackIn;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.MTable;
import org.compiere.model.X_AD_Column;
import org.compiere.model.X_AD_Package_Exp_Detail;
import org.compiere.model.X_AD_Table;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class TableElementHandler extends AbstractElementHandler {
	private ColumnElementHandler columnHandler = new ColumnElementHandler();
	
	private List<Integer>tables = new ArrayList<Integer>();
	
	public void startElement(Properties ctx, Element element) throws SAXException
	{
		final PackIn packIn = (PackIn)ctx.get("PackInProcess");
		final String elementValue = element.getElementValue();
		final Attributes atts = element.attributes;
		log.info(elementValue+" "+atts.getValue("ADTableNameID"));
		final String entitytype = atts.getValue("EntityType");
		
		if (isProcessElement(ctx, entitytype))
		{
			final String tableName = atts.getValue("ADTableNameID");
			int id = packIn.getTableId(tableName);
			if (id <= 0)
			{
				id = get_IDWithColumn(ctx, "AD_Table", "TableName", tableName);
				if (id > 0)
					packIn.addTable(tableName, id);
			}
			if (id > 0 && isTableProcess(ctx, id) && element.pass == 1)
			{
				return;
			}
			
			MTable m_Table = new MTable(ctx, id, getTrxName(ctx));
			if (id <= 0 && atts.getValue("AD_Table_ID") != null && Integer.parseInt(atts.getValue("AD_Table_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_Table.setAD_Table_ID(Integer.parseInt(atts.getValue("AD_Table_ID")));
			int AD_Backup_ID = -1;
			String Object_Status = null;
			if (id > 0)
			{		
				AD_Backup_ID = copyRecord(ctx, "AD_Table",m_Table);
				Object_Status = "Update";			
			}
			else
			{
				Object_Status = "New";
				AD_Backup_ID =0;
			}
			m_Table.setTableName(tableName);
			//
			// Window
			final String windowName = atts.getValue("ADWindowNameID");
			if (!Util.isEmpty(windowName, true))
			{
				id = get_IDWithColumn(ctx, "AD_Window", "Name", windowName);
				if (id > 0)
				{
					m_Table.setAD_Window_ID(id);
				}
				else if (!element.defer)
				{
					element.defer = true;
					element.unresolved = "Window:"+windowName;
				}
				else
				{
					log.warning("@NotFound@ @AD_Window_ID@:"+windowName);
				}
			}
			//
			// PO Window
			final String poWindowName = getStringValue(atts,"POWindowNameID");
			if (!Util.isEmpty(poWindowName, true))
			{
				id = get_IDWithColumn(ctx, "AD_Window", "Name", poWindowName);
				if (id > 0)
				{
					m_Table.setPO_Window_ID(id);
				}
				else if (!element.defer)
				{
					element.defer = true;
					element.unresolved = "POWindow:"+poWindowName;
				}
				else
				{
					log.warning("@NotFound@ @PO_Window_ID@:"+poWindowName);
				}
			}
			//
			// Validation Rule
			final String valRuleName = getStringValue(atts,"ADValRuleNameID");
			if (!Util.isEmpty(valRuleName, true))
			{
				id = get_IDWithColumn(ctx, "AD_Val_Rule", "Name", valRuleName);
				if (id > 0)
				{
					m_Table.setAD_Val_Rule_ID(id);
				}
				else
				{
					element.defer = true;
					element.unresolved = "ValRule:"+valRuleName;
				}
			}
			//
			m_Table.setAccessLevel (atts.getValue("AccessLevel"));		    
			m_Table.setDescription(getStringValue(atts,"Description"));
			m_Table.setEntityType(atts.getValue("EntityType"));
			m_Table.setHelp(getStringValue(atts,"Help"));            
			m_Table.setIsActive(atts.getValue("isActive") != null ? Boolean.valueOf(atts.getValue("isActive")).booleanValue():true);
			m_Table.setImportTable(getStringValue(atts,"ImportTable"));
			m_Table.setIsChangeLog(Boolean.valueOf(atts.getValue("isChangeLog")).booleanValue());
			m_Table.setIsDeleteable(Boolean.valueOf(atts.getValue("isDeleteable")).booleanValue());
			m_Table.setIsHighVolume(Boolean.valueOf(atts.getValue("isHighVolume")).booleanValue());
			m_Table.setIsSecurityEnabled(Boolean.valueOf(atts.getValue("isSecurityEnabled")).booleanValue());
			m_Table.setIsView(Boolean.valueOf(atts.getValue("isView")).booleanValue());
			//m_Table.setLoadSeq(Integer.parseInt(atts.getValue("LoadSeq")));
			m_Table.setName(atts.getValue("Name"));
			m_Table.setReplicationType(getStringValue(atts,"ReplicationType"));
			m_Table.setTableName(atts.getValue("TableName"));
			if (m_Table.save(getTrxName(ctx)) == true)
			{		    	
				record_log (ctx, 1, m_Table.getName(),"Table", m_Table.get_ID(),AD_Backup_ID, Object_Status,"AD_Table",get_IDWithColumn(ctx, "AD_Table", "TableName", "AD_Table"));
				tables.add(m_Table.getAD_Table_ID());
				packIn.addTable(tableName, m_Table.getAD_Table_ID());
				element.recordId = m_Table.getAD_Table_ID();
			}
			else
			{
				record_log (ctx, 0, m_Table.getName(),"Table", m_Table.get_ID(),AD_Backup_ID, Object_Status,"AD_Table",get_IDWithColumn(ctx, "AD_Table", "TableName", "AD_Table"));
				throw new POSaveFailedException("Table");
			}            
		}
		else
		{
			element.skip = true;
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		
		int AD_Table_ID = Env.getContextAsInt(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_AD_Table_ID);
		PackOut packOut = (PackOut)ctx.get("PackOutProcess");
		boolean exported = isTableProcess(ctx, AD_Table_ID);
		AttributesImpl atts = new AttributesImpl();
		//Export table if not already done so
		if (!exported){
			
			String sql = "SELECT Name FROM AD_Table WHERE AD_Table_ID= " + AD_Table_ID;
			
			PreparedStatement pstmt = null;
			pstmt = DB.prepareStatement (sql, getTrxName(ctx));		
			
			try {
				
				ResultSet rs = pstmt.executeQuery();		
				
				while (rs.next())
				{
					X_AD_Table m_Table = new X_AD_Table (ctx, AD_Table_ID, null);
					createTableBinding(atts,m_Table);	
					document.startElement("","","table",atts);
					
					String sql1 = "SELECT * FROM AD_Column WHERE AD_Table_ID = " + AD_Table_ID
					+ " ORDER BY IsKey DESC, AD_Column_ID";  // Export key column as the first one				
					
					PreparedStatement pstmt1 = null;
					pstmt1 = DB.prepareStatement (sql1, getTrxName(ctx));		
					
					try {
						
						ResultSet rs1 = pstmt1.executeQuery();		
						
						while (rs1.next()){
							
							packOut.createAdElement(rs1.getInt("AD_Element_ID"), document);
							
							if (rs1.getInt("AD_Reference_ID")>0)
								packOut.createReference (rs1.getInt("AD_Reference_ID"), document);
							
							if (rs1.getInt("AD_Reference_Value_ID")>0)
								packOut.createReference (rs1.getInt("AD_Reference_Value_ID"), document);						
							
							if (rs1.getInt("AD_Process_ID")>0)
								packOut.createProcess (rs1.getInt("AD_Process_ID"), document);	
							
							if (rs1.getInt("AD_Val_Rule_ID")>0)
								packOut.createDynamicRuleValidation (rs1.getInt("AD_Val_Rule_ID"), document);
	
							createColumn(ctx, document, rs1.getInt("AD_Column_ID"));							
						}
						
						rs1.close();
						pstmt1.close();
						pstmt1 = null;
					}
					catch (Exception e)	{
						log.log(Level.SEVERE,"getProcess", e);
					}
					finally	{
						try	{
							if (pstmt1 != null)
								pstmt1.close ();
						}
						catch (Exception e){}
						pstmt1 = null;
					}	
					document.endElement("","","table");
				}
				rs.close();
				pstmt.close();
				pstmt = null;
			}
			
			catch (Exception e){
				log.log(Level.SEVERE,"getProcess", e);
			}
			finally{
				try	{
					if (pstmt != null)
						pstmt.close ();
				}
				catch (Exception e){}
				pstmt = null;
			}
		}
		
	}

	private void createColumn(Properties ctx, TransformerHandler document, int AD_Column_ID) throws SAXException {
		Env.setContext(ctx, X_AD_Column.COLUMNNAME_AD_Column_ID, AD_Column_ID);
		columnHandler.create(ctx, document);
		ctx.remove(X_AD_Column.COLUMNNAME_AD_Column_ID);
	}

	private boolean isTableProcess(Properties ctx, int AD_Table_ID)
	{
		if (tables.contains(AD_Table_ID))
			return true;
		else {
			tables.add(AD_Table_ID);
			return false;
		}
	}

	private AttributesImpl createTableBinding( AttributesImpl atts, X_AD_Table m_Table) 
	{
		String sql = null;
		String name = null;        
		atts.clear();
		if (m_Table.getAD_Table_ID() <= PackOut.MAX_OFFICIAL_ID)
			atts.addAttribute("", "", "AD_Table_ID", "CDATA", Integer.toString(m_Table.getAD_Table_ID()));
		atts.addAttribute("","","Name","CDATA",(m_Table.getName () != null ? m_Table.getName ():""));        
		if (m_Table.getAD_Table_ID()> 0 ){
			sql = "SELECT TableName FROM AD_Table WHERE AD_Table_ID=?";
			name = DB.getSQLValueString(null,sql,m_Table.getAD_Table_ID());        
			atts.addAttribute("","","ADTableNameID","CDATA",name);
		}
		else
			atts.addAttribute("","","ADTableNameID","CDATA","");
		if (m_Table.getAD_Window_ID()> 0 ){
			sql = "SELECT Name FROM AD_Window WHERE AD_Window_ID=?";
			name = DB.getSQLValueString(null,sql,m_Table.getAD_Window_ID());        
			atts.addAttribute("","","ADWindowNameID","CDATA",name);
		}
		else
			atts.addAttribute("","","ADWindowNameID","CDATA","");
		if (m_Table.getPO_Window_ID()> 0 ){
			sql = "SELECT Name FROM AD_Window WHERE AD_Window_ID=?";
			name = DB.getSQLValueString(null,sql,m_Table.getPO_Window_ID());       
			atts.addAttribute("","","POWindowNameID","CDATA",name);
		}
		else
			atts.addAttribute("","","POWindowNameID","CDATA","");
		if (m_Table.getAD_Val_Rule_ID()> 0 ){
			sql = "SELECT Name FROM AD_Val_Rule WHERE AD_Val_Rule_ID=?";
			name = DB.getSQLValueString(null,sql,m_Table.getAD_Val_Rule_ID());        
			atts.addAttribute("","","ADValRuleNameID","CDATA",name);
		}
		else
			atts.addAttribute("","","ADValRuleNameID","CDATA","");	
		atts.addAttribute("","","AccessLevel","CDATA",(m_Table.getAccessLevel () != null ? m_Table.getAccessLevel ():""));
		atts.addAttribute("","","Description","CDATA",(m_Table.getDescription () != null ? m_Table.getDescription ():""));
		atts.addAttribute("","","EntityType","CDATA",(m_Table.getEntityType () != null ? m_Table.getEntityType ():""));
		atts.addAttribute("","","Help","CDATA",(m_Table.getHelp() != null ? m_Table.getHelp ():""));
		atts.addAttribute("","","ImportTable","CDATA",(m_Table.getImportTable () != null ? m_Table.getImportTable ():""));
		atts.addAttribute("","","isChangeLog","CDATA",(m_Table.isChangeLog()== true ? "true":"false"));
		atts.addAttribute("","","isActive","CDATA",(m_Table.isActive()== true ? "true":"false"));
		atts.addAttribute("","","isDeleteable","CDATA",(m_Table.isDeleteable()== true ? "true":"false"));
		atts.addAttribute("","","isHighVolume","CDATA",(m_Table.isHighVolume()== true ? "true":"false"));
		atts.addAttribute("","","isSecurityEnabled","CDATA",(m_Table.isSecurityEnabled()== true ? "true":"false"));
		atts.addAttribute("","","isView","CDATA",(m_Table.isView()== true ? "true":"false"));
		atts.addAttribute("","","LoadSeq","CDATA",(m_Table.getLoadSeq ()> 0 ? "" + m_Table.getLoadSeq ():""));        
		atts.addAttribute("","","ReplicationType","CDATA",(m_Table.getReplicationType () != null ? m_Table.getReplicationType ():""));
		atts.addAttribute("","","TableName","CDATA",(m_Table.getTableName () != null ? m_Table.getTableName ():""));
		return atts;
	}
}
