package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_PackingMaterial
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_PackingMaterial 
{

	String Table_Name = "M_HU_PackingMaterial";

//	/** AD_Table_ID=540519 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Zulässiges Verpackungsvolumen.
	 * In diesem Feld kann ein maximales Ladungsvolumen für eine Handling Unit eingegeben werden. Wird das zulässige Volumen beim Verpacken erreicht, wird der weitere Verpackungsvorgang ggf. in einer neuen Handling Unit durchgeführt.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAllowedPackingVolume (@Nullable BigDecimal AllowedPackingVolume);

	/**
	 * Get Zulässiges Verpackungsvolumen.
	 * In diesem Feld kann ein maximales Ladungsvolumen für eine Handling Unit eingegeben werden. Wird das zulässige Volumen beim Verpacken erreicht, wird der weitere Verpackungsvorgang ggf. in einer neuen Handling Unit durchgeführt.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAllowedPackingVolume();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_AllowedPackingVolume = new ModelColumn<>(I_M_HU_PackingMaterial.class, "AllowedPackingVolume", null);
	String COLUMNNAME_AllowedPackingVolume = "AllowedPackingVolume";

	/**
	 * Set Zulässiges Verpackungsgewicht.
	 * In diesem Feld kann ein maximales Ladungsgewicht für eine Handling Unit eingegeben werden. Wird das zulässige Gewicht beim Verpacken erreicht wird der weitere Verpackungsvorgang ggf. mit einer neuen Handling Unit durchgeführt.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAllowedPackingWeight (@Nullable BigDecimal AllowedPackingWeight);

	/**
	 * Get Zulässiges Verpackungsgewicht.
	 * In diesem Feld kann ein maximales Ladungsgewicht für eine Handling Unit eingegeben werden. Wird das zulässige Gewicht beim Verpacken erreicht wird der weitere Verpackungsvorgang ggf. mit einer neuen Handling Unit durchgeführt.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAllowedPackingWeight();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_AllowedPackingWeight = new ModelColumn<>(I_M_HU_PackingMaterial.class, "AllowedPackingWeight", null);
	String COLUMNNAME_AllowedPackingWeight = "AllowedPackingWeight";

	/**
	 * Set UOM for Dimensions.
	 * Unit of measurement for the dimensions (height, width, length) of the packaging material, and basic unit of measurement for all volume specifications.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_Dimension_ID (int C_UOM_Dimension_ID);

	/**
	 * Get UOM for Dimensions.
	 * Unit of measurement for the dimensions (height, width, length) of the packaging material, and basic unit of measurement for all volume specifications.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_Dimension_ID();

	String COLUMNNAME_C_UOM_Dimension_ID = "C_UOM_Dimension_ID";

	/**
	 * Set Einheit Gewicht.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_Weight_ID (int C_UOM_Weight_ID);

	/**
	 * Get Einheit Gewicht.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_Weight_ID();

	String COLUMNNAME_C_UOM_Weight_ID = "C_UOM_Weight_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_PackingMaterial.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Description = new ModelColumn<>(I_M_HU_PackingMaterial.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Übervolumentoleranz.
	 * In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsvolumen einer Handling Unit überschritten werden kann.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExcessVolumeTolerance (@Nullable BigDecimal ExcessVolumeTolerance);

	/**
	 * Get Übervolumentoleranz.
	 * In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsvolumen einer Handling Unit überschritten werden kann.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getExcessVolumeTolerance();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_ExcessVolumeTolerance = new ModelColumn<>(I_M_HU_PackingMaterial.class, "ExcessVolumeTolerance", null);
	String COLUMNNAME_ExcessVolumeTolerance = "ExcessVolumeTolerance";

	/**
	 * Set Übergewichtstoleranz.
	 * In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsgewicht einer Handling Unit überschritten werden kann.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExcessWeightTolerance (@Nullable BigDecimal ExcessWeightTolerance);

	/**
	 * Get Übergewichtstoleranz.
	 * In diesem Feld kann eine prozentuale Toleranzgrenze eingegeben werden, bis zu der das zulässige Verpackungsgewicht einer Handling Unit überschritten werden kann.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getExcessWeightTolerance();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_ExcessWeightTolerance = new ModelColumn<>(I_M_HU_PackingMaterial.class, "ExcessWeightTolerance", null);
	String COLUMNNAME_ExcessWeightTolerance = "ExcessWeightTolerance";

	/**
	 * Set Füllgrad.
	 * Der Füllgrad gibt prozentual an, bis zu welchem Grad eine Handling Unit gefüllt werden soll. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFillingLevel (@Nullable BigDecimal FillingLevel);

	/**
	 * Get Füllgrad.
	 * Der Füllgrad gibt prozentual an, bis zu welchem Grad eine Handling Unit gefüllt werden soll. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFillingLevel();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_FillingLevel = new ModelColumn<>(I_M_HU_PackingMaterial.class, "FillingLevel", null);
	String COLUMNNAME_FillingLevel = "FillingLevel";

	/**
	 * Set Höhe.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHeight (@Nullable BigDecimal Height);

	/**
	 * Get Höhe.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getHeight();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Height = new ModelColumn<>(I_M_HU_PackingMaterial.class, "Height", null);
	String COLUMNNAME_Height = "Height";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_PackingMaterial.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Geschlossen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsClosed (boolean IsClosed);

	/**
	 * Get Geschlossen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isClosed();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_IsClosed = new ModelColumn<>(I_M_HU_PackingMaterial.class, "IsClosed", null);
	String COLUMNNAME_IsClosed = "IsClosed";

	/**
	 * Set Länge.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLength (@Nullable BigDecimal Length);

	/**
	 * Get Länge.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getLength();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Length = new ModelColumn<>(I_M_HU_PackingMaterial.class, "Length", null);
	String COLUMNNAME_Length = "Length";

	/**
	 * Set Packing Material.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PackingMaterial_ID (int M_HU_PackingMaterial_ID);

	/**
	 * Get Packing Material.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PackingMaterial_ID();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_M_HU_PackingMaterial_ID = new ModelColumn<>(I_M_HU_PackingMaterial.class, "M_HU_PackingMaterial_ID", null);
	String COLUMNNAME_M_HU_PackingMaterial_ID = "M_HU_PackingMaterial_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Name = new ModelColumn<>(I_M_HU_PackingMaterial.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Stapelbarkeitsfaktor.
	 * Der Stapelbarkeitsfaktor gibt an, wie viele Packmittel (z.B. Paletten) aufeinander gestapelt werden können. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStackabilityFactor (int StackabilityFactor);

	/**
	 * Get Stapelbarkeitsfaktor.
	 * Der Stapelbarkeitsfaktor gibt an, wie viele Packmittel (z.B. Paletten) aufeinander gestapelt werden können. Die Angabe hat keinen steuernden Charakter und dient nur zu Informationszwecken.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getStackabilityFactor();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_StackabilityFactor = new ModelColumn<>(I_M_HU_PackingMaterial.class, "StackabilityFactor", null);
	String COLUMNNAME_StackabilityFactor = "StackabilityFactor";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_PackingMaterial.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Breite.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWidth (@Nullable BigDecimal Width);

	/**
	 * Get Breite.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getWidth();

	ModelColumn<I_M_HU_PackingMaterial, Object> COLUMN_Width = new ModelColumn<>(I_M_HU_PackingMaterial.class, "Width", null);
	String COLUMNNAME_Width = "Width";
}
