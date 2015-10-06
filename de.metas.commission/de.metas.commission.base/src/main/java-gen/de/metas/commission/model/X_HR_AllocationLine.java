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
package de.metas.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

/** Generated Model for HR_AllocationLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_HR_AllocationLine extends PO implements I_HR_AllocationLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_HR_AllocationLine (Properties ctx, int HR_AllocationLine_ID, String trxName)
    {
      super (ctx, HR_AllocationLine_ID, trxName);
      /** if (HR_AllocationLine_ID == 0)
        {
			setAD_Table_ID (0);
			setHR_AllocationLine_ID (0);
			setHR_Movement_ID (0);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HR_AllocationLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_HR_AllocationLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Betrag.
		@param Amount 
		Betrag in einer definierten Währung
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_ValueNoCheck (COLUMNNAME_Amount, Amount);
	}

	/** Get Betrag.
		@return Betrag in einer definierten Währung
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Belegdatum.
		@param DateDoc 
		Datum des Belegs
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_ValueNoCheck (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Belegdatum.
		@return Datum des Belegs
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Payroll Movement Allocation Line.
		@param HR_AllocationLine_ID Payroll Movement Allocation Line	  */
	public void setHR_AllocationLine_ID (int HR_AllocationLine_ID)
	{
		if (HR_AllocationLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_AllocationLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_AllocationLine_ID, Integer.valueOf(HR_AllocationLine_ID));
	}

	/** Get Payroll Movement Allocation Line.
		@return Payroll Movement Allocation Line	  */
	public int getHR_AllocationLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_AllocationLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_HR_Movement getHR_Movement() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Movement)MTable.get(getCtx(), org.eevolution.model.I_HR_Movement.Table_Name)
			.getPO(getHR_Movement_ID(), get_TrxName());	}

	/** Set Payroll Movement.
		@param HR_Movement_ID Payroll Movement	  */
	public void setHR_Movement_ID (int HR_Movement_ID)
	{
		if (HR_Movement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HR_Movement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HR_Movement_ID, Integer.valueOf(HR_Movement_ID));
	}

	/** Get Payroll Movement.
		@return Payroll Movement	  */
	public int getHR_Movement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Movement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}