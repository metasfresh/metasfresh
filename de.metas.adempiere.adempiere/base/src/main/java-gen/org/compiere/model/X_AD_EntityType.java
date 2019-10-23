/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_EntityType
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_EntityType extends org.compiere.model.PO implements I_AD_EntityType, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 134094654L;

    /** Standard Constructor */
    public X_AD_EntityType (Properties ctx, int AD_EntityType_ID, String trxName)
    {
      super (ctx, AD_EntityType_ID, trxName);
      /** if (AD_EntityType_ID == 0)
        {
			setAD_EntityType_ID (0);
			setEntityType (null);
			setIsDisplayed (true);
// Y
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_EntityType (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Entitäts-Art.
		@param AD_EntityType_ID 
		Systementitäts-Art
	  */
	@Override
	public void setAD_EntityType_ID (int AD_EntityType_ID)
	{
		if (AD_EntityType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_EntityType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_EntityType_ID, Integer.valueOf(AD_EntityType_ID));
	}

	/** Get Entitäts-Art.
		@return Systementitäts-Art
	  */
	@Override
	public int getAD_EntityType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_EntityType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Classpath.
		@param Classpath 
		Extension Classpath
	  */
	@Override
	public void setClasspath (java.lang.String Classpath)
	{
		set_Value (COLUMNNAME_Classpath, Classpath);
	}

	/** Get Classpath.
		@return Extension Classpath
	  */
	@Override
	public java.lang.String getClasspath () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Classpath);
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

	/** Set Entitäts-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{
		set_ValueNoCheck (COLUMNNAME_EntityType, EntityType);
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

	/** Set Displayed.
		@param IsDisplayed 
		Determines, if this field is displayed
	  */
	@Override
	public void setIsDisplayed (boolean IsDisplayed)
	{
		set_Value (COLUMNNAME_IsDisplayed, Boolean.valueOf(IsDisplayed));
	}

	/** Get Displayed.
		@return Determines, if this field is displayed
	  */
	@Override
	public boolean isDisplayed () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisplayed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ModelPackage.
		@param ModelPackage 
		Java Package of the model classes
	  */
	@Override
	public void setModelPackage (java.lang.String ModelPackage)
	{
		set_Value (COLUMNNAME_ModelPackage, ModelPackage);
	}

	/** Get ModelPackage.
		@return Java Package of the model classes
	  */
	@Override
	public java.lang.String getModelPackage () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ModelPackage);
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

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Version.
		@param Version 
		Version of the table definition
	  */
	@Override
	public void setVersion (java.lang.String Version)
	{
		set_Value (COLUMNNAME_Version, Version);
	}

	/** Get Version.
		@return Version of the table definition
	  */
	@Override
	public java.lang.String getVersion () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Version);
	}

	/** Set WebUIServletListener Class.
		@param WebUIServletListenerClass 
		Optional class to execute custom code on WebUI startup; A declared class needs to implement IWebUIServletListener
	  */
	@Override
	public void setWebUIServletListenerClass (java.lang.String WebUIServletListenerClass)
	{
		set_Value (COLUMNNAME_WebUIServletListenerClass, WebUIServletListenerClass);
	}

	/** Get WebUIServletListener Class.
		@return Optional class to execute custom code on WebUI startup; A declared class needs to implement IWebUIServletListener
	  */
	@Override
	public java.lang.String getWebUIServletListenerClass () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WebUIServletListenerClass);
	}
}