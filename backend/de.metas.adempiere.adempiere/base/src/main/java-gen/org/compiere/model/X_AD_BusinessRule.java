// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_BusinessRule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_BusinessRule extends org.compiere.model.PO implements I_AD_BusinessRule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1552046992L;

    /** Standard Constructor */
    public X_AD_BusinessRule (final Properties ctx, final int AD_BusinessRule_ID, @Nullable final String trxName)
    {
      super (ctx, AD_BusinessRule_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_BusinessRule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_BusinessRule_ID (final int AD_BusinessRule_ID)
	{
		if (AD_BusinessRule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_ID, AD_BusinessRule_ID);
	}

	@Override
	public int getAD_BusinessRule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_BusinessRule_ID);
	}

	@Override
	public void setAD_Message_ID (final int AD_Message_ID)
	{
		if (AD_Message_ID < 1) 
			set_Value (COLUMNNAME_AD_Message_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Message_ID, AD_Message_ID);
	}

	@Override
	public int getAD_Message_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Message_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setIsDebug (final boolean IsDebug)
	{
		set_Value (COLUMNNAME_IsDebug, IsDebug);
	}

	@Override
	public boolean isDebug() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDebug);
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

	@Override
	public org.compiere.model.I_AD_Val_Rule getValidation_Rule()
	{
		return get_ValueAsPO(COLUMNNAME_Validation_Rule_ID, org.compiere.model.I_AD_Val_Rule.class);
	}

	@Override
	public void setValidation_Rule(final org.compiere.model.I_AD_Val_Rule Validation_Rule)
	{
		set_ValueFromPO(COLUMNNAME_Validation_Rule_ID, org.compiere.model.I_AD_Val_Rule.class, Validation_Rule);
	}

	@Override
	public void setValidation_Rule_ID (final int Validation_Rule_ID)
	{
		if (Validation_Rule_ID < 1) 
			set_Value (COLUMNNAME_Validation_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_Validation_Rule_ID, Validation_Rule_ID);
	}

	@Override
	public int getValidation_Rule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Validation_Rule_ID);
	}
}