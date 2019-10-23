/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Shipment_Declaration_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Shipment_Declaration_Config extends org.compiere.model.PO implements I_M_Shipment_Declaration_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -874398582L;

    /** Standard Constructor */
    public X_M_Shipment_Declaration_Config (Properties ctx, int M_Shipment_Declaration_Config_ID, String trxName)
    {
      super (ctx, M_Shipment_Declaration_Config_ID, trxName);
      /** if (M_Shipment_Declaration_Config_ID == 0)
        {
			setC_DocType_ID (0);
			setDocumentLinesNumber (0); // 6
			setM_Shipment_Declaration_Config_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_Shipment_Declaration_Config (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_C_DocType getC_DocType_Correction() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_Correction_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType_Correction(org.compiere.model.I_C_DocType C_DocType_Correction)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_Correction_ID, org.compiere.model.I_C_DocType.class, C_DocType_Correction);
	}

	/** Set Belegart korrektur.
		@param C_DocType_Correction_ID Belegart korrektur	  */
	@Override
	public void setC_DocType_Correction_ID (int C_DocType_Correction_ID)
	{
		if (C_DocType_Correction_ID < 1) 
			set_Value (COLUMNNAME_C_DocType_Correction_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_Correction_ID, Integer.valueOf(C_DocType_Correction_ID));
	}

	/** Get Belegart korrektur.
		@return Belegart korrektur	  */
	@Override
	public int getC_DocType_Correction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_Correction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
	}

	/** Set Belegart.
		@param C_DocType_ID 
		Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DocumentLinesNumber.
		@param DocumentLinesNumber DocumentLinesNumber	  */
	@Override
	public void setDocumentLinesNumber (int DocumentLinesNumber)
	{
		set_Value (COLUMNNAME_DocumentLinesNumber, Integer.valueOf(DocumentLinesNumber));
	}

	/** Get DocumentLinesNumber.
		@return DocumentLinesNumber	  */
	@Override
	public int getDocumentLinesNumber () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocumentLinesNumber);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Abgabemeldung Konfiguration.
		@param M_Shipment_Declaration_Config_ID Abgabemeldung Konfiguration	  */
	@Override
	public void setM_Shipment_Declaration_Config_ID (int M_Shipment_Declaration_Config_ID)
	{
		if (M_Shipment_Declaration_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipment_Declaration_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipment_Declaration_Config_ID, Integer.valueOf(M_Shipment_Declaration_Config_ID));
	}

	/** Get Abgabemeldung Konfiguration.
		@return Abgabemeldung Konfiguration	  */
	@Override
	public int getM_Shipment_Declaration_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipment_Declaration_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}