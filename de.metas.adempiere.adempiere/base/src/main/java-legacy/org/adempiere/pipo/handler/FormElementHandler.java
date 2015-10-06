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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.MForm;
import org.compiere.model.X_AD_Form;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class FormElementHandler extends AbstractElementHandler {

	private List<Integer> forms = new ArrayList<Integer>();
	
	public void startElement(Properties ctx, Element element) throws SAXException {
		String elementValue = element.getElementValue();
		Attributes atts = element.attributes;
		log.info(elementValue+" "+atts.getValue("ADFormNameID"));
		
		String entitytype = atts.getValue("EntityType");		
		if (isProcessElement(ctx, entitytype)) {
			String name = atts.getValue("ADFormNameID");
			int id = get_ID(ctx, "AD_Form", name);
			MForm m_Form = new MForm(ctx, id, getTrxName(ctx));
			int AD_Backup_ID = -1;
			String Object_Status = null;
			if (id <= 0 && atts.getValue("AD_Form_ID") != null && Integer.parseInt(atts.getValue("AD_Form_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_Form.setAD_Form_ID(Integer.parseInt(atts.getValue("AD_Form_ID")));
			if (id > 0){
				AD_Backup_ID = copyRecord(ctx, "AD_Form",m_Form);
				Object_Status = "Update";
			}
			else{
				Object_Status = "New";
				AD_Backup_ID =0;
			}	    
			m_Form.setClassname (atts.getValue("Classname"));
			m_Form.setIsBetaFunctionality (Boolean.valueOf(atts.getValue("isBetaFunctionality")).booleanValue());
			m_Form.setAccessLevel(atts.getValue("AccessLevel"));
			m_Form.setDescription(getStringValue(atts, "Description"));
			m_Form.setEntityType(atts.getValue("EntityType"));
			m_Form.setHelp(getStringValue(atts, "Help"));
			m_Form.setIsActive(atts.getValue("isActive") != null ? Boolean.valueOf(atts.getValue("isActive")).booleanValue():true);
			m_Form.setName(atts.getValue("Name")); 
			
			if (m_Form.save(getTrxName(ctx)) == true){		    	
				record_log (ctx, 1, m_Form.getName(),"Form", m_Form.get_ID(),AD_Backup_ID, Object_Status,"AD_Form",get_IDWithColumn(ctx, "AD_Table", "TableName", "AD_Form"));           		        		
			}
			else{
				record_log (ctx, 0, m_Form.getName(),"Form", m_Form.get_ID(),AD_Backup_ID, Object_Status,"AD_Form",get_IDWithColumn(ctx, "AD_Table", "TableName", "AD_Form"));
				throw new POSaveFailedException("Failed to save form definition");
			}
		} else {
			element.skip = true;
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_Form_ID = Env.getContextAsInt(ctx, "AD_Form_ID");
		if (forms.contains(AD_Form_ID)) return;
		
		forms.add(AD_Form_ID);
		X_AD_Form m_Form = new X_AD_Form (ctx, AD_Form_ID, null);
		AttributesImpl atts = new AttributesImpl();
		createFormBinding(atts,m_Form);
		document.startElement("","","form",atts);
		document.endElement("","","form");		
	}
	
	private AttributesImpl createFormBinding( AttributesImpl atts, X_AD_Form m_Form) 
	{
		String sql = null;
        String name = null;
        atts.clear();
        if (m_Form.getAD_Form_ID()> 0 ){
            sql = "SELECT Name FROM AD_Form WHERE AD_Form_ID=?";
            name = DB.getSQLValueString(null,sql,m_Form.getAD_Form_ID());
            if (name != null )
                atts.addAttribute("","","ADFormNameID","CDATA",name);
            else
            	atts.addAttribute("","","ADFormNameID","CDATA","");
        } else {
        	atts.addAttribute("","","ADFormNameID","CDATA","");
        }
		if (m_Form.getAD_Form_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_Form_ID","CDATA",Integer.toString(m_Form.getAD_Form_ID()));
        	
        atts.addAttribute("","","Classname","CDATA",(m_Form.getClassname () != null ? m_Form.getClassname ():""));
        atts.addAttribute("","","isBetaFunctionality","CDATA",(m_Form.isBetaFunctionality()== true ? "true":"false"));
        atts.addAttribute("","","AccessLevel","CDATA",(m_Form.getAccessLevel () != null ? m_Form.getAccessLevel ():""));
        atts.addAttribute("","","Description","CDATA",(m_Form.getDescription () != null ? m_Form.getDescription ():""));
        atts.addAttribute("","","isActive","CDATA",(m_Form.isActive()== true ? "true":"false"));
        atts.addAttribute("","","EntityType","CDATA",(m_Form.getEntityType () != null ? m_Form.getEntityType ():""));
        atts.addAttribute("","","Help","CDATA",(m_Form.getHelp() != null ? m_Form.getHelp():""));
        atts.addAttribute("","","Name","CDATA",(m_Form.getName() != null ? m_Form.getName():""));
        return atts;
	}

}
