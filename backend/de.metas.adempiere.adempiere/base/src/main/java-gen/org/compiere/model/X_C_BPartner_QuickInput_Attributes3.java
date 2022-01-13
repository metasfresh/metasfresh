// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_QuickInput_Attributes3
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_QuickInput_Attributes3 extends org.compiere.model.PO implements I_C_BPartner_QuickInput_Attributes3, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1693767118L;

    /** Standard Constructor */
    public X_C_BPartner_QuickInput_Attributes3 (final Properties ctx, final int C_BPartner_QuickInput_Attributes3_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_QuickInput_Attributes3_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_QuickInput_Attributes3 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * Attributes3 AD_Reference_ID=541334
	 * Reference name: Attributes3
	 */
	public static final int ATTRIBUTES3_AD_Reference_ID=541334;
	/** Test(A3T1) = A3T1 */
	public static final String ATTRIBUTES3_TestA3T1 = "A3T1";
	/** Test(A3T2) = A3T2 */
	public static final String ATTRIBUTES3_TestA3T2 = "A3T2";
	@Override
	public void setAttributes3 (final java.lang.String Attributes3)
	{
		set_Value (COLUMNNAME_Attributes3, Attributes3);
	}

	@Override
	public java.lang.String getAttributes3() 
	{
		return get_ValueAsString(COLUMNNAME_Attributes3);
	}

	@Override
	public void setC_BPartner_QuickInput_Attributes3_ID (final int C_BPartner_QuickInput_Attributes3_ID)
	{
		if (C_BPartner_QuickInput_Attributes3_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_Attributes3_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_Attributes3_ID, C_BPartner_QuickInput_Attributes3_ID);
	}

	@Override
	public int getC_BPartner_QuickInput_Attributes3_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_QuickInput_Attributes3_ID);
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