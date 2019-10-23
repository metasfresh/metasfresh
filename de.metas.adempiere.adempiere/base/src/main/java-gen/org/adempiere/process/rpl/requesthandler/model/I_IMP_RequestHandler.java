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
package org.adempiere.process.rpl.requesthandler.model;


/** Generated Interface for IMP_RequestHandler
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_IMP_RequestHandler 
{

    /** TableName=IMP_RequestHandler */
    public static final String Table_Name = "IMP_RequestHandler";

    /** AD_Table_ID=540456 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant fÃ¼r diese Installation.
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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/** Set EntitÃ¤ts-Art.
	  * Dictionary Entity Type;
 Determines ownership and synchronization
	  */
	public void setEntityType (java.lang.String EntityType);

	/** Get EntitÃ¤ts-Art.
	  * Dictionary Entity Type;
 Determines ownership and synchronization
	  */
	public java.lang.String getEntityType();

    /** Column name EXP_Format_ID */
    public static final String COLUMNNAME_EXP_Format_ID = "EXP_Format_ID";

	/** Set Export Format	  */
	public void setEXP_Format_ID (int EXP_Format_ID);

	/** Get Export Format	  */
	public int getEXP_Format_ID();

	public org.compiere.model.I_EXP_Format getEXP_Format() throws RuntimeException;

	public void setEXP_Format(org.compiere.model.I_EXP_Format EXP_Format);

    /** Column name IMP_RequestHandler_ID */
    public static final String COLUMNNAME_IMP_RequestHandler_ID = "IMP_RequestHandler_ID";

	/** Set Request handler	  */
	public void setIMP_RequestHandler_ID (int IMP_RequestHandler_ID);

	/** Get Request handler	  */
	public int getIMP_RequestHandler_ID();

    /** Column name IMP_RequestHandlerType_ID */
    public static final String COLUMNNAME_IMP_RequestHandlerType_ID = "IMP_RequestHandlerType_ID";

	/** Set Request handler type	  */
	public void setIMP_RequestHandlerType_ID (int IMP_RequestHandlerType_ID);

	/** Get Request handler type	  */
	public int getIMP_RequestHandlerType_ID();

	public org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandlerType getIMP_RequestHandlerType() throws RuntimeException;

	public void setIMP_RequestHandlerType(org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandlerType IMP_RequestHandlerType);

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
	public void setName (java.lang.String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public java.lang.String getName();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set SuchschlÃ¼ssel.
	  * SuchschlÃ¼ssel fÃ¼r den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	public void setValue (java.lang.String Value);

	/** Get SuchschlÃ¼ssel.
	  * SuchschlÃ¼ssel fÃ¼r den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	public java.lang.String getValue();
}
