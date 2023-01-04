// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Sequence
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Sequence extends org.compiere.model.PO implements I_AD_Sequence, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1692385967L;

    /** Standard Constructor */
    public X_AD_Sequence (final Properties ctx, final int AD_Sequence_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Sequence_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Sequence (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_Sequence_ID (final int AD_Sequence_ID)
	{
		if (AD_Sequence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Sequence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Sequence_ID, AD_Sequence_ID);
	}

	@Override
	public int getAD_Sequence_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Sequence_ID);
	}

	@Override
	public void setCurrentNext (final int CurrentNext)
	{
		set_Value (COLUMNNAME_CurrentNext, CurrentNext);
	}

	@Override
	public int getCurrentNext() 
	{
		return get_ValueAsInt(COLUMNNAME_CurrentNext);
	}

	@Override
	public void setCurrentNextSys (final int CurrentNextSys)
	{
		set_Value (COLUMNNAME_CurrentNextSys, CurrentNextSys);
	}

	@Override
	public int getCurrentNextSys() 
	{
		return get_ValueAsInt(COLUMNNAME_CurrentNextSys);
	}

	@Override
	public void setCustomSequenceNoProvider_JavaClass_ID (final int CustomSequenceNoProvider_JavaClass_ID)
	{
		if (CustomSequenceNoProvider_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_CustomSequenceNoProvider_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_CustomSequenceNoProvider_JavaClass_ID, CustomSequenceNoProvider_JavaClass_ID);
	}

	@Override
	public int getCustomSequenceNoProvider_JavaClass_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CustomSequenceNoProvider_JavaClass_ID);
	}

	@Override
	public void setDateColumn (final @Nullable java.lang.String DateColumn)
	{
		set_Value (COLUMNNAME_DateColumn, DateColumn);
	}

	@Override
	public java.lang.String getDateColumn() 
	{
		return get_ValueAsString(COLUMNNAME_DateColumn);
	}

	@Override
	public void setDecimalPattern (final @Nullable java.lang.String DecimalPattern)
	{
		set_Value (COLUMNNAME_DecimalPattern, DecimalPattern);
	}

	@Override
	public java.lang.String getDecimalPattern() 
	{
		return get_ValueAsString(COLUMNNAME_DecimalPattern);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setIncrementNo (final int IncrementNo)
	{
		set_Value (COLUMNNAME_IncrementNo, IncrementNo);
	}

	@Override
	public int getIncrementNo() 
	{
		return get_ValueAsInt(COLUMNNAME_IncrementNo);
	}

	@Override
	public void setIsAudited (final boolean IsAudited)
	{
		set_Value (COLUMNNAME_IsAudited, IsAudited);
	}

	@Override
	public boolean isAudited() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAudited);
	}

	@Override
	public void setIsAutoSequence (final boolean IsAutoSequence)
	{
		set_Value (COLUMNNAME_IsAutoSequence, IsAutoSequence);
	}

	@Override
	public boolean isAutoSequence() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoSequence);
	}

	@Override
	public void setIsTableID (final boolean IsTableID)
	{
		set_Value (COLUMNNAME_IsTableID, IsTableID);
	}

	@Override
	public boolean isTableID() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTableID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setPrefix (final @Nullable java.lang.String Prefix)
	{
		set_Value (COLUMNNAME_Prefix, Prefix);
	}

	@Override
	public java.lang.String getPrefix() 
	{
		return get_ValueAsString(COLUMNNAME_Prefix);
	}

	@Override
	public void setStartNewMonth (final boolean StartNewMonth)
	{
		set_Value (COLUMNNAME_StartNewMonth, StartNewMonth);
	}

	@Override
	public boolean isStartNewMonth() 
	{
		return get_ValueAsBoolean(COLUMNNAME_StartNewMonth);
	}

	@Override
	public void setStartNewYear (final boolean StartNewYear)
	{
		set_Value (COLUMNNAME_StartNewYear, StartNewYear);
	}

	@Override
	public boolean isStartNewYear() 
	{
		return get_ValueAsBoolean(COLUMNNAME_StartNewYear);
	}

	@Override
	public void setStartNo (final int StartNo)
	{
		set_Value (COLUMNNAME_StartNo, StartNo);
	}

	@Override
	public int getStartNo() 
	{
		return get_ValueAsInt(COLUMNNAME_StartNo);
	}

	@Override
	public void setSuffix (final @Nullable java.lang.String Suffix)
	{
		set_Value (COLUMNNAME_Suffix, Suffix);
	}

	@Override
	public java.lang.String getSuffix() 
	{
		return get_ValueAsString(COLUMNNAME_Suffix);
	}

	@Override
	public void setVFormat (final @Nullable java.lang.String VFormat)
	{
		set_Value (COLUMNNAME_VFormat, VFormat);
	}

	@Override
	public java.lang.String getVFormat() 
	{
		return get_ValueAsString(COLUMNNAME_VFormat);
	}
}