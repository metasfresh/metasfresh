// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for MobileUI_HUManager_LayoutSection
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_HUManager_LayoutSection extends org.compiere.model.PO implements I_MobileUI_HUManager_LayoutSection, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 915665874L;

    /** Standard Constructor */
    public X_MobileUI_HUManager_LayoutSection (final Properties ctx, final int MobileUI_HUManager_LayoutSection_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_HUManager_LayoutSection_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_HUManager_LayoutSection (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * LayoutSection AD_Reference_ID=541981
	 * Reference name: MobileUI_HUManager_LayoutSection LayoutSection
	 */
	public static final int LAYOUTSECTION_AD_Reference_ID=541981;
	/** DisplayName = DisplayName */
	public static final String LAYOUTSECTION_DisplayName = "DisplayName";
	/** QR Code = QRCode */
	public static final String LAYOUTSECTION_QRCode = "QRCode";
	/** EAN Code = EANCode */
	public static final String LAYOUTSECTION_EANCode = "EANCode";
	/** Locator = Locator */
	public static final String LAYOUTSECTION_Locator = "Locator";
	/** HUStatus = HUStatus */
	public static final String LAYOUTSECTION_HUStatus = "HUStatus";
	/** Product = Product */
	public static final String LAYOUTSECTION_Product = "Product";
	/** Qty = Qty */
	public static final String LAYOUTSECTION_Qty = "Qty";
	/** Attributes = Attributes */
	public static final String LAYOUTSECTION_Attributes = "Attributes";
	/** ClearanceStatus = ClearanceStatus */
	public static final String LAYOUTSECTION_ClearanceStatus = "ClearanceStatus";
	@Override
	public void setLayoutSection (final java.lang.String LayoutSection)
	{
		set_Value (COLUMNNAME_LayoutSection, LayoutSection);
	}

	@Override
	public java.lang.String getLayoutSection() 
	{
		return get_ValueAsString(COLUMNNAME_LayoutSection);
	}

	@Override
	public org.compiere.model.I_MobileUI_HUManager getMobileUI_HUManager()
	{
		return get_ValueAsPO(COLUMNNAME_MobileUI_HUManager_ID, org.compiere.model.I_MobileUI_HUManager.class);
	}

	@Override
	public void setMobileUI_HUManager(final org.compiere.model.I_MobileUI_HUManager MobileUI_HUManager)
	{
		set_ValueFromPO(COLUMNNAME_MobileUI_HUManager_ID, org.compiere.model.I_MobileUI_HUManager.class, MobileUI_HUManager);
	}

	@Override
	public void setMobileUI_HUManager_ID (final int MobileUI_HUManager_ID)
	{
		if (MobileUI_HUManager_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_HUManager_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_HUManager_ID, MobileUI_HUManager_ID);
	}

	@Override
	public int getMobileUI_HUManager_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_HUManager_ID);
	}

	@Override
	public void setMobileUI_HUManager_LayoutSection_ID (final int MobileUI_HUManager_LayoutSection_ID)
	{
		if (MobileUI_HUManager_LayoutSection_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_HUManager_LayoutSection_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_HUManager_LayoutSection_ID, MobileUI_HUManager_LayoutSection_ID);
	}

	@Override
	public int getMobileUI_HUManager_LayoutSection_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_HUManager_LayoutSection_ID);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}