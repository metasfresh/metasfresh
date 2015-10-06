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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.compiere.model.X_AD_Menu;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class MenuElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;
		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("ADMenuNameID"));
		// String entitytype = atts.getValue("EntityType");
		// if (entitytype.compareTo("U") == 0 || entitytype.compareTo("D") == 0
		// && m_UpdateMode == true ) {

		String name = null;
		int idDetail = 0;
		StringBuffer sqlB = null;

		name = atts.getValue("ADMenuNameID");
		int menuid = get_IDWithColumn(ctx, "AD_Menu", "Name", name);
		X_AD_Menu m_Menu = new X_AD_Menu(ctx, menuid, getTrxName(ctx));
		if (menuid <= 0 && atts.getValue("AD_Menu_ID") != null && Integer.parseInt(atts.getValue("AD_Menu_ID")) <= PackOut.MAX_OFFICIAL_ID)
			m_Menu.setAD_Menu_ID(Integer.parseInt(atts.getValue("AD_Menu_ID")));
		if (menuid > 0) {
			AD_Backup_ID = copyRecord(ctx, "AD_Menu", m_Menu);
			Object_Status = "Update";
		} else {
			Object_Status = "New";
			AD_Backup_ID = 0;
		}

		m_Menu.setName(name);
		name = atts.getValue("ADWindowNameID");
		if (name != null && name.trim().length() > 0) {
			int id = get_IDWithColumn(ctx, "AD_Window", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_Menu.setAD_Window_ID(id);
		}
		
		name = atts.getValue("ADProcessNameID");
		if (name != null && name.trim().length() > 0) {
			int id = get_IDWithColumn(ctx, "AD_Process", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_Menu.setAD_Process_ID(id);
		}
		
		name = atts.getValue("ADFormNameID");
		if (name != null && name.trim().length() > 0) {
			int id = get_IDWithColumn(ctx, "AD_Form", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_Menu.setAD_Form_ID(id);
		}
		
		name = atts.getValue("ADTaskNameID");
		if (name != null && name.trim().length() > 0) {
			int id = get_IDWithColumn(ctx, "AD_Task", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_Menu.setAD_Task_ID(id);
		}
		
		name = atts.getValue("ADWorkbenchNameID");
		if (name != null && name.trim().length() > 0) {
			int id = get_IDWithColumn(ctx, "AD_Workbench", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_Menu.setAD_Workbench_ID(id);
		}
		
		name = atts.getValue("ADWorkflowNameID");
		if (name != null && name.trim().length() > 0) {
			int id = get_IDWithColumn(ctx, "AD_Workflow", "Name", name);
			if (id <= 0) {
				element.defer = true;
				return;
			}
			m_Menu.setAD_Workflow_ID(id);
		}
		
		String action = (atts.getValue("Action") != null ? atts
				.getValue("Action") : " ");
		if (action.compareTo(" ") > -1)
			m_Menu.setAction(action);
		m_Menu.setDescription(getStringValue(atts, "Description"));
		m_Menu.setEntityType(atts.getValue("EntityType"));
		m_Menu.setIsReadOnly(Boolean.valueOf(atts.getValue("isReadOnly"))
				.booleanValue());
		m_Menu.setIsSOTrx(Boolean.valueOf(atts.getValue("isSOTrx"))
				.booleanValue());
		m_Menu.setIsSummary(Boolean.valueOf(atts.getValue("isSummary"))
				.booleanValue());
		m_Menu.setIsActive(Boolean.valueOf(atts.getValue("isActive"))
				.booleanValue());
		if (m_Menu.save(getTrxName(ctx)) == true) {
			try {
				idDetail = record_log(ctx, 1, m_Menu.getName(), "Menu", m_Menu
						.get_ID(), AD_Backup_ID, Object_Status, "AD_Menu",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Menu"));
			} catch (SAXException e) {
				log.info("setmenu:" + e);
			}
		} else {
			try {
				idDetail = record_log(ctx, 0, m_Menu.getName(), "Menu", m_Menu
						.get_ID(), AD_Backup_ID, Object_Status, "AD_Menu",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Menu"));
			} catch (SAXException e) {
				log.info("setmenu:" + e);
			}
		}
		name = atts.getValue("ADParentMenuNameID");
		int id = get_ID(ctx, "AD_Menu", name);

		String sql2 = "SELECT count(Parent_ID) FROM AD_TREENODEMM WHERE AD_Tree_ID = 10"
				+ " AND Node_ID = " + menuid;
		int countRecords = DB.getSQLValue(getTrxName(ctx), sql2);
		if (countRecords > 0) {
			StringBuffer sqlC = new StringBuffer(
					"select * from AD_TREENODEMM where AD_Tree_ID = 10 and "
							+ " Node_ID =?");
			try {
				PreparedStatement pstmt1 = DB.prepareStatement(sqlC.toString(),
						getTrxName(ctx));
				pstmt1.setInt(1, menuid);
				ResultSet rs1 = pstmt1.executeQuery();
				if (rs1.next()) {

					String colValue = null;
					ResultSetMetaData meta = rs1.getMetaData();
					int columns = meta.getColumnCount();
					int tableID = get_IDWithColumn(ctx, "AD_Table",
							"TableName", "AD_TreeNodeMM");

					for (int q = 1; q <= columns; q++) {

						String col_Name = meta.getColumnName(q);
						StringBuffer sql = new StringBuffer(
								"SELECT AD_Column_ID FROM AD_column WHERE Upper(ColumnName) = '"
										+ col_Name + "' AND AD_Table_ID = ?");
						int columnID = DB.getSQLValue(getTrxName(ctx), sql
								.toString(), tableID);
						sql = new StringBuffer(
								"SELECT AD_Reference_ID FROM AD_COLUMN WHERE AD_Column_ID = "
										+ (columnID == -1 ? "null" : columnID));
						int referenceID = DB.getSQLValue(getTrxName(ctx), sql
								.toString());
						int idBackup = DB.getNextID(Env
								.getAD_Client_ID(ctx), "AD_Package_Imp_Backup",
								getTrxName(ctx));
						if (referenceID == 20 || referenceID == 28)
							if (rs1.getObject(q).equals("Y"))
								colValue = "true";
							else
								colValue = "false";
						else
							colValue = rs1.getObject(q).toString();

						StringBuffer sqlD = new StringBuffer(
								"INSERT INTO AD_Package_Imp_Backup"
										+ "(AD_Client_ID, AD_Org_ID, CreatedBy, UpdatedBy, "
										+ "AD_PACKAGE_IMP_BACKUP_ID, AD_PACKAGE_IMP_DETAIL_ID, AD_PACKAGE_IMP_ID,"
										+ " AD_TABLE_ID, AD_COLUMN_ID, AD_REFERENCE_ID, COLVALUE)"
										+ "VALUES(" + " "
										+ Env.getAD_Client_ID(ctx)
										+ ", "
										+ Env.getAD_Org_ID(ctx)
										+ ", "
										+ Env.getAD_User_ID(ctx)
										+ ", "
										+ Env.getAD_User_ID(ctx)
										+ ", "
										+ idBackup
										+ ", "
										+ idDetail
										+ ", "
										+ getPackageImpId(ctx)
										+ ", "
										+ tableID
										+ ", "
										+ (columnID == -1 ? "null" : columnID)
										+ ", "
										+ (referenceID == -1 ? "null" : referenceID)
										+ ", '" + colValue + "')");
						int no = DB.executeUpdate(sqlD.toString(),
								getTrxName(ctx));
						if (no == -1)
							log.info("Insert to import backup failed");
					}

				}
				rs1.close();
				pstmt1.close();
				pstmt1 = null;

			} catch (Exception e) {
				log.info("get_IDWithMasterID:" + e);
			}

			sqlB = new StringBuffer("UPDATE AD_TREENODEMM ").append(
					"SET Parent_ID = " + id).append(
					" , SeqNo = " + atts.getValue("ADParentSeqno")).append(
					" WHERE AD_Tree_ID = 10").append(
					" AND Node_ID = " + m_Menu.getAD_Menu_ID());
		} else {
			sqlB = new StringBuffer("INSERT INTO AD_TREENODEMM").append(
					"(AD_Client_ID, AD_Org_ID, CreatedBy, UpdatedBy, ").append(
					"Parent_ID, SeqNo, AD_Tree_ID, Node_ID)").append(
					"VALUES(0, 0, 0, 0, ").append(
					id + "," + atts.getValue("ADParentSeqno") + ", 10, "
							+ m_Menu.getAD_Menu_ID() + ")");
		}
		DB.executeUpdate(sqlB.toString(), getTrxName(ctx));
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_Menu_ID = Env.getContextAsInt(ctx, "AD_Menu_ID");
		X_AD_Menu m_Menu = new X_AD_Menu(ctx, AD_Menu_ID, null);
		if (m_Menu.isSummary() == false) {
			createApplication(ctx, document, AD_Menu_ID);
		} else {
			AttributesImpl atts = new AttributesImpl();
			createMenuBinding(atts, m_Menu);
			document.startElement("", "", "menu", atts);
			createModule(ctx, document, AD_Menu_ID);
			document.endElement("", "", "menu");
		}
	}

	private AttributesImpl createMenuBinding(AttributesImpl atts,
			X_AD_Menu m_Menu) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_Menu.getAD_Menu_ID() > 0) {

			sql = "SELECT Name FROM AD_Menu WHERE AD_Menu_ID=?";
			name = DB.getSQLValueString(null, sql, m_Menu.getAD_Menu_ID());
			atts.addAttribute("", "", "ADMenuNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADMenuNameID", "CDATA", "");
		if (m_Menu.getAD_Window_ID() > 0) {
			sql = "SELECT Name FROM AD_Window WHERE AD_Window_ID=?";
			name = DB.getSQLValueString(null, sql, m_Menu.getAD_Window_ID());
			atts.addAttribute("", "", "ADWindowNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADWindowNameID", "CDATA", "");
		if (m_Menu.getAD_Process_ID() > 0) {
			sql = "SELECT Name FROM AD_Process WHERE AD_Process_ID=?";
			name = DB.getSQLValueString(null, sql, m_Menu.getAD_Process_ID());
			atts.addAttribute("", "", "ADProcessNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADProcessNameID", "CDATA", "");
		if (m_Menu.getAD_Form_ID() > 0) {
			sql = "SELECT Name FROM AD_Form WHERE AD_Form_ID=?";
			name = DB.getSQLValueString(null, sql, m_Menu.getAD_Form_ID());
			atts.addAttribute("", "", "ADFormNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADFormNameID", "CDATA", "");
		if (m_Menu.getAD_Task_ID() > 0) {
			sql = "SELECT Name FROM AD_Task WHERE AD_Task_ID=?";
			name = DB.getSQLValueString(null, sql, m_Menu.getAD_Task_ID());
			atts.addAttribute("", "", "ADTaskNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADTaskNameID", "CDATA", "");
		if (m_Menu.getAD_Workbench_ID() > 0) {
			sql = "SELECT Name FROM AD_Workbench WHERE AD_Workbench_ID=?";
			name = DB.getSQLValueString(null, sql, m_Menu.getAD_Workbench_ID());
			atts.addAttribute("", "", "ADWorkbenchNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADWorkbenchNameID", "CDATA", "");
		if (m_Menu.getAD_Workflow_ID() > 0) {
			sql = "SELECT Name FROM AD_Workflow WHERE AD_Workflow_ID=?";
			name = DB.getSQLValueString(null, sql, m_Menu.getAD_Workflow_ID());
			atts.addAttribute("", "", "ADWorkflowNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADWorkflowNameID", "CDATA", "");
		sql = "SELECT Parent_ID FROM AD_TreeNoDemm WHERE AD_Tree_ID = 10 and Node_ID=?";
		int id = DB.getSQLValue(null, sql, m_Menu.getAD_Menu_ID());
		if (id > 0) {
			sql = "SELECT Name FROM AD_Menu WHERE AD_Menu_ID=?";
			name = DB.getSQLValueString(null, sql, id);
			atts.addAttribute("", "", "ADParentMenuNameID", "CDATA", name);
		}
		sql = "SELECT SeqNo FROM AD_TreeNoDemm WHERE AD_Tree_ID = 10 and Node_ID=?";
		id = DB.getSQLValue(null, sql, m_Menu.getAD_Menu_ID());
		if (m_Menu.getAD_Menu_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_Menu_ID","CDATA",Integer.toString(m_Menu.getAD_Menu_ID()));
		atts.addAttribute("", "", "ADParentSeqno", "CDATA", "" + id);
		atts.addAttribute("", "", "Action", "CDATA",
				(m_Menu.getAction() != null ? m_Menu.getAction() : ""));
		atts.addAttribute("", "", "Description", "CDATA", (m_Menu
				.getDescription() != null ? m_Menu.getDescription() : ""));
		atts.addAttribute("", "", "EntityType", "CDATA", (m_Menu
				.getEntityType() != null ? m_Menu.getEntityType() : ""));
		atts.addAttribute("", "", "isActive", "CDATA",
				(m_Menu.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "isReadOnly", "CDATA",
				(m_Menu.isReadOnly() == true ? "true" : "false"));
		atts.addAttribute("", "", "isSOTrx", "CDATA",
				(m_Menu.isSOTrx() == true ? "true" : "false"));
		atts.addAttribute("", "", "isSummary", "CDATA",
				(m_Menu.isSummary() == true ? "true" : "false"));
		return atts;
	}

	private void createApplication(Properties ctx, TransformerHandler document,
			int AD_Menu_ID) throws SAXException {
		PackOut packOut = (PackOut)ctx.get("PackOutProcess");
		String sql = null;
		// int x = 0;
		sql = "SELECT A.Node_ID, B.AD_Menu_ID, B.Name, B.AD_WINDOW_ID, B.AD_WORKFLOW_ID, B.AD_TASK_ID, "
				+ "B.AD_PROCESS_ID, B.AD_FORM_ID, B.AD_WORKBENCH_ID "
				+ "FROM AD_TreeNoDemm A, AD_Menu B "
				+ "WHERE A.Node_ID = "
				+ AD_Menu_ID + " AND A.Node_ID = B.AD_Menu_ID";

		AttributesImpl atts = new AttributesImpl();
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));

		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				X_AD_Menu m_Menu = new X_AD_Menu(ctx, rs.getInt("AD_Menu_ID"),
						null);
				atts = createMenuBinding(atts, m_Menu);
				document.startElement("", "", "menu", atts);
				if (rs.getInt("AD_WINDOW_ID") > 0
						|| rs.getInt("AD_WORKFLOW_ID") > 0
						|| rs.getInt("AD_TASK_ID") > 0
						|| rs.getInt("AD_PROCESS_ID") > 0
						|| rs.getInt("AD_FORM_ID") > 0
						|| rs.getInt("AD_WORKBENCH_ID") > 0) {
					// Call CreateWindow.
					if (rs.getInt("AD_WINDOW_ID") > 0) {
						packOut.createWindow(rs.getInt("AD_WINDOW_ID"), document);
					}
					// Call CreateProcess.
					else if (rs.getInt("AD_PROCESS_ID") > 0) {
						packOut.createProcess(rs.getInt("AD_PROCESS_ID"), document);
					}
					// Call CreateTask.
					else if (rs.getInt("AD_TASK_ID") > 0) {
						packOut.createTask(rs.getInt("AD_TASK_ID"), document);
					}
					// Call CreateForm.
					else if (rs.getInt("AD_FORM_ID") > 0) {
						packOut.createForm(rs.getInt("AD_FORM_ID"), document);
					}
					// Call CreateWorkflow
					else if (rs.getInt("AD_Workflow_ID") > 0) {
						packOut.createWorkflow(rs.getInt("AD_Workflow_ID"), 
								document);
					}
					// Call CreateModule because entry is a summary menu
				} else {
					createModule(ctx, document, rs.getInt("Node_ID"));
				}
				document.endElement("", "", "menu");
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			log.log(Level.SEVERE, "getWindows", e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			pstmt = null;
		}
	}

	public void createModule(Properties ctx, TransformerHandler document,
			int menu_id) throws SAXException {
		PackOut packOut = (PackOut)ctx.get("PackOutProcess");
		String sql = null;
		sql = "SELECT A.Node_ID, B.AD_Menu_ID, B.Name, B.AD_WINDOW_ID, B.AD_WORKFLOW_ID, B.AD_TASK_ID, "
				+ "B.AD_PROCESS_ID, B.AD_FORM_ID, B.AD_WORKBENCH_ID "
				+ "FROM AD_TreeNoDemm A, AD_Menu B "
				+ "WHERE A.Parent_ID = "
				+ menu_id + " AND A.Node_ID = B.AD_Menu_ID";

		AttributesImpl atts = new AttributesImpl();
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				// Menu tag Start.
				X_AD_Menu m_Menu = new X_AD_Menu(ctx, rs.getInt("AD_Menu_ID"),
						null);
				atts = createMenuBinding(atts, m_Menu);
				document.startElement("", "", "menu", atts);
				if (rs.getInt("AD_WINDOW_ID") > 0
						|| rs.getInt("AD_WORKFLOW_ID") > 0
						|| rs.getInt("AD_TASK_ID") > 0
						|| rs.getInt("AD_PROCESS_ID") > 0
						|| rs.getInt("AD_FORM_ID") > 0
						|| rs.getInt("AD_WORKBENCH_ID") > 0) {
					// Call CreateWindow.
					if (rs.getInt("AD_WINDOW_ID") > 0) {
						packOut.createWindow(rs.getInt("AD_WINDOW_ID"), document);
					}
					// Call CreateProcess.
					else if (rs.getInt("AD_PROCESS_ID") > 0) {
						packOut.createProcess(rs.getInt("AD_PROCESS_ID"), 
								document);
					}
					// Call CreateTask.
					else if (rs.getInt("AD_TASK_ID") > 0) {
						packOut.createTask(rs.getInt("AD_TASK_ID"), document);
					}
					// Call CreateForm.
					else if (rs.getInt("AD_FORM_ID") > 0) {
						packOut.createForm(rs.getInt("AD_FORM_ID"), document);
					}
					// Call CreateWorkflow
					else if (rs.getInt("AD_Workflow_ID") > 0) {
						packOut.createWorkflow(rs.getInt("AD_Workflow_ID"), 
								document);
					}
					// Call CreateModule because entry is a summary menu
				} else {
					createModule(ctx, document, rs.getInt("Node_ID"));
				}
				document.endElement("", "", "menu");
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			log.log(Level.SEVERE, "getWindows", e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			pstmt = null;
		}
	}
}
