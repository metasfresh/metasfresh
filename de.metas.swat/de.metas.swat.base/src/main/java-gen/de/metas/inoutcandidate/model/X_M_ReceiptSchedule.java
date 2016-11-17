/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_ReceiptSchedule
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ReceiptSchedule extends org.compiere.model.PO implements I_M_ReceiptSchedule, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 14259786L;

    /** Standard Constructor */
    public X_M_ReceiptSchedule (Properties ctx, int M_ReceiptSchedule_ID, String trxName)
    {
      super (ctx, M_ReceiptSchedule_ID, trxName);
      /** if (M_ReceiptSchedule_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_DocType_ID (0);
			setDeliveryRule (null);
			setDeliveryViaRule (null);
			setIsBPartnerAddress_Override (false);
// N
			setIsPackagingMaterial (false);
// N
			setM_Product_ID (0);
			setM_ReceiptSchedule_ID (0);
			setM_Warehouse_ID (0);
			setProcessed (false);
// N
			setQtyMovedWithIssues (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_M_ReceiptSchedule (Properties ctx, ResultSet rs, String trxName)
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
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
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

	/** Set Bestellung.
		@param C_Order_ID 
		Bestellung
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Bestellung.
		@return Bestellung
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
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

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
	/** Mit nächster Abolieferung = S */
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

	/** Set Packaging Material .
		@param IsPackagingMaterial Packaging Material 	  */
	@Override
	public void setIsPackagingMaterial (boolean IsPackagingMaterial)
	{
		set_Value (COLUMNNAME_IsPackagingMaterial, Boolean.valueOf(IsPackagingMaterial));
	}

	/** Get Packaging Material .
		@return Packaging Material 	  */
	@Override
	public boolean isPackagingMaterial () 
	{
		Object oo = get_Value(COLUMNNAME_IsPackagingMaterial);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Ausprägung Merkmals-Satz.
		@param M_AttributeSetInstance_ID 
		Instanz des Merkmals-Satzes zum Produkt
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Ausprägung Merkmals-Satz.
		@return Instanz des Merkmals-Satzes zum Produkt
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

	/** Set Bewegungs-Datum.
		@param MovementDate 
		Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	  */
	@Override
	public void setMovementDate (java.sql.Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	/** Get Bewegungs-Datum.
		@return Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	  */
	@Override
	public java.sql.Timestamp getMovementDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_MovementDate);
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

	/** Set Wareneingangsdisposition.
		@param M_ReceiptSchedule_ID Wareneingangsdisposition	  */
	@Override
	public void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID)
	{
		if (M_ReceiptSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_ID, Integer.valueOf(M_ReceiptSchedule_ID));
	}

	/** Get Wareneingangsdisposition.
		@return Wareneingangsdisposition	  */
	@Override
	public int getM_ReceiptSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ReceiptSchedule_ID);
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
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
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
			set_Value (COLUMNNAME_M_Warehouse_Override_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_Override_ID, Integer.valueOf(M_Warehouse_Override_ID));
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

	/** Set Bewegte Menge.
		@param QtyMoved Bewegte Menge	  */
	@Override
	public void setQtyMoved (java.math.BigDecimal QtyMoved)
	{
		set_Value (COLUMNNAME_QtyMoved, QtyMoved);
	}

	/** Get Bewegte Menge.
		@return Bewegte Menge	  */
	@Override
	public java.math.BigDecimal getQtyMoved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMoved);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Moved (With Issues).
		@param QtyMovedWithIssues Qty Moved (With Issues)	  */
	@Override
	public void setQtyMovedWithIssues (java.math.BigDecimal QtyMovedWithIssues)
	{
		set_ValueNoCheck (COLUMNNAME_QtyMovedWithIssues, QtyMovedWithIssues);
	}

	/** Get Qty Moved (With Issues).
		@return Qty Moved (With Issues)	  */
	@Override
	public java.math.BigDecimal getQtyMovedWithIssues () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMovedWithIssues);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bestellte Menge.
		@param QtyOrdered 
		Bestellte Menge
	  */
	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Bestellte Menge.
		@return Bestellte Menge
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyOrderedOverUnder.
		@param QtyOrderedOverUnder QtyOrderedOverUnder	  */
	@Override
	public void setQtyOrderedOverUnder (java.math.BigDecimal QtyOrderedOverUnder)
	{
		set_Value (COLUMNNAME_QtyOrderedOverUnder, QtyOrderedOverUnder);
	}

	/** Get QtyOrderedOverUnder.
		@return QtyOrderedOverUnder	  */
	@Override
	public java.math.BigDecimal getQtyOrderedOverUnder () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrderedOverUnder);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Menge zu bewegen.
		@param QtyToMove Menge zu bewegen	  */
	@Override
	public void setQtyToMove (java.math.BigDecimal QtyToMove)
	{
		set_Value (COLUMNNAME_QtyToMove, QtyToMove);
	}

	/** Get Menge zu bewegen.
		@return Menge zu bewegen	  */
	@Override
	public java.math.BigDecimal getQtyToMove () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToMove);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Menge zu bewegen abw..
		@param QtyToMove_Override Menge zu bewegen abw.	  */
	@Override
	public void setQtyToMove_Override (java.math.BigDecimal QtyToMove_Override)
	{
		set_Value (COLUMNNAME_QtyToMove_Override, QtyToMove_Override);
	}

	/** Get Menge zu bewegen abw..
		@return Menge zu bewegen abw.	  */
	@Override
	public java.math.BigDecimal getQtyToMove_Override () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToMove_Override);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qualitätsabzug %.
		@param QualityDiscountPercent Qualitätsabzug %	  */
	@Override
	public void setQualityDiscountPercent (java.math.BigDecimal QualityDiscountPercent)
	{
		set_Value (COLUMNNAME_QualityDiscountPercent, QualityDiscountPercent);
	}

	/** Get Qualitätsabzug %.
		@return Qualitätsabzug %	  */
	@Override
	public java.math.BigDecimal getQualityDiscountPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QualityDiscountPercent);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qualität-Notiz.
		@param QualityNote Qualität-Notiz	  */
	@Override
	public void setQualityNote (java.lang.String QualityNote)
	{
		set_Value (COLUMNNAME_QualityNote, QualityNote);
	}

	/** Get Qualität-Notiz.
		@return Qualität-Notiz	  */
	@Override
	public java.lang.String getQualityNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_QualityNote);
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

	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	@Override
	public void setStatus (java.lang.String Status)
	{
		set_ValueNoCheck (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	@Override
	public java.lang.String getStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}
}