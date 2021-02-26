// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_WF_ActivityResult
 *  @author metasfresh (generated) 
 */
public class X_AD_WF_ActivityResult extends org.compiere.model.PO implements I_AD_WF_ActivityResult, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 400558170L;

    /** Standard Constructor */
    public X_AD_WF_ActivityResult (final Properties ctx, final int AD_WF_ActivityResult_ID, @Nullable final String trxName)
    {
      super (ctx, AD_WF_ActivityResult_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_WF_ActivityResult (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_WF_Activity_ID (final int AD_WF_Activity_ID)
	{
		if (AD_WF_Activity_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Activity_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Activity_ID, AD_WF_Activity_ID);
	}

	@Override
	public int getAD_WF_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Activity_ID);
	}

	@Override
	public void setAD_WF_ActivityResult_ID (final int AD_WF_ActivityResult_ID)
	{
		if (AD_WF_ActivityResult_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_ActivityResult_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_ActivityResult_ID, AD_WF_ActivityResult_ID);
	}

	@Override
	public int getAD_WF_ActivityResult_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_ActivityResult_ID);
	}

	@Override
	public void setAttributeName (final java.lang.String AttributeName)
	{
		set_Value (COLUMNNAME_AttributeName, AttributeName);
	}

	@Override
	public java.lang.String getAttributeName() 
	{
		return get_ValueAsString(COLUMNNAME_AttributeName);
	}

	@Override
	public void setAttributeValue (final java.lang.String AttributeValue)
	{
		set_Value (COLUMNNAME_AttributeValue, AttributeValue);
	}

	@Override
	public java.lang.String getAttributeValue() 
	{
		return get_ValueAsString(COLUMNNAME_AttributeValue);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setHelp (final java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}
}