// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Element
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Element extends org.compiere.model.PO implements I_C_Element, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2095520697L;

    /** Standard Constructor */
    public X_C_Element (final Properties ctx, final int C_Element_ID, @Nullable final String trxName)
    {
      super (ctx, C_Element_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Element (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Tree getAD_Tree()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree(final org.compiere.model.I_AD_Tree AD_Tree)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_ID, org.compiere.model.I_AD_Tree.class, AD_Tree);
	}

	@Override
	public void setAD_Tree_ID (final int AD_Tree_ID)
	{
		if (AD_Tree_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_ID, AD_Tree_ID);
	}

	@Override
	public int getAD_Tree_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Tree_ID);
	}

	@Override
	public void setC_Element_ID (final int C_Element_ID)
	{
		if (C_Element_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Element_ID, C_Element_ID);
	}

	@Override
	public int getC_Element_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Element_ID);
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
	 * ElementType AD_Reference_ID=116
	 * Reference name: C_Element Type
	 */
	public static final int ELEMENTTYPE_AD_Reference_ID=116;
	/** Account = A */
	public static final String ELEMENTTYPE_Account = "A";
	/** UserDefined = U */
	public static final String ELEMENTTYPE_UserDefined = "U";
	@Override
	public void setElementType (final java.lang.String ElementType)
	{
		set_ValueNoCheck (COLUMNNAME_ElementType, ElementType);
	}

	@Override
	public java.lang.String getElementType() 
	{
		return get_ValueAsString(COLUMNNAME_ElementType);
	}

	@Override
	public void setIsBalancing (final boolean IsBalancing)
	{
		set_Value (COLUMNNAME_IsBalancing, IsBalancing);
	}

	@Override
	public boolean isBalancing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBalancing);
	}

	@Override
	public void setIsNaturalAccount (final boolean IsNaturalAccount)
	{
		set_Value (COLUMNNAME_IsNaturalAccount, IsNaturalAccount);
	}

	@Override
	public boolean isNaturalAccount() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNaturalAccount);
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
	public void setVFormat (final @Nullable java.lang.String VFormat)
	{
		set_Value (COLUMNNAME_VFormat, VFormat);
	}

	@Override
	public java.lang.String getVFormat() 
	{
		return get_ValueAsString(COLUMNNAME_VFormat);
	}
}