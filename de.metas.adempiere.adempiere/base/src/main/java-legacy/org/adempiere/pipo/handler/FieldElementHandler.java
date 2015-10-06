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
 *                 Teo Sarca teo.sarca@gmail.com                              *
 *****************************************************************************/
package org.adempiere.pipo.handler;

import java.math.BigDecimal;
import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackIn;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.MField;
import org.compiere.model.X_AD_Field;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class FieldElementHandler extends AbstractElementHandler
{
	public void startElement(Properties ctx, Element element) throws SAXException
	{
		final String include_tabname = element.attributes.getValue("ADIncludeTabNameID");
		
		// Set Included Tab ID if this task was previously postponed 
		if (element.defer && element.recordId > 0 && include_tabname != null)
		{
			MField field = new MField(ctx, element.recordId, getTrxName(ctx));
			setIncluded_Tab_ID(ctx, field, include_tabname);
			field.saveEx();
			return;
		}
		
		PackIn packIn = (PackIn)ctx.get("PackInProcess");
		String elementValue = element.getElementValue();
		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("Name"));
		String entitytype = atts.getValue("EntityType");
		if (isProcessElement(ctx, entitytype)) {
			if (element.parent != null && element.parent.getElementValue().equals("tab") &&
				element.parent.defer) {
				element.defer = true;
				return;
			}
			String name = atts.getValue("Name");
			String tabname = atts.getValue("ADTabNameID");
			String colname = atts.getValue("ADColumnNameID");
			String tableName = atts.getValue("ADTableNameID");
			int tableid = packIn.getTableId(tableName);
			if (tableid <= 0) {
				tableid = get_IDWithColumn(ctx, "AD_Table", "TableName", tableName);
				if (tableid > 0)
					packIn.addTable(tableName, tableid);
			}
			if (tableid <= 0) {
				element.defer = true;
				return;
			}
			int windowid = get_ID(ctx, "AD_Window", atts
					.getValue("ADWindowNameID"));
			if (windowid <= 0) {
				element.defer = true;
				return;
			}
			int columnid = packIn.getColumnId(tableName, colname);
			if (columnid <= 0) {
				columnid = get_IDWithMasterAndColumn(ctx, "AD_Column",
					"ColumnName", colname, "AD_Table", tableid);
				if (columnid > 0)
					packIn.addColumn(tableName, colname, columnid);
			}
			if (columnid <= 0) {
				element.defer = true;
				return;
			}
			int tabid = 0;
			if (element.parent != null && element.parent.getElementValue().equals("tab") &&
					element.parent.recordId > 0) {
				tabid = element.parent.recordId;
			} else {
				StringBuffer sqlB = new StringBuffer(
						"select AD_Tab_ID from AD_Tab where AD_Window_ID = "
								+ windowid).append(" and Name = '" + tabname + "'")
						.append(" and AD_Table_ID = ?");
				tabid = DB.getSQLValue(getTrxName(ctx), sqlB.toString(),
						tableid);
				if (element.parent != null && element.parent.getElementValue().equals("tab") && tabid > 0) {
					element.parent.recordId = tabid;
				}
			}
			if (tabid > 0) {
				StringBuffer sqlB = new StringBuffer(
						"select AD_Field_ID from AD_Field where AD_Column_ID = ")
						.append(columnid)
						.append(" and AD_Tab_ID = ?");
				int id = DB.getSQLValue(getTrxName(ctx), sqlB.toString(), tabid);
				final MField m_Field = new MField(ctx, id, getTrxName(ctx));
				if (id <= 0 && atts.getValue("AD_Field_ID") != null && Integer.parseInt(atts.getValue("AD_Field_ID")) <= PackOut.MAX_OFFICIAL_ID)
					m_Field.setAD_Field_ID(Integer.parseInt(atts.getValue("AD_Field_ID")));
				int AD_Backup_ID = -1;
				String Object_Status = null;
				if (id > 0) {
					AD_Backup_ID = copyRecord(ctx, "AD_Field", m_Field);
					Object_Status = "Update";
				} else {
					Object_Status = "New";
					AD_Backup_ID = 0;
				}
			
				m_Field.setName(atts.getValue("Name"));
				m_Field.setAD_Column_ID(columnid);
				name = atts.getValue("ADFieldGroupNameID");
				id = get_IDWithColumn(ctx, "AD_FieldGroup", "Name", name);
				m_Field.setAD_FieldGroup_ID(id);
				m_Field.setAD_Tab_ID(tabid);
				m_Field.setEntityType(atts.getValue("EntityType"));
				m_Field.setIsSameLine(Boolean
						.valueOf(atts.getValue("SameLine")).booleanValue());
				m_Field.setIsCentrallyMaintained(Boolean.valueOf(
						atts.getValue("isCentrallyMaintained")).booleanValue());
				m_Field.setIsDisplayed(Boolean.valueOf(
						atts.getValue("Displayed")).booleanValue());
				// m_Field.setIsEncrypted(Boolean.valueOf(atts.getValue("isEncrypted")).booleanValue());
				m_Field.setIsFieldOnly(Boolean.valueOf(
						atts.getValue("isFieldOnly")).booleanValue());
				m_Field.setIsHeading(Boolean
						.valueOf(atts.getValue("isHeading")).booleanValue());
				m_Field.setIsReadOnly(Boolean.valueOf(
						atts.getValue("isReadOnly")).booleanValue());
				m_Field.setSeqNo(Integer.parseInt(atts.getValue("SeqNo")));
				m_Field.setDisplayLength(Integer.parseInt(atts
						.getValue("DisplayLength")));
				m_Field.setDescription(getStringValue(atts, "Description"));
				m_Field.setHelp(getStringValue(atts, "Help"));
				m_Field.setIsActive(atts.getValue("isActive") != null ? Boolean
						.valueOf(atts.getValue("isActive")).booleanValue()
						: true);
				String sortNo = getStringValue(atts, "SortNo");
				if (sortNo != null)
					m_Field.setSortNo(new BigDecimal(sortNo));
				m_Field.setDisplayLogic(getStringValue(atts, "DisplayLogic"));
				
				String Name = atts.getValue("ADReferenceNameID");
				id = get_IDWithColumn(ctx, "AD_Reference", "Name", Name);
				m_Field.setAD_Reference_ID(id);
				
				Name = atts.getValue("ADValRuleNameID");
				id = get_IDWithColumn(ctx, "AD_Val_Rule", "Name", Name);
				m_Field.setAD_Val_Rule_ID(id);
				Name = atts.getValue("ADReferenceNameValueID");
				id = get_IDWithColumn(ctx, "AD_Reference", "Name", Name);
				m_Field.setAD_Reference_Value_ID(id);
				m_Field.setInfoFactoryClass(getStringValue(atts, "InfoFactoryClass"));
				setIncluded_Tab_ID(ctx, m_Field, include_tabname);
				
				if (m_Field.save(getTrxName(ctx)) == true) {
					record_log(ctx, 1, m_Field.getName(), "Field", m_Field
							.get_ID(), AD_Backup_ID, Object_Status, "AD_Field",
							get_IDWithColumn(ctx, "AD_Table", "TableName",
									"AD_Field"));
					element.recordId = m_Field.getAD_Field_ID();
				} else {
					record_log(ctx, 0, m_Field.getName(), "Field", m_Field
							.get_ID(), AD_Backup_ID, Object_Status, "AD_Field",
							get_IDWithColumn(ctx, "AD_Table", "TableName",
									"AD_Field"));
					throw new POSaveFailedException("Failed to save field definition.");
				}
				
				// If Included Tab not found, then postpone this task for later processing 
				if (m_Field.getAD_Field_ID() > 0 && include_tabname != null && m_Field.getIncluded_Tab_ID() <= 0)
				{
					element.defer = true;
				}
			} else {
				element.defer = true;
				return;
			}
		} else {
			element.skip = true;
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_Field_ID = Env.getContextAsInt(ctx,
				X_AD_Field.COLUMNNAME_AD_Field_ID);
		X_AD_Field m_Field = new X_AD_Field(ctx, AD_Field_ID, null);
		AttributesImpl atts = new AttributesImpl();
		createFieldBinding(atts, m_Field);
		
		PackOut packOut = (PackOut)ctx.get("PackOutProcess");
				
		if(m_Field.getAD_FieldGroup_ID() > 0){
			packOut.createFieldGroupElement(m_Field.getAD_FieldGroup_ID(), document);
		}
		
		if(m_Field.getAD_Reference_ID() > 0) {
			packOut.createReference(m_Field.getAD_Reference_ID(), document);
		}
		
		if (m_Field.getAD_Reference_Value_ID() > 0) {
			packOut.createReference(m_Field.getAD_Reference_Value_ID(), document);
		}
		
		if (m_Field.getAD_Val_Rule_ID() > 0) {
			packOut.createDynamicRuleValidation(m_Field.getAD_Val_Rule_ID(), document);
		}
		
		document.startElement("", "", "field", atts);
		document.endElement("", "", "field");
	}

	private AttributesImpl createFieldBinding(AttributesImpl atts,
			X_AD_Field m_Field) {
		String sql = null;
		String name = null;
		atts.clear();
		if (m_Field.getAD_Field_ID() <= PackOut.MAX_OFFICIAL_ID)
			atts.addAttribute("", "", "AD_Field_ID", "CDATA", Integer.toString(m_Field.getAD_Field_ID()));
		if (m_Field.getAD_Column_ID() > 0) {
			sql = "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?";
			name = DB.getSQLValueString(null, sql, m_Field.getAD_Column_ID());
			atts.addAttribute("", "", "ADColumnNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADColumnNameID", "CDATA", "");

		if (m_Field.getAD_Column_ID() > 0) {
			sql = "SELECT AD_Table_ID FROM AD_Column WHERE AD_Column_ID=?";
			int idTable = DB.getSQLValue(null, sql, m_Field.getAD_Column_ID());
			sql = "SELECT TableName FROM AD_Table WHERE AD_Table_ID=?";
			name = DB.getSQLValueString(null, sql, idTable);
			atts.addAttribute("", "", "ADTableNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADTableNameID", "CDATA", "");
		if (m_Field.getAD_FieldGroup_ID() > 0) {
			sql = "SELECT Name FROM AD_FieldGroup WHERE AD_FieldGroup_ID=?";
			name = DB.getSQLValueString(null, sql, m_Field
					.getAD_FieldGroup_ID());
			atts.addAttribute("", "", "ADFieldGroupNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADFieldGroupNameID", "CDATA", "");

		if (m_Field.getAD_Field_ID() > 0) {
			sql = "SELECT Name FROM AD_Field WHERE AD_Field_ID=?";
			name = DB.getSQLValueString(null, sql, m_Field.getAD_Field_ID());
			atts.addAttribute("", "", "ADFieldNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADFieldNameID", "CDATA", "");

		if (m_Field.getAD_Tab_ID() > 0) {
			sql = "SELECT Name FROM AD_Tab WHERE AD_Tab_ID=?";
			name = DB.getSQLValueString(null, sql, m_Field.getAD_Tab_ID());
			atts.addAttribute("", "", "ADTabNameID", "CDATA", name);
			sql = "SELECT AD_Window_ID FROM AD_Tab WHERE AD_Tab_ID=?";
			int windowid = DB.getSQLValue(null, sql, m_Field.getAD_Tab_ID());
			sql = "SELECT Name FROM AD_Window WHERE AD_Window_ID=?";
			name = DB.getSQLValueString(null, sql, windowid);
			atts.addAttribute("", "", "ADWindowNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADTabNameID", "CDATA", "");
		
		if (m_Field.getIncluded_Tab_ID() > 0) {
			sql = "SELECT Name FROM AD_Tab WHERE AD_Tab_ID=?";
			name = DB.getSQLValueString(null, sql, m_Field.getIncluded_Tab_ID());
			atts.addAttribute("", "", "ADIncludeTabNameID", "CDATA", name);
		}

		atts.addAttribute("", "", "EntityType", "CDATA", (m_Field
				.getEntityType() != null ? m_Field.getEntityType() : ""));
		atts.addAttribute("", "", "Name", "CDATA",
				(m_Field.getName() != null ? m_Field.getName() : ""));
		atts.addAttribute("", "", "SameLine", "CDATA",
				(m_Field.isSameLine() == true ? "true" : "false"));
		atts.addAttribute("", "", "isCentrallyMaintained", "CDATA", (m_Field
				.isCentrallyMaintained() == true ? "true" : "false"));
		atts.addAttribute("", "", "Displayed", "CDATA",
				(m_Field.isDisplayed() == true ? "true" : "false"));
		atts.addAttribute("", "", "isActive", "CDATA",
				(m_Field.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "isEncrypted", "CDATA", (m_Field
				.isEncrypted() == true ? "true" : "false"));
		atts.addAttribute("", "", "isFieldOnly", "CDATA", (m_Field
				.isFieldOnly() == true ? "true" : "false"));
		atts.addAttribute("", "", "isHeading", "CDATA",
				(m_Field.isHeading() == true ? "true" : "false"));
		atts.addAttribute("", "", "isReadOnly", "CDATA",
				(m_Field.isReadOnly() == true ? "true" : "false"));
		atts.addAttribute("", "", "SeqNo", "CDATA", "" + (m_Field.getSeqNo()));
		atts.addAttribute("", "", "DisplayLength", "CDATA",
				(m_Field.getDisplayLength() > 0 ? ""
						+ m_Field.getDisplayLength() : "0"));
		atts.addAttribute("", "", "Description", "CDATA", (m_Field
				.getDescription() != null ? m_Field.getDescription() : ""));
		atts.addAttribute("", "", "Help", "CDATA",
				(m_Field.getHelp() != null ? m_Field.getHelp() : ""));
		atts.addAttribute("", "", "SortNo", "CDATA",
				(m_Field.getSortNo() != null ? m_Field.getSortNo().toString()
						: ""));
		atts.addAttribute("", "", "DisplayLogic", "CDATA", (m_Field
				.getDisplayLogic() != null ? m_Field.getDisplayLogic() : ""));
		atts.addAttribute("", "", "ObscureType", "CDATA", (m_Field
				.getObscureType() != null ? m_Field.getObscureType() : ""));
		
		atts.addAttribute("", "", "InfoFactoryClass", "CDATA", (m_Field.getInfoFactoryClass() != null 
				? m_Field.getInfoFactoryClass() : ""));
		
		if (m_Field.getAD_Reference_ID() > 0) {
			sql = "SELECT Name FROM AD_Reference WHERE AD_Reference_ID=?";
			name = DB.getSQLValueString(null, sql, m_Field
					.getAD_Reference_ID());
			atts.addAttribute("", "", "ADReferenceNameID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADReferenceNameID", "CDATA", "");
		if (m_Field.getAD_Reference_Value_ID() > 0) {
			sql = "SELECT Name FROM AD_Reference WHERE AD_Reference_ID=?";
			name = DB.getSQLValueString(null, sql, m_Field
					.getAD_Reference_Value_ID());
			atts.addAttribute("", "", "ADReferenceNameValueID", "CDATA", name);
		} else
			atts.addAttribute("", "", "ADReferenceNameValueID", "CDATA", "");
		if (m_Field.getAD_Val_Rule_ID() > 0) {
			sql = "SELECT Name FROM AD_Val_Rule WHERE AD_Val_Rule_ID=?";
			name = DB
					.getSQLValueString(null, sql, m_Field.getAD_Val_Rule_ID());
			atts.addAttribute("", "", "ADValRuleNameID", "CDATA", name);
		} else			
			atts.addAttribute("", "", "ADValRuleNameID", "CDATA", "");
		
		return atts;
	}
	
	/**
	 * Set Included_Tab_ID (if needed)
	 * @param ctx
	 * @param field
	 * @param includedTabName
	 */
	private void setIncluded_Tab_ID(Properties ctx, MField field, String includedTabName)
	{
		if (includedTabName == null)
			return;
		//
		final String trxName = getTrxName(ctx);
		final int AD_Tab_ID = field.getAD_Tab_ID();
		if (AD_Tab_ID <= 0)
		{
			log.warning("AD_Tab_ID=0 ("+field+")");
			return;
		}
		final int AD_Window_ID = DB.getSQLValueEx(trxName,
				"SELECT AD_Window_ID FROM AD_Tab WHERE AD_Tab_ID=?",
				AD_Tab_ID); 
		final int included_Tab_ID = DB.getSQLValueEx(trxName,
				"SELECT AD_Tab_ID FROM AD_Tab WHERE Name=? AND AD_Window_ID=? AND AD_Tab_ID<>?",
				includedTabName, AD_Window_ID, AD_Tab_ID);
		if(included_Tab_ID > 0)
		{
			field.setIncluded_Tab_ID(included_Tab_ID);
		}

	}
}
