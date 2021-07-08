// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for RV_M_HU_Storage_InvoiceHistory
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_RV_M_HU_Storage_InvoiceHistory extends org.compiere.model.PO implements I_RV_M_HU_Storage_InvoiceHistory, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 284018895L;

    /** Standard Constructor */
    public X_RV_M_HU_Storage_InvoiceHistory (final Properties ctx, final int RV_M_HU_Storage_InvoiceHistory_ID, @Nullable final String trxName)
    {
      super (ctx, RV_M_HU_Storage_InvoiceHistory_ID, trxName);
    }

    /** Load Constructor */
    public X_RV_M_HU_Storage_InvoiceHistory (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setHUStorageASIKey (final @Nullable java.lang.String HUStorageASIKey)
	{
		set_ValueNoCheck (COLUMNNAME_HUStorageASIKey, HUStorageASIKey);
	}

	@Override
	public java.lang.String getHUStorageASIKey() 
	{
		return get_ValueAsString(COLUMNNAME_HUStorageASIKey);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
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
	public void setQtyOnHand (final @Nullable BigDecimal QtyOnHand)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	@Override
	public BigDecimal getQtyOnHand() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOnHand);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered (final @Nullable BigDecimal QtyOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReserved (final @Nullable BigDecimal QtyReserved)
	{
		set_ValueNoCheck (COLUMNNAME_QtyReserved, QtyReserved);
	}

	@Override
	public BigDecimal getQtyReserved() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReserved);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}