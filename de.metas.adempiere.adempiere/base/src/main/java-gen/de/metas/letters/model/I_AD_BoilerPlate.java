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
package de.metas.letters.model;


/** Generated Interface for AD_BoilerPlate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_BoilerPlate 
{

    /** TableName=AD_BoilerPlate */
    public static final String Table_Name = "AD_BoilerPlate";

    /** AD_Table_ID=504410 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AD_BoilerPlate_ID */
    public static final String COLUMNNAME_AD_BoilerPlate_ID = "AD_BoilerPlate_ID";

	/** Set Textbaustein	  */
	public void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID);

	/** Get Textbaustein	  */
	public int getAD_BoilerPlate_ID();

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

    /** Column name JasperProcess_ID */
    public static final String COLUMNNAME_JasperProcess_ID = "JasperProcess_ID";

	/** Set Jasper Process.
	  * The Jasper Process used by the printengine if any process defined
	  */
	public void setJasperProcess_ID (int JasperProcess_ID);

	/** Get Jasper Process.
	  * The Jasper Process used by the printengine if any process defined
	  */
	public int getJasperProcess_ID();

	public org.compiere.model.I_AD_Process getJasperProcess() throws RuntimeException;

	public void setJasperProcess(org.compiere.model.I_AD_Process JasperProcess);

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (java.lang.String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public java.lang.String getName();

    /** Column name Subject */
    public static final String COLUMNNAME_Subject = "Subject";

	/** Set Betreff.
	  * Mail Betreff
	  */
	public void setSubject (java.lang.String Subject);

	/** Get Betreff.
	  * Mail Betreff
	  */
	public java.lang.String getSubject();

    /** Column name TextSnippext */
    public static final String COLUMNNAME_TextSnippext = "TextSnippext";

	/** Set TextSchnipsel	  */
	public void setTextSnippext (java.lang.String TextSnippext);

	/** Get TextSchnipsel	  */
	public java.lang.String getTextSnippext();

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
