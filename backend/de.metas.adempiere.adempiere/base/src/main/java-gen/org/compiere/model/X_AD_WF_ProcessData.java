// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_WF_ProcessData
 *  @author metasfresh (generated) 
 */
public class X_AD_WF_ProcessData extends org.compiere.model.PO implements I_AD_WF_ProcessData, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -171195175L;

    /** Standard Constructor */
    public X_AD_WF_ProcessData (final Properties ctx, final int AD_WF_ProcessData_ID, @Nullable final String trxName)
    {
      super (ctx, AD_WF_ProcessData_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_WF_ProcessData (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_WF_Process_ID (final int AD_WF_Process_ID)
	{
		if (AD_WF_Process_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Process_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Process_ID, AD_WF_Process_ID);
	}

	@Override
	public int getAD_WF_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Process_ID);
	}

	@Override
	public void setAD_WF_ProcessData_ID (final int AD_WF_ProcessData_ID)
	{
		if (AD_WF_ProcessData_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_ProcessData_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_ProcessData_ID, AD_WF_ProcessData_ID);
	}

	@Override
	public int getAD_WF_ProcessData_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_ProcessData_ID);
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
}