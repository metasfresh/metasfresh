/** Generated Model - DO NOT CHANGE */
package de.metas.procurement.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for PMM_Balance
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PMM_Balance extends org.compiere.model.PO implements I_PMM_Balance, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -328862649L;

    /** Standard Constructor */
    public X_PMM_Balance (Properties ctx, int PMM_Balance_ID, String trxName)
    {
      super (ctx, PMM_Balance_ID, trxName);
      /** if (PMM_Balance_ID == 0)
        {
			setC_BPartner_ID (0);
			setM_HU_PI_Item_Product_ID (0);
			setM_Product_ID (0);
			setMonthDate (new Timestamp( System.currentTimeMillis() ));
			setPMM_Balance_ID (0);
			setQtyDelivered (Env.ZERO);
// 0
			setQtyOrdered (Env.ZERO);
// 0
			setQtyOrdered_TU (Env.ZERO);
// 0
			setQtyPromised (Env.ZERO);
// 0
			setQtyPromised_TU (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_PMM_Balance (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
	public de.metas.flatrate.model.I_C_Flatrate_DataEntry getC_Flatrate_DataEntry() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_DataEntry_ID, de.metas.flatrate.model.I_C_Flatrate_DataEntry.class);
	}

	@Override
	public void setC_Flatrate_DataEntry(de.metas.flatrate.model.I_C_Flatrate_DataEntry C_Flatrate_DataEntry)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_DataEntry_ID, de.metas.flatrate.model.I_C_Flatrate_DataEntry.class, C_Flatrate_DataEntry);
	}

	/** Set Abrechnungssatz.
		@param C_Flatrate_DataEntry_ID Abrechnungssatz	  */
	@Override
	public void setC_Flatrate_DataEntry_ID (int C_Flatrate_DataEntry_ID)
	{
		if (C_Flatrate_DataEntry_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_DataEntry_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_DataEntry_ID, Integer.valueOf(C_Flatrate_DataEntry_ID));
	}

	/** Get Abrechnungssatz.
		@return Abrechnungssatz	  */
	@Override
	public int getC_Flatrate_DataEntry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_DataEntry_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	public de.metas.handlingunits.model.I_M_HU_PI_Item_Product getM_HU_PI_Item_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PI_Item_Product_ID, de.metas.handlingunits.model.I_M_HU_PI_Item_Product.class);
	}

	@Override
	public void setM_HU_PI_Item_Product(de.metas.handlingunits.model.I_M_HU_PI_Item_Product M_HU_PI_Item_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PI_Item_Product_ID, de.metas.handlingunits.model.I_M_HU_PI_Item_Product.class, M_HU_PI_Item_Product);
	}

	/** Set Packvorschrift-Produkt Zuordnung.
		@param M_HU_PI_Item_Product_ID Packvorschrift-Produkt Zuordnung	  */
	@Override
	public void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Item_Product_ID, Integer.valueOf(M_HU_PI_Item_Product_ID));
	}

	/** Get Packvorschrift-Produkt Zuordnung.
		@return Packvorschrift-Produkt Zuordnung	  */
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

	/** Set Monatserster.
		@param MonthDate Monatserster	  */
	@Override
	public void setMonthDate (java.sql.Timestamp MonthDate)
	{
		set_ValueNoCheck (COLUMNNAME_MonthDate, MonthDate);
	}

	/** Get Monatserster.
		@return Monatserster	  */
	@Override
	public java.sql.Timestamp getMonthDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_MonthDate);
	}

	/** Set PMM balance.
		@param PMM_Balance_ID PMM balance	  */
	@Override
	public void setPMM_Balance_ID (int PMM_Balance_ID)
	{
		if (PMM_Balance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PMM_Balance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PMM_Balance_ID, Integer.valueOf(PMM_Balance_ID));
	}

	/** Get PMM balance.
		@return PMM balance	  */
	@Override
	public int getPMM_Balance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PMM_Balance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Gelieferte Menge.
		@param QtyDelivered 
		Gelieferte Menge
	  */
	@Override
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Gelieferte Menge.
		@return Gelieferte Menge
	  */
	@Override
	public java.math.BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
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
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
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

	/** Set Bestellte Menge (TU).
		@param QtyOrdered_TU 
		Bestellte Menge (TU)
	  */
	@Override
	public void setQtyOrdered_TU (java.math.BigDecimal QtyOrdered_TU)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOrdered_TU, QtyOrdered_TU);
	}

	/** Get Bestellte Menge (TU).
		@return Bestellte Menge (TU)
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered_TU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered_TU);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zusagbar.
		@param QtyPromised Zusagbar	  */
	@Override
	public void setQtyPromised (java.math.BigDecimal QtyPromised)
	{
		set_Value (COLUMNNAME_QtyPromised, QtyPromised);
	}

	/** Get Zusagbar.
		@return Zusagbar	  */
	@Override
	public java.math.BigDecimal getQtyPromised () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPromised);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zusagbar (TU).
		@param QtyPromised_TU Zusagbar (TU)	  */
	@Override
	public void setQtyPromised_TU (java.math.BigDecimal QtyPromised_TU)
	{
		set_ValueNoCheck (COLUMNNAME_QtyPromised_TU, QtyPromised_TU);
	}

	/** Get Zusagbar (TU).
		@return Zusagbar (TU)	  */
	@Override
	public java.math.BigDecimal getQtyPromised_TU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPromised_TU);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Wochenerster.
		@param WeekDate Wochenerster	  */
	@Override
	public void setWeekDate (java.sql.Timestamp WeekDate)
	{
		set_ValueNoCheck (COLUMNNAME_WeekDate, WeekDate);
	}

	/** Get Wochenerster.
		@return Wochenerster	  */
	@Override
	public java.sql.Timestamp getWeekDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_WeekDate);
	}
}