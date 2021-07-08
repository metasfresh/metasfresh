// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_WF_Node_Para
 *  @author metasfresh (generated) 
 */
public class X_AD_WF_Node_Para extends org.compiere.model.PO implements I_AD_WF_Node_Para, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1247511262L;

    /** Standard Constructor */
    public X_AD_WF_Node_Para (final Properties ctx, final int AD_WF_Node_Para_ID, @Nullable final String trxName)
    {
      super (ctx, AD_WF_Node_Para_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_WF_Node_Para (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Process_Para getAD_Process_Para()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_Para_ID, org.compiere.model.I_AD_Process_Para.class);
	}

	@Override
	public void setAD_Process_Para(final org.compiere.model.I_AD_Process_Para AD_Process_Para)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_Para_ID, org.compiere.model.I_AD_Process_Para.class, AD_Process_Para);
	}

	@Override
	public void setAD_Process_Para_ID (final int AD_Process_Para_ID)
	{
		if (AD_Process_Para_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_Para_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_Para_ID, AD_Process_Para_ID);
	}

	@Override
	public int getAD_Process_Para_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_Para_ID);
	}

	@Override
	public void setAD_WF_Node_ID (final int AD_WF_Node_ID)
	{
		if (AD_WF_Node_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, AD_WF_Node_ID);
	}

	@Override
	public int getAD_WF_Node_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Node_ID);
	}

	@Override
	public void setAD_WF_Node_Para_ID (final int AD_WF_Node_Para_ID)
	{
		if (AD_WF_Node_Para_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_Para_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_Para_ID, AD_WF_Node_Para_ID);
	}

	@Override
	public int getAD_WF_Node_Para_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Node_Para_ID);
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

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}
}