package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Product_ASI_Data
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Product_ASI_Data 
{

	String Table_Name = "M_Product_ASI_Data";

//	/** AD_Table_ID=542588 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_Created = new ModelColumn<>(I_M_Product_ASI_Data.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Auszeichnungsname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomerLabelName (@Nullable java.lang.String CustomerLabelName);

	/**
	 * Get Auszeichnungsname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCustomerLabelName();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_CustomerLabelName = new ModelColumn<>(I_M_Product_ASI_Data.class, "CustomerLabelName", null);
	String COLUMNNAME_CustomerLabelName = "CustomerLabelName";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_Description = new ModelColumn<>(I_M_Product_ASI_Data.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Beschreibungs-URL.
	 * URL für die Beschreibung
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescriptionURL (@Nullable java.lang.String DescriptionURL);

	/**
	 * Get Beschreibungs-URL.
	 * URL für die Beschreibung
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescriptionURL();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_DescriptionURL = new ModelColumn<>(I_M_Product_ASI_Data.class, "DescriptionURL", null);
	String COLUMNNAME_DescriptionURL = "DescriptionURL";

	/**
	 * Set EAN13-Produktcode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEAN13_ProductCode (@Nullable java.lang.String EAN13_ProductCode);

	/**
	 * Get EAN13-Produktcode.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEAN13_ProductCode();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_EAN13_ProductCode = new ModelColumn<>(I_M_Product_ASI_Data.class, "EAN13_ProductCode", null);
	String COLUMNNAME_EAN13_ProductCode = "EAN13_ProductCode";

	/**
	 * Set CU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEAN_CU (@Nullable java.lang.String EAN_CU);

	/**
	 * Get CU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEAN_CU();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_EAN_CU = new ModelColumn<>(I_M_Product_ASI_Data.class, "EAN_CU", null);
	String COLUMNNAME_EAN_CU = "EAN_CU";

	/**
	 * Set GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGTIN (@Nullable java.lang.String GTIN);

	/**
	 * Get GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGTIN();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_GTIN = new ModelColumn<>(I_M_Product_ASI_Data.class, "GTIN", null);
	String COLUMNNAME_GTIN = "GTIN";

	/**
	 * Set Zutaten.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIngredients (@Nullable java.lang.String Ingredients);

	/**
	 * Get Zutaten.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIngredients();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_Ingredients = new ModelColumn<>(I_M_Product_ASI_Data.class, "Ingredients", null);
	String COLUMNNAME_Ingredients = "Ingredients";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Product_ASI_Data.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	ModelColumn<I_M_Product_ASI_Data, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_M_Product_ASI_Data.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Merkmalsdaten.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ASI_Data_ID (int M_Product_ASI_Data_ID);

	/**
	 * Get Merkmalsdaten.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ASI_Data_ID();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_M_Product_ASI_Data_ID = new ModelColumn<>(I_M_Product_ASI_Data.class, "M_Product_ASI_Data_ID", null);
	String COLUMNNAME_M_Product_ASI_Data_ID = "M_Product_ASI_Data_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Produktkategorie.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductCategory (@Nullable java.lang.String ProductCategory);

	/**
	 * Get Produktkategorie.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductCategory();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_ProductCategory = new ModelColumn<>(I_M_Product_ASI_Data.class, "ProductCategory", null);
	String COLUMNNAME_ProductCategory = "ProductCategory";

	/**
	 * Set Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductDescription (@Nullable java.lang.String ProductDescription);

	/**
	 * Get Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductDescription();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_ProductDescription = new ModelColumn<>(I_M_Product_ASI_Data.class, "ProductDescription", null);
	String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Bezeichnung.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductName (@Nullable java.lang.String ProductName);

	/**
	 * Get Bezeichnung.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductName();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_ProductName = new ModelColumn<>(I_M_Product_ASI_Data.class, "ProductName", null);
	String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set ArtNr..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductNo (@Nullable java.lang.String ProductNo);

	/**
	 * Get ArtNr..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductNo();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_ProductNo = new ModelColumn<>(I_M_Product_ASI_Data.class, "ProductNo", null);
	String COLUMNNAME_ProductNo = "ProductNo";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_SeqNo = new ModelColumn<>(I_M_Product_ASI_Data.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set UPC.
	 * Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUPC (@Nullable java.lang.String UPC);

	/**
	 * Get UPC.
	 * Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUPC();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_UPC = new ModelColumn<>(I_M_Product_ASI_Data.class, "UPC", null);
	String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Product_ASI_Data, Object> COLUMN_Updated = new ModelColumn<>(I_M_Product_ASI_Data.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
