// Generated Model - DO NOT CHANGE
package de.metas.ordercandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_OLCand
 *  @author metasfresh (generated) 
 */
public class X_C_OLCand extends org.compiere.model.PO implements I_C_OLCand, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -888025760L;

    /** Standard Constructor */
    public X_C_OLCand (final Properties ctx, final int C_OLCand_ID, @Nullable final String trxName)
    {
      super (ctx, C_OLCand_ID, trxName);
    }

    /** Load Constructor */
    public X_C_OLCand (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_DataDestination_ID (final int AD_DataDestination_ID)
	{
		if (AD_DataDestination_ID < 1) 
			set_Value (COLUMNNAME_AD_DataDestination_ID, null);
		else 
			set_Value (COLUMNNAME_AD_DataDestination_ID, AD_DataDestination_ID);
	}

	@Override
	public int getAD_DataDestination_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_DataDestination_ID);
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
	public void setAD_User_EnteredBy_ID (final int AD_User_EnteredBy_ID)
	{
		if (AD_User_EnteredBy_ID < 1) 
			set_Value (COLUMNNAME_AD_User_EnteredBy_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_EnteredBy_ID, AD_User_EnteredBy_ID);
	}

	@Override
	public int getAD_User_EnteredBy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_EnteredBy_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
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
	public void setC_BPartner_Effective_ID (final int C_BPartner_Effective_ID)
	{
		throw new IllegalArgumentException ("C_BPartner_Effective_ID is virtual column");	}

	@Override
	public int getC_BPartner_Effective_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Effective_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_BPartner_Override_ID (final int C_BPartner_Override_ID)
	{
		if (C_BPartner_Override_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Override_ID, C_BPartner_Override_ID);
	}

	@Override
	public int getC_BPartner_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Override_ID);
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
	public void setC_BP_Location_Effective_ID (final int C_BP_Location_Effective_ID)
	{
		throw new IllegalArgumentException ("C_BP_Location_Effective_ID is virtual column");	}

	@Override
	public int getC_BP_Location_Effective_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Location_Effective_ID);
	}

	@Override
	public void setC_BP_Location_Override_ID (final int C_BP_Location_Override_ID)
	{
		if (C_BP_Location_Override_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Location_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Location_Override_ID, C_BP_Location_Override_ID);
	}

	@Override
	public int getC_BP_Location_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Location_Override_ID);
	}

	@Override
	public void setC_Charge_ID (final int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, C_Charge_ID);
	}

	@Override
	public int getC_Charge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Charge_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
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
	public void setC_DocTypeOrder_ID (final int C_DocTypeOrder_ID)
	{
		if (C_DocTypeOrder_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeOrder_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeOrder_ID, C_DocTypeOrder_ID);
	}

	@Override
	public int getC_DocTypeOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeOrder_ID);
	}

	@Override
	public void setC_Flatrate_Conditions_ID (final int C_Flatrate_Conditions_ID)
	{
		if (C_Flatrate_Conditions_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, C_Flatrate_Conditions_ID);
	}

	@Override
	public int getC_Flatrate_Conditions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Conditions_ID);
	}

	@Override
	public void setC_OLCand_ID (final int C_OLCand_ID)
	{
		if (C_OLCand_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_ID, C_OLCand_ID);
	}

	@Override
	public int getC_OLCand_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCand_ID);
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
	public void setC_TaxCategory_ID (final int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, C_TaxCategory_ID);
	}

	@Override
	public int getC_TaxCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_TaxCategory_ID);
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
	public void setC_UOM_Internal_ID (final int C_UOM_Internal_ID)
	{
		if (C_UOM_Internal_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Internal_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Internal_ID, C_UOM_Internal_ID);
	}

	@Override
	public int getC_UOM_Internal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_Internal_ID);
	}

	@Override
	public void setDateCandidate (final java.sql.Timestamp DateCandidate)
	{
		set_Value (COLUMNNAME_DateCandidate, DateCandidate);
	}

	@Override
	public java.sql.Timestamp getDateCandidate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateCandidate);
	}

	@Override
	public void setDateOrdered (final java.sql.Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	@Override
	public void setDatePromised (final java.sql.Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	@Override
	public java.sql.Timestamp getDatePromised() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DatePromised);
	}

	@Override
	public void setDatePromised_Effective (final java.sql.Timestamp DatePromised_Effective)
	{
		throw new IllegalArgumentException ("DatePromised_Effective is virtual column");	}

	@Override
	public java.sql.Timestamp getDatePromised_Effective() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DatePromised_Effective);
	}

	@Override
	public void setDatePromised_Override (final java.sql.Timestamp DatePromised_Override)
	{
		set_Value (COLUMNNAME_DatePromised_Override, DatePromised_Override);
	}

	@Override
	public java.sql.Timestamp getDatePromised_Override() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DatePromised_Override);
	}

	/** 
	 * DeliveryRule AD_Reference_ID=151
	 * Reference name: C_Order DeliveryRule
	 */
	public static final int DELIVERYRULE_AD_Reference_ID=151;
	/** AfterReceipt = R */
	public static final String DELIVERYRULE_AfterReceipt = "R";
	/** Availability = A */
	public static final String DELIVERYRULE_Availability = "A";
	/** CompleteLine = L */
	public static final String DELIVERYRULE_CompleteLine = "L";
	/** CompleteOrder = O */
	public static final String DELIVERYRULE_CompleteOrder = "O";
	/** Force = F */
	public static final String DELIVERYRULE_Force = "F";
	/** Manual = M */
	public static final String DELIVERYRULE_Manual = "M";
	/** MitNaechsterAbolieferung = S */
	public static final String DELIVERYRULE_MitNaechsterAbolieferung = "S";
	@Override
	public void setDeliveryRule (final java.lang.String DeliveryRule)
	{
		set_Value (COLUMNNAME_DeliveryRule, DeliveryRule);
	}

	@Override
	public java.lang.String getDeliveryRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryRule);
	}

	/** 
	 * DeliveryViaRule AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int DELIVERYVIARULE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String DELIVERYVIARULE_Pickup = "P";
	/** Delivery = D */
	public static final String DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_Shipper = "S";
	@Override
	public void setDeliveryViaRule (final java.lang.String DeliveryViaRule)
	{
		set_Value (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	@Override
	public java.lang.String getDeliveryViaRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryViaRule);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDescriptionBottom (final java.lang.String DescriptionBottom)
	{
		set_Value (COLUMNNAME_DescriptionBottom, DescriptionBottom);
	}

	@Override
	public java.lang.String getDescriptionBottom() 
	{
		return get_ValueAsString(COLUMNNAME_DescriptionBottom);
	}

	@Override
	public void setDescriptionHeader (final java.lang.String DescriptionHeader)
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
	public void setDropShip_BPartner_Effective_ID (final int DropShip_BPartner_Effective_ID)
	{
		throw new IllegalArgumentException ("DropShip_BPartner_Effective_ID is virtual column");	}

	@Override
	public int getDropShip_BPartner_Effective_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_BPartner_Effective_ID);
	}

	@Override
	public void setDropShip_BPartner_ID (final int DropShip_BPartner_ID)
	{
		if (DropShip_BPartner_ID < 1) 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, DropShip_BPartner_ID);
	}

	@Override
	public int getDropShip_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_BPartner_ID);
	}

	@Override
	public void setDropShip_BPartner_Override_ID (final int DropShip_BPartner_Override_ID)
	{
		if (DropShip_BPartner_Override_ID < 1) 
			set_Value (COLUMNNAME_DropShip_BPartner_Override_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_BPartner_Override_ID, DropShip_BPartner_Override_ID);
	}

	@Override
	public int getDropShip_BPartner_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_BPartner_Override_ID);
	}

	@Override
	public void setDropShip_Location_Effective_ID (final int DropShip_Location_Effective_ID)
	{
		throw new IllegalArgumentException ("DropShip_Location_Effective_ID is virtual column");	}

	@Override
	public int getDropShip_Location_Effective_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_Location_Effective_ID);
	}

	@Override
	public void setDropShip_Location_ID (final int DropShip_Location_ID)
	{
		if (DropShip_Location_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_ID, DropShip_Location_ID);
	}

	@Override
	public int getDropShip_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_Location_ID);
	}

	@Override
	public void setDropShip_Location_Override_ID (final int DropShip_Location_Override_ID)
	{
		if (DropShip_Location_Override_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_Override_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_Override_ID, DropShip_Location_Override_ID);
	}

	@Override
	public int getDropShip_Location_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_Location_Override_ID);
	}

	@Override
	public void setDropShip_User_ID (final int DropShip_User_ID)
	{
		if (DropShip_User_ID < 1) 
			set_Value (COLUMNNAME_DropShip_User_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_User_ID, DropShip_User_ID);
	}

	@Override
	public int getDropShip_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_User_ID);
	}

	@Override
	public void setErrorMsg (final java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	@Override
	public java.lang.String getErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_ErrorMsg);
	}

	@Override
	public void setExternalHeaderId (final java.lang.String ExternalHeaderId)
	{
		set_Value (COLUMNNAME_ExternalHeaderId, ExternalHeaderId);
	}

	@Override
	public java.lang.String getExternalHeaderId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalHeaderId);
	}

	@Override
	public void setExternalLineId (final java.lang.String ExternalLineId)
	{
		set_Value (COLUMNNAME_ExternalLineId, ExternalLineId);
	}

	@Override
	public java.lang.String getExternalLineId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalLineId);
	}

	@Override
	public void setHandOver_Location_Effective_ID (final int HandOver_Location_Effective_ID)
	{
		throw new IllegalArgumentException ("HandOver_Location_Effective_ID is virtual column");	}

	@Override
	public int getHandOver_Location_Effective_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Location_Effective_ID);
	}

	@Override
	public void setHandOver_Location_ID (final int HandOver_Location_ID)
	{
		if (HandOver_Location_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Location_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Location_ID, HandOver_Location_ID);
	}

	@Override
	public int getHandOver_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Location_ID);
	}

	@Override
	public void setHandOver_Location_Override_ID (final int HandOver_Location_Override_ID)
	{
		if (HandOver_Location_Override_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Location_Override_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Location_Override_ID, HandOver_Location_Override_ID);
	}

	@Override
	public int getHandOver_Location_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Location_Override_ID);
	}

	@Override
	public void setHandOver_Partner_Effective_ID (final int HandOver_Partner_Effective_ID)
	{
		throw new IllegalArgumentException ("HandOver_Partner_Effective_ID is virtual column");	}

	@Override
	public int getHandOver_Partner_Effective_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Partner_Effective_ID);
	}

	@Override
	public void setHandOver_Partner_ID (final int HandOver_Partner_ID)
	{
		if (HandOver_Partner_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Partner_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Partner_ID, HandOver_Partner_ID);
	}

	@Override
	public int getHandOver_Partner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Partner_ID);
	}

	@Override
	public void setHandOver_Partner_Override_ID (final int HandOver_Partner_Override_ID)
	{
		if (HandOver_Partner_Override_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Partner_Override_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Partner_Override_ID, HandOver_Partner_Override_ID);
	}

	@Override
	public int getHandOver_Partner_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Partner_Override_ID);
	}

	@Override
	public void setHandOver_User_ID (final int HandOver_User_ID)
	{
		if (HandOver_User_ID < 1) 
			set_Value (COLUMNNAME_HandOver_User_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_User_ID, HandOver_User_ID);
	}

	@Override
	public int getHandOver_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_User_ID);
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
	public void setIsExplicitProductPriceAttribute (final boolean IsExplicitProductPriceAttribute)
	{
		set_Value (COLUMNNAME_IsExplicitProductPriceAttribute, IsExplicitProductPriceAttribute);
	}

	@Override
	public boolean isExplicitProductPriceAttribute() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExplicitProductPriceAttribute);
	}

	@Override
	public void setIsManualDiscount (final boolean IsManualDiscount)
	{
		set_Value (COLUMNNAME_IsManualDiscount, IsManualDiscount);
	}

	@Override
	public boolean isManualDiscount() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManualDiscount);
	}

	@Override
	public void setIsManualPrice (final boolean IsManualPrice)
	{
		set_Value (COLUMNNAME_IsManualPrice, IsManualPrice);
	}

	@Override
	public boolean isManualPrice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManualPrice);
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
	public org.compiere.model.I_M_AttributeSet getM_AttributeSet()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class);
	}

	@Override
	public void setM_AttributeSet(final org.compiere.model.I_M_AttributeSet M_AttributeSet)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class, M_AttributeSet);
	}

	@Override
	public void setM_AttributeSet_ID (final int M_AttributeSet_ID)
	{
		if (M_AttributeSet_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSet_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSet_ID, M_AttributeSet_ID);
	}

	@Override
	public int getM_AttributeSet_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSet_ID);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public void setM_HU_PI_Item_Product_Effective_ID (final int M_HU_PI_Item_Product_Effective_ID)
	{
		throw new IllegalArgumentException ("M_HU_PI_Item_Product_Effective_ID is virtual column");	}

	@Override
	public int getM_HU_PI_Item_Product_Effective_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_Product_Effective_ID);
	}

	@Override
	public void setM_HU_PI_Item_Product_ID (final int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, M_HU_PI_Item_Product_ID);
	}

	@Override
	public int getM_HU_PI_Item_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_Product_ID);
	}

	@Override
	public void setM_HU_PI_Item_Product_Override_ID (final int M_HU_PI_Item_Product_Override_ID)
	{
		if (M_HU_PI_Item_Product_Override_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_Override_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_Override_ID, M_HU_PI_Item_Product_Override_ID);
	}

	@Override
	public int getM_HU_PI_Item_Product_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_Product_Override_ID);
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
	public void setM_Product_Effective_ID (final int M_Product_Effective_ID)
	{
		throw new IllegalArgumentException ("M_Product_Effective_ID is virtual column");	}

	@Override
	public int getM_Product_Effective_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Effective_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_Product_Override_ID (final int M_Product_Override_ID)
	{
		if (M_Product_Override_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Override_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Override_ID, M_Product_Override_ID);
	}

	@Override
	public int getM_Product_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Override_ID);
	}

	@Override
	public void setM_ProductPrice_Attribute_ID (final int M_ProductPrice_Attribute_ID)
	{
		if (M_ProductPrice_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Attribute_ID, M_ProductPrice_Attribute_ID);
	}

	@Override
	public int getM_ProductPrice_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ProductPrice_Attribute_ID);
	}

	@Override
	public org.compiere.model.I_M_ProductPrice getM_ProductPrice()
	{
		return get_ValueAsPO(COLUMNNAME_M_ProductPrice_ID, org.compiere.model.I_M_ProductPrice.class);
	}

	@Override
	public void setM_ProductPrice(final org.compiere.model.I_M_ProductPrice M_ProductPrice)
	{
		set_ValueFromPO(COLUMNNAME_M_ProductPrice_ID, org.compiere.model.I_M_ProductPrice.class, M_ProductPrice);
	}

	@Override
	public void setM_ProductPrice_ID (final int M_ProductPrice_ID)
	{
		if (M_ProductPrice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_ID, M_ProductPrice_ID);
	}

	@Override
	public int getM_ProductPrice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ProductPrice_ID);
	}

	@Override
	public org.compiere.model.I_M_Shipper getM_Shipper()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(final org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	@Override
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setM_Warehouse_Dest_ID (final int M_Warehouse_Dest_ID)
	{
		if (M_Warehouse_Dest_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_Dest_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_Dest_ID, M_Warehouse_Dest_ID);
	}

	@Override
	public int getM_Warehouse_Dest_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Dest_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
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
	@Override
	public void setPaymentRule (final java.lang.String PaymentRule)
	{
		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	@Override
	public java.lang.String getPaymentRule() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentRule);
	}

	@Override
	public void setPOReference (final java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}

	@Override
	public void setPresetDateInvoiced (final java.sql.Timestamp PresetDateInvoiced)
	{
		set_Value (COLUMNNAME_PresetDateInvoiced, PresetDateInvoiced);
	}

	@Override
	public java.sql.Timestamp getPresetDateInvoiced() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PresetDateInvoiced);
	}

	@Override
	public void setPresetDateShipped (final java.sql.Timestamp PresetDateShipped)
	{
		set_Value (COLUMNNAME_PresetDateShipped, PresetDateShipped);
	}

	@Override
	public java.sql.Timestamp getPresetDateShipped() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PresetDateShipped);
	}

	@Override
	public void setPriceActual (final BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	@Override
	public BigDecimal getPriceActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceActual);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceDifference (final BigDecimal PriceDifference)
	{
		throw new IllegalArgumentException ("PriceDifference is virtual column");	}

	@Override
	public BigDecimal getPriceDifference() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceDifference);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceEntered (final BigDecimal PriceEntered)
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
	public void setPriceInternal (final BigDecimal PriceInternal)
	{
		set_Value (COLUMNNAME_PriceInternal, PriceInternal);
	}

	@Override
	public BigDecimal getPriceInternal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceInternal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPrice_UOM_Internal_ID (final int Price_UOM_Internal_ID)
	{
		if (Price_UOM_Internal_ID < 1) 
			set_Value (COLUMNNAME_Price_UOM_Internal_ID, null);
		else 
			set_Value (COLUMNNAME_Price_UOM_Internal_ID, Price_UOM_Internal_ID);
	}

	@Override
	public int getPrice_UOM_Internal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Price_UOM_Internal_ID);
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
	public void setProductDescription (final java.lang.String ProductDescription)
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	@Override
	public java.lang.String getProductDescription() 
	{
		return get_ValueAsString(COLUMNNAME_ProductDescription);
	}

	@Override
	public void setQtyEntered (final BigDecimal QtyEntered)
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
	public void setQtyItemCapacity (final BigDecimal QtyItemCapacity)
	{
		set_Value (COLUMNNAME_QtyItemCapacity, QtyItemCapacity);
	}

	@Override
	public BigDecimal getQtyItemCapacity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyItemCapacity);
		return bd != null ? bd : BigDecimal.ZERO;
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
}