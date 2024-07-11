// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Warehouse
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Warehouse extends org.compiere.model.PO implements I_M_Warehouse, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 895356713L;

    /** Standard Constructor */
    public X_M_Warehouse (final Properties ctx, final int M_Warehouse_ID, @Nullable final String trxName)
    {
      super (ctx, M_Warehouse_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Warehouse (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Activity_ID (final int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, C_Activity_ID);
	}

	@Override
	public int getC_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Activity_ID);
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
	public org.compiere.model.I_C_Location getC_Location()
	{
		return get_ValueAsPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_Location(final org.compiere.model.I_C_Location C_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class, C_Location);
	}

	@Override
	public void setC_Location_ID (final int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, C_Location_ID);
	}

	@Override
	public int getC_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Location_ID);
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
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setIsInTransit (final boolean IsInTransit)
	{
		set_Value (COLUMNNAME_IsInTransit, IsInTransit);
	}

	@Override
	public boolean isInTransit() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInTransit);
	}

	@Override
	public void setIsIssueWarehouse (final boolean IsIssueWarehouse)
	{
		set_Value (COLUMNNAME_IsIssueWarehouse, IsIssueWarehouse);
	}

	@Override
	public boolean isIssueWarehouse() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsIssueWarehouse);
	}

	@Override
	public void setIsPickingWarehouse (final boolean IsPickingWarehouse)
	{
		set_Value (COLUMNNAME_IsPickingWarehouse, IsPickingWarehouse);
	}

	@Override
	public boolean isPickingWarehouse() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickingWarehouse);
	}

	@Override
	public void setIsQualityReturnWarehouse (final boolean IsQualityReturnWarehouse)
	{
		set_Value (COLUMNNAME_IsQualityReturnWarehouse, IsQualityReturnWarehouse);
	}

	@Override
	public boolean isQualityReturnWarehouse() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsQualityReturnWarehouse);
	}

	@Override
	public void setIsQuarantineWarehouse (final boolean IsQuarantineWarehouse)
	{
		set_Value (COLUMNNAME_IsQuarantineWarehouse, IsQuarantineWarehouse);
	}

	@Override
	public boolean isQuarantineWarehouse() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsQuarantineWarehouse);
	}

	@Override
	public void setIsReceiveAsSourceHU (final boolean IsReceiveAsSourceHU)
	{
		set_Value (COLUMNNAME_IsReceiveAsSourceHU, IsReceiveAsSourceHU);
	}

	@Override
	public boolean isReceiveAsSourceHU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReceiveAsSourceHU);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public org.compiere.model.I_M_Warehouse_PickingGroup getM_Warehouse_PickingGroup()
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_PickingGroup_ID, org.compiere.model.I_M_Warehouse_PickingGroup.class);
	}

	@Override
	public void setM_Warehouse_PickingGroup(final org.compiere.model.I_M_Warehouse_PickingGroup M_Warehouse_PickingGroup)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_PickingGroup_ID, org.compiere.model.I_M_Warehouse_PickingGroup.class, M_Warehouse_PickingGroup);
	}

	@Override
	public void setM_Warehouse_PickingGroup_ID (final int M_Warehouse_PickingGroup_ID)
	{
		if (M_Warehouse_PickingGroup_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_PickingGroup_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_PickingGroup_ID, M_Warehouse_PickingGroup_ID);
	}

	@Override
	public int getM_Warehouse_PickingGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_PickingGroup_ID);
	}

	@Override
	public org.compiere.model.I_M_Warehouse_Type getM_Warehouse_Type()
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_Type_ID, org.compiere.model.I_M_Warehouse_Type.class);
	}

	@Override
	public void setM_Warehouse_Type(final org.compiere.model.I_M_Warehouse_Type M_Warehouse_Type)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_Type_ID, org.compiere.model.I_M_Warehouse_Type.class, M_Warehouse_Type);
	}

	@Override
	public void setM_Warehouse_Type_ID (final int M_Warehouse_Type_ID)
	{
		if (M_Warehouse_Type_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_Type_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_Type_ID, M_Warehouse_Type_ID);
	}

	@Override
	public int getM_Warehouse_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Type_ID);
	}

	@Override
	public void setM_WarehouseSource_ID (final int M_WarehouseSource_ID)
	{
		if (M_WarehouseSource_ID < 1) 
			set_Value (COLUMNNAME_M_WarehouseSource_ID, null);
		else 
			set_Value (COLUMNNAME_M_WarehouseSource_ID, M_WarehouseSource_ID);
	}

	@Override
	public int getM_WarehouseSource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_WarehouseSource_ID);
	}

	@Override
	public org.compiere.model.I_M_Warehouse_Group getManufacturing_Warehouse_Group()
	{
		return get_ValueAsPO(COLUMNNAME_Manufacturing_Warehouse_Group_ID, org.compiere.model.I_M_Warehouse_Group.class);
	}

	@Override
	public void setManufacturing_Warehouse_Group(final org.compiere.model.I_M_Warehouse_Group Manufacturing_Warehouse_Group)
	{
		set_ValueFromPO(COLUMNNAME_Manufacturing_Warehouse_Group_ID, org.compiere.model.I_M_Warehouse_Group.class, Manufacturing_Warehouse_Group);
	}

	@Override
	public void setManufacturing_Warehouse_Group_ID (final int Manufacturing_Warehouse_Group_ID)
	{
		if (Manufacturing_Warehouse_Group_ID < 1) 
			set_Value (COLUMNNAME_Manufacturing_Warehouse_Group_ID, null);
		else 
			set_Value (COLUMNNAME_Manufacturing_Warehouse_Group_ID, Manufacturing_Warehouse_Group_ID);
	}

	@Override
	public int getManufacturing_Warehouse_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Manufacturing_Warehouse_Group_ID);
	}

	/** 
	 * MRP_Exclude AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int MRP_EXCLUDE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String MRP_EXCLUDE_Yes = "Y";
	/** No = N */
	public static final String MRP_EXCLUDE_No = "N";
	@Override
	public void setMRP_Exclude (final @Nullable java.lang.String MRP_Exclude)
	{
		set_Value (COLUMNNAME_MRP_Exclude, MRP_Exclude);
	}

	@Override
	public java.lang.String getMRP_Exclude() 
	{
		return get_ValueAsString(COLUMNNAME_MRP_Exclude);
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

	@Override
	public org.compiere.model.I_S_Resource getPP_Plant()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setPP_Plant(final org.compiere.model.I_S_Resource PP_Plant)
	{
		set_ValueFromPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class, PP_Plant);
	}

	@Override
	public void setPP_Plant_ID (final int PP_Plant_ID)
	{
		if (PP_Plant_ID < 1) 
			set_Value (COLUMNNAME_PP_Plant_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Plant_ID, PP_Plant_ID);
	}

	@Override
	public int getPP_Plant_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Plant_ID);
	}

	@Override
	public void setReplenishmentClass (final @Nullable java.lang.String ReplenishmentClass)
	{
		set_Value (COLUMNNAME_ReplenishmentClass, ReplenishmentClass);
	}

	@Override
	public java.lang.String getReplenishmentClass() 
	{
		return get_ValueAsString(COLUMNNAME_ReplenishmentClass);
	}

	@Override
	public void setSeparator (final java.lang.String Separator)
	{
		set_Value (COLUMNNAME_Separator, Separator);
	}

	@Override
	public java.lang.String getSeparator() 
	{
		return get_ValueAsString(COLUMNNAME_Separator);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}