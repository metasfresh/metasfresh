// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Project_WO_ObjectUnderTest
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_WO_ObjectUnderTest extends org.compiere.model.PO implements I_C_Project_WO_ObjectUnderTest, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1675901874L;

    /** Standard Constructor */
    public X_C_Project_WO_ObjectUnderTest (final Properties ctx, final int C_Project_WO_ObjectUnderTest_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_WO_ObjectUnderTest_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_WO_ObjectUnderTest (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public void setC_Project_WO_ObjectUnderTest_ID (final int C_Project_WO_ObjectUnderTest_ID)
	{
		if (C_Project_WO_ObjectUnderTest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_ObjectUnderTest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_ObjectUnderTest_ID, C_Project_WO_ObjectUnderTest_ID);
	}

	@Override
	public int getC_Project_WO_ObjectUnderTest_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_WO_ObjectUnderTest_ID);
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
	public void setNumberOfObjectsUnderTest (final int NumberOfObjectsUnderTest)
	{
		set_Value (COLUMNNAME_NumberOfObjectsUnderTest, NumberOfObjectsUnderTest);
	}

	@Override
	public int getNumberOfObjectsUnderTest() 
	{
		return get_ValueAsInt(COLUMNNAME_NumberOfObjectsUnderTest);
	}

	@Override
	public void setWODeliveryNote (final @Nullable java.lang.String WODeliveryNote)
	{
		set_Value (COLUMNNAME_WODeliveryNote, WODeliveryNote);
	}

	@Override
	public java.lang.String getWODeliveryNote() 
	{
		return get_ValueAsString(COLUMNNAME_WODeliveryNote);
	}

	@Override
	public void setWOManufacturer (final @Nullable java.lang.String WOManufacturer)
	{
		set_Value (COLUMNNAME_WOManufacturer, WOManufacturer);
	}

	@Override
	public java.lang.String getWOManufacturer() 
	{
		return get_ValueAsString(COLUMNNAME_WOManufacturer);
	}

	@Override
	public void setWOObjectName (final @Nullable java.lang.String WOObjectName)
	{
		set_Value (COLUMNNAME_WOObjectName, WOObjectName);
	}

	@Override
	public java.lang.String getWOObjectName() 
	{
		return get_ValueAsString(COLUMNNAME_WOObjectName);
	}

	@Override
	public void setWOObjectType (final @Nullable java.lang.String WOObjectType)
	{
		set_Value (COLUMNNAME_WOObjectType, WOObjectType);
	}

	@Override
	public java.lang.String getWOObjectType() 
	{
		return get_ValueAsString(COLUMNNAME_WOObjectType);
	}

	@Override
	public void setWOObjectWhereabouts (final @Nullable java.lang.String WOObjectWhereabouts)
	{
		set_Value (COLUMNNAME_WOObjectWhereabouts, WOObjectWhereabouts);
	}

	@Override
	public java.lang.String getWOObjectWhereabouts() 
	{
		return get_ValueAsString(COLUMNNAME_WOObjectWhereabouts);
	}
}