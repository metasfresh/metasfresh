package de.metas.printing.model;


/** Generated Interface for C_Printing_Queue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Printing_Queue 
{

    /** TableName=C_Printing_Queue */
    public static final String Table_Name = "C_Printing_Queue";

    /** AD_Table_ID=540435 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Archiv.
	 * Archiv fĂĽr Belege und Berichte
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Archive_ID (int AD_Archive_ID);

	/**
	 * Get Archiv.
	 * Archiv fĂĽr Belege und Berichte
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Archive_ID();

	public org.compiere.model.I_AD_Archive getAD_Archive();

	public void setAD_Archive(org.compiere.model.I_AD_Archive AD_Archive);

    /** Column definition for AD_Archive_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, org.compiere.model.I_AD_Archive> COLUMN_AD_Archive_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, org.compiere.model.I_AD_Archive>(I_C_Printing_Queue.class, "AD_Archive_ID", org.compiere.model.I_AD_Archive.class);
    /** Column name AD_Archive_ID */
    public static final String COLUMNNAME_AD_Archive_ID = "AD_Archive_ID";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Language.
	 * Sprache für diesen Eintrag
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Language (java.lang.String AD_Language);

	/**
	 * Get Language.
	 * Sprache für diesen Eintrag
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_Language();

    /** Column definition for AD_Language */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_AD_Language = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "AD_Language", null);
    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess.
	 * Prozess oder Bericht
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Prozess oder Bericht
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process();

	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

    /** Column definition for AD_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, org.compiere.model.I_AD_Process>(I_C_Printing_Queue.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Role.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Role.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Role_ID();

	public org.compiere.model.I_AD_Role getAD_Role();

	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role);

    /** Column definition for AD_Role_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, org.compiere.model.I_AD_Role>(I_C_Printing_Queue.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
    /** Column name AD_Role_ID */
    public static final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_BPartner_ID();

    /** Column name Bill_BPartner_ID */
    public static final String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Bill Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Bill Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_Location_ID();

    /** Column name Bill_Location_ID */
    public static final String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/**
	 * Set Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Async_Batch_ID (int C_Async_Batch_ID);

	/**
	 * Get Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Async_Batch_ID();

    /** Column definition for C_Async_Batch_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_C_Async_Batch_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "C_Async_Batch_ID", null);
    /** Column name C_Async_Batch_ID */
    public static final String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Kopien.
	 * Anzahl der zu erstellenden/zu druckenden Exemplare
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCopies (int Copies);

	/**
	 * Get Kopien.
	 * Anzahl der zu erstellenden/zu druckenden Exemplare
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCopies();

    /** Column definition for Copies */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_Copies = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "Copies", null);
    /** Column name Copies */
    public static final String COLUMNNAME_Copies = "Copies";

	/**
	 * Set Druck-Warteschlangendatensatz.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Printing_Queue_ID (int C_Printing_Queue_ID);

	/**
	 * Get Druck-Warteschlangendatensatz.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Printing_Queue_ID();

    /** Column definition for C_Printing_Queue_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_C_Printing_Queue_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "C_Printing_Queue_ID", null);
    /** Column name C_Printing_Queue_ID */
    public static final String COLUMNNAME_C_Printing_Queue_ID = "C_Printing_Queue_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Shipmentdate.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate);

	/**
	 * Get Shipmentdate.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDeliveryDate();

    /** Column definition for DeliveryDate */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_DeliveryDate = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "DeliveryDate", null);
    /** Column name DeliveryDate */
    public static final String COLUMNNAME_DeliveryDate = "DeliveryDate";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Abweichender Rechnungspartner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsDifferentInvoicingPartner (boolean IsDifferentInvoicingPartner);

	/**
	 * Get Abweichender Rechnungspartner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isDifferentInvoicingPartner();

    /** Column definition for IsDifferentInvoicingPartner */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_IsDifferentInvoicingPartner = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "IsDifferentInvoicingPartner", null);
    /** Column name IsDifferentInvoicingPartner */
    public static final String COLUMNNAME_IsDifferentInvoicingPartner = "IsDifferentInvoicingPartner";

	/**
	 * Set Foreign Customer.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsForeignCustomer (boolean IsForeignCustomer);

	/**
	 * Get Foreign Customer.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isForeignCustomer();

    /** Column definition for IsForeignCustomer */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_IsForeignCustomer = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "IsForeignCustomer", null);
    /** Column name IsForeignCustomer */
    public static final String COLUMNNAME_IsForeignCustomer = "IsForeignCustomer";

	/**
	 * Set Abw. Druck-Empfänger.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPrintoutForOtherUser (boolean IsPrintoutForOtherUser);

	/**
	 * Get Abw. Druck-Empfänger.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPrintoutForOtherUser();

    /** Column definition for IsPrintoutForOtherUser */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_IsPrintoutForOtherUser = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "IsPrintoutForOtherUser", null);
    /** Column name IsPrintoutForOtherUser */
    public static final String COLUMNNAME_IsPrintoutForOtherUser = "IsPrintoutForOtherUser";

	/**
	 * Set Print Item Name.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setItemName (java.lang.String ItemName);

	/**
	 * Get Print Item Name.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getItemName();

    /** Column definition for ItemName */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_ItemName = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "ItemName", null);
    /** Column name ItemName */
    public static final String COLUMNNAME_ItemName = "ItemName";

	/**
	 * Set Warteschlangen-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrintingQueueAggregationKey (java.lang.String PrintingQueueAggregationKey);

	/**
	 * Get Warteschlangen-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPrintingQueueAggregationKey();

    /** Column definition for PrintingQueueAggregationKey */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_PrintingQueueAggregationKey = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "PrintingQueueAggregationKey", null);
    /** Column name PrintingQueueAggregationKey */
    public static final String COLUMNNAME_PrintingQueueAggregationKey = "PrintingQueueAggregationKey";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Printing_Queue, Object>(I_C_Printing_Queue.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
