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
package de.metas.adempiere.model;


/** Generated Interface for C_CountryArea_Assign
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_CountryArea_Assign 
{

    /** TableName=C_CountryArea_Assign */
    public static final String Table_Name = "C_CountryArea_Assign";

    /** AD_Table_ID=540384 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

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

    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/** Set Land.
	  * Land
	  */
	public void setC_Country_ID (int C_Country_ID);

	/** Get Land.
	  * Land
	  */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException;

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column name C_CountryArea_Assign_ID */
    public static final String COLUMNNAME_C_CountryArea_Assign_ID = "C_CountryArea_Assign_ID";

	/** Set Country area assign	  */
	public void setC_CountryArea_Assign_ID (int C_CountryArea_Assign_ID);

	/** Get Country area assign	  */
	public int getC_CountryArea_Assign_ID();

    /** Column name C_CountryArea_ID */
    public static final String COLUMNNAME_C_CountryArea_ID = "C_CountryArea_ID";

	/** Set Country Area	  */
	public void setC_CountryArea_ID (int C_CountryArea_ID);

	/** Get Country Area	  */
	public int getC_CountryArea_ID();

	public I_C_CountryArea getC_CountryArea() throws RuntimeException;

	public void setC_CountryArea(I_C_CountryArea C_CountryArea);

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

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set GĂĽltig ab.
	  * GĂĽltig ab inklusiv (erster Tag)
	  */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/** Get GĂĽltig ab.
	  * GĂĽltig ab inklusiv (erster Tag)
	  */
	public java.sql.Timestamp getValidFrom();

    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/** Set GĂĽltig bis.
	  * GĂĽltig bis inklusiv (letzter Tag)
	  */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/** Get GĂĽltig bis.
	  * GĂĽltig bis inklusiv (letzter Tag)
	  */
	public java.sql.Timestamp getValidTo();
}
