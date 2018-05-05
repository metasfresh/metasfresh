/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.derkurier.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DerKurier_Shipper_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DerKurier_Shipper_Config extends org.compiere.model.PO implements I_DerKurier_Shipper_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1999426518L;

    /** Standard Constructor */
    public X_DerKurier_Shipper_Config (Properties ctx, int DerKurier_Shipper_Config_ID, String trxName)
    {
      super (ctx, DerKurier_Shipper_Config_ID, trxName);
      /** if (DerKurier_Shipper_Config_ID == 0)
        {
			setAD_Sequence_ID (0);
			setAPIServerBaseURL (null);
			setDerKurier_Shipper_Config_ID (0);
			setDK_CustomerNumber (null);
			setM_Shipper_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DerKurier_Shipper_Config (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_AD_Sequence getAD_Sequence() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Sequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setAD_Sequence(org.compiere.model.I_AD_Sequence AD_Sequence)
	{
		set_ValueFromPO(COLUMNNAME_AD_Sequence_ID, org.compiere.model.I_AD_Sequence.class, AD_Sequence);
	}

	/** Set Reihenfolge.
		@param AD_Sequence_ID 
		Nummernfolgen für Belege
	  */
	@Override
	public void setAD_Sequence_ID (int AD_Sequence_ID)
	{
		if (AD_Sequence_ID < 1) 
			set_Value (COLUMNNAME_AD_Sequence_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Sequence_ID, Integer.valueOf(AD_Sequence_ID));
	}

	/** Get Reihenfolge.
		@return Nummernfolgen für Belege
	  */
	@Override
	public int getAD_Sequence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Sequence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set REST API Server BaseURL.
		@param APIServerBaseURL REST API Server BaseURL	  */
	@Override
	public void setAPIServerBaseURL (java.lang.String APIServerBaseURL)
	{
		set_Value (COLUMNNAME_APIServerBaseURL, APIServerBaseURL);
	}

	/** Get REST API Server BaseURL.
		@return REST API Server BaseURL	  */
	@Override
	public java.lang.String getAPIServerBaseURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_APIServerBaseURL);
	}

	/** Set DerKurier Shipper Configuration.
		@param DerKurier_Shipper_Config_ID DerKurier Shipper Configuration	  */
	@Override
	public void setDerKurier_Shipper_Config_ID (int DerKurier_Shipper_Config_ID)
	{
		if (DerKurier_Shipper_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DerKurier_Shipper_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DerKurier_Shipper_Config_ID, Integer.valueOf(DerKurier_Shipper_Config_ID));
	}

	/** Get DerKurier Shipper Configuration.
		@return DerKurier Shipper Configuration	  */
	@Override
	public int getDerKurier_Shipper_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DerKurier_Shipper_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kundennummer.
		@param DK_CustomerNumber Kundennummer	  */
	@Override
	public void setDK_CustomerNumber (java.lang.String DK_CustomerNumber)
	{
		set_Value (COLUMNNAME_DK_CustomerNumber, DK_CustomerNumber);
	}

	/** Get Kundennummer.
		@return Kundennummer	  */
	@Override
	public java.lang.String getDK_CustomerNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DK_CustomerNumber);
	}

	/** Set EMail Empfänger.
		@param EMail_To 
		EMail address to send requests to - e.g. edi@manufacturer.com 
	  */
	@Override
	public void setEMail_To (java.lang.String EMail_To)
	{
		set_Value (COLUMNNAME_EMail_To, EMail_To);
	}

	/** Get EMail Empfänger.
		@return EMail address to send requests to - e.g. edi@manufacturer.com 
	  */
	@Override
	public java.lang.String getEMail_To () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail_To);
	}

	@Override
	public org.compiere.model.I_M_Shipper getM_Shipper() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	/** Set Lieferweg.
		@param M_Shipper_ID 
		Methode oder Art der Warenlieferung
	  */
	@Override
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/** Get Lieferweg.
		@return Methode oder Art der Warenlieferung
	  */
	@Override
	public int getM_Shipper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}