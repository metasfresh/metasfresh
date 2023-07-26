// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_ProjectType
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_ProjectType extends org.compiere.model.PO implements I_C_ProjectType, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -596423106L;

    /** Standard Constructor */
    public X_C_ProjectType (final Properties ctx, final int C_ProjectType_ID, @Nullable final String trxName)
    {
      super (ctx, C_ProjectType_ID, trxName);
    }

    /** Load Constructor */
    public X_C_ProjectType (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Sequence getAD_Sequence_ProjectValue()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Sequence_ProjectValue_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setAD_Sequence_ProjectValue(final org.compiere.model.I_AD_Sequence AD_Sequence_ProjectValue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Sequence_ProjectValue_ID, org.compiere.model.I_AD_Sequence.class, AD_Sequence_ProjectValue);
	}

	@Override
	public void setAD_Sequence_ProjectValue_ID (final int AD_Sequence_ProjectValue_ID)
	{
		if (AD_Sequence_ProjectValue_ID < 1) 
			set_Value (COLUMNNAME_AD_Sequence_ProjectValue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Sequence_ProjectValue_ID, AD_Sequence_ProjectValue_ID);
	}

	@Override
	public int getAD_Sequence_ProjectValue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Sequence_ProjectValue_ID);
	}

	@Override
	public void setC_ProjectType_ID (final int C_ProjectType_ID)
	{
		if (C_ProjectType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectType_ID, C_ProjectType_ID);
	}

	@Override
	public int getC_ProjectType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectType_ID);
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
	 * ProjectCategory AD_Reference_ID=288
	 * Reference name: C_ProjectType Category
	 */
	public static final int PROJECTCATEGORY_AD_Reference_ID=288;
	/** General = N */
	public static final String PROJECTCATEGORY_General = "N";
	/** AssetProject = A */
	public static final String PROJECTCATEGORY_AssetProject = "A";
	/** WorkOrderJob = W */
	public static final String PROJECTCATEGORY_WorkOrderJob = "W";
	/** ServiceChargeProject = S */
	public static final String PROJECTCATEGORY_ServiceChargeProject = "S";
	/** ServiceOrRepair = R */
	public static final String PROJECTCATEGORY_ServiceOrRepair = "R";
	@Override
	public void setProjectCategory (final java.lang.String ProjectCategory)
	{
		set_ValueNoCheck (COLUMNNAME_ProjectCategory, ProjectCategory);
	}

	@Override
	public java.lang.String getProjectCategory() 
	{
		return get_ValueAsString(COLUMNNAME_ProjectCategory);
	}
}