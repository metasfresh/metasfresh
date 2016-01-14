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

/** Generated Model for M_Material_Tracking_Report_Line_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Material_Tracking_Report_Line_Alloc extends org.compiere.model.PO implements I_M_Material_Tracking_Report_Line_Alloc, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1071389059L;

    /** Standard Constructor */
    public X_M_Material_Tracking_Report_Line_Alloc (Properties ctx, int M_Material_Tracking_Report_Line_Alloc_ID, String trxName)
    {
      super (ctx, M_Material_Tracking_Report_Line_Alloc_ID, trxName);
      /** if (M_Material_Tracking_Report_Line_Alloc_ID == 0)
        {
			setM_Material_Tracking_Report_Line_Alloc_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Material_Tracking_Report_Line_Alloc (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.materialtracking.model.I_M_Material_Tracking getM_Material_Tracking() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Material_Tracking_ID, de.metas.materialtracking.model.I_M_Material_Tracking.class);
	}

	@Override
	public void setM_Material_Tracking(de.metas.materialtracking.model.I_M_Material_Tracking M_Material_Tracking)
	{
		set_ValueFromPO(COLUMNNAME_M_Material_Tracking_ID, de.metas.materialtracking.model.I_M_Material_Tracking.class, M_Material_Tracking);
	}

	/** Set Material-Vorgang-ID.
		@param M_Material_Tracking_ID Material-Vorgang-ID	  */
	@Override
	public void setM_Material_Tracking_ID (int M_Material_Tracking_ID)
	{
		if (M_Material_Tracking_ID < 1) 
			set_Value (COLUMNNAME_M_Material_Tracking_ID, null);
		else 
			set_Value (COLUMNNAME_M_Material_Tracking_ID, Integer.valueOf(M_Material_Tracking_ID));
	}

	/** Get Material-Vorgang-ID.
		@return Material-Vorgang-ID	  */
	@Override
	public int getM_Material_Tracking_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Material_Tracking_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_Material_Tracking_Report_Line_Alloc.
		@param M_Material_Tracking_Report_Line_Alloc_ID M_Material_Tracking_Report_Line_Alloc	  */
	@Override
	public void setM_Material_Tracking_Report_Line_Alloc_ID (int M_Material_Tracking_Report_Line_Alloc_ID)
	{
		if (M_Material_Tracking_Report_Line_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_Report_Line_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_Report_Line_Alloc_ID, Integer.valueOf(M_Material_Tracking_Report_Line_Alloc_ID));
	}

	/** Get M_Material_Tracking_Report_Line_Alloc.
		@return M_Material_Tracking_Report_Line_Alloc	  */
	@Override
	public int getM_Material_Tracking_Report_Line_Alloc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Material_Tracking_Report_Line_Alloc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line getM_Material_Tracking_Report_Line() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Material_Tracking_Report_Line_ID, de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line.class);
	}

	@Override
	public void setM_Material_Tracking_Report_Line(de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line M_Material_Tracking_Report_Line)
	{
		set_ValueFromPO(COLUMNNAME_M_Material_Tracking_Report_Line_ID, de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line.class, M_Material_Tracking_Report_Line);
	}

	/** Set M_Material_Tracking_Report_Line.
		@param M_Material_Tracking_Report_Line_ID M_Material_Tracking_Report_Line	  */
	@Override
	public void setM_Material_Tracking_Report_Line_ID (int M_Material_Tracking_Report_Line_ID)
	{
		if (M_Material_Tracking_Report_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_Report_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_Report_Line_ID, Integer.valueOf(M_Material_Tracking_Report_Line_ID));
	}

	/** Get M_Material_Tracking_Report_Line.
		@return M_Material_Tracking_Report_Line	  */
	@Override
	public int getM_Material_Tracking_Report_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Material_Tracking_Report_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}