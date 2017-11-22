/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Reference
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Reference extends org.compiere.model.PO implements I_AD_Reference, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1980394103L;

    /** Standard Constructor */
    public X_AD_Reference (Properties ctx, int AD_Reference_ID, String trxName)
    {
      super (ctx, AD_Reference_ID, trxName);
      /** if (AD_Reference_ID == 0)
        {
			setAD_Reference_ID (0);
			setEntityType (null); // U
			setName (null);
			setValidationType (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Reference (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Referenz.
		@param AD_Reference_ID 
		System Reference and Validation
	  */
	@Override
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Referenz.
		@return System Reference and Validation
	  */
	@Override
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
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
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitäts-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitäts-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set Order By Value.
		@param IsOrderByValue 
		Order list using the value column instead of the name column
	  */
	@Override
	public void setIsOrderByValue (boolean IsOrderByValue)
	{
		set_Value (COLUMNNAME_IsOrderByValue, Boolean.valueOf(IsOrderByValue));
	}

	/** Get Order By Value.
		@return Order list using the value column instead of the name column
	  */
	@Override
	public boolean isOrderByValue () 
	{
		Object oo = get_Value(COLUMNNAME_IsOrderByValue);
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

	/** 
	 * ValidationType AD_Reference_ID=2
	 * Reference name: AD_Reference Validation Types
	 */
	public static final int VALIDATIONTYPE_AD_Reference_ID=2;
	/** ListValidation = L */
	public static final String VALIDATIONTYPE_ListValidation = "L";
	/** DataType = D */
	public static final String VALIDATIONTYPE_DataType = "D";
	/** TableValidation = T */
	public static final String VALIDATIONTYPE_TableValidation = "T";
	/** Set Validation type.
		@param ValidationType 
		Different method of validating data
	  */
	@Override
	public void setValidationType (java.lang.String ValidationType)
	{

		set_Value (COLUMNNAME_ValidationType, ValidationType);
	}

	/** Get Validation type.
		@return Different method of validating data
	  */
	@Override
	public java.lang.String getValidationType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ValidationType);
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