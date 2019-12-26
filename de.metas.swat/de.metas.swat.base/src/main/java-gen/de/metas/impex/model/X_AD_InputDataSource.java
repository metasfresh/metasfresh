/** Generated Model - DO NOT CHANGE */
package de.metas.impex.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_InputDataSource
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_AD_InputDataSource extends org.compiere.model.PO implements I_AD_InputDataSource, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1582538781L;

    /** Standard Constructor */
    public X_AD_InputDataSource (Properties ctx, int AD_InputDataSource_ID, String trxName)
    {
      super (ctx, AD_InputDataSource_ID, trxName);
      /** if (AD_InputDataSource_ID == 0)
        {
			setAD_InputDataSource_ID (0);
			setEntityType (null); // de.metas.swat
			setIsDestination (false); // N
			setIsEdiEnabled (false); // N
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_InputDataSource (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Eingabequelle.
		@param AD_InputDataSource_ID Eingabequelle	  */
	@Override
	public void setAD_InputDataSource_ID (int AD_InputDataSource_ID)
	{
		if (AD_InputDataSource_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_InputDataSource_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_InputDataSource_ID, Integer.valueOf(AD_InputDataSource_ID));
	}

	/** Get Eingabequelle.
		@return Eingabequelle	  */
	@Override
	public int getAD_InputDataSource_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_InputDataSource_ID);
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

	/** Set External ID.
		@param ExternalId External ID	  */
	@Override
	public void setExternalId (java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	/** Get External ID.
		@return External ID	  */
	@Override
	public java.lang.String getExternalId ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalId);
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

	/** Set IsDestination.
		@param IsDestination IsDestination	  */
	@Override
	public void setIsDestination (boolean IsDestination)
	{
		set_Value (COLUMNNAME_IsDestination, Boolean.valueOf(IsDestination));
	}

	/** Get IsDestination.
		@return IsDestination	  */
	@Override
	public boolean isDestination ()
	{
		Object oo = get_Value(COLUMNNAME_IsDestination);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Beleg soll per EDI übermittelt werden.
		@param IsEdiEnabled Beleg soll per EDI übermittelt werden	  */
	@Override
	public void setIsEdiEnabled (boolean IsEdiEnabled)
	{
		set_Value (COLUMNNAME_IsEdiEnabled, Boolean.valueOf(IsEdiEnabled));
	}

	/** Get Beleg soll per EDI übermittelt werden.
		@return Beleg soll per EDI übermittelt werden	  */
	@Override
	public boolean isEdiEnabled ()
	{
		Object oo = get_Value(COLUMNNAME_IsEdiEnabled);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set URL.
		@param URL
		Full URL address - e.g. http://www.adempiere.org
	  */
	@Override
	public void setURL (java.lang.String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	/** Get URL.
		@return Full URL address - e.g. http://www.adempiere.org
	  */
	@Override
	public java.lang.String getURL ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_URL);
	}

	/** Set Suchschlüssel.
		@param Value
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getValue ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}