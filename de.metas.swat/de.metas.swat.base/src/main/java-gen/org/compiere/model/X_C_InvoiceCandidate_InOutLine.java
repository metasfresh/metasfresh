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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_InvoiceCandidate_InOutLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_InvoiceCandidate_InOutLine extends org.compiere.model.PO implements I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -571797059L;

    /** Standard Constructor */
    public X_C_InvoiceCandidate_InOutLine (Properties ctx, int C_InvoiceCandidate_InOutLine_ID, String trxName)
    {
      super (ctx, C_InvoiceCandidate_InOutLine_ID, trxName);
      /** if (C_InvoiceCandidate_InOutLine_ID == 0)
        {
			setC_InvoiceCandidate_InOutLine_ID (0);
			setQtyInvoiced (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_C_InvoiceCandidate_InOutLine (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate getC_Invoice_Candidate() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);
	}

	@Override
	public void setC_Invoice_Candidate(de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class, C_Invoice_Candidate);
	}

	/** Set Rechnungskandidat.
		@param C_Invoice_Candidate_ID 
		Eindeutige Identifikationsnummer eines Rechnungskandidaten
	  */
	@Override
	public void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID)
	{
		if (C_Invoice_Candidate_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, Integer.valueOf(C_Invoice_Candidate_ID));
	}

	/** Get Rechnungskandidat.
		@return Eindeutige Identifikationsnummer eines Rechnungskandidaten
	  */
	@Override
	public int getC_Invoice_Candidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_InvoiceCandidate_InOutLine.
		@param C_InvoiceCandidate_InOutLine_ID C_InvoiceCandidate_InOutLine	  */
	@Override
	public void setC_InvoiceCandidate_InOutLine_ID (int C_InvoiceCandidate_InOutLine_ID)
	{
		if (C_InvoiceCandidate_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceCandidate_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceCandidate_InOutLine_ID, Integer.valueOf(C_InvoiceCandidate_InOutLine_ID));
	}

	/** Get C_InvoiceCandidate_InOutLine.
		@return C_InvoiceCandidate_InOutLine	  */
	@Override
	public int getC_InvoiceCandidate_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceCandidate_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lieferung/ Wareneingang freigeben.
		@param IsInOutApprovedForInvoicing Lieferung/ Wareneingang freigeben	  */
	@Override
	public void setIsInOutApprovedForInvoicing (boolean IsInOutApprovedForInvoicing)
	{
		throw new IllegalArgumentException ("IsInOutApprovedForInvoicing is virtual column");	}

	/** Get Lieferung/ Wareneingang freigeben.
		@return Lieferung/ Wareneingang freigeben	  */
	@Override
	public boolean isInOutApprovedForInvoicing () 
	{
		Object oo = get_Value(COLUMNNAME_IsInOutApprovedForInvoicing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
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

	/** Set Gelieferte Menge.
		@param QtyDelivered 
		Gelieferte Menge
	  */
	@Override
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered)
	{
		throw new IllegalArgumentException ("QtyDelivered is virtual column");	}

	/** Get Gelieferte Menge.
		@return Gelieferte Menge
	  */
	@Override
	public java.math.BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Berechn. Menge.
		@param QtyInvoiced 
		Menge, die bereits in Rechnung gestellt wurde
	  */
	@Override
	public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Berechn. Menge.
		@return Menge, die bereits in Rechnung gestellt wurde
	  */
	@Override
	public java.math.BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}