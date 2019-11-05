package de.metas.shipping.model;

/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved. *
 * This program is free software, you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program, if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/

/**
 * Generated Interface for M_ShipperTransportation
 *
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_M_ShipperTransportation
{

	/** TableName=M_ShipperTransportation */
	public static final String Table_Name = "M_ShipperTransportation";

	/** AD_Table_ID=540030 */
	public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

	org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

	/**
	 * AccessLevel = 1 - Org
	 */
	java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

	/** Load Meta Data */

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

	/** Column definition for AD_Client_ID */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
	/** Column name AD_Client_ID */
	public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 */
	public void setAD_Org_ID(int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

	/** Column definition for AD_Org_ID */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
	/** Column name AD_Org_ID */
	public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Ladeliste erstellen */
	public void setBillLadingReport(java.lang.String BillLadingReport);

	/** Get Ladeliste erstellen */
	public java.lang.String getBillLadingReport();

	/** Column definition for BillLadingReport */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_BillLadingReport = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "BillLadingReport", null);
	/** Column name BillLadingReport */
	public static final String COLUMNNAME_BillLadingReport = "BillLadingReport";

	/**
	 * Set Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 */
	public void setC_DocType_ID(int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

	/** Column definition for C_DocType_ID */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
	/** Column name C_DocType_ID */
	public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Sammelrechnung erstellen */
	public void setCollectiveBillReport(java.lang.String CollectiveBillReport);

	/** Get Sammelrechnung erstellen */
	public java.lang.String getCollectiveBillReport();

	/** Column definition for CollectiveBillReport */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_CollectiveBillReport = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "CollectiveBillReport", null);
	/** Column name CollectiveBillReport */
	public static final String COLUMNNAME_CollectiveBillReport = "CollectiveBillReport";

	/**
	 * Get Created.
	 * Date this record was created
	 */
	public java.sql.Timestamp getCreated();

	/** Column definition for Created */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "Created", null);
	/** Column name Created */
	public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 */
	public int getCreatedBy();

	/** Column definition for CreatedBy */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "CreatedBy", org.compiere.model.I_AD_User.class);
	/** Column name CreatedBy */
	public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Set Packstücke erfassen */
	public void setCreateShippingPackages(java.lang.String CreateShippingPackages);

	/** Get Packstücke erfassen */
	public java.lang.String getCreateShippingPackages();

	/** Column definition for CreateShippingPackages */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_CreateShippingPackages = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "CreateShippingPackages", null);
	/** Column name CreateShippingPackages */
	public static final String COLUMNNAME_CreateShippingPackages = "CreateShippingPackages";

	/**
	 * Set Belegdatum.
	 * Datum des Belegs
	 */
	public void setDateDoc(java.sql.Timestamp DateDoc);

	/**
	 * Get Belegdatum.
	 * Datum des Belegs
	 */
	public java.sql.Timestamp getDateDoc();

	/** Column definition for DateDoc */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DateDoc = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "DateDoc", null);
	/** Column name DateDoc */
	public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Beschreibung */
	public void setDescription(java.lang.String Description);

	/** Get Beschreibung */
	public java.lang.String getDescription();

	/** Column definition for Description */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "Description", null);
	/** Column name Description */
	public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Document Action.
	 * The targeted status of the document
	 */
	public void setDocAction(java.lang.String DocAction);

	/**
	 * Get Document Action.
	 * The targeted status of the document
	 */
	public java.lang.String getDocAction();

	/** Column definition for DocAction */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "DocAction", null);
	/** Column name DocAction */
	public static final String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 */
	public void setDocStatus(java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 */
	public java.lang.String getDocStatus();

	/** Column definition for DocStatus */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "DocStatus", null);
	/** Column name DocStatus */
	public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 */
	public void setDocumentNo(java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 */
	public java.lang.String getDocumentNo();

	/** Column definition for DocumentNo */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "DocumentNo", null);
	/** Column name DocumentNo */
	public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Active.
	 * The record is active in the system
	 */
	public void setIsActive(boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 */
	public boolean isActive();

	/** Column definition for IsActive */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "IsActive", null);
	/** Column name IsActive */
	public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Approved.
	 * Indicates if this document requires approval
	 */
	public void setIsApproved(boolean IsApproved);

	/**
	 * Get Approved.
	 * Indicates if this document requires approval
	 */
	public boolean isApproved();

	/** Column definition for IsApproved */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "IsApproved", null);
	/** Column name IsApproved */
	public static final String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Lieferweg.
	 * Methode oder Art der Warenlieferung
	 */
	public void setM_Shipper_ID(int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Methode oder Art der Warenlieferung
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper() throws RuntimeException;

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

	/** Column definition for M_Shipper_ID */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	/** Column name M_Shipper_ID */
	public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/** Set Shipper Transportation */
	public void setM_ShipperTransportation_ID(int M_ShipperTransportation_ID);

	/** Get Shipper Transportation */
	public int getM_ShipperTransportation_ID();

	/** Column definition for M_ShipperTransportation_ID */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_M_ShipperTransportation_ID = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "M_ShipperTransportation_ID", null);
	/** Column name M_ShipperTransportation_ID */
	public static final String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";

	/** Set Package Net Total */
	public void setPackageNetTotal(java.math.BigDecimal PackageNetTotal);

	/** Get Package Net Total */
	public java.math.BigDecimal getPackageNetTotal();

	/** Column definition for PackageNetTotal */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_PackageNetTotal = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "PackageNetTotal", null);
	/** Column name PackageNetTotal */
	public static final String COLUMNNAME_PackageNetTotal = "PackageNetTotal";

	/**
	 * Set Package Weight.
	 * Weight of a package
	 */
	public void setPackageWeight(java.math.BigDecimal PackageWeight);

	/**
	 * Get Package Weight.
	 * Weight of a package
	 */
	public java.math.BigDecimal getPackageWeight();

	/** Column definition for PackageWeight */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_PackageWeight = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "PackageWeight", null);
	/** Column name PackageWeight */
	public static final String COLUMNNAME_PackageWeight = "PackageWeight";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 */
	public void setProcessed(boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 */
	public boolean isProcessed();

	/** Column definition for Processed */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "Processed", null);
	/** Column name Processed */
	public static final String COLUMNNAME_Processed = "Processed";

	/** Set Process Now */
	public void setProcessing(boolean Processing);

	/** Get Process Now */
	public boolean isProcessing();

	/** Column definition for Processing */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "Processing", null);
	/** Column name Processing */
	public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Shipper Partner.
	 * Business Partner to be used as shipper
	 */
	public void setShipper_BPartner_ID(int Shipper_BPartner_ID);

	/**
	 * Get Shipper Partner.
	 * Business Partner to be used as shipper
	 */
	public int getShipper_BPartner_ID();

	public org.compiere.model.I_C_BPartner getShipper_BPartner() throws RuntimeException;

	public void setShipper_BPartner(org.compiere.model.I_C_BPartner Shipper_BPartner);

	/** Column definition for Shipper_BPartner_ID */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, org.compiere.model.I_C_BPartner> COLUMN_Shipper_BPartner_ID = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "Shipper_BPartner_ID", org.compiere.model.I_C_BPartner.class);
	/** Column name Shipper_BPartner_ID */
	public static final String COLUMNNAME_Shipper_BPartner_ID = "Shipper_BPartner_ID";

	/**
	 * Set Shipper Location.
	 * Business Partner Location for Shipper
	 */
	public void setShipper_Location_ID(int Shipper_Location_ID);

	/**
	 * Get Shipper Location.
	 * Business Partner Location for Shipper
	 */
	public int getShipper_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getShipper_Location() throws RuntimeException;

	public void setShipper_Location(org.compiere.model.I_C_BPartner_Location Shipper_Location);

	/** Column definition for Shipper_Location_ID */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, org.compiere.model.I_C_BPartner_Location> COLUMN_Shipper_Location_ID = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "Shipper_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
	/** Column name Shipper_Location_ID */
	public static final String COLUMNNAME_Shipper_Location_ID = "Shipper_Location_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 */
	public java.sql.Timestamp getUpdated();

	/** Column definition for Updated */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "Updated", null);
	/** Column name Updated */
	public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 */
	public int getUpdatedBy();

	/** Column definition for UpdatedBy */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
	/** Column name UpdatedBy */
	public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Abholung am.
	 * Date and time to fetch
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setDateToBeFetched(java.sql.Timestamp DateToBeFetched);

	/**
	 * Get Abholung am.
	 * Date and time to fetch
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getDateToBeFetched();

	/** Column definition for DateToBeFetched */
	public static final org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DateToBeFetched = new org.adempiere.model.ModelColumn<>(I_M_ShipperTransportation.class, "DateToBeFetched", null);
	/** Column name DateToBeFetched */
	public static final String COLUMNNAME_DateToBeFetched = "DateToBeFetched";

}
