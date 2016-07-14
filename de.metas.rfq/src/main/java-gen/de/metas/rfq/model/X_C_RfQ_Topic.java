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
	private static final long serialVersionUID = -2139452456L;

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
	public org.compiere.model.I_AD_PrintFormat getRfQ_Invitation_PrintFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_RfQ_Invitation_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setRfQ_Invitation_PrintFormat(org.compiere.model.I_AD_PrintFormat RfQ_Invitation_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_RfQ_Invitation_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, RfQ_Invitation_PrintFormat);
	}

	/** Set RfQ Invitation Druck - Format.
		@param RfQ_Invitation_PrintFormat_ID RfQ Invitation Druck - Format	  */
	@Override
	public void setRfQ_Invitation_PrintFormat_ID (int RfQ_Invitation_PrintFormat_ID)
	{
		if (RfQ_Invitation_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_RfQ_Invitation_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_RfQ_Invitation_PrintFormat_ID, Integer.valueOf(RfQ_Invitation_PrintFormat_ID));
	}

	/** Get RfQ Invitation Druck - Format.
		@return RfQ Invitation Druck - Format	  */
	@Override
	public int getRfQ_Invitation_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RfQ_Invitation_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_MailText getRfQ_InvitationWithoutQty_MailText() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_RfQ_InvitationWithoutQty_MailText_ID, org.compiere.model.I_R_MailText.class);
	}

	@Override
	public void setRfQ_InvitationWithoutQty_MailText(org.compiere.model.I_R_MailText RfQ_InvitationWithoutQty_MailText)
	{
		set_ValueFromPO(COLUMNNAME_RfQ_InvitationWithoutQty_MailText_ID, org.compiere.model.I_R_MailText.class, RfQ_InvitationWithoutQty_MailText);
	}

	/** Set RfQ without Qty Invitation mail text.
		@param RfQ_InvitationWithoutQty_MailText_ID RfQ without Qty Invitation mail text	  */
	@Override
	public void setRfQ_InvitationWithoutQty_MailText_ID (int RfQ_InvitationWithoutQty_MailText_ID)
	{
		if (RfQ_InvitationWithoutQty_MailText_ID < 1) 
			set_Value (COLUMNNAME_RfQ_InvitationWithoutQty_MailText_ID, null);
		else 
			set_Value (COLUMNNAME_RfQ_InvitationWithoutQty_MailText_ID, Integer.valueOf(RfQ_InvitationWithoutQty_MailText_ID));
	}

	/** Get RfQ without Qty Invitation mail text.
		@return RfQ without Qty Invitation mail text	  */
	@Override
	public int getRfQ_InvitationWithoutQty_MailText_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RfQ_InvitationWithoutQty_MailText_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_PrintFormat getRfQ_InvitationWithoutQty_PrintFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_RfQ_InvitationWithoutQty_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setRfQ_InvitationWithoutQty_PrintFormat(org.compiere.model.I_AD_PrintFormat RfQ_InvitationWithoutQty_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_RfQ_InvitationWithoutQty_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, RfQ_InvitationWithoutQty_PrintFormat);
	}

	/** Set RfQ without Qty Invitation Druck - Format.
		@param RfQ_InvitationWithoutQty_PrintFormat_ID RfQ without Qty Invitation Druck - Format	  */
	@Override
	public void setRfQ_InvitationWithoutQty_PrintFormat_ID (int RfQ_InvitationWithoutQty_PrintFormat_ID)
	{
		if (RfQ_InvitationWithoutQty_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_RfQ_InvitationWithoutQty_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_RfQ_InvitationWithoutQty_PrintFormat_ID, Integer.valueOf(RfQ_InvitationWithoutQty_PrintFormat_ID));
	}

	/** Get RfQ without Qty Invitation Druck - Format.
		@return RfQ without Qty Invitation Druck - Format	  */
	@Override
	public int getRfQ_InvitationWithoutQty_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RfQ_InvitationWithoutQty_PrintFormat_ID);
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
	public org.compiere.model.I_AD_PrintFormat getRfQ_Lost_PrintFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_RfQ_Lost_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setRfQ_Lost_PrintFormat(org.compiere.model.I_AD_PrintFormat RfQ_Lost_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_RfQ_Lost_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, RfQ_Lost_PrintFormat);
	}

	/** Set RfQ Lost Druck - Format.
		@param RfQ_Lost_PrintFormat_ID RfQ Lost Druck - Format	  */
	@Override
	public void setRfQ_Lost_PrintFormat_ID (int RfQ_Lost_PrintFormat_ID)
	{
		if (RfQ_Lost_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_RfQ_Lost_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_RfQ_Lost_PrintFormat_ID, Integer.valueOf(RfQ_Lost_PrintFormat_ID));
	}

	/** Get RfQ Lost Druck - Format.
		@return RfQ Lost Druck - Format	  */
	@Override
	public int getRfQ_Lost_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RfQ_Lost_PrintFormat_ID);
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

	@Override
	public org.compiere.model.I_AD_PrintFormat getRfQ_Win_PrintFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_RfQ_Win_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setRfQ_Win_PrintFormat(org.compiere.model.I_AD_PrintFormat RfQ_Win_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_RfQ_Win_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, RfQ_Win_PrintFormat);
	}

	/** Set RfQ Won Druck - Format.
		@param RfQ_Win_PrintFormat_ID RfQ Won Druck - Format	  */
	@Override
	public void setRfQ_Win_PrintFormat_ID (int RfQ_Win_PrintFormat_ID)
	{
		if (RfQ_Win_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_RfQ_Win_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_RfQ_Win_PrintFormat_ID, Integer.valueOf(RfQ_Win_PrintFormat_ID));
	}

	/** Get RfQ Won Druck - Format.
		@return RfQ Won Druck - Format	  */
	@Override
	public int getRfQ_Win_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RfQ_Win_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * RfQType AD_Reference_ID=540661
	 * Reference name: RfQType
	 */
	public static final int RFQTYPE_AD_Reference_ID=540661;
	/** Default = D */
	public static final String RFQTYPE_Default = "D";
	/** Procurement = P */
	public static final String RFQTYPE_Procurement = "P";
	/** Set Ausschreibung Art.
		@param RfQType Ausschreibung Art	  */
	@Override
	public void setRfQType (java.lang.String RfQType)
	{

		set_Value (COLUMNNAME_RfQType, RfQType);
	}

	/** Get Ausschreibung Art.
		@return Ausschreibung Art	  */
	@Override
	public java.lang.String getRfQType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RfQType);
	}
}