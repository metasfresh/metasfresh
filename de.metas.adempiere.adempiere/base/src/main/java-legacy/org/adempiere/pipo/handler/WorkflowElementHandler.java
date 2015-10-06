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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.compiere.model.X_AD_Package_Exp_Detail;
import org.compiere.model.X_AD_WF_NextCondition;
import org.compiere.model.X_AD_WF_Node;
import org.compiere.model.X_AD_WF_NodeNext;
import org.compiere.model.X_AD_Workflow;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.wf.MWorkflow;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class WorkflowElementHandler extends AbstractElementHandler {

	private WorkflowNodeElementHandler nodeHandler = new WorkflowNodeElementHandler();
	private WorkflowNodeNextElementHandler nodeNextHandler = new WorkflowNodeNextElementHandler();
	private WorkflowNodeNextConditionElementHandler nextConditionHandler = new WorkflowNodeNextConditionElementHandler();
	
	private List<Integer> workflows = new ArrayList<Integer>();

	public void startElement(Properties ctx, Element element)
			throws SAXException {
		Attributes atts = element.attributes;
		String elementValue = element.getElementValue();
		log.info(elementValue + " " + atts.getValue("Name"));
		String entitytype = atts.getValue("EntityType");
		log.info("entitytype " + atts.getValue("EntityType"));

		if (isProcessElement(ctx, entitytype)) {
			
			String workflowName = atts.getValue("Name");

			int id = get_IDWithColumn(ctx, "AD_Workflow", "name", workflowName);
			if (id > 0 && workflows.contains(id)) {
				element.skip = true;
				return;
			}

			MWorkflow m_Workflow = new MWorkflow(ctx, id, getTrxName(ctx));
			int AD_Backup_ID = -1;
			String Object_Status = null;
			if (id <= 0 && atts.getValue("AD_Workflow_ID") != null && Integer.parseInt(atts.getValue("AD_Workflow_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_Workflow.setAD_Workflow_ID(Integer.parseInt(atts.getValue("AD_Workflow_ID")));
			if (id > 0) {
				AD_Backup_ID = copyRecord(ctx, "AD_Workflow", m_Workflow);
				Object_Status = "Update";
			} else {
				Object_Status = "New";
				AD_Backup_ID = 0;
			}

			String name = atts.getValue("ADWorkflowResponsibleNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_WF_Responsible", "Name", name);
				if (id <= 0) {
					element.defer = true;
					element.unresolved = "AD_WF_Responsible: " + name;
					return;
				}
				m_Workflow.setAD_WF_Responsible_ID(id);
			}

			name = atts.getValue("ADTableNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_Table", "TableName", name);
				if (id <= 0) {
					element.defer = true;
					element.unresolved = "AD_Table: " + name;
					return;
				}
				m_Workflow.setAD_Table_ID(id);

			}

			name = atts.getValue("ADWorkflowProcessorNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_WorkflowProcessor", "Name", name);
				if (id <= 0) {
					element.defer = true;
					element.unresolved = "AD_WorkflowProcessor: " + name;
					return;
				}
				m_Workflow.setAD_WorkflowProcessor_ID(id);

			}
			
			m_Workflow.setValue(atts.getValue("Value"));
			m_Workflow.setName(workflowName);
			m_Workflow.setIsBetaFunctionality (Boolean.valueOf(atts.getValue("isBetaFunctionality")).booleanValue());
			m_Workflow.setAccessLevel(atts.getValue("AccessLevel"));
			m_Workflow.setDescription(getStringValue(atts,"Description"));
			m_Workflow.setHelp(getStringValue(atts,"Help"));
			m_Workflow.setDurationUnit(getStringValue(atts,"DurationUnit"));
			m_Workflow.setAuthor(getStringValue(atts,"Author"));
			if(getStringValue(atts, "Version") != null)
				m_Workflow.setVersion(Integer.valueOf(atts.getValue("Version")));
			if(getStringValue(atts, "Priority") != null)
				m_Workflow.setPriority(Integer.valueOf(atts.getValue("Priority")));
			if(getStringValue(atts, "Limit") != null)
				m_Workflow.setDurationLimit(Integer.valueOf(atts.getValue("Limit")));
			if(getStringValue(atts, "Duration") != null)
				m_Workflow.setDuration(Integer.valueOf(atts.getValue("Duration")));
			if(getStringValue(atts, "Cost") != null)
				m_Workflow.setCost(new BigDecimal(atts.getValue("Cost")));
			
			m_Workflow.setWorkingTime(Integer.valueOf(atts
					.getValue("WorkingTime")));
			m_Workflow.setWaitingTime(Integer.valueOf(atts
					.getValue("WaitingTime")));
			m_Workflow.setPublishStatus(atts.getValue("PublishStatus"));
			m_Workflow.setWorkflowType(atts.getValue("WorkflowType"));
			m_Workflow.setDocValueLogic(getStringValue(atts,"DocValueLogic"));
			m_Workflow.setIsValid(atts.getValue("isValid") != null ? Boolean
					.valueOf(atts.getValue("isValid")).booleanValue() : true);
			m_Workflow.setEntityType(atts.getValue("EntityType"));
			m_Workflow.setAD_WF_Node_ID(-1);
			log.info("about to execute m_Workflow.save");
			if (m_Workflow.save(getTrxName(ctx)) == true) {
				log.info("m_Workflow save success");
				record_log(ctx, 1, m_Workflow.getName(), "Workflow", m_Workflow
						.get_ID(), AD_Backup_ID, Object_Status, "AD_Workflow",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Workflow"));
				workflows.add(m_Workflow.getAD_Workflow_ID());
				element.recordId = m_Workflow.getAD_Workflow_ID();
			} else {
				log.info("m_Workflow save failure");
				record_log(ctx, 0, m_Workflow.getName(), "Workflow", m_Workflow
						.get_ID(), AD_Backup_ID, Object_Status, "AD_Workflow",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Workflow"));
				throw new POSaveFailedException("MWorkflow");
			}
		} else {
			element.skip = true;
		}
	}

	/**
	 * @param ctx
	 * @param element 
	 */
	public void endElement(Properties ctx, Element element) throws SAXException {
		if (!element.defer && !element.skip && element.recordId > 0) {
			Attributes atts = element.attributes;
			//set start node
			String name = atts.getValue("ADWorkflowNodeNameID");
			if (name != null && name.trim().length() > 0) {
				MWorkflow m_Workflow = new MWorkflow(ctx, element.recordId, getTrxName(ctx));
				int id = get_IDWithMasterAndColumn(ctx, "AD_WF_Node", "Name", name, "AD_Workflow", m_Workflow.getAD_Workflow_ID()); 
				if (id <= 0) {
					log.warning("Failed to resolve start node reference for workflow element. Workflow=" 
							+ m_Workflow.getName() + " StartNode=" + name);
					return;
				}
				m_Workflow.setAD_WF_Node_ID(id);
				if (m_Workflow.save(getTrxName(ctx)) == true) {
					log.info("m_Workflow update success");
					record_log(ctx, 1, m_Workflow.getName(), "Workflow", m_Workflow
							.get_ID(), 0, "Update", "AD_Workflow",
							get_IDWithColumn(ctx, "AD_Table", "TableName",
									"AD_Workflow"));
					workflows.add(m_Workflow.getAD_Workflow_ID());
					element.recordId = m_Workflow.getAD_Workflow_ID();
				} else {
					log.info("m_Workflow update fail");
					record_log(ctx, 0, m_Workflow.getName(), "Workflow", m_Workflow
							.get_ID(), 0, "Update", "AD_Workflow",
							get_IDWithColumn(ctx, "AD_Table", "TableName",
									"AD_Workflow"));
					throw new POSaveFailedException("MWorkflow");
				}
			}			
		}
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_Workflow_ID = Env.getContextAsInt(ctx,
				X_AD_Package_Exp_Detail.COLUMNNAME_AD_Workflow_ID);
		if (workflows.contains(AD_Workflow_ID))
			return;
		
		workflows.add(AD_Workflow_ID);
		String sql = "SELECT Name FROM AD_Workflow WHERE  AD_Workflow_ID= "
				+ AD_Workflow_ID;
		int ad_wf_nodenext_id = 0;
		int ad_wf_nodenextcondition_id = 0;
		AttributesImpl atts = new AttributesImpl();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = DB.prepareStatement(sql, getTrxName(ctx));

		try {

			rs = pstmt.executeQuery();

			while (rs.next()) {
				X_AD_Workflow m_Workflow = new X_AD_Workflow(ctx,
						AD_Workflow_ID, null);
				
				createWorkflowBinding(atts, m_Workflow);
				document.startElement("", "", "workflow", atts);
				String sql1 = "SELECT AD_WF_Node_ID FROM AD_WF_Node WHERE AD_Workflow_ID = "
						+ AD_Workflow_ID
						+ " ORDER BY "+X_AD_WF_Node.COLUMNNAME_AD_WF_Node_ID
						;

				PreparedStatement pstmt1 = null;
				ResultSet rs1 = null;
				try {
					pstmt1 = DB.prepareStatement(sql1, getTrxName(ctx));
					// Generated workflowNodeNext(s) and
					// workflowNodeNextCondition(s)
					rs1 = pstmt1.executeQuery();
					while (rs1.next()) {

						int nodeId = rs1.getInt("AD_WF_Node_ID");
						createNode(ctx, document, nodeId);

						ad_wf_nodenext_id = 0;

						String sqlnn = "SELECT AD_WF_NodeNext_ID FROM AD_WF_NodeNext WHERE AD_WF_Node_ID = ?"
							+ " ORDER BY "+X_AD_WF_NodeNext.COLUMNNAME_AD_WF_NodeNext_ID;
						PreparedStatement pstmtnn = null;
						ResultSet rsnn = null;
						try {
							pstmtnn = DB.prepareStatement(sqlnn, getTrxName(ctx));
							pstmtnn.setInt(1, nodeId);
							rsnn = pstmtnn.executeQuery();
							while (rsnn.next()) {
								ad_wf_nodenext_id = rsnn.getInt("AD_WF_NodeNext_ID");
								if (ad_wf_nodenext_id > 0) {
									createNodeNext(ctx, document, ad_wf_nodenext_id);

									ad_wf_nodenextcondition_id = 0;

									String sqlnnc = "SELECT AD_WF_NextCondition_ID FROM AD_WF_NextCondition WHERE AD_WF_NodeNext_ID = ?"
													+ " ORDER BY "+X_AD_WF_NextCondition.COLUMNNAME_AD_WF_NextCondition_ID;
									PreparedStatement pstmtnnc = null;
									ResultSet rsnnc = null;
									try {
										pstmtnnc = DB.prepareStatement(sqlnnc, getTrxName(ctx));
										pstmtnnc.setInt(1, ad_wf_nodenext_id);
										rsnnc = pstmtnnc.executeQuery();
										while (rsnnc.next()) {
											ad_wf_nodenextcondition_id = rsnnc.getInt("AD_WF_NextCondition_ID");
											log.info("ad_wf_nodenextcondition_id: " + String.valueOf(ad_wf_nodenextcondition_id));
											if (ad_wf_nodenextcondition_id > 0) {
												createNodeNextCondition(ctx, document, ad_wf_nodenextcondition_id);
											}
										}
									} finally {
										DB.close(rsnnc, pstmtnnc);
										rsnnc = null;
										pstmtnnc = null;
									}				
								}
							}
							
						} finally {
							DB.close(rsnn, pstmtnn);
							rsnn = null;
							pstmtnn = null;
						}				

					}
				} finally {
					DB.close(rs1, pstmt1);
					rs1 = null;
					pstmt1 = null;
					
					document.endElement("", "", "workflow");
				}				
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "Workflow", e);
			if (e instanceof SAXException)
				throw (SAXException) e;
			else if (e instanceof SQLException)
				throw new DatabaseAccessException("Failed to export workflow.", e);
			else
				throw new RuntimeException("Failed to export workflow.", e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	private void createNodeNextCondition(Properties ctx,
			TransformerHandler document, int ad_wf_nodenextcondition_id)
			throws SAXException {
		Env.setContext(ctx,
				X_AD_WF_NextCondition.COLUMNNAME_AD_WF_NextCondition_ID,
				ad_wf_nodenextcondition_id);
		nextConditionHandler.create(ctx, document);
		ctx.remove(X_AD_WF_NextCondition.COLUMNNAME_AD_WF_NextCondition_ID);
	}

	private void createNodeNext(Properties ctx, TransformerHandler document,
			int ad_wf_nodenext_id) throws SAXException {
		Env.setContext(ctx, X_AD_WF_NodeNext.COLUMNNAME_AD_WF_NodeNext_ID,
				ad_wf_nodenext_id);
		nodeNextHandler.create(ctx, document);
		ctx.remove(X_AD_WF_NodeNext.COLUMNNAME_AD_WF_NodeNext_ID);
	}

	private void createNode(Properties ctx, TransformerHandler document,
			int AD_WF_Node_ID) throws SAXException {
		Env.setContext(ctx, X_AD_WF_Node.COLUMNNAME_AD_WF_Node_ID,
				AD_WF_Node_ID);
		nodeHandler.create(ctx, document);
		ctx.remove(X_AD_WF_Node.COLUMNNAME_AD_WF_Node_ID);
	}

	private AttributesImpl createWorkflowBinding(AttributesImpl atts,
			X_AD_Workflow m_Workflow) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_Workflow.getAD_Workflow_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_Workflow_ID","CDATA",Integer.toString(m_Workflow.getAD_Workflow_ID()));
		atts.addAttribute("", "", "Value", "CDATA", (m_Workflow.getValue() != null ? m_Workflow.getValue() : ""));
		atts.addAttribute("", "", "Name", "CDATA",
				(m_Workflow.getName() != null ? m_Workflow.getName() : ""));
		if (m_Workflow.getAD_Table_ID() > 0) {
			sql = "SELECT TableName FROM AD_Table WHERE AD_Table_ID=?";
			name = DB.getSQLValueString(null, sql, m_Workflow.getAD_Table_ID());
			atts.addAttribute("", "", "ADTableNameID", "CDATA", 
				(name != null ? name : ""));
		} else
			atts.addAttribute("", "", "ADTableNameID", "CDATA", "");

		if (m_Workflow.getAD_WF_Node_ID() > 0) {
			sql = "SELECT Name FROM AD_WF_Node WHERE AD_WF_Node_ID=?";
			name = DB.getSQLValueString(null, sql, m_Workflow
					.getAD_WF_Node_ID());
			atts.addAttribute("", "", "ADWorkflowNodeNameID", "CDATA", 
				(name != null ? name : ""));
		} else
			atts.addAttribute("", "", "ADWorkflowNodeNameID", "CDATA", "");
		
		if (m_Workflow.getAD_WF_Responsible_ID() > 0) {
			sql = "SELECT Name FROM AD_WF_Responsible WHERE AD_WF_Responsible_ID=?";
			name = DB.getSQLValueString(null, sql, m_Workflow
					.getAD_WF_Responsible_ID());
			atts.addAttribute("", "", "ADWorkflowResponsibleNameID", "CDATA",
				(name != null ? name : ""));
		} else
			atts.addAttribute("", "", "ADWorkflowResponsibleNameID", "CDATA",
					"");
		
		if (m_Workflow.getAD_WorkflowProcessor_ID() > 0) {
			sql = "SELECT Name FROM  AD_WorkflowProcessor_ID WHERE AD_WorkflowProcessor_ID=?";
			name = DB.getSQLValueString(null, sql, m_Workflow
					.getAD_WorkflowProcessor_ID());
			atts.addAttribute("", "", "ADWorkflowProcessorNameID", "CDATA",
				(name != null ? name : ""));
		} else
			atts.addAttribute("", "", "ADWorkflowProcessorNameID", "CDATA", "");
		
        atts.addAttribute("","","isBetaFunctionality","CDATA",(m_Workflow.isBetaFunctionality()== true ? "true":"false"));

        atts.addAttribute("", "", "AccessLevel", "CDATA", (m_Workflow
				.getAccessLevel() != null ? m_Workflow.getAccessLevel() : ""));
		atts
				.addAttribute("", "", "DurationUnit", "CDATA", (m_Workflow
						.getDurationUnit() != null ? m_Workflow
						.getDurationUnit() : ""));
		atts.addAttribute("", "", "Help", "CDATA",
				(m_Workflow.getHelp() != null ? m_Workflow.getHelp() : ""));
		atts.addAttribute("", "", "Description", "CDATA", (m_Workflow
				.getDescription() != null ? m_Workflow.getDescription() : ""));
		atts.addAttribute("", "", "EntityType", "CDATA", (m_Workflow
				.getEntityType() != null ? m_Workflow.getEntityType() : ""));
		atts.addAttribute("", "", "Author", "CDATA",
				(m_Workflow.getAuthor() != null ? m_Workflow.getAuthor() : ""));
		atts.addAttribute("", "", "Version", "CDATA", (""
				+ m_Workflow.getVersion() != null ? ""
				+ m_Workflow.getVersion() : ""));
		// FIXME: Handle dates
		// atts.addAttribute("","","ValidFrom","CDATA",(m_Workflow.getValidFrom
		// ().toGMTString() != null ?
		// m_Workflow.getValidFrom().toGMTString():""));
		// atts.addAttribute("","","ValidTo","CDATA",(m_Workflow.getValidTo
		// ().toGMTString() != null ?
		// m_Workflow.getValidTo().toGMTString():""));
		atts.addAttribute("", "", "Priority", "CDATA", ("" + m_Workflow
				.getPriority()));
		atts.addAttribute("", "", "Limit", "CDATA",
				("" + m_Workflow.getDurationLimit()));
		atts.addAttribute("", "", "Duration", "CDATA", ("" + m_Workflow
				.getDuration()));
		atts.addAttribute("", "", "Cost", "CDATA", ("" + m_Workflow.getCost()));
		atts.addAttribute("", "", "WorkingTime", "CDATA", ("" + m_Workflow
				.getWorkingTime()));
		atts.addAttribute("", "", "WaitingTime", "CDATA", ("" + m_Workflow
				.getWaitingTime()));
		atts.addAttribute("", "", "PublishStatus", "CDATA", (m_Workflow
				.getPublishStatus() != null ? m_Workflow.getPublishStatus()
				: ""));
		atts
				.addAttribute("", "", "WorkflowType", "CDATA", (m_Workflow
						.getWorkflowType() != null ? m_Workflow
						.getWorkflowType() : ""));
		atts.addAttribute("", "", "DocValueLogic", "CDATA", (m_Workflow
				.getDocValueLogic() != null ? m_Workflow.getDocValueLogic()
				: ""));
		atts.addAttribute("", "", "isValid", "CDATA",
				(m_Workflow.isValid() == true ? "true" : "false"));
		// Doesn't appear to be necessary
		// atts.addAttribute("","","SetupTime","CDATA",(""+m_Workflow.getSetupTime()
		// != null ? ""+m_Workflow.getSetupTime():""));
		return atts;
	}
}
