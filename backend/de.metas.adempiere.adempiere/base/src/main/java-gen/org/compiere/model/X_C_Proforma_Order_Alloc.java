// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Proforma_Order_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Proforma_Order_Alloc extends org.compiere.model.PO implements I_C_Proforma_Order_Alloc, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 395743088L;

    /** Standard Constructor */
    public X_C_Proforma_Order_Alloc (final Properties ctx, final int C_Proforma_Order_Alloc_ID, @Nullable final String trxName)
    {
      super (ctx, C_Proforma_Order_Alloc_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Proforma_Order_Alloc (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Invoice_ID (final int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_Proforma_Order_Alloc_ID (final int C_Proforma_Order_Alloc_ID)
	{
		if (C_Proforma_Order_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Proforma_Order_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Proforma_Order_Alloc_ID, C_Proforma_Order_Alloc_ID);
	}

	@Override
	public int getC_Proforma_Order_Alloc_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Proforma_Order_Alloc_ID);
	}
}