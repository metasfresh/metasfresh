/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_MailConfig
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_MailConfig extends org.compiere.model.PO implements I_AD_MailConfig, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 358956060L;

    /** Standard Constructor */
    public X_AD_MailConfig (Properties ctx, int AD_MailConfig_ID, String trxName)
    {
      super (ctx, AD_MailConfig_ID, trxName);
      /** if (AD_MailConfig_ID == 0)
        {
			setAD_MailBox_ID (0);
			setAD_MailConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_MailConfig (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_MailBox getAD_MailBox() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_MailBox_ID, org.compiere.model.I_AD_MailBox.class);
	}

	@Override
	public void setAD_MailBox(org.compiere.model.I_AD_MailBox AD_MailBox)
	{
		set_ValueFromPO(COLUMNNAME_AD_MailBox_ID, org.compiere.model.I_AD_MailBox.class, AD_MailBox);
	}

	/** Set Mail Box.
		@param AD_MailBox_ID Mail Box	  */
	@Override
	public void setAD_MailBox_ID (int AD_MailBox_ID)
	{
		if (AD_MailBox_ID < 1) 
			set_Value (COLUMNNAME_AD_MailBox_ID, null);
		else 
			set_Value (COLUMNNAME_AD_MailBox_ID, Integer.valueOf(AD_MailBox_ID));
	}

	/** Get Mail Box.
		@return Mail Box	  */
	@Override
	public int getAD_MailBox_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_MailBox_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mail Configuration.
		@param AD_MailConfig_ID Mail Configuration	  */
	@Override
	public void setAD_MailConfig_ID (int AD_MailConfig_ID)
	{
		if (AD_MailConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_MailConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_MailConfig_ID, Integer.valueOf(AD_MailConfig_ID));
	}

	/** Get Mail Configuration.
		@return Mail Configuration	  */
	@Override
	public int getAD_MailConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_MailConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	/** Set Prozess.
		@param AD_Process_ID 
		Prozess oder Bericht
	  */
	@Override
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	/** Get Prozess.
		@return Prozess oder Bericht
	  */
	@Override
	public int getAD_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * CustomType AD_Reference_ID=540142
	 * Reference name: AD_MailConfig_CustomType
	 */
	public static final int CUSTOMTYPE_AD_Reference_ID=540142;
	/** org.compiere.util.Login = L */
	public static final String CUSTOMTYPE_OrgCompiereUtilLogin = "L";
	/** Set Custom Type.
		@param CustomType Custom Type	  */
	@Override
	public void setCustomType (java.lang.String CustomType)
	{

		set_Value (COLUMNNAME_CustomType, CustomType);
	}

	/** Get Custom Type.
		@return Custom Type	  */
	@Override
	public java.lang.String getCustomType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CustomType);
	}

	/** Set Document BaseType.
		@param DocBaseType 
		Logical type of document
	  */
	@Override
	public void setDocBaseType (java.lang.String DocBaseType)
	{
		set_Value (COLUMNNAME_DocBaseType, DocBaseType);
	}

	/** Get Document BaseType.
		@return Logical type of document
	  */
	@Override
	public java.lang.String getDocBaseType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocBaseType);
	}

	/** Set Doc Sub Type.
		@param DocSubType 
		Document Sub Type
	  */
	@Override
	public void setDocSubType (java.lang.String DocSubType)
	{
		set_Value (COLUMNNAME_DocSubType, DocSubType);
	}

	/** Get Doc Sub Type.
		@return Document Sub Type
	  */
	@Override
	public java.lang.String getDocSubType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocSubType);
	}
}