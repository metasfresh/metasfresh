package de.metas.pos.repository.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_POS_JournalLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_POS_JournalLine 
{

	String Table_Name = "C_POS_JournalLine";

//	/** AD_Table_ID=542439 */
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
	 * Set Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmount (BigDecimal Amount);

	/**
	 * Get Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount();

	ModelColumn<I_C_POS_JournalLine, Object> COLUMN_Amount = new ModelColumn<>(I_C_POS_JournalLine.class, "Amount", null);
	String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Cashier.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCashier_ID (int Cashier_ID);

	/**
	 * Get Cashier.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCashier_ID();

	String COLUMNNAME_Cashier_ID = "Cashier_ID";

	/**
	 * Set POS Cash Journal.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_Journal_ID (int C_POS_Journal_ID);

	/**
	 * Get POS Cash Journal.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_Journal_ID();

	de.metas.pos.repository.model.I_C_POS_Journal getC_POS_Journal();

	void setC_POS_Journal(de.metas.pos.repository.model.I_C_POS_Journal C_POS_Journal);

	ModelColumn<I_C_POS_JournalLine, de.metas.pos.repository.model.I_C_POS_Journal> COLUMN_C_POS_Journal_ID = new ModelColumn<>(I_C_POS_JournalLine.class, "C_POS_Journal_ID", de.metas.pos.repository.model.I_C_POS_Journal.class);
	String COLUMNNAME_C_POS_Journal_ID = "C_POS_Journal_ID";

	/**
	 * Set POS Cash Journal Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_JournalLine_ID (int C_POS_JournalLine_ID);

	/**
	 * Get POS Cash Journal Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_JournalLine_ID();

	ModelColumn<I_C_POS_JournalLine, Object> COLUMN_C_POS_JournalLine_ID = new ModelColumn<>(I_C_POS_JournalLine.class, "C_POS_JournalLine_ID", null);
	String COLUMNNAME_C_POS_JournalLine_ID = "C_POS_JournalLine_ID";

	/**
	 * Set POS Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_POS_Order_ID (int C_POS_Order_ID);

	/**
	 * Get POS Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_POS_Order_ID();

	@Nullable de.metas.pos.repository.model.I_C_POS_Order getC_POS_Order();

	void setC_POS_Order(@Nullable de.metas.pos.repository.model.I_C_POS_Order C_POS_Order);

	ModelColumn<I_C_POS_JournalLine, de.metas.pos.repository.model.I_C_POS_Order> COLUMN_C_POS_Order_ID = new ModelColumn<>(I_C_POS_JournalLine.class, "C_POS_Order_ID", de.metas.pos.repository.model.I_C_POS_Order.class);
	String COLUMNNAME_C_POS_Order_ID = "C_POS_Order_ID";

	/**
	 * Set POS Payment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_POS_Payment_ID (int C_POS_Payment_ID);

	/**
	 * Get POS Payment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_POS_Payment_ID();

	@Nullable de.metas.pos.repository.model.I_C_POS_Payment getC_POS_Payment();

	void setC_POS_Payment(@Nullable de.metas.pos.repository.model.I_C_POS_Payment C_POS_Payment);

	ModelColumn<I_C_POS_JournalLine, de.metas.pos.repository.model.I_C_POS_Payment> COLUMN_C_POS_Payment_ID = new ModelColumn<>(I_C_POS_JournalLine.class, "C_POS_Payment_ID", de.metas.pos.repository.model.I_C_POS_Payment.class);
	String COLUMNNAME_C_POS_Payment_ID = "C_POS_Payment_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_POS_JournalLine, Object> COLUMN_Created = new ModelColumn<>(I_C_POS_JournalLine.class, "Created", null);
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
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_POS_JournalLine, Object> COLUMN_Description = new ModelColumn<>(I_C_POS_JournalLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_C_POS_JournalLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_POS_JournalLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType (java.lang.String Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType();

	ModelColumn<I_C_POS_JournalLine, Object> COLUMN_Type = new ModelColumn<>(I_C_POS_JournalLine.class, "Type", null);
	String COLUMNNAME_Type = "Type";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_POS_JournalLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_POS_JournalLine.class, "Updated", null);
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
