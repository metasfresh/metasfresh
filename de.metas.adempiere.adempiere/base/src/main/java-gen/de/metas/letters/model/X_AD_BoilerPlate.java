/** Generated Model - DO NOT CHANGE */
package de.metas.letters.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_BoilerPlate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_BoilerPlate extends org.compiere.model.PO implements I_AD_BoilerPlate, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1171863183L;

    /** Standard Constructor */
    public X_AD_BoilerPlate (Properties ctx, int AD_BoilerPlate_ID, String trxName)
    {
      super (ctx, AD_BoilerPlate_ID, trxName);
      /** if (AD_BoilerPlate_ID == 0)
        {
			setAD_BoilerPlate_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_BoilerPlate (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Textbaustein.
		@param AD_BoilerPlate_ID Textbaustein	  */
	@Override
	public void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID)
	{
		if (AD_BoilerPlate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_BoilerPlate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_BoilerPlate_ID, Integer.valueOf(AD_BoilerPlate_ID));
	}

	/** Get Textbaustein.
		@return Textbaustein	  */
	@Override
	public int getAD_BoilerPlate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_BoilerPlate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Process getJasperProcess() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_JasperProcess_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setJasperProcess(org.compiere.model.I_AD_Process JasperProcess)
	{
		set_ValueFromPO(COLUMNNAME_JasperProcess_ID, org.compiere.model.I_AD_Process.class, JasperProcess);
	}

	/** Set Jasper Process.
		@param JasperProcess_ID 
		The Jasper Process used by the printengine if any process defined
	  */
	@Override
	public void setJasperProcess_ID (int JasperProcess_ID)
	{
		if (JasperProcess_ID < 1) 
			set_Value (COLUMNNAME_JasperProcess_ID, null);
		else 
			set_Value (COLUMNNAME_JasperProcess_ID, Integer.valueOf(JasperProcess_ID));
	}

	/** Get Jasper Process.
		@return The Jasper Process used by the printengine if any process defined
	  */
	@Override
	public int getJasperProcess_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JasperProcess_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Betreff.
		@param Subject 
		Mail Betreff
	  */
	@Override
	public void setSubject (java.lang.String Subject)
	{
		set_Value (COLUMNNAME_Subject, Subject);
	}

	/** Get Betreff.
		@return Mail Betreff
	  */
	@Override
	public java.lang.String getSubject () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Subject);
	}

	/** Set Text.
		@param TextSnippet Text	  */
	@Override
	public void setTextSnippet (java.lang.String TextSnippet)
	{
		set_Value (COLUMNNAME_TextSnippet, TextSnippet);
	}

	/** Get Text.
		@return Text	  */
	@Override
	public java.lang.String getTextSnippet () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TextSnippet);
	}
}