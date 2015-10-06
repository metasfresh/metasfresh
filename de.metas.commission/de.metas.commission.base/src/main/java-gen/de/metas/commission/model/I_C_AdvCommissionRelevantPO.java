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

/** Generated Interface for C_AdvCommissionRelevantPO
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvCommissionRelevantPO 
{

    /** TableName=C_AdvCommissionRelevantPO */
    public static final String Table_Name = "C_AdvCommissionRelevantPO";

    /** AD_Table_ID=540073 */
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

    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/** Set Referenz.
	  * Systemreferenz und Validierung
	  */
	public void setAD_Reference_ID (int AD_Reference_ID);

	/** Get Referenz.
	  * Systemreferenz und Validierung
	  */
	public int getAD_Reference_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException;

    /** Column name BPartnerColumn_ID */
    public static final String COLUMNNAME_BPartnerColumn_ID = "BPartnerColumn_ID";

	/** Set Geschäftspartner-Spalte.
	  * Spalte (der Referenz-Tabelle), aus der der Geschäftspartner entnommen werden soll
	  */
	public void setBPartnerColumn_ID (int BPartnerColumn_ID);

	/** Get Geschäftspartner-Spalte.
	  * Spalte (der Referenz-Tabelle), aus der der Geschäftspartner entnommen werden soll
	  */
	public int getBPartnerColumn_ID();

	public org.compiere.model.I_AD_Column getBPartnerColumn() throws RuntimeException;

    /** Column name C_AdvCommissionRelevantPO_ID */
    public static final String COLUMNNAME_C_AdvCommissionRelevantPO_ID = "C_AdvCommissionRelevantPO_ID";

	/** Set Def. relevanter Datensatz	  */
	public void setC_AdvCommissionRelevantPO_ID (int C_AdvCommissionRelevantPO_ID);

	/** Get Def. relevanter Datensatz	  */
	public int getC_AdvCommissionRelevantPO_ID();

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

    /** Column name DateDocColumn_ID */
    public static final String COLUMNNAME_DateDocColumn_ID = "DateDocColumn_ID";

	/** Set DateDocColumn_ID.
	  * Spalte (der Referenz-Tabelle), aus der das Buchungdatum entnommen werden soll.
	  */
	public void setDateDocColumn_ID (int DateDocColumn_ID);

	/** Get DateDocColumn_ID.
	  * Spalte (der Referenz-Tabelle), aus der das Buchungdatum entnommen werden soll.
	  */
	public int getDateDocColumn_ID();

	public org.compiere.model.I_AD_Column getDateDocColumn() throws RuntimeException;

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Beschreibung.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Info */
    public static final String COLUMNNAME_Info = "Info";

	/** Set Info.
	  * Information
	  */
	public void setInfo (boolean Info);

	/** Get Info.
	  * Information
	  */
	public boolean isInfo();

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
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
