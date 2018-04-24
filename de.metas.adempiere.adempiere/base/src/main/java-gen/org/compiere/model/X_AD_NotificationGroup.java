/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_NotificationGroup
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_NotificationGroup extends org.compiere.model.PO implements I_AD_NotificationGroup, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 615921059L;

    /** Standard Constructor */
    public X_AD_NotificationGroup (Properties ctx, int AD_NotificationGroup_ID, String trxName)
    {
      super (ctx, AD_NotificationGroup_ID, trxName);
      /** if (AD_NotificationGroup_ID == 0)
        {
			setAD_NotificationGroup_ID (0);
			setEntityType (null);
			setInternalName (null);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_NotificationGroup (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Notification group.
		@param AD_NotificationGroup_ID Notification group	  */
	@Override
	public void setAD_NotificationGroup_ID (int AD_NotificationGroup_ID)
	{
		if (AD_NotificationGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_NotificationGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_NotificationGroup_ID, Integer.valueOf(AD_NotificationGroup_ID));
	}

	/** Get Notification group.
		@return Notification group	  */
	@Override
	public int getAD_NotificationGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_NotificationGroup_ID);
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

	/** Set Interner Name.
		@param InternalName 
		Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public void setInternalName (java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
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