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

/** Generated Interface for C_AdvCommissionInstance
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvCommissionInstance 
{

    /** TableName=C_AdvCommissionInstance */
    public static final String Table_Name = "C_AdvCommissionInstance";

    /** AD_Table_ID=540077 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant fð² ¤iese Installation.
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

    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/** Set Prozess-Instanz.
	  * Instanz eines Prozesses
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID);

	/** Get Prozess-Instanz.
	  * Instanz eines Prozesses
	  */
	public int getAD_PInstance_ID();

	public org.compiere.model.I_AD_PInstance getAD_PInstance() throws RuntimeException;

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set DB-Tabelle.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get DB-Tabelle.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException;

    /** Column name AmtToPay */
    public static final String COLUMNNAME_AmtToPay = "AmtToPay";

	/** Set Betrag auszuzahlen	  */
	public void setAmtToPay (BigDecimal AmtToPay);

	/** Get Betrag auszuzahlen	  */
	public BigDecimal getAmtToPay();

    /** Column name C_AdvCommissionInstance_ID */
    public static final String COLUMNNAME_C_AdvCommissionInstance_ID = "C_AdvCommissionInstance_ID";

	/** Set Provisionsvorgang	  */
	public void setC_AdvCommissionInstance_ID (int C_AdvCommissionInstance_ID);

	/** Get Provisionsvorgang	  */
	public int getC_AdvCommissionInstance_ID();

    /** Column name C_AdvCommissionRelevantPO_ID */
    public static final String COLUMNNAME_C_AdvCommissionRelevantPO_ID = "C_AdvCommissionRelevantPO_ID";

	/** Set Def. relevanter Datensatz	  */
	public void setC_AdvCommissionRelevantPO_ID (int C_AdvCommissionRelevantPO_ID);

	/** Get Def. relevanter Datensatz	  */
	public int getC_AdvCommissionRelevantPO_ID();

	public de.metas.commission.model.I_C_AdvCommissionRelevantPO getC_AdvCommissionRelevantPO() throws RuntimeException;

    /** Column name C_AdvCommissionTerm_ID */
    public static final String COLUMNNAME_C_AdvCommissionTerm_ID = "C_AdvCommissionTerm_ID";

	/** Set Provisionsart	  */
	public void setC_AdvCommissionTerm_ID (int C_AdvCommissionTerm_ID);

	/** Get Provisionsart	  */
	public int getC_AdvCommissionTerm_ID();

	public de.metas.commission.model.I_C_AdvCommissionTerm getC_AdvCommissionTerm() throws RuntimeException;

    /** Column name C_AdvComSystem_Type_ID */
    public static final String COLUMNNAME_C_AdvComSystem_Type_ID = "C_AdvComSystem_Type_ID";

	/** Set Vergütungsplan - Provisionsart	  */
	public void setC_AdvComSystem_Type_ID (int C_AdvComSystem_Type_ID);

	/** Get Vergütungsplan - Provisionsart	  */
	public int getC_AdvComSystem_Type_ID();

	public de.metas.commission.model.I_C_AdvComSystem_Type getC_AdvComSystem_Type() throws RuntimeException;

    /** Column name C_IncidentLine_ID */
    public static final String COLUMNNAME_C_IncidentLine_ID = "C_IncidentLine_ID";

	/** Set C_IncidentLine_ID	  */
	public void setC_IncidentLine_ID (int C_IncidentLine_ID);

	/** Get C_IncidentLine_ID	  */
	public int getC_IncidentLine_ID();

	public de.metas.commission.model.I_C_IncidentLine getC_IncidentLine() throws RuntimeException;

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

    /** Column name C_Sponsor_Customer_ID */
    public static final String COLUMNNAME_C_Sponsor_Customer_ID = "C_Sponsor_Customer_ID";

	/** Set Sponsor-Kunde	  */
	public void setC_Sponsor_Customer_ID (int C_Sponsor_Customer_ID);

	/** Get Sponsor-Kunde	  */
	public int getC_Sponsor_Customer_ID();

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor_Customer() throws RuntimeException;

    /** Column name C_Sponsor_SalesRep_ID */
    public static final String COLUMNNAME_C_Sponsor_SalesRep_ID = "C_Sponsor_SalesRep_ID";

	/** Set Sponsor-Vertriebspartner	  */
	public void setC_Sponsor_SalesRep_ID (int C_Sponsor_SalesRep_ID);

	/** Get Sponsor-Vertriebspartner	  */
	public int getC_Sponsor_SalesRep_ID();

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor_SalesRep() throws RuntimeException;

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

    /** Column name IsClosed */
    public static final String COLUMNNAME_IsClosed = "IsClosed";

	/** Set Geschlossen.
	  * The status is closed
	  */
	public void setIsClosed (boolean IsClosed);

	/** Get Geschlossen.
	  * The status is closed
	  */
	public boolean isClosed();

    /** Column name IsCommissionLock */
    public static final String COLUMNNAME_IsCommissionLock = "IsCommissionLock";

	/** Set Provisionssperre	  */
	public void setIsCommissionLock (boolean IsCommissionLock);

	/** Get Provisionssperre	  */
	public boolean isCommissionLock();

    /** Column name IsVolumeOfSales */
    public static final String COLUMNNAME_IsVolumeOfSales = "IsVolumeOfSales";

	/** Set Verkaufsvolumen.
	  * Gibt an, ob der Datensatz bei der Ermittlung von Rang, Kompression usw. als Verkaufsvolumen zu berücksichtigen ist
	  */
	public void setIsVolumeOfSales (boolean IsVolumeOfSales);

	/** Get Verkaufsvolumen.
	  * Gibt an, ob der Datensatz bei der Ermittlung von Rang, Kompression usw. als Verkaufsvolumen zu berücksichtigen ist
	  */
	public boolean isVolumeOfSales();

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

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Points_Calculated */
    public static final String COLUMNNAME_Points_Calculated = "Points_Calculated";

	/** Set Punkte-Berechnet	  */
	public void setPoints_Calculated (BigDecimal Points_Calculated);

	/** Get Punkte-Berechnet	  */
	public BigDecimal getPoints_Calculated();

    /** Column name Points_Predicted */
    public static final String COLUMNNAME_Points_Predicted = "Points_Predicted";

	/** Set Punkte-Prognostiziert	  */
	public void setPoints_Predicted (BigDecimal Points_Predicted);

	/** Get Punkte-Prognostiziert	  */
	public BigDecimal getPoints_Predicted();

    /** Column name PointsSum_Predicted */
    public static final String COLUMNNAME_PointsSum_Predicted = "PointsSum_Predicted";

	/** Set Punkte-Summe-Prognostiziert	  */
	public void setPointsSum_Predicted (BigDecimal PointsSum_Predicted);

	/** Get Punkte-Summe-Prognostiziert	  */
	public BigDecimal getPointsSum_Predicted();

    /** Column name PointsSum_ToCalculate */
    public static final String COLUMNNAME_PointsSum_ToCalculate = "PointsSum_ToCalculate";

	/** Set Punkte-Summe-Prov.-Relevant	  */
	public void setPointsSum_ToCalculate (BigDecimal PointsSum_ToCalculate);

	/** Get Punkte-Summe-Prov.-Relevant	  */
	public BigDecimal getPointsSum_ToCalculate();

    /** Column name Points_ToCalculate */
    public static final String COLUMNNAME_Points_ToCalculate = "Points_ToCalculate";

	/** Set Punkte-zu berechnen	  */
	public void setPoints_ToCalculate (BigDecimal Points_ToCalculate);

	/** Get Punkte-zu berechnen	  */
	public BigDecimal getPoints_ToCalculate();

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Datensatz-ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Datensatz-ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

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
}
