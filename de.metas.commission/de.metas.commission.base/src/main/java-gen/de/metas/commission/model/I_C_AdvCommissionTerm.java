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
package de.metas.commission.model;


/** Generated Interface for C_AdvCommissionTerm
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_AdvCommissionTerm 
{

    /** TableName=C_AdvCommissionTerm */
    public static final String Table_Name = "C_AdvCommissionTerm";

    /** AD_Table_ID=540076 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant fð² ¤iese Installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column name C_AdvCommissionCondition_ID */
    public static final String COLUMNNAME_C_AdvCommissionCondition_ID = "C_AdvCommissionCondition_ID";

	/** Set Provisionsvertrag	  */
	public void setC_AdvCommissionCondition_ID (int C_AdvCommissionCondition_ID);

	/** Get Provisionsvertrag	  */
	public int getC_AdvCommissionCondition_ID();

	public de.metas.commission.model.I_C_AdvCommissionCondition getC_AdvCommissionCondition() throws RuntimeException;

	public void setC_AdvCommissionCondition(de.metas.commission.model.I_C_AdvCommissionCondition C_AdvCommissionCondition);

    /** Column name C_AdvCommissionTerm_ID */
    public static final String COLUMNNAME_C_AdvCommissionTerm_ID = "C_AdvCommissionTerm_ID";

	/** Set Provisionsart	  */
	public void setC_AdvCommissionTerm_ID (int C_AdvCommissionTerm_ID);

	/** Get Provisionsart	  */
	public int getC_AdvCommissionTerm_ID();

    /** Column name C_AdvComSystem_ID */
    public static final String COLUMNNAME_C_AdvComSystem_ID = "C_AdvComSystem_ID";

	/** Set Vergütungsplan	  */
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID);

	/** Get Vergütungsplan	  */
	public int getC_AdvComSystem_ID();

	public de.metas.commission.model.I_C_AdvComSystem getC_AdvComSystem() throws RuntimeException;

	public void setC_AdvComSystem(de.metas.commission.model.I_C_AdvComSystem C_AdvComSystem);

    /** Column name C_AdvComSystem_Type_ID */
    public static final String COLUMNNAME_C_AdvComSystem_Type_ID = "C_AdvComSystem_Type_ID";

	/** Set Vergütungsplan - Provisionsart	  */
	public void setC_AdvComSystem_Type_ID (int C_AdvComSystem_Type_ID);

	/** Get Vergütungsplan - Provisionsart	  */
	public int getC_AdvComSystem_Type_ID();

	public de.metas.commission.model.I_C_AdvComSystem_Type getC_AdvComSystem_Type() throws RuntimeException;

	public void setC_AdvComSystem_Type(de.metas.commission.model.I_C_AdvComSystem_Type C_AdvComSystem_Type);

    /** Column name C_AdvComTermSRFCollector_ID */
    public static final String COLUMNNAME_C_AdvComTermSRFCollector_ID = "C_AdvComTermSRFCollector_ID";

	/** Set VP-Datensammler.
	  * Referenz auf Provisionsart, die VP (Sponsor) Daten bereit hält (z.B. Rang, Kompression)
	  */
	public void setC_AdvComTermSRFCollector_ID (int C_AdvComTermSRFCollector_ID);

	/** Get VP-Datensammler.
	  * Referenz auf Provisionsart, die VP (Sponsor) Daten bereit hält (z.B. Rang, Kompression)
	  */
	public int getC_AdvComTermSRFCollector_ID();

	public de.metas.commission.model.I_C_AdvCommissionTerm getC_AdvComTermSRFCollector() throws RuntimeException;

	public void setC_AdvComTermSRFCollector(de.metas.commission.model.I_C_AdvCommissionTerm C_AdvComTermSRFCollector);

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public java.sql.Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column name HR_Concept_ID */
    public static final String COLUMNNAME_HR_Concept_ID = "HR_Concept_ID";

	/** Set Abrechnungsposten	  */
	public void setHR_Concept_ID (int HR_Concept_ID);

	/** Get Abrechnungsposten	  */
	public int getHR_Concept_ID();

	public org.eevolution.model.I_HR_Concept getHR_Concept() throws RuntimeException;

	public void setHR_Concept(org.eevolution.model.I_HR_Concept HR_Concept);

    /** Column name InternalName */
    public static final String COLUMNNAME_InternalName = "InternalName";

	/** Set Interner Name.
	  * Eindeutiger system-interner Bezeichner des Datensatzes.
	  */
	public void setInternalName (java.lang.String InternalName);

	/** Get Interner Name.
	  * Eindeutiger system-interner Bezeichner des Datensatzes.
	  */
	public java.lang.String getInternalName();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public boolean isActive();

    /** Column name IsSalesRepFactCollector */
    public static final String COLUMNNAME_IsSalesRepFactCollector = "IsSalesRepFactCollector";

	/** Set Sammelt VP-Daten.
	  * Provisionsart schreibt keine Daten in den Buchauszug, sonder erstellt Sponsor-bezogene Daten wie z.B. APV
	  */
	public void setIsSalesRepFactCollector (boolean IsSalesRepFactCollector);

	/** Get Sammelt VP-Daten.
	  * Provisionsart schreibt keine Daten in den Buchauszug, sonder erstellt Sponsor-bezogene Daten wie z.B. APV
	  */
	public boolean isSalesRepFactCollector();

    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/** Set Produkt-Kategorie.
	  * Kategorie eines Produktes
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/** Get Produkt-Kategorie.
	  * Kategorie eines Produktes
	  */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException;

	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category);

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Produkt.
	  * Produkt, Leistung, Artikel
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Produkt.
	  * Produkt, Leistung, Artikel
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (java.lang.String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public java.lang.String getName();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Reihenfolge.
	  * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	  */
	public void setSeqNo (int SeqNo);

	/** Get Reihenfolge.
	  * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	  */
	public int getSeqNo();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set Gð¬´©g ab.
	  * Gð¬´©g ab inklusiv (erster Tag)
	  */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/** Get Gð¬´©g ab.
	  * Gð¬´©g ab inklusiv (erster Tag)
	  */
	public java.sql.Timestamp getValidFrom();
}
