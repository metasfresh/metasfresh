/** Generated Model - DO NOT CHANGE */
package de.metas.async.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Async_Batch_Type
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Async_Batch_Type extends org.compiere.model.PO implements I_C_Async_Batch_Type, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 59289758L;

    /** Standard Constructor */
    public X_C_Async_Batch_Type (Properties ctx, int C_Async_Batch_Type_ID, String trxName)
    {
      super (ctx, C_Async_Batch_Type_ID, trxName);
      /** if (C_Async_Batch_Type_ID == 0)
        {
			setC_Async_Batch_Type_ID (0);
			setInternalName (null);
			setIsSendMail (false); // N
			setIsSendNotification (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_Async_Batch_Type (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Textbaustein.
		@param AD_BoilerPlate_ID Textbaustein	  */
	@Override
	public void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID)
	{
		if (AD_BoilerPlate_ID < 1) 
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, null);
		else 
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, Integer.valueOf(AD_BoilerPlate_ID));
	}

	/** Get Textbaustein.
		@return Textbaustein	  */
	@Override
	public int getAD_BoilerPlate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_BoilerPlate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Async Batch Type.
		@param C_Async_Batch_Type_ID Async Batch Type	  */
	@Override
	public void setC_Async_Batch_Type_ID (int C_Async_Batch_Type_ID)
	{
		if (C_Async_Batch_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Async_Batch_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Async_Batch_Type_ID, Integer.valueOf(C_Async_Batch_Type_ID));
	}

	/** Get Async Batch Type.
		@return Async Batch Type	  */
	@Override
	public int getC_Async_Batch_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Async_Batch_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Interner Name.
		@param InternalName 
		Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public void setInternalName (java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	/** Get Interner Name.
		@return Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public java.lang.String getInternalName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InternalName);
	}

	/** Set Send Mail.
		@param IsSendMail Send Mail	  */
	@Override
	public void setIsSendMail (boolean IsSendMail)
	{
		set_Value (COLUMNNAME_IsSendMail, Boolean.valueOf(IsSendMail));
	}

	/** Get Send Mail.
		@return Send Mail	  */
	@Override
	public boolean isSendMail () 
	{
		Object oo = get_Value(COLUMNNAME_IsSendMail);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Send Notification.
		@param IsSendNotification Send Notification	  */
	@Override
	public void setIsSendNotification (boolean IsSendNotification)
	{
		set_Value (COLUMNNAME_IsSendNotification, Boolean.valueOf(IsSendNotification));
	}

	/** Get Send Notification.
		@return Send Notification	  */
	@Override
	public boolean isSendNotification () 
	{
		Object oo = get_Value(COLUMNNAME_IsSendNotification);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Keep Alive Time (Hours).
		@param KeepAliveTimeHours Keep Alive Time (Hours)	  */
	@Override
	public void setKeepAliveTimeHours (java.lang.String KeepAliveTimeHours)
	{
		set_Value (COLUMNNAME_KeepAliveTimeHours, KeepAliveTimeHours);
	}

	/** Get Keep Alive Time (Hours).
		@return Keep Alive Time (Hours)	  */
	@Override
	public java.lang.String getKeepAliveTimeHours () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_KeepAliveTimeHours);
	}

	/** 
	 * NotificationType AD_Reference_ID=540643
	 * Reference name: _NotificationType
	 */
	public static final int NOTIFICATIONTYPE_AD_Reference_ID=540643;
	/** Async Batch Processed = ABP */
	public static final String NOTIFICATIONTYPE_AsyncBatchProcessed = "ABP";
	/** Workpackage Processed = WPP */
	public static final String NOTIFICATIONTYPE_WorkpackageProcessed = "WPP";
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

	/** Set Skip Timeout (millis).
		@param SkipTimeoutMillis Skip Timeout (millis)	  */
	@Override
	public void setSkipTimeoutMillis (int SkipTimeoutMillis)
	{
		set_Value (COLUMNNAME_SkipTimeoutMillis, Integer.valueOf(SkipTimeoutMillis));
	}

	/** Get Skip Timeout (millis).
		@return Skip Timeout (millis)	  */
	@Override
	public int getSkipTimeoutMillis () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SkipTimeoutMillis);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}