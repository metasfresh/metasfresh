/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Sequence
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Sequence extends org.compiere.model.PO implements I_AD_Sequence, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1937766211L;

    /** Standard Constructor */
    public X_AD_Sequence (Properties ctx, int AD_Sequence_ID, String trxName)
    {
      super (ctx, AD_Sequence_ID, trxName);
      /** if (AD_Sequence_ID == 0)
        {
			setAD_Sequence_ID (0);
			setCurrentNext (0); // 1000000
			setCurrentNextSys (0); // 100
			setIncrementNo (0); // 1
			setIsAutoSequence (false);
			setName (null);
			setStartNo (0); // 1000000
        } */
    }

    /** Load Constructor */
    public X_AD_Sequence (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Reihenfolge.
		@param AD_Sequence_ID 
		Document Sequence
	  */
	@Override
	public void setAD_Sequence_ID (int AD_Sequence_ID)
	{
		if (AD_Sequence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Sequence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Sequence_ID, Integer.valueOf(AD_Sequence_ID));
	}

	/** Get Reihenfolge.
		@return Document Sequence
	  */
	@Override
	public int getAD_Sequence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Sequence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Aktuell nächster Wert.
		@param CurrentNext 
		The next number to be used
	  */
	@Override
	public void setCurrentNext (int CurrentNext)
	{
		set_Value (COLUMNNAME_CurrentNext, Integer.valueOf(CurrentNext));
	}

	/** Get Aktuell nächster Wert.
		@return The next number to be used
	  */
	@Override
	public int getCurrentNext () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CurrentNext);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Current Next (System).
		@param CurrentNextSys 
		Next sequence for system use
	  */
	@Override
	public void setCurrentNextSys (int CurrentNextSys)
	{
		set_Value (COLUMNNAME_CurrentNextSys, Integer.valueOf(CurrentNextSys));
	}

	/** Get Current Next (System).
		@return Next sequence for system use
	  */
	@Override
	public int getCurrentNextSys () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CurrentNextSys);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Abw. Sequenznummer-Implementierung.
		@param CustomSequenceNoProvider_JavaClass_ID Abw. Sequenznummer-Implementierung	  */
	@Override
	public void setCustomSequenceNoProvider_JavaClass_ID (int CustomSequenceNoProvider_JavaClass_ID)
	{
		if (CustomSequenceNoProvider_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_CustomSequenceNoProvider_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_CustomSequenceNoProvider_JavaClass_ID, Integer.valueOf(CustomSequenceNoProvider_JavaClass_ID));
	}

	/** Get Abw. Sequenznummer-Implementierung.
		@return Abw. Sequenznummer-Implementierung	  */
	@Override
	public int getCustomSequenceNoProvider_JavaClass_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CustomSequenceNoProvider_JavaClass_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Column.
		@param DateColumn 
		Fully qualified date column
	  */
	@Override
	public void setDateColumn (java.lang.String DateColumn)
	{
		set_Value (COLUMNNAME_DateColumn, DateColumn);
	}

	/** Get Date Column.
		@return Fully qualified date column
	  */
	@Override
	public java.lang.String getDateColumn () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DateColumn);
	}

	/** Set Decimal Pattern.
		@param DecimalPattern 
		Java Decimal Pattern
	  */
	@Override
	public void setDecimalPattern (java.lang.String DecimalPattern)
	{
		set_Value (COLUMNNAME_DecimalPattern, DecimalPattern);
	}

	/** Get Decimal Pattern.
		@return Java Decimal Pattern
	  */
	@Override
	public java.lang.String getDecimalPattern () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DecimalPattern);
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

	/** Set Increment.
		@param IncrementNo 
		The number to increment the last document number by
	  */
	@Override
	public void setIncrementNo (int IncrementNo)
	{
		set_Value (COLUMNNAME_IncrementNo, Integer.valueOf(IncrementNo));
	}

	/** Get Increment.
		@return The number to increment the last document number by
	  */
	@Override
	public int getIncrementNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IncrementNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Activate Audit.
		@param IsAudited 
		Activate Audit Trail of what numbers are generated
	  */
	@Override
	public void setIsAudited (boolean IsAudited)
	{
		set_Value (COLUMNNAME_IsAudited, Boolean.valueOf(IsAudited));
	}

	/** Get Activate Audit.
		@return Activate Audit Trail of what numbers are generated
	  */
	@Override
	public boolean isAudited () 
	{
		Object oo = get_Value(COLUMNNAME_IsAudited);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Automatische Nummerierung.
		@param IsAutoSequence 
		Automatically assign the next number
	  */
	@Override
	public void setIsAutoSequence (boolean IsAutoSequence)
	{
		set_Value (COLUMNNAME_IsAutoSequence, Boolean.valueOf(IsAutoSequence));
	}

	/** Get Automatische Nummerierung.
		@return Automatically assign the next number
	  */
	@Override
	public boolean isAutoSequence () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutoSequence);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Used for Record ID.
		@param IsTableID 
		The document number  will be used as the record key
	  */
	@Override
	public void setIsTableID (boolean IsTableID)
	{
		set_Value (COLUMNNAME_IsTableID, Boolean.valueOf(IsTableID));
	}

	/** Get Used for Record ID.
		@return The document number  will be used as the record key
	  */
	@Override
	public boolean isTableID () 
	{
		Object oo = get_Value(COLUMNNAME_IsTableID);
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

	/** Set Prefix.
		@param Prefix 
		Prefix before the sequence number
	  */
	@Override
	public void setPrefix (java.lang.String Prefix)
	{
		set_Value (COLUMNNAME_Prefix, Prefix);
	}

	/** Get Prefix.
		@return Prefix before the sequence number
	  */
	@Override
	public java.lang.String getPrefix () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Prefix);
	}

	/** Set Nummernfolge jedes Jahr neu beginnen.
		@param StartNewYear 
		Restart the sequence with Start on every 1/1
	  */
	@Override
	public void setStartNewYear (boolean StartNewYear)
	{
		set_Value (COLUMNNAME_StartNewYear, Boolean.valueOf(StartNewYear));
	}

	/** Get Nummernfolge jedes Jahr neu beginnen.
		@return Restart the sequence with Start on every 1/1
	  */
	@Override
	public boolean isStartNewYear () 
	{
		Object oo = get_Value(COLUMNNAME_StartNewYear);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Start No.
		@param StartNo 
		Starting number/position
	  */
	@Override
	public void setStartNo (int StartNo)
	{
		set_Value (COLUMNNAME_StartNo, Integer.valueOf(StartNo));
	}

	/** Get Start No.
		@return Starting number/position
	  */
	@Override
	public int getStartNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_StartNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Suffix.
		@param Suffix 
		Suffix after the number
	  */
	@Override
	public void setSuffix (java.lang.String Suffix)
	{
		set_Value (COLUMNNAME_Suffix, Suffix);
	}

	/** Get Suffix.
		@return Suffix after the number
	  */
	@Override
	public java.lang.String getSuffix () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Suffix);
	}

	/** Set Value Format.
		@param VFormat 
		Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	@Override
	public void setVFormat (java.lang.String VFormat)
	{
		set_Value (COLUMNNAME_VFormat, VFormat);
	}

	/** Get Value Format.
		@return Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	@Override
	public java.lang.String getVFormat () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VFormat);
	}
}