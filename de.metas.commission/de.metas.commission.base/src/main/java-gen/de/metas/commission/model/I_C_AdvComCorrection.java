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

/** Generated Interface for C_AdvComCorrection
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvComCorrection 
{

    /** TableName=C_AdvComCorrection */
    public static final String Table_Name = "C_AdvComCorrection";

    /** AD_Table_ID=540166 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name Action */
    public static final String COLUMNNAME_Action = "Action";

	/** Set Aktion.
	  * Zeigt die durchzuführende Aktion an
	  */
	public void setAction (String Action);

	/** Get Aktion.
	  * Zeigt die durchzuführende Aktion an
	  */
	public String getAction();

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

    /** Column name C_AdvComCorrection_ID */
    public static final String COLUMNNAME_C_AdvComCorrection_ID = "C_AdvComCorrection_ID";

	/** Set Provisions-Korrektursatz	  */
	public void setC_AdvComCorrection_ID (int C_AdvComCorrection_ID);

	/** Get Provisions-Korrektursatz	  */
	public int getC_AdvComCorrection_ID();

    /** Column name Commission */
    public static final String COLUMNNAME_Commission = "Commission";

	/** Set Prov. %.
	  * Commission stated as a percentage
	  */
	public void setCommission (BigDecimal Commission);

	/** Get Prov. %.
	  * Commission stated as a percentage
	  */
	public BigDecimal getCommission();

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

    /** Column name IsTrigger */
    public static final String COLUMNNAME_IsTrigger = "IsTrigger";

	/** Set Auslöser	  */
	public void setIsTrigger (boolean IsTrigger);

	/** Get Auslöser	  */
	public boolean isTrigger();

    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/** Set Notiz.
	  * Optional weitere Information für ein Dokument
	  */
	public void setNote (String Note);

	/** Get Notiz.
	  * Optional weitere Information für ein Dokument
	  */
	public String getNote();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Verarbeitet.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Verarbeitet.
	  * The document has been processed
	  */
	public boolean isProcessed();

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
