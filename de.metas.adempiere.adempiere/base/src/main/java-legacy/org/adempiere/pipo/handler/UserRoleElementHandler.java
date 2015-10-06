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
import org.compiere.model.X_AD_User;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class UserRoleElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element) throws SAXException {
		String elementValue = element.getElementValue();
		log.info(elementValue);
		int roleid =0;
		int userid =0;
		int orgid =0;
		
		StringBuffer sqlB = null;
		Attributes atts = element.attributes;
		if (atts.getValue("username")!=null){
			String name = atts.getValue("username");		
			sqlB = new StringBuffer ("SELECT AD_User_ID FROM AD_User WHERE Name= ?");
			userid = DB.getSQLValue(getTrxName(ctx),sqlB.toString(),name);
		}
		
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
		
		sqlB = new StringBuffer ("SELECT count(*) FROM AD_User_Roles WHERE AD_User_ID = ? and AD_Role_ID = ?");		
		int count = DB.getSQLValue(getTrxName(ctx),sqlB.toString(),userid,roleid);
		
		int AD_Backup_ID = -1;
		String Object_Status = null;
		if (count>0){
			//AD_Backup_ID = copyRecord("AD_Role",m_Role);
			Object_Status = "Update";			
			sqlB = new StringBuffer ("UPDATE AD_User_Roles ")
					.append( "SET isActive = '" + atts.getValue("isActive")+"'" )
					.append( " WHERE AD_User_ID = " + userid )
					.append( " and AD_Role_ID = " + roleid )
					.append( " and AD_Org_ID = " + orgid );
			
			int no = DB.executeUpdate (sqlB.toString(), getTrxName(ctx));
			if (no == -1)
				log.info("Update to user roles failed");
		}
		else{
			Object_Status = "New";
			AD_Backup_ID =0;
			sqlB = new StringBuffer ("INSERT INTO AD_User_Roles") 
					.append( "(AD_Client_ID,  CreatedBy, UpdatedBy, " ) 
					.append( "AD_User_ID, AD_Role_ID, AD_Org_ID, isActive) " )
					.append( "VALUES(" ) 
					.append( " "+ Env.getAD_Client_ID(ctx) )    				
					.append( ", "+ Env.getAD_User_ID(ctx) )
					.append( ", "+ Env.getAD_User_ID(ctx) ) 
					.append( ", " +userid ) 
					.append( ", " + roleid )
					.append( ", " + orgid )
					.append( ", '" + atts.getValue("isActive")+"')" );
			int no = DB.executeUpdate (sqlB.toString(), getTrxName(ctx));
			if (no == -1)
				log.info("Insert to user roles failed");
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_User_ID = Env.getContextAsInt(ctx, X_AD_User.COLUMNNAME_AD_User_ID);
		int AD_Role_ID = Env.getContextAsInt(ctx, X_AD_Role.COLUMNNAME_AD_Role_ID);
		int AD_Org_ID = Env.getContextAsInt(ctx, "AD_Org_ID");
		AttributesImpl atts = new AttributesImpl();
		createUserAssignBinding(atts, AD_User_ID,AD_Role_ID, AD_Org_ID);
		document.startElement("", "", "userrole", atts);
		document.endElement("", "", "userrole");
	}

	private AttributesImpl createUserAssignBinding(AttributesImpl atts,
			int user_id, int role_id, int org_id) {
		String sql = null;
		String name = null;
		atts.clear();

		sql = "SELECT Name FROM AD_User WHERE AD_User_ID=?";
		name = DB.getSQLValueString(null, sql, user_id);
		atts.addAttribute("", "", "username", "CDATA", name);

		sql = "SELECT Name FROM AD_Role WHERE AD_Role_ID=?";
		name = DB.getSQLValueString(null, sql, role_id);
		atts.addAttribute("", "", "rolename", "CDATA", name);

		sql = "SELECT isActive FROM AD_User_Roles WHERE AD_User_ID=" + user_id
				+ " and AD_Role_ID=?";
		String TrueFalse = DB.getSQLValueString(null, sql, role_id);
		atts.addAttribute("", "", "isActive", "CDATA", TrueFalse);

		sql = "SELECT Name FROM AD_Org WHERE AD_Org_ID=?";
		name = DB.getSQLValueString(null, sql, org_id);
		atts.addAttribute("", "", "orgname", "CDATA", name);

		return atts;
	}
}
