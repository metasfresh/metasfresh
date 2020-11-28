package de.metas.contracts.model;


/** Generated Interface for C_Invoice_Candidate_Assignment_Aggregate_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Invoice_Candidate_Assignment_Aggregate_V 
{

    /** TableName=C_Invoice_Candidate_Assignment_Aggregate_V */
    public static final String Table_Name = "C_Invoice_Candidate_Assignment_Aggregate_V";

    /** AD_Table_ID=541009 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

	/**
	 * Set Zugeordneter Geldbetrag.
	 * Zugeordneter Geldbetrag, in der Währung des Vertrags-Rechnungskandidaten.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAssignedMoneyAmount (java.math.BigDecimal AssignedMoneyAmount);

	/**
	 * Get Zugeordneter Geldbetrag.
	 * Zugeordneter Geldbetrag, in der Währung des Vertrags-Rechnungskandidaten.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAssignedMoneyAmount();

    /** Column definition for AssignedMoneyAmount */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment_Aggregate_V, Object> COLUMN_AssignedMoneyAmount = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment_Aggregate_V, Object>(I_C_Invoice_Candidate_Assignment_Aggregate_V.class, "AssignedMoneyAmount", null);
    /** Column name AssignedMoneyAmount */
    public static final String COLUMNNAME_AssignedMoneyAmount = "AssignedMoneyAmount";

	/**
	 * Set Zugeordnete Menge.
	 * Zugeordneter Menge in der Maßeinheit des jeweiligen Produktes
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAssignedQuantity (java.math.BigDecimal AssignedQuantity);

	/**
	 * Get Zugeordnete Menge.
	 * Zugeordneter Menge in der Maßeinheit des jeweiligen Produktes
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAssignedQuantity();

    /** Column definition for AssignedQuantity */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment_Aggregate_V, Object> COLUMN_AssignedQuantity = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment_Aggregate_V, Object>(I_C_Invoice_Candidate_Assignment_Aggregate_V.class, "AssignedQuantity", null);
    /** Column name AssignedQuantity */
    public static final String COLUMNNAME_AssignedQuantity = "AssignedQuantity";

	/**
	 * Set C_Flatrate_RefundConfig.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_RefundConfig_ID (int C_Flatrate_RefundConfig_ID);

	/**
	 * Get C_Flatrate_RefundConfig.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_RefundConfig_ID();

	public de.metas.contracts.model.I_C_Flatrate_RefundConfig getC_Flatrate_RefundConfig();

	public void setC_Flatrate_RefundConfig(de.metas.contracts.model.I_C_Flatrate_RefundConfig C_Flatrate_RefundConfig);

    /** Column definition for C_Flatrate_RefundConfig_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment_Aggregate_V, de.metas.contracts.model.I_C_Flatrate_RefundConfig> COLUMN_C_Flatrate_RefundConfig_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment_Aggregate_V, de.metas.contracts.model.I_C_Flatrate_RefundConfig>(I_C_Invoice_Candidate_Assignment_Aggregate_V.class, "C_Flatrate_RefundConfig_ID", de.metas.contracts.model.I_C_Flatrate_RefundConfig.class);
    /** Column name C_Flatrate_RefundConfig_ID */
    public static final String COLUMNNAME_C_Flatrate_RefundConfig_ID = "C_Flatrate_RefundConfig_ID";

	/**
	 * Set Vertrag-Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_Term_ID (int C_Invoice_Candidate_Term_ID);

	/**
	 * Get Vertrag-Rechnungskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_Term_ID();

    /** Column definition for C_Invoice_Candidate_Term_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment_Aggregate_V, Object> COLUMN_C_Invoice_Candidate_Term_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate_Assignment_Aggregate_V, Object>(I_C_Invoice_Candidate_Assignment_Aggregate_V.class, "C_Invoice_Candidate_Term_ID", null);
    /** Column name C_Invoice_Candidate_Term_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_Term_ID = "C_Invoice_Candidate_Term_ID";
}
