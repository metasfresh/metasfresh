/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Invoice extends org.compiere.model.PO implements I_C_Invoice, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1581909050L;

    /** Standard Constructor */
    public X_C_Invoice (Properties ctx, int C_Invoice_ID, String trxName)
    {
      super (ctx, C_Invoice_ID, trxName);
      /** if (C_Invoice_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Currency_ID (0); // @C_Currency_ID@
			setC_DocType_ID (0); // 0
			setC_DocTypeTarget_ID (0);
			setC_Invoice_ID (0);
			setC_PaymentTerm_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() )); // @#Date@
			setDateInvoiced (new Timestamp( System.currentTimeMillis() )); // @#Date@
			setDocAction (null); // CO
			setDocStatus (null); // DR
			setDocumentNo (null);
			setGrandTotal (BigDecimal.ZERO);
			setIsApproved (false); // @IsApproved@
			setIsDiscountPrinted (false); // N
			setIsInDispute (false); // N
			setIsPaid (false);
			setIsPayScheduleValid (false);
			setIsPrinted (false);
			setIsSalesPartnerRequired (false); // N
			setIsSelfService (false);
			setIsSOTrx (false); // @IsSOTrx@
			setIsTaxIncluded (false);
			setIsTransferred (false);
			setIsUseBPartnerAddress (false); // N
			setM_PriceList_ID (0);
			setPaymentRule (null); // P
			setPosted (false); // N
			setProcessed (false);
			setSendEMail (false);
			setTotalLines (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_Invoice (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Buchende Organisation.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	@Override
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Buchende Organisation.
		@return Performing or initiating organization
	  */
	@Override
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Leistungsempfänger.
		@param Beneficiary_BPartner_ID Leistungsempfänger	  */
	@Override
	public void setBeneficiary_BPartner_ID (int Beneficiary_BPartner_ID)
	{
		if (Beneficiary_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Beneficiary_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Beneficiary_BPartner_ID, Integer.valueOf(Beneficiary_BPartner_ID));
	}

	/** Get Leistungsempfänger.
		@return Leistungsempfänger	  */
	@Override
	public int getBeneficiary_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Beneficiary_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Leistungsempfänger Kontakt.
		@param Beneficiary_Contact_ID Leistungsempfänger Kontakt	  */
	@Override
	public void setBeneficiary_Contact_ID (int Beneficiary_Contact_ID)
	{
		if (Beneficiary_Contact_ID < 1) 
			set_Value (COLUMNNAME_Beneficiary_Contact_ID, null);
		else 
			set_Value (COLUMNNAME_Beneficiary_Contact_ID, Integer.valueOf(Beneficiary_Contact_ID));
	}

	/** Get Leistungsempfänger Kontakt.
		@return Leistungsempfänger Kontakt	  */
	@Override
	public int getBeneficiary_Contact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Beneficiary_Contact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Leistungsempfänger Addresse.
		@param Beneficiary_Location_ID Leistungsempfänger Addresse	  */
	@Override
	public void setBeneficiary_Location_ID (int Beneficiary_Location_ID)
	{
		if (Beneficiary_Location_ID < 1) 
			set_Value (COLUMNNAME_Beneficiary_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Beneficiary_Location_ID, Integer.valueOf(Beneficiary_Location_ID));
	}

	/** Get Leistungsempfänger Addresse.
		@return Leistungsempfänger Addresse	  */
	@Override
	public int getBeneficiary_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Beneficiary_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Anschrift-Text.
		@param BPartnerAddress Anschrift-Text	  */
	@Override
	public void setBPartnerAddress (java.lang.String BPartnerAddress)
	{
		set_Value (COLUMNNAME_BPartnerAddress, BPartnerAddress);
	}

	/** Get Anschrift-Text.
		@return Anschrift-Text	  */
	@Override
	public java.lang.String getBPartnerAddress () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerAddress);
	}

	/** Set Kostenstelle.
		@param C_Activity_ID 
		Kostenstelle
	  */
	@Override
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Kostenstelle.
		@return Kostenstelle
	  */
	@Override
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
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
		@return Identifies a Business Partner
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
		Identifies the (ship to) address for this Business Partner
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
		@return Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zugeordneter Vertriebspartner.
		@param C_BPartner_SalesRep_ID Zugeordneter Vertriebspartner	  */
	@Override
	public void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID)
	{
		if (C_BPartner_SalesRep_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_SalesRep_ID, Integer.valueOf(C_BPartner_SalesRep_ID));
	}

	/** Get Zugeordneter Vertriebspartner.
		@return Zugeordneter Vertriebspartner	  */
	@Override
	public int getC_BPartner_SalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Campaign getC_Campaign()
	{
		return get_ValueAsPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class);
	}

	@Override
	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class, C_Campaign);
	}

	/** Set Werbemassnahme.
		@param C_Campaign_ID 
		Marketing Campaign
	  */
	@Override
	public void setC_Campaign_ID (int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/** Get Werbemassnahme.
		@return Marketing Campaign
	  */
	@Override
	public int getC_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_CashLine getC_CashLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_CashLine_ID, org.compiere.model.I_C_CashLine.class);
	}

	@Override
	public void setC_CashLine(org.compiere.model.I_C_CashLine C_CashLine)
	{
		set_ValueFromPO(COLUMNNAME_C_CashLine_ID, org.compiere.model.I_C_CashLine.class, C_CashLine);
	}

	/** Set Cash Journal Line.
		@param C_CashLine_ID 
		Cash Journal Line
	  */
	@Override
	public void setC_CashLine_ID (int C_CashLine_ID)
	{
		if (C_CashLine_ID < 1) 
			set_Value (COLUMNNAME_C_CashLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_CashLine_ID, Integer.valueOf(C_CashLine_ID));
	}

	/** Get Cash Journal Line.
		@return Cash Journal Line
	  */
	@Override
	public int getC_CashLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CashLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kosten.
		@param C_Charge_ID 
		Additional document charges
	  */
	@Override
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Kosten.
		@return Additional document charges
	  */
	@Override
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kursart.
		@param C_ConversionType_ID 
		Currency Conversion Rate Type
	  */
	@Override
	public void setC_ConversionType_ID (int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
	}

	/** Get Kursart.
		@return Currency Conversion Rate Type
	  */
	@Override
	public int getC_ConversionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConversionType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Währung.
		@param C_Currency_ID 
		The Currency for this record
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return The Currency for this record
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Belegart.
		@param C_DocType_ID 
		Document type or rules
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
		@return Document type or rules
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zielbelegart.
		@param C_DocTypeTarget_ID 
		Target document type for conversing documents
	  */
	@Override
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID)
	{
		if (C_DocTypeTarget_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeTarget_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeTarget_ID, Integer.valueOf(C_DocTypeTarget_ID));
	}

	/** Get Zielbelegart.
		@return Target document type for conversing documents
	  */
	@Override
	public int getC_DocTypeTarget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeTarget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DunningLevel getC_DunningLevel()
	{
		return get_ValueAsPO(COLUMNNAME_C_DunningLevel_ID, org.compiere.model.I_C_DunningLevel.class);
	}

	@Override
	public void setC_DunningLevel(org.compiere.model.I_C_DunningLevel C_DunningLevel)
	{
		set_ValueFromPO(COLUMNNAME_C_DunningLevel_ID, org.compiere.model.I_C_DunningLevel.class, C_DunningLevel);
	}

	/** Set Mahnstufe.
		@param C_DunningLevel_ID Mahnstufe	  */
	@Override
	public void setC_DunningLevel_ID (int C_DunningLevel_ID)
	{
		if (C_DunningLevel_ID < 1) 
			set_Value (COLUMNNAME_C_DunningLevel_ID, null);
		else 
			set_Value (COLUMNNAME_C_DunningLevel_ID, Integer.valueOf(C_DunningLevel_ID));
	}

	/** Get Mahnstufe.
		@return Mahnstufe	  */
	@Override
	public int getC_DunningLevel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DunningLevel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Gebühr.
		@param ChargeAmt Gebühr	  */
	@Override
	public void setChargeAmt (java.math.BigDecimal ChargeAmt)
	{
		set_Value (COLUMNNAME_ChargeAmt, ChargeAmt);
	}

	/** Get Gebühr.
		@return Gebühr	  */
	@Override
	public java.math.BigDecimal getChargeAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ChargeAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Rechnung.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Rechnung.
		@return Invoice Identifier
	  */
	@Override
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Copy From.
		@param CopyFrom 
		Copy From Record
	  */
	@Override
	public void setCopyFrom (java.lang.String CopyFrom)
	{
		set_Value (COLUMNNAME_CopyFrom, CopyFrom);
	}

	/** Get Copy From.
		@return Copy From Record
	  */
	@Override
	public java.lang.String getCopyFrom () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CopyFrom);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
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

	/** Set Zahlung.
		@param C_Payment_ID 
		Payment identifier
	  */
	@Override
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Zahlung.
		@return Payment identifier
	  */
	@Override
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zahlungsbedingung.
		@param C_PaymentTerm_ID 
		Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Zahlungsbedingung.
		@return Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Projekt.
		@param C_Project_ID 
		Financial Project
	  */
	@Override
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Projekt.
		@return Financial Project
	  */
	@Override
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Nachbelastung.
		@param CreateAdjustmentCharge Nachbelastung	  */
	@Override
	public void setCreateAdjustmentCharge (java.lang.String CreateAdjustmentCharge)
	{
		set_Value (COLUMNNAME_CreateAdjustmentCharge, CreateAdjustmentCharge);
	}

	/** Get Nachbelastung.
		@return Nachbelastung	  */
	@Override
	public java.lang.String getCreateAdjustmentCharge () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreateAdjustmentCharge);
	}

	/** Set Erzeuge Gutschrift.
		@param CreateCreditMemo Erzeuge Gutschrift	  */
	@Override
	public void setCreateCreditMemo (java.lang.String CreateCreditMemo)
	{
		set_Value (COLUMNNAME_CreateCreditMemo, CreateCreditMemo);
	}

	/** Get Erzeuge Gutschrift.
		@return Erzeuge Gutschrift	  */
	@Override
	public java.lang.String getCreateCreditMemo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreateCreditMemo);
	}

	/** Set CreateDta.
		@param CreateDta CreateDta	  */
	@Override
	public void setCreateDta (java.lang.String CreateDta)
	{
		set_Value (COLUMNNAME_CreateDta, CreateDta);
	}

	/** Get CreateDta.
		@return CreateDta	  */
	@Override
	public java.lang.String getCreateDta () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreateDta);
	}

	/** Set Position(en) kopieren von.
		@param CreateFrom 
		Process which will generate a new document lines based on an existing document
	  */
	@Override
	public void setCreateFrom (java.lang.String CreateFrom)
	{
		set_Value (COLUMNNAME_CreateFrom, CreateFrom);
	}

	/** Get Position(en) kopieren von.
		@return Process which will generate a new document lines based on an existing document
	  */
	@Override
	public java.lang.String getCreateFrom () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreateFrom);
	}

	/** 
	 * CreditMemoReason AD_Reference_ID=540014
	 * Reference name: C_CreditMemo_Reason
	 */
	public static final int CREDITMEMOREASON_AD_Reference_ID=540014;
	/** Falschlieferung = CMF */
	public static final String CREDITMEMOREASON_Falschlieferung = "CMF";
	/** Doppellieferung = CMD */
	public static final String CREDITMEMOREASON_Doppellieferung = "CMD";
	/** Set Gutschrift Grund.
		@param CreditMemoReason Gutschrift Grund	  */
	@Override
	public void setCreditMemoReason (java.lang.String CreditMemoReason)
	{

		set_Value (COLUMNNAME_CreditMemoReason, CreditMemoReason);
	}

	/** Get Gutschrift Grund.
		@return Gutschrift Grund	  */
	@Override
	public java.lang.String getCreditMemoReason () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditMemoReason);
	}

	/** Set Buchungsdatum.
		@param DateAcct 
		Accounting Date
	  */
	@Override
	public void setDateAcct (java.sql.Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Buchungsdatum.
		@return Accounting Date
	  */
	@Override
	public java.sql.Timestamp getDateAcct () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Rechnungsdatum.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	@Override
	public void setDateInvoiced (java.sql.Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Rechnungsdatum.
		@return Date printed on Invoice
	  */
	@Override
	public java.sql.Timestamp getDateInvoiced () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set Auftragsdatum.
		@param DateOrdered 
		Date of Order
	  */
	@Override
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_DateOrdered, DateOrdered);
	}

	/** Get Auftragsdatum.
		@return Date of Order
	  */
	@Override
	public java.sql.Timestamp getDateOrdered () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateOrdered);
	}

	/** Set Date printed.
		@param DatePrinted 
		Date the document was printed.
	  */
	@Override
	public void setDatePrinted (java.sql.Timestamp DatePrinted)
	{
		set_Value (COLUMNNAME_DatePrinted, DatePrinted);
	}

	/** Get Date printed.
		@return Date the document was printed.
	  */
	@Override
	public java.sql.Timestamp getDatePrinted () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DatePrinted);
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

	/** Set DescriptionBottom.
		@param DescriptionBottom DescriptionBottom	  */
	@Override
	public void setDescriptionBottom (java.lang.String DescriptionBottom)
	{
		set_Value (COLUMNNAME_DescriptionBottom, DescriptionBottom);
	}

	/** Get DescriptionBottom.
		@return DescriptionBottom	  */
	@Override
	public java.lang.String getDescriptionBottom () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DescriptionBottom);
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
	/** Set Belegverarbeitung.
		@param DocAction 
		The targeted status of the document
	  */
	@Override
	public void setDocAction (java.lang.String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Belegverarbeitung.
		@return The targeted status of the document
	  */
	@Override
	public java.lang.String getDocAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocAction);
	}

	/** Set Dokument Basis Typ.
		@param DocBaseType Dokument Basis Typ	  */
	@Override
	public void setDocBaseType (java.lang.String DocBaseType)
	{
		throw new IllegalArgumentException ("DocBaseType is virtual column");	}

	/** Get Dokument Basis Typ.
		@return Dokument Basis Typ	  */
	@Override
	public java.lang.String getDocBaseType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocBaseType);
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

	/** Set Dunning Grace Date.
		@param DunningGrace Dunning Grace Date	  */
	@Override
	public void setDunningGrace (java.sql.Timestamp DunningGrace)
	{
		set_Value (COLUMNNAME_DunningGrace, DunningGrace);
	}

	/** Get Dunning Grace Date.
		@return Dunning Grace Date	  */
	@Override
	public java.sql.Timestamp getDunningGrace () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DunningGrace);
	}

	/** Set Generate To.
		@param GenerateTo 
		Generate To
	  */
	@Override
	public void setGenerateTo (java.lang.String GenerateTo)
	{
		set_Value (COLUMNNAME_GenerateTo, GenerateTo);
	}

	/** Get Generate To.
		@return Generate To
	  */
	@Override
	public java.lang.String getGenerateTo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GenerateTo);
	}

	/** Set Summe Gesamt.
		@param GrandTotal 
		Total amount of document
	  */
	@Override
	public void setGrandTotal (java.math.BigDecimal GrandTotal)
	{
		set_ValueNoCheck (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Summe Gesamt.
		@return Total amount of document
	  */
	@Override
	public java.math.BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** 
	 * Incoterm AD_Reference_ID=501599
	 * Reference name: Incoterms
	 */
	public static final int INCOTERM_AD_Reference_ID=501599;
	/** EXW_AbWerk = EXW */
	public static final String INCOTERM_EXW_AbWerk = "EXW";
	/** FCA_FreiSpediteur = FCA */
	public static final String INCOTERM_FCA_FreiSpediteur = "FCA";
	/** FAS_FreiLaengsseitsSchiff = FAS */
	public static final String INCOTERM_FAS_FreiLaengsseitsSchiff = "FAS";
	/** FOB_FreiAnBord = FOB */
	public static final String INCOTERM_FOB_FreiAnBord = "FOB";
	/** CFR_KostenUndFracht = CFR */
	public static final String INCOTERM_CFR_KostenUndFracht = "CFR";
	/** CIF_KostenVersicherungUndFracht = CIF */
	public static final String INCOTERM_CIF_KostenVersicherungUndFracht = "CIF";
	/** CPT_FrachtPortoBezahltBis = CPT */
	public static final String INCOTERM_CPT_FrachtPortoBezahltBis = "CPT";
	/** CIP_FrachtPortoUndVersicherungBezahltBis = CIP */
	public static final String INCOTERM_CIP_FrachtPortoUndVersicherungBezahltBis = "CIP";
	/** DAF_FreiGrenze = DAF */
	public static final String INCOTERM_DAF_FreiGrenze = "DAF";
	/** DES_FreiAbSchiff = DES */
	public static final String INCOTERM_DES_FreiAbSchiff = "DES";
	/** DEQ_FreiAbKai = DEQ */
	public static final String INCOTERM_DEQ_FreiAbKai = "DEQ";
	/** DDU_FreiUnverzollt = DDU */
	public static final String INCOTERM_DDU_FreiUnverzollt = "DDU";
	/** DDP_Verzollt = DDP */
	public static final String INCOTERM_DDP_Verzollt = "DDP";
	/** Set Incoterm.
		@param Incoterm 
		Internationale Handelsklauseln (engl. International Commercial Terms)
	  */
	@Override
	public void setIncoterm (java.lang.String Incoterm)
	{

		set_Value (COLUMNNAME_Incoterm, Incoterm);
	}

	/** Get Incoterm.
		@return Internationale Handelsklauseln (engl. International Commercial Terms)
	  */
	@Override
	public java.lang.String getIncoterm () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Incoterm);
	}

	/** Set IncotermLocation.
		@param IncotermLocation 
		Anzugebender Ort für Handelsklausel
	  */
	@Override
	public void setIncotermLocation (java.lang.String IncotermLocation)
	{
		set_Value (COLUMNNAME_IncotermLocation, IncotermLocation);
	}

	/** Get IncotermLocation.
		@return Anzugebender Ort für Handelsklausel
	  */
	@Override
	public java.lang.String getIncotermLocation () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IncotermLocation);
	}

	/** 
	 * InvoiceCollectionType AD_Reference_ID=394
	 * Reference name: C_Invoice InvoiceCollectionType
	 */
	public static final int INVOICECOLLECTIONTYPE_AD_Reference_ID=394;
	/** Dunning = D */
	public static final String INVOICECOLLECTIONTYPE_Dunning = "D";
	/** CollectionAgency = C */
	public static final String INVOICECOLLECTIONTYPE_CollectionAgency = "C";
	/** LegalProcedure = L */
	public static final String INVOICECOLLECTIONTYPE_LegalProcedure = "L";
	/** Uncollectable = U */
	public static final String INVOICECOLLECTIONTYPE_Uncollectable = "U";
	/** Set Inkasso-Status.
		@param InvoiceCollectionType 
		Invoice Collection Status
	  */
	@Override
	public void setInvoiceCollectionType (java.lang.String InvoiceCollectionType)
	{

		set_Value (COLUMNNAME_InvoiceCollectionType, InvoiceCollectionType);
	}

	/** Get Inkasso-Status.
		@return Invoice Collection Status
	  */
	@Override
	public java.lang.String getInvoiceCollectionType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceCollectionType);
	}

	/** Set Invoice_includedTab.
		@param Invoice_includedTab Invoice_includedTab	  */
	@Override
	public void setInvoice_includedTab (java.lang.String Invoice_includedTab)
	{
		set_Value (COLUMNNAME_Invoice_includedTab, Invoice_includedTab);
	}

	/** Get Invoice_includedTab.
		@return Invoice_includedTab	  */
	@Override
	public java.lang.String getInvoice_includedTab () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Invoice_includedTab);
	}

	/** Set Freigegeben.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	@Override
	public void setIsApproved (boolean IsApproved)
	{
		set_ValueNoCheck (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Freigegeben.
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

	/** Set Rabatte drucken.
		@param IsDiscountPrinted 
		Print Discount on Invoice and Order
	  */
	@Override
	public void setIsDiscountPrinted (boolean IsDiscountPrinted)
	{
		set_Value (COLUMNNAME_IsDiscountPrinted, Boolean.valueOf(IsDiscountPrinted));
	}

	/** Get Rabatte drucken.
		@return Print Discount on Invoice and Order
	  */
	@Override
	public boolean isDiscountPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsDiscountPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Strittig.
		@param IsInDispute Strittig	  */
	@Override
	public void setIsInDispute (boolean IsInDispute)
	{
		set_Value (COLUMNNAME_IsInDispute, Boolean.valueOf(IsInDispute));
	}

	/** Get Strittig.
		@return Strittig	  */
	@Override
	public boolean isInDispute () 
	{
		Object oo = get_Value(COLUMNNAME_IsInDispute);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Gezahlt.
		@param IsPaid 
		The document is paid
	  */
	@Override
	public void setIsPaid (boolean IsPaid)
	{
		set_Value (COLUMNNAME_IsPaid, Boolean.valueOf(IsPaid));
	}

	/** Get Gezahlt.
		@return The document is paid
	  */
	@Override
	public boolean isPaid () 
	{
		Object oo = get_Value(COLUMNNAME_IsPaid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Pay Schedule valid.
		@param IsPayScheduleValid 
		Is the Payment Schedule is valid
	  */
	@Override
	public void setIsPayScheduleValid (boolean IsPayScheduleValid)
	{
		set_ValueNoCheck (COLUMNNAME_IsPayScheduleValid, Boolean.valueOf(IsPayScheduleValid));
	}

	/** Get Pay Schedule valid.
		@return Is the Payment Schedule is valid
	  */
	@Override
	public boolean isPayScheduleValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsPayScheduleValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set andrucken.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	@Override
	public void setIsPrinted (boolean IsPrinted)
	{
		set_ValueNoCheck (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get andrucken.
		@return Indicates if this document / line is printed
	  */
	@Override
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Auftrag nur mit Vertriebspartner.
		@param IsSalesPartnerRequired 
		Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.
	  */
	@Override
	public void setIsSalesPartnerRequired (boolean IsSalesPartnerRequired)
	{
		set_Value (COLUMNNAME_IsSalesPartnerRequired, Boolean.valueOf(IsSalesPartnerRequired));
	}

	/** Get Auftrag nur mit Vertriebspartner.
		@return Legt für den Rechnungspartner fest, ob bei einer Beauftragung immer ein Vertriebspartner angegeben werden muss.
	  */
	@Override
	public boolean isSalesPartnerRequired () 
	{
		Object oo = get_Value(COLUMNNAME_IsSalesPartnerRequired);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Selbstbedienung.
		@param IsSelfService 
		This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	@Override
	public void setIsSelfService (boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Selbstbedienung.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	@Override
	public boolean isSelfService () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelfService);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verkaufs-Transaktion.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	@Override
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_ValueNoCheck (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Verkaufs-Transaktion.
		@return This is a Sales Transaction
	  */
	@Override
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Preis inklusive Steuern.
		@param IsTaxIncluded 
		Tax is included in the price 
	  */
	@Override
	public void setIsTaxIncluded (boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, Boolean.valueOf(IsTaxIncluded));
	}

	/** Get Preis inklusive Steuern.
		@return Tax is included in the price 
	  */
	@Override
	public boolean isTaxIncluded () 
	{
		Object oo = get_Value(COLUMNNAME_IsTaxIncluded);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Transferred.
		@param IsTransferred 
		Transferred to General Ledger (i.e. accounted)
	  */
	@Override
	public void setIsTransferred (boolean IsTransferred)
	{
		set_ValueNoCheck (COLUMNNAME_IsTransferred, Boolean.valueOf(IsTransferred));
	}

	/** Get Transferred.
		@return Transferred to General Ledger (i.e. accounted)
	  */
	@Override
	public boolean isTransferred () 
	{
		Object oo = get_Value(COLUMNNAME_IsTransferred);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Benutze abw. Adresse.
		@param IsUseBPartnerAddress Benutze abw. Adresse	  */
	@Override
	public void setIsUseBPartnerAddress (boolean IsUseBPartnerAddress)
	{
		set_Value (COLUMNNAME_IsUseBPartnerAddress, Boolean.valueOf(IsUseBPartnerAddress));
	}

	/** Get Benutze abw. Adresse.
		@return Benutze abw. Adresse	  */
	@Override
	public boolean isUseBPartnerAddress () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseBPartnerAddress);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Preisliste.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	@Override
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Preisliste.
		@return Unique identifier of a Price List
	  */
	@Override
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_RMA getM_RMA()
	{
		return get_ValueAsPO(COLUMNNAME_M_RMA_ID, org.compiere.model.I_M_RMA.class);
	}

	@Override
	public void setM_RMA(org.compiere.model.I_M_RMA M_RMA)
	{
		set_ValueFromPO(COLUMNNAME_M_RMA_ID, org.compiere.model.I_M_RMA.class, M_RMA);
	}

	/** Set Warenrücksendung - Freigabe (RMA).
		@param M_RMA_ID 
		Return Material Authorization
	  */
	@Override
	public void setM_RMA_ID (int M_RMA_ID)
	{
		if (M_RMA_ID < 1) 
			set_Value (COLUMNNAME_M_RMA_ID, null);
		else 
			set_Value (COLUMNNAME_M_RMA_ID, Integer.valueOf(M_RMA_ID));
	}

	/** Get Warenrücksendung - Freigabe (RMA).
		@return Return Material Authorization
	  */
	@Override
	public int getM_RMA_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_RMA_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * PaymentRule AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULE_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULE_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULE_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULE_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULE_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULE_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULE_Mixed = "M";
	/** PayPal = L */
	public static final String PAYMENTRULE_PayPal = "L";
	/** Set Zahlungsweise.
		@param PaymentRule 
		How you pay the invoice
	  */
	@Override
	public void setPaymentRule (java.lang.String PaymentRule)
	{

		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	/** Get Zahlungsweise.
		@return How you pay the invoice
	  */
	@Override
	public java.lang.String getPaymentRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentRule);
	}

	/** Set Referenz.
		@param POReference 
		Referenz-Nummer des Kunden
	  */
	@Override
	public void setPOReference (java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	/** Get Referenz.
		@return Referenz-Nummer des Kunden
	  */
	@Override
	public java.lang.String getPOReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}

	/** Set Buchungsstatus.
		@param Posted 
		Buchungsstatus
	  */
	@Override
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Buchungsstatus.
		@return Buchungsstatus
	  */
	@Override
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
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

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
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

	@Override
	public org.compiere.model.I_C_Invoice getRef_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_Ref_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setRef_Invoice(org.compiere.model.I_C_Invoice Ref_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_Ref_Invoice_ID, org.compiere.model.I_C_Invoice.class, Ref_Invoice);
	}

	/** Set Referenced Invoice.
		@param Ref_Invoice_ID Referenced Invoice	  */
	@Override
	public void setRef_Invoice_ID (int Ref_Invoice_ID)
	{
		if (Ref_Invoice_ID < 1) 
			set_Value (COLUMNNAME_Ref_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_Invoice_ID, Integer.valueOf(Ref_Invoice_ID));
	}

	/** Get Referenced Invoice.
		@return Referenced Invoice	  */
	@Override
	public int getRef_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Invoice getReversal()
	{
		return get_ValueAsPO(COLUMNNAME_Reversal_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setReversal(org.compiere.model.I_C_Invoice Reversal)
	{
		set_ValueFromPO(COLUMNNAME_Reversal_ID, org.compiere.model.I_C_Invoice.class, Reversal);
	}

	/** Set Storno-Gegenbeleg.
		@param Reversal_ID 
		ID of document reversal
	  */
	@Override
	public void setReversal_ID (int Reversal_ID)
	{
		if (Reversal_ID < 1) 
			set_Value (COLUMNNAME_Reversal_ID, null);
		else 
			set_Value (COLUMNNAME_Reversal_ID, Integer.valueOf(Reversal_ID));
	}

	/** Get Storno-Gegenbeleg.
		@return ID of document reversal
	  */
	@Override
	public int getReversal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Reversal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Vertriebspartnercode.
		@param SalesPartnerCode Vertriebspartnercode	  */
	@Override
	public void setSalesPartnerCode (java.lang.String SalesPartnerCode)
	{
		set_Value (COLUMNNAME_SalesPartnerCode, SalesPartnerCode);
	}

	/** Get Vertriebspartnercode.
		@return Vertriebspartnercode	  */
	@Override
	public java.lang.String getSalesPartnerCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SalesPartnerCode);
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

	/** Set E-Mail senden.
		@param SendEMail 
		Enable sending Document EMail
	  */
	@Override
	public void setSendEMail (boolean SendEMail)
	{
		set_Value (COLUMNNAME_SendEMail, Boolean.valueOf(SendEMail));
	}

	/** Get E-Mail senden.
		@return Enable sending Document EMail
	  */
	@Override
	public boolean isSendEMail () 
	{
		Object oo = get_Value(COLUMNNAME_SendEMail);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Summe Zeilen.
		@param TotalLines 
		Total of all document lines
	  */
	@Override
	public void setTotalLines (java.math.BigDecimal TotalLines)
	{
		set_ValueNoCheck (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Summe Zeilen.
		@return Total of all document lines
	  */
	@Override
	public java.math.BigDecimal getTotalLines () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLines);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser1()
	{
		return get_ValueAsPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser1(org.compiere.model.I_C_ElementValue User1)
	{
		set_ValueFromPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class, User1);
	}

	/** Set Nutzer 1.
		@param User1_ID 
		User defined list element #1
	  */
	@Override
	public void setUser1_ID (int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
	}

	/** Get Nutzer 1.
		@return User defined list element #1
	  */
	@Override
	public int getUser1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser2()
	{
		return get_ValueAsPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser2(org.compiere.model.I_C_ElementValue User2)
	{
		set_ValueFromPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class, User2);
	}

	/** Set Nutzer 2.
		@param User2_ID 
		User defined list element #2
	  */
	@Override
	public void setUser2_ID (int User2_ID)
	{
		if (User2_ID < 1) 
			set_Value (COLUMNNAME_User2_ID, null);
		else 
			set_Value (COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
	}

	/** Get Nutzer 2.
		@return User defined list element #2
	  */
	@Override
	public int getUser2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UserFlag.
		@param UserFlag 
		Can be used to flag records and thus make them selectable from the UI via advanced search.
	  */
	@Override
	public void setUserFlag (java.lang.String UserFlag)
	{
		set_Value (COLUMNNAME_UserFlag, UserFlag);
	}

	/** Get UserFlag.
		@return Can be used to flag records and thus make them selectable from the UI via advanced search.
	  */
	@Override
	public java.lang.String getUserFlag () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserFlag);
	}
	
	
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

}