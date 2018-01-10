package de.metas.shipper.gateway.go.model;


/** Generated Interface for GO_DeliveryOrder
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_GO_DeliveryOrder 
{

    /** TableName=GO_DeliveryOrder */
    public static final String Table_Name = "GO_DeliveryOrder";

    /** AD_Table_ID=540890 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_AD_Client>(I_GO_DeliveryOrder.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_AD_Org>(I_GO_DeliveryOrder.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_AD_User>(I_GO_DeliveryOrder.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set AX4 Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_AX4Number (java.lang.String GO_AX4Number);

	/**
	 * Get AX4 Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_AX4Number();

    /** Column definition for GO_AX4Number */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_AX4Number = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_AX4Number", null);
    /** Column name GO_AX4Number */
    public static final String COLUMNNAME_GO_AX4Number = "GO_AX4Number";

	/**
	 * Set Customer Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_CustomerReference (java.lang.String GO_CustomerReference);

	/**
	 * Get Customer Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_CustomerReference();

    /** Column definition for GO_CustomerReference */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_CustomerReference = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_CustomerReference", null);
    /** Column name GO_CustomerReference */
    public static final String COLUMNNAME_GO_CustomerReference = "GO_CustomerReference";

	/**
	 * Set Deliver To BPartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliverToBPartner_ID (int GO_DeliverToBPartner_ID);

	/**
	 * Get Deliver To BPartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGO_DeliverToBPartner_ID();

	public org.compiere.model.I_C_BPartner getGO_DeliverToBPartner();

	public void setGO_DeliverToBPartner(org.compiere.model.I_C_BPartner GO_DeliverToBPartner);

    /** Column definition for GO_DeliverToBPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_C_BPartner> COLUMN_GO_DeliverToBPartner_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_C_BPartner>(I_GO_DeliveryOrder.class, "GO_DeliverToBPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name GO_DeliverToBPartner_ID */
    public static final String COLUMNNAME_GO_DeliverToBPartner_ID = "GO_DeliverToBPartner_ID";

	/**
	 * Set Deliver To BPartner Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliverToBPLocation_ID (int GO_DeliverToBPLocation_ID);

	/**
	 * Get Deliver To BPartner Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGO_DeliverToBPLocation_ID();

	public org.compiere.model.I_C_BPartner_Location getGO_DeliverToBPLocation();

	public void setGO_DeliverToBPLocation(org.compiere.model.I_C_BPartner_Location GO_DeliverToBPLocation);

    /** Column definition for GO_DeliverToBPLocation_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_C_BPartner_Location> COLUMN_GO_DeliverToBPLocation_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_C_BPartner_Location>(I_GO_DeliveryOrder.class, "GO_DeliverToBPLocation_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name GO_DeliverToBPLocation_ID */
    public static final String COLUMNNAME_GO_DeliverToBPLocation_ID = "GO_DeliverToBPLocation_ID";

	/**
	 * Set Deliver To Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliverToCompanyName (java.lang.String GO_DeliverToCompanyName);

	/**
	 * Get Deliver To Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_DeliverToCompanyName();

    /** Column definition for GO_DeliverToCompanyName */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_DeliverToCompanyName = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_DeliverToCompanyName", null);
    /** Column name GO_DeliverToCompanyName */
    public static final String COLUMNNAME_GO_DeliverToCompanyName = "GO_DeliverToCompanyName";

	/**
	 * Set Deliver To Company Name2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliverToCompanyName2 (java.lang.String GO_DeliverToCompanyName2);

	/**
	 * Get Deliver To Company Name2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_DeliverToCompanyName2();

    /** Column definition for GO_DeliverToCompanyName2 */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_DeliverToCompanyName2 = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_DeliverToCompanyName2", null);
    /** Column name GO_DeliverToCompanyName2 */
    public static final String COLUMNNAME_GO_DeliverToCompanyName2 = "GO_DeliverToCompanyName2";

	/**
	 * Set Deliver To Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliverToDate (java.sql.Timestamp GO_DeliverToDate);

	/**
	 * Get Deliver To Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getGO_DeliverToDate();

    /** Column definition for GO_DeliverToDate */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_DeliverToDate = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_DeliverToDate", null);
    /** Column name GO_DeliverToDate */
    public static final String COLUMNNAME_GO_DeliverToDate = "GO_DeliverToDate";

	/**
	 * Set Deliver To Company Department.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliverToDepartment (java.lang.String GO_DeliverToDepartment);

	/**
	 * Get Deliver To Company Department.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_DeliverToDepartment();

    /** Column definition for GO_DeliverToDepartment */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_DeliverToDepartment = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_DeliverToDepartment", null);
    /** Column name GO_DeliverToDepartment */
    public static final String COLUMNNAME_GO_DeliverToDepartment = "GO_DeliverToDepartment";

	/**
	 * Set Deliver To Address.
	 *
	 * <br>Type: Location
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliverToLocation_ID (int GO_DeliverToLocation_ID);

	/**
	 * Get Deliver To Address.
	 *
	 * <br>Type: Location
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGO_DeliverToLocation_ID();

	public org.compiere.model.I_C_Location getGO_DeliverToLocation();

	public void setGO_DeliverToLocation(org.compiere.model.I_C_Location GO_DeliverToLocation);

    /** Column definition for GO_DeliverToLocation_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_C_Location> COLUMN_GO_DeliverToLocation_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_C_Location>(I_GO_DeliveryOrder.class, "GO_DeliverToLocation_ID", org.compiere.model.I_C_Location.class);
    /** Column name GO_DeliverToLocation_ID */
    public static final String COLUMNNAME_GO_DeliverToLocation_ID = "GO_DeliverToLocation_ID";

	/**
	 * Set Deliver To Note.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliverToNote (java.lang.String GO_DeliverToNote);

	/**
	 * Get Deliver To Note.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_DeliverToNote();

    /** Column definition for GO_DeliverToNote */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_DeliverToNote = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_DeliverToNote", null);
    /** Column name GO_DeliverToNote */
    public static final String COLUMNNAME_GO_DeliverToNote = "GO_DeliverToNote";

	/**
	 * Set Deliver To Phone No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliverToPhoneNo (java.lang.String GO_DeliverToPhoneNo);

	/**
	 * Get Deliver To Phone No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_DeliverToPhoneNo();

    /** Column definition for GO_DeliverToPhoneNo */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_DeliverToPhoneNo = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_DeliverToPhoneNo", null);
    /** Column name GO_DeliverToPhoneNo */
    public static final String COLUMNNAME_GO_DeliverToPhoneNo = "GO_DeliverToPhoneNo";

	/**
	 * Set Deliver Time (from).
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliverToTimeFrom (java.sql.Timestamp GO_DeliverToTimeFrom);

	/**
	 * Get Deliver Time (from).
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getGO_DeliverToTimeFrom();

    /** Column definition for GO_DeliverToTimeFrom */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_DeliverToTimeFrom = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_DeliverToTimeFrom", null);
    /** Column name GO_DeliverToTimeFrom */
    public static final String COLUMNNAME_GO_DeliverToTimeFrom = "GO_DeliverToTimeFrom";

	/**
	 * Set Deliver Time (to).
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliverToTimeTo (java.sql.Timestamp GO_DeliverToTimeTo);

	/**
	 * Get Deliver Time (to).
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getGO_DeliverToTimeTo();

    /** Column definition for GO_DeliverToTimeTo */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_DeliverToTimeTo = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_DeliverToTimeTo", null);
    /** Column name GO_DeliverToTimeTo */
    public static final String COLUMNNAME_GO_DeliverToTimeTo = "GO_DeliverToTimeTo";

	/**
	 * Set GO Delivery Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliveryOrder_ID (int GO_DeliveryOrder_ID);

	/**
	 * Get GO Delivery Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGO_DeliveryOrder_ID();

    /** Column definition for GO_DeliveryOrder_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_DeliveryOrder_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_DeliveryOrder_ID", null);
    /** Column name GO_DeliveryOrder_ID */
    public static final String COLUMNNAME_GO_DeliveryOrder_ID = "GO_DeliveryOrder_ID";

	/**
	 * Set Gross Weight (Kg).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_GrossWeightKg (int GO_GrossWeightKg);

	/**
	 * Get Gross Weight (Kg).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGO_GrossWeightKg();

    /** Column definition for GO_GrossWeightKg */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_GrossWeightKg = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_GrossWeightKg", null);
    /** Column name GO_GrossWeightKg */
    public static final String COLUMNNAME_GO_GrossWeightKg = "GO_GrossWeightKg";

	/**
	 * Set HWB Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_HWBNumber (java.lang.String GO_HWBNumber);

	/**
	 * Get HWB Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_HWBNumber();

    /** Column definition for GO_HWBNumber */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_HWBNumber = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_HWBNumber", null);
    /** Column name GO_HWBNumber */
    public static final String COLUMNNAME_GO_HWBNumber = "GO_HWBNumber";

	/**
	 * Set Number Of Packages.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_NumberOfPackages (int GO_NumberOfPackages);

	/**
	 * Get Number Of Packages.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGO_NumberOfPackages();

    /** Column definition for GO_NumberOfPackages */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_NumberOfPackages = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_NumberOfPackages", null);
    /** Column name GO_NumberOfPackages */
    public static final String COLUMNNAME_GO_NumberOfPackages = "GO_NumberOfPackages";

	/**
	 * Set Order Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_OrderStatus (java.lang.String GO_OrderStatus);

	/**
	 * Get Order Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_OrderStatus();

    /** Column definition for GO_OrderStatus */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_OrderStatus = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_OrderStatus", null);
    /** Column name GO_OrderStatus */
    public static final String COLUMNNAME_GO_OrderStatus = "GO_OrderStatus";

	/**
	 * Set Package content description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_PackageContentDescription (java.lang.String GO_PackageContentDescription);

	/**
	 * Get Package content description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_PackageContentDescription();

    /** Column definition for GO_PackageContentDescription */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_PackageContentDescription = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_PackageContentDescription", null);
    /** Column name GO_PackageContentDescription */
    public static final String COLUMNNAME_GO_PackageContentDescription = "GO_PackageContentDescription";

	/**
	 * Set Paid Mode.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_PaidMode (java.lang.String GO_PaidMode);

	/**
	 * Get Paid Mode.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_PaidMode();

    /** Column definition for GO_PaidMode */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_PaidMode = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_PaidMode", null);
    /** Column name GO_PaidMode */
    public static final String COLUMNNAME_GO_PaidMode = "GO_PaidMode";

	/**
	 * Set Pickup Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_PickupCompanyName (java.lang.String GO_PickupCompanyName);

	/**
	 * Get Pickup Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_PickupCompanyName();

    /** Column definition for GO_PickupCompanyName */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_PickupCompanyName = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_PickupCompanyName", null);
    /** Column name GO_PickupCompanyName */
    public static final String COLUMNNAME_GO_PickupCompanyName = "GO_PickupCompanyName";

	/**
	 * Set Pickup Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_PickupDate (java.sql.Timestamp GO_PickupDate);

	/**
	 * Get Pickup Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getGO_PickupDate();

    /** Column definition for GO_PickupDate */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_PickupDate = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_PickupDate", null);
    /** Column name GO_PickupDate */
    public static final String COLUMNNAME_GO_PickupDate = "GO_PickupDate";

	/**
	 * Set Pickup address.
	 *
	 * <br>Type: Location
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_PickupLocation_ID (int GO_PickupLocation_ID);

	/**
	 * Get Pickup address.
	 *
	 * <br>Type: Location
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGO_PickupLocation_ID();

	public org.compiere.model.I_C_Location getGO_PickupLocation();

	public void setGO_PickupLocation(org.compiere.model.I_C_Location GO_PickupLocation);

    /** Column definition for GO_PickupLocation_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_C_Location> COLUMN_GO_PickupLocation_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_C_Location>(I_GO_DeliveryOrder.class, "GO_PickupLocation_ID", org.compiere.model.I_C_Location.class);
    /** Column name GO_PickupLocation_ID */
    public static final String COLUMNNAME_GO_PickupLocation_ID = "GO_PickupLocation_ID";

	/**
	 * Set Pickup note.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_PickupNote (java.lang.String GO_PickupNote);

	/**
	 * Get Pickup note.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_PickupNote();

    /** Column definition for GO_PickupNote */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_PickupNote = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_PickupNote", null);
    /** Column name GO_PickupNote */
    public static final String COLUMNNAME_GO_PickupNote = "GO_PickupNote";

	/**
	 * Set Pickup Time (from).
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_PickupTimeFrom (java.sql.Timestamp GO_PickupTimeFrom);

	/**
	 * Get Pickup Time (from).
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getGO_PickupTimeFrom();

    /** Column definition for GO_PickupTimeFrom */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_PickupTimeFrom = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_PickupTimeFrom", null);
    /** Column name GO_PickupTimeFrom */
    public static final String COLUMNNAME_GO_PickupTimeFrom = "GO_PickupTimeFrom";

	/**
	 * Set Pickup Time (to).
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_PickupTimeTo (java.sql.Timestamp GO_PickupTimeTo);

	/**
	 * Get Pickup Time (to).
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getGO_PickupTimeTo();

    /** Column definition for GO_PickupTimeTo */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_PickupTimeTo = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_PickupTimeTo", null);
    /** Column name GO_PickupTimeTo */
    public static final String COLUMNNAME_GO_PickupTimeTo = "GO_PickupTimeTo";

	/**
	 * Set Self Delivery.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_SelfDelivery (java.lang.String GO_SelfDelivery);

	/**
	 * Get Self Delivery.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_SelfDelivery();

    /** Column definition for GO_SelfDelivery */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_SelfDelivery = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_SelfDelivery", null);
    /** Column name GO_SelfDelivery */
    public static final String COLUMNNAME_GO_SelfDelivery = "GO_SelfDelivery";

	/**
	 * Set Self Pickup.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_SelfPickup (java.lang.String GO_SelfPickup);

	/**
	 * Get Self Pickup.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_SelfPickup();

    /** Column definition for GO_SelfPickup */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_SelfPickup = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_SelfPickup", null);
    /** Column name GO_SelfPickup */
    public static final String COLUMNNAME_GO_SelfPickup = "GO_SelfPickup";

	/**
	 * Set Service type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_ServiceType (java.lang.String GO_ServiceType);

	/**
	 * Get Service type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_ServiceType();

    /** Column definition for GO_ServiceType */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_GO_ServiceType = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "GO_ServiceType", null);
    /** Column name GO_ServiceType */
    public static final String COLUMNNAME_GO_ServiceType = "GO_ServiceType";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_M_Shipper>(I_GO_DeliveryOrder.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Transport Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID);

	/**
	 * Get Transport Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ShipperTransportation_ID();

    /** Column definition for M_ShipperTransportation_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_M_ShipperTransportation_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "M_ShipperTransportation_ID", null);
    /** Column name M_ShipperTransportation_ID */
    public static final String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, Object>(I_GO_DeliveryOrder.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder, org.compiere.model.I_AD_User>(I_GO_DeliveryOrder.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
