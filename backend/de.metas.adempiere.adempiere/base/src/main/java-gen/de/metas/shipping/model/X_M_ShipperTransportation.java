/** Generated Model - DO NOT CHANGE */
package de.metas.shipping.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ShipperTransportation
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ShipperTransportation extends org.compiere.model.PO implements I_M_ShipperTransportation, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -578881320L;

    /** Standard Constructor */
    public X_M_ShipperTransportation (Properties ctx, int M_ShipperTransportation_ID, String trxName)
    {
      super (ctx, M_ShipperTransportation_ID, trxName);
      /** if (M_ShipperTransportation_ID == 0)
        {
			setC_DocType_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() )); // @#Date@
			setDocAction (null); // CO
			setDocStatus (null); // DR
			setDocumentNo (null);
			setIsApproved (false);
			setM_Shipper_ID (0);
			setM_ShipperTransportation_ID (0);
			setProcessed (false);
			setShipper_BPartner_ID (0);
			setShipper_Location_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_ShipperTransportation (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Ladeliste erstellen.
		@param BillLadingReport Ladeliste erstellen	  */
	@Override
	public void setBillLadingReport (java.lang.String BillLadingReport)
	{
		set_Value (COLUMNNAME_BillLadingReport, BillLadingReport);
	}

	/** Get Ladeliste erstellen.
		@return Ladeliste erstellen	  */
	@Override
	public java.lang.String getBillLadingReport () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BillLadingReport);
	}

	/** Set Belegart.
		@param C_DocType_ID 
		Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sammelrechnung erstellen.
		@param CollectiveBillReport Sammelrechnung erstellen	  */
	@Override
	public void setCollectiveBillReport (java.lang.String CollectiveBillReport)
	{
		set_Value (COLUMNNAME_CollectiveBillReport, CollectiveBillReport);
	}

	/** Get Sammelrechnung erstellen.
		@return Sammelrechnung erstellen	  */
	@Override
	public java.lang.String getCollectiveBillReport () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CollectiveBillReport);
	}

	/** Set Packstücke erfassen.
		@param CreateShippingPackages Packstücke erfassen	  */
	@Override
	public void setCreateShippingPackages (java.lang.String CreateShippingPackages)
	{
		set_Value (COLUMNNAME_CreateShippingPackages, CreateShippingPackages);
	}

	/** Get Packstücke erfassen.
		@return Packstücke erfassen	  */
	@Override
	public java.lang.String getCreateShippingPackages () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreateShippingPackages);
	}

	/** Set Belegdatum.
		@param DateDoc 
		Datum des Belegs
	  */
	@Override
	public void setDateDoc (java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Belegdatum.
		@return Datum des Belegs
	  */
	@Override
	public java.sql.Timestamp getDateDoc () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Abholung am.
		@param DateToBeFetched 
		Date and time to fetch
	  */
	@Override
	public void setDateToBeFetched (java.sql.Timestamp DateToBeFetched)
	{
		set_Value (COLUMNNAME_DateToBeFetched, DateToBeFetched);
	}

	/** Get Abholung am.
		@return Date and time to fetch
	  */
	@Override
	public java.sql.Timestamp getDateToBeFetched () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateToBeFetched);
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

	/** 
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse_Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse_Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re_Activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** UnClose = UC */
	public static final String DOCACTION_UnClose = "UC";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	@Override
	public void setDocAction (java.lang.String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	@Override
	public java.lang.String getDocAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocAction);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	@Override
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	@Override
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
			set_ValueNoCheck (COLUMNNAME_M_ShipperTransportation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipperTransportation_ID, Integer.valueOf(M_ShipperTransportation_ID));
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

	/** Set Tour.
		@param M_Tour_ID Tour	  */
	@Override
	public void setM_Tour_ID (int M_Tour_ID)
	{
		if (M_Tour_ID < 1) 
			set_Value (COLUMNNAME_M_Tour_ID, null);
		else 
			set_Value (COLUMNNAME_M_Tour_ID, Integer.valueOf(M_Tour_ID));
	}

	/** Get Tour.
		@return Tour	  */
	@Override
	public int getM_Tour_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Tour_ID);
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

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
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

	/** Set Process Now.
		@param Processing Process Now	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kundenbetreuer.
		@param SalesRep_ID Kundenbetreuer	  */
	@Override
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Kundenbetreuer.
		@return Kundenbetreuer	  */
	@Override
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipper Partner.
		@param Shipper_BPartner_ID 
		Business Partner to be used as shipper
	  */
	@Override
	public void setShipper_BPartner_ID (int Shipper_BPartner_ID)
	{
		if (Shipper_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Shipper_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Shipper_BPartner_ID, Integer.valueOf(Shipper_BPartner_ID));
	}

	/** Get Shipper Partner.
		@return Business Partner to be used as shipper
	  */
	@Override
	public int getShipper_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Shipper_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipper Location.
		@param Shipper_Location_ID 
		Business Partner Location for Shipper
	  */
	@Override
	public void setShipper_Location_ID (int Shipper_Location_ID)
	{
		if (Shipper_Location_ID < 1) 
			set_Value (COLUMNNAME_Shipper_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Shipper_Location_ID, Integer.valueOf(Shipper_Location_ID));
	}

	/** Get Shipper Location.
		@return Business Partner Location for Shipper
	  */
	@Override
	public int getShipper_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Shipper_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}