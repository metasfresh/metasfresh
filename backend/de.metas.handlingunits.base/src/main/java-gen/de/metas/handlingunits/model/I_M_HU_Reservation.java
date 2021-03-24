package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_Reservation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Reservation 
{

	String Table_Name = "M_HU_Reservation";

//	/** AD_Table_ID=540988 */
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
	 * Set Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID);

	/**
	 * Get Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Customer_ID();

	String COLUMNNAME_C_BPartner_Customer_ID = "C_BPartner_Customer_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLineSO_ID (int C_OrderLineSO_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLineSO_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLineSO();

	void setC_OrderLineSO(@Nullable org.compiere.model.I_C_OrderLine C_OrderLineSO);

	ModelColumn<I_M_HU_Reservation, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLineSO_ID = new ModelColumn<>(I_M_HU_Reservation.class, "C_OrderLineSO_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLineSO_ID = "C_OrderLineSO_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_Reservation, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Reservation.class, "Created", null);
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

	ModelColumn<I_M_HU_Reservation, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Reservation.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set M_HU_Reservation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Reservation_ID (int M_HU_Reservation_ID);

	/**
	 * Get M_HU_Reservation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Reservation_ID();

	ModelColumn<I_M_HU_Reservation, Object> COLUMN_M_HU_Reservation_ID = new ModelColumn<>(I_M_HU_Reservation.class, "M_HU_Reservation_ID", null);
	String COLUMNNAME_M_HU_Reservation_ID = "M_HU_Reservation_ID";

	/**
	 * Set Open Qty.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyReserved (BigDecimal QtyReserved);

	/**
	 * Get Open Qty.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReserved();

	ModelColumn<I_M_HU_Reservation, Object> COLUMN_QtyReserved = new ModelColumn<>(I_M_HU_Reservation.class, "QtyReserved", null);
	String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_Reservation, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Reservation.class, "Updated", null);
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
	 * Set CU.
	 * Customer Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVHU_ID (int VHU_ID);

	/**
	 * Get CU.
	 * Customer Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getVHU_ID();

	de.metas.handlingunits.model.I_M_HU getVHU();

	void setVHU(de.metas.handlingunits.model.I_M_HU VHU);

	ModelColumn<I_M_HU_Reservation, de.metas.handlingunits.model.I_M_HU> COLUMN_VHU_ID = new ModelColumn<>(I_M_HU_Reservation.class, "VHU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_VHU_ID = "VHU_ID";
}
