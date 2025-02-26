// Generated Model - DO NOT CHANGE
package de.metas.replenishment;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Material_Needs_Planner_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Material_Needs_Planner_V extends org.compiere.model.PO implements I_M_Material_Needs_Planner_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1700304408L;

    /** Standard Constructor */
    public X_M_Material_Needs_Planner_V (final Properties ctx, final int M_Material_Needs_Planner_V_ID, @Nullable final String trxName)
    {
      super (ctx, M_Material_Needs_Planner_V_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Material_Needs_Planner_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAverage_Qty_Last_Six_Weeks (final @Nullable BigDecimal Average_Qty_Last_Six_Weeks)
	{
		set_ValueNoCheck (COLUMNNAME_Average_Qty_Last_Six_Weeks, Average_Qty_Last_Six_Weeks);
	}

	@Override
	public BigDecimal getAverage_Qty_Last_Six_Weeks() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Average_Qty_Last_Six_Weeks);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDemand (final @Nullable BigDecimal Demand)
	{
		set_ValueNoCheck (COLUMNNAME_Demand, Demand);
	}

	@Override
	public BigDecimal getDemand() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Demand);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
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
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setQuantityOnHand (final @Nullable BigDecimal QuantityOnHand)
	{
		set_ValueNoCheck (COLUMNNAME_QuantityOnHand, QuantityOnHand);
	}

	@Override
	public BigDecimal getQuantityOnHand() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QuantityOnHand);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotal_Qty_Five_Weeks_Ago (final @Nullable BigDecimal Total_Qty_Five_Weeks_Ago)
	{
		set_ValueNoCheck (COLUMNNAME_Total_Qty_Five_Weeks_Ago, Total_Qty_Five_Weeks_Ago);
	}

	@Override
	public BigDecimal getTotal_Qty_Five_Weeks_Ago() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Total_Qty_Five_Weeks_Ago);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotal_Qty_Four_Weeks_Ago (final @Nullable BigDecimal Total_Qty_Four_Weeks_Ago)
	{
		set_ValueNoCheck (COLUMNNAME_Total_Qty_Four_Weeks_Ago, Total_Qty_Four_Weeks_Ago);
	}

	@Override
	public BigDecimal getTotal_Qty_Four_Weeks_Ago() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Total_Qty_Four_Weeks_Ago);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotal_Qty_One_Week_Ago (final @Nullable BigDecimal Total_Qty_One_Week_Ago)
	{
		set_ValueNoCheck (COLUMNNAME_Total_Qty_One_Week_Ago, Total_Qty_One_Week_Ago);
	}

	@Override
	public BigDecimal getTotal_Qty_One_Week_Ago() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Total_Qty_One_Week_Ago);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotal_Qty_Six_Weeks_Ago (final @Nullable BigDecimal Total_Qty_Six_Weeks_Ago)
	{
		set_ValueNoCheck (COLUMNNAME_Total_Qty_Six_Weeks_Ago, Total_Qty_Six_Weeks_Ago);
	}

	@Override
	public BigDecimal getTotal_Qty_Six_Weeks_Ago() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Total_Qty_Six_Weeks_Ago);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotal_Qty_Three_Weeks_Ago (final @Nullable BigDecimal Total_Qty_Three_Weeks_Ago)
	{
		set_ValueNoCheck (COLUMNNAME_Total_Qty_Three_Weeks_Ago, Total_Qty_Three_Weeks_Ago);
	}

	@Override
	public BigDecimal getTotal_Qty_Three_Weeks_Ago() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Total_Qty_Three_Weeks_Ago);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotal_Qty_Two_Weeks_Ago (final @Nullable BigDecimal Total_Qty_Two_Weeks_Ago)
	{
		set_ValueNoCheck (COLUMNNAME_Total_Qty_Two_Weeks_Ago, Total_Qty_Two_Weeks_Ago);
	}

	@Override
	public BigDecimal getTotal_Qty_Two_Weeks_Ago() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Total_Qty_Two_Weeks_Ago);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}