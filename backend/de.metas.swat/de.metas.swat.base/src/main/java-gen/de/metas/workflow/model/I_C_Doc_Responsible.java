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
package de.metas.workflow.model;


/** Generated Interface for C_Doc_Responsible
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Doc_Responsible 
{

    /** TableName=C_Doc_Responsible */
    public static final String Table_Name = "C_Doc_Responsible";

    /** AD_Table_ID=540470 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

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

    /** Column name AD_User_Responsible_ID */
    public static final String COLUMNNAME_AD_User_Responsible_ID = "AD_User_Responsible_ID";

	/** Set Responsible User	  */
	public void setAD_User_Responsible_ID (int AD_User_Responsible_ID);

	/** Get Responsible User	  */
	public int getAD_User_Responsible_ID();

	public org.compiere.model.I_AD_User getAD_User_Responsible() throws RuntimeException;

	public void setAD_User_Responsible(org.compiere.model.I_AD_User AD_User_Responsible);

    /** Column name AD_WF_Responsible_ID */
    public static final String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";

	/** Set Workflow - Verantwortlicher.
	  * Verantwortlicher für die Ausführung des Workflow
	  */
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID);

	/** Get Workflow - Verantwortlicher.
	  * Verantwortlicher für die Ausführung des Workflow
	  */
	public int getAD_WF_Responsible_ID();

	public org.compiere.model.I_AD_WF_Responsible getAD_WF_Responsible() throws RuntimeException;

	public void setAD_WF_Responsible(org.compiere.model.I_AD_WF_Responsible AD_WF_Responsible);

    /** Column name AD_WF_Responsible_Name */
    public static final String COLUMNNAME_AD_WF_Responsible_Name = "AD_WF_Responsible_Name";

	/** Set Workflow - Verantwortlicher (text)	  */
	public void setAD_WF_Responsible_Name (java.lang.String AD_WF_Responsible_Name);

	/** Get Workflow - Verantwortlicher (text)	  */
	public java.lang.String getAD_WF_Responsible_Name();

    /** Column name C_Doc_Responsible_ID */
    public static final String COLUMNNAME_C_Doc_Responsible_ID = "C_Doc_Responsible_ID";

	/** Set Document Responsible	  */
	public void setC_Doc_Responsible_ID (int C_Doc_Responsible_ID);

	/** Get Document Responsible	  */
	public int getC_Doc_Responsible_ID();

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
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
