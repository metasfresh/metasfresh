// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_LeichMehl_ProductMapping
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_LeichMehl_ProductMapping extends org.compiere.model.PO implements I_ExternalSystem_Config_LeichMehl_ProductMapping, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 1395078978L;

	/** Standard Constructor */
	public X_ExternalSystem_Config_LeichMehl_ProductMapping (final Properties ctx, final int ExternalSystem_Config_LeichMehl_ProductMapping_ID, @Nullable final String trxName)
	{
		super (ctx, ExternalSystem_Config_LeichMehl_ProductMapping_ID, trxName);
	}

	/** Load Constructor */
	public X_ExternalSystem_Config_LeichMehl_ProductMapping (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setExternalSystem_Config_LeichMehl_ProductMapping_ID (final int ExternalSystem_Config_LeichMehl_ProductMapping_ID)
	{
		if (ExternalSystem_Config_LeichMehl_ProductMapping_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_LeichMehl_ProductMapping_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_LeichMehl_ProductMapping_ID, ExternalSystem_Config_LeichMehl_ProductMapping_ID);
	}

	@Override
	public int getExternalSystem_Config_LeichMehl_ProductMapping_ID()
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_LeichMehl_ProductMapping_ID);
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
			set_Value (COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID, null);
		else
			set_Value (COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID, LeichMehl_PluFile_ConfigGroup_ID);
	}

	@Override
	public int getLeichMehl_PluFile_ConfigGroup_ID()
	{
		return get_ValueAsInt(COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setPLU_File (final java.lang.String PLU_File)
	{
		set_Value (COLUMNNAME_PLU_File, PLU_File);
	}

	@Override
	public java.lang.String getPLU_File()
	{
		return get_ValueAsString(COLUMNNAME_PLU_File);
	}
}