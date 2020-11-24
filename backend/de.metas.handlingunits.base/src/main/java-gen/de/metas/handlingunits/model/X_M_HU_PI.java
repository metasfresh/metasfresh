/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_PI
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_PI extends org.compiere.model.PO implements I_M_HU_PI, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -601360170L;

    /** Standard Constructor */
    public X_M_HU_PI (Properties ctx, int M_HU_PI_ID, String trxName)
    {
      super (ctx, M_HU_PI_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_PI (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setIsDefaultForPicking (boolean IsDefaultForPicking)
	{
		set_Value (COLUMNNAME_IsDefaultForPicking, Boolean.valueOf(IsDefaultForPicking));
	}

	@Override
	public boolean isDefaultForPicking() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefaultForPicking);
	}

	@Override
	public void setIsDefaultLU (boolean IsDefaultLU)
	{
		set_Value (COLUMNNAME_IsDefaultLU, Boolean.valueOf(IsDefaultLU));
	}

	@Override
	public boolean isDefaultLU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefaultLU);
	}

	@Override
	public void setM_HU_PI_ID (int M_HU_PI_ID)
	{
		if (M_HU_PI_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_ID, Integer.valueOf(M_HU_PI_ID));
	}

	@Override
	public int getM_HU_PI_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_ID);
	}

	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}