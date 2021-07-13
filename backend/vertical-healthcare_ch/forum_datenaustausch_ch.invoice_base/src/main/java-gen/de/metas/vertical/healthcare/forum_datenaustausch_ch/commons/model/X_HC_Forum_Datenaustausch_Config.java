/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for HC_Forum_Datenaustausch_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_HC_Forum_Datenaustausch_Config extends org.compiere.model.PO implements I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1798838991L;

    /** Standard Constructor */
    public X_HC_Forum_Datenaustausch_Config (final Properties ctx, final int HC_Forum_Datenaustausch_Config_ID, final String trxName)
    {
      super (ctx, HC_Forum_Datenaustausch_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_HC_Forum_Datenaustausch_Config (final Properties ctx, final ResultSet rs, final String trxName)
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
	public void setBill_BPartner_ID (final int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Bill_BPartner_ID);
	}

	@Override
	public int getBill_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_BPartner_ID);
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
	 * ExportedXmlMode AD_Reference_ID=540940
	 * Reference name: ExportedXmlMode
	 */
	public static final int EXPORTEDXMLMODE_AD_Reference_ID=540940;
	/** production = P */
	public static final String EXPORTEDXMLMODE_Production = "P";
	/** test = T */
	public static final String EXPORTEDXMLMODE_Test = "T";
	@Override
	public void setExportedXmlMode (final java.lang.String ExportedXmlMode)
	{
		set_Value (COLUMNNAME_ExportedXmlMode, ExportedXmlMode);
	}

	@Override
	public java.lang.String getExportedXmlMode() 
	{
		return get_ValueAsString(COLUMNNAME_ExportedXmlMode);
	}

	/** 
	 * ExportedXmlVersion AD_Reference_ID=540921
	 * Reference name: ExportXmlVersion
	 */
	public static final int EXPORTEDXMLVERSION_AD_Reference_ID=540921;
	/** v440 = 4.4 */
	public static final String EXPORTEDXMLVERSION_V440 = "4.4";
	@Override
	public void setExportedXmlVersion (final java.lang.String ExportedXmlVersion)
	{
		set_Value (COLUMNNAME_ExportedXmlVersion, ExportedXmlVersion);
	}

	@Override
	public java.lang.String getExportedXmlVersion() 
	{
		return get_ValueAsString(COLUMNNAME_ExportedXmlVersion);
	}

	@Override
	public void setFrom_EAN (final java.lang.String From_EAN)
	{
		set_Value (COLUMNNAME_From_EAN, From_EAN);
	}

	@Override
	public java.lang.String getFrom_EAN() 
	{
		return get_ValueAsString(COLUMNNAME_From_EAN);
	}

	@Override
	public void setHC_Forum_Datenaustausch_Config_ID (final int HC_Forum_Datenaustausch_Config_ID)
	{
		if (HC_Forum_Datenaustausch_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Forum_Datenaustausch_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Forum_Datenaustausch_Config_ID, HC_Forum_Datenaustausch_Config_ID);
	}

	@Override
	public int getHC_Forum_Datenaustausch_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HC_Forum_Datenaustausch_Config_ID);
	}

	/** 
	 * ImportedBPartnerLanguage AD_Reference_ID=327
	 * Reference name: AD_Language System
	 */
	public static final int IMPORTEDBPARTNERLANGUAGE_AD_Reference_ID=327;
	@Override
	public void setImportedBPartnerLanguage (final java.lang.String ImportedBPartnerLanguage)
	{
		set_Value (COLUMNNAME_ImportedBPartnerLanguage, ImportedBPartnerLanguage);
	}

	@Override
	public java.lang.String getImportedBPartnerLanguage() 
	{
		return get_ValueAsString(COLUMNNAME_ImportedBPartnerLanguage);
	}

	@Override
	public org.compiere.model.I_C_BP_Group getImportedMunicipalityBP_Group()
	{
		return get_ValueAsPO(COLUMNNAME_ImportedMunicipalityBP_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setImportedMunicipalityBP_Group(final org.compiere.model.I_C_BP_Group ImportedMunicipalityBP_Group)
	{
		set_ValueFromPO(COLUMNNAME_ImportedMunicipalityBP_Group_ID, org.compiere.model.I_C_BP_Group.class, ImportedMunicipalityBP_Group);
	}

	@Override
	public void setImportedMunicipalityBP_Group_ID (final int ImportedMunicipalityBP_Group_ID)
	{
		if (ImportedMunicipalityBP_Group_ID < 1) 
			set_Value (COLUMNNAME_ImportedMunicipalityBP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_ImportedMunicipalityBP_Group_ID, ImportedMunicipalityBP_Group_ID);
	}

	@Override
	public int getImportedMunicipalityBP_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ImportedMunicipalityBP_Group_ID);
	}

	@Override
	public org.compiere.model.I_C_BP_Group getImportedPartientBP_Group()
	{
		return get_ValueAsPO(COLUMNNAME_ImportedPartientBP_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setImportedPartientBP_Group(final org.compiere.model.I_C_BP_Group ImportedPartientBP_Group)
	{
		set_ValueFromPO(COLUMNNAME_ImportedPartientBP_Group_ID, org.compiere.model.I_C_BP_Group.class, ImportedPartientBP_Group);
	}

	@Override
	public void setImportedPartientBP_Group_ID (final int ImportedPartientBP_Group_ID)
	{
		if (ImportedPartientBP_Group_ID < 1) 
			set_Value (COLUMNNAME_ImportedPartientBP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_ImportedPartientBP_Group_ID, ImportedPartientBP_Group_ID);
	}

	@Override
	public int getImportedPartientBP_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ImportedPartientBP_Group_ID);
	}

	@Override
	public void setStoreDirectory (final java.lang.String StoreDirectory)
	{
		set_Value (COLUMNNAME_StoreDirectory, StoreDirectory);
	}

	@Override
	public java.lang.String getStoreDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_StoreDirectory);
	}

	@Override
	public void setVia_EAN (final java.lang.String Via_EAN)
	{
		set_Value (COLUMNNAME_Via_EAN, Via_EAN);
	}

	@Override
	public java.lang.String getVia_EAN() 
	{
		return get_ValueAsString(COLUMNNAME_Via_EAN);
	}
}