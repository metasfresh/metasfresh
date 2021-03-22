package de.metas.vertical.healthcare.alberta.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for M_Product_AlbertaPackagingUnit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Product_AlbertaPackagingUnit 
{

	String Table_Name = "M_Product_AlbertaPackagingUnit";

//	/** AD_Table_ID=541598 */
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
	 * Set Unit.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setArticleUnit (String ArticleUnit);

	/**
	 * Get Unit.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getArticleUnit();

	ModelColumn<I_M_Product_AlbertaPackagingUnit, Object> COLUMN_ArticleUnit = new ModelColumn<>(I_M_Product_AlbertaPackagingUnit.class, "ArticleUnit", null);
	String COLUMNNAME_ArticleUnit = "ArticleUnit";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Product_AlbertaPackagingUnit, Object> COLUMN_Created = new ModelColumn<>(I_M_Product_AlbertaPackagingUnit.class, "Created", null);
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

	ModelColumn<I_M_Product_AlbertaPackagingUnit, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Product_AlbertaPackagingUnit.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Alberta packaging units.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_AlbertaPackagingUnit_ID (int M_Product_AlbertaPackagingUnit_ID);

	/**
	 * Get Alberta packaging units.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_AlbertaPackagingUnit_ID();

	ModelColumn<I_M_Product_AlbertaPackagingUnit, Object> COLUMN_M_Product_AlbertaPackagingUnit_ID = new ModelColumn<>(I_M_Product_AlbertaPackagingUnit.class, "M_Product_AlbertaPackagingUnit_ID", null);
	String COLUMNNAME_M_Product_AlbertaPackagingUnit_ID = "M_Product_AlbertaPackagingUnit_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

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
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty (@Nullable BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_M_Product_AlbertaPackagingUnit, Object> COLUMN_Qty = new ModelColumn<>(I_M_Product_AlbertaPackagingUnit.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Product_AlbertaPackagingUnit, Object> COLUMN_Updated = new ModelColumn<>(I_M_Product_AlbertaPackagingUnit.class, "Updated", null);
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
}
