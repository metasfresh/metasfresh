package de.metas.esb.edi.model;


/** Generated Interface for EDI_M_Product_Lookup_UPC_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_M_Product_Lookup_UPC_v 
{

    /** TableName=EDI_M_Product_Lookup_UPC_v */
    public static final String Table_Name = "EDI_M_Product_Lookup_UPC_v";

    /** AD_Table_ID=540547 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGLN (java.lang.String GLN);

	/**
	 * Get GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGLN();

    /** Column definition for GLN */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_Product_Lookup_UPC_v, Object> COLUMN_GLN = new org.adempiere.model.ModelColumn<I_EDI_M_Product_Lookup_UPC_v, Object>(I_EDI_M_Product_Lookup_UPC_v.class, "GLN", null);
    /** Column name GLN */
    public static final String COLUMNNAME_GLN = "GLN";
}
