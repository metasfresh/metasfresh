// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_User_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_User_Attribute extends org.compiere.model.PO implements I_AD_User_Attribute, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 137206892L;

    /** Standard Constructor */
    public X_AD_User_Attribute (final Properties ctx, final int AD_User_Attribute_ID, @Nullable final String trxName)
    {
      super (ctx, AD_User_Attribute_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_User_Attribute (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_Attribute_ID (final int AD_User_Attribute_ID)
	{
		if (AD_User_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_Attribute_ID, AD_User_Attribute_ID);
	}

	@Override
	public int getAD_User_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_Attribute_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	/** 
	 * Attribute AD_Reference_ID=541332
	 * Reference name: AD_User_Attribute
	 */
	public static final int ATTRIBUTE_AD_Reference_ID=541332;
	/** Delegate = D */
	public static final String ATTRIBUTE_Delegate = "D";
	/** Politician = P */
	public static final String ATTRIBUTE_Politician = "P";
	/** Rechtsberater = R */
	public static final String ATTRIBUTE_Rechtsberater = "R";
	/** Schätzer = S */
	public static final String ATTRIBUTE_Schaetzer = "S";
	/** Vorstand = V */
	public static final String ATTRIBUTE_Vorstand = "V";
	@Override
	public void setAttribute (final java.lang.String Attribute)
	{
		set_Value (COLUMNNAME_Attribute, Attribute);
	}

	@Override
	public java.lang.String getAttribute() 
	{
		return get_ValueAsString(COLUMNNAME_Attribute);
	}
}