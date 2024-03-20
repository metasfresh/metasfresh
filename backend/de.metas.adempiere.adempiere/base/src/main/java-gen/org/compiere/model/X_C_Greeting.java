// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Greeting
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Greeting extends org.compiere.model.PO implements I_C_Greeting, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2002257322L;

    /** Standard Constructor */
    public X_C_Greeting (final Properties ctx, final int C_Greeting_ID, @Nullable final String trxName)
    {
      super (ctx, C_Greeting_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Greeting (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Greeting_ID (final int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Greeting_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Greeting_ID, C_Greeting_ID);
	}

	@Override
	public int getC_Greeting_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Greeting_ID);
	}

	@Override
	public void setGreeting (final @Nullable java.lang.String Greeting)
	{
		set_Value (COLUMNNAME_Greeting, Greeting);
	}

	@Override
	public java.lang.String getGreeting() 
	{
		return get_ValueAsString(COLUMNNAME_Greeting);
	}

	/** 
	 * GreetingStandardType AD_Reference_ID=541326
	 * Reference name: GreetingStandardType
	 */
	public static final int GREETINGSTANDARDTYPE_AD_Reference_ID=541326;
	/** MR = MR */
	public static final String GREETINGSTANDARDTYPE_MR = "MR";
	/** MRS = MRS */
	public static final String GREETINGSTANDARDTYPE_MRS = "MRS";
	/** MR+MRS = MR+MRS */
	public static final String GREETINGSTANDARDTYPE_MRPlusMRS = "MR+MRS";
	/** MRS+MR = MRS+MR */
	public static final String GREETINGSTANDARDTYPE_MRSPlusMR = "MRS+MR";
	@Override
	public void setGreetingStandardType (final @Nullable java.lang.String GreetingStandardType)
	{
		set_Value (COLUMNNAME_GreetingStandardType, GreetingStandardType);
	}

	@Override
	public java.lang.String getGreetingStandardType() 
	{
		return get_ValueAsString(COLUMNNAME_GreetingStandardType);
	}

	@Override
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setIsFirstNameOnly (final boolean IsFirstNameOnly)
	{
		set_Value (COLUMNNAME_IsFirstNameOnly, IsFirstNameOnly);
	}

	@Override
	public boolean isFirstNameOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFirstNameOnly);
	}

	@Override
	public void setLetter_Salutation (final @Nullable java.lang.String Letter_Salutation)
	{
		set_Value (COLUMNNAME_Letter_Salutation, Letter_Salutation);
	}

	@Override
	public java.lang.String getLetter_Salutation() 
	{
		return get_ValueAsString(COLUMNNAME_Letter_Salutation);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}
}