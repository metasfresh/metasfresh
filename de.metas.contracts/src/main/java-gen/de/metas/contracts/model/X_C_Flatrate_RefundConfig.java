/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_RefundConfig
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Flatrate_RefundConfig extends org.compiere.model.PO implements I_C_Flatrate_RefundConfig, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1849446722L;

    /** Standard Constructor */
    public X_C_Flatrate_RefundConfig (Properties ctx, int C_Flatrate_RefundConfig_ID, String trxName)
    {
      super (ctx, C_Flatrate_RefundConfig_ID, trxName);
      /** if (C_Flatrate_RefundConfig_ID == 0)
        {
			setC_Flatrate_Conditions_ID (0);
			setC_Flatrate_RefundConfig_ID (0);
			setC_InvoiceSchedule_ID (0);
			setM_Product_ID (0);
			setPercent (BigDecimal.ZERO);
			setRefundInvoiceType (null); // Invoice
        } */
    }

    /** Load Constructor */
    public X_C_Flatrate_RefundConfig (Properties ctx, ResultSet rs, String trxName)
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

	/** Set C_Flatrate_RefundConfig.
		@param C_Flatrate_RefundConfig_ID C_Flatrate_RefundConfig	  */
	@Override
	public void setC_Flatrate_RefundConfig_ID (int C_Flatrate_RefundConfig_ID)
	{
		if (C_Flatrate_RefundConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_RefundConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_RefundConfig_ID, Integer.valueOf(C_Flatrate_RefundConfig_ID));
	}

	/** Get C_Flatrate_RefundConfig.
		@return C_Flatrate_RefundConfig	  */
	@Override
	public int getC_Flatrate_RefundConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_RefundConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class);
	}

	@Override
	public void setC_InvoiceSchedule(org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class, C_InvoiceSchedule);
	}

	/** Set Terminplan Rechnung.
		@param C_InvoiceSchedule_ID 
		Plan für die Rechnungsstellung
	  */
	@Override
	public void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID)
	{
		if (C_InvoiceSchedule_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, Integer.valueOf(C_InvoiceSchedule_ID));
	}

	/** Get Terminplan Rechnung.
		@return Plan für die Rechnungsstellung
	  */
	@Override
	public int getC_InvoiceSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceSchedule_ID);
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

	/** Set Percent.
		@param Percent 
		Percentage
	  */
	@Override
	public void setPercent (java.math.BigDecimal Percent)
	{
		set_Value (COLUMNNAME_Percent, Percent);
	}

	/** Get Percent.
		@return Percentage
	  */
	@Override
	public java.math.BigDecimal getPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Percent);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** 
	 * RefundInvoiceType AD_Reference_ID=540863
	 * Reference name: RefundInvoiceType
	 */
	public static final int REFUNDINVOICETYPE_AD_Reference_ID=540863;
	/** Invoice = Invoice */
	public static final String REFUNDINVOICETYPE_Invoice = "Invoice";
	/** Creditmemo = Creditmemo */
	public static final String REFUNDINVOICETYPE_Creditmemo = "Creditmemo";
	/** Set Rückvergütung per.
		@param RefundInvoiceType Rückvergütung per	  */
	@Override
	public void setRefundInvoiceType (java.lang.String RefundInvoiceType)
	{

		set_Value (COLUMNNAME_RefundInvoiceType, RefundInvoiceType);
	}

	/** Get Rückvergütung per.
		@return Rückvergütung per	  */
	@Override
	public java.lang.String getRefundInvoiceType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RefundInvoiceType);
	}
}