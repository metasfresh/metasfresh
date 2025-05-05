// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_PaymentInstruction
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_PaymentInstruction extends org.compiere.model.PO implements I_C_PaymentInstruction, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 148441571L;

    /** Standard Constructor */
    public X_C_PaymentInstruction (final Properties ctx, final int C_PaymentInstruction_ID, @Nullable final String trxName)
    {
      super (ctx, C_PaymentInstruction_ID, trxName);
    }

    /** Load Constructor */
    public X_C_PaymentInstruction (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_PaymentInstruction_ID (final int C_PaymentInstruction_ID)
	{
		if (C_PaymentInstruction_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaymentInstruction_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaymentInstruction_ID, C_PaymentInstruction_ID);
	}

	@Override
	public int getC_PaymentInstruction_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentInstruction_ID);
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
	public void setName (final @Nullable java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setValue (final @Nullable java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}