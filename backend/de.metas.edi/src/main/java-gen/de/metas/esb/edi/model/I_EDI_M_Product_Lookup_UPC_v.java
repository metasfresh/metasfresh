package de.metas.esb.edi.model;

<<<<<<< HEAD
=======
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/** Generated Interface for EDI_M_Product_Lookup_UPC_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public interface I_EDI_M_Product_Lookup_UPC_v 
{

    /** TableName=EDI_M_Product_Lookup_UPC_v */
    public static final String Table_Name = "EDI_M_Product_Lookup_UPC_v";

    /** AD_Table_ID=540547 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
=======
@SuppressWarnings("unused")
public interface I_EDI_M_Product_Lookup_UPC_v 
{

	String Table_Name = "EDI_M_Product_Lookup_UPC_v";

//	/** AD_Table_ID=540547 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))


	/**
	 * Set GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setGLN (java.lang.String GLN);
=======
	void setGLN (@Nullable java.lang.String GLN);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getGLN();

    /** Column definition for GLN */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_Product_Lookup_UPC_v, Object> COLUMN_GLN = new org.adempiere.model.ModelColumn<I_EDI_M_Product_Lookup_UPC_v, Object>(I_EDI_M_Product_Lookup_UPC_v.class, "GLN", null);
    /** Column name GLN */
    public static final String COLUMNNAME_GLN = "GLN";
=======
	@Nullable java.lang.String getGLN();

	ModelColumn<I_EDI_M_Product_Lookup_UPC_v, Object> COLUMN_GLN = new ModelColumn<>(I_EDI_M_Product_Lookup_UPC_v.class, "GLN", null);
	String COLUMNNAME_GLN = "GLN";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
