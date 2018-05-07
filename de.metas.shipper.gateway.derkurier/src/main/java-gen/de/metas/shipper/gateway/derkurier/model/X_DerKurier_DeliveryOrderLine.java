/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.derkurier.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DerKurier_DeliveryOrderLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DerKurier_DeliveryOrderLine extends org.compiere.model.PO implements I_DerKurier_DeliveryOrderLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2110467792L;

    /** Standard Constructor */
    public X_DerKurier_DeliveryOrderLine (Properties ctx, int DerKurier_DeliveryOrderLine_ID, String trxName)
    {
      super (ctx, DerKurier_DeliveryOrderLine_ID, trxName);
      /** if (DerKurier_DeliveryOrderLine_ID == 0)
        {
			setC_Country_ID (0);
			setDerKurier_DeliveryOrder_ID (0);
			setDerKurier_DeliveryOrderLine_ID (0);
			setDK_Consignee_City (null);
			setDK_Consignee_Country (null);
			setDK_Consignee_HouseNumber (null);
			setDK_Consignee_Name (null);
			setDK_Consignee_ZipCode (null);
			setDK_CustomerNumber (null);
			setDK_PackageAmount (0);
			setDK_ParcelNumber (null);
			setDK_ParcelWeight (BigDecimal.ZERO);
			setLine (0);
        } */
    }

    /** Load Constructor */
    public X_DerKurier_DeliveryOrderLine (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	/** Set Land.
		@param C_Country_ID 
		Land
	  */
	@Override
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Land.
		@return Land
	  */
	@Override
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder getDerKurier_DeliveryOrder() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DerKurier_DeliveryOrder_ID, de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder.class);
	}

	@Override
	public void setDerKurier_DeliveryOrder(de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder DerKurier_DeliveryOrder)
	{
		set_ValueFromPO(COLUMNNAME_DerKurier_DeliveryOrder_ID, de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder.class, DerKurier_DeliveryOrder);
	}

	/** Set DerKurier_DeliveryOrder.
		@param DerKurier_DeliveryOrder_ID DerKurier_DeliveryOrder	  */
	@Override
	public void setDerKurier_DeliveryOrder_ID (int DerKurier_DeliveryOrder_ID)
	{
		if (DerKurier_DeliveryOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DerKurier_DeliveryOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DerKurier_DeliveryOrder_ID, Integer.valueOf(DerKurier_DeliveryOrder_ID));
	}

	/** Get DerKurier_DeliveryOrder.
		@return DerKurier_DeliveryOrder	  */
	@Override
	public int getDerKurier_DeliveryOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DerKurier_DeliveryOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DerKurier_DeliveryOrderLine.
		@param DerKurier_DeliveryOrderLine_ID DerKurier_DeliveryOrderLine	  */
	@Override
	public void setDerKurier_DeliveryOrderLine_ID (int DerKurier_DeliveryOrderLine_ID)
	{
		if (DerKurier_DeliveryOrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DerKurier_DeliveryOrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DerKurier_DeliveryOrderLine_ID, Integer.valueOf(DerKurier_DeliveryOrderLine_ID));
	}

	/** Get DerKurier_DeliveryOrderLine.
		@return DerKurier_DeliveryOrderLine	  */
	@Override
	public int getDerKurier_DeliveryOrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DerKurier_DeliveryOrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Empfänger Ort.
		@param DK_Consignee_City Empfänger Ort	  */
	@Override
	public void setDK_Consignee_City (java.lang.String DK_Consignee_City)
	{
		set_Value (COLUMNNAME_DK_Consignee_City, DK_Consignee_City);
	}

	/** Get Empfänger Ort.
		@return Empfänger Ort	  */
	@Override
	public java.lang.String getDK_Consignee_City () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Consignee_City);
	}

	/** Set Empfänger Land.
		@param DK_Consignee_Country 
		Zweistelliger ISO-3166 Ländercode
	  */
	@Override
	public void setDK_Consignee_Country (java.lang.String DK_Consignee_Country)
	{
		set_Value (COLUMNNAME_DK_Consignee_Country, DK_Consignee_Country);
	}

	/** Get Empfänger Land.
		@return Zweistelliger ISO-3166 Ländercode
	  */
	@Override
	public java.lang.String getDK_Consignee_Country () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Consignee_Country);
	}

	/** Set Gewünschtes Lieferdepot.
		@param DK_Consignee_DesiredStation Gewünschtes Lieferdepot	  */
	@Override
	public void setDK_Consignee_DesiredStation (java.lang.String DK_Consignee_DesiredStation)
	{
		set_Value (COLUMNNAME_DK_Consignee_DesiredStation, DK_Consignee_DesiredStation);
	}

	/** Get Gewünschtes Lieferdepot.
		@return Gewünschtes Lieferdepot	  */
	@Override
	public java.lang.String getDK_Consignee_DesiredStation () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Consignee_DesiredStation);
	}

	/** Set Empfänger Email.
		@param DK_Consignee_EMail Empfänger Email	  */
	@Override
	public void setDK_Consignee_EMail (java.lang.String DK_Consignee_EMail)
	{
		set_Value (COLUMNNAME_DK_Consignee_EMail, DK_Consignee_EMail);
	}

	/** Get Empfänger Email.
		@return Empfänger Email	  */
	@Override
	public java.lang.String getDK_Consignee_EMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Consignee_EMail);
	}

	/** Set Empfänger Hausnummer.
		@param DK_Consignee_HouseNumber Empfänger Hausnummer	  */
	@Override
	public void setDK_Consignee_HouseNumber (java.lang.String DK_Consignee_HouseNumber)
	{
		set_Value (COLUMNNAME_DK_Consignee_HouseNumber, DK_Consignee_HouseNumber);
	}

	/** Get Empfänger Hausnummer.
		@return Empfänger Hausnummer	  */
	@Override
	public java.lang.String getDK_Consignee_HouseNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Consignee_HouseNumber);
	}

	/** Set Empfänger Name.
		@param DK_Consignee_Name Empfänger Name	  */
	@Override
	public void setDK_Consignee_Name (java.lang.String DK_Consignee_Name)
	{
		set_Value (COLUMNNAME_DK_Consignee_Name, DK_Consignee_Name);
	}

	/** Get Empfänger Name.
		@return Empfänger Name	  */
	@Override
	public java.lang.String getDK_Consignee_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Consignee_Name);
	}

	/** Set Empfänger Name2.
		@param DK_Consignee_Name2 Empfänger Name2	  */
	@Override
	public void setDK_Consignee_Name2 (java.lang.String DK_Consignee_Name2)
	{
		set_Value (COLUMNNAME_DK_Consignee_Name2, DK_Consignee_Name2);
	}

	/** Get Empfänger Name2.
		@return Empfänger Name2	  */
	@Override
	public java.lang.String getDK_Consignee_Name2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Consignee_Name2);
	}

	/** Set Empfänger Name3.
		@param DK_Consignee_Name3 Empfänger Name3	  */
	@Override
	public void setDK_Consignee_Name3 (java.lang.String DK_Consignee_Name3)
	{
		set_Value (COLUMNNAME_DK_Consignee_Name3, DK_Consignee_Name3);
	}

	/** Get Empfänger Name3.
		@return Empfänger Name3	  */
	@Override
	public java.lang.String getDK_Consignee_Name3 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Consignee_Name3);
	}

	/** Set Empfänger Telefon.
		@param DK_Consignee_Phone Empfänger Telefon	  */
	@Override
	public void setDK_Consignee_Phone (java.lang.String DK_Consignee_Phone)
	{
		set_Value (COLUMNNAME_DK_Consignee_Phone, DK_Consignee_Phone);
	}

	/** Get Empfänger Telefon.
		@return Empfänger Telefon	  */
	@Override
	public java.lang.String getDK_Consignee_Phone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Consignee_Phone);
	}

	/** Set Empfänger Strasse.
		@param DK_Consignee_Street Empfänger Strasse	  */
	@Override
	public void setDK_Consignee_Street (java.lang.String DK_Consignee_Street)
	{
		set_Value (COLUMNNAME_DK_Consignee_Street, DK_Consignee_Street);
	}

	/** Get Empfänger Strasse.
		@return Empfänger Strasse	  */
	@Override
	public java.lang.String getDK_Consignee_Street () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Consignee_Street);
	}

	/** Set Empfänger PLZ.
		@param DK_Consignee_ZipCode 
		Postleitzahl
	  */
	@Override
	public void setDK_Consignee_ZipCode (java.lang.String DK_Consignee_ZipCode)
	{
		set_Value (COLUMNNAME_DK_Consignee_ZipCode, DK_Consignee_ZipCode);
	}

	/** Get Empfänger PLZ.
		@return Postleitzahl
	  */
	@Override
	public java.lang.String getDK_Consignee_ZipCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Consignee_ZipCode);
	}

	/** Set Kundennummer.
		@param DK_CustomerNumber Kundennummer	  */
	@Override
	public void setDK_CustomerNumber (java.lang.String DK_CustomerNumber)
	{
		set_Value (COLUMNNAME_DK_CustomerNumber, DK_CustomerNumber);
	}

	/** Get Kundennummer.
		@return Kundennummer	  */
	@Override
	public java.lang.String getDK_CustomerNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_CustomerNumber);
	}

	/** Set Gewünschtes Lieferdatum.
		@param DK_DesiredDeliveryDate Gewünschtes Lieferdatum	  */
	@Override
	public void setDK_DesiredDeliveryDate (java.sql.Timestamp DK_DesiredDeliveryDate)
	{
		set_Value (COLUMNNAME_DK_DesiredDeliveryDate, DK_DesiredDeliveryDate);
	}

	/** Get Gewünschtes Lieferdatum.
		@return Gewünschtes Lieferdatum	  */
	@Override
	public java.sql.Timestamp getDK_DesiredDeliveryDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DK_DesiredDeliveryDate);
	}

	/** Set Gewünschte Lieferuhrzeit von.
		@param DK_DesiredDeliveryTime_From Gewünschte Lieferuhrzeit von	  */
	@Override
	public void setDK_DesiredDeliveryTime_From (java.sql.Timestamp DK_DesiredDeliveryTime_From)
	{
		set_Value (COLUMNNAME_DK_DesiredDeliveryTime_From, DK_DesiredDeliveryTime_From);
	}

	/** Get Gewünschte Lieferuhrzeit von.
		@return Gewünschte Lieferuhrzeit von	  */
	@Override
	public java.sql.Timestamp getDK_DesiredDeliveryTime_From () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DK_DesiredDeliveryTime_From);
	}

	/** Set Gewünschte Lieferuhrzeit bis.
		@param DK_DesiredDeliveryTime_To Gewünschte Lieferuhrzeit bis	  */
	@Override
	public void setDK_DesiredDeliveryTime_To (java.sql.Timestamp DK_DesiredDeliveryTime_To)
	{
		set_Value (COLUMNNAME_DK_DesiredDeliveryTime_To, DK_DesiredDeliveryTime_To);
	}

	/** Get Gewünschte Lieferuhrzeit bis.
		@return Gewünschte Lieferuhrzeit bis	  */
	@Override
	public java.sql.Timestamp getDK_DesiredDeliveryTime_To () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DK_DesiredDeliveryTime_To);
	}

	/** Set Gesamtpaketanzahl.
		@param DK_PackageAmount Gesamtpaketanzahl	  */
	@Override
	public void setDK_PackageAmount (int DK_PackageAmount)
	{
		set_Value (COLUMNNAME_DK_PackageAmount, Integer.valueOf(DK_PackageAmount));
	}

	/** Get Gesamtpaketanzahl.
		@return Gesamtpaketanzahl	  */
	@Override
	public int getDK_PackageAmount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DK_PackageAmount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pakethöhe.
		@param DK_ParcelHeight Pakethöhe	  */
	@Override
	public void setDK_ParcelHeight (java.math.BigDecimal DK_ParcelHeight)
	{
		set_Value (COLUMNNAME_DK_ParcelHeight, DK_ParcelHeight);
	}

	/** Get Pakethöhe.
		@return Pakethöhe	  */
	@Override
	public java.math.BigDecimal getDK_ParcelHeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DK_ParcelHeight);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Paketlänge.
		@param DK_ParcelLength Paketlänge	  */
	@Override
	public void setDK_ParcelLength (java.math.BigDecimal DK_ParcelLength)
	{
		set_Value (COLUMNNAME_DK_ParcelLength, DK_ParcelLength);
	}

	/** Get Paketlänge.
		@return Paketlänge	  */
	@Override
	public java.math.BigDecimal getDK_ParcelLength () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DK_ParcelLength);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Sendungsnummer.
		@param DK_ParcelNumber Sendungsnummer	  */
	@Override
	public void setDK_ParcelNumber (java.lang.String DK_ParcelNumber)
	{
		set_Value (COLUMNNAME_DK_ParcelNumber, DK_ParcelNumber);
	}

	/** Get Sendungsnummer.
		@return Sendungsnummer	  */
	@Override
	public java.lang.String getDK_ParcelNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_ParcelNumber);
	}

	/** Set Paketgewicht.
		@param DK_ParcelWeight Paketgewicht	  */
	@Override
	public void setDK_ParcelWeight (java.math.BigDecimal DK_ParcelWeight)
	{
		set_Value (COLUMNNAME_DK_ParcelWeight, DK_ParcelWeight);
	}

	/** Get Paketgewicht.
		@return Paketgewicht	  */
	@Override
	public java.math.BigDecimal getDK_ParcelWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DK_ParcelWeight);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Paketbreite.
		@param DK_ParcelWidth Paketbreite	  */
	@Override
	public void setDK_ParcelWidth (java.math.BigDecimal DK_ParcelWidth)
	{
		set_Value (COLUMNNAME_DK_ParcelWidth, DK_ParcelWidth);
	}

	/** Get Paketbreite.
		@return Paketbreite	  */
	@Override
	public java.math.BigDecimal getDK_ParcelWidth () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DK_ParcelWidth);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Referenz.
		@param DK_Reference Referenz	  */
	@Override
	public void setDK_Reference (java.lang.String DK_Reference)
	{
		set_Value (COLUMNNAME_DK_Reference, DK_Reference);
	}

	/** Get Referenz.
		@return Referenz	  */
	@Override
	public java.lang.String getDK_Reference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Reference);
	}

	/** Set Zeile Nr..
		@param Line 
		Einzelne Zeile in dem Dokument
	  */
	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Einzelne Zeile in dem Dokument
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}