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

/** Generated Interface for C_AdvComDoc
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvComDoc 
{

    /** TableName=C_AdvComDoc */
    public static final String Table_Name = "C_AdvComDoc";

    /** AD_Table_ID=540288 */
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

    /** Column name C_AdvComDoc_ID */
    public static final String COLUMNNAME_C_AdvComDoc_ID = "C_AdvComDoc_ID";

	/** Set Provisionsauslöser	  */
	public void setC_AdvComDoc_ID (int C_AdvComDoc_ID);

	/** Get Provisionsauslöser	  */
	public int getC_AdvComDoc_ID();

    /** Column name C_AllocationLine_ID */
    public static final String COLUMNNAME_C_AllocationLine_ID = "C_AllocationLine_ID";

	/** Set Zuordnungs-Position.
	  * Zuordnungs-Position
	  */
	public void setC_AllocationLine_ID (int C_AllocationLine_ID);

	/** Get Zuordnungs-Position.
	  * Zuordnungs-Position
	  */
	public int getC_AllocationLine_ID();

	public org.compiere.model.I_C_AllocationLine getC_AllocationLine() throws RuntimeException;

    /** Column name C_DocType_Ref_ID */
    public static final String COLUMNNAME_C_DocType_Ref_ID = "C_DocType_Ref_ID";

	/** Set Belegart	  */
	public void setC_DocType_Ref_ID (int C_DocType_Ref_ID);

	/** Get Belegart	  */
	public int getC_DocType_Ref_ID();

	public org.compiere.model.I_C_DocType getC_DocType_Ref() throws RuntimeException;

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

    /** Column name DateDoc_Ref */
    public static final String COLUMNNAME_DateDoc_Ref = "DateDoc_Ref";

	/** Set Belegdatum	  */
	public void setDateDoc_Ref (Timestamp DateDoc_Ref);

	/** Get Belegdatum	  */
	public Timestamp getDateDoc_Ref();

    /** Column name DateFact */
    public static final String COLUMNNAME_DateFact = "DateFact";

	/** Set Valuta-Datum	  */
	public void setDateFact (Timestamp DateFact);

	/** Get Valuta-Datum	  */
	public Timestamp getDateFact();

    /** Column name DateFact_Override */
    public static final String COLUMNNAME_DateFact_Override = "DateFact_Override";

	/** Set Abw. Valutadatum	  */
	public void setDateFact_Override (Timestamp DateFact_Override);

	/** Get Abw. Valutadatum	  */
	public Timestamp getDateFact_Override();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Belegverarbeitung.
	  * Der zukünftige Status des Belegs
	  */
	public void setDocAction (String DocAction);

	/** Get Belegverarbeitung.
	  * Der zukünftige Status des Belegs
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Belegstatus.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Belegstatus.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo_Ref */
    public static final String COLUMNNAME_DocumentNo_Ref = "DocumentNo_Ref";

	/** Set Belegnummer	  */
	public void setDocumentNo_Ref (String DocumentNo_Ref);

	/** Get Belegnummer	  */
	public String getDocumentNo_Ref();

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

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Freigegeben.
	  * Zeigt an, ob dieser Beleg eine Freigabe braucht
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Freigegeben.
	  * Zeigt an, ob dieser Beleg eine Freigabe braucht
	  */
	public boolean isApproved();

    /** Column name IsDateFactOverridable */
    public static final String COLUMNNAME_IsDateFactOverridable = "IsDateFactOverridable";

	/** Set Valutadatum Änderbar	  */
	public void setIsDateFactOverridable (boolean IsDateFactOverridable);

	/** Get Valutadatum Änderbar	  */
	public boolean isDateFactOverridable();

    /** Column name IsProcessedByComSystem */
    public static final String COLUMNNAME_IsProcessedByComSystem = "IsProcessedByComSystem";

	/** Set In Buchauszug verbucht.
	  * Änderung wurde in den Buchauszug aufgenommen
	  */
	public void setIsProcessedByComSystem (boolean IsProcessedByComSystem);

	/** Get In Buchauszug verbucht.
	  * Änderung wurde in den Buchauszug aufgenommen
	  */
	public boolean isProcessedByComSystem();

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

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

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
