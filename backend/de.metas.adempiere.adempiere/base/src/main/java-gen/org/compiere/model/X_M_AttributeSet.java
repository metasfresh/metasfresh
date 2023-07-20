// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_AttributeSet
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_AttributeSet extends org.compiere.model.PO implements I_M_AttributeSet, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1636107389L;

    /** Standard Constructor */
    public X_M_AttributeSet (final Properties ctx, final int M_AttributeSet_ID, @Nullable final String trxName)
    {
      super (ctx, M_AttributeSet_ID, trxName);
    }

    /** Load Constructor */
    public X_M_AttributeSet (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsInstanceAttribute (final boolean IsInstanceAttribute)
	{
		set_Value (COLUMNNAME_IsInstanceAttribute, IsInstanceAttribute);
	}

	@Override
	public boolean isInstanceAttribute() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInstanceAttribute);
	}

	/** 
	 * MandatoryType AD_Reference_ID=324
	 * Reference name: M_AttributeSet MandatoryType
	 */
	public static final int MANDATORYTYPE_AD_Reference_ID=324;
	/** Not Mandatary = N */
	public static final String MANDATORYTYPE_NotMandatary = "N";
	/** Always Mandatory = Y */
	public static final String MANDATORYTYPE_AlwaysMandatory = "Y";
	/** WhenShipping = S */
	public static final String MANDATORYTYPE_WhenShipping = "S";
	@Override
	public void setMandatoryType (final java.lang.String MandatoryType)
	{
		set_Value (COLUMNNAME_MandatoryType, MandatoryType);
	}

	@Override
	public java.lang.String getMandatoryType() 
	{
		return get_ValueAsString(COLUMNNAME_MandatoryType);
	}

	@Override
	public void setM_AttributeSet_ID (final int M_AttributeSet_ID)
	{
		if (M_AttributeSet_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSet_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSet_ID, M_AttributeSet_ID);
	}

	@Override
	public int getM_AttributeSet_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSet_ID);
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