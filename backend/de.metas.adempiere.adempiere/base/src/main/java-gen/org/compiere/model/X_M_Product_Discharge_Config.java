// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Product_Discharge_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_Discharge_Config extends org.compiere.model.PO implements I_M_Product_Discharge_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -199998364L;

    /** Standard Constructor */
    public X_M_Product_Discharge_Config (final Properties ctx, final int M_Product_Discharge_Config_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_Discharge_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_Discharge_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_DocTypeInvoice_ID (final int C_DocTypeInvoice_ID)
	{
		if (C_DocTypeInvoice_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeInvoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeInvoice_ID, C_DocTypeInvoice_ID);
	}

	@Override
	public int getC_DocTypeInvoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeInvoice_ID);
	}

	@Override
	public void setC_DocTypeOrder_ID (final int C_DocTypeOrder_ID)
	{
		if (C_DocTypeOrder_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeOrder_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeOrder_ID, C_DocTypeOrder_ID);
	}

	@Override
	public int getC_DocTypeOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeOrder_ID);
	}

	@Override
	public void setC_UOM_Discharge_ID (final int C_UOM_Discharge_ID)
	{
		if (C_UOM_Discharge_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Discharge_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Discharge_ID, C_UOM_Discharge_ID);
	}

	@Override
	public int getC_UOM_Discharge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_Discharge_ID);
	}

	@Override
	public void setM_Product_Discharge_Config_ID (final int M_Product_Discharge_Config_ID)
	{
		if (M_Product_Discharge_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Discharge_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Discharge_Config_ID, M_Product_Discharge_Config_ID);
	}

	@Override
	public int getM_Product_Discharge_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Discharge_Config_ID);
	}

	@Override
	public void setM_Product_Discharge_ID (final int M_Product_Discharge_ID)
	{
		if (M_Product_Discharge_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Discharge_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Discharge_ID, M_Product_Discharge_ID);
	}

	@Override
	public int getM_Product_Discharge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Discharge_ID);
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