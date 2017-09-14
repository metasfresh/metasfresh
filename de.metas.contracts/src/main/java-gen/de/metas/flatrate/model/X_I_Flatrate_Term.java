/** Generated Model - DO NOT CHANGE */
package de.metas.flatrate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_Flatrate_Term
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_Flatrate_Term extends org.compiere.model.PO implements I_I_Flatrate_Term, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -172093638L;

    /** Standard Constructor */
    public X_I_Flatrate_Term (Properties ctx, int I_Flatrate_Term_ID, String trxName)
    {
      super (ctx, I_Flatrate_Term_ID, trxName);
      /** if (I_Flatrate_Term_ID == 0)
        {
			setI_Flatrate_Term_ID (0);
			setI_IsImported (null); // N
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_I_Flatrate_Term (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Geschäftspartner-Schlüssel.
		@param BPartnerValue 
		Key of the Business Partner
	  */
	@Override
	public void setBPartnerValue (java.lang.String BPartnerValue)
	{
		set_Value (COLUMNNAME_BPartnerValue, BPartnerValue);
	}

	/** Get Geschäftspartner-Schlüssel.
		@return Key of the Business Partner
	  */
	@Override
	public java.lang.String getBPartnerValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerValue);
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
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, Integer.valueOf(C_Flatrate_Conditions_ID));
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

	/** Set Vertragsbedingungen Key.
		@param C_Flatrate_Conditions_Value Vertragsbedingungen Key	  */
	@Override
	public void setC_Flatrate_Conditions_Value (java.lang.String C_Flatrate_Conditions_Value)
	{
		set_Value (COLUMNNAME_C_Flatrate_Conditions_Value, C_Flatrate_Conditions_Value);
	}

	/** Get Vertragsbedingungen Key.
		@return Vertragsbedingungen Key	  */
	@Override
	public java.lang.String getC_Flatrate_Conditions_Value () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_C_Flatrate_Conditions_Value);
	}

	@Override
	public de.metas.flatrate.model.I_C_Flatrate_Term getC_Flatrate_Term() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.flatrate.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_Flatrate_Term(de.metas.flatrate.model.I_C_Flatrate_Term C_Flatrate_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.flatrate.model.I_C_Flatrate_Term.class, C_Flatrate_Term);
	}

	/** Set Pauschale - Vertragsperiode.
		@param C_Flatrate_Term_ID Pauschale - Vertragsperiode	  */
	@Override
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, Integer.valueOf(C_Flatrate_Term_ID));
	}

	/** Get Pauschale - Vertragsperiode.
		@return Pauschale - Vertragsperiode	  */
	@Override
	public int getC_Flatrate_Term_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Term_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getDropShip_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DropShip_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setDropShip_BPartner(org.compiere.model.I_C_BPartner DropShip_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_DropShip_BPartner_ID, org.compiere.model.I_C_BPartner.class, DropShip_BPartner);
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

	/** Set Lieferempfänger Key.
		@param DropShip_BPartner_Value 
		Business Partner to ship to
	  */
	@Override
	public void setDropShip_BPartner_Value (java.lang.String DropShip_BPartner_Value)
	{
		set_Value (COLUMNNAME_DropShip_BPartner_Value, DropShip_BPartner_Value);
	}

	/** Get Lieferempfänger Key.
		@return Business Partner to ship to
	  */
	@Override
	public java.lang.String getDropShip_BPartner_Value () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DropShip_BPartner_Value);
	}

	/** Set Enddatum.
		@param EndDate 
		Last effective date (inclusive)
	  */
	@Override
	public void setEndDate (java.sql.Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get Enddatum.
		@return Last effective date (inclusive)
	  */
	@Override
	public java.sql.Timestamp getEndDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EndDate);
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

	/** Set Contract import.
		@param I_Flatrate_Term_ID Contract import	  */
	@Override
	public void setI_Flatrate_Term_ID (int I_Flatrate_Term_ID)
	{
		if (I_Flatrate_Term_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_Flatrate_Term_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_Flatrate_Term_ID, Integer.valueOf(I_Flatrate_Term_ID));
	}

	/** Get Contract import.
		@return Contract import	  */
	@Override
	public int getI_Flatrate_Term_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_Flatrate_Term_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Master End Date.
		@param MasterEndDate Master End Date	  */
	@Override
	public void setMasterEndDate (java.sql.Timestamp MasterEndDate)
	{
		set_Value (COLUMNNAME_MasterEndDate, MasterEndDate);
	}

	/** Get Master End Date.
		@return Master End Date	  */
	@Override
	public java.sql.Timestamp getMasterEndDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_MasterEndDate);
	}

	/** Set Master Start Date.
		@param MasterStartDate Master Start Date	  */
	@Override
	public void setMasterStartDate (java.sql.Timestamp MasterStartDate)
	{
		set_Value (COLUMNNAME_MasterStartDate, MasterStartDate);
	}

	/** Get Master Start Date.
		@return Master Start Date	  */
	@Override
	public java.sql.Timestamp getMasterStartDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_MasterStartDate);
	}

	/** Set Preis.
		@param Price 
		Preis
	  */
	@Override
	public void setPrice (java.math.BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get Preis.
		@return Preis
	  */
	@Override
	public java.math.BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set Menge.
		@param Qty 
		Menge
	  */
	@Override
	public void setQty(BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	@Override
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Anfangsdatum.
		@param StartDate 
		First effective day (inclusive)
	  */
	@Override
	public void setStartDate (java.sql.Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Anfangsdatum.
		@return First effective day (inclusive)
	  */
	@Override
	public java.sql.Timestamp getStartDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_StartDate);
	}
}