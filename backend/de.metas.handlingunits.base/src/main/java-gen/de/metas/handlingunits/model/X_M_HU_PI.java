// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_PI
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_PI extends org.compiere.model.PO implements I_M_HU_PI, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 979131992L;

    /** Standard Constructor */
    public X_M_HU_PI (final Properties ctx, final int M_HU_PI_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_PI_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_PI (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	@Override
	public void setIsDefaultForPicking (final boolean IsDefaultForPicking)
	{
		set_Value (COLUMNNAME_IsDefaultForPicking, IsDefaultForPicking);
	}

	@Override
	public boolean isDefaultForPicking() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefaultForPicking);
	}

	@Override
	public void setIsDefaultLU (final boolean IsDefaultLU)
	{
		set_Value (COLUMNNAME_IsDefaultLU, IsDefaultLU);
	}

	@Override
	public boolean isDefaultLU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefaultLU);
	}

	@Override
	public void setM_HU_PI_ID (final int M_HU_PI_ID)
	{
		if (M_HU_PI_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_ID, M_HU_PI_ID);
	}

	@Override
	public int getM_HU_PI_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_ID);
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
}