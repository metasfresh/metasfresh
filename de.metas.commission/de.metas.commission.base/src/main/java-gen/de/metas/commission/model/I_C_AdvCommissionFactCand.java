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


/** Generated Interface for C_AdvCommissionFactCand
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_AdvCommissionFactCand 
{

    /** TableName=C_AdvCommissionFactCand */
    public static final String Table_Name = "C_AdvCommissionFactCand";

    /** AD_Table_ID=540072 */
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

    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	/** Set System-Problem.
	  * Automatically created or manually entered System Issue
	  */
	public void setAD_Issue_ID (int AD_Issue_ID);

	/** Get System-Problem.
	  * Automatically created or manually entered System Issue
	  */
	public int getAD_Issue_ID();

	public org.compiere.model.I_AD_Issue getAD_Issue() throws RuntimeException;

	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue);

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

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

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column name AlsoHandleTypesWithProcessNow */
    public static final String COLUMNNAME_AlsoHandleTypesWithProcessNow = "AlsoHandleTypesWithProcessNow";

	/** Set Inklusive Provisionsarten mit "Sofort verarbeiten"	  */
	public void setAlsoHandleTypesWithProcessNow (boolean AlsoHandleTypesWithProcessNow);

	/** Get Inklusive Provisionsarten mit "Sofort verarbeiten"	  */
	public boolean isAlsoHandleTypesWithProcessNow();

    /** Column name C_AdvComFactCand_Cause_ID */
    public static final String COLUMNNAME_C_AdvComFactCand_Cause_ID = "C_AdvComFactCand_Cause_ID";

	/** Set Urspr. Warteschlangen-Eintrag	  */
	public void setC_AdvComFactCand_Cause_ID (int C_AdvComFactCand_Cause_ID);

	/** Get Urspr. Warteschlangen-Eintrag	  */
	public int getC_AdvComFactCand_Cause_ID();

	public de.metas.commission.model.I_C_AdvCommissionFactCand getC_AdvComFactCand_Cause() throws RuntimeException;

	public void setC_AdvComFactCand_Cause(de.metas.commission.model.I_C_AdvCommissionFactCand C_AdvComFactCand_Cause);

    /** Column name C_AdvCommissionFactCand_ID */
    public static final String COLUMNNAME_C_AdvCommissionFactCand_ID = "C_AdvCommissionFactCand_ID";

	/** Set Provisionsdaten-Wartschlange	  */
	public void setC_AdvCommissionFactCand_ID (int C_AdvCommissionFactCand_ID);

	/** Get Provisionsdaten-Wartschlange	  */
	public int getC_AdvCommissionFactCand_ID();

    /** Column name C_AdvCommissionRelevantPO_ID */
    public static final String COLUMNNAME_C_AdvCommissionRelevantPO_ID = "C_AdvCommissionRelevantPO_ID";

	/** Set Def. relevanter Datensatz	  */
	public void setC_AdvCommissionRelevantPO_ID (int C_AdvCommissionRelevantPO_ID);

	/** Get Def. relevanter Datensatz	  */
	public int getC_AdvCommissionRelevantPO_ID();

	public de.metas.commission.model.I_C_AdvCommissionRelevantPO getC_AdvCommissionRelevantPO() throws RuntimeException;

	public void setC_AdvCommissionRelevantPO(de.metas.commission.model.I_C_AdvCommissionRelevantPO C_AdvCommissionRelevantPO);

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

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public java.sql.Timestamp getDateAcct();

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

    /** Column name IsError */
    public static final String COLUMNNAME_IsError = "IsError";

	/** Set Fehler.
	  * Ein Fehler ist bei der Durchführung aufgetreten
	  */
	public void setIsError (boolean IsError);

	/** Get Fehler.
	  * Ein Fehler ist bei der Durchführung aufgetreten
	  */
	public boolean isError();

    /** Column name IsImmediateProcessingDone */
    public static final String COLUMNNAME_IsImmediateProcessingDone = "IsImmediateProcessingDone";

	/** Set Sofort-Verarbeitung durchgeführt	  */
	public void setIsImmediateProcessingDone (boolean IsImmediateProcessingDone);

	/** Get Sofort-Verarbeitung durchgeführt	  */
	public boolean isImmediateProcessingDone();

    /** Column name IsSubsequentProcessingDone */
    public static final String COLUMNNAME_IsSubsequentProcessingDone = "IsSubsequentProcessingDone";

	/** Set Nachgelagerte Verarbeitung durchgeführt	  */
	public void setIsSubsequentProcessingDone (boolean IsSubsequentProcessingDone);

	/** Get Nachgelagerte Verarbeitung durchgeführt	  */
	public boolean isSubsequentProcessingDone();

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

    /** Column name TrxName */
    public static final String COLUMNNAME_TrxName = "TrxName";

	/** Set Transaktion.
	  * Name of the transaction
	  */
	public void setTrxName (java.lang.String TrxName);

	/** Get Transaktion.
	  * Name of the transaction
	  */
	public java.lang.String getTrxName();

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
}
