package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Generated Interface for M_PackagingContainer
 *
 * @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_M_PackagingContainer
{

	String Table_Name = "M_PackagingContainer";

	//	/** AD_Table_ID=540020 */
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
	void setAD_Org_ID(int AD_Org_ID);

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_PackagingContainer, Object> COLUMN_Created = new ModelColumn<>(I_M_PackagingContainer.class, "Created", null);
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
	 * Set UOM for Length.
	 * Standard Unit of Measure for Length
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_Length_ID(int C_UOM_Length_ID);

	/**
	 * Get UOM for Length.
	 * Standard Unit of Measure for Length
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_Length_ID();

	String COLUMNNAME_C_UOM_Length_ID = "C_UOM_Length_ID";

	/**
	 * Set UOM for weight.
	 * Standardmaßeinheit für Gewicht
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_Weight_ID(int C_UOM_Weight_ID);

	/**
	 * Get UOM for weight.
	 * Standardmaßeinheit für Gewicht
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_Weight_ID();

	String COLUMNNAME_C_UOM_Weight_ID = "C_UOM_Weight_ID";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription(@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable
	java.lang.String getDescription();

	ModelColumn<I_M_PackagingContainer, Object> COLUMN_Description = new ModelColumn<>(I_M_PackagingContainer.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set height.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHeight(@Nullable BigDecimal Height);

	/**
	 * Get height.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getHeight();

	ModelColumn<I_M_PackagingContainer, Object> COLUMN_Height = new ModelColumn<>(I_M_PackagingContainer.class, "Height", null);
	String COLUMNNAME_Height = "Height";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive(boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_M_PackagingContainer, Object> COLUMN_IsActive = new ModelColumn<>(I_M_PackagingContainer.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set length.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLength(@Nullable BigDecimal Length);

	/**
	 * Get length.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getLength();

	ModelColumn<I_M_PackagingContainer, Object> COLUMN_Length = new ModelColumn<>(I_M_PackagingContainer.class, "Length", null);
	String COLUMNNAME_Length = "Length";

	/**
	 * Set Max. Volumen.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMaxVolume(BigDecimal MaxVolume);

	/**
	 * Get Max. Volumen.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMaxVolume();

	ModelColumn<I_M_PackagingContainer, Object> COLUMN_MaxVolume = new ModelColumn<>(I_M_PackagingContainer.class, "MaxVolume", null);
	String COLUMNNAME_MaxVolume = "MaxVolume";

	/**
	 * Set Max Weight.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMaxWeight(BigDecimal MaxWeight);

	/**
	 * Get Max Weight.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMaxWeight();

	ModelColumn<I_M_PackagingContainer, Object> COLUMN_MaxWeight = new ModelColumn<>(I_M_PackagingContainer.class, "MaxWeight", null);
	String COLUMNNAME_MaxWeight = "MaxWeight";

	/**
	 * Set Package.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PackagingContainer_ID(int M_PackagingContainer_ID);

	/**
	 * Get Package.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PackagingContainer_ID();

	ModelColumn<I_M_PackagingContainer, Object> COLUMN_M_PackagingContainer_ID = new ModelColumn<>(I_M_PackagingContainer.class, "M_PackagingContainer_ID", null);
	String COLUMNNAME_M_PackagingContainer_ID = "M_PackagingContainer_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID(int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName(java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_M_PackagingContainer, Object> COLUMN_Name = new ModelColumn<>(I_M_PackagingContainer.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_PackagingContainer, Object> COLUMN_Updated = new ModelColumn<>(I_M_PackagingContainer.class, "Updated", null);
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
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValue(@Nullable java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable
	java.lang.String getValue();

	ModelColumn<I_M_PackagingContainer, Object> COLUMN_Value = new ModelColumn<>(I_M_PackagingContainer.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set width.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWidth(@Nullable BigDecimal Width);

	/**
	 * Get width.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getWidth();

	ModelColumn<I_M_PackagingContainer, Object> COLUMN_Width = new ModelColumn<>(I_M_PackagingContainer.class, "Width", null);
	String COLUMNNAME_Width = "Width";
}
