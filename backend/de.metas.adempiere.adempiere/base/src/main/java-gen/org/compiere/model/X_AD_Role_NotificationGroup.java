/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Role_NotificationGroup
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Role_NotificationGroup extends org.compiere.model.PO implements I_AD_Role_NotificationGroup, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1663671405L;

    /** Standard Constructor */
    public X_AD_Role_NotificationGroup (Properties ctx, int AD_Role_NotificationGroup_ID, String trxName)
    {
      super (ctx, AD_Role_NotificationGroup_ID, trxName);
      /** if (AD_Role_NotificationGroup_ID == 0)
        {
			setAD_NotificationGroup_ID (0);
			setAD_Role_ID (0);
			setAD_Role_NotificationGroup_ID (0);
			setNotificationType (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Role_NotificationGroup (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_AD_NotificationGroup getAD_NotificationGroup() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_NotificationGroup_ID, org.compiere.model.I_AD_NotificationGroup.class);
	}

	@Override
	public void setAD_NotificationGroup(org.compiere.model.I_AD_NotificationGroup AD_NotificationGroup)
	{
		set_ValueFromPO(COLUMNNAME_AD_NotificationGroup_ID, org.compiere.model.I_AD_NotificationGroup.class, AD_NotificationGroup);
	}

	/** Set Notification group.
		@param AD_NotificationGroup_ID Notification group	  */
	@Override
	public void setAD_NotificationGroup_ID (int AD_NotificationGroup_ID)
	{
		if (AD_NotificationGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_NotificationGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_NotificationGroup_ID, Integer.valueOf(AD_NotificationGroup_ID));
	}

	/** Get Notification group.
		@return Notification group	  */
	@Override
	public int getAD_NotificationGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_NotificationGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	/** Set Rolle.
		@param AD_Role_ID 
		Responsibility Role
	  */
	@Override
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Rolle.
		@return Responsibility Role
	  */
	@Override
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Role Notification Group.
		@param AD_Role_NotificationGroup_ID Role Notification Group	  */
	@Override
	public void setAD_Role_NotificationGroup_ID (int AD_Role_NotificationGroup_ID)
	{
		if (AD_Role_NotificationGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_NotificationGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_NotificationGroup_ID, Integer.valueOf(AD_Role_NotificationGroup_ID));
	}

	/** Get Role Notification Group.
		@return Role Notification Group	  */
	@Override
	public int getAD_Role_NotificationGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_NotificationGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** 
	 * NotificationType AD_Reference_ID=344
	 * Reference name: AD_User NotificationType
	 */
	public static final int NOTIFICATIONTYPE_AD_Reference_ID=344;
	/** EMail = E */
	public static final String NOTIFICATIONTYPE_EMail = "E";
	/** Notice = N */
	public static final String NOTIFICATIONTYPE_Notice = "N";
	/** None = X */
	public static final String NOTIFICATIONTYPE_None = "X";
	/** EMailPlusNotice = B */
	public static final String NOTIFICATIONTYPE_EMailPlusNotice = "B";
	/** NotifyUserInCharge = O */
	public static final String NOTIFICATIONTYPE_NotifyUserInCharge = "O";
	/** Set Benachrichtigungs-Art.
		@param NotificationType 
		Art der Benachrichtigung
	  */
	@Override
	public void setNotificationType (java.lang.String NotificationType)
	{

		set_Value (COLUMNNAME_NotificationType, NotificationType);
	}

	/** Get Benachrichtigungs-Art.
		@return Art der Benachrichtigung
	  */
	@Override
	public java.lang.String getNotificationType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_NotificationType);
	}
}