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
package de.metas.invoicecandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Candidate_HeaderAggregation
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Invoice_Candidate_HeaderAggregation extends org.compiere.model.PO implements I_C_Invoice_Candidate_HeaderAggregation, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1317450643L;

    /** Standard Constructor */
    public X_C_Invoice_Candidate_HeaderAggregation (Properties ctx, int C_Invoice_Candidate_HeaderAggregation_ID, String trxName)
    {
      super (ctx, C_Invoice_Candidate_HeaderAggregation_ID, trxName);
      /** if (C_Invoice_Candidate_HeaderAggregation_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Invoice_Candidate_HeaderAggregation_ID (0);
			setHeaderAggregationKey (null);
			setInvoicingGroupNo (0);
			setIsSOTrx (false);
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_C_Invoice_Candidate_HeaderAggregation (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Abrechnungsgruppe.
		@param C_Invoice_Candidate_HeaderAggregation_ID Abrechnungsgruppe	  */
	@Override
	public void setC_Invoice_Candidate_HeaderAggregation_ID (int C_Invoice_Candidate_HeaderAggregation_ID)
	{
		if (C_Invoice_Candidate_HeaderAggregation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, Integer.valueOf(C_Invoice_Candidate_HeaderAggregation_ID));
	}

	/** Get Abrechnungsgruppe.
		@return Abrechnungsgruppe	  */
	@Override
	public int getC_Invoice_Candidate_HeaderAggregation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kopf-Aggregationsmerkmal.
		@param HeaderAggregationKey Kopf-Aggregationsmerkmal	  */
	@Override
	public void setHeaderAggregationKey (java.lang.String HeaderAggregationKey)
	{
		set_ValueNoCheck (COLUMNNAME_HeaderAggregationKey, HeaderAggregationKey);
	}

	/** Get Kopf-Aggregationsmerkmal.
		@return Kopf-Aggregationsmerkmal	  */
	@Override
	public java.lang.String getHeaderAggregationKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HeaderAggregationKey);
	}

	@Override
	public de.metas.aggregation.model.I_C_Aggregation getHeaderAggregationKeyBuilder() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_HeaderAggregationKeyBuilder_ID, de.metas.aggregation.model.I_C_Aggregation.class);
	}

	@Override
	public void setHeaderAggregationKeyBuilder(de.metas.aggregation.model.I_C_Aggregation HeaderAggregationKeyBuilder)
	{
		set_ValueFromPO(COLUMNNAME_HeaderAggregationKeyBuilder_ID, de.metas.aggregation.model.I_C_Aggregation.class, HeaderAggregationKeyBuilder);
	}

	/** Set Header aggregation builder.
		@param HeaderAggregationKeyBuilder_ID Header aggregation builder	  */
	@Override
	public void setHeaderAggregationKeyBuilder_ID (int HeaderAggregationKeyBuilder_ID)
	{
		if (HeaderAggregationKeyBuilder_ID < 1) 
			set_Value (COLUMNNAME_HeaderAggregationKeyBuilder_ID, null);
		else 
			set_Value (COLUMNNAME_HeaderAggregationKeyBuilder_ID, Integer.valueOf(HeaderAggregationKeyBuilder_ID));
	}

	/** Get Header aggregation builder.
		@return Header aggregation builder	  */
	@Override
	public int getHeaderAggregationKeyBuilder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HeaderAggregationKeyBuilder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Abrechnungsgruppe.
		@param InvoicingGroupNo 
		Dev-Hinweis: Werte werden per DB-Trigger-Funktion gesetzt!
	  */
	@Override
	public void setInvoicingGroupNo (int InvoicingGroupNo)
	{
		set_ValueNoCheck (COLUMNNAME_InvoicingGroupNo, Integer.valueOf(InvoicingGroupNo));
	}

	/** Get Abrechnungsgruppe.
		@return Dev-Hinweis: Werte werden per DB-Trigger-Funktion gesetzt!
	  */
	@Override
	public int getInvoicingGroupNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_InvoicingGroupNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	@Override
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_ValueNoCheck (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	@Override
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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