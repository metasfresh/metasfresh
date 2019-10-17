/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 *  Product Attribute Set
 *
 *	@author Jorg Janke
 *	@version $Id: MAttributeInstance.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MAttributeInstance extends X_M_AttributeInstance
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6154044437449512042L;


	/**
	 * 	Persistency Constructor
	 *	@param ctx context
	 *	@param ignored ignored
	 *	@param trxName transaction
	 */
	public MAttributeInstance (Properties ctx, int id, String trxName)
	{
		super(ctx, id, trxName);
	}	//	MAttributeInstance

	/**
	 * 	Load Cosntructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAttributeInstance (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAttributeInstance

	/**
	 * 	String Value Constructior
	 *	@param ctx context
	 *	@param M_Attribute_ID attribute
	 *	@param M_AttributeSetInstance_ID instance
	 *	@param Value string value
	 *	@param trxName transaction
	 */
	public MAttributeInstance (Properties ctx, int M_Attribute_ID,
		int M_AttributeSetInstance_ID, String Value, String trxName)
	{
		super(ctx, 0, trxName);
		setM_Attribute_ID (M_Attribute_ID);
		setM_AttributeSetInstance_ID (M_AttributeSetInstance_ID);
		setValue (Value);
	}	//	MAttributeInstance

	/**
	 * 	Number Value Constructior
	 *	@param ctx context
	 *	@param M_Attribute_ID attribute
	 *	@param M_AttributeSetInstance_ID instance
	 *	@param BDValue number value
	 *	@param trxName transaction
	 */
	public MAttributeInstance (Properties ctx, int M_Attribute_ID,
		int M_AttributeSetInstance_ID, BigDecimal BDValue, String trxName)
	{
		super(ctx, 0, trxName);
		setM_Attribute_ID (M_Attribute_ID);
		setM_AttributeSetInstance_ID (M_AttributeSetInstance_ID);
		setValueNumber(BDValue);
	}	//	MAttributeInstance

	/**
	 * 	Selection Value Constructior
	 *	@param ctx context
	 *	@param M_Attribute_ID attribute
	 *	@param M_AttributeSetInstance_ID instance
	 *	@param M_AttributeValue_ID selection
	 * 	@param Value String representation for fast display
	 *	@param trxName transaction
	 */
	public MAttributeInstance (Properties ctx, int M_Attribute_ID,
		int M_AttributeSetInstance_ID, int M_AttributeValue_ID, String Value, String trxName)
	{
		super(ctx, 0, trxName);
		setM_Attribute_ID (M_Attribute_ID);
		setM_AttributeSetInstance_ID (M_AttributeSetInstance_ID);
		setM_AttributeValue_ID (M_AttributeValue_ID);
		setValue (Value);
	}	//	MAttributeInstance


	/**
	 * 	Set ValueNumber
	 *	@param ValueNumber number
	 */
	@Override
	public void setValueNumber (BigDecimal ValueNumber)
	{
		super.setValueNumber (ValueNumber);
		if (ValueNumber == null)
		{
			setValue(null);
			return;
		}
		if (ValueNumber.signum() == 0)
		{
			setValue("0");
			return;
		}
		setValue(ValueNumber.toString());
	}	//	setValueNumber


	/**
	 *	String Representation
	 * 	@return info
	 */
	@Override
	public String toString()
	{
		final String value = getValue();
		return value == null ? "" : value;
	}	//	toString

}	//	MAttributeInstance
