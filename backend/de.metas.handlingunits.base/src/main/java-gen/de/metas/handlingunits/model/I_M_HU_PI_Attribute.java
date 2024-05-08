package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_PI_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_PI_Attribute 
{

	String Table_Name = "M_HU_PI_Attribute";

//	/** AD_Table_ID=540507 */
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
	 * Set Aggregation Strategy.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAggregationStrategy_JavaClass_ID (int AggregationStrategy_JavaClass_ID);

	/**
	 * Get Aggregation Strategy.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAggregationStrategy_JavaClass_ID();

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_AggregationStrategy_JavaClass_ID = new ModelColumn<>(I_M_HU_PI_Attribute.class, "AggregationStrategy_JavaClass_ID", null);
	String COLUMNNAME_AggregationStrategy_JavaClass_ID = "AggregationStrategy_JavaClass_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_PI_Attribute.class, "Created", null);
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
	 * Set HU Transfer Attribute Strategy.
	 * Strategy used for transferring an attribute from a source HU.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHU_TansferStrategy_JavaClass_ID (int HU_TansferStrategy_JavaClass_ID);

	/**
	 * Get HU Transfer Attribute Strategy.
	 * Strategy used for transferring an attribute from a source HU.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getHU_TansferStrategy_JavaClass_ID();

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_HU_TansferStrategy_JavaClass_ID = new ModelColumn<>(I_M_HU_PI_Attribute.class, "HU_TansferStrategy_JavaClass_ID", null);
	String COLUMNNAME_HU_TansferStrategy_JavaClass_ID = "HU_TansferStrategy_JavaClass_ID";

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

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_PI_Attribute.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDisplayed (boolean IsDisplayed);

	/**
	 * Get Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDisplayed();

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_IsDisplayed = new ModelColumn<>(I_M_HU_PI_Attribute.class, "IsDisplayed", null);
	String COLUMNNAME_IsDisplayed = "IsDisplayed";

	/**
	 * Set Instanz-Attribut.
	 * The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsInstanceAttribute (boolean IsInstanceAttribute);

	/**
	 * Get Instanz-Attribut.
	 * The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isInstanceAttribute();

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_IsInstanceAttribute = new ModelColumn<>(I_M_HU_PI_Attribute.class, "IsInstanceAttribute", null);
	String COLUMNNAME_IsInstanceAttribute = "IsInstanceAttribute";

	/**
	 * Set Pflichtangabe.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsMandatory (boolean IsMandatory);

	/**
	 * Get Pflichtangabe.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isMandatory();

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_IsMandatory = new ModelColumn<>(I_M_HU_PI_Attribute.class, "IsMandatory", null);
	String COLUMNNAME_IsMandatory = "IsMandatory";

	/**
	 * Set OnlyIfInProductAttributeSet.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOnlyIfInProductAttributeSet (boolean IsOnlyIfInProductAttributeSet);

	/**
	 * Get OnlyIfInProductAttributeSet.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnlyIfInProductAttributeSet();

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_IsOnlyIfInProductAttributeSet = new ModelColumn<>(I_M_HU_PI_Attribute.class, "IsOnlyIfInProductAttributeSet", null);
	String COLUMNNAME_IsOnlyIfInProductAttributeSet = "IsOnlyIfInProductAttributeSet";

	/**
	 * Set Schreibgeschützt.
	 * Feld / Eintrag / Berecih ist schreibgeschützt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReadOnly (boolean IsReadOnly);

	/**
	 * Get Schreibgeschützt.
	 * Feld / Eintrag / Berecih ist schreibgeschützt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReadOnly();

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_IsReadOnly = new ModelColumn<>(I_M_HU_PI_Attribute.class, "IsReadOnly", null);
	String COLUMNNAME_IsReadOnly = "IsReadOnly";

	/**
	 * Set Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Attribute_ID();

	String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Handling Units Packing Instructions Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Attribute_ID (int M_HU_PI_Attribute_ID);

	/**
	 * Get Handling Units Packing Instructions Attribute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Attribute_ID();

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_M_HU_PI_Attribute_ID = new ModelColumn<>(I_M_HU_PI_Attribute.class, "M_HU_PI_Attribute_ID", null);
	String COLUMNNAME_M_HU_PI_Attribute_ID = "M_HU_PI_Attribute_ID";

	/**
	 * Set Packvorschrift Version.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Version_ID (int M_HU_PI_Version_ID);

	/**
	 * Get Packvorschrift Version.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Version_ID();

	de.metas.handlingunits.model.I_M_HU_PI_Version getM_HU_PI_Version();

	void setM_HU_PI_Version(de.metas.handlingunits.model.I_M_HU_PI_Version M_HU_PI_Version);

	ModelColumn<I_M_HU_PI_Attribute, de.metas.handlingunits.model.I_M_HU_PI_Version> COLUMN_M_HU_PI_Version_ID = new ModelColumn<>(I_M_HU_PI_Attribute.class, "M_HU_PI_Version_ID", de.metas.handlingunits.model.I_M_HU_PI_Version.class);
	String COLUMNNAME_M_HU_PI_Version_ID = "M_HU_PI_Version_ID";

	/**
	 * Set Propagation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPropagationType (java.lang.String PropagationType);

	/**
	 * Get Propagation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPropagationType();

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_PropagationType = new ModelColumn<>(I_M_HU_PI_Attribute.class, "PropagationType", null);
	String COLUMNNAME_PropagationType = "PropagationType";

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

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_SeqNo = new ModelColumn<>(I_M_HU_PI_Attribute.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Splitter Strategy.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSplitterStrategy_JavaClass_ID (int SplitterStrategy_JavaClass_ID);

	/**
	 * Get Splitter Strategy.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSplitterStrategy_JavaClass_ID();

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_SplitterStrategy_JavaClass_ID = new ModelColumn<>(I_M_HU_PI_Attribute.class, "SplitterStrategy_JavaClass_ID", null);
	String COLUMNNAME_SplitterStrategy_JavaClass_ID = "SplitterStrategy_JavaClass_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_PI_Attribute.class, "Updated", null);
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
	 * Set Use in ASI.
	 * If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUseInASI (boolean UseInASI);

	/**
	 * Get Use in ASI.
	 * If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseInASI();

	ModelColumn<I_M_HU_PI_Attribute, Object> COLUMN_UseInASI = new ModelColumn<>(I_M_HU_PI_Attribute.class, "UseInASI", null);
	String COLUMNNAME_UseInASI = "UseInASI";
}
