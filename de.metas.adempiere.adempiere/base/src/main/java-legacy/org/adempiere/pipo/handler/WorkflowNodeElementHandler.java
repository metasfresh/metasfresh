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
 *
 * Copyright (C) 2005 Robert Klein. robeklein@hotmail.com
 * Contributor(s): Low Heng Sin hengsin@avantz.com
 * Contributor(s): Victor Perez. victor.perez@e-evolution.com [Bugs-1789058 ]
 *****************************************************************************/
package org.adempiere.pipo.handler;

import java.math.BigDecimal;
import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.X_AD_WF_Node;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class WorkflowNodeElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element)
			throws SAXException {
		Attributes atts = element.attributes;
		String elementValue = element.getElementValue();
		log.info(elementValue + " " + atts.getValue("Name"));
		String entitytype = atts.getValue("EntityType");
		log.info("entitytype " + atts.getValue("EntityType"));

		if (isProcessElement(ctx, entitytype)) {
			if (element.parent != null && element.parent.skip) {
				element.skip = true;
				return;
			}
			if (element.parent != null && element.parent.getElementValue().equals("workflow")
				&& element.parent.defer) {
				element.unresolved = "Parent element mark as defer: " + atts.getValue("ADWorkflowNameID");
				element.defer = true;
				return;
			}
			
			int workflowId = 0;
			String workflowName = atts.getValue("ADWorkflowNameID");
			if (element.parent != null && element.parent.getElementValue().equals("workflow")
				&& element.parent.recordId > 0)
				workflowId = element.parent.recordId;
			else {
				workflowId = get_IDWithColumn(ctx, "AD_Workflow", "name",
					workflowName);
				if (workflowId <= 0) {
					element.defer = true;
					element.unresolved = "AD_Workflow: " + workflowName;
					return;
				}
				else if (element.parent != null && element.parent.getElementValue().equals("workflow"))
					element.parent.recordId = workflowId;
			}

			String workflowNodeName = atts.getValue("Name").trim();

			StringBuffer sqlB = new StringBuffer(
					"SELECT ad_wf_node_id FROM AD_WF_Node WHERE AD_Workflow_ID=? and Name =?");

			int id = DB.getSQLValue(getTrxName(ctx), sqlB.toString(),
					workflowId, workflowNodeName);

			X_AD_WF_Node m_WFNode = new X_AD_WF_Node(ctx, id, getTrxName(ctx));
			int AD_Backup_ID = -1;
			String Object_Status = null;
			if (id <= 0 && atts.getValue("AD_WF_Node_ID") != null && Integer.parseInt(atts.getValue("AD_WF_Node_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_WFNode.setAD_WF_Node_ID(Integer.parseInt(atts.getValue("AD_WF_Node_ID")));
			if (id > 0) {
				AD_Backup_ID = copyRecord(ctx, "AD_WF_Node", m_WFNode);
				Object_Status = "Update";
			} else {
				Object_Status = "New";
				AD_Backup_ID = 0;
			}
			m_WFNode.setValue(atts.getValue("Value"));
			m_WFNode.setName(workflowNodeName);
			m_WFNode.setAD_Workflow_ID(workflowId);

			String name = atts.getValue("ADProcessNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_Process", "Name", name);
				if (id <= 0) {
					element.defer = true;
					element.unresolved = "AD_Process: " + name;
					return;
				}
				m_WFNode.setAD_Process_ID(id);
			}

			name = atts.getValue("ADFormNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_Form", "Name", name);
				if (id <= 0) {
					element.defer = true;
					element.unresolved = "AD_Form: " + name;
					return;
				}
				m_WFNode.setAD_Form_ID(id);
			}

			name = atts.getValue("ADWorkflowResponsibleNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_WF_Responsible", "Name", name);
				//TODO: export and import of ad_wf_responsible
				/*
				if (id <= 0) {
					element.defer = true;
					return;
				}*/
				if (id > 0)
					m_WFNode.setAD_WF_Responsible_ID(id);
			}

			name = atts.getValue("ADWindowNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_Window", "Name", name);
				if (id <= 0) {
					element.defer = true;
					element.unresolved = "AD_Window: " + name;
					return;
				}
				m_WFNode.setAD_Window_ID(id);
			}

			name = atts.getValue("ADImageNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_Image", "Name", name);
				//TODO: export and import of ad_image
				/*
				if (id <= 0) {
					element.defer = true;
					return;
				}*/
				if (id > 0)
					m_WFNode.setAD_Image_ID(id);
			}

			name = atts.getValue("ADWorkflowBlockNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_WF_Block", "Name", name);
				//TODO: export and import of ad_workflow_block
				/*
				if (id <= 0) {
					element.defer = true;
					return;
				}*/
				if (id > 0)
					m_WFNode.setAD_WF_Block_ID(id);
			}
			
			//[Bugs-1789058 ]
			/*
			name = atts.getValue("WorkflowNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_Workflow", "Name", name);
				//TODO: export and import of ad_workflow
				
				if (id <= 0) {
					element.defer = true;
					element.unresolved = "Sub Workflow: " + name;
					return;
				}
				if (id > 0)
					m_WFNode.setWorkflow_ID(id);
			}*/
			
			/*
			 * FIXME: Do we need TaskName ? if
			 * (atts.getValue("ADTaskNameID")!=null){ String name =
			 * atts.getValue("ADTaskNameID"); sqlB = new StringBuffer ("SELECT
			 * AD_Task_ID FROM AD_Task WHERE Name= ?"); taskid =
			 * DB.getSQLValue(m_trxName,sqlB.toString(),name); }
			 */
			m_WFNode.setValue(atts.getValue("Value"));
			m_WFNode.setEntityType(atts.getValue("EntityType"));
			m_WFNode.setAction(getStringValue(atts,"Action"));
			m_WFNode.setDocAction(getStringValue(atts, "DocAction"));
			m_WFNode.setDescription(getStringValue(atts,"Description"));
			m_WFNode.setJoinElement(atts.getValue("JoinElement"));
			m_WFNode.setSplitElement(atts.getValue("SplitElement"));
			if (getStringValue(atts, "XPosition") != null)
				m_WFNode.setXPosition(Integer.valueOf(atts.getValue("XPosition")));
			if (getStringValue(atts, "YPosition") != null) 
				m_WFNode.setYPosition(Integer.valueOf(atts.getValue("YPosition")));
			m_WFNode.setWaitingTime(Integer.valueOf(atts
					.getValue("WaitingTime")));
			if (getStringValue(atts, "WaitTime") != null)
				m_WFNode.setWaitTime(Integer.valueOf(atts.getValue("WaitTime")));
			m_WFNode.setWorkingTime(Integer.valueOf(atts
					.getValue("WorkingTime")));
			if (getStringValue(atts, "Cost") != null)
				m_WFNode.setCost(new BigDecimal(atts.getValue("Cost")));
			if (getStringValue(atts, "Duration") != null)
				m_WFNode.setDuration(Integer.valueOf(atts.getValue("Duration")));
			if (getStringValue(atts, "Priority") != null)
				m_WFNode.setPriority(Integer.valueOf(atts.getValue("Priority")));
			m_WFNode.setStartMode(getStringValue(atts, "StartMode"));
			m_WFNode.setSubflowExecution(getStringValue(atts,"SubflowExecution"));
			m_WFNode.setIsCentrallyMaintained(Boolean.valueOf(
					atts.getValue("IsCentrallyMaintained")).booleanValue());
			if (getStringValue(atts,"DynPriorityChange") != null)
				m_WFNode.setDynPriorityChange(new BigDecimal(atts
					.getValue("DynPriorityChange")));
			// m_WFNode.setAccessLevel (atts.getValue("AccessLevel"));
			m_WFNode.setDynPriorityUnit (getStringValue(atts,"DynPriorityUnit"));
			m_WFNode.setIsActive(atts.getValue("isActive") != null ? Boolean
					.valueOf(atts.getValue("isActive")).booleanValue() : true);
			m_WFNode.setValue(atts.getValue("Value"));
			log.info("about to execute m_WFNode.save");
			if (m_WFNode.save(getTrxName(ctx)) == true) {
				log.info("m_WFNode save success");
				record_log(ctx, 1, m_WFNode.getName(), "WFNode", m_WFNode
						.get_ID(), AD_Backup_ID, Object_Status, "AD_WF_Node",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_WF_Node"));
			} else {
				log.info("m_WFNode save failure");
				record_log(ctx, 0, m_WFNode.getName(), "WFNode", m_WFNode
						.get_ID(), AD_Backup_ID, Object_Status, "AD_WF_Node",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_WF_Node"));
				throw new POSaveFailedException("WorkflowNode");
			}
		} else {
			element.skip = true;
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_WF_Node_ID = Env.getContextAsInt(ctx,
				X_AD_WF_Node.COLUMNNAME_AD_WF_Node_ID);
		AttributesImpl atts = new AttributesImpl();
		X_AD_WF_Node m_WF_Node = new X_AD_WF_Node(ctx, AD_WF_Node_ID,
				getTrxName(ctx));

		createWorkflowNodeBinding(atts, m_WF_Node);
		document.startElement("", "", "workflowNode", atts);
		document.endElement("", "", "workflowNode");
	}

	private AttributesImpl createWorkflowNodeBinding(AttributesImpl atts,
			X_AD_WF_Node m_WF_Node) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_WF_Node.getAD_WF_Node_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_WF_Node_ID","CDATA",Integer.toString(m_WF_Node.getAD_WF_Node_ID()));
		atts.addAttribute("", "", "Value", "CDATA", (m_WF_Node.getValue() != null ? m_WF_Node.getValue() : ""));
		atts.addAttribute("", "", "Name", "CDATA",
				(m_WF_Node.getName() != null ? m_WF_Node.getName() : ""));

		if (m_WF_Node.getAD_Workflow_ID() > 0) {
			sql = "SELECT Name FROM AD_Workflow WHERE AD_Workflow_ID=?";
			name = DB.getSQLValueString(null, sql, m_WF_Node
					.getAD_Workflow_ID());
			atts.addAttribute("", "", "ADWorkflowNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADWorkflowNameID", "CDATA", "");

		if (m_WF_Node.getAD_Window_ID() > 0) {
			sql = "SELECT Name FROM AD_Window WHERE AD_Window_ID=?";
			name = DB.getSQLValueString(null, sql, m_WF_Node.getAD_Window_ID());
			if (name != null)
				atts.addAttribute("", "", "ADWindowNameID", "CDATA", name);
			else
				atts.addAttribute("", "", "ADWindowNameID", "CDATA", "");
		} else {
			atts.addAttribute("", "", "ADWindowNameID", "CDATA", "");
		}
		

		if (m_WF_Node.getAD_Task_ID() > 0) {
			sql = "SELECT Name FROM AD_Task WHERE AD_Task_ID=?";
			name = DB.getSQLValueString(null, sql, m_WF_Node.getAD_Task_ID());
			if (name != null)
				atts.addAttribute("", "", "ADTaskNameID", "CDATA", name);
			else
				atts.addAttribute("", "", "ADTaskNameID", "CDATA", "");
		} else {
			atts.addAttribute("", "", "ADTaskNameID", "CDATA", "");
		}
		

		if (m_WF_Node.getAD_Process_ID() > 0) {
			sql = "SELECT Name FROM AD_Process WHERE AD_Process_ID=?";
			name = DB
					.getSQLValueString(null, sql, m_WF_Node.getAD_Process_ID());
			atts.addAttribute("", "", "ADProcessNameID", "CDATA", 
				(name != null ? name : ""));
		} else
			atts.addAttribute("", "", "ADProcessNameID", "CDATA", "");
		
		if (m_WF_Node.getAD_Form_ID() > 0) {
			sql = "SELECT Name FROM AD_Form WHERE AD_Form_ID=?";
			name = DB.getSQLValueString(null, sql, m_WF_Node.getAD_Form_ID());
			atts.addAttribute("", "", "ADFormNameID", "CDATA", 
				(name != null ? name : ""));
		} else
			atts.addAttribute("", "", "ADFormNameID", "CDATA", "");
		
		if (m_WF_Node.getAD_WF_Block_ID() > 0) {
			sql = "SELECT Name FROM AD_WF_Block WHERE AD_WF_Block_ID=?";
			name = DB.getSQLValueString(null, sql, m_WF_Node
					.getAD_WF_Block_ID());
			atts.addAttribute("", "", "ADWorkflowBlockNameID", "CDATA", 
				(name != null ? name : ""));
		} else
			atts.addAttribute("", "", "ADWorkflowBlockNameID", "CDATA", "");

		if (m_WF_Node.getAD_WF_Responsible_ID() > 0) {
			sql = "SELECT Name FROM AD_WF_Responsible WHERE AD_WF_Responsible_ID=?";
			name = DB.getSQLValueString(null, sql, m_WF_Node
					.getAD_WF_Responsible_ID());
			atts.addAttribute("", "", "ADWorkflowResponsibleNameID", "CDATA",
					(name != null ? name : ""));
		} else
			atts.addAttribute("", "", "ADWorkflowResponsibleNameID", "CDATA",
					"");

		if (m_WF_Node.getAD_Image_ID() > 0) {
			sql = "SELECT Name FROM AD_Image WHERE AD_Image_ID=?";
			name = DB.getSQLValueString(null, sql, m_WF_Node.getAD_Image_ID());
			if (name != null)
				atts.addAttribute("", "", "ADImageNameID", "CDATA", name);
			else
				atts.addAttribute("", "", "ADImageNameID", "CDATA", "");
		} else {
			atts.addAttribute("", "", "ADImageNameID", "CDATA", "");
		}
		
		//[Bugs-1789058 ]
		if (m_WF_Node.getWorkflow_ID() > 0) {
			sql = "SELECT Name FROM AD_Workflow WHERE AD_Workflow_ID=?";
			name = DB.getSQLValueString(null, sql, m_WF_Node.getWorkflow_ID());
			if (name != null)
				atts.addAttribute("", "", "WorkflowNameID", "CDATA", name);
			else
				atts.addAttribute("", "", "WorkflowNameID", "CDATA", "");
		} else {
			atts.addAttribute("", "", "WorkflowNameID", "CDATA", "");
		}
		
		
		if (m_WF_Node.getAD_Column_ID() > 0) {
			sql = "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?";
			name = DB.getSQLValueString(null, sql, m_WF_Node.getAD_Column_ID());
			atts.addAttribute("", "", "ADColumnNameID", "CDATA", 
				(name != null ? name : ""));
		} else
			atts.addAttribute("", "", "ADColumnNameID", "CDATA", "");
		atts.addAttribute("", "", "Value", "CDATA", (m_WF_Node
				.getValue() != null ? m_WF_Node.getValue() : ""));
		
		atts.addAttribute("", "", "Value", "CDATA", (m_WF_Node
				.getValue() != null ? m_WF_Node.getValue() : ""));
		
		atts.addAttribute("", "", "isActive", "CDATA",
				(m_WF_Node.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "Description", "CDATA", (m_WF_Node
				.getDescription() != null ? m_WF_Node.getDescription() : ""));
		atts.addAttribute("", "", "Help", "CDATA",
				(m_WF_Node.getHelp() != null ? m_WF_Node.getHelp() : ""));
		atts.addAttribute("", "", "isCentrallyMaintained", "CDATA", (m_WF_Node
				.isCentrallyMaintained() == true ? "true" : "false"));

		atts.addAttribute("", "", "Action", "CDATA",
				(m_WF_Node.getAction() != null ? m_WF_Node.getAction() : ""));
		atts.addAttribute("", "", "EntityType", "CDATA", (m_WF_Node
				.getEntityType() != null ? m_WF_Node.getEntityType() : ""));
		atts.addAttribute("", "", "XPosition", "CDATA", ("" + m_WF_Node
				.getXPosition()));
		atts.addAttribute("", "", "YPosition", "CDATA", ("" + m_WF_Node
				.getYPosition()));
		atts.addAttribute("", "", "SubflowExecution", "CDATA", (m_WF_Node
				.getSubflowExecution() != null ? m_WF_Node
				.getSubflowExecution() : ""));
		atts.addAttribute("", "", "StartMode", "CDATA", (m_WF_Node
				.getStartMode() != null ? m_WF_Node.getStartMode() : ""));
		atts.addAttribute("", "", "Priority", "CDATA", ("" + m_WF_Node
				.getPriority()));
		atts.addAttribute("", "", "Duration", "CDATA", ("" + m_WF_Node
				.getDuration()));
		atts.addAttribute("", "", "Cost", "CDATA", ("" + m_WF_Node.getCost()));
		atts.addAttribute("", "", "WorkingTime", "CDATA", ("" + m_WF_Node
				.getWorkingTime()));
		atts.addAttribute("", "", "WaitingTime", "CDATA", ("" + m_WF_Node
				.getWaitingTime()));
		atts.addAttribute("", "", "JoinElement", "CDATA", (m_WF_Node
				.getJoinElement() != null ? m_WF_Node.getJoinElement() : ""));
		atts.addAttribute("", "", "SplitElement", "CDATA", (m_WF_Node
				.getSplitElement() != null ? m_WF_Node.getSplitElement() : ""));
		atts.addAttribute("", "", "WaitTime", "CDATA", ("" + m_WF_Node
				.getWaitTime()));
		atts.addAttribute("", "", "AttributeName", "CDATA",
				(m_WF_Node.getAttributeName() != null ? m_WF_Node
						.getAttributeName() : ""));
		atts.addAttribute("", "", "AttributeValue", "CDATA", (m_WF_Node
				.getAttributeValue() != null ? m_WF_Node.getAttributeValue()
				: ""));
		atts.addAttribute("", "", "DocAction", "CDATA", (m_WF_Node
				.getDocAction() != null ? m_WF_Node.getDocAction() : ""));
		atts.addAttribute("", "", "DynPriorityUnit", "CDATA", (m_WF_Node
				.getDynPriorityUnit() != null ? m_WF_Node.getDynPriorityUnit()
				: ""));
		atts.addAttribute("", "", "DynPriorityChange", "CDATA", ("" + m_WF_Node
				.getDynPriorityChange()));

		return atts;
	}
}
