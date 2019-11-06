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
	private static final long serialVersionUID = 859411291L;

    /** Standard Constructor */
    public X_DPD_StoreOrder (Properties ctx, int DPD_StoreOrder_ID, String trxName)
    {
      super (ctx, DPD_StoreOrder_ID, trxName);
      /** if (DPD_StoreOrder_ID == 0)
        {
			setDPD_StoreOrder_ID (0);
			setHeightInCm (0); // 0
			setLengthInCm (0); // 0
			setPickupDate (new Timestamp( System.currentTimeMillis() )); // 0
			setPickupDay (0); // 0
			setPickupTimeFrom (new Timestamp( System.currentTimeMillis() )); // 0
			setPickupTimeTo (new Timestamp( System.currentTimeMillis() )); // 0
			setWeightInKg (0); // 0
			setWidthInCm (0); // 0
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

	/** Set Dpd Order Type.
		@param DpdOrderType Dpd Order Type	  */
	@Override
	public void setDpdOrderType (java.lang.String DpdOrderType)
	{
		set_Value (COLUMNNAME_DpdOrderType, DpdOrderType);
	}

	/** Get Dpd Order Type.
		@return Dpd Order Type	  */
	@Override
	public java.lang.String getDpdOrderType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DpdOrderType);
	}

	/** Set Dpd Product.
		@param DpdProduct Dpd Product	  */
	@Override
	public void setDpdProduct (java.lang.String DpdProduct)
	{
		set_Value (COLUMNNAME_DpdProduct, DpdProduct);
	}

	/** Get Dpd Product.
		@return Dpd Product	  */
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

	/** Set Height In Cm.
		@param HeightInCm Height In Cm	  */
	@Override
	public void setHeightInCm (int HeightInCm)
	{
		set_Value (COLUMNNAME_HeightInCm, Integer.valueOf(HeightInCm));
	}

	/** Get Height In Cm.
		@return Height In Cm	  */
	@Override
	public int getHeightInCm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HeightInCm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Length In Cm.
		@param LengthInCm Length In Cm	  */
	@Override
	public void setLengthInCm (int LengthInCm)
	{
		set_Value (COLUMNNAME_LengthInCm, Integer.valueOf(LengthInCm));
	}

	/** Get Length In Cm.
		@return Length In Cm	  */
	@Override
	public int getLengthInCm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LengthInCm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Package getM_Package()
	{
		return get_ValueAsPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class);
	}

	@Override
	public void setM_Package(org.compiere.model.I_M_Package M_Package)
	{
		set_ValueFromPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class, M_Package);
	}

	/** Set Packstück.
		@param M_Package_ID 
		Shipment Package
	  */
	@Override
	public void setM_Package_ID (int M_Package_ID)
	{
		if (M_Package_ID < 1) 
			set_Value (COLUMNNAME_M_Package_ID, null);
		else 
			set_Value (COLUMNNAME_M_Package_ID, Integer.valueOf(M_Package_ID));
	}

	/** Get Packstück.
		@return Shipment Package
	  */
	@Override
	public int getM_Package_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Package_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MpsID.
		@param MpsID MpsID	  */
	@Override
	public void setMpsID (java.lang.String MpsID)
	{
		set_Value (COLUMNNAME_MpsID, MpsID);
	}

	/** Get MpsID.
		@return MpsID	  */
	@Override
	public java.lang.String getMpsID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MpsID);
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

	/** Set Notification Channel.
		@param NotificationChannel Notification Channel	  */
	@Override
	public void setNotificationChannel (java.lang.String NotificationChannel)
	{
		set_Value (COLUMNNAME_NotificationChannel, NotificationChannel);
	}

	/** Get Notification Channel.
		@return Notification Channel	  */
	@Override
	public java.lang.String getNotificationChannel () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_NotificationChannel);
	}

	/** Set Package Content.
		@param PackageContent Package Content	  */
	@Override
	public void setPackageContent (java.lang.String PackageContent)
	{
		set_Value (COLUMNNAME_PackageContent, PackageContent);
	}

	/** Get Package Content.
		@return Package Content	  */
	@Override
	public java.lang.String getPackageContent () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PackageContent);
	}

	/** Set Paper Format.
		@param PaperFormat Paper Format	  */
	@Override
	public void setPaperFormat (java.lang.String PaperFormat)
	{
		set_Value (COLUMNNAME_PaperFormat, PaperFormat);
	}

	/** Get Paper Format.
		@return Paper Format	  */
	@Override
	public java.lang.String getPaperFormat () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaperFormat);
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

	/** Set Pickup Date.
		@param PickupDate Pickup Date	  */
	@Override
	public void setPickupDate (java.sql.Timestamp PickupDate)
	{
		set_Value (COLUMNNAME_PickupDate, PickupDate);
	}

	/** Get Pickup Date.
		@return Pickup Date	  */
	@Override
	public java.sql.Timestamp getPickupDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PickupDate);
	}

	/** Set Pickup Day.
		@param PickupDay Pickup Day	  */
	@Override
	public void setPickupDay (int PickupDay)
	{
		set_Value (COLUMNNAME_PickupDay, Integer.valueOf(PickupDay));
	}

	/** Get Pickup Day.
		@return Pickup Day	  */
	@Override
	public int getPickupDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PickupDay);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pickup Time From.
		@param PickupTimeFrom Pickup Time From	  */
	@Override
	public void setPickupTimeFrom (java.sql.Timestamp PickupTimeFrom)
	{
		set_Value (COLUMNNAME_PickupTimeFrom, PickupTimeFrom);
	}

	/** Get Pickup Time From.
		@return Pickup Time From	  */
	@Override
	public java.sql.Timestamp getPickupTimeFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PickupTimeFrom);
	}

	/** Set Pickup Time To.
		@param PickupTimeTo Pickup Time To	  */
	@Override
	public void setPickupTimeTo (java.sql.Timestamp PickupTimeTo)
	{
		set_Value (COLUMNNAME_PickupTimeTo, PickupTimeTo);
	}

	/** Get Pickup Time To.
		@return Pickup Time To	  */
	@Override
	public java.sql.Timestamp getPickupTimeTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PickupTimeTo);
	}

	/** Set Printer Language.
		@param PrinterLanguage Printer Language	  */
	@Override
	public void setPrinterLanguage (java.lang.String PrinterLanguage)
	{
		set_Value (COLUMNNAME_PrinterLanguage, PrinterLanguage);
	}

	/** Get Printer Language.
		@return Printer Language	  */
	@Override
	public java.lang.String getPrinterLanguage () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrinterLanguage);
	}

	/** Set Recipient City.
		@param RecipientCity Recipient City	  */
	@Override
	public void setRecipientCity (java.lang.String RecipientCity)
	{
		set_Value (COLUMNNAME_RecipientCity, RecipientCity);
	}

	/** Get Recipient City.
		@return Recipient City	  */
	@Override
	public java.lang.String getRecipientCity () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientCity);
	}

	/** Set Recipient Country.
		@param RecipientCountry Recipient Country	  */
	@Override
	public void setRecipientCountry (java.lang.String RecipientCountry)
	{
		set_Value (COLUMNNAME_RecipientCountry, RecipientCountry);
	}

	/** Get Recipient Country.
		@return Recipient Country	  */
	@Override
	public java.lang.String getRecipientCountry () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientCountry);
	}

	/** Set Recipient Email Address.
		@param RecipientEmailAddress Recipient Email Address	  */
	@Override
	public void setRecipientEmailAddress (java.lang.String RecipientEmailAddress)
	{
		set_Value (COLUMNNAME_RecipientEmailAddress, RecipientEmailAddress);
	}

	/** Get Recipient Email Address.
		@return Recipient Email Address	  */
	@Override
	public java.lang.String getRecipientEmailAddress () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientEmailAddress);
	}

	/** Set Recipient House No.
		@param RecipientHouseNo Recipient House No	  */
	@Override
	public void setRecipientHouseNo (java.lang.String RecipientHouseNo)
	{
		set_Value (COLUMNNAME_RecipientHouseNo, RecipientHouseNo);
	}

	/** Get Recipient House No.
		@return Recipient House No	  */
	@Override
	public java.lang.String getRecipientHouseNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientHouseNo);
	}

	/** Set Recipient Name 1.
		@param RecipientName1 Recipient Name 1	  */
	@Override
	public void setRecipientName1 (java.lang.String RecipientName1)
	{
		set_Value (COLUMNNAME_RecipientName1, RecipientName1);
	}

	/** Get Recipient Name 1.
		@return Recipient Name 1	  */
	@Override
	public java.lang.String getRecipientName1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientName1);
	}

	/** Set Recipient Name 2.
		@param RecipientName2 Recipient Name 2	  */
	@Override
	public void setRecipientName2 (java.lang.String RecipientName2)
	{
		set_Value (COLUMNNAME_RecipientName2, RecipientName2);
	}

	/** Get Recipient Name 2.
		@return Recipient Name 2	  */
	@Override
	public java.lang.String getRecipientName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientName2);
	}

	/** Set Recipient Phone.
		@param RecipientPhone Recipient Phone	  */
	@Override
	public void setRecipientPhone (java.lang.String RecipientPhone)
	{
		set_Value (COLUMNNAME_RecipientPhone, RecipientPhone);
	}

	/** Get Recipient Phone.
		@return Recipient Phone	  */
	@Override
	public java.lang.String getRecipientPhone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientPhone);
	}

	/** Set Recipient Street.
		@param RecipientStreet Recipient Street	  */
	@Override
	public void setRecipientStreet (java.lang.String RecipientStreet)
	{
		set_Value (COLUMNNAME_RecipientStreet, RecipientStreet);
	}

	/** Get Recipient Street.
		@return Recipient Street	  */
	@Override
	public java.lang.String getRecipientStreet () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientStreet);
	}

	/** Set Recipient Zip Code.
		@param RecipientZipCode Recipient Zip Code	  */
	@Override
	public void setRecipientZipCode (java.lang.String RecipientZipCode)
	{
		set_Value (COLUMNNAME_RecipientZipCode, RecipientZipCode);
	}

	/** Get Recipient Zip Code.
		@return Recipient Zip Code	  */
	@Override
	public java.lang.String getRecipientZipCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RecipientZipCode);
	}

	/** Set Sender City.
		@param SenderCity Sender City	  */
	@Override
	public void setSenderCity (java.lang.String SenderCity)
	{
		set_Value (COLUMNNAME_SenderCity, SenderCity);
	}

	/** Get Sender City.
		@return Sender City	  */
	@Override
	public java.lang.String getSenderCity () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderCity);
	}

	/** Set Sender Country.
		@param SenderCountry Sender Country	  */
	@Override
	public void setSenderCountry (java.lang.String SenderCountry)
	{
		set_Value (COLUMNNAME_SenderCountry, SenderCountry);
	}

	/** Get Sender Country.
		@return Sender Country	  */
	@Override
	public java.lang.String getSenderCountry () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderCountry);
	}

	/** Set Sender House No.
		@param SenderHouseNo Sender House No	  */
	@Override
	public void setSenderHouseNo (java.lang.String SenderHouseNo)
	{
		set_Value (COLUMNNAME_SenderHouseNo, SenderHouseNo);
	}

	/** Get Sender House No.
		@return Sender House No	  */
	@Override
	public java.lang.String getSenderHouseNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderHouseNo);
	}

	/** Set Sender Name 1.
		@param SenderName1 Sender Name 1	  */
	@Override
	public void setSenderName1 (java.lang.String SenderName1)
	{
		set_Value (COLUMNNAME_SenderName1, SenderName1);
	}

	/** Get Sender Name 1.
		@return Sender Name 1	  */
	@Override
	public java.lang.String getSenderName1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderName1);
	}

	/** Set Sender Name 2.
		@param SenderName2 Sender Name 2	  */
	@Override
	public void setSenderName2 (java.lang.String SenderName2)
	{
		set_Value (COLUMNNAME_SenderName2, SenderName2);
	}

	/** Get Sender Name 2.
		@return Sender Name 2	  */
	@Override
	public java.lang.String getSenderName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderName2);
	}

	/** Set Sender Street.
		@param SenderStreet Sender Street	  */
	@Override
	public void setSenderStreet (java.lang.String SenderStreet)
	{
		set_Value (COLUMNNAME_SenderStreet, SenderStreet);
	}

	/** Get Sender Street.
		@return Sender Street	  */
	@Override
	public java.lang.String getSenderStreet () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderStreet);
	}

	/** Set Sender Zip Code.
		@param SenderZipCode Sender Zip Code	  */
	@Override
	public void setSenderZipCode (java.lang.String SenderZipCode)
	{
		set_Value (COLUMNNAME_SenderZipCode, SenderZipCode);
	}

	/** Get Sender Zip Code.
		@return Sender Zip Code	  */
	@Override
	public java.lang.String getSenderZipCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SenderZipCode);
	}

	/** Set Sending Depot.
		@param SendingDepot Sending Depot	  */
	@Override
	public void setSendingDepot (java.lang.String SendingDepot)
	{
		set_Value (COLUMNNAME_SendingDepot, SendingDepot);
	}

	/** Get Sending Depot.
		@return Sending Depot	  */
	@Override
	public java.lang.String getSendingDepot () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SendingDepot);
	}

	/** Set Weight In Kg.
		@param WeightInKg Weight In Kg	  */
	@Override
	public void setWeightInKg (int WeightInKg)
	{
		set_Value (COLUMNNAME_WeightInKg, Integer.valueOf(WeightInKg));
	}

	/** Get Weight In Kg.
		@return Weight In Kg	  */
	@Override
	public int getWeightInKg () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WeightInKg);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Width In Cm.
		@param WidthInCm Width In Cm	  */
	@Override
	public void setWidthInCm (int WidthInCm)
	{
		set_Value (COLUMNNAME_WidthInCm, Integer.valueOf(WidthInCm));
	}

	/** Get Width In Cm.
		@return Width In Cm	  */
	@Override
	public int getWidthInCm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WidthInCm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}