/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Package
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_M_Package extends org.compiere.model.PO implements I_M_Package, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -623218126L;

	/** Standard Constructor */
    public X_M_Package (Properties ctx, int M_Package_ID, String trxName)
    {
      super (ctx, M_Package_ID, trxName);
      /** if (M_Package_ID == 0)
        {
			setDocumentNo (null);
			setIsClosed (false); // N
			setM_Package_ID (0);
			setM_Shipper_ID (0);
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_M_Package (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Eingangsdatum.
		@param DateReceived
		Date a product was received
	  */
	@Override
	public void setDateReceived (java.sql.Timestamp DateReceived)
	{
		set_Value (COLUMNNAME_DateReceived, DateReceived);
	}

	/** Get Eingangsdatum.
		@return Date a product was received
	  */
	@Override
	public java.sql.Timestamp getDateReceived ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateReceived);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Nr..
		@param DocumentNo
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Geschlossen.
		@param IsClosed Geschlossen	  */
	@Override
	public void setIsClosed (boolean IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, Boolean.valueOf(IsClosed));
	}

	/** Get Geschlossen.
		@return Geschlossen	  */
	@Override
	public boolean isClosed ()
	{
		Object oo = get_Value(COLUMNNAME_IsClosed);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	/** Set Lieferung/Wareneingang.
		@param M_InOut_ID
		Material Shipment Document
	  */
	@Override
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Lieferung/Wareneingang.
		@return Material Shipment Document
	  */
	@Override
	public int getM_InOut_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Packstück.
		@param M_Package_ID
		Shipment Package
	  */
	@Override
	public void setM_Package_ID (int M_Package_ID)
	{
		if (M_Package_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, Integer.valueOf(M_Package_ID));
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


	/** Set Verpackung.
		@param M_PackagingContainer_ID Verpackung	  */
	@Override
	public void setM_PackagingContainer_ID (int M_PackagingContainer_ID)
	{
		if (M_PackagingContainer_ID < 1)
			set_Value (COLUMNNAME_M_PackagingContainer_ID, null);
		else
			set_Value (COLUMNNAME_M_PackagingContainer_ID, Integer.valueOf(M_PackagingContainer_ID));
	}

	/** Get Verpackung.
		@return Verpackung	  */
	@Override
	public int getM_PackagingContainer_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PackagingContainer_ID);
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
		Method or manner of product delivery
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
		@return Method or manner of product delivery
	  */
	@Override
	public int getM_Shipper_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ziel-Lager.
		@param M_Warehouse_Dest_ID Ziel-Lager	  */
	@Override
	public void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID)
	{
		if (M_Warehouse_Dest_ID < 1)
			set_Value (COLUMNNAME_M_Warehouse_Dest_ID, null);
		else
			set_Value (COLUMNNAME_M_Warehouse_Dest_ID, Integer.valueOf(M_Warehouse_Dest_ID));
	}

	/** Get Ziel-Lager.
		@return Ziel-Lager	  */
	@Override
	public int getM_Warehouse_Dest_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_Dest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Package Net Total.
		@param PackageNetTotal Package Net Total	  */
	@Override
	public void setPackageNetTotal (java.math.BigDecimal PackageNetTotal)
	{
		set_Value (COLUMNNAME_PackageNetTotal, PackageNetTotal);
	}

	/** Get Package Net Total.
		@return Package Net Total	  */
	@Override
	public java.math.BigDecimal getPackageNetTotal ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PackageNetTotal);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Package Weight.
		@param PackageWeight
		Weight of a package
	  */
	@Override
	public void setPackageWeight (java.math.BigDecimal PackageWeight)
	{
		set_Value (COLUMNNAME_PackageWeight, PackageWeight);
	}

	/** Get Package Weight.
		@return Weight of a package
	  */
	@Override
	public java.math.BigDecimal getPackageWeight ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PackageWeight);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Verarbeitet.
		@param Processed
		Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Datensatz verarbeitet wurde.
	  */
	@Override
	public boolean isProcessed ()
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Info Received.
		@param ReceivedInfo
		Information of the receipt of the package (acknowledgement)
	  */
	@Override
	public void setReceivedInfo (java.lang.String ReceivedInfo)
	{
		set_Value (COLUMNNAME_ReceivedInfo, ReceivedInfo);
	}

	/** Get Info Received.
		@return Information of the receipt of the package (acknowledgement)
	  */
	@Override
	public java.lang.String getReceivedInfo ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReceivedInfo);
	}

	/** Set Lieferdatum.
		@param ShipDate
		Shipment Date/Time
	  */
	@Override
	public void setShipDate (java.sql.Timestamp ShipDate)
	{
		set_Value (COLUMNNAME_ShipDate, ShipDate);
	}

	/** Get Lieferdatum.
		@return Shipment Date/Time
	  */
	@Override
	public java.sql.Timestamp getShipDate ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ShipDate);
	}

	/** Set Tracking Info.
		@param TrackingInfo Tracking Info	  */
	@Override
	public void setTrackingInfo (java.lang.String TrackingInfo)
	{
		set_Value (COLUMNNAME_TrackingInfo, TrackingInfo);
	}

	/** Get Tracking Info.
		@return Tracking Info	  */
	@Override
	public java.lang.String getTrackingInfo ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_TrackingInfo);
	}

	@Override
	public void setTrackingURL (java.lang.String TrackingURL)
	{
		set_Value (COLUMNNAME_TrackingURL, TrackingURL);
	}

	@Override
	public java.lang.String getTrackingURL()
	{
		return (java.lang.String)get_Value(COLUMNNAME_TrackingURL);
	}

}
