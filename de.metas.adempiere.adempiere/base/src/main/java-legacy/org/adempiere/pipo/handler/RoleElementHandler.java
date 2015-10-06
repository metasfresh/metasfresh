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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.DatabaseAccessException;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.MRole;
import org.compiere.model.X_AD_Form;
import org.compiere.model.X_AD_Package_Exp_Detail;
import org.compiere.model.X_AD_Process;
import org.compiere.model.X_AD_Role;
import org.compiere.model.X_AD_Task;
import org.compiere.model.X_AD_User;
import org.compiere.model.X_AD_Window;
import org.compiere.model.X_AD_Workflow;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class RoleElementHandler extends AbstractElementHandler {

	private List<Integer> roles = new ArrayList<Integer>();
	
	private OrgRoleElementHandler orgHandler = new OrgRoleElementHandler();
	private ProcessAccessElementHandler processHandler = new ProcessAccessElementHandler();
	private UserRoleElementHandler userHandler = new UserRoleElementHandler();
	private WindowAccessElementHandler windowHandler = new WindowAccessElementHandler();
	private FormAccessElementHandler formHandler = new FormAccessElementHandler();
	private TaskAccessElementHandler taskHandler = new TaskAccessElementHandler();
	private WorkflowAccessElementHandler workflowHandler = new WorkflowAccessElementHandler();
	
	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("Name"));

		String name = atts.getValue("Name");

		int id = get_ID(ctx, "AD_Role", name);
		MRole m_Role = new MRole(ctx, id, getTrxName(ctx));

		int AD_Backup_ID = -1;
		String Object_Status = null;
		if (id <= 0 && atts.getValue("AD_Role_ID") != null && Integer.parseInt(atts.getValue("AD_Role_ID")) <= PackOut.MAX_OFFICIAL_ID)
			m_Role.setAD_Role_ID(Integer.parseInt(atts.getValue("AD_Role_ID")));
		if (id > 0) {
			AD_Backup_ID = copyRecord(ctx, "AD_Role", m_Role);
			Object_Status = "Update";
		} else {
			Object_Status = "New";
			AD_Backup_ID = 0;
		}

		m_Role.setName(name);
		name = atts.getValue("treemenuname");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "AD_Tree", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_Role.setAD_Tree_Menu_ID(id);
		}

		name = atts.getValue("treeorgname");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "AD_Tree", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_Role.setAD_Tree_Org_ID(id);
		}

		name = atts.getValue("currencycode");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "C_Currency", "ISO_Code", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_Role.setC_Currency_ID(id);
		}

		name = atts.getValue("supervisorid");
		if (name != null && name.trim().length() > 0) {
			id = get_IDWithColumn(ctx, "AD_User", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_Role.setC_Currency_ID(id);
		}

		m_Role.setDescription(getStringValue(atts,"Description"));
		String amtApproval = getStringValue(atts,"AmtApproval");
		if (amtApproval != null)
			m_Role.setAmtApproval(new BigDecimal(amtApproval));
		m_Role.setIsActive(atts.getValue("isActive") != null ? Boolean.valueOf(
				atts.getValue("isActive")).booleanValue() : true);
		m_Role
				.setIsAccessAllOrgs(atts.getValue("isAccessAllOrgs") != null ? Boolean
						.valueOf(atts.getValue("isAccessAllOrgs"))
						.booleanValue()
						: true);
		m_Role
				.setIsCanApproveOwnDoc(atts.getValue("isCanApproveOwnDoc") != null ? Boolean
						.valueOf(atts.getValue("isCanApproveOwnDoc"))
						.booleanValue()
						: true);
		m_Role.setIsCanExport(atts.getValue("isCanExport") != null ? Boolean
				.valueOf(atts.getValue("isCanExport")).booleanValue() : true);
		m_Role.setIsCanReport(atts.getValue("isCanReport") != null ? Boolean
				.valueOf(atts.getValue("isCanReport")).booleanValue() : true);
		m_Role.setIsChangeLog(atts.getValue("isChangeLog") != null ? Boolean
				.valueOf(atts.getValue("isChangeLog")).booleanValue() : true);
		m_Role.setIsManual(atts.getValue("isManual") != null ? Boolean.valueOf(
				atts.getValue("isManual")).booleanValue() : true);
		m_Role
				.setIsPersonalAccess(atts.getValue("isPersonalAccess") != null ? Boolean
						.valueOf(atts.getValue("isPersonalAccess"))
						.booleanValue()
						: true);
		m_Role
				.setIsPersonalLock(atts.getValue("isPersonalLock") != null ? Boolean
						.valueOf(atts.getValue("isPersonalLock"))
						.booleanValue()
						: true);
		m_Role.setIsShowAcct(atts.getValue("isShowAcct") != null ? Boolean
				.valueOf(atts.getValue("isShowAcct")).booleanValue() : true);
		m_Role
				.setIsUseUserOrgAccess(atts.getValue("isUseUserOrgAccess") != null ? Boolean
						.valueOf(atts.getValue("isUseUserOrgAccess"))
						.booleanValue()
						: true);
		m_Role
				.setOverwritePriceLimit(atts.getValue("isOverwritePriceLimit") != null ? Boolean
						.valueOf(atts.getValue("isOverwritePriceLimit"))
						.booleanValue()
						: true);
		m_Role.setPreferenceType(atts.getValue("PreferenceType"));
		m_Role.setUserLevel(atts.getValue("UserLevel"));
		m_Role.setAllow_Info_Account(Boolean.valueOf(atts.getValue("AllowInfoAccount")));
		m_Role.setAllow_Info_Asset(Boolean.valueOf(atts.getValue("AllowInfoAsset")));
		m_Role.setAllow_Info_BPartner(Boolean.valueOf(atts.getValue("AllowInfoBPartner")));
		m_Role.setAllow_Info_CashJournal(Boolean.valueOf(atts.getValue("AllowInfoCashJournal")));
		m_Role.setAllow_Info_InOut(Boolean.valueOf(atts.getValue("AllowInfoInOut")));
		m_Role.setAllow_Info_Invoice(Boolean.valueOf(atts.getValue("AllowInfoInvoice")));
		m_Role.setAllow_Info_Order(Boolean.valueOf(atts.getValue("AllowInfoOrder")));
		m_Role.setAllow_Info_Payment(Boolean.valueOf(atts.getValue("AllowInfoPayment")));
		m_Role.setAllow_Info_Product(Boolean.valueOf(atts.getValue("AllowInfoProduct")));
		m_Role.setAllow_Info_Resource(Boolean.valueOf(atts.getValue("AllowInfoResource")));
		m_Role.setAllow_Info_Schedule(Boolean.valueOf(atts.getValue("AllowInfoSchedule")));
		m_Role.setAllow_Info_Schedule(Boolean.valueOf(atts.getValue("AllowInfoCRP")));
		m_Role.setAllow_Info_Schedule(Boolean.valueOf(atts.getValue("AllowInfoMRP")));
		

		if (m_Role.save(getTrxName(ctx)) == true) {

			record_log(ctx, 1, m_Role.getName(), "Role", m_Role.get_ID(),
					AD_Backup_ID, Object_Status, "AD_Role", get_IDWithColumn(
							ctx, "AD_Table", "TableName", "AD_Role"));
		} else {

			record_log(ctx, 0, m_Role.getName(), "Role", m_Role.get_ID(),
					AD_Backup_ID, Object_Status, "AD_Role", get_IDWithColumn(
							ctx, "AD_Table", "TableName", "AD_Role"));
			throw new POSaveFailedException("Role");
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int Role_id = Env.getContextAsInt(ctx,
				X_AD_Package_Exp_Detail.COLUMNNAME_AD_Role_ID);
		if (roles.contains(Role_id))
			return;
		roles.add(Role_id);
		X_AD_Role m_Role = new X_AD_Role(ctx, Role_id, null);
		AttributesImpl atts = new AttributesImpl();
		createRoleBinding(atts, m_Role);
		document.startElement("", "", "role", atts);

		// Process org access
		String sql = "SELECT * FROM AD_Role_OrgAccess WHERE AD_Role_ID= "
				+ Role_id;
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				createOrgAccess(ctx, document, rs.getInt("AD_Org_ID"), rs
						.getInt("AD_Role_ID"));
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}

		catch (Exception e) {
			log.log(Level.SEVERE, "AD_Role_OrgAccess", e);
			throw new DatabaseAccessException("Failed to export organization role access.");
		}
		// Process user assignment access
		sql = "SELECT * FROM AD_User_Roles WHERE AD_Role_ID= " + Role_id;
		pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				createUserRole(ctx, document, rs.getInt("AD_User_ID"),
						rs.getInt("AD_Role_ID"), rs.getInt("AD_Org_ID"));
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}

		catch (Exception e) {
			log.log(Level.SEVERE, "AD_User_Roles", e);
			throw new DatabaseAccessException("Failed to export user role assignment.");
		}
		
		// Process AD_Window_Access Values
		sql = "SELECT * FROM AD_Window_Access WHERE AD_Role_ID= " + Role_id;
		pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				createWindowAccess(ctx, document, rs
						.getInt("AD_Window_ID"), rs.getInt("AD_Role_ID"));
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}

		catch (Exception e) {
			log.log(Level.SEVERE, "AD_Window_Access", e);
			throw new DatabaseAccessException("Failed to export window access.");
		}

		// Process AD_Process_Access Values
		sql = "SELECT * FROM AD_Process_Access WHERE AD_Role_ID= " + Role_id;
		pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				createProcessAccess(ctx, document, rs
						.getInt("AD_Process_ID"), rs.getInt("AD_Role_ID"));
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}

		catch (Exception e) {
			log.log(Level.SEVERE, "AD_Process_Access", e);
			throw new DatabaseAccessException("Failed to export process access.");
		}

		// Process AD_Form_Access Values
		sql = "SELECT * FROM AD_Form_Access WHERE AD_Role_ID= " + Role_id;
		pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				createFormAccess(ctx, document, rs.getInt("AD_Form_ID"),
						rs.getInt("AD_Role_ID"));
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}

		catch (Exception e) {
			log.log(Level.SEVERE, "AD_Form_Access", e);
			throw new DatabaseAccessException("Failed to export form access.");
		}

		// Process AD_Workflow_Access Values
		sql = "SELECT * FROM AD_Workflow_Access WHERE AD_Role_ID= " + Role_id;
		pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				createWorkflowAccess(ctx, document, rs
						.getInt("AD_Workflow_ID"), rs.getInt("AD_Role_ID"));
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}

		catch (Exception e) {
			log.log(Level.SEVERE, "AD_Workflow_Access", e);
			throw new DatabaseAccessException("Failed to export workflow access.");
		}

		// Process AD_Task_Access Values
		sql = "SELECT * FROM AD_Task_Access WHERE AD_Role_ID= " + Role_id;
		pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				createTaskAccess(ctx, document, rs.getInt("AD_Task_ID"), rs
						.getInt("AD_Role_ID")); 
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}

		catch (Exception e) {
			log.log(Level.SEVERE, "AD_Task_Access", e);
			throw new DatabaseAccessException("Failed to export task access.");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			pstmt = null;
		}
		document.endElement("", "", "role");
	}

	private void createTaskAccess(Properties ctx, TransformerHandler document,
			int AD_Task_ID, int AD_Role_ID) throws SAXException {
		Env.setContext(ctx, X_AD_Task.COLUMNNAME_AD_Task_ID, AD_Task_ID);
		Env.setContext(ctx, X_AD_Role.COLUMNNAME_AD_Role_ID, AD_Role_ID);
		taskHandler.create(ctx, document);
		ctx.remove(X_AD_Task.COLUMNNAME_AD_Task_ID);
		ctx.remove(X_AD_Role.COLUMNNAME_AD_Role_ID);
	}

	private void createWorkflowAccess(Properties ctx,
			TransformerHandler document, int AD_Workflow_ID, int AD_Role_ID) throws SAXException {
		Env.setContext(ctx, X_AD_Workflow.COLUMNNAME_AD_Workflow_ID, AD_Workflow_ID);
		Env.setContext(ctx, X_AD_Role.COLUMNNAME_AD_Role_ID, AD_Role_ID);
		workflowHandler.create(ctx, document);
		ctx.remove(X_AD_Workflow.COLUMNNAME_AD_Workflow_ID);
		ctx.remove(X_AD_Role.COLUMNNAME_AD_Role_ID);
	}

	private void createFormAccess(Properties ctx, TransformerHandler document,
			int AD_Form_ID, int AD_Role_ID) throws SAXException {
		Env.setContext(ctx, X_AD_Form.COLUMNNAME_AD_Form_ID, AD_Form_ID);
		Env.setContext(ctx, X_AD_Role.COLUMNNAME_AD_Role_ID, AD_Role_ID);
		formHandler.create(ctx, document);
		ctx.remove(X_AD_Form.COLUMNNAME_AD_Form_ID);
		ctx.remove(X_AD_Role.COLUMNNAME_AD_Role_ID);
	}

	private void createProcessAccess(Properties ctx,
			TransformerHandler document, int AD_Process_ID, int AD_Role_ID) throws SAXException {
		Env.setContext(ctx, X_AD_Process.COLUMNNAME_AD_Process_ID, AD_Process_ID);
		Env.setContext(ctx, X_AD_Role.COLUMNNAME_AD_Role_ID, AD_Role_ID);
		processHandler.create(ctx, document);
		ctx.remove(X_AD_Process.COLUMNNAME_AD_Process_ID);
		ctx.remove(X_AD_Role.COLUMNNAME_AD_Role_ID);
	}

	private void createWindowAccess(Properties ctx,
			TransformerHandler document, int AD_Window_ID, int AD_Role_ID) throws SAXException {
		Env.setContext(ctx, X_AD_Window.COLUMNNAME_AD_Window_ID, AD_Window_ID);
		Env.setContext(ctx, X_AD_Role.COLUMNNAME_AD_Role_ID, AD_Role_ID);
		windowHandler.create(ctx, document);
		ctx.remove(X_AD_Window.COLUMNNAME_AD_Window_ID);
		ctx.remove(X_AD_Role.COLUMNNAME_AD_Role_ID);
	}

	private void createUserRole(Properties ctx, TransformerHandler document,
			int AD_User_ID, int AD_Role_ID, int AD_Org_ID) throws SAXException {
		Env.setContext(ctx, X_AD_User.COLUMNNAME_AD_User_ID, AD_User_ID);
		Env.setContext(ctx, X_AD_Role.COLUMNNAME_AD_Role_ID, AD_Role_ID);
		Env.setContext(ctx, "AD_Org_ID", AD_Org_ID);
		userHandler.create(ctx, document);
		ctx.remove(X_AD_User.COLUMNNAME_AD_User_ID);
		ctx.remove(X_AD_Role.COLUMNNAME_AD_Role_ID);
		ctx.remove("AD_Org_ID");
	}

	private void createOrgAccess(Properties ctx, TransformerHandler document,
			int AD_Org_ID, int AD_Role_ID) throws SAXException {
		Env.setContext(ctx, "AD_Org_ID", AD_Org_ID);
		Env.setContext(ctx, X_AD_Role.COLUMNNAME_AD_Role_ID, AD_Role_ID);
		orgHandler.create(ctx, document);
		ctx.remove("AD_Org_ID");
		ctx.remove(X_AD_Role.COLUMNNAME_AD_Role_ID);
	}

	private AttributesImpl createRoleBinding(AttributesImpl atts,
			X_AD_Role m_Role) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_Role.getAD_Role_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_Role_ID","CDATA",Integer.toString(m_Role.getAD_Role_ID()));

		if (m_Role.getAD_Tree_Menu_ID() > 0) {
			sql = "SELECT Name FROM AD_Tree WHERE AD_Tree_ID=? AND AD_Tree.TreeType='MM'";
			name = DB.getSQLValueString(null, sql, m_Role.getAD_Tree_Menu_ID());
			atts.addAttribute("", "", "treemenuname", "CDATA", name);
		} else
			atts.addAttribute("", "", "treemenuname", "CDATA", "");

		if (m_Role.getAD_Tree_Org_ID() > 0) {
			sql = "SELECT Name FROM AD_Tree WHERE AD_Tree_ID=? AND AD_Tree.TreeType='OO'";
			name = DB.getSQLValueString(null, sql, m_Role.getAD_Tree_Org_ID());
			atts.addAttribute("", "", "treeorgname", "CDATA", name);
		} else
			atts.addAttribute("", "", "treeorgname", "CDATA", "");

		if (m_Role.getC_Currency_ID() > 0) {
			sql = "SELECT ISO_Code FROM C_Currency WHERE C_Currency_ID=?";
			name = DB.getSQLValueString(null, sql, m_Role.getC_Currency_ID());
			atts.addAttribute("", "", "currencycode", "CDATA", name);
		} else
			atts.addAttribute("", "", "currencycode", "CDATA", "");

		if (m_Role.getSupervisor_ID() > 0) {
			sql = "SELECT Name FROM AD_User WHERE AD_User_ID=?";
			name = DB.getSQLValueString(null, sql, m_Role.getC_Currency_ID());
			atts.addAttribute("", "", "supervisorid", "CDATA", name);
		} else
			atts.addAttribute("", "", "supervisorid", "CDATA", "");

		atts.addAttribute("", "", "Description", "CDATA", (m_Role
				.getDescription() != null ? m_Role.getDescription() : ""));
		atts.addAttribute("", "", "Name", "CDATA",
				(m_Role.getName() != null ? m_Role.getName() : ""));
		atts.addAttribute("", "", "AmtApproval", "CDATA", ("" + m_Role
				.getAmtApproval()));
		atts.addAttribute("", "", "isAccessAllOrgs", "CDATA", (m_Role
				.isAccessAllOrgs() == true ? "true" : "false"));
		atts.addAttribute("", "", "isActive", "CDATA",
				(m_Role.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "isCanApproveOwnDoc", "CDATA", (m_Role
				.isCanApproveOwnDoc() == true ? "true" : "false"));
		atts.addAttribute("", "", "isCanExport", "CDATA",
				(m_Role.isCanExport() == true ? "true" : "false"));
		atts.addAttribute("", "", "isCanReport", "CDATA",
				(m_Role.isCanReport() == true ? "true" : "false"));
		atts.addAttribute("", "", "isChangeLog", "CDATA",
				(m_Role.isChangeLog() == true ? "true" : "false"));
		atts.addAttribute("", "", "isManual", "CDATA",
				(m_Role.isManual() == true ? "true" : "false"));
		atts.addAttribute("", "", "isPersonalAccess", "CDATA", (m_Role
				.isPersonalAccess() == true ? "true" : "false"));
		atts.addAttribute("", "", "isPersonalLock", "CDATA", (m_Role
				.isPersonalLock() == true ? "true" : "false"));
		atts.addAttribute("", "", "isShowAcct", "CDATA",
				(m_Role.isShowAcct() == true ? "true" : "false"));
		atts.addAttribute("", "", "isUseUserOrgAccess", "CDATA", (m_Role
				.isUseUserOrgAccess() == true ? "true" : "false"));
		atts.addAttribute("", "", "isOverwritePriceLimit", "CDATA", (m_Role
				.isOverwritePriceLimit() == true ? "true" : "false"));
		atts
				.addAttribute("", "", "PreferenceType", "CDATA", (m_Role
						.getPreferenceType() != null ? m_Role
						.getPreferenceType() : ""));
		atts.addAttribute("", "", "UserLevel", "CDATA",
				(m_Role.getUserLevel() != null ? m_Role.getUserLevel() : ""));
		
		atts.addAttribute("", "", "AllowInfoAccount", "CDATA", Boolean.toString(m_Role.isAllow_Info_Account()));
		atts.addAttribute("", "", "AllowInfoAsset", "CDATA", Boolean.toString(m_Role.isAllow_Info_Asset()));
		atts.addAttribute("", "", "AllowInfoBPartner", "CDATA", Boolean.toString(m_Role.isAllow_Info_BPartner()));
		atts.addAttribute("", "", "AllowInfoCashJournal", "CDATA", Boolean.toString(m_Role.isAllow_Info_CashJournal()));
		atts.addAttribute("", "", "AllowInfoInOut", "CDATA", Boolean.toString(m_Role.isAllow_Info_InOut()));
		atts.addAttribute("", "", "AllowInfoInvoice", "CDATA", Boolean.toString(m_Role.isAllow_Info_Invoice()));
		atts.addAttribute("", "", "AllowInfoOrder", "CDATA", Boolean.toString(m_Role.isAllow_Info_Order()));
		atts.addAttribute("", "", "AllowInfoPayment", "CDATA", Boolean.toString(m_Role.isAllow_Info_Payment()));
		atts.addAttribute("", "", "AllowInfoProduct", "CDATA", Boolean.toString(m_Role.isAllow_Info_Product()));
		atts.addAttribute("", "", "AllowInfoResource", "CDATA", Boolean.toString(m_Role.isAllow_Info_Resource()));
		atts.addAttribute("", "", "AllowInfoSchedule", "CDATA", Boolean.toString(m_Role.isAllow_Info_Schedule()));
		atts.addAttribute("", "", "AllowInfoCRP", "CDATA", Boolean.toString(m_Role.isAllow_Info_CRP()));
		atts.addAttribute("", "", "AllowInfoMRP", "CDATA", Boolean.toString(m_Role.isAllow_Info_MRP()));
		
		return atts;
	}
}
