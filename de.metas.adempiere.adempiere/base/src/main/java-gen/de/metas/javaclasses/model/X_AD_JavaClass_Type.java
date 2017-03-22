/** Generated Model - DO NOT CHANGE */
package de.metas.javaclasses.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_JavaClass_Type
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_AD_JavaClass_Type extends org.compiere.model.PO implements I_AD_JavaClass_Type, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1492810605L;

    /** Standard Constructor */
    public X_AD_JavaClass_Type (Properties ctx, int AD_JavaClass_Type_ID, String trxName)
    {
      super (ctx, AD_JavaClass_Type_ID, trxName);
      /** if (AD_JavaClass_Type_ID == 0)
        {
			setEntityType (0);
			setAD_JavaClass_Type_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_JavaClass_Type (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Java Class Type.
		@param AD_JavaClass_Type_ID Java Class Type	  */
	@Override
	public void setAD_JavaClass_Type_ID (int AD_JavaClass_Type_ID)
	{
		if (AD_JavaClass_Type_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_JavaClass_Type_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_JavaClass_Type_ID, Integer.valueOf(AD_JavaClass_Type_ID));
	}

	/** Get Java Class Type.
		@return Java Class Type	  */
	@Override
	public int getAD_JavaClass_Type_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_JavaClass_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Java-Klasse.
		@param Classname Java-Klasse	  */
	@Override
	public void setClassname (java.lang.String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	/** Get Java-Klasse.
		@return Java-Klasse	  */
	@Override
	public java.lang.String getClassname ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Classname);
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

	/** Set Interner Name.
		@param InternalName
		Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public void setInternalName (java.lang.String InternalName)
	{
		set_ValueNoCheck (COLUMNNAME_InternalName, InternalName);
	}

	/** Get Interner Name.
		@return Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public java.lang.String getInternalName ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_InternalName);
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
}