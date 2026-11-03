// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Flatrate_RefundConfig_PackingOption
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Flatrate_RefundConfig_PackingOption extends org.compiere.model.PO implements I_C_Flatrate_RefundConfig_PackingOption, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1500422952L;

    /** Standard Constructor */
    public X_C_Flatrate_RefundConfig_PackingOption (final Properties ctx, final int C_Flatrate_RefundConfig_PackingOption_ID, @Nullable final String trxName)
    {
      super (ctx, C_Flatrate_RefundConfig_PackingOption_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Flatrate_RefundConfig_PackingOption (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Flatrate_Conditions_ID (final int C_Flatrate_Conditions_ID)
	{
		if (C_Flatrate_Conditions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Conditions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Conditions_ID, C_Flatrate_Conditions_ID);
	}

	@Override
	public int getC_Flatrate_Conditions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Conditions_ID);
	}

	@Override
	public void setC_Flatrate_RefundConfig_ID (final int C_Flatrate_RefundConfig_ID)
	{
		if (C_Flatrate_RefundConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_RefundConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_RefundConfig_ID, C_Flatrate_RefundConfig_ID);
	}

	@Override
	public int getC_Flatrate_RefundConfig_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_RefundConfig_ID);
	}

	@Override
	public void setC_Flatrate_RefundConfig_PackingOption_ID (final int C_Flatrate_RefundConfig_PackingOption_ID)
	{
		if (C_Flatrate_RefundConfig_PackingOption_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_RefundConfig_PackingOption_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_RefundConfig_PackingOption_ID, C_Flatrate_RefundConfig_PackingOption_ID);
	}

	@Override
	public int getC_Flatrate_RefundConfig_PackingOption_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_RefundConfig_PackingOption_ID);
	}

	@Override
	public void setM_HU_PackingMaterial_ID (final int M_HU_PackingMaterial_ID)
	{
		if (M_HU_PackingMaterial_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PackingMaterial_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PackingMaterial_ID, M_HU_PackingMaterial_ID);
	}

	@Override
	public int getM_HU_PackingMaterial_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PackingMaterial_ID);
	}
}