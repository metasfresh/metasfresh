// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_Contact_QuickInput_Attributes
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Contact_QuickInput_Attributes extends org.compiere.model.PO implements I_C_BPartner_Contact_QuickInput_Attributes, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1870304130L;

    /** Standard Constructor */
    public X_C_BPartner_Contact_QuickInput_Attributes (final Properties ctx, final int C_BPartner_Contact_QuickInput_Attributes_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Contact_QuickInput_Attributes_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Contact_QuickInput_Attributes (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * Attribute AD_Reference_ID=541332
	 * Reference name: AD_User_Attribute
	 */
	public static final int ATTRIBUTE_AD_Reference_ID=541332;
	/** Delegate = D */
	public static final String ATTRIBUTE_Delegate = "D";
	/** Politician = P */
	public static final String ATTRIBUTE_Politician = "P";
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

	@Override
	public void setC_BPartner_Contact_QuickInput_Attributes_ID (final int C_BPartner_Contact_QuickInput_Attributes_ID)
	{
		if (C_BPartner_Contact_QuickInput_Attributes_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Contact_QuickInput_Attributes_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Contact_QuickInput_Attributes_ID, C_BPartner_Contact_QuickInput_Attributes_ID);
	}

	@Override
	public int getC_BPartner_Contact_QuickInput_Attributes_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Contact_QuickInput_Attributes_ID);
	}

	@Override
	public org.compiere.model.I_C_BPartner_Contact_QuickInput getC_BPartner_Contact_QuickInput()
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Contact_QuickInput_ID, org.compiere.model.I_C_BPartner_Contact_QuickInput.class);
	}

	@Override
	public void setC_BPartner_Contact_QuickInput(final org.compiere.model.I_C_BPartner_Contact_QuickInput C_BPartner_Contact_QuickInput)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Contact_QuickInput_ID, org.compiere.model.I_C_BPartner_Contact_QuickInput.class, C_BPartner_Contact_QuickInput);
	}

	@Override
	public void setC_BPartner_Contact_QuickInput_ID (final int C_BPartner_Contact_QuickInput_ID)
	{
		if (C_BPartner_Contact_QuickInput_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Contact_QuickInput_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Contact_QuickInput_ID, C_BPartner_Contact_QuickInput_ID);
	}

	@Override
	public int getC_BPartner_Contact_QuickInput_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Contact_QuickInput_ID);
	}
}