/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.dpd.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DPD_StoreOrder
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DPD_StoreOrder extends org.compiere.model.PO implements I_DPD_StoreOrder, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1851456000L;

    /** Standard Constructor */
    public X_DPD_StoreOrder (Properties ctx, int DPD_StoreOrder_ID, String trxName)
    {
      super (ctx, DPD_StoreOrder_ID, trxName);
      /** if (DPD_StoreOrder_ID == 0)
        {
			setDPD_StoreOrder_ID (0);
			setPickupDate (new Timestamp( System.currentTimeMillis() ));
			setPickupDay (0);
			setPickupTimeFrom (new Timestamp( System.currentTimeMillis() ));
			setPickupTimeTo (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_DPD_StoreOrder (Properties ctx, ResultSet rs, String trxName)
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

	/** Set DPD Auftragsart.
		@param DpdOrderType DPD Auftragsart	  */
	@Override
	public void setDpdOrderType (java.lang.String DpdOrderType)
	{
		set_Value (COLUMNNAME_DpdOrderType, DpdOrderType);
	}

	/** Get DPD Auftragsart.
		@return DPD Auftragsart	  */
	@Override
	public java.lang.String getDpdOrderType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DpdOrderType);
	}

	/** Set DPD Produkt.
		@param DpdProduct DPD Produkt	  */
	@Override
	public void setDpdProduct (java.lang.String DpdProduct)
	{
		set_Value (COLUMNNAME_DpdProduct, DpdProduct);
	}

	/** Get DPD Produkt.
		@return DPD Produkt	  */
	@Override
	public java.lang.String getDpdProduct () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DpdProduct);
	}

	/** Set DPD StoreOrder.
		@param DPD_StoreOrder_ID DPD StoreOrder	  */
	@Override
	public void setDPD_StoreOrder_ID (int DPD_StoreOrder_ID)
	{
		if (DPD_StoreOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_StoreOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_StoreOrder_ID, Integer.valueOf(DPD_StoreOrder_ID));
	}

	/** Get DPD StoreOrder.
		@return DPD StoreOrder	  */
	@Override
	public int getDPD_StoreOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_StoreOrder_ID);
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

	/** Set Benachrichtigungsart.
		@param NotificationChannel Benachrichtigungsart	  */
	@Override
	public void setNotificationChannel (java.lang.String NotificationChannel)
	{
		set_Value (COLUMNNAME_NotificationChannel, NotificationChannel);
	}

	/** Get Benachrichtigungsart.
		@return Benachrichtigungsart	  */
	@Override
	public java.lang.String getNotificationChannel () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_NotificationChannel);
	}

	/** Set Papierformat.
		@param PaperFormat Papierformat	  */
	@Override
	public void setPaperFormat (java.lang.String PaperFormat)
	{
		set_Value (COLUMNNAME_PaperFormat, PaperFormat);
	}

	/** Get Papierformat.
		@return Papierformat	  */
	@Override
	public java.lang.String getPaperFormat () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaperFormat);
	}

	/** Set Paketscheindaten PDF.
		@param PdfLabelData Paketscheindaten PDF	  */
	@Override
	public void setPdfLabelData (byte[] PdfLabelData)
	{
		set_Value (COLUMNNAME_PdfLabelData, PdfLabelData);
	}

	/** Get Paketscheindaten PDF.
		@return Paketscheindaten PDF	  */
	@Override
	public byte[] getPdfLabelData () 
	{
		return (byte[])get_Value(COLUMNNAME_PdfLabelData);
	}

	/** Set Datum der Abholung.
		@param PickupDate Datum der Abholung	  */
	@Override
	public void setPickupDate (java.sql.Timestamp PickupDate)
	{
		set_Value (COLUMNNAME_PickupDate, PickupDate);
	}

	/** Get Datum der Abholung.
		@return Datum der Abholung	  */
	@Override
	public java.sql.Timestamp getPickupDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PickupDate);
	}

	/** Set Tag der Abholung.
		@param PickupDay Tag der Abholung	  */
	@Override
	public void setPickupDay (int PickupDay)
	{
		set_Value (COLUMNNAME_PickupDay, Integer.valueOf(PickupDay));
	}

	/** Get Tag der Abholung.
		@return Tag der Abholung	  */
	@Override
	public int getPickupDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PickupDay);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Abhoung Uhrzeit ab.
		@param PickupTimeFrom Abhoung Uhrzeit ab	  */
	@Override
	public void setPickupTimeFrom (java.sql.Timestamp PickupTimeFrom)
	{
		set_Value (COLUMNNAME_PickupTimeFrom, PickupTimeFrom);
	}

	/** Get Abhoung Uhrzeit ab.
		@return Abhoung Uhrzeit ab	  */
	@Override
	public java.sql.Timestamp getPickupTimeFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PickupTimeFrom);
	}

	/** Set Abholung Uhrzeit bis.
		@param PickupTimeTo Abholung Uhrzeit bis	  */
	@Override
	public void setPickupTimeTo (java.sql.Timestamp PickupTimeTo)
	{
		set_Value (COLUMNNAME_PickupTimeTo, PickupTimeTo);
	}

	/** Get Abholung Uhrzeit bis.
		@return Abholung Uhrzeit bis	  */
	@Override
	public java.sql.Timestamp getPickupTimeTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PickupTimeTo);
	}

	/** Set Sprache Paketschein.
		@param PrinterLanguage Sprache Paketschein	  */
	@Override
	public void setPrinterLanguage (java.lang.String PrinterLanguage)
	{
		set_Value (COLUMNNAME_PrinterLanguage, PrinterLanguage);
	}

	/** Get Sprache Paketschein.
		@return Sprache Paketschein	  */
	@Override
	public java.lang.String getPrinterLanguage () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrinterLanguage);
	}

	/** Set Ort Empfänger.
		@param RecipientCity Ort Empfänger	  */
	@Override
	public void setRecipientCity (java.lang.String RecipientCity)
	{
		set_Value (COLUMNNAME_RecipientCity, RecipientCity);
	}

	/** Get Ort Empfänger.
		@return Ort Empfänger	  */
	@Override
	public java.lang.String getRecipientCity () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientCity);
	}

	/** Set Land Empfänger.
		@param RecipientCountry Land Empfänger	  */
	@Override
	public void setRecipientCountry (java.lang.String RecipientCountry)
	{
		set_Value (COLUMNNAME_RecipientCountry, RecipientCountry);
	}

	/** Get Land Empfänger.
		@return Land Empfänger	  */
	@Override
	public java.lang.String getRecipientCountry () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientCountry);
	}

	/** Set E-Mail-Adresse Empfänger.
		@param RecipientEmailAddress E-Mail-Adresse Empfänger	  */
	@Override
	public void setRecipientEmailAddress (java.lang.String RecipientEmailAddress)
	{
		set_Value (COLUMNNAME_RecipientEmailAddress, RecipientEmailAddress);
	}

	/** Get E-Mail-Adresse Empfänger.
		@return E-Mail-Adresse Empfänger	  */
	@Override
	public java.lang.String getRecipientEmailAddress () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientEmailAddress);
	}

	/** Set Haus-Nr. Empfänger.
		@param RecipientHouseNo Haus-Nr. Empfänger	  */
	@Override
	public void setRecipientHouseNo (java.lang.String RecipientHouseNo)
	{
		set_Value (COLUMNNAME_RecipientHouseNo, RecipientHouseNo);
	}

	/** Get Haus-Nr. Empfänger.
		@return Haus-Nr. Empfänger	  */
	@Override
	public java.lang.String getRecipientHouseNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientHouseNo);
	}

	/** Set Name 1 Empfänger.
		@param RecipientName1 Name 1 Empfänger	  */
	@Override
	public void setRecipientName1 (java.lang.String RecipientName1)
	{
		set_Value (COLUMNNAME_RecipientName1, RecipientName1);
	}

	/** Get Name 1 Empfänger.
		@return Name 1 Empfänger	  */
	@Override
	public java.lang.String getRecipientName1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientName1);
	}

	/** Set Name 2 Empfänger.
		@param RecipientName2 Name 2 Empfänger	  */
	@Override
	public void setRecipientName2 (java.lang.String RecipientName2)
	{
		set_Value (COLUMNNAME_RecipientName2, RecipientName2);
	}

	/** Get Name 2 Empfänger.
		@return Name 2 Empfänger	  */
	@Override
	public java.lang.String getRecipientName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientName2);
	}

	/** Set Telefon Empfänger.
		@param RecipientPhone Telefon Empfänger	  */
	@Override
	public void setRecipientPhone (java.lang.String RecipientPhone)
	{
		set_Value (COLUMNNAME_RecipientPhone, RecipientPhone);
	}

	/** Get Telefon Empfänger.
		@return Telefon Empfänger	  */
	@Override
	public java.lang.String getRecipientPhone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientPhone);
	}

	/** Set Straße Empfänger.
		@param RecipientStreet Straße Empfänger	  */
	@Override
	public void setRecipientStreet (java.lang.String RecipientStreet)
	{
		set_Value (COLUMNNAME_RecipientStreet, RecipientStreet);
	}

	/** Get Straße Empfänger.
		@return Straße Empfänger	  */
	@Override
	public java.lang.String getRecipientStreet () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientStreet);
	}

	/** Set PLZ Empfänger.
		@param RecipientZipCode PLZ Empfänger	  */
	@Override
	public void setRecipientZipCode (java.lang.String RecipientZipCode)
	{
		set_Value (COLUMNNAME_RecipientZipCode, RecipientZipCode);
	}

	/** Get PLZ Empfänger.
		@return PLZ Empfänger	  */
	@Override
	public java.lang.String getRecipientZipCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientZipCode);
	}

	/** Set Ort Absender.
		@param SenderCity Ort Absender	  */
	@Override
	public void setSenderCity (java.lang.String SenderCity)
	{
		set_Value (COLUMNNAME_SenderCity, SenderCity);
	}

	/** Get Ort Absender.
		@return Ort Absender	  */
	@Override
	public java.lang.String getSenderCity () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderCity);
	}

	/** Set Land Absender.
		@param SenderCountry Land Absender	  */
	@Override
	public void setSenderCountry (java.lang.String SenderCountry)
	{
		set_Value (COLUMNNAME_SenderCountry, SenderCountry);
	}

	/** Get Land Absender.
		@return Land Absender	  */
	@Override
	public java.lang.String getSenderCountry () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderCountry);
	}

	/** Set Haus-Nr. Absender.
		@param SenderHouseNo Haus-Nr. Absender	  */
	@Override
	public void setSenderHouseNo (java.lang.String SenderHouseNo)
	{
		set_Value (COLUMNNAME_SenderHouseNo, SenderHouseNo);
	}

	/** Get Haus-Nr. Absender.
		@return Haus-Nr. Absender	  */
	@Override
	public java.lang.String getSenderHouseNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderHouseNo);
	}

	/** Set Name 1 Absender.
		@param SenderName1 Name 1 Absender	  */
	@Override
	public void setSenderName1 (java.lang.String SenderName1)
	{
		set_Value (COLUMNNAME_SenderName1, SenderName1);
	}

	/** Get Name 1 Absender.
		@return Name 1 Absender	  */
	@Override
	public java.lang.String getSenderName1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderName1);
	}

	/** Set Name 2 Absender.
		@param SenderName2 Name 2 Absender	  */
	@Override
	public void setSenderName2 (java.lang.String SenderName2)
	{
		set_Value (COLUMNNAME_SenderName2, SenderName2);
	}

	/** Get Name 2 Absender.
		@return Name 2 Absender	  */
	@Override
	public java.lang.String getSenderName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderName2);
	}

	/** Set Straße Absender.
		@param SenderStreet Straße Absender	  */
	@Override
	public void setSenderStreet (java.lang.String SenderStreet)
	{
		set_Value (COLUMNNAME_SenderStreet, SenderStreet);
	}

	/** Get Straße Absender.
		@return Straße Absender	  */
	@Override
	public java.lang.String getSenderStreet () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderStreet);
	}

	/** Set PLZ Absender.
		@param SenderZipCode PLZ Absender	  */
	@Override
	public void setSenderZipCode (java.lang.String SenderZipCode)
	{
		set_Value (COLUMNNAME_SenderZipCode, SenderZipCode);
	}

	/** Get PLZ Absender.
		@return PLZ Absender	  */
	@Override
	public java.lang.String getSenderZipCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderZipCode);
	}

	/** Set Versandlager.
		@param SendingDepot Versandlager	  */
	@Override
	public void setSendingDepot (java.lang.String SendingDepot)
	{
		set_Value (COLUMNNAME_SendingDepot, SendingDepot);
	}

	/** Get Versandlager.
		@return Versandlager	  */
	@Override
	public java.lang.String getSendingDepot () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SendingDepot);
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