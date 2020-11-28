/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for CM_Chat
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_CM_Chat extends org.compiere.model.PO implements I_CM_Chat, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1111366608L;

    /** Standard Constructor */
    public X_CM_Chat (Properties ctx, int CM_Chat_ID, String trxName)
    {
      super (ctx, CM_Chat_ID, trxName);
      /** if (CM_Chat_ID == 0)
        {
			setAD_Table_ID (0);
			setCM_Chat_ID (0);
			setConfidentialType (null);
			setDescription (null);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CM_Chat (Properties ctx, ResultSet rs, String trxName)
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

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Chat.
		@param CM_Chat_ID 
		Chat or discussion thread
	  */
	@Override
	public void setCM_Chat_ID (int CM_Chat_ID)
	{
		if (CM_Chat_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_Chat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_Chat_ID, Integer.valueOf(CM_Chat_ID));
	}

	/** Get Chat.
		@return Chat or discussion thread
	  */
	@Override
	public int getCM_Chat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Chat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_CM_ChatType getCM_ChatType()
	{
		return get_ValueAsPO(COLUMNNAME_CM_ChatType_ID, org.compiere.model.I_CM_ChatType.class);
	}

	@Override
	public void setCM_ChatType(org.compiere.model.I_CM_ChatType CM_ChatType)
	{
		set_ValueFromPO(COLUMNNAME_CM_ChatType_ID, org.compiere.model.I_CM_ChatType.class, CM_ChatType);
	}

	/** Set Chat-Art.
		@param CM_ChatType_ID 
		Type of discussion / chat
	  */
	@Override
	public void setCM_ChatType_ID (int CM_ChatType_ID)
	{
		if (CM_ChatType_ID < 1) 
			set_Value (COLUMNNAME_CM_ChatType_ID, null);
		else 
			set_Value (COLUMNNAME_CM_ChatType_ID, Integer.valueOf(CM_ChatType_ID));
	}

	/** Get Chat-Art.
		@return Type of discussion / chat
	  */
	@Override
	public int getCM_ChatType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_ChatType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * ConfidentialType AD_Reference_ID=340
	 * Reference name: R_Request Confidential
	 */
	public static final int CONFIDENTIALTYPE_AD_Reference_ID=340;
	/** Public Information = A */
	public static final String CONFIDENTIALTYPE_PublicInformation = "A";
	/** Partner Confidential = C */
	public static final String CONFIDENTIALTYPE_PartnerConfidential = "C";
	/** Internal = I */
	public static final String CONFIDENTIALTYPE_Internal = "I";
	/** Private Information = P */
	public static final String CONFIDENTIALTYPE_PrivateInformation = "P";
	/** Set Vertraulichkeit.
		@param ConfidentialType 
		Type of Confidentiality
	  */
	@Override
	public void setConfidentialType (java.lang.String ConfidentialType)
	{

		set_Value (COLUMNNAME_ConfidentialType, ConfidentialType);
	}

	/** Get Vertraulichkeit.
		@return Type of Confidentiality
	  */
	@Override
	public java.lang.String getConfidentialType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ConfidentialType);
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
	 * ModerationType AD_Reference_ID=395
	 * Reference name: CM_Chat ModerationType
	 */
	public static final int MODERATIONTYPE_AD_Reference_ID=395;
	/** Not moderated = N */
	public static final String MODERATIONTYPE_NotModerated = "N";
	/** Before Publishing = B */
	public static final String MODERATIONTYPE_BeforePublishing = "B";
	/** After Publishing = A */
	public static final String MODERATIONTYPE_AfterPublishing = "A";
	/** Set Moderation Type.
		@param ModerationType 
		Type of moderation
	  */
	@Override
	public void setModerationType (java.lang.String ModerationType)
	{

		set_Value (COLUMNNAME_ModerationType, ModerationType);
	}

	/** Get Moderation Type.
		@return Type of moderation
	  */
	@Override
	public java.lang.String getModerationType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ModerationType);
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}