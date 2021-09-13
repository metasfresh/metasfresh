// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_Adv_Search_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Adv_Search_v extends org.compiere.model.PO implements I_C_BPartner_Adv_Search_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1353110591L;

    /** Standard Constructor */
    public X_C_BPartner_Adv_Search_v (final Properties ctx, final int C_BPartner_Adv_Search_v_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Adv_Search_v_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Adv_Search_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BP_Contact_ID (final int C_BP_Contact_ID)
	{
		if (C_BP_Contact_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_Contact_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_Contact_ID, C_BP_Contact_ID);
	}

	@Override
	public int getC_BP_Contact_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Contact_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setCity (final @Nullable java.lang.String City)
	{
		set_Value (COLUMNNAME_City, City);
	}

	@Override
	public java.lang.String getCity() 
	{
		return get_ValueAsString(COLUMNNAME_City);
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
	public void setIsCompany (final boolean IsCompany)
	{
		set_ValueNoCheck (COLUMNNAME_IsCompany, IsCompany);
	}

	@Override
	public boolean isCompany() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCompany);
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
	public void setName (final @Nullable java.lang.String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
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