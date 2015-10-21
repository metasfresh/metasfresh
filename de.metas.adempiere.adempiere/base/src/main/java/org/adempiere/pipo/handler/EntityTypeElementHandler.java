/**
 * 
 */
package org.adempiere.pipo.handler;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.I_AD_EntityType;
import org.compiere.model.MEntityType;
import org.compiere.model.PO;
import org.compiere.model.X_AD_Package_Exp_Detail;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * @author Teo Sarca
 * 			<li>FR [ 2847694 ] 2pack import/export AD_EntityType functionality						 
 * 				https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2847694&group_id=176962
 */
public class EntityTypeElementHandler extends AbstractElementHandler
{
	public static final String TAG_Name = "entitytype";
	
	private final List<Integer> entityTypes = new ArrayList<Integer>();

	@Override
	public void startElement(Properties ctx, Element element) throws SAXException
	{
		final String elementValue = element.getElementValue();
		final Attributes atts = element.attributes;
		final String entitytype = atts.getValue(I_AD_EntityType.COLUMNNAME_EntityType);
		log.info(elementValue+" "+entitytype);
		if (isProcessElement(ctx, entitytype))
		{
			int id = get_IDWithColumn(ctx, I_AD_EntityType.Table_Name, I_AD_EntityType.COLUMNNAME_EntityType, entitytype);
			final MEntityType entity = new MEntityType(ctx, id, getTrxName(ctx));
			final int AD_Backup_ID;
			final String Object_Status;
			if (id <= 0 && getIntValue(atts, I_AD_EntityType.COLUMNNAME_AD_EntityType_ID, 0) <= PackOut.MAX_OFFICIAL_ID)
			{
				entity.setAD_EntityType_ID(getIntValue(atts, I_AD_EntityType.COLUMNNAME_AD_EntityType_ID, 0));
			}
			if (id > 0)
			{		
				AD_Backup_ID = copyRecord(ctx, I_AD_EntityType.Table_Name, entity);
				Object_Status = "Update";			
			}
			else
			{
				Object_Status = "New";
				AD_Backup_ID = 0;
			}

			entity.setName(getStringValue(atts, I_AD_EntityType.COLUMNNAME_Name));
			entity.setDescription(getStringValue(atts, I_AD_EntityType.COLUMNNAME_Description));
			entity.setHelp(getStringValue(atts, I_AD_EntityType.COLUMNNAME_Help));
			entity.setEntityType(getStringValue(atts, I_AD_EntityType.COLUMNNAME_EntityType));
			entity.setVersion(atts.getValue(I_AD_EntityType.COLUMNNAME_Version));
			entity.setIsActive(getBooleanValue(atts, I_AD_EntityType.COLUMNNAME_IsActive, true));
			entity.setModelPackage(getStringValue(atts, I_AD_EntityType.COLUMNNAME_ModelPackage));
			entity.setClasspath(getStringValue(atts, I_AD_EntityType.COLUMNNAME_Classpath));
			if (entity.save(getTrxName(ctx)) == true)
			{		    	
				record_log (ctx, 1, entity.getEntityType(), TAG_Name, entity.get_ID(),
						AD_Backup_ID, Object_Status,
						I_AD_EntityType.Table_Name, InterfaceWrapperHelper.getTableId(I_AD_EntityType.class));
			}
			else
			{
				record_log (ctx, 0, entity.getEntityType(), TAG_Name, entity.get_ID(),
						AD_Backup_ID, Object_Status,
						I_AD_EntityType.Table_Name, InterfaceWrapperHelper.getTableId(I_AD_EntityType.class));
				throw new POSaveFailedException("Failed to save message.");
			}
		}
		else
		{
			element.skip = true;
		}
	}

	@Override
	public void endElement(Properties ctx, Element element) throws SAXException
	{
	}
	
	@Override
	public void create(Properties ctx, TransformerHandler document) throws SAXException
	{
		// TODO
		final int AD_EntityType_ID = Env.getContextAsInt(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_AD_EntityType_ID);
		if (entityTypes.contains(AD_EntityType_ID))
			return;
		entityTypes.add(AD_EntityType_ID);


		final MEntityType entity = new MEntityType(ctx, AD_EntityType_ID, null);
		AttributesImpl atts = new AttributesImpl();
		createMessageBinding(atts, entity);	
		document.startElement("", "", TAG_Name, atts);
		document.endElement("", "", TAG_Name);
	}

	private AttributesImpl createMessageBinding(AttributesImpl atts, MEntityType entity) 
	{
		atts.clear();
		if (entity.getAD_EntityType_ID() <= PackOut.MAX_OFFICIAL_ID)
		{
			addAttribute(atts, I_AD_EntityType.COLUMNNAME_AD_EntityType_ID, entity);
		}
		
		addAttribute(atts, I_AD_EntityType.COLUMNNAME_Name, entity);
		addAttribute(atts, I_AD_EntityType.COLUMNNAME_Description, entity);
		addAttribute(atts, I_AD_EntityType.COLUMNNAME_Help, entity);
		addAttribute(atts, I_AD_EntityType.COLUMNNAME_EntityType, entity);
		addAttribute(atts, I_AD_EntityType.COLUMNNAME_Version, entity);
		addAttribute(atts, I_AD_EntityType.COLUMNNAME_IsActive, entity);
		addAttribute(atts, I_AD_EntityType.COLUMNNAME_ModelPackage, entity);
		addAttribute(atts, I_AD_EntityType.COLUMNNAME_Classpath, entity);
		
		return atts;
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
