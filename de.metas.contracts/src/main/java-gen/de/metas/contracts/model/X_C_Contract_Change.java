/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Contract_Change
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Contract_Change extends org.compiere.model.PO implements I_C_Contract_Change, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -327922583L;

    /** Standard Constructor */
    public X_C_Contract_Change (Properties ctx, int C_Contract_Change_ID, String trxName)
    {
      super (ctx, C_Contract_Change_ID, trxName);
      /** if (C_Contract_Change_ID == 0)
        {
			setAction (null);
			setC_Contract_Change_ID (0);
			setC_Flatrate_Transition_ID (0);
			setDeadLine (0);
			setDeadLineUnit (null);
        } */
    }

    /** Load Constructor */
    public X_C_Contract_Change (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * Action AD_Reference_ID=540010
	 * Reference name: C_Contract_Change Action
	 */
	public static final int ACTION_AD_Reference_ID=540010;
	/** Statuswechsel = ST */
	public static final String ACTION_Statuswechsel = "ST";
	/** Abowechsel = SU */
	public static final String ACTION_Abowechsel = "SU";
	/** Set Aktion.
		@param Action 
		Zeigt die durchzuführende Aktion an
	  */
	@Override
	public void setAction (java.lang.String Action)
	{

		set_Value (COLUMNNAME_Action, Action);
	}

	/** Get Aktion.
		@return Zeigt die durchzuführende Aktion an
	  */
	@Override
	public java.lang.String getAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Action);
	}

	/** Set Abowechsel-Konditionen.
		@param C_Contract_Change_ID Abowechsel-Konditionen	  */
	@Override
	public void setC_Contract_Change_ID (int C_Contract_Change_ID)
	{
		if (C_Contract_Change_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Contract_Change_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Contract_Change_ID, Integer.valueOf(C_Contract_Change_ID));
	}

	/** Get Abowechsel-Konditionen.
		@return Abowechsel-Konditionen	  */
	@Override
	public int getC_Contract_Change_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Contract_Change_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Conditions_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class);
	}

	@Override
	public void setC_Flatrate_Conditions(de.metas.contracts.model.I_C_Flatrate_Conditions C_Flatrate_Conditions)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Conditions_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class, C_Flatrate_Conditions);
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

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions_Next() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Conditions_Next_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class);
	}

	@Override
	public void setC_Flatrate_Conditions_Next(de.metas.contracts.model.I_C_Flatrate_Conditions C_Flatrate_Conditions_Next)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Conditions_Next_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class, C_Flatrate_Conditions_Next);
	}

	/** Set Nächste Vertragsbedingungen.
		@param C_Flatrate_Conditions_Next_ID 
		Auswahl der Vertragsbedingungen, die bei einer Vertragsverlängerung in der folgenden Vertragsperide anzuwenden sind
	  */
	@Override
	public void setC_Flatrate_Conditions_Next_ID (int C_Flatrate_Conditions_Next_ID)
	{
		if (C_Flatrate_Conditions_Next_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_Next_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_Next_ID, Integer.valueOf(C_Flatrate_Conditions_Next_ID));
	}

	/** Get Nächste Vertragsbedingungen.
		@return Auswahl der Vertragsbedingungen, die bei einer Vertragsverlängerung in der folgenden Vertragsperide anzuwenden sind
	  */
	@Override
	public int getC_Flatrate_Conditions_Next_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Conditions_Next_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Transition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Transition_ID, Integer.valueOf(C_Flatrate_Transition_ID));
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

	/** 
	 * ContractStatus AD_Reference_ID=540004
	 * Reference name: ContractStatus ChangeStatus
	 */
	public static final int CONTRACTSTATUS_AD_Reference_ID=540004;
	/** Lieferpause = Pa */
	public static final String CONTRACTSTATUS_Lieferpause = "Pa";
	/** Gekündigt = Qu */
	public static final String CONTRACTSTATUS_Gekuendigt = "Qu";
	/** Laufend  = Ru */
	public static final String CONTRACTSTATUS_Laufend = "Ru";
	/** Liefersperre = St */
	public static final String CONTRACTSTATUS_Liefersperre = "St";
	/** Set Vertrags-Status.
		@param ContractStatus Vertrags-Status	  */
	@Override
	public void setContractStatus (java.lang.String ContractStatus)
	{

		set_Value (COLUMNNAME_ContractStatus, ContractStatus);
	}

	/** Get Vertrags-Status.
		@return Vertrags-Status	  */
	@Override
	public java.lang.String getContractStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContractStatus);
	}

	/** Set Gültigkeitsfrist.
		@param DeadLine 
		Zeitpunk vor dem Vertragsende, bis zu dem die Änderungskondition gültig ist
	  */
	@Override
	public void setDeadLine (int DeadLine)
	{
		set_Value (COLUMNNAME_DeadLine, Integer.valueOf(DeadLine));
	}

	/** Get Gültigkeitsfrist.
		@return Zeitpunk vor dem Vertragsende, bis zu dem die Änderungskondition gültig ist
	  */
	@Override
	public int getDeadLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DeadLine);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * DeadLineUnit AD_Reference_ID=540281
	 * Reference name: TermDurationUnit
	 */
	public static final int DEADLINEUNIT_AD_Reference_ID=540281;
	/** Monat(e) = month */
	public static final String DEADLINEUNIT_MonatE = "month";
	/** Woche(n) = week */
	public static final String DEADLINEUNIT_WocheN = "week";
	/** Tag(e) = day */
	public static final String DEADLINEUNIT_TagE = "day";
	/** Jahr(e) = year */
	public static final String DEADLINEUNIT_JahrE = "year";
	/** Set Einheit der Frist.
		@param DeadLineUnit Einheit der Frist	  */
	@Override
	public void setDeadLineUnit (java.lang.String DeadLineUnit)
	{

		set_Value (COLUMNNAME_DeadLineUnit, DeadLineUnit);
	}

	/** Get Einheit der Frist.
		@return Einheit der Frist	  */
	@Override
	public java.lang.String getDeadLineUnit () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeadLineUnit);
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
}