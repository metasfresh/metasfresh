// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Allergen
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Allergen extends org.compiere.model.PO implements I_M_Allergen, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1730419304L;

    /** Standard Constructor */
    public X_M_Allergen (final Properties ctx, final int M_Allergen_ID, @Nullable final String trxName)
    {
      super (ctx, M_Allergen_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Allergen (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setColor (final @Nullable java.lang.String Color)
	{
		set_Value (COLUMNNAME_Color, Color);
	}

	@Override
	public java.lang.String getColor() 
	{
		return get_ValueAsString(COLUMNNAME_Color);
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
	public void setM_Allergen_ID (final int M_Allergen_ID)
	{
		if (M_Allergen_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Allergen_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Allergen_ID, M_Allergen_ID);
	}

	@Override
	public int getM_Allergen_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Allergen_ID);
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