/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Rule
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Rule extends org.compiere.model.PO implements I_AD_Rule, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1584044405L;

    /** Standard Constructor */
    public X_AD_Rule (Properties ctx, int AD_Rule_ID, String trxName)
    {
      super (ctx, AD_Rule_ID, trxName);
      /** if (AD_Rule_ID == 0)
        {
			setAD_Rule_ID (0);
			setEntityType (null);
// U
			setEventType (null);
			setName (null);
			setRuleType (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Rule (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * AccessLevel AD_Reference_ID=5
	 * Reference name: AD_Table Access Levels
	 */
	public static final int ACCESSLEVEL_AD_Reference_ID=5;
	/** Organization = 1 */
	public static final String ACCESSLEVEL_Organization = "1";
	/** ClientPlusOrganization = 3 */
	public static final String ACCESSLEVEL_ClientPlusOrganization = "3";
	/** SystemOnly = 4 */
	public static final String ACCESSLEVEL_SystemOnly = "4";
	/** All = 7 */
	public static final String ACCESSLEVEL_All = "7";
	/** SystemPlusClient = 6 */
	public static final String ACCESSLEVEL_SystemPlusClient = "6";
	/** ClientOnly = 2 */
	public static final String ACCESSLEVEL_ClientOnly = "2";
	/** Set Berechtigungsstufe.
		@param AccessLevel 
		Access Level required
	  */
	@Override
	public void setAccessLevel (java.lang.String AccessLevel)
	{

		set_Value (COLUMNNAME_AccessLevel, AccessLevel);
	}

	/** Get Berechtigungsstufe.
		@return Access Level required
	  */
	@Override
	public java.lang.String getAccessLevel () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccessLevel);
	}

	/** Set Regel.
		@param AD_Rule_ID Regel	  */
	@Override
	public void setAD_Rule_ID (int AD_Rule_ID)
	{
		if (AD_Rule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Rule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Rule_ID, Integer.valueOf(AD_Rule_ID));
	}

	/** Get Regel.
		@return Regel	  */
	@Override
	public int getAD_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Rule_ID);
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
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitäts-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitäts-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	/** 
	 * EventType AD_Reference_ID=53236
	 * Reference name: AD_Rule_EventType
	 */
	public static final int EVENTTYPE_AD_Reference_ID=53236;
	/** Callout = C */
	public static final String EVENTTYPE_Callout = "C";
	/** Process = P */
	public static final String EVENTTYPE_Process = "P";
	/** ModelValidatorTableEvent = T */
	public static final String EVENTTYPE_ModelValidatorTableEvent = "T";
	/** ModelValidatorDocumentEvent = D */
	public static final String EVENTTYPE_ModelValidatorDocumentEvent = "D";
	/** ModelValidatorLoginEvent = L */
	public static final String EVENTTYPE_ModelValidatorLoginEvent = "L";
	/** HumanResourcePayroll = H */
	public static final String EVENTTYPE_HumanResourcePayroll = "H";
	/** Set Ereignisart.
		@param EventType Ereignisart	  */
	@Override
	public void setEventType (java.lang.String EventType)
	{

		set_Value (COLUMNNAME_EventType, EventType);
	}

	/** Get Ereignisart.
		@return Ereignisart	  */
	@Override
	public java.lang.String getEventType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EventType);
	}

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
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

	/** 
	 * RuleType AD_Reference_ID=53235
	 * Reference name: AD_Rule_RuleType
	 */
	public static final int RULETYPE_AD_Reference_ID=53235;
	/** AspectOrientProgram = A */
	public static final String RULETYPE_AspectOrientProgram = "A";
	/** JSR223ScriptingAPIs = S */
	public static final String RULETYPE_JSR223ScriptingAPIs = "S";
	/** JSR94RuleEngineAPI = R */
	public static final String RULETYPE_JSR94RuleEngineAPI = "R";
	/** SQL = Q */
	public static final String RULETYPE_SQL = "Q";
	/** Set Rule Type.
		@param RuleType Rule Type	  */
	@Override
	public void setRuleType (java.lang.String RuleType)
	{

		set_Value (COLUMNNAME_RuleType, RuleType);
	}

	/** Get Rule Type.
		@return Rule Type	  */
	@Override
	public java.lang.String getRuleType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RuleType);
	}

	/** Set Skript.
		@param Script 
		Dynamic Java Language Script to calculate result
	  */
	@Override
	public void setScript (java.lang.String Script)
	{
		set_Value (COLUMNNAME_Script, Script);
	}

	/** Get Skript.
		@return Dynamic Java Language Script to calculate result
	  */
	@Override
	public java.lang.String getScript () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Script);
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}