package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for EDI_DesadvLine_InOutLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_DesadvLine_InOutLine 
{

	String Table_Name = "EDI_DesadvLine_InOutLine";

//	/** AD_Table_ID=542413 */
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
	 * Set BPartner UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_BPartner_ID (int C_UOM_BPartner_ID);

	/**
	 * Get BPartner UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_BPartner_ID();

	String COLUMNNAME_C_UOM_BPartner_ID = "C_UOM_BPartner_ID";

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
	 * Set Invoicing-UOM.
	 * Maßeinheit in der die betreffende Zeile abgerechnet wird
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_Invoice_ID (int C_UOM_Invoice_ID);

	/**
	 * Get Invoicing-UOM.
	 * Maßeinheit in der die betreffende Zeile abgerechnet wird
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_Invoice_ID();

	String COLUMNNAME_C_UOM_Invoice_ID = "C_UOM_Invoice_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_DesadvLine_InOutLine, Object> COLUMN_Created = new ModelColumn<>(I_EDI_DesadvLine_InOutLine.class, "Created", null);
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
	 * Set DESADV Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEDI_DesadvLine_ID (int EDI_DesadvLine_ID);

	/**
	 * Get DESADV Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEDI_DesadvLine_ID();

	I_EDI_DesadvLine getEDI_DesadvLine();

	void setEDI_DesadvLine(I_EDI_DesadvLine EDI_DesadvLine);

	ModelColumn<I_EDI_DesadvLine_InOutLine, I_EDI_DesadvLine> COLUMN_EDI_DesadvLine_ID = new ModelColumn<>(I_EDI_DesadvLine_InOutLine.class, "EDI_DesadvLine_ID", I_EDI_DesadvLine.class);
	String COLUMNNAME_EDI_DesadvLine_ID = "EDI_DesadvLine_ID";

	/**
	 * Set EDI_DesadvLine_InOutLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEDI_DesadvLine_InOutLine_ID (int EDI_DesadvLine_InOutLine_ID);

	/**
	 * Get EDI_DesadvLine_InOutLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEDI_DesadvLine_InOutLine_ID();

	ModelColumn<I_EDI_DesadvLine_InOutLine, Object> COLUMN_EDI_DesadvLine_InOutLine_ID = new ModelColumn<>(I_EDI_DesadvLine_InOutLine.class, "EDI_DesadvLine_InOutLine_ID", null);
	String COLUMNNAME_EDI_DesadvLine_InOutLine_ID = "EDI_DesadvLine_InOutLine_ID";

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

	ModelColumn<I_EDI_DesadvLine_InOutLine, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_DesadvLine_InOutLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_InOutLine_ID();

	org.compiere.model.I_M_InOutLine getM_InOutLine();

	void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

	ModelColumn<I_EDI_DesadvLine_InOutLine, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_EDI_DesadvLine_InOutLine.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

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
	 * Set Delviered (invoicing UOM).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDeliveredInInvoiceUOM (@Nullable BigDecimal QtyDeliveredInInvoiceUOM);

	/**
	 * Get Delviered (invoicing UOM).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDeliveredInInvoiceUOM();

	ModelColumn<I_EDI_DesadvLine_InOutLine, Object> COLUMN_QtyDeliveredInInvoiceUOM = new ModelColumn<>(I_EDI_DesadvLine_InOutLine.class, "QtyDeliveredInInvoiceUOM", null);
	String COLUMNNAME_QtyDeliveredInInvoiceUOM = "QtyDeliveredInInvoiceUOM";

	/**
	 * Set Delivered (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyDeliveredInStockingUOM (BigDecimal QtyDeliveredInStockingUOM);

	/**
	 * Get Delivered (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDeliveredInStockingUOM();

	ModelColumn<I_EDI_DesadvLine_InOutLine, Object> COLUMN_QtyDeliveredInStockingUOM = new ModelColumn<>(I_EDI_DesadvLine_InOutLine.class, "QtyDeliveredInStockingUOM", null);
	String COLUMNNAME_QtyDeliveredInStockingUOM = "QtyDeliveredInStockingUOM";

	/**
	 * Set Delivered quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyDeliveredInUOM (BigDecimal QtyDeliveredInUOM);

	/**
	 * Get Delivered quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDeliveredInUOM();

	ModelColumn<I_EDI_DesadvLine_InOutLine, Object> COLUMN_QtyDeliveredInUOM = new ModelColumn<>(I_EDI_DesadvLine_InOutLine.class, "QtyDeliveredInUOM", null);
	String COLUMNNAME_QtyDeliveredInUOM = "QtyDeliveredInUOM";

	/**
	 * Set Qty Entered In BPartner UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEnteredInBPartnerUOM (@Nullable BigDecimal QtyEnteredInBPartnerUOM);

	/**
	 * Get Qty Entered In BPartner UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEnteredInBPartnerUOM();

	ModelColumn<I_EDI_DesadvLine_InOutLine, Object> COLUMN_QtyEnteredInBPartnerUOM = new ModelColumn<>(I_EDI_DesadvLine_InOutLine.class, "QtyEnteredInBPartnerUOM", null);
	String COLUMNNAME_QtyEnteredInBPartnerUOM = "QtyEnteredInBPartnerUOM";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_DesadvLine_InOutLine, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_DesadvLine_InOutLine.class, "Updated", null);
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
