/** Generated Model - DO NOT CHANGE */
package de.metas.procurement.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PMM_WeekReport_Event
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PMM_WeekReport_Event extends org.compiere.model.PO implements I_PMM_WeekReport_Event, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1153422858L;

    /** Standard Constructor */
    public X_PMM_WeekReport_Event (Properties ctx, int PMM_WeekReport_Event_ID, String trxName)
    {
      super (ctx, PMM_WeekReport_Event_ID, trxName);
      /** if (PMM_WeekReport_Event_ID == 0)
        {
			setPMM_WeekReport_Event_ID (0);
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_PMM_WeekReport_Event (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Issue getAD_Issue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	/** Set System-Problem.
		@param AD_Issue_ID 
		Automatically created or manually entered System Issue
	  */
	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	/** Get System-Problem.
		@return Automatically created or manually entered System Issue
	  */
	@Override
	public int getAD_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Fehlermeldung.
		@param ErrorMsg Fehlermeldung	  */
	@Override
	public void setErrorMsg (java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Fehlermeldung.
		@return Fehlermeldung	  */
	@Override
	public java.lang.String getErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set Event UUID.
		@param Event_UUID Event UUID	  */
	@Override
	public void setEvent_UUID (java.lang.String Event_UUID)
	{
		set_ValueNoCheck (COLUMNNAME_Event_UUID, Event_UUID);
	}

	/** Get Event UUID.
		@return Event UUID	  */
	@Override
	public java.lang.String getEvent_UUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Event_UUID);
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
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
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

	/** Set Packvorschrift.
		@param M_HU_PI_Item_Product_ID Packvorschrift	  */
	@Override
	public void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, Integer.valueOf(M_HU_PI_Item_Product_ID));
	}

	/** Get Packvorschrift.
		@return Packvorschrift	  */
	@Override
	public int getM_HU_PI_Item_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PI_Item_Product_ID);
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
	public de.metas.procurement.base.model.I_PMM_Product getPMM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PMM_Product_ID, de.metas.procurement.base.model.I_PMM_Product.class);
	}

	@Override
	public void setPMM_Product(de.metas.procurement.base.model.I_PMM_Product PMM_Product)
	{
		set_ValueFromPO(COLUMNNAME_PMM_Product_ID, de.metas.procurement.base.model.I_PMM_Product.class, PMM_Product);
	}

	/** Set Lieferprodukt.
		@param PMM_Product_ID Lieferprodukt	  */
	@Override
	public void setPMM_Product_ID (int PMM_Product_ID)
	{
		if (PMM_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PMM_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PMM_Product_ID, Integer.valueOf(PMM_Product_ID));
	}

	/** Get Lieferprodukt.
		@return Lieferprodukt	  */
	@Override
	public int getPMM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PMM_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * PMM_Trend AD_Reference_ID=540648
	 * Reference name: PMM_Trend
	 */
	public static final int PMM_TREND_AD_Reference_ID=540648;
	/** Up = U */
	public static final String PMM_TREND_Up = "U";
	/** Down = D */
	public static final String PMM_TREND_Down = "D";
	/** Same = E */
	public static final String PMM_TREND_Same = "E";
	/** Zero = Z */
	public static final String PMM_TREND_Zero = "Z";
	/** Set Trend.
		@param PMM_Trend Trend	  */
	@Override
	public void setPMM_Trend (java.lang.String PMM_Trend)
	{

		set_Value (COLUMNNAME_PMM_Trend, PMM_Trend);
	}

	/** Get Trend.
		@return Trend	  */
	@Override
	public java.lang.String getPMM_Trend () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PMM_Trend);
	}

	@Override
	public de.metas.procurement.base.model.I_PMM_Week getPMM_Week() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PMM_Week_ID, de.metas.procurement.base.model.I_PMM_Week.class);
	}

	@Override
	public void setPMM_Week(de.metas.procurement.base.model.I_PMM_Week PMM_Week)
	{
		set_ValueFromPO(COLUMNNAME_PMM_Week_ID, de.metas.procurement.base.model.I_PMM_Week.class, PMM_Week);
	}

	/** Set Procurement Week.
		@param PMM_Week_ID Procurement Week	  */
	@Override
	public void setPMM_Week_ID (int PMM_Week_ID)
	{
		if (PMM_Week_ID < 1) 
			set_Value (COLUMNNAME_PMM_Week_ID, null);
		else 
			set_Value (COLUMNNAME_PMM_Week_ID, Integer.valueOf(PMM_Week_ID));
	}

	/** Get Procurement Week.
		@return Procurement Week	  */
	@Override
	public int getPMM_Week_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PMM_Week_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Week report event.
		@param PMM_WeekReport_Event_ID Week report event	  */
	@Override
	public void setPMM_WeekReport_Event_ID (int PMM_WeekReport_Event_ID)
	{
		if (PMM_WeekReport_Event_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PMM_WeekReport_Event_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PMM_WeekReport_Event_ID, Integer.valueOf(PMM_WeekReport_Event_ID));
	}

	/** Get Week report event.
		@return Week report event	  */
	@Override
	public int getPMM_WeekReport_Event_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PMM_WeekReport_Event_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Wochenerster.
		@param WeekDate Wochenerster	  */
	@Override
	public void setWeekDate (java.sql.Timestamp WeekDate)
	{
		set_Value (COLUMNNAME_WeekDate, WeekDate);
	}

	/** Get Wochenerster.
		@return Wochenerster	  */
	@Override
	public java.sql.Timestamp getWeekDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_WeekDate);
	}
}