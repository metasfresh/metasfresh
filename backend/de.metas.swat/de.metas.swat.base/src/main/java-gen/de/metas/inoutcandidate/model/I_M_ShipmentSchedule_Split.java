package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;
import org.compiere.model.I_M_InOutLine;

/** Generated Interface for M_ShipmentSchedule_Split
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_ShipmentSchedule_Split 
{

	String Table_Name = "M_ShipmentSchedule_Split";

//	/** AD_Table_ID=542406 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_ShipmentSchedule_Split, Object> COLUMN_Created = new ModelColumn<>(I_M_ShipmentSchedule_Split.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Shipmentdate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeliveryDate (java.sql.Timestamp DeliveryDate);

	/**
	 * Get Shipmentdate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDeliveryDate();

	ModelColumn<I_M_ShipmentSchedule_Split, Object> COLUMN_DeliveryDate = new ModelColumn<>(I_M_ShipmentSchedule_Split.class, "DeliveryDate", null);
	String COLUMNNAME_DeliveryDate = "DeliveryDate";

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

	ModelColumn<I_M_ShipmentSchedule_Split, Object> COLUMN_IsActive = new ModelColumn<>(I_M_ShipmentSchedule_Split.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOut_ID();

	@Nullable org.compiere.model.I_M_InOut getM_InOut();

	void setM_InOut(@Nullable org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_M_ShipmentSchedule_Split, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_M_ShipmentSchedule_Split.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOutLine_ID();

	@Nullable org.compiere.model.I_M_InOutLine getM_InOutLine();

	void setM_InOutLine(@Nullable org.compiere.model.I_M_InOutLine M_InOutLine);

	ModelColumn<I_M_ShipmentSchedule_Split, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_M_ShipmentSchedule_Split.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ID();

	de.metas.inoutcandidate.model.I_M_ShipmentSchedule getM_ShipmentSchedule();

	void setM_ShipmentSchedule(de.metas.inoutcandidate.model.I_M_ShipmentSchedule M_ShipmentSchedule);

	ModelColumn<I_M_ShipmentSchedule_Split, de.metas.inoutcandidate.model.I_M_ShipmentSchedule> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_M_ShipmentSchedule_Split.class, "M_ShipmentSchedule_ID", de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Split Shipment Schedule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_Split_ID (int M_ShipmentSchedule_Split_ID);

	/**
	 * Get Split Shipment Schedule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_Split_ID();

	ModelColumn<I_M_ShipmentSchedule_Split, Object> COLUMN_M_ShipmentSchedule_Split_ID = new ModelColumn<>(I_M_ShipmentSchedule_Split.class, "M_ShipmentSchedule_Split_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_Split_ID = "M_ShipmentSchedule_Split_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_M_ShipmentSchedule_Split, Object> COLUMN_Processed = new ModelColumn<>(I_M_ShipmentSchedule_Split.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Quantity to Deliver.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyToDeliver (BigDecimal QtyToDeliver);

	/**
	 * Get Quantity to Deliver.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToDeliver();

	ModelColumn<I_M_ShipmentSchedule_Split, Object> COLUMN_QtyToDeliver = new ModelColumn<>(I_M_ShipmentSchedule_Split.class, "QtyToDeliver", null);
	String COLUMNNAME_QtyToDeliver = "QtyToDeliver";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_ShipmentSchedule_Split, Object> COLUMN_Updated = new ModelColumn<>(I_M_ShipmentSchedule_Split.class, "Updated", null);
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
	 * Set UserElementNumber1.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementNumber1 (@Nullable BigDecimal UserElementNumber1);

	/**
	 * Get UserElementNumber1.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getUserElementNumber1();

	ModelColumn<I_M_InOutLine, Object> COLUMN_UserElementNumber1 = new ModelColumn<>(I_M_InOutLine.class, "UserElementNumber1", null);
	String COLUMNNAME_UserElementNumber1 = "UserElementNumber1";

	/**
	 * Set UserElementNumber2.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementNumber2 (@Nullable BigDecimal UserElementNumber2);

	/**
	 * Get UserElementNumber2.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getUserElementNumber2();

	ModelColumn<I_M_InOutLine, Object> COLUMN_UserElementNumber2 = new ModelColumn<>(I_M_InOutLine.class, "UserElementNumber2", null);
	String COLUMNNAME_UserElementNumber2 = "UserElementNumber2";
}
