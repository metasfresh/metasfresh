// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for LeichMehl_PluFile_ConfigGroup
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_LeichMehl_PluFile_ConfigGroup extends org.compiere.model.PO implements I_LeichMehl_PluFile_ConfigGroup, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1053984394L;

    /** Standard Constructor */
    public X_LeichMehl_PluFile_ConfigGroup (final Properties ctx, final int LeichMehl_PluFile_ConfigGroup_ID, @Nullable final String trxName)
    {
      super (ctx, LeichMehl_PluFile_ConfigGroup_ID, trxName);
    }

    /** Load Constructor */
    public X_LeichMehl_PluFile_ConfigGroup (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Process getAD_Process_CustomQuery()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_CustomQuery_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process_CustomQuery(final org.compiere.model.I_AD_Process AD_Process_CustomQuery)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_CustomQuery_ID, org.compiere.model.I_AD_Process.class, AD_Process_CustomQuery);
	}

	@Override
	public void setAD_Process_CustomQuery_ID (final int AD_Process_CustomQuery_ID)
	{
		if (AD_Process_CustomQuery_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_CustomQuery_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_CustomQuery_ID, AD_Process_CustomQuery_ID);
	}

	@Override
	public int getAD_Process_CustomQuery_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_CustomQuery_ID);
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
	public void setIsAdditionalCustomQuery (final boolean IsAdditionalCustomQuery)
	{
		set_Value (COLUMNNAME_IsAdditionalCustomQuery, IsAdditionalCustomQuery);
	}

	@Override
	public boolean isAdditionalCustomQuery() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAdditionalCustomQuery);
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
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}
}