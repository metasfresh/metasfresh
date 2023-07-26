/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Element
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Element extends org.compiere.model.PO implements I_C_Element, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 242103581L;

    /** Standard Constructor */
    public X_C_Element (Properties ctx, int C_Element_ID, String trxName)
    {
      super (ctx, C_Element_ID, trxName);
      /** if (C_Element_ID == 0)
        {
			setAD_Tree_ID (0);
			setC_Element_ID (0);
			setElementType (null); // A
			setIsBalancing (false);
			setIsNaturalAccount (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_Element (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_AD_Tree getAD_Tree() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree(org.compiere.model.I_AD_Tree AD_Tree)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_ID, org.compiere.model.I_AD_Tree.class, AD_Tree);
	}

	/** Set Baum.
		@param AD_Tree_ID 
		Identifies a Tree
	  */
	@Override
	public void setAD_Tree_ID (int AD_Tree_ID)
	{
		if (AD_Tree_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_ID, Integer.valueOf(AD_Tree_ID));
	}

	/** Get Baum.
		@return Identifies a Tree
	  */
	@Override
	public int getAD_Tree_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Element.
		@param C_Element_ID 
		Accounting Element
	  */
	@Override
	public void setC_Element_ID (int C_Element_ID)
	{
		if (C_Element_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Element_ID, Integer.valueOf(C_Element_ID));
	}

	/** Get Element.
		@return Accounting Element
	  */
	@Override
	public int getC_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
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
	/** Set Art.
		@param ElementType 
		Element Type (account or user defined)
	  */
	@Override
	public void setElementType (java.lang.String ElementType)
	{

		set_ValueNoCheck (COLUMNNAME_ElementType, ElementType);
	}

	/** Get Art.
		@return Element Type (account or user defined)
	  */
	@Override
	public java.lang.String getElementType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ElementType);
	}

	/** Set Saldierung.
		@param IsBalancing 
		All transactions within an element value must balance (e.g. cost centers)
	  */
	@Override
	public void setIsBalancing (boolean IsBalancing)
	{
		set_Value (COLUMNNAME_IsBalancing, Boolean.valueOf(IsBalancing));
	}

	/** Get Saldierung.
		@return All transactions within an element value must balance (e.g. cost centers)
	  */
	@Override
	public boolean isBalancing () 
	{
		Object oo = get_Value(COLUMNNAME_IsBalancing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Basiskonto.
		@param IsNaturalAccount 
		The primary natural account
	  */
	@Override
	public void setIsNaturalAccount (boolean IsNaturalAccount)
	{
		set_Value (COLUMNNAME_IsNaturalAccount, Boolean.valueOf(IsNaturalAccount));
	}

	/** Get Basiskonto.
		@return The primary natural account
	  */
	@Override
	public boolean isNaturalAccount () 
	{
		Object oo = get_Value(COLUMNNAME_IsNaturalAccount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Value Format.
		@param VFormat 
		Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	@Override
	public void setVFormat (java.lang.String VFormat)
	{
		set_Value (COLUMNNAME_VFormat, VFormat);
	}

	/** Get Value Format.
		@return Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	@Override
	public java.lang.String getVFormat () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VFormat);
	}
}