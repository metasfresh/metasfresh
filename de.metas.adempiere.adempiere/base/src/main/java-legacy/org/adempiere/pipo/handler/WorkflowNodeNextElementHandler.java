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
import org.compiere.model.X_AD_WF_NextCondition;
import org.compiere.model.X_AD_WF_NodeNext;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.wf.MWFNodeNext;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class WorkflowNodeNextElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element) throws SAXException {
		Attributes atts = element.attributes;
		String entitytype = atts.getValue("EntityType");
		log.info("entitytype "+atts.getValue("EntityType"));

		if (isProcessElement(ctx, entitytype)) {
			if (element.parent != null && element.parent.skip) {
				element.skip = true;
				return;
			}

			String workflowName = atts.getValue("ADWorkflowNameID");
			int workflowId = get_IDWithColumn(ctx, "AD_Workflow", "name", workflowName);
			if (workflowId <= 0) {
				element.defer = true;
				element.unresolved = "AD_Workflow: " + workflowName;
				return;
			}

			String workflowNodeName = atts.getValue("ADWorkflowNodeNameID").trim();
			String workflowNodeNextName = atts.getValue("ADWorkflowNodeNextNameID").trim();

			StringBuffer sqlB = new StringBuffer ("SELECT ad_wf_node_id FROM AD_WF_Node WHERE AD_Workflow_ID=? and Name =?");		

			int wfNodeId = DB.getSQLValue(getTrxName(ctx),sqlB.toString(),workflowId,workflowNodeName);
			if (wfNodeId <= 0) {
				element.defer = true;
				element.unresolved = "AD_WF_Node: " + workflowNodeName;
				return;
			}

			int wfNodeNextId = DB.getSQLValue(getTrxName(ctx),sqlB.toString(),workflowId,workflowNodeNextName);
			if (wfNodeNextId <= 0) {
				element.defer = true;
				element.unresolved = "AD_WF_Node: " + workflowNodeNextName;
				return;
			}

			sqlB = new StringBuffer ("SELECT  ad_wf_nodenext_id FROM AD_WF_NodeNext  WHERE ad_wf_node_id =? and ad_wf_next_id =?");		

			int id = DB.getSQLValue(getTrxName(ctx),sqlB.toString(),wfNodeId,wfNodeNextId);

			MWFNodeNext m_WFNodeNext = new MWFNodeNext(ctx, id, getTrxName(ctx));
			int AD_Backup_ID = -1;
			String Object_Status = null;
			if (id <= 0 && atts.getValue("AD_WF_NodeNext_ID") != null && Integer.parseInt(atts.getValue("AD_WF_NodeNext_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_WFNodeNext.setAD_WF_NodeNext_ID(Integer.parseInt(atts.getValue("AD_WF_NodeNext_ID")));
			if (id > 0){		
				AD_Backup_ID = copyRecord(ctx, "AD_WF_NodeNext",m_WFNodeNext);
				Object_Status = "Update";			
			}
			else{
				Object_Status = "New";
				AD_Backup_ID =0;
			}
			m_WFNodeNext.setAD_WF_Node_ID(wfNodeId);
			m_WFNodeNext.setAD_WF_Next_ID(wfNodeNextId);
			m_WFNodeNext.setEntityType(atts.getValue("EntityType"));
			m_WFNodeNext.setSeqNo(Integer.valueOf(atts.getValue("SeqNo")));
			m_WFNodeNext.setIsActive(atts.getValue("isActive") != null ? Boolean.valueOf(atts.getValue("isActive")).booleanValue():true);
			m_WFNodeNext.setIsStdUserWorkflow(atts.getValue("IsStdUserWorkflow") != null ? Boolean.valueOf(atts.getValue("IsStdUserWorkflow")).booleanValue():true);
			log.info("about to execute m_WFNodeNext.save");
			if (m_WFNodeNext.save(getTrxName(ctx)) == true){		    	
				log.info("m_WFNodeNext save success");
				record_log (ctx, 1, String.valueOf(m_WFNodeNext.get_ID()),"WFNodeNext", m_WFNodeNext.get_ID(),AD_Backup_ID, Object_Status,"AD_WF_NodeNext",
							get_IDWithColumn(ctx, "AD_Table", "TableName", "AD_WF_NodeNext"));           		        		
			}
			else{
				log.info("m_WFNodeNext save failure");
				record_log (ctx, 0, String.valueOf(m_WFNodeNext.get_ID()),"WFNodeNext", m_WFNodeNext.get_ID(),AD_Backup_ID, Object_Status,"AD_WF_NodeNext",
							get_IDWithColumn(ctx, "AD_Table", "TableName", "AD_WF_NodeNext"));
				throw new POSaveFailedException("WorkflowNodeNext");
			}            
		} else {
			element.skip = true;
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int ad_wf_nodenext_id = Env.getContextAsInt(ctx, X_AD_WF_NodeNext.COLUMNNAME_AD_WF_NodeNext_ID);
		X_AD_WF_NodeNext m_WF_NodeNext = new X_AD_WF_NodeNext(
				ctx, ad_wf_nodenext_id, null);
		AttributesImpl atts = new AttributesImpl();
		createWorkflowNodeNextBinding(atts, m_WF_NodeNext);
		document.startElement("", "", "workflowNodeNext",
				atts);
		document.endElement("", "", "workflowNodeNext");
		
	}

	private AttributesImpl createWorkflowNodeNextBinding(AttributesImpl atts,
			X_AD_WF_NodeNext m_WF_NodeNext)
	// public AttributesImpl createwf_nodenextBinding( AttributesImpl atts,
	// X_AD_WF_NodeNext m_WF_NodeNext, X_AD_WF_Node m_WF_Node)
	{
		String sql = null;
		String name = null;
		atts.clear();
		if (m_WF_NodeNext.getAD_WF_NodeNext_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_WF_NodeNext_ID","CDATA",Integer.toString(m_WF_NodeNext.getAD_WF_NodeNext_ID()));
		// log.log(Level.INFO,"m_WF_NodeNext.getAD_WF_Node_ID: ",
		// m_WF_NodeNext.getAD_WF_Node_ID());
		// log.log(Level.INFO,"m_WF_NodeNext.getAD_WF_Next_ID: ",
		// m_WF_NodeNext.getAD_WF_Next_ID());

		if (m_WF_NodeNext.getAD_WF_Node_ID() > 0) {
			sql = "SELECT AD_Workflow.Name FROM AD_Workflow, AD_WF_Node WHERE  AD_Workflow.AD_Workflow_ID = AD_WF_Node.AD_Workflow_ID and AD_WF_Node.AD_WF_Node_ID =?";
			name = DB.getSQLValueString(null, sql, m_WF_NodeNext
					.getAD_WF_Node_ID());
			atts.addAttribute("", "", "ADWorkflowNameID", "CDATA", name);
			sql = "SELECT Name FROM AD_WF_Node WHERE AD_WF_Node_ID=?";
			name = DB.getSQLValueString(null, sql, m_WF_NodeNext
					.getAD_WF_Node_ID());
			atts.addAttribute("", "", "ADWorkflowNodeNameID", "CDATA", name);
		}

		if (m_WF_NodeNext.getAD_WF_Next_ID() > 0) {
			sql = "SELECT Name FROM AD_WF_Node WHERE AD_WF_Node_ID=?";
			name = DB.getSQLValueString(null, sql, m_WF_NodeNext
					.getAD_WF_Next_ID());
			// log.log(Level.INFO,"node next name: ", name);
			atts
					.addAttribute("", "", "ADWorkflowNodeNextNameID", "CDATA",
							name);
		}

		// FIXME: don't know if I need org_id or not
		// sql = "SELECT Name FROM AD_Org WHERE AD_Org_ID=?";
		// name = DB.getSQLValueString(null,sql,org_id);
		// atts.addAttribute("","","orgname","CDATA",name);

		atts.addAttribute("", "", "isActive", "CDATA", (m_WF_NodeNext
				.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "EntityType", "CDATA", (m_WF_NodeNext
				.getEntityType() != null ? m_WF_NodeNext.getEntityType() : ""));
		atts.addAttribute("", "", "Description", "CDATA",
				(m_WF_NodeNext.getDescription() != null ? m_WF_NodeNext
						.getDescription() : ""));
		atts.addAttribute("", "", "SeqNo", "CDATA", (String
				.valueOf(m_WF_NodeNext.getSeqNo()) != null ? String
				.valueOf(m_WF_NodeNext.getSeqNo()) : ""));
		atts.addAttribute("", "", "IsStdUserWorkflow", "CDATA", (String
				.valueOf(m_WF_NodeNext.isStdUserWorkflow()) != null ? String
				.valueOf(m_WF_NodeNext.isStdUserWorkflow()) : ""));

		return atts;
	}
	
	private AttributesImpl createWorkflowNodeNextConditionBinding(
			AttributesImpl atts, X_AD_WF_NextCondition m_WF_NodeNextCondition) {
		String sql = null;
		String name = null;
		atts.clear();

		if (m_WF_NodeNextCondition.getAD_WF_NodeNext_ID() > 0) {
			// FIXME: it appears nodes point back to themselves
			// so a group by is necessary
			sql = "SELECT AD_Workflow.Name FROM AD_Workflow, AD_WF_Node, AD_WF_NodeNext WHERE  AD_Workflow.AD_Workflow_ID = AD_WF_Node.AD_Workflow_ID and AD_WF_Node.AD_WF_Node_ID = AD_WF_NodeNext.AD_WF_Node_ID and AD_WF_NodeNext.AD_WF_NodeNext_ID = ? group by AD_Workflow.Name";
			name = DB.getSQLValueString(null, sql, m_WF_NodeNextCondition
					.getAD_WF_NodeNext_ID());
			atts.addAttribute("", "", "ADWorkflowNameID", "CDATA", name);
			// FIXME: it appears nodes point back to themselves
			// so a group by is necessary
			sql = "SELECT AD_WF_Node.Name FROM AD_WF_Node, AD_WF_NodeNext WHERE AD_WF_Node.AD_WF_Node_ID = AD_WF_NodeNext.AD_WF_Node_ID and AD_WF_NodeNext.AD_WF_NodeNext_ID =  ? group by AD_WF_Node.Name";
			name = DB.getSQLValueString(null, sql, m_WF_NodeNextCondition
					.getAD_WF_NodeNext_ID());
			atts.addAttribute("", "", "ADWorkflowNodeNameID", "CDATA", name);
			// FIXME: it appears nodes point back to themselves
			// so a group by is necessary
			sql = "SELECT AD_WF_Node.Name FROM AD_WF_Node, AD_WF_NodeNext, AD_WF_NextCondition WHERE AD_WF_Node.AD_WF_Node_ID = AD_WF_NodeNext.AD_WF_Next_ID and AD_WF_NodeNext.AD_WF_NodeNext_ID =  ? group by AD_WF_Node.Name";
			name = DB.getSQLValueString(null, sql, m_WF_NodeNextCondition
					.getAD_WF_NodeNext_ID());
			// log.log(Level.INFO,"node next name: ", name);
			atts
					.addAttribute("", "", "ADWorkflowNodeNextNameID", "CDATA",
							name);
		}

		if (m_WF_NodeNextCondition.getAD_Column_ID() > 0) {

			sql = "SELECT AD_Table.TableName FROM AD_Table, AD_Column, AD_WF_NextCondition  WHERE AD_Column.AD_Table_ID=AD_Table.AD_Table_ID and AD_Column.AD_Column_ID = ?";
			name = DB.getSQLValueString(null, sql, m_WF_NodeNextCondition
					.getAD_Column_ID());
			atts.addAttribute("", "", "ADTableNameID", "CDATA", name);

			sql = "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?";
			name = DB.getSQLValueString(null, sql, m_WF_NodeNextCondition
					.getAD_Column_ID());
			atts.addAttribute("", "", "ADColumnNameID", "CDATA", name);
		} else {
			atts.addAttribute("", "", "ADTableNameID", "CDATA", name);
			atts.addAttribute("", "", "ADColumnNameID", "CDATA", "");
		}

		// FIXME: don't know if I need org_id or not
		// sql = "SELECT Name FROM AD_Org WHERE AD_Org_ID=?";
		// name = DB.getSQLValueString(null,sql,org_id);
		// atts.addAttribute("","","orgname","CDATA",name);

		atts.addAttribute("", "", "isActive", "CDATA", (m_WF_NodeNextCondition
				.isActive() == true ? "true" : "false"));
		atts
				.addAttribute(
						"",
						"",
						"EntityType",
						"CDATA",
						(m_WF_NodeNextCondition.getEntityType() != null ? m_WF_NodeNextCondition
								.getEntityType()
								: ""));
		atts.addAttribute("", "", "AndOr", "CDATA", (m_WF_NodeNextCondition
				.getAndOr() != null ? m_WF_NodeNextCondition.getAndOr() : ""));
		atts.addAttribute("", "", "Operation", "CDATA", (m_WF_NodeNextCondition
				.getOperation() != null ? m_WF_NodeNextCondition.getOperation()
				: ""));
		atts.addAttribute("", "", "Value", "CDATA", (m_WF_NodeNextCondition
				.getValue() != null ? m_WF_NodeNextCondition.getValue() : ""));
		atts
				.addAttribute(
						"",
						"",
						"Value2",
						"CDATA",
						(m_WF_NodeNextCondition.getValue2() != null ? m_WF_NodeNextCondition
								.getValue2()
								: ""));
		atts.addAttribute("", "", "SeqNo", "CDATA", (String
				.valueOf(m_WF_NodeNextCondition.getSeqNo()) != null ? String
				.valueOf(m_WF_NodeNextCondition.getSeqNo()) : ""));

		return atts;
	}
}
