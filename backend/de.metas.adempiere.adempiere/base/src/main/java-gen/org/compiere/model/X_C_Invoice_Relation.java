// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Relation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Invoice_Relation extends org.compiere.model.PO implements I_C_Invoice_Relation, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 526123812L;

    /** Standard Constructor */
    public X_C_Invoice_Relation (final Properties ctx, final int C_Invoice_Relation_ID, @Nullable final String trxName)
    {
      super (ctx, C_Invoice_Relation_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Invoice_Relation (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice_From()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_From_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice_From(final org.compiere.model.I_C_Invoice C_Invoice_From)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_From_ID, org.compiere.model.I_C_Invoice.class, C_Invoice_From);
	}

	@Override
	public void setC_Invoice_From_ID (final int C_Invoice_From_ID)
	{
		if (C_Invoice_From_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_From_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_From_ID, C_Invoice_From_ID);
	}

	@Override
	public int getC_Invoice_From_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_From_ID);
	}

	@Override
	public void setC_Invoice_Relation_ID (final int C_Invoice_Relation_ID)
	{
		if (C_Invoice_Relation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Relation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Relation_ID, C_Invoice_Relation_ID);
	}

	@Override
	public int getC_Invoice_Relation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Relation_ID);
	}

	/** 
	 * C_Invoice_Relation_Type AD_Reference_ID=541475
	 * Reference name: C_Invoice_Relation_Types
	 */
	public static final int C_INVOICE_RELATION_TYPE_AD_Reference_ID=541475;
	/** PurchaseToSales = POtoSO */
	public static final String C_INVOICE_RELATION_TYPE_PurchaseToSales = "POtoSO";
	@Override
	public void setC_Invoice_Relation_Type (final java.lang.String C_Invoice_Relation_Type)
	{
		set_Value (COLUMNNAME_C_Invoice_Relation_Type, C_Invoice_Relation_Type);
	}

	@Override
	public java.lang.String getC_Invoice_Relation_Type() 
	{
		return get_ValueAsString(COLUMNNAME_C_Invoice_Relation_Type);
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice_To()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_To_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice_To(final org.compiere.model.I_C_Invoice C_Invoice_To)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_To_ID, org.compiere.model.I_C_Invoice.class, C_Invoice_To);
	}

	@Override
	public void setC_Invoice_To_ID (final int C_Invoice_To_ID)
	{
		if (C_Invoice_To_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_To_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_To_ID, C_Invoice_To_ID);
	}

	@Override
	public int getC_Invoice_To_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_To_ID);
	}
}