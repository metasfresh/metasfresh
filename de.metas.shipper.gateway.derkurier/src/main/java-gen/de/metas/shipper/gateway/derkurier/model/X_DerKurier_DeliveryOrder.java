/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.derkurier.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DerKurier_DeliveryOrder
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DerKurier_DeliveryOrder extends org.compiere.model.PO implements I_DerKurier_DeliveryOrder, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1494709073L;

    /** Standard Constructor */
    public X_DerKurier_DeliveryOrder (Properties ctx, int DerKurier_DeliveryOrder_ID, String trxName)
    {
      super (ctx, DerKurier_DeliveryOrder_ID, trxName);
      /** if (DerKurier_DeliveryOrder_ID == 0)
        {
			setDerKurier_DeliveryOrder_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DerKurier_DeliveryOrder (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Gewünschtes Abholdatum.
		@param DK_DesiredPickupDate Gewünschtes Abholdatum	  */
	@Override
	public void setDK_DesiredPickupDate (java.sql.Timestamp DK_DesiredPickupDate)
	{
		set_Value (COLUMNNAME_DK_DesiredPickupDate, DK_DesiredPickupDate);
	}

	/** Get Gewünschtes Abholdatum.
		@return Gewünschtes Abholdatum	  */
	@Override
	public java.sql.Timestamp getDK_DesiredPickupDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DK_DesiredPickupDate);
	}

	/** Set Gewünschte Abholuhrzeit von.
		@param DK_DesiredPickupTime_From Gewünschte Abholuhrzeit von	  */
	@Override
	public void setDK_DesiredPickupTime_From (java.sql.Timestamp DK_DesiredPickupTime_From)
	{
		set_Value (COLUMNNAME_DK_DesiredPickupTime_From, DK_DesiredPickupTime_From);
	}

	/** Get Gewünschte Abholuhrzeit von.
		@return Gewünschte Abholuhrzeit von	  */
	@Override
	public java.sql.Timestamp getDK_DesiredPickupTime_From () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DK_DesiredPickupTime_From);
	}

	/** Set Gewünschte Abholuhrzeit bis.
		@param DK_DesiredPickupTime_To Gewünschte Abholuhrzeit bis	  */
	@Override
	public void setDK_DesiredPickupTime_To (java.sql.Timestamp DK_DesiredPickupTime_To)
	{
		set_Value (COLUMNNAME_DK_DesiredPickupTime_To, DK_DesiredPickupTime_To);
	}

	/** Get Gewünschte Abholuhrzeit bis.
		@return Gewünschte Abholuhrzeit bis	  */
	@Override
	public java.sql.Timestamp getDK_DesiredPickupTime_To () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DK_DesiredPickupTime_To);
	}

	/** Set Absender Ort.
		@param DK_Sender_City Absender Ort	  */
	@Override
	public void setDK_Sender_City (java.lang.String DK_Sender_City)
	{
		set_Value (COLUMNNAME_DK_Sender_City, DK_Sender_City);
	}

	/** Get Absender Ort.
		@return Absender Ort	  */
	@Override
	public java.lang.String getDK_Sender_City () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Sender_City);
	}

	/** Set Absender Land.
		@param DK_Sender_Country 
		Zweistelliger ISO-3166 Ländercode
	  */
	@Override
	public void setDK_Sender_Country (java.lang.String DK_Sender_Country)
	{
		set_Value (COLUMNNAME_DK_Sender_Country, DK_Sender_Country);
	}

	/** Get Absender Land.
		@return Zweistelliger ISO-3166 Ländercode
	  */
	@Override
	public java.lang.String getDK_Sender_Country () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Sender_Country);
	}

	/** Set Absender Hausnummer.
		@param DK_Sender_HouseNumber Absender Hausnummer	  */
	@Override
	public void setDK_Sender_HouseNumber (java.lang.String DK_Sender_HouseNumber)
	{
		set_Value (COLUMNNAME_DK_Sender_HouseNumber, DK_Sender_HouseNumber);
	}

	/** Get Absender Hausnummer.
		@return Absender Hausnummer	  */
	@Override
	public java.lang.String getDK_Sender_HouseNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Sender_HouseNumber);
	}

	/** Set Absender Name.
		@param DK_Sender_Name Absender Name	  */
	@Override
	public void setDK_Sender_Name (java.lang.String DK_Sender_Name)
	{
		set_Value (COLUMNNAME_DK_Sender_Name, DK_Sender_Name);
	}

	/** Get Absender Name.
		@return Absender Name	  */
	@Override
	public java.lang.String getDK_Sender_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Sender_Name);
	}

	/** Set Absender Name2.
		@param DK_Sender_Name2 Absender Name2	  */
	@Override
	public void setDK_Sender_Name2 (java.lang.String DK_Sender_Name2)
	{
		set_Value (COLUMNNAME_DK_Sender_Name2, DK_Sender_Name2);
	}

	/** Get Absender Name2.
		@return Absender Name2	  */
	@Override
	public java.lang.String getDK_Sender_Name2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Sender_Name2);
	}

	/** Set Absender Name3.
		@param DK_Sender_Name3 Absender Name3	  */
	@Override
	public void setDK_Sender_Name3 (java.lang.String DK_Sender_Name3)
	{
		set_Value (COLUMNNAME_DK_Sender_Name3, DK_Sender_Name3);
	}

	/** Get Absender Name3.
		@return Absender Name3	  */
	@Override
	public java.lang.String getDK_Sender_Name3 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Sender_Name3);
	}

	/** Set Absender Strasse.
		@param DK_Sender_Street Absender Strasse	  */
	@Override
	public void setDK_Sender_Street (java.lang.String DK_Sender_Street)
	{
		set_Value (COLUMNNAME_DK_Sender_Street, DK_Sender_Street);
	}

	/** Get Absender Strasse.
		@return Absender Strasse	  */
	@Override
	public java.lang.String getDK_Sender_Street () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Sender_Street);
	}

	/** Set Absender PLZ.
		@param DK_Sender_ZipCode 
		Postleitzahl
	  */
	@Override
	public void setDK_Sender_ZipCode (java.lang.String DK_Sender_ZipCode)
	{
		set_Value (COLUMNNAME_DK_Sender_ZipCode, DK_Sender_ZipCode);
	}

	/** Get Absender PLZ.
		@return Postleitzahl
	  */
	@Override
	public java.lang.String getDK_Sender_ZipCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_Sender_ZipCode);
	}
}