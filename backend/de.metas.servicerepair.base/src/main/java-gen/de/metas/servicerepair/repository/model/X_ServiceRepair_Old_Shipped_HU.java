// Generated Model - DO NOT CHANGE
package de.metas.servicerepair.repository.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ServiceRepair_Old_Shipped_HU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ServiceRepair_Old_Shipped_HU extends org.compiere.model.PO implements I_ServiceRepair_Old_Shipped_HU, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1009577124L;

    /** Standard Constructor */
    public X_ServiceRepair_Old_Shipped_HU (final Properties ctx, final int ServiceRepair_Old_Shipped_HU_ID, @Nullable final String trxName)
    {
      super (ctx, ServiceRepair_Old_Shipped_HU_ID, trxName);
    }

    /** Load Constructor */
    public X_ServiceRepair_Old_Shipped_HU (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBPartnerName (final @Nullable java.lang.String BPartnerName)
	{
		set_Value (COLUMNNAME_BPartnerName, BPartnerName);
	}

	@Override
	public java.lang.String getBPartnerName() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerName);
	}

	@Override
	public void setBPartnerValue (final @Nullable java.lang.String BPartnerValue)
	{
		set_Value (COLUMNNAME_BPartnerValue, BPartnerValue);
	}

	@Override
	public java.lang.String getBPartnerValue() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerValue);
	}

	@Override
	public void setC_BPartner_Customer_ID (final int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, C_BPartner_Customer_ID);
	}

	@Override
	public int getC_BPartner_Customer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Customer_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setProductNo (final java.lang.String ProductNo)
	{
		set_Value (COLUMNNAME_ProductNo, ProductNo);
	}

	@Override
	public java.lang.String getProductNo() 
	{
		return get_ValueAsString(COLUMNNAME_ProductNo);
	}

	@Override
	public void setReference (final @Nullable java.lang.String Reference)
	{
		set_Value (COLUMNNAME_Reference, Reference);
	}

	@Override
	public java.lang.String getReference() 
	{
		return get_ValueAsString(COLUMNNAME_Reference);
	}

	@Override
	public void setSerialNo (final @Nullable java.lang.String SerialNo)
	{
		set_Value (COLUMNNAME_SerialNo, SerialNo);
	}

	@Override
	public java.lang.String getSerialNo() 
	{
		return get_ValueAsString(COLUMNNAME_SerialNo);
	}

	@Override
	public void setServiceRepair_Old_Shipped_HU_ID (final int ServiceRepair_Old_Shipped_HU_ID)
	{
		if (ServiceRepair_Old_Shipped_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ServiceRepair_Old_Shipped_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ServiceRepair_Old_Shipped_HU_ID, ServiceRepair_Old_Shipped_HU_ID);
	}

	@Override
	public int getServiceRepair_Old_Shipped_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ServiceRepair_Old_Shipped_HU_ID);
	}

	@Override
	public void setWarrantyStartDate (final @Nullable java.sql.Timestamp WarrantyStartDate)
	{
		set_Value (COLUMNNAME_WarrantyStartDate, WarrantyStartDate);
	}

	@Override
	public java.sql.Timestamp getWarrantyStartDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_WarrantyStartDate);
	}
}