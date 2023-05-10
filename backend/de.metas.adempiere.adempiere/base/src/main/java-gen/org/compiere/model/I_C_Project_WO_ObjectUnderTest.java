package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Project_WO_ObjectUnderTest
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_WO_ObjectUnderTest 
{

	String Table_Name = "C_Project_WO_ObjectUnderTest";

//	/** AD_Table_ID=542184 */
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
	 * Set Provision order line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_Provision_ID (int C_OrderLine_Provision_ID);

	/**
	 * Get Provision order line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_Provision_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine_Provision();

	void setC_OrderLine_Provision(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine_Provision);

	ModelColumn<I_C_Project_WO_ObjectUnderTest, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_Provision_ID = new ModelColumn<>(I_C_Project_WO_ObjectUnderTest.class, "C_OrderLine_Provision_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_Provision_ID = "C_OrderLine_Provision_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set C_Project_WO_ObjectUnderTest.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_WO_ObjectUnderTest_ID (int C_Project_WO_ObjectUnderTest_ID);

	/**
	 * Get C_Project_WO_ObjectUnderTest.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_WO_ObjectUnderTest_ID();

	ModelColumn<I_C_Project_WO_ObjectUnderTest, Object> COLUMN_C_Project_WO_ObjectUnderTest_ID = new ModelColumn<>(I_C_Project_WO_ObjectUnderTest.class, "C_Project_WO_ObjectUnderTest_ID", null);
	String COLUMNNAME_C_Project_WO_ObjectUnderTest_ID = "C_Project_WO_ObjectUnderTest_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Project_WO_ObjectUnderTest, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_WO_ObjectUnderTest.class, "Created", null);
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
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_C_Project_WO_ObjectUnderTest, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_Project_WO_ObjectUnderTest.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

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

	ModelColumn<I_C_Project_WO_ObjectUnderTest, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_WO_ObjectUnderTest.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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
	 * Set Number of test items.
	 * Number of objects under test
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setNumberOfObjectsUnderTest (int NumberOfObjectsUnderTest);

	/**
	 * Get Number of test items.
	 * Number of objects under test
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getNumberOfObjectsUnderTest();

	ModelColumn<I_C_Project_WO_ObjectUnderTest, Object> COLUMN_NumberOfObjectsUnderTest = new ModelColumn<>(I_C_Project_WO_ObjectUnderTest.class, "NumberOfObjectsUnderTest", null);
	String COLUMNNAME_NumberOfObjectsUnderTest = "NumberOfObjectsUnderTest";

	/**
	 * Set Object delivered on.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setObjectDeliveredDate (@Nullable java.sql.Timestamp ObjectDeliveredDate);

	/**
	 * Get Object delivered on.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getObjectDeliveredDate();

	ModelColumn<I_C_Project_WO_ObjectUnderTest, Object> COLUMN_ObjectDeliveredDate = new ModelColumn<>(I_C_Project_WO_ObjectUnderTest.class, "ObjectDeliveredDate", null);
	String COLUMNNAME_ObjectDeliveredDate = "ObjectDeliveredDate";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Project_WO_ObjectUnderTest, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_WO_ObjectUnderTest.class, "Updated", null);
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
	 * Set Delivery note.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWODeliveryNote (@Nullable java.lang.String WODeliveryNote);

	/**
	 * Get Delivery note.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWODeliveryNote();

	ModelColumn<I_C_Project_WO_ObjectUnderTest, Object> COLUMN_WODeliveryNote = new ModelColumn<>(I_C_Project_WO_ObjectUnderTest.class, "WODeliveryNote", null);
	String COLUMNNAME_WODeliveryNote = "WODeliveryNote";

	/**
	 * Set Manufacturer.
	 * Manufacturer of the object under test
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWOManufacturer (@Nullable java.lang.String WOManufacturer);

	/**
	 * Get Manufacturer.
	 * Manufacturer of the object under test
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWOManufacturer();

	ModelColumn<I_C_Project_WO_ObjectUnderTest, Object> COLUMN_WOManufacturer = new ModelColumn<>(I_C_Project_WO_ObjectUnderTest.class, "WOManufacturer", null);
	String COLUMNNAME_WOManufacturer = "WOManufacturer";

	/**
	 * Set Name.
	 * Name of the test item (e.g. type designation)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWOObjectName (@Nullable java.lang.String WOObjectName);

	/**
	 * Get Name.
	 * Name of the test item (e.g. type designation)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWOObjectName();

	ModelColumn<I_C_Project_WO_ObjectUnderTest, Object> COLUMN_WOObjectName = new ModelColumn<>(I_C_Project_WO_ObjectUnderTest.class, "WOObjectName", null);
	String COLUMNNAME_WOObjectName = "WOObjectName";

	/**
	 * Set Class.
	 * Type of test item
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWOObjectType (@Nullable java.lang.String WOObjectType);

	/**
	 * Get Class.
	 * Type of test item
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWOObjectType();

	ModelColumn<I_C_Project_WO_ObjectUnderTest, Object> COLUMN_WOObjectType = new ModelColumn<>(I_C_Project_WO_ObjectUnderTest.class, "WOObjectType", null);
	String COLUMNNAME_WOObjectType = "WOObjectType";

	/**
	 * Set Whereabouts.
	 * Whereabouts of the test item after the test
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWOObjectWhereabouts (@Nullable java.lang.String WOObjectWhereabouts);

	/**
	 * Get Whereabouts.
	 * Whereabouts of the test item after the test
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWOObjectWhereabouts();

	ModelColumn<I_C_Project_WO_ObjectUnderTest, Object> COLUMN_WOObjectWhereabouts = new ModelColumn<>(I_C_Project_WO_ObjectUnderTest.class, "WOObjectWhereabouts", null);
	String COLUMNNAME_WOObjectWhereabouts = "WOObjectWhereabouts";
}
