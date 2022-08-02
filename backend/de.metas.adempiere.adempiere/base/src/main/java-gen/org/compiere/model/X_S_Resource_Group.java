// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_Resource_Group
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_Resource_Group extends org.compiere.model.PO implements I_S_Resource_Group, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1553321030L;

    /** Standard Constructor */
    public X_S_Resource_Group (final Properties ctx, final int S_Resource_Group_ID, @Nullable final String trxName)
    {
      super (ctx, S_Resource_Group_ID, trxName);
    }

    /** Load Constructor */
    public X_S_Resource_Group (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * DurationUnit AD_Reference_ID=299
	 * Reference name: WF_DurationUnit
	 */
	public static final int DURATIONUNIT_AD_Reference_ID=299;
	/** Year = Y */
	public static final String DURATIONUNIT_Year = "Y";
	/** Month = M */
	public static final String DURATIONUNIT_Month = "M";
	/** Day = D */
	public static final String DURATIONUNIT_Day = "D";
	/** Hour = h */
	public static final String DURATIONUNIT_Hour = "h";
	/** Minute = m */
	public static final String DURATIONUNIT_Minute = "m";
	/** Second = s */
	public static final String DURATIONUNIT_Second = "s";
	@Override
	public void setDurationUnit (final java.lang.String DurationUnit)
	{
		set_Value (COLUMNNAME_DurationUnit, DurationUnit);
	}

	@Override
	public java.lang.String getDurationUnit() 
	{
		return get_ValueAsString(COLUMNNAME_DurationUnit);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
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
	public void setS_Resource_Group_ID (final int S_Resource_Group_ID)
	{
		if (S_Resource_Group_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Resource_Group_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Resource_Group_ID, S_Resource_Group_ID);
	}

	@Override
	public int getS_Resource_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_Group_ID);
	}
}