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

/** Generated Model for GL_Budget
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_GL_Budget extends PO implements I_GL_Budget, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_GL_Budget (Properties ctx, int GL_Budget_ID, String trxName)
    {
      super (ctx, GL_Budget_ID, trxName);
      /** if (GL_Budget_ID == 0)
        {
			setGL_Budget_ID (0);
			setIsPrimary (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_GL_Budget (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_GL_Budget[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** BudgetStatus AD_Reference_ID=178 */
	public static final int BUDGETSTATUS_AD_Reference_ID=178;
	/** Draft = D */
	public static final String BUDGETSTATUS_Draft = "D";
	/** Approved = A */
	public static final String BUDGETSTATUS_Approved = "A";
	/** Set Budget Status.
		@param BudgetStatus 
		Indicates the current status of this budget
	  */
	public void setBudgetStatus (String BudgetStatus)
	{

		set_Value (COLUMNNAME_BudgetStatus, BudgetStatus);
	}

	/** Get Budget Status.
		@return Indicates the current status of this budget
	  */
	public String getBudgetStatus () 
	{
		return (String)get_Value(COLUMNNAME_BudgetStatus);
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

	/** Set Budget.
		@param GL_Budget_ID 
		General Ledger Budget
	  */
	public void setGL_Budget_ID (int GL_Budget_ID)
	{
		if (GL_Budget_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_Budget_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_Budget_ID, Integer.valueOf(GL_Budget_ID));
	}

	/** Get Budget.
		@return General Ledger Budget
	  */
	public int getGL_Budget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Budget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Primary.
		@param IsPrimary 
		Indicates if this is the primary budget
	  */
	public void setIsPrimary (boolean IsPrimary)
	{
		set_Value (COLUMNNAME_IsPrimary, Boolean.valueOf(IsPrimary));
	}

	/** Get Primary.
		@return Indicates if this is the primary budget
	  */
	public boolean isPrimary () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrimary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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