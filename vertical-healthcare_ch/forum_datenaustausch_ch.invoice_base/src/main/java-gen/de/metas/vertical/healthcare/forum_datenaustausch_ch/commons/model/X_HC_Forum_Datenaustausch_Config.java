/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for HC_Forum_Datenaustausch_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_HC_Forum_Datenaustausch_Config extends org.compiere.model.PO implements I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -688898494L;

    /** Standard Constructor */
    public X_HC_Forum_Datenaustausch_Config (Properties ctx, int HC_Forum_Datenaustausch_Config_ID, String trxName)
    {
      super (ctx, HC_Forum_Datenaustausch_Config_ID, trxName);
      /** if (HC_Forum_Datenaustausch_Config_ID == 0)
        {
			setExportedXmlMode (null); // P
			setExportedXmlVersion (null);
			setHC_Forum_Datenaustausch_Config_ID (0);
			setVia_EAN (null);
        } */
    }

    /** Load Constructor */
    public X_HC_Forum_Datenaustausch_Config (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_BPartner getBill_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Bill_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setBill_BPartner(org.compiere.model.I_C_BPartner Bill_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_Bill_BPartner_ID, org.compiere.model.I_C_BPartner.class, Bill_BPartner);
	}

	/** Set Rechnungspartner.
		@param Bill_BPartner_ID 
		Geschäftspartner für die Rechnungsstellung
	  */
	@Override
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
	}

	/** Get Rechnungspartner.
		@return Geschäftspartner für die Rechnungsstellung
	  */
	@Override
	public int getBill_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_BPartner_ID);
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
	 * ExportedXmlMode AD_Reference_ID=540940
	 * Reference name: ExportedXmlMode
	 */
	public static final int EXPORTEDXMLMODE_AD_Reference_ID=540940;
	/** production = P */
	public static final String EXPORTEDXMLMODE_Production = "P";
	/** test = T */
	public static final String EXPORTEDXMLMODE_Test = "T";
	/** Set Modus der Exportdateien.
		@param ExportedXmlMode Modus der Exportdateien	  */
	@Override
	public void setExportedXmlMode (java.lang.String ExportedXmlMode)
	{

		set_Value (COLUMNNAME_ExportedXmlMode, ExportedXmlMode);
	}

	/** Get Modus der Exportdateien.
		@return Modus der Exportdateien	  */
	@Override
	public java.lang.String getExportedXmlMode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExportedXmlMode);
	}

	/** 
	 * ExportedXmlVersion AD_Reference_ID=540921
	 * Reference name: ExportXmlVersion
	 */
	public static final int EXPORTEDXMLVERSION_AD_Reference_ID=540921;
	/** v440 = 4.4 */
	public static final String EXPORTEDXMLVERSION_V440 = "4.4";
	/** Set Export XML Version.
		@param ExportedXmlVersion Export XML Version	  */
	@Override
	public void setExportedXmlVersion (java.lang.String ExportedXmlVersion)
	{

		set_Value (COLUMNNAME_ExportedXmlVersion, ExportedXmlVersion);
	}

	/** Get Export XML Version.
		@return Export XML Version	  */
	@Override
	public java.lang.String getExportedXmlVersion () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExportedXmlVersion);
	}

	/** Set Absender EAN.
		@param From_EAN 
		Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei
	  */
	@Override
	public void setFrom_EAN (java.lang.String From_EAN)
	{
		set_Value (COLUMNNAME_From_EAN, From_EAN);
	}

	/** Get Absender EAN.
		@return Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei
	  */
	@Override
	public java.lang.String getFrom_EAN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_From_EAN);
	}

	/** Set forum-datenaustausch.ch config.
		@param HC_Forum_Datenaustausch_Config_ID forum-datenaustausch.ch config	  */
	@Override
	public void setHC_Forum_Datenaustausch_Config_ID (int HC_Forum_Datenaustausch_Config_ID)
	{
		if (HC_Forum_Datenaustausch_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HC_Forum_Datenaustausch_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HC_Forum_Datenaustausch_Config_ID, Integer.valueOf(HC_Forum_Datenaustausch_Config_ID));
	}

	/** Get forum-datenaustausch.ch config.
		@return forum-datenaustausch.ch config	  */
	@Override
	public int getHC_Forum_Datenaustausch_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HC_Forum_Datenaustausch_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Speicherverzeichnis.
		@param StoreDirectory 
		Verzeichnis, in dem exportierte Dateien gespeichert werden.
	  */
	@Override
	public void setStoreDirectory (java.lang.String StoreDirectory)
	{
		set_Value (COLUMNNAME_StoreDirectory, StoreDirectory);
	}

	/** Get Speicherverzeichnis.
		@return Verzeichnis, in dem exportierte Dateien gespeichert werden.
	  */
	@Override
	public java.lang.String getStoreDirectory () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StoreDirectory);
	}

	/** Set Via EAN.
		@param Via_EAN 
		Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.
	  */
	@Override
	public void setVia_EAN (java.lang.String Via_EAN)
	{
		set_Value (COLUMNNAME_Via_EAN, Via_EAN);
	}

	/** Get Via EAN.
		@return Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.
	  */
	@Override
	public java.lang.String getVia_EAN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Via_EAN);
	}
}