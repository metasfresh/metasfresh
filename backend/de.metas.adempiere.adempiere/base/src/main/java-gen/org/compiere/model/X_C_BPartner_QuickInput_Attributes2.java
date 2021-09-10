// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_QuickInput_Attributes2
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_QuickInput_Attributes2 extends org.compiere.model.PO implements I_C_BPartner_QuickInput_Attributes2, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -187167824L;

    /** Standard Constructor */
    public X_C_BPartner_QuickInput_Attributes2 (final Properties ctx, final int C_BPartner_QuickInput_Attributes2_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_QuickInput_Attributes2_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_QuickInput_Attributes2 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * Attributes2 AD_Reference_ID=541333
	 * Reference name: Attributes2
	 */
	public static final int ATTRIBUTES2_AD_Reference_ID=541333;
	/** Test(A2T1) = A2T1 */
	public static final String ATTRIBUTES2_TestA2T1 = "A2T1";
	/** Test(A2T2) = A2T2 */
	public static final String ATTRIBUTES2_TestA2T2 = "A2T2";
	@Override
	public void setAttributes2 (final java.lang.String Attributes2)
	{
		set_Value (COLUMNNAME_Attributes2, Attributes2);
	}

	@Override
	public java.lang.String getAttributes2() 
	{
		return get_ValueAsString(COLUMNNAME_Attributes2);
	}

	@Override
	public void setC_BPartner_QuickInput_Attributes2_ID (final int C_BPartner_QuickInput_Attributes2_ID)
	{
		if (C_BPartner_QuickInput_Attributes2_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_Attributes2_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_Attributes2_ID, C_BPartner_QuickInput_Attributes2_ID);
	}

	@Override
	public int getC_BPartner_QuickInput_Attributes2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_QuickInput_Attributes2_ID);
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