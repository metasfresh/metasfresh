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
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for GL_FundRestriction
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_GL_FundRestriction extends PO implements I_GL_FundRestriction, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_GL_FundRestriction (Properties ctx, int GL_FundRestriction_ID, String trxName)
    {
      super (ctx, GL_FundRestriction_ID, trxName);
      /** if (GL_FundRestriction_ID == 0)
        {
			setC_ElementValue_ID (0);
			setGL_Fund_ID (0);
			setGL_FundRestriction_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_GL_FundRestriction (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_GL_FundRestriction[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (I_C_ElementValue)MTable.get(getCtx(), I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	public I_GL_Fund getGL_Fund() throws RuntimeException
    {
		return (I_GL_Fund)MTable.get(getCtx(), I_GL_Fund.Table_Name)
			.getPO(getGL_Fund_ID(), get_TrxName());	}

	/** Set GL Fund.
		@param GL_Fund_ID 
		General Ledger Funds Control
	  */
	public void setGL_Fund_ID (int GL_Fund_ID)
	{
		if (GL_Fund_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_Fund_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_Fund_ID, Integer.valueOf(GL_Fund_ID));
	}

	/** Get GL Fund.
		@return General Ledger Funds Control
	  */
	public int getGL_Fund_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Fund_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fund Restriction.
		@param GL_FundRestriction_ID 
		Restriction of Funds
	  */
	public void setGL_FundRestriction_ID (int GL_FundRestriction_ID)
	{
		if (GL_FundRestriction_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_FundRestriction_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_FundRestriction_ID, Integer.valueOf(GL_FundRestriction_ID));
	}

	/** Get Fund Restriction.
		@return Restriction of Funds
	  */
	public int getGL_FundRestriction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_FundRestriction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }
}