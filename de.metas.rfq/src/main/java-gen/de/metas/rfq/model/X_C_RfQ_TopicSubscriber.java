/** Generated Model - DO NOT CHANGE */
package de.metas.rfq.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_RfQ_TopicSubscriber
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_RfQ_TopicSubscriber extends org.compiere.model.PO implements I_C_RfQ_TopicSubscriber, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 707677790L;

    /** Standard Constructor */
    public X_C_RfQ_TopicSubscriber (Properties ctx, int C_RfQ_TopicSubscriber_ID, String trxName)
    {
      super (ctx, C_RfQ_TopicSubscriber_ID, trxName);
      /** if (C_RfQ_TopicSubscriber_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_RfQ_Topic_ID (0);
			setC_RfQ_TopicSubscriber_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_RfQ_TopicSubscriber (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.rfq.model.I_C_RfQ_Topic getC_RfQ_Topic() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQ_Topic_ID, de.metas.rfq.model.I_C_RfQ_Topic.class);
	}

	@Override
	public void setC_RfQ_Topic(de.metas.rfq.model.I_C_RfQ_Topic C_RfQ_Topic)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQ_Topic_ID, de.metas.rfq.model.I_C_RfQ_Topic.class, C_RfQ_Topic);
	}

	/** Set Ausschreibungs-Thema.
		@param C_RfQ_Topic_ID 
		Topic for Request for Quotations
	  */
	@Override
	public void setC_RfQ_Topic_ID (int C_RfQ_Topic_ID)
	{
		if (C_RfQ_Topic_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_Topic_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_Topic_ID, Integer.valueOf(C_RfQ_Topic_ID));
	}

	/** Get Ausschreibungs-Thema.
		@return Topic for Request for Quotations
	  */
	@Override
	public int getC_RfQ_Topic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_Topic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RfQ Subscriber.
		@param C_RfQ_TopicSubscriber_ID 
		Request for Quotation Topic Subscriber
	  */
	@Override
	public void setC_RfQ_TopicSubscriber_ID (int C_RfQ_TopicSubscriber_ID)
	{
		if (C_RfQ_TopicSubscriber_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_TopicSubscriber_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_TopicSubscriber_ID, Integer.valueOf(C_RfQ_TopicSubscriber_ID));
	}

	/** Get RfQ Subscriber.
		@return Request for Quotation Topic Subscriber
	  */
	@Override
	public int getC_RfQ_TopicSubscriber_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_TopicSubscriber_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datum der Abmeldung.
		@param OptOutDate 
		Date the contact opted out
	  */
	@Override
	public void setOptOutDate (java.sql.Timestamp OptOutDate)
	{
		set_Value (COLUMNNAME_OptOutDate, OptOutDate);
	}

	/** Get Datum der Abmeldung.
		@return Date the contact opted out
	  */
	@Override
	public java.sql.Timestamp getOptOutDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_OptOutDate);
	}

	/** Set Anmeldung.
		@param SubscribeDate 
		Date the contact actively subscribed
	  */
	@Override
	public void setSubscribeDate (java.sql.Timestamp SubscribeDate)
	{
		set_Value (COLUMNNAME_SubscribeDate, SubscribeDate);
	}

	/** Get Anmeldung.
		@return Date the contact actively subscribed
	  */
	@Override
	public java.sql.Timestamp getSubscribeDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_SubscribeDate);
	}
}