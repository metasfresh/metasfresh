/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_Replenish
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_Replenish extends org.compiere.model.PO implements I_I_Replenish, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -962299609L;

    /** Standard Constructor */
    public X_I_Replenish (Properties ctx, int I_Replenish_ID, String trxName)
    {
      super (ctx, I_Replenish_ID, trxName);
      /** if (I_Replenish_ID == 0)
        {
			setI_IsImported (null); // N
			setI_Replenish_ID (0);
			setTimeToMarket (0); // 0
        } */
    }

    /** Load Constructor */
    public X_I_Replenish (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_Calendar getC_Calendar() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Calendar);
	}

	/** Set Kalender.
		@param C_Calendar_ID 
		Bezeichnung des Buchführungs-Kalenders
	  */
	@Override
	public void setC_Calendar_ID (int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_ID, Integer.valueOf(C_Calendar_ID));
	}

	/** Get Kalender.
		@return Bezeichnung des Buchführungs-Kalenders
	  */
	@Override
	public int getC_Calendar_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Calendar_ID);
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
		Periode des Kalenders
	  */
	@Override
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Periode.
		@return Periode des Kalenders
	  */
	@Override
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datum.
		@param DateGeneral Datum	  */
	@Override
	public void setDateGeneral (java.lang.String DateGeneral)
	{
		set_Value (COLUMNNAME_DateGeneral, DateGeneral);
	}

	/** Get Datum.
		@return Datum	  */
	@Override
	public java.lang.String getDateGeneral () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DateGeneral);
	}

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** 
	 * I_IsImported AD_Reference_ID=540745
	 * Reference name: I_IsImported
	 */
	public static final int I_ISIMPORTED_AD_Reference_ID=540745;
	/** NotImported = N */
	public static final String I_ISIMPORTED_NotImported = "N";
	/** Imported = Y */
	public static final String I_ISIMPORTED_Imported = "Y";
	/** ImportFailed = E */
	public static final String I_ISIMPORTED_ImportFailed = "E";
	/** Set Importiert.
		@param I_IsImported 
		Ist dieser Import verarbeitet worden?
	  */
	@Override
	public void setI_IsImported (java.lang.String I_IsImported)
	{

		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	/** Get Importiert.
		@return Ist dieser Import verarbeitet worden?
	  */
	@Override
	public java.lang.String getI_IsImported () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_IsImported);
	}

	/** Set Import Replenishment.
		@param I_Replenish_ID Import Replenishment	  */
	@Override
	public void setI_Replenish_ID (int I_Replenish_ID)
	{
		if (I_Replenish_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_Replenish_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_Replenish_ID, Integer.valueOf(I_Replenish_ID));
	}

	/** Get Import Replenishment.
		@return Import Replenishment	  */
	@Override
	public int getI_Replenish_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_Replenish_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Maximalmenge.
		@param Level_Max 
		Maximaler Bestand für dieses Produkt
	  */
	@Override
	public void setLevel_Max (java.math.BigDecimal Level_Max)
	{
		set_Value (COLUMNNAME_Level_Max, Level_Max);
	}

	/** Get Maximalmenge.
		@return Maximaler Bestand für dieses Produkt
	  */
	@Override
	public java.math.BigDecimal getLevel_Max () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Level_Max);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Mindestmenge.
		@param Level_Min 
		Minimaler Bestand für dieses Produkt
	  */
	@Override
	public void setLevel_Min (java.math.BigDecimal Level_Min)
	{
		set_Value (COLUMNNAME_Level_Min, Level_Min);
	}

	/** Get Mindestmenge.
		@return Minimaler Bestand für dieses Produkt
	  */
	@Override
	public java.math.BigDecimal getLevel_Min () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Level_Min);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Lagerort-Schlüssel.
		@param LocatorValue 
		Suchschlüssel für den Lagerort im Lager
	  */
	@Override
	public void setLocatorValue (java.lang.String LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, LocatorValue);
	}

	/** Get Lagerort-Schlüssel.
		@return Suchschlüssel für den Lagerort im Lager
	  */
	@Override
	public java.lang.String getLocatorValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LocatorValue);
	}

	@Override
	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class);
	}

	@Override
	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator)
	{
		set_ValueFromPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class, M_Locator);
	}

	/** Set Lagerort.
		@param M_Locator_ID 
		Lagerort im Lager
	  */
	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Lagerort.
		@return Lagerort im Lager
	  */
	@Override
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
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

	@Override
	public org.compiere.model.I_M_Replenish getM_Replenish() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Replenish_ID, org.compiere.model.I_M_Replenish.class);
	}

	@Override
	public void setM_Replenish(org.compiere.model.I_M_Replenish M_Replenish)
	{
		set_ValueFromPO(COLUMNNAME_M_Replenish_ID, org.compiere.model.I_M_Replenish.class, M_Replenish);
	}

	/** Set M_Replenish.
		@param M_Replenish_ID M_Replenish	  */
	@Override
	public void setM_Replenish_ID (int M_Replenish_ID)
	{
		if (M_Replenish_ID < 1) 
			set_Value (COLUMNNAME_M_Replenish_ID, null);
		else 
			set_Value (COLUMNNAME_M_Replenish_ID, Integer.valueOf(M_Replenish_ID));
	}

	/** Get M_Replenish.
		@return M_Replenish	  */
	@Override
	public int getM_Replenish_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Replenish_ID);
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
	public org.compiere.model.I_M_Warehouse getM_WarehouseSource() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_WarehouseSource_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_WarehouseSource(org.compiere.model.I_M_Warehouse M_WarehouseSource)
	{
		set_ValueFromPO(COLUMNNAME_M_WarehouseSource_ID, org.compiere.model.I_M_Warehouse.class, M_WarehouseSource);
	}

	/** Set Source Warehouse.
		@param M_WarehouseSource_ID 
		Optional Warehouse to replenish from
	  */
	@Override
	public void setM_WarehouseSource_ID (int M_WarehouseSource_ID)
	{
		if (M_WarehouseSource_ID < 1) 
			set_Value (COLUMNNAME_M_WarehouseSource_ID, null);
		else 
			set_Value (COLUMNNAME_M_WarehouseSource_ID, Integer.valueOf(M_WarehouseSource_ID));
	}

	/** Get Source Warehouse.
		@return Optional Warehouse to replenish from
	  */
	@Override
	public int getM_WarehouseSource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_WarehouseSource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Organisations-Schlüssel.
		@param OrgValue 
		Suchschlüssel der Organisation
	  */
	@Override
	public void setOrgValue (java.lang.String OrgValue)
	{
		set_Value (COLUMNNAME_OrgValue, OrgValue);
	}

	/** Get Organisations-Schlüssel.
		@return Suchschlüssel der Organisation
	  */
	@Override
	public java.lang.String getOrgValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OrgValue);
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

	/** Set Produktschlüssel.
		@param ProductValue 
		Schlüssel des Produktes
	  */
	@Override
	public void setProductValue (java.lang.String ProductValue)
	{
		set_Value (COLUMNNAME_ProductValue, ProductValue);
	}

	/** Get Produktschlüssel.
		@return Schlüssel des Produktes
	  */
	@Override
	public java.lang.String getProductValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductValue);
	}

	/** 
	 * ReplenishType AD_Reference_ID=164
	 * Reference name: M_Replenish Type
	 */
	public static final int REPLENISHTYPE_AD_Reference_ID=164;
	/** Maximalbestand beibehalten = 2 */
	public static final String REPLENISHTYPE_MaximalbestandBeibehalten = "2";
	/** Manuell = 0 */
	public static final String REPLENISHTYPE_Manuell = "0";
	/** Bei Unterschreitung Minimalbestand = 1 */
	public static final String REPLENISHTYPE_BeiUnterschreitungMinimalbestand = "1";
	/** Custom = 9 */
	public static final String REPLENISHTYPE_Custom = "9";
	/** Zuk?nftigen Bestand sichern = 7 */
	public static final String REPLENISHTYPE_ZukNftigenBestandSichern = "7";
	/** Set Art der Wiederauffüllung.
		@param ReplenishType 
		Methode für das Nachbestellen des Produktes
	  */
	@Override
	public void setReplenishType (java.lang.String ReplenishType)
	{

		set_Value (COLUMNNAME_ReplenishType, ReplenishType);
	}

	/** Get Art der Wiederauffüllung.
		@return Methode für das Nachbestellen des Produktes
	  */
	@Override
	public java.lang.String getReplenishType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReplenishType);
	}

	/** Set Markteinführungszeit.
		@param TimeToMarket 
		Time to Market (in Wochen)
	  */
	@Override
	public void setTimeToMarket (int TimeToMarket)
	{
		set_Value (COLUMNNAME_TimeToMarket, Integer.valueOf(TimeToMarket));
	}

	/** Get Markteinführungszeit.
		@return Time to Market (in Wochen)
	  */
	@Override
	public int getTimeToMarket () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TimeToMarket);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Source Warehouse Value.
		@param WarehouseSourceValue Source Warehouse Value	  */
	@Override
	public void setWarehouseSourceValue (java.lang.String WarehouseSourceValue)
	{
		set_Value (COLUMNNAME_WarehouseSourceValue, WarehouseSourceValue);
	}

	/** Get Source Warehouse Value.
		@return Source Warehouse Value	  */
	@Override
	public java.lang.String getWarehouseSourceValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WarehouseSourceValue);
	}

	/** Set Lager-Schlüssel.
		@param WarehouseValue 
		Suchschlüssel des Lagers
	  */
	@Override
	public void setWarehouseValue (java.lang.String WarehouseValue)
	{
		set_Value (COLUMNNAME_WarehouseValue, WarehouseValue);
	}

	/** Get Lager-Schlüssel.
		@return Suchschlüssel des Lagers
	  */
	@Override
	public java.lang.String getWarehouseValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WarehouseValue);
	}
}