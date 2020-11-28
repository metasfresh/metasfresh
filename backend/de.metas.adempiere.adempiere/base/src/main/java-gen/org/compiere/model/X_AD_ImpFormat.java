// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_ImpFormat
 *  @author metasfresh (generated) 
 */
public class X_AD_ImpFormat extends org.compiere.model.PO implements I_AD_ImpFormat, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1897064103L;

    /** Standard Constructor */
    public X_AD_ImpFormat (final Properties ctx, final int AD_ImpFormat_ID, @Nullable final String trxName)
    {
      super (ctx, AD_ImpFormat_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_ImpFormat (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_ImpFormat_ID (final int AD_ImpFormat_ID)
	{
		if (AD_ImpFormat_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_ImpFormat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_ImpFormat_ID, AD_ImpFormat_ID);
	}

	@Override
	public int getAD_ImpFormat_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_ImpFormat_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * FileCharset AD_Reference_ID=541224
	 * Reference name: FileCharset
	 */
	public static final int FILECHARSET_AD_Reference_ID=541224;
	/** UTF-8 = utf-8 */
	public static final String FILECHARSET_UTF_8 = "utf-8";
	/** Windows-1252 = Windows-1252 */
	public static final String FILECHARSET_Windows_1252 = "Windows-1252";
	@Override
	public void setFileCharset (final java.lang.String FileCharset)
	{
		set_Value (COLUMNNAME_FileCharset, FileCharset);
	}

	@Override
	public java.lang.String getFileCharset() 
	{
		return get_ValueAsString(COLUMNNAME_FileCharset);
	}

	/** 
	 * FormatType AD_Reference_ID=209
	 * Reference name: AD_ImpFormat FormatType
	 */
	public static final int FORMATTYPE_AD_Reference_ID=209;
	/** Fixed Position = F */
	public static final String FORMATTYPE_FixedPosition = "F";
	/** CommaSeparated = C */
	public static final String FORMATTYPE_CommaSeparated = "C";
	/** TabSeparated = T */
	public static final String FORMATTYPE_TabSeparated = "T";
	/** XML = X */
	public static final String FORMATTYPE_XML = "X";
	/** SemicolonSeparated = S */
	public static final String FORMATTYPE_SemicolonSeparated = "S";
	@Override
	public void setFormatType (final java.lang.String FormatType)
	{
		set_Value (COLUMNNAME_FormatType, FormatType);
	}

	@Override
	public java.lang.String getFormatType() 
	{
		return get_ValueAsString(COLUMNNAME_FormatType);
	}

	@Override
	public void setIsManualImport (final boolean IsManualImport)
	{
		set_Value (COLUMNNAME_IsManualImport, IsManualImport);
	}

	@Override
	public boolean isManualImport() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManualImport);
	}

	@Override
	public void setIsMultiLine (final boolean IsMultiLine)
	{
		set_Value (COLUMNNAME_IsMultiLine, IsMultiLine);
	}

	@Override
	public boolean isMultiLine() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMultiLine);
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
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setSkipFirstNRows (final int SkipFirstNRows)
	{
		set_Value (COLUMNNAME_SkipFirstNRows, SkipFirstNRows);
	}

	@Override
	public int getSkipFirstNRows() 
	{
		return get_ValueAsInt(COLUMNNAME_SkipFirstNRows);
	}
}