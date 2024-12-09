package de.metas.esb.edi.model;

<<<<<<< HEAD
=======
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/** Generated Interface for EDI_M_InOut_Overdelivery_C_OrderLine_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public interface I_EDI_M_InOut_Overdelivery_C_OrderLine_v 
{

    /** TableName=EDI_M_InOut_Overdelivery_C_OrderLine_v */
    public static final String Table_Name = "EDI_M_InOut_Overdelivery_C_OrderLine_v";

    /** AD_Table_ID=53741 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
=======
@SuppressWarnings("unused")
public interface I_EDI_M_InOut_Overdelivery_C_OrderLine_v 
{

	String Table_Name = "EDI_M_InOut_Overdelivery_C_OrderLine_v";

//	/** AD_Table_ID=53741 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))


	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_OrderLine_ID (int C_OrderLine_ID);
=======
	void setC_OrderLine_ID (int C_OrderLine_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_OrderLine_ID();

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_C_OrderLine>(I_EDI_M_InOut_Overdelivery_C_OrderLine_v.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";
=======
	int getC_OrderLine_ID();

	ModelColumn<I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_EDI_M_InOut_Overdelivery_C_OrderLine_v.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setM_InOut_ID (int M_InOut_ID);
=======
	void setM_InOut_ID (int M_InOut_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_M_InOut>(I_EDI_M_InOut_Overdelivery_C_OrderLine_v.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";
=======
	int getM_InOut_ID();

	@Nullable org.compiere.model.I_M_InOut getM_InOut();

	void setM_InOut(@Nullable org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_EDI_M_InOut_Overdelivery_C_OrderLine_v.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
