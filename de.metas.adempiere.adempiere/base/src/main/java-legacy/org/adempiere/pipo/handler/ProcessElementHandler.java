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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.DatabaseAccessException;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.X_AD_Process;
import org.compiere.model.X_AD_Process_Para;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ProcessElementHandler extends AbstractElementHandler {

	private ProcessParaElementHandler paraHandler = new ProcessParaElementHandler();
	
	private List<Integer> processes = new ArrayList<Integer>();

	@Override
	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("Name"));
		int id = 0;
		String entitytype = atts.getValue("EntityType");
		if (isProcessElement(ctx, entitytype)) {
			String value = atts.getValue("Value");

			// Get New process.
			id = get_IDWithColumn(ctx, "AD_Process", "Value", value);

			X_AD_Process m_Process = null;
			int AD_Backup_ID = -1;
			String Object_Status = null;
			if (id > 0) {
				m_Process = new X_AD_Process(ctx, id, getTrxName(ctx));
				AD_Backup_ID = copyRecord(ctx, "AD_Process", m_Process);
				Object_Status = "Update";
			} else {
				m_Process = new X_AD_Process(ctx, id, getTrxName(ctx));
				if (id <= 0 && atts.getValue("AD_Process_ID") != null && Integer.parseInt(atts.getValue("AD_Process_ID")) <= PackOut.MAX_OFFICIAL_ID)
					m_Process.setAD_Process_ID(Integer.parseInt(atts.getValue("AD_Process_ID")));
				Object_Status = "New";
				AD_Backup_ID = 0;
			}

			String name = atts.getValue("Name");
			m_Process.setName(name);

			name = atts.getValue("ADWorkflowNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_Workflow", "Name", name);
				if (id <= 0) {
					element.defer = true;
					element.unresolved = "AD_Workflow: " + name;
					return;
				}
				m_Process.setAD_Workflow_ID(id);
			}

			name = atts.getValue("ADPrintFormatNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_PrintFormat", "Name", name);
				if (id <= 0) {
					if (element.pass == 1) {
						element.defer = true;
						element.unresolved = "AD_PrintFormat: " + name;
						return;
					} else {
						log.warn("AD_PrintFormat: " + name + " not found for Process: " + m_Process.getName());
					}
				}
				if (id > 0)
					m_Process.setAD_PrintFormat_ID(id);
			}

			name = atts.getValue("ADReportViewNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_ReportView", "Name", name);
				if (id <= 0) {
					if (element.pass == 1) {
						element.defer = true;
						element.unresolved = "AD_ReportView: " + name;
						return;
					} else {
						log.warn("AD_ReportView: " + name + " not found for Process: " + m_Process.getName());
					}
				}
				if (id > 0)
					m_Process.setAD_ReportView_ID(id);
			}

			m_Process.setAccessLevel(atts.getValue("AccessLevel"));
			m_Process.setClassname(getStringValue(atts, "Classname"));
			m_Process.setDescription(getStringValue(atts, "Description"));
			m_Process.setEntityType(atts.getValue("EntityType"));
			m_Process.setHelp(getStringValue(atts, "Help"));
			m_Process.setIsBetaFunctionality(Boolean.valueOf(
					atts.getValue("isBetaFunctionality")).booleanValue());
			m_Process.setIsDirectPrint(Boolean.valueOf(
					atts.getValue("isDirectPrint")).booleanValue());
			m_Process.setIsReport(Boolean.valueOf(atts.getValue("isReport"))
					.booleanValue());
			m_Process.setName(atts.getValue("Name"));

			m_Process.setProcedureName(getStringValue(atts, "ProcedureName"));
			m_Process.setIsActive(atts.getValue("isActive") != null ? Boolean
					.valueOf(atts.getValue("isActive")).booleanValue() : true);
			m_Process.setValue(atts.getValue("Value"));
			m_Process.setWorkflowValue(atts.getValue("WorkflowValue"));
			m_Process.setShowHelp((getStringValue(atts, "ShowHelp")));
			m_Process.setJasperReport(getStringValue(atts, "JasperReport"));
			if (m_Process.save(getTrxName(ctx)) == true) {
				record_log(ctx, 1, m_Process.getName(), "Process", m_Process
						.get_ID(), AD_Backup_ID, Object_Status, "AD_Process",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Process"));
				element.recordId = m_Process.getAD_Process_ID();
			} else {
				record_log(ctx, 0, m_Process.getName(), "Process", m_Process
						.get_ID(), AD_Backup_ID, Object_Status, "AD_Process",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Process"));
				throw new POSaveFailedException("Process");
			}
		} else {
			element.skip = true;
		}
	}

	@Override
	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	@Override
	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_Process_ID = Env.getContextAsInt(ctx, "AD_Process_ID");
		if (processes.contains(AD_Process_ID))
			return;
		processes.add(AD_Process_ID);
		PackOut packOut = (PackOut) ctx.get("PackOutProcess");
		String sqlW = "SELECT AD_Process_ID FROM AD_PROCESS WHERE AD_PROCESS_ID = "
				+ AD_Process_ID;

		AttributesImpl atts = new AttributesImpl();
		PreparedStatement pstmt1 = null;
		pstmt1 = DB.prepareStatement(sqlW, getTrxName(ctx));
		try {
			ResultSet rs1 = pstmt1.executeQuery();
			while (rs1.next()) {
				X_AD_Process m_Process = new X_AD_Process(ctx, rs1
						.getInt("AD_Process_ID"), null);
				log.info("AD_ReportView_ID: "
						+ m_Process.getAD_Process_ID());

				if (m_Process.isReport() && m_Process.getAD_ReportView_ID() > 0) {
					packOut.createReportview(m_Process.getAD_ReportView_ID(),
							document);
				}
				if (m_Process.isReport() && m_Process.getAD_PrintFormat_ID() > 0) {

					packOut.createPrintFormat(m_Process.getAD_PrintFormat_ID(),
							document);
				}
				if (m_Process.getAD_Workflow_ID() > 0) {

					packOut.createWorkflow(m_Process.getAD_Workflow_ID(), 
							document);
				}
				createProcessBinding(atts, m_Process);
				document.startElement("", "", "process", atts);
				// processpara tags
				String sqlP = "SELECT * FROM AD_PROCESS_PARA WHERE AD_PROCESS_ID = "+ AD_Process_ID
				+" ORDER BY "+X_AD_Process_Para.COLUMNNAME_SeqNo+","+X_AD_Process_Para.COLUMNNAME_AD_Process_Para_ID;
				PreparedStatement pstmtP = null;
				pstmtP = DB.prepareStatement(sqlP, getTrxName(ctx));
				try {
					ResultSet rsP = pstmtP.executeQuery();
					while (rsP.next()) {
						if (rsP.getInt("AD_Reference_ID") > 0)
							packOut.createReference(rsP
									.getInt("AD_Reference_ID"), document);
						if (rsP.getInt("AD_Reference_Value_ID") > 0)
							packOut.createReference(rsP
									.getInt("AD_Reference_Value_ID"), 
									document);
						if (rsP.getInt("AD_Val_Rule_ID") > 0)
							packOut.createDynamicRuleValidation (rsP.getInt("AD_Val_Rule_ID"), document);
						
						createProcessPara(ctx, document, rsP
								.getInt("AD_Process_Para_ID"));
					}
					rsP.close();
					pstmtP.close();
					pstmtP = null;
				} catch (Exception e) {
					log.error("getProcess_Para", e);
					if (e instanceof SAXException)
						throw (SAXException) e;
					else if (e instanceof SQLException)
						throw new DatabaseAccessException("Failed to export process.", e);
					else if (e instanceof RuntimeException)
						throw (RuntimeException) e;
					else
						throw new RuntimeException("Failed to export process.", e);
				} finally {
					try {
						if (pstmtP != null)
							pstmtP.close();
					} catch (Exception e) {
					}
					pstmtP = null;
				}
				document.endElement("", "", "process");
			}
			rs1.close();
			pstmt1.close();
			pstmt1 = null;
		} catch (Exception e) {
			log.error("getProcess", e);
		} finally {
			try {
				if (pstmt1 != null)
					pstmt1.close();
			} catch (Exception e) {
			}
			pstmt1 = null;
		}

	}

	private void createProcessPara(Properties ctx, TransformerHandler document,
			int AD_Process_Para_ID) throws SAXException {
		Env.setContext(ctx, X_AD_Process_Para.COLUMNNAME_AD_Process_Para_ID,
				AD_Process_Para_ID);
		paraHandler.create(ctx, document);
		ctx.remove(X_AD_Process_Para.COLUMNNAME_AD_Process_Para_ID);
	}

	private AttributesImpl createProcessBinding(AttributesImpl atts,
			X_AD_Process m_Process) {
		String sql = null;
		String name = null;
		atts.clear();

		if (m_Process.getAD_Process_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_Process_ID","CDATA",Integer.toString(m_Process.getAD_Process_ID()));

		atts.addAttribute("", "", "Name", "CDATA",
				(m_Process.getName() != null ? m_Process.getName() : ""));

		if (m_Process.getAD_Workflow_ID() > 0) {
			sql = "SELECT Name FROM AD_Workflow WHERE AD_Workflow_ID=?";
			name = DB.getSQLValueString(null, sql, m_Process
					.getAD_Workflow_ID());
			atts.addAttribute("", "", "ADWorkflowNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADWorkflowNameID", "CDATA", "");
		if (m_Process.getAD_Process_ID() > 0) {
			sql = "SELECT Name FROM AD_Process WHERE AD_Process_ID=?";
			name = DB
					.getSQLValueString(null, sql, m_Process.getAD_Process_ID());
			atts.addAttribute("", "", "ADProcessNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADProcessNameID", "CDATA", "");
		if (m_Process.getAD_PrintFormat_ID() > 0) {
			sql = "SELECT Name FROM AD_PrintFormat WHERE AD_PrintFormat_ID=?";
			name = DB.getSQLValueString(null, sql, m_Process
					.getAD_PrintFormat_ID());
			atts.addAttribute("", "", "ADPrintFormatNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADPrintFormatNameID", "CDATA", "");
		if (m_Process.getAD_ReportView_ID() > 0) {
			sql = "SELECT Name FROM AD_ReportView WHERE AD_ReportView_ID=?";
			name = DB.getSQLValueString(null, sql, m_Process
					.getAD_ReportView_ID());
			atts.addAttribute("", "", "ADReportViewNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADReportViewNameID", "CDATA", "");
		atts.addAttribute("", "", "AccessLevel", "CDATA", (m_Process
				.getAccessLevel() != null ? m_Process.getAccessLevel() : ""));
		atts.addAttribute("", "", "Classname", "CDATA", (m_Process
				.getClassname() != null ? m_Process.getClassname() : ""));
		atts.addAttribute("", "", "Description", "CDATA", (m_Process
				.getDescription() != null ? m_Process.getDescription() : ""));
		atts.addAttribute("", "", "EntityType", "CDATA", (m_Process
				.getEntityType() != null ? m_Process.getEntityType() : ""));
		atts.addAttribute("", "", "Help", "CDATA",
				(m_Process.getHelp() != null ? m_Process.getHelp() : ""));
		atts.addAttribute("", "", "isBetaFunctionality", "CDATA", (m_Process
				.isBetaFunctionality() == true ? "true" : "false"));
		atts.addAttribute("", "", "isDirectPrint", "CDATA", (m_Process
				.isDirectPrint() == true ? "true" : "false"));
		atts.addAttribute("", "", "isReport", "CDATA",
				(m_Process.isReport() == true ? "true" : "false"));
		atts.addAttribute("", "", "isActive", "CDATA",
				(m_Process.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "ProcedureName", "CDATA",
				(m_Process.getProcedureName() != null ? m_Process
						.getProcedureName() : ""));
		atts.addAttribute("", "", "StatisticCount", "CDATA", "0");
		atts.addAttribute("", "", "StatisticSeconds", "CDATA", "0");
		atts.addAttribute("", "", "Value", "CDATA",
				(m_Process.getValue() != null ? m_Process.getValue() : ""));
		atts.addAttribute("", "", "WorkflowValue", "CDATA",
				(m_Process.getWorkflowValue() != null ? m_Process
						.getWorkflowValue() : ""));
		atts.addAttribute("", "", "ShowHelp", "CDATA", 
				(m_Process.getShowHelp() != null ? m_Process.getShowHelp() : ""));
		atts.addAttribute("", "", "JasperReport", "CDATA", 
				(m_Process.getJasperReport() != null ? m_Process.getJasperReport() : ""));
		return atts;
	}
}
