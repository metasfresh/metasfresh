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

/** Generated Interface for C_AdvComRelevantPO_Type
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvComRelevantPO_Type 
{

    /** TableName=C_AdvComRelevantPO_Type */
    public static final String Table_Name = "C_AdvComRelevantPO_Type";

    /** AD_Table_ID=540158 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

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

    /** Column name C_AdvCommissionRelevantPO_ID */
    public static final String COLUMNNAME_C_AdvCommissionRelevantPO_ID = "C_AdvCommissionRelevantPO_ID";

	/** Set Def. relevanter Datensatz	  */
	public void setC_AdvCommissionRelevantPO_ID (int C_AdvCommissionRelevantPO_ID);

	/** Get Def. relevanter Datensatz	  */
	public int getC_AdvCommissionRelevantPO_ID();

	public de.metas.commission.model.I_C_AdvCommissionRelevantPO getC_AdvCommissionRelevantPO() throws RuntimeException;

    /** Column name C_AdvComRelevantPO_Type_ID */
    public static final String COLUMNNAME_C_AdvComRelevantPO_Type_ID = "C_AdvComRelevantPO_Type_ID";

	/** Set C_AdvComRelevantPO_Type	  */
	public void setC_AdvComRelevantPO_Type_ID (int C_AdvComRelevantPO_Type_ID);

	/** Get C_AdvComRelevantPO_Type	  */
	public int getC_AdvComRelevantPO_Type_ID();

    /** Column name C_AdvComSystem_ID */
    public static final String COLUMNNAME_C_AdvComSystem_ID = "C_AdvComSystem_ID";

	/** Set Vergütungsplan	  */
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID);

	/** Get Vergütungsplan	  */
	public int getC_AdvComSystem_ID();

	public de.metas.commission.model.I_C_AdvComSystem getC_AdvComSystem() throws RuntimeException;

    /** Column name C_AdvComSystem_Type_ID */
    public static final String COLUMNNAME_C_AdvComSystem_Type_ID = "C_AdvComSystem_Type_ID";

	/** Set Vergütungsplan - Provisionsart	  */
	public void setC_AdvComSystem_Type_ID (int C_AdvComSystem_Type_ID);

	/** Get Vergütungsplan - Provisionsart	  */
	public int getC_AdvComSystem_Type_ID();

	public de.metas.commission.model.I_C_AdvComSystem_Type getC_AdvComSystem_Type() throws RuntimeException;

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

    /** Column name IsProcessImmediately */
    public static final String COLUMNNAME_IsProcessImmediately = "IsProcessImmediately";

	/** Set Sofort verarbeiten	  */
	public void setIsProcessImmediately (boolean IsProcessImmediately);

	/** Get Sofort verarbeiten	  */
	public boolean isProcessImmediately();

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
