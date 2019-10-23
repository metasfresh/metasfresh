/** Generated Model - DO NOT CHANGE */
package de.metas.invoicecandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Candidate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Invoice_Candidate extends org.compiere.model.PO implements I_C_Invoice_Candidate, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1899380101L;

    /** Standard Constructor */
    public X_C_Invoice_Candidate (Properties ctx, int C_Invoice_Candidate_ID, String trxName)
    {
      super (ctx, C_Invoice_Candidate_ID, trxName);
      /** if (C_Invoice_Candidate_ID == 0)
        {
			setApprovalForInvoicing (false); // N
			setBill_BPartner_ID (0);
			setBill_Location_ID (0);
			setC_ILCandHandler_ID (0);
			setC_Invoice_Candidate_ID (0);
			setC_Tax_ID (0);
			setDiscount (BigDecimal.ZERO);
			setInvoicableQtyBasedOn (null); // Nominal
			setInvoiceRule (null);
			setIsEdiRecipient (false); // @SQL=SELECT COALESCE( (SELECT IsEDIRecipient FROM C_BPartner where C_BPartner_ID = @Bill_BPartner_ID/-1@), 'N')
			setIsError (false); // N
			setIsFreightCost (false); // N
			setIsGroupCompensationLine (false); // N
			setIsInOutApprovedForInvoicing (false); // N
			setIsManual (false); // N
			setIsPackagingMaterial (false); // N
			setIsPrinted (true); // Y
			setIsSOTrx (true); // Y
			setIsTaxIncluded (false); // N
			setIsToClear (false); // N
			setProcessed (false); // N
			setProcessed_Calc (false); // N
			setQtyOrdered (BigDecimal.ZERO);
			setQtyToInvoice (BigDecimal.ZERO); // 0
			setQtyToInvoiceBeforeDiscount (BigDecimal.ZERO); // 0
			setRecord_ID (0);
			setSplitAmt (BigDecimal.ZERO); // 0
        } */
    }

    /** Load Constructor */
    public X_C_Invoice_Candidate (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Statusmeldung.
		@param AD_Note_ID 
		System-Nachricht
	  */
	@Override
	public void setAD_Note_ID (int AD_Note_ID)
	{
		if (AD_Note_ID < 1) 
			set_Value (COLUMNNAME_AD_Note_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Note_ID, Integer.valueOf(AD_Note_ID));
	}

	/** Get Statusmeldung.
		@return System-Nachricht
	  */
	@Override
	public int getAD_Note_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Note_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Betreuer.
		@param AD_User_InCharge_ID 
		Person, die bei einem fachlichen Problem vom System informiert wird.
	  */
	@Override
	public void setAD_User_InCharge_ID (int AD_User_InCharge_ID)
	{
		if (AD_User_InCharge_ID < 1) 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, Integer.valueOf(AD_User_InCharge_ID));
	}

	/** Get Betreuer.
		@return Person, die bei einem fachlichen Problem vom System informiert wird.
	  */
	@Override
	public int getAD_User_InCharge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_InCharge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Freigabe zur Fakturierung.
		@param ApprovalForInvoicing Freigabe zur Fakturierung	  */
	@Override
	public void setApprovalForInvoicing (boolean ApprovalForInvoicing)
	{
		set_Value (COLUMNNAME_ApprovalForInvoicing, Boolean.valueOf(ApprovalForInvoicing));
	}

	/** Get Freigabe zur Fakturierung.
		@return Freigabe zur Fakturierung	  */
	@Override
	public boolean isApprovalForInvoicing () 
	{
		Object oo = get_Value(COLUMNNAME_ApprovalForInvoicing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rechnungspartner.
		@param Bill_BPartner_ID 
		Geschäftspartner für die Rechnungsstellung
	  */
	@Override
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
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

	/** Set Name Rechnungspartner.
		@param Bill_BPartner_Name Name Rechnungspartner	  */
	@Override
	public void setBill_BPartner_Name (java.lang.String Bill_BPartner_Name)
	{
		throw new IllegalArgumentException ("Bill_BPartner_Name is virtual column");	}

	/** Get Name Rechnungspartner.
		@return Name Rechnungspartner	  */
	@Override
	public java.lang.String getBill_BPartner_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Bill_BPartner_Name);
	}

	/** Set Rechnungsstandort.
		@param Bill_Location_ID 
		Standort des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_Location_ID (int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_ID, Integer.valueOf(Bill_Location_ID));
	}

	/** Get Rechnungsstandort.
		@return Standort des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rechungsadresse abw..
		@param Bill_Location_Override_ID 
		Standort des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_Location_Override_ID (int Bill_Location_Override_ID)
	{
		if (Bill_Location_Override_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_Override_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_Override_ID, Integer.valueOf(Bill_Location_Override_ID));
	}

	/** Get Rechungsadresse abw..
		@return Standort des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_Location_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_Location_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rechnungskontakt.
		@param Bill_User_ID 
		Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_User_ID (int Bill_User_ID)
	{
		if (Bill_User_ID < 1) 
			set_Value (COLUMNNAME_Bill_User_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_User_ID, Integer.valueOf(Bill_User_ID));
	}

	/** Get Rechnungskontakt.
		@return Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rechnungskontakt abw..
		@param Bill_User_ID_Override_ID Rechnungskontakt abw.	  */
	@Override
	public void setBill_User_ID_Override_ID (int Bill_User_ID_Override_ID)
	{
		if (Bill_User_ID_Override_ID < 1) 
			set_Value (COLUMNNAME_Bill_User_ID_Override_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_User_ID_Override_ID, Integer.valueOf(Bill_User_ID_Override_ID));
	}

	/** Get Rechnungskontakt abw..
		@return Rechnungskontakt abw.	  */
	@Override
	public int getBill_User_ID_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_User_ID_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kostenstelle.
		@param C_Activity_ID 
		Kostenstelle
	  */
	@Override
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Kostenstelle.
		@return Kostenstelle
	  */
	@Override
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Vertriebspartner.
		@param C_BPartner_SalesRep_ID Vertriebspartner	  */
	@Override
	public void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID)
	{
		if (C_BPartner_SalesRep_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_SalesRep_ID, Integer.valueOf(C_BPartner_SalesRep_ID));
	}

	/** Get Vertriebspartner.
		@return Vertriebspartner	  */
	@Override
	public int getC_BPartner_SalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kosten.
		@param C_Charge_ID 
		Zusätzliche Kosten
	  */
	@Override
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
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

	/** Set Kursart.
		@param C_ConversionType_ID 
		Kursart
	  */
	@Override
	public void setC_ConversionType_ID (int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
	}

	/** Get Kursart.
		@return Kursart
	  */
	@Override
	public int getC_ConversionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConversionType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rechnungs-Belegart.
		@param C_DocTypeInvoice_ID 
		Document type used for invoices generated from this sales document
	  */
	@Override
	public void setC_DocTypeInvoice_ID (int C_DocTypeInvoice_ID)
	{
		if (C_DocTypeInvoice_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeInvoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeInvoice_ID, Integer.valueOf(C_DocTypeInvoice_ID));
	}

	/** Get Rechnungs-Belegart.
		@return Document type used for invoices generated from this sales document
	  */
	@Override
	public int getC_DocTypeInvoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeInvoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.invoicecandidate.model.I_C_ILCandHandler getC_ILCandHandler()
	{
		return get_ValueAsPO(COLUMNNAME_C_ILCandHandler_ID, de.metas.invoicecandidate.model.I_C_ILCandHandler.class);
	}

	@Override
	public void setC_ILCandHandler(de.metas.invoicecandidate.model.I_C_ILCandHandler C_ILCandHandler)
	{
		set_ValueFromPO(COLUMNNAME_C_ILCandHandler_ID, de.metas.invoicecandidate.model.I_C_ILCandHandler.class, C_ILCandHandler);
	}

	/** Set Rechnungskandidaten-Controller.
		@param C_ILCandHandler_ID Rechnungskandidaten-Controller	  */
	@Override
	public void setC_ILCandHandler_ID (int C_ILCandHandler_ID)
	{
		if (C_ILCandHandler_ID < 1) 
			set_Value (COLUMNNAME_C_ILCandHandler_ID, null);
		else 
			set_Value (COLUMNNAME_C_ILCandHandler_ID, Integer.valueOf(C_ILCandHandler_ID));
	}

	/** Get Rechnungskandidaten-Controller.
		@return Rechnungskandidaten-Controller	  */
	@Override
	public int getC_ILCandHandler_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ILCandHandler_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg getC_Invoice_Candidate_Agg()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_Agg_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg.class);
	}

	@Override
	public void setC_Invoice_Candidate_Agg(de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg C_Invoice_Candidate_Agg)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_Agg_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg.class, C_Invoice_Candidate_Agg);
	}

	/** Set Aggregator.
		@param C_Invoice_Candidate_Agg_ID 
		Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	  */
	@Override
	public void setC_Invoice_Candidate_Agg_ID (int C_Invoice_Candidate_Agg_ID)
	{
		if (C_Invoice_Candidate_Agg_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Agg_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Agg_ID, Integer.valueOf(C_Invoice_Candidate_Agg_ID));
	}

	/** Get Aggregator.
		@return Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	  */
	@Override
	public int getC_Invoice_Candidate_Agg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_Agg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation getC_Invoice_Candidate_HeaderAggregation_Effective()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Effective_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class);
	}

	@Override
	public void setC_Invoice_Candidate_HeaderAggregation_Effective(de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation C_Invoice_Candidate_HeaderAggregation_Effective)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Effective_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class, C_Invoice_Candidate_HeaderAggregation_Effective);
	}

	/** Set Abrechnungsgruppe eff..
		@param C_Invoice_Candidate_HeaderAggregation_Effective_ID Abrechnungsgruppe eff.	  */
	@Override
	public void setC_Invoice_Candidate_HeaderAggregation_Effective_ID (int C_Invoice_Candidate_HeaderAggregation_Effective_ID)
	{
		if (C_Invoice_Candidate_HeaderAggregation_Effective_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Effective_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Effective_ID, Integer.valueOf(C_Invoice_Candidate_HeaderAggregation_Effective_ID));
	}

	/** Get Abrechnungsgruppe eff..
		@return Abrechnungsgruppe eff.	  */
	@Override
	public int getC_Invoice_Candidate_HeaderAggregation_Effective_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Effective_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation getC_Invoice_Candidate_HeaderAggregation()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class);
	}

	@Override
	public void setC_Invoice_Candidate_HeaderAggregation(de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation C_Invoice_Candidate_HeaderAggregation)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class, C_Invoice_Candidate_HeaderAggregation);
	}

	/** Set Abrechnungsgruppe.
		@param C_Invoice_Candidate_HeaderAggregation_ID Abrechnungsgruppe	  */
	@Override
	public void setC_Invoice_Candidate_HeaderAggregation_ID (int C_Invoice_Candidate_HeaderAggregation_ID)
	{
		if (C_Invoice_Candidate_HeaderAggregation_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, Integer.valueOf(C_Invoice_Candidate_HeaderAggregation_ID));
	}

	/** Get Abrechnungsgruppe.
		@return Abrechnungsgruppe	  */
	@Override
	public int getC_Invoice_Candidate_HeaderAggregation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation getC_Invoice_Candidate_HeaderAggregation_Override()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Override_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class);
	}

	@Override
	public void setC_Invoice_Candidate_HeaderAggregation_Override(de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation C_Invoice_Candidate_HeaderAggregation_Override)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Override_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class, C_Invoice_Candidate_HeaderAggregation_Override);
	}

	/** Set Abrechnungsgruppe abw..
		@param C_Invoice_Candidate_HeaderAggregation_Override_ID Abrechnungsgruppe abw.	  */
	@Override
	public void setC_Invoice_Candidate_HeaderAggregation_Override_ID (int C_Invoice_Candidate_HeaderAggregation_Override_ID)
	{
		if (C_Invoice_Candidate_HeaderAggregation_Override_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Override_ID, Integer.valueOf(C_Invoice_Candidate_HeaderAggregation_Override_ID));
	}

	/** Get Abrechnungsgruppe abw..
		@return Abrechnungsgruppe abw.	  */
	@Override
	public int getC_Invoice_Candidate_HeaderAggregation_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Override_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_ID, Integer.valueOf(C_Invoice_Candidate_ID));
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
	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule()
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
			set_ValueNoCheck (COLUMNNAME_C_InvoiceSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceSchedule_ID, Integer.valueOf(C_InvoiceSchedule_ID));
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

	/** Set Auftragspartner.
		@param C_Order_BPartner 
		The partner from C_Order
	  */
	@Override
	public void setC_Order_BPartner (int C_Order_BPartner)
	{
		throw new IllegalArgumentException ("C_Order_BPartner is virtual column");	}

	/** Get Auftragspartner.
		@return The partner from C_Order
	  */
	@Override
	public int getC_Order_BPartner () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_BPartner);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order_CompensationGroup getC_Order_CompensationGroup()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_CompensationGroup_ID, org.compiere.model.I_C_Order_CompensationGroup.class);
	}

	@Override
	public void setC_Order_CompensationGroup(org.compiere.model.I_C_Order_CompensationGroup C_Order_CompensationGroup)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_CompensationGroup_ID, org.compiere.model.I_C_Order_CompensationGroup.class, C_Order_CompensationGroup);
	}

	/** Set Order Compensation Group.
		@param C_Order_CompensationGroup_ID Order Compensation Group	  */
	@Override
	public void setC_Order_CompensationGroup_ID (int C_Order_CompensationGroup_ID)
	{
		if (C_Order_CompensationGroup_ID < 1) 
			set_Value (COLUMNNAME_C_Order_CompensationGroup_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_CompensationGroup_ID, Integer.valueOf(C_Order_CompensationGroup_ID));
	}

	/** Get Order Compensation Group.
		@return Order Compensation Group	  */
	@Override
	public int getC_Order_CompensationGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_CompensationGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
		Order
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
		@return Order
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	/** Set Auftragsposition.
		@param C_OrderLine_ID 
		Auftragsposition
	  */
	@Override
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Auftragsposition.
		@return Auftragsposition
	  */
	@Override
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zahlungsbedingung eff..
		@param C_PaymentTerm_Effective_ID Zahlungsbedingung eff.	  */
	@Override
	public void setC_PaymentTerm_Effective_ID (int C_PaymentTerm_Effective_ID)
	{
		throw new IllegalArgumentException ("C_PaymentTerm_Effective_ID is virtual column");	}

	/** Get Zahlungsbedingung eff..
		@return Zahlungsbedingung eff.	  */
	@Override
	public int getC_PaymentTerm_Effective_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_Effective_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zahlungsbedingung.
		@param C_PaymentTerm_ID 
		Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Zahlungsbedingung.
		@return Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zahlungsbedingung abw..
		@param C_PaymentTerm_Override_ID 
		Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public void setC_PaymentTerm_Override_ID (int C_PaymentTerm_Override_ID)
	{
		if (C_PaymentTerm_Override_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_Override_ID, Integer.valueOf(C_PaymentTerm_Override_ID));
	}

	/** Get Zahlungsbedingung abw..
		@return Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public int getC_PaymentTerm_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Steuer eff..
		@param C_Tax_Effective_ID Steuer eff.	  */
	@Override
	public void setC_Tax_Effective_ID (int C_Tax_Effective_ID)
	{
		throw new IllegalArgumentException ("C_Tax_Effective_ID is virtual column");	}

	/** Get Steuer eff..
		@return Steuer eff.	  */
	@Override
	public int getC_Tax_Effective_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_Effective_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Steuer.
		@param C_Tax_ID 
		Steuerart
	  */
	@Override
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Steuer.
		@return Steuerart
	  */
	@Override
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Steuer abw..
		@param C_Tax_Override_ID 
		Abweichender Steuersatz
	  */
	@Override
	public void setC_Tax_Override_ID (int C_Tax_Override_ID)
	{
		if (C_Tax_Override_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_Override_ID, Integer.valueOf(C_Tax_Override_ID));
	}

	/** Get Steuer abw..
		@return Abweichender Steuersatz
	  */
	@Override
	public int getC_Tax_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Buchungsdatum.
		@param DateAcct 
		Accounting Date
	  */
	@Override
	public void setDateAcct (java.sql.Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Buchungsdatum.
		@return Accounting Date
	  */
	@Override
	public java.sql.Timestamp getDateAcct () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Rechnungsdatum.
		@param DateInvoiced 
		Datum auf der Rechnung
	  */
	@Override
	public void setDateInvoiced (java.sql.Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Rechnungsdatum.
		@return Datum auf der Rechnung
	  */
	@Override
	public java.sql.Timestamp getDateInvoiced () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set Auftragsdatum.
		@param DateOrdered 
		Datum des Auftrags
	  */
	@Override
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	/** Get Auftragsdatum.
		@return Datum des Auftrags
	  */
	@Override
	public java.sql.Timestamp getDateOrdered () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateOrdered);
	}

	/** Set Zugesagter Termin.
		@param DatePromised 
		Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public void setDatePromised (java.sql.Timestamp DatePromised)
	{
		throw new IllegalArgumentException ("DatePromised is virtual column");	}

	/** Get Zugesagter Termin.
		@return Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public java.sql.Timestamp getDatePromised () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DatePromised);
	}

	/** Set Abrechnung ab.
		@param DateToInvoice 
		Termin ab dem die Rechnung erstellt werden darf
	  */
	@Override
	public void setDateToInvoice (java.sql.Timestamp DateToInvoice)
	{
		set_ValueNoCheck (COLUMNNAME_DateToInvoice, DateToInvoice);
	}

	/** Get Abrechnung ab.
		@return Termin ab dem die Rechnung erstellt werden darf
	  */
	@Override
	public java.sql.Timestamp getDateToInvoice () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateToInvoice);
	}

	/** Set Abrechnung ab eff..
		@param DateToInvoice_Effective 
		Enthält den Wert aus "Abrechnung ab" oder der Überschreibe-Wert aus "Abrechnung ab abw.
	  */
	@Override
	public void setDateToInvoice_Effective (java.sql.Timestamp DateToInvoice_Effective)
	{
		throw new IllegalArgumentException ("DateToInvoice_Effective is virtual column");	}

	/** Get Abrechnung ab eff..
		@return Enthält den Wert aus "Abrechnung ab" oder der Überschreibe-Wert aus "Abrechnung ab abw.
	  */
	@Override
	public java.sql.Timestamp getDateToInvoice_Effective () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateToInvoice_Effective);
	}

	/** Set Abrechnung ab abw..
		@param DateToInvoice_Override 
		Überschreibt den regulären Termin ab dem die Rechnung erstellt werden darf
	  */
	@Override
	public void setDateToInvoice_Override (java.sql.Timestamp DateToInvoice_Override)
	{
		set_Value (COLUMNNAME_DateToInvoice_Override, DateToInvoice_Override);
	}

	/** Get Abrechnung ab abw..
		@return Überschreibt den regulären Termin ab dem die Rechnung erstellt werden darf
	  */
	@Override
	public java.sql.Timestamp getDateToInvoice_Override () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateToInvoice_Override);
	}

	/** Set Lieferdatum.
		@param DeliveryDate Lieferdatum	  */
	@Override
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate)
	{
		set_Value (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	/** Get Lieferdatum.
		@return Lieferdatum	  */
	@Override
	public java.sql.Timestamp getDeliveryDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DeliveryDate);
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

	/** Set Schlusstext.
		@param DescriptionBottom Schlusstext	  */
	@Override
	public void setDescriptionBottom (java.lang.String DescriptionBottom)
	{
		set_Value (COLUMNNAME_DescriptionBottom, DescriptionBottom);
	}

	/** Get Schlusstext.
		@return Schlusstext	  */
	@Override
	public java.lang.String getDescriptionBottom () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DescriptionBottom);
	}

	/** Set Kopfbeschreibung.
		@param DescriptionHeader Kopfbeschreibung	  */
	@Override
	public void setDescriptionHeader (java.lang.String DescriptionHeader)
	{
		set_Value (COLUMNNAME_DescriptionHeader, DescriptionHeader);
	}

	/** Get Kopfbeschreibung.
		@return Kopfbeschreibung	  */
	@Override
	public java.lang.String getDescriptionHeader () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DescriptionHeader);
	}

	/** Set Rabatt %.
		@param Discount 
		Abschlag in Prozent
	  */
	@Override
	public void setDiscount (java.math.BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Rabatt %.
		@return Abschlag in Prozent
	  */
	@Override
	public java.math.BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Rabatt abw. %.
		@param Discount_Override 
		Abschlag in Prozent
	  */
	@Override
	public void setDiscount_Override (java.math.BigDecimal Discount_Override)
	{
		set_Value (COLUMNNAME_Discount_Override, Discount_Override);
	}

	/** Get Rabatt abw. %.
		@return Abschlag in Prozent
	  */
	@Override
	public java.math.BigDecimal getDiscount_Override () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount_Override);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set External ID.
		@param ExternalId External ID	  */
	@Override
	public void setExternalId (java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	/** Get External ID.
		@return External ID	  */
	@Override
	public java.lang.String getExternalId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalId);
	}

	/** Set First ship location.
		@param First_Ship_BPLocation_ID 
		BParter location of first shipment/receipt
	  */
	@Override
	public void setFirst_Ship_BPLocation_ID (int First_Ship_BPLocation_ID)
	{
		if (First_Ship_BPLocation_ID < 1) 
			set_Value (COLUMNNAME_First_Ship_BPLocation_ID, null);
		else 
			set_Value (COLUMNNAME_First_Ship_BPLocation_ID, Integer.valueOf(First_Ship_BPLocation_ID));
	}

	/** Get First ship location.
		@return BParter location of first shipment/receipt
	  */
	@Override
	public int getFirst_Ship_BPLocation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_First_Ship_BPLocation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * GroupCompensationAmtType AD_Reference_ID=540759
	 * Reference name: GroupCompensationAmtType
	 */
	public static final int GROUPCOMPENSATIONAMTTYPE_AD_Reference_ID=540759;
	/** Percent = P */
	public static final String GROUPCOMPENSATIONAMTTYPE_Percent = "P";
	/** PriceAndQty = Q */
	public static final String GROUPCOMPENSATIONAMTTYPE_PriceAndQty = "Q";
	/** Set Compensation Amount Type.
		@param GroupCompensationAmtType Compensation Amount Type	  */
	@Override
	public void setGroupCompensationAmtType (java.lang.String GroupCompensationAmtType)
	{

		set_Value (COLUMNNAME_GroupCompensationAmtType, GroupCompensationAmtType);
	}

	/** Get Compensation Amount Type.
		@return Compensation Amount Type	  */
	@Override
	public java.lang.String getGroupCompensationAmtType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GroupCompensationAmtType);
	}

	/** Set Compensation base amount.
		@param GroupCompensationBaseAmt 
		Base amount for calculating percentage group compensation
	  */
	@Override
	public void setGroupCompensationBaseAmt (java.math.BigDecimal GroupCompensationBaseAmt)
	{
		set_Value (COLUMNNAME_GroupCompensationBaseAmt, GroupCompensationBaseAmt);
	}

	/** Get Compensation base amount.
		@return Base amount for calculating percentage group compensation
	  */
	@Override
	public java.math.BigDecimal getGroupCompensationBaseAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GroupCompensationBaseAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Compensation percentage.
		@param GroupCompensationPercentage Compensation percentage	  */
	@Override
	public void setGroupCompensationPercentage (java.math.BigDecimal GroupCompensationPercentage)
	{
		set_Value (COLUMNNAME_GroupCompensationPercentage, GroupCompensationPercentage);
	}

	/** Get Compensation percentage.
		@return Compensation percentage	  */
	@Override
	public java.math.BigDecimal getGroupCompensationPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GroupCompensationPercentage);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** 
	 * GroupCompensationType AD_Reference_ID=540758
	 * Reference name: GroupCompensationType
	 */
	public static final int GROUPCOMPENSATIONTYPE_AD_Reference_ID=540758;
	/** Surcharge = S */
	public static final String GROUPCOMPENSATIONTYPE_Surcharge = "S";
	/** Discount = D */
	public static final String GROUPCOMPENSATIONTYPE_Discount = "D";
	/** Set Compensation Type.
		@param GroupCompensationType Compensation Type	  */
	@Override
	public void setGroupCompensationType (java.lang.String GroupCompensationType)
	{

		set_Value (COLUMNNAME_GroupCompensationType, GroupCompensationType);
	}

	/** Get Compensation Type.
		@return Compensation Type	  */
	@Override
	public java.lang.String getGroupCompensationType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GroupCompensationType);
	}

	/** Set Kopf-Aggregationsmerkmal.
		@param HeaderAggregationKey Kopf-Aggregationsmerkmal	  */
	@Override
	public void setHeaderAggregationKey (java.lang.String HeaderAggregationKey)
	{
		set_Value (COLUMNNAME_HeaderAggregationKey, HeaderAggregationKey);
	}

	/** Get Kopf-Aggregationsmerkmal.
		@return Kopf-Aggregationsmerkmal	  */
	@Override
	public java.lang.String getHeaderAggregationKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HeaderAggregationKey);
	}

	/** Set Header aggregation builder.
		@param HeaderAggregationKeyBuilder_ID Header aggregation builder	  */
	@Override
	public void setHeaderAggregationKeyBuilder_ID (int HeaderAggregationKeyBuilder_ID)
	{
		if (HeaderAggregationKeyBuilder_ID < 1) 
			set_Value (COLUMNNAME_HeaderAggregationKeyBuilder_ID, null);
		else 
			set_Value (COLUMNNAME_HeaderAggregationKeyBuilder_ID, Integer.valueOf(HeaderAggregationKeyBuilder_ID));
	}

	/** Get Header aggregation builder.
		@return Header aggregation builder	  */
	@Override
	public int getHeaderAggregationKeyBuilder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HeaderAggregationKeyBuilder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kopf-Aggregationsmerkmal (vorgegeben).
		@param HeaderAggregationKey_Calc Kopf-Aggregationsmerkmal (vorgegeben)	  */
	@Override
	public void setHeaderAggregationKey_Calc (java.lang.String HeaderAggregationKey_Calc)
	{
		set_Value (COLUMNNAME_HeaderAggregationKey_Calc, HeaderAggregationKey_Calc);
	}

	/** Get Kopf-Aggregationsmerkmal (vorgegeben).
		@return Kopf-Aggregationsmerkmal (vorgegeben)	  */
	@Override
	public java.lang.String getHeaderAggregationKey_Calc () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HeaderAggregationKey_Calc);
	}

	/** 
	 * InvoicableQtyBasedOn AD_Reference_ID=541023
	 * Reference name: InvoicableQtyBasedOn
	 */
	public static final int INVOICABLEQTYBASEDON_AD_Reference_ID=541023;
	/** Nominal = Nominal */
	public static final String INVOICABLEQTYBASEDON_Nominal = "Nominal";
	/** CatchWeight = CatchWeight */
	public static final String INVOICABLEQTYBASEDON_CatchWeight = "CatchWeight";
	/** Set Abr. Menge basiert auf.
		@param InvoicableQtyBasedOn 
		Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.
	  */
	@Override
	public void setInvoicableQtyBasedOn (java.lang.String InvoicableQtyBasedOn)
	{

		set_Value (COLUMNNAME_InvoicableQtyBasedOn, InvoicableQtyBasedOn);
	}

	/** Get Abr. Menge basiert auf.
		@return Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.
	  */
	@Override
	public java.lang.String getInvoicableQtyBasedOn () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoicableQtyBasedOn);
	}

	/** 
	 * InvoiceRule AD_Reference_ID=150
	 * Reference name: C_Order InvoiceRule
	 */
	public static final int INVOICERULE_AD_Reference_ID=150;
	/** AfterOrderDelivered = O */
	public static final String INVOICERULE_AfterOrderDelivered = "O";
	/** AfterDelivery = D */
	public static final String INVOICERULE_AfterDelivery = "D";
	/** CustomerScheduleAfterDelivery = S */
	public static final String INVOICERULE_CustomerScheduleAfterDelivery = "S";
	/** Immediate = I */
	public static final String INVOICERULE_Immediate = "I";
	/** Set Rechnungsstellung.
		@param InvoiceRule 
		"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	  */
	@Override
	public void setInvoiceRule (java.lang.String InvoiceRule)
	{

		set_ValueNoCheck (COLUMNNAME_InvoiceRule, InvoiceRule);
	}

	/** Get Rechnungsstellung.
		@return "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	  */
	@Override
	public java.lang.String getInvoiceRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceRule);
	}

	/** 
	 * InvoiceRule_Effective AD_Reference_ID=150
	 * Reference name: C_Order InvoiceRule
	 */
	public static final int INVOICERULE_EFFECTIVE_AD_Reference_ID=150;
	/** AfterOrderDelivered = O */
	public static final String INVOICERULE_EFFECTIVE_AfterOrderDelivered = "O";
	/** AfterDelivery = D */
	public static final String INVOICERULE_EFFECTIVE_AfterDelivery = "D";
	/** CustomerScheduleAfterDelivery = S */
	public static final String INVOICERULE_EFFECTIVE_CustomerScheduleAfterDelivery = "S";
	/** Immediate = I */
	public static final String INVOICERULE_EFFECTIVE_Immediate = "I";
	/** Set Rechnungsstellung eff..
		@param InvoiceRule_Effective Rechnungsstellung eff.	  */
	@Override
	public void setInvoiceRule_Effective (java.lang.String InvoiceRule_Effective)
	{

		throw new IllegalArgumentException ("InvoiceRule_Effective is virtual column");	}

	/** Get Rechnungsstellung eff..
		@return Rechnungsstellung eff.	  */
	@Override
	public java.lang.String getInvoiceRule_Effective () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceRule_Effective);
	}

	/** 
	 * InvoiceRule_Override AD_Reference_ID=150
	 * Reference name: C_Order InvoiceRule
	 */
	public static final int INVOICERULE_OVERRIDE_AD_Reference_ID=150;
	/** AfterOrderDelivered = O */
	public static final String INVOICERULE_OVERRIDE_AfterOrderDelivered = "O";
	/** AfterDelivery = D */
	public static final String INVOICERULE_OVERRIDE_AfterDelivery = "D";
	/** CustomerScheduleAfterDelivery = S */
	public static final String INVOICERULE_OVERRIDE_CustomerScheduleAfterDelivery = "S";
	/** Immediate = I */
	public static final String INVOICERULE_OVERRIDE_Immediate = "I";
	/** Set Rechnungsstellung abw..
		@param InvoiceRule_Override 
		Erlaubt es, eine abweichende Rechnungsstellungsregel vorzugeben.
	  */
	@Override
	public void setInvoiceRule_Override (java.lang.String InvoiceRule_Override)
	{

		set_Value (COLUMNNAME_InvoiceRule_Override, InvoiceRule_Override);
	}

	/** Get Rechnungsstellung abw..
		@return Erlaubt es, eine abweichende Rechnungsstellungsregel vorzugeben.
	  */
	@Override
	public java.lang.String getInvoiceRule_Override () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceRule_Override);
	}

	/** Set Status Terminplan.
		@param InvoiceScheduleAmtStatus 
		Bei Geschätspartnern, deren Rechnungs-Terminplan einen Grenzbetrag hat, zeigt dieses Feld an, ob der Grenzbetrag unterschritten ist.
	  */
	@Override
	public void setInvoiceScheduleAmtStatus (java.lang.String InvoiceScheduleAmtStatus)
	{
		set_Value (COLUMNNAME_InvoiceScheduleAmtStatus, InvoiceScheduleAmtStatus);
	}

	/** Get Status Terminplan.
		@return Bei Geschätspartnern, deren Rechnungs-Terminplan einen Grenzbetrag hat, zeigt dieses Feld an, ob der Grenzbetrag unterschritten ist.
	  */
	@Override
	public java.lang.String getInvoiceScheduleAmtStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceScheduleAmtStatus);
	}

	/** Set Erhält EDI-Belege.
		@param IsEdiRecipient 
		Erhält EDI-Belege
	  */
	@Override
	public void setIsEdiRecipient (boolean IsEdiRecipient)
	{
		set_ValueNoCheck (COLUMNNAME_IsEdiRecipient, Boolean.valueOf(IsEdiRecipient));
	}

	/** Get Erhält EDI-Belege.
		@return Erhält EDI-Belege
	  */
	@Override
	public boolean isEdiRecipient () 
	{
		Object oo = get_Value(COLUMNNAME_IsEdiRecipient);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Fehler.
		@param IsError 
		Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Fehler.
		@return Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsFreightCost.
		@param IsFreightCost IsFreightCost	  */
	@Override
	public void setIsFreightCost (boolean IsFreightCost)
	{
		set_Value (COLUMNNAME_IsFreightCost, Boolean.valueOf(IsFreightCost));
	}

	/** Get IsFreightCost.
		@return IsFreightCost	  */
	@Override
	public boolean isFreightCost () 
	{
		Object oo = get_Value(COLUMNNAME_IsFreightCost);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Group Compensation Line.
		@param IsGroupCompensationLine Group Compensation Line	  */
	@Override
	public void setIsGroupCompensationLine (boolean IsGroupCompensationLine)
	{
		set_Value (COLUMNNAME_IsGroupCompensationLine, Boolean.valueOf(IsGroupCompensationLine));
	}

	/** Get Group Compensation Line.
		@return Group Compensation Line	  */
	@Override
	public boolean isGroupCompensationLine () 
	{
		Object oo = get_Value(COLUMNNAME_IsGroupCompensationLine);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Strittig.
		@param IsInDispute Strittig	  */
	@Override
	public void setIsInDispute (boolean IsInDispute)
	{
		set_Value (COLUMNNAME_IsInDispute, Boolean.valueOf(IsInDispute));
	}

	/** Get Strittig.
		@return Strittig	  */
	@Override
	public boolean isInDispute () 
	{
		Object oo = get_Value(COLUMNNAME_IsInDispute);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lieferung/ Wareneingang freigeben.
		@param IsInOutApprovedForInvoicing Lieferung/ Wareneingang freigeben	  */
	@Override
	public void setIsInOutApprovedForInvoicing (boolean IsInOutApprovedForInvoicing)
	{
		set_ValueNoCheck (COLUMNNAME_IsInOutApprovedForInvoicing, Boolean.valueOf(IsInOutApprovedForInvoicing));
	}

	/** Get Lieferung/ Wareneingang freigeben.
		@return Lieferung/ Wareneingang freigeben	  */
	@Override
	public boolean isInOutApprovedForInvoicing () 
	{
		Object oo = get_Value(COLUMNNAME_IsInOutApprovedForInvoicing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manuell.
		@param IsManual 
		Dies ist ein manueller Vorgang
	  */
	@Override
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manuell.
		@return Dies ist ein manueller Vorgang
	  */
	@Override
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Ist Material-Vorgang.
		@param IsMaterialTracking Ist Material-Vorgang	  */
	@Override
	public void setIsMaterialTracking (boolean IsMaterialTracking)
	{
		throw new IllegalArgumentException ("IsMaterialTracking is virtual column");	}

	/** Get Ist Material-Vorgang.
		@return Ist Material-Vorgang	  */
	@Override
	public boolean isMaterialTracking () 
	{
		Object oo = get_Value(COLUMNNAME_IsMaterialTracking);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verpackungsmaterial.
		@param IsPackagingMaterial Verpackungsmaterial	  */
	@Override
	public void setIsPackagingMaterial (boolean IsPackagingMaterial)
	{
		set_Value (COLUMNNAME_IsPackagingMaterial, Boolean.valueOf(IsPackagingMaterial));
	}

	/** Get Verpackungsmaterial.
		@return Verpackungsmaterial	  */
	@Override
	public boolean isPackagingMaterial () 
	{
		Object oo = get_Value(COLUMNNAME_IsPackagingMaterial);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set andrucken.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	@Override
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get andrucken.
		@return Indicates if this document / line is printed
	  */
	@Override
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	@Override
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	@Override
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Preis inklusive Steuern.
		@param IsTaxIncluded 
		Tax is included in the price 
	  */
	@Override
	public void setIsTaxIncluded (boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, Boolean.valueOf(IsTaxIncluded));
	}

	/** Get Preis inklusive Steuern.
		@return Tax is included in the price 
	  */
	@Override
	public boolean isTaxIncluded () 
	{
		Object oo = get_Value(COLUMNNAME_IsTaxIncluded);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * IsTaxIncluded_Override AD_Reference_ID=540528
	 * Reference name: Yes_No
	 */
	public static final int ISTAXINCLUDED_OVERRIDE_AD_Reference_ID=540528;
	/** Yes = Y */
	public static final String ISTAXINCLUDED_OVERRIDE_Yes = "Y";
	/** No = N */
	public static final String ISTAXINCLUDED_OVERRIDE_No = "N";
	/** Set Preis inkl. Steuern abw..
		@param IsTaxIncluded_Override 
		Tax is included in the price 
	  */
	@Override
	public void setIsTaxIncluded_Override (java.lang.String IsTaxIncluded_Override)
	{

		set_Value (COLUMNNAME_IsTaxIncluded_Override, IsTaxIncluded_Override);
	}

	/** Get Preis inkl. Steuern abw..
		@return Tax is included in the price 
	  */
	@Override
	public java.lang.String getIsTaxIncluded_Override () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsTaxIncluded_Override);
	}

	/** Set zur Verrechnung.
		@param IsToClear 
		Leistung wird nicht unmittelbar in Rechnung gestellt, sondern mit anderen Posten (z.B. Pauschale) verrechnet
	  */
	@Override
	public void setIsToClear (boolean IsToClear)
	{
		set_ValueNoCheck (COLUMNNAME_IsToClear, Boolean.valueOf(IsToClear));
	}

	/** Get zur Verrechnung.
		@return Leistung wird nicht unmittelbar in Rechnung gestellt, sondern mit anderen Posten (z.B. Pauschale) verrechnet
	  */
	@Override
	public boolean isToClear () 
	{
		Object oo = get_Value(COLUMNNAME_IsToClear);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set zu Akt..
		@param IsToRecompute 
		Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.
	  */
	@Override
	public void setIsToRecompute (boolean IsToRecompute)
	{
		throw new IllegalArgumentException ("IsToRecompute is virtual column");	}

	/** Get zu Akt..
		@return Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.
	  */
	@Override
	public boolean isToRecompute () 
	{
		Object oo = get_Value(COLUMNNAME_IsToRecompute);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zeile Nr..
		@param Line 
		Einzelne Zeile in dem Dokument
	  */
	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Einzelne Zeile in dem Dokument
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zeilen-Aggregationsmerkmal.
		@param LineAggregationKey 
		Wird vom System gesetzt und legt fest, welche Kandidaten zu einer Rechnungszeile zusammen gefasst (aggregiert) werden sollen.
	  */
	@Override
	public void setLineAggregationKey (java.lang.String LineAggregationKey)
	{
		set_Value (COLUMNNAME_LineAggregationKey, LineAggregationKey);
	}

	/** Get Zeilen-Aggregationsmerkmal.
		@return Wird vom System gesetzt und legt fest, welche Kandidaten zu einer Rechnungszeile zusammen gefasst (aggregiert) werden sollen.
	  */
	@Override
	public java.lang.String getLineAggregationKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LineAggregationKey);
	}

	/** Set Line aggregation builder.
		@param LineAggregationKeyBuilder_ID Line aggregation builder	  */
	@Override
	public void setLineAggregationKeyBuilder_ID (int LineAggregationKeyBuilder_ID)
	{
		if (LineAggregationKeyBuilder_ID < 1) 
			set_Value (COLUMNNAME_LineAggregationKeyBuilder_ID, null);
		else 
			set_Value (COLUMNNAME_LineAggregationKeyBuilder_ID, Integer.valueOf(LineAggregationKeyBuilder_ID));
	}

	/** Get Line aggregation builder.
		@return Line aggregation builder	  */
	@Override
	public int getLineAggregationKeyBuilder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineAggregationKeyBuilder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Aggregations-Zusatz.
		@param LineAggregationKey_Suffix 
		Optionale Möglichkeit, einzelne Rechnungskandidaten aus einer gemeinsamen Aggregations-Gruppe herauszulösen.
	  */
	@Override
	public void setLineAggregationKey_Suffix (java.lang.String LineAggregationKey_Suffix)
	{
		set_Value (COLUMNNAME_LineAggregationKey_Suffix, LineAggregationKey_Suffix);
	}

	/** Get Aggregations-Zusatz.
		@return Optionale Möglichkeit, einzelne Rechnungskandidaten aus einer gemeinsamen Aggregations-Gruppe herauszulösen.
	  */
	@Override
	public java.lang.String getLineAggregationKey_Suffix () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LineAggregationKey_Suffix);
	}

	/** Set Zeilennetto.
		@param LineNetAmt 
		Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	  */
	@Override
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt)
	{
		set_ValueNoCheck (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Zeilennetto.
		@return Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	  */
	@Override
	public java.math.BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	/** Set Lieferung/Wareneingang.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	@Override
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Lieferung/Wareneingang.
		@return Material Shipment Document
	  */
	@Override
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Version Preisliste.
		@param M_PriceList_Version_ID 
		Bezeichnet eine einzelne Version der Preisliste
	  */
	@Override
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, Integer.valueOf(M_PriceList_Version_ID));
	}

	/** Get Version Preisliste.
		@return Bezeichnet eine einzelne Version der Preisliste
	  */
	@Override
	public int getM_PriceList_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_Version_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Produkt Kategorie.
		@param M_Product_Category_ID 
		Kategorie eines Produktes
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		throw new IllegalArgumentException ("M_Product_Category_ID is virtual column");	}

	/** Get Produkt Kategorie.
		@return Kategorie eines Produktes
	  */
	@Override
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Abgerechneter Betrag.
		@param NetAmtInvoiced 
		Bezeichnet den Netto-Geldbetrag, der für diesen Rechnungskandidaten bereits in Rechnung gestellt wurde
	  */
	@Override
	public void setNetAmtInvoiced (java.math.BigDecimal NetAmtInvoiced)
	{
		set_Value (COLUMNNAME_NetAmtInvoiced, NetAmtInvoiced);
	}

	/** Get Abgerechneter Betrag.
		@return Bezeichnet den Netto-Geldbetrag, der für diesen Rechnungskandidaten bereits in Rechnung gestellt wurde
	  */
	@Override
	public java.math.BigDecimal getNetAmtInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NetAmtInvoiced);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abzurechnender Betrag.
		@param NetAmtToInvoice 
		Bezeichnet den netto-Geldbetrag, der für den jeweiligen Rechnungskandidaten aktuell bei einem Rechnungslauf in Rechnung gestellt würde.
	  */
	@Override
	public void setNetAmtToInvoice (java.math.BigDecimal NetAmtToInvoice)
	{
		set_Value (COLUMNNAME_NetAmtToInvoice, NetAmtToInvoice);
	}

	/** Get Abzurechnender Betrag.
		@return Bezeichnet den netto-Geldbetrag, der für den jeweiligen Rechnungskandidaten aktuell bei einem Rechnungslauf in Rechnung gestellt würde.
	  */
	@Override
	public java.math.BigDecimal getNetAmtToInvoice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NetAmtToInvoice);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Notiz.
		@param Note 
		Optional weitere Information
	  */
	@Override
	public void setNote (java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Notiz.
		@return Optional weitere Information
	  */
	@Override
	public java.lang.String getNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Note);
	}

	/** Set Referenz.
		@param POReference 
		Referenz-Nummer des Kunden
	  */
	@Override
	public void setPOReference (java.lang.String POReference)
	{
		set_ValueNoCheck (COLUMNNAME_POReference, POReference);
	}

	/** Get Referenz.
		@return Referenz-Nummer des Kunden
	  */
	@Override
	public java.lang.String getPOReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}

	/** Set Vorbelegtes Rechnungsdatum.
		@param PresetDateInvoiced Vorbelegtes Rechnungsdatum	  */
	@Override
	public void setPresetDateInvoiced (java.sql.Timestamp PresetDateInvoiced)
	{
		set_Value (COLUMNNAME_PresetDateInvoiced, PresetDateInvoiced);
	}

	/** Get Vorbelegtes Rechnungsdatum.
		@return Vorbelegtes Rechnungsdatum	  */
	@Override
	public java.sql.Timestamp getPresetDateInvoiced () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PresetDateInvoiced);
	}

	/** Set Einzelpreis.
		@param PriceActual 
		Effektiver Preis
	  */
	@Override
	public void setPriceActual (java.math.BigDecimal PriceActual)
	{
		set_ValueNoCheck (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Einzelpreis.
		@return Effektiver Preis
	  */
	@Override
	public java.math.BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Preis Eff. Netto.
		@param PriceActual_Net_Effective Preis Eff. Netto	  */
	@Override
	public void setPriceActual_Net_Effective (java.math.BigDecimal PriceActual_Net_Effective)
	{
		set_Value (COLUMNNAME_PriceActual_Net_Effective, PriceActual_Net_Effective);
	}

	/** Get Preis Eff. Netto.
		@return Preis Eff. Netto	  */
	@Override
	public java.math.BigDecimal getPriceActual_Net_Effective () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual_Net_Effective);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Einzelpreis abw..
		@param PriceActual_Override 
		Effektiver Preis
	  */
	@Override
	public void setPriceActual_Override (java.math.BigDecimal PriceActual_Override)
	{
		set_Value (COLUMNNAME_PriceActual_Override, PriceActual_Override);
	}

	/** Get Einzelpreis abw..
		@return Effektiver Preis
	  */
	@Override
	public java.math.BigDecimal getPriceActual_Override () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual_Override);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Preis.
		@param PriceEntered 
		Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit
	  */
	@Override
	public void setPriceEntered (java.math.BigDecimal PriceEntered)
	{
		set_Value (COLUMNNAME_PriceEntered, PriceEntered);
	}

	/** Get Preis.
		@return Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit
	  */
	@Override
	public java.math.BigDecimal getPriceEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceEntered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Preis Abw..
		@param PriceEntered_Override Preis Abw.	  */
	@Override
	public void setPriceEntered_Override (java.math.BigDecimal PriceEntered_Override)
	{
		set_Value (COLUMNNAME_PriceEntered_Override, PriceEntered_Override);
	}

	/** Get Preis Abw..
		@return Preis Abw.	  */
	@Override
	public java.math.BigDecimal getPriceEntered_Override () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceEntered_Override);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Preiseinheit.
		@param Price_UOM_ID Preiseinheit	  */
	@Override
	public void setPrice_UOM_ID (int Price_UOM_ID)
	{
		if (Price_UOM_ID < 1) 
			set_Value (COLUMNNAME_Price_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Price_UOM_ID, Integer.valueOf(Price_UOM_ID));
	}

	/** Get Preiseinheit.
		@return Preiseinheit	  */
	@Override
	public int getPrice_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Price_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * Priority AD_Reference_ID=154
	 * Reference name: _PriorityRule
	 */
	public static final int PRIORITY_AD_Reference_ID=154;
	/** High = 3 */
	public static final String PRIORITY_High = "3";
	/** Medium = 5 */
	public static final String PRIORITY_Medium = "5";
	/** Low = 7 */
	public static final String PRIORITY_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITY_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITY_Minor = "9";
	/** Set Priority.
		@param Priority 
		Indicates if this request is of a high, medium or low priority.
	  */
	@Override
	public void setPriority (java.lang.String Priority)
	{

		set_Value (COLUMNNAME_Priority, Priority);
	}

	/** Get Priority.
		@return Indicates if this request is of a high, medium or low priority.
	  */
	@Override
	public java.lang.String getPriority () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Priority);
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

	/** Set Verarbeitet (System).
		@param Processed_Calc Verarbeitet (System)	  */
	@Override
	public void setProcessed_Calc (boolean Processed_Calc)
	{
		set_Value (COLUMNNAME_Processed_Calc, Boolean.valueOf(Processed_Calc));
	}

	/** Get Verarbeitet (System).
		@return Verarbeitet (System)	  */
	@Override
	public boolean isProcessed_Calc () 
	{
		Object oo = get_Value(COLUMNNAME_Processed_Calc);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * Processed_Override AD_Reference_ID=540528
	 * Reference name: Yes_No
	 */
	public static final int PROCESSED_OVERRIDE_AD_Reference_ID=540528;
	/** Yes = Y */
	public static final String PROCESSED_OVERRIDE_Yes = "Y";
	/** No = N */
	public static final String PROCESSED_OVERRIDE_No = "N";
	/** Set Verarbeitet abw..
		@param Processed_Override Verarbeitet abw.	  */
	@Override
	public void setProcessed_Override (java.lang.String Processed_Override)
	{

		set_Value (COLUMNNAME_Processed_Override, Processed_Override);
	}

	/** Get Verarbeitet abw..
		@return Verarbeitet abw.	  */
	@Override
	public java.lang.String getProcessed_Override () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Processed_Override);
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		throw new IllegalArgumentException ("Processing is virtual column");	}

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

	/** 
	 * ProductType AD_Reference_ID=270
	 * Reference name: M_Product_ProductType
	 */
	public static final int PRODUCTTYPE_AD_Reference_ID=270;
	/** Item = I */
	public static final String PRODUCTTYPE_Item = "I";
	/** Service = S */
	public static final String PRODUCTTYPE_Service = "S";
	/** Resource = R */
	public static final String PRODUCTTYPE_Resource = "R";
	/** ExpenseType = E */
	public static final String PRODUCTTYPE_ExpenseType = "E";
	/** Online = O */
	public static final String PRODUCTTYPE_Online = "O";
	/** FreightCost = F */
	public static final String PRODUCTTYPE_FreightCost = "F";
	/** Set Produktart.
		@param ProductType 
		Art von Produkt
	  */
	@Override
	public void setProductType (java.lang.String ProductType)
	{

		throw new IllegalArgumentException ("ProductType is virtual column");	}

	/** Get Produktart.
		@return Art von Produkt
	  */
	@Override
	public java.lang.String getProductType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductType);
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
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Liefermenge.
		@param QtyDeliveredInUOM 
		Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)
	  */
	@Override
	public void setQtyDeliveredInUOM (java.math.BigDecimal QtyDeliveredInUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInUOM, QtyDeliveredInUOM);
	}

	/** Get Liefermenge.
		@return Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)
	  */
	@Override
	public java.math.BigDecimal getQtyDeliveredInUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDeliveredInUOM);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge.
		@param QtyEntered 
		Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	  */
	@Override
	public void setQtyEntered (java.math.BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Menge.
		@return Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	  */
	@Override
	public java.math.BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Berechn. Menge.
		@param QtyInvoiced 
		Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.
	  */
	@Override
	public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Berechn. Menge.
		@return Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.
	  */
	@Override
	public java.math.BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abgerechnet.
		@param QtyInvoicedInUOM Abgerechnet	  */
	@Override
	public void setQtyInvoicedInUOM (java.math.BigDecimal QtyInvoicedInUOM)
	{
		set_ValueNoCheck (COLUMNNAME_QtyInvoicedInUOM, QtyInvoicedInUOM);
	}

	/** Get Abgerechnet.
		@return Abgerechnet	  */
	@Override
	public java.math.BigDecimal getQtyInvoicedInUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoicedInUOM);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bestellt/ Beauftragt.
		@param QtyOrdered 
		Bestellt/ Beauftragt
	  */
	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Bestellt/ Beauftragt.
		@return Bestellt/ Beauftragt
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set QtyOrderedOverUnder.
		@param QtyOrderedOverUnder QtyOrderedOverUnder	  */
	@Override
	public void setQtyOrderedOverUnder (java.math.BigDecimal QtyOrderedOverUnder)
	{
		set_Value (COLUMNNAME_QtyOrderedOverUnder, QtyOrderedOverUnder);
	}

	/** Get QtyOrderedOverUnder.
		@return QtyOrderedOverUnder	  */
	@Override
	public java.math.BigDecimal getQtyOrderedOverUnder () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrderedOverUnder);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abzurechnen eff. (Lagereinheit).
		@param QtyToInvoice 
		Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde
	  */
	@Override
	public void setQtyToInvoice (java.math.BigDecimal QtyToInvoice)
	{
		set_Value (COLUMNNAME_QtyToInvoice, QtyToInvoice);
	}

	/** Get Abzurechnen eff. (Lagereinheit).
		@return Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde
	  */
	@Override
	public java.math.BigDecimal getQtyToInvoice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToInvoice);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Zu berechn. Menge vor Qualitätsabzug.
		@param QtyToInvoiceBeforeDiscount Zu berechn. Menge vor Qualitätsabzug	  */
	@Override
	public void setQtyToInvoiceBeforeDiscount (java.math.BigDecimal QtyToInvoiceBeforeDiscount)
	{
		set_Value (COLUMNNAME_QtyToInvoiceBeforeDiscount, QtyToInvoiceBeforeDiscount);
	}

	/** Get Zu berechn. Menge vor Qualitätsabzug.
		@return Zu berechn. Menge vor Qualitätsabzug	  */
	@Override
	public java.math.BigDecimal getQtyToInvoiceBeforeDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToInvoiceBeforeDiscount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Zu berechn. Menge In Preiseinheit.
		@param QtyToInvoiceInPriceUOM 
		Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.
	  */
	@Override
	public void setQtyToInvoiceInPriceUOM (java.math.BigDecimal QtyToInvoiceInPriceUOM)
	{
		set_Value (COLUMNNAME_QtyToInvoiceInPriceUOM, QtyToInvoiceInPriceUOM);
	}

	/** Get Zu berechn. Menge In Preiseinheit.
		@return Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.
	  */
	@Override
	public java.math.BigDecimal getQtyToInvoiceInPriceUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToInvoiceInPriceUOM);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abzurechnen eff..
		@param QtyToInvoiceInUOM Abzurechnen eff.	  */
	@Override
	public void setQtyToInvoiceInUOM (java.math.BigDecimal QtyToInvoiceInUOM)
	{
		set_Value (COLUMNNAME_QtyToInvoiceInUOM, QtyToInvoiceInUOM);
	}

	/** Get Abzurechnen eff..
		@return Abzurechnen eff.	  */
	@Override
	public java.math.BigDecimal getQtyToInvoiceInUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToInvoiceInUOM);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abzurechnen.
		@param QtyToInvoiceInUOM_Calc Abzurechnen	  */
	@Override
	public void setQtyToInvoiceInUOM_Calc (java.math.BigDecimal QtyToInvoiceInUOM_Calc)
	{
		set_ValueNoCheck (COLUMNNAME_QtyToInvoiceInUOM_Calc, QtyToInvoiceInUOM_Calc);
	}

	/** Get Abzurechnen.
		@return Abzurechnen	  */
	@Override
	public java.math.BigDecimal getQtyToInvoiceInUOM_Calc () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToInvoiceInUOM_Calc);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Zu berechn. Menge abw..
		@param QtyToInvoice_Override 
		Der Benutzer kann eine abweichende zu berechnede Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.
	  */
	@Override
	public void setQtyToInvoice_Override (java.math.BigDecimal QtyToInvoice_Override)
	{
		set_Value (COLUMNNAME_QtyToInvoice_Override, QtyToInvoice_Override);
	}

	/** Get Zu berechn. Menge abw..
		@return Der Benutzer kann eine abweichende zu berechnede Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.
	  */
	@Override
	public java.math.BigDecimal getQtyToInvoice_Override () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToInvoice_Override);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Zu berechn. Menge abw. erl..
		@param QtyToInvoice_OverrideFulfilled 
		Angabe über den Teil der abweichenden Menge, der bereits in Rechnung gestellt wurde
	  */
	@Override
	public void setQtyToInvoice_OverrideFulfilled (java.math.BigDecimal QtyToInvoice_OverrideFulfilled)
	{
		set_Value (COLUMNNAME_QtyToInvoice_OverrideFulfilled, QtyToInvoice_OverrideFulfilled);
	}

	/** Get Zu berechn. Menge abw. erl..
		@return Angabe über den Teil der abweichenden Menge, der bereits in Rechnung gestellt wurde
	  */
	@Override
	public java.math.BigDecimal getQtyToInvoice_OverrideFulfilled () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToInvoice_OverrideFulfilled);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Minderwertige Menge.
		@param QtyWithIssues 
		Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.
	  */
	@Override
	public void setQtyWithIssues (java.math.BigDecimal QtyWithIssues)
	{
		set_Value (COLUMNNAME_QtyWithIssues, QtyWithIssues);
	}

	/** Get Minderwertige Menge.
		@return Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.
	  */
	@Override
	public java.math.BigDecimal getQtyWithIssues () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyWithIssues);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Minderwertige Menge eff..
		@param QtyWithIssues_Effective 
		Liefermenge, die nicht fakturiert wird soll. Der Wert weicht von "Minderwertige Menge" ab, wenn ein abweichender "Qualitätsabzug %" Wert gesetzt wurde.
	  */
	@Override
	public void setQtyWithIssues_Effective (java.math.BigDecimal QtyWithIssues_Effective)
	{
		set_Value (COLUMNNAME_QtyWithIssues_Effective, QtyWithIssues_Effective);
	}

	/** Get Minderwertige Menge eff..
		@return Liefermenge, die nicht fakturiert wird soll. Der Wert weicht von "Minderwertige Menge" ab, wenn ein abweichender "Qualitätsabzug %" Wert gesetzt wurde.
	  */
	@Override
	public java.math.BigDecimal getQtyWithIssues_Effective () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyWithIssues_Effective);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qualitätsabzug %.
		@param QualityDiscountPercent Qualitätsabzug %	  */
	@Override
	public void setQualityDiscountPercent (java.math.BigDecimal QualityDiscountPercent)
	{
		set_Value (COLUMNNAME_QualityDiscountPercent, QualityDiscountPercent);
	}

	/** Get Qualitätsabzug %.
		@return Qualitätsabzug %	  */
	@Override
	public java.math.BigDecimal getQualityDiscountPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QualityDiscountPercent);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qualitätsabzug % eff..
		@param QualityDiscountPercent_Effective Qualitätsabzug % eff.	  */
	@Override
	public void setQualityDiscountPercent_Effective (java.math.BigDecimal QualityDiscountPercent_Effective)
	{
		throw new IllegalArgumentException ("QualityDiscountPercent_Effective is virtual column");	}

	/** Get Qualitätsabzug % eff..
		@return Qualitätsabzug % eff.	  */
	@Override
	public java.math.BigDecimal getQualityDiscountPercent_Effective () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QualityDiscountPercent_Effective);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qualitätsabzug % abw..
		@param QualityDiscountPercent_Override Qualitätsabzug % abw.	  */
	@Override
	public void setQualityDiscountPercent_Override (java.math.BigDecimal QualityDiscountPercent_Override)
	{
		set_Value (COLUMNNAME_QualityDiscountPercent_Override, QualityDiscountPercent_Override);
	}

	/** Get Qualitätsabzug % abw..
		@return Qualitätsabzug % abw.	  */
	@Override
	public java.math.BigDecimal getQualityDiscountPercent_Override () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QualityDiscountPercent_Override);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** 
	 * QualityInvoiceLineGroupType AD_Reference_ID=540617
	 * Reference name: QualityInvoiceLineGroupType
	 */
	public static final int QUALITYINVOICELINEGROUPTYPE_AD_Reference_ID=540617;
	/** Scrap = 01 */
	public static final String QUALITYINVOICELINEGROUPTYPE_Scrap = "01";
	/** ProducedByProducts = 02 */
	public static final String QUALITYINVOICELINEGROUPTYPE_ProducedByProducts = "02";
	/** AdditionalFee = 03 */
	public static final String QUALITYINVOICELINEGROUPTYPE_AdditionalFee = "03";
	/** ProducedMainProduct = 04 */
	public static final String QUALITYINVOICELINEGROUPTYPE_ProducedMainProduct = "04";
	/** ProducedCoProduct = 05 */
	public static final String QUALITYINVOICELINEGROUPTYPE_ProducedCoProduct = "05";
	/** WithholdingAmount = 06 */
	public static final String QUALITYINVOICELINEGROUPTYPE_WithholdingAmount = "06";
	/** PreceeedingRegularOrderDeduction = 07 */
	public static final String QUALITYINVOICELINEGROUPTYPE_PreceeedingRegularOrderDeduction = "07";
	/** Set Rechnungspositionsart.
		@param QualityInvoiceLineGroupType Rechnungspositionsart	  */
	@Override
	public void setQualityInvoiceLineGroupType (java.lang.String QualityInvoiceLineGroupType)
	{

		set_Value (COLUMNNAME_QualityInvoiceLineGroupType, QualityInvoiceLineGroupType);
	}

	/** Get Rechnungspositionsart.
		@return Rechnungspositionsart	  */
	@Override
	public java.lang.String getQualityInvoiceLineGroupType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_QualityInvoiceLineGroupType);
	}

	/** Set ReasonDiscount.
		@param ReasonDiscount ReasonDiscount	  */
	@Override
	public void setReasonDiscount (java.lang.String ReasonDiscount)
	{
		set_Value (COLUMNNAME_ReasonDiscount, ReasonDiscount);
	}

	/** Get ReasonDiscount.
		@return ReasonDiscount	  */
	@Override
	public java.lang.String getReasonDiscount () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReasonDiscount);
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Status nach Akt..
		@param SchedulerResult 
		Informationen des Aktualisierungsprozesses
	  */
	@Override
	public void setSchedulerResult (java.lang.String SchedulerResult)
	{
		set_Value (COLUMNNAME_SchedulerResult, SchedulerResult);
	}

	/** Get Status nach Akt..
		@return Informationen des Aktualisierungsprozesses
	  */
	@Override
	public java.lang.String getSchedulerResult () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SchedulerResult);
	}

	/** Set Restbetrag.
		@param SplitAmt Restbetrag	  */
	@Override
	public void setSplitAmt (java.math.BigDecimal SplitAmt)
	{
		set_Value (COLUMNNAME_SplitAmt, SplitAmt);
	}

	/** Get Restbetrag.
		@return Restbetrag	  */
	@Override
	public java.math.BigDecimal getSplitAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SplitAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Lagereinheit.
		@param StockingUOM_ID Lagereinheit	  */
	@Override
	public void setStockingUOM_ID (int StockingUOM_ID)
	{
		throw new IllegalArgumentException ("StockingUOM_ID is virtual column");	}

	/** Get Lagereinheit.
		@return Lagereinheit	  */
	@Override
	public int getStockingUOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_StockingUOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total des Auftrags.
		@param TotalOfOrder Total des Auftrags	  */
	@Override
	public void setTotalOfOrder (java.math.BigDecimal TotalOfOrder)
	{
		throw new IllegalArgumentException ("TotalOfOrder is virtual column");	}

	/** Get Total des Auftrags.
		@return Total des Auftrags	  */
	@Override
	public java.math.BigDecimal getTotalOfOrder () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalOfOrder);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Auftrag Total ohne Rabatt.
		@param TotalOfOrderExcludingDiscount Auftrag Total ohne Rabatt	  */
	@Override
	public void setTotalOfOrderExcludingDiscount (java.math.BigDecimal TotalOfOrderExcludingDiscount)
	{
		throw new IllegalArgumentException ("TotalOfOrderExcludingDiscount is virtual column");	}

	/** Get Auftrag Total ohne Rabatt.
		@return Auftrag Total ohne Rabatt	  */
	@Override
	public java.math.BigDecimal getTotalOfOrderExcludingDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalOfOrderExcludingDiscount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}