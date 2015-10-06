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

/** Generated Interface for C_IncidentLineFact
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_IncidentLineFact 
{

    /** TableName=C_IncidentLineFact */
    public static final String Table_Name = "C_IncidentLineFact";

    /** AD_Table_ID=540181 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get AD_Client_ID.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set AD_Org_ID.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get AD_Org_ID.
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

    /** Column name C_AdvCommissionFactCand_ID */
    public static final String COLUMNNAME_C_AdvCommissionFactCand_ID = "C_AdvCommissionFactCand_ID";

	/** Set Provisionsdaten-Wartschlange	  */
	public void setC_AdvCommissionFactCand_ID (int C_AdvCommissionFactCand_ID);

	/** Get Provisionsdaten-Wartschlange	  */
	public int getC_AdvCommissionFactCand_ID();

	public de.metas.commission.model.I_C_AdvCommissionFactCand getC_AdvCommissionFactCand() throws RuntimeException;

    /** Column name C_AdvCommissionRelevantPO_ID */
    public static final String COLUMNNAME_C_AdvCommissionRelevantPO_ID = "C_AdvCommissionRelevantPO_ID";

	/** Set C_AdvCommissionRelevantPO_ID	  */
	public void setC_AdvCommissionRelevantPO_ID (int C_AdvCommissionRelevantPO_ID);

	/** Get C_AdvCommissionRelevantPO_ID	  */
	public int getC_AdvCommissionRelevantPO_ID();

	public de.metas.commission.model.I_C_AdvCommissionRelevantPO getC_AdvCommissionRelevantPO() throws RuntimeException;

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set C_BPartner_ID.
	  * Bezeichnet einen Geschäftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get C_BPartner_ID.
	  * Bezeichnet einen Geschäftspartner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_IncidentLineFact_ID */
    public static final String COLUMNNAME_C_IncidentLineFact_ID = "C_IncidentLineFact_ID";

	/** Set C_IncidentLineFact_ID	  */
	public void setC_IncidentLineFact_ID (int C_IncidentLineFact_ID);

	/** Get C_IncidentLineFact_ID	  */
	public int getC_IncidentLineFact_ID();

    /** Column name C_IncidentLine_ID */
    public static final String COLUMNNAME_C_IncidentLine_ID = "C_IncidentLine_ID";

	/** Set C_IncidentLine_ID	  */
	public void setC_IncidentLine_ID (int C_IncidentLine_ID);

	/** Get C_IncidentLine_ID	  */
	public int getC_IncidentLine_ID();

	public de.metas.commission.model.I_C_IncidentLine getC_IncidentLine() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get CreatedBy.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

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

	/** Set IsActive.
	  * Der Eintrag ist im System aktiv
	  */
	public void setIsActive (boolean IsActive);

	/** Get IsActive.
	  * Der Eintrag ist im System aktiv
	  */
	public boolean isActive();

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

	/** Get Updated.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get UpdatedBy.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
