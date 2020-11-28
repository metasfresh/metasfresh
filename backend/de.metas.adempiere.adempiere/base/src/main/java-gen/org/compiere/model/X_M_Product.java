/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Product extends org.compiere.model.PO implements I_M_Product, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -75218417L;

    /** Standard Constructor */
    public X_M_Product (Properties ctx, int M_Product_ID, String trxName)
    {
      super (ctx, M_Product_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product (Properties ctx, ResultSet rs, String trxName)
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
	public void setAdditional_produktinfos (java.lang.String Additional_produktinfos)
	{
		set_Value (COLUMNNAME_Additional_produktinfos, Additional_produktinfos);
	}

	@Override
	public java.lang.String getAdditional_produktinfos() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Additional_produktinfos);
	}

	@Override
	public org.compiere.model.I_C_RevenueRecognition getC_RevenueRecognition()
	{
		return get_ValueAsPO(COLUMNNAME_C_RevenueRecognition_ID, org.compiere.model.I_C_RevenueRecognition.class);
	}

	@Override
	public void setC_RevenueRecognition(org.compiere.model.I_C_RevenueRecognition C_RevenueRecognition)
	{
		set_ValueFromPO(COLUMNNAME_C_RevenueRecognition_ID, org.compiere.model.I_C_RevenueRecognition.class, C_RevenueRecognition);
	}

	@Override
	public void setC_RevenueRecognition_ID (int C_RevenueRecognition_ID)
	{
		if (C_RevenueRecognition_ID < 1) 
			set_Value (COLUMNNAME_C_RevenueRecognition_ID, null);
		else 
			set_Value (COLUMNNAME_C_RevenueRecognition_ID, Integer.valueOf(C_RevenueRecognition_ID));
	}

	@Override
	public int getC_RevenueRecognition_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_RevenueRecognition_ID);
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
	public void setCustomerLabelName (java.lang.String CustomerLabelName)
	{
		set_Value (COLUMNNAME_CustomerLabelName, CustomerLabelName);
	}

	@Override
	public java.lang.String getCustomerLabelName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CustomerLabelName);
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
	public void setGroup1 (java.lang.String Group1)
	{
		set_Value (COLUMNNAME_Group1, Group1);
	}

	@Override
	public java.lang.String getGroup1() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Group1);
	}

	@Override
	public void setGroup2 (java.lang.String Group2)
	{
		set_Value (COLUMNNAME_Group2, Group2);
	}

	@Override
	public java.lang.String getGroup2() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Group2);
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
	public void setGroupCompensationAmtType (java.lang.String GroupCompensationAmtType)
	{

		set_Value (COLUMNNAME_GroupCompensationAmtType, GroupCompensationAmtType);
	}

	@Override
	public java.lang.String getGroupCompensationAmtType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GroupCompensationAmtType);
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
	public void setGroupCompensationType (java.lang.String GroupCompensationType)
	{

		set_Value (COLUMNNAME_GroupCompensationType, GroupCompensationType);
	}

	@Override
	public java.lang.String getGroupCompensationType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GroupCompensationType);
	}

	@Override
	public void setGTIN (java.lang.String GTIN)
	{
		set_Value (COLUMNNAME_GTIN, GTIN);
	}

	@Override
	public java.lang.String getGTIN() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GTIN);
	}

	@Override
	public void setGuaranteeDaysMin (int GuaranteeDaysMin)
	{
		set_Value (COLUMNNAME_GuaranteeDaysMin, Integer.valueOf(GuaranteeDaysMin));
	}

	@Override
	public int getGuaranteeDaysMin() 
	{
		return get_ValueAsInt(COLUMNNAME_GuaranteeDaysMin);
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
	public void setIngredients (java.lang.String Ingredients)
	{
		set_Value (COLUMNNAME_Ingredients, Ingredients);
	}

	@Override
	public java.lang.String getIngredients() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Ingredients);
	}

	@Override
	public void setIsBOM (boolean IsBOM)
	{
		set_Value (COLUMNNAME_IsBOM, Boolean.valueOf(IsBOM));
	}

	@Override
	public boolean isBOM() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBOM);
	}

	@Override
	public void setIsCommissioned (boolean IsCommissioned)
	{
		set_Value (COLUMNNAME_IsCommissioned, Boolean.valueOf(IsCommissioned));
	}

	@Override
	public boolean isCommissioned() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCommissioned);
	}

	@Override
	public void setIsDropShip (boolean IsDropShip)
	{
		set_Value (COLUMNNAME_IsDropShip, Boolean.valueOf(IsDropShip));
	}

	@Override
	public boolean isDropShip() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDropShip);
	}

	@Override
	public void setIsExcludeAutoDelivery (boolean IsExcludeAutoDelivery)
	{
		set_Value (COLUMNNAME_IsExcludeAutoDelivery, Boolean.valueOf(IsExcludeAutoDelivery));
	}

	@Override
	public boolean isExcludeAutoDelivery() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExcludeAutoDelivery);
	}

	@Override
	public void setIsInvoicePrintDetails (boolean IsInvoicePrintDetails)
	{
		set_Value (COLUMNNAME_IsInvoicePrintDetails, Boolean.valueOf(IsInvoicePrintDetails));
	}

	@Override
	public boolean isInvoicePrintDetails() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvoicePrintDetails);
	}

	@Override
	public void setIsManufactured (boolean IsManufactured)
	{
		throw new IllegalArgumentException ("IsManufactured is virtual column");	}

	@Override
	public boolean isManufactured() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManufactured);
	}

	@Override
	public void setIsPickListPrintDetails (boolean IsPickListPrintDetails)
	{
		set_Value (COLUMNNAME_IsPickListPrintDetails, Boolean.valueOf(IsPickListPrintDetails));
	}

	@Override
	public boolean isPickListPrintDetails() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickListPrintDetails);
	}

	@Override
	public void setIsPurchased (boolean IsPurchased)
	{
		set_Value (COLUMNNAME_IsPurchased, Boolean.valueOf(IsPurchased));
	}

	@Override
	public boolean isPurchased() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPurchased);
	}

	@Override
	public void setIsQuotationGroupping (boolean IsQuotationGroupping)
	{
		set_Value (COLUMNNAME_IsQuotationGroupping, Boolean.valueOf(IsQuotationGroupping));
	}

	@Override
	public boolean isQuotationGroupping() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsQuotationGroupping);
	}

	@Override
	public void setIsSelfService (boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	@Override
	public boolean isSelfService() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSelfService);
	}

	@Override
	public void setIsShowEG (boolean IsShowEG)
	{
		set_Value (COLUMNNAME_IsShowEG, Boolean.valueOf(IsShowEG));
	}

	@Override
	public boolean isShowEG() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShowEG);
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
	public void setIsSummary (boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	@Override
	public boolean isSummary() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSummary);
	}

	@Override
	public void setIsVerified (boolean IsVerified)
	{
		set_ValueNoCheck (COLUMNNAME_IsVerified, Boolean.valueOf(IsVerified));
	}

	@Override
	public boolean isVerified() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsVerified);
	}

	@Override
	public void setIsWebStoreFeatured (boolean IsWebStoreFeatured)
	{
		set_Value (COLUMNNAME_IsWebStoreFeatured, Boolean.valueOf(IsWebStoreFeatured));
	}

	@Override
	public boolean isWebStoreFeatured() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsWebStoreFeatured);
	}

	@Override
	public void setLowLevel (int LowLevel)
	{
		set_Value (COLUMNNAME_LowLevel, Integer.valueOf(LowLevel));
	}

	@Override
	public int getLowLevel() 
	{
		return get_ValueAsInt(COLUMNNAME_LowLevel);
	}

	@Override
	public org.compiere.model.I_M_AttributeSet getM_AttributeSet()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class);
	}

	@Override
	public void setM_AttributeSet(org.compiere.model.I_M_AttributeSet M_AttributeSet)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class, M_AttributeSet);
	}

	@Override
	public void setM_AttributeSet_ID (int M_AttributeSet_ID)
	{
		if (M_AttributeSet_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSet_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSet_ID, Integer.valueOf(M_AttributeSet_ID));
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
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
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
	public org.compiere.model.I_M_FreightCategory getM_FreightCategory()
	{
		return get_ValueAsPO(COLUMNNAME_M_FreightCategory_ID, org.compiere.model.I_M_FreightCategory.class);
	}

	@Override
	public void setM_FreightCategory(org.compiere.model.I_M_FreightCategory M_FreightCategory)
	{
		set_ValueFromPO(COLUMNNAME_M_FreightCategory_ID, org.compiere.model.I_M_FreightCategory.class, M_FreightCategory);
	}

	@Override
	public void setM_FreightCategory_ID (int M_FreightCategory_ID)
	{
		if (M_FreightCategory_ID < 1) 
			set_Value (COLUMNNAME_M_FreightCategory_ID, null);
		else 
			set_Value (COLUMNNAME_M_FreightCategory_ID, Integer.valueOf(M_FreightCategory_ID));
	}

	@Override
	public int getM_FreightCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_FreightCategory_ID);
	}

	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
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
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** 
	 * MRP_Exclude AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int MRP_EXCLUDE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String MRP_EXCLUDE_Yes = "Y";
	/** No = N */
	public static final String MRP_EXCLUDE_No = "N";
	@Override
	public void setMRP_Exclude (java.lang.String MRP_Exclude)
	{

		set_Value (COLUMNNAME_MRP_Exclude, MRP_Exclude);
	}

	@Override
	public java.lang.String getMRP_Exclude() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MRP_Exclude);
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
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

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
	public org.compiere.model.I_R_MailText getR_MailText()
	{
		return get_ValueAsPO(COLUMNNAME_R_MailText_ID, org.compiere.model.I_R_MailText.class);
	}

	@Override
	public void setR_MailText(org.compiere.model.I_R_MailText R_MailText)
	{
		set_ValueFromPO(COLUMNNAME_R_MailText_ID, org.compiere.model.I_R_MailText.class, R_MailText);
	}

	@Override
	public void setR_MailText_ID (int R_MailText_ID)
	{
		if (R_MailText_ID < 1) 
			set_Value (COLUMNNAME_R_MailText_ID, null);
		else 
			set_Value (COLUMNNAME_R_MailText_ID, Integer.valueOf(R_MailText_ID));
	}

	@Override
	public int getR_MailText_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_MailText_ID);
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
	public org.compiere.model.I_S_ExpenseType getS_ExpenseType()
	{
		return get_ValueAsPO(COLUMNNAME_S_ExpenseType_ID, org.compiere.model.I_S_ExpenseType.class);
	}

	@Override
	public void setS_ExpenseType(org.compiere.model.I_S_ExpenseType S_ExpenseType)
	{
		set_ValueFromPO(COLUMNNAME_S_ExpenseType_ID, org.compiere.model.I_S_ExpenseType.class, S_ExpenseType);
	}

	@Override
	public void setS_ExpenseType_ID (int S_ExpenseType_ID)
	{
		if (S_ExpenseType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_ExpenseType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_ExpenseType_ID, Integer.valueOf(S_ExpenseType_ID));
	}

	@Override
	public int getS_ExpenseType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_ExpenseType_ID);
	}

	@Override
	public org.compiere.model.I_S_Resource getS_Resource()
	{
		return get_ValueAsPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource)
	{
		set_ValueFromPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class, S_Resource);
	}

	@Override
	public void setS_Resource_ID (int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
	}

	@Override
	public int getS_Resource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_ID);
	}

	@Override
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	@Override
	public int getSalesRep_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SalesRep_ID);
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
	public void setShelfHeight (java.math.BigDecimal ShelfHeight)
	{
		set_Value (COLUMNNAME_ShelfHeight, ShelfHeight);
	}

	@Override
	public java.math.BigDecimal getShelfHeight() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ShelfHeight);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setStorageDetails (java.lang.String StorageDetails)
	{
		set_Value (COLUMNNAME_StorageDetails, StorageDetails);
	}

	@Override
	public java.lang.String getStorageDetails() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StorageDetails);
	}

	@Override
	public void setUnitsPerPack (int UnitsPerPack)
	{
		set_Value (COLUMNNAME_UnitsPerPack, Integer.valueOf(UnitsPerPack));
	}

	@Override
	public int getUnitsPerPack() 
	{
		return get_ValueAsInt(COLUMNNAME_UnitsPerPack);
	}

	@Override
	public void setUnitsPerPallet (java.math.BigDecimal UnitsPerPallet)
	{
		set_Value (COLUMNNAME_UnitsPerPallet, UnitsPerPallet);
	}

	@Override
	public java.math.BigDecimal getUnitsPerPallet() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_UnitsPerPallet);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setVersionNo (java.lang.String VersionNo)
	{
		set_Value (COLUMNNAME_VersionNo, VersionNo);
	}

	@Override
	public java.lang.String getVersionNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VersionNo);
	}

	@Override
	public void setVolume (java.math.BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	@Override
	public java.math.BigDecimal getVolume() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Volume);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setWarehouse_temperature (java.lang.String Warehouse_temperature)
	{
		set_Value (COLUMNNAME_Warehouse_temperature, Warehouse_temperature);
	}

	@Override
	public java.lang.String getWarehouse_temperature() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Warehouse_temperature);
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
	public void setM_CommodityNumber_ID (int M_CommodityNumber_ID) 
	{
		if (M_CommodityNumber_ID < 1)
			set_Value (COLUMNNAME_M_CommodityNumber_ID, null);
		else
			set_Value (COLUMNNAME_M_CommodityNumber_ID, Integer.valueOf(M_CommodityNumber_ID));
	}

	@Override
	public int getM_CommodityNumber_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_CommodityNumber_ID);
	}
}