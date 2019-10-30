/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Order
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Order extends org.compiere.model.PO implements I_C_Order, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2025653177L;

    /** Standard Constructor */
    public X_C_Order (Properties ctx, int C_Order_ID, String trxName)
    {
      super (ctx, C_Order_ID, trxName);
      /** if (C_Order_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Currency_ID (0); // @C_Currency_ID@
			setC_DocType_ID (0); // 0
			setC_DocTypeTarget_ID (0);
			setC_Order_ID (0);
			setC_PaymentTerm_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() )); // @#Date@
			setDateOrdered (new Timestamp( System.currentTimeMillis() )); // @#Date@
			setDatePromised (new Timestamp( System.currentTimeMillis() )); // @#Date@
			setDeliveryRule (null); // F
			setDeliveryViaRule (null);
			setDocAction (null); // CO
			setDocStatus (null); // DR
			setDocumentNo (null);
			setFreightAmt (BigDecimal.ZERO);
			setFreightCostRule (null); // I
			setGrandTotal (BigDecimal.ZERO);
			setInvoiceRule (null); // I
			setIsApproved (false); // @IsApproved@
			setIsCreditApproved (false);
			setIsDelivered (false);
			setIsDiscountPrinted (false); // N
			setIsDropShip (false); // N
			setIsInvoiced (false);
			setIsPrinted (false);
			setIsSalesPartnerRequired (false); // N
			setIsSelected (false);
			setIsSelfService (false);
			setIsSOTrx (false); // @IsSOTrx@
			setIsTaxIncluded (false);
			setIsTransferred (false);
			setIsUseBillToAddress (false); // N
			setIsUseBPartnerAddress (false); // N
			setM_PricingSystem_ID (0);
			setM_Warehouse_ID (0);
			setPaymentRule (null);
			setPosted (false); // N
			setPriorityRule (null); // 5
			setProcessed (false);
			setSendEMail (false);
			setTotalLines (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_Order (Properties ctx, ResultSet rs, String trxName)
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

	/** Set AmountRefunded.
		@param AmountRefunded AmountRefunded	  */
	@Override
	public void setAmountRefunded (java.math.BigDecimal AmountRefunded)
	{
		set_Value (COLUMNNAME_AmountRefunded, AmountRefunded);
	}

	/** Get AmountRefunded.
		@return AmountRefunded	  */
	@Override
	public java.math.BigDecimal getAmountRefunded () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountRefunded);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set AmountTendered.
		@param AmountTendered AmountTendered	  */
	@Override
	public void setAmountTendered (java.math.BigDecimal AmountTendered)
	{
		set_Value (COLUMNNAME_AmountTendered, AmountTendered);
	}

	/** Get AmountTendered.
		@return AmountTendered	  */
	@Override
	public java.math.BigDecimal getAmountTendered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountTendered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Rechnungspartner.
		@param Bill_BPartner_ID 
		Geschäftspartner für die Rechnungsstellung
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
		@return Geschäftspartner für die Rechnungsstellung
	  */
	@Override
	public int getBill_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rechnungspartner-Memo.
		@param Bill_BPartner_Memo Rechnungspartner-Memo	  */
	@Override
	public void setBill_BPartner_Memo (java.lang.String Bill_BPartner_Memo)
	{
		throw new IllegalArgumentException ("Bill_BPartner_Memo is virtual column");	}

	/** Get Rechnungspartner-Memo.
		@return Rechnungspartner-Memo	  */
	@Override
	public java.lang.String getBill_BPartner_Memo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Bill_BPartner_Memo);
	}

	/** Set Rechnungsstandort.
		@param Bill_Location_ID 
		Business Partner Location for invoicing
	  */
	@Override
	public void setBill_Location_ID (int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_ID, Integer.valueOf(Bill_Location_ID));
	}

	/** Get Rechnungsstandort.
		@return Business Partner Location for invoicing
	  */
	@Override
	public int getBill_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Abw. Rechnungsadresse.
		@param BillToAddress Abw. Rechnungsadresse	  */
	@Override
	public void setBillToAddress (java.lang.String BillToAddress)
	{
		set_Value (COLUMNNAME_BillToAddress, BillToAddress);
	}

	/** Get Abw. Rechnungsadresse.
		@return Abw. Rechnungsadresse	  */
	@Override
	public java.lang.String getBillToAddress () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BillToAddress);
	}

	/** Set Rechnungskontakt.
		@param Bill_User_ID 
		Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_User_ID (int Bill_User_ID)
	{
		if (Bill_User_ID < 1) 
			set_Value (COLUMNNAME_Bill_User_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_User_ID, Integer.valueOf(Bill_User_ID));
	}

	/** Get Rechnungskontakt.
		@return Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_User_ID);
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

	/** Set Geschäftspartner-Memo.
		@param C_BPartner_Memo Geschäftspartner-Memo	  */
	@Override
	public void setC_BPartner_Memo (java.lang.String C_BPartner_Memo)
	{
		throw new IllegalArgumentException ("C_BPartner_Memo is virtual column");	}

	/** Get Geschäftspartner-Memo.
		@return Geschäftspartner-Memo	  */
	@Override
	public java.lang.String getC_BPartner_Memo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_C_BPartner_Memo);
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
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccount);
	}

	/** Set Bankverbindung.
		@param C_BP_BankAccount_ID 
		Bank Account of the Business Partner
	  */
	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	/** Get Bankverbindung.
		@return Bank Account of the Business Partner
	  */
	@Override
	public int getC_BP_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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

	/** Set Rabatt %.
		@param CompleteOrderDiscount 
		Abschlag in Prozent
	  */
	@Override
	public void setCompleteOrderDiscount (java.math.BigDecimal CompleteOrderDiscount)
	{
		set_Value (COLUMNNAME_CompleteOrderDiscount, CompleteOrderDiscount);
	}

	/** Get Rabatt %.
		@return Abschlag in Prozent
	  */
	@Override
	public java.math.BigDecimal getCompleteOrderDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CompleteOrderDiscount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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
	public org.compiere.model.I_C_Payment getC_Payment()
	{
		return get_ValueAsPO(COLUMNNAME_C_Payment_ID, org.compiere.model.I_C_Payment.class);
	}

	@Override
	public void setC_Payment(org.compiere.model.I_C_Payment C_Payment)
	{
		set_ValueFromPO(COLUMNNAME_C_Payment_ID, org.compiere.model.I_C_Payment.class, C_Payment);
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

	@Override
	public org.compiere.model.I_C_POS getC_POS()
	{
		return get_ValueAsPO(COLUMNNAME_C_POS_ID, org.compiere.model.I_C_POS.class);
	}

	@Override
	public void setC_POS(org.compiere.model.I_C_POS C_POS)
	{
		set_ValueFromPO(COLUMNNAME_C_POS_ID, org.compiere.model.I_C_POS.class, C_POS);
	}

	/** Set POS-Terminal.
		@param C_POS_ID 
		Point of Sales Terminal
	  */
	@Override
	public void setC_POS_ID (int C_POS_ID)
	{
		if (C_POS_ID < 1) 
			set_Value (COLUMNNAME_C_POS_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_ID, Integer.valueOf(C_POS_ID));
	}

	/** Get POS-Terminal.
		@return Point of Sales Terminal
	  */
	@Override
	public int getC_POS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_POS_ID);
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

	/** Set Create Copy.
		@param CreateCopy Create Copy	  */
	@Override
	public void setCreateCopy (java.lang.String CreateCopy)
	{
		set_Value (COLUMNNAME_CreateCopy, CreateCopy);
	}

	/** Get Create Copy.
		@return Create Copy	  */
	@Override
	public java.lang.String getCreateCopy () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreateCopy);
	}

	/** Set Erzeuge Auftrag.
		@param CreateNewFromProposal 
		Erzeugt aus dem bestehenden Angebot einen neuen Auftrag
	  */
	@Override
	public void setCreateNewFromProposal (java.lang.String CreateNewFromProposal)
	{
		set_Value (COLUMNNAME_CreateNewFromProposal, CreateNewFromProposal);
	}

	/** Get Erzeuge Auftrag.
		@return Erzeugt aus dem bestehenden Angebot einen neuen Auftrag
	  */
	@Override
	public java.lang.String getCreateNewFromProposal () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreateNewFromProposal);
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

	/** Set Auftragsdatum.
		@param DateOrdered 
		Date of Order
	  */
	@Override
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
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

	/** Set Zugesagter Termin.
		@param DatePromised 
		Date Order was promised
	  */
	@Override
	public void setDatePromised (java.sql.Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Zugesagter Termin.
		@return Date Order was promised
	  */
	@Override
	public java.sql.Timestamp getDatePromised () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DatePromised);
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
		Defines the timing of Delivery
	  */
	@Override
	public void setDeliveryRule (java.lang.String DeliveryRule)
	{

		set_Value (COLUMNNAME_DeliveryRule, DeliveryRule);
	}

	/** Get Lieferart.
		@return Defines the timing of Delivery
	  */
	@Override
	public java.lang.String getDeliveryRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryRule);
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

		set_Value (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	/** Get Lieferung.
		@return Wie der Auftrag geliefert wird
	  */
	@Override
	public java.lang.String getDeliveryViaRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryViaRule);
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

	/** Set Schlusstext.
		@param DescriptionBottom Schlusstext	  */
	@Override
	public void setDescriptionBottom (java.lang.String DescriptionBottom)
	{
		set_Value (COLUMNNAME_DescriptionBottom, DescriptionBottom);
	}

	/** Get Schlusstext.
		@return Schlusstext	  */
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

	/** Set Lieferempfänger.
		@param DropShip_BPartner_ID 
		Business Partner to ship to
	  */
	@Override
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID)
	{
		if (DropShip_BPartner_ID < 1) 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, Integer.valueOf(DropShip_BPartner_ID));
	}

	/** Get Lieferempfänger.
		@return Business Partner to ship to
	  */
	@Override
	public int getDropShip_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lieferempfänger-Memo.
		@param DropShip_BPartner_Memo Lieferempfänger-Memo	  */
	@Override
	public void setDropShip_BPartner_Memo (java.lang.String DropShip_BPartner_Memo)
	{
		throw new IllegalArgumentException ("DropShip_BPartner_Memo is virtual column");	}

	/** Get Lieferempfänger-Memo.
		@return Lieferempfänger-Memo	  */
	@Override
	public java.lang.String getDropShip_BPartner_Memo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DropShip_BPartner_Memo);
	}

	/** Set Lieferadresse.
		@param DropShip_Location_ID 
		Business Partner Location for shipping to
	  */
	@Override
	public void setDropShip_Location_ID (int DropShip_Location_ID)
	{
		if (DropShip_Location_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_ID, Integer.valueOf(DropShip_Location_ID));
	}

	/** Get Lieferadresse.
		@return Business Partner Location for shipping to
	  */
	@Override
	public int getDropShip_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lieferkontakt.
		@param DropShip_User_ID 
		Business Partner Contact for drop shipment
	  */
	@Override
	public void setDropShip_User_ID (int DropShip_User_ID)
	{
		if (DropShip_User_ID < 1) 
			set_Value (COLUMNNAME_DropShip_User_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_User_ID, Integer.valueOf(DropShip_User_ID));
	}

	/** Get Lieferkontakt.
		@return Business Partner Contact for drop shipment
	  */
	@Override
	public int getDropShip_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Frachtbetrag.
		@param FreightAmt 
		Freight Amount 
	  */
	@Override
	public void setFreightAmt (java.math.BigDecimal FreightAmt)
	{
		set_Value (COLUMNNAME_FreightAmt, FreightAmt);
	}

	/** Get Frachtbetrag.
		@return Freight Amount 
	  */
	@Override
	public java.math.BigDecimal getFreightAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FreightAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** 
	 * FreightCostRule AD_Reference_ID=153
	 * Reference name: C_Order FreightCostRule
	 */
	public static final int FREIGHTCOSTRULE_AD_Reference_ID=153;
	/** FreightIncluded = I */
	public static final String FREIGHTCOSTRULE_FreightIncluded = "I";
	/** FixPrice = F */
	public static final String FREIGHTCOSTRULE_FixPrice = "F";
	/** Calculated = C */
	public static final String FREIGHTCOSTRULE_Calculated = "C";
	/** Line = L */
	public static final String FREIGHTCOSTRULE_Line = "L";
	/** Versandkostenpauschale = P */
	public static final String FREIGHTCOSTRULE_Versandkostenpauschale = "P";
	/** Set Frachtkostenberechnung.
		@param FreightCostRule 
		Methode zur Frachtkostenberechnung
	  */
	@Override
	public void setFreightCostRule (java.lang.String FreightCostRule)
	{

		set_Value (COLUMNNAME_FreightCostRule, FreightCostRule);
	}

	/** Get Frachtkostenberechnung.
		@return Methode zur Frachtkostenberechnung
	  */
	@Override
	public java.lang.String getFreightCostRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FreightCostRule);
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
	 * InvoiceRule AD_Reference_ID=150
	 * Reference name: C_Order InvoiceRule
	 */
	public static final int INVOICERULE_AD_Reference_ID=150;
	/** AfterOrderDelivered = O */
	public static final String INVOICERULE_AfterOrderDelivered = "O";
	/** AfterDelivery = D */
	public static final String INVOICERULE_AfterDelivery = "D";
	/** CustomerScheduleAfterDelivery = S */
	public static final String INVOICERULE_CustomerScheduleAfterDelivery = "S";
	/** Immediate = I */
	public static final String INVOICERULE_Immediate = "I";
	/** Set Rechnungsstellung.
		@param InvoiceRule 
		"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	  */
	@Override
	public void setInvoiceRule (java.lang.String InvoiceRule)
	{

		set_Value (COLUMNNAME_InvoiceRule, InvoiceRule);
	}

	/** Get Rechnungsstellung.
		@return "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	  */
	@Override
	public java.lang.String getInvoiceRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceRule);
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

	/** Set Kredit gebilligt.
		@param IsCreditApproved 
		Credit  has been approved
	  */
	@Override
	public void setIsCreditApproved (boolean IsCreditApproved)
	{
		set_ValueNoCheck (COLUMNNAME_IsCreditApproved, Boolean.valueOf(IsCreditApproved));
	}

	/** Get Kredit gebilligt.
		@return Credit  has been approved
	  */
	@Override
	public boolean isCreditApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsCreditApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zugestellt.
		@param IsDelivered Zugestellt	  */
	@Override
	public void setIsDelivered (boolean IsDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_IsDelivered, Boolean.valueOf(IsDelivered));
	}

	/** Get Zugestellt.
		@return Zugestellt	  */
	@Override
	public boolean isDelivered () 
	{
		Object oo = get_Value(COLUMNNAME_IsDelivered);
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

	/** Set Streckengeschäft.
		@param IsDropShip 
		Drop Shipments are sent from the Vendor directly to the Customer
	  */
	@Override
	public void setIsDropShip (boolean IsDropShip)
	{
		set_Value (COLUMNNAME_IsDropShip, Boolean.valueOf(IsDropShip));
	}

	/** Get Streckengeschäft.
		@return Drop Shipments are sent from the Vendor directly to the Customer
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

	/** Set Berechnete Menge.
		@param IsInvoiced 
		Is this invoiced?
	  */
	@Override
	public void setIsInvoiced (boolean IsInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_IsInvoiced, Boolean.valueOf(IsInvoiced));
	}

	/** Get Berechnete Menge.
		@return Is this invoiced?
	  */
	@Override
	public boolean isInvoiced () 
	{
		Object oo = get_Value(COLUMNNAME_IsInvoiced);
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

	/** Set Selektiert.
		@param IsSelected Selektiert	  */
	@Override
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selektiert.
		@return Selektiert	  */
	@Override
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
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
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
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

	/** Set Benutze abw. Rechnungsadresse.
		@param IsUseBillToAddress Benutze abw. Rechnungsadresse	  */
	@Override
	public void setIsUseBillToAddress (boolean IsUseBillToAddress)
	{
		set_Value (COLUMNNAME_IsUseBillToAddress, Boolean.valueOf(IsUseBillToAddress));
	}

	/** Get Benutze abw. Rechnungsadresse.
		@return Benutze abw. Rechnungsadresse	  */
	@Override
	public boolean isUseBillToAddress () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseBillToAddress);
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

	@Override
	public org.compiere.model.I_C_Order getLink_Order()
	{
		return get_ValueAsPO(COLUMNNAME_Link_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setLink_Order(org.compiere.model.I_C_Order Link_Order)
	{
		set_ValueFromPO(COLUMNNAME_Link_Order_ID, org.compiere.model.I_C_Order.class, Link_Order);
	}

	/** Set Zugehörige Bestellung.
		@param Link_Order_ID 
		Mit diesem Feld kann ein Auftrag die ihm zugehörige Bestellung referenzieren.
	  */
	@Override
	public void setLink_Order_ID (int Link_Order_ID)
	{
		if (Link_Order_ID < 1) 
			set_Value (COLUMNNAME_Link_Order_ID, null);
		else 
			set_Value (COLUMNNAME_Link_Order_ID, Integer.valueOf(Link_Order_ID));
	}

	/** Get Zugehörige Bestellung.
		@return Mit diesem Feld kann ein Auftrag die ihm zugehörige Bestellung referenzieren.
	  */
	@Override
	public int getLink_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Link_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_FreightCategory getM_FreightCategory()
	{
		return get_ValueAsPO(COLUMNNAME_M_FreightCategory_ID, org.compiere.model.I_M_FreightCategory.class);
	}

	@Override
	public void setM_FreightCategory(org.compiere.model.I_M_FreightCategory M_FreightCategory)
	{
		set_ValueFromPO(COLUMNNAME_M_FreightCategory_ID, org.compiere.model.I_M_FreightCategory.class, M_FreightCategory);
	}

	/** Set Fracht-Kategorie.
		@param M_FreightCategory_ID 
		Category of the Freight
	  */
	@Override
	public void setM_FreightCategory_ID (int M_FreightCategory_ID)
	{
		if (M_FreightCategory_ID < 1) 
			set_Value (COLUMNNAME_M_FreightCategory_ID, null);
		else 
			set_Value (COLUMNNAME_M_FreightCategory_ID, Integer.valueOf(M_FreightCategory_ID));
	}

	/** Get Fracht-Kategorie.
		@return Category of the Freight
	  */
	@Override
	public int getM_FreightCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_FreightCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Preissystem.
		@param M_PricingSystem_ID 
		Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public void setM_PricingSystem_ID (int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, Integer.valueOf(M_PricingSystem_ID));
	}

	/** Get Preissystem.
		@return Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public int getM_PricingSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PricingSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** Set Lager.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
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
		@return Storage Warehouse and Service Point
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Orderline_includedTab.
		@param Orderline_includedTab Orderline_includedTab	  */
	@Override
	public void setOrderline_includedTab (java.lang.String Orderline_includedTab)
	{
		set_ValueNoCheck (COLUMNNAME_Orderline_includedTab, Orderline_includedTab);
	}

	/** Get Orderline_includedTab.
		@return Orderline_includedTab	  */
	@Override
	public java.lang.String getOrderline_includedTab () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Orderline_includedTab);
	}

	/** Set OrderType.
		@param OrderType OrderType	  */
	@Override
	public void setOrderType (java.lang.String OrderType)
	{
		set_Value (COLUMNNAME_OrderType, OrderType);
	}

	/** Get OrderType.
		@return OrderType	  */
	@Override
	public java.lang.String getOrderType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OrderType);
	}

	/** Set Payment BPartner.
		@param Pay_BPartner_ID 
		Business Partner responsible for the payment
	  */
	@Override
	public void setPay_BPartner_ID (int Pay_BPartner_ID)
	{
		if (Pay_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Pay_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Pay_BPartner_ID, Integer.valueOf(Pay_BPartner_ID));
	}

	/** Get Payment BPartner.
		@return Business Partner responsible for the payment
	  */
	@Override
	public int getPay_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Pay_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payment Location.
		@param Pay_Location_ID 
		Location of the Business Partner responsible for the payment
	  */
	@Override
	public void setPay_Location_ID (int Pay_Location_ID)
	{
		if (Pay_Location_ID < 1) 
			set_Value (COLUMNNAME_Pay_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Pay_Location_ID, Integer.valueOf(Pay_Location_ID));
	}

	/** Get Payment Location.
		@return Location of the Business Partner responsible for the payment
	  */
	@Override
	public int getPay_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Pay_Location_ID);
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

	/** Set Verbucht.
		@param Posted 
		Posting status
	  */
	@Override
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Verbucht.
		@return Posting status
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

		set_Value (COLUMNNAME_PriorityRule, PriorityRule);
	}

	/** Get Priorität.
		@return Priority of a document
	  */
	@Override
	public java.lang.String getPriorityRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PriorityRule);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
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

	/** Set Promotion Code.
		@param PromotionCode 
		User entered promotion code at sales time
	  */
	@Override
	public void setPromotionCode (java.lang.String PromotionCode)
	{
		set_Value (COLUMNNAME_PromotionCode, PromotionCode);
	}

	/** Get Promotion Code.
		@return User entered promotion code at sales time
	  */
	@Override
	public java.lang.String getPromotionCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PromotionCode);
	}

	/** Set Menge-Schnelleingabe.
		@param Qty_FastInput Menge-Schnelleingabe	  */
	@Override
	public void setQty_FastInput (java.math.BigDecimal Qty_FastInput)
	{
		set_Value (COLUMNNAME_Qty_FastInput, Qty_FastInput);
	}

	/** Get Menge-Schnelleingabe.
		@return Menge-Schnelleingabe	  */
	@Override
	public java.math.BigDecimal getQty_FastInput () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty_FastInput);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** 
	 * ReceivedVia AD_Reference_ID=540088
	 * Reference name: ReceivedVia
	 */
	public static final int RECEIVEDVIA_AD_Reference_ID=540088;
	/** Fax = F */
	public static final String RECEIVEDVIA_Fax = "F";
	/** Telefon = T */
	public static final String RECEIVEDVIA_Telefon = "T";
	/** Webshop = W */
	public static final String RECEIVEDVIA_Webshop = "W";
	/** Barverkauf = B */
	public static final String RECEIVEDVIA_Barverkauf = "B";
	/** E-Mail = E */
	public static final String RECEIVEDVIA_E_Mail = "E";
	/** Seminar = S */
	public static final String RECEIVEDVIA_Seminar = "S";
	/** Set Eingegangen via.
		@param ReceivedVia Eingegangen via	  */
	@Override
	public void setReceivedVia (java.lang.String ReceivedVia)
	{

		set_Value (COLUMNNAME_ReceivedVia, ReceivedVia);
	}

	/** Get Eingegangen via.
		@return Eingegangen via	  */
	@Override
	public java.lang.String getReceivedVia () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReceivedVia);
	}

	/** Set Auftragsdatum  (Ref. Auftrag).
		@param Ref_DateOrder Auftragsdatum  (Ref. Auftrag)	  */
	@Override
	public void setRef_DateOrder (java.sql.Timestamp Ref_DateOrder)
	{
		throw new IllegalArgumentException ("Ref_DateOrder is virtual column");	}

	/** Get Auftragsdatum  (Ref. Auftrag).
		@return Auftragsdatum  (Ref. Auftrag)	  */
	@Override
	public java.sql.Timestamp getRef_DateOrder () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_Ref_DateOrder);
	}

	@Override
	public org.compiere.model.I_C_Order getRef_Order()
	{
		return get_ValueAsPO(COLUMNNAME_Ref_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setRef_Order(org.compiere.model.I_C_Order Ref_Order)
	{
		set_ValueFromPO(COLUMNNAME_Ref_Order_ID, org.compiere.model.I_C_Order.class, Ref_Order);
	}

	/** Set Referenced Order.
		@param Ref_Order_ID 
		Reference to corresponding Sales/Purchase Order
	  */
	@Override
	public void setRef_Order_ID (int Ref_Order_ID)
	{
		if (Ref_Order_ID < 1) 
			set_Value (COLUMNNAME_Ref_Order_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_Order_ID, Integer.valueOf(Ref_Order_ID));
	}

	/** Get Referenced Order.
		@return Reference to corresponding Sales/Purchase Order
	  */
	@Override
	public int getRef_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order getRef_Proposal()
	{
		return get_ValueAsPO(COLUMNNAME_Ref_Proposal_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setRef_Proposal(org.compiere.model.I_C_Order Ref_Proposal)
	{
		set_ValueFromPO(COLUMNNAME_Ref_Proposal_ID, org.compiere.model.I_C_Order.class, Ref_Proposal);
	}

	/** Set Referenz Angebot/Auftrag.
		@param Ref_Proposal_ID 
		Verknüpfung zwischen einem Angebot und dem zugehörigen Auftrag
	  */
	@Override
	public void setRef_Proposal_ID (int Ref_Proposal_ID)
	{
		if (Ref_Proposal_ID < 1) 
			set_Value (COLUMNNAME_Ref_Proposal_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_Proposal_ID, Integer.valueOf(Ref_Proposal_ID));
	}

	/** Get Referenz Angebot/Auftrag.
		@return Verknüpfung zwischen einem Angebot und dem zugehörigen Auftrag
	  */
	@Override
	public int getRef_Proposal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_Proposal_ID);
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

	/** Set Volumen.
		@param Volume 
		Volume of a product
	  */
	@Override
	public void setVolume (java.math.BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	/** Get Volumen.
		@return Volume of a product
	  */
	@Override
	public java.math.BigDecimal getVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Volume);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Gewicht.
		@param Weight 
		Weight of a product
	  */
	@Override
	public void setWeight (java.math.BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Gewicht.
		@return Weight of a product
	  */
	@Override
	public java.math.BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}