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
import org.compiere.model.X_AD_Role;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class OrgRoleElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element) throws SAXException {
		String elementValue = element.getElementValue();
		log.info(elementValue);
		int roleid =0;
		int orgid =0;		
		StringBuffer sqlB = null;
		Attributes atts = element.attributes;
		if (atts.getValue("rolename")!=null){
			String name = atts.getValue("rolename");		
			sqlB = new StringBuffer ("SELECT AD_Role_ID FROM AD_Role WHERE Name= ?");
			roleid = DB.getSQLValue(getTrxName(ctx),sqlB.toString(),name);
		}
		
		if (atts.getValue("orgname")!=null){
			String name = atts.getValue("orgname");		
			sqlB = new StringBuffer ("SELECT AD_Org_ID FROM AD_Org WHERE Name= ?");
			orgid = DB.getSQLValue(getTrxName(ctx),sqlB.toString(),name);
		}
		
		sqlB = new StringBuffer ("SELECT count(*) FROM AD_Role_OrgAccess WHERE AD_Role_ID=? and AD_Org_ID=?");		
		int count = DB.getSQLValue(getTrxName(ctx),sqlB.toString(),roleid,orgid);
		int AD_Backup_ID = -1;
		String Object_Status = null;
		if (count>0){		   	
			Object_Status = "Update";			
			sqlB = new StringBuffer ("UPDATE AD_Role_OrgAccess ")
					.append( "SET isActive = '" + atts.getValue("isActive") )
					.append( "', isReadOnly = '" + atts.getValue("isReadOnly") )
					.append( "' WHERE AD_Role_ID = " + roleid )
					.append( " and AD_Org_ID = " + orgid );
			
			int no = DB.executeUpdate (sqlB.toString(), getTrxName(ctx));
			if (no == -1)
				log.info("Update to org access failed");
		}
		else{
			Object_Status = "New";
			AD_Backup_ID =0;
			sqlB = new StringBuffer ("INSERT INTO AD_Role_OrgAccess")
					.append( "(AD_Client_ID, CreatedBy, UpdatedBy, " ) 
					.append( "AD_Role_ID, AD_Org_ID, isActive, isReadOnly) " )
					.append( "VALUES(" ) 
					.append( " "+ Env.getAD_Client_ID(ctx) )    				
					.append( ", "+ Env.getAD_User_ID(ctx) )
					.append( ", "+ Env.getAD_User_ID(ctx) )
					.append( ", " + roleid )
					.append( ", " + orgid )
					.append( ", '" + atts.getValue("isActive") )
					.append( "', '" + atts.getValue("isReadOnly")+"')");
			
			int no = DB.executeUpdate (sqlB.toString(), getTrxName(ctx));
			if (no == -1)
				log.info("Insert to org access failed");
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_Org_ID = Env.getContextAsInt(ctx, "AD_Org_ID");
		int AD_Role_ID = Env.getContextAsInt(ctx, X_AD_Role.COLUMNNAME_AD_Role_ID);
		AttributesImpl atts = new AttributesImpl();
		createOrgAccessBinding(atts, AD_Org_ID, AD_Role_ID);
		document.startElement("", "", "OrgAccess", atts);
		document.endElement("", "", "OrgAccess");
		
	}

	private AttributesImpl createOrgAccessBinding(AttributesImpl atts,
			int org_id, int role_id) {
		String sql = null;
		String name = null;
		atts.clear();

		sql = "SELECT Name FROM AD_Org WHERE AD_Org_ID=?";
		name = DB.getSQLValueString(null, sql, org_id);
		atts.addAttribute("", "", "orgname", "CDATA", name);

		sql = "SELECT Name FROM AD_Role WHERE AD_Role_ID=?";
		name = DB.getSQLValueString(null, sql, role_id);
		atts.addAttribute("", "", "rolename", "CDATA", name);

		sql = "SELECT isActive FROM AD_Role_OrgAccess WHERE AD_Org_ID="
				+ org_id + " and AD_Role_ID=?";
		String TrueFalse = DB.getSQLValueString(null, sql, role_id);
		atts.addAttribute("", "", "isActive", "CDATA", TrueFalse);

		sql = "SELECT isReadOnly FROM AD_Role_OrgAccess WHERE AD_Org_ID="
				+ org_id + " and AD_Role_ID=?";
		String isReadOnly = DB.getSQLValueString(null, sql, role_id);
		atts.addAttribute("", "", "isReadOnly", "CDATA", isReadOnly);

		return atts;
	}
}
