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
 *****************************************************************************/
package org.adempiere.pipo.handler;

import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.X_AD_ImpFormat_Row;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ImpFormatRowElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element) throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;
		Attributes atts = element.attributes;
		log.info(elementValue+" "+atts.getValue("Name"));
		
		int impformid = get_ID(ctx, "AD_ImpFormat", atts.getValue("ADImpFormatNameID"));
		if (impformid <= 0) {
			element.defer = true;
			return;
		}
		
		String name = atts.getValue("ADTableNameID");
		int tableid = 0;
		if (name != null && name.trim().length() > 0) {
			tableid = get_IDWithColumn(ctx, "AD_Table", "TableName", name);
			if (tableid <= 0) {
				element.defer = true;
				return;
			}
				
		}
		
		name = atts.getValue("ADColumnNameID");
		int columnid = 0;
		if (name != null && name.trim().length() > 0) {
			columnid  = get_IDWithMasterAndColumn (ctx, "AD_Column","ColumnName", name, "AD_Table", tableid);
			if (columnid <= 0) {
				element.defer = true;
				return;
			}
		}
		
		StringBuffer sqlB = new StringBuffer ("SELECT AD_ImpFormat_Row_ID FROM AD_ImpFormat_Row WHERE AD_Column_ID=? and AD_ImpFormat_ID=?");		
		int id = DB.getSQLValue(getTrxName(ctx),sqlB.toString(),columnid,impformid);
		X_AD_ImpFormat_Row m_ImpFormat_row = new X_AD_ImpFormat_Row(ctx, id, getTrxName(ctx));
		if (id <= 0 && atts.getValue("AD_ImpFormat_Row_ID") != null && Integer.parseInt(atts.getValue("AD_ImpFormat_Row_ID")) <= PackOut.MAX_OFFICIAL_ID)
			m_ImpFormat_row.setAD_ImpFormat_Row_ID(Integer.parseInt(atts.getValue("AD_ImpFormat_Row_ID")));
		if (id > 0){
			AD_Backup_ID = copyRecord(ctx, "AD_ImpFormat",m_ImpFormat_row);
			Object_Status = "Update";			
		}
		else{
			Object_Status = "New";
			AD_Backup_ID =0;
		}
		m_ImpFormat_row.setName(atts.getValue("Name"));	    
		m_ImpFormat_row.setIsActive(atts.getValue("isActive") != null ? Boolean.valueOf(atts.getValue("isActive")).booleanValue():true);
		if (columnid > 0)
			m_ImpFormat_row.setAD_Column_ID(columnid);
		m_ImpFormat_row.setAD_ImpFormat_ID(impformid);
		m_ImpFormat_row.setDataFormat(atts.getValue("DataFormat"));
		m_ImpFormat_row.setDataType(atts.getValue("DataType"));
		m_ImpFormat_row.setDecimalPoint(atts.getValue("DecimalPoint"));
		m_ImpFormat_row.setDivideBy100(atts.getValue("isDivideBy100") != null ? Boolean.valueOf(atts.getValue("isDivideBy100")).booleanValue():true);
		m_ImpFormat_row.setConstantValue(atts.getValue("ConstantValue"));
		m_ImpFormat_row.setCallout(atts.getValue("Callout"));	    
		m_ImpFormat_row.setEndNo(Integer.parseInt(atts.getValue("EndNo")));
		m_ImpFormat_row.setScript(atts.getValue("Script"));
		m_ImpFormat_row.setSeqNo(Integer.parseInt(atts.getValue("SeqNo")));
		m_ImpFormat_row.setStartNo(Integer.parseInt(atts.getValue("StartNo")));	    
		if (m_ImpFormat_row.save(getTrxName(ctx)) == true){		    	
			record_log (ctx, 1, m_ImpFormat_row.getName(),"ImpFormatRow", m_ImpFormat_row.get_ID(),AD_Backup_ID, Object_Status,"AD_ImpFormat",get_IDWithColumn(ctx, "AD_Table", "TableName", "m_ImpFormat_row"));           		        		
		}
		else{
			record_log (ctx, 0, m_ImpFormat_row.getName(),"ImpFormatRow", m_ImpFormat_row.get_ID(),AD_Backup_ID, Object_Status,"AD_ImpFormat",get_IDWithColumn(ctx, "AD_Table", "TableName", "m_ImpFormat_row"));
			throw new POSaveFailedException("Failed to import Import Format Row.");
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_ImpFormat_Row_ID = Env.getContextAsInt(ctx, X_AD_ImpFormat_Row.COLUMNNAME_AD_ImpFormat_Row_ID);
		X_AD_ImpFormat_Row m_ImpFormat_Row = new X_AD_ImpFormat_Row (ctx, AD_ImpFormat_Row_ID, getTrxName(ctx));
		AttributesImpl atts = new AttributesImpl();
		createImpFormatRowBinding(atts,m_ImpFormat_Row);
		document.startElement("","","impformatrow",atts);
		document.endElement("","","impformatrow");
	}

	private AttributesImpl createImpFormatRowBinding( AttributesImpl atts, X_AD_ImpFormat_Row m_ImpFormat_Row) 
	{	
		String sql = null;
		String name = null;
		atts.clear();
		if (m_ImpFormat_Row.getAD_ImpFormat_ID()> 0 ){
			sql = "SELECT Name FROM AD_ImpFormat WHERE AD_ImpFormat_ID=?";
			name = DB.getSQLValueString(null,sql,m_ImpFormat_Row.getAD_ImpFormat_ID());
			atts.addAttribute("","","ADImpFormatNameID","CDATA",name);
		}
		else
			atts.addAttribute("","","ADImpFormatNameID","CDATA","");
		
		if (m_ImpFormat_Row.getAD_Column_ID()> 0 ){
			sql = "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?";
			name = DB.getSQLValueString(null,sql,m_ImpFormat_Row.getAD_Column_ID());
			atts.addAttribute("","","ADColumnNameID","CDATA",name);
		}
		else
			atts.addAttribute("","","ADColumnNameID","CDATA","");
		
		if (m_ImpFormat_Row.getAD_Column_ID()> 0 ){
			sql = "SELECT AD_Table_ID FROM AD_Column WHERE AD_Column_ID=?";
			int idTable = DB.getSQLValue(null, sql,m_ImpFormat_Row.getAD_Column_ID());
			sql = "SELECT TableName FROM AD_Table WHERE AD_Table_ID=?";
			name = DB.getSQLValueString(null,sql,idTable);
			atts.addAttribute("","","ADTableNameID","CDATA",name);
		}
		else
			atts.addAttribute("","","ADTableNameID","CDATA","");
		if (m_ImpFormat_Row.getAD_ImpFormat_Row_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_ImpFormat_Row_ID","CDATA",Integer.toString(m_ImpFormat_Row.getAD_ImpFormat_Row_ID()));
		
		atts.addAttribute("","","Name","CDATA",(m_ImpFormat_Row.getName () != null ? m_ImpFormat_Row.getName ():""));
		atts.addAttribute("","","SeqNo","CDATA",""+m_ImpFormat_Row.getSeqNo());
		atts.addAttribute("","","StartNo","CDATA",""+m_ImpFormat_Row.getStartNo());
		atts.addAttribute("","","EndNo","CDATA",""+m_ImpFormat_Row.getEndNo());
		atts.addAttribute("","","DataType","CDATA",(m_ImpFormat_Row.getDataType () != null ? m_ImpFormat_Row.getDataType():""));
		atts.addAttribute("","","DataFormat","CDATA",(m_ImpFormat_Row.getDataFormat () != null ? m_ImpFormat_Row.getDataFormat():""));
		atts.addAttribute("","","DecimalPoint","CDATA",(m_ImpFormat_Row.getDecimalPoint () != null ? m_ImpFormat_Row.getDecimalPoint():""));
		atts.addAttribute("","","isDivideBy100","CDATA",(m_ImpFormat_Row.isDivideBy100 ()== true ? "true":"false"));
		atts.addAttribute("","","ConstantValue","CDATA",(m_ImpFormat_Row.getConstantValue() != null ? m_ImpFormat_Row.getConstantValue():""));
		atts.addAttribute("","","Callout","CDATA",(m_ImpFormat_Row.getCallout() != null ? m_ImpFormat_Row.getCallout():""));
		atts.addAttribute("","","Script","CDATA",(m_ImpFormat_Row.getScript() != null ? m_ImpFormat_Row.getScript():""));
		atts.addAttribute("","","isActive","CDATA",(m_ImpFormat_Row.isActive()== true ? "true":"false"));
		
		return atts;
	}
}
