// Generated Model - DO NOT CHANGE
package de.metas.material.cockpit.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_AvailableForSales_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MD_AvailableForSales_Config extends org.compiere.model.PO implements I_MD_AvailableForSales_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1746797042L;

    /** Standard Constructor */
    public X_MD_AvailableForSales_Config (final Properties ctx, final int MD_AvailableForSales_Config_ID, @Nullable final String trxName)
    {
      super (ctx, MD_AvailableForSales_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_AvailableForSales_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAsyncTimeoutMillis (final int AsyncTimeoutMillis)
	{
		set_Value (COLUMNNAME_AsyncTimeoutMillis, AsyncTimeoutMillis);
	}

	@Override
	public int getAsyncTimeoutMillis() 
	{
		return get_ValueAsInt(COLUMNNAME_AsyncTimeoutMillis);
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
	public org.compiere.model.I_AD_Color getInsufficientQtyAvailableForSalesColor()
	{
		return get_ValueAsPO(COLUMNNAME_InsufficientQtyAvailableForSalesColor_ID, org.compiere.model.I_AD_Color.class);
	}

	@Override
	public void setInsufficientQtyAvailableForSalesColor(final org.compiere.model.I_AD_Color InsufficientQtyAvailableForSalesColor)
	{
		set_ValueFromPO(COLUMNNAME_InsufficientQtyAvailableForSalesColor_ID, org.compiere.model.I_AD_Color.class, InsufficientQtyAvailableForSalesColor);
	}

	@Override
	public void setInsufficientQtyAvailableForSalesColor_ID (final int InsufficientQtyAvailableForSalesColor_ID)
	{
		if (InsufficientQtyAvailableForSalesColor_ID < 1) 
			set_Value (COLUMNNAME_InsufficientQtyAvailableForSalesColor_ID, null);
		else 
			set_Value (COLUMNNAME_InsufficientQtyAvailableForSalesColor_ID, InsufficientQtyAvailableForSalesColor_ID);
	}

	@Override
	public int getInsufficientQtyAvailableForSalesColor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_InsufficientQtyAvailableForSalesColor_ID);
	}

	@Override
	public void setIsAsync (final boolean IsAsync)
	{
		set_Value (COLUMNNAME_IsAsync, IsAsync);
	}

	@Override
	public boolean isAsync() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAsync);
	}

	@Override
	public void setIsFeatureActivated (final boolean IsFeatureActivated)
	{
		set_Value (COLUMNNAME_IsFeatureActivated, IsFeatureActivated);
	}

	@Override
	public boolean isFeatureActivated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFeatureActivated);
	}

	@Override
	public void setMD_AvailableForSales_Config_ID (final int MD_AvailableForSales_Config_ID)
	{
		if (MD_AvailableForSales_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_AvailableForSales_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_AvailableForSales_Config_ID, MD_AvailableForSales_Config_ID);
	}

	@Override
	public int getMD_AvailableForSales_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_AvailableForSales_Config_ID);
	}

	@Override
	public void setSalesOrderLookBehindHours (final int SalesOrderLookBehindHours)
	{
		set_Value (COLUMNNAME_SalesOrderLookBehindHours, SalesOrderLookBehindHours);
	}

	@Override
	public int getSalesOrderLookBehindHours() 
	{
		return get_ValueAsInt(COLUMNNAME_SalesOrderLookBehindHours);
	}

	@Override
	public void setShipmentDateLookAheadHours (final int ShipmentDateLookAheadHours)
	{
		set_Value (COLUMNNAME_ShipmentDateLookAheadHours, ShipmentDateLookAheadHours);
	}

	@Override
	public int getShipmentDateLookAheadHours() 
	{
		return get_ValueAsInt(COLUMNNAME_ShipmentDateLookAheadHours);
	}
}