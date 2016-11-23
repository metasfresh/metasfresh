/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_SchedulerRecipient
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_SchedulerRecipient extends org.compiere.model.PO implements I_AD_SchedulerRecipient, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1154097534L;

    /** Standard Constructor */
    public X_AD_SchedulerRecipient (Properties ctx, int AD_SchedulerRecipient_ID, String trxName)
    {
      super (ctx, AD_SchedulerRecipient_ID, trxName);
      /** if (AD_SchedulerRecipient_ID == 0)
        {
			setAD_Scheduler_ID (0);
			setAD_SchedulerRecipient_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_SchedulerRecipient (Properties ctx, ResultSet rs, String trxName)
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
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
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

	@Override
	public org.compiere.model.I_AD_Scheduler getAD_Scheduler() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Scheduler_ID, org.compiere.model.I_AD_Scheduler.class);
	}

	@Override
	public void setAD_Scheduler(org.compiere.model.I_AD_Scheduler AD_Scheduler)
	{
		set_ValueFromPO(COLUMNNAME_AD_Scheduler_ID, org.compiere.model.I_AD_Scheduler.class, AD_Scheduler);
	}

	/** Set Ablaufsteuerung.
		@param AD_Scheduler_ID 
		Schedule Processes
	  */
	@Override
	public void setAD_Scheduler_ID (int AD_Scheduler_ID)
	{
		if (AD_Scheduler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Scheduler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Scheduler_ID, Integer.valueOf(AD_Scheduler_ID));
	}

	/** Get Ablaufsteuerung.
		@return Schedule Processes
	  */
	@Override
	public int getAD_Scheduler_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Scheduler_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Empfänger.
		@param AD_SchedulerRecipient_ID 
		Recipient of the Scheduler Notification
	  */
	@Override
	public void setAD_SchedulerRecipient_ID (int AD_SchedulerRecipient_ID)
	{
		if (AD_SchedulerRecipient_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_SchedulerRecipient_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_SchedulerRecipient_ID, Integer.valueOf(AD_SchedulerRecipient_ID));
	}

	/** Get Empfänger.
		@return Recipient of the Scheduler Notification
	  */
	@Override
	public int getAD_SchedulerRecipient_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_SchedulerRecipient_ID);
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
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
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
}