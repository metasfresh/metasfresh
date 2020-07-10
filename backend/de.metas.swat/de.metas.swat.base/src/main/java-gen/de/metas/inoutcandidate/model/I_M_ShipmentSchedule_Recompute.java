package de.metas.inoutcandidate.model;


/** Generated Interface for M_ShipmentSchedule_Recompute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ShipmentSchedule_Recompute 
{

    /** TableName=M_ShipmentSchedule_Recompute */
    public static final String Table_Name = "M_ShipmentSchedule_Recompute";

    /** AD_Table_ID=541488 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/**
	 * Set Prozess-Instanz.
	 * Instanz eines Prozesses
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Prozess-Instanz.
	 * Instanz eines Prozesses
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PInstance_ID();

	public org.compiere.model.I_AD_PInstance getAD_PInstance();

	public void setAD_PInstance(org.compiere.model.I_AD_PInstance AD_PInstance);

    /** Column definition for AD_PInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_Recompute, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_Recompute, org.compiere.model.I_AD_PInstance>(I_M_ShipmentSchedule_Recompute.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Lieferdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Lieferdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ShipmentSchedule_ID();

	public de.metas.inoutcandidate.model.I_M_ShipmentSchedule getM_ShipmentSchedule();

	public void setM_ShipmentSchedule(de.metas.inoutcandidate.model.I_M_ShipmentSchedule M_ShipmentSchedule);

    /** Column definition for M_ShipmentSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_Recompute, de.metas.inoutcandidate.model.I_M_ShipmentSchedule> COLUMN_M_ShipmentSchedule_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_Recompute, de.metas.inoutcandidate.model.I_M_ShipmentSchedule>(I_M_ShipmentSchedule_Recompute.class, "M_ShipmentSchedule_ID", de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
    /** Column name M_ShipmentSchedule_ID */
    public static final String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";
}
