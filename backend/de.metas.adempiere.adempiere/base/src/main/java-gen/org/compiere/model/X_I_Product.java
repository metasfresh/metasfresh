/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_Product extends org.compiere.model.PO implements I_I_Product, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1888192132L;

    /** Standard Constructor */
    public X_I_Product (Properties ctx, int I_Product_ID, String trxName)
    {
      super (ctx, I_Product_ID, trxName);
    }

    /** Load Constructor */
    public X_I_Product (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Issue getAD_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setBPartner_Value (java.lang.String BPartner_Value)
	{
		set_Value (COLUMNNAME_BPartner_Value, BPartner_Value);
	}

	@Override
	public java.lang.String getBPartner_Value() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartner_Value);
	}

	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	@Override
	public void setC_DataImport_ID (int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, Integer.valueOf(C_DataImport_ID));
	}

	@Override
	public int getC_DataImport_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class);
	}

	@Override
	public void setC_DataImport_Run(org.compiere.model.I_C_DataImport_Run C_DataImport_Run)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class, C_DataImport_Run);
	}

	@Override
	public void setC_DataImport_Run_ID (int C_DataImport_Run_ID)
	{
		if (C_DataImport_Run_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, Integer.valueOf(C_DataImport_Run_ID));
	}

	@Override
	public int getC_DataImport_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_Run_ID);
	}

	@Override
	public void setC_TaxCategory_ID (int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
	}

	@Override
	public int getC_TaxCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_TaxCategory_ID);
	}

	@Override
	public void setC_TaxCategory_Name (java.lang.String C_TaxCategory_Name)
	{
		set_Value (COLUMNNAME_C_TaxCategory_Name, C_TaxCategory_Name);
	}

	@Override
	public java.lang.String getC_TaxCategory_Name() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_C_TaxCategory_Name);
	}

	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setClassification (java.lang.String Classification)
	{
		set_Value (COLUMNNAME_Classification, Classification);
	}

	@Override
	public java.lang.String getClassification() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Classification);
	}

	@Override
	public void setCostPerOrder (java.math.BigDecimal CostPerOrder)
	{
		set_Value (COLUMNNAME_CostPerOrder, CostPerOrder);
	}

	@Override
	public java.math.BigDecimal getCostPerOrder() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CostPerOrder);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCustomsTariff (java.lang.String CustomsTariff)
	{
		set_Value (COLUMNNAME_CustomsTariff, CustomsTariff);
	}

	@Override
	public java.lang.String getCustomsTariff() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CustomsTariff);
	}

	@Override
	public void setDeliveryTime_Promised (int DeliveryTime_Promised)
	{
		set_Value (COLUMNNAME_DeliveryTime_Promised, Integer.valueOf(DeliveryTime_Promised));
	}

	@Override
	public int getDeliveryTime_Promised() 
	{
		return get_ValueAsInt(COLUMNNAME_DeliveryTime_Promised);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setDescriptionURL (java.lang.String DescriptionURL)
	{
		set_Value (COLUMNNAME_DescriptionURL, DescriptionURL);
	}

	@Override
	public java.lang.String getDescriptionURL() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DescriptionURL);
	}

	@Override
	public void setDiscontinued (boolean Discontinued)
	{
		set_Value (COLUMNNAME_Discontinued, Boolean.valueOf(Discontinued));
	}

	@Override
	public boolean isDiscontinued() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Discontinued);
	}

	@Override
	public void setDiscontinuedBy (java.sql.Timestamp DiscontinuedBy)
	{
		set_Value (COLUMNNAME_DiscontinuedBy, DiscontinuedBy);
	}

	@Override
	public java.sql.Timestamp getDiscontinuedBy() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DiscontinuedBy);
	}

	@Override
	public void setDocumentNote (java.lang.String DocumentNote)
	{
		set_Value (COLUMNNAME_DocumentNote, DocumentNote);
	}

	@Override
	public java.lang.String getDocumentNote() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNote);
	}

	@Override
	public void setExternalId (java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalId);
	}

	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_ValueNoCheck (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	@Override
	public java.lang.String getI_ErrorMsg() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	@Override
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	@Override
	public boolean isI_IsImported() 
	{
		return get_ValueAsBoolean(COLUMNNAME_I_IsImported);
	}

	@Override
	public void setI_LineContent (java.lang.String I_LineContent)
	{
		set_Value (COLUMNNAME_I_LineContent, I_LineContent);
	}

	@Override
	public java.lang.String getI_LineContent() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_LineContent);
	}

	@Override
	public void setI_LineNo (int I_LineNo)
	{
		set_Value (COLUMNNAME_I_LineNo, Integer.valueOf(I_LineNo));
	}

	@Override
	public int getI_LineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_I_LineNo);
	}

	@Override
	public void setI_Product_ID (int I_Product_ID)
	{
		if (I_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_Product_ID, Integer.valueOf(I_Product_ID));
	}

	@Override
	public int getI_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_I_Product_ID);
	}

	@Override
	public void setImageURL (java.lang.String ImageURL)
	{
		set_Value (COLUMNNAME_ImageURL, ImageURL);
	}

	@Override
	public java.lang.String getImageURL() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ImageURL);
	}

	@Override
	public void setISO_Code (java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setIsSold (boolean IsSold)
	{
		set_Value (COLUMNNAME_IsSold, Boolean.valueOf(IsSold));
	}

	@Override
	public boolean isSold() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSold);
	}

	@Override
	public void setIsStocked (boolean IsStocked)
	{
		set_Value (COLUMNNAME_IsStocked, Boolean.valueOf(IsStocked));
	}

	@Override
	public boolean isStocked() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsStocked);
	}

	@Override
	public org.compiere.model.I_M_CustomsTariff getM_CustomsTariff()
	{
		return get_ValueAsPO(COLUMNNAME_M_CustomsTariff_ID, org.compiere.model.I_M_CustomsTariff.class);
	}

	@Override
	public void setM_CustomsTariff(org.compiere.model.I_M_CustomsTariff M_CustomsTariff)
	{
		set_ValueFromPO(COLUMNNAME_M_CustomsTariff_ID, org.compiere.model.I_M_CustomsTariff.class, M_CustomsTariff);
	}

	@Override
	public void setM_CustomsTariff_ID (int M_CustomsTariff_ID)
	{
		if (M_CustomsTariff_ID < 1) 
			set_Value (COLUMNNAME_M_CustomsTariff_ID, null);
		else 
			set_Value (COLUMNNAME_M_CustomsTariff_ID, Integer.valueOf(M_CustomsTariff_ID));
	}

	@Override
	public int getM_CustomsTariff_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CustomsTariff_ID);
	}

	@Override
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, Integer.valueOf(M_PriceList_Version_ID));
	}

	@Override
	public int getM_PriceList_Version_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_Version_ID);
	}

	@Override
	public void setM_PriceList_Version_Name (java.lang.String M_PriceList_Version_Name)
	{
		set_Value (COLUMNNAME_M_PriceList_Version_Name, M_PriceList_Version_Name);
	}

	@Override
	public java.lang.String getM_PriceList_Version_Name() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_M_PriceList_Version_Name);
	}

	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
	}

	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	/** 
	 * M_ProductPlanningSchema_Selector AD_Reference_ID=540829
	 * Reference name: M_ProductPlanningSchema_Selector_List
	 */
	public static final int M_PRODUCTPLANNINGSCHEMA_SELECTOR_AD_Reference_ID=540829;
	/** Normal = N */
	public static final String M_PRODUCTPLANNINGSCHEMA_SELECTOR_Normal = "N";
	/** QuotationBOMProduct = Q */
	public static final String M_PRODUCTPLANNINGSCHEMA_SELECTOR_QuotationBOMProduct = "Q";
	@Override
	public void setM_ProductPlanningSchema_Selector (java.lang.String M_ProductPlanningSchema_Selector)
	{

		set_Value (COLUMNNAME_M_ProductPlanningSchema_Selector, M_ProductPlanningSchema_Selector);
	}

	@Override
	public java.lang.String getM_ProductPlanningSchema_Selector() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_M_ProductPlanningSchema_Selector);
	}

	@Override
	public void setManufacturer_ID (int Manufacturer_ID)
	{
		if (Manufacturer_ID < 1) 
			set_Value (COLUMNNAME_Manufacturer_ID, null);
		else 
			set_Value (COLUMNNAME_Manufacturer_ID, Integer.valueOf(Manufacturer_ID));
	}

	@Override
	public int getManufacturer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Manufacturer_ID);
	}

	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public void setNetWeight (java.math.BigDecimal NetWeight)
	{
		set_Value (COLUMNNAME_NetWeight, NetWeight);
	}

	@Override
	public java.math.BigDecimal getNetWeight() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_NetWeight);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setOrder_Min (int Order_Min)
	{
		set_Value (COLUMNNAME_Order_Min, Integer.valueOf(Order_Min));
	}

	@Override
	public int getOrder_Min() 
	{
		return get_ValueAsInt(COLUMNNAME_Order_Min);
	}

	@Override
	public void setOrder_Pack (int Order_Pack)
	{
		set_Value (COLUMNNAME_Order_Pack, Integer.valueOf(Order_Pack));
	}

	@Override
	public int getOrder_Pack() 
	{
		return get_ValueAsInt(COLUMNNAME_Order_Pack);
	}

	@Override
	public void setPackage_UOM_ID (int Package_UOM_ID)
	{
		if (Package_UOM_ID < 1) 
			set_Value (COLUMNNAME_Package_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Package_UOM_ID, Integer.valueOf(Package_UOM_ID));
	}

	@Override
	public int getPackage_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Package_UOM_ID);
	}

	@Override
	public void setPackageSize (java.lang.String PackageSize)
	{
		set_Value (COLUMNNAME_PackageSize, PackageSize);
	}

	@Override
	public java.lang.String getPackageSize() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PackageSize);
	}

	@Override
	public void setPriceEffective (java.sql.Timestamp PriceEffective)
	{
		set_Value (COLUMNNAME_PriceEffective, PriceEffective);
	}

	@Override
	public java.sql.Timestamp getPriceEffective() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PriceEffective);
	}

	@Override
	public void setPriceLimit (java.math.BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	@Override
	public java.math.BigDecimal getPriceLimit() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceLimit);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceList (java.math.BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	@Override
	public java.math.BigDecimal getPriceList() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceList);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPricePO (java.math.BigDecimal PricePO)
	{
		set_Value (COLUMNNAME_PricePO, PricePO);
	}

	@Override
	public java.math.BigDecimal getPricePO() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PricePO);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceStd (java.math.BigDecimal PriceStd)
	{
		set_Value (COLUMNNAME_PriceStd, PriceStd);
	}

	@Override
	public java.math.BigDecimal getPriceStd() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceStd);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setProductCategory_Value (java.lang.String ProductCategory_Value)
	{
		set_Value (COLUMNNAME_ProductCategory_Value, ProductCategory_Value);
	}

	@Override
	public java.lang.String getProductCategory_Value() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductCategory_Value);
	}

	@Override
	public void setProductManufacturer (java.lang.String ProductManufacturer)
	{
		set_Value (COLUMNNAME_ProductManufacturer, ProductManufacturer);
	}

	@Override
	public java.lang.String getProductManufacturer() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductManufacturer);
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
	@Override
	public void setProductType (java.lang.String ProductType)
	{

		set_Value (COLUMNNAME_ProductType, ProductType);
	}

	@Override
	public java.lang.String getProductType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductType);
	}

	@Override
	public org.compiere.model.I_C_Country getRawMaterialOrigin()
	{
		return get_ValueAsPO(COLUMNNAME_RawMaterialOrigin_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setRawMaterialOrigin(org.compiere.model.I_C_Country RawMaterialOrigin)
	{
		set_ValueFromPO(COLUMNNAME_RawMaterialOrigin_ID, org.compiere.model.I_C_Country.class, RawMaterialOrigin);
	}

	@Override
	public void setRawMaterialOrigin_ID (int RawMaterialOrigin_ID)
	{
		if (RawMaterialOrigin_ID < 1) 
			set_Value (COLUMNNAME_RawMaterialOrigin_ID, null);
		else 
			set_Value (COLUMNNAME_RawMaterialOrigin_ID, Integer.valueOf(RawMaterialOrigin_ID));
	}

	@Override
	public int getRawMaterialOrigin_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_RawMaterialOrigin_ID);
	}

	@Override
	public void setRawMaterialOriginCountryCode (java.lang.String RawMaterialOriginCountryCode)
	{
		set_Value (COLUMNNAME_RawMaterialOriginCountryCode, RawMaterialOriginCountryCode);
	}

	@Override
	public java.lang.String getRawMaterialOriginCountryCode() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RawMaterialOriginCountryCode);
	}

	@Override
	public void setRoyaltyAmt (java.math.BigDecimal RoyaltyAmt)
	{
		set_Value (COLUMNNAME_RoyaltyAmt, RoyaltyAmt);
	}

	@Override
	public java.math.BigDecimal getRoyaltyAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_RoyaltyAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setShelfDepth (int ShelfDepth)
	{
		set_Value (COLUMNNAME_ShelfDepth, Integer.valueOf(ShelfDepth));
	}

	@Override
	public int getShelfDepth() 
	{
		return get_ValueAsInt(COLUMNNAME_ShelfDepth);
	}

	@Override
	public void setShelfHeight (int ShelfHeight)
	{
		set_Value (COLUMNNAME_ShelfHeight, Integer.valueOf(ShelfHeight));
	}

	@Override
	public int getShelfHeight() 
	{
		return get_ValueAsInt(COLUMNNAME_ShelfHeight);
	}

	@Override
	public void setShelfWidth (int ShelfWidth)
	{
		set_Value (COLUMNNAME_ShelfWidth, Integer.valueOf(ShelfWidth));
	}

	@Override
	public int getShelfWidth() 
	{
		return get_ValueAsInt(COLUMNNAME_ShelfWidth);
	}

	@Override
	public void setSKU (java.lang.String SKU)
	{
		set_Value (COLUMNNAME_SKU, SKU);
	}

	@Override
	public java.lang.String getSKU() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SKU);
	}

	@Override
	public void setUnitsPerPallet (int UnitsPerPallet)
	{
		set_Value (COLUMNNAME_UnitsPerPallet, Integer.valueOf(UnitsPerPallet));
	}

	@Override
	public int getUnitsPerPallet() 
	{
		return get_ValueAsInt(COLUMNNAME_UnitsPerPallet);
	}

	@Override
	public void setUPC (java.lang.String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	@Override
	public java.lang.String getUPC() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC);
	}

	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	@Override
	public void setVendorCategory (java.lang.String VendorCategory)
	{
		set_Value (COLUMNNAME_VendorCategory, VendorCategory);
	}

	@Override
	public java.lang.String getVendorCategory() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VendorCategory);
	}

	@Override
	public void setVendorProductNo (java.lang.String VendorProductNo)
	{
		set_Value (COLUMNNAME_VendorProductNo, VendorProductNo);
	}

	@Override
	public java.lang.String getVendorProductNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VendorProductNo);
	}

	@Override
	public void setVolume (int Volume)
	{
		set_Value (COLUMNNAME_Volume, Integer.valueOf(Volume));
	}

	@Override
	public int getVolume() 
	{
		return get_ValueAsInt(COLUMNNAME_Volume);
	}

	@Override
	public void setWeight (java.math.BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	@Override
	public java.math.BigDecimal getWeight() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Weight);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setX12DE355 (java.lang.String X12DE355)
	{
		set_Value (COLUMNNAME_X12DE355, X12DE355);
	}

	@Override
	public java.lang.String getX12DE355() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_X12DE355);
	}
}