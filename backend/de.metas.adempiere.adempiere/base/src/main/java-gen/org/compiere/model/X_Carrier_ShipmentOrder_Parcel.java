// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Carrier_ShipmentOrder_Parcel
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Carrier_ShipmentOrder_Parcel extends org.compiere.model.PO implements I_Carrier_ShipmentOrder_Parcel, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1137276923L;

    /** Standard Constructor */
    public X_Carrier_ShipmentOrder_Parcel (final Properties ctx, final int Carrier_ShipmentOrder_Parcel_ID, @Nullable final String trxName)
    {
      super (ctx, Carrier_ShipmentOrder_Parcel_ID, trxName);
    }

    /** Load Constructor */
    public X_Carrier_ShipmentOrder_Parcel (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setawb (final @Nullable java.lang.String awb)
	{
		set_Value (COLUMNNAME_awb, awb);
	}

	@Override
	public java.lang.String getawb() 
	{
		return get_ValueAsString(COLUMNNAME_awb);
	}

	@Override
	public void setCarrier_ShipmentOrder_ID (final int Carrier_ShipmentOrder_ID)
	{
		if (Carrier_ShipmentOrder_ID < 1) 
			set_Value (COLUMNNAME_Carrier_ShipmentOrder_ID, null);
		else 
			set_Value (COLUMNNAME_Carrier_ShipmentOrder_ID, Carrier_ShipmentOrder_ID);
	}

	@Override
	public int getCarrier_ShipmentOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_ShipmentOrder_ID);
	}

	@Override
	public void setCarrier_ShipmentOrder_Parcel_ID (final int Carrier_ShipmentOrder_Parcel_ID)
	{
		if (Carrier_ShipmentOrder_Parcel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Carrier_ShipmentOrder_Parcel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Carrier_ShipmentOrder_Parcel_ID, Carrier_ShipmentOrder_Parcel_ID);
	}

	@Override
	public int getCarrier_ShipmentOrder_Parcel_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_ShipmentOrder_Parcel_ID);
	}

	@Override
	public void setHeightInCm (final int HeightInCm)
	{
		set_Value (COLUMNNAME_HeightInCm, HeightInCm);
	}

	@Override
	public int getHeightInCm() 
	{
		return get_ValueAsInt(COLUMNNAME_HeightInCm);
	}

	@Override
	public void setLengthInCm (final int LengthInCm)
	{
		set_Value (COLUMNNAME_LengthInCm, LengthInCm);
	}

	@Override
	public int getLengthInCm() 
	{
		return get_ValueAsInt(COLUMNNAME_LengthInCm);
	}

	@Override
	public void setM_Package_ID (final int M_Package_ID)
	{
		if (M_Package_ID < 1) 
			set_Value (COLUMNNAME_M_Package_ID, null);
		else 
			set_Value (COLUMNNAME_M_Package_ID, M_Package_ID);
	}

	@Override
	public int getM_Package_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Package_ID);
	}

	@Override
	public void setPackageDescription (final @Nullable java.lang.String PackageDescription)
	{
		set_Value (COLUMNNAME_PackageDescription, PackageDescription);
	}

	@Override
	public java.lang.String getPackageDescription() 
	{
		return get_ValueAsString(COLUMNNAME_PackageDescription);
	}

	@Override
	public void setPdfLabelData (final @Nullable byte[] PdfLabelData)
	{
		set_Value (COLUMNNAME_PdfLabelData, PdfLabelData);
	}

	@Override
	public byte[] getPdfLabelData() 
	{
		return (byte[])get_Value(COLUMNNAME_PdfLabelData);
	}

	@Override
	public void setTrackingURL (final @Nullable java.lang.String TrackingURL)
	{
		set_Value (COLUMNNAME_TrackingURL, TrackingURL);
	}

	@Override
	public java.lang.String getTrackingURL() 
	{
		return get_ValueAsString(COLUMNNAME_TrackingURL);
	}

	@Override
	public void setWeightInKg (final BigDecimal WeightInKg)
	{
		set_Value (COLUMNNAME_WeightInKg, WeightInKg);
	}

	@Override
	public BigDecimal getWeightInKg() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_WeightInKg);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setWidthInCm (final int WidthInCm)
	{
		set_Value (COLUMNNAME_WidthInCm, WidthInCm);
	}

	@Override
	public int getWidthInCm() 
	{
		return get_ValueAsInt(COLUMNNAME_WidthInCm);
	}
}