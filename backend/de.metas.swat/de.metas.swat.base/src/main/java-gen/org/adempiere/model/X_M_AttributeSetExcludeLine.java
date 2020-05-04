/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.adempiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_AttributeSetExcludeLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_AttributeSetExcludeLine extends org.compiere.model.PO implements I_M_AttributeSetExcludeLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -770906073L;

    /** Standard Constructor */
    public X_M_AttributeSetExcludeLine (Properties ctx, int M_AttributeSetExcludeLine_ID, String trxName)
    {
      super (ctx, M_AttributeSetExcludeLine_ID, trxName);
      /** if (M_AttributeSetExcludeLine_ID == 0)
        {
			setM_Attribute_ID (0);
			setM_AttributeSetExcludeLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_AttributeSetExcludeLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_M_AttributeSetExcludeLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_M_Attribute getM_Attribute() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class);
	}

	@Override
	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class, M_Attribute);
	}

	/** Set Merkmal.
		@param M_Attribute_ID 
		Produkt-Merkmal
	  */
	@Override
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
	}

	/** Get Merkmal.
		@return Produkt-Merkmal
	  */
	@Override
	public int getM_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_AttributeSetExclude getM_AttributeSetExclude() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetExclude_ID, org.compiere.model.I_M_AttributeSetExclude.class);
	}

	@Override
	public void setM_AttributeSetExclude(org.compiere.model.I_M_AttributeSetExclude M_AttributeSetExclude)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetExclude_ID, org.compiere.model.I_M_AttributeSetExclude.class, M_AttributeSetExclude);
	}

	/** Set Exclude Attribute Set.
		@param M_AttributeSetExclude_ID 
		Exclude the ability to enter Attribute Sets
	  */
	@Override
	public void setM_AttributeSetExclude_ID (int M_AttributeSetExclude_ID)
	{
		if (M_AttributeSetExclude_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetExclude_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetExclude_ID, Integer.valueOf(M_AttributeSetExclude_ID));
	}

	/** Get Exclude Attribute Set.
		@return Exclude the ability to enter Attribute Sets
	  */
	@Override
	public int getM_AttributeSetExclude_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetExclude_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Exclude attribute.
		@param M_AttributeSetExcludeLine_ID 
		Only excludes selected attributes from the attribute set
	  */
	@Override
	public void setM_AttributeSetExcludeLine_ID (int M_AttributeSetExcludeLine_ID)
	{
		if (M_AttributeSetExcludeLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetExcludeLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetExcludeLine_ID, Integer.valueOf(M_AttributeSetExcludeLine_ID));
	}

	/** Get Exclude attribute.
		@return Only excludes selected attributes from the attribute set
	  */
	@Override
	public int getM_AttributeSetExcludeLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetExcludeLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}