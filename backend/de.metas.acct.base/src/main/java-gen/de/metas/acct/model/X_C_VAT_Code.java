/** Generated Model - DO NOT CHANGE */
package de.metas.acct.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_VAT_Code
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_VAT_Code extends org.compiere.model.PO implements I_C_VAT_Code, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 390507226L;

    /** Standard Constructor */
    public X_C_VAT_Code (Properties ctx, int C_VAT_Code_ID, String trxName)
    {
      super (ctx, C_VAT_Code_ID, trxName);
      /** if (C_VAT_Code_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setC_Tax_ID (0);
			setC_VAT_Code_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
			setVATCode (null);
        } */
    }

    /** Load Constructor */
    public X_C_VAT_Code (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema()
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	/** Set Buchführungs-Schema.
		@param C_AcctSchema_ID 
		Stammdaten für Buchhaltung
	  */
	@Override
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Buchführungs-Schema.
		@return Stammdaten für Buchhaltung
	  */
	@Override
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Steuer.
		@param C_Tax_ID 
		Steuerart
	  */
	@Override
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Steuer.
		@return Steuerart
	  */
	@Override
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set VAT Code.
		@param C_VAT_Code_ID VAT Code	  */
	@Override
	public void setC_VAT_Code_ID (int C_VAT_Code_ID)
	{
		if (C_VAT_Code_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_VAT_Code_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_VAT_Code_ID, Integer.valueOf(C_VAT_Code_ID));
	}

	/** Get VAT Code.
		@return VAT Code	  */
	@Override
	public int getC_VAT_Code_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_VAT_Code_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** 
	 * IsSOTrx AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISSOTRX_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISSOTRX_Yes = "Y";
	/** No = N */
	public static final String ISSOTRX_No = "N";
	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	@Override
	public void setIsSOTrx (java.lang.String IsSOTrx)
	{

		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	@Override
	public java.lang.String getIsSOTrx () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsSOTrx);
	}

	/** Set Gültig ab.
		@param ValidFrom 
		Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Gültig bis.
		@param ValidTo 
		Gültig bis inklusiv (letzter Tag)
	  */
	@Override
	public void setValidTo (java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Gültig bis.
		@return Gültig bis inklusiv (letzter Tag)
	  */
	@Override
	public java.sql.Timestamp getValidTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidTo);
	}

	/** Set VAT Code.
		@param VATCode VAT Code	  */
	@Override
	public void setVATCode (java.lang.String VATCode)
	{
		set_Value (COLUMNNAME_VATCode, VATCode);
	}

	/** Get VAT Code.
		@return VAT Code	  */
	@Override
	public java.lang.String getVATCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VATCode);
	}
}