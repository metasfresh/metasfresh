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

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for P_AdvCommissionDetails
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_P_AdvCommissionDetails 
{

    /** TableName=P_AdvCommissionDetails */
    public static final String Table_Name = "P_AdvCommissionDetails";

    /** AD_Table_ID=540183 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

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

    /** Column name C_AdvCommissionInstance_ID */
    public static final String COLUMNNAME_C_AdvCommissionInstance_ID = "C_AdvCommissionInstance_ID";

	/** Set Provisionsvorgang	  */
	public void setC_AdvCommissionInstance_ID (int C_AdvCommissionInstance_ID);

	/** Get Provisionsvorgang	  */
	public int getC_AdvCommissionInstance_ID();

	public de.metas.commission.model.I_C_AdvCommissionInstance getC_AdvCommissionInstance() throws RuntimeException;

    /** Column name C_AdvCommissionTerm_ID */
    public static final String COLUMNNAME_C_AdvCommissionTerm_ID = "C_AdvCommissionTerm_ID";

	/** Set Provisionsart	  */
	public void setC_AdvCommissionTerm_ID (int C_AdvCommissionTerm_ID);

	/** Get Provisionsart	  */
	public int getC_AdvCommissionTerm_ID();

	public de.metas.commission.model.I_C_AdvCommissionTerm getC_AdvCommissionTerm() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Belegart.
	  * Belegart oder Verarbeitungsvorgaben
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Belegart.
	  * Belegart oder Verarbeitungsvorgaben
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name commission_ca */
    public static final String COLUMNNAME_commission_ca = "commission_ca";

	/** Set Prozent Berechnung	  */
	public void setcommission_ca (BigDecimal commission_ca);

	/** Get Prozent Berechnung	  */
	public BigDecimal getcommission_ca();

    /** Column name CommissionPoints_CA */
    public static final String COLUMNNAME_CommissionPoints_CA = "CommissionPoints_CA";

	/** Set Punkte Berechnung	  */
	public void setCommissionPoints_CA (BigDecimal CommissionPoints_CA);

	/** Get Punkte Berechnung	  */
	public BigDecimal getCommissionPoints_CA();

    /** Column name CommissionPoints_PR */
    public static final String COLUMNNAME_CommissionPoints_PR = "CommissionPoints_PR";

	/** Set Punkte Prognose	  */
	public void setCommissionPoints_PR (BigDecimal CommissionPoints_PR);

	/** Get Punkte Prognose	  */
	public BigDecimal getCommissionPoints_PR();

    /** Column name CommissionPointsSum_CA */
    public static final String COLUMNNAME_CommissionPointsSum_CA = "CommissionPointsSum_CA";

	/** Set Punkte Summe Berechnung	  */
	public void setCommissionPointsSum_CA (BigDecimal CommissionPointsSum_CA);

	/** Get Punkte Summe Berechnung	  */
	public BigDecimal getCommissionPointsSum_CA();

    /** Column name CommissionPointsSum_PR */
    public static final String COLUMNNAME_CommissionPointsSum_PR = "CommissionPointsSum_PR";

	/** Set Punkte Summe Prognose	  */
	public void setCommissionPointsSum_PR (BigDecimal CommissionPointsSum_PR);

	/** Get Punkte Summe Prognose	  */
	public BigDecimal getCommissionPointsSum_PR();

    /** Column name commission_pr */
    public static final String COLUMNNAME_commission_pr = "commission_pr";

	/** Set Prozent Prognose	  */
	public void setcommission_pr (BigDecimal commission_pr);

	/** Get Prozent Prognose	  */
	public BigDecimal getcommission_pr();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column name C_Sponsor_ID */
    public static final String COLUMNNAME_C_Sponsor_ID = "C_Sponsor_ID";

	/** Set Sponsor	  */
	public void setC_Sponsor_ID (int C_Sponsor_ID);

	/** Get Sponsor	  */
	public int getC_Sponsor_ID();

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor() throws RuntimeException;

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Belegdatum.
	  * Datum des Belegs
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Belegdatum.
	  * Datum des Belegs
	  */
	public Timestamp getDateDoc();

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

    /** Column name kundenname */
    public static final String COLUMNNAME_kundenname = "kundenname";

	/** Set Kunde	  */
	public void setkundenname (String kundenname);

	/** Get Kunde	  */
	public String getkundenname();

    /** Column name kundennr */
    public static final String COLUMNNAME_kundennr = "kundennr";

	/** Set Kundennr.	  */
	public void setkundennr (String kundennr);

	/** Get Kundennr.	  */
	public String getkundennr();

    /** Column name LevelCalculation */
    public static final String COLUMNNAME_LevelCalculation = "LevelCalculation";

	/** Set Ebene Berechnung	  */
	public void setLevelCalculation (int LevelCalculation);

	/** Get Ebene Berechnung	  */
	public int getLevelCalculation();

    /** Column name LevelForecast */
    public static final String COLUMNNAME_LevelForecast = "LevelForecast";

	/** Set Ebene Prognose	  */
	public void setLevelForecast (int LevelForecast);

	/** Get Ebene Prognose	  */
	public int getLevelForecast();

    /** Column name LevelHierarchy */
    public static final String COLUMNNAME_LevelHierarchy = "LevelHierarchy";

	/** Set Ebene Hierarchie	  */
	public void setLevelHierarchy (int LevelHierarchy);

	/** Get Ebene Hierarchie	  */
	public int getLevelHierarchy();

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Zeile Nr..
	  * Einzelne Zeile in dem Dokument
	  */
	public void setLine (BigDecimal Line);

	/** Get Zeile Nr..
	  * Einzelne Zeile in dem Dokument
	  */
	public BigDecimal getLine();

    /** Column name month */
    public static final String COLUMNNAME_month = "month";

	/** Set month	  */
	public void setmonth (BigDecimal month);

	/** Get month	  */
	public BigDecimal getmonth();

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

    /** Column name Product */
    public static final String COLUMNNAME_Product = "Product";

	/** Set Produkt	  */
	public void setProduct (String Product);

	/** Get Produkt	  */
	public String getProduct();

    /** Column name provisionsart */
    public static final String COLUMNNAME_provisionsart = "provisionsart";

	/** Set Provisionsart	  */
	public void setprovisionsart (String provisionsart);

	/** Get Provisionsart	  */
	public String getprovisionsart();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Menge.
	  * Menge
	  */
	public void setQty (BigDecimal Qty);

	/** Get Menge.
	  * Menge
	  */
	public BigDecimal getQty();

    /** Column name round */
    public static final String COLUMNNAME_round = "round";

	/** Set round	  */
	public void setround (BigDecimal round);

	/** Get round	  */
	public BigDecimal getround();

    /** Column name sponsornr */
    public static final String COLUMNNAME_sponsornr = "sponsornr";

	/** Set Sponsor Nummer	  */
	public void setsponsornr (String sponsornr);

	/** Get Sponsor Nummer	  */
	public String getsponsornr();

    /** Column name stadt */
    public static final String COLUMNNAME_stadt = "stadt";

	/** Set Stadt	  */
	public void setstadt (String stadt);

	/** Get Stadt	  */
	public String getstadt();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();

    /** Column name vorgang */
    public static final String COLUMNNAME_vorgang = "vorgang";

	/** Set Vorgang	  */
	public void setvorgang (String vorgang);

	/** Get Vorgang	  */
	public String getvorgang();

    /** Column name year */
    public static final String COLUMNNAME_year = "year";

	/** Set year	  */
	public void setyear (BigDecimal year);

	/** Get year	  */
	public BigDecimal getyear();
}
