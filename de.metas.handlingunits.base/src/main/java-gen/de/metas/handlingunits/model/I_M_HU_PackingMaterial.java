package de.metas.handlingunits.model;


/** Generated Interface for M_HU_PackingMaterial
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_PackingMaterial 
{

    /** TableName=M_HU_PackingMaterial */
    public static final String Table_Name = "M_HU_PackingMaterial";

    /** AD_Table_ID=540519 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_AD_Client>(I_M_HU_PackingMaterial.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_AD_Org>(I_M_HU_PackingMaterial.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Zulässiges Verpackungsvolumen.
	 * In diesem Feld kann ein maximales Ladungsvolumen für eine Handling Unit eingegeben werden. Wird das zulässige Volumen beim Verpacken erreicht, wird der weitere Verpackungsvorgang ggf. in einer neuen Handling Unit durchgeführt.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAllowedPackingVolume (java.math.BigDecimal AllowedPackingVolume);

	/**
	 * Get Zulässiges Verpackungsvolumen.
	 * In diesem Feld kann ein maximales Ladungsvolumen für eine Handling Unit eingegeben werden. Wird das zulässige Volumen beim Verpacken erreicht, wird der weitere Verpackungsvorgang ggf. in einer neuen Handling Unit durchgeführt.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAllowedPackingVolume();

    /** Column definition for AllowedPackingVolume */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_AllowedPackingVolume = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "AllowedPackingVolume", null);
    /** Column name AllowedPackingVolume */
    public static final String COLUMNNAME_AllowedPackingVolume = "AllowedPackingVolume";

	/**
	 * Set Zulässiges Verpackungsgewicht.
	 * In diesem Feld kann ein maximales Ladungsgewicht für eine Handling Unit eingegeben werden. Wird das zulässige Gewicht beim Verpacken erreicht wird der weitere Verpackungsvorgang ggf. mit einer neuen Handling Unit durchgeführt.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAllowedPackingWeight (java.math.BigDecimal AllowedPackingWeight);

	/**
	 * Get Zulässiges Verpackungsgewicht.
	 * In diesem Feld kann ein maximales Ladungsgewicht für eine Handling Unit eingegeben werden. Wird das zulässige Gewicht beim Verpacken erreicht wird der weitere Verpackungsvorgang ggf. mit einer neuen Handling Unit durchgeführt.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAllowedPackingWeight();

    /** Column definition for AllowedPackingWeight */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_AllowedPackingWeight = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "AllowedPackingWeight", null);
    /** Column name AllowedPackingWeight */
    public static final String COLUMNNAME_AllowedPackingWeight = "AllowedPackingWeight";

	/**
	 * Set Einheit Abessungen.
	 * Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_Dimension_ID (int C_UOM_Dimension_ID);

	/**
	 * Get Einheit Abessungen.
	 * Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_Dimension_ID();

	public org.compiere.model.I_C_UOM getC_UOM_Dimension();

	public void setC_UOM_Dimension(org.compiere.model.I_C_UOM C_UOM_Dimension);

    /** Column definition for C_UOM_Dimension_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_C_UOM> COLUMN_C_UOM_Dimension_ID = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_C_UOM>(I_M_HU_PackingMaterial.class, "C_UOM_Dimension_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_Dimension_ID */
    public static final String COLUMNNAME_C_UOM_Dimension_ID = "C_UOM_Dimension_ID";

	/**
	 * Set Einheit Gewicht.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_Weight_ID (int C_UOM_Weight_ID);

	/**
	 * Get Einheit Gewicht.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_Weight_ID();

	public org.compiere.model.I_C_UOM getC_UOM_Weight();

	public void setC_UOM_Weight(org.compiere.model.I_C_UOM C_UOM_Weight);

    /** Column definition for C_UOM_Weight_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_C_UOM> COLUMN_C_UOM_Weight_ID = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_C_UOM>(I_M_HU_PackingMaterial.class, "C_UOM_Weight_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_Weight_ID */
    public static final String COLUMNNAME_C_UOM_Weight_ID = "C_UOM_Weight_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_AD_User>(I_M_HU_PackingMaterial.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Übervolumentoleranz.
	 * In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsvolumen einer Handling Unit überschritten werden kann.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExcessVolumeTolerance (java.math.BigDecimal ExcessVolumeTolerance);

	/**
	 * Get Übervolumentoleranz.
	 * In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsvolumen einer Handling Unit überschritten werden kann.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getExcessVolumeTolerance();

    /** Column definition for ExcessVolumeTolerance */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_ExcessVolumeTolerance = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "ExcessVolumeTolerance", null);
    /** Column name ExcessVolumeTolerance */
    public static final String COLUMNNAME_ExcessVolumeTolerance = "ExcessVolumeTolerance";

	/**
	 * Set Übergewichtstoleranz.
	 * In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsgewicht einer Handling Unit überschritten werden kann.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExcessWeightTolerance (java.math.BigDecimal ExcessWeightTolerance);

	/**
	 * Get Übergewichtstoleranz.
	 * In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsgewicht einer Handling Unit überschritten werden kann.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getExcessWeightTolerance();

    /** Column definition for ExcessWeightTolerance */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_ExcessWeightTolerance = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "ExcessWeightTolerance", null);
    /** Column name ExcessWeightTolerance */
    public static final String COLUMNNAME_ExcessWeightTolerance = "ExcessWeightTolerance";

	/**
	 * Set Füllgrad.
	 * Der Füllgrad gibt prozentual an, bis zu welchem Grad eine Handling Unit gefüllt werden soll. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFillingLevel (java.math.BigDecimal FillingLevel);

	/**
	 * Get Füllgrad.
	 * Der Füllgrad gibt prozentual an, bis zu welchem Grad eine Handling Unit gefüllt werden soll. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFillingLevel();

    /** Column definition for FillingLevel */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_FillingLevel = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "FillingLevel", null);
    /** Column name FillingLevel */
    public static final String COLUMNNAME_FillingLevel = "FillingLevel";

	/**
	 * Set Höhe.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHeight (java.math.BigDecimal Height);

	/**
	 * Get Höhe.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getHeight();

    /** Column definition for Height */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Height = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "Height", null);
    /** Column name Height */
    public static final String COLUMNNAME_Height = "Height";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Geschlossen.
	 * In diesem Feld kann gesteuert werden, ob beim Verpacken das Gesamtvolumen verändert wird oder gleich bleibt.
Beispiel
Beim Verpacken einer offenen Palette ändert sich das Gesamtvolumen.
Beim Verpacken einer geschlossenen Kiste ändert sich das Gesamtvolumen nicht.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsClosed (boolean IsClosed);

	/**
	 * Get Geschlossen.
	 * In diesem Feld kann gesteuert werden, ob beim Verpacken das Gesamtvolumen verändert wird oder gleich bleibt.
Beispiel
Beim Verpacken einer offenen Palette ändert sich das Gesamtvolumen.
Beim Verpacken einer geschlossenen Kiste ändert sich das Gesamtvolumen nicht.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isClosed();

    /** Column definition for IsClosed */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_IsClosed = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "IsClosed", null);
    /** Column name IsClosed */
    public static final String COLUMNNAME_IsClosed = "IsClosed";

	/**
	 * Set Länge.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLength (java.math.BigDecimal Length);

	/**
	 * Get Länge.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLength();

    /** Column definition for Length */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Length = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "Length", null);
    /** Column name Length */
    public static final String COLUMNNAME_Length = "Length";

	/**
	 * Set Packmittel.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PackingMaterial_ID (int M_HU_PackingMaterial_ID);

	/**
	 * Get Packmittel.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PackingMaterial_ID();

    /** Column definition for M_HU_PackingMaterial_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_M_HU_PackingMaterial_ID = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "M_HU_PackingMaterial_ID", null);
    /** Column name M_HU_PackingMaterial_ID */
    public static final String COLUMNNAME_M_HU_PackingMaterial_ID = "M_HU_PackingMaterial_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_M_Product>(I_M_HU_PackingMaterial.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Stapelbarkeitsfaktor.
	 * Der Stapelbarkeitsfaktor gibt an, wie viele Packmittel (z.B. Paletten) aufeinander gestapelt werden können. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStackabilityFactor (int StackabilityFactor);

	/**
	 * Get Stapelbarkeitsfaktor.
	 * Der Stapelbarkeitsfaktor gibt an, wie viele Packmittel (z.B. Paletten) aufeinander gestapelt werden können. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getStackabilityFactor();

    /** Column definition for StackabilityFactor */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_StackabilityFactor = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "StackabilityFactor", null);
    /** Column name StackabilityFactor */
    public static final String COLUMNNAME_StackabilityFactor = "StackabilityFactor";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, org.compiere.model.I_AD_User>(I_M_HU_PackingMaterial.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Breite.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWidth (java.math.BigDecimal Width);

	/**
	 * Get Breite.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getWidth();

    /** Column definition for Width */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Width = new org.adempiere.model.ModelColumn<I_M_HU_PackingMaterial, Object>(I_M_HU_PackingMaterial.class, "Width", null);
    /** Column name Width */
    public static final String COLUMNNAME_Width = "Width";
}
