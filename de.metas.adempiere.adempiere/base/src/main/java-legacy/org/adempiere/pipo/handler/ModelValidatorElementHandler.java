/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2009 Adempiere, Inc. All Rights Reserved.               *
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
 * Copyright (C) 2009 Teo Sarca, teo.sarca@gmail.com                          *
 *****************************************************************************/
package org.adempiere.pipo.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.adempiere.pipo.PackOut;
import org.adempiere.pipo.exception.POSaveFailedException;
import org.compiere.model.I_AD_ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_AD_ModelValidator;
import org.compiere.model.X_AD_Package_Exp_Detail;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * @author Teo Sarca, teo.sarca@gmail.com
 * 			<li>FR [ 2847669 ] 2pack export model validator functionality
 * 				https://sourceforge.net/tracker/?func=detail&aid=2847669&group_id=176962&atid=879335
 */
public class ModelValidatorElementHandler extends AbstractElementHandler
{
	public static final String TAG_Name = "modelvalidator";
	
	private final List<Integer> validators = new ArrayList<Integer>();

	public void startElement(Properties ctx, Element element) throws SAXException
	{
		String elementValue = element.getElementValue();
		Attributes atts = element.attributes;
		log.info(elementValue+" "+atts.getValue(I_AD_ModelValidator.COLUMNNAME_Name));
		String entitytype = atts.getValue(I_AD_ModelValidator.COLUMNNAME_EntityType);
		if (isProcessElement(ctx, entitytype))
		{
			String name = atts.getValue(I_AD_ModelValidator.COLUMNNAME_Name);
			int id = get_IDWithColumn(ctx, I_AD_ModelValidator.Table_Name, I_AD_ModelValidator.COLUMNNAME_Name, name);

			final X_AD_ModelValidator validator = new X_AD_ModelValidator(ctx, id, getTrxName(ctx));
			final int AD_Backup_ID;
			final String Object_Status;
			if (id <= 0 && getIntValue(atts, I_AD_ModelValidator.COLUMNNAME_AD_ModelValidator_ID, 0) <= PackOut.MAX_OFFICIAL_ID)
			{
				validator.setAD_ModelValidator_ID(getIntValue(atts, I_AD_ModelValidator.COLUMNNAME_AD_ModelValidator_ID, 0));
			}
			if (id > 0)
			{		
				AD_Backup_ID = copyRecord(ctx, I_AD_ModelValidator.Table_Name, validator);
				Object_Status = "Update";			
			}
			else
			{
				Object_Status = "New";
				AD_Backup_ID = 0;
			}

			validator.setName(name);
			validator.setDescription(getStringValue(atts, I_AD_ModelValidator.COLUMNNAME_Description));
			validator.setHelp(getStringValue(atts, I_AD_ModelValidator.COLUMNNAME_Help));
			validator.setEntityType(atts.getValue(I_AD_ModelValidator.COLUMNNAME_EntityType));
			validator.setModelValidationClass(atts.getValue(I_AD_ModelValidator.COLUMNNAME_ModelValidationClass));
			validator.setIsActive(getBooleanValue(atts, I_AD_ModelValidator.COLUMNNAME_IsActive, true));
			validator.setSeqNo(getIntValue(atts, I_AD_ModelValidator.COLUMNNAME_SeqNo, 0));

			if (validator.save(getTrxName(ctx)) == true)
			{		    	
				record_log (ctx, 1, validator.getName(),TAG_Name, validator.get_ID(),
						AD_Backup_ID, Object_Status,
						I_AD_ModelValidator.Table_Name, I_AD_ModelValidator.Table_ID);
			}
			else
			{
				record_log (ctx, 0, validator.getName(),TAG_Name, validator.get_ID(),
						AD_Backup_ID, Object_Status,
						I_AD_ModelValidator.Table_Name, I_AD_ModelValidator.Table_ID);
				throw new POSaveFailedException("Failed to save message.");
			}
		}
		else
		{
			element.skip = true;
		}
	}

	public void endElement(Properties ctx, Element element) throws SAXException
	{
	}

	public void create(Properties ctx, TransformerHandler document) throws SAXException
	{
		final int AD_ModelValidator_ID = Env.getContextAsInt(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_AD_ModelValidator_ID);
		if (validators.contains(AD_ModelValidator_ID))
			return;
		validators.add(AD_ModelValidator_ID);


		final X_AD_ModelValidator validator = new X_AD_ModelValidator(ctx, AD_ModelValidator_ID, null);
		AttributesImpl atts = new AttributesImpl();
		createMessageBinding(atts, validator);	
		document.startElement("", "", TAG_Name, atts);
		document.endElement("", "", TAG_Name);
	}

	private AttributesImpl createMessageBinding(AttributesImpl atts, X_AD_ModelValidator validator) 
	{
		atts.clear();
		if (validator.getAD_ModelValidator_ID() <= PackOut.MAX_OFFICIAL_ID)
		{
			addAttribute(atts, X_AD_ModelValidator.COLUMNNAME_AD_ModelValidator_ID, validator);
		}
		addAttribute(atts, X_AD_ModelValidator.COLUMNNAME_Name, validator);
		addAttribute(atts, X_AD_ModelValidator.COLUMNNAME_Description, validator);
		addAttribute(atts, X_AD_ModelValidator.COLUMNNAME_Help, validator);
		addAttribute(atts, X_AD_ModelValidator.COLUMNNAME_ModelValidationClass, validator);
		addAttribute(atts, X_AD_ModelValidator.COLUMNNAME_EntityType, validator);
		addAttribute(atts, X_AD_ModelValidator.COLUMNNAME_IsActive, validator);
		addAttribute(atts, X_AD_ModelValidator.COLUMNNAME_SeqNo, validator);
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
