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
import org.adempiere.pipo.exception.DatabaseAccessException;
import org.compiere.model.X_AD_Form;
import org.compiere.model.X_AD_Role;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class FormAccessElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element) throws SAXException {
		String elementValue = element.getElementValue();
		log.info(elementValue);
		int roleid =0;
		int formid =0;		
		StringBuffer sqlB = null;
		Attributes atts = element.attributes;
		if (atts.getValue("rolename")!=null){
			String name = atts.getValue("rolename");		
			sqlB = new StringBuffer ("SELECT AD_Role_ID FROM AD_Role WHERE Name= ?");
			roleid = DB.getSQLValue(getTrxName(ctx),sqlB.toString(),name);
		}
		
		if (atts.getValue("formname")!=null){
			String name = atts.getValue("formname");		
			sqlB = new StringBuffer ("SELECT AD_Form_ID FROM AD_Form WHERE Name= ?");
			formid = DB.getSQLValue(getTrxName(ctx),sqlB.toString(),name);
		}
		
		sqlB = new StringBuffer ("SELECT count(*) FROM AD_Form_Access WHERE AD_Role_ID=? and AD_Form_ID=?");		
		int count = DB.getSQLValue(getTrxName(ctx),sqlB.toString(),roleid,formid);
		String Object_Status = null;
		int AD_Backup_ID = -1;
		if (count>0){		   	
			Object_Status = "Update";			
			sqlB = new StringBuffer ("UPDATE AD_Form_Access ")
					.append( "SET isActive = '" + atts.getValue("isActive") )
					.append( "', isReadWrite = '" + atts.getValue("isReadWrite") )
					.append( "' WHERE AD_Role_ID = " + roleid )
					.append( " and AD_Form_ID = " + formid );
			
			int no = DB.executeUpdate (sqlB.toString(), getTrxName(ctx));
			if (no == -1) {
				log.info("Update to form access failed");
				throw new DatabaseAccessException("Update to form access failed");
			}
		}
		else{
			Object_Status = "New";
			AD_Backup_ID =0;
			sqlB = new StringBuffer ("INSERT INTO AD_Form_Access" 
					+   "(AD_Client_ID, AD_Org_ID, CreatedBy, UpdatedBy, " 
					+   "AD_Role_ID, AD_Form_ID, isActive, isReadWrite) "
					+	"VALUES(" 
					+	" "+ Env.getAD_Client_ID(ctx)
					+	", "+ Env.getAD_Org_ID(ctx)
					+	", "+ Env.getAD_User_ID(ctx)
					+	", "+ Env.getAD_User_ID(ctx)
					+	", " + roleid
					+	", " + formid
					+	", '" + atts.getValue("isActive")
					+	"', '" + atts.getValue("isReadWrite")+"')");
			
			int no = DB.executeUpdate (sqlB.toString(), getTrxName(ctx));
			if (no == -1) {
				log.info("Insert to form access failed");
				throw new DatabaseAccessException("Insert to form access failed");
			}
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_Form_ID = Env.getContextAsInt(ctx, X_AD_Form.COLUMNNAME_AD_Form_ID);
		int AD_Role_ID = Env.getContextAsInt(ctx, X_AD_Role.COLUMNNAME_AD_Role_ID);
		AttributesImpl atts = new AttributesImpl();
		createFormAccessBinding(atts, AD_Form_ID, AD_Role_ID);
		document.startElement("", "", "formaccess", atts);
		document.endElement("", "", "formaccess");
	}
	
	private AttributesImpl createFormAccessBinding(AttributesImpl atts,
			int form_id, int role_id) {
		String sql = null;
		String name = null;
		atts.clear();

		sql = "SELECT Name FROM AD_Form WHERE AD_Form_ID=?";
		name = DB.getSQLValueString(null, sql, form_id);
		atts.addAttribute("", "", "formname", "CDATA", name);

		sql = "SELECT Name FROM AD_Role WHERE AD_Role_ID=?";
		name = DB.getSQLValueString(null, sql, role_id);
		atts.addAttribute("", "", "rolename", "CDATA", name);

		sql = "SELECT isActive FROM AD_Form_Access WHERE AD_Form_ID=" + form_id
				+ " and AD_Role_ID=?";
		String TrueFalse = DB.getSQLValueString(null, sql, role_id);
		atts.addAttribute("", "", "isActive", "CDATA", TrueFalse);

		sql = "SELECT isReadWrite FROM AD_Form_Access WHERE AD_Form_ID="
				+ form_id + " and AD_Role_ID=?";
		String isReadWrite = DB.getSQLValueString(null, sql, role_id);
		atts.addAttribute("", "", "isReadWrite", "CDATA", isReadWrite);

		return atts;
	}

}
