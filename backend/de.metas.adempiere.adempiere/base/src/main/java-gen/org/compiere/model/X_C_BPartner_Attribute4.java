// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_Attribute4
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Attribute4 extends org.compiere.model.PO implements I_C_BPartner_Attribute4, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 576738530L;

    /** Standard Constructor */
    public X_C_BPartner_Attribute4 (final Properties ctx, final int C_BPartner_Attribute4_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Attribute4_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Attribute4 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	/** Internet = Internet */
	public static final String ATTRIBUTES4_Internet = "Internet";
	/** Persönlich = Persönlich */
	public static final String ATTRIBUTES4_Persoenlich = "Persönlich";
	/** Anlass = Anlass */
	public static final String ATTRIBUTES4_Anlass = "Anlass";
	/** Zeitung = Zeitung */
	public static final String ATTRIBUTES4_Zeitung = "Zeitung";
	/** TV = TV */
	public static final String ATTRIBUTES4_TV = "TV";
	/** Empfehlung = Empfehlung */
	public static final String ATTRIBUTES4_Empfehlung = "Empfehlung";
	/** Inserate = Inserate */
	public static final String ATTRIBUTES4_Inserate = "Inserate";
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
	public void setC_BPartner_Attribute4_ID (final int C_BPartner_Attribute4_ID)
	{
		if (C_BPartner_Attribute4_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Attribute4_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Attribute4_ID, C_BPartner_Attribute4_ID);
	}

	@Override
	public int getC_BPartner_Attribute4_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Attribute4_ID);
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