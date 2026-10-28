// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_CompensationGroup_ContractSettings_TakeOver_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_CompensationGroup_ContractSettings_TakeOver_Product extends org.compiere.model.PO implements I_C_CompensationGroup_ContractSettings_TakeOver_Product, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 386986184L;

    /** Standard Constructor */
    public X_C_CompensationGroup_ContractSettings_TakeOver_Product (final Properties ctx, final int C_CompensationGroup_ContractSettings_TakeOver_Product_ID, @Nullable final String trxName)
    {
      super (ctx, C_CompensationGroup_ContractSettings_TakeOver_Product_ID, trxName);
    }

    /** Load Constructor */
    public X_C_CompensationGroup_ContractSettings_TakeOver_Product (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_CompensationGroup_ContractSettings_TakeOver_ID (final int C_CompensationGroup_ContractSettings_TakeOver_ID)
	{
		if (C_CompensationGroup_ContractSettings_TakeOver_ID < 1) 
			set_Value (COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID, null);
		else 
			set_Value (COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID, C_CompensationGroup_ContractSettings_TakeOver_ID);
	}

	@Override
	public int getC_CompensationGroup_ContractSettings_TakeOver_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID);
	}

	@Override
	public void setC_CompensationGroup_ContractSettings_TakeOver_Product_ID (final int C_CompensationGroup_ContractSettings_TakeOver_Product_ID)
	{
		if (C_CompensationGroup_ContractSettings_TakeOver_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_Product_ID, C_CompensationGroup_ContractSettings_TakeOver_Product_ID);
	}

	@Override
	public int getC_CompensationGroup_ContractSettings_TakeOver_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_Product_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}
}