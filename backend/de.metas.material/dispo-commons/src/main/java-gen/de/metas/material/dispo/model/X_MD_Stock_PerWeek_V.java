// Generated Model - DO NOT CHANGE
package de.metas.material.dispo.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for MD_Stock_PerWeek_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MD_Stock_PerWeek_V extends org.compiere.model.PO implements I_MD_Stock_PerWeek_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1386846925L;

    /** Standard Constructor */
    public X_MD_Stock_PerWeek_V (final Properties ctx, final int MD_Stock_PerWeek_V_ID, @Nullable final String trxName)
    {
      super (ctx, MD_Stock_PerWeek_V_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Stock_PerWeek_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setQtyATP (final @Nullable BigDecimal QtyATP)
	{
		set_ValueNoCheck (COLUMNNAME_QtyATP, QtyATP);
	}

	@Override
	public BigDecimal getQtyATP() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyATP);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyExpectedReceipts (final @Nullable BigDecimal QtyExpectedReceipts)
	{
		set_ValueNoCheck (COLUMNNAME_QtyExpectedReceipts, QtyExpectedReceipts);
	}

	@Override
	public BigDecimal getQtyExpectedReceipts() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyExpectedReceipts);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyExpectedShipments (final @Nullable BigDecimal QtyExpectedShipments)
	{
		set_ValueNoCheck (COLUMNNAME_QtyExpectedShipments, QtyExpectedShipments);
	}

	@Override
	public BigDecimal getQtyExpectedShipments() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyExpectedShipments);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setWeekStartDate (final @Nullable java.sql.Timestamp WeekStartDate)
	{
		set_ValueNoCheck (COLUMNNAME_WeekStartDate, WeekStartDate);
	}

	@Override
	public java.sql.Timestamp getWeekStartDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_WeekStartDate);
	}
}