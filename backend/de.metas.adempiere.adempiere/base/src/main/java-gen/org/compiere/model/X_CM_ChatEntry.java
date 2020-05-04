/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for CM_ChatEntry
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_CM_ChatEntry extends org.compiere.model.PO implements I_CM_ChatEntry, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1128109696L;

    /** Standard Constructor */
    public X_CM_ChatEntry (Properties ctx, int CM_ChatEntry_ID, String trxName)
    {
      super (ctx, CM_ChatEntry_ID, trxName);
      /** if (CM_ChatEntry_ID == 0)
        {
			setCharacterData (null);
			setChatEntryType (null); // N
			setCM_ChatEntry_ID (0);
			setCM_Chat_ID (0);
			setConfidentialType (null);
        } */
    }

    /** Load Constructor */
    public X_CM_ChatEntry (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Character Data.
		@param CharacterData 
		Long Character Field
	  */
	@Override
	public void setCharacterData (java.lang.String CharacterData)
	{
		set_ValueNoCheck (COLUMNNAME_CharacterData, CharacterData);
	}

	/** Get Character Data.
		@return Long Character Field
	  */
	@Override
	public java.lang.String getCharacterData () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CharacterData);
	}

	/** 
	 * ChatEntryType AD_Reference_ID=398
	 * Reference name: CM_Chat EntryType
	 */
	public static final int CHATENTRYTYPE_AD_Reference_ID=398;
	/** Wiki = W */
	public static final String CHATENTRYTYPE_Wiki = "W";
	/** Note (flat) = N */
	public static final String CHATENTRYTYPE_NoteFlat = "N";
	/** Forum (threaded) = F */
	public static final String CHATENTRYTYPE_ForumThreaded = "F";
	/** Set Chat Entry Type.
		@param ChatEntryType 
		Type of Chat/Forum Entry
	  */
	@Override
	public void setChatEntryType (java.lang.String ChatEntryType)
	{

		set_Value (COLUMNNAME_ChatEntryType, ChatEntryType);
	}

	/** Get Chat Entry Type.
		@return Type of Chat/Forum Entry
	  */
	@Override
	public java.lang.String getChatEntryType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ChatEntryType);
	}

	@Override
	public org.compiere.model.I_CM_ChatEntry getCM_ChatEntryGrandParent()
	{
		return get_ValueAsPO(COLUMNNAME_CM_ChatEntryGrandParent_ID, org.compiere.model.I_CM_ChatEntry.class);
	}

	@Override
	public void setCM_ChatEntryGrandParent(org.compiere.model.I_CM_ChatEntry CM_ChatEntryGrandParent)
	{
		set_ValueFromPO(COLUMNNAME_CM_ChatEntryGrandParent_ID, org.compiere.model.I_CM_ChatEntry.class, CM_ChatEntryGrandParent);
	}

	/** Set Chat Entry Grandparent.
		@param CM_ChatEntryGrandParent_ID 
		Link to Grand Parent (root level)
	  */
	@Override
	public void setCM_ChatEntryGrandParent_ID (int CM_ChatEntryGrandParent_ID)
	{
		if (CM_ChatEntryGrandParent_ID < 1) 
			set_Value (COLUMNNAME_CM_ChatEntryGrandParent_ID, null);
		else 
			set_Value (COLUMNNAME_CM_ChatEntryGrandParent_ID, Integer.valueOf(CM_ChatEntryGrandParent_ID));
	}

	/** Get Chat Entry Grandparent.
		@return Link to Grand Parent (root level)
	  */
	@Override
	public int getCM_ChatEntryGrandParent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_ChatEntryGrandParent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Chat-Eintrag.
		@param CM_ChatEntry_ID 
		Individual Chat / Discussion Entry
	  */
	@Override
	public void setCM_ChatEntry_ID (int CM_ChatEntry_ID)
	{
		if (CM_ChatEntry_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_ChatEntry_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_ChatEntry_ID, Integer.valueOf(CM_ChatEntry_ID));
	}

	/** Get Chat-Eintrag.
		@return Individual Chat / Discussion Entry
	  */
	@Override
	public int getCM_ChatEntry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_ChatEntry_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_CM_ChatEntry getCM_ChatEntryParent()
	{
		return get_ValueAsPO(COLUMNNAME_CM_ChatEntryParent_ID, org.compiere.model.I_CM_ChatEntry.class);
	}

	@Override
	public void setCM_ChatEntryParent(org.compiere.model.I_CM_ChatEntry CM_ChatEntryParent)
	{
		set_ValueFromPO(COLUMNNAME_CM_ChatEntryParent_ID, org.compiere.model.I_CM_ChatEntry.class, CM_ChatEntryParent);
	}

	/** Set Chat Entry Parent.
		@param CM_ChatEntryParent_ID 
		Link to direct Parent
	  */
	@Override
	public void setCM_ChatEntryParent_ID (int CM_ChatEntryParent_ID)
	{
		if (CM_ChatEntryParent_ID < 1) 
			set_Value (COLUMNNAME_CM_ChatEntryParent_ID, null);
		else 
			set_Value (COLUMNNAME_CM_ChatEntryParent_ID, Integer.valueOf(CM_ChatEntryParent_ID));
	}

	/** Get Chat Entry Parent.
		@return Link to direct Parent
	  */
	@Override
	public int getCM_ChatEntryParent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_ChatEntryParent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_CM_Chat getCM_Chat()
	{
		return get_ValueAsPO(COLUMNNAME_CM_Chat_ID, org.compiere.model.I_CM_Chat.class);
	}

	@Override
	public void setCM_Chat(org.compiere.model.I_CM_Chat CM_Chat)
	{
		set_ValueFromPO(COLUMNNAME_CM_Chat_ID, org.compiere.model.I_CM_Chat.class, CM_Chat);
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

	/** 
	 * ModeratorStatus AD_Reference_ID=396
	 * Reference name: CM_ChatEntry ModeratorStatus
	 */
	public static final int MODERATORSTATUS_AD_Reference_ID=396;
	/** Nicht angezeigt = N */
	public static final String MODERATORSTATUS_NichtAngezeigt = "N";
	/** Veröffentlicht = P */
	public static final String MODERATORSTATUS_Veroeffentlicht = "P";
	/** To be reviewed = R */
	public static final String MODERATORSTATUS_ToBeReviewed = "R";
	/** Verdächtig = S */
	public static final String MODERATORSTATUS_Verdaechtig = "S";
	/** Set Moderation Status.
		@param ModeratorStatus 
		Status of Moderation
	  */
	@Override
	public void setModeratorStatus (java.lang.String ModeratorStatus)
	{

		set_Value (COLUMNNAME_ModeratorStatus, ModeratorStatus);
	}

	/** Get Moderation Status.
		@return Status of Moderation
	  */
	@Override
	public java.lang.String getModeratorStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ModeratorStatus);
	}

	/** Set Betreff.
		@param Subject 
		Mail Betreff
	  */
	@Override
	public void setSubject (java.lang.String Subject)
	{
		set_Value (COLUMNNAME_Subject, Subject);
	}

	/** Get Betreff.
		@return Mail Betreff
	  */
	@Override
	public java.lang.String getSubject () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Subject);
	}
}