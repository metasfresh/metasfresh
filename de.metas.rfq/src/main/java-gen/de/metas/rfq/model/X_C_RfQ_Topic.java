/** Generated Model - DO NOT CHANGE */
package de.metas.rfq.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_RfQ_Topic
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_RfQ_Topic extends org.compiere.model.PO implements I_C_RfQ_Topic, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 936120848L;

    /** Standard Constructor */
    public X_C_RfQ_Topic (Properties ctx, int C_RfQ_Topic_ID, String trxName)
    {
      super (ctx, C_RfQ_Topic_ID, trxName);
      /** if (C_RfQ_Topic_ID == 0)
        {
			setC_RfQ_Topic_ID (0);
			setIsSelfService (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_RfQ_Topic (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setAD_PrintFormat(org.compiere.model.I_AD_PrintFormat AD_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, AD_PrintFormat);
	}

	/** Set Druck - Format.
		@param AD_PrintFormat_ID 
		Data Print Format
	  */
	@Override
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, Integer.valueOf(AD_PrintFormat_ID));
	}

	/** Get Druck - Format.
		@return Data Print Format
	  */
	@Override
	public int getAD_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Selbstbedienung.
		@param IsSelfService 
		This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	@Override
	public void setIsSelfService (boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Selbstbedienung.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	@Override
	public boolean isSelfService () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelfService);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public org.compiere.model.I_R_MailText getRfQ_Invitation_MailText() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_RfQ_Invitation_MailText_ID, org.compiere.model.I_R_MailText.class);
	}

	@Override
	public void setRfQ_Invitation_MailText(org.compiere.model.I_R_MailText RfQ_Invitation_MailText)
	{
		set_ValueFromPO(COLUMNNAME_RfQ_Invitation_MailText_ID, org.compiere.model.I_R_MailText.class, RfQ_Invitation_MailText);
	}

	/** Set RfQ Invitation mail text.
		@param RfQ_Invitation_MailText_ID RfQ Invitation mail text	  */
	@Override
	public void setRfQ_Invitation_MailText_ID (int RfQ_Invitation_MailText_ID)
	{
		if (RfQ_Invitation_MailText_ID < 1) 
			set_Value (COLUMNNAME_RfQ_Invitation_MailText_ID, null);
		else 
			set_Value (COLUMNNAME_RfQ_Invitation_MailText_ID, Integer.valueOf(RfQ_Invitation_MailText_ID));
	}

	/** Get RfQ Invitation mail text.
		@return RfQ Invitation mail text	  */
	@Override
	public int getRfQ_Invitation_MailText_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RfQ_Invitation_MailText_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_MailText getRfQ_Lost_MailText() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_RfQ_Lost_MailText_ID, org.compiere.model.I_R_MailText.class);
	}

	@Override
	public void setRfQ_Lost_MailText(org.compiere.model.I_R_MailText RfQ_Lost_MailText)
	{
		set_ValueFromPO(COLUMNNAME_RfQ_Lost_MailText_ID, org.compiere.model.I_R_MailText.class, RfQ_Lost_MailText);
	}

	/** Set RfQ lost mail text.
		@param RfQ_Lost_MailText_ID RfQ lost mail text	  */
	@Override
	public void setRfQ_Lost_MailText_ID (int RfQ_Lost_MailText_ID)
	{
		if (RfQ_Lost_MailText_ID < 1) 
			set_Value (COLUMNNAME_RfQ_Lost_MailText_ID, null);
		else 
			set_Value (COLUMNNAME_RfQ_Lost_MailText_ID, Integer.valueOf(RfQ_Lost_MailText_ID));
	}

	/** Get RfQ lost mail text.
		@return RfQ lost mail text	  */
	@Override
	public int getRfQ_Lost_MailText_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RfQ_Lost_MailText_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_MailText getRfQ_Win_MailText() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_RfQ_Win_MailText_ID, org.compiere.model.I_R_MailText.class);
	}

	@Override
	public void setRfQ_Win_MailText(org.compiere.model.I_R_MailText RfQ_Win_MailText)
	{
		set_ValueFromPO(COLUMNNAME_RfQ_Win_MailText_ID, org.compiere.model.I_R_MailText.class, RfQ_Win_MailText);
	}

	/** Set RfQ win mail text.
		@param RfQ_Win_MailText_ID RfQ win mail text	  */
	@Override
	public void setRfQ_Win_MailText_ID (int RfQ_Win_MailText_ID)
	{
		if (RfQ_Win_MailText_ID < 1) 
			set_Value (COLUMNNAME_RfQ_Win_MailText_ID, null);
		else 
			set_Value (COLUMNNAME_RfQ_Win_MailText_ID, Integer.valueOf(RfQ_Win_MailText_ID));
	}

	/** Get RfQ win mail text.
		@return RfQ win mail text	  */
	@Override
	public int getRfQ_Win_MailText_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RfQ_Win_MailText_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}