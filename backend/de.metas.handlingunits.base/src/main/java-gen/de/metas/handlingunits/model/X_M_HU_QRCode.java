// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_QRCode
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_QRCode extends org.compiere.model.PO implements I_M_HU_QRCode, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1856747414L;

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
	public void setattributes (final java.lang.String attributes)
	{
		set_ValueNoCheck (COLUMNNAME_attributes, attributes);
	}

	@Override
	public java.lang.String getattributes() 
	{
		return get_ValueAsString(COLUMNNAME_attributes);
	}

	@Override
	public void setDisplayableQRCode (final java.lang.String DisplayableQRCode)
	{
		set_Value (COLUMNNAME_DisplayableQRCode, DisplayableQRCode);
	}

	@Override
	public java.lang.String getDisplayableQRCode() 
	{
		return get_ValueAsString(COLUMNNAME_DisplayableQRCode);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(final de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
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
	public void setRenderedQRCode (final java.lang.String RenderedQRCode)
	{
		set_Value (COLUMNNAME_RenderedQRCode, RenderedQRCode);
	}

	@Override
	public java.lang.String getRenderedQRCode() 
	{
		return get_ValueAsString(COLUMNNAME_RenderedQRCode);
	}

	@Override
	public void setUniqueId (final java.lang.String UniqueId)
	{
		set_ValueNoCheck (COLUMNNAME_UniqueId, UniqueId);
	}

	@Override
	public java.lang.String getUniqueId() 
	{
		return get_ValueAsString(COLUMNNAME_UniqueId);
	}
}