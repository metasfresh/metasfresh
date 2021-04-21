package de.metas.vertical.healthcare.alberta.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for M_Product_AlbertaArticle
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Product_AlbertaArticle 
{

	String Table_Name = "M_Product_AlbertaArticle";

//	/** AD_Table_ID=541597 */
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
	 * Set Additional description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAdditionalDescription (@Nullable String AdditionalDescription);

	/**
	 * Get Additional description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getAdditionalDescription();

	ModelColumn<I_M_Product_AlbertaArticle, Object> COLUMN_AdditionalDescription = new ModelColumn<>(I_M_Product_AlbertaArticle.class, "AdditionalDescription", null);
	String COLUMNNAME_AdditionalDescription = "AdditionalDescription";

	/**
	 * Set Inventory type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setArticleInventoryType (String ArticleInventoryType);

	/**
	 * Get Inventory type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getArticleInventoryType();

	ModelColumn<I_M_Product_AlbertaArticle, Object> COLUMN_ArticleInventoryType = new ModelColumn<>(I_M_Product_AlbertaArticle.class, "ArticleInventoryType", null);
	String COLUMNNAME_ArticleInventoryType = "ArticleInventoryType";

	/**
	 * Set Stars.
	 * (0-5)
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setArticleStars (BigDecimal ArticleStars);

	/**
	 * Get Stars.
	 * (0-5)
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getArticleStars();

	ModelColumn<I_M_Product_AlbertaArticle, Object> COLUMN_ArticleStars = new ModelColumn<>(I_M_Product_AlbertaArticle.class, "ArticleStars", null);
	String COLUMNNAME_ArticleStars = "ArticleStars";

	/**
	 * Set Article status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setArticleStatus (String ArticleStatus);

	/**
	 * Get Article status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getArticleStatus();

	ModelColumn<I_M_Product_AlbertaArticle, Object> COLUMN_ArticleStatus = new ModelColumn<>(I_M_Product_AlbertaArticle.class, "ArticleStatus", null);
	String COLUMNNAME_ArticleStatus = "ArticleStatus";

	/**
	 * Set Assortment type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAssortmentType (String AssortmentType);

	/**
	 * Get Assortment type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getAssortmentType();

	ModelColumn<I_M_Product_AlbertaArticle, Object> COLUMN_AssortmentType = new ModelColumn<>(I_M_Product_AlbertaArticle.class, "AssortmentType", null);
	String COLUMNNAME_AssortmentType = "AssortmentType";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Product_AlbertaArticle, Object> COLUMN_Created = new ModelColumn<>(I_M_Product_AlbertaArticle.class, "Created", null);
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

	ModelColumn<I_M_Product_AlbertaArticle, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Product_AlbertaArticle.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Alberta article data.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_AlbertaArticle_ID (int M_Product_AlbertaArticle_ID);

	/**
	 * Get Alberta article data.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_AlbertaArticle_ID();

	ModelColumn<I_M_Product_AlbertaArticle, Object> COLUMN_M_Product_AlbertaArticle_ID = new ModelColumn<>(I_M_Product_AlbertaArticle.class, "M_Product_AlbertaArticle_ID", null);
	String COLUMNNAME_M_Product_AlbertaArticle_ID = "M_Product_AlbertaArticle_ID";

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
	 * Set Medical aid position number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMedicalAidPositionNumber (@Nullable String MedicalAidPositionNumber);

	/**
	 * Get Medical aid position number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getMedicalAidPositionNumber();

	ModelColumn<I_M_Product_AlbertaArticle, Object> COLUMN_MedicalAidPositionNumber = new ModelColumn<>(I_M_Product_AlbertaArticle.class, "MedicalAidPositionNumber", null);
	String COLUMNNAME_MedicalAidPositionNumber = "MedicalAidPositionNumber";

	/**
	 * Set Purchase rating.
	 * (A - F)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPurchaseRating (@Nullable String PurchaseRating);

	/**
	 * Get Purchase rating.
	 * (A - F)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getPurchaseRating();

	ModelColumn<I_M_Product_AlbertaArticle, Object> COLUMN_PurchaseRating = new ModelColumn<>(I_M_Product_AlbertaArticle.class, "PurchaseRating", null);
	String COLUMNNAME_PurchaseRating = "PurchaseRating";

	/**
	 * Set Size.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSize (@Nullable String Size);

	/**
	 * Get Size.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getSize();

	ModelColumn<I_M_Product_AlbertaArticle, Object> COLUMN_Size = new ModelColumn<>(I_M_Product_AlbertaArticle.class, "Size", null);
	String COLUMNNAME_Size = "Size";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Product_AlbertaArticle, Object> COLUMN_Updated = new ModelColumn<>(I_M_Product_AlbertaArticle.class, "Updated", null);
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
