/** Generated Model - DO NOT CHANGE */
package de.metas.payment.esr.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for x_esr_import_in_c_bankstatement_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_x_esr_import_in_c_bankstatement_v extends org.compiere.model.PO implements I_x_esr_import_in_c_bankstatement_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 310319026L;

    /** Standard Constructor */
    public X_x_esr_import_in_c_bankstatement_v (Properties ctx, int x_esr_import_in_c_bankstatement_v_ID, String trxName)
    {
      super (ctx, x_esr_import_in_c_bankstatement_v_ID, trxName);
      /** if (x_esr_import_in_c_bankstatement_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_x_esr_import_in_c_bankstatement_v (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_BankStatement getC_BankStatement() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BankStatement_ID, org.compiere.model.I_C_BankStatement.class);
	}

	@Override
	public void setC_BankStatement(org.compiere.model.I_C_BankStatement C_BankStatement)
	{
		set_ValueFromPO(COLUMNNAME_C_BankStatement_ID, org.compiere.model.I_C_BankStatement.class, C_BankStatement);
	}

	/** Set Bankauszug.
		@param C_BankStatement_ID 
		Bank Statement of account
	  */
	@Override
	public void setC_BankStatement_ID (int C_BankStatement_ID)
	{
		if (C_BankStatement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_ID, Integer.valueOf(C_BankStatement_ID));
	}

	/** Get Bankauszug.
		@return Bank Statement of account
	  */
	@Override
	public int getC_BankStatement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankStatement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Belegdatum.
		@param DateDoc 
		Datum des Belegs
	  */
	@Override
	public void setDateDoc (java.sql.Timestamp DateDoc)
	{
		set_ValueNoCheck (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Belegdatum.
		@return Datum des Belegs
	  */
	@Override
	public java.sql.Timestamp getDateDoc () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	@Override
	public de.metas.payment.esr.model.I_ESR_Import getESR_Import() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_ESR_Import_ID, de.metas.payment.esr.model.I_ESR_Import.class);
	}

	@Override
	public void setESR_Import(de.metas.payment.esr.model.I_ESR_Import ESR_Import)
	{
		set_ValueFromPO(COLUMNNAME_ESR_Import_ID, de.metas.payment.esr.model.I_ESR_Import.class, ESR_Import);
	}

	/** Set ESR Zahlungsimport.
		@param ESR_Import_ID ESR Zahlungsimport	  */
	@Override
	public void setESR_Import_ID (int ESR_Import_ID)
	{
		if (ESR_Import_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, Integer.valueOf(ESR_Import_ID));
	}

	/** Get ESR Zahlungsimport.
		@return ESR Zahlungsimport	  */
	@Override
	public int getESR_Import_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ESR_Import_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}