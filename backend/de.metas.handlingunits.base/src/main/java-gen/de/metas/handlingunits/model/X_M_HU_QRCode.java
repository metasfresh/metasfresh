// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_QRCode
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_QRCode extends org.compiere.model.PO implements I_M_HU_QRCode, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2052191480L;

    /** Standard Constructor */
    public X_M_HU_QRCode (final Properties ctx, final int M_HU_QRCode_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_QRCode_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_QRCode (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setattributes (final String attributes)
	{
		set_ValueNoCheck (COLUMNNAME_attributes, attributes);
	}

	@Override
	public String getattributes()
	{
		return get_ValueAsString(COLUMNNAME_attributes);
	}

	@Override
	public void setDisplayableQRCode (final String DisplayableQRCode)
	{
		set_Value (COLUMNNAME_DisplayableQRCode, DisplayableQRCode);
	}

	@Override
	public String getDisplayableQRCode()
	{
		return get_ValueAsString(COLUMNNAME_DisplayableQRCode);
	}

	@Override
	public void setM_HU_QRCode_ID (final int M_HU_QRCode_ID)
	{
		if (M_HU_QRCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_QRCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_QRCode_ID, M_HU_QRCode_ID);
	}

	@Override
	public int getM_HU_QRCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_QRCode_ID);
	}

	@Override
	public void setRenderedQRCode (final String RenderedQRCode)
	{
		set_Value (COLUMNNAME_RenderedQRCode, RenderedQRCode);
	}

	@Override
	public String getRenderedQRCode()
	{
		return get_ValueAsString(COLUMNNAME_RenderedQRCode);
	}

	@Override
	public void setUniqueId (final String UniqueId)
	{
		set_ValueNoCheck (COLUMNNAME_UniqueId, UniqueId);
	}

	@Override
	public String getUniqueId()
	{
		return get_ValueAsString(COLUMNNAME_UniqueId);
	}
}