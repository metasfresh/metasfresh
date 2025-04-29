// Generated Model - DO NOT CHANGE
package de.metas.shipper.gateway.dhl.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DHL_ShipmentOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DHL_ShipmentOrder extends org.compiere.model.PO implements I_DHL_ShipmentOrder, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2019675086L;

    /** Standard Constructor */
    public X_DHL_ShipmentOrder (final Properties ctx, final int DHL_ShipmentOrder_ID, @Nullable final String trxName)
    {
      super (ctx, DHL_ShipmentOrder_ID, trxName);
    }

    /** Load Constructor */
    public X_DHL_ShipmentOrder (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAdditionalFee (final @Nullable BigDecimal AdditionalFee)
	{
		set_Value (COLUMNNAME_AdditionalFee, AdditionalFee);
	}

	@Override
	public BigDecimal getAdditionalFee() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AdditionalFee);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public org.compiere.model.I_C_Customs_Invoice getC_Customs_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Customs_Invoice_ID, org.compiere.model.I_C_Customs_Invoice.class);
	}

	@Override
	public void setC_Customs_Invoice(final org.compiere.model.I_C_Customs_Invoice C_Customs_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Customs_Invoice_ID, org.compiere.model.I_C_Customs_Invoice.class, C_Customs_Invoice);
	}

	@Override
	public void setC_Customs_Invoice_ID (final int C_Customs_Invoice_ID)
	{
		if (C_Customs_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Customs_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Customs_Invoice_ID, C_Customs_Invoice_ID);
	}

	@Override
	public int getC_Customs_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Customs_Invoice_ID);
	}

	@Override
	public org.compiere.model.I_C_Customs_Invoice_Line getC_Customs_Invoice_Line()
	{
		return get_ValueAsPO(COLUMNNAME_C_Customs_Invoice_Line_ID, org.compiere.model.I_C_Customs_Invoice_Line.class);
	}

	@Override
	public void setC_Customs_Invoice_Line(final org.compiere.model.I_C_Customs_Invoice_Line C_Customs_Invoice_Line)
	{
		set_ValueFromPO(COLUMNNAME_C_Customs_Invoice_Line_ID, org.compiere.model.I_C_Customs_Invoice_Line.class, C_Customs_Invoice_Line);
	}

	@Override
	public void setC_Customs_Invoice_Line_ID (final int C_Customs_Invoice_Line_ID)
	{
		if (C_Customs_Invoice_Line_ID < 1) 
			set_Value (COLUMNNAME_C_Customs_Invoice_Line_ID, null);
		else 
			set_Value (COLUMNNAME_C_Customs_Invoice_Line_ID, C_Customs_Invoice_Line_ID);
	}

	@Override
	public int getC_Customs_Invoice_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Customs_Invoice_Line_ID);
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
	public void setCustomsAmount (final int CustomsAmount)
	{
		set_Value (COLUMNNAME_CustomsAmount, CustomsAmount);
	}

	@Override
	public int getCustomsAmount() 
	{
		return get_ValueAsInt(COLUMNNAME_CustomsAmount);
	}

	@Override
	public void setCustomsTariffNumber (final @Nullable java.lang.String CustomsTariffNumber)
	{
		set_Value (COLUMNNAME_CustomsTariffNumber, CustomsTariffNumber);
	}

	@Override
	public java.lang.String getCustomsTariffNumber() 
	{
		return get_ValueAsString(COLUMNNAME_CustomsTariffNumber);
	}

	@Override
	public void setCustomsValue (final @Nullable BigDecimal CustomsValue)
	{
		set_Value (COLUMNNAME_CustomsValue, CustomsValue);
	}

	@Override
	public BigDecimal getCustomsValue() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CustomsValue);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDHL_HeightInCm (final int DHL_HeightInCm)
	{
		set_Value (COLUMNNAME_DHL_HeightInCm, DHL_HeightInCm);
	}

	@Override
	public int getDHL_HeightInCm() 
	{
		return get_ValueAsInt(COLUMNNAME_DHL_HeightInCm);
	}

	@Override
	public void setDHL_LengthInCm (final int DHL_LengthInCm)
	{
		set_Value (COLUMNNAME_DHL_LengthInCm, DHL_LengthInCm);
	}

	@Override
	public int getDHL_LengthInCm() 
	{
		return get_ValueAsInt(COLUMNNAME_DHL_LengthInCm);
	}

	@Override
	public void setDHL_Product (final @Nullable java.lang.String DHL_Product)
	{
		set_Value (COLUMNNAME_DHL_Product, DHL_Product);
	}

	@Override
	public java.lang.String getDHL_Product() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Product);
	}

	@Override
	public void setDHL_Receiver_City (final @Nullable java.lang.String DHL_Receiver_City)
	{
		set_Value (COLUMNNAME_DHL_Receiver_City, DHL_Receiver_City);
	}

	@Override
	public java.lang.String getDHL_Receiver_City() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Receiver_City);
	}

	@Override
	public void setDHL_Receiver_CountryISO2Code (final @Nullable java.lang.String DHL_Receiver_CountryISO2Code)
	{
		set_Value (COLUMNNAME_DHL_Receiver_CountryISO2Code, DHL_Receiver_CountryISO2Code);
	}

	@Override
	public java.lang.String getDHL_Receiver_CountryISO2Code() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Receiver_CountryISO2Code);
	}

	@Override
	public void setDHL_Receiver_CountryISO3Code (final @Nullable java.lang.String DHL_Receiver_CountryISO3Code)
	{
		set_Value (COLUMNNAME_DHL_Receiver_CountryISO3Code, DHL_Receiver_CountryISO3Code);
	}

	@Override
	public java.lang.String getDHL_Receiver_CountryISO3Code() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Receiver_CountryISO3Code);
	}

	@Override
	public void setDHL_Receiver_Email (final @Nullable java.lang.String DHL_Receiver_Email)
	{
		set_Value (COLUMNNAME_DHL_Receiver_Email, DHL_Receiver_Email);
	}

	@Override
	public java.lang.String getDHL_Receiver_Email() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Receiver_Email);
	}

	@Override
	public void setDHL_Receiver_Name1 (final @Nullable java.lang.String DHL_Receiver_Name1)
	{
		set_Value (COLUMNNAME_DHL_Receiver_Name1, DHL_Receiver_Name1);
	}

	@Override
	public java.lang.String getDHL_Receiver_Name1() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Receiver_Name1);
	}

	@Override
	public void setDHL_Receiver_Name2 (final @Nullable java.lang.String DHL_Receiver_Name2)
	{
		set_Value (COLUMNNAME_DHL_Receiver_Name2, DHL_Receiver_Name2);
	}

	@Override
	public java.lang.String getDHL_Receiver_Name2() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Receiver_Name2);
	}

	@Override
	public void setDHL_Receiver_Phone (final @Nullable java.lang.String DHL_Receiver_Phone)
	{
		set_Value (COLUMNNAME_DHL_Receiver_Phone, DHL_Receiver_Phone);
	}

	@Override
	public java.lang.String getDHL_Receiver_Phone() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Receiver_Phone);
	}

	@Override
	public void setDHL_Receiver_StreetName1 (final @Nullable java.lang.String DHL_Receiver_StreetName1)
	{
		set_Value (COLUMNNAME_DHL_Receiver_StreetName1, DHL_Receiver_StreetName1);
	}

	@Override
	public java.lang.String getDHL_Receiver_StreetName1() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Receiver_StreetName1);
	}

	@Override
	public void setDHL_Receiver_StreetName2 (final @Nullable java.lang.String DHL_Receiver_StreetName2)
	{
		set_Value (COLUMNNAME_DHL_Receiver_StreetName2, DHL_Receiver_StreetName2);
	}

	@Override
	public java.lang.String getDHL_Receiver_StreetName2() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Receiver_StreetName2);
	}

	@Override
	public void setDHL_Receiver_StreetNumber (final @Nullable java.lang.String DHL_Receiver_StreetNumber)
	{
		set_Value (COLUMNNAME_DHL_Receiver_StreetNumber, DHL_Receiver_StreetNumber);
	}

	@Override
	public java.lang.String getDHL_Receiver_StreetNumber() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Receiver_StreetNumber);
	}

	@Override
	public void setDHL_Receiver_ZipCode (final @Nullable java.lang.String DHL_Receiver_ZipCode)
	{
		set_Value (COLUMNNAME_DHL_Receiver_ZipCode, DHL_Receiver_ZipCode);
	}

	@Override
	public java.lang.String getDHL_Receiver_ZipCode() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Receiver_ZipCode);
	}

	@Override
	public void setDHL_RecipientEmailAddress (final @Nullable java.lang.String DHL_RecipientEmailAddress)
	{
		set_Value (COLUMNNAME_DHL_RecipientEmailAddress, DHL_RecipientEmailAddress);
	}

	@Override
	public java.lang.String getDHL_RecipientEmailAddress() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_RecipientEmailAddress);
	}

	@Override
	public void setDHL_ShipmentDate (final @Nullable java.lang.String DHL_ShipmentDate)
	{
		set_Value (COLUMNNAME_DHL_ShipmentDate, DHL_ShipmentDate);
	}

	@Override
	public java.lang.String getDHL_ShipmentDate() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_ShipmentDate);
	}

	@Override
	public void setDHL_ShipmentOrder_ID (final int DHL_ShipmentOrder_ID)
	{
		if (DHL_ShipmentOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DHL_ShipmentOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DHL_ShipmentOrder_ID, DHL_ShipmentOrder_ID);
	}

	@Override
	public int getDHL_ShipmentOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DHL_ShipmentOrder_ID);
	}

	@Override
	public de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest getDHL_ShipmentOrderRequest()
	{
		return get_ValueAsPO(COLUMNNAME_DHL_ShipmentOrderRequest_ID, de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest.class);
	}

	@Override
	public void setDHL_ShipmentOrderRequest(final de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest DHL_ShipmentOrderRequest)
	{
		set_ValueFromPO(COLUMNNAME_DHL_ShipmentOrderRequest_ID, de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest.class, DHL_ShipmentOrderRequest);
	}

	@Override
	public void setDHL_ShipmentOrderRequest_ID (final int DHL_ShipmentOrderRequest_ID)
	{
		if (DHL_ShipmentOrderRequest_ID < 1) 
			set_Value (COLUMNNAME_DHL_ShipmentOrderRequest_ID, null);
		else 
			set_Value (COLUMNNAME_DHL_ShipmentOrderRequest_ID, DHL_ShipmentOrderRequest_ID);
	}

	@Override
	public int getDHL_ShipmentOrderRequest_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DHL_ShipmentOrderRequest_ID);
	}

	@Override
	public void setDHL_Shipper_City (final @Nullable java.lang.String DHL_Shipper_City)
	{
		set_Value (COLUMNNAME_DHL_Shipper_City, DHL_Shipper_City);
	}

	@Override
	public java.lang.String getDHL_Shipper_City() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Shipper_City);
	}

	@Override
	public void setDHL_Shipper_CountryISO2Code (final @Nullable java.lang.String DHL_Shipper_CountryISO2Code)
	{
		set_Value (COLUMNNAME_DHL_Shipper_CountryISO2Code, DHL_Shipper_CountryISO2Code);
	}

	@Override
	public java.lang.String getDHL_Shipper_CountryISO2Code() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Shipper_CountryISO2Code);
	}

	@Override
	public void setDHL_Shipper_CountryISO3Code (final @Nullable java.lang.String DHL_Shipper_CountryISO3Code)
	{
		set_Value (COLUMNNAME_DHL_Shipper_CountryISO3Code, DHL_Shipper_CountryISO3Code);
	}

	@Override
	public java.lang.String getDHL_Shipper_CountryISO3Code() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Shipper_CountryISO3Code);
	}

	@Override
	public void setDHL_Shipper_Name1 (final @Nullable java.lang.String DHL_Shipper_Name1)
	{
		set_Value (COLUMNNAME_DHL_Shipper_Name1, DHL_Shipper_Name1);
	}

	@Override
	public java.lang.String getDHL_Shipper_Name1() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Shipper_Name1);
	}

	@Override
	public void setDHL_Shipper_Name2 (final @Nullable java.lang.String DHL_Shipper_Name2)
	{
		set_Value (COLUMNNAME_DHL_Shipper_Name2, DHL_Shipper_Name2);
	}

	@Override
	public java.lang.String getDHL_Shipper_Name2() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Shipper_Name2);
	}

	@Override
	public void setDHL_Shipper_StreetName1 (final @Nullable java.lang.String DHL_Shipper_StreetName1)
	{
		set_Value (COLUMNNAME_DHL_Shipper_StreetName1, DHL_Shipper_StreetName1);
	}

	@Override
	public java.lang.String getDHL_Shipper_StreetName1() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Shipper_StreetName1);
	}

	@Override
	public void setDHL_Shipper_StreetName2 (final @Nullable java.lang.String DHL_Shipper_StreetName2)
	{
		set_Value (COLUMNNAME_DHL_Shipper_StreetName2, DHL_Shipper_StreetName2);
	}

	@Override
	public java.lang.String getDHL_Shipper_StreetName2() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Shipper_StreetName2);
	}

	@Override
	public void setDHL_Shipper_StreetNumber (final @Nullable java.lang.String DHL_Shipper_StreetNumber)
	{
		set_Value (COLUMNNAME_DHL_Shipper_StreetNumber, DHL_Shipper_StreetNumber);
	}

	@Override
	public java.lang.String getDHL_Shipper_StreetNumber() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Shipper_StreetNumber);
	}

	@Override
	public void setDHL_Shipper_ZipCode (final @Nullable java.lang.String DHL_Shipper_ZipCode)
	{
		set_Value (COLUMNNAME_DHL_Shipper_ZipCode, DHL_Shipper_ZipCode);
	}

	@Override
	public java.lang.String getDHL_Shipper_ZipCode() 
	{
		return get_ValueAsString(COLUMNNAME_DHL_Shipper_ZipCode);
	}

	@Override
	public void setDHL_WeightInKg (final BigDecimal DHL_WeightInKg)
	{
		set_Value (COLUMNNAME_DHL_WeightInKg, DHL_WeightInKg);
	}

	@Override
	public BigDecimal getDHL_WeightInKg() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DHL_WeightInKg);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDHL_WidthInCm (final int DHL_WidthInCm)
	{
		set_Value (COLUMNNAME_DHL_WidthInCm, DHL_WidthInCm);
	}

	@Override
	public int getDHL_WidthInCm() 
	{
		return get_ValueAsInt(COLUMNNAME_DHL_WidthInCm);
	}

	@Override
	public void setElectronicExportNotification (final @Nullable java.lang.String ElectronicExportNotification)
	{
		set_Value (COLUMNNAME_ElectronicExportNotification, ElectronicExportNotification);
	}

	@Override
	public java.lang.String getElectronicExportNotification() 
	{
		return get_ValueAsString(COLUMNNAME_ElectronicExportNotification);
	}

	@Override
	public void setExportType (final @Nullable java.lang.String ExportType)
	{
		set_Value (COLUMNNAME_ExportType, ExportType);
	}

	@Override
	public java.lang.String getExportType() 
	{
		return get_ValueAsString(COLUMNNAME_ExportType);
	}

	@Override
	public void setExportTypeDescription (final @Nullable java.lang.String ExportTypeDescription)
	{
		set_Value (COLUMNNAME_ExportTypeDescription, ExportTypeDescription);
	}

	@Override
	public java.lang.String getExportTypeDescription() 
	{
		return get_ValueAsString(COLUMNNAME_ExportTypeDescription);
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
	public org.compiere.model.I_M_Package getM_Package()
	{
		return get_ValueAsPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class);
	}

	@Override
	public void setM_Package(final org.compiere.model.I_M_Package M_Package)
	{
		set_ValueFromPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class, M_Package);
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
	public org.compiere.model.I_M_Shipper getM_Shipper()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(final org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
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
	public void setNetWeightKg (final @Nullable BigDecimal NetWeightKg)
	{
		set_Value (COLUMNNAME_NetWeightKg, NetWeightKg);
	}

	@Override
	public BigDecimal getNetWeightKg() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_NetWeightKg);
		return bd != null ? bd : BigDecimal.ZERO;
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
}