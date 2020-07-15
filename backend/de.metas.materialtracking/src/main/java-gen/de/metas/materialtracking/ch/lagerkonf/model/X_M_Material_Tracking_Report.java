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
package de.metas.materialtracking.ch.lagerkonf.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Material_Tracking_Report
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Material_Tracking_Report extends org.compiere.model.PO implements I_M_Material_Tracking_Report, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2063136126L;

    /** Standard Constructor */
    public X_M_Material_Tracking_Report (Properties ctx, int M_Material_Tracking_Report_ID, String trxName)
    {
      super (ctx, M_Material_Tracking_Report_ID, trxName);
      /** if (M_Material_Tracking_Report_ID == 0)
        {
			setC_Period_ID (0);
			setC_Year_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setM_Material_Tracking_Report_ID (0);
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_M_Material_Tracking_Report (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class);
	}

	@Override
	public void setC_Period(org.compiere.model.I_C_Period C_Period)
	{
		set_ValueFromPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class, C_Period);
	}

	/** Set Periode.
		@param C_Period_ID 
		Periode des Kalenders
	  */
	@Override
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Periode.
		@return Periode des Kalenders
	  */
	@Override
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Year_ID, org.compiere.model.I_C_Year.class);
	}

	@Override
	public void setC_Year(org.compiere.model.I_C_Year C_Year)
	{
		set_ValueFromPO(COLUMNNAME_C_Year_ID, org.compiere.model.I_C_Year.class, C_Year);
	}

	/** Set Jahr.
		@param C_Year_ID 
		Kalenderjahr
	  */
	@Override
	public void setC_Year_ID (int C_Year_ID)
	{
		if (C_Year_ID < 1) 
			set_Value (COLUMNNAME_C_Year_ID, null);
		else 
			set_Value (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
	}

	/** Get Jahr.
		@return Kalenderjahr
	  */
	@Override
	public int getC_Year_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Year_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Belegdatum.
		@param DateDoc 
		Datum des Belegs
	  */
	@Override
	public void setDateDoc (java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Belegdatum.
		@return Datum des Belegs
	  */
	@Override
	public java.sql.Timestamp getDateDoc () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set M_Material_Tracking_Report.
		@param M_Material_Tracking_Report_ID M_Material_Tracking_Report	  */
	@Override
	public void setM_Material_Tracking_Report_ID (int M_Material_Tracking_Report_ID)
	{
		if (M_Material_Tracking_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_Report_ID, Integer.valueOf(M_Material_Tracking_Report_ID));
	}

	/** Get M_Material_Tracking_Report.
		@return M_Material_Tracking_Report	  */
	@Override
	public int getM_Material_Tracking_Report_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Material_Tracking_Report_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}