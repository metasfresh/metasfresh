package de.metas.esb.edi.model;


/** Generated Interface for EDI_M_InOut_Overdelivery_C_OrderLine_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_M_InOut_Overdelivery_C_OrderLine_v 
{

    /** TableName=EDI_M_InOut_Overdelivery_C_OrderLine_v */
    public static final String Table_Name = "EDI_M_InOut_Overdelivery_C_OrderLine_v";

    /** AD_Table_ID=53741 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_C_OrderLine>(I_EDI_M_InOut_Overdelivery_C_OrderLine_v.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_M_InOut>(I_EDI_M_InOut_Overdelivery_C_OrderLine_v.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";
}
