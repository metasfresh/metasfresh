// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BP_Relation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BP_Relation extends org.compiere.model.PO implements I_C_BP_Relation, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -634134424L;

    /** Standard Constructor */
    public X_C_BP_Relation (final Properties ctx, final int C_BP_Relation_ID, @Nullable final String trxName)
    {
      super (ctx, C_BP_Relation_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BP_Relation (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartnerRelation_ID (final int C_BPartnerRelation_ID)
	{
		if (C_BPartnerRelation_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerRelation_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerRelation_ID, C_BPartnerRelation_ID);
	}

	@Override
	public int getC_BPartnerRelation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartnerRelation_ID);
	}

	@Override
	public void setC_BPartnerRelation_Location_ID (final int C_BPartnerRelation_Location_ID)
	{
		if (C_BPartnerRelation_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerRelation_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerRelation_Location_ID, C_BPartnerRelation_Location_ID);
	}

	@Override
	public int getC_BPartnerRelation_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartnerRelation_Location_ID);
	}

	@Override
	public void setC_BP_Relation_ID (final int C_BP_Relation_ID)
	{
		if (C_BP_Relation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_Relation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_Relation_ID, C_BP_Relation_ID);
	}

	@Override
	public int getC_BP_Relation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Relation_ID);
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
	public void setIsBillTo (final boolean IsBillTo)
	{
		set_Value (COLUMNNAME_IsBillTo, IsBillTo);
	}

	@Override
	public boolean isBillTo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBillTo);
	}

	@Override
	public void setIsFetchedFrom (final boolean IsFetchedFrom)
	{
		set_Value (COLUMNNAME_IsFetchedFrom, IsFetchedFrom);
	}

	@Override
	public boolean isFetchedFrom() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFetchedFrom);
	}

	@Override
	public void setIsPayFrom (final boolean IsPayFrom)
	{
		set_Value (COLUMNNAME_IsPayFrom, IsPayFrom);
	}

	@Override
	public boolean isPayFrom() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPayFrom);
	}

	@Override
	public void setIsRemitTo (final boolean IsRemitTo)
	{
		set_Value (COLUMNNAME_IsRemitTo, IsRemitTo);
	}

	@Override
	public boolean isRemitTo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRemitTo);
	}

	@Override
	public void setIsShipTo (final boolean IsShipTo)
	{
		set_Value (COLUMNNAME_IsShipTo, IsShipTo);
	}

	@Override
	public boolean isShipTo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShipTo);
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

	/** 
	 * Role AD_Reference_ID=541254
	 * Reference name: Role
	 */
	public static final int ROLE_AD_Reference_ID=541254;
	/** Main Producer = MP */
	public static final String ROLE_MainProducer = "MP";
	/** Hostpital  = HO */
	public static final String ROLE_Hostpital = "HO";
	/** Physician Doctor = PD */
	public static final String ROLE_PhysicianDoctor = "PD";
	/** General Practitioner = GP */
	public static final String ROLE_GeneralPractitioner = "GP";
	/** Health Insurance = HI */
	public static final String ROLE_HealthInsurance = "HI";
	/** Nursing Home = NH */
	public static final String ROLE_NursingHome = "NH";
	/** Caregiver = CG */
	public static final String ROLE_Caregiver = "CG";
	/** Preferred Pharmacy = PP */
	public static final String ROLE_PreferredPharmacy = "PP";
	/** Nursing Service = NS */
	public static final String ROLE_NursingService = "NS";
	@Override
	public void setRole (final @Nullable java.lang.String Role)
	{
		set_Value (COLUMNNAME_Role, Role);
	}

	@Override
	public java.lang.String getRole() 
	{
		return get_ValueAsString(COLUMNNAME_Role);
	}
}