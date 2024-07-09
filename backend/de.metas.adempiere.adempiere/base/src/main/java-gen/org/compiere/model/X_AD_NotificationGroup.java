// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_NotificationGroup
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_NotificationGroup extends org.compiere.model.PO implements I_AD_NotificationGroup, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1837200981L;

    /** Standard Constructor */
    public X_AD_NotificationGroup (final Properties ctx, final int AD_NotificationGroup_ID, @Nullable final String trxName)
    {
      super (ctx, AD_NotificationGroup_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_NotificationGroup (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_NotificationGroup_ID (final int AD_NotificationGroup_ID)
	{
		if (AD_NotificationGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_NotificationGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_NotificationGroup_ID, AD_NotificationGroup_ID);
	}

	@Override
	public int getAD_NotificationGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_NotificationGroup_ID);
	}

	@Override
	public void setDeadletter_User_ID (final int Deadletter_User_ID)
	{
		if (Deadletter_User_ID < 1) 
			set_Value (COLUMNNAME_Deadletter_User_ID, null);
		else 
			set_Value (COLUMNNAME_Deadletter_User_ID, Deadletter_User_ID);
	}

	@Override
	public int getDeadletter_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Deadletter_User_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
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
	public void setInternalName (final java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName() 
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
	}

	@Override
	public void setIsNotifyOrgBPUsersOnly (final boolean IsNotifyOrgBPUsersOnly)
	{
		set_Value (COLUMNNAME_IsNotifyOrgBPUsersOnly, IsNotifyOrgBPUsersOnly);
	}

	@Override
	public boolean isNotifyOrgBPUsersOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNotifyOrgBPUsersOnly);
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