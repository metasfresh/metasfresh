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
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.wf.MWFNextCondition;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class WorkflowNodeNextConditionElementHandler extends
		AbstractElementHandler {

	public void startElement(Properties ctx, Element element)
			throws SAXException {
		Attributes atts = element.attributes;
		String entitytype = atts.getValue("EntityType");
		log.info("entitytype " + atts.getValue("EntityType"));

		if (isProcessElement(ctx, entitytype)) {
			if (element.parent != null && element.parent.skip) {
				element.skip = true;
				return;
			}
			
			String workflowName = atts.getValue("ADWorkflowNameID");

			int workflowId = get_IDWithColumn(ctx, "AD_Workflow", "name",
					workflowName);
			if (workflowId <= 0) {
				element.defer = true;
				element.unresolved = "AD_Workflow: " + workflowName;
				return;
			}

			String workflowNodeName = atts.getValue("ADWorkflowNodeNameID");
			String workflowNodeNextName = atts
					.getValue("ADWorkflowNodeNextNameID");

			StringBuffer sqlB = new StringBuffer(
					"SELECT ad_wf_node_id FROM AD_WF_Node WHERE AD_Workflow_ID=? and Name =?");

			int wfNodeId = DB.getSQLValue(getTrxName(ctx), sqlB.toString(),
					workflowId, workflowNodeName);
			if (wfNodeId <= 0) {
				element.unresolved = "AD_WF_Node=" + workflowNodeName;
				element.defer = true;
				return;
			}
			
			int wfNodeNextId = DB.getSQLValue(getTrxName(ctx), sqlB.toString(),
					workflowId, workflowNodeNextName);
			if (wfNodeNextId <= 0) {
				element.unresolved = "AD_WF_Node=" + workflowNodeNextName;
				element.defer = true;
				return;
			}

			sqlB = new StringBuffer(
					"SELECT  ad_wf_nodenext_id FROM AD_WF_NodeNext  WHERE ad_wf_node_id =? and ad_wf_next_id =?");
			int wfNodeNextTablePKId = DB.getSQLValue(getTrxName(ctx), sqlB
					.toString(), wfNodeId, wfNodeNextId);

			sqlB = new StringBuffer(
					"SELECT  ad_wf_nextcondition_id FROM AD_WF_NextCondition  WHERE ad_wf_nodenext_id =?");
			int id = DB.getSQLValue(getTrxName(ctx), sqlB.toString(),
					wfNodeNextTablePKId);

			MWFNextCondition m_WFNodeNextCondition = new MWFNextCondition(ctx,
					id, getTrxName(ctx));
			int AD_Backup_ID = -1;
			String Object_Status = null;
			if (id <= 0 && atts.getValue("AD_WF_NextCondition_ID") != null && Integer.parseInt(atts.getValue("AD_WF_NextCondition_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_WFNodeNextCondition.setAD_WF_NextCondition_ID(Integer.parseInt(atts.getValue("AD_WF_NextCondition_ID")));
			if (id > 0) {
				AD_Backup_ID = copyRecord(ctx, "AD_WF_NextCondition",
						m_WFNodeNextCondition);
				Object_Status = "Update";
			} else {
				Object_Status = "New";
				AD_Backup_ID = 0;
			}

			sqlB = new StringBuffer(
					"SELECT  AD_Column.ad_column_id FROM AD_Column, AD_Table WHERE AD_Column.ad_table_id = AD_Table.ad_table_id and AD_Table.name = '"
							+ atts.getValue("ADTableNameID")
							+ "' and AD_Column.name = ?");
			// int columnId =
			// DB.getSQLValue(m_trxName,sqlB.toString(),atts.getValue("ADTableNameID"),
			// atts.getValue("ADColumnNameID"));
			int columnId = DB.getSQLValue(getTrxName(ctx), sqlB.toString(),
					atts.getValue("ADColumnNameID"));
			m_WFNodeNextCondition.setAD_Column_ID(columnId);

			m_WFNodeNextCondition.setAD_WF_NodeNext_ID(wfNodeNextTablePKId);
			m_WFNodeNextCondition
					.setIsActive(atts.getValue("isActive") != null ? Boolean
							.valueOf(atts.getValue("isActive")).booleanValue()
							: true);
			m_WFNodeNextCondition.setAD_WF_NodeNext_ID(wfNodeNextTablePKId);
			m_WFNodeNextCondition.setSeqNo(Integer.valueOf(atts
					.getValue("SeqNo")));
			m_WFNodeNextCondition.setEntityType(atts.getValue("EntityType"));
			m_WFNodeNextCondition.setAndOr(atts.getValue("AndOr"));
			m_WFNodeNextCondition.setOperation(atts.getValue("Operation"));
			m_WFNodeNextCondition.setValue(atts.getValue("Value"));
			m_WFNodeNextCondition.setValue2(atts.getValue("Value2"));
			
			log.info("about to execute m_WFNodeNextCondition.save");
			if (m_WFNodeNextCondition.save(getTrxName(ctx)) == true) {
				log.info("m_WFNodeNextCondition save success");
				record_log(
						ctx,
						1,
						String.valueOf(m_WFNodeNextCondition.get_ID()),
						"WFNextCondition",
						m_WFNodeNextCondition.get_ID(),
						AD_Backup_ID,
						Object_Status,
						"AD_WF_NextCondition",
						get_IDWithColumn(ctx, "AD_Table",
								"TableName", "AD_WF_NextCondition"));
			} else {
				log.info("m_WFNodeNextCondition save failure");
				record_log(
						ctx,
						0,
						String.valueOf(m_WFNodeNextCondition.get_ID()),
						"WFNextCondition",
						m_WFNodeNextCondition.get_ID(),
						AD_Backup_ID,
						Object_Status,
						"AD_WF_NextCondition",
						get_IDWithColumn(ctx, "AD_Table",
								"TableName", "AD_WF_NextCondition"));
				throw new POSaveFailedException("WorkflowNodeNextCondition");
			}
		} else {
			element.skip = true;
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int ad_wf_nodenextcondition_id = Env.getContextAsInt(ctx,
				X_AD_WF_NextCondition.COLUMNNAME_AD_WF_NextCondition_ID);
		X_AD_WF_NextCondition m_WF_NodeNextCondition = new X_AD_WF_NextCondition(
				ctx, ad_wf_nodenextcondition_id, null);
		AttributesImpl atts = new AttributesImpl();
		createWorkflowNodeNextConditionBinding(atts, m_WF_NodeNextCondition);
		document.startElement("", "", "workflowNodeNextCondition", atts);
		document.endElement("", "", "workflowNodeNextCondition");
	}

	private AttributesImpl createWorkflowNodeNextConditionBinding(
			AttributesImpl atts, X_AD_WF_NextCondition m_WF_NodeNextCondition) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_WF_NodeNextCondition.getAD_WF_NextCondition_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_WF_NextCondition_ID","CDATA",Integer.toString(m_WF_NodeNextCondition.getAD_WF_NextCondition_ID()));

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
