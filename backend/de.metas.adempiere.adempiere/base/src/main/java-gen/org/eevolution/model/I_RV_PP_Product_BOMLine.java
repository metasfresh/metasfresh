package org.eevolution.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for RV_PP_Product_BOMLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_RV_PP_Product_BOMLine 
{

	String Table_Name = "RV_PP_Product_BOMLine";

//	/** AD_Table_ID=53063 */
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
	 * Set Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PInstance_ID();

	@Nullable org.compiere.model.I_AD_PInstance getAD_PInstance();

	void setAD_PInstance(@Nullable org.compiere.model.I_AD_PInstance AD_PInstance);

	ModelColumn<I_RV_PP_Product_BOMLine, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

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

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_ComponentType = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "ComponentType", null);
	String COLUMNNAME_ComponentType = "ComponentType";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_Created = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_Description = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Implosion.
	 * Implosion of a Bill of Materials refers to finding all the BOM''s in which a component is used.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImplosion (boolean Implosion);

	/**
	 * Get Implosion.
	 * Implosion of a Bill of Materials refers to finding all the BOM''s in which a component is used.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isImplosion();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_Implosion = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "Implosion", null);
	String COLUMNNAME_Implosion = "Implosion";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_IsActive = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_IsCritical = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "IsCritical", null);
	String COLUMNNAME_IsCritical = "IsCritical";

	/**
	 * Set Is Qty Percentage.
	 * Indicate that this component is based in % Quantity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsQtyPercentage (boolean IsQtyPercentage);

	/**
	 * Get Is Qty Percentage.
	 * Indicate that this component is based in % Quantity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isQtyPercentage();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_IsQtyPercentage = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "IsQtyPercentage", null);
	String COLUMNNAME_IsQtyPercentage = "IsQtyPercentage";

	/**
	 * Set Issue Method.
	 * There are two methods for issue the components to Manufacturing Order
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssueMethod (@Nullable java.lang.String IssueMethod);

	/**
	 * Get Issue Method.
	 * There are two methods for issue the components to Manufacturing Order
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIssueMethod();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_IssueMethod = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "IssueMethod", null);
	String COLUMNNAME_IssueMethod = "IssueMethod";

	/**
	 * Set Level no.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLevelNo (int LevelNo);

	/**
	 * Get Level no.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLevelNo();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_LevelNo = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "LevelNo", null);
	String COLUMNNAME_LevelNo = "LevelNo";

	/**
	 * Set Levels.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLevels (@Nullable java.lang.String Levels);

	/**
	 * Get Levels.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLevels();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_Levels = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "Levels", null);
	String COLUMNNAME_Levels = "Levels";

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

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_Line = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "Line", null);
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

	ModelColumn<I_RV_PP_Product_BOMLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set BOM & Formula Version.
	 * BOM & Formula
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Product_BOM_ID (int PP_Product_BOM_ID);

	/**
	 * Get BOM & Formula Version.
	 * BOM & Formula
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Product_BOM_ID();

	@Nullable org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM();

	void setPP_Product_BOM(@Nullable org.eevolution.model.I_PP_Product_BOM PP_Product_BOM);

	ModelColumn<I_RV_PP_Product_BOMLine, org.eevolution.model.I_PP_Product_BOM> COLUMN_PP_Product_BOM_ID = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "PP_Product_BOM_ID", org.eevolution.model.I_PP_Product_BOM.class);
	String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";

	/**
	 * Set BOM Line.
	 * BOM Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Product_BOMLine_ID (int PP_Product_BOMLine_ID);

	/**
	 * Get BOM Line.
	 * BOM Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Product_BOMLine_ID();

	@Nullable org.eevolution.model.I_PP_Product_BOMLine getPP_Product_BOMLine();

	void setPP_Product_BOMLine(@Nullable org.eevolution.model.I_PP_Product_BOMLine PP_Product_BOMLine);

	ModelColumn<I_RV_PP_Product_BOMLine, org.eevolution.model.I_PP_Product_BOMLine> COLUMN_PP_Product_BOMLine_ID = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "PP_Product_BOMLine_ID", org.eevolution.model.I_PP_Product_BOMLine.class);
	String COLUMNNAME_PP_Product_BOMLine_ID = "PP_Product_BOMLine_ID";

	/**
	 * Set Quantity in %.
	 * Indicate the Quantity % use in this Formula
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyBatch (BigDecimal QtyBatch);

	/**
	 * Get Quantity in %.
	 * Indicate the Quantity % use in this Formula
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyBatch();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_QtyBatch = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "QtyBatch", null);
	String COLUMNNAME_QtyBatch = "QtyBatch";

	/**
	 * Set Quantity.
	 * Indicate the Quantity  use in this BOM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyBOM (@Nullable BigDecimal QtyBOM);

	/**
	 * Get Quantity.
	 * Indicate the Quantity  use in this BOM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyBOM();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_QtyBOM = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "QtyBOM", null);
	String COLUMNNAME_QtyBOM = "QtyBOM";

	/**
	 * Set % Scrap.
	 * Indicate the % Scrap  for calculate the Scrap Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setScrap (@Nullable BigDecimal Scrap);

	/**
	 * Get % Scrap.
	 * Indicate the % Scrap  for calculate the Scrap Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getScrap();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_Scrap = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "Scrap", null);
	String COLUMNNAME_Scrap = "Scrap";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_SeqNo = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTM_Product_ID (int TM_Product_ID);

	/**
	 * Get Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getTM_Product_ID();

	String COLUMNNAME_TM_Product_ID = "TM_Product_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_Updated = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Valid From.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidFrom (@Nullable java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidFrom();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_ValidFrom = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidTo (@Nullable java.sql.Timestamp ValidTo);

	/**
	 * Get Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidTo();

	ModelColumn<I_RV_PP_Product_BOMLine, Object> COLUMN_ValidTo = new ModelColumn<>(I_RV_PP_Product_BOMLine.class, "ValidTo", null);
	String COLUMNNAME_ValidTo = "ValidTo";
}
