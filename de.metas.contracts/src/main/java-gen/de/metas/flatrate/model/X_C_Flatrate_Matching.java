/** Generated Model - DO NOT CHANGE */
package de.metas.flatrate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_Matching
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Flatrate_Matching extends org.compiere.model.PO implements I_C_Flatrate_Matching, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 897501876L;

    /** Standard Constructor */
    public X_C_Flatrate_Matching (Properties ctx, int C_Flatrate_Matching_ID, String trxName)
    {
      super (ctx, C_Flatrate_Matching_ID, trxName);
      /** if (C_Flatrate_Matching_ID == 0)
        {
			setC_Flatrate_Conditions_ID (0);
			setC_Flatrate_Matching_ID (0);
			setC_Flatrate_Transition_ID (0);
			setM_Product_Category_Matching_ID (0);
			setQtyPerDelivery (BigDecimal.ZERO); // 1
			setSeqNo (0); // @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 FROM C_Flatrate_Matching WHERE C_Flatrate_Conditions_ID=@C_Flatrate_Conditions_ID@
        } */
    }

    /** Load Constructor */
    public X_C_Flatrate_Matching (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Charge_ID, org.compiere.model.I_C_Charge.class);
	}

	@Override
	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge)
	{
		set_ValueFromPO(COLUMNNAME_C_Charge_ID, org.compiere.model.I_C_Charge.class, C_Charge);
	}

	/** Set Kosten.
		@param C_Charge_ID 
		Zusätzliche Kosten
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
		@return Zusätzliche Kosten
	  */
	@Override
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.flatrate.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Conditions_ID, de.metas.flatrate.model.I_C_Flatrate_Conditions.class);
	}

	@Override
	public void setC_Flatrate_Conditions(de.metas.flatrate.model.I_C_Flatrate_Conditions C_Flatrate_Conditions)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Conditions_ID, de.metas.flatrate.model.I_C_Flatrate_Conditions.class, C_Flatrate_Conditions);
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

	/** Set Zuordnungszeile.
		@param C_Flatrate_Matching_ID Zuordnungszeile	  */
	@Override
	public void setC_Flatrate_Matching_ID (int C_Flatrate_Matching_ID)
	{
		if (C_Flatrate_Matching_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Matching_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Matching_ID, Integer.valueOf(C_Flatrate_Matching_ID));
	}

	/** Get Zuordnungszeile.
		@return Zuordnungszeile	  */
	@Override
	public int getC_Flatrate_Matching_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Matching_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.flatrate.model.I_C_Flatrate_Transition getC_Flatrate_Transition() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.flatrate.model.I_C_Flatrate_Transition.class);
	}

	@Override
	public void setC_Flatrate_Transition(de.metas.flatrate.model.I_C_Flatrate_Transition C_Flatrate_Transition)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.flatrate.model.I_C_Flatrate_Transition.class, C_Flatrate_Transition);
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
	public org.compiere.model.I_M_Product_Category getM_Product_Category_Matching() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Category_Matching_ID, org.compiere.model.I_M_Product_Category.class);
	}

	@Override
	public void setM_Product_Category_Matching(org.compiere.model.I_M_Product_Category M_Product_Category_Matching)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Category_Matching_ID, org.compiere.model.I_M_Product_Category.class, M_Product_Category_Matching);
	}

	/** Set Produkt-Kategorie.
		@param M_Product_Category_Matching_ID 
		Kategorie eines Produktes, dass dem Vertrag unterliegt
	  */
	@Override
	public void setM_Product_Category_Matching_ID (int M_Product_Category_Matching_ID)
	{
		if (M_Product_Category_Matching_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_Matching_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_Matching_ID, Integer.valueOf(M_Product_Category_Matching_ID));
	}

	/** Get Produkt-Kategorie.
		@return Kategorie eines Produktes, dass dem Vertrag unterliegt
	  */
	@Override
	public int getM_Product_Category_Matching_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_Matching_ID);
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

	/** Set Liefermenge pro Abolieferung.
		@param QtyPerDelivery Liefermenge pro Abolieferung	  */
	@Override
	public void setQtyPerDelivery (java.math.BigDecimal QtyPerDelivery)
	{
		set_Value (COLUMNNAME_QtyPerDelivery, QtyPerDelivery);
	}

	/** Get Liefermenge pro Abolieferung.
		@return Liefermenge pro Abolieferung	  */
	@Override
	public java.math.BigDecimal getQtyPerDelivery () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPerDelivery);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}