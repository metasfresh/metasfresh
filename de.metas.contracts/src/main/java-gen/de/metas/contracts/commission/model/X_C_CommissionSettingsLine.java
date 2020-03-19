/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_CommissionSettingsLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_CommissionSettingsLine extends org.compiere.model.PO implements I_C_CommissionSettingsLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -81235738L;

    /** Standard Constructor */
    public X_C_CommissionSettingsLine (Properties ctx, int C_CommissionSettingsLine_ID, String trxName)
    {
      super (ctx, C_CommissionSettingsLine_ID, trxName);
      /** if (C_CommissionSettingsLine_ID == 0)
        {
			setC_CommissionSettingsLine_ID (0);
			setC_HierarchyCommissionSettings_ID (0);
			setIsExcludeBPGroup (false); // N
			setIsExcludeProductCategory (false); // N
			setPercentOfBasePoints (BigDecimal.ZERO);
			setSeqNo (0); // @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM C_CommissionSettingsLine WHERE C_HierarchyCommissionSettings=@C_HierarchyCommissionSettings@
        } */
    }

    /** Load Constructor */
    public X_C_CommissionSettingsLine (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Kunde.
		@param C_BPartner_Customer_ID Kunde	  */
	@Override
	public void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, Integer.valueOf(C_BPartner_Customer_ID));
	}

	/** Get Kunde.
		@return Kunde	  */
	@Override
	public int getC_BPartner_Customer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Customer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Einstellungsdetail.
		@param C_CommissionSettingsLine_ID Einstellungsdetail	  */
	@Override
	public void setC_CommissionSettingsLine_ID (int C_CommissionSettingsLine_ID)
	{
		if (C_CommissionSettingsLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CommissionSettingsLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CommissionSettingsLine_ID, Integer.valueOf(C_CommissionSettingsLine_ID));
	}

	/** Get Einstellungsdetail.
		@return Einstellungsdetail	  */
	@Override
	public int getC_CommissionSettingsLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CommissionSettingsLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings getC_HierarchyCommissionSettings()
	{
		return get_ValueAsPO(COLUMNNAME_C_HierarchyCommissionSettings_ID, de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings.class);
	}

	@Override
	public void setC_HierarchyCommissionSettings(de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings C_HierarchyCommissionSettings)
	{
		set_ValueFromPO(COLUMNNAME_C_HierarchyCommissionSettings_ID, de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings.class, C_HierarchyCommissionSettings);
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

	@Override
	public org.compiere.model.I_C_BP_Group getCustomer_Group()
	{
		return get_ValueAsPO(COLUMNNAME_Customer_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setCustomer_Group(org.compiere.model.I_C_BP_Group Customer_Group)
	{
		set_ValueFromPO(COLUMNNAME_Customer_Group_ID, org.compiere.model.I_C_BP_Group.class, Customer_Group);
	}

	/** Set Kundengruppe.
		@param Customer_Group_ID Kundengruppe	  */
	@Override
	public void setCustomer_Group_ID (int Customer_Group_ID)
	{
		if (Customer_Group_ID < 1) 
			set_Value (COLUMNNAME_Customer_Group_ID, null);
		else 
			set_Value (COLUMNNAME_Customer_Group_ID, Integer.valueOf(Customer_Group_ID));
	}

	/** Get Kundengruppe.
		@return Kundengruppe	  */
	@Override
	public int getCustomer_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Customer_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kunde bzw. Gruppe ausschließen.
		@param IsExcludeBPGroup 
		Wenn eine Kundegruppe oder ein Kunde ausgewählt ist, entscheided dieses Feld, ob es ein Ein- oder Ausschlusskriterium ist
	  */
	@Override
	public void setIsExcludeBPGroup (boolean IsExcludeBPGroup)
	{
		set_Value (COLUMNNAME_IsExcludeBPGroup, Boolean.valueOf(IsExcludeBPGroup));
	}

	/** Get Kunde bzw. Gruppe ausschließen.
		@return Wenn eine Kundegruppe oder ein Kunde ausgewählt ist, entscheided dieses Feld, ob es ein Ein- oder Ausschlusskriterium ist
	  */
	@Override
	public boolean isExcludeBPGroup () 
	{
		Object oo = get_Value(COLUMNNAME_IsExcludeBPGroup);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Produktkategorie ausschließen.
		@param IsExcludeProductCategory 
		Wenn eine Produktkategorie ausgewählt ist, entscheided dieses Feld, ob diese Kategorie ein Ein- oder Ausschlusskriterium ist
	  */
	@Override
	public void setIsExcludeProductCategory (boolean IsExcludeProductCategory)
	{
		set_Value (COLUMNNAME_IsExcludeProductCategory, Boolean.valueOf(IsExcludeProductCategory));
	}

	/** Get Produktkategorie ausschließen.
		@return Wenn eine Produktkategorie ausgewählt ist, entscheided dieses Feld, ob diese Kategorie ein Ein- oder Ausschlusskriterium ist
	  */
	@Override
	public boolean isExcludeProductCategory () 
	{
		Object oo = get_Value(COLUMNNAME_IsExcludeProductCategory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Produkt Kategorie.
		@param M_Product_Category_ID 
		Kategorie eines Produktes
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Produkt Kategorie.
		@return Kategorie eines Produktes
	  */
	@Override
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set % der Basispunkte.
		@param PercentOfBasePoints % der Basispunkte	  */
	@Override
	public void setPercentOfBasePoints (java.math.BigDecimal PercentOfBasePoints)
	{
		set_Value (COLUMNNAME_PercentOfBasePoints, PercentOfBasePoints);
	}

	/** Get % der Basispunkte.
		@return % der Basispunkte	  */
	@Override
	public java.math.BigDecimal getPercentOfBasePoints () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PercentOfBasePoints);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}