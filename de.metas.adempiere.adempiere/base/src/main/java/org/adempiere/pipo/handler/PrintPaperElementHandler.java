/**
 * 
 */
package org.adempiere.pipo.handler;

/*
 * #%L
 * ADempiere ERP - Base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.I_AD_PrintPaper;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * @author teo.sarca@gmail.com
 * 		<li>FR [ 2867966 ] Export PrintPaper
 * 			https://sourceforge.net/tracker/?func=detail&aid=2867966&group_id=176962&atid=879335
 */
public class PrintPaperElementHandler extends AbstractElementHandler
{
	public static final String TAG_Name = "printpaper";

	private final List<Integer> list = new ArrayList<Integer>();
	
	protected String getTagName()
	{
		return TAG_Name;
	}
	
	protected String getTableName()
	{
		return I_AD_PrintPaper.Table_Name;
	}
	
	protected final int getTable_ID()
	{
		return MTable.getTable_ID(getTableName());
	}
	
	protected String getKeyColumnName()
	{
		return I_AD_PrintPaper.COLUMNNAME_AD_PrintPaper_ID;
	}
	
	protected String getIdentifierColumnName()
	{
		return I_AD_PrintPaper.COLUMNNAME_Name;
	}
	
	protected String[] getAttributeNames()
	{
		final String[] attributeNames = new String[]{
				I_AD_PrintPaper.COLUMNNAME_Name,
				I_AD_PrintPaper.COLUMNNAME_Description,
				I_AD_PrintPaper.COLUMNNAME_IsActive,
				I_AD_PrintPaper.COLUMNNAME_IsDefault,
				I_AD_PrintPaper.COLUMNNAME_IsLandscape,
				I_AD_PrintPaper.COLUMNNAME_Code,
				I_AD_PrintPaper.COLUMNNAME_MarginTop,
				I_AD_PrintPaper.COLUMNNAME_MarginLeft,
				I_AD_PrintPaper.COLUMNNAME_MarginRight,
				I_AD_PrintPaper.COLUMNNAME_MarginBottom,
				I_AD_PrintPaper.COLUMNNAME_SizeX,
				I_AD_PrintPaper.COLUMNNAME_SizeY,
				I_AD_PrintPaper.COLUMNNAME_DimensionUnits,
		};
		return attributeNames;
	}
	
	protected int getExportItem_ID(Properties ctx)
	{
		final int id = Env.getContextAsInt(ctx, getKeyColumnName());
		return id;
	}
	
	protected PO getCreatePO(Properties ctx, int id, String trxName)
	{
		return MTable.get(ctx, getTableName()).getPO(id, trxName);
	}

	public void startElement(Properties ctx, Element element) throws SAXException
	{
		final String elementValue = element.getElementValue();
		final Attributes atts = element.attributes;

		final String strIdentifier = atts.getValue(getIdentifierColumnName());
		final int id = get_IDWithColumn(ctx, getTableName(), getIdentifierColumnName(), strIdentifier);
		final PO po = getCreatePO(ctx, id, getTrxName(ctx));
		final String keyColumnName = getKeyColumnName();
		log.info(elementValue+" "+strIdentifier+"["+id+"]");

		if (id <= 0 && keyColumnName != null && getIntValue(atts, keyColumnName, 0) <= PackOut.MAX_OFFICIAL_ID)
		{
			po.set_ValueOfColumn(keyColumnName, getIntValue(atts, keyColumnName, 0));
		}
		
		final int AD_Backup_ID;
		final String Object_Status;
		if (id > 0)
		{		
			AD_Backup_ID = copyRecord(ctx, getTableName(), po);
			Object_Status = "Update";
		}
		else
		{
			Object_Status = "New";
			AD_Backup_ID = 0;
		}

		for (String attributeName : getAttributeNames())
		{
			loadAttribute(atts, attributeName, po);
		}

		if (po.save(getTrxName(ctx)) == true)
		{		    	
			record_log (ctx, 1, strIdentifier, getTagName(), po.get_ID(),
					AD_Backup_ID, Object_Status,
					getTableName(), getTable_ID());
		}
		else
		{
			record_log (ctx, 0, strIdentifier, getTagName(), po.get_ID(),
					AD_Backup_ID, Object_Status,
					getTableName(), getTable_ID());
			throw new POSaveFailedException("Failed to save message.");
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException
	{
	}

	public void create(Properties ctx, TransformerHandler document) throws SAXException
	{
		final int id = getExportItem_ID(ctx);
		if (list.contains(id))
			return;
		list.add(id);

		final PO po = getCreatePO(ctx, id, null);
		final AttributesImpl atts = new AttributesImpl();
		createMessageBinding(atts, po);	
		document.startElement("", "", getTagName(), atts);
		document.endElement("", "", getTagName());
	}

	private AttributesImpl createMessageBinding(AttributesImpl atts, PO po) 
	{
		atts.clear();
		// Add ID if it's official
		String keyColumnName = getKeyColumnName();
		if (keyColumnName != null && po.get_ID() <= PackOut.MAX_OFFICIAL_ID)
		{
			addAttribute(atts, keyColumnName, po);
		}
		for (String name : getAttributeNames())
		{
			addAttribute(atts, name, po);
		}
		return atts;
	}

	protected void loadAttribute(Attributes atts, String name, PO po)
	{
		final String strValueExact = atts.getValue(name);
		String strValue = strValueExact;
		if (Util.isEmpty(strValue, true))
		{
			strValue = null;
		}
		else
		{
			strValue = strValue.trim();
		}
		
		final POInfo poInfo = POInfo.getPOInfo(po.getCtx(), getTable_ID());
		final Class<?> clazz = poInfo.getColumnClass(poInfo.getColumnIndex(name));
		if (strValue == null)
		{
			po.set_ValueOfColumn(name, null);
		}
		else if (clazz == BigDecimal.class)
		{
			po.set_ValueOfColumn(name, new BigDecimal(strValue));
		}
		else if (clazz == Integer.class)
		{
			po.set_ValueOfColumn(name, new BigDecimal(strValue).intValueExact());
		}
		else if (clazz == String.class)
		{
			po.set_ValueOfColumn(name, strValueExact);
		}
		else if (clazz == Boolean.class)
		{
			po.set_ValueOfColumn(name, Boolean.valueOf(strValue));
		}
		else if (clazz == Timestamp.class)
		{
			Timestamp ts = Timestamp.valueOf(strValue);
			po.set_ValueOfColumn(name, ts);
		}
		else
		{
			throw new AdempiereException("Class not supported - "+clazz);
		}
	}

	protected boolean getBooleanValue(Attributes atts, String qName, boolean defaultValue)
	{
		String s = atts.getValue(qName);
		return s != null ? Boolean.valueOf(s) : defaultValue;
	}

	protected int getIntValue(Attributes atts, String qName, int defaultValue)
	{
		Object o = atts.getValue(qName);
		if (o == null)
			return defaultValue;
		if (o instanceof Number)
			return ((Number)o).intValue();
		return Integer.parseInt(o.toString());
	}

	private final void addAttribute(AttributesImpl atts, String name, PO po)
	{
		Object value = po.get_Value(name);
		atts.addAttribute("", "", name, "CDATA", toStringAttribute(value));
	}

	private final String toStringAttribute(Object value)
	{
		if (value == null)
			return "";
		if (value instanceof Boolean)
			return ((Boolean)value).booleanValue() == true ? "true" : "false";
		return value.toString();
	}
}
