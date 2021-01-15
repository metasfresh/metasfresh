// Generated Model - DO NOT CHANGE
package de.metas.servicerepair.repository.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_Repair_Consumption_Summary
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_Repair_Consumption_Summary extends org.compiere.model.PO implements I_C_Project_Repair_Consumption_Summary, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 866752045L;

    /** Standard Constructor */
    public X_C_Project_Repair_Consumption_Summary (final Properties ctx, final int C_Project_Repair_Consumption_Summary_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_Repair_Consumption_Summary_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_Repair_Consumption_Summary (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public void setC_Project_Repair_Consumption_Summary_ID (final int C_Project_Repair_Consumption_Summary_ID)
	{
		if (C_Project_Repair_Consumption_Summary_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_Repair_Consumption_Summary_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_Repair_Consumption_Summary_ID, C_Project_Repair_Consumption_Summary_ID);
	}

	@Override
	public int getC_Project_Repair_Consumption_Summary_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_Repair_Consumption_Summary_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
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

	@Override
	public void setQtyConsumed (final BigDecimal QtyConsumed)
	{
		set_Value (COLUMNNAME_QtyConsumed, QtyConsumed);
	}

	@Override
	public BigDecimal getQtyConsumed() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyConsumed);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReserved (final BigDecimal QtyReserved)
	{
		set_Value (COLUMNNAME_QtyReserved, QtyReserved);
	}

	@Override
	public BigDecimal getQtyReserved() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReserved);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}