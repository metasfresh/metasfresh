// Generated Model - DO NOT CHANGE
package de.metas.vertical.healthcare.alberta.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Alberta_Order
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Alberta_Order extends org.compiere.model.PO implements I_Alberta_Order, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 1465961560L;

    /** Standard Constructor */
    public X_Alberta_Order (final Properties ctx, final int Alberta_Order_ID, @Nullable final String trxName)
    {
      super (ctx, Alberta_Order_ID, trxName);
    }

    /** Load Constructor */
    public X_Alberta_Order (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAlberta_Order_ID (final int Alberta_Order_ID)
	{
		if (Alberta_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Alberta_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Alberta_Order_ID, Alberta_Order_ID);
	}

	@Override
	public int getAlberta_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Alberta_Order_ID);
	}

	@Override
	public void setAnnotation (final @Nullable String Annotation)
	{
		set_Value (COLUMNNAME_Annotation, Annotation);
	}

	@Override
	public String getAnnotation()
	{
		return get_ValueAsString(COLUMNNAME_Annotation);
	}

	@Override
	public void setC_Doctor_BPartner_ID (final int C_Doctor_BPartner_ID)
	{
		if (C_Doctor_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_Doctor_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_Doctor_BPartner_ID, C_Doctor_BPartner_ID);
	}

	@Override
	public int getC_Doctor_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Doctor_BPartner_ID);
	}

	@Override
	public void setC_Pharmacy_BPartner_ID (final int C_Pharmacy_BPartner_ID)
	{
		if (C_Pharmacy_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_Pharmacy_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_Pharmacy_BPartner_ID, C_Pharmacy_BPartner_ID);
	}

	@Override
	public int getC_Pharmacy_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Pharmacy_BPartner_ID);
	}

	@Override
	public void setCreationDate (final @Nullable java.sql.Timestamp CreationDate)
	{
		set_Value (COLUMNNAME_CreationDate, CreationDate);
	}

	@Override
	public java.sql.Timestamp getCreationDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_CreationDate);
	}

	@Override
	public void setDayOfDelivery (final @Nullable BigDecimal DayOfDelivery)
	{
		set_Value (COLUMNNAME_DayOfDelivery, DayOfDelivery);
	}

	@Override
	public BigDecimal getDayOfDelivery() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DayOfDelivery);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDeliveryInfo (final @Nullable String DeliveryInfo)
	{
		set_Value (COLUMNNAME_DeliveryInfo, DeliveryInfo);
	}

	@Override
	public String getDeliveryInfo()
	{
		return get_ValueAsString(COLUMNNAME_DeliveryInfo);
	}

	@Override
	public void setDeliveryNote (final @Nullable String DeliveryNote)
	{
		set_Value (COLUMNNAME_DeliveryNote, DeliveryNote);
	}

	@Override
	public String getDeliveryNote()
	{
		return get_ValueAsString(COLUMNNAME_DeliveryNote);
	}

	@Override
	public void setEndDate (final @Nullable java.sql.Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	@Override
	public java.sql.Timestamp getEndDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EndDate);
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
	public void setIsArchived (final boolean IsArchived)
	{
		set_Value (COLUMNNAME_IsArchived, IsArchived);
	}

	@Override
	public boolean isArchived() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsArchived);
	}

	@Override
	public void setIsInitialCare (final boolean IsInitialCare)
	{
		set_Value (COLUMNNAME_IsInitialCare, IsInitialCare);
	}

	@Override
	public boolean isInitialCare() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInitialCare);
	}

	@Override
	public void setIsSeriesOrder (final boolean IsSeriesOrder)
	{
		set_Value (COLUMNNAME_IsSeriesOrder, IsSeriesOrder);
	}

	@Override
	public boolean isSeriesOrder() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSeriesOrder);
	}

	@Override
	public void setNextDelivery (final @Nullable java.sql.Timestamp NextDelivery)
	{
		set_Value (COLUMNNAME_NextDelivery, NextDelivery);
	}

	@Override
	public java.sql.Timestamp getNextDelivery() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_NextDelivery);
	}

	@Override
	public void setRootId (final @Nullable String RootId)
	{
		set_Value (COLUMNNAME_RootId, RootId);
	}

	@Override
	public String getRootId()
	{
		return get_ValueAsString(COLUMNNAME_RootId);
	}

	@Override
	public void setStartDate (final @Nullable java.sql.Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	@Override
	public java.sql.Timestamp getStartDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_StartDate);
	}
}