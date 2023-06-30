// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for GPLR_Report_Shipment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_GPLR_Report_Shipment extends org.compiere.model.PO implements I_GPLR_Report_Shipment, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -293172243L;

    /** Standard Constructor */
    public X_GPLR_Report_Shipment (final Properties ctx, final int GPLR_Report_Shipment_ID, @Nullable final String trxName)
    {
      super (ctx, GPLR_Report_Shipment_ID, trxName);
    }

    /** Load Constructor */
    public X_GPLR_Report_Shipment (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public org.compiere.model.I_GPLR_Report getGPLR_Report()
	{
		return get_ValueAsPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class);
	}

	@Override
	public void setGPLR_Report(final org.compiere.model.I_GPLR_Report GPLR_Report)
	{
		set_ValueFromPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class, GPLR_Report);
	}

	@Override
	public void setGPLR_Report_ID (final int GPLR_Report_ID)
	{
		if (GPLR_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, GPLR_Report_ID);
	}

	@Override
	public int getGPLR_Report_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_ID);
	}

	@Override
	public void setGPLR_Report_Shipment_ID (final int GPLR_Report_Shipment_ID)
	{
		if (GPLR_Report_Shipment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_Shipment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_Shipment_ID, GPLR_Report_Shipment_ID);
	}

	@Override
	public int getGPLR_Report_Shipment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_Shipment_ID);
	}

	@Override
	public void setIncoterm_Code (final @Nullable java.lang.String Incoterm_Code)
	{
		set_Value (COLUMNNAME_Incoterm_Code, Incoterm_Code);
	}

	@Override
	public java.lang.String getIncoterm_Code() 
	{
		return get_ValueAsString(COLUMNNAME_Incoterm_Code);
	}

	@Override
	public void setIncotermLocation (final @Nullable java.lang.String IncotermLocation)
	{
		set_Value (COLUMNNAME_IncotermLocation, IncotermLocation);
	}

	@Override
	public java.lang.String getIncotermLocation() 
	{
		return get_ValueAsString(COLUMNNAME_IncotermLocation);
	}

	@Override
	public void setMovementDate (final @Nullable java.sql.Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	@Override
	public java.sql.Timestamp getMovementDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MovementDate);
	}

	@Override
	public void setShippingInfo (final @Nullable java.lang.String ShippingInfo)
	{
		set_Value (COLUMNNAME_ShippingInfo, ShippingInfo);
	}

	@Override
	public java.lang.String getShippingInfo() 
	{
		return get_ValueAsString(COLUMNNAME_ShippingInfo);
	}

	@Override
	public void setShipTo_BPartnerName (final @Nullable java.lang.String ShipTo_BPartnerName)
	{
		set_Value (COLUMNNAME_ShipTo_BPartnerName, ShipTo_BPartnerName);
	}

	@Override
	public java.lang.String getShipTo_BPartnerName() 
	{
		return get_ValueAsString(COLUMNNAME_ShipTo_BPartnerName);
	}

	@Override
	public void setShipTo_BPartnerValue (final @Nullable java.lang.String ShipTo_BPartnerValue)
	{
		set_Value (COLUMNNAME_ShipTo_BPartnerValue, ShipTo_BPartnerValue);
	}

	@Override
	public java.lang.String getShipTo_BPartnerValue() 
	{
		return get_ValueAsString(COLUMNNAME_ShipTo_BPartnerValue);
	}

	@Override
	public void setShipTo_CountryCode (final @Nullable java.lang.String ShipTo_CountryCode)
	{
		set_Value (COLUMNNAME_ShipTo_CountryCode, ShipTo_CountryCode);
	}

	@Override
	public java.lang.String getShipTo_CountryCode() 
	{
		return get_ValueAsString(COLUMNNAME_ShipTo_CountryCode);
	}

	@Override
	public void setWarehouseExternalId (final @Nullable java.lang.String WarehouseExternalId)
	{
		set_Value (COLUMNNAME_WarehouseExternalId, WarehouseExternalId);
	}

	@Override
	public java.lang.String getWarehouseExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_WarehouseExternalId);
	}

	@Override
	public void setWarehouseName (final @Nullable java.lang.String WarehouseName)
	{
		set_Value (COLUMNNAME_WarehouseName, WarehouseName);
	}

	@Override
	public java.lang.String getWarehouseName() 
	{
		return get_ValueAsString(COLUMNNAME_WarehouseName);
	}

	@Override
	public void setWarehouseValue (final @Nullable java.lang.String WarehouseValue)
	{
		set_Value (COLUMNNAME_WarehouseValue, WarehouseValue);
	}

	@Override
	public java.lang.String getWarehouseValue() 
	{
		return get_ValueAsString(COLUMNNAME_WarehouseValue);
	}
}