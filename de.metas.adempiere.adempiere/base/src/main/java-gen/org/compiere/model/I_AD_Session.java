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
package org.compiere.model;


/** Generated Interface for AD_Session
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Session 
{

    /** TableName=AD_Session */
    public static final String Table_Name = "AD_Session";

    /** AD_Table_ID=566 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Client/Tenant for this installation.
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

    /** Column name AD_Role_ID */
    public static final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/** Set Rolle.
	  * Responsibility Role
	  */
	public void setAD_Role_ID (int AD_Role_ID);

	/** Get Rolle.
	  * Responsibility Role
	  */
	public int getAD_Role_ID();

	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException;

	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role);

    /** Column name AD_Session_ID */
    public static final String COLUMNNAME_AD_Session_ID = "AD_Session_ID";

	/** Set Session.
	  * User Session Online or Web
	  */
	public void setAD_Session_ID (int AD_Session_ID);

	/** Get Session.
	  * User Session Online or Web
	  */
	public int getAD_Session_ID();

    /** Column name Client_Info */
    public static final String COLUMNNAME_Client_Info = "Client_Info";

	/** Set Client Info.
	  * Informations about connected client (e.g. web browser name, version etc)
	  */
	public void setClient_Info (java.lang.String Client_Info);

	/** Get Client Info.
	  * Informations about connected client (e.g. web browser name, version etc)
	  */
	public java.lang.String getClient_Info();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Date this record was created
	  */
	public java.sql.Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsLoginIncorrect */
    public static final String COLUMNNAME_IsLoginIncorrect = "IsLoginIncorrect";

	/** Set IsLoginIncorrect.
	  * Flag is yes if  is login incorect
	  */
	public void setIsLoginIncorrect (boolean IsLoginIncorrect);

	/** Get IsLoginIncorrect.
	  * Flag is yes if  is login incorect
	  */
	public boolean isLoginIncorrect();

    /** Column name LoginDate */
    public static final String COLUMNNAME_LoginDate = "LoginDate";

	/** Set Login date	  */
	public void setLoginDate (java.sql.Timestamp LoginDate);

	/** Get Login date	  */
	public java.sql.Timestamp getLoginDate();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public void setProcessed (boolean Processed);

	/** Get Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public boolean isProcessed();

    /** Column name Remote_Addr */
    public static final String COLUMNNAME_Remote_Addr = "Remote_Addr";

	/** Set Remote Addr.
	  * Remote Address
	  */
	public void setRemote_Addr (java.lang.String Remote_Addr);

	/** Get Remote Addr.
	  * Remote Address
	  */
	public java.lang.String getRemote_Addr();

    /** Column name Remote_Host */
    public static final String COLUMNNAME_Remote_Host = "Remote_Host";

	/** Set Remote Host.
	  * Remote host Info
	  */
	public void setRemote_Host (java.lang.String Remote_Host);

	/** Get Remote Host.
	  * Remote host Info
	  */
	public java.lang.String getRemote_Host();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Date this record was updated
	  */
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name WebSession */
    public static final String COLUMNNAME_WebSession = "WebSession";

	/** Set Web-Sitzung.
	  * Web Session ID
	  */
	public void setWebSession (java.lang.String WebSession);

	/** Get Web-Sitzung.
	  * Web Session ID
	  */
	public java.lang.String getWebSession();
}
