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


/** Generated Interface for C_AdvCommissionCondition
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_AdvCommissionCondition 
{

    /** TableName=C_AdvCommissionCondition */
    public static final String Table_Name = "C_AdvCommissionCondition";

    /** AD_Table_ID=540075 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant für diese Installation.
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

    /** Column name C_AdvCommissionCondition_ID */
    public static final String COLUMNNAME_C_AdvCommissionCondition_ID = "C_AdvCommissionCondition_ID";

	/** Set Provisionsvertrag	  */
	public void setC_AdvCommissionCondition_ID (int C_AdvCommissionCondition_ID);

	/** Get Provisionsvertrag	  */
	public int getC_AdvCommissionCondition_ID();

    /** Column name C_AdvComSystem_ID */
    public static final String COLUMNNAME_C_AdvComSystem_ID = "C_AdvComSystem_ID";

	/** Set Vergütungsplan	  */
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID);

	/** Get Vergütungsplan	  */
	public int getC_AdvComSystem_ID();

	public de.metas.commission.model.I_C_AdvComSystem getC_AdvComSystem() throws RuntimeException;

	public void setC_AdvComSystem(de.metas.commission.model.I_C_AdvComSystem C_AdvComSystem);

    /** Column name C_Calendar_ID */
    public static final String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

	/** Set Kalender.
	  * Bezeichnung des Buchfð¨²µngs-Kalenders
	  */
	public void setC_Calendar_ID (int C_Calendar_ID);

	/** Get Kalender.
	  * Bezeichnung des Buchfð¨²µngs-Kalenders
	  */
	public int getC_Calendar_ID();

	public org.compiere.model.I_C_Calendar getC_Calendar() throws RuntimeException;

	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar);

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Währung.
	  * Die Währung für diesen Eintrag
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Währung.
	  * Die Währung für diesen Eintrag
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column name C_Doctype_Payroll_ID */
    public static final String COLUMNNAME_C_Doctype_Payroll_ID = "C_Doctype_Payroll_ID";

	/** Set Lohnbuch-Belegart	  */
	public void setC_Doctype_Payroll_ID (int C_Doctype_Payroll_ID);

	/** Get Lohnbuch-Belegart	  */
	public int getC_Doctype_Payroll_ID();

	public org.compiere.model.I_C_DocType getC_Doctype_Payroll() throws RuntimeException;

	public void setC_Doctype_Payroll(org.compiere.model.I_C_DocType C_Doctype_Payroll);

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

    /** Column name HR_Payroll_ID */
    public static final String COLUMNNAME_HR_Payroll_ID = "HR_Payroll_ID";

	/** Set Payroll	  */
	public void setHR_Payroll_ID (int HR_Payroll_ID);

	/** Get Payroll	  */
	public int getHR_Payroll_ID();

	public org.eevolution.model.I_HR_Payroll getHR_Payroll() throws RuntimeException;

	public void setHR_Payroll(org.eevolution.model.I_HR_Payroll HR_Payroll);

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

    /** Column name IsDefaultForOrphandedSponsors */
    public static final String COLUMNNAME_IsDefaultForOrphandedSponsors = "IsDefaultForOrphandedSponsors";

	/** Set für Sponsoren ohne VP	  */
	public void setIsDefaultForOrphandedSponsors (boolean IsDefaultForOrphandedSponsors);

	/** Get für Sponsoren ohne VP	  */
	public boolean isDefaultForOrphandedSponsors();

    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/** Set Produkt-Kategorie.
	  * Kategorie eines Produktes
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/** Get Produkt-Kategorie.
	  * Kategorie eines Produktes
	  */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException;

	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category);

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
