/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package de.metas.inout.model;


/** Generated Interface for M_Material_Balance_Detail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Material_Balance_Detail 
{

    /** TableName=M_Material_Balance_Detail */
    public static final String Table_Name = "M_Material_Balance_Detail";

    /** AD_Table_ID=540674 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_AD_Client>(I_M_Material_Balance_Detail.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_AD_Org>(I_M_Material_Balance_Detail.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_C_BPartner>(I_M_Material_Balance_Detail.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_C_DocType>(I_M_Material_Balance_Detail.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Periode.
	 * Periode des Kalenders
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Periode.
	 * Periode des Kalenders
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period();

	public void setC_Period(org.compiere.model.I_C_Period C_Period);

    /** Column definition for C_Period_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_C_Period>(I_M_Material_Balance_Detail.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object>(I_M_Material_Balance_Detail.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_AD_User>(I_M_Material_Balance_Detail.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_C_UOM>(I_M_Material_Balance_Detail.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object>(I_M_Material_Balance_Detail.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object>(I_M_Material_Balance_Detail.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsForFlatrate.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsForFlatrate (boolean IsForFlatrate);

	/**
	 * Get IsForFlatrate.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isForFlatrate();

    /** Column definition for IsForFlatrate */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object> COLUMN_IsForFlatrate = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object>(I_M_Material_Balance_Detail.class, "IsForFlatrate", null);
    /** Column name IsForFlatrate */
    public static final String COLUMNNAME_IsForFlatrate = "IsForFlatrate";

	/**
	 * Set IsReset.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReset (boolean IsReset);

	/**
	 * Get IsReset.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReset();

    /** Column definition for IsReset */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object> COLUMN_IsReset = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object>(I_M_Material_Balance_Detail.class, "IsReset", null);
    /** Column name IsReset */
    public static final String COLUMNNAME_IsReset = "IsReset";

	/**
	 * Set Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Lieferung/Wareneingang.
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_M_InOut>(I_M_Material_Balance_Detail.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_M_InOutLine>(I_M_Material_Balance_Detail.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set M_Material_Balance_Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Material_Balance_Config_ID (int M_Material_Balance_Config_ID);

	/**
	 * Get M_Material_Balance_Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Material_Balance_Config_ID();

	public de.metas.inout.model.I_M_Material_Balance_Config getM_Material_Balance_Config();

	public void setM_Material_Balance_Config(de.metas.inout.model.I_M_Material_Balance_Config M_Material_Balance_Config);

    /** Column definition for M_Material_Balance_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, de.metas.inout.model.I_M_Material_Balance_Config> COLUMN_M_Material_Balance_Config_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, de.metas.inout.model.I_M_Material_Balance_Config>(I_M_Material_Balance_Detail.class, "M_Material_Balance_Config_ID", de.metas.inout.model.I_M_Material_Balance_Config.class);
    /** Column name M_Material_Balance_Config_ID */
    public static final String COLUMNNAME_M_Material_Balance_Config_ID = "M_Material_Balance_Config_ID";

	/**
	 * Set M_Material_Balance_Detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Material_Balance_Detail_ID (int M_Material_Balance_Detail_ID);

	/**
	 * Get M_Material_Balance_Detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Material_Balance_Detail_ID();

    /** Column definition for M_Material_Balance_Detail_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object> COLUMN_M_Material_Balance_Detail_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object>(I_M_Material_Balance_Detail.class, "M_Material_Balance_Detail_ID", null);
    /** Column name M_Material_Balance_Detail_ID */
    public static final String COLUMNNAME_M_Material_Balance_Detail_ID = "M_Material_Balance_Detail_ID";

	/**
	 * Set Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getMovementDate();

    /** Column definition for MovementDate */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object> COLUMN_MovementDate = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object>(I_M_Material_Balance_Detail.class, "MovementDate", null);
    /** Column name MovementDate */
    public static final String COLUMNNAME_MovementDate = "MovementDate";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_M_Product>(I_M_Material_Balance_Detail.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set QtyIncoming.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyIncoming (java.math.BigDecimal QtyIncoming);

	/**
	 * Get QtyIncoming.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyIncoming();

    /** Column definition for QtyIncoming */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object> COLUMN_QtyIncoming = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object>(I_M_Material_Balance_Detail.class, "QtyIncoming", null);
    /** Column name QtyIncoming */
    public static final String COLUMNNAME_QtyIncoming = "QtyIncoming";

	/**
	 * Set QtyOutgoing.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOutgoing (java.math.BigDecimal QtyOutgoing);

	/**
	 * Get QtyOutgoing.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOutgoing();

    /** Column definition for QtyOutgoing */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object> COLUMN_QtyOutgoing = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object>(I_M_Material_Balance_Detail.class, "QtyOutgoing", null);
    /** Column name QtyOutgoing */
    public static final String COLUMNNAME_QtyOutgoing = "QtyOutgoing";

	/**
	 * Set ResetDate.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResetDate (java.sql.Timestamp ResetDate);

	/**
	 * Get ResetDate.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getResetDate();

    /** Column definition for ResetDate */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object> COLUMN_ResetDate = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object>(I_M_Material_Balance_Detail.class, "ResetDate", null);
    /** Column name ResetDate */
    public static final String COLUMNNAME_ResetDate = "ResetDate";

	/**
	 * Set ResetDateEffective.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResetDateEffective (java.sql.Timestamp ResetDateEffective);

	/**
	 * Get ResetDateEffective.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getResetDateEffective();

    /** Column definition for ResetDateEffective */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object> COLUMN_ResetDateEffective = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object>(I_M_Material_Balance_Detail.class, "ResetDateEffective", null);
    /** Column name ResetDateEffective */
    public static final String COLUMNNAME_ResetDateEffective = "ResetDateEffective";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, Object>(I_M_Material_Balance_Detail.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Material_Balance_Detail, org.compiere.model.I_AD_User>(I_M_Material_Balance_Detail.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
