// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU extends org.compiere.model.PO implements I_M_HU, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -975613490L;

    /** Standard Constructor */
    public X_M_HU (final Properties ctx, final int M_HU_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setClearanceDate (final @Nullable java.sql.Timestamp ClearanceDate)
	{
		set_Value (COLUMNNAME_ClearanceDate, ClearanceDate);
	}

	@Override
	public java.sql.Timestamp getClearanceDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ClearanceDate);
	}

	@Override
	public void setClearanceNote (final @Nullable java.lang.String ClearanceNote)
	{
		set_Value (COLUMNNAME_ClearanceNote, ClearanceNote);
	}

	@Override
	public java.lang.String getClearanceNote() 
	{
		return get_ValueAsString(COLUMNNAME_ClearanceNote);
	}

	/** 
	 * ClearanceStatus AD_Reference_ID=541540
	 * Reference name: Clearance
	 */
	public static final int CLEARANCESTATUS_AD_Reference_ID=541540;
	/** Cleared = C */
	public static final String CLEARANCESTATUS_Cleared = "C";
	/** Locked = L */
	public static final String CLEARANCESTATUS_Locked = "L";
	/** Quarantined = Q */
	public static final String CLEARANCESTATUS_Quarantined = "Q";
	/** Test Pending = P */
	public static final String CLEARANCESTATUS_TestPending = "P";
	@Override
	public void setClearanceStatus (final @Nullable java.lang.String ClearanceStatus)
	{
		set_Value (COLUMNNAME_ClearanceStatus, ClearanceStatus);
	}

	@Override
	public java.lang.String getClearanceStatus() 
	{
		return get_ValueAsString(COLUMNNAME_ClearanceStatus);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getClonedFrom_HU()
	{
		return get_ValueAsPO(COLUMNNAME_ClonedFrom_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setClonedFrom_HU(final de.metas.handlingunits.model.I_M_HU ClonedFrom_HU)
	{
		set_ValueFromPO(COLUMNNAME_ClonedFrom_HU_ID, de.metas.handlingunits.model.I_M_HU.class, ClonedFrom_HU);
	}

	@Override
	public void setClonedFrom_HU_ID (final int ClonedFrom_HU_ID)
	{
		if (ClonedFrom_HU_ID < 1) 
			set_Value (COLUMNNAME_ClonedFrom_HU_ID, null);
		else 
			set_Value (COLUMNNAME_ClonedFrom_HU_ID, ClonedFrom_HU_ID);
	}

	@Override
	public int getClonedFrom_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ClonedFrom_HU_ID);
	}

	@Override
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
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
	public void setIsChildHU (final boolean IsChildHU)
	{
		throw new IllegalArgumentException ("IsChildHU is virtual column");	}

	@Override
	public boolean isChildHU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsChildHU);
	}

	@Override
	public void setIsExternalProperty (final boolean IsExternalProperty)
	{
		set_Value (COLUMNNAME_IsExternalProperty, IsExternalProperty);
	}

	@Override
	public boolean isExternalProperty() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExternalProperty);
	}

	@Override
	public void setIsReserved (final boolean IsReserved)
	{
		set_Value (COLUMNNAME_IsReserved, IsReserved);
	}

	@Override
	public boolean isReserved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReserved);
	}

	@Override
	public void setLocked (final boolean Locked)
	{
		throw new IllegalArgumentException ("Locked is virtual column");	}

	@Override
	public boolean isLocked() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Locked);
	}

	@Override
	public void setLot (final @Nullable java.lang.String Lot)
	{
		throw new IllegalArgumentException ("Lot is virtual column");	}

	@Override
	public java.lang.String getLot() 
	{
		return get_ValueAsString(COLUMNNAME_Lot);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, M_HU_ID);
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
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		throw new IllegalArgumentException ("M_Product_Category_ID is virtual column");	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		throw new IllegalArgumentException ("M_Product_ID is virtual column");	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setSerialNo (final @Nullable java.lang.String SerialNo)
	{
		throw new IllegalArgumentException ("SerialNo is virtual column");	}

	@Override
	public java.lang.String getSerialNo() 
	{
		return get_ValueAsString(COLUMNNAME_SerialNo);
	}

	@Override
	public void setServiceContract (final @Nullable java.lang.String ServiceContract)
	{
		throw new IllegalArgumentException ("ServiceContract is virtual column");	}

	@Override
	public java.lang.String getServiceContract() 
	{
		return get_ValueAsString(COLUMNNAME_ServiceContract);
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