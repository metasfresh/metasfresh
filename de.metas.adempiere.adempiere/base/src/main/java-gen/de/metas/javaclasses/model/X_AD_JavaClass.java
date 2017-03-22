/** Generated Model - DO NOT CHANGE */
package de.metas.javaclasses.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_JavaClass
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_AD_JavaClass extends org.compiere.model.PO implements I_AD_JavaClass, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2104480126L;

    /** Standard Constructor */
    public X_AD_JavaClass (Properties ctx, int AD_JavaClass_ID, String trxName)
    {
      super (ctx, AD_JavaClass_ID, trxName);
      /** if (AD_JavaClass_ID == 0)
        {
			setEntityType (0);
			setAD_JavaClass_ID (0);
			setAD_JavaClass_Type_ID (0);
			setClassname (null);
			setIsInterface (false);
// N
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_JavaClass (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Java Klasse.
		@param AD_JavaClass_ID Java Klasse	  */
	@Override
	public void setAD_JavaClass_ID (int AD_JavaClass_ID)
	{
		if (AD_JavaClass_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_JavaClass_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_JavaClass_ID, Integer.valueOf(AD_JavaClass_ID));
	}

	/** Get Java Klasse.
		@return Java Klasse	  */
	@Override
	public int getAD_JavaClass_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_JavaClass_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.javaclasses.model.I_AD_JavaClass_Type getAD_JavaClass_Type() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_JavaClass_Type_ID, de.metas.javaclasses.model.I_AD_JavaClass_Type.class);
	}

	@Override
	public void setAD_JavaClass_Type(de.metas.javaclasses.model.I_AD_JavaClass_Type AD_JavaClass_Type)
	{
		set_ValueFromPO(COLUMNNAME_AD_JavaClass_Type_ID, de.metas.javaclasses.model.I_AD_JavaClass_Type.class, AD_JavaClass_Type);
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

	/** Set Interface.
		@param IsInterface Interface	  */
	@Override
	public void setIsInterface (boolean IsInterface)
	{
		set_Value (COLUMNNAME_IsInterface, Boolean.valueOf(IsInterface));
	}

	/** Get Interface.
		@return Interface	  */
	@Override
	public boolean isInterface ()
	{
		Object oo = get_Value(COLUMNNAME_IsInterface);
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
}