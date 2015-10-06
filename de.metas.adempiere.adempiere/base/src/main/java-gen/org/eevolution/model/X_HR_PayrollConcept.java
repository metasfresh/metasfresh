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
package org.eevolution.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for HR_PayrollConcept
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_HR_PayrollConcept extends PO implements I_HR_PayrollConcept, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_HR_PayrollConcept (Properties ctx, int HR_PayrollConcept_ID, String trxName)
    {
      super (ctx, HR_PayrollConcept_ID, trxName);
      /** if (HR_PayrollConcept_ID == 0)
        {
			setHR_Concept_ID (0);
			setHR_PayrollConcept_ID (0);
			setHR_Payroll_ID (0);
			setIsPrinted (false);
// N
        } */
    }

    /** Load Constructor */
    public X_HR_PayrollConcept (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_PayrollConcept[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Rule getAD_Rule() throws RuntimeException
    {
		return (I_AD_Rule)MTable.get(getCtx(), I_AD_Rule.Table_Name)
			.getPO(getAD_Rule_ID(), get_TrxName());	}

	/** Set Rule.
		@param AD_Rule_ID Rule	  */
	public void setAD_Rule_ID (int AD_Rule_ID)
	{
		if (AD_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Rule_ID, Integer.valueOf(AD_Rule_ID));
	}

	/** Get Rule.
		@return Rule	  */
	public int getAD_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Rule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_HR_Concept getHR_Concept() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(getHR_Concept_ID(), get_TrxName());	}

	/** Set Payroll Concept.
		@param HR_Concept_ID Payroll Concept	  */
	public void setHR_Concept_ID (int HR_Concept_ID)
	{
		if (HR_Concept_ID < 1) 
			set_Value (COLUMNNAME_HR_Concept_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Concept_ID, Integer.valueOf(HR_Concept_ID));
	}

	/** Get Payroll Concept.
		@return Payroll Concept	  */
	public int getHR_Concept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Concept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payroll Concept.
		@param HR_PayrollConcept_ID Payroll Concept	  */
	public void setHR_PayrollConcept_ID (int HR_PayrollConcept_ID)
	{
		if (HR_PayrollConcept_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_PayrollConcept_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_PayrollConcept_ID, Integer.valueOf(HR_PayrollConcept_ID));
	}

	/** Get Payroll Concept.
		@return Payroll Concept	  */
	public int getHR_PayrollConcept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_PayrollConcept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_HR_Payroll getHR_Payroll() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Payroll)MTable.get(getCtx(), org.eevolution.model.I_HR_Payroll.Table_Name)
			.getPO(getHR_Payroll_ID(), get_TrxName());	}

	/** Set Payroll.
		@param HR_Payroll_ID Payroll	  */
	public void setHR_Payroll_ID (int HR_Payroll_ID)
	{
		if (HR_Payroll_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_Payroll_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_Payroll_ID, Integer.valueOf(HR_Payroll_ID));
	}

	/** Get Payroll.
		@return Payroll	  */
	public int getHR_Payroll_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Payroll_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Displayed.
		@param IsDisplayed 
		Determines, if this field is displayed
	  */
	public void setIsDisplayed (boolean IsDisplayed)
	{
		set_Value (COLUMNNAME_IsDisplayed, Boolean.valueOf(IsDisplayed));
	}

	/** Get Displayed.
		@return Determines, if this field is displayed
	  */
	public boolean isDisplayed () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisplayed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Included.
		@param IsInclude 
		Defines whether this content / template is included into another one
	  */
	public void setIsInclude (boolean IsInclude)
	{
		set_Value (COLUMNNAME_IsInclude, Boolean.valueOf(IsInclude));
	}

	/** Get Included.
		@return Defines whether this content / template is included into another one
	  */
	public boolean isInclude () 
	{
		Object oo = get_Value(COLUMNNAME_IsInclude);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Printed.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get Printed.
		@return Indicates if this document / line is printed
	  */
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
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

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}