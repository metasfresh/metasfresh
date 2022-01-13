// Generated Model - DO NOT CHANGE
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for S_ExternalProjectReference
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_ExternalProjectReference extends org.compiere.model.PO implements I_S_ExternalProjectReference, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1118222642L;

    /** Standard Constructor */
    public X_S_ExternalProjectReference (final Properties ctx, final int S_ExternalProjectReference_ID, @Nullable final String trxName)
    {
      super (ctx, S_ExternalProjectReference_ID, trxName);
    }

    /** Load Constructor */
    public X_S_ExternalProjectReference (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public void setExternalProjectOwner (final java.lang.String ExternalProjectOwner)
	{
		set_Value (COLUMNNAME_ExternalProjectOwner, ExternalProjectOwner);
	}

	@Override
	public java.lang.String getExternalProjectOwner() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalProjectOwner);
	}

	@Override
	public void setExternalReference (final java.lang.String ExternalReference)
	{
		set_Value (COLUMNNAME_ExternalReference, ExternalReference);
	}

	@Override
	public java.lang.String getExternalReference() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalReference);
	}

	@Override
	public void setExternalReferenceURL (final @Nullable java.lang.String ExternalReferenceURL)
	{
		set_Value (COLUMNNAME_ExternalReferenceURL, ExternalReferenceURL);
	}

	@Override
	public java.lang.String getExternalReferenceURL() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalReferenceURL);
	}

	/** 
	 * ExternalSystem AD_Reference_ID=541117
	 * Reference name: ExternalSystem
	 */
	public static final int EXTERNALSYSTEM_AD_Reference_ID=541117;
	/** Github = Github */
	public static final String EXTERNALSYSTEM_Github = "Github";
	/** Everhour = Everhour */
	public static final String EXTERNALSYSTEM_Everhour = "Everhour";
	/** ALBERTA = ALBERTA */
	public static final String EXTERNALSYSTEM_ALBERTA = "ALBERTA";
	/** Shopware6 = Shopware6 */
	public static final String EXTERNALSYSTEM_Shopware6 = "Shopware6";
	/** Other = Other */
	public static final String EXTERNALSYSTEM_Other = "Other";
	@Override
	public void setExternalSystem (final java.lang.String ExternalSystem)
	{
		set_Value (COLUMNNAME_ExternalSystem, ExternalSystem);
	}

	@Override
	public java.lang.String getExternalSystem() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystem);
	}

	/** 
	 * ProjectType AD_Reference_ID=541118
	 * Reference name: ExternalProjectType
	 */
	public static final int PROJECTTYPE_AD_Reference_ID=541118;
	/** Budget = Budget */
	public static final String PROJECTTYPE_Budget = "Budget";
	/** Development = Effort */
	public static final String PROJECTTYPE_Development = "Effort";
	@Override
	public void setProjectType (final java.lang.String ProjectType)
	{
		set_Value (COLUMNNAME_ProjectType, ProjectType);
	}

	@Override
	public java.lang.String getProjectType() 
	{
		return get_ValueAsString(COLUMNNAME_ProjectType);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setS_ExternalProjectReference_ID (final int S_ExternalProjectReference_ID)
	{
		if (S_ExternalProjectReference_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_ExternalProjectReference_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_ExternalProjectReference_ID, S_ExternalProjectReference_ID);
	}

	@Override
	public int getS_ExternalProjectReference_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_ExternalProjectReference_ID);
	}
}