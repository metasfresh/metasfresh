package org.eevolution.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for PP_Product_BOMLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PP_Product_BOMLine 
{

	String Table_Name = "PP_Product_BOMLine";

//	/** AD_Table_ID=53019 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Quantity Assay.
	 * Indicated the Quantity Assay to use into Quality Order
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAssay (@Nullable BigDecimal Assay);

	/**
	 * Get Quantity Assay.
	 * Indicated the Quantity Assay to use into Quality Order
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAssay();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Assay = new ModelColumn<>(I_PP_Product_BOMLine.class, "Assay", null);
	String COLUMNNAME_Assay = "Assay";

	/**
	 * Set Backflush Group.
	 * The Grouping Components to the Backflush
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBackflushGroup (@Nullable java.lang.String BackflushGroup);

	/**
	 * Get Backflush Group.
	 * The Grouping Components to the Backflush
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBackflushGroup();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_BackflushGroup = new ModelColumn<>(I_PP_Product_BOMLine.class, "BackflushGroup", null);
	String COLUMNNAME_BackflushGroup = "BackflushGroup";

	/**
	 * Set Component Type.
	 * Component Type for a Bill of Material or Formula
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setComponentType (@Nullable java.lang.String ComponentType);

	/**
	 * Get Component Type.
	 * Component Type for a Bill of Material or Formula
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getComponentType();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_ComponentType = new ModelColumn<>(I_PP_Product_BOMLine.class, "ComponentType", null);
	String COLUMNNAME_ComponentType = "ComponentType";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Created = new ModelColumn<>(I_PP_Product_BOMLine.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set CU Label Qty.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCULabelQuanitity (@Nullable java.lang.String CULabelQuanitity);

	/**
	 * Get CU Label Qty.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCULabelQuanitity();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_CULabelQuanitity = new ModelColumn<>(I_PP_Product_BOMLine.class, "CULabelQuanitity", null);
	String COLUMNNAME_CULabelQuanitity = "CULabelQuanitity";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Description = new ModelColumn<>(I_PP_Product_BOMLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set expectedResult.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setexpectedResult (@Nullable BigDecimal expectedResult);

	/**
	 * Get expectedResult.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getexpectedResult();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_expectedResult = new ModelColumn<>(I_PP_Product_BOMLine.class, "expectedResult", null);
	String COLUMNNAME_expectedResult = "expectedResult";

	/**
	 * Set Feature.
	 * Indicated the Feature for Product Configure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFeature (@Nullable java.lang.String Feature);

	/**
	 * Get Feature.
	 * Indicated the Feature for Product Configure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFeature();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Feature = new ModelColumn<>(I_PP_Product_BOMLine.class, "Feature", null);
	String COLUMNNAME_Feature = "Feature";

	/**
	 * Set Forecast.
	 * Indicated the % of participation this component into a of the BOM Planning
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setForecast (@Nullable BigDecimal Forecast);

	/**
	 * Get Forecast.
	 * Indicated the % of participation this component into a of the BOM Planning
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getForecast();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Forecast = new ModelColumn<>(I_PP_Product_BOMLine.class, "Forecast", null);
	String COLUMNNAME_Forecast = "Forecast";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Help = new ModelColumn<>(I_PP_Product_BOMLine.class, "Help", null);
	String COLUMNNAME_Help = "Help";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Product_BOMLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Issue any product.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowIssuingAnyProduct (boolean IsAllowIssuingAnyProduct);

	/**
	 * Get Issue any product.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowIssuingAnyProduct();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IsAllowIssuingAnyProduct = new ModelColumn<>(I_PP_Product_BOMLine.class, "IsAllowIssuingAnyProduct", null);
	String COLUMNNAME_IsAllowIssuingAnyProduct = "IsAllowIssuingAnyProduct";

	/**
	 * Set Is Critical Component.
	 * Indicate that a Manufacturing Order can not begin without have this component
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsCritical (boolean IsCritical);

	/**
	 * Get Is Critical Component.
	 * Indicate that a Manufacturing Order can not begin without have this component
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isCritical();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IsCritical = new ModelColumn<>(I_PP_Product_BOMLine.class, "IsCritical", null);
	String COLUMNNAME_IsCritical = "IsCritical";

	/**
	 * Set Enforce Issuing Tolerance.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEnforceIssuingTolerance (boolean IsEnforceIssuingTolerance);

	/**
	 * Get Enforce Issuing Tolerance.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEnforceIssuingTolerance();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IsEnforceIssuingTolerance = new ModelColumn<>(I_PP_Product_BOMLine.class, "IsEnforceIssuingTolerance", null);
	String COLUMNNAME_IsEnforceIssuingTolerance = "IsEnforceIssuingTolerance";

	/**
	 * Set Manual quantity input only.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManualQtyInput (boolean IsManualQtyInput);

	/**
	 * Get Manual quantity input only.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManualQtyInput();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IsManualQtyInput = new ModelColumn<>(I_PP_Product_BOMLine.class, "IsManualQtyInput", null);
	String COLUMNNAME_IsManualQtyInput = "IsManualQtyInput";

	/**
	 * Set Is %.
	 * Indicate that this component is based in % Quantity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsQtyPercentage (boolean IsQtyPercentage);

	/**
	 * Get Is %.
	 * Indicate that this component is based in % Quantity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isQtyPercentage();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IsQtyPercentage = new ModelColumn<>(I_PP_Product_BOMLine.class, "IsQtyPercentage", null);
	String COLUMNNAME_IsQtyPercentage = "IsQtyPercentage";

	/**
	 * Set Issue Method.
	 * There are two methods for issue the components to Manufacturing Order
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIssueMethod (java.lang.String IssueMethod);

	/**
	 * Get Issue Method.
	 * There are two methods for issue the components to Manufacturing Order
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getIssueMethod();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IssueMethod = new ModelColumn<>(I_PP_Product_BOMLine.class, "IssueMethod", null);
	String COLUMNNAME_IssueMethod = "IssueMethod";

	/**
	 * Set Tolerance %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssuingTolerance_Perc (@Nullable BigDecimal IssuingTolerance_Perc);

	/**
	 * Get Tolerance %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getIssuingTolerance_Perc();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IssuingTolerance_Perc = new ModelColumn<>(I_PP_Product_BOMLine.class, "IssuingTolerance_Perc", null);
	String COLUMNNAME_IssuingTolerance_Perc = "IssuingTolerance_Perc";

	/**
	 * Set Tolerance.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssuingTolerance_Qty (@Nullable BigDecimal IssuingTolerance_Qty);

	/**
	 * Get Tolerance.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getIssuingTolerance_Qty();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IssuingTolerance_Qty = new ModelColumn<>(I_PP_Product_BOMLine.class, "IssuingTolerance_Qty", null);
	String COLUMNNAME_IssuingTolerance_Qty = "IssuingTolerance_Qty";

	/**
	 * Set Tolerance Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssuingTolerance_UOM_ID (int IssuingTolerance_UOM_ID);

	/**
	 * Get Tolerance Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getIssuingTolerance_UOM_ID();

	String COLUMNNAME_IssuingTolerance_UOM_ID = "IssuingTolerance_UOM_ID";

	/**
	 * Set Tolerance Value Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssuingTolerance_ValueType (@Nullable java.lang.String IssuingTolerance_ValueType);

	/**
	 * Get Tolerance Value Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIssuingTolerance_ValueType();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IssuingTolerance_ValueType = new ModelColumn<>(I_PP_Product_BOMLine.class, "IssuingTolerance_ValueType", null);
	String COLUMNNAME_IssuingTolerance_ValueType = "IssuingTolerance_ValueType";

	/**
	 * Set Lead Time Offset.
	 * Optional Lead Time offset before starting production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLeadTimeOffset (int LeadTimeOffset);

	/**
	 * Get Lead Time Offset.
	 * Optional Lead Time offset before starting production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLeadTimeOffset();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_LeadTimeOffset = new ModelColumn<>(I_PP_Product_BOMLine.class, "LeadTimeOffset", null);
	String COLUMNNAME_LeadTimeOffset = "LeadTimeOffset";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Line = new ModelColumn<>(I_PP_Product_BOMLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	@Nullable org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(@Nullable org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_PP_Product_BOMLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Change Notice.
	 * Bill of Materials (Engineering) Change Notice (Version)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ChangeNotice_ID (int M_ChangeNotice_ID);

	/**
	 * Get Change Notice.
	 * Bill of Materials (Engineering) Change Notice (Version)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ChangeNotice_ID();

	@Nullable org.compiere.model.I_M_ChangeNotice getM_ChangeNotice();

	void setM_ChangeNotice(@Nullable org.compiere.model.I_M_ChangeNotice M_ChangeNotice);

	ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_M_ChangeNotice> COLUMN_M_ChangeNotice_ID = new ModelColumn<>(I_PP_Product_BOMLine.class, "M_ChangeNotice_ID", org.compiere.model.I_M_ChangeNotice.class);
	String COLUMNNAME_M_ChangeNotice_ID = "M_ChangeNotice_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set % oldScrap.
	 * Indicate the % Scrap  for calculate the Scrap Quantity
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setoldScrap (@Nullable BigDecimal oldScrap);

	/**
	 * Get % oldScrap.
	 * Indicate the % Scrap  for calculate the Scrap Quantity
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getoldScrap();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_oldScrap = new ModelColumn<>(I_PP_Product_BOMLine.class, "oldScrap", null);
	String COLUMNNAME_oldScrap = "oldScrap";

	/**
	 * Set BOM & Formula Version.
	 * BOM & Formula
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Product_BOM_ID (int PP_Product_BOM_ID);

	/**
	 * Get BOM & Formula Version.
	 * BOM & Formula
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Product_BOM_ID();

	org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM();

	void setPP_Product_BOM(org.eevolution.model.I_PP_Product_BOM PP_Product_BOM);

	ModelColumn<I_PP_Product_BOMLine, org.eevolution.model.I_PP_Product_BOM> COLUMN_PP_Product_BOM_ID = new ModelColumn<>(I_PP_Product_BOMLine.class, "PP_Product_BOM_ID", org.eevolution.model.I_PP_Product_BOM.class);
	String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";

	/**
	 * Set BOM Line.
	 * BOM Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Product_BOMLine_ID (int PP_Product_BOMLine_ID);

	/**
	 * Get BOM Line.
	 * BOM Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Product_BOMLine_ID();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_PP_Product_BOMLine_ID = new ModelColumn<>(I_PP_Product_BOMLine.class, "PP_Product_BOMLine_ID", null);
	String COLUMNNAME_PP_Product_BOMLine_ID = "PP_Product_BOMLine_ID";

	/**
	 * Set Qty Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty_Attribute_ID (int Qty_Attribute_ID);

	/**
	 * Get Qty Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getQty_Attribute_ID();

	String COLUMNNAME_Qty_Attribute_ID = "Qty_Attribute_ID";

	/**
	 * Set % Qty.
	 * Indicate the Quantity % use in this Formula
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyBatch (@Nullable BigDecimal QtyBatch);

	/**
	 * Get % Qty.
	 * Indicate the Quantity % use in this Formula
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyBatch();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_QtyBatch = new ModelColumn<>(I_PP_Product_BOMLine.class, "QtyBatch", null);
	String COLUMNNAME_QtyBatch = "QtyBatch";

	/**
	 * Set Qty.
	 * Indicate the Quantity  use in this BOM
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyBOM (@Nullable BigDecimal QtyBOM);

	/**
	 * Get Qty.
	 * Indicate the Quantity  use in this BOM
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyBOM();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_QtyBOM = new ModelColumn<>(I_PP_Product_BOMLine.class, "QtyBOM", null);
	String COLUMNNAME_QtyBOM = "QtyBOM";

	/**
	 * Set % Scrap.
	 * Indicate the Scrap %  for calculate the Scrap Quantity
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setScrap (@Nullable BigDecimal Scrap);

	/**
	 * Get % Scrap.
	 * Indicate the Scrap %  for calculate the Scrap Quantity
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getScrap();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Scrap = new ModelColumn<>(I_PP_Product_BOMLine.class, "Scrap", null);
	String COLUMNNAME_Scrap = "Scrap";

	/**
	 * Set Show Sub BOM Ingredients.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShowSubBOMIngredients (boolean ShowSubBOMIngredients);

	/**
	 * Get Show Sub BOM Ingredients.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isShowSubBOMIngredients();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_ShowSubBOMIngredients = new ModelColumn<>(I_PP_Product_BOMLine.class, "ShowSubBOMIngredients", null);
	String COLUMNNAME_ShowSubBOMIngredients = "ShowSubBOMIngredients";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Product_BOMLine.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getValidFrom();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_ValidFrom = new ModelColumn<>(I_PP_Product_BOMLine.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidTo (@Nullable java.sql.Timestamp ValidTo);

	/**
	 * Get Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidTo();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_ValidTo = new ModelColumn<>(I_PP_Product_BOMLine.class, "ValidTo", null);
	String COLUMNNAME_ValidTo = "ValidTo";

	/**
	 * Set VariantGroup.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVariantGroup (@Nullable java.lang.String VariantGroup);

	/**
	 * Get VariantGroup.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVariantGroup();

	ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_VariantGroup = new ModelColumn<>(I_PP_Product_BOMLine.class, "VariantGroup", null);
	String COLUMNNAME_VariantGroup = "VariantGroup";
}
