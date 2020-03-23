/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Commission_Instance
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Commission_Instance extends org.compiere.model.PO implements I_C_Commission_Instance, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1850947292L;

    /** Standard Constructor */
    public X_C_Commission_Instance (Properties ctx, int C_Commission_Instance_ID, String trxName)
    {
      super (ctx, C_Commission_Instance_ID, trxName);
      /** if (C_Commission_Instance_ID == 0)
        {
			setBill_BPartner_ID (0);
			setC_Commission_Instance_ID (0);
			setCommissionDate (new Timestamp( System.currentTimeMillis() ));
			setCommissionTrigger_Type (null);
			setMostRecentTriggerTimestamp (new Timestamp( System.currentTimeMillis() ));
			setM_Product_Order_ID (0);
			setPointsBase_Forecasted (BigDecimal.ZERO);
			setPointsBase_Invoiceable (BigDecimal.ZERO);
			setPointsBase_Invoiced (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_Commission_Instance (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Rechnungspartner.
		@param Bill_BPartner_ID 
		Geschäftspartner für die Rechnungsstellung
	  */
	@Override
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
	}

	/** Get Rechnungspartner.
		@return Geschäftspartner für die Rechnungsstellung
	  */
	@Override
	public int getBill_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Provisionsvorgang.
		@param C_Commission_Instance_ID Provisionsvorgang	  */
	@Override
	public void setC_Commission_Instance_ID (int C_Commission_Instance_ID)
	{
		if (C_Commission_Instance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, Integer.valueOf(C_Commission_Instance_ID));
	}

	/** Get Provisionsvorgang.
		@return Provisionsvorgang	  */
	@Override
	public int getC_Commission_Instance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Commission_Instance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rechnungskandidat.
		@param C_Invoice_Candidate_ID Rechnungskandidat	  */
	@Override
	public void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID)
	{
		if (C_Invoice_Candidate_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, Integer.valueOf(C_Invoice_Candidate_ID));
	}

	/** Get Rechnungskandidat.
		@return Rechnungskandidat	  */
	@Override
	public int getC_Invoice_Candidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	/** Set Rechnung.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Rechnung.
		@return Invoice Identifier
	  */
	@Override
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class);
	}

	@Override
	public void setC_InvoiceLine(org.compiere.model.I_C_InvoiceLine C_InvoiceLine)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class, C_InvoiceLine);
	}

	/** Set Rechnungsposition.
		@param C_InvoiceLine_ID 
		Rechnungszeile
	  */
	@Override
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Rechnungsposition.
		@return Rechnungszeile
	  */
	@Override
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datum.
		@param CommissionDate 
		Belegdatum des Provisionsauslösers
	  */
	@Override
	public void setCommissionDate (java.sql.Timestamp CommissionDate)
	{
		set_Value (COLUMNNAME_CommissionDate, CommissionDate);
	}

	/** Get Datum.
		@return Belegdatum des Provisionsauslösers
	  */
	@Override
	public java.sql.Timestamp getCommissionDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_CommissionDate);
	}

	/** 
	 * CommissionTrigger_Type AD_Reference_ID=541115
	 * Reference name: CommissionTrigger_Type
	 */
	public static final int COMMISSIONTRIGGER_TYPE_AD_Reference_ID=541115;
	/** InvoiceCandidate = InvoiceCandidate */
	public static final String COMMISSIONTRIGGER_TYPE_InvoiceCandidate = "InvoiceCandidate";
	/** CustomerInvoice = CustomerInvoice */
	public static final String COMMISSIONTRIGGER_TYPE_CustomerInvoice = "CustomerInvoice";
	/** CustomerCreditmemo = CustomerCreditmemo */
	public static final String COMMISSIONTRIGGER_TYPE_CustomerCreditmemo = "CustomerCreditmemo";
	/** Set Provisionsauslöser.
		@param CommissionTrigger_Type 
		Art des Dokuments, dass den Provisionsvorgang ausgelöst hat
	  */
	@Override
	public void setCommissionTrigger_Type (java.lang.String CommissionTrigger_Type)
	{

		set_Value (COLUMNNAME_CommissionTrigger_Type, CommissionTrigger_Type);
	}

	/** Get Provisionsauslöser.
		@return Art des Dokuments, dass den Provisionsvorgang ausgelöst hat
	  */
	@Override
	public java.lang.String getCommissionTrigger_Type () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CommissionTrigger_Type);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	/** Set Auftrag.
		@param C_Order_ID 
		Auftrag
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Auftrag.
		@return Auftrag
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Provisionsauslöser Zeitpunkt.
		@param MostRecentTriggerTimestamp Provisionsauslöser Zeitpunkt	  */
	@Override
	public void setMostRecentTriggerTimestamp (java.sql.Timestamp MostRecentTriggerTimestamp)
	{
		set_Value (COLUMNNAME_MostRecentTriggerTimestamp, MostRecentTriggerTimestamp);
	}

	/** Get Provisionsauslöser Zeitpunkt.
		@return Provisionsauslöser Zeitpunkt	  */
	@Override
	public java.sql.Timestamp getMostRecentTriggerTimestamp () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_MostRecentTriggerTimestamp);
	}

	/** Set Beauftragtes Produkt.
		@param M_Product_Order_ID Beauftragtes Produkt	  */
	@Override
	public void setM_Product_Order_ID (int M_Product_Order_ID)
	{
		if (M_Product_Order_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Order_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Order_ID, Integer.valueOf(M_Product_Order_ID));
	}

	/** Get Beauftragtes Produkt.
		@return Beauftragtes Produkt	  */
	@Override
	public int getM_Product_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beauftragte Basispunktzahl.
		@param PointsBase_Forecasted Beauftragte Basispunktzahl	  */
	@Override
	public void setPointsBase_Forecasted (java.math.BigDecimal PointsBase_Forecasted)
	{
		set_Value (COLUMNNAME_PointsBase_Forecasted, PointsBase_Forecasted);
	}

	/** Get Beauftragte Basispunktzahl.
		@return Beauftragte Basispunktzahl	  */
	@Override
	public java.math.BigDecimal getPointsBase_Forecasted () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsBase_Forecasted);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Fakturierbare Basispunktzahl.
		@param PointsBase_Invoiceable Fakturierbare Basispunktzahl	  */
	@Override
	public void setPointsBase_Invoiceable (java.math.BigDecimal PointsBase_Invoiceable)
	{
		set_Value (COLUMNNAME_PointsBase_Invoiceable, PointsBase_Invoiceable);
	}

	/** Get Fakturierbare Basispunktzahl.
		@return Fakturierbare Basispunktzahl	  */
	@Override
	public java.math.BigDecimal getPointsBase_Invoiceable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsBase_Invoiceable);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Fakturierte Basispunktzahl.
		@param PointsBase_Invoiced Fakturierte Basispunktzahl	  */
	@Override
	public void setPointsBase_Invoiced (java.math.BigDecimal PointsBase_Invoiced)
	{
		set_Value (COLUMNNAME_PointsBase_Invoiced, PointsBase_Invoiced);
	}

	/** Get Fakturierte Basispunktzahl.
		@return Fakturierte Basispunktzahl	  */
	@Override
	public java.math.BigDecimal getPointsBase_Invoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsBase_Invoiced);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Referenz.
		@param POReference 
		Referenz-Nummer des Kunden
	  */
	@Override
	public void setPOReference (java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	/** Get Referenz.
		@return Referenz-Nummer des Kunden
	  */
	@Override
	public java.lang.String getPOReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}
}