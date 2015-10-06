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

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product Attribute Value
 * 
 * @author Jorg Janke
 * @version $Id: MAttributeValue.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 * @deprecated use {@link I_M_AttributeValue} instead.
 */
@Deprecated
public class MAttributeValue extends X_M_AttributeValue
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4105427429027399512L;

	/**
	 * 	Constructor
	 *	@param ctx context
	 *	@param M_AttributeValue_ID id
	 *	@param trxName transaction
	 */
	public MAttributeValue (Properties ctx, int M_AttributeValue_ID, String trxName)
	{
		super (ctx, M_AttributeValue_ID, trxName);
		/** if (M_AttributeValue_ID == 0)
		{
		setM_AttributeValue_ID (0);
		setM_Attribute_ID (0);
		setName (null);
		setValue (null);
		}
		**/
	}	//	MAttributeValue

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAttributeValue (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAttributeValue

	/**
	 *	String Representation
	 * 	@return info
	 */
	// NOTE: on this method relly UI components like VPAttributeDialog
	@Override
	public String toString()
	{
		return getName();
	}	//	toString

}	//	MAttributeValue
