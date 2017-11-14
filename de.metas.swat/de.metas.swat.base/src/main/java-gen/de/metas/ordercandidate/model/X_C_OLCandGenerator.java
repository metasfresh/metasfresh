/** Generated Model - DO NOT CHANGE */
package de.metas.ordercandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_OLCandGenerator
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_OLCandGenerator extends org.compiere.model.PO implements I_C_OLCandGenerator, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1173211513L;

    /** Standard Constructor */
    public X_C_OLCandGenerator (Properties ctx, int C_OLCandGenerator_ID, String trxName)
    {
      super (ctx, C_OLCandGenerator_ID, trxName);
      /** if (C_OLCandGenerator_ID == 0)
        {
			setC_OLCandGenerator_ID (0);
			setEntityType (null);
			setIsProcessDirectly (false); // N
			setName (null);
			setOCGeneratorImpl (null);
        } */
    }

    /** Load Constructor */
    public X_C_OLCandGenerator (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Table getAD_Table_Source() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_Source_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table_Source(org.compiere.model.I_AD_Table AD_Table_Source)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_Source_ID, org.compiere.model.I_AD_Table.class, AD_Table_Source);
	}

	/** Set Quell-Tabelle.
		@param AD_Table_Source_ID Quell-Tabelle	  */
	@Override
	public void setAD_Table_Source_ID (int AD_Table_Source_ID)
	{
		if (AD_Table_Source_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_Source_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_Source_ID, Integer.valueOf(AD_Table_Source_ID));
	}

	/** Get Quell-Tabelle.
		@return Quell-Tabelle	  */
	@Override
	public int getAD_Table_Source_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_Source_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Auftragskand. Erzeugen.
		@param C_OLCandGenerator_ID Auftragskand. Erzeugen	  */
	@Override
	public void setC_OLCandGenerator_ID (int C_OLCandGenerator_ID)
	{
		if (C_OLCandGenerator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCandGenerator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCandGenerator_ID, Integer.valueOf(C_OLCandGenerator_ID));
	}

	/** Get Auftragskand. Erzeugen.
		@return Auftragskand. Erzeugen	  */
	@Override
	public int getC_OLCandGenerator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OLCandGenerator_ID);
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

	/** Set Sofort verarbeiten.
		@param IsProcessDirectly Sofort verarbeiten	  */
	@Override
	public void setIsProcessDirectly (boolean IsProcessDirectly)
	{
		set_Value (COLUMNNAME_IsProcessDirectly, Boolean.valueOf(IsProcessDirectly));
	}

	/** Get Sofort verarbeiten.
		@return Sofort verarbeiten	  */
	@Override
	public boolean isProcessDirectly () 
	{
		Object oo = get_Value(COLUMNNAME_IsProcessDirectly);
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

	/** Set Auftragskand.-Erzeuger.
		@param OCGeneratorImpl 
		Java-Class
	  */
	@Override
	public void setOCGeneratorImpl (java.lang.String OCGeneratorImpl)
	{
		set_Value (COLUMNNAME_OCGeneratorImpl, OCGeneratorImpl);
	}

	/** Get Auftragskand.-Erzeuger.
		@return Java-Class
	  */
	@Override
	public java.lang.String getOCGeneratorImpl () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OCGeneratorImpl);
	}
}