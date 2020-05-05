/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_AcctSchema
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_AcctSchema extends org.compiere.model.PO implements I_C_AcctSchema, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 935837223L;

    /** Standard Constructor */
    public X_C_AcctSchema (Properties ctx, int C_AcctSchema_ID, String trxName)
    {
      super (ctx, C_AcctSchema_ID, trxName);
      /** if (C_AcctSchema_ID == 0)
        {
			setAutoPeriodControl (false);
			setC_AcctSchema_ID (0);
			setC_Currency_ID (0);
			setCommitmentType (null); // N
			setCostingLevel (null); // C
			setCostingMethod (null); // S
			setGAAP (null);
			setHasAlias (false);
			setHasCombination (false);
			setIsAccrual (true); // Y
			setIsAdjustCOGS (false);
			setIsDiscountCorrectsTax (false);
			setIsExplicitCostAdjustment (false); // N
			setIsPostServices (false); // N
			setIsTradeDiscountPosted (false);
			setM_CostType_ID (0);
			setName (null);
			setSeparator (null); // -
			setTaxCorrectionType (null);
        } */
    }

    /** Load Constructor */
    public X_C_AcctSchema (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Org getAD_OrgOnly() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_OrgOnly_ID, org.compiere.model.I_AD_Org.class);
	}

	@Override
	public void setAD_OrgOnly(org.compiere.model.I_AD_Org AD_OrgOnly)
	{
		set_ValueFromPO(COLUMNNAME_AD_OrgOnly_ID, org.compiere.model.I_AD_Org.class, AD_OrgOnly);
	}

	/** Set Nur für Organisation.
		@param AD_OrgOnly_ID 
		Kontrierung nur für die angegebene Organisation
	  */
	@Override
	public void setAD_OrgOnly_ID (int AD_OrgOnly_ID)
	{
		if (AD_OrgOnly_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgOnly_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgOnly_ID, Integer.valueOf(AD_OrgOnly_ID));
	}

	/** Get Nur für Organisation.
		@return Kontrierung nur für die angegebene Organisation
	  */
	@Override
	public int getAD_OrgOnly_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgOnly_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Automatische Periodenkontrolle.
		@param AutoPeriodControl 
		If selected, the periods are automatically opened and closed
	  */
	@Override
	public void setAutoPeriodControl (boolean AutoPeriodControl)
	{
		set_Value (COLUMNNAME_AutoPeriodControl, Boolean.valueOf(AutoPeriodControl));
	}

	/** Get Automatische Periodenkontrolle.
		@return If selected, the periods are automatically opened and closed
	  */
	@Override
	public boolean isAutoPeriodControl () 
	{
		Object oo = get_Value(COLUMNNAME_AutoPeriodControl);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Buchführungs-Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	@Override
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Buchführungs-Schema.
		@return Rules for accounting
	  */
	@Override
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
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

	@Override
	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class);
	}

	@Override
	public void setC_Period(org.compiere.model.I_C_Period C_Period)
	{
		set_ValueFromPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class, C_Period);
	}

	/** Set Periode.
		@param C_Period_ID 
		Period of the Calendar
	  */
	@Override
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Periode.
		@return Period of the Calendar
	  */
	@Override
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * CommitmentType AD_Reference_ID=359
	 * Reference name: C_AcctSchema CommitmentType
	 */
	public static final int COMMITMENTTYPE_AD_Reference_ID=359;
	/** POCommitmentOnly = C */
	public static final String COMMITMENTTYPE_POCommitmentOnly = "C";
	/** POCommitmentReservation = B */
	public static final String COMMITMENTTYPE_POCommitmentReservation = "B";
	/** None = N */
	public static final String COMMITMENTTYPE_None = "N";
	/** POSOCommitmentReservation = A */
	public static final String COMMITMENTTYPE_POSOCommitmentReservation = "A";
	/** SOCommitmentOnly = S */
	public static final String COMMITMENTTYPE_SOCommitmentOnly = "S";
	/** POSOCommitment = O */
	public static final String COMMITMENTTYPE_POSOCommitment = "O";
	/** Set Art Reservierung.
		@param CommitmentType 
		Erstelle Reservierungen für Budgetkontrolle
	  */
	@Override
	public void setCommitmentType (java.lang.String CommitmentType)
	{

		set_Value (COLUMNNAME_CommitmentType, CommitmentType);
	}

	/** Get Art Reservierung.
		@return Erstelle Reservierungen für Budgetkontrolle
	  */
	@Override
	public java.lang.String getCommitmentType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CommitmentType);
	}

	/** 
	 * CostingLevel AD_Reference_ID=355
	 * Reference name: C_AcctSchema CostingLevel
	 */
	public static final int COSTINGLEVEL_AD_Reference_ID=355;
	/** Client = C */
	public static final String COSTINGLEVEL_Client = "C";
	/** Organization = O */
	public static final String COSTINGLEVEL_Organization = "O";
	/** BatchLot = B */
	public static final String COSTINGLEVEL_BatchLot = "B";
	/** Set Kostenrechnungsstufe.
		@param CostingLevel 
		The lowest level to accumulate Costing Information
	  */
	@Override
	public void setCostingLevel (java.lang.String CostingLevel)
	{

		set_Value (COLUMNNAME_CostingLevel, CostingLevel);
	}

	/** Get Kostenrechnungsstufe.
		@return The lowest level to accumulate Costing Information
	  */
	@Override
	public java.lang.String getCostingLevel () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CostingLevel);
	}

	/** 
	 * CostingMethod AD_Reference_ID=122
	 * Reference name: C_AcctSchema Costing Method
	 */
	public static final int COSTINGMETHOD_AD_Reference_ID=122;
	/** StandardCosting = S */
	public static final String COSTINGMETHOD_StandardCosting = "S";
	/** AveragePO = A */
	public static final String COSTINGMETHOD_AveragePO = "A";
	/** Lifo = L */
	public static final String COSTINGMETHOD_Lifo = "L";
	/** Fifo = F */
	public static final String COSTINGMETHOD_Fifo = "F";
	/** LastPOPrice = p */
	public static final String COSTINGMETHOD_LastPOPrice = "p";
	/** AverageInvoice = I */
	public static final String COSTINGMETHOD_AverageInvoice = "I";
	/** LastInvoice = i */
	public static final String COSTINGMETHOD_LastInvoice = "i";
	/** UserDefined = U */
	public static final String COSTINGMETHOD_UserDefined = "U";
	/** _ = x */
	public static final String COSTINGMETHOD__ = "x";
	/** Set Kostenrechnungsmethode.
		@param CostingMethod 
		Indicates how Costs will be calculated
	  */
	@Override
	public void setCostingMethod (java.lang.String CostingMethod)
	{

		set_Value (COLUMNNAME_CostingMethod, CostingMethod);
	}

	/** Get Kostenrechnungsmethode.
		@return Indicates how Costs will be calculated
	  */
	@Override
	public java.lang.String getCostingMethod () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CostingMethod);
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
	 * GAAP AD_Reference_ID=123
	 * Reference name: C_AcctSchema GAAP
	 */
	public static final int GAAP_AD_Reference_ID=123;
	/** InternationalGAAP = UN */
	public static final String GAAP_InternationalGAAP = "UN";
	/** USGAAP = US */
	public static final String GAAP_USGAAP = "US";
	/** GermanHGB = DE */
	public static final String GAAP_GermanHGB = "DE";
	/** FrenchAccountingStandard = FR */
	public static final String GAAP_FrenchAccountingStandard = "FR";
	/** CustomAccountingRules = XX */
	public static final String GAAP_CustomAccountingRules = "XX";
	/** Set GAAP.
		@param GAAP 
		Generally Accepted Accounting Principles
	  */
	@Override
	public void setGAAP (java.lang.String GAAP)
	{

		set_Value (COLUMNNAME_GAAP, GAAP);
	}

	/** Get GAAP.
		@return Generally Accepted Accounting Principles
	  */
	@Override
	public java.lang.String getGAAP () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GAAP);
	}

	/** Set Use Account Alias.
		@param HasAlias 
		Ability to select (partial) account combinations by an Alias
	  */
	@Override
	public void setHasAlias (boolean HasAlias)
	{
		set_Value (COLUMNNAME_HasAlias, Boolean.valueOf(HasAlias));
	}

	/** Get Use Account Alias.
		@return Ability to select (partial) account combinations by an Alias
	  */
	@Override
	public boolean isHasAlias () 
	{
		Object oo = get_Value(COLUMNNAME_HasAlias);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Use Account Combination Control.
		@param HasCombination 
		Combination of account elements are checked
	  */
	@Override
	public void setHasCombination (boolean HasCombination)
	{
		set_Value (COLUMNNAME_HasCombination, Boolean.valueOf(HasCombination));
	}

	/** Get Use Account Combination Control.
		@return Combination of account elements are checked
	  */
	@Override
	public boolean isHasCombination () 
	{
		Object oo = get_Value(COLUMNNAME_HasCombination);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Umsatzrealisierung bei Rechnung.
		@param IsAccrual 
		Definiert ob der Umsatz bei Rechnung oder erst bei Zahlung realisiert wird
	  */
	@Override
	public void setIsAccrual (boolean IsAccrual)
	{
		set_Value (COLUMNNAME_IsAccrual, Boolean.valueOf(IsAccrual));
	}

	/** Get Umsatzrealisierung bei Rechnung.
		@return Definiert ob der Umsatz bei Rechnung oder erst bei Zahlung realisiert wird
	  */
	@Override
	public boolean isAccrual () 
	{
		Object oo = get_Value(COLUMNNAME_IsAccrual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Adjust COGS.
		@param IsAdjustCOGS 
		Adjust Cost of Good Sold
	  */
	@Override
	public void setIsAdjustCOGS (boolean IsAdjustCOGS)
	{
		set_Value (COLUMNNAME_IsAdjustCOGS, Boolean.valueOf(IsAdjustCOGS));
	}

	/** Get Adjust COGS.
		@return Adjust Cost of Good Sold
	  */
	@Override
	public boolean isAdjustCOGS () 
	{
		Object oo = get_Value(COLUMNNAME_IsAdjustCOGS);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Negativbuchung erlauben.
		@param IsAllowNegativePosting 
		Erlaube die Buchung von negativen Werten
	  */
	@Override
	public void setIsAllowNegativePosting (boolean IsAllowNegativePosting)
	{
		set_Value (COLUMNNAME_IsAllowNegativePosting, Boolean.valueOf(IsAllowNegativePosting));
	}

	/** Get Negativbuchung erlauben.
		@return Erlaube die Buchung von negativen Werten
	  */
	@Override
	public boolean isAllowNegativePosting () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllowNegativePosting);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Correct tax for Discounts/Charges.
		@param IsDiscountCorrectsTax 
		Correct the tax for payment discount and charges
	  */
	@Override
	public void setIsDiscountCorrectsTax (boolean IsDiscountCorrectsTax)
	{
		set_Value (COLUMNNAME_IsDiscountCorrectsTax, Boolean.valueOf(IsDiscountCorrectsTax));
	}

	/** Get Correct tax for Discounts/Charges.
		@return Correct the tax for payment discount and charges
	  */
	@Override
	public boolean isDiscountCorrectsTax () 
	{
		Object oo = get_Value(COLUMNNAME_IsDiscountCorrectsTax);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Bezugsnebenkosten direkt verbuchen.
		@param IsExplicitCostAdjustment 
		Verbuchung der Bezugsnebenkosten direkt und nicht erst nach Aufteilung.
	  */
	@Override
	public void setIsExplicitCostAdjustment (boolean IsExplicitCostAdjustment)
	{
		set_Value (COLUMNNAME_IsExplicitCostAdjustment, Boolean.valueOf(IsExplicitCostAdjustment));
	}

	/** Get Bezugsnebenkosten direkt verbuchen.
		@return Verbuchung der Bezugsnebenkosten direkt und nicht erst nach Aufteilung.
	  */
	@Override
	public boolean isExplicitCostAdjustment () 
	{
		Object oo = get_Value(COLUMNNAME_IsExplicitCostAdjustment);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verbuchung bei identischen Konten.
		@param IsPostIfClearingEqual 
		Verbuchung bei identischen Konten (Transit) durchführen
	  */
	@Override
	public void setIsPostIfClearingEqual (boolean IsPostIfClearingEqual)
	{
		set_Value (COLUMNNAME_IsPostIfClearingEqual, Boolean.valueOf(IsPostIfClearingEqual));
	}

	/** Get Verbuchung bei identischen Konten.
		@return Verbuchung bei identischen Konten (Transit) durchführen
	  */
	@Override
	public boolean isPostIfClearingEqual () 
	{
		Object oo = get_Value(COLUMNNAME_IsPostIfClearingEqual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Leistungen seperat verbuchen.
		@param IsPostServices 
		Verbuchung soll unterscheiden soll zwischen Produkt Lieferungen und Dienstleistungen.
	  */
	@Override
	public void setIsPostServices (boolean IsPostServices)
	{
		set_Value (COLUMNNAME_IsPostServices, Boolean.valueOf(IsPostServices));
	}

	/** Get Leistungen seperat verbuchen.
		@return Verbuchung soll unterscheiden soll zwischen Produkt Lieferungen und Dienstleistungen.
	  */
	@Override
	public boolean isPostServices () 
	{
		Object oo = get_Value(COLUMNNAME_IsPostServices);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rabatte seperat verbuchen.
		@param IsTradeDiscountPosted 
		Erzeuge seperate Buchungen für Handelstrabatte
	  */
	@Override
	public void setIsTradeDiscountPosted (boolean IsTradeDiscountPosted)
	{
		set_Value (COLUMNNAME_IsTradeDiscountPosted, Boolean.valueOf(IsTradeDiscountPosted));
	}

	/** Get Rabatte seperat verbuchen.
		@return Erzeuge seperate Buchungen für Handelstrabatte
	  */
	@Override
	public boolean isTradeDiscountPosted () 
	{
		Object oo = get_Value(COLUMNNAME_IsTradeDiscountPosted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_CostType getM_CostType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_CostType_ID, org.compiere.model.I_M_CostType.class);
	}

	@Override
	public void setM_CostType(org.compiere.model.I_M_CostType M_CostType)
	{
		set_ValueFromPO(COLUMNNAME_M_CostType_ID, org.compiere.model.I_M_CostType.class, M_CostType);
	}

	/** Set Kostenkategorie.
		@param M_CostType_ID 
		Type of Cost (e.g. Current, Plan, Future)
	  */
	@Override
	public void setM_CostType_ID (int M_CostType_ID)
	{
		if (M_CostType_ID < 1) 
			set_Value (COLUMNNAME_M_CostType_ID, null);
		else 
			set_Value (COLUMNNAME_M_CostType_ID, Integer.valueOf(M_CostType_ID));
	}

	/** Get Kostenkategorie.
		@return Type of Cost (e.g. Current, Plan, Future)
	  */
	@Override
	public int getM_CostType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_CostType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Future Days.
		@param Period_OpenFuture 
		Number of days to be able to post to a future date (based on system date)
	  */
	@Override
	public void setPeriod_OpenFuture (int Period_OpenFuture)
	{
		set_Value (COLUMNNAME_Period_OpenFuture, Integer.valueOf(Period_OpenFuture));
	}

	/** Get Future Days.
		@return Number of days to be able to post to a future date (based on system date)
	  */
	@Override
	public int getPeriod_OpenFuture () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Period_OpenFuture);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set History Days.
		@param Period_OpenHistory 
		Number of days to be able to post in the past (based on system date)
	  */
	@Override
	public void setPeriod_OpenHistory (int Period_OpenHistory)
	{
		set_Value (COLUMNNAME_Period_OpenHistory, Integer.valueOf(Period_OpenHistory));
	}

	/** Get History Days.
		@return Number of days to be able to post in the past (based on system date)
	  */
	@Override
	public int getPeriod_OpenHistory () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Period_OpenHistory);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Element-Trenner.
		@param Separator 
		Element Separator
	  */
	@Override
	public void setSeparator (java.lang.String Separator)
	{
		set_Value (COLUMNNAME_Separator, Separator);
	}

	/** Get Element-Trenner.
		@return Element Separator
	  */
	@Override
	public java.lang.String getSeparator () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Separator);
	}

	/** 
	 * TaxCorrectionType AD_Reference_ID=392
	 * Reference name: C_AcctSchema TaxCorrectionType
	 */
	public static final int TAXCORRECTIONTYPE_AD_Reference_ID=392;
	/** None = N */
	public static final String TAXCORRECTIONTYPE_None = "N";
	/** Write_OffOnly = W */
	public static final String TAXCORRECTIONTYPE_Write_OffOnly = "W";
	/** DiscountOnly = D */
	public static final String TAXCORRECTIONTYPE_DiscountOnly = "D";
	/** Write_OffAndDiscount = B */
	public static final String TAXCORRECTIONTYPE_Write_OffAndDiscount = "B";
	/** Set MwSt. Korrektur.
		@param TaxCorrectionType 
		Art der MwSt. Korrektur
	  */
	@Override
	public void setTaxCorrectionType (java.lang.String TaxCorrectionType)
	{

		set_Value (COLUMNNAME_TaxCorrectionType, TaxCorrectionType);
	}

	/** Get MwSt. Korrektur.
		@return Art der MwSt. Korrektur
	  */
	@Override
	public java.lang.String getTaxCorrectionType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TaxCorrectionType);
	}
}