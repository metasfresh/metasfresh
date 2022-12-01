// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for CRM_Occupation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_CRM_Occupation extends org.compiere.model.PO implements I_CRM_Occupation, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -950592078L;

    /** Standard Constructor */
    public X_CRM_Occupation (final Properties ctx, final int CRM_Occupation_ID, @Nullable final String trxName)
    {
      super (ctx, CRM_Occupation_ID, trxName);
    }

    /** Load Constructor */
    public X_CRM_Occupation (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCRM_Occupation_ID (final int CRM_Occupation_ID)
	{
		if (CRM_Occupation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CRM_Occupation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CRM_Occupation_ID, CRM_Occupation_ID);
	}

	@Override
	public int getCRM_Occupation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CRM_Occupation_ID);
	}

	@Override
	public org.compiere.model.I_CRM_Occupation getCRM_Occupation_Parent()
	{
		return get_ValueAsPO(COLUMNNAME_CRM_Occupation_Parent_ID, org.compiere.model.I_CRM_Occupation.class);
	}

	@Override
	public void setCRM_Occupation_Parent(final org.compiere.model.I_CRM_Occupation CRM_Occupation_Parent)
	{
		set_ValueFromPO(COLUMNNAME_CRM_Occupation_Parent_ID, org.compiere.model.I_CRM_Occupation.class, CRM_Occupation_Parent);
	}

	@Override
	public void setCRM_Occupation_Parent_ID (final int CRM_Occupation_Parent_ID)
	{
		if (CRM_Occupation_Parent_ID < 1) 
			set_Value (COLUMNNAME_CRM_Occupation_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_CRM_Occupation_Parent_ID, CRM_Occupation_Parent_ID);
	}

	@Override
	public int getCRM_Occupation_Parent_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CRM_Occupation_Parent_ID);
	}

	/** 
	 * JobType AD_Reference_ID=541383
	 * Reference name: JobType
	 */
	public static final int JOBTYPE_AD_Reference_ID=541383;
	/** Job = B */
	public static final String JOBTYPE_Job = "B";
	/** Specialization = F */
	public static final String JOBTYPE_Specialization = "F";
	/** AdditionalSpecialization = Z */
	public static final String JOBTYPE_AdditionalSpecialization = "Z";
	@Override
	public void setJobType (final @Nullable java.lang.String JobType)
	{
		set_Value (COLUMNNAME_JobType, JobType);
	}

	@Override
	public java.lang.String getJobType() 
	{
		return get_ValueAsString(COLUMNNAME_JobType);
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