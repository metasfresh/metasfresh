/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.commission.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_HierarchyCommissionSettings
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_HierarchyCommissionSettings extends org.compiere.model.PO implements I_C_HierarchyCommissionSettings, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1839506217L;

    /** Standard Constructor */
    public X_C_HierarchyCommissionSettings (Properties ctx, int C_HierarchyCommissionSettings_ID, String trxName)
    {
      super (ctx, C_HierarchyCommissionSettings_ID, trxName);
      /** if (C_HierarchyCommissionSettings_ID == 0)
        {
			setC_HierarchyCommissionSettings_ID (0);
			setIsSubtractLowerLevelCommissionFromBase (true); // Y
			setName (null);
			setPointsPrecision (0); // 2
        } */
    }

    /** Load Constructor */
    public X_C_HierarchyCommissionSettings (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Einstellungen für Hierachie-Provisionsverträge.
		@param C_HierarchyCommissionSettings_ID Einstellungen für Hierachie-Provisionsverträge	  */
	@Override
	public void setC_HierarchyCommissionSettings_ID (int C_HierarchyCommissionSettings_ID)
	{
		if (C_HierarchyCommissionSettings_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_HierarchyCommissionSettings_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_HierarchyCommissionSettings_ID, Integer.valueOf(C_HierarchyCommissionSettings_ID));
	}

	/** Get Einstellungen für Hierachie-Provisionsverträge.
		@return Einstellungen für Hierachie-Provisionsverträge	  */
	@Override
	public int getC_HierarchyCommissionSettings_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_HierarchyCommissionSettings_ID);
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

	/** Set Provision von Basis abziehen.
		@param IsSubtractLowerLevelCommissionFromBase 
		Legt fest, ob die für untere Hierarchie-Ebenen ermittelten Provisionspunkte bei der aktuellen Ebene von der Basispunktzahl abgezogen werden sollen.
	  */
	@Override
	public void setIsSubtractLowerLevelCommissionFromBase (boolean IsSubtractLowerLevelCommissionFromBase)
	{
		set_Value (COLUMNNAME_IsSubtractLowerLevelCommissionFromBase, Boolean.valueOf(IsSubtractLowerLevelCommissionFromBase));
	}

	/** Get Provision von Basis abziehen.
		@return Legt fest, ob die für untere Hierarchie-Ebenen ermittelten Provisionspunkte bei der aktuellen Ebene von der Basispunktzahl abgezogen werden sollen.
	  */
	@Override
	public boolean isSubtractLowerLevelCommissionFromBase () 
	{
		Object oo = get_Value(COLUMNNAME_IsSubtractLowerLevelCommissionFromBase);
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

	/** Set Nachkommapräzision.
		@param PointsPrecision 
		Anzahl der Nachkommastellen auf die beim Berechnen der Provisionspounkte gerundet wird.
	  */
	@Override
	public void setPointsPrecision (int PointsPrecision)
	{
		set_Value (COLUMNNAME_PointsPrecision, Integer.valueOf(PointsPrecision));
	}

	/** Get Nachkommapräzision.
		@return Anzahl der Nachkommastellen auf die beim Berechnen der Provisionspounkte gerundet wird.
	  */
	@Override
	public int getPointsPrecision () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PointsPrecision);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}