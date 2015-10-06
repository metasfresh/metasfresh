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

import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.X_AD_Element;
import org.compiere.model.X_AD_Process_Para;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ProcessParaElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element)
			throws SAXException {
		String elementValue = element.getElementValue();
		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("Name"));

		String entitytype = atts.getValue("EntityType");
		if (isProcessElement(ctx, entitytype)) {
			if (element.parent != null && element.parent.getElementValue().equals("process") &&
				element.parent.defer) {
				element.defer = true;
				return;
			}
			
			String name = atts.getValue("Name");

			int id = 0;
			int masterId = 0;
			String processValue = "";
			if (element.parent != null && element.parent.getElementValue().equals("process") &&
				element.parent.recordId > 0) {
				masterId = element.parent.recordId;
			} else {
				processValue =  atts.getValue("ADProcessValueID");
				if (processValue != null && processValue.trim().length() > 0) {
					masterId = get_IDWithColumn(ctx, "AD_Process", "Value", processValue);					
				} else {
					//for backward compatibility
					processValue = atts.getValue("ADProcessNameID");
					masterId = get_IDWithColumn(ctx, "AD_Process", "Name", processValue);
				}				
			}
			if (masterId <= 0) {
				element.defer = true;
				element.unresolved = "AD_Process: " + processValue;
				return;
			}
			id = get_IDWithMasterAndColumn(ctx, "AD_Process_Para", "Name", 
					name, "AD_Process", masterId);
			
			X_AD_Process_Para m_Process_para = new X_AD_Process_Para(ctx, id,
					getTrxName(ctx));
			int AD_Backup_ID = -1;
			String Object_Status = null;
			if (id <= 0 && atts.getValue("AD_Process_Para_ID") != null && Integer.parseInt(atts.getValue("AD_Process_Para_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_Process_para.setAD_Process_Para_ID(Integer.parseInt(atts.getValue("AD_Process_Para_ID")));
			if (id > 0) {
				AD_Backup_ID = copyRecord(ctx, "AD_Process_Para",
						m_Process_para);
				Object_Status = "Update";
			} else {
				Object_Status = "New";
				AD_Backup_ID = 0;
			}
			m_Process_para.setName(atts.getValue("Name"));
			
			m_Process_para.setAD_Process_ID(masterId);
			
			m_Process_para.setColumnName(atts.getValue("ColumnName"));
			m_Process_para.setEntityType(atts.getValue("EntityType"));
			
			name = atts.getValue("ADElementNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_Element", "ColumnName", name);
				if (id <= 0 /** Check Packout version - 005 */)
				{
					id = get_IDWithColumn(ctx, "AD_Element", "Name", name);
				}
				// Setup Element
				X_AD_Element adElement = new X_AD_Element(ctx, id, getTrxName(ctx));
				if (adElement.getAD_Element_ID() == 0) {
					String columnName = m_Process_para.getColumnName();
					id = get_IDWithColumn(ctx, "AD_Element", "ColumnName", columnName);
					if ( id > 0 ) {
						adElement = new X_AD_Element(ctx, id, getTrxName(ctx));
					} else {
						adElement.setColumnName(columnName);
						adElement.setEntityType(m_Process_para.getEntityType());
						adElement.setPrintName(name);
	
						adElement.setName(m_Process_para.getName());
						if (adElement.save(getTrxName(ctx)) == true) {
							record_log(ctx, 1, m_Process_para.getName(), "Element", adElement
									.getAD_Element_ID(), AD_Backup_ID, "New",
									"AD_Element", get_IDWithColumn(ctx, "AD_Table",
											"TableName", "AD_Element"));
						} else {
							record_log(ctx, 0, m_Process_para.getName(), "Element", adElement
									.getAD_Element_ID(), AD_Backup_ID, "New",
									"AD_Element", get_IDWithColumn(ctx, "AD_Table",
											"TableName", "AD_Element"));
						}
					}
				}
				m_Process_para.setAD_Element_ID(adElement.getAD_Element_ID());
			}

			name = atts.getValue("ADReferenceNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_Reference", "Name", name);
				if (id <= 0) {
					element.defer = true;
					element.unresolved = "AD_Reference: " + name;
					return;
				}
				m_Process_para.setAD_Reference_ID(id);
			}

			name = atts.getValue("ADReferenceValueNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_Reference", "Name", name);
				if (id <= 0) {
					element.defer = true;
					element.unresolved = "AD_Reference: " + name; 
					return;
				}
				m_Process_para.setAD_Reference_Value_ID(id);
			}

			name = atts.getValue("ADValRuleNameID");
			if (name != null && name.trim().length() > 0) {
				id = get_IDWithColumn(ctx, "AD_Val_Rule", "Name", name);
				if (id <= 0) {
					element.defer = true;
					element.unresolved = "AD_Val_Rule: " +name;
					return;
				}
				m_Process_para.setAD_Val_Rule_ID(id);
			}
			
			m_Process_para.setDefaultValue(getStringValue(atts, "DefaultValue"));
			m_Process_para.setDefaultValue2(getStringValue(atts, "DefaultValue2"));
			m_Process_para.setDescription(getStringValue(atts,"Description"));
			
			m_Process_para.setHelp(getStringValue(atts,"Help"));
			m_Process_para
					.setIsActive(atts.getValue("isActive") != null ? Boolean
							.valueOf(atts.getValue("isActive")).booleanValue()
							: true);
			
			m_Process_para.setVFormat(getStringValue(atts,"VFormat"));
			m_Process_para.setValueMax(getStringValue(atts,"ValueMax"));
			m_Process_para.setValueMin(getStringValue(atts,"ValueMin"));
			m_Process_para.setSeqNo(Integer.parseInt(atts.getValue("SeqNo")));
			m_Process_para.setFieldLength(Integer.parseInt(atts
					.getValue("FieldLength")));
			m_Process_para.setIsCentrallyMaintained(Boolean.valueOf(
					atts.getValue("isCentrallyMaintained")).booleanValue());
			m_Process_para.setIsMandatory(Boolean.valueOf(
					atts.getValue("isMandatory")).booleanValue());
			m_Process_para.setIsRange(Boolean.valueOf(atts.getValue("isRange"))
					.booleanValue());
			if (m_Process_para.save(getTrxName(ctx)) == true) {
				record_log(ctx, 1, m_Process_para.getName(), "Process_para",
						m_Process_para.get_ID(), AD_Backup_ID, Object_Status,
						"AD_Process_para", get_IDWithColumn(ctx, "AD_Table",
								"TableName", "AD_Process_para"));
			} else {
				record_log(ctx, 0, m_Process_para.getName(), "Process_para",
						m_Process_para.get_ID(), AD_Backup_ID, Object_Status,
						"AD_Process_para", get_IDWithColumn(ctx, "AD_Table",
								"TableName", "AD_Process_para"));
				throw new POSaveFailedException("ProcessPara");
			}
		} else {
			element.skip = true;
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_Process_Para_ID = Env.getContextAsInt(ctx,
				X_AD_Process_Para.COLUMNNAME_AD_Process_Para_ID);
		X_AD_Process_Para m_Processpara = new X_AD_Process_Para(ctx,
				AD_Process_Para_ID, getTrxName(ctx));
		AttributesImpl atts = new AttributesImpl();
		createProcessParaBinding(atts, m_Processpara);
		document.startElement("", "", "processpara", atts);
		document.endElement("", "", "processpara");
	}

	private AttributesImpl createProcessParaBinding(AttributesImpl atts,
			X_AD_Process_Para m_Processpara) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_Processpara.getAD_Process_Para_ID() <= PackOut.MAX_OFFICIAL_ID)
	        atts.addAttribute("","","AD_Process_Para_ID","CDATA",Integer.toString(m_Processpara.getAD_Process_Para_ID()));
		atts
				.addAttribute("", "", "Name", "CDATA",
						(m_Processpara.getName() != null ? m_Processpara
								.getName() : ""));
		if (m_Processpara.getAD_Process_ID() > 0) {
			sql = "SELECT Value FROM AD_Process WHERE AD_Process_ID=?";
			name = DB.getSQLValueString(null, sql, m_Processpara
					.getAD_Process_ID());
			atts.addAttribute("", "", "ADProcessValueID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADProcessValueID", "CDATA", "");
		if (m_Processpara.getAD_Process_Para_ID() > 0) {
			sql = "SELECT Name FROM AD_Process_Para WHERE AD_Process_Para_ID=?";
			name = DB.getSQLValueString(null, sql, m_Processpara
					.getAD_Process_Para_ID());
			atts.addAttribute("", "", "ADProcessParaNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADProcessParaNameID", "CDATA", "");
		if (m_Processpara.getAD_Element_ID() > 0) {
			sql = "SELECT ColumnName FROM AD_Element WHERE AD_Element_ID=?";
			name = DB.getSQLValueStringEx(null, sql, m_Processpara.getAD_Element_ID());
			atts.addAttribute("", "", "ADElementNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADElementNameID", "CDATA", "");
		if (m_Processpara.getAD_Reference_ID() > 0) {
			sql = "SELECT Name FROM AD_Reference WHERE AD_Reference_ID=?";
			name = DB.getSQLValueString(null, sql, m_Processpara
					.getAD_Reference_ID());
			atts.addAttribute("", "", "ADReferenceNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADReferenceNameID", "CDATA", "");
		if (m_Processpara.getAD_Reference_Value_ID() > 0) {
			sql = "SELECT Name FROM AD_Reference WHERE AD_Reference_ID=?";
			name = DB.getSQLValueString(null, sql, m_Processpara
					.getAD_Reference_Value_ID());
			atts.addAttribute("", "", "ADReferenceValueNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADReferenceValueNameID", "CDATA", "");
		if (m_Processpara.getAD_Val_Rule_ID() > 0) {
			sql = "SELECT Name FROM AD_Val_Rule WHERE AD_Val_Rule_ID=?";
			name = DB.getSQLValueString(null, sql, m_Processpara
					.getAD_Val_Rule_ID());
			atts.addAttribute("", "", "ADValRuleNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADValRuleNameID", "CDATA", "");
		atts.addAttribute("", "", "ColumnName", "CDATA", (m_Processpara
				.getColumnName() != null ? m_Processpara.getColumnName() : ""));
		atts.addAttribute("", "", "DefaultValue", "CDATA", (m_Processpara
				.getDefaultValue() != null ? m_Processpara.getDefaultValue()
				: ""));
		atts.addAttribute("", "", "DefaultValue2", "CDATA", (m_Processpara
				.getDefaultValue2() != null ? m_Processpara.getDefaultValue2()
				: ""));
		atts.addAttribute("", "", "Description", "CDATA",
				(m_Processpara.getDescription() != null ? m_Processpara
						.getDescription() : ""));
		atts.addAttribute("", "", "EntityType", "CDATA", (m_Processpara
				.getEntityType() != null ? m_Processpara.getEntityType() : ""));
		atts
				.addAttribute("", "", "Help", "CDATA",
						(m_Processpara.getHelp() != null ? m_Processpara
								.getHelp() : ""));
		atts.addAttribute("", "", "isActive", "CDATA", (m_Processpara
				.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "VFormat", "CDATA", (m_Processpara
				.getVFormat() != null ? m_Processpara.getVFormat() : ""));
		atts.addAttribute("", "", "ValueMax", "CDATA", (m_Processpara
				.getValueMax() != null ? m_Processpara.getValueMax() : ""));
		atts.addAttribute("", "", "ValueMin", "CDATA", (m_Processpara
				.getValueMin() != null ? m_Processpara.getValueMin() : ""));
		atts.addAttribute("", "", "SeqNo", "CDATA",
				(m_Processpara.getSeqNo() > 0 ? "" + m_Processpara.getSeqNo()
						: "0"));
		atts.addAttribute("", "", "FieldLength", "CDATA", (m_Processpara
				.getFieldLength() > 0 ? "" + m_Processpara.getFieldLength()
				: "0"));
		atts.addAttribute("", "", "isCentrallyMaintained", "CDATA",
				(m_Processpara.isCentrallyMaintained() == true ? "true"
						: "false"));
		atts.addAttribute("", "", "isMandatory", "CDATA", (m_Processpara
				.isMandatory() == true ? "true" : "false"));
		atts.addAttribute("", "", "isRange", "CDATA",
				(m_Processpara.isRange() == true ? "true" : "false"));
		return atts;
	}
}
