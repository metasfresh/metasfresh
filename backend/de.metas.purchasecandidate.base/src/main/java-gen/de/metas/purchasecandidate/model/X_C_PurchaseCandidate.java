// Generated Model - DO NOT CHANGE
package de.metas.purchasecandidate.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_PurchaseCandidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_PurchaseCandidate extends org.compiere.model.PO implements I_C_PurchaseCandidate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 863199146L;

    /** Standard Constructor */
    public X_C_PurchaseCandidate (final Properties ctx, final int C_PurchaseCandidate_ID, @Nullable final String trxName)
    {
      super (ctx, C_PurchaseCandidate_ID, trxName);
    }

    /** Load Constructor */
    public X_C_PurchaseCandidate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_OrderLine getC_OrderLineSO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLineSO(final org.compiere.model.I_C_OrderLine C_OrderLineSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLineSO);
	}

	@Override
	public void setC_OrderLineSO_ID (final int C_OrderLineSO_ID)
	{
		if (C_OrderLineSO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLineSO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLineSO_ID, C_OrderLineSO_ID);
	}

	@Override
	public int getC_OrderLineSO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLineSO_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_OrderSO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_OrderSO(final org.compiere.model.I_C_Order C_OrderSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class, C_OrderSO);
	}

	@Override
	public void setC_OrderSO_ID (final int C_OrderSO_ID)
	{
		if (C_OrderSO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderSO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderSO_ID, C_OrderSO_ID);
	}

	@Override
	public int getC_OrderSO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderSO_ID);
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
	public void setC_PurchaseCandidate_ID (final int C_PurchaseCandidate_ID)
	{
		if (C_PurchaseCandidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PurchaseCandidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PurchaseCandidate_ID, C_PurchaseCandidate_ID);
	}

	@Override
	public int getC_PurchaseCandidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PurchaseCandidate_ID);
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
	public void setDemandReference (final String DemandReference)
	{
		set_Value (COLUMNNAME_DemandReference, DemandReference);
	}

	@Override
	public String getDemandReference()
	{
		return get_ValueAsString(COLUMNNAME_DemandReference);
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
	public void setDiscountEff (final @Nullable BigDecimal DiscountEff)
	{
		set_ValueNoCheck (COLUMNNAME_DiscountEff, DiscountEff);
	}

	@Override
	public BigDecimal getDiscountEff() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DiscountEff);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDiscountInternal (final @Nullable BigDecimal DiscountInternal)
	{
		set_ValueNoCheck (COLUMNNAME_DiscountInternal, DiscountInternal);
	}

	@Override
	public BigDecimal getDiscountInternal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DiscountInternal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setExternalHeaderId (final @Nullable String ExternalHeaderId)
	{
		set_Value (COLUMNNAME_ExternalHeaderId, ExternalHeaderId);
	}

	@Override
	public String getExternalHeaderId()
	{
		return get_ValueAsString(COLUMNNAME_ExternalHeaderId);
	}

	@Override
	public void setExternalLineId (final @Nullable String ExternalLineId)
	{
		set_Value (COLUMNNAME_ExternalLineId, ExternalLineId);
	}

	@Override
	public String getExternalLineId()
	{
		return get_ValueAsString(COLUMNNAME_ExternalLineId);
	}

	@Override
	public void setExternalPurchaseOrderURL (final @Nullable String ExternalPurchaseOrderURL)
	{
		set_Value (COLUMNNAME_ExternalPurchaseOrderURL, ExternalPurchaseOrderURL);
	}

	@Override
	public String getExternalPurchaseOrderURL()
	{
		return get_ValueAsString(COLUMNNAME_ExternalPurchaseOrderURL);
	}

	@Override
	public void setIsAggregatePO (final boolean IsAggregatePO)
	{
		set_Value (COLUMNNAME_IsAggregatePO, IsAggregatePO);
	}

	@Override
	public boolean isAggregatePO() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAggregatePO);
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
	public void setIsPrepared (final boolean IsPrepared)
	{
		set_Value (COLUMNNAME_IsPrepared, IsPrepared);
	}

	@Override
	public boolean isPrepared() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrepared);
	}

	@Override
	public void setIsRequisitionCreated (final boolean IsRequisitionCreated)
	{
		set_Value (COLUMNNAME_IsRequisitionCreated, IsRequisitionCreated);
	}

	@Override
	public boolean isRequisitionCreated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRequisitionCreated);
	}

	@Override
	public void setIsSimulated (final boolean IsSimulated)
	{
		set_Value (COLUMNNAME_IsSimulated, IsSimulated);
	}

	@Override
	public boolean isSimulated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSimulated);
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
	public org.compiere.model.I_M_Forecast getM_Forecast()
	{
		return get_ValueAsPO(COLUMNNAME_M_Forecast_ID, org.compiere.model.I_M_Forecast.class);
	}

	@Override
	public void setM_Forecast(final org.compiere.model.I_M_Forecast M_Forecast)
	{
		set_ValueFromPO(COLUMNNAME_M_Forecast_ID, org.compiere.model.I_M_Forecast.class, M_Forecast);
	}

	@Override
	public void setM_Forecast_ID (final int M_Forecast_ID)
	{
		if (M_Forecast_ID < 1) 
			set_Value (COLUMNNAME_M_Forecast_ID, null);
		else 
			set_Value (COLUMNNAME_M_Forecast_ID, M_Forecast_ID);
	}

	@Override
	public int getM_Forecast_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Forecast_ID);
	}

	@Override
	public org.compiere.model.I_M_ForecastLine getM_ForecastLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_ForecastLine_ID, org.compiere.model.I_M_ForecastLine.class);
	}

	@Override
	public void setM_ForecastLine(final org.compiere.model.I_M_ForecastLine M_ForecastLine)
	{
		set_ValueFromPO(COLUMNNAME_M_ForecastLine_ID, org.compiere.model.I_M_ForecastLine.class, M_ForecastLine);
	}

	@Override
	public void setM_ForecastLine_ID (final int M_ForecastLine_ID)
	{
		if (M_ForecastLine_ID < 1) 
			set_Value (COLUMNNAME_M_ForecastLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_ForecastLine_ID, M_ForecastLine_ID);
	}

	@Override
	public int getM_ForecastLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ForecastLine_ID);
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
	public void setM_WarehousePO_ID (final int M_WarehousePO_ID)
	{
		if (M_WarehousePO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_WarehousePO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_WarehousePO_ID, M_WarehousePO_ID);
	}

	@Override
	public int getM_WarehousePO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_WarehousePO_ID);
	}

	@Override
	public void setPOReference (final @Nullable String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	@Override
	public String getPOReference()
	{
		return get_ValueAsString(COLUMNNAME_POReference);
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
	public void setPriceDifference (final @Nullable BigDecimal PriceDifference)
	{
		throw new IllegalArgumentException ("PriceDifference is virtual column");	}

	@Override
	public BigDecimal getPriceDifference() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceDifference);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceEffective (final @Nullable BigDecimal PriceEffective)
	{
		set_ValueNoCheck (COLUMNNAME_PriceEffective, PriceEffective);
	}

	@Override
	public BigDecimal getPriceEffective() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceEffective);
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
	public void setPriceInternal (final @Nullable BigDecimal PriceInternal)
	{
		set_ValueNoCheck (COLUMNNAME_PriceInternal, PriceInternal);
	}

	@Override
	public BigDecimal getPriceInternal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceInternal);
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
	public void setProfitPurchasePriceActual (final @Nullable BigDecimal ProfitPurchasePriceActual)
	{
		set_Value (COLUMNNAME_ProfitPurchasePriceActual, ProfitPurchasePriceActual);
	}

	@Override
	public BigDecimal getProfitPurchasePriceActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ProfitPurchasePriceActual);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProfitSalesPriceActual (final @Nullable BigDecimal ProfitSalesPriceActual)
	{
		set_Value (COLUMNNAME_ProfitSalesPriceActual, ProfitSalesPriceActual);
	}

	@Override
	public BigDecimal getProfitSalesPriceActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ProfitSalesPriceActual);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPurchaseDateOrdered (final java.sql.Timestamp PurchaseDateOrdered)
	{
		set_Value (COLUMNNAME_PurchaseDateOrdered, PurchaseDateOrdered);
	}

	@Override
	public java.sql.Timestamp getPurchaseDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PurchaseDateOrdered);
	}

	@Override
	public void setPurchaseDatePromised (final java.sql.Timestamp PurchaseDatePromised)
	{
		set_Value (COLUMNNAME_PurchaseDatePromised, PurchaseDatePromised);
	}

	@Override
	public java.sql.Timestamp getPurchaseDatePromised() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PurchaseDatePromised);
	}

	@Override
	public void setPurchasedQty (final @Nullable BigDecimal PurchasedQty)
	{
		set_Value (COLUMNNAME_PurchasedQty, PurchasedQty);
	}

	@Override
	public BigDecimal getPurchasedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PurchasedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPurchasePriceActual (final @Nullable BigDecimal PurchasePriceActual)
	{
		set_Value (COLUMNNAME_PurchasePriceActual, PurchasePriceActual);
	}

	@Override
	public BigDecimal getPurchasePriceActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PurchasePriceActual);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToPurchase (final BigDecimal QtyToPurchase)
	{
		set_Value (COLUMNNAME_QtyToPurchase, QtyToPurchase);
	}

	@Override
	public BigDecimal getQtyToPurchase() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToPurchase);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setReminderDate (final @Nullable java.sql.Timestamp ReminderDate)
	{
		set_Value (COLUMNNAME_ReminderDate, ReminderDate);
	}

	@Override
	public java.sql.Timestamp getReminderDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ReminderDate);
	}

	/** 
	 * Source AD_Reference_ID=541284
	 * Reference name: PO Sources
	 */
	public static final int SOURCE_AD_Reference_ID=541284;
	/** Material Disposition = MD */
	public static final String SOURCE_MaterialDisposition = "MD";
	/** Sales Order = SO */
	public static final String SOURCE_SalesOrder = "SO";
	/** API = API */
	public static final String SOURCE_API = "API";
	@Override
	public void setSource (final @Nullable String Source)
	{
		set_ValueNoCheck (COLUMNNAME_Source, Source);
	}

	@Override
	public String getSource()
	{
		return get_ValueAsString(COLUMNNAME_Source);
	}

	@Override
	public void setUserElementString1 (final @Nullable String UserElementString1)
	{
		set_Value (COLUMNNAME_UserElementString1, UserElementString1);
	}

	@Override
	public String getUserElementString1()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString1);
	}

	@Override
	public void setUserElementString2 (final @Nullable String UserElementString2)
	{
		set_Value (COLUMNNAME_UserElementString2, UserElementString2);
	}

	@Override
	public String getUserElementString2()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString2);
	}

	@Override
	public void setUserElementString3 (final @Nullable String UserElementString3)
	{
		set_Value (COLUMNNAME_UserElementString3, UserElementString3);
	}

	@Override
	public String getUserElementString3()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString3);
	}

	@Override
	public void setUserElementString4 (final @Nullable String UserElementString4)
	{
		set_Value (COLUMNNAME_UserElementString4, UserElementString4);
	}

	@Override
	public String getUserElementString4()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString4);
	}

	@Override
	public void setUserElementString5 (final @Nullable String UserElementString5)
	{
		set_Value (COLUMNNAME_UserElementString5, UserElementString5);
	}

	@Override
	public String getUserElementString5()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString5);
	}

	@Override
	public void setUserElementString6 (final @Nullable String UserElementString6)
	{
		set_Value (COLUMNNAME_UserElementString6, UserElementString6);
	}

	@Override
	public String getUserElementString6()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString6);
	}

	@Override
	public void setUserElementString7 (final @Nullable String UserElementString7)
	{
		set_Value (COLUMNNAME_UserElementString7, UserElementString7);
	}

	@Override
	public String getUserElementString7()
	{
		return get_ValueAsString(COLUMNNAME_UserElementString7);
	}

	@Override
	public void setVendor_ID (final int Vendor_ID)
	{
		if (Vendor_ID < 1) 
			set_Value (COLUMNNAME_Vendor_ID, null);
		else 
			set_Value (COLUMNNAME_Vendor_ID, Vendor_ID);
	}

	@Override
	public int getVendor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Vendor_ID);
	}
}