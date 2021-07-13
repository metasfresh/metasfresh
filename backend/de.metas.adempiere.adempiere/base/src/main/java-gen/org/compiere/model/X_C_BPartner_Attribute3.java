// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_Attribute3
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Attribute3 extends org.compiere.model.PO implements I_C_BPartner_Attribute3, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1524170765L;

    /** Standard Constructor */
    public X_C_BPartner_Attribute3 (final Properties ctx, final int C_BPartner_Attribute3_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Attribute3_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Attribute3 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Attribute3_ID (final int C_BPartner_Attribute3_ID)
	{
		if (C_BPartner_Attribute3_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Attribute3_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Attribute3_ID, C_BPartner_Attribute3_ID);
	}

	@Override
	public int getC_BPartner_Attribute3_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Attribute3_ID);
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