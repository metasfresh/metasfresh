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


/** Generated Interface for C_AdvComSystem
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_AdvComSystem 
{

    /** TableName=C_AdvComSystem */
    public static final String Table_Name = "C_AdvComSystem";

    /** AD_Table_ID=540249 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

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

	/** Set Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column name AD_Role_Admin_ID */
    public static final String COLUMNNAME_AD_Role_Admin_ID = "AD_Role_Admin_ID";

	/** Set Admin-Rolle	  */
	public void setAD_Role_Admin_ID (int AD_Role_Admin_ID);

	/** Get Admin-Rolle	  */
	public int getAD_Role_Admin_ID();

	public org.compiere.model.I_AD_Role getAD_Role_Admin() throws RuntimeException;

	public void setAD_Role_Admin(org.compiere.model.I_AD_Role AD_Role_Admin);

    /** Column name AD_User_Admin_ID */
    public static final String COLUMNNAME_AD_User_Admin_ID = "AD_User_Admin_ID";

	/** Set Admin-Benutzer	  */
	public void setAD_User_Admin_ID (int AD_User_Admin_ID);

	/** Get Admin-Benutzer	  */
	public int getAD_User_Admin_ID();

	public org.compiere.model.I_AD_User getAD_User_Admin() throws RuntimeException;

	public void setAD_User_Admin(org.compiere.model.I_AD_User AD_User_Admin);

    /** Column name C_AdvComRankCollection_ID */
    public static final String COLUMNNAME_C_AdvComRankCollection_ID = "C_AdvComRankCollection_ID";

	/** Set Vergütungsgruppensammlung	  */
	public void setC_AdvComRankCollection_ID (int C_AdvComRankCollection_ID);

	/** Get Vergütungsgruppensammlung	  */
	public int getC_AdvComRankCollection_ID();

	public de.metas.commission.model.I_C_AdvComRankCollection getC_AdvComRankCollection() throws RuntimeException;

	public void setC_AdvComRankCollection(de.metas.commission.model.I_C_AdvComRankCollection C_AdvComRankCollection);

    /** Column name C_AdvComSystem_ID */
    public static final String COLUMNNAME_C_AdvComSystem_ID = "C_AdvComSystem_ID";

	/** Set Vergütungsplan	  */
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID);

	/** Get Vergütungsplan	  */
	public int getC_AdvComSystem_ID();

    /** Column name C_Sponsor_Root_ID */
    public static final String COLUMNNAME_C_Sponsor_Root_ID = "C_Sponsor_Root_ID";

	/** Set Wurzel-Sponsor	  */
	public void setC_Sponsor_Root_ID (int C_Sponsor_Root_ID);

	/** Get Wurzel-Sponsor	  */
	public int getC_Sponsor_Root_ID();

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor_Root() throws RuntimeException;

	public void setC_Sponsor_Root(de.metas.commission.model.I_C_Sponsor C_Sponsor_Root);

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

    /** Column name InternalName */
    public static final String COLUMNNAME_InternalName = "InternalName";

	/** Set Interner Name.
	  * Eindeutiger system-interner Bezeichner des Datensatzes.
	  */
	public void setInternalName (java.lang.String InternalName);

	/** Get Interner Name.
	  * Eindeutiger system-interner Bezeichner des Datensatzes.
	  */
	public java.lang.String getInternalName();

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

    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/** Set Standard.
	  * Default value
	  */
	public void setIsDefault (boolean IsDefault);

	/** Get Standard.
	  * Default value
	  */
	public boolean isDefault();

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
