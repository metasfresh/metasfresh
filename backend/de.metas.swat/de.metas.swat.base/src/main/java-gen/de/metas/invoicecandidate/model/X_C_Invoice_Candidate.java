// Generated Model - DO NOT CHANGE
package de.metas.invoicecandidate.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Invoice_Candidate extends org.compiere.model.PO implements I_C_Invoice_Candidate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 853148688L;

    /** Standard Constructor */
    public X_C_Invoice_Candidate (final Properties ctx, final int C_Invoice_Candidate_ID, @Nullable final String trxName)
    {
      super (ctx, C_Invoice_Candidate_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Invoice_Candidate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_InputDataSource_ID (final int AD_InputDataSource_ID)
	{
		if (AD_InputDataSource_ID < 1) 
			set_Value (COLUMNNAME_AD_InputDataSource_ID, null);
		else 
			set_Value (COLUMNNAME_AD_InputDataSource_ID, AD_InputDataSource_ID);
	}

	@Override
	public int getAD_InputDataSource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_InputDataSource_ID);
	}

	@Override
	public void setAD_Note_ID (final int AD_Note_ID)
	{
		if (AD_Note_ID < 1) 
			set_Value (COLUMNNAME_AD_Note_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Note_ID, AD_Note_ID);
	}

	@Override
	public int getAD_Note_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Note_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setAD_User_InCharge_ID (final int AD_User_InCharge_ID)
	{
		if (AD_User_InCharge_ID < 1) 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, AD_User_InCharge_ID);
	}

	@Override
	public int getAD_User_InCharge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_InCharge_ID);
	}

	@Override
	public void setApprovalForInvoicing (final boolean ApprovalForInvoicing)
	{
		set_Value (COLUMNNAME_ApprovalForInvoicing, ApprovalForInvoicing);
	}

	@Override
	public boolean isApprovalForInvoicing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_ApprovalForInvoicing);
	}

	@Override
	public void setBill_BPartner_ID (final int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Bill_BPartner_ID);
	}

	@Override
	public int getBill_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_BPartner_ID);
	}

	@Override
	public void setBill_BPartner_Name (final @Nullable java.lang.String Bill_BPartner_Name)
	{
		throw new IllegalArgumentException ("Bill_BPartner_Name is virtual column");	}

	@Override
	public java.lang.String getBill_BPartner_Name() 
	{
		return get_ValueAsString(COLUMNNAME_Bill_BPartner_Name);
	}

	@Override
	public void setBill_Location_ID (final int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_ID, Bill_Location_ID);
	}

	@Override
	public int getBill_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_ID);
	}

	@Override
	public void setBill_Location_Override_ID (final int Bill_Location_Override_ID)
	{
		if (Bill_Location_Override_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_Override_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_Override_ID, Bill_Location_Override_ID);
	}

	@Override
	public int getBill_Location_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_Override_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getBill_Location_Override_Value()
	{
		return get_ValueAsPO(COLUMNNAME_Bill_Location_Override_Value_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setBill_Location_Override_Value(final org.compiere.model.I_C_Location Bill_Location_Override_Value)
	{
		set_ValueFromPO(COLUMNNAME_Bill_Location_Override_Value_ID, org.compiere.model.I_C_Location.class, Bill_Location_Override_Value);
	}

	@Override
	public void setBill_Location_Override_Value_ID (final int Bill_Location_Override_Value_ID)
	{
		if (Bill_Location_Override_Value_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_Override_Value_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_Override_Value_ID, Bill_Location_Override_Value_ID);
	}

	@Override
	public int getBill_Location_Override_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_Override_Value_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getBill_Location_Value()
	{
		return get_ValueAsPO(COLUMNNAME_Bill_Location_Value_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setBill_Location_Value(final org.compiere.model.I_C_Location Bill_Location_Value)
	{
		set_ValueFromPO(COLUMNNAME_Bill_Location_Value_ID, org.compiere.model.I_C_Location.class, Bill_Location_Value);
	}

	@Override
	public void setBill_Location_Value_ID (final int Bill_Location_Value_ID)
	{
		if (Bill_Location_Value_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_Value_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_Value_ID, Bill_Location_Value_ID);
	}

	@Override
	public int getBill_Location_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_Value_ID);
	}

	@Override
	public void setBill_User_ID (final int Bill_User_ID)
	{
		if (Bill_User_ID < 1) 
			set_Value (COLUMNNAME_Bill_User_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_User_ID, Bill_User_ID);
	}

	@Override
	public int getBill_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_User_ID);
	}

	@Override
	public void setBill_User_ID_Override_ID (final int Bill_User_ID_Override_ID)
	{
		if (Bill_User_ID_Override_ID < 1) 
			set_Value (COLUMNNAME_Bill_User_ID_Override_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_User_ID_Override_ID, Bill_User_ID_Override_ID);
	}

	@Override
	public int getBill_User_ID_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_User_ID_Override_ID);
	}

	@Override
	public void setC_Activity_ID (final int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, C_Activity_ID);
	}

	@Override
	public int getC_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Activity_ID);
	}

	@Override
	public void setC_Async_Batch_ID (final int C_Async_Batch_ID)
	{
		if (C_Async_Batch_ID < 1) 
			set_Value (COLUMNNAME_C_Async_Batch_ID, null);
		else 
			set_Value (COLUMNNAME_C_Async_Batch_ID, C_Async_Batch_ID);
	}

	@Override
	public int getC_Async_Batch_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Async_Batch_ID);
	}

	@Override
	public void setC_BPartner_SalesRep_ID (final int C_BPartner_SalesRep_ID)
	{
		if (C_BPartner_SalesRep_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_SalesRep_ID, C_BPartner_SalesRep_ID);
	}

	@Override
	public int getC_BPartner_SalesRep_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_SalesRep_ID);
	}

	@Override
	public org.compiere.model.I_C_Campaign getC_Campaign()
	{
		return get_ValueAsPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class);
	}

	@Override
	public void setC_Campaign(final org.compiere.model.I_C_Campaign C_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class, C_Campaign);
	}

	@Override
	public void setC_Campaign_ID (final int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, C_Campaign_ID);
	}

	@Override
	public int getC_Campaign_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Campaign_ID);
	}

	@Override
	public void setC_Charge_ID (final int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, C_Charge_ID);
	}

	@Override
	public int getC_Charge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Charge_ID);
	}

	@Override
	public void setC_ConversionType_ID (final int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, C_ConversionType_ID);
	}

	@Override
	public int getC_ConversionType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ConversionType_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setC_DocTypeInvoice_ID (final int C_DocTypeInvoice_ID)
	{
		if (C_DocTypeInvoice_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeInvoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeInvoice_ID, C_DocTypeInvoice_ID);
	}

	@Override
	public int getC_DocTypeInvoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeInvoice_ID);
	}

	@Override
	public void setC_Flatrate_Term_ID (final int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, C_Flatrate_Term_ID);
	}

	@Override
	public int getC_Flatrate_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Term_ID);
	}

	@Override
	public de.metas.invoicecandidate.model.I_C_ILCandHandler getC_ILCandHandler()
	{
		return get_ValueAsPO(COLUMNNAME_C_ILCandHandler_ID, de.metas.invoicecandidate.model.I_C_ILCandHandler.class);
	}

	@Override
	public void setC_ILCandHandler(final de.metas.invoicecandidate.model.I_C_ILCandHandler C_ILCandHandler)
	{
		set_ValueFromPO(COLUMNNAME_C_ILCandHandler_ID, de.metas.invoicecandidate.model.I_C_ILCandHandler.class, C_ILCandHandler);
	}

	@Override
	public void setC_ILCandHandler_ID (final int C_ILCandHandler_ID)
	{
		if (C_ILCandHandler_ID < 1) 
			set_Value (COLUMNNAME_C_ILCandHandler_ID, null);
		else 
			set_Value (COLUMNNAME_C_ILCandHandler_ID, C_ILCandHandler_ID);
	}

	@Override
	public int getC_ILCandHandler_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ILCandHandler_ID);
	}

	@Override
	public org.compiere.model.I_C_Incoterms getC_Incoterms()
	{
		return get_ValueAsPO(COLUMNNAME_C_Incoterms_ID, org.compiere.model.I_C_Incoterms.class);
	}

	@Override
	public void setC_Incoterms(final org.compiere.model.I_C_Incoterms C_Incoterms)
	{
		set_ValueFromPO(COLUMNNAME_C_Incoterms_ID, org.compiere.model.I_C_Incoterms.class, C_Incoterms);
	}

	@Override
	public void setC_Incoterms_ID (final int C_Incoterms_ID)
	{
		if (C_Incoterms_ID < 1) 
			set_Value (COLUMNNAME_C_Incoterms_ID, null);
		else 
			set_Value (COLUMNNAME_C_Incoterms_ID, C_Incoterms_ID);
	}

	@Override
	public int getC_Incoterms_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Incoterms_ID);
	}

	@Override
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg getC_Invoice_Candidate_Agg()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_Agg_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg.class);
	}

	@Override
	public void setC_Invoice_Candidate_Agg(final de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg C_Invoice_Candidate_Agg)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_Agg_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg.class, C_Invoice_Candidate_Agg);
	}

	@Override
	public void setC_Invoice_Candidate_Agg_ID (final int C_Invoice_Candidate_Agg_ID)
	{
		if (C_Invoice_Candidate_Agg_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Agg_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Agg_ID, C_Invoice_Candidate_Agg_ID);
	}

	@Override
	public int getC_Invoice_Candidate_Agg_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_Agg_ID);
	}

	@Override
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation getC_Invoice_Candidate_HeaderAggregation_Effective()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Effective_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class);
	}

	@Override
	public void setC_Invoice_Candidate_HeaderAggregation_Effective(final de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation C_Invoice_Candidate_HeaderAggregation_Effective)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Effective_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class, C_Invoice_Candidate_HeaderAggregation_Effective);
	}

	@Override
	public void setC_Invoice_Candidate_HeaderAggregation_Effective_ID (final int C_Invoice_Candidate_HeaderAggregation_Effective_ID)
	{
		if (C_Invoice_Candidate_HeaderAggregation_Effective_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Effective_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Effective_ID, C_Invoice_Candidate_HeaderAggregation_Effective_ID);
	}

	@Override
	public int getC_Invoice_Candidate_HeaderAggregation_Effective_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Effective_ID);
	}

	@Override
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation getC_Invoice_Candidate_HeaderAggregation()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class);
	}

	@Override
	public void setC_Invoice_Candidate_HeaderAggregation(final de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation C_Invoice_Candidate_HeaderAggregation)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class, C_Invoice_Candidate_HeaderAggregation);
	}

	@Override
	public void setC_Invoice_Candidate_HeaderAggregation_ID (final int C_Invoice_Candidate_HeaderAggregation_ID)
	{
		if (C_Invoice_Candidate_HeaderAggregation_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID, C_Invoice_Candidate_HeaderAggregation_ID);
	}

	@Override
	public int getC_Invoice_Candidate_HeaderAggregation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID);
	}

	@Override
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation getC_Invoice_Candidate_HeaderAggregation_Override()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Override_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class);
	}

	@Override
	public void setC_Invoice_Candidate_HeaderAggregation_Override(final de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation C_Invoice_Candidate_HeaderAggregation_Override)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Override_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class, C_Invoice_Candidate_HeaderAggregation_Override);
	}

	@Override
	public void setC_Invoice_Candidate_HeaderAggregation_Override_ID (final int C_Invoice_Candidate_HeaderAggregation_Override_ID)
	{
		if (C_Invoice_Candidate_HeaderAggregation_Override_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Override_ID, C_Invoice_Candidate_HeaderAggregation_Override_ID);
	}

	@Override
	public int getC_Invoice_Candidate_HeaderAggregation_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Override_ID);
	}

	@Override
	public void setC_Invoice_Candidate_ID (final int C_Invoice_Candidate_ID)
	{
		if (C_Invoice_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_ID, C_Invoice_Candidate_ID);
	}

	@Override
	public int getC_Invoice_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_ID);
	}

	@Override
	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule()
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class);
	}

	@Override
	public void setC_InvoiceSchedule(final org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class, C_InvoiceSchedule);
	}

	@Override
	public void setC_InvoiceSchedule_ID (final int C_InvoiceSchedule_ID)
	{
		if (C_InvoiceSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceSchedule_ID, C_InvoiceSchedule_ID);
	}

	@Override
	public int getC_InvoiceSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceSchedule_ID);
	}

	@Override
	public void setC_Order_BPartner (final int C_Order_BPartner)
	{
		throw new IllegalArgumentException ("C_Order_BPartner is virtual column");	}

	@Override
	public int getC_Order_BPartner() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_BPartner);
	}

	@Override
	public org.compiere.model.I_C_Order_CompensationGroup getC_Order_CompensationGroup()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_CompensationGroup_ID, org.compiere.model.I_C_Order_CompensationGroup.class);
	}

	@Override
	public void setC_Order_CompensationGroup(final org.compiere.model.I_C_Order_CompensationGroup C_Order_CompensationGroup)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_CompensationGroup_ID, org.compiere.model.I_C_Order_CompensationGroup.class, C_Order_CompensationGroup);
	}

	@Override
	public void setC_Order_CompensationGroup_ID (final int C_Order_CompensationGroup_ID)
	{
		if (C_Order_CompensationGroup_ID < 1) 
			set_Value (COLUMNNAME_C_Order_CompensationGroup_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_CompensationGroup_ID, C_Order_CompensationGroup_ID);
	}

	@Override
	public int getC_Order_CompensationGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_CompensationGroup_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(final org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	@Override
	public void setC_OrderLine_ID (final int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public void setC_PaymentTerm_Effective_ID (final int C_PaymentTerm_Effective_ID)
	{
		throw new IllegalArgumentException ("C_PaymentTerm_Effective_ID is virtual column");	}

	@Override
	public int getC_PaymentTerm_Effective_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentTerm_Effective_ID);
	}

	@Override
	public void setC_PaymentTerm_ID (final int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, C_PaymentTerm_ID);
	}

	@Override
	public int getC_PaymentTerm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentTerm_ID);
	}

	@Override
	public void setC_PaymentTerm_Override_ID (final int C_PaymentTerm_Override_ID)
	{
		if (C_PaymentTerm_Override_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_Override_ID, C_PaymentTerm_Override_ID);
	}

	@Override
	public int getC_PaymentTerm_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentTerm_Override_ID);
	}

	@Override
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public void setC_Shipping_Location_ID (final int C_Shipping_Location_ID)
	{
		if (C_Shipping_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Shipping_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Shipping_Location_ID, C_Shipping_Location_ID);
	}

	@Override
	public int getC_Shipping_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Shipping_Location_ID);
	}

	@Override
	public void setC_Tax_Effective_ID (final int C_Tax_Effective_ID)
	{
		throw new IllegalArgumentException ("C_Tax_Effective_ID is virtual column");	}

	@Override
	public int getC_Tax_Effective_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_Effective_ID);
	}

	@Override
	public void setC_Tax_ID (final int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, C_Tax_ID);
	}

	@Override
	public int getC_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_ID);
	}

	@Override
	public void setC_Tax_Override_ID (final int C_Tax_Override_ID)
	{
		if (C_Tax_Override_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_Override_ID, C_Tax_Override_ID);
	}

	@Override
	public int getC_Tax_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_Override_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setDateAcct (final @Nullable java.sql.Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
	}

	@Override
	public void setDateInvoiced (final @Nullable java.sql.Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	@Override
	public java.sql.Timestamp getDateInvoiced() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateInvoiced);
	}

	@Override
	public void setDateOrdered (final @Nullable java.sql.Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	@Override
	public void setDatePromised (final @Nullable java.sql.Timestamp DatePromised)
	{
		throw new IllegalArgumentException ("DatePromised is virtual column");	}

	@Override
	public java.sql.Timestamp getDatePromised() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DatePromised);
	}

	@Override
	public void setDateToInvoice (final @Nullable java.sql.Timestamp DateToInvoice)
	{
		set_ValueNoCheck (COLUMNNAME_DateToInvoice, DateToInvoice);
	}

	@Override
	public java.sql.Timestamp getDateToInvoice() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateToInvoice);
	}

	@Override
	public void setDateToInvoice_Effective (final @Nullable java.sql.Timestamp DateToInvoice_Effective)
	{
		throw new IllegalArgumentException ("DateToInvoice_Effective is virtual column");	}

	@Override
	public java.sql.Timestamp getDateToInvoice_Effective() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateToInvoice_Effective);
	}

	@Override
	public void setDateToInvoice_Override (final @Nullable java.sql.Timestamp DateToInvoice_Override)
	{
		set_Value (COLUMNNAME_DateToInvoice_Override, DateToInvoice_Override);
	}

	@Override
	public java.sql.Timestamp getDateToInvoice_Override() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateToInvoice_Override);
	}

	@Override
	public void setDeliveryDate (final @Nullable java.sql.Timestamp DeliveryDate)
	{
		set_Value (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	@Override
	public java.sql.Timestamp getDeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveryDate);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDescriptionBottom (final @Nullable java.lang.String DescriptionBottom)
	{
		set_Value (COLUMNNAME_DescriptionBottom, DescriptionBottom);
	}

	@Override
	public java.lang.String getDescriptionBottom() 
	{
		return get_ValueAsString(COLUMNNAME_DescriptionBottom);
	}

	@Override
	public void setDescriptionHeader (final @Nullable java.lang.String DescriptionHeader)
	{
		set_Value (COLUMNNAME_DescriptionHeader, DescriptionHeader);
	}

	@Override
	public java.lang.String getDescriptionHeader() 
	{
		return get_ValueAsString(COLUMNNAME_DescriptionHeader);
	}

	@Override
	public void setDiscount (final BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	@Override
	public BigDecimal getDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Discount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDiscount_Override (final @Nullable BigDecimal Discount_Override)
	{
		set_Value (COLUMNNAME_Discount_Override, Discount_Override);
	}

	@Override
	public BigDecimal getDiscount_Override() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Discount_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setEMail (final @Nullable java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	@Override
	public java.lang.String getEMail() 
	{
		return get_ValueAsString(COLUMNNAME_EMail);
	}

	@Override
	public void setErrorMsg (final @Nullable java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	@Override
	public java.lang.String getErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_ErrorMsg);
	}

	@Override
	public void setExternalHeaderId (final @Nullable java.lang.String ExternalHeaderId)
	{
		set_Value (COLUMNNAME_ExternalHeaderId, ExternalHeaderId);
	}

	@Override
	public java.lang.String getExternalHeaderId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalHeaderId);
	}

	@Override
	public void setExternalLineId (final @Nullable java.lang.String ExternalLineId)
	{
		set_Value (COLUMNNAME_ExternalLineId, ExternalLineId);
	}

	@Override
	public java.lang.String getExternalLineId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalLineId);
	}

	@Override
	public void setFirst_Ship_BPLocation_ID (final int First_Ship_BPLocation_ID)
	{
		if (First_Ship_BPLocation_ID < 1) 
			set_Value (COLUMNNAME_First_Ship_BPLocation_ID, null);
		else 
			set_Value (COLUMNNAME_First_Ship_BPLocation_ID, First_Ship_BPLocation_ID);
	}

	@Override
	public int getFirst_Ship_BPLocation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_First_Ship_BPLocation_ID);
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
	@Override
	public void setGroupCompensationAmtType (final @Nullable java.lang.String GroupCompensationAmtType)
	{
		set_Value (COLUMNNAME_GroupCompensationAmtType, GroupCompensationAmtType);
	}

	@Override
	public java.lang.String getGroupCompensationAmtType() 
	{
		return get_ValueAsString(COLUMNNAME_GroupCompensationAmtType);
	}

	@Override
	public void setGroupCompensationBaseAmt (final @Nullable BigDecimal GroupCompensationBaseAmt)
	{
		set_Value (COLUMNNAME_GroupCompensationBaseAmt, GroupCompensationBaseAmt);
	}

	@Override
	public BigDecimal getGroupCompensationBaseAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GroupCompensationBaseAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setGroupCompensationPercentage (final @Nullable BigDecimal GroupCompensationPercentage)
	{
		set_Value (COLUMNNAME_GroupCompensationPercentage, GroupCompensationPercentage);
	}

	@Override
	public BigDecimal getGroupCompensationPercentage() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GroupCompensationPercentage);
		return bd != null ? bd : BigDecimal.ZERO;
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
	@Override
	public void setGroupCompensationType (final @Nullable java.lang.String GroupCompensationType)
	{
		set_Value (COLUMNNAME_GroupCompensationType, GroupCompensationType);
	}

	@Override
	public java.lang.String getGroupCompensationType() 
	{
		return get_ValueAsString(COLUMNNAME_GroupCompensationType);
	}

	@Override
	public void setHeaderAggregationKey (final @Nullable java.lang.String HeaderAggregationKey)
	{
		set_Value (COLUMNNAME_HeaderAggregationKey, HeaderAggregationKey);
	}

	@Override
	public java.lang.String getHeaderAggregationKey() 
	{
		return get_ValueAsString(COLUMNNAME_HeaderAggregationKey);
	}

	@Override
	public void setHeaderAggregationKeyBuilder_ID (final int HeaderAggregationKeyBuilder_ID)
	{
		if (HeaderAggregationKeyBuilder_ID < 1) 
			set_Value (COLUMNNAME_HeaderAggregationKeyBuilder_ID, null);
		else 
			set_Value (COLUMNNAME_HeaderAggregationKeyBuilder_ID, HeaderAggregationKeyBuilder_ID);
	}

	@Override
	public int getHeaderAggregationKeyBuilder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HeaderAggregationKeyBuilder_ID);
	}

	@Override
	public void setHeaderAggregationKey_Calc (final @Nullable java.lang.String HeaderAggregationKey_Calc)
	{
		set_Value (COLUMNNAME_HeaderAggregationKey_Calc, HeaderAggregationKey_Calc);
	}

	@Override
	public java.lang.String getHeaderAggregationKey_Calc() 
	{
		return get_ValueAsString(COLUMNNAME_HeaderAggregationKey_Calc);
	}

	@Override
	public void setIncotermLocation (final @Nullable java.lang.String IncotermLocation)
	{
		set_Value (COLUMNNAME_IncotermLocation, IncotermLocation);
	}

	@Override
	public java.lang.String getIncotermLocation() 
	{
		return get_ValueAsString(COLUMNNAME_IncotermLocation);
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
	@Override
	public void setInvoicableQtyBasedOn (final java.lang.String InvoicableQtyBasedOn)
	{
		set_Value (COLUMNNAME_InvoicableQtyBasedOn, InvoicableQtyBasedOn);
	}

	@Override
	public java.lang.String getInvoicableQtyBasedOn() 
	{
		return get_ValueAsString(COLUMNNAME_InvoicableQtyBasedOn);
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
	/** OrderCompletelyDelivered = C */
	public static final String INVOICERULE_OrderCompletelyDelivered = "C";
	/** After Pick = P */
	public static final String INVOICERULE_AfterPick = "P";
	@Override
	public void setInvoiceRule (final java.lang.String InvoiceRule)
	{
		set_ValueNoCheck (COLUMNNAME_InvoiceRule, InvoiceRule);
	}

	@Override
	public java.lang.String getInvoiceRule() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceRule);
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
	/** OrderCompletelyDelivered = C */
	public static final String INVOICERULE_EFFECTIVE_OrderCompletelyDelivered = "C";
	/** After Pick = P */
	public static final String INVOICERULE_EFFECTIVE_AfterPick = "P";
	@Override
	public void setInvoiceRule_Effective (final @Nullable java.lang.String InvoiceRule_Effective)
	{
		throw new IllegalArgumentException ("InvoiceRule_Effective is virtual column");	}

	@Override
	public java.lang.String getInvoiceRule_Effective() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceRule_Effective);
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
	/** OrderCompletelyDelivered = C */
	public static final String INVOICERULE_OVERRIDE_OrderCompletelyDelivered = "C";
	/** After Pick = P */
	public static final String INVOICERULE_OVERRIDE_AfterPick = "P";
	@Override
	public void setInvoiceRule_Override (final @Nullable java.lang.String InvoiceRule_Override)
	{
		set_Value (COLUMNNAME_InvoiceRule_Override, InvoiceRule_Override);
	}

	@Override
	public java.lang.String getInvoiceRule_Override() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceRule_Override);
	}

	@Override
	public void setInvoiceScheduleAmtStatus (final @Nullable java.lang.String InvoiceScheduleAmtStatus)
	{
		set_Value (COLUMNNAME_InvoiceScheduleAmtStatus, InvoiceScheduleAmtStatus);
	}

	@Override
	public java.lang.String getInvoiceScheduleAmtStatus() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceScheduleAmtStatus);
	}

	@Override
	public void setInvoicingErrorMsg (final @Nullable java.lang.String InvoicingErrorMsg)
	{
		set_Value (COLUMNNAME_InvoicingErrorMsg, InvoicingErrorMsg);
	}

	@Override
	public java.lang.String getInvoicingErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_InvoicingErrorMsg);
	}

	@Override
	public void setIsEdiEnabled (final boolean IsEdiEnabled)
	{
		set_Value (COLUMNNAME_IsEdiEnabled, IsEdiEnabled);
	}

	@Override
	public boolean isEdiEnabled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEdiEnabled);
	}

	@Override
	public void setIsEdiInvoicRecipient (final boolean IsEdiInvoicRecipient)
	{
		set_ValueNoCheck (COLUMNNAME_IsEdiInvoicRecipient, IsEdiInvoicRecipient);
	}

	@Override
	public boolean isEdiInvoicRecipient() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEdiInvoicRecipient);
	}

	@Override
	public void setIsError (final boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, IsError);
	}

	@Override
	public boolean isError() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsError);
	}

	@Override
	public void setIsFreightCost (final boolean IsFreightCost)
	{
		set_Value (COLUMNNAME_IsFreightCost, IsFreightCost);
	}

	@Override
	public boolean isFreightCost() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFreightCost);
	}

	@Override
	public void setIsGroupCompensationLine (final boolean IsGroupCompensationLine)
	{
		set_Value (COLUMNNAME_IsGroupCompensationLine, IsGroupCompensationLine);
	}

	@Override
	public boolean isGroupCompensationLine() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsGroupCompensationLine);
	}

	@Override
	public void setIsInDispute (final boolean IsInDispute)
	{
		set_Value (COLUMNNAME_IsInDispute, IsInDispute);
	}

	@Override
	public boolean isInDispute() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInDispute);
	}

	@Override
	public void setIsInOutApprovedForInvoicing (final boolean IsInOutApprovedForInvoicing)
	{
		set_ValueNoCheck (COLUMNNAME_IsInOutApprovedForInvoicing, IsInOutApprovedForInvoicing);
	}

	@Override
	public boolean isInOutApprovedForInvoicing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInOutApprovedForInvoicing);
	}

	@Override
	public void setIsInvoicingError (final boolean IsInvoicingError)
	{
		set_Value (COLUMNNAME_IsInvoicingError, IsInvoicingError);
	}

	@Override
	public boolean isInvoicingError() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvoicingError);
	}

	@Override
	public void setIsManual (final boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, IsManual);
	}

	@Override
	public boolean isManual() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManual);
	}

	@Override
	public void setIsMaterialTracking (final boolean IsMaterialTracking)
	{
		throw new IllegalArgumentException ("IsMaterialTracking is virtual column");	}

	@Override
	public boolean isMaterialTracking() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMaterialTracking);
	}

	@Override
	public void setIsPackagingMaterial (final boolean IsPackagingMaterial)
	{
		set_Value (COLUMNNAME_IsPackagingMaterial, IsPackagingMaterial);
	}

	@Override
	public boolean isPackagingMaterial() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPackagingMaterial);
	}

	@Override
	public void setIsPrinted (final boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, IsPrinted);
	}

	@Override
	public boolean isPrinted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrinted);
	}

	@Override
	public void setIsSimulation (final boolean IsSimulation)
	{
		set_Value (COLUMNNAME_IsSimulation, IsSimulation);
	}

	@Override
	public boolean isSimulation() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSimulation);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
	}

	@Override
	public void setIsTaxIncluded (final boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, IsTaxIncluded);
	}

	@Override
	public boolean isTaxIncluded() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTaxIncluded);
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
	@Override
	public void setIsTaxIncluded_Override (final @Nullable java.lang.String IsTaxIncluded_Override)
	{
		set_Value (COLUMNNAME_IsTaxIncluded_Override, IsTaxIncluded_Override);
	}

	@Override
	public java.lang.String getIsTaxIncluded_Override() 
	{
		return get_ValueAsString(COLUMNNAME_IsTaxIncluded_Override);
	}

	@Override
	public void setIsToClear (final boolean IsToClear)
	{
		set_ValueNoCheck (COLUMNNAME_IsToClear, IsToClear);
	}

	@Override
	public boolean isToClear() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsToClear);
	}

	@Override
	public void setIsToRecompute (final boolean IsToRecompute)
	{
		throw new IllegalArgumentException ("IsToRecompute is virtual column");	}

	@Override
	public boolean isToRecompute() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsToRecompute);
	}

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setLineAggregationKey (final @Nullable java.lang.String LineAggregationKey)
	{
		set_Value (COLUMNNAME_LineAggregationKey, LineAggregationKey);
	}

	@Override
	public java.lang.String getLineAggregationKey() 
	{
		return get_ValueAsString(COLUMNNAME_LineAggregationKey);
	}

	@Override
	public void setLineAggregationKeyBuilder_ID (final int LineAggregationKeyBuilder_ID)
	{
		if (LineAggregationKeyBuilder_ID < 1) 
			set_Value (COLUMNNAME_LineAggregationKeyBuilder_ID, null);
		else 
			set_Value (COLUMNNAME_LineAggregationKeyBuilder_ID, LineAggregationKeyBuilder_ID);
	}

	@Override
	public int getLineAggregationKeyBuilder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_LineAggregationKeyBuilder_ID);
	}

	@Override
	public void setLineAggregationKey_Suffix (final @Nullable java.lang.String LineAggregationKey_Suffix)
	{
		set_Value (COLUMNNAME_LineAggregationKey_Suffix, LineAggregationKey_Suffix);
	}

	@Override
	public java.lang.String getLineAggregationKey_Suffix() 
	{
		return get_ValueAsString(COLUMNNAME_LineAggregationKey_Suffix);
	}

	@Override
	public void setLineNetAmt (final @Nullable BigDecimal LineNetAmt)
	{
		set_ValueNoCheck (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	@Override
	public BigDecimal getLineNetAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_LineNetAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(final org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	@Override
	public void setM_InOut_ID (final int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, M_InOut_ID);
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
	public void setM_Material_Tracking_ID (final int M_Material_Tracking_ID)
	{
		if (M_Material_Tracking_ID < 1) 
			set_Value (COLUMNNAME_M_Material_Tracking_ID, null);
		else 
			set_Value (COLUMNNAME_M_Material_Tracking_ID, M_Material_Tracking_ID);
	}

	@Override
	public int getM_Material_Tracking_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Material_Tracking_ID);
	}

	@Override
	public void setM_PriceList_Version_ID (final int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, M_PriceList_Version_ID);
	}

	@Override
	public int getM_PriceList_Version_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_Version_ID);
	}

	@Override
	public void setM_PricingSystem_ID (final int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, M_PricingSystem_ID);
	}

	@Override
	public int getM_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PricingSystem_ID);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		throw new IllegalArgumentException ("M_Product_Category_ID is virtual column");	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_ShipmentSchedule_ID (final int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, M_ShipmentSchedule_ID);
	}

	@Override
	public int getM_ShipmentSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ID);
	}

	@Override
	public void setNetAmtInvoiced (final @Nullable BigDecimal NetAmtInvoiced)
	{
		set_Value (COLUMNNAME_NetAmtInvoiced, NetAmtInvoiced);
	}

	@Override
	public BigDecimal getNetAmtInvoiced() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_NetAmtInvoiced);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setNetAmtToInvoice (final @Nullable BigDecimal NetAmtToInvoice)
	{
		set_Value (COLUMNNAME_NetAmtToInvoice, NetAmtToInvoice);
	}

	@Override
	public BigDecimal getNetAmtToInvoice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_NetAmtToInvoice);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setNote (final @Nullable java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
	}

	@Override
	public void setpackingmaterialname (final @Nullable java.lang.String packingmaterialname)
	{
		throw new IllegalArgumentException ("packingmaterialname is virtual column");	}

	@Override
	public java.lang.String getpackingmaterialname() 
	{
		return get_ValueAsString(COLUMNNAME_packingmaterialname);
	}

	/** 
	 * PaymentRule AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULE_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULE_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULE_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULE_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULE_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULE_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULE_Mixed = "M";
	/** PayPal = L */
	public static final String PAYMENTRULE_PayPal = "L";
	/** PayPal Extern = V */
	public static final String PAYMENTRULE_PayPalExtern = "V";
	/** Kreditkarte Extern = U */
	public static final String PAYMENTRULE_KreditkarteExtern = "U";
	/** Sofortüberweisung = R */
	public static final String PAYMENTRULE_Sofortueberweisung = "R";
	@Override
	public void setPaymentRule (final @Nullable java.lang.String PaymentRule)
	{
		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	@Override
	public java.lang.String getPaymentRule() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentRule);
	}

	@Override
	public void setPOReference (final @Nullable java.lang.String POReference)
	{
		set_ValueNoCheck (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}

	@Override
	public void setPresetDateInvoiced (final @Nullable java.sql.Timestamp PresetDateInvoiced)
	{
		set_Value (COLUMNNAME_PresetDateInvoiced, PresetDateInvoiced);
	}

	@Override
	public java.sql.Timestamp getPresetDateInvoiced() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PresetDateInvoiced);
	}

	@Override
	public void setPriceActual (final @Nullable BigDecimal PriceActual)
	{
		set_ValueNoCheck (COLUMNNAME_PriceActual, PriceActual);
	}

	@Override
	public BigDecimal getPriceActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceActual);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceActual_Net_Effective (final @Nullable BigDecimal PriceActual_Net_Effective)
	{
		set_Value (COLUMNNAME_PriceActual_Net_Effective, PriceActual_Net_Effective);
	}

	@Override
	public BigDecimal getPriceActual_Net_Effective() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceActual_Net_Effective);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceActual_Override (final @Nullable BigDecimal PriceActual_Override)
	{
		set_Value (COLUMNNAME_PriceActual_Override, PriceActual_Override);
	}

	@Override
	public BigDecimal getPriceActual_Override() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceActual_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceEntered (final @Nullable BigDecimal PriceEntered)
	{
		set_Value (COLUMNNAME_PriceEntered, PriceEntered);
	}

	@Override
	public BigDecimal getPriceEntered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceEntered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceEntered_Override (final @Nullable BigDecimal PriceEntered_Override)
	{
		set_Value (COLUMNNAME_PriceEntered_Override, PriceEntered_Override);
	}

	@Override
	public BigDecimal getPriceEntered_Override() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceEntered_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPrice_UOM_ID (final int Price_UOM_ID)
	{
		if (Price_UOM_ID < 1) 
			set_Value (COLUMNNAME_Price_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Price_UOM_ID, Price_UOM_ID);
	}

	@Override
	public int getPrice_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Price_UOM_ID);
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
	@Override
	public void setPriority (final @Nullable java.lang.String Priority)
	{
		set_Value (COLUMNNAME_Priority, Priority);
	}

	@Override
	public java.lang.String getPriority() 
	{
		return get_ValueAsString(COLUMNNAME_Priority);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessed_Calc (final boolean Processed_Calc)
	{
		set_Value (COLUMNNAME_Processed_Calc, Processed_Calc);
	}

	@Override
	public boolean isProcessed_Calc() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed_Calc);
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
	@Override
	public void setProcessed_Override (final @Nullable java.lang.String Processed_Override)
	{
		set_Value (COLUMNNAME_Processed_Override, Processed_Override);
	}

	@Override
	public java.lang.String getProcessed_Override() 
	{
		return get_ValueAsString(COLUMNNAME_Processed_Override);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		throw new IllegalArgumentException ("Processing is virtual column");	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
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
	/** Nahrung = N */
	public static final String PRODUCTTYPE_Nahrung = "N";
	@Override
	public void setProductType (final @Nullable java.lang.String ProductType)
	{
		throw new IllegalArgumentException ("ProductType is virtual column");	}

	@Override
	public java.lang.String getProductType() 
	{
		return get_ValueAsString(COLUMNNAME_ProductType);
	}

	@Override
	public void setQtyDelivered (final @Nullable BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	@Override
	public BigDecimal getQtyDelivered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDeliveredInUOM (final @Nullable BigDecimal QtyDeliveredInUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInUOM, QtyDeliveredInUOM);
	}

	@Override
	public BigDecimal getQtyDeliveredInUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyEntered (final @Nullable BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	@Override
	public BigDecimal getQtyEntered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEntered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyEnteredTU (final @Nullable BigDecimal QtyEnteredTU)
	{
		set_Value (COLUMNNAME_QtyEnteredTU, QtyEnteredTU);
	}

	@Override
	public BigDecimal getQtyEnteredTU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEnteredTU);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInvoiced (final @Nullable BigDecimal QtyInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	@Override
	public BigDecimal getQtyInvoiced() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoiced);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInvoicedInUOM (final @Nullable BigDecimal QtyInvoicedInUOM)
	{
		set_ValueNoCheck (COLUMNNAME_QtyInvoicedInUOM, QtyInvoicedInUOM);
	}

	@Override
	public BigDecimal getQtyInvoicedInUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoicedInUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered (final BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrderedOverUnder (final @Nullable BigDecimal QtyOrderedOverUnder)
	{
		set_Value (COLUMNNAME_QtyOrderedOverUnder, QtyOrderedOverUnder);
	}

	@Override
	public BigDecimal getQtyOrderedOverUnder() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrderedOverUnder);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyPicked (final @Nullable BigDecimal QtyPicked)
	{
		set_Value (COLUMNNAME_QtyPicked, QtyPicked);
	}

	@Override
	public BigDecimal getQtyPicked() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPicked);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyPickedInUOM (final @Nullable BigDecimal QtyPickedInUOM)
	{
		set_Value (COLUMNNAME_QtyPickedInUOM, QtyPickedInUOM);
	}

	@Override
	public BigDecimal getQtyPickedInUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPickedInUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToInvoice (final BigDecimal QtyToInvoice)
	{
		set_Value (COLUMNNAME_QtyToInvoice, QtyToInvoice);
	}

	@Override
	public BigDecimal getQtyToInvoice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToInvoice);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToInvoiceBeforeDiscount (final BigDecimal QtyToInvoiceBeforeDiscount)
	{
		set_Value (COLUMNNAME_QtyToInvoiceBeforeDiscount, QtyToInvoiceBeforeDiscount);
	}

	@Override
	public BigDecimal getQtyToInvoiceBeforeDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToInvoiceBeforeDiscount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToInvoiceInPriceUOM (final @Nullable BigDecimal QtyToInvoiceInPriceUOM)
	{
		set_Value (COLUMNNAME_QtyToInvoiceInPriceUOM, QtyToInvoiceInPriceUOM);
	}

	@Override
	public BigDecimal getQtyToInvoiceInPriceUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToInvoiceInPriceUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToInvoiceInUOM (final @Nullable BigDecimal QtyToInvoiceInUOM)
	{
		set_Value (COLUMNNAME_QtyToInvoiceInUOM, QtyToInvoiceInUOM);
	}

	@Override
	public BigDecimal getQtyToInvoiceInUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToInvoiceInUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToInvoiceInUOM_Calc (final @Nullable BigDecimal QtyToInvoiceInUOM_Calc)
	{
		set_ValueNoCheck (COLUMNNAME_QtyToInvoiceInUOM_Calc, QtyToInvoiceInUOM_Calc);
	}

	@Override
	public BigDecimal getQtyToInvoiceInUOM_Calc() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToInvoiceInUOM_Calc);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToInvoiceInUOM_Override (final @Nullable BigDecimal QtyToInvoiceInUOM_Override)
	{
		set_Value (COLUMNNAME_QtyToInvoiceInUOM_Override, QtyToInvoiceInUOM_Override);
	}

	@Override
	public BigDecimal getQtyToInvoiceInUOM_Override()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToInvoiceInUOM_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToInvoice_Override (final @Nullable BigDecimal QtyToInvoice_Override)
	{
		set_Value (COLUMNNAME_QtyToInvoice_Override, QtyToInvoice_Override);
	}

	@Override
	public BigDecimal getQtyToInvoice_Override() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToInvoice_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToInvoice_OverrideFulfilled (final @Nullable BigDecimal QtyToInvoice_OverrideFulfilled)
	{
		set_Value (COLUMNNAME_QtyToInvoice_OverrideFulfilled, QtyToInvoice_OverrideFulfilled);
	}

	@Override
	public BigDecimal getQtyToInvoice_OverrideFulfilled() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToInvoice_OverrideFulfilled);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyWithIssues (final @Nullable BigDecimal QtyWithIssues)
	{
		set_Value (COLUMNNAME_QtyWithIssues, QtyWithIssues);
	}

	@Override
	public BigDecimal getQtyWithIssues() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyWithIssues);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyWithIssues_Effective (final @Nullable BigDecimal QtyWithIssues_Effective)
	{
		set_Value (COLUMNNAME_QtyWithIssues_Effective, QtyWithIssues_Effective);
	}

	@Override
	public BigDecimal getQtyWithIssues_Effective() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyWithIssues_Effective);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQualityDiscountPercent (final @Nullable BigDecimal QualityDiscountPercent)
	{
		set_Value (COLUMNNAME_QualityDiscountPercent, QualityDiscountPercent);
	}

	@Override
	public BigDecimal getQualityDiscountPercent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QualityDiscountPercent);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQualityDiscountPercent_Effective (final @Nullable BigDecimal QualityDiscountPercent_Effective)
	{
		throw new IllegalArgumentException ("QualityDiscountPercent_Effective is virtual column");	}

	@Override
	public BigDecimal getQualityDiscountPercent_Effective() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QualityDiscountPercent_Effective);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQualityDiscountPercent_Override (final @Nullable BigDecimal QualityDiscountPercent_Override)
	{
		set_Value (COLUMNNAME_QualityDiscountPercent_Override, QualityDiscountPercent_Override);
	}

	@Override
	public BigDecimal getQualityDiscountPercent_Override() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QualityDiscountPercent_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQualityDiscountPercent_ReceiptSchedule (final @Nullable BigDecimal QualityDiscountPercent_ReceiptSchedule)
	{
		throw new IllegalArgumentException ("QualityDiscountPercent_ReceiptSchedule is virtual column");	}

	@Override
	public BigDecimal getQualityDiscountPercent_ReceiptSchedule() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QualityDiscountPercent_ReceiptSchedule);
		return bd != null ? bd : BigDecimal.ZERO;
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
	@Override
	public void setQualityInvoiceLineGroupType (final @Nullable java.lang.String QualityInvoiceLineGroupType)
	{
		set_Value (COLUMNNAME_QualityInvoiceLineGroupType, QualityInvoiceLineGroupType);
	}

	@Override
	public java.lang.String getQualityInvoiceLineGroupType() 
	{
		return get_ValueAsString(COLUMNNAME_QualityInvoiceLineGroupType);
	}

	@Override
	public void setQualityNote_ReceiptSchedule (final @Nullable java.lang.String QualityNote_ReceiptSchedule)
	{
		throw new IllegalArgumentException ("QualityNote_ReceiptSchedule is virtual column");	}

	@Override
	public java.lang.String getQualityNote_ReceiptSchedule() 
	{
		return get_ValueAsString(COLUMNNAME_QualityNote_ReceiptSchedule);
	}

	@Override
	public void setReasonDiscount (final @Nullable java.lang.String ReasonDiscount)
	{
		set_Value (COLUMNNAME_ReasonDiscount, ReasonDiscount);
	}

	@Override
	public java.lang.String getReasonDiscount() 
	{
		return get_ValueAsString(COLUMNNAME_ReasonDiscount);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setSchedulerResult (final @Nullable java.lang.String SchedulerResult)
	{
		set_Value (COLUMNNAME_SchedulerResult, SchedulerResult);
	}

	@Override
	public java.lang.String getSchedulerResult() 
	{
		return get_ValueAsString(COLUMNNAME_SchedulerResult);
	}

	@Override
	public void setSplitAmt (final BigDecimal SplitAmt)
	{
		set_Value (COLUMNNAME_SplitAmt, SplitAmt);
	}

	@Override
	public BigDecimal getSplitAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SplitAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setStockingUOM_ID (final int StockingUOM_ID)
	{
		throw new IllegalArgumentException ("StockingUOM_ID is virtual column");	}

	@Override
	public int getStockingUOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_StockingUOM_ID);
	}

	@Override
	public void setTotalOfOrder (final @Nullable BigDecimal TotalOfOrder)
	{
		throw new IllegalArgumentException ("TotalOfOrder is virtual column");	}

	@Override
	public BigDecimal getTotalOfOrder() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalOfOrder);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotalOfOrderExcludingDiscount (final @Nullable BigDecimal TotalOfOrderExcludingDiscount)
	{
		throw new IllegalArgumentException ("TotalOfOrderExcludingDiscount is virtual column");	}

	@Override
	public BigDecimal getTotalOfOrderExcludingDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalOfOrderExcludingDiscount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUserElementString1 (final @Nullable java.lang.String UserElementString1)
	{
		set_Value (COLUMNNAME_UserElementString1, UserElementString1);
	}

	@Override
	public java.lang.String getUserElementString1() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString1);
	}

	@Override
	public void setUserElementString2 (final @Nullable java.lang.String UserElementString2)
	{
		set_Value (COLUMNNAME_UserElementString2, UserElementString2);
	}

	@Override
	public java.lang.String getUserElementString2() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString2);
	}

	@Override
	public void setUserElementString3 (final @Nullable java.lang.String UserElementString3)
	{
		set_Value (COLUMNNAME_UserElementString3, UserElementString3);
	}

	@Override
	public java.lang.String getUserElementString3() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString3);
	}

	@Override
	public void setUserElementString4 (final @Nullable java.lang.String UserElementString4)
	{
		set_Value (COLUMNNAME_UserElementString4, UserElementString4);
	}

	@Override
	public java.lang.String getUserElementString4() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString4);
	}

	@Override
	public void setUserElementString5 (final @Nullable java.lang.String UserElementString5)
	{
		set_Value (COLUMNNAME_UserElementString5, UserElementString5);
	}

	@Override
	public java.lang.String getUserElementString5() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString5);
	}

	@Override
	public void setUserElementString6 (final @Nullable java.lang.String UserElementString6)
	{
		set_Value (COLUMNNAME_UserElementString6, UserElementString6);
	}

	@Override
	public java.lang.String getUserElementString6() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString6);
	}

	@Override
	public void setUserElementString7 (final @Nullable java.lang.String UserElementString7)
	{
		set_Value (COLUMNNAME_UserElementString7, UserElementString7);
	}

	@Override
	public java.lang.String getUserElementString7() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString7);
	}
}