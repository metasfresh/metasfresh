/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_User_NotificationGroup
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_User_NotificationGroup extends org.compiere.model.PO implements I_AD_User_NotificationGroup, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -79697306L;

    /** Standard Constructor */
    public X_AD_User_NotificationGroup (Properties ctx, int AD_User_NotificationGroup_ID, String trxName)
    {
      super (ctx, AD_User_NotificationGroup_ID, trxName);
      /** if (AD_User_NotificationGroup_ID == 0)
        {
			setAD_NotificationGroup_ID (0);
			setAD_User_ID (0);
			setAD_User_NotificationGroup_ID (0);
			setNotificationType (null);
        } */
    }

    /** Load Constructor */
    public X_AD_User_NotificationGroup (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set User Notification Group.
		@param AD_User_NotificationGroup_ID User Notification Group	  */
	@Override
	public void setAD_User_NotificationGroup_ID (int AD_User_NotificationGroup_ID)
	{
		if (AD_User_NotificationGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_NotificationGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_NotificationGroup_ID, Integer.valueOf(AD_User_NotificationGroup_ID));
	}

	/** Get User Notification Group.
		@return User Notification Group	  */
	@Override
	public int getAD_User_NotificationGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_NotificationGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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