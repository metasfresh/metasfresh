// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Verification_Set
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Invoice_Verification_Set extends org.compiere.model.PO implements I_C_Invoice_Verification_Set, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -582303584L;

    /** Standard Constructor */
    public X_C_Invoice_Verification_Set (final Properties ctx, final int C_Invoice_Verification_Set_ID, @Nullable final String trxName)
    {
      super (ctx, C_Invoice_Verification_Set_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Invoice_Verification_Set (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Invoice_Verification_Set_ID (final int C_Invoice_Verification_Set_ID)
	{
		if (C_Invoice_Verification_Set_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Verification_Set_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Verification_Set_ID, C_Invoice_Verification_Set_ID);
	}

	@Override
	public int getC_Invoice_Verification_Set_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Verification_Set_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}
}