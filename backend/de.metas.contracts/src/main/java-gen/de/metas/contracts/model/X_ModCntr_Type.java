// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ModCntr_Type
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ModCntr_Type extends org.compiere.model.PO implements I_ModCntr_Type, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -1913315153L;

    /** Standard Constructor */
    public X_ModCntr_Type (final Properties ctx, final int ModCntr_Type_ID, @Nullable final String trxName)
    {
      super (ctx, ModCntr_Type_ID, trxName);
    }

    /** Load Constructor */
    public X_ModCntr_Type (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setClassname (final @Nullable String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	@Override
	public String getClassname()
	{
		return get_ValueAsString(COLUMNNAME_Classname);
	}

	@Override
	public void setDescription (final @Nullable String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public String getDescription()
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setModCntr_Type_ID (final int ModCntr_Type_ID)
	{
		if (ModCntr_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Type_ID, ModCntr_Type_ID);
	}

	@Override
	public int getModCntr_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Type_ID);
	}

	@Override
	public void setName (final String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public String getName()
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setValue (final String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public String getValue()
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}