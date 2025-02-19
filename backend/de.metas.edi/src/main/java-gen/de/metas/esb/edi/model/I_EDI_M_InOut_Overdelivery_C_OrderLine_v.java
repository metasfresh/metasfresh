package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for EDI_M_InOut_Overdelivery_C_OrderLine_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_M_InOut_Overdelivery_C_OrderLine_v 
{

	String Table_Name = "EDI_M_InOut_Overdelivery_C_OrderLine_v";

//	/** AD_Table_ID=53741 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	ModelColumn<I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_EDI_M_InOut_Overdelivery_C_OrderLine_v.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

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

	ModelColumn<I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_EDI_M_InOut_Overdelivery_C_OrderLine_v.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";
}
