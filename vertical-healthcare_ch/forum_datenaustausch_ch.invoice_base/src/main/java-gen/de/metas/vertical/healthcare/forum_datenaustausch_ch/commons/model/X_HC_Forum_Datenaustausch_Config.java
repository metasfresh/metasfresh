/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for HC_Forum_Datenaustausch_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_HC_Forum_Datenaustausch_Config extends org.compiere.model.PO implements I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 529857679L;

    /** Standard Constructor */
    public X_HC_Forum_Datenaustausch_Config (Properties ctx, int HC_Forum_Datenaustausch_Config_ID, String trxName)
    {
      super (ctx, HC_Forum_Datenaustausch_Config_ID, trxName);
      /** if (HC_Forum_Datenaustausch_Config_ID == 0)
        {
			setExportXmlVersion (null);
			setHC_Forum_Datenaustausch_ID (0);
        } */
    }

    /** Load Constructor */
    public X_HC_Forum_Datenaustausch_Config (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * ExportXmlVersion AD_Reference_ID=540921
	 * Reference name: ExportXmlVersion
	 */
	public static final int EXPORTXMLVERSION_AD_Reference_ID=540921;
	/** v440 = 4.4 */
	public static final String EXPORTXMLVERSION_V440 = "4.4";
	/** Set Export XML Version.
		@param ExportXmlVersion Export XML Version	  */
	@Override
	public void setExportXmlVersion (java.lang.String ExportXmlVersion)
	{

		set_Value (COLUMNNAME_ExportXmlVersion, ExportXmlVersion);
	}

	/** Get Export XML Version.
		@return Export XML Version	  */
	@Override
	public java.lang.String getExportXmlVersion () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExportXmlVersion);
	}

	/** Set HC_Forum_Datenaustausch.
		@param HC_Forum_Datenaustausch_ID HC_Forum_Datenaustausch	  */
	@Override
	public void setHC_Forum_Datenaustausch_ID (int HC_Forum_Datenaustausch_ID)
	{
		if (HC_Forum_Datenaustausch_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Forum_Datenaustausch_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Forum_Datenaustausch_ID, Integer.valueOf(HC_Forum_Datenaustausch_ID));
	}

	/** Get HC_Forum_Datenaustausch.
		@return HC_Forum_Datenaustausch	  */
	@Override
	public int getHC_Forum_Datenaustausch_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Forum_Datenaustausch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}