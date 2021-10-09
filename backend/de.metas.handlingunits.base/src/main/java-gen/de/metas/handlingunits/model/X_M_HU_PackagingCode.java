// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_PackagingCode
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_PackagingCode extends org.compiere.model.PO implements I_M_HU_PackagingCode, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1348078474L;

    /** Standard Constructor */
    public X_M_HU_PackagingCode (final Properties ctx, final int M_HU_PackagingCode_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_PackagingCode_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_PackagingCode (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	/** 
	 * HU_UnitType AD_Reference_ID=540472
	 * Reference name: HU_UnitType
	 */
	public static final int HU_UNITTYPE_AD_Reference_ID=540472;
	/** TransportUnit = TU */
	public static final String HU_UNITTYPE_TransportUnit = "TU";
	/** LoadLogistiqueUnit = LU */
	public static final String HU_UNITTYPE_LoadLogistiqueUnit = "LU";
	/** VirtualPI = V */
	public static final String HU_UNITTYPE_VirtualPI = "V";
	@Override
	public void setHU_UnitType (final @Nullable java.lang.String HU_UnitType)
	{
		set_Value (COLUMNNAME_HU_UnitType, HU_UnitType);
	}

	@Override
	public java.lang.String getHU_UnitType() 
	{
		return get_ValueAsString(COLUMNNAME_HU_UnitType);
	}

	@Override
	public void setM_HU_PackagingCode_ID (final int M_HU_PackagingCode_ID)
	{
		if (M_HU_PackagingCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PackagingCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PackagingCode_ID, M_HU_PackagingCode_ID);
	}

	@Override
	public int getM_HU_PackagingCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PackagingCode_ID);
	}

	@Override
	public void setPackagingCode (final java.lang.String PackagingCode)
	{
		set_Value (COLUMNNAME_PackagingCode, PackagingCode);
	}

	@Override
	public java.lang.String getPackagingCode() 
	{
		return get_ValueAsString(COLUMNNAME_PackagingCode);
	}
}