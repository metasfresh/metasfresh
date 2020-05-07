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

/** Generated Model for A_Asset_Info_Oth
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_A_Asset_Info_Oth extends PO implements I_A_Asset_Info_Oth, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_A_Asset_Info_Oth (Properties ctx, int A_Asset_Info_Oth_ID, String trxName)
    {
      super (ctx, A_Asset_Info_Oth_ID, trxName);
      /** if (A_Asset_Info_Oth_ID == 0)
        {
			setA_Asset_ID (0);
			setA_Asset_Info_Oth_ID (0);
        } */
    }

    /** Load Constructor */
    public X_A_Asset_Info_Oth (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_A_Asset_Info_Oth[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set A_Asset_Info_Oth_ID.
		@param A_Asset_Info_Oth_ID A_Asset_Info_Oth_ID	  */
	public void setA_Asset_Info_Oth_ID (int A_Asset_Info_Oth_ID)
	{
		if (A_Asset_Info_Oth_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_Info_Oth_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_Info_Oth_ID, Integer.valueOf(A_Asset_Info_Oth_ID));
	}

	/** Get A_Asset_Info_Oth_ID.
		@return A_Asset_Info_Oth_ID	  */
	public int getA_Asset_Info_Oth_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Info_Oth_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set A_User1.
		@param A_User1 A_User1	  */
	public void setA_User1 (String A_User1)
	{
		set_Value (COLUMNNAME_A_User1, A_User1);
	}

	/** Get A_User1.
		@return A_User1	  */
	public String getA_User1 () 
	{
		return (String)get_Value(COLUMNNAME_A_User1);
	}

	/** Set A_User10.
		@param A_User10 A_User10	  */
	public void setA_User10 (String A_User10)
	{
		set_Value (COLUMNNAME_A_User10, A_User10);
	}

	/** Get A_User10.
		@return A_User10	  */
	public String getA_User10 () 
	{
		return (String)get_Value(COLUMNNAME_A_User10);
	}

	/** Set A_User11.
		@param A_User11 A_User11	  */
	public void setA_User11 (String A_User11)
	{
		set_Value (COLUMNNAME_A_User11, A_User11);
	}

	/** Get A_User11.
		@return A_User11	  */
	public String getA_User11 () 
	{
		return (String)get_Value(COLUMNNAME_A_User11);
	}

	/** Set A_User12.
		@param A_User12 A_User12	  */
	public void setA_User12 (String A_User12)
	{
		set_Value (COLUMNNAME_A_User12, A_User12);
	}

	/** Get A_User12.
		@return A_User12	  */
	public String getA_User12 () 
	{
		return (String)get_Value(COLUMNNAME_A_User12);
	}

	/** Set A_User13.
		@param A_User13 A_User13	  */
	public void setA_User13 (String A_User13)
	{
		set_Value (COLUMNNAME_A_User13, A_User13);
	}

	/** Get A_User13.
		@return A_User13	  */
	public String getA_User13 () 
	{
		return (String)get_Value(COLUMNNAME_A_User13);
	}

	/** Set A_User14.
		@param A_User14 A_User14	  */
	public void setA_User14 (String A_User14)
	{
		set_Value (COLUMNNAME_A_User14, A_User14);
	}

	/** Get A_User14.
		@return A_User14	  */
	public String getA_User14 () 
	{
		return (String)get_Value(COLUMNNAME_A_User14);
	}

	/** Set A_User15.
		@param A_User15 A_User15	  */
	public void setA_User15 (String A_User15)
	{
		set_Value (COLUMNNAME_A_User15, A_User15);
	}

	/** Get A_User15.
		@return A_User15	  */
	public String getA_User15 () 
	{
		return (String)get_Value(COLUMNNAME_A_User15);
	}

	/** Set A_User2.
		@param A_User2 A_User2	  */
	public void setA_User2 (String A_User2)
	{
		set_Value (COLUMNNAME_A_User2, A_User2);
	}

	/** Get A_User2.
		@return A_User2	  */
	public String getA_User2 () 
	{
		return (String)get_Value(COLUMNNAME_A_User2);
	}

	/** Set A_User3.
		@param A_User3 A_User3	  */
	public void setA_User3 (String A_User3)
	{
		set_Value (COLUMNNAME_A_User3, A_User3);
	}

	/** Get A_User3.
		@return A_User3	  */
	public String getA_User3 () 
	{
		return (String)get_Value(COLUMNNAME_A_User3);
	}

	/** Set A_User4.
		@param A_User4 A_User4	  */
	public void setA_User4 (String A_User4)
	{
		set_Value (COLUMNNAME_A_User4, A_User4);
	}

	/** Get A_User4.
		@return A_User4	  */
	public String getA_User4 () 
	{
		return (String)get_Value(COLUMNNAME_A_User4);
	}

	/** Set A_User5.
		@param A_User5 A_User5	  */
	public void setA_User5 (String A_User5)
	{
		set_Value (COLUMNNAME_A_User5, A_User5);
	}

	/** Get A_User5.
		@return A_User5	  */
	public String getA_User5 () 
	{
		return (String)get_Value(COLUMNNAME_A_User5);
	}

	/** Set A_User6.
		@param A_User6 A_User6	  */
	public void setA_User6 (String A_User6)
	{
		set_Value (COLUMNNAME_A_User6, A_User6);
	}

	/** Get A_User6.
		@return A_User6	  */
	public String getA_User6 () 
	{
		return (String)get_Value(COLUMNNAME_A_User6);
	}

	/** Set A_User7.
		@param A_User7 A_User7	  */
	public void setA_User7 (String A_User7)
	{
		set_Value (COLUMNNAME_A_User7, A_User7);
	}

	/** Get A_User7.
		@return A_User7	  */
	public String getA_User7 () 
	{
		return (String)get_Value(COLUMNNAME_A_User7);
	}

	/** Set A_User8.
		@param A_User8 A_User8	  */
	public void setA_User8 (String A_User8)
	{
		set_Value (COLUMNNAME_A_User8, A_User8);
	}

	/** Get A_User8.
		@return A_User8	  */
	public String getA_User8 () 
	{
		return (String)get_Value(COLUMNNAME_A_User8);
	}

	/** Set A_User9.
		@param A_User9 A_User9	  */
	public void setA_User9 (String A_User9)
	{
		set_Value (COLUMNNAME_A_User9, A_User9);
	}

	/** Get A_User9.
		@return A_User9	  */
	public String getA_User9 () 
	{
		return (String)get_Value(COLUMNNAME_A_User9);
	}

	/** Set Text.
		@param Text Text	  */
	public void setText (String Text)
	{
		set_Value (COLUMNNAME_Text, Text);
	}

	/** Get Text.
		@return Text	  */
	public String getText () 
	{
		return (String)get_Value(COLUMNNAME_Text);
	}
}