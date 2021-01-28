// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_OrderLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_OrderLine extends org.compiere.model.PO implements I_C_OrderLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 652463218L;

    /** Standard Constructor */
    public X_C_OrderLine (final Properties ctx, final int C_OrderLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_OrderLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_OrderLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_OrgTrx_ID (final int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, AD_OrgTrx_ID);
	}

	@Override
	public int getAD_OrgTrx_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgTrx_ID);
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
	public void setBase_Commission_Points_Per_Price_UOM (final @Nullable BigDecimal Base_Commission_Points_Per_Price_UOM)
	{
		set_Value (COLUMNNAME_Base_Commission_Points_Per_Price_UOM, Base_Commission_Points_Per_Price_UOM);
	}

	@Override
	public BigDecimal getBase_Commission_Points_Per_Price_UOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Base_Commission_Points_Per_Price_UOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setBase_PricingSystem_ID (final int Base_PricingSystem_ID)
	{
		if (Base_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_Base_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_Base_PricingSystem_ID, Base_PricingSystem_ID);
	}

	@Override
	public int getBase_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Base_PricingSystem_ID);
	}

	@Override
	public void setBPartnerAddress (final @Nullable java.lang.String BPartnerAddress)
	{
		set_Value (COLUMNNAME_BPartnerAddress, BPartnerAddress);
	}

	@Override
	public java.lang.String getBPartnerAddress() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerAddress);
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
	public de.metas.order.model.I_C_CompensationGroup_SchemaLine getC_CompensationGroup_SchemaLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_CompensationGroup_SchemaLine_ID, de.metas.order.model.I_C_CompensationGroup_SchemaLine.class);
	}

	@Override
	public void setC_CompensationGroup_SchemaLine(final de.metas.order.model.I_C_CompensationGroup_SchemaLine C_CompensationGroup_SchemaLine)
	{
		set_ValueFromPO(COLUMNNAME_C_CompensationGroup_SchemaLine_ID, de.metas.order.model.I_C_CompensationGroup_SchemaLine.class, C_CompensationGroup_SchemaLine);
	}

	@Override
	public void setC_CompensationGroup_SchemaLine_ID (final int C_CompensationGroup_SchemaLine_ID)
	{
		if (C_CompensationGroup_SchemaLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_SchemaLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_SchemaLine_ID, C_CompensationGroup_SchemaLine_ID);
	}

	@Override
	public int getC_CompensationGroup_SchemaLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_SchemaLine_ID);
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
	public org.compiere.model.I_C_ProjectPhase getC_ProjectPhase()
	{
		return get_ValueAsPO(COLUMNNAME_C_ProjectPhase_ID, org.compiere.model.I_C_ProjectPhase.class);
	}

	@Override
	public void setC_ProjectPhase(final org.compiere.model.I_C_ProjectPhase C_ProjectPhase)
	{
		set_ValueFromPO(COLUMNNAME_C_ProjectPhase_ID, org.compiere.model.I_C_ProjectPhase.class, C_ProjectPhase);
	}

	@Override
	public void setC_ProjectPhase_ID (final int C_ProjectPhase_ID)
	{
		if (C_ProjectPhase_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectPhase_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectPhase_ID, C_ProjectPhase_ID);
	}

	@Override
	public int getC_ProjectPhase_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectPhase_ID);
	}

	@Override
	public org.compiere.model.I_C_ProjectTask getC_ProjectTask()
	{
		return get_ValueAsPO(COLUMNNAME_C_ProjectTask_ID, org.compiere.model.I_C_ProjectTask.class);
	}

	@Override
	public void setC_ProjectTask(final org.compiere.model.I_C_ProjectTask C_ProjectTask)
	{
		set_ValueFromPO(COLUMNNAME_C_ProjectTask_ID, org.compiere.model.I_C_ProjectTask.class, C_ProjectTask);
	}

	@Override
	public void setC_ProjectTask_ID (final int C_ProjectTask_ID)
	{
		if (C_ProjectTask_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectTask_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectTask_ID, C_ProjectTask_ID);
	}

	@Override
	public int getC_ProjectTask_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectTask_ID);
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
	public void setDateDelivered (final @Nullable java.sql.Timestamp DateDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_DateDelivered, DateDelivered);
	}

	@Override
	public java.sql.Timestamp getDateDelivered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDelivered);
	}

	@Override
	public void setDateInvoiced (final @Nullable java.sql.Timestamp DateInvoiced)
	{
		set_ValueNoCheck (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	@Override
	public java.sql.Timestamp getDateInvoiced() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateInvoiced);
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
	public void setDatePromised (final @Nullable java.sql.Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	@Override
	public java.sql.Timestamp getDatePromised() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DatePromised);
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
	public void setDiscount (final @Nullable BigDecimal Discount)
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
	public void setEnforcePriceLimit (final boolean EnforcePriceLimit)
	{
		set_Value (COLUMNNAME_EnforcePriceLimit, EnforcePriceLimit);
	}

	@Override
	public boolean isEnforcePriceLimit() 
	{
		return get_ValueAsBoolean(COLUMNNAME_EnforcePriceLimit);
	}

	@Override
	public org.eevolution.model.I_PP_Product_BOMLine getExplodedFrom_BOMLine()
	{
		return get_ValueAsPO(COLUMNNAME_ExplodedFrom_BOMLine_ID, org.eevolution.model.I_PP_Product_BOMLine.class);
	}

	@Override
	public void setExplodedFrom_BOMLine(final org.eevolution.model.I_PP_Product_BOMLine ExplodedFrom_BOMLine)
	{
		set_ValueFromPO(COLUMNNAME_ExplodedFrom_BOMLine_ID, org.eevolution.model.I_PP_Product_BOMLine.class, ExplodedFrom_BOMLine);
	}

	@Override
	public void setExplodedFrom_BOMLine_ID (final int ExplodedFrom_BOMLine_ID)
	{
		if (ExplodedFrom_BOMLine_ID < 1) 
			set_Value (COLUMNNAME_ExplodedFrom_BOMLine_ID, null);
		else 
			set_Value (COLUMNNAME_ExplodedFrom_BOMLine_ID, ExplodedFrom_BOMLine_ID);
	}

	@Override
	public int getExplodedFrom_BOMLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExplodedFrom_BOMLine_ID);
	}

	@Override
	public void setFreightAmt (final BigDecimal FreightAmt)
	{
		set_Value (COLUMNNAME_FreightAmt, FreightAmt);
	}

	@Override
	public BigDecimal getFreightAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FreightAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * FrequencyType AD_Reference_ID=283
	 * Reference name: C_Recurring Frequency
	 */
	public static final int FREQUENCYTYPE_AD_Reference_ID=283;
	/** Täglich = D */
	public static final String FREQUENCYTYPE_Taeglich = "D";
	/** Wöchentlich = W */
	public static final String FREQUENCYTYPE_Woechentlich = "W";
	/** Monatlich = M */
	public static final String FREQUENCYTYPE_Monatlich = "M";
	/** Quartalsweise = Q */
	public static final String FREQUENCYTYPE_Quartalsweise = "Q";
	@Override
	public void setFrequencyType (final java.lang.String FrequencyType)
	{
		set_Value (COLUMNNAME_FrequencyType, FrequencyType);
	}

	@Override
	public java.lang.String getFrequencyType() 
	{
		return get_ValueAsString(COLUMNNAME_FrequencyType);
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
	public void setIsCampaignPrice (final boolean IsCampaignPrice)
	{
		set_Value (COLUMNNAME_IsCampaignPrice, IsCampaignPrice);
	}

	@Override
	public boolean isCampaignPrice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCampaignPrice);
	}

	@Override
	public void setIsDescription (final boolean IsDescription)
	{
		set_Value (COLUMNNAME_IsDescription, IsDescription);
	}

	@Override
	public boolean isDescription() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDescription);
	}

	@Override
	public void setIsDiscountEditable (final boolean IsDiscountEditable)
	{
		set_Value (COLUMNNAME_IsDiscountEditable, IsDiscountEditable);
	}

	@Override
	public boolean isDiscountEditable() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDiscountEditable);
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
	public void setIsIndividualDescription (final boolean IsIndividualDescription)
	{
		set_Value (COLUMNNAME_IsIndividualDescription, IsIndividualDescription);
	}

	@Override
	public boolean isIndividualDescription() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsIndividualDescription);
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
	public void setIsManualPaymentTerm (final boolean IsManualPaymentTerm)
	{
		set_Value (COLUMNNAME_IsManualPaymentTerm, IsManualPaymentTerm);
	}

	@Override
	public boolean isManualPaymentTerm() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManualPaymentTerm);
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
	public void setIsPriceEditable (final boolean IsPriceEditable)
	{
		set_Value (COLUMNNAME_IsPriceEditable, IsPriceEditable);
	}

	@Override
	public boolean isPriceEditable() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPriceEditable);
	}

	@Override
	public void setIsSubscription (final boolean IsSubscription)
	{
		set_Value (COLUMNNAME_IsSubscription, IsSubscription);
	}

	@Override
	public boolean isSubscription() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSubscription);
	}

	@Override
	public void setIsTempPricingConditions (final boolean IsTempPricingConditions)
	{
		set_Value (COLUMNNAME_IsTempPricingConditions, IsTempPricingConditions);
	}

	@Override
	public boolean isTempPricingConditions() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTempPricingConditions);
	}

	@Override
	public void setIsUseBPartnerAddress (final boolean IsUseBPartnerAddress)
	{
		set_Value (COLUMNNAME_IsUseBPartnerAddress, IsUseBPartnerAddress);
	}

	@Override
	public boolean isUseBPartnerAddress() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUseBPartnerAddress);
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
	public void setLineNetAmt (final BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	@Override
	public BigDecimal getLineNetAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_LineNetAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_C_OrderLine getLink_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_Link_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setLink_OrderLine(final org.compiere.model.I_C_OrderLine Link_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_Link_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, Link_OrderLine);
	}

	@Override
	public void setLink_OrderLine_ID (final int Link_OrderLine_ID)
	{
		if (Link_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_Link_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_Link_OrderLine_ID, Link_OrderLine_ID);
	}

	@Override
	public int getLink_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Link_OrderLine_ID);
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
	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema()
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
	public void setM_DiscountSchema(final org.compiere.model.I_M_DiscountSchema M_DiscountSchema)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, M_DiscountSchema);
	}

	@Override
	public void setM_DiscountSchema_ID (final int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, M_DiscountSchema_ID);
	}

	@Override
	public int getM_DiscountSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchema_ID);
	}

	@Override
	public org.compiere.model.I_M_DiscountSchemaBreak getM_DiscountSchemaBreak()
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchemaBreak_ID, org.compiere.model.I_M_DiscountSchemaBreak.class);
	}

	@Override
	public void setM_DiscountSchemaBreak(final org.compiere.model.I_M_DiscountSchemaBreak M_DiscountSchemaBreak)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchemaBreak_ID, org.compiere.model.I_M_DiscountSchemaBreak.class, M_DiscountSchemaBreak);
	}

	@Override
	public void setM_DiscountSchemaBreak_ID (final int M_DiscountSchemaBreak_ID)
	{
		if (M_DiscountSchemaBreak_ID < 1) 
			set_Value (COLUMNNAME_M_DiscountSchemaBreak_ID, null);
		else 
			set_Value (COLUMNNAME_M_DiscountSchemaBreak_ID, M_DiscountSchemaBreak_ID);
	}

	@Override
	public int getM_DiscountSchemaBreak_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchemaBreak_ID);
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
	public void setM_Product_DocumentNote (final @Nullable java.lang.String M_Product_DocumentNote)
	{
		set_ValueNoCheck (COLUMNNAME_M_Product_DocumentNote, M_Product_DocumentNote);
	}

	@Override
	public java.lang.String getM_Product_DocumentNote() 
	{
		return get_ValueAsString(COLUMNNAME_M_Product_DocumentNote);
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
	public org.compiere.model.I_M_Promotion getM_Promotion()
	{
		return get_ValueAsPO(COLUMNNAME_M_Promotion_ID, org.compiere.model.I_M_Promotion.class);
	}

	@Override
	public void setM_Promotion(final org.compiere.model.I_M_Promotion M_Promotion)
	{
		set_ValueFromPO(COLUMNNAME_M_Promotion_ID, org.compiere.model.I_M_Promotion.class, M_Promotion);
	}

	@Override
	public void setM_Promotion_ID (final int M_Promotion_ID)
	{
		if (M_Promotion_ID < 1) 
			set_Value (COLUMNNAME_M_Promotion_ID, null);
		else 
			set_Value (COLUMNNAME_M_Promotion_ID, M_Promotion_ID);
	}

	@Override
	public int getM_Promotion_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Promotion_ID);
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

	@Override
	public void setNoPriceConditionsColor_ID (final int NoPriceConditionsColor_ID)
	{
		if (NoPriceConditionsColor_ID < 1) 
			set_Value (COLUMNNAME_NoPriceConditionsColor_ID, null);
		else 
			set_Value (COLUMNNAME_NoPriceConditionsColor_ID, NoPriceConditionsColor_ID);
	}

	@Override
	public int getNoPriceConditionsColor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_NoPriceConditionsColor_ID);
	}

	@Override
	public void setOrderDiscount (final @Nullable BigDecimal OrderDiscount)
	{
		set_Value (COLUMNNAME_OrderDiscount, OrderDiscount);
	}

	@Override
	public BigDecimal getOrderDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_OrderDiscount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPaymentDiscount (final @Nullable BigDecimal PaymentDiscount)
	{
		set_Value (COLUMNNAME_PaymentDiscount, PaymentDiscount);
	}

	@Override
	public BigDecimal getPaymentDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PaymentDiscount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPOCallOrderDate (final @Nullable java.sql.Timestamp POCallOrderDate)
	{
		set_Value (COLUMNNAME_POCallOrderDate, POCallOrderDate);
	}

	@Override
	public java.sql.Timestamp getPOCallOrderDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_POCallOrderDate);
	}

	@Override
	public org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class);
	}

	@Override
	public void setPP_Cost_Collector(final org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector)
	{
		set_ValueFromPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class, PP_Cost_Collector);
	}

	@Override
	public void setPP_Cost_Collector_ID (final int PP_Cost_Collector_ID)
	{
		if (PP_Cost_Collector_ID < 1) 
			set_Value (COLUMNNAME_PP_Cost_Collector_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Cost_Collector_ID, PP_Cost_Collector_ID);
	}

	@Override
	public int getPP_Cost_Collector_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Cost_Collector_ID);
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
	public void setPresetDateShipped (final @Nullable java.sql.Timestamp PresetDateShipped)
	{
		set_Value (COLUMNNAME_PresetDateShipped, PresetDateShipped);
	}

	@Override
	public java.sql.Timestamp getPresetDateShipped() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PresetDateShipped);
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
	public void setPriceCost (final @Nullable BigDecimal PriceCost)
	{
		set_Value (COLUMNNAME_PriceCost, PriceCost);
	}

	@Override
	public BigDecimal getPriceCost() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceCost);
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
	public void setPriceLimit (final BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	@Override
	public BigDecimal getPriceLimit() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceLimit);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceLimitNote (final @Nullable java.lang.String PriceLimitNote)
	{
		set_Value (COLUMNNAME_PriceLimitNote, PriceLimitNote);
	}

	@Override
	public java.lang.String getPriceLimitNote() 
	{
		return get_ValueAsString(COLUMNNAME_PriceLimitNote);
	}

	@Override
	public void setPriceList (final BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	@Override
	public BigDecimal getPriceList() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceList);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceList_Std (final @Nullable BigDecimal PriceList_Std)
	{
		set_Value (COLUMNNAME_PriceList_Std, PriceList_Std);
	}

	@Override
	public BigDecimal getPriceList_Std() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceList_Std);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceStd (final @Nullable BigDecimal PriceStd)
	{
		set_Value (COLUMNNAME_PriceStd, PriceStd);
	}

	@Override
	public BigDecimal getPriceStd() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceStd);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setProductDescription (final @Nullable java.lang.String ProductDescription)
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	@Override
	public java.lang.String getProductDescription() 
	{
		return get_ValueAsString(COLUMNNAME_ProductDescription);
	}

	@Override
	public void setProfitPriceActual (final @Nullable BigDecimal ProfitPriceActual)
	{
		set_Value (COLUMNNAME_ProfitPriceActual, ProfitPriceActual);
	}

	@Override
	public BigDecimal getProfitPriceActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ProfitPriceActual);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDelivered (final BigDecimal QtyDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	@Override
	public BigDecimal getQtyDelivered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setQtyEnteredInPriceUOM (final @Nullable BigDecimal QtyEnteredInPriceUOM)
	{
		set_ValueNoCheck (COLUMNNAME_QtyEnteredInPriceUOM, QtyEnteredInPriceUOM);
	}

	@Override
	public BigDecimal getQtyEnteredInPriceUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEnteredInPriceUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInvoiced (final BigDecimal QtyInvoiced)
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
	public void setQtyItemCapacity (final @Nullable BigDecimal QtyItemCapacity)
	{
		set_ValueNoCheck (COLUMNNAME_QtyItemCapacity, QtyItemCapacity);
	}

	@Override
	public BigDecimal getQtyItemCapacity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyItemCapacity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyLostSales (final BigDecimal QtyLostSales)
	{
		set_Value (COLUMNNAME_QtyLostSales, QtyLostSales);
	}

	@Override
	public BigDecimal getQtyLostSales() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyLostSales);
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
	public void setQtyReserved (final BigDecimal QtyReserved)
	{
		set_ValueNoCheck (COLUMNNAME_QtyReserved, QtyReserved);
	}

	@Override
	public BigDecimal getQtyReserved() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReserved);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_C_OrderLine getRef_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_Ref_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setRef_OrderLine(final org.compiere.model.I_C_OrderLine Ref_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_Ref_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, Ref_OrderLine);
	}

	@Override
	public void setRef_OrderLine_ID (final int Ref_OrderLine_ID)
	{
		if (Ref_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_Ref_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_OrderLine_ID, Ref_OrderLine_ID);
	}

	@Override
	public int getRef_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Ref_OrderLine_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getRef_ProposalLine()
	{
		return get_ValueAsPO(COLUMNNAME_Ref_ProposalLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setRef_ProposalLine(final org.compiere.model.I_C_OrderLine Ref_ProposalLine)
	{
		set_ValueFromPO(COLUMNNAME_Ref_ProposalLine_ID, org.compiere.model.I_C_OrderLine.class, Ref_ProposalLine);
	}

	@Override
	public void setRef_ProposalLine_ID (final int Ref_ProposalLine_ID)
	{
		if (Ref_ProposalLine_ID < 1) 
			set_Value (COLUMNNAME_Ref_ProposalLine_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_ProposalLine_ID, Ref_ProposalLine_ID);
	}

	@Override
	public int getRef_ProposalLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Ref_ProposalLine_ID);
	}

	@Override
	public void setRRAmt (final @Nullable BigDecimal RRAmt)
	{
		set_Value (COLUMNNAME_RRAmt, RRAmt);
	}

	@Override
	public BigDecimal getRRAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_RRAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRRStartDate (final @Nullable java.sql.Timestamp RRStartDate)
	{
		set_Value (COLUMNNAME_RRStartDate, RRStartDate);
	}

	@Override
	public java.sql.Timestamp getRRStartDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_RRStartDate);
	}

	@Override
	public void setRunsMax (final int RunsMax)
	{
		set_Value (COLUMNNAME_RunsMax, RunsMax);
	}

	@Override
	public int getRunsMax() 
	{
		return get_ValueAsInt(COLUMNNAME_RunsMax);
	}

	@Override
	public void setS_ResourceAssignment_ID (final int S_ResourceAssignment_ID)
	{
		if (S_ResourceAssignment_ID < 1) 
			set_Value (COLUMNNAME_S_ResourceAssignment_ID, null);
		else 
			set_Value (COLUMNNAME_S_ResourceAssignment_ID, S_ResourceAssignment_ID);
	}

	@Override
	public int getS_ResourceAssignment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_ResourceAssignment_ID);
	}

	/** 
	 * ShipmentAllocation_BestBefore_Policy AD_Reference_ID=541043
	 * Reference name: ShipmentAllocation_BestBefore_Policy
	 */
	public static final int SHIPMENTALLOCATION_BESTBEFORE_POLICY_AD_Reference_ID=541043;
	/** Newest_First = N */
	public static final String SHIPMENTALLOCATION_BESTBEFORE_POLICY_Newest_First = "N";
	/** Expiring_First = E */
	public static final String SHIPMENTALLOCATION_BESTBEFORE_POLICY_Expiring_First = "E";
	@Override
	public void setShipmentAllocation_BestBefore_Policy (final @Nullable java.lang.String ShipmentAllocation_BestBefore_Policy)
	{
		set_Value (COLUMNNAME_ShipmentAllocation_BestBefore_Policy, ShipmentAllocation_BestBefore_Policy);
	}

	@Override
	public java.lang.String getShipmentAllocation_BestBefore_Policy() 
	{
		return get_ValueAsString(COLUMNNAME_ShipmentAllocation_BestBefore_Policy);
	}

	@Override
	public void setTaxAmtInfo (final @Nullable BigDecimal TaxAmtInfo)
	{
		set_Value (COLUMNNAME_TaxAmtInfo, TaxAmtInfo);
	}

	@Override
	public BigDecimal getTaxAmtInfo() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmtInfo);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTraded_Commission_Percent (final @Nullable BigDecimal Traded_Commission_Percent)
	{
		set_Value (COLUMNNAME_Traded_Commission_Percent, Traded_Commission_Percent);
	}

	@Override
	public BigDecimal getTraded_Commission_Percent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Traded_Commission_Percent);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser1()
	{
		return get_ValueAsPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser1(final org.compiere.model.I_C_ElementValue User1)
	{
		set_ValueFromPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class, User1);
	}

	@Override
	public void setUser1_ID (final int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, User1_ID);
	}

	@Override
	public int getUser1_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_User1_ID);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser2()
	{
		return get_ValueAsPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser2(final org.compiere.model.I_C_ElementValue User2)
	{
		set_ValueFromPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class, User2);
	}

	@Override
	public void setUser2_ID (final int User2_ID)
	{
		if (User2_ID < 1) 
			set_Value (COLUMNNAME_User2_ID, null);
		else 
			set_Value (COLUMNNAME_User2_ID, User2_ID);
	}

	@Override
	public int getUser2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_User2_ID);
	}
}