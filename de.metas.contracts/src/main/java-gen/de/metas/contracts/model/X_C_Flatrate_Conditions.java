/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_Conditions
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Flatrate_Conditions extends org.compiere.model.PO implements I_C_Flatrate_Conditions, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1549500807L;

    /** Standard Constructor */
    public X_C_Flatrate_Conditions (Properties ctx, int C_Flatrate_Conditions_ID, String trxName)
    {
      super (ctx, C_Flatrate_Conditions_ID, trxName);
      /** if (C_Flatrate_Conditions_ID == 0)
        {
			setC_Flatrate_Conditions_ID (0);
			setC_UOM_ID (0);
			setDocAction (null); // CO
			setDocStatus (null); // DR
			setInvoiceRule (null); // I
			setIsCalculatePrice (true); // Y
			setIsClosingWithActualSum (false); // N
			setIsClosingWithCorrectionSum (false); // N
			setIsCorrectionAmtAtClosing (false); // N
			setIsCreateNoInvoice (false); // N
			setIsFreeOfCharge (false); // N
			setIsManualPrice (false); // N
			setIsSimulation (false); // N
			setM_Product_Flatrate_ID (0);
			setMargin_Max (BigDecimal.ZERO);
			setMargin_Min (BigDecimal.ZERO);
			setName (null);
			setProcessed (false); // N
			setProcessing (false); // N
			setType_Clearing (null); // EX
			setType_Conditions (null);
			setType_Flatrate (null); // NONE
			setUOMType (null);
        } */
    }

    /** Load Constructor */
    public X_C_Flatrate_Conditions (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Vertragsbedingungen.
		@param C_Flatrate_Conditions_ID Vertragsbedingungen	  */
	@Override
	public void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID)
	{
		if (C_Flatrate_Conditions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Conditions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Conditions_ID, Integer.valueOf(C_Flatrate_Conditions_ID));
	}

	/** Get Vertragsbedingungen.
		@return Vertragsbedingungen	  */
	@Override
	public int getC_Flatrate_Conditions_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Conditions_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Flatrate_Matching_IncludedT.
		@param C_Flatrate_Matching_IncludedT C_Flatrate_Matching_IncludedT	  */
	@Override
	public void setC_Flatrate_Matching_IncludedT (java.lang.String C_Flatrate_Matching_IncludedT)
	{
		throw new IllegalArgumentException ("C_Flatrate_Matching_IncludedT is virtual column");	}

	/** Get C_Flatrate_Matching_IncludedT.
		@return C_Flatrate_Matching_IncludedT	  */
	@Override
	public java.lang.String getC_Flatrate_Matching_IncludedT () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_C_Flatrate_Matching_IncludedT);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Transition getC_Flatrate_Transition() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.contracts.model.I_C_Flatrate_Transition.class);
	}

	@Override
	public void setC_Flatrate_Transition(de.metas.contracts.model.I_C_Flatrate_Transition C_Flatrate_Transition)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.contracts.model.I_C_Flatrate_Transition.class, C_Flatrate_Transition);
	}

	/** Set Vertragsverlängerung/-übergang.
		@param C_Flatrate_Transition_ID 
		Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	  */
	@Override
	public void setC_Flatrate_Transition_ID (int C_Flatrate_Transition_ID)
	{
		if (C_Flatrate_Transition_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Transition_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Transition_ID, Integer.valueOf(C_Flatrate_Transition_ID));
	}

	/** Get Vertragsverlängerung/-übergang.
		@return Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	  */
	@Override
	public int getC_Flatrate_Transition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Transition_ID);
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

	/** 
	 * ClearingAmtBaseOn AD_Reference_ID=540278
	 * Reference name: ClearingAmtBaseOn
	 */
	public static final int CLEARINGAMTBASEON_AD_Reference_ID=540278;
	/** ProductPrice = ProductPrice */
	public static final String CLEARINGAMTBASEON_ProductPrice = "ProductPrice";
	/** FlatrateAmount = FlatrateAmount */
	public static final String CLEARINGAMTBASEON_FlatrateAmount = "FlatrateAmount";
	/** Set Basis für Verrechnungs-Zahlbetrag.
		@param ClearingAmtBaseOn 
		Entscheidet, ob der Verrechnungsbetrag auf Basis der Produktpreise (tats. erbrachte Leistungen) oder als prozentualer Aufschlag/Abschlag ermittelt wird. 
	  */
	@Override
	public void setClearingAmtBaseOn (java.lang.String ClearingAmtBaseOn)
	{

		set_Value (COLUMNNAME_ClearingAmtBaseOn, ClearingAmtBaseOn);
	}

	/** Get Basis für Verrechnungs-Zahlbetrag.
		@return Entscheidet, ob der Verrechnungsbetrag auf Basis der Produktpreise (tats. erbrachte Leistungen) oder als prozentualer Aufschlag/Abschlag ermittelt wird. 
	  */
	@Override
	public java.lang.String getClearingAmtBaseOn () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ClearingAmtBaseOn);
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
		Der zukünftige Status des Belegs
	  */
	@Override
	public void setDocAction (java.lang.String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Belegverarbeitung.
		@return Der zukünftige Status des Belegs
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

	/** 
	 * InvoiceRule AD_Reference_ID=150
	 * Reference name: C_Order InvoiceRule
	 */
	public static final int INVOICERULE_AD_Reference_ID=150;
	/** Nach Lieferung Auftrag = O */
	public static final String INVOICERULE_NachLieferungAuftrag = "O";
	/** Nach Lieferung = D */
	public static final String INVOICERULE_NachLieferung = "D";
	/** Kundenintervall (nach Lieferung) = S */
	public static final String INVOICERULE_KundenintervallNachLieferung = "S";
	/** Sofort = I */
	public static final String INVOICERULE_Sofort = "I";
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

	/** Set Calculate Price.
		@param IsCalculatePrice Calculate Price	  */
	@Override
	public void setIsCalculatePrice (boolean IsCalculatePrice)
	{
		set_Value (COLUMNNAME_IsCalculatePrice, Boolean.valueOf(IsCalculatePrice));
	}

	/** Get Calculate Price.
		@return Calculate Price	  */
	@Override
	public boolean isCalculatePrice () 
	{
		Object oo = get_Value(COLUMNNAME_IsCalculatePrice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Gegenüberstellung mit erbr. Leist..
		@param IsClosingWithActualSum 
		Legt fest, ob die pauschal abgerechenten Beträge den tatsächlich erbrachten Leistungen gegenüber gestellt werden sollen
	  */
	@Override
	public void setIsClosingWithActualSum (boolean IsClosingWithActualSum)
	{
		set_Value (COLUMNNAME_IsClosingWithActualSum, Boolean.valueOf(IsClosingWithActualSum));
	}

	/** Get Gegenüberstellung mit erbr. Leist..
		@return Legt fest, ob die pauschal abgerechenten Beträge den tatsächlich erbrachten Leistungen gegenüber gestellt werden sollen
	  */
	@Override
	public boolean isClosingWithActualSum () 
	{
		Object oo = get_Value(COLUMNNAME_IsClosingWithActualSum);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Abschlusskorrektur vorsehen.
		@param IsClosingWithCorrectionSum 
		Legt fest, ob innerhalb der Vertragslaufzeit (in der Regel zu deren Ende) noch korrigierte Pauschalen-Mengen erfasst werden können
	  */
	@Override
	public void setIsClosingWithCorrectionSum (boolean IsClosingWithCorrectionSum)
	{
		set_Value (COLUMNNAME_IsClosingWithCorrectionSum, Boolean.valueOf(IsClosingWithCorrectionSum));
	}

	/** Get Abschlusskorrektur vorsehen.
		@return Legt fest, ob innerhalb der Vertragslaufzeit (in der Regel zu deren Ende) noch korrigierte Pauschalen-Mengen erfasst werden können
	  */
	@Override
	public boolean isClosingWithCorrectionSum () 
	{
		Object oo = get_Value(COLUMNNAME_IsClosingWithCorrectionSum);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verrechung erst nach Abschlusskorrektur.
		@param IsCorrectionAmtAtClosing 
		Legt fest, ob Nach- bzw. Rückzahlungen erst nach Erfassung der Abschlusskorrektur in Rechnung zu stellen sind
	  */
	@Override
	public void setIsCorrectionAmtAtClosing (boolean IsCorrectionAmtAtClosing)
	{
		set_Value (COLUMNNAME_IsCorrectionAmtAtClosing, Boolean.valueOf(IsCorrectionAmtAtClosing));
	}

	/** Get Verrechung erst nach Abschlusskorrektur.
		@return Legt fest, ob Nach- bzw. Rückzahlungen erst nach Erfassung der Abschlusskorrektur in Rechnung zu stellen sind
	  */
	@Override
	public boolean isCorrectionAmtAtClosing () 
	{
		Object oo = get_Value(COLUMNNAME_IsCorrectionAmtAtClosing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Keine Rechnungserstellung.
		@param IsCreateNoInvoice Keine Rechnungserstellung	  */
	@Override
	public void setIsCreateNoInvoice (boolean IsCreateNoInvoice)
	{
		set_Value (COLUMNNAME_IsCreateNoInvoice, Boolean.valueOf(IsCreateNoInvoice));
	}

	/** Get Keine Rechnungserstellung.
		@return Keine Rechnungserstellung	  */
	@Override
	public boolean isCreateNoInvoice () 
	{
		Object oo = get_Value(COLUMNNAME_IsCreateNoInvoice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Gratis.
		@param IsFreeOfCharge 
		Es wird unabhängig vom gewählten Preissystem ein Rabatt von 100% gewährt
	  */
	@Override
	public void setIsFreeOfCharge (boolean IsFreeOfCharge)
	{
		set_Value (COLUMNNAME_IsFreeOfCharge, Boolean.valueOf(IsFreeOfCharge));
	}

	/** Get Gratis.
		@return Es wird unabhängig vom gewählten Preissystem ein Rabatt von 100% gewährt
	  */
	@Override
	public boolean isFreeOfCharge () 
	{
		Object oo = get_Value(COLUMNNAME_IsFreeOfCharge);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manueller Preis.
		@param IsManualPrice Manueller Preis	  */
	@Override
	public void setIsManualPrice (boolean IsManualPrice)
	{
		set_Value (COLUMNNAME_IsManualPrice, Boolean.valueOf(IsManualPrice));
	}

	/** Get Manueller Preis.
		@return Manueller Preis	  */
	@Override
	public boolean isManualPrice () 
	{
		Object oo = get_Value(COLUMNNAME_IsManualPrice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Planspiel.
		@param IsSimulation Planspiel	  */
	@Override
	public void setIsSimulation (boolean IsSimulation)
	{
		set_Value (COLUMNNAME_IsSimulation, Boolean.valueOf(IsSimulation));
	}

	/** Get Planspiel.
		@return Planspiel	  */
	@Override
	public boolean isSimulation () 
	{
		Object oo = get_Value(COLUMNNAME_IsSimulation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_PricingSystem getM_PricingSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class);
	}

	@Override
	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem)
	{
		set_ValueFromPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class, M_PricingSystem);
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

	@Override
	public org.compiere.model.I_M_Product getM_Product_Actual() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Actual_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product_Actual(org.compiere.model.I_M_Product M_Product_Actual)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Actual_ID, org.compiere.model.I_M_Product.class, M_Product_Actual);
	}

	/** Set Produkt für Verrechnung.
		@param M_Product_Actual_ID 
		Produkt, unter dem ggf. die Differenz zu tatsächlich erbrachten Leistungen in Rechnung gestellt wird.
	  */
	@Override
	public void setM_Product_Actual_ID (int M_Product_Actual_ID)
	{
		if (M_Product_Actual_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Actual_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Actual_ID, Integer.valueOf(M_Product_Actual_ID));
	}

	/** Get Produkt für Verrechnung.
		@return Produkt, unter dem ggf. die Differenz zu tatsächlich erbrachten Leistungen in Rechnung gestellt wird.
	  */
	@Override
	public int getM_Product_Actual_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Actual_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product_Correction() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Correction_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product_Correction(org.compiere.model.I_M_Product M_Product_Correction)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Correction_ID, org.compiere.model.I_M_Product.class, M_Product_Correction);
	}

	/** Set Produkt für Abschlusskorrektur.
		@param M_Product_Correction_ID 
		Produkt, unter dem ggf. die Differenz zu den in der Abschlusskorrektur gemeldeten Mengen in Rechnung gestellt wird.
	  */
	@Override
	public void setM_Product_Correction_ID (int M_Product_Correction_ID)
	{
		if (M_Product_Correction_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Correction_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Correction_ID, Integer.valueOf(M_Product_Correction_ID));
	}

	/** Get Produkt für Abschlusskorrektur.
		@return Produkt, unter dem ggf. die Differenz zu den in der Abschlusskorrektur gemeldeten Mengen in Rechnung gestellt wird.
	  */
	@Override
	public int getM_Product_Correction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Correction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product_Flatrate() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Flatrate_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product_Flatrate(org.compiere.model.I_M_Product M_Product_Flatrate)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Flatrate_ID, org.compiere.model.I_M_Product.class, M_Product_Flatrate);
	}

	/** Set Produkt für pauschale Berechnung.
		@param M_Product_Flatrate_ID 
		Produkt, unter dem die pauschal abzurechnenden Leistungen in Rechnung gestellt werden
	  */
	@Override
	public void setM_Product_Flatrate_ID (int M_Product_Flatrate_ID)
	{
		if (M_Product_Flatrate_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Flatrate_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Flatrate_ID, Integer.valueOf(M_Product_Flatrate_ID));
	}

	/** Get Produkt für pauschale Berechnung.
		@return Produkt, unter dem die pauschal abzurechnenden Leistungen in Rechnung gestellt werden
	  */
	@Override
	public int getM_Product_Flatrate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Flatrate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Korridor - Überschreitung.
		@param Margin_Max Korridor - Überschreitung	  */
	@Override
	public void setMargin_Max (java.math.BigDecimal Margin_Max)
	{
		set_Value (COLUMNNAME_Margin_Max, Margin_Max);
	}

	/** Get Korridor - Überschreitung.
		@return Korridor - Überschreitung	  */
	@Override
	public java.math.BigDecimal getMargin_Max () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Margin_Max);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Korridor - Unterschreitung.
		@param Margin_Min Korridor - Unterschreitung	  */
	@Override
	public void setMargin_Min (java.math.BigDecimal Margin_Min)
	{
		set_Value (COLUMNNAME_Margin_Min, Margin_Min);
	}

	/** Get Korridor - Unterschreitung.
		@return Korridor - Unterschreitung	  */
	@Override
	public java.math.BigDecimal getMargin_Min () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Margin_Min);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Drucktext.
		@param PrintName 
		Bezeichnung, die auf dem Dokument oder der Korrespondenz gedruckt werden soll
	  */
	@Override
	public void setPrintName (java.lang.String PrintName)
	{
		set_Value (COLUMNNAME_PrintName, PrintName);
	}

	/** Get Drucktext.
		@return Bezeichnung, die auf dem Dokument oder der Korrespondenz gedruckt werden soll
	  */
	@Override
	public java.lang.String getPrintName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrintName);
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

	/** 
	 * Type_Clearing AD_Reference_ID=540265
	 * Reference name: Type_Clearing
	 */
	public static final int TYPE_CLEARING_AD_Reference_ID=540265;
	/** Complete = CO */
	public static final String TYPE_CLEARING_Complete = "CO";
	/** Exceeding = EX */
	public static final String TYPE_CLEARING_Exceeding = "EX";
	/** Set Verrechnungsmodus.
		@param Type_Clearing Verrechnungsmodus	  */
	@Override
	public void setType_Clearing (java.lang.String Type_Clearing)
	{

		set_Value (COLUMNNAME_Type_Clearing, Type_Clearing);
	}

	/** Get Verrechnungsmodus.
		@return Verrechnungsmodus	  */
	@Override
	public java.lang.String getType_Clearing () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type_Clearing);
	}

	/** 
	 * Type_Conditions AD_Reference_ID=540271
	 * Reference name: Type_Conditions
	 */
	public static final int TYPE_CONDITIONS_AD_Reference_ID=540271;
	/** FlatFee = FlatFee */
	public static final String TYPE_CONDITIONS_FlatFee = "FlatFee";
	/** HoldingFee = HoldingFee */
	public static final String TYPE_CONDITIONS_HoldingFee = "HoldingFee";
	/** Subscription = Subscr */
	public static final String TYPE_CONDITIONS_Subscription = "Subscr";
	/** Refundable = Refundable */
	public static final String TYPE_CONDITIONS_Refundable = "Refundable";
	/** QualityBasedInvoicing = QualityBsd */
	public static final String TYPE_CONDITIONS_QualityBasedInvoicing = "QualityBsd";
	/** Procurement = Procuremnt */
	public static final String TYPE_CONDITIONS_Procurement = "Procuremnt";
	/** Set Vertragsart.
		@param Type_Conditions Vertragsart	  */
	@Override
	public void setType_Conditions (java.lang.String Type_Conditions)
	{

		set_ValueNoCheck (COLUMNNAME_Type_Conditions, Type_Conditions);
	}

	/** Get Vertragsart.
		@return Vertragsart	  */
	@Override
	public java.lang.String getType_Conditions () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type_Conditions);
	}

	/** 
	 * Type_Flatrate AD_Reference_ID=540264
	 * Reference name: Type_Flatrate
	 */
	public static final int TYPE_FLATRATE_AD_Reference_ID=540264;
	/** NONE = NONE */
	public static final String TYPE_FLATRATE_NONE = "NONE";
	/** Corridor_Percent = LIPE */
	public static final String TYPE_FLATRATE_Corridor_Percent = "LIPE";
	/** Set Verrechnungsart.
		@param Type_Flatrate 
		Art der Verrechnung bei der Gegenüberstellung mit tatsächliche erbrachten Leistungen
	  */
	@Override
	public void setType_Flatrate (java.lang.String Type_Flatrate)
	{

		set_Value (COLUMNNAME_Type_Flatrate, Type_Flatrate);
	}

	/** Get Verrechnungsart.
		@return Art der Verrechnung bei der Gegenüberstellung mit tatsächliche erbrachten Leistungen
	  */
	@Override
	public java.lang.String getType_Flatrate () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type_Flatrate);
	}

	/** 
	 * UOMType AD_Reference_ID=540262
	 * Reference name: UOM Type Flatrate
	 */
	public static final int UOMTYPE_AD_Reference_ID=540262;
	/** Gesundheitswesen = HC */
	public static final String UOMTYPE_Gesundheitswesen = "HC";
	/** Abrechnungsgenauigkeit = TD */
	public static final String UOMTYPE_Abrechnungsgenauigkeit = "TD";
	/** Set Einheiten-Typ.
		@param UOMType 
		Dient der Zusammenfassung ähnlicher Maßeinheiten
	  */
	@Override
	public void setUOMType (java.lang.String UOMType)
	{

		set_Value (COLUMNNAME_UOMType, UOMType);
	}

	/** Get Einheiten-Typ.
		@return Dient der Zusammenfassung ähnlicher Maßeinheiten
	  */
	@Override
	public java.lang.String getUOMType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UOMType);
	}
}