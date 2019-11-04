/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.dpd.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DPD_Shipper_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DPD_Shipper_Config extends org.compiere.model.PO implements I_DPD_Shipper_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 468320853L;

    /** Standard Constructor */
    public X_DPD_Shipper_Config (Properties ctx, int DPD_Shipper_Config_ID, String trxName)
    {
      super (ctx, DPD_Shipper_Config_ID, trxName);
      /** if (DPD_Shipper_Config_ID == 0)
        {
			setDPD_Shipper_config_ID (0);
			setLoginApiUrl (null);
        } */
    }

    /** Load Constructor */
    public X_DPD_Shipper_Config (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Delis ID.
		@param DelisID Delis ID	  */
	@Override
	public void setDelisID (java.lang.String DelisID)
	{
		set_Value (COLUMNNAME_DelisID, DelisID);
	}

	/** Get Delis ID.
		@return Delis ID	  */
	@Override
	public java.lang.String getDelisID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DelisID);
	}

	/** Set Delis Password.
		@param DelisPassword Delis Password	  */
	@Override
	public void setDelisPassword (java.lang.String DelisPassword)
	{
		set_Value (COLUMNNAME_DelisPassword, DelisPassword);
	}

	/** Get Delis Password.
		@return Delis Password	  */
	@Override
	public java.lang.String getDelisPassword () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DelisPassword);
	}

	/** Set DPD Shipper Configuration.
		@param DPD_Shipper_config_ID DPD Shipper Configuration	  */
	@Override
	public void setDPD_Shipper_config_ID (int DPD_Shipper_config_ID)
	{
		if (DPD_Shipper_config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_Shipper_config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_Shipper_config_ID, Integer.valueOf(DPD_Shipper_config_ID));
	}

	/** Get DPD Shipper Configuration.
		@return DPD Shipper Configuration	  */
	@Override
	public int getDPD_Shipper_config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_Shipper_config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Login Api Url.
		@param LoginApiUrl Login Api Url	  */
	@Override
	public void setLoginApiUrl (java.lang.String LoginApiUrl)
	{
		set_Value (COLUMNNAME_LoginApiUrl, LoginApiUrl);
	}

	/** Get Login Api Url.
		@return Login Api Url	  */
	@Override
	public java.lang.String getLoginApiUrl () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LoginApiUrl);
	}

	@Override
	public org.compiere.model.I_M_Shipper getM_Shipper()
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
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
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

	/** Set Shipment Service Api Url.
		@param ShipmentServiceApiUrl Shipment Service Api Url	  */
	@Override
	public void setShipmentServiceApiUrl (java.lang.String ShipmentServiceApiUrl)
	{
		set_Value (COLUMNNAME_ShipmentServiceApiUrl, ShipmentServiceApiUrl);
	}

	/** Get Shipment Service Api Url.
		@return Shipment Service Api Url	  */
	@Override
	public java.lang.String getShipmentServiceApiUrl () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ShipmentServiceApiUrl);
	}
}