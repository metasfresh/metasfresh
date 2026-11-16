// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_PI_GRAI
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_PI_GRAI extends org.compiere.model.PO implements I_M_HU_PI_GRAI, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -222282277L;

    /** Standard Constructor */
    public X_M_HU_PI_GRAI (final Properties ctx, final int M_HU_PI_GRAI_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_PI_GRAI_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_PI_GRAI (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setGRAI_AssetType (final java.lang.String GRAI_AssetType)
	{
		set_Value (COLUMNNAME_GRAI_AssetType, GRAI_AssetType);
	}

	@Override
	public java.lang.String getGRAI_AssetType() 
	{
		return get_ValueAsString(COLUMNNAME_GRAI_AssetType);
	}

	@Override
	public void setGRAI_CompanyPrefix (final java.lang.String GRAI_CompanyPrefix)
	{
		set_Value (COLUMNNAME_GRAI_CompanyPrefix, GRAI_CompanyPrefix);
	}

	@Override
	public java.lang.String getGRAI_CompanyPrefix() 
	{
		return get_ValueAsString(COLUMNNAME_GRAI_CompanyPrefix);
	}

	@Override
	public void setM_HU_PI_GRAI_ID (final int M_HU_PI_GRAI_ID)
	{
		if (M_HU_PI_GRAI_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_GRAI_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_GRAI_ID, M_HU_PI_GRAI_ID);
	}

	@Override
	public int getM_HU_PI_GRAI_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_GRAI_ID);
	}

	@Override
	public void setM_HU_PI_ID (final int M_HU_PI_ID)
	{
		if (M_HU_PI_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_ID, M_HU_PI_ID);
	}

	@Override
	public int getM_HU_PI_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_ID);
	}
}