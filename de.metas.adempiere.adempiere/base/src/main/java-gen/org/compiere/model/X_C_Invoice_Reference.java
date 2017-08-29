/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Reference
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Invoice_Reference extends org.compiere.model.PO implements I_C_Invoice_Reference, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1154487117L;

    /** Standard Constructor */
    public X_C_Invoice_Reference (Properties ctx, int C_Invoice_Reference_ID, String trxName)
    {
      super (ctx, C_Invoice_Reference_ID, trxName);
      /** if (C_Invoice_Reference_ID == 0)
        {
			setC_Invoice_Reference_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Invoice_Reference (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	/** Set Rechnung.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Rechnung.
		@return Invoice Identifier
	  */
	@Override
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice_Linked() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Linked_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice_Linked(org.compiere.model.I_C_Invoice C_Invoice_Linked)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Linked_ID, org.compiere.model.I_C_Invoice.class, C_Invoice_Linked);
	}

	/** Set C_Invoice_Linked_ID.
		@param C_Invoice_Linked_ID C_Invoice_Linked_ID	  */
	@Override
	public void setC_Invoice_Linked_ID (int C_Invoice_Linked_ID)
	{
		if (C_Invoice_Linked_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Linked_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Linked_ID, Integer.valueOf(C_Invoice_Linked_ID));
	}

	/** Get C_Invoice_Linked_ID.
		@return C_Invoice_Linked_ID	  */
	@Override
	public int getC_Invoice_Linked_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Linked_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Invoice_Reference.
		@param C_Invoice_Reference_ID C_Invoice_Reference	  */
	@Override
	public void setC_Invoice_Reference_ID (int C_Invoice_Reference_ID)
	{
		if (C_Invoice_Reference_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Reference_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Reference_ID, Integer.valueOf(C_Invoice_Reference_ID));
	}

	/** Get C_Invoice_Reference.
		@return C_Invoice_Reference	  */
	@Override
	public int getC_Invoice_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * C_Invoice_Reference_Type AD_Reference_ID=540738
	 * Reference name: C_Invoice_Reference_Type_List
	 */
	public static final int C_INVOICE_REFERENCE_TYPE_AD_Reference_ID=540738;
	/** CreditMemo = C */
	public static final String C_INVOICE_REFERENCE_TYPE_CreditMemo = "C";
	/** AdjustmentCharge = A */
	public static final String C_INVOICE_REFERENCE_TYPE_AdjustmentCharge = "A";
	/** Set C_Invoice_Reference_Type.
		@param C_Invoice_Reference_Type C_Invoice_Reference_Type	  */
	@Override
	public void setC_Invoice_Reference_Type (java.lang.String C_Invoice_Reference_Type)
	{

		set_Value (COLUMNNAME_C_Invoice_Reference_Type, C_Invoice_Reference_Type);
	}

	/** Get C_Invoice_Reference_Type.
		@return C_Invoice_Reference_Type	  */
	@Override
	public java.lang.String getC_Invoice_Reference_Type () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_C_Invoice_Reference_Type);
	}
}