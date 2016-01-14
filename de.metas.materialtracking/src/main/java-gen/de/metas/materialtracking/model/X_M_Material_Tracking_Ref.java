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
package de.metas.materialtracking.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_Material_Tracking_Ref
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Material_Tracking_Ref extends org.compiere.model.PO implements I_M_Material_Tracking_Ref, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 963553039L;

    /** Standard Constructor */
    public X_M_Material_Tracking_Ref (Properties ctx, int M_Material_Tracking_Ref_ID, String trxName)
    {
      super (ctx, M_Material_Tracking_Ref_ID, trxName);
      /** if (M_Material_Tracking_Ref_ID == 0)
        {
			setAD_Table_ID (0);
			setIsQualityInspectionDoc (false);
// N
			setM_Material_Tracking_ID (0);
			setM_Material_Tracking_Ref_ID (0);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Material_Tracking_Ref (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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

	/** Set Lieferdatum.
		@param DateDelivered 
		Datum, zu dem die Ware geliefert wurde
	  */
	@Override
	public void setDateDelivered (java.sql.Timestamp DateDelivered)
	{
		throw new IllegalArgumentException ("DateDelivered is virtual column");	}

	/** Get Lieferdatum.
		@return Datum, zu dem die Ware geliefert wurde
	  */
	@Override
	public java.sql.Timestamp getDateDelivered () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateDelivered);
	}

	/** Set Beleg Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		throw new IllegalArgumentException ("DocumentNo is virtual column");	}

	/** Get Beleg Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Quality Inspection Document.
		@param IsQualityInspectionDoc Quality Inspection Document	  */
	@Override
	public void setIsQualityInspectionDoc (boolean IsQualityInspectionDoc)
	{
		set_Value (COLUMNNAME_IsQualityInspectionDoc, Boolean.valueOf(IsQualityInspectionDoc));
	}

	/** Get Quality Inspection Document.
		@return Quality Inspection Document	  */
	@Override
	public boolean isQualityInspectionDoc () 
	{
		Object oo = get_Value(COLUMNNAME_IsQualityInspectionDoc);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public I_M_Material_Tracking getM_Material_Tracking() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Material_Tracking_ID, I_M_Material_Tracking.class);
	}

	@Override
	public void setM_Material_Tracking(I_M_Material_Tracking M_Material_Tracking)
	{
		set_ValueFromPO(COLUMNNAME_M_Material_Tracking_ID, I_M_Material_Tracking.class, M_Material_Tracking);
	}

	/** Set Material-Vorgang-ID.
		@param M_Material_Tracking_ID Material-Vorgang-ID	  */
	@Override
	public void setM_Material_Tracking_ID (int M_Material_Tracking_ID)
	{
		if (M_Material_Tracking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_ID, Integer.valueOf(M_Material_Tracking_ID));
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

	/** Set Material Tracking Reference.
		@param M_Material_Tracking_Ref_ID Material Tracking Reference	  */
	@Override
	public void setM_Material_Tracking_Ref_ID (int M_Material_Tracking_Ref_ID)
	{
		if (M_Material_Tracking_Ref_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_Ref_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_Ref_ID, Integer.valueOf(M_Material_Tracking_Ref_ID));
	}

	/** Get Material Tracking Reference.
		@return Material Tracking Reference	  */
	@Override
	public int getM_Material_Tracking_Ref_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Material_Tracking_Ref_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Qty Issued.
		@param QtyIssued Qty Issued	  */
	@Override
	public void setQtyIssued (java.math.BigDecimal QtyIssued)
	{
		set_Value (COLUMNNAME_QtyIssued, QtyIssued);
	}

	/** Get Qty Issued.
		@return Qty Issued	  */
	@Override
	public java.math.BigDecimal getQtyIssued () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyIssued);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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