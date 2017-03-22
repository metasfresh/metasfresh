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
package de.metas.async.model;


/** Generated Interface for C_Queue_WorkPackage
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Queue_WorkPackage 
{

    /** TableName=C_Queue_WorkPackage */
    public static final String Table_Name = "C_Queue_WorkPackage";

    /** AD_Table_ID=540425 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_Client>(I_C_Queue_WorkPackage.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Issue_ID();

	public org.compiere.model.I_AD_Issue getAD_Issue();

	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue);

    /** Column definition for AD_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_Issue>(I_C_Queue_WorkPackage.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_Org>(I_C_Queue_WorkPackage.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Erstellt durch Prozess-Instanz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setAD_PInstance_Creator_ID (int AD_PInstance_Creator_ID);

	/**
	 * Get Erstellt durch Prozess-Instanz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getAD_PInstance_Creator_ID();

	public org.compiere.model.I_AD_PInstance getAD_PInstance_Creator();

	public void setAD_PInstance_Creator(org.compiere.model.I_AD_PInstance AD_PInstance_Creator);

    /** Column definition for AD_PInstance_Creator_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_Creator_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_PInstance>(I_C_Queue_WorkPackage.class, "AD_PInstance_Creator_ID", org.compiere.model.I_AD_PInstance.class);
    /** Column name AD_PInstance_Creator_ID */
    public static final String COLUMNNAME_AD_PInstance_Creator_ID = "AD_PInstance_Creator_ID";

	/**
	 * Set Prozess-Instanz.
	 * Instanz eines Prozesses
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Prozess-Instanz.
	 * Instanz eines Prozesses
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PInstance_ID();

	public org.compiere.model.I_AD_PInstance getAD_PInstance();

	public void setAD_PInstance(org.compiere.model.I_AD_PInstance AD_PInstance);

    /** Column definition for AD_PInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_PInstance>(I_C_Queue_WorkPackage.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Rolle.
	 * Responsibility Role
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Rolle.
	 * Responsibility Role
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Role_ID();

	public org.compiere.model.I_AD_Role getAD_Role();

	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role);

    /** Column definition for AD_Role_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_Role>(I_C_Queue_WorkPackage.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
    /** Column name AD_Role_ID */
    public static final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_User>(I_C_Queue_WorkPackage.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_InCharge_ID();

	public org.compiere.model.I_AD_User getAD_User_InCharge();

	public void setAD_User_InCharge(org.compiere.model.I_AD_User AD_User_InCharge);

    /** Column definition for AD_User_InCharge_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_User> COLUMN_AD_User_InCharge_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_User>(I_C_Queue_WorkPackage.class, "AD_User_InCharge_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_InCharge_ID */
    public static final String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Batch Enqueued Count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBatchEnqueuedCount (int BatchEnqueuedCount);

	/**
	 * Get Batch Enqueued Count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBatchEnqueuedCount();

    /** Column definition for BatchEnqueuedCount */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_BatchEnqueuedCount = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "BatchEnqueuedCount", null);
    /** Column name BatchEnqueuedCount */
    public static final String COLUMNNAME_BatchEnqueuedCount = "BatchEnqueuedCount";

	/**
	 * Set Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Async_Batch_ID (int C_Async_Batch_ID);

	/**
	 * Get Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Async_Batch_ID();

	public de.metas.async.model.I_C_Async_Batch getC_Async_Batch();

	public void setC_Async_Batch(de.metas.async.model.I_C_Async_Batch C_Async_Batch);

    /** Column definition for C_Async_Batch_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, de.metas.async.model.I_C_Async_Batch> COLUMN_C_Async_Batch_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, de.metas.async.model.I_C_Async_Batch>(I_C_Queue_WorkPackage.class, "C_Async_Batch_ID", de.metas.async.model.I_C_Async_Batch.class);
    /** Column name C_Async_Batch_ID */
    public static final String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/**
	 * Set Queue Block.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_Block_ID (int C_Queue_Block_ID);

	/**
	 * Get Queue Block.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_Block_ID();

	public de.metas.async.model.I_C_Queue_Block getC_Queue_Block();

	public void setC_Queue_Block(de.metas.async.model.I_C_Queue_Block C_Queue_Block);

    /** Column definition for C_Queue_Block_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, de.metas.async.model.I_C_Queue_Block> COLUMN_C_Queue_Block_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, de.metas.async.model.I_C_Queue_Block>(I_C_Queue_WorkPackage.class, "C_Queue_Block_ID", de.metas.async.model.I_C_Queue_Block.class);
    /** Column name C_Queue_Block_ID */
    public static final String COLUMNNAME_C_Queue_Block_ID = "C_Queue_Block_ID";

	/**
	 * Set WorkPackage Processor.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setC_Queue_PackageProcessor_ID (int C_Queue_PackageProcessor_ID);

	/**
	 * Get WorkPackage Processor.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getC_Queue_PackageProcessor_ID();

	public de.metas.async.model.I_C_Queue_PackageProcessor getC_Queue_PackageProcessor();

	public void setC_Queue_PackageProcessor(de.metas.async.model.I_C_Queue_PackageProcessor C_Queue_PackageProcessor);

    /** Column definition for C_Queue_PackageProcessor_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, de.metas.async.model.I_C_Queue_PackageProcessor> COLUMN_C_Queue_PackageProcessor_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, de.metas.async.model.I_C_Queue_PackageProcessor>(I_C_Queue_WorkPackage.class, "C_Queue_PackageProcessor_ID", de.metas.async.model.I_C_Queue_PackageProcessor.class);
    /** Column name C_Queue_PackageProcessor_ID */
    public static final String COLUMNNAME_C_Queue_PackageProcessor_ID = "C_Queue_PackageProcessor_ID";

	/**
	 * Set WorkPackage Queue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Queue_WorkPackage_ID (int C_Queue_WorkPackage_ID);

	/**
	 * Get WorkPackage Queue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Queue_WorkPackage_ID();

    /** Column definition for C_Queue_WorkPackage_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_C_Queue_WorkPackage_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "C_Queue_WorkPackage_ID", null);
    /** Column name C_Queue_WorkPackage_ID */
    public static final String COLUMNNAME_C_Queue_WorkPackage_ID = "C_Queue_WorkPackage_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_User>(I_C_Queue_WorkPackage.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Fehlermeldung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setErrorMsg (java.lang.String ErrorMsg);

	/**
	 * Get Fehlermeldung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getErrorMsg();

    /** Column definition for ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_ErrorMsg = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "ErrorMsg", null);
    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsError (boolean IsError);

	/**
	 * Get Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isError();

    /** Column definition for IsError */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "IsError", null);
    /** Column name IsError */
    public static final String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Fehler zur Kentnis genommen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsErrorAcknowledged (boolean IsErrorAcknowledged);

	/**
	 * Get Fehler zur Kentnis genommen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isErrorAcknowledged();

    /** Column definition for IsErrorAcknowledged */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_IsErrorAcknowledged = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "IsErrorAcknowledged", null);
    /** Column name IsErrorAcknowledged */
    public static final String COLUMNNAME_IsErrorAcknowledged = "IsErrorAcknowledged";

	/**
	 * Set Bereit zur Verarbeitung.
	 * Zeigt an, ob das Zusammentstellen eines Arbeitspakets abgeschlossen ist.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReadyForProcessing (boolean IsReadyForProcessing);

	/**
	 * Get Bereit zur Verarbeitung.
	 * Zeigt an, ob das Zusammentstellen eines Arbeitspakets abgeschlossen ist.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReadyForProcessing();

    /** Column definition for IsReadyForProcessing */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_IsReadyForProcessing = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "IsReadyForProcessing", null);
    /** Column name IsReadyForProcessing */
    public static final String COLUMNNAME_IsReadyForProcessing = "IsReadyForProcessing";

	/**
	 * Set Last Duration (ms).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastDurationMillis (int LastDurationMillis);

	/**
	 * Get Last Duration (ms).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLastDurationMillis();

    /** Column definition for LastDurationMillis */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_LastDurationMillis = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "LastDurationMillis", null);
    /** Column name LastDurationMillis */
    public static final String COLUMNNAME_LastDurationMillis = "LastDurationMillis";

	/**
	 * Set Last End Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastEndTime (java.sql.Timestamp LastEndTime);

	/**
	 * Get Last End Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLastEndTime();

    /** Column definition for LastEndTime */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_LastEndTime = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "LastEndTime", null);
    /** Column name LastEndTime */
    public static final String COLUMNNAME_LastEndTime = "LastEndTime";

	/**
	 * Set Last StartTime.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastStartTime (java.sql.Timestamp LastStartTime);

	/**
	 * Get Last StartTime.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLastStartTime();

    /** Column definition for LastStartTime */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_LastStartTime = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "LastStartTime", null);
    /** Column name LastStartTime */
    public static final String COLUMNNAME_LastStartTime = "LastStartTime";

	/**
	 * Set Gesperrt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setLocked (boolean Locked);

	/**
	 * Get Gesperrt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isLocked();

    /** Column definition for Locked */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Locked = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "Locked", null);
    /** Column name Locked */
    public static final String COLUMNNAME_Locked = "Locked";

	/**
	 * Set Priority.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPriority (java.lang.String Priority);

	/**
	 * Get Priority.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriority();

    /** Column definition for Priority */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Priority = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "Priority", null);
    /** Column name Priority */
    public static final String COLUMNNAME_Priority = "Priority";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Skipped Count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSkipped_Count (int Skipped_Count);

	/**
	 * Get Skipped Count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSkipped_Count();

    /** Column definition for Skipped_Count */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Skipped_Count = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "Skipped_Count", null);
    /** Column name Skipped_Count */
    public static final String COLUMNNAME_Skipped_Count = "Skipped_Count";

	/**
	 * Set Skipped First Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSkipped_First_Time (java.sql.Timestamp Skipped_First_Time);

	/**
	 * Get Skipped First Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getSkipped_First_Time();

    /** Column definition for Skipped_First_Time */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Skipped_First_Time = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "Skipped_First_Time", null);
    /** Column name Skipped_First_Time */
    public static final String COLUMNNAME_Skipped_First_Time = "Skipped_First_Time";

	/**
	 * Set Skipped Last Reason.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSkipped_Last_Reason (java.lang.String Skipped_Last_Reason);

	/**
	 * Get Skipped Last Reason.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSkipped_Last_Reason();

    /** Column definition for Skipped_Last_Reason */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Skipped_Last_Reason = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "Skipped_Last_Reason", null);
    /** Column name Skipped_Last_Reason */
    public static final String COLUMNNAME_Skipped_Last_Reason = "Skipped_Last_Reason";

	/**
	 * Set Zuletzt Übersprungen um.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSkippedAt (java.sql.Timestamp SkippedAt);

	/**
	 * Get Zuletzt Übersprungen um.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getSkippedAt();

    /** Column definition for SkippedAt */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_SkippedAt = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "SkippedAt", null);
    /** Column name SkippedAt */
    public static final String COLUMNNAME_SkippedAt = "SkippedAt";

	/**
	 * Set Skip Timeout (millis).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSkipTimeoutMillis (int SkipTimeoutMillis);

	/**
	 * Get Skip Timeout (millis).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSkipTimeoutMillis();

    /** Column definition for SkipTimeoutMillis */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_SkipTimeoutMillis = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "SkipTimeoutMillis", null);
    /** Column name SkipTimeoutMillis */
    public static final String COLUMNNAME_SkipTimeoutMillis = "SkipTimeoutMillis";

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
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, Object>(I_C_Queue_WorkPackage.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_User>(I_C_Queue_WorkPackage.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
