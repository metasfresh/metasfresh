// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_Data
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Flatrate_Data extends org.compiere.model.PO implements I_C_Flatrate_Data, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -691275621L;

    /** Standard Constructor */
    public X_C_Flatrate_Data (final Properties ctx, final int C_Flatrate_Data_ID, @Nullable final String trxName)
    {
      super (ctx, C_Flatrate_Data_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Flatrate_Data (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Flatrate_DataEntry_IncludedT (final @Nullable java.lang.String C_Flatrate_DataEntry_IncludedT)
	{
		set_Value (COLUMNNAME_C_Flatrate_DataEntry_IncludedT, C_Flatrate_DataEntry_IncludedT);
	}

	@Override
	public java.lang.String getC_Flatrate_DataEntry_IncludedT() 
	{
		return get_ValueAsString(COLUMNNAME_C_Flatrate_DataEntry_IncludedT);
	}

	@Override
	public void setC_Flatrate_Data_ID (final int C_Flatrate_Data_ID)
	{
		if (C_Flatrate_Data_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Data_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Data_ID, C_Flatrate_Data_ID);
	}

	@Override
	public int getC_Flatrate_Data_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Data_ID);
	}

	@Override
	public void setHasContracts (final boolean HasContracts)
	{
		set_Value (COLUMNNAME_HasContracts, HasContracts);
	}

	@Override
	public boolean isHasContracts() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HasContracts);
	}
}