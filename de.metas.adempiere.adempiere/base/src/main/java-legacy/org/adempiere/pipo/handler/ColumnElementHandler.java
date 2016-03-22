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
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackIn;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.DatabaseAccessException;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.X_AD_Column;
import org.compiere.model.X_AD_Element;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class ColumnElementHandler extends AbstractElementHandler
{

	public void startElement(Properties ctx, Element element)
			throws SAXException
	{
		PackIn packIn = (PackIn)ctx.get("PackInProcess");
		String elementValue = element.getElementValue();
		Attributes atts = element.attributes;
		log.info(elementValue + " " + atts.getValue("ColumnName"));
		int success = 0;
		String entitytype = atts.getValue("EntityType");
		if (isProcessElement(ctx, entitytype))
		{
			if (element.parent != null && element.parent.getElementValue().equals("table") &&
					element.parent.defer)
			{
				element.defer = true;
				return;
			}
			String columnName = atts.getValue("ColumnName");
			String tableName = atts.getValue("ADTableNameID");
			int tableid = 0;
			if (element.parent != null && element.parent.getElementValue().equals("table") &&
					element.parent.recordId > 0)
			{
				tableid = element.parent.recordId;
			}
			else
			{
				tableid = packIn.getTableId(tableName);
			}
			if (tableid <= 0)
			{
				tableid = get_IDWithColumn(ctx, "AD_Table", "TableName", tableName);
				if (tableid > 0)
					packIn.addTable(tableName, tableid);
			}
			int id = packIn.getColumnId(tableName, columnName);
			if (id <= 0)
			{
				id = get_IDWithMasterAndColumn(ctx, "AD_Column", "ColumnName",
						columnName, "AD_Table", tableid);
				if (id > 0)
				{
					packIn.addColumn(tableName, columnName, id);
				}
			}
			MColumn m_Column = new MColumn(ctx, id, getTrxName(ctx));
			if (id <= 0 && atts.getValue("AD_Column_ID") != null && Integer.parseInt(atts.getValue("AD_Column_ID")) <= PackOut.MAX_OFFICIAL_ID)
				m_Column.setAD_Column_ID(Integer.parseInt(atts.getValue("AD_Column_ID")));
			int AD_Backup_ID = -1;
			String Object_Status = null;
			if (id > 0)
			{
				AD_Backup_ID = copyRecord(ctx, "AD_Column", m_Column);
				Object_Status = "Update";
			}
			else
			{
				Object_Status = "New";
				AD_Backup_ID = 0;
			}
			m_Column.setColumnName(columnName);

			// Process
			String processName = atts.getValue("ADProcessNameID");
			int AD_Process_ID = get_IDWithColumn(ctx, "AD_Process", "Value", processName);
			if (AD_Process_ID <= 0 /** TODO PackOut version check 005 */
			)
			{
				AD_Process_ID = get_IDWithColumn(ctx, "AD_Process", "Name", processName);
			}
			m_Column.setAD_Process_ID(AD_Process_ID);
			//
			String Name = atts.getValue("ADReferenceNameID");
			id = get_IDWithColumn(ctx, "AD_Reference", "Name", Name);
			m_Column.setAD_Reference_ID(id);
			// log.info("Column ID ->"+id);
			Name = atts.getValue("ADTableNameID");
			id = get_IDWithColumn(ctx, "AD_Table", "TableName", Name);
			m_Column.setAD_Table_ID(id);

			Name = atts.getValue("ADValRuleNameID");
			id = get_IDWithColumn(ctx, "AD_Val_Rule", "Name", Name);
			m_Column.setAD_Val_Rule_ID(id);
			Name = atts.getValue("ADReferenceNameValueID");
			id = get_IDWithColumn(ctx, "AD_Reference", "Name", Name);
			m_Column.setAD_Reference_Value_ID(id);
			// m_Column.setCallout(getStringValue(atts, "Callout")); // metas: TODO
			m_Column.setColumnSQL(getStringValue(atts, "ColumnSQL"));

			m_Column.setColumnName(atts.getValue("ColumnName"));
			m_Column.setDefaultValue(getStringValue(atts, "DefaultValue"));
			m_Column.setDescription(getStringValue(atts, "Description"));
			m_Column.setEntityType(atts.getValue("EntityType"));

			if (Integer.parseInt(atts.getValue("FieldLength")) > 0)
				m_Column.setFieldLength(Integer.parseInt(atts
						.getValue("FieldLength")));
			m_Column.setHelp(getStringValue(atts, "Help"));
			m_Column.setIsActive(atts.getValue("isActive") != null ? Boolean
					.valueOf(atts.getValue("isActive")).booleanValue() : true);
			m_Column.setIsAlwaysUpdateable((Boolean.valueOf(atts
					.getValue("isAlwaysUpdateable")).booleanValue()));
			// m_Column.setIsEncrypted(atts.getValue("isEncrypted"));
			m_Column.setIsIdentifier((Boolean.valueOf(atts
					.getValue("isIdentifier")).booleanValue()));
			m_Column.setIsKey((Boolean.valueOf(atts.getValue("isKey"))
					.booleanValue()));
			m_Column.setIsMandatory((Boolean.valueOf(atts
					.getValue("isMandatory")).booleanValue()));

			m_Column.setIsParent((Boolean.valueOf(atts.getValue("isParent"))
					.booleanValue()));
			m_Column.setIsSelectionColumn((Boolean.valueOf(atts
					.getValue("isSelectionColumn")).booleanValue()));
			m_Column.setIsSyncDatabase(atts.getValue("getIsSyncDatabase"));

			m_Column.setIsTranslated((Boolean.valueOf(atts
					.getValue("isTranslated")).booleanValue()));
			m_Column.setIsUpdateable((Boolean.valueOf(atts
					.getValue("isUpdateable")).booleanValue()));
			m_Column.setName(atts.getValue("Name"));
			m_Column.setReadOnlyLogic(getStringValue(atts, "ReadOnlyLogic"));

			if (Integer.parseInt(atts.getValue("SeqNo")) > 0)
				m_Column.setSeqNo(Integer.parseInt(atts.getValue("SeqNo")));
			m_Column.setVFormat(getStringValue(atts, "VFormat"));
			if (getStringValue(atts, "ValueMax") != null)
				m_Column.setValueMax(atts.getValue("ValueMax"));
			if (getStringValue(atts, "ValueMin") != null)
				m_Column.setValueMin(atts.getValue("ValueMin"));
			if (getStringValue(atts, "Version") != null)
				m_Column.setVersion(new BigDecimal(atts.getValue("Version")));

			m_Column.setInfoFactoryClass(getStringValue(atts, "InfoFactoryClass"));

			// Setup Element.
			id = get_IDWithColumn(ctx, "AD_Element", "ColumnName", m_Column
					.getColumnName());
			X_AD_Element adElement = new X_AD_Element(ctx, id, getTrxName(ctx));

			String Object_Status_col = Object_Status;
			if (adElement.getAD_Element_ID() == 0)
			{
				// Object_Status = "New";
				adElement.setColumnName(m_Column.getColumnName());
				adElement.setEntityType(m_Column.getEntityType());
				adElement.setPrintName(m_Column.getColumnName());

				adElement.setName(m_Column.getColumnName());
				if (adElement.save(getTrxName(ctx)) == true)
				{
					record_log(ctx, 1, m_Column.getName(), "Element", adElement
							.getAD_Element_ID(), AD_Backup_ID, "New",
							"AD_Element", get_IDWithColumn(ctx, "AD_Table",
									"TableName", "AD_Element"));
				}
				else
				{
					record_log(ctx, 0, m_Column.getName(), "Element", adElement
							.getAD_Element_ID(), AD_Backup_ID, "New",
							"AD_Element", get_IDWithColumn(ctx, "AD_Table",
									"TableName", "AD_Element"));
				}
			}

			Object_Status = Object_Status_col;
			m_Column.setAD_Element_ID(adElement.getAD_Element_ID());

			boolean recreateColumn = (m_Column.is_new()
					|| m_Column.is_ValueChanged("AD_Reference_ID")
					|| m_Column.is_ValueChanged("FieldLength")
					|| m_Column.is_ValueChanged("ColumnName") || m_Column
							.is_ValueChanged("IsMandatory"));

			// ignore fieldlength change for clob and lob
			if (!m_Column.is_ValueChanged("AD_Reference_ID") && m_Column.is_ValueChanged("FieldLength"))
			{
				if (DisplayType.isLOB(m_Column.getAD_Reference_ID()))
				{
					recreateColumn = false;
				}
			}

			// changed default ??
			// m_Column.is_ValueChanged("DefaultValue") doesn't work well with
			// nulls
			if (!recreateColumn)
			{
				String oldDefault = (String)m_Column
						.get_ValueOld("DefaultValue");
				String newDefault = m_Column.getDefaultValue();
				if (oldDefault != null && oldDefault.length() == 0)
					oldDefault = null;
				if (newDefault != null && newDefault.length() == 0)
					newDefault = null;
				if ((oldDefault == null && newDefault != null)
						|| (oldDefault != null && newDefault == null))
				{
					recreateColumn = true;
				}
				else if (oldDefault != null && newDefault != null)
				{
					if (!oldDefault.equals(newDefault))
						recreateColumn = true;
				}
			}

			// Don't create database column for virtual columns
			boolean syncDatabase = "Y".equalsIgnoreCase(atts.getValue("getIsSyncDatabase"));
			if (recreateColumn)
			{
				if (m_Column.isVirtualColumn() || !syncDatabase)
					recreateColumn = false;
			}

			if (m_Column.save(getTrxName(ctx)) == true)
			{
				record_log(ctx, 1, m_Column.getName(), "Column", m_Column
						.get_ID(), AD_Backup_ID, Object_Status, "AD_Column",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Column"));
				element.recordId = m_Column.getAD_Column_ID();
			}
			else
			{
				record_log(ctx, 0, m_Column.getName(), "Column", m_Column
						.get_ID(), AD_Backup_ID, Object_Status, "AD_Column",
						get_IDWithColumn(ctx, "AD_Table", "TableName",
								"AD_Column"));
				throw new POSaveFailedException("Failed to import column.");
			}

			if (recreateColumn || syncDatabase)
			{
				MTable table = new MTable(ctx, m_Column.getAD_Table_ID(), getTrxName(ctx));
				if (!table.isView() && !m_Column.isVirtualColumn())
				{
					success = createColumn(ctx, table, m_Column, recreateColumn);

					if (success == 1)
					{
						record_log(ctx, 1, m_Column.getColumnName(), "dbColumn",
								m_Column.get_ID(), 0, Object_Status, atts.getValue(
										"ADTableNameID").toUpperCase(),
								get_IDWithColumn(ctx, "AD_Table", "TableName", atts
										.getValue("ADTableNameID").toUpperCase()));
					}
					else
					{
						record_log(ctx, 0, m_Column.getColumnName(), "dbColumn",
								m_Column.get_ID(), 0, Object_Status, atts.getValue(
										"ADTableNameID").toUpperCase(),
								get_IDWithColumn(ctx, "AD_Table", "TableName", atts
										.getValue("ADTableNameID").toUpperCase()));
						throw new DatabaseAccessException("Failed to create column or related constraint for " + m_Column.getColumnName());
					}
				}
			}
		}
		else
		{
			element.skip = true;
		}
	}

	/**
	 * Check if column exists in database and modify. If not create column.
	 * 
	 * @param tablename
	 * @param columnname
	 * @param v_AD_Reference_ID
	 * @param v_FieldLength
	 * @param v_DefaultValue
	 * @param v_IsMandatory
	 * 
	 */
	private int createColumn(Properties ctx, MTable table, MColumn column, boolean doAlter)
	{

		int no = 0;

		String sql = null;
		ResultSet rst = null;
		ResultSet rsc = null;
		Connection conn = null;
		Trx trx = Trx.get(getTrxName(ctx), true);
		if (!trx.commit())
			return 0;

		try
		{
			// Find Column in Database
			conn = trx.getConnection();
			DatabaseMetaData md = conn.getMetaData();
			String catalog = DB.getDatabase().getCatalog();
			String schema = DB.getDatabase().getSchema();
			String tableName = table.getTableName();
			String columnName = column.getColumnName();

			tableName = tableName.toLowerCase();
			columnName = columnName.toLowerCase();

			rst = md.getTables(catalog, schema, tableName,
					new String[] { "TABLE" });
			if (!rst.next())
			{
				// table doesn't exist
				sql = table.getSQLCreate();
			}
			else
			{
				//
				rsc = md.getColumns(catalog, schema, tableName, columnName);
				if (rsc.next())
				{
					if (doAlter)
					{
						// update existing column
						boolean notNull = DatabaseMetaData.columnNoNulls == rsc
								.getInt("NULLABLE");
						sql = column.getSQLModify(table,
								column.isMandatory() != notNull);
					}
				}
				else
				{
					// No existing column
					sql = column.getSQLAdd(table);
				}
				rsc.close();
				rsc = null;
			}

			rst.close();
			rst = null;
			// execute modify or add if needed
			if (sql != null && sql.trim().length() > 0)
			{
				log.info(sql);

				if (sql.indexOf(DB.SQLSTATEMENT_SEPARATOR) == -1)
				{
					no = DB.executeUpdate(sql, false, trx.getTrxName());
					if (no == -1)
						return 0;
				}
				else
				{
					String statements[] = sql.split(DB.SQLSTATEMENT_SEPARATOR);
					for (int i = 0; i < statements.length; i++)
					{
						int count = DB.executeUpdate(statements[i], false,
								trx.getTrxName());
						if (count == -1)
						{
							return 0;
						}
						no += count;
					}
				}
			}
			trx.commit(true);
		}
		catch (SQLException e)
		{
			log.error(e.getLocalizedMessage(), e);
			if (rsc != null)
			{
				try
				{
					rsc.close();
				}
				catch (SQLException e1)
				{
				}
				rsc = null;
			}
			if (rst != null)
			{
				try
				{
					rst.close();
				}
				catch (SQLException e1)
				{
				}
				rst = null;
			}
			trx.rollback();
			return 0;
		}

		return 1;
	}

	public void endElement(Properties ctx, Element element) throws SAXException
	{
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException
	{
		int AD_Column_ID = Env.getContextAsInt(ctx,
				X_AD_Column.COLUMNNAME_AD_Column_ID);
		AttributesImpl atts = new AttributesImpl();
		X_AD_Column m_Column = new X_AD_Column(ctx, AD_Column_ID,
				getTrxName(ctx));
		createColumnBinding(atts, m_Column);
		document.startElement("", "", "column", atts);
		document.endElement("", "", "column");
	}

	private AttributesImpl createColumnBinding(AttributesImpl atts,
			X_AD_Column m_Column)
	{
		String sql = null;
		String name = null;
		atts.clear();
		if (m_Column.getAD_Column_ID() <= PackOut.MAX_OFFICIAL_ID)
			atts.addAttribute("", "", "AD_Column_ID", "CDATA", Integer.toString(m_Column.getAD_Column_ID()));
		if (m_Column.getAD_Column_ID() > 0)
		{
			sql = "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?";
			name = DB.getSQLValueString(null, sql, m_Column.getAD_Column_ID());
			atts.addAttribute("", "", "ADColumnNameID", "CDATA", name);
		}
		else
			atts.addAttribute("", "", "ADColumnNameID", "CDATA", "");
		//
		if (m_Column.getAD_Process_ID() > 0)
		{
			sql = "SELECT Value FROM AD_Process WHERE AD_Process_ID=?";
			name = DB.getSQLValueStringEx(null, sql, m_Column.getAD_Process_ID());
			atts.addAttribute("", "", "ADProcessNameID", "CDATA", name);
		}
		else
		{
			atts.addAttribute("", "", "ADProcessNameID", "CDATA", "");
		}
		// Element - this info is not needed since we search for element based on ColumnName
		if (m_Column.getAD_Element_ID() > 0)
		{
			sql = "SELECT ColumnName FROM AD_Element WHERE AD_Element_ID=?";
			name = DB.getSQLValueStringEx(null, sql, m_Column.getAD_Element_ID());
			atts.addAttribute("", "", "ADElementNameID", "CDATA", name);
		}
		else
		{
			atts.addAttribute("", "", "ADElementNameID", "CDATA", "");
		}
		//
		if (m_Column.getAD_Reference_ID() > 0)
		{
			sql = "SELECT Name FROM AD_Reference WHERE AD_Reference_ID=?";
			name = DB.getSQLValueString(null, sql, m_Column
					.getAD_Reference_ID());
			atts.addAttribute("", "", "ADReferenceNameID", "CDATA", name);
		}
		else
			atts.addAttribute("", "", "ADReferenceNameID", "CDATA", "");
		if (m_Column.getAD_Reference_Value_ID() > 0)
		{
			sql = "SELECT Name FROM AD_Reference WHERE AD_Reference_ID=?";
			name = DB.getSQLValueString(null, sql, m_Column
					.getAD_Reference_Value_ID());
			atts.addAttribute("", "", "ADReferenceNameValueID", "CDATA", name);
		}
		else
			atts.addAttribute("", "", "ADReferenceNameValueID", "CDATA", "");
		if (m_Column.getAD_Table_ID() > 0)
		{
			sql = "SELECT TableName FROM AD_Table WHERE AD_Table_ID=?";
			name = DB.getSQLValueString(null, sql, m_Column.getAD_Table_ID());
			atts.addAttribute("", "", "ADTableNameID", "CDATA", name);
		}
		else
			atts.addAttribute("", "", "ADTableNameID", "CDATA", "");
		if (m_Column.getAD_Val_Rule_ID() > 0)
		{
			sql = "SELECT Name FROM AD_Val_Rule WHERE AD_Val_Rule_ID=?";
			name = DB
					.getSQLValueString(null, sql, m_Column.getAD_Val_Rule_ID());
			atts.addAttribute("", "", "ADValRuleNameID", "CDATA", name);
		}
		else
			atts.addAttribute("", "", "ADValRuleNameID", "CDATA", "");
		// metas: TODO: Callout was moved to AD_ColumnCallout
		// atts.addAttribute("", "", "Callout", "CDATA",
		// (m_Column.getCallout() != null ? m_Column.getCallout() : ""));
		atts.addAttribute("", "", "ColumnSQL", "CDATA", (m_Column
				.getColumnSQL() != null ? m_Column.getColumnSQL() : ""));
		atts.addAttribute("", "", "ColumnName", "CDATA", (m_Column
				.getColumnName() != null ? m_Column.getColumnName() : ""));
		atts.addAttribute("", "", "DefaultValue", "CDATA", (m_Column
				.getDefaultValue() != null ? m_Column.getDefaultValue() : ""));
		atts.addAttribute("", "", "Description", "CDATA", (m_Column
				.getDescription() != null ? m_Column.getDescription() : ""));
		atts.addAttribute("", "", "EntityType", "CDATA", (m_Column
				.getEntityType() != null ? m_Column.getEntityType() : ""));
		atts.addAttribute("", "", "FieldLength", "CDATA", (m_Column
				.getFieldLength() > 0 ? "" + m_Column.getFieldLength() : "0"));
		atts.addAttribute("", "", "Help", "CDATA",
				(m_Column.getHelp() != null ? m_Column.getHelp() : ""));
		atts.addAttribute("", "", "isAlwaysUpdateable", "CDATA", (m_Column
				.isAlwaysUpdateable() == true ? "true" : "false"));
		// atts.addAttribute("","","isEncrypted","CDATA",(m_Column.getIsEncrypted()==
		// true ? "true":"false"));
		atts.addAttribute("", "", "isIdentifier", "CDATA", (m_Column
				.isIdentifier() == true ? "true" : "false"));
		atts.addAttribute("", "", "isKey", "CDATA",
				(m_Column.isKey() == true ? "true" : "false"));
		atts.addAttribute("", "", "isMandatory", "CDATA", (m_Column
				.isMandatory() == true ? "true" : "false"));
		atts.addAttribute("", "", "isParent", "CDATA",
				(m_Column.isParent() == true ? "true" : "false"));
		atts.addAttribute("", "", "isSelectionColumn", "CDATA", (m_Column
				.isSelectionColumn() == true ? "true" : "false"));
		atts.addAttribute("", "", "isActive", "CDATA",
				(m_Column.isActive() == true ? "true" : "false"));
		atts.addAttribute("", "", "isTranslated", "CDATA", (m_Column
				.isTranslated() == true ? "true" : "false"));
		atts.addAttribute("", "", "isUpdateable", "CDATA", (m_Column
				.isUpdateable() == true ? "true" : "false"));
		atts.addAttribute("", "", "Name", "CDATA",
				(m_Column.getName() != null ? m_Column.getName() : ""));
		atts.addAttribute("", "", "getIsSyncDatabase", "CDATA", "Y");
		atts
				.addAttribute("", "", "ReadOnlyLogic", "CDATA", (m_Column
						.getReadOnlyLogic() != null ? m_Column
								.getReadOnlyLogic() : ""));
		atts.addAttribute("", "", "SeqNo", "CDATA",
				(m_Column.getSeqNo() > 0 ? "" + m_Column.getSeqNo() : "0"));
		atts.addAttribute("", "", "VFormat", "CDATA",
				(m_Column.getVFormat() != null ? m_Column.getVFormat() : ""));
		atts.addAttribute("", "", "ValueMax", "CDATA",
				(m_Column.getValueMax() != null ? m_Column.getValueMax() : ""));
		atts.addAttribute("", "", "ValueMin", "CDATA",
				(m_Column.getValueMin() != null ? m_Column.getValueMin() : ""));
		atts.addAttribute("", "", "Version", "CDATA",
				(m_Column.getVersion() != null ? "" + m_Column.getVersion()
						: "0.0"));
		atts.addAttribute("", "", "InfoFactoryClass", "CDATA", (m_Column.getInfoFactoryClass() != null
				? m_Column.getInfoFactoryClass() : ""));

		return atts;
	}
}
