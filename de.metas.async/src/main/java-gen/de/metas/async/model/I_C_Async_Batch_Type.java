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


/** Generated Interface for C_Async_Batch_Type
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Async_Batch_Type 
{

    /** TableName=C_Async_Batch_Type */
    public static final String Table_Name = "C_Async_Batch_Type";

    /** AD_Table_ID=540625 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Textbaustein.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID);

	/**
	 * Get Textbaustein.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_BoilerPlate_ID();

	public de.metas.letters.model.I_AD_BoilerPlate getAD_BoilerPlate();

	public void setAD_BoilerPlate(de.metas.letters.model.I_AD_BoilerPlate AD_BoilerPlate);

    /** Column definition for AD_BoilerPlate_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, de.metas.letters.model.I_AD_BoilerPlate> COLUMN_AD_BoilerPlate_ID = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, de.metas.letters.model.I_AD_BoilerPlate>(I_C_Async_Batch_Type.class, "AD_BoilerPlate_ID", de.metas.letters.model.I_AD_BoilerPlate.class);
    /** Column name AD_BoilerPlate_ID */
    public static final String COLUMNNAME_AD_BoilerPlate_ID = "AD_BoilerPlate_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, org.compiere.model.I_AD_Client>(I_C_Async_Batch_Type.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, org.compiere.model.I_AD_Org>(I_C_Async_Batch_Type.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Async Batch Type.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Async_Batch_Type_ID (int C_Async_Batch_Type_ID);

	/**
	 * Get Async Batch Type.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Async_Batch_Type_ID();

    /** Column definition for C_Async_Batch_Type_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_C_Async_Batch_Type_ID = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object>(I_C_Async_Batch_Type.class, "C_Async_Batch_Type_ID", null);
    /** Column name C_Async_Batch_Type_ID */
    public static final String COLUMNNAME_C_Async_Batch_Type_ID = "C_Async_Batch_Type_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object>(I_C_Async_Batch_Type.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, org.compiere.model.I_AD_User>(I_C_Async_Batch_Type.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Interner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInternalName (java.lang.String InternalName);

	/**
	 * Get Interner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInternalName();

    /** Column definition for InternalName */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_InternalName = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object>(I_C_Async_Batch_Type.class, "InternalName", null);
    /** Column name InternalName */
    public static final String COLUMNNAME_InternalName = "InternalName";

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
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object>(I_C_Async_Batch_Type.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Send Mail.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSendMail (boolean IsSendMail);

	/**
	 * Get Send Mail.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSendMail();

    /** Column definition for IsSendMail */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_IsSendMail = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object>(I_C_Async_Batch_Type.class, "IsSendMail", null);
    /** Column name IsSendMail */
    public static final String COLUMNNAME_IsSendMail = "IsSendMail";

	/**
	 * Set Send Notification.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSendNotification (boolean IsSendNotification);

	/**
	 * Get Send Notification.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSendNotification();

    /** Column definition for IsSendNotification */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_IsSendNotification = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object>(I_C_Async_Batch_Type.class, "IsSendNotification", null);
    /** Column name IsSendNotification */
    public static final String COLUMNNAME_IsSendNotification = "IsSendNotification";

	/**
	 * Set Keep Alive Time (Hours).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setKeepAliveTimeHours (java.lang.String KeepAliveTimeHours);

	/**
	 * Get Keep Alive Time (Hours).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getKeepAliveTimeHours();

    /** Column definition for KeepAliveTimeHours */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_KeepAliveTimeHours = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object>(I_C_Async_Batch_Type.class, "KeepAliveTimeHours", null);
    /** Column name KeepAliveTimeHours */
    public static final String COLUMNNAME_KeepAliveTimeHours = "KeepAliveTimeHours";

	/**
	 * Set Benachrichtigungs-Art.
	 * Art der Benachrichtigung
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setNotificationType (java.lang.String NotificationType);

	/**
	 * Get Benachrichtigungs-Art.
	 * Art der Benachrichtigung
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNotificationType();

    /** Column definition for NotificationType */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_NotificationType = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object>(I_C_Async_Batch_Type.class, "NotificationType", null);
    /** Column name NotificationType */
    public static final String COLUMNNAME_NotificationType = "NotificationType";

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
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, Object>(I_C_Async_Batch_Type.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Async_Batch_Type, org.compiere.model.I_AD_User>(I_C_Async_Batch_Type.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
