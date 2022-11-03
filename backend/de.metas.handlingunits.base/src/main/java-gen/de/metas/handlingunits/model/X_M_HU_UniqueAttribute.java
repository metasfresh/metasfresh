// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_UniqueAttribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_UniqueAttribute extends org.compiere.model.PO implements I_M_HU_UniqueAttribute, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1827566429L;

    /** Standard Constructor */
    public X_M_HU_UniqueAttribute (final Properties ctx, final int M_HU_UniqueAttribute_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_UniqueAttribute_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_UniqueAttribute (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Attribute_ID (final int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, M_Attribute_ID);
	}

	@Override
	public int getM_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Attribute_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Attribute getM_HU_Attribute()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Attribute_ID, de.metas.handlingunits.model.I_M_HU_Attribute.class);
	}

	@Override
	public void setM_HU_Attribute(final de.metas.handlingunits.model.I_M_HU_Attribute M_HU_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Attribute_ID, de.metas.handlingunits.model.I_M_HU_Attribute.class, M_HU_Attribute);
	}

	@Override
	public void setM_HU_Attribute_ID (final int M_HU_Attribute_ID)
	{
		if (M_HU_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_HU_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_Attribute_ID, M_HU_Attribute_ID);
	}

	@Override
	public int getM_HU_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Attribute_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(final de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public void setM_HU_PI_Attribute_ID (final int M_HU_PI_Attribute_ID)
	{
		if (M_HU_PI_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Attribute_ID, M_HU_PI_Attribute_ID);
	}

	@Override
	public int getM_HU_PI_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Attribute_ID);
	}

	@Override
	public void setM_HU_UniqueAttribute_ID (final int M_HU_UniqueAttribute_ID)
	{
		if (M_HU_UniqueAttribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_UniqueAttribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_UniqueAttribute_ID, M_HU_UniqueAttribute_ID);
	}

	@Override
	public int getM_HU_UniqueAttribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_UniqueAttribute_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setValue (final @Nullable java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}