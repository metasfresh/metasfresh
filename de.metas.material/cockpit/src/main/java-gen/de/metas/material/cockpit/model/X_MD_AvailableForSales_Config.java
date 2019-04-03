/** Generated Model - DO NOT CHANGE */
package de.metas.material.cockpit.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_AvailableForSales_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_AvailableForSales_Config extends org.compiere.model.PO implements I_MD_AvailableForSales_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1247520403L;

    /** Standard Constructor */
    public X_MD_AvailableForSales_Config (Properties ctx, int MD_AvailableForSales_Config_ID, String trxName)
    {
      super (ctx, MD_AvailableForSales_Config_ID, trxName);
      /** if (MD_AvailableForSales_Config_ID == 0)
        {
			setInsufficientQtyAvailableForSalesColor_ID (0);
			setIsFeatureActivated (true); // Y
			setMD_AvailableForSales_Config_ID (0);
			setSalesOrderLookBehindHours (0);
			setShipmentDateLookAheadHours (0);
        } */
    }

    /** Load Constructor */
    public X_MD_AvailableForSales_Config (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Color getInsufficientQtyAvailableForSalesColor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_InsufficientQtyAvailableForSalesColor_ID, org.compiere.model.I_AD_Color.class);
	}

	@Override
	public void setInsufficientQtyAvailableForSalesColor(org.compiere.model.I_AD_Color InsufficientQtyAvailableForSalesColor)
	{
		set_ValueFromPO(COLUMNNAME_InsufficientQtyAvailableForSalesColor_ID, org.compiere.model.I_AD_Color.class, InsufficientQtyAvailableForSalesColor);
	}

	/** Set InsufficientQtyAvailableForSalesColor_ID.
		@param InsufficientQtyAvailableForSalesColor_ID 
		Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.
	  */
	@Override
	public void setInsufficientQtyAvailableForSalesColor_ID (int InsufficientQtyAvailableForSalesColor_ID)
	{
		if (InsufficientQtyAvailableForSalesColor_ID < 1) 
			set_Value (COLUMNNAME_InsufficientQtyAvailableForSalesColor_ID, null);
		else 
			set_Value (COLUMNNAME_InsufficientQtyAvailableForSalesColor_ID, Integer.valueOf(InsufficientQtyAvailableForSalesColor_ID));
	}

	/** Get InsufficientQtyAvailableForSalesColor_ID.
		@return Farbe, mit der Auftragszeilen markiert werden, wenn der derzeitige Lagerbestand abzüglich absehbarer Lieferungen nicht ausreicht, um die jeweilige Auftragsposition zu bedienen.
	  */
	@Override
	public int getInsufficientQtyAvailableForSalesColor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_InsufficientQtyAvailableForSalesColor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Feature aktivtiert.
		@param IsFeatureActivated Feature aktivtiert	  */
	@Override
	public void setIsFeatureActivated (boolean IsFeatureActivated)
	{
		set_Value (COLUMNNAME_IsFeatureActivated, Boolean.valueOf(IsFeatureActivated));
	}

	/** Get Feature aktivtiert.
		@return Feature aktivtiert	  */
	@Override
	public boolean isFeatureActivated () 
	{
		Object oo = get_Value(COLUMNNAME_IsFeatureActivated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set MD_AvailableForSales_Config.
		@param MD_AvailableForSales_Config_ID MD_AvailableForSales_Config	  */
	@Override
	public void setMD_AvailableForSales_Config_ID (int MD_AvailableForSales_Config_ID)
	{
		if (MD_AvailableForSales_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_AvailableForSales_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_AvailableForSales_Config_ID, Integer.valueOf(MD_AvailableForSales_Config_ID));
	}

	/** Get MD_AvailableForSales_Config.
		@return MD_AvailableForSales_Config	  */
	@Override
	public int getMD_AvailableForSales_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_AvailableForSales_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rückschauinterval Auftragspositionen in Bearb. (Std).
		@param SalesOrderLookBehindHours 
		Interval bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.
	  */
	@Override
	public void setSalesOrderLookBehindHours (int SalesOrderLookBehindHours)
	{
		set_Value (COLUMNNAME_SalesOrderLookBehindHours, Integer.valueOf(SalesOrderLookBehindHours));
	}

	/** Get Rückschauinterval Auftragspositionen in Bearb. (Std).
		@return Interval bis zum Bereitstellungsdatum der aktuellen Auftrags, innerhalb dessen andere noch nicht fertig gestellte Auftragspositionen berücksichtigt werden sollen. Sollte abhängig von der üblichen Auftragsbearbeitungsdauer gesetzt werden.
	  */
	@Override
	public int getSalesOrderLookBehindHours () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesOrderLookBehindHours);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Vorausschauinterval zu gepl. Lieferungen (Std).
		@param ShipmentDateLookAheadHours 
		Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.
	  */
	@Override
	public void setShipmentDateLookAheadHours (int ShipmentDateLookAheadHours)
	{
		set_Value (COLUMNNAME_ShipmentDateLookAheadHours, Integer.valueOf(ShipmentDateLookAheadHours));
	}

	/** Get Vorausschauinterval zu gepl. Lieferungen (Std).
		@return Interval ab Bereitstellungsdatum des aktuellen Auftrags, innerhalb dessen geplante Lieferungen berücktsichtigt werden sollen. Sollte abhängig vom üblichen Zulaufinterval gesetzt werden.
	  */
	@Override
	public int getShipmentDateLookAheadHours () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShipmentDateLookAheadHours);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}