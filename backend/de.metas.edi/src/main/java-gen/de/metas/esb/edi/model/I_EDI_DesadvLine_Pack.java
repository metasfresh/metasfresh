package de.metas.esb.edi.model;


/** Generated Interface for EDI_DesadvLine_Pack
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_DesadvLine_Pack 
{

    /** TableName=EDI_DesadvLine_Pack */
    public static final String Table_Name = "EDI_DesadvLine_Pack";

    /** AD_Table_ID=540676 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Best before date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBestBeforeDate (java.sql.Timestamp BestBeforeDate);

	/**
	 * Get Best before date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getBestBeforeDate();

    /** Column definition for BestBeforeDate */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_BestBeforeDate = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "BestBeforeDate", null);
    /** Column name BestBeforeDate */
    public static final String COLUMNNAME_BestBeforeDate = "BestBeforeDate";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_Desadv_ID (int EDI_Desadv_ID);

	/**
	 * Get DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEDI_Desadv_ID();

	public de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv();

	public void setEDI_Desadv(de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv);

    /** Column definition for EDI_Desadv_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, de.metas.esb.edi.model.I_EDI_Desadv> COLUMN_EDI_Desadv_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, de.metas.esb.edi.model.I_EDI_Desadv>(I_EDI_DesadvLine_Pack.class, "EDI_Desadv_ID", de.metas.esb.edi.model.I_EDI_Desadv.class);
    /** Column name EDI_Desadv_ID */
    public static final String COLUMNNAME_EDI_Desadv_ID = "EDI_Desadv_ID";

	/**
	 * Set DESADV Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_DesadvLine_ID (int EDI_DesadvLine_ID);

	/**
	 * Get DESADV Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEDI_DesadvLine_ID();

	public de.metas.esb.edi.model.I_EDI_DesadvLine getEDI_DesadvLine();

	public void setEDI_DesadvLine(de.metas.esb.edi.model.I_EDI_DesadvLine EDI_DesadvLine);

    /** Column definition for EDI_DesadvLine_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, de.metas.esb.edi.model.I_EDI_DesadvLine> COLUMN_EDI_DesadvLine_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, de.metas.esb.edi.model.I_EDI_DesadvLine>(I_EDI_DesadvLine_Pack.class, "EDI_DesadvLine_ID", de.metas.esb.edi.model.I_EDI_DesadvLine.class);
    /** Column name EDI_DesadvLine_ID */
    public static final String COLUMNNAME_EDI_DesadvLine_ID = "EDI_DesadvLine_ID";

	/**
	 * Set EDI Lieferavis Pack (DESADV).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_DesadvLine_Pack_ID (int EDI_DesadvLine_Pack_ID);

	/**
	 * Get EDI Lieferavis Pack (DESADV).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEDI_DesadvLine_Pack_ID();

    /** Column definition for EDI_DesadvLine_Pack_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_EDI_DesadvLine_Pack_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "EDI_DesadvLine_Pack_ID", null);
    /** Column name EDI_DesadvLine_Pack_ID */
    public static final String COLUMNNAME_EDI_DesadvLine_Pack_ID = "EDI_DesadvLine_Pack_ID";

	/**
	 * Set SSCC18.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIPA_SSCC18 (java.lang.String IPA_SSCC18);

	/**
	 * Get SSCC18.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIPA_SSCC18();

    /** Column definition for IPA_SSCC18 */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_IPA_SSCC18 = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "IPA_SSCC18", null);
    /** Column name IPA_SSCC18 */
    public static final String COLUMNNAME_IPA_SSCC18 = "IPA_SSCC18";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set manual SSCC18.
	 * metasfresh ticks this flag if there is no HU assigned and the user can manually provide a SSCC18 value.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManual_IPA_SSCC18 (boolean IsManual_IPA_SSCC18);

	/**
	 * Get manual SSCC18.
	 * metasfresh ticks this flag if there is no HU assigned and the user can manually provide a SSCC18 value.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManual_IPA_SSCC18();

    /** Column definition for IsManual_IPA_SSCC18 */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_IsManual_IPA_SSCC18 = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "IsManual_IPA_SSCC18", null);
    /** Column name IsManual_IPA_SSCC18 */
    public static final String COLUMNNAME_IsManual_IPA_SSCC18 = "IsManual_IPA_SSCC18";

	/**
	 * Set Lot number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLotNumber (java.lang.String LotNumber);

	/**
	 * Get Lot number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLotNumber();

    /** Column definition for LotNumber */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_LotNumber = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "LotNumber", null);
    /** Column name LotNumber */
    public static final String COLUMNNAME_LotNumber = "LotNumber";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_ID();

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "M_HU_ID", null);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set LU packaging code.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PackagingCode_LU_ID (int M_HU_PackagingCode_LU_ID);

	/**
	 * Get LU packaging code.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PackagingCode_LU_ID();

    /** Column definition for M_HU_PackagingCode_LU_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_M_HU_PackagingCode_LU_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "M_HU_PackagingCode_LU_ID", null);
    /** Column name M_HU_PackagingCode_LU_ID */
    public static final String COLUMNNAME_M_HU_PackagingCode_LU_ID = "M_HU_PackagingCode_LU_ID";

	/**
	 * Set M_HU_PackagingCode_LU_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setM_HU_PackagingCode_LU_Text (java.lang.String M_HU_PackagingCode_LU_Text);

	/**
	 * Get M_HU_PackagingCode_LU_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getM_HU_PackagingCode_LU_Text();

    /** Column definition for M_HU_PackagingCode_LU_Text */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_M_HU_PackagingCode_LU_Text = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "M_HU_PackagingCode_LU_Text", null);
    /** Column name M_HU_PackagingCode_LU_Text */
    public static final String COLUMNNAME_M_HU_PackagingCode_LU_Text = "M_HU_PackagingCode_LU_Text";

	/**
	 * Set TU packaging code.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PackagingCode_TU_ID (int M_HU_PackagingCode_TU_ID);

	/**
	 * Get TU packaging code.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PackagingCode_TU_ID();

    /** Column definition for M_HU_PackagingCode_TU_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_M_HU_PackagingCode_TU_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "M_HU_PackagingCode_TU_ID", null);
    /** Column name M_HU_PackagingCode_TU_ID */
    public static final String COLUMNNAME_M_HU_PackagingCode_TU_ID = "M_HU_PackagingCode_TU_ID";

	/**
	 * Set M_HU_PackagingCode_TU_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setM_HU_PackagingCode_TU_Text (java.lang.String M_HU_PackagingCode_TU_Text);

	/**
	 * Get M_HU_PackagingCode_TU_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getM_HU_PackagingCode_TU_Text();

    /** Column definition for M_HU_PackagingCode_TU_Text */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_M_HU_PackagingCode_TU_Text = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "M_HU_PackagingCode_TU_Text", null);
    /** Column name M_HU_PackagingCode_TU_Text */
    public static final String COLUMNNAME_M_HU_PackagingCode_TU_Text = "M_HU_PackagingCode_TU_Text";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, org.compiere.model.I_M_InOut>(I_EDI_DesadvLine_Pack.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, org.compiere.model.I_M_InOutLine>(I_EDI_DesadvLine_Pack.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMovementQty (java.math.BigDecimal MovementQty);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getMovementQty();

    /** Column definition for MovementQty */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_MovementQty = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "MovementQty", null);
    /** Column name MovementQty */
    public static final String COLUMNNAME_MovementQty = "MovementQty";

	/**
	 * Set Qty CU per TU.
	 * Number of CUs per package (usually TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyCU (java.math.BigDecimal QtyCU);

	/**
	 * Get Qty CU per TU.
	 * Number of CUs per package (usually TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyCU();

    /** Column definition for QtyCU */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_QtyCU = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "QtyCU", null);
    /** Column name QtyCU */
    public static final String COLUMNNAME_QtyCU = "QtyCU";

	/**
	 * Set Menge CU/LU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyCUsPerLU (java.math.BigDecimal QtyCUsPerLU);

	/**
	 * Get Menge CU/LU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyCUsPerLU();

    /** Column definition for QtyCUsPerLU */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_QtyCUsPerLU = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "QtyCUsPerLU", null);
    /** Column name QtyCUsPerLU */
    public static final String COLUMNNAME_QtyCUsPerLU = "QtyCUsPerLU";

	/**
	 * Set Packaging capacity.
	 * Capacity in the respective product's unit of measuerement
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyItemCapacity (java.math.BigDecimal QtyItemCapacity);

	/**
	 * Get Packaging capacity.
	 * Capacity in the respective product's unit of measuerement
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyItemCapacity();

    /** Column definition for QtyItemCapacity */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_QtyItemCapacity = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "QtyItemCapacity", null);
    /** Column name QtyItemCapacity */
    public static final String COLUMNNAME_QtyItemCapacity = "QtyItemCapacity";

	/**
	 * Set Number of TUs.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyTU (int QtyTU);

	/**
	 * Get Number of TUs.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getQtyTU();

    /** Column definition for QtyTU */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_QtyTU = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "QtyTU", null);
    /** Column name QtyTU */
    public static final String COLUMNNAME_QtyTU = "QtyTU";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_Pack, Object>(I_EDI_DesadvLine_Pack.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
