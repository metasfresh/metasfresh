// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_Contact_QuickInput
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Contact_QuickInput extends org.compiere.model.PO implements I_C_BPartner_Contact_QuickInput, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -724059472L;

    /** Standard Constructor */
    public X_C_BPartner_Contact_QuickInput (final Properties ctx, final int C_BPartner_Contact_QuickInput_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Contact_QuickInput_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Contact_QuickInput (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	@Override
	public void setC_Greeting_ID (final int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1) 
			set_Value (COLUMNNAME_C_Greeting_ID, null);
		else 
			set_Value (COLUMNNAME_C_Greeting_ID, C_Greeting_ID);
	}

	@Override
	public int getC_Greeting_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Greeting_ID);
	}

	@Override
	public void setEMail (final @Nullable java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	@Override
	public java.lang.String getEMail() 
	{
		return get_ValueAsString(COLUMNNAME_EMail);
	}

	@Override
	public void setFirstname (final @Nullable java.lang.String Firstname)
	{
		set_Value (COLUMNNAME_Firstname, Firstname);
	}

	@Override
	public java.lang.String getFirstname() 
	{
		return get_ValueAsString(COLUMNNAME_Firstname);
	}

	@Override
	public void setIsMembershipContact (final boolean IsMembershipContact)
	{
		set_Value (COLUMNNAME_IsMembershipContact, IsMembershipContact);
	}

	@Override
	public boolean isMembershipContact() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMembershipContact);
	}

	@Override
	public void setIsNewsletter (final boolean IsNewsletter)
	{
		set_Value (COLUMNNAME_IsNewsletter, IsNewsletter);
	}

	@Override
	public boolean isNewsletter() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNewsletter);
	}

	@Override
	public void setLastname (final @Nullable java.lang.String Lastname)
	{
		set_Value (COLUMNNAME_Lastname, Lastname);
	}

	@Override
	public java.lang.String getLastname() 
	{
		return get_ValueAsString(COLUMNNAME_Lastname);
	}

	@Override
	public void setPhone (final @Nullable java.lang.String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	@Override
	public java.lang.String getPhone() 
	{
		return get_ValueAsString(COLUMNNAME_Phone);
	}
}