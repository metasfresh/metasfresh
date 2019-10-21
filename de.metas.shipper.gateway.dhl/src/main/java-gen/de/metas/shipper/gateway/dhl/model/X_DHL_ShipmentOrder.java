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
	private static final long serialVersionUID = -432340413L;

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

	/** Set Luftfrachtbrief.
		@param awb Luftfrachtbrief	  */
	@Override
	public void setawb (java.lang.String awb)
	{
		set_Value (COLUMNNAME_awb, awb);
	}

	/** Get Luftfrachtbrief.
		@return Luftfrachtbrief	  */
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

	/** Set Höhe in cm.
		@param DHL_HeightInCm Höhe in cm	  */
	@Override
	public void setDHL_HeightInCm (int DHL_HeightInCm)
	{
		set_Value (COLUMNNAME_DHL_HeightInCm, Integer.valueOf(DHL_HeightInCm));
	}

	/** Get Höhe in cm.
		@return Höhe in cm	  */
	@Override
	public int getDHL_HeightInCm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DHL_HeightInCm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Länge in cm.
		@param DHL_LengthInCm Länge in cm	  */
	@Override
	public void setDHL_LengthInCm (int DHL_LengthInCm)
	{
		set_Value (COLUMNNAME_DHL_LengthInCm, Integer.valueOf(DHL_LengthInCm));
	}

	/** Get Länge in cm.
		@return Länge in cm	  */
	@Override
	public int getDHL_LengthInCm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DHL_LengthInCm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Produkt.
		@param DHL_Product Produkt	  */
	@Override
	public void setDHL_Product (java.lang.String DHL_Product)
	{
		set_Value (COLUMNNAME_DHL_Product, DHL_Product);
	}

	/** Get Produkt.
		@return Produkt	  */
	@Override
	public java.lang.String getDHL_Product () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Product);
	}

	/** Set Empfängerort.
		@param DHL_Receiver_City Empfängerort	  */
	@Override
	public void setDHL_Receiver_City (java.lang.String DHL_Receiver_City)
	{
		set_Value (COLUMNNAME_DHL_Receiver_City, DHL_Receiver_City);
	}

	/** Get Empfängerort.
		@return Empfängerort	  */
	@Override
	public java.lang.String getDHL_Receiver_City () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_City);
	}

	/** Set Empfänger Ländercode (ISO-2).
		@param DHL_Receiver_CountryISO2Code Empfänger Ländercode (ISO-2)	  */
	@Override
	public void setDHL_Receiver_CountryISO2Code (java.lang.String DHL_Receiver_CountryISO2Code)
	{
		set_Value (COLUMNNAME_DHL_Receiver_CountryISO2Code, DHL_Receiver_CountryISO2Code);
	}

	/** Get Empfänger Ländercode (ISO-2).
		@return Empfänger Ländercode (ISO-2)	  */
	@Override
	public java.lang.String getDHL_Receiver_CountryISO2Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_CountryISO2Code);
	}

	/** Set Empfänger Ländercode (ISO-3).
		@param DHL_Receiver_CountryISO3Code Empfänger Ländercode (ISO-3)	  */
	@Override
	public void setDHL_Receiver_CountryISO3Code (java.lang.String DHL_Receiver_CountryISO3Code)
	{
		set_Value (COLUMNNAME_DHL_Receiver_CountryISO3Code, DHL_Receiver_CountryISO3Code);
	}

	/** Get Empfänger Ländercode (ISO-3).
		@return Empfänger Ländercode (ISO-3)	  */
	@Override
	public java.lang.String getDHL_Receiver_CountryISO3Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_CountryISO3Code);
	}

	/** Set E-Mail Empfänger.
		@param DHL_Receiver_Email E-Mail Empfänger	  */
	@Override
	public void setDHL_Receiver_Email (java.lang.String DHL_Receiver_Email)
	{
		set_Value (COLUMNNAME_DHL_Receiver_Email, DHL_Receiver_Email);
	}

	/** Get E-Mail Empfänger.
		@return E-Mail Empfänger	  */
	@Override
	public java.lang.String getDHL_Receiver_Email () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_Email);
	}

	/** Set Name 1 Empfänger.
		@param DHL_Receiver_Name1 Name 1 Empfänger	  */
	@Override
	public void setDHL_Receiver_Name1 (java.lang.String DHL_Receiver_Name1)
	{
		set_Value (COLUMNNAME_DHL_Receiver_Name1, DHL_Receiver_Name1);
	}

	/** Get Name 1 Empfänger.
		@return Name 1 Empfänger	  */
	@Override
	public java.lang.String getDHL_Receiver_Name1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_Name1);
	}

	/** Set Name 2 Empfänger.
		@param DHL_Receiver_Name2 Name 2 Empfänger	  */
	@Override
	public void setDHL_Receiver_Name2 (java.lang.String DHL_Receiver_Name2)
	{
		set_Value (COLUMNNAME_DHL_Receiver_Name2, DHL_Receiver_Name2);
	}

	/** Get Name 2 Empfänger.
		@return Name 2 Empfänger	  */
	@Override
	public java.lang.String getDHL_Receiver_Name2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_Name2);
	}

	/** Set Telefon Empfänger.
		@param DHL_Receiver_Phone Telefon Empfänger	  */
	@Override
	public void setDHL_Receiver_Phone (java.lang.String DHL_Receiver_Phone)
	{
		set_Value (COLUMNNAME_DHL_Receiver_Phone, DHL_Receiver_Phone);
	}

	/** Get Telefon Empfänger.
		@return Telefon Empfänger	  */
	@Override
	public java.lang.String getDHL_Receiver_Phone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_Phone);
	}

	/** Set Straße 1 Empfänger.
		@param DHL_Receiver_StreetName1 Straße 1 Empfänger	  */
	@Override
	public void setDHL_Receiver_StreetName1 (java.lang.String DHL_Receiver_StreetName1)
	{
		set_Value (COLUMNNAME_DHL_Receiver_StreetName1, DHL_Receiver_StreetName1);
	}

	/** Get Straße 1 Empfänger.
		@return Straße 1 Empfänger	  */
	@Override
	public java.lang.String getDHL_Receiver_StreetName1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_StreetName1);
	}

	/** Set Straße 2 Empfänger.
		@param DHL_Receiver_StreetName2 Straße 2 Empfänger	  */
	@Override
	public void setDHL_Receiver_StreetName2 (java.lang.String DHL_Receiver_StreetName2)
	{
		set_Value (COLUMNNAME_DHL_Receiver_StreetName2, DHL_Receiver_StreetName2);
	}

	/** Get Straße 2 Empfänger.
		@return Straße 2 Empfänger	  */
	@Override
	public java.lang.String getDHL_Receiver_StreetName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_StreetName2);
	}

	/** Set Hausnummer Empfänger.
		@param DHL_Receiver_StreetNumber Hausnummer Empfänger	  */
	@Override
	public void setDHL_Receiver_StreetNumber (java.lang.String DHL_Receiver_StreetNumber)
	{
		set_Value (COLUMNNAME_DHL_Receiver_StreetNumber, DHL_Receiver_StreetNumber);
	}

	/** Get Hausnummer Empfänger.
		@return Hausnummer Empfänger	  */
	@Override
	public java.lang.String getDHL_Receiver_StreetNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Receiver_StreetNumber);
	}

	/** Set PLZ Empfänger.
		@param DHL_Receiver_ZipCode PLZ Empfänger	  */
	@Override
	public void setDHL_Receiver_ZipCode (java.lang.String DHL_Receiver_ZipCode)
	{
		set_Value (COLUMNNAME_DHL_Receiver_ZipCode, DHL_Receiver_ZipCode);
	}

	/** Get PLZ Empfänger.
		@return PLZ Empfänger	  */
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

	/** Set Lieferdatum.
		@param DHL_ShipmentDate Lieferdatum	  */
	@Override
	public void setDHL_ShipmentDate (java.lang.String DHL_ShipmentDate)
	{
		set_Value (COLUMNNAME_DHL_ShipmentDate, DHL_ShipmentDate);
	}

	/** Get Lieferdatum.
		@return Lieferdatum	  */
	@Override
	public java.lang.String getDHL_ShipmentDate () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_ShipmentDate);
	}

	/** Set DHL_ShipmentOrder.
		@param DHL_ShipmentOrder_ID DHL_ShipmentOrder	  */
	@Override
	public void setDHL_ShipmentOrder_ID (int DHL_ShipmentOrder_ID)
	{
		if (DHL_ShipmentOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DHL_ShipmentOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DHL_ShipmentOrder_ID, Integer.valueOf(DHL_ShipmentOrder_ID));
	}

	/** Get DHL_ShipmentOrder.
		@return DHL_ShipmentOrder	  */
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

	/** Set Lieferort.
		@param DHL_Shipper_City Lieferort	  */
	@Override
	public void setDHL_Shipper_City (java.lang.String DHL_Shipper_City)
	{
		set_Value (COLUMNNAME_DHL_Shipper_City, DHL_Shipper_City);
	}

	/** Get Lieferort.
		@return Lieferort	  */
	@Override
	public java.lang.String getDHL_Shipper_City () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_City);
	}

	/** Set Lieferant Ländercode (ISO-2).
		@param DHL_Shipper_CountryISO2Code Lieferant Ländercode (ISO-2)	  */
	@Override
	public void setDHL_Shipper_CountryISO2Code (java.lang.String DHL_Shipper_CountryISO2Code)
	{
		set_Value (COLUMNNAME_DHL_Shipper_CountryISO2Code, DHL_Shipper_CountryISO2Code);
	}

	/** Get Lieferant Ländercode (ISO-2).
		@return Lieferant Ländercode (ISO-2)	  */
	@Override
	public java.lang.String getDHL_Shipper_CountryISO2Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_CountryISO2Code);
	}

	/** Set Lieferant Ländercode (ISO-3).
		@param DHL_Shipper_CountryISO3Code Lieferant Ländercode (ISO-3)	  */
	@Override
	public void setDHL_Shipper_CountryISO3Code (java.lang.String DHL_Shipper_CountryISO3Code)
	{
		set_Value (COLUMNNAME_DHL_Shipper_CountryISO3Code, DHL_Shipper_CountryISO3Code);
	}

	/** Get Lieferant Ländercode (ISO-3).
		@return Lieferant Ländercode (ISO-3)	  */
	@Override
	public java.lang.String getDHL_Shipper_CountryISO3Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_CountryISO3Code);
	}

	/** Set Lieferant Name 1.
		@param DHL_Shipper_Name1 Lieferant Name 1	  */
	@Override
	public void setDHL_Shipper_Name1 (java.lang.String DHL_Shipper_Name1)
	{
		set_Value (COLUMNNAME_DHL_Shipper_Name1, DHL_Shipper_Name1);
	}

	/** Get Lieferant Name 1.
		@return Lieferant Name 1	  */
	@Override
	public java.lang.String getDHL_Shipper_Name1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_Name1);
	}

	/** Set  Lieferant Name 2.
		@param DHL_Shipper_Name2  Lieferant Name 2	  */
	@Override
	public void setDHL_Shipper_Name2 (java.lang.String DHL_Shipper_Name2)
	{
		set_Value (COLUMNNAME_DHL_Shipper_Name2, DHL_Shipper_Name2);
	}

	/** Get  Lieferant Name 2.
		@return  Lieferant Name 2	  */
	@Override
	public java.lang.String getDHL_Shipper_Name2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_Name2);
	}

	/** Set Straße 1 Lieferant.
		@param DHL_Shipper_StreetName1 Straße 1 Lieferant	  */
	@Override
	public void setDHL_Shipper_StreetName1 (java.lang.String DHL_Shipper_StreetName1)
	{
		set_Value (COLUMNNAME_DHL_Shipper_StreetName1, DHL_Shipper_StreetName1);
	}

	/** Get Straße 1 Lieferant.
		@return Straße 1 Lieferant	  */
	@Override
	public java.lang.String getDHL_Shipper_StreetName1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_StreetName1);
	}

	/** Set Straße 2 Lieferant.
		@param DHL_Shipper_StreetName2 Straße 2 Lieferant	  */
	@Override
	public void setDHL_Shipper_StreetName2 (java.lang.String DHL_Shipper_StreetName2)
	{
		set_Value (COLUMNNAME_DHL_Shipper_StreetName2, DHL_Shipper_StreetName2);
	}

	/** Get Straße 2 Lieferant.
		@return Straße 2 Lieferant	  */
	@Override
	public java.lang.String getDHL_Shipper_StreetName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_StreetName2);
	}

	/** Set Hausnummer Lieferant.
		@param DHL_Shipper_StreetNumber Hausnummer Lieferant	  */
	@Override
	public void setDHL_Shipper_StreetNumber (java.lang.String DHL_Shipper_StreetNumber)
	{
		set_Value (COLUMNNAME_DHL_Shipper_StreetNumber, DHL_Shipper_StreetNumber);
	}

	/** Get Hausnummer Lieferant.
		@return Hausnummer Lieferant	  */
	@Override
	public java.lang.String getDHL_Shipper_StreetNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_StreetNumber);
	}

	/** Set PLZ Lieferant.
		@param DHL_Shipper_ZipCode PLZ Lieferant	  */
	@Override
	public void setDHL_Shipper_ZipCode (java.lang.String DHL_Shipper_ZipCode)
	{
		set_Value (COLUMNNAME_DHL_Shipper_ZipCode, DHL_Shipper_ZipCode);
	}

	/** Get PLZ Lieferant.
		@return PLZ Lieferant	  */
	@Override
	public java.lang.String getDHL_Shipper_ZipCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DHL_Shipper_ZipCode);
	}

	/** Set Gewicht in kg.
		@param DHL_WeightInKg Gewicht in kg	  */
	@Override
	public void setDHL_WeightInKg (java.math.BigDecimal DHL_WeightInKg)
	{
		set_Value (COLUMNNAME_DHL_WeightInKg, DHL_WeightInKg);
	}

	/** Get Gewicht in kg.
		@return Gewicht in kg	  */
	@Override
	public java.math.BigDecimal getDHL_WeightInKg () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DHL_WeightInKg);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Breite in cm.
		@param DHL_WidthInCm Breite in cm	  */
	@Override
	public void setDHL_WidthInCm (int DHL_WidthInCm)
	{
		set_Value (COLUMNNAME_DHL_WidthInCm, Integer.valueOf(DHL_WidthInCm));
	}

	/** Get Breite in cm.
		@return Breite in cm	  */
	@Override
	public int getDHL_WidthInCm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DHL_WidthInCm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Shipper getM_Shipper()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	/** Set Lieferweg.
		@param M_Shipper_ID 
		Methode oder Art der Warenlieferung
	  */
	@Override
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/** Get Lieferweg.
		@return Methode oder Art der Warenlieferung
	  */
	@Override
	public int getM_Shipper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transport Auftrag.
		@param M_ShipperTransportation_ID Transport Auftrag	  */
	@Override
	public void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID)
	{
		if (M_ShipperTransportation_ID < 1) 
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, Integer.valueOf(M_ShipperTransportation_ID));
	}

	/** Get Transport Auftrag.
		@return Transport Auftrag	  */
	@Override
	public int getM_ShipperTransportation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipperTransportation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Paket-ID.
		@param PackageId Paket-ID	  */
	@Override
	public void setPackageId (int PackageId)
	{
		set_Value (COLUMNNAME_PackageId, Integer.valueOf(PackageId));
	}

	/** Get Paket-ID.
		@return Paket-ID	  */
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

	/** Set Nachverfolgungs-URL.
		@param TrackingURL 
		URL des Spediteurs um Sendungen zu verfolgen
	  */
	@Override
	public void setTrackingURL (java.lang.String TrackingURL)
	{
		set_Value (COLUMNNAME_TrackingURL, TrackingURL);
	}

	/** Get Nachverfolgungs-URL.
		@return URL des Spediteurs um Sendungen zu verfolgen
	  */
	@Override
	public java.lang.String getTrackingURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TrackingURL);
	}
}