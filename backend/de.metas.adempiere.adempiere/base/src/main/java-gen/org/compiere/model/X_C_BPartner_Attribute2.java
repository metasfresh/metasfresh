// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_Attribute2
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Attribute2 extends org.compiere.model.PO implements I_C_BPartner_Attribute2, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 451491893L;

    /** Standard Constructor */
    public X_C_BPartner_Attribute2 (final Properties ctx, final int C_BPartner_Attribute2_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Attribute2_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Attribute2 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Attribute2_ID (final int C_BPartner_Attribute2_ID)
	{
		if (C_BPartner_Attribute2_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Attribute2_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Attribute2_ID, C_BPartner_Attribute2_ID);
	}

	@Override
	public int getC_BPartner_Attribute2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Attribute2_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}
}