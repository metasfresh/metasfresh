// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_Snapshot
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Snapshot extends org.compiere.model.PO implements I_M_HU_Snapshot, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1155582820L;

    /** Standard Constructor */
    public X_M_HU_Snapshot (final Properties ctx, final int M_HU_Snapshot_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Snapshot_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Snapshot (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setHUPlanningReceiptOwnerPM (final boolean HUPlanningReceiptOwnerPM)
	{
		set_Value (COLUMNNAME_HUPlanningReceiptOwnerPM, HUPlanningReceiptOwnerPM);
	}

	@Override
	public boolean isHUPlanningReceiptOwnerPM() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HUPlanningReceiptOwnerPM);
	}

	/** 
	 * HUStatus AD_Reference_ID=540478
	 * Reference name: HUStatus
	 */
	public static final int HUSTATUS_AD_Reference_ID=540478;
	/** Planning = P */
	public static final String HUSTATUS_Planning = "P";
	/** Active = A */
	public static final String HUSTATUS_Active = "A";
	/** Destroyed = D */
	public static final String HUSTATUS_Destroyed = "D";
	/** Picked = S */
	public static final String HUSTATUS_Picked = "S";
	/** Shipped = E */
	public static final String HUSTATUS_Shipped = "E";
	/** Issued = I */
	public static final String HUSTATUS_Issued = "I";
	@Override
	public void setHUStatus (final java.lang.String HUStatus)
	{
		set_Value (COLUMNNAME_HUStatus, HUStatus);
	}

	@Override
	public java.lang.String getHUStatus() 
	{
		return get_ValueAsString(COLUMNNAME_HUStatus);
	}

	@Override
	public void setLocked (final boolean Locked)
	{
		set_Value (COLUMNNAME_Locked, Locked);
	}

	@Override
	public boolean isLocked() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Locked);
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
	public de.metas.handlingunits.model.I_M_HU_Item getM_HU_Item_Parent()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Item_Parent_ID, de.metas.handlingunits.model.I_M_HU_Item.class);
	}

	@Override
	public void setM_HU_Item_Parent(final de.metas.handlingunits.model.I_M_HU_Item M_HU_Item_Parent)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Item_Parent_ID, de.metas.handlingunits.model.I_M_HU_Item.class, M_HU_Item_Parent);
	}

	@Override
	public void setM_HU_Item_Parent_ID (final int M_HU_Item_Parent_ID)
	{
		if (M_HU_Item_Parent_ID < 1) 
			set_Value (COLUMNNAME_M_HU_Item_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_Item_Parent_ID, M_HU_Item_Parent_ID);
	}

	@Override
	public int getM_HU_Item_Parent_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Item_Parent_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_LUTU_Configuration_ID, de.metas.handlingunits.model.I_M_HU_LUTU_Configuration.class);
	}

	@Override
	public void setM_HU_LUTU_Configuration(final de.metas.handlingunits.model.I_M_HU_LUTU_Configuration M_HU_LUTU_Configuration)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_LUTU_Configuration_ID, de.metas.handlingunits.model.I_M_HU_LUTU_Configuration.class, M_HU_LUTU_Configuration);
	}

	@Override
	public void setM_HU_LUTU_Configuration_ID (final int M_HU_LUTU_Configuration_ID)
	{
		if (M_HU_LUTU_Configuration_ID < 1) 
			set_Value (COLUMNNAME_M_HU_LUTU_Configuration_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_LUTU_Configuration_ID, M_HU_LUTU_Configuration_ID);
	}

	@Override
	public int getM_HU_LUTU_Configuration_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_LUTU_Configuration_ID);
	}

	@Override
	public void setM_HU_PI_Item_Product_ID (final int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, M_HU_PI_Item_Product_ID);
	}

	@Override
	public int getM_HU_PI_Item_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_Product_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI_Version getM_HU_PI_Version()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PI_Version_ID, de.metas.handlingunits.model.I_M_HU_PI_Version.class);
	}

	@Override
	public void setM_HU_PI_Version(final de.metas.handlingunits.model.I_M_HU_PI_Version M_HU_PI_Version)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PI_Version_ID, de.metas.handlingunits.model.I_M_HU_PI_Version.class, M_HU_PI_Version);
	}

	@Override
	public void setM_HU_PI_Version_ID (final int M_HU_PI_Version_ID)
	{
		if (M_HU_PI_Version_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Version_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Version_ID, M_HU_PI_Version_ID);
	}

	@Override
	public int getM_HU_PI_Version_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Version_ID);
	}

	@Override
	public void setM_HU_Snapshot_ID (final int M_HU_Snapshot_ID)
	{
		if (M_HU_Snapshot_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Snapshot_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Snapshot_ID, M_HU_Snapshot_ID);
	}

	@Override
	public int getM_HU_Snapshot_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Snapshot_ID);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
	}

	@Override
	public void setSnapshot_UUID (final java.lang.String Snapshot_UUID)
	{
		set_Value (COLUMNNAME_Snapshot_UUID, Snapshot_UUID);
	}

	@Override
	public java.lang.String getSnapshot_UUID() 
	{
		return get_ValueAsString(COLUMNNAME_Snapshot_UUID);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}