// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Locator
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Locator extends org.compiere.model.PO implements I_M_Locator, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 223491434L;

    /** Standard Constructor */
    public X_M_Locator (final Properties ctx, final int M_Locator_ID, @Nullable final String trxName)
    {
      super (ctx, M_Locator_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Locator (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDateLastInventory (final @Nullable java.sql.Timestamp DateLastInventory)
	{
		set_Value (COLUMNNAME_DateLastInventory, DateLastInventory);
	}

	@Override
	public java.sql.Timestamp getDateLastInventory() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateLastInventory);
	}

	@Override
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setIsGroundLocator (final boolean IsGroundLocator)
	{
		set_Value (COLUMNNAME_IsGroundLocator, IsGroundLocator);
	}

	@Override
	public boolean isGroundLocator() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsGroundLocator);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setPriorityNo (final int PriorityNo)
	{
		set_Value (COLUMNNAME_PriorityNo, PriorityNo);
	}

	@Override
	public int getPriorityNo() 
	{
		return get_ValueAsInt(COLUMNNAME_PriorityNo);
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
	public void setX (final java.lang.String X)
	{
		set_Value (COLUMNNAME_X, X);
	}

	@Override
	public java.lang.String getX() 
	{
		return get_ValueAsString(COLUMNNAME_X);
	}

	@Override
	public void setX1 (final java.lang.String X1)
	{
		set_Value (COLUMNNAME_X1, X1);
	}

	@Override
	public java.lang.String getX1() 
	{
		return get_ValueAsString(COLUMNNAME_X1);
	}

	@Override
	public void setY (final java.lang.String Y)
	{
		set_Value (COLUMNNAME_Y, Y);
	}

	@Override
	public java.lang.String getY() 
	{
		return get_ValueAsString(COLUMNNAME_Y);
	}

	@Override
	public void setZ (final java.lang.String Z)
	{
		set_Value (COLUMNNAME_Z, Z);
	}

	@Override
	public java.lang.String getZ() 
	{
		return get_ValueAsString(COLUMNNAME_Z);
	}
}