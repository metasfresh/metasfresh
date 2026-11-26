package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Doc_TextLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Doc_TextLine 
{

	String Table_Name = "C_Doc_TextLine";

//	/** AD_Table_ID=542648 */
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
	 * Set Text Line.
	 * Free-text line in a sales document.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Doc_TextLine_ID (int C_Doc_TextLine_ID);

	/**
	 * Get Text Line.
	 * Free-text line in a sales document.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Doc_TextLine_ID();

	ModelColumn<I_C_Doc_TextLine, Object> COLUMN_C_Doc_TextLine_ID = new ModelColumn<>(I_C_Doc_TextLine.class, "C_Doc_TextLine_ID", null);
	String COLUMNNAME_C_Doc_TextLine_ID = "C_Doc_TextLine_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	ModelColumn<I_C_Doc_TextLine, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_Doc_TextLine.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Doc_TextLine, Object> COLUMN_Created = new ModelColumn<>(I_C_Doc_TextLine.class, "Created", null);
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

	ModelColumn<I_C_Doc_TextLine, Object> COLUMN_CreatedBy = new ModelColumn<>(I_C_Doc_TextLine.class, "CreatedBy", null);
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

	ModelColumn<I_C_Doc_TextLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Doc_TextLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (BigDecimal Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getLine();

	ModelColumn<I_C_Doc_TextLine, Object> COLUMN_Line = new ModelColumn<>(I_C_Doc_TextLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOut_ID();

	ModelColumn<I_C_Doc_TextLine, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_C_Doc_TextLine.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Text.
	 * The free text of this line;
 may be empty, which prints as a blank line.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTextLine (@Nullable java.lang.String TextLine);

	/**
	 * Get Text.
	 * The free text of this line;
 may be empty, which prints as a blank line.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTextLine();

	ModelColumn<I_C_Doc_TextLine, Object> COLUMN_TextLine = new ModelColumn<>(I_C_Doc_TextLine.class, "TextLine", null);
	String COLUMNNAME_TextLine = "TextLine";

	/**
	 * Set Scope.
	 * Whether the text applies to the following lines or to the whole document.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTextLineScope (java.lang.String TextLineScope);

	/**
	 * Get Scope.
	 * Whether the text applies to the following lines or to the whole document.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTextLineScope();

	ModelColumn<I_C_Doc_TextLine, Object> COLUMN_TextLineScope = new ModelColumn<>(I_C_Doc_TextLine.class, "TextLineScope", null);
	String COLUMNNAME_TextLineScope = "TextLineScope";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Doc_TextLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_Doc_TextLine.class, "Updated", null);
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

	ModelColumn<I_C_Doc_TextLine, Object> COLUMN_UpdatedBy = new ModelColumn<>(I_C_Doc_TextLine.class, "UpdatedBy", null);
	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
