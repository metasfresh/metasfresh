// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for A_Asset
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_A_Asset extends org.compiere.model.PO implements I_A_Asset, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1887586493L;

    /** Standard Constructor */
    public X_A_Asset (final Properties ctx, final int A_Asset_ID, @Nullable final String trxName)
    {
      super (ctx, A_Asset_ID, trxName);
    }

    /** Load Constructor */
    public X_A_Asset (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setA_Asset_CreateDate (final @Nullable java.sql.Timestamp A_Asset_CreateDate)
	{
		set_ValueNoCheck (COLUMNNAME_A_Asset_CreateDate, A_Asset_CreateDate);
	}

	@Override
	public java.sql.Timestamp getA_Asset_CreateDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_A_Asset_CreateDate);
	}

	@Override
	public org.compiere.model.I_A_Asset_Group getA_Asset_Group()
	{
		return get_ValueAsPO(COLUMNNAME_A_Asset_Group_ID, org.compiere.model.I_A_Asset_Group.class);
	}

	@Override
	public void setA_Asset_Group(final org.compiere.model.I_A_Asset_Group A_Asset_Group)
	{
		set_ValueFromPO(COLUMNNAME_A_Asset_Group_ID, org.compiere.model.I_A_Asset_Group.class, A_Asset_Group);
	}

	@Override
	public void setA_Asset_Group_ID (final int A_Asset_Group_ID)
	{
		if (A_Asset_Group_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_Group_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_Group_ID, A_Asset_Group_ID);
	}

	@Override
	public int getA_Asset_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_A_Asset_Group_ID);
	}

	@Override
	public void setA_Asset_ID (final int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, A_Asset_ID);
	}

	@Override
	public int getA_Asset_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_A_Asset_ID);
	}

	@Override
	public void setA_Asset_RevalDate (final @Nullable java.sql.Timestamp A_Asset_RevalDate)
	{
		set_Value (COLUMNNAME_A_Asset_RevalDate, A_Asset_RevalDate);
	}

	@Override
	public java.sql.Timestamp getA_Asset_RevalDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_A_Asset_RevalDate);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public org.compiere.model.I_A_Asset getA_Parent_Asset()
	{
		return get_ValueAsPO(COLUMNNAME_A_Parent_Asset_ID, org.compiere.model.I_A_Asset.class);
	}

	@Override
	public void setA_Parent_Asset(final org.compiere.model.I_A_Asset A_Parent_Asset)
	{
		set_ValueFromPO(COLUMNNAME_A_Parent_Asset_ID, org.compiere.model.I_A_Asset.class, A_Parent_Asset);
	}

	@Override
	public void setA_Parent_Asset_ID (final int A_Parent_Asset_ID)
	{
		if (A_Parent_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Parent_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Parent_Asset_ID, A_Parent_Asset_ID);
	}

	@Override
	public int getA_Parent_Asset_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_A_Parent_Asset_ID);
	}

	@Override
	public void setA_QTY_Current (final @Nullable BigDecimal A_QTY_Current)
	{
		set_Value (COLUMNNAME_A_QTY_Current, A_QTY_Current);
	}

	@Override
	public BigDecimal getA_QTY_Current() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_A_QTY_Current);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setA_QTY_Original (final @Nullable BigDecimal A_QTY_Original)
	{
		set_Value (COLUMNNAME_A_QTY_Original, A_QTY_Original);
	}

	@Override
	public BigDecimal getA_QTY_Original() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_A_QTY_Original);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAssetDepreciationDate (final @Nullable java.sql.Timestamp AssetDepreciationDate)
	{
		set_Value (COLUMNNAME_AssetDepreciationDate, AssetDepreciationDate);
	}

	@Override
	public java.sql.Timestamp getAssetDepreciationDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AssetDepreciationDate);
	}

	@Override
	public void setAssetDisposalDate (final @Nullable java.sql.Timestamp AssetDisposalDate)
	{
		set_Value (COLUMNNAME_AssetDisposalDate, AssetDisposalDate);
	}

	@Override
	public java.sql.Timestamp getAssetDisposalDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AssetDisposalDate);
	}

	@Override
	public void setAssetServiceDate (final @Nullable java.sql.Timestamp AssetServiceDate)
	{
		set_Value (COLUMNNAME_AssetServiceDate, AssetServiceDate);
	}

	@Override
	public java.sql.Timestamp getAssetServiceDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AssetServiceDate);
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
	public void setC_BPartnerSR_ID (final int C_BPartnerSR_ID)
	{
		if (C_BPartnerSR_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerSR_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerSR_ID, C_BPartnerSR_ID);
	}

	@Override
	public int getC_BPartnerSR_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartnerSR_ID);
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
	public void setHelp (final @Nullable java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setIsDepreciated (final boolean IsDepreciated)
	{
		set_Value (COLUMNNAME_IsDepreciated, IsDepreciated);
	}

	@Override
	public boolean isDepreciated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDepreciated);
	}

	@Override
	public void setIsDisposed (final boolean IsDisposed)
	{
		set_Value (COLUMNNAME_IsDisposed, IsDisposed);
	}

	@Override
	public boolean isDisposed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisposed);
	}

	@Override
	public void setIsFullyDepreciated (final boolean IsFullyDepreciated)
	{
		set_ValueNoCheck (COLUMNNAME_IsFullyDepreciated, IsFullyDepreciated);
	}

	@Override
	public boolean isFullyDepreciated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFullyDepreciated);
	}

	@Override
	public void setIsInPosession (final boolean IsInPosession)
	{
		set_Value (COLUMNNAME_IsInPosession, IsInPosession);
	}

	@Override
	public boolean isInPosession() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInPosession);
	}

	@Override
	public void setIsOwned (final boolean IsOwned)
	{
		set_Value (COLUMNNAME_IsOwned, IsOwned);
	}

	@Override
	public boolean isOwned() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOwned);
	}

	@Override
	public void setLastMaintenanceDate (final @Nullable java.sql.Timestamp LastMaintenanceDate)
	{
		set_Value (COLUMNNAME_LastMaintenanceDate, LastMaintenanceDate);
	}

	@Override
	public java.sql.Timestamp getLastMaintenanceDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_LastMaintenanceDate);
	}

	@Override
	public void setLastMaintenanceNote (final @Nullable java.lang.String LastMaintenanceNote)
	{
		set_Value (COLUMNNAME_LastMaintenanceNote, LastMaintenanceNote);
	}

	@Override
	public java.lang.String getLastMaintenanceNote() 
	{
		return get_ValueAsString(COLUMNNAME_LastMaintenanceNote);
	}

	@Override
	public void setLastMaintenanceUnit (final int LastMaintenanceUnit)
	{
		set_Value (COLUMNNAME_LastMaintenanceUnit, LastMaintenanceUnit);
	}

	@Override
	public int getLastMaintenanceUnit() 
	{
		return get_ValueAsInt(COLUMNNAME_LastMaintenanceUnit);
	}

	@Override
	public void setLease_BPartner_ID (final int Lease_BPartner_ID)
	{
		if (Lease_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Lease_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Lease_BPartner_ID, Lease_BPartner_ID);
	}

	@Override
	public int getLease_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Lease_BPartner_ID);
	}

	@Override
	public void setLeaseTerminationDate (final @Nullable java.sql.Timestamp LeaseTerminationDate)
	{
		set_Value (COLUMNNAME_LeaseTerminationDate, LeaseTerminationDate);
	}

	@Override
	public java.sql.Timestamp getLeaseTerminationDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_LeaseTerminationDate);
	}

	@Override
	public void setLifeUseUnits (final int LifeUseUnits)
	{
		set_Value (COLUMNNAME_LifeUseUnits, LifeUseUnits);
	}

	@Override
	public int getLifeUseUnits() 
	{
		return get_ValueAsInt(COLUMNNAME_LifeUseUnits);
	}

	@Override
	public void setLocationComment (final @Nullable java.lang.String LocationComment)
	{
		set_Value (COLUMNNAME_LocationComment, LocationComment);
	}

	@Override
	public java.lang.String getLocationComment() 
	{
		return get_ValueAsString(COLUMNNAME_LocationComment);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(final org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	@Override
	public void setM_InOutLine_ID (final int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, M_InOutLine_ID);
	}

	@Override
	public int getM_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
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
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
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
	public void setNextMaintenenceDate (final @Nullable java.sql.Timestamp NextMaintenenceDate)
	{
		set_Value (COLUMNNAME_NextMaintenenceDate, NextMaintenenceDate);
	}

	@Override
	public java.sql.Timestamp getNextMaintenenceDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_NextMaintenenceDate);
	}

	@Override
	public void setNextMaintenenceUnit (final int NextMaintenenceUnit)
	{
		set_Value (COLUMNNAME_NextMaintenenceUnit, NextMaintenenceUnit);
	}

	@Override
	public int getNextMaintenenceUnit() 
	{
		return get_ValueAsInt(COLUMNNAME_NextMaintenenceUnit);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUseLifeMonths (final int UseLifeMonths)
	{
		set_Value (COLUMNNAME_UseLifeMonths, UseLifeMonths);
	}

	@Override
	public int getUseLifeMonths() 
	{
		return get_ValueAsInt(COLUMNNAME_UseLifeMonths);
	}

	@Override
	public void setUseLifeYears (final int UseLifeYears)
	{
		set_Value (COLUMNNAME_UseLifeYears, UseLifeYears);
	}

	@Override
	public int getUseLifeYears() 
	{
		return get_ValueAsInt(COLUMNNAME_UseLifeYears);
	}

	@Override
	public void setUseUnits (final int UseUnits)
	{
		set_ValueNoCheck (COLUMNNAME_UseUnits, UseUnits);
	}

	@Override
	public int getUseUnits() 
	{
		return get_ValueAsInt(COLUMNNAME_UseUnits);
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

	@Override
	public void setVersionNo (final @Nullable java.lang.String VersionNo)
	{
		set_Value (COLUMNNAME_VersionNo, VersionNo);
	}

	@Override
	public java.lang.String getVersionNo() 
	{
		return get_ValueAsString(COLUMNNAME_VersionNo);
	}
}