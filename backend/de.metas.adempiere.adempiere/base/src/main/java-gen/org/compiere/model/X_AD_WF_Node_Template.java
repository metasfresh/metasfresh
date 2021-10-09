// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_WF_Node_Template
 *  @author metasfresh (generated) 
 */
public class X_AD_WF_Node_Template extends org.compiere.model.PO implements I_AD_WF_Node_Template, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1982377865L;

    /** Standard Constructor */
    public X_AD_WF_Node_Template (final Properties ctx, final int AD_WF_Node_Template_ID, @Nullable final String trxName)
    {
      super (ctx, AD_WF_Node_Template_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_WF_Node_Template (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_WF_Node_Template_ID (final int AD_WF_Node_Template_ID)
	{
		if (AD_WF_Node_Template_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_Template_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_Template_ID, AD_WF_Node_Template_ID);
	}

	@Override
	public int getAD_WF_Node_Template_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Node_Template_ID);
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