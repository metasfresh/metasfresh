// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_WF_Block
 *  @author metasfresh (generated) 
 */
public class X_AD_WF_Block extends org.compiere.model.PO implements I_AD_WF_Block, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1036331149L;

    /** Standard Constructor */
    public X_AD_WF_Block (final Properties ctx, final int AD_WF_Block_ID, @Nullable final String trxName)
    {
      super (ctx, AD_WF_Block_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_WF_Block (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_WF_Block_ID (final int AD_WF_Block_ID)
	{
		if (AD_WF_Block_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Block_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Block_ID, AD_WF_Block_ID);
	}

	@Override
	public int getAD_WF_Block_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Block_ID);
	}

	@Override
	public void setAD_Workflow_ID (final int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, AD_Workflow_ID);
	}

	@Override
	public int getAD_Workflow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Workflow_ID);
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