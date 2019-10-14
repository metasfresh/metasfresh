/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.dhl.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DHL_ShipmentOrder
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DHL_ShipmentOrder extends org.compiere.model.PO implements I_DHL_ShipmentOrder, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1123981938L;

    /** Standard Constructor */
    public X_DHL_ShipmentOrder (Properties ctx, int DHL_ShipmentOrder_ID, String trxName)
    {
      super (ctx, DHL_ShipmentOrder_ID, trxName);
      /** if (DHL_ShipmentOrder_ID == 0)
        {
			setDHL_HeightInCm (0); // 0
			setDHL_LengthInCm (0); // 0
			setDHL_ShipmentOrder_ID (0);
			setDHL_WeightInKg (BigDecimal.ZERO); // 0
			setDHL_WidthInCm (0); // 0
			setPackageId (0); // 0
        } */
    }

    /** Load Constructor */
    public X_DHL_ShipmentOrder (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set awb.
		@param awb awb	  */
	@Override
	public void setawb (java.lang.String awb)
	{
		set_Value (COLUMNNAME_awb, awb);
	}

	/** Get awb.
		@return awb	  */
	@Override
	public java.lang.String getawb () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_awb);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kundenreferenz.
		@param CustomerReference Kundenreferenz	  */
	@Override
	public void setCustomerReference (java.lang.String CustomerReference)
	{
		set_Value (COLUMNNAME_CustomerReference, CustomerReference);
	}

	/** Get Kundenreferenz.
		@return Kundenreferenz	  */
	@Override
	public java.lang.String getCustomerReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CustomerReference);
	}

	/** Set DHL_AccountNumber.
		@param DHL_AccountNumber DHL_AccountNumber	  */
	@Override
	public void setDHL_AccountNumber (java.lang.String DHL_AccountNumber)
	{
		set_Value (COLUMNNAME_DHL_AccountNumber, DHL_AccountNumber);
	}

	/** Get DHL_AccountNumber.
		@return DHL_AccountNumber	  */
	@Override
	public java.lang.String getDHL_AccountNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_AccountNumber);
	}

	/** Set DHL_HeightInCm.
		@param DHL_HeightInCm DHL_HeightInCm	  */
	@Override
	public void setDHL_HeightInCm (int DHL_HeightInCm)
	{
		set_Value (COLUMNNAME_DHL_HeightInCm, Integer.valueOf(DHL_HeightInCm));
	}

	/** Get DHL_HeightInCm.
		@return DHL_HeightInCm	  */
	@Override
	public int getDHL_HeightInCm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DHL_HeightInCm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DHL_LengthInCm.
		@param DHL_LengthInCm DHL_LengthInCm	  */
	@Override
	public void setDHL_LengthInCm (int DHL_LengthInCm)
	{
		set_Value (COLUMNNAME_DHL_LengthInCm, Integer.valueOf(DHL_LengthInCm));
	}

	/** Get DHL_LengthInCm.
		@return DHL_LengthInCm	  */
	@Override
	public int getDHL_LengthInCm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DHL_LengthInCm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DHL_Product.
		@param DHL_Product DHL_Product	  */
	@Override
	public void setDHL_Product (java.lang.String DHL_Product)
	{
		set_Value (COLUMNNAME_DHL_Product, DHL_Product);
	}

	/** Get DHL_Product.
		@return DHL_Product	  */
	@Override
	public java.lang.String getDHL_Product () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Product);
	}

	/** Set DHL_Receiver_City.
		@param DHL_Receiver_City DHL_Receiver_City	  */
	@Override
	public void setDHL_Receiver_City (java.lang.String DHL_Receiver_City)
	{
		set_Value (COLUMNNAME_DHL_Receiver_City, DHL_Receiver_City);
	}

	/** Get DHL_Receiver_City.
		@return DHL_Receiver_City	  */
	@Override
	public java.lang.String getDHL_Receiver_City () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_City);
	}

	/** Set DHL_Receiver_CountryISO2Code.
		@param DHL_Receiver_CountryISO2Code DHL_Receiver_CountryISO2Code	  */
	@Override
	public void setDHL_Receiver_CountryISO2Code (java.lang.String DHL_Receiver_CountryISO2Code)
	{
		set_Value (COLUMNNAME_DHL_Receiver_CountryISO2Code, DHL_Receiver_CountryISO2Code);
	}

	/** Get DHL_Receiver_CountryISO2Code.
		@return DHL_Receiver_CountryISO2Code	  */
	@Override
	public java.lang.String getDHL_Receiver_CountryISO2Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_CountryISO2Code);
	}

	/** Set DHL_Receiver_CountryISO3Code.
		@param DHL_Receiver_CountryISO3Code DHL_Receiver_CountryISO3Code	  */
	@Override
	public void setDHL_Receiver_CountryISO3Code (java.lang.String DHL_Receiver_CountryISO3Code)
	{
		set_Value (COLUMNNAME_DHL_Receiver_CountryISO3Code, DHL_Receiver_CountryISO3Code);
	}

	/** Get DHL_Receiver_CountryISO3Code.
		@return DHL_Receiver_CountryISO3Code	  */
	@Override
	public java.lang.String getDHL_Receiver_CountryISO3Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_CountryISO3Code);
	}

	/** Set DHL_Receiver_Email.
		@param DHL_Receiver_Email DHL_Receiver_Email	  */
	@Override
	public void setDHL_Receiver_Email (java.lang.String DHL_Receiver_Email)
	{
		set_Value (COLUMNNAME_DHL_Receiver_Email, DHL_Receiver_Email);
	}

	/** Get DHL_Receiver_Email.
		@return DHL_Receiver_Email	  */
	@Override
	public java.lang.String getDHL_Receiver_Email () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_Email);
	}

	/** Set DHL_Receiver_Name1.
		@param DHL_Receiver_Name1 DHL_Receiver_Name1	  */
	@Override
	public void setDHL_Receiver_Name1 (java.lang.String DHL_Receiver_Name1)
	{
		set_Value (COLUMNNAME_DHL_Receiver_Name1, DHL_Receiver_Name1);
	}

	/** Get DHL_Receiver_Name1.
		@return DHL_Receiver_Name1	  */
	@Override
	public java.lang.String getDHL_Receiver_Name1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_Name1);
	}

	/** Set DHL_Receiver_Name2.
		@param DHL_Receiver_Name2 DHL_Receiver_Name2	  */
	@Override
	public void setDHL_Receiver_Name2 (java.lang.String DHL_Receiver_Name2)
	{
		set_Value (COLUMNNAME_DHL_Receiver_Name2, DHL_Receiver_Name2);
	}

	/** Get DHL_Receiver_Name2.
		@return DHL_Receiver_Name2	  */
	@Override
	public java.lang.String getDHL_Receiver_Name2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_Name2);
	}

	/** Set DHL_Receiver_Phone.
		@param DHL_Receiver_Phone DHL_Receiver_Phone	  */
	@Override
	public void setDHL_Receiver_Phone (java.lang.String DHL_Receiver_Phone)
	{
		set_Value (COLUMNNAME_DHL_Receiver_Phone, DHL_Receiver_Phone);
	}

	/** Get DHL_Receiver_Phone.
		@return DHL_Receiver_Phone	  */
	@Override
	public java.lang.String getDHL_Receiver_Phone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_Phone);
	}

	/** Set DHL_Receiver_StreetName1.
		@param DHL_Receiver_StreetName1 DHL_Receiver_StreetName1	  */
	@Override
	public void setDHL_Receiver_StreetName1 (java.lang.String DHL_Receiver_StreetName1)
	{
		set_Value (COLUMNNAME_DHL_Receiver_StreetName1, DHL_Receiver_StreetName1);
	}

	/** Get DHL_Receiver_StreetName1.
		@return DHL_Receiver_StreetName1	  */
	@Override
	public java.lang.String getDHL_Receiver_StreetName1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_StreetName1);
	}

	/** Set DHL_Receiver_StreetName2.
		@param DHL_Receiver_StreetName2 DHL_Receiver_StreetName2	  */
	@Override
	public void setDHL_Receiver_StreetName2 (java.lang.String DHL_Receiver_StreetName2)
	{
		set_Value (COLUMNNAME_DHL_Receiver_StreetName2, DHL_Receiver_StreetName2);
	}

	/** Get DHL_Receiver_StreetName2.
		@return DHL_Receiver_StreetName2	  */
	@Override
	public java.lang.String getDHL_Receiver_StreetName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_StreetName2);
	}

	/** Set DHL_Receiver_StreetNumber.
		@param DHL_Receiver_StreetNumber DHL_Receiver_StreetNumber	  */
	@Override
	public void setDHL_Receiver_StreetNumber (java.lang.String DHL_Receiver_StreetNumber)
	{
		set_Value (COLUMNNAME_DHL_Receiver_StreetNumber, DHL_Receiver_StreetNumber);
	}

	/** Get DHL_Receiver_StreetNumber.
		@return DHL_Receiver_StreetNumber	  */
	@Override
	public java.lang.String getDHL_Receiver_StreetNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_StreetNumber);
	}

	/** Set DHL_Receiver_ZipCode.
		@param DHL_Receiver_ZipCode DHL_Receiver_ZipCode	  */
	@Override
	public void setDHL_Receiver_ZipCode (java.lang.String DHL_Receiver_ZipCode)
	{
		set_Value (COLUMNNAME_DHL_Receiver_ZipCode, DHL_Receiver_ZipCode);
	}

	/** Get DHL_Receiver_ZipCode.
		@return DHL_Receiver_ZipCode	  */
	@Override
	public java.lang.String getDHL_Receiver_ZipCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_ZipCode);
	}

	/** Set DHL_RecipientEmailAddress.
		@param DHL_RecipientEmailAddress DHL_RecipientEmailAddress	  */
	@Override
	public void setDHL_RecipientEmailAddress (java.lang.String DHL_RecipientEmailAddress)
	{
		set_Value (COLUMNNAME_DHL_RecipientEmailAddress, DHL_RecipientEmailAddress);
	}

	/** Get DHL_RecipientEmailAddress.
		@return DHL_RecipientEmailAddress	  */
	@Override
	public java.lang.String getDHL_RecipientEmailAddress () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_RecipientEmailAddress);
	}

	/** Set DHL_ShipmentDate.
		@param DHL_ShipmentDate DHL_ShipmentDate	  */
	@Override
	public void setDHL_ShipmentDate (java.lang.String DHL_ShipmentDate)
	{
		set_Value (COLUMNNAME_DHL_ShipmentDate, DHL_ShipmentDate);
	}

	/** Get DHL_ShipmentDate.
		@return DHL_ShipmentDate	  */
	@Override
	public java.lang.String getDHL_ShipmentDate () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_ShipmentDate);
	}

	/** Set DHL_ShipmetnOrder.
		@param DHL_ShipmentOrder_ID DHL_ShipmetnOrder	  */
	@Override
	public void setDHL_ShipmentOrder_ID (int DHL_ShipmentOrder_ID)
	{
		if (DHL_ShipmentOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DHL_ShipmentOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DHL_ShipmentOrder_ID, Integer.valueOf(DHL_ShipmentOrder_ID));
	}

	/** Get DHL_ShipmetnOrder.
		@return DHL_ShipmetnOrder	  */
	@Override
	public int getDHL_ShipmentOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DHL_ShipmentOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest getDHL_ShipmentOrderRequest()
	{
		return get_ValueAsPO(COLUMNNAME_DHL_ShipmentOrderRequest_ID, de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest.class);
	}

	@Override
	public void setDHL_ShipmentOrderRequest(de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest DHL_ShipmentOrderRequest)
	{
		set_ValueFromPO(COLUMNNAME_DHL_ShipmentOrderRequest_ID, de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest.class, DHL_ShipmentOrderRequest);
	}

	/** Set DHL Shipment Order Request.
		@param DHL_ShipmentOrderRequest_ID DHL Shipment Order Request	  */
	@Override
	public void setDHL_ShipmentOrderRequest_ID (int DHL_ShipmentOrderRequest_ID)
	{
		if (DHL_ShipmentOrderRequest_ID < 1) 
			set_Value (COLUMNNAME_DHL_ShipmentOrderRequest_ID, null);
		else 
			set_Value (COLUMNNAME_DHL_ShipmentOrderRequest_ID, Integer.valueOf(DHL_ShipmentOrderRequest_ID));
	}

	/** Get DHL Shipment Order Request.
		@return DHL Shipment Order Request	  */
	@Override
	public int getDHL_ShipmentOrderRequest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DHL_ShipmentOrderRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DHL_Shipper_City.
		@param DHL_Shipper_City DHL_Shipper_City	  */
	@Override
	public void setDHL_Shipper_City (java.lang.String DHL_Shipper_City)
	{
		set_Value (COLUMNNAME_DHL_Shipper_City, DHL_Shipper_City);
	}

	/** Get DHL_Shipper_City.
		@return DHL_Shipper_City	  */
	@Override
	public java.lang.String getDHL_Shipper_City () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_City);
	}

	/** Set DHL_Shipper_CountryISO2Code.
		@param DHL_Shipper_CountryISO2Code DHL_Shipper_CountryISO2Code	  */
	@Override
	public void setDHL_Shipper_CountryISO2Code (java.lang.String DHL_Shipper_CountryISO2Code)
	{
		set_Value (COLUMNNAME_DHL_Shipper_CountryISO2Code, DHL_Shipper_CountryISO2Code);
	}

	/** Get DHL_Shipper_CountryISO2Code.
		@return DHL_Shipper_CountryISO2Code	  */
	@Override
	public java.lang.String getDHL_Shipper_CountryISO2Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_CountryISO2Code);
	}

	/** Set DHL_Shipper_CountryISO3Code.
		@param DHL_Shipper_CountryISO3Code DHL_Shipper_CountryISO3Code	  */
	@Override
	public void setDHL_Shipper_CountryISO3Code (java.lang.String DHL_Shipper_CountryISO3Code)
	{
		set_Value (COLUMNNAME_DHL_Shipper_CountryISO3Code, DHL_Shipper_CountryISO3Code);
	}

	/** Get DHL_Shipper_CountryISO3Code.
		@return DHL_Shipper_CountryISO3Code	  */
	@Override
	public java.lang.String getDHL_Shipper_CountryISO3Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_CountryISO3Code);
	}

	/** Set DHL_Shipper_Name1.
		@param DHL_Shipper_Name1 DHL_Shipper_Name1	  */
	@Override
	public void setDHL_Shipper_Name1 (java.lang.String DHL_Shipper_Name1)
	{
		set_Value (COLUMNNAME_DHL_Shipper_Name1, DHL_Shipper_Name1);
	}

	/** Get DHL_Shipper_Name1.
		@return DHL_Shipper_Name1	  */
	@Override
	public java.lang.String getDHL_Shipper_Name1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_Name1);
	}

	/** Set DHL_Shipper_Name2.
		@param DHL_Shipper_Name2 DHL_Shipper_Name2	  */
	@Override
	public void setDHL_Shipper_Name2 (java.lang.String DHL_Shipper_Name2)
	{
		set_Value (COLUMNNAME_DHL_Shipper_Name2, DHL_Shipper_Name2);
	}

	/** Get DHL_Shipper_Name2.
		@return DHL_Shipper_Name2	  */
	@Override
	public java.lang.String getDHL_Shipper_Name2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_Name2);
	}

	/** Set DHL_Shipper_StreetName1.
		@param DHL_Shipper_StreetName1 DHL_Shipper_StreetName1	  */
	@Override
	public void setDHL_Shipper_StreetName1 (java.lang.String DHL_Shipper_StreetName1)
	{
		set_Value (COLUMNNAME_DHL_Shipper_StreetName1, DHL_Shipper_StreetName1);
	}

	/** Get DHL_Shipper_StreetName1.
		@return DHL_Shipper_StreetName1	  */
	@Override
	public java.lang.String getDHL_Shipper_StreetName1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_StreetName1);
	}

	/** Set DHL_Shipper_StreetName2.
		@param DHL_Shipper_StreetName2 DHL_Shipper_StreetName2	  */
	@Override
	public void setDHL_Shipper_StreetName2 (java.lang.String DHL_Shipper_StreetName2)
	{
		set_Value (COLUMNNAME_DHL_Shipper_StreetName2, DHL_Shipper_StreetName2);
	}

	/** Get DHL_Shipper_StreetName2.
		@return DHL_Shipper_StreetName2	  */
	@Override
	public java.lang.String getDHL_Shipper_StreetName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_StreetName2);
	}

	/** Set DHL_Shipper_StreetNumber.
		@param DHL_Shipper_StreetNumber DHL_Shipper_StreetNumber	  */
	@Override
	public void setDHL_Shipper_StreetNumber (java.lang.String DHL_Shipper_StreetNumber)
	{
		set_Value (COLUMNNAME_DHL_Shipper_StreetNumber, DHL_Shipper_StreetNumber);
	}

	/** Get DHL_Shipper_StreetNumber.
		@return DHL_Shipper_StreetNumber	  */
	@Override
	public java.lang.String getDHL_Shipper_StreetNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_StreetNumber);
	}

	/** Set DHL_Shipper_ZipCode.
		@param DHL_Shipper_ZipCode DHL_Shipper_ZipCode	  */
	@Override
	public void setDHL_Shipper_ZipCode (java.lang.String DHL_Shipper_ZipCode)
	{
		set_Value (COLUMNNAME_DHL_Shipper_ZipCode, DHL_Shipper_ZipCode);
	}

	/** Get DHL_Shipper_ZipCode.
		@return DHL_Shipper_ZipCode	  */
	@Override
	public java.lang.String getDHL_Shipper_ZipCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_ZipCode);
	}

	/** Set DHL_WeightInKg.
		@param DHL_WeightInKg DHL_WeightInKg	  */
	@Override
	public void setDHL_WeightInKg (java.math.BigDecimal DHL_WeightInKg)
	{
		set_Value (COLUMNNAME_DHL_WeightInKg, DHL_WeightInKg);
	}

	/** Get DHL_WeightInKg.
		@return DHL_WeightInKg	  */
	@Override
	public java.math.BigDecimal getDHL_WeightInKg () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DHL_WeightInKg);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set DHL_WidthInCm.
		@param DHL_WidthInCm DHL_WidthInCm	  */
	@Override
	public void setDHL_WidthInCm (int DHL_WidthInCm)
	{
		set_Value (COLUMNNAME_DHL_WidthInCm, Integer.valueOf(DHL_WidthInCm));
	}

	/** Get DHL_WidthInCm.
		@return DHL_WidthInCm	  */
	@Override
	public int getDHL_WidthInCm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DHL_WidthInCm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Package Id.
		@param PackageId Package Id	  */
	@Override
	public void setPackageId (int PackageId)
	{
		set_Value (COLUMNNAME_PackageId, Integer.valueOf(PackageId));
	}

	/** Get Package Id.
		@return Package Id	  */
	@Override
	public int getPackageId () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PackageId);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PdfLabelData.
		@param PdfLabelData PdfLabelData	  */
	@Override
	public void setPdfLabelData (byte[] PdfLabelData)
	{
		set_Value (COLUMNNAME_PdfLabelData, PdfLabelData);
	}

	/** Get PdfLabelData.
		@return PdfLabelData	  */
	@Override
	public byte[] getPdfLabelData () 
	{
		return (byte[])get_Value(COLUMNNAME_PdfLabelData);
	}
}