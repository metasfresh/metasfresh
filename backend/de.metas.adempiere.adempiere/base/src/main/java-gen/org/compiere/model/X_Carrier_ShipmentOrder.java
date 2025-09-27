// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Carrier_ShipmentOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Carrier_ShipmentOrder extends org.compiere.model.PO implements I_Carrier_ShipmentOrder, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 176503779L;

    /** Standard Constructor */
    public X_Carrier_ShipmentOrder (final Properties ctx, final int Carrier_ShipmentOrder_ID, @Nullable final String trxName)
    {
      super (ctx, Carrier_ShipmentOrder_ID, trxName);
    }

    /** Load Constructor */
    public X_Carrier_ShipmentOrder (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCarrier_Product (final java.lang.String Carrier_Product)
	{
		set_Value (COLUMNNAME_Carrier_Product, Carrier_Product);
	}

	@Override
	public java.lang.String getCarrier_Product() 
	{
		return get_ValueAsString(COLUMNNAME_Carrier_Product);
	}

	@Override
	public void setCarrier_ShipmentOrder_ID (final int Carrier_ShipmentOrder_ID)
	{
		if (Carrier_ShipmentOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Carrier_ShipmentOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Carrier_ShipmentOrder_ID, Carrier_ShipmentOrder_ID);
	}

	@Override
	public int getCarrier_ShipmentOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_ShipmentOrder_ID);
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
	public void setCustomerReference (final @Nullable java.lang.String CustomerReference)
	{
		set_Value (COLUMNNAME_CustomerReference, CustomerReference);
	}

	@Override
	public java.lang.String getCustomerReference() 
	{
		return get_ValueAsString(COLUMNNAME_CustomerReference);
	}

	@Override
	public void setInternationalDelivery (final boolean InternationalDelivery)
	{
		set_Value (COLUMNNAME_InternationalDelivery, InternationalDelivery);
	}

	@Override
	public boolean isInternationalDelivery() 
	{
		return get_ValueAsBoolean(COLUMNNAME_InternationalDelivery);
	}

	@Override
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setM_ShipperTransportation_ID (final int M_ShipperTransportation_ID)
	{
		if (M_ShipperTransportation_ID < 1) 
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, M_ShipperTransportation_ID);
	}

	@Override
	public int getM_ShipperTransportation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipperTransportation_ID);
	}

	@Override
	public void setReceiver_City (final @Nullable java.lang.String Receiver_City)
	{
		set_Value (COLUMNNAME_Receiver_City, Receiver_City);
	}

	@Override
	public java.lang.String getReceiver_City() 
	{
		return get_ValueAsString(COLUMNNAME_Receiver_City);
	}

	@Override
	public void setReceiver_CountryISO2Code (final @Nullable java.lang.String Receiver_CountryISO2Code)
	{
		set_Value (COLUMNNAME_Receiver_CountryISO2Code, Receiver_CountryISO2Code);
	}

	@Override
	public java.lang.String getReceiver_CountryISO2Code() 
	{
		return get_ValueAsString(COLUMNNAME_Receiver_CountryISO2Code);
	}

	@Override
	public void setReceiver_CountryISO3Code (final @Nullable java.lang.String Receiver_CountryISO3Code)
	{
		set_Value (COLUMNNAME_Receiver_CountryISO3Code, Receiver_CountryISO3Code);
	}

	@Override
	public java.lang.String getReceiver_CountryISO3Code() 
	{
		return get_ValueAsString(COLUMNNAME_Receiver_CountryISO3Code);
	}

	@Override
	public void setReceiver_Email (final @Nullable java.lang.String Receiver_Email)
	{
		set_Value (COLUMNNAME_Receiver_Email, Receiver_Email);
	}

	@Override
	public java.lang.String getReceiver_Email() 
	{
		return get_ValueAsString(COLUMNNAME_Receiver_Email);
	}

	@Override
	public void setReceiver_EORI (final @Nullable java.lang.String Receiver_EORI)
	{
		set_Value (COLUMNNAME_Receiver_EORI, Receiver_EORI);
	}

	@Override
	public java.lang.String getReceiver_EORI() 
	{
		return get_ValueAsString(COLUMNNAME_Receiver_EORI);
	}

	@Override
	public void setReceiver_Name1 (final @Nullable java.lang.String Receiver_Name1)
	{
		set_Value (COLUMNNAME_Receiver_Name1, Receiver_Name1);
	}

	@Override
	public java.lang.String getReceiver_Name1() 
	{
		return get_ValueAsString(COLUMNNAME_Receiver_Name1);
	}

	@Override
	public void setReceiver_Name2 (final @Nullable java.lang.String Receiver_Name2)
	{
		set_Value (COLUMNNAME_Receiver_Name2, Receiver_Name2);
	}

	@Override
	public java.lang.String getReceiver_Name2() 
	{
		return get_ValueAsString(COLUMNNAME_Receiver_Name2);
	}

	@Override
	public void setReceiver_Phone (final @Nullable java.lang.String Receiver_Phone)
	{
		set_Value (COLUMNNAME_Receiver_Phone, Receiver_Phone);
	}

	@Override
	public java.lang.String getReceiver_Phone() 
	{
		return get_ValueAsString(COLUMNNAME_Receiver_Phone);
	}

	@Override
	public void setReceiver_StreetName1 (final @Nullable java.lang.String Receiver_StreetName1)
	{
		set_Value (COLUMNNAME_Receiver_StreetName1, Receiver_StreetName1);
	}

	@Override
	public java.lang.String getReceiver_StreetName1() 
	{
		return get_ValueAsString(COLUMNNAME_Receiver_StreetName1);
	}

	@Override
	public void setReceiver_StreetName2 (final @Nullable java.lang.String Receiver_StreetName2)
	{
		set_Value (COLUMNNAME_Receiver_StreetName2, Receiver_StreetName2);
	}

	@Override
	public java.lang.String getReceiver_StreetName2() 
	{
		return get_ValueAsString(COLUMNNAME_Receiver_StreetName2);
	}

	@Override
	public void setReceiver_StreetNumber (final @Nullable java.lang.String Receiver_StreetNumber)
	{
		set_Value (COLUMNNAME_Receiver_StreetNumber, Receiver_StreetNumber);
	}

	@Override
	public java.lang.String getReceiver_StreetNumber() 
	{
		return get_ValueAsString(COLUMNNAME_Receiver_StreetNumber);
	}

	@Override
	public void setReceiver_ZipCode (final @Nullable java.lang.String Receiver_ZipCode)
	{
		set_Value (COLUMNNAME_Receiver_ZipCode, Receiver_ZipCode);
	}

	@Override
	public java.lang.String getReceiver_ZipCode() 
	{
		return get_ValueAsString(COLUMNNAME_Receiver_ZipCode);
	}

	@Override
	public void setShipmentDate (final java.sql.Timestamp ShipmentDate)
	{
		set_Value (COLUMNNAME_ShipmentDate, ShipmentDate);
	}

	@Override
	public java.sql.Timestamp getShipmentDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ShipmentDate);
	}

	@Override
	public void setShipper_City (final @Nullable java.lang.String Shipper_City)
	{
		set_Value (COLUMNNAME_Shipper_City, Shipper_City);
	}

	@Override
	public java.lang.String getShipper_City() 
	{
		return get_ValueAsString(COLUMNNAME_Shipper_City);
	}

	@Override
	public void setShipper_CountryISO2Code (final @Nullable java.lang.String Shipper_CountryISO2Code)
	{
		set_Value (COLUMNNAME_Shipper_CountryISO2Code, Shipper_CountryISO2Code);
	}

	@Override
	public java.lang.String getShipper_CountryISO2Code() 
	{
		return get_ValueAsString(COLUMNNAME_Shipper_CountryISO2Code);
	}

	@Override
	public void setShipper_CountryISO3Code (final @Nullable java.lang.String Shipper_CountryISO3Code)
	{
		set_Value (COLUMNNAME_Shipper_CountryISO3Code, Shipper_CountryISO3Code);
	}

	@Override
	public java.lang.String getShipper_CountryISO3Code() 
	{
		return get_ValueAsString(COLUMNNAME_Shipper_CountryISO3Code);
	}

	@Override
	public void setShipper_EORI (final @Nullable java.lang.String Shipper_EORI)
	{
		set_Value (COLUMNNAME_Shipper_EORI, Shipper_EORI);
	}

	@Override
	public java.lang.String getShipper_EORI() 
	{
		return get_ValueAsString(COLUMNNAME_Shipper_EORI);
	}

	@Override
	public void setShipper_Name1 (final @Nullable java.lang.String Shipper_Name1)
	{
		set_Value (COLUMNNAME_Shipper_Name1, Shipper_Name1);
	}

	@Override
	public java.lang.String getShipper_Name1() 
	{
		return get_ValueAsString(COLUMNNAME_Shipper_Name1);
	}

	@Override
	public void setShipper_Name2 (final @Nullable java.lang.String Shipper_Name2)
	{
		set_Value (COLUMNNAME_Shipper_Name2, Shipper_Name2);
	}

	@Override
	public java.lang.String getShipper_Name2() 
	{
		return get_ValueAsString(COLUMNNAME_Shipper_Name2);
	}

	@Override
	public void setShipper_StreetName1 (final @Nullable java.lang.String Shipper_StreetName1)
	{
		set_Value (COLUMNNAME_Shipper_StreetName1, Shipper_StreetName1);
	}

	@Override
	public java.lang.String getShipper_StreetName1() 
	{
		return get_ValueAsString(COLUMNNAME_Shipper_StreetName1);
	}

	@Override
	public void setShipper_StreetName2 (final @Nullable java.lang.String Shipper_StreetName2)
	{
		set_Value (COLUMNNAME_Shipper_StreetName2, Shipper_StreetName2);
	}

	@Override
	public java.lang.String getShipper_StreetName2() 
	{
		return get_ValueAsString(COLUMNNAME_Shipper_StreetName2);
	}

	@Override
	public void setShipper_StreetNumber (final @Nullable java.lang.String Shipper_StreetNumber)
	{
		set_Value (COLUMNNAME_Shipper_StreetNumber, Shipper_StreetNumber);
	}

	@Override
	public java.lang.String getShipper_StreetNumber() 
	{
		return get_ValueAsString(COLUMNNAME_Shipper_StreetNumber);
	}

	@Override
	public void setShipper_ZipCode (final @Nullable java.lang.String Shipper_ZipCode)
	{
		set_Value (COLUMNNAME_Shipper_ZipCode, Shipper_ZipCode);
	}

	@Override
	public java.lang.String getShipper_ZipCode() 
	{
		return get_ValueAsString(COLUMNNAME_Shipper_ZipCode);
	}
}