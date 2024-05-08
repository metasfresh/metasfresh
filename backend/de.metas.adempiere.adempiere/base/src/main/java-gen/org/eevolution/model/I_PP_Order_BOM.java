package org.eevolution.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PP_Order_BOM
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PP_Order_BOM 
{

	String Table_Name = "PP_Order_BOM";

//	/** AD_Table_ID=53026 */
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
	 * Set Stücklisten-Zugehörigkeit.
	 * Type of BOM
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBOMType (@Nullable java.lang.String BOMType);

	/**
	 * Get Stücklisten-Zugehörigkeit.
	 * Type of BOM
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBOMType();

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_BOMType = new ModelColumn<>(I_PP_Order_BOM.class, "BOMType", null);
	String COLUMNNAME_BOMType = "BOMType";

	/**
	 * Set BOM Use.
	 * The use of the Bill of Material
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBOMUse (@Nullable java.lang.String BOMUse);

	/**
	 * Get BOM Use.
	 * The use of the Bill of Material
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBOMUse();

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_BOMUse = new ModelColumn<>(I_PP_Order_BOM.class, "BOMUse", null);
	String COLUMNNAME_BOMUse = "BOMUse";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCopyFrom (@Nullable java.lang.String CopyFrom);

	/**
	 * Get Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCopyFrom();

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_CopyFrom = new ModelColumn<>(I_PP_Order_BOM.class, "CopyFrom", null);
	String COLUMNNAME_CopyFrom = "CopyFrom";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_Created = new ModelColumn<>(I_PP_Order_BOM.class, "Created", null);
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

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_Description = new ModelColumn<>(I_PP_Order_BOM.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (@Nullable java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNo();

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_DocumentNo = new ModelColumn<>(I_PP_Order_BOM.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

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

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_Help = new ModelColumn<>(I_PP_Order_BOM.class, "Help", null);
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

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Order_BOM.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_PP_Order_BOM, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_PP_Order_BOM.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Änderungsmeldung.
	 * Bill of Materials (Engineering) Change Notice (Version)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ChangeNotice_ID (int M_ChangeNotice_ID);

	/**
	 * Get Änderungsmeldung.
	 * Bill of Materials (Engineering) Change Notice (Version)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ChangeNotice_ID();

	@Nullable org.compiere.model.I_M_ChangeNotice getM_ChangeNotice();

	void setM_ChangeNotice(@Nullable org.compiere.model.I_M_ChangeNotice M_ChangeNotice);

	ModelColumn<I_PP_Order_BOM, org.compiere.model.I_M_ChangeNotice> COLUMN_M_ChangeNotice_ID = new ModelColumn<>(I_PP_Order_BOM.class, "M_ChangeNotice_ID", org.compiere.model.I_M_ChangeNotice.class);
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
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_Name = new ModelColumn<>(I_PP_Order_BOM.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Manufacturing Order BOM.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_BOM_ID (int PP_Order_BOM_ID);

	/**
	 * Get Manufacturing Order BOM.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_BOM_ID();

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_PP_Order_BOM_ID = new ModelColumn<>(I_PP_Order_BOM.class, "PP_Order_BOM_ID", null);
	String COLUMNNAME_PP_Order_BOM_ID = "PP_Order_BOM_ID";

	/**
	 * Set Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_ID();

	org.eevolution.model.I_PP_Order getPP_Order();

	void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

	ModelColumn<I_PP_Order_BOM, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new ModelColumn<>(I_PP_Order_BOM.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_Processing = new ModelColumn<>(I_PP_Order_BOM.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Revision.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRevision (@Nullable java.lang.String Revision);

	/**
	 * Get Revision.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRevision();

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_Revision = new ModelColumn<>(I_PP_Order_BOM.class, "Revision", null);
	String COLUMNNAME_Revision = "Revision";

	/**
	 * Set Serial No. Sequence.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSerialNo_Sequence_ID (int SerialNo_Sequence_ID);

	/**
	 * Get Serial No. Sequence.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSerialNo_Sequence_ID();

	@Nullable org.compiere.model.I_AD_Sequence getSerialNo_Sequence();

	void setSerialNo_Sequence(@Nullable org.compiere.model.I_AD_Sequence SerialNo_Sequence);

	ModelColumn<I_PP_Order_BOM, org.compiere.model.I_AD_Sequence> COLUMN_SerialNo_Sequence_ID = new ModelColumn<>(I_PP_Order_BOM.class, "SerialNo_Sequence_ID", org.compiere.model.I_AD_Sequence.class);
	String COLUMNNAME_SerialNo_Sequence_ID = "SerialNo_Sequence_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Order_BOM.class, "Updated", null);
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
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getValidFrom();

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_ValidFrom = new ModelColumn<>(I_PP_Order_BOM.class, "ValidFrom", null);
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

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_ValidTo = new ModelColumn<>(I_PP_Order_BOM.class, "ValidTo", null);
	String COLUMNNAME_ValidTo = "ValidTo";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_PP_Order_BOM, Object> COLUMN_Value = new ModelColumn<>(I_PP_Order_BOM.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
