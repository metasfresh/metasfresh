// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_QuickInput_Attributes4
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_QuickInput_Attributes4 extends org.compiere.model.PO implements I_C_BPartner_QuickInput_Attributes4, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -720265236L;

    /** Standard Constructor */
    public X_C_BPartner_QuickInput_Attributes4 (final Properties ctx, final int C_BPartner_QuickInput_Attributes4_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_QuickInput_Attributes4_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_QuickInput_Attributes4 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * Attributes4 AD_Reference_ID=541335
	 * Reference name: Attributes4
	 */
	public static final int ATTRIBUTES4_AD_Reference_ID=541335;
	/** Test(A4T1) = A4T1 */
	public static final String ATTRIBUTES4_TestA4T1 = "A4T1";
	/** Test(A4T2) = A4T2 */
	public static final String ATTRIBUTES4_TestA4T2 = "A4T2";
	@Override
	public void setAttributes4 (final java.lang.String Attributes4)
	{
		set_Value (COLUMNNAME_Attributes4, Attributes4);
	}

	@Override
	public java.lang.String getAttributes4() 
	{
		return get_ValueAsString(COLUMNNAME_Attributes4);
	}

	@Override
	public void setC_BPartner_QuickInput_Attributes4_ID (final int C_BPartner_QuickInput_Attributes4_ID)
	{
		if (C_BPartner_QuickInput_Attributes4_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_Attributes4_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_Attributes4_ID, C_BPartner_QuickInput_Attributes4_ID);
	}

	@Override
	public int getC_BPartner_QuickInput_Attributes4_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_QuickInput_Attributes4_ID);
	}

	@Override
	public org.compiere.model.I_C_BPartner_QuickInput getC_BPartner_QuickInput()
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_QuickInput_ID, org.compiere.model.I_C_BPartner_QuickInput.class);
	}

	@Override
	public void setC_BPartner_QuickInput(final org.compiere.model.I_C_BPartner_QuickInput C_BPartner_QuickInput)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_QuickInput_ID, org.compiere.model.I_C_BPartner_QuickInput.class, C_BPartner_QuickInput);
	}

	@Override
	public void setC_BPartner_QuickInput_ID (final int C_BPartner_QuickInput_ID)
	{
		if (C_BPartner_QuickInput_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_ID, C_BPartner_QuickInput_ID);
	}

	@Override
	public int getC_BPartner_QuickInput_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_QuickInput_ID);
	}
}