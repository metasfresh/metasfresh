// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for LeichMehl_PluFile_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_LeichMehl_PluFile_Config extends org.compiere.model.PO implements I_LeichMehl_PluFile_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 847609254L;

    /** Standard Constructor */
    public X_LeichMehl_PluFile_Config (final Properties ctx, final int LeichMehl_PluFile_Config_ID, @Nullable final String trxName)
    {
      super (ctx, LeichMehl_PluFile_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_LeichMehl_PluFile_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup getLeichMehl_PluFile_ConfigGroup()
	{
		return get_ValueAsPO(COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID, de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup.class);
	}

	@Override
	public void setLeichMehl_PluFile_ConfigGroup(final de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup LeichMehl_PluFile_ConfigGroup)
	{
		set_ValueFromPO(COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID, de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup.class, LeichMehl_PluFile_ConfigGroup);
	}

	@Override
	public void setLeichMehl_PluFile_ConfigGroup_ID (final int LeichMehl_PluFile_ConfigGroup_ID)
	{
		if (LeichMehl_PluFile_ConfigGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID, LeichMehl_PluFile_ConfigGroup_ID);
	}

	@Override
	public int getLeichMehl_PluFile_ConfigGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID);
	}

	@Override
	public void setLeichMehl_PluFile_Config_ID (final int LeichMehl_PluFile_Config_ID)
	{
		if (LeichMehl_PluFile_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LeichMehl_PluFile_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LeichMehl_PluFile_Config_ID, LeichMehl_PluFile_Config_ID);
	}

	@Override
	public int getLeichMehl_PluFile_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_LeichMehl_PluFile_Config_ID);
	}

	@Override
	public void setReplacement (final java.lang.String Replacement)
	{
		set_Value (COLUMNNAME_Replacement, Replacement);
	}

	@Override
	public java.lang.String getReplacement() 
	{
		return get_ValueAsString(COLUMNNAME_Replacement);
	}

	/** 
	 * ReplacementSource AD_Reference_ID=541598
	 * Reference name: ReplacementSourceList
	 */
	public static final int REPLACEMENTSOURCE_AD_Reference_ID=541598;
	/** Product = P */
	public static final String REPLACEMENTSOURCE_Product = "P";
	/** PPOrder = PP */
	public static final String REPLACEMENTSOURCE_PPOrder = "PP";
	@Override
	public void setReplacementSource (final java.lang.String ReplacementSource)
	{
		set_Value (COLUMNNAME_ReplacementSource, ReplacementSource);
	}

	@Override
	public java.lang.String getReplacementSource() 
	{
		return get_ValueAsString(COLUMNNAME_ReplacementSource);
	}

	@Override
	public void setReplaceRegExp (final @Nullable java.lang.String ReplaceRegExp)
	{
		set_Value (COLUMNNAME_ReplaceRegExp, ReplaceRegExp);
	}

	@Override
	public java.lang.String getReplaceRegExp() 
	{
		return get_ValueAsString(COLUMNNAME_ReplaceRegExp);
	}

	@Override
	public void setTargetFieldName (final java.lang.String TargetFieldName)
	{
		set_Value (COLUMNNAME_TargetFieldName, TargetFieldName);
	}

	@Override
	public java.lang.String getTargetFieldName() 
	{
		return get_ValueAsString(COLUMNNAME_TargetFieldName);
	}

	/** 
	 * TargetFieldType AD_Reference_ID=541611
	 * Reference name: AttributeTypeList
	 */
	public static final int TARGETFIELDTYPE_AD_Reference_ID=541611;
	/** textArea = textArea */
	public static final String TARGETFIELDTYPE_TextArea = "textArea";
	/** EAN13 = EAN13 */
	public static final String TARGETFIELDTYPE_EAN13 = "EAN13";
	/** EAN128 = EAN128 */
	public static final String TARGETFIELDTYPE_EAN128 = "EAN128";
	/** numberField = numberField */
	public static final String TARGETFIELDTYPE_NumberField = "numberField";
	/** date = date */
	public static final String TARGETFIELDTYPE_Date = "date";
	/** unitChar = unitChar */
	public static final String TARGETFIELDTYPE_UnitChar = "unitChar";
	/** graphic = graphic */
	public static final String TARGETFIELDTYPE_Graphic = "graphic";
	@Override
	public void setTargetFieldType (final java.lang.String TargetFieldType)
	{
		set_Value (COLUMNNAME_TargetFieldType, TargetFieldType);
	}

	@Override
	public java.lang.String getTargetFieldType() 
	{
		return get_ValueAsString(COLUMNNAME_TargetFieldType);
	}
}