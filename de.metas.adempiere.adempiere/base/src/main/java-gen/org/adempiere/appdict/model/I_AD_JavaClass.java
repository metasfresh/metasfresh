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
package org.adempiere.appdict.model;


/** Generated Interface for AD_JavaClass
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_JavaClass 
{

    /** TableName=AD_JavaClass */
    public static final String Table_Name = "AD_JavaClass";

    /** AD_Table_ID=540520 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column name AD_EntityType_ID */
    public static final String COLUMNNAME_AD_EntityType_ID = "AD_EntityType_ID";

	/** Set Entitäts-Art.
	  * Systementitäts-Art
	  */
	public void setAD_EntityType_ID (int AD_EntityType_ID);

	/** Get Entitäts-Art.
	  * Systementitäts-Art
	  */
	public int getAD_EntityType_ID();

	public org.compiere.model.I_AD_EntityType getAD_EntityType() throws RuntimeException;

	public void setAD_EntityType(org.compiere.model.I_AD_EntityType AD_EntityType);

    /** Column name AD_JavaClass_ID */
    public static final String COLUMNNAME_AD_JavaClass_ID = "AD_JavaClass_ID";

	/** Set AD_JavaClass	  */
	public void setAD_JavaClass_ID (int AD_JavaClass_ID);

	/** Get AD_JavaClass	  */
	public int getAD_JavaClass_ID();

    /** Column name AD_JavaClass_Type_ID */
    public static final String COLUMNNAME_AD_JavaClass_Type_ID = "AD_JavaClass_Type_ID";

	/** Set Java Class Type	  */
	public void setAD_JavaClass_Type_ID (int AD_JavaClass_Type_ID);

	/** Get Java Class Type	  */
	public int getAD_JavaClass_Type_ID();

	public I_AD_JavaClass_Type getAD_JavaClass_Type() throws RuntimeException;

	public void setAD_JavaClass_Type(I_AD_JavaClass_Type AD_JavaClass_Type);

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

    /** Column name Classname */
    public static final String COLUMNNAME_Classname = "Classname";

	/** Set Java-Klasse	  */
	public void setClassname (java.lang.String Classname);

	/** Get Java-Klasse	  */
	public java.lang.String getClassname();

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
}
