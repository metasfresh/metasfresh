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
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_ReceiptSchedule_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ReceiptSchedule_Alloc extends org.compiere.model.PO implements I_M_ReceiptSchedule_Alloc, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1247388279L;

    /** Standard Constructor */
    public X_M_ReceiptSchedule_Alloc (Properties ctx, int M_ReceiptSchedule_Alloc_ID, String trxName)
    {
      super (ctx, M_ReceiptSchedule_Alloc_ID, trxName);
      /** if (M_ReceiptSchedule_Alloc_ID == 0)
        {
			setM_ReceiptSchedule_Alloc_ID (0);
			setM_ReceiptSchedule_ID (0);
			setQtyWithIssues (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_M_ReceiptSchedule_Alloc (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_ReceiptSchedule_Alloc[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	@Override
	public void setM_InOut_ID (int M_InOut_ID)
	{
		throw new IllegalArgumentException ("M_InOut_ID is virtual column");	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	@Override
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	/** Set Versand-/Wareneingangsposition.
		@param M_InOutLine_ID 
		Position auf Versand- oder Wareneingangsbeleg
	  */
	@Override
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Versand-/Wareneingangsposition.
		@return Position auf Versand- oder Wareneingangsbeleg
	  */
	@Override
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Wareneingangsdispo - Wareneingangszeile.
		@param M_ReceiptSchedule_Alloc_ID Wareneingangsdispo - Wareneingangszeile	  */
	@Override
	public void setM_ReceiptSchedule_Alloc_ID (int M_ReceiptSchedule_Alloc_ID)
	{
		if (M_ReceiptSchedule_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_Alloc_ID, Integer.valueOf(M_ReceiptSchedule_Alloc_ID));
	}

	/** Get Wareneingangsdispo - Wareneingangszeile.
		@return Wareneingangsdispo - Wareneingangszeile	  */
	@Override
	public int getM_ReceiptSchedule_Alloc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ReceiptSchedule_Alloc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_ReceiptSchedule getM_ReceiptSchedule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_ReceiptSchedule_ID, de.metas.inoutcandidate.model.I_M_ReceiptSchedule.class);
	}

	@Override
	public void setM_ReceiptSchedule(de.metas.inoutcandidate.model.I_M_ReceiptSchedule M_ReceiptSchedule)
	{
		set_ValueFromPO(COLUMNNAME_M_ReceiptSchedule_ID, de.metas.inoutcandidate.model.I_M_ReceiptSchedule.class, M_ReceiptSchedule);
	}

	/** Set Wareneingangsdisposition.
		@param M_ReceiptSchedule_ID Wareneingangsdisposition	  */
	@Override
	public void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID)
	{
		if (M_ReceiptSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, Integer.valueOf(M_ReceiptSchedule_ID));
	}

	/** Get Wareneingangsdisposition.
		@return Wareneingangsdisposition	  */
	@Override
	public int getM_ReceiptSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ReceiptSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zugewiesene Menge.
		@param QtyAllocated Zugewiesene Menge	  */
	@Override
	public void setQtyAllocated (java.math.BigDecimal QtyAllocated)
	{
		set_ValueNoCheck (COLUMNNAME_QtyAllocated, QtyAllocated);
	}

	/** Get Zugewiesene Menge.
		@return Zugewiesene Menge	  */
	@Override
	public java.math.BigDecimal getQtyAllocated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAllocated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyWithIssues.
		@param QtyWithIssues QtyWithIssues	  */
	@Override
	public void setQtyWithIssues (java.math.BigDecimal QtyWithIssues)
	{
		set_ValueNoCheck (COLUMNNAME_QtyWithIssues, QtyWithIssues);
	}

	/** Get QtyWithIssues.
		@return QtyWithIssues	  */
	@Override
	public java.math.BigDecimal getQtyWithIssues () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyWithIssues);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qualitätsabzug %.
		@param QualityDiscountPercent Qualitätsabzug %	  */
	@Override
	public void setQualityDiscountPercent (java.math.BigDecimal QualityDiscountPercent)
	{
		throw new IllegalArgumentException ("QualityDiscountPercent is virtual column");	}

	/** Get Qualitätsabzug %.
		@return Qualitätsabzug %	  */
	@Override
	public java.math.BigDecimal getQualityDiscountPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QualityDiscountPercent);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qualität-Notiz.
		@param QualityNote Qualität-Notiz	  */
	@Override
	public void setQualityNote (java.lang.String QualityNote)
	{
		throw new IllegalArgumentException ("QualityNote is virtual column");	}

	/** Get Qualität-Notiz.
		@return Qualität-Notiz	  */
	@Override
	public java.lang.String getQualityNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_QualityNote);
	}
}