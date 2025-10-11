// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Shipper
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Shipper extends org.compiere.model.PO implements I_M_Shipper, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 479216404L;

    /** Standard Constructor */
    public X_M_Shipper (final Properties ctx, final int M_Shipper_ID, @Nullable final String trxName)
    {
      super (ctx, M_Shipper_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Shipper (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setInternalName (final @Nullable java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName() 
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
	}

	@Override
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	/** 
	 * ShipperGateway AD_Reference_ID=540808
	 * Reference name: ShipperGateway
	 */
	public static final int SHIPPERGATEWAY_AD_Reference_ID=540808;
	/** GO = go */
	public static final String SHIPPERGATEWAY_GO = "go";
	/** Der Kurier = derKurier */
	public static final String SHIPPERGATEWAY_DerKurier = "derKurier";
	/** DHL = dhl */
	public static final String SHIPPERGATEWAY_DHL = "dhl";
	/** DPD = dpd */
	public static final String SHIPPERGATEWAY_DPD = "dpd";
	/** nShift = nshift */
	public static final String SHIPPERGATEWAY_NShift = "nshift";
	@Override
	public void setShipperGateway (final @Nullable java.lang.String ShipperGateway)
	{
		set_Value (COLUMNNAME_ShipperGateway, ShipperGateway);
	}

	@Override
	public java.lang.String getShipperGateway() 
	{
		return get_ValueAsString(COLUMNNAME_ShipperGateway);
	}

	@Override
	public void setTrackingURL (final @Nullable java.lang.String TrackingURL)
	{
		set_Value (COLUMNNAME_TrackingURL, TrackingURL);
	}

	@Override
	public java.lang.String getTrackingURL() 
	{
		return get_ValueAsString(COLUMNNAME_TrackingURL);
	}

	@Override
	public void setValue (final @Nullable java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}