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


/** Generated Interface for C_Postal
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a#216
 */
@SuppressWarnings("javadoc")
public interface I_C_Postal 
{

    /** TableName=C_Postal */
    public static final String Table_Name = "C_Postal";

    /** AD_Table_ID=540317 */
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

    /** Column name C_City_ID */
    public static final String COLUMNNAME_C_City_ID = "C_City_ID";

	/** Set Ort.
	  * Ort
	  */
	public void setC_City_ID (int C_City_ID);

	/** Get Ort.
	  * Ort
	  */
	public int getC_City_ID();

	public org.compiere.model.I_C_City getC_City() throws RuntimeException;

	public void setC_City(org.compiere.model.I_C_City C_City);

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

    /** Column name C_Postal_ID */
    public static final String COLUMNNAME_C_Postal_ID = "C_Postal_ID";

	/** Set Postal codes	  */
	public void setC_Postal_ID (int C_Postal_ID);

	/** Get Postal codes	  */
	public int getC_Postal_ID();

    /** Column name C_Region_ID */
    public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/** Set Region.
	  * Identifiziert eine geographische Region
	  */
	public void setC_Region_ID (int C_Region_ID);

	/** Get Region.
	  * Identifiziert eine geographische Region
	  */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException;

	public void setC_Region(org.compiere.model.I_C_Region C_Region);

    /** Column name City */
    public static final String COLUMNNAME_City = "City";

	/** Set Ort.
	  * Name des Ortes
	  */
	public void setCity (java.lang.String City);

	/** Get Ort.
	  * Name des Ortes
	  */
	public java.lang.String getCity();

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

    /** Column name District */
    public static final String COLUMNNAME_District = "District";

	/** Set Bezirk	  */
	public void setDistrict (java.lang.String District);

	/** Get Bezirk	  */
	public java.lang.String getDistrict();

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

    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/** Set Manuell.
	  * Dies ist ein manueller Vorgang
	  */
	public void setIsManual (boolean IsManual);

	/** Get Manuell.
	  * Dies ist ein manueller Vorgang
	  */
	public boolean isManual();

    /** Column name IsValidDPD */
    public static final String COLUMNNAME_IsValidDPD = "IsValidDPD";

	/** Set DPD Validated.
	  * Record was validated on DPD database
	  */
	public void setIsValidDPD (boolean IsValidDPD);

	/** Get DPD Validated.
	  * Record was validated on DPD database
	  */
	public boolean isValidDPD();

    /** Column name NonStdAddress */
    public static final String COLUMNNAME_NonStdAddress = "NonStdAddress";

	/** Set Bereinigung notwendig	  */
	public void setNonStdAddress (boolean NonStdAddress);

	/** Get Bereinigung notwendig	  */
	public boolean isNonStdAddress();

    /** Column name Postal */
    public static final String COLUMNNAME_Postal = "Postal";

	/** Set PLZ.
	  * Postleitzahl
	  */
	public void setPostal (java.lang.String Postal);

	/** Get PLZ.
	  * Postleitzahl
	  */
	public java.lang.String getPostal();

    /** Column name Postal_Add */
    public static final String COLUMNNAME_Postal_Add = "Postal_Add";

	/** Set -.
	  * Additional ZIP or Postal code
	  */
	public void setPostal_Add (java.lang.String Postal_Add);

	/** Get -.
	  * Additional ZIP or Postal code
	  */
	public java.lang.String getPostal_Add();

    /** Column name RegionName */
    public static final String COLUMNNAME_RegionName = "RegionName";

	/** Set Region.
	  * Name der Region
	  */
	public void setRegionName (java.lang.String RegionName);

	/** Get Region.
	  * Name der Region
	  */
	public java.lang.String getRegionName();

    /** Column name Township */
    public static final String COLUMNNAME_Township = "Township";

	/** Set Gemeinde	  */
	public void setTownship (java.lang.String Township);

	/** Get Gemeinde	  */
	public java.lang.String getTownship();

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
