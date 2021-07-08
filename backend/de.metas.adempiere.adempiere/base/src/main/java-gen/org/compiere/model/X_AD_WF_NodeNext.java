// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_WF_NodeNext
 *  @author metasfresh (generated) 
 */
public class X_AD_WF_NodeNext extends org.compiere.model.PO implements I_AD_WF_NodeNext, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 734887374L;

    /** Standard Constructor */
    public X_AD_WF_NodeNext (final Properties ctx, final int AD_WF_NodeNext_ID, @Nullable final String trxName)
    {
      super (ctx, AD_WF_NodeNext_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_WF_NodeNext (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_WF_Next_ID (final int AD_WF_Next_ID)
	{
		if (AD_WF_Next_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Next_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Next_ID, AD_WF_Next_ID);
	}

	@Override
	public int getAD_WF_Next_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Next_ID);
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
	public void setAD_WF_NodeNext_ID (final int AD_WF_NodeNext_ID)
	{
		if (AD_WF_NodeNext_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_NodeNext_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_NodeNext_ID, AD_WF_NodeNext_ID);
	}

	@Override
	public int getAD_WF_NodeNext_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_NodeNext_ID);
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

	@Override
	public void setIsStdUserWorkflow (final boolean IsStdUserWorkflow)
	{
		set_Value (COLUMNNAME_IsStdUserWorkflow, IsStdUserWorkflow);
	}

	@Override
	public boolean isStdUserWorkflow() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsStdUserWorkflow);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setTransitionCode (final java.lang.String TransitionCode)
	{
		set_Value (COLUMNNAME_TransitionCode, TransitionCode);
	}

	@Override
	public java.lang.String getTransitionCode() 
	{
		return get_ValueAsString(COLUMNNAME_TransitionCode);
	}
}