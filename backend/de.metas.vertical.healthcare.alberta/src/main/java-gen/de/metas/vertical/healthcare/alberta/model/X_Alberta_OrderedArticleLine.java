// Generated Model - DO NOT CHANGE
package de.metas.vertical.healthcare.alberta.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Alberta_OrderedArticleLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Alberta_OrderedArticleLine extends org.compiere.model.PO implements I_Alberta_OrderedArticleLine, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -854061715L;

    /** Standard Constructor */
    public X_Alberta_OrderedArticleLine (final Properties ctx, final int Alberta_OrderedArticleLine_ID, @Nullable final String trxName)
    {
      super (ctx, Alberta_OrderedArticleLine_ID, trxName);
    }

    /** Load Constructor */
    public X_Alberta_OrderedArticleLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public I_Alberta_Order getAlberta_Order()
	{
		return get_ValueAsPO(COLUMNNAME_Alberta_Order_ID, I_Alberta_Order.class);
	}

	@Override
	public void setAlberta_Order(final I_Alberta_Order Alberta_Order)
	{
		set_ValueFromPO(COLUMNNAME_Alberta_Order_ID, I_Alberta_Order.class, Alberta_Order);
	}

	@Override
	public void setAlberta_Order_ID (final int Alberta_Order_ID)
	{
		if (Alberta_Order_ID < 1) 
			set_Value (COLUMNNAME_Alberta_Order_ID, null);
		else 
			set_Value (COLUMNNAME_Alberta_Order_ID, Alberta_Order_ID);
	}

	@Override
	public int getAlberta_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Alberta_Order_ID);
	}

	@Override
	public void setAlberta_OrderedArticleLine_ID (final int Alberta_OrderedArticleLine_ID)
	{
		if (Alberta_OrderedArticleLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Alberta_OrderedArticleLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Alberta_OrderedArticleLine_ID, Alberta_OrderedArticleLine_ID);
	}

	@Override
	public int getAlberta_OrderedArticleLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Alberta_OrderedArticleLine_ID);
	}

	@Override
	public void setC_OLCand_ID (final int C_OLCand_ID)
	{
		if (C_OLCand_ID < 1) 
			set_Value (COLUMNNAME_C_OLCand_ID, null);
		else 
			set_Value (COLUMNNAME_C_OLCand_ID, C_OLCand_ID);
	}

	@Override
	public int getC_OLCand_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCand_ID);
	}

	@Override
	public void setDurationAmount (final @Nullable BigDecimal DurationAmount)
	{
		set_Value (COLUMNNAME_DurationAmount, DurationAmount);
	}

	@Override
	public BigDecimal getDurationAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DurationAmount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setExternalId (final String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public String getExternalId()
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setExternallyUpdatedAt (final @Nullable java.sql.Timestamp ExternallyUpdatedAt)
	{
		set_Value (COLUMNNAME_ExternallyUpdatedAt, ExternallyUpdatedAt);
	}

	@Override
	public java.sql.Timestamp getExternallyUpdatedAt() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ExternallyUpdatedAt);
	}

	@Override
	public void setIsPrivateSale (final boolean IsPrivateSale)
	{
		set_Value (COLUMNNAME_IsPrivateSale, IsPrivateSale);
	}

	@Override
	public boolean isPrivateSale() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrivateSale);
	}

	@Override
	public void setIsRentalEquipment (final boolean IsRentalEquipment)
	{
		set_Value (COLUMNNAME_IsRentalEquipment, IsRentalEquipment);
	}

	@Override
	public boolean isRentalEquipment() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRentalEquipment);
	}

	@Override
	public void setSalesLineId (final @Nullable String SalesLineId)
	{
		set_Value (COLUMNNAME_SalesLineId, SalesLineId);
	}

	@Override
	public String getSalesLineId()
	{
		return get_ValueAsString(COLUMNNAME_SalesLineId);
	}

	@Override
	public void setTimePeriod (final @Nullable BigDecimal TimePeriod)
	{
		set_Value (COLUMNNAME_TimePeriod, TimePeriod);
	}

	@Override
	public BigDecimal getTimePeriod() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TimePeriod);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUnit (final @Nullable String Unit)
	{
		set_Value (COLUMNNAME_Unit, Unit);
	}

	@Override
	public String getUnit()
	{
		return get_ValueAsString(COLUMNNAME_Unit);
	}
}