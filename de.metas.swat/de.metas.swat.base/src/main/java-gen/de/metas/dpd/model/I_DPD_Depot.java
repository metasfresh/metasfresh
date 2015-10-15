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
package de.metas.dpd.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.I_M_Shipper;
import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for DPD_Depot
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_DPD_Depot 
{

    /** TableName=DPD_Depot */
    public static final String Table_Name = "DPD_Depot";

    /** AD_Table_ID=540117 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant fuer diese Installation.
	  */
	public int getAD_Client_ID();

    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/** Set Adresszeile 1.
	  * Adresszeile 1 fuer diesen Standort
	  */
	public void setAddress1 (String Address1);

	/** Get Adresszeile 1.
	  * Adresszeile 1 fuer diesen Standort
	  */
	public String getAddress1();

    /** Column name Address2 */
    public static final String COLUMNNAME_Address2 = "Address2";

	/** Set Adresszeile 2.
	  * Adresszeile 2 fuer diesen Standort
	  */
	public void setAddress2 (String Address2);

	/** Get Adresszeile 2.
	  * Adresszeile 2 fuer diesen Standort
	  */
	public String getAddress2();

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

    /** Column name City */
    public static final String COLUMNNAME_City = "City";

	/** Set Ort.
	  * Name des Ortes
	  */
	public void setCity (String City);

	/** Get Ort.
	  * Name des Ortes
	  */
	public String getCity();

    /** Column name CountryCode */
    public static final String COLUMNNAME_CountryCode = "CountryCode";

	/** Set ISO Laendercode.
	  * Zweibuchstabiger ISO Laendercode gemaess ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	public void setCountryCode (String CountryCode);

	/** Get ISO Laendercode.
	  * Zweibuchstabiger ISO Laendercode gemaess ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	public String getCountryCode();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column name DPD_Depot_ID */
    public static final String COLUMNNAME_DPD_Depot_ID = "DPD_Depot_ID";

	/** Set DPD_Depot	  */
	public void setDPD_Depot_ID (int DPD_Depot_ID);

	/** Get DPD_Depot	  */
	public int getDPD_Depot_ID();

    /** Column name DPD_FileInfo_ID */
    public static final String COLUMNNAME_DPD_FileInfo_ID = "DPD_FileInfo_ID";

	/** Set DPD_FileInfo	  */
	public void setDPD_FileInfo_ID (int DPD_FileInfo_ID);

	/** Get DPD_FileInfo	  */
	public int getDPD_FileInfo_ID();

	public de.metas.dpd.model.I_DPD_FileInfo getDPD_FileInfo() throws RuntimeException;

    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/** Set EMail.
	  * EMail-Adresse
	  */
	public void setEMail (String EMail);

	/** Get EMail.
	  * EMail-Adresse
	  */
	public String getEMail();

    /** Column name Fax */
    public static final String COLUMNNAME_Fax = "Fax";

	/** Set Fax.
	  * Faxnummer
	  */
	public void setFax (String Fax);

	/** Get Fax.
	  * Faxnummer
	  */
	public String getFax();

    /** Column name GeoPostDepotNumber */
    public static final String COLUMNNAME_GeoPostDepotNumber = "GeoPostDepotNumber";

	/** Set GeoPostDepotNumber	  */
	public void setGeoPostDepotNumber (String GeoPostDepotNumber);

	/** Get GeoPostDepotNumber	  */
	public String getGeoPostDepotNumber();

    /** Column name GroupID */
    public static final String COLUMNNAME_GroupID = "GroupID";

	/** Set GroupID	  */
	public void setGroupID (String GroupID);

	/** Get GroupID	  */
	public String getGroupID();

    /** Column name IATALikeCode */
    public static final String COLUMNNAME_IATALikeCode = "IATALikeCode";

	/** Set IATALikeCode	  */
	public void setIATALikeCode (String IATALikeCode);

	/** Get IATALikeCode	  */
	public String getIATALikeCode();

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

    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/** Set Lieferweg.
	  * Methode oder Art der Warenlieferung
	  */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/** Get Lieferweg.
	  * Methode oder Art der Warenlieferung
	  */
	public int getM_Shipper_ID();

	public I_M_Shipper getM_Shipper() throws RuntimeException;

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";

	/** Set Name 2.
	  * Zusaetzliche Bezeichnung
	  */
	public void setName2 (String Name2);

	/** Get Name 2.
	  * Zusaetzliche Bezeichnung
	  */
	public String getName2();

    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/** Set Phone.
	  * Identifies a telephone number
	  */
	public void setPhone (String Phone);

	/** Get Phone.
	  * Identifies a telephone number
	  */
	public String getPhone();

    /** Column name Postal */
    public static final String COLUMNNAME_Postal = "Postal";

	/** Set PLZ.
	  * Postleitzahl
	  */
	public void setPostal (String Postal);

	/** Get PLZ.
	  * Postleitzahl
	  */
	public String getPostal();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();

    /** Column name URL */
    public static final String COLUMNNAME_URL = "URL";

	/** Set URL.
	  * Full URL address - e.g. http://www.adempiere.org
	  */
	public void setURL (String URL);

	/** Get URL.
	  * Full URL address - e.g. http://www.adempiere.org
	  */
	public String getURL();
}
