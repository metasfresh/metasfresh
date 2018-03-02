/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ShipmentSchedule
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ShipmentSchedule extends org.compiere.model.PO implements I_M_ShipmentSchedule, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1274970292L;

    /** Standard Constructor */
    public X_M_ShipmentSchedule (Properties ctx, int M_ShipmentSchedule_ID, String trxName)
    {
      super (ctx, M_ShipmentSchedule_ID, trxName);
      /** if (M_ShipmentSchedule_ID == 0)
        {
			setAD_Table_ID (0);
			setBill_BPartner_ID (0);
			setBPartnerAddress (null);
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setDeliveryRule (null);
			setDeliveryViaRule (null);
			setIsBPartnerAddress_Override (false); // N
			setIsClosed (false); // N
			setIsDeliveryStop (false); // N
			setIsDisplayed (true); // Y
			setIsDropShip (false); // N
			setM_ShipmentSchedule_ID (0);
			setM_Warehouse_ID (0);
			setProcessed (false); // N
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_ShipmentSchedule (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User_Override() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_Override_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User_Override(org.compiere.model.I_AD_User AD_User_Override)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_Override_ID, org.compiere.model.I_AD_User.class, AD_User_Override);
	}

	/** Set Ansprechpartner abw..
		@param AD_User_Override_ID Ansprechpartner abw.	  */
	@Override
	public void setAD_User_Override_ID (int AD_User_Override_ID)
	{
		if (AD_User_Override_ID < 1) 
			set_Value (COLUMNNAME_AD_User_Override_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_Override_ID, Integer.valueOf(AD_User_Override_ID));
	}

	/** Get Ansprechpartner abw..
		@return Ansprechpartner abw.	  */
	@Override
	public int getAD_User_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sammel-Lieferscheine erlaubt.
		@param AllowConsolidateInOut Sammel-Lieferscheine erlaubt	  */
	@Override
	public void setAllowConsolidateInOut (boolean AllowConsolidateInOut)
	{
		set_Value (COLUMNNAME_AllowConsolidateInOut, Boolean.valueOf(AllowConsolidateInOut));
	}

	/** Get Sammel-Lieferscheine erlaubt.
		@return Sammel-Lieferscheine erlaubt	  */
	@Override
	public boolean isAllowConsolidateInOut () 
	{
		Object oo = get_Value(COLUMNNAME_AllowConsolidateInOut);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_C_BPartner getBill_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Bill_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setBill_BPartner(org.compiere.model.I_C_BPartner Bill_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_Bill_BPartner_ID, org.compiere.model.I_C_BPartner.class, Bill_BPartner);
	}

	/** Set Rechnungspartner.
		@param Bill_BPartner_ID 
		Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
	}

	/** Get Rechnungspartner.
		@return Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Anschrift-Text.
		@param BPartnerAddress Anschrift-Text	  */
	@Override
	public void setBPartnerAddress (java.lang.String BPartnerAddress)
	{
		set_ValueNoCheck (COLUMNNAME_BPartnerAddress, BPartnerAddress);
	}

	/** Get Anschrift-Text.
		@return Anschrift-Text	  */
	@Override
	public java.lang.String getBPartnerAddress () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerAddress);
	}

	/** Set Anschrift-Text abw..
		@param BPartnerAddress_Override Anschrift-Text abw.	  */
	@Override
	public void setBPartnerAddress_Override (java.lang.String BPartnerAddress_Override)
	{
		set_Value (COLUMNNAME_BPartnerAddress_Override, BPartnerAddress_Override);
	}

	/** Get Anschrift-Text abw..
		@return Anschrift-Text abw.	  */
	@Override
	public java.lang.String getBPartnerAddress_Override () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerAddress_Override);
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
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

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner_Override() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Override_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner_Override(org.compiere.model.I_C_BPartner C_BPartner_Override)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Override_ID, org.compiere.model.I_C_BPartner.class, C_BPartner_Override);
	}

	/** Set Geschäftspartner abw..
		@param C_BPartner_Override_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_Override_ID (int C_BPartner_Override_ID)
	{
		if (C_BPartner_Override_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Override_ID, Integer.valueOf(C_BPartner_Override_ID));
	}

	/** Get Geschäftspartner abw..
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner_Vendor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Vendor_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner_Vendor(org.compiere.model.I_C_BPartner C_BPartner_Vendor)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Vendor_ID, org.compiere.model.I_C_BPartner.class, C_BPartner_Vendor);
	}

	/** Set C_BPartner_Vendor_ID.
		@param C_BPartner_Vendor_ID C_BPartner_Vendor_ID	  */
	@Override
	public void setC_BPartner_Vendor_ID (int C_BPartner_Vendor_ID)
	{
		if (C_BPartner_Vendor_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, Integer.valueOf(C_BPartner_Vendor_ID));
	}

	/** Get C_BPartner_Vendor_ID.
		@return C_BPartner_Vendor_ID	  */
	@Override
	public int getC_BPartner_Vendor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Vendor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BP_Location_Override() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Location_Override_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BP_Location_Override(org.compiere.model.I_C_BPartner_Location C_BP_Location_Override)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Location_Override_ID, org.compiere.model.I_C_BPartner_Location.class, C_BP_Location_Override);
	}

	/** Set Standort abw..
		@param C_BP_Location_Override_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BP_Location_Override_ID (int C_BP_Location_Override_ID)
	{
		if (C_BP_Location_Override_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Location_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Location_Override_ID, Integer.valueOf(C_BP_Location_Override_ID));
	}

	/** Get Standort abw..
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BP_Location_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Location_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		throw new IllegalArgumentException ("C_Currency_ID is virtual column");	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
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

	@Override
	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	/** Set Auftrag.
		@param C_Order_ID 
		Order
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Auftrag.
		@return Order
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	/** Set Auftragsposition.
		@param C_OrderLine_ID 
		Auftragsposition
	  */
	@Override
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Auftragsposition.
		@return Auftragsposition
	  */
	@Override
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		throw new IllegalArgumentException ("C_UOM_ID is virtual column");	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Auftragsdatum.
		@param DateOrdered 
		Datum des Auftrags
	  */
	@Override
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_DateOrdered, DateOrdered);
	}

	/** Get Auftragsdatum.
		@return Datum des Auftrags
	  */
	@Override
	public java.sql.Timestamp getDateOrdered () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateOrdered);
	}

	/** Set Lieferdatum.
		@param DeliveryDate Lieferdatum	  */
	@Override
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate)
	{
		set_Value (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	/** Get Lieferdatum.
		@return Lieferdatum	  */
	@Override
	public java.sql.Timestamp getDeliveryDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DeliveryDate);
	}

	/** Set Lieferdatum eff..
		@param DeliveryDate_Effective Lieferdatum eff.	  */
	@Override
	public void setDeliveryDate_Effective (java.sql.Timestamp DeliveryDate_Effective)
	{
		throw new IllegalArgumentException ("DeliveryDate_Effective is virtual column");	}

	/** Get Lieferdatum eff..
		@return Lieferdatum eff.	  */
	@Override
	public java.sql.Timestamp getDeliveryDate_Effective () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DeliveryDate_Effective);
	}

	/** Set Lieferdatum abw.
		@param DeliveryDate_Override Lieferdatum abw	  */
	@Override
	public void setDeliveryDate_Override (java.sql.Timestamp DeliveryDate_Override)
	{
		set_Value (COLUMNNAME_DeliveryDate_Override, DeliveryDate_Override);
	}

	/** Get Lieferdatum abw.
		@return Lieferdatum abw	  */
	@Override
	public java.sql.Timestamp getDeliveryDate_Override () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DeliveryDate_Override);
	}

	/** 
	 * DeliveryRule AD_Reference_ID=151
	 * Reference name: C_Order DeliveryRule
	 */
	public static final int DELIVERYRULE_AD_Reference_ID=151;
	/** AfterReceipt = R */
	public static final String DELIVERYRULE_AfterReceipt = "R";
	/** Availability = A */
	public static final String DELIVERYRULE_Availability = "A";
	/** CompleteLine = L */
	public static final String DELIVERYRULE_CompleteLine = "L";
	/** CompleteOrder = O */
	public static final String DELIVERYRULE_CompleteOrder = "O";
	/** Force = F */
	public static final String DELIVERYRULE_Force = "F";
	/** Manual = M */
	public static final String DELIVERYRULE_Manual = "M";
	/** MitNaechsterAbolieferung = S */
	public static final String DELIVERYRULE_MitNaechsterAbolieferung = "S";
	/** Set Lieferart.
		@param DeliveryRule 
		Definiert die zeitliche Steuerung von Lieferungen
	  */
	@Override
	public void setDeliveryRule (java.lang.String DeliveryRule)
	{

		set_ValueNoCheck (COLUMNNAME_DeliveryRule, DeliveryRule);
	}

	/** Get Lieferart.
		@return Definiert die zeitliche Steuerung von Lieferungen
	  */
	@Override
	public java.lang.String getDeliveryRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryRule);
	}

	/** 
	 * DeliveryRule_Override AD_Reference_ID=540009
	 * Reference name: M_ShipmentSchedule DeliveryRule
	 */
	public static final int DELIVERYRULE_OVERRIDE_AD_Reference_ID=540009;
	/** Verfügbarkeit = A */
	public static final String DELIVERYRULE_OVERRIDE_Verfuegbarkeit = "A";
	/** Erzwungen = F */
	public static final String DELIVERYRULE_OVERRIDE_Erzwungen = "F";
	/** Position komplett = L */
	public static final String DELIVERYRULE_OVERRIDE_PositionKomplett = "L";
	/** Manuell = M */
	public static final String DELIVERYRULE_OVERRIDE_Manuell = "M";
	/** After Receipt = R */
	public static final String DELIVERYRULE_OVERRIDE_AfterReceipt = "R";
	/** Mit nächster Abolieferung = S */
	public static final String DELIVERYRULE_OVERRIDE_MitNaechsterAbolieferung = "S";
	/** Set Lieferart abw..
		@param DeliveryRule_Override Lieferart abw.	  */
	@Override
	public void setDeliveryRule_Override (java.lang.String DeliveryRule_Override)
	{

		set_Value (COLUMNNAME_DeliveryRule_Override, DeliveryRule_Override);
	}

	/** Get Lieferart abw..
		@return Lieferart abw.	  */
	@Override
	public java.lang.String getDeliveryRule_Override () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryRule_Override);
	}

	/** 
	 * DeliveryViaRule AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int DELIVERYVIARULE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String DELIVERYVIARULE_Pickup = "P";
	/** Delivery = D */
	public static final String DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_Shipper = "S";
	/** Set Lieferung.
		@param DeliveryViaRule 
		Wie der Auftrag geliefert wird
	  */
	@Override
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule)
	{

		set_ValueNoCheck (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	/** Get Lieferung.
		@return Wie der Auftrag geliefert wird
	  */
	@Override
	public java.lang.String getDeliveryViaRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryViaRule);
	}

	/** 
	 * DeliveryViaRule_Override AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int DELIVERYVIARULE_OVERRIDE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String DELIVERYVIARULE_OVERRIDE_Pickup = "P";
	/** Delivery = D */
	public static final String DELIVERYVIARULE_OVERRIDE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_OVERRIDE_Shipper = "S";
	/** Set Lieferung durch abw..
		@param DeliveryViaRule_Override Lieferung durch abw.	  */
	@Override
	public void setDeliveryViaRule_Override (java.lang.String DeliveryViaRule_Override)
	{

		set_Value (COLUMNNAME_DeliveryViaRule_Override, DeliveryViaRule_Override);
	}

	/** Get Lieferung durch abw..
		@return Lieferung durch abw.	  */
	@Override
	public java.lang.String getDeliveryViaRule_Override () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryViaRule_Override);
	}

	/** 
	 * DocSubType AD_Reference_ID=148
	 * Reference name: C_DocType SubType
	 */
	public static final int DOCSUBTYPE_AD_Reference_ID=148;
	/** OnCreditOrder = WI */
	public static final String DOCSUBTYPE_OnCreditOrder = "WI";
	/** POSOrder = WR */
	public static final String DOCSUBTYPE_POSOrder = "WR";
	/** WarehouseOrder = WP */
	public static final String DOCSUBTYPE_WarehouseOrder = "WP";
	/** StandardOrder = SO */
	public static final String DOCSUBTYPE_StandardOrder = "SO";
	/** Proposal = ON */
	public static final String DOCSUBTYPE_Proposal = "ON";
	/** Quotation = OB */
	public static final String DOCSUBTYPE_Quotation = "OB";
	/** ReturnMaterial = RM */
	public static final String DOCSUBTYPE_ReturnMaterial = "RM";
	/** PrepayOrder = PR */
	public static final String DOCSUBTYPE_PrepayOrder = "PR";
	/** Auftrag (Vorkasse) zur Disposition = PM */
	public static final String DOCSUBTYPE_AuftragVorkasseZurDisposition = "PM";
	/** Provisionskorrektur = CC */
	public static final String DOCSUBTYPE_Provisionskorrektur = "CC";
	/** Provisionsberechnung = CA */
	public static final String DOCSUBTYPE_Provisionsberechnung = "CA";
	/** FlatFee = FF */
	public static final String DOCSUBTYPE_FlatFee = "FF";
	/** HoldingFee = HF */
	public static final String DOCSUBTYPE_HoldingFee = "HF";
	/** Subscription = SU */
	public static final String DOCSUBTYPE_Subscription = "SU";
	/** NB - Mengendifferenz = AQ */
	public static final String DOCSUBTYPE_NB_Mengendifferenz = "AQ";
	/** NB - Preisdifferenz = AP */
	public static final String DOCSUBTYPE_NB_Preisdifferenz = "AP";
	/** GS - Lieferdifferenz = CQ */
	public static final String DOCSUBTYPE_GS_Lieferdifferenz = "CQ";
	/** GS - Preisdifferenz = CR */
	public static final String DOCSUBTYPE_GS_Preisdifferenz = "CR";
	/** QualityInspection = QI */
	public static final String DOCSUBTYPE_QualityInspection = "QI";
	/** Leergutanlieferung = ER */
	public static final String DOCSUBTYPE_Leergutanlieferung = "ER";
	/** Produktanlieferung = MR */
	public static final String DOCSUBTYPE_Produktanlieferung = "MR";
	/** Produktauslieferung = MS */
	public static final String DOCSUBTYPE_Produktauslieferung = "MS";
	/** Leergutausgabe = ES */
	public static final String DOCSUBTYPE_Leergutausgabe = "ES";
	/** GS - Retoure = CS */
	public static final String DOCSUBTYPE_GS_Retoure = "CS";
	/** VendorInvoice = VI */
	public static final String DOCSUBTYPE_VendorInvoice = "VI";
	/** DownPayment = DP */
	public static final String DOCSUBTYPE_DownPayment = "DP";
	/** Saldokorektur = EC */
	public static final String DOCSUBTYPE_Saldokorektur = "EC";
	/** Material Disposal = MD */
	public static final String DOCSUBTYPE_MaterialDisposal = "MD";
	/** Set Doc Sub Type.
		@param DocSubType 
		Document Sub Type
	  */
	@Override
	public void setDocSubType (java.lang.String DocSubType)
	{

		set_ValueNoCheck (COLUMNNAME_DocSubType, DocSubType);
	}

	/** Get Doc Sub Type.
		@return Document Sub Type
	  */
	@Override
	public java.lang.String getDocSubType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocSubType);
	}

	/** Set Kopf-Aggregationsmerkmal.
		@param HeaderAggregationKey Kopf-Aggregationsmerkmal	  */
	@Override
	public void setHeaderAggregationKey (java.lang.String HeaderAggregationKey)
	{
		set_Value (COLUMNNAME_HeaderAggregationKey, HeaderAggregationKey);
	}

	/** Get Kopf-Aggregationsmerkmal.
		@return Kopf-Aggregationsmerkmal	  */
	@Override
	public java.lang.String getHeaderAggregationKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HeaderAggregationKey);
	}

	/** Set abw. Anschrift.
		@param IsBPartnerAddress_Override abw. Anschrift	  */
	@Override
	public void setIsBPartnerAddress_Override (boolean IsBPartnerAddress_Override)
	{
		set_Value (COLUMNNAME_IsBPartnerAddress_Override, Boolean.valueOf(IsBPartnerAddress_Override));
	}

	/** Get abw. Anschrift.
		@return abw. Anschrift	  */
	@Override
	public boolean isBPartnerAddress_Override () 
	{
		Object oo = get_Value(COLUMNNAME_IsBPartnerAddress_Override);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Delivery stop.
		@param IsDeliveryStop Delivery stop	  */
	@Override
	public void setIsDeliveryStop (boolean IsDeliveryStop)
	{
		set_Value (COLUMNNAME_IsDeliveryStop, Boolean.valueOf(IsDeliveryStop));
	}

	/** Get Delivery stop.
		@return Delivery stop	  */
	@Override
	public boolean isDeliveryStop () 
	{
		Object oo = get_Value(COLUMNNAME_IsDeliveryStop);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Displayed.
		@param IsDisplayed 
		Determines, if this field is displayed
	  */
	@Override
	public void setIsDisplayed (boolean IsDisplayed)
	{
		set_Value (COLUMNNAME_IsDisplayed, Boolean.valueOf(IsDisplayed));
	}

	/** Get Displayed.
		@return Determines, if this field is displayed
	  */
	@Override
	public boolean isDisplayed () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisplayed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Streckengeschäft.
		@param IsDropShip 
		Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	  */
	@Override
	public void setIsDropShip (boolean IsDropShip)
	{
		set_Value (COLUMNNAME_IsDropShip, Boolean.valueOf(IsDropShip));
	}

	/** Get Streckengeschäft.
		@return Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	  */
	@Override
	public boolean isDropShip () 
	{
		Object oo = get_Value(COLUMNNAME_IsDropShip);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set zu Akt..
		@param IsToRecompute 
		Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.
	  */
	@Override
	public void setIsToRecompute (boolean IsToRecompute)
	{
		throw new IllegalArgumentException ("IsToRecompute is virtual column");	}

	/** Get zu Akt..
		@return Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.
	  */
	@Override
	public boolean isToRecompute () 
	{
		Object oo = get_Value(COLUMNNAME_IsToRecompute);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zeilennetto.
		@param LineNetAmt 
		Noch offener der Nettowert Zeile (offene Menge * Einzelpreis) ohne Fracht und Geb�hren
	  */
	@Override
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt)
	{
		set_ValueNoCheck (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Zeilennetto.
		@return Noch offener der Nettowert Zeile (offene Menge * Einzelpreis) ohne Fracht und Geb�hren
	  */
	@Override
	public java.math.BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	/** Set Merkmale.
		@param M_AttributeSetInstance_ID 
		Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Merkmale.
		@return Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_IolCandHandler getM_IolCandHandler() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class);
	}

	@Override
	public void setM_IolCandHandler(de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler)
	{
		set_ValueFromPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class, M_IolCandHandler);
	}

	/** Set M_IolCandHandler.
		@param M_IolCandHandler_ID M_IolCandHandler	  */
	@Override
	public void setM_IolCandHandler_ID (int M_IolCandHandler_ID)
	{
		if (M_IolCandHandler_ID < 1) 
			set_Value (COLUMNNAME_M_IolCandHandler_ID, null);
		else 
			set_Value (COLUMNNAME_M_IolCandHandler_ID, Integer.valueOf(M_IolCandHandler_ID));
	}

	/** Get M_IolCandHandler.
		@return M_IolCandHandler	  */
	@Override
	public int getM_IolCandHandler_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_IolCandHandler_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_Shipment_Constraint getM_Shipment_Constraint() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipment_Constraint_ID, de.metas.inoutcandidate.model.I_M_Shipment_Constraint.class);
	}

	@Override
	public void setM_Shipment_Constraint(de.metas.inoutcandidate.model.I_M_Shipment_Constraint M_Shipment_Constraint)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipment_Constraint_ID, de.metas.inoutcandidate.model.I_M_Shipment_Constraint.class, M_Shipment_Constraint);
	}

	/** Set Shipment constraint.
		@param M_Shipment_Constraint_ID Shipment constraint	  */
	@Override
	public void setM_Shipment_Constraint_ID (int M_Shipment_Constraint_ID)
	{
		if (M_Shipment_Constraint_ID < 1) 
			set_Value (COLUMNNAME_M_Shipment_Constraint_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipment_Constraint_ID, Integer.valueOf(M_Shipment_Constraint_ID));
	}

	/** Get Shipment constraint.
		@return Shipment constraint	  */
	@Override
	public int getM_Shipment_Constraint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipment_Constraint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lieferdisposition.
		@param M_ShipmentSchedule_ID Lieferdisposition	  */
	@Override
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, Integer.valueOf(M_ShipmentSchedule_ID));
	}

	/** Get Lieferdisposition.
		@return Lieferdisposition	  */
	@Override
	public int getM_ShipmentSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipmentSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse_Dest() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_Dest_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse_Dest(org.compiere.model.I_M_Warehouse M_Warehouse_Dest)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_Dest_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse_Dest);
	}

	/** Set Ziel-Lager.
		@param M_Warehouse_Dest_ID Ziel-Lager	  */
	@Override
	public void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID)
	{
		throw new IllegalArgumentException ("M_Warehouse_Dest_ID is virtual column");	}

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

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse);
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Lager oder Ort für Dienstleistung
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Lager oder Ort für Dienstleistung
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse_Override() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_Override_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse_Override(org.compiere.model.I_M_Warehouse M_Warehouse_Override)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_Override_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse_Override);
	}

	/** Set Lager abw..
		@param M_Warehouse_Override_ID 
		Lager oder Ort für Dienstleistung
	  */
	@Override
	public void setM_Warehouse_Override_ID (int M_Warehouse_Override_ID)
	{
		if (M_Warehouse_Override_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_Override_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_Override_ID, Integer.valueOf(M_Warehouse_Override_ID));
	}

	/** Get Lager abw..
		@return Lager oder Ort für Dienstleistung
	  */
	@Override
	public int getM_Warehouse_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Referenz.
		@param POReference 
		Referenz-Nummer des Kunden
	  */
	@Override
	public void setPOReference (java.lang.String POReference)
	{
		throw new IllegalArgumentException ("POReference is virtual column");	}

	/** Get Referenz.
		@return Referenz-Nummer des Kunden
	  */
	@Override
	public java.lang.String getPOReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}

	/** Set Bereitstellungsdatum.
		@param PreparationDate Bereitstellungsdatum	  */
	@Override
	public void setPreparationDate (java.sql.Timestamp PreparationDate)
	{
		set_Value (COLUMNNAME_PreparationDate, PreparationDate);
	}

	/** Get Bereitstellungsdatum.
		@return Bereitstellungsdatum	  */
	@Override
	public java.sql.Timestamp getPreparationDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PreparationDate);
	}

	/** Set Bereitstellungsdatum eff..
		@param PreparationDate_Effective Bereitstellungsdatum eff.	  */
	@Override
	public void setPreparationDate_Effective (java.sql.Timestamp PreparationDate_Effective)
	{
		throw new IllegalArgumentException ("PreparationDate_Effective is virtual column");	}

	/** Get Bereitstellungsdatum eff..
		@return Bereitstellungsdatum eff.	  */
	@Override
	public java.sql.Timestamp getPreparationDate_Effective () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PreparationDate_Effective);
	}

	/** Set Bereitstellungsdatum abw..
		@param PreparationDate_Override Bereitstellungsdatum abw.	  */
	@Override
	public void setPreparationDate_Override (java.sql.Timestamp PreparationDate_Override)
	{
		set_Value (COLUMNNAME_PreparationDate_Override, PreparationDate_Override);
	}

	/** Get Bereitstellungsdatum abw..
		@return Bereitstellungsdatum abw.	  */
	@Override
	public java.sql.Timestamp getPreparationDate_Override () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PreparationDate_Override);
	}

	/** 
	 * PriorityRule AD_Reference_ID=154
	 * Reference name: _PriorityRule
	 */
	public static final int PRIORITYRULE_AD_Reference_ID=154;
	/** High = 3 */
	public static final String PRIORITYRULE_High = "3";
	/** Medium = 5 */
	public static final String PRIORITYRULE_Medium = "5";
	/** Low = 7 */
	public static final String PRIORITYRULE_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITYRULE_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITYRULE_Minor = "9";
	/** Set Priorität.
		@param PriorityRule 
		Priority of a document
	  */
	@Override
	public void setPriorityRule (java.lang.String PriorityRule)
	{

		set_ValueNoCheck (COLUMNNAME_PriorityRule, PriorityRule);
	}

	/** Get Priorität.
		@return Priority of a document
	  */
	@Override
	public java.lang.String getPriorityRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PriorityRule);
	}

	/** 
	 * PriorityRule_Override AD_Reference_ID=154
	 * Reference name: _PriorityRule
	 */
	public static final int PRIORITYRULE_OVERRIDE_AD_Reference_ID=154;
	/** High = 3 */
	public static final String PRIORITYRULE_OVERRIDE_High = "3";
	/** Medium = 5 */
	public static final String PRIORITYRULE_OVERRIDE_Medium = "5";
	/** Low = 7 */
	public static final String PRIORITYRULE_OVERRIDE_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITYRULE_OVERRIDE_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITYRULE_OVERRIDE_Minor = "9";
	/** Set Priorität Abw..
		@param PriorityRule_Override Priorität Abw.	  */
	@Override
	public void setPriorityRule_Override (java.lang.String PriorityRule_Override)
	{

		set_Value (COLUMNNAME_PriorityRule_Override, PriorityRule_Override);
	}

	/** Get Priorität Abw..
		@return Priorität Abw.	  */
	@Override
	public java.lang.String getPriorityRule_Override () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PriorityRule_Override);
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
		throw new IllegalArgumentException ("Processing is virtual column");	}

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

	/** 
	 * ProductDescription AD_Reference_ID=162
	 * Reference name: M_Product (no summary)
	 */
	public static final int PRODUCTDESCRIPTION_AD_Reference_ID=162;
	/** Set Produktbeschreibung.
		@param ProductDescription 
		Produktbeschreibung
	  */
	@Override
	public void setProductDescription (java.lang.String ProductDescription)
	{

		set_ValueNoCheck (COLUMNNAME_ProductDescription, ProductDescription);
	}

	/** Get Produktbeschreibung.
		@return Produktbeschreibung
	  */
	@Override
	public java.lang.String getProductDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductDescription);
	}

	/** Set Gelieferte Menge.
		@param QtyDelivered 
		Gelieferte Menge
	  */
	@Override
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Gelieferte Menge.
		@return Gelieferte Menge
	  */
	@Override
	public java.math.BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bestand.
		@param QtyOnHand 
		Bestand
	  */
	@Override
	public void setQtyOnHand (java.math.BigDecimal QtyOnHand)
	{
		set_Value (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	/** Get Bestand.
		@return Bestand
	  */
	@Override
	public java.math.BigDecimal getQtyOnHand () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnHand);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bestellt/ Beauftragt.
		@param QtyOrdered 
		Bestellt/ Beauftragt
	  */
	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Bestellt/ Beauftragt.
		@return Bestellt/ Beauftragt
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set QtyOrdered_Calculated.
		@param QtyOrdered_Calculated QtyOrdered_Calculated	  */
	@Override
	public void setQtyOrdered_Calculated (java.math.BigDecimal QtyOrdered_Calculated)
	{
		set_Value (COLUMNNAME_QtyOrdered_Calculated, QtyOrdered_Calculated);
	}

	/** Get QtyOrdered_Calculated.
		@return QtyOrdered_Calculated	  */
	@Override
	public java.math.BigDecimal getQtyOrdered_Calculated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered_Calculated);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bestellt abw..
		@param QtyOrdered_Override Bestellt abw.	  */
	@Override
	public void setQtyOrdered_Override (java.math.BigDecimal QtyOrdered_Override)
	{
		set_Value (COLUMNNAME_QtyOrdered_Override, QtyOrdered_Override);
	}

	/** Get Bestellt abw..
		@return Bestellt abw.	  */
	@Override
	public java.math.BigDecimal getQtyOrdered_Override () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered_Override);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Auf Packzettel.
		@param QtyPickList Auf Packzettel	  */
	@Override
	public void setQtyPickList (java.math.BigDecimal QtyPickList)
	{
		set_Value (COLUMNNAME_QtyPickList, QtyPickList);
	}

	/** Get Auf Packzettel.
		@return Auf Packzettel	  */
	@Override
	public java.math.BigDecimal getQtyPickList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPickList);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Offen.
		@param QtyReserved 
		Offene Menge
	  */
	@Override
	public void setQtyReserved (java.math.BigDecimal QtyReserved)
	{
		set_ValueNoCheck (COLUMNNAME_QtyReserved, QtyReserved);
	}

	/** Get Offen.
		@return Offene Menge
	  */
	@Override
	public java.math.BigDecimal getQtyReserved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReserved);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Ausliefermenge.
		@param QtyToDeliver Ausliefermenge	  */
	@Override
	public void setQtyToDeliver (java.math.BigDecimal QtyToDeliver)
	{
		set_Value (COLUMNNAME_QtyToDeliver, QtyToDeliver);
	}

	/** Get Ausliefermenge.
		@return Ausliefermenge	  */
	@Override
	public java.math.BigDecimal getQtyToDeliver () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToDeliver);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Liefermenge abw..
		@param QtyToDeliver_Override 
		Menge, die abweichend von der ursprünglichen Vorgabe ausgeliefert wird
	  */
	@Override
	public void setQtyToDeliver_Override (java.math.BigDecimal QtyToDeliver_Override)
	{
		set_Value (COLUMNNAME_QtyToDeliver_Override, QtyToDeliver_Override);
	}

	/** Get Liefermenge abw..
		@return Menge, die abweichend von der ursprünglichen Vorgabe ausgeliefert wird
	  */
	@Override
	public java.math.BigDecimal getQtyToDeliver_Override () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToDeliver_Override);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Erl. Liefermenge abw..
		@param QtyToDeliver_OverrideFulfilled 
		Teilmenge von "Abw. Liefermenge", die bereits Ausgeliefert wurde
	  */
	@Override
	public void setQtyToDeliver_OverrideFulfilled (java.math.BigDecimal QtyToDeliver_OverrideFulfilled)
	{
		set_Value (COLUMNNAME_QtyToDeliver_OverrideFulfilled, QtyToDeliver_OverrideFulfilled);
	}

	/** Get Erl. Liefermenge abw..
		@return Teilmenge von "Abw. Liefermenge", die bereits Ausgeliefert wurde
	  */
	@Override
	public java.math.BigDecimal getQtyToDeliver_OverrideFulfilled () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToDeliver_OverrideFulfilled);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Positionssumme.
		@param SinglePriceTag_ID Positionssumme	  */
	@Override
	public void setSinglePriceTag_ID (java.lang.String SinglePriceTag_ID)
	{
		set_ValueNoCheck (COLUMNNAME_SinglePriceTag_ID, SinglePriceTag_ID);
	}

	/** Get Positionssumme.
		@return Positionssumme	  */
	@Override
	public java.lang.String getSinglePriceTag_ID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SinglePriceTag_ID);
	}

	/** Set Status.
		@param Status Status	  */
	@Override
	public void setStatus (java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status	  */
	@Override
	public java.lang.String getStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}
}