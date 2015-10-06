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
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.DatabaseAccessException;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.MTab;
import org.compiere.model.MTable;
import org.compiere.model.X_AD_Field;
import org.compiere.model.X_AD_Tab;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class TabElementHandler extends AbstractElementHandler
{
	private FieldElementHandler fieldHandler = new FieldElementHandler();
	
	public void startElement(Properties ctx, Element element) throws SAXException {
		String elementValue = element.getElementValue();
		Attributes atts = element.attributes;
		log.info(elementValue+" "+atts.getValue("ADTabNameID"));
		String entitytype = atts.getValue("EntityType");
		if (isProcessElement(ctx, entitytype)) {
			if (element.parent != null && element.parent.getElementValue().equals("window")
				&& element.parent.defer) {
				element.defer = true;
				return;
			}
			String name = atts.getValue("ADTabNameID");
			int tableid = get_IDWithColumn(ctx, "AD_Table", "TableName", atts.getValue("ADTableNameID"));
			if (tableid <= 0) {
				element.defer = true;
				return;
			}
			int windowid = 0;
			if (element.parent != null && element.parent.getElementValue().equals("window")
					&& element.parent.recordId > 0) {
				windowid = element.parent.recordId;
			} else {
				windowid = get_ID(ctx, "AD_Window", atts.getValue("ADWindowNameID"));
				if (element.parent != null && element.parent.getElementValue().equals("window")
						&& windowid > 0) {
					element.parent.recordId = windowid;
				}
			}
			if (windowid <= 0) {
				element.defer = true;
				return;
			}
			StringBuffer sqlB = new StringBuffer ("select AD_Tab_ID from AD_Tab where AD_Window_ID = " + windowid
					+ " and Name = '"+name +"'"
					+ " and AD_Table_ID = ?");
			
			int id = DB.getSQLValue(getTrxName(ctx), sqlB.toString (), tableid);
			MTab m_Tab = new MTab(ctx, id, getTrxName(ctx));
			if (id <= 0 && atts.getValue("AD_Tab_ID") != null && Integer.parseInt(atts.getValue("AD_Tab_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_Tab.setAD_Tab_ID(Integer.parseInt(atts.getValue("AD_Tab_ID")));
			int AD_Backup_ID = -1;
			String Object_Status = null;
			if (id > 0){			
				AD_Backup_ID = copyRecord(ctx, "AD_Tab",m_Tab);
				Object_Status = "Update";
			}
			else{
				Object_Status = "New";
				AD_Backup_ID =0;
			}
			sqlB = null;
			m_Tab.setName(name);	
			id = 0;
			if (getStringValue(atts,"ADColumnSortYesNoNameID")!= null){
				name = atts.getValue("ADColumnSortYesNoNameID");	    
				id  = get_IDWithMasterAndColumn (ctx, "AD_Column","Name", name, MTable.Table_Name, get_IDWithColumn(ctx,MTable.Table_Name, MTable.COLUMNNAME_TableName, atts.getValue("ADTableNameID")));
				m_Tab.setAD_ColumnSortYesNo_ID(id);
			}
			if (getStringValue(atts,"ADColumnSortOrderNameID")!= null){
				name = atts.getValue("ADColumnSortOrderNameID");	    
				id  = get_IDWithMasterAndColumn (ctx, "AD_Column","Name", name, MTable.Table_Name, get_IDWithColumn(ctx,MTable.Table_Name, MTable.COLUMNNAME_TableName, atts.getValue("ADTableNameID")));				
				m_Tab.setAD_ColumnSortOrder_ID(id);
			}
			if (getStringValue(atts,"ADImageNameID")!= null){
				name = atts.getValue("ADImageNameID");	    
				id = get_IDWithColumn(ctx, "AD_Image", "Name", name);
				m_Tab.setAD_Image_ID(id);
			}
			if (getStringValue(atts,"ADProcessNameID")!= null){
				
				name = atts.getValue("ADProcessNameID");
				if (name != null && name.trim().length() > 0) {
					id = get_IDWithColumn(ctx, "AD_Process", "Name", name);			
					if (id <= 0) {
						element.defer = true;
						element.unresolved = "AD_Process: " + name;
						return;
					}
					m_Tab.setAD_Process_ID(id);
				}
			}		    
			if (getStringValue(atts,"ADTableNameID")!= null){
				name = atts.getValue("ADTableNameID");	    
				id = get_IDWithColumn(ctx, "AD_Table", "TableName", name);
				m_Tab.setAD_Table_ID(id);   
			}
			if (getStringValue(atts,"ADColumnNameID")!= null) {
				name = atts.getValue("ADColumnNameID");
				id  = get_IDWithMasterAndColumn(ctx, "AD_Column","ColumnName", atts.getValue("ADColumnNameID"), "AD_Table", m_Tab.getAD_Table_ID());
				if (id <= 0 /** TODO Check PackOut Version -- 005 */)
				{
					id  = get_IDWithMasterAndColumn(ctx, "AD_Column","Name", atts.getValue("ADColumnNameID"), "AD_Table", m_Tab.getAD_Table_ID());
				}
				m_Tab.setAD_Column_ID(id);
				if (id <= 0)
				{
					log.warning("@NotFound@ @AD_Column_ID@ - @Name@:"+name+", @AD_Table_ID@:"+atts.getValue("ADTableNameID"));
				}
			}
			m_Tab.setAD_Window_ID(windowid);   
			
			if (getStringValue(atts,"IncludedTabNameID")!= null){
				name = atts.getValue("IncludedTabNameID");	    
				id = get_IDWithColumn(ctx, "AD_Tab", "Name", name);
				m_Tab.setIncluded_Tab_ID(id);		        
			}
			m_Tab.setCommitWarning(atts.getValue("CommitWarning"));
			m_Tab.setDescription(getStringValue(atts,"Description"));
			m_Tab.setEntityType (atts.getValue("EntityType"));
			m_Tab.setHasTree(Boolean.valueOf(atts.getValue("isHasTree")).booleanValue());
			m_Tab.setHelp (getStringValue(atts,"Help"));
			m_Tab.setIsActive(atts.getValue("isActive") != null ? Boolean.valueOf(atts.getValue("isActive")).booleanValue():true);
			m_Tab.setImportFields (atts.getValue("ImportFields"));
			m_Tab.setIsInfoTab (Boolean.valueOf(atts.getValue("isInfoTab")).booleanValue());
			m_Tab.setIsReadOnly (Boolean.valueOf(atts.getValue("isReadOnly")).booleanValue());
			m_Tab.setIsSingleRow (Boolean.valueOf(atts.getValue("isSingleRow")).booleanValue());
			m_Tab.setIsSortTab (Boolean.valueOf(atts.getValue("isSortTab")).booleanValue());
			m_Tab.setIsTranslationTab (Boolean.valueOf(atts.getValue("IsTranslationTab")).booleanValue());
			m_Tab.setName (atts.getValue("Name"));
			m_Tab.setOrderByClause (getStringValue(atts,"OrderByClause"));
			m_Tab.setProcessing(false);
			m_Tab.setSeqNo (Integer.parseInt(atts.getValue("SeqNo")));
			m_Tab.setTabLevel (Integer.parseInt(atts.getValue("TabLevel")));
			m_Tab.setWhereClause (getStringValue(atts,"WhereClause"));
			if (getStringValue(atts,"ReadOnlyLogic") != null) {
				m_Tab.setReadOnlyLogic(atts.getValue("ReadOnlyLogic"));
			}
			if (getStringValue(atts,"DisplayLogic") != null) {
				m_Tab.setDisplayLogic(atts.getValue("DisplayLogic"));
			}
			if (getStringValue(atts,"isInsertRecord") != null) {
				m_Tab.setIsInsertRecord(Boolean.valueOf(atts.getValue("isInsertRecord")).booleanValue());
			}
			if (getStringValue(atts,"isAdvancedTab") != null) {
				m_Tab.setIsAdvancedTab(Boolean.valueOf(atts.getValue("isAdvancedTab")).booleanValue());
			}
			if (m_Tab.save(getTrxName(ctx)) == true){		    	
				record_log (ctx, 1, m_Tab.getName(),"Tab", m_Tab.get_ID(),AD_Backup_ID, Object_Status,"AD_Tab",get_IDWithColumn(ctx, "AD_Table", "TableName", "AD_Tab"));
				element.recordId = m_Tab.getAD_Tab_ID();
			} else {
				record_log (ctx, 0, m_Tab.getName(),"Tab", m_Tab.get_ID(),AD_Backup_ID, Object_Status,"AD_Tab",get_IDWithColumn(ctx, "AD_Table", "TableName", "AD_Tab"));
				throw new POSaveFailedException("Tab");
			}	
		} else {
			element.skip = true;
		}

	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document) throws SAXException
	{
		PackOut packOut = (PackOut)ctx.get("PackOutProcess");
		int AD_Tab_ID = Env.getContextAsInt(ctx, X_AD_Tab.COLUMNNAME_AD_Tab_ID);
		X_AD_Tab m_Tab = new X_AD_Tab (ctx, AD_Tab_ID, getTrxName(ctx));
		AttributesImpl atts = new AttributesImpl();
		createTabBinding(atts,m_Tab);
		document.startElement("","","tab",atts);
		//Fields tags.
		String sql = "SELECT * FROM AD_FIELD WHERE AD_TAB_ID = " + AD_Tab_ID
			+ "ORDER BY SEQNO asc, "+X_AD_Field.COLUMNNAME_AD_Field_ID;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {									
			pstmt = DB.prepareStatement (sql, getTrxName(ctx));		
			rs = pstmt.executeQuery();								
			while (rs.next())
			{
				createField(ctx, document, rs.getInt("AD_Field_ID"));				
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE,e.getLocalizedMessage(), e);
			throw new DatabaseAccessException("Failed to export window tab", e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}					
		document.endElement("","","tab");
		
		if(m_Tab.getAD_Process_ID() > 0 )
		{
			packOut.createProcess(m_Tab.getAD_Process_ID(), document);
		}
		
	}

	private void createField(Properties ctx, TransformerHandler document,
			int AD_Field_ID) throws SAXException {
		Env.setContext(ctx, X_AD_Field.COLUMNNAME_AD_Field_ID, AD_Field_ID);
		fieldHandler.create(ctx, document);
		ctx.remove(X_AD_Field.COLUMNNAME_AD_Field_ID);
	}
	
	private AttributesImpl createTabBinding( AttributesImpl atts, X_AD_Tab m_Tab) 
	{        
		String sql = null;
		String name = null;
		atts.clear();
		if (m_Tab.getAD_Tab_ID() <= PackOut.MAX_OFFICIAL_ID)
			atts.addAttribute("", "", "AD_Tab_ID", "CDATA", Integer.toString(m_Tab.getAD_Tab_ID()));
		atts.addAttribute("","","Name","CDATA",(m_Tab.getName () != null ? m_Tab.getName ():"")); 
		if (m_Tab.getAD_ColumnSortOrder_ID()>0){
			sql = "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?";
			name = DB.getSQLValueString(null,sql,m_Tab.getAD_ColumnSortOrder_ID());
			atts.addAttribute("","","ADColumnSortOrderNameID","CDATA",name);
		}
		else
			atts.addAttribute("","","ADColumnSortOrderNameID","CDATA","");        
		if (m_Tab.getAD_ColumnSortYesNo_ID()>0   ){
			sql = "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?";
			name = DB.getSQLValueString(null,sql,m_Tab.getAD_ColumnSortYesNo_ID());        
			atts.addAttribute("","","ADColumnSortYesNoNameID","CDATA",name);        
		}
		else
			atts.addAttribute("","","ADColumnSortYesNoNameID","CDATA",""); 
		if (m_Tab.getAD_Column_ID()>0  ){        
			sql = "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?";
			name = DB.getSQLValueString(null,sql,m_Tab.getAD_Column_ID());
			atts.addAttribute("","","ADColumnNameID","CDATA",name);
		}
		else        	
			atts.addAttribute("","","ADColumnNameID","CDATA","");       
		if (m_Tab.getAD_Image_ID() >0 ){        
			sql = "SELECT Name FROM AD_Image WHERE AD_Image_ID=?";
			name = DB.getSQLValueString(null,sql,m_Tab.getAD_Image_ID());        
			atts.addAttribute("","","ADImageNameID","CDATA",name);
		}
		else
			atts.addAttribute("","","ADImageNameID","CDATA","");	
		if (m_Tab.getAD_Process_ID() >0 ){        
			sql = "SELECT Name FROM AD_Process WHERE AD_Process_ID=?";
			name = DB.getSQLValueString(null,sql,m_Tab.getAD_Process_ID());
			atts.addAttribute("","","ADProcessNameID","CDATA",name);
		}
		else
			atts.addAttribute("","","ADProcessNameID","CDATA","");        
		if (m_Tab.getAD_Tab_ID() >0  ){
			sql = "SELECT Name FROM AD_Tab WHERE AD_Tab_ID=?";
			name = DB.getSQLValueString(null,sql,m_Tab.getAD_Tab_ID());
			atts.addAttribute("","","ADTabNameID","CDATA",name);
		}
		else
			atts.addAttribute("","","ADTabNameID","CDATA","");
		
		sql = "SELECT TableName FROM AD_Table WHERE AD_Table_ID=?";
		name = DB.getSQLValueString(null,sql,m_Tab.getAD_Table_ID());
		atts.addAttribute("","","ADTableNameID","CDATA",name);        
		sql = "SELECT Name FROM AD_Window WHERE AD_Window_ID=?";
		name = DB.getSQLValueString(null,sql,m_Tab.getAD_Window_ID());
		atts.addAttribute("","","ADWindowNameID","CDATA",name);
		if (m_Tab.getIncluded_Tab_ID() > 0  ){       
			sql = "SELECT Name FROM AD_Tab WHERE AD_Tab_ID=?";
			name = DB.getSQLValueString(null,sql,m_Tab.getIncluded_Tab_ID());
			atts.addAttribute("","","IncludedTabNameID","CDATA",name);
		}
		else
			atts.addAttribute("","","IncludedTabNameID","CDATA","");
		atts.addAttribute("","","CommitWarning","CDATA",(m_Tab.getCommitWarning () != null ? m_Tab.getCommitWarning ():""));        
		atts.addAttribute("","","Description","CDATA",(m_Tab.getDescription () != null ? m_Tab.getDescription ():""));        
		atts.addAttribute("","","EntityType","CDATA",(m_Tab.getEntityType () != null ? m_Tab.getEntityType ():""));        
		atts.addAttribute("","","isHasTree","CDATA",(m_Tab.isHasTree()== true ? "true":"false"));        
		atts.addAttribute("","","Help","CDATA",(m_Tab.getHelp () != null ? m_Tab.getHelp ():""));        
		atts.addAttribute("","","isInfoTab","CDATA",(m_Tab.isInfoTab()== true ? "true":"false"));
		atts.addAttribute("","","isReadOnly","CDATA",(m_Tab.isReadOnly()== true ? "true":"false"));
		atts.addAttribute("","","isSingleRow","CDATA",(m_Tab.isSingleRow()== true ? "true":"false"));
		atts.addAttribute("","","isSortTab","CDATA",(m_Tab.isSortTab()== true ? "true":"false"));
		atts.addAttribute("","","isActive","CDATA",(m_Tab.isActive()== true ? "true":"false"));
		atts.addAttribute("","","IsTranslationTab","CDATA",(m_Tab.isTranslationTab()== true ? "true":"false"));        
		atts.addAttribute("","","OrderByClause","CDATA",(m_Tab.getOrderByClause () != null ? m_Tab.getOrderByClause ():""));
		atts.addAttribute("","","isProcessing","CDATA",(m_Tab.isProcessing()== true ? "true":"false"));
		atts.addAttribute("","","SeqNo","CDATA",(m_Tab.getSeqNo () >= 0 ? "" + m_Tab.getSeqNo ():"0"));
		atts.addAttribute("","","TabLevel","CDATA",(m_Tab.getTabLevel () >= 0 ? "" + m_Tab.getTabLevel ():""));
		atts.addAttribute("","","WhereClause","CDATA",(m_Tab.getWhereClause () != null ? m_Tab.getWhereClause ():""));
		atts.addAttribute("","","ReadOnlyLogic","CDATA",(m_Tab.getReadOnlyLogic() != null ? m_Tab.getReadOnlyLogic ():""));        
		atts.addAttribute("","","DisplayLogic","CDATA",(m_Tab.getDisplayLogic() != null ? m_Tab.getDisplayLogic ():""));        
		atts.addAttribute("","","isInsertRecord","CDATA",(m_Tab.isInsertRecord()== true ? "true":"false"));        
		atts.addAttribute("","","isAdvancedTab","CDATA",(m_Tab.isAdvancedTab()== true ? "true":"false"));        
		atts.addAttribute("","","Syncfields","CDATA","false");                
		return atts;		
	}

}
