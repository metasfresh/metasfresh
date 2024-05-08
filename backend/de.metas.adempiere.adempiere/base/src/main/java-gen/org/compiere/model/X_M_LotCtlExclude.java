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

/** Generated Model for M_LotCtlExclude
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_LotCtlExclude extends PO implements I_M_LotCtlExclude, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_M_LotCtlExclude (Properties ctx, int M_LotCtlExclude_ID, String trxName)
    {
      super (ctx, M_LotCtlExclude_ID, trxName);
      /** if (M_LotCtlExclude_ID == 0)
        {
			setAD_Table_ID (0);
			setIsSOTrx (false);
			setM_LotCtlExclude_ID (0);
			setM_LotCtl_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_LotCtlExclude (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_LotCtlExclude[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Exclude Lot.
		@param M_LotCtlExclude_ID 
		Exclude the ability to create Lots in Attribute Sets
	  */
	public void setM_LotCtlExclude_ID (int M_LotCtlExclude_ID)
	{
		if (M_LotCtlExclude_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_LotCtlExclude_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_LotCtlExclude_ID, Integer.valueOf(M_LotCtlExclude_ID));
	}

	/** Get Exclude Lot.
		@return Exclude the ability to create Lots in Attribute Sets
	  */
	public int getM_LotCtlExclude_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_LotCtlExclude_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_LotCtl getM_LotCtl() throws RuntimeException
    {
		return (I_M_LotCtl)MTable.get(getCtx(), I_M_LotCtl.Table_Name)
			.getPO(getM_LotCtl_ID(), get_TrxName());	}

	/** Set Lot Control.
		@param M_LotCtl_ID 
		Product Lot Control
	  */
	public void setM_LotCtl_ID (int M_LotCtl_ID)
	{
		if (M_LotCtl_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_LotCtl_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_LotCtl_ID, Integer.valueOf(M_LotCtl_ID));
	}

	/** Get Lot Control.
		@return Product Lot Control
	  */
	public int getM_LotCtl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_LotCtl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}