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
package de.metas.ordercandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_Order_Line_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Order_Line_Alloc extends org.compiere.model.PO implements I_C_Order_Line_Alloc, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -209983382L;

    /** Standard Constructor */
    public X_C_Order_Line_Alloc (Properties ctx, int C_Order_Line_Alloc_ID, String trxName)
    {
      super (ctx, C_Order_Line_Alloc_ID, trxName);
      /** if (C_Order_Line_Alloc_ID == 0)
        {
			setC_OLCand_ID (0);
			setC_Order_Line_Alloc_ID (0);
			setC_OrderLine_ID (0);
			setQtyOrdered (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_C_Order_Line_Alloc (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.ordercandidate.model.I_C_OLCand getC_OLCand() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OLCand_ID, de.metas.ordercandidate.model.I_C_OLCand.class);
	}

	@Override
	public void setC_OLCand(de.metas.ordercandidate.model.I_C_OLCand C_OLCand)
	{
		set_ValueFromPO(COLUMNNAME_C_OLCand_ID, de.metas.ordercandidate.model.I_C_OLCand.class, C_OLCand);
	}

	/** Set Auftragskandidat.
		@param C_OLCand_ID Auftragskandidat	  */
	@Override
	public void setC_OLCand_ID (int C_OLCand_ID)
	{
		if (C_OLCand_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_ID, Integer.valueOf(C_OLCand_ID));
	}

	/** Get Auftragskandidat.
		@return Auftragskandidat	  */
	@Override
	public int getC_OLCand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OLCand_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.ordercandidate.model.I_C_OLCandProcessor getC_OLCandProcessor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OLCandProcessor_ID, de.metas.ordercandidate.model.I_C_OLCandProcessor.class);
	}

	@Override
	public void setC_OLCandProcessor(de.metas.ordercandidate.model.I_C_OLCandProcessor C_OLCandProcessor)
	{
		set_ValueFromPO(COLUMNNAME_C_OLCandProcessor_ID, de.metas.ordercandidate.model.I_C_OLCandProcessor.class, C_OLCandProcessor);
	}

	/** Set Auftragskand. Verarb..
		@param C_OLCandProcessor_ID Auftragskand. Verarb.	  */
	@Override
	public void setC_OLCandProcessor_ID (int C_OLCandProcessor_ID)
	{
		if (C_OLCandProcessor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCandProcessor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCandProcessor_ID, Integer.valueOf(C_OLCandProcessor_ID));
	}

	/** Get Auftragskand. Verarb..
		@return Auftragskand. Verarb.	  */
	@Override
	public int getC_OLCandProcessor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OLCandProcessor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Auftragskandidat - Auftragszeile.
		@param C_Order_Line_Alloc_ID Auftragskandidat - Auftragszeile	  */
	@Override
	public void setC_Order_Line_Alloc_ID (int C_Order_Line_Alloc_ID)
	{
		if (C_Order_Line_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_Line_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_Line_Alloc_ID, Integer.valueOf(C_Order_Line_Alloc_ID));
	}

	/** Get Auftragskandidat - Auftragszeile.
		@return Auftragskandidat - Auftragszeile	  */
	@Override
	public int getC_Order_Line_Alloc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_Line_Alloc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	/** Set Auftragsposition.
		@param C_OrderLine_ID 
		Auftragsposition
	  */
	@Override
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Auftragsposition.
		@return Auftragsposition
	  */
	@Override
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		throw new IllegalArgumentException ("DocStatus is virtual column");	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Bestellte Menge.
		@param QtyOrdered 
		Bestellte Menge
	  */
	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Bestellte Menge.
		@return Bestellte Menge
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}