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

import org.compiere.model.I_M_Package;
import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for DPD_StatusData
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_DPD_StatusData 
{

    /** TableName=DPD_StatusData */
    public static final String Table_Name = "DPD_StatusData";

    /** AD_Table_ID=540130 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant fuer diese Installation.
	  */
	public int getAD_Client_ID();

    /** Column name AddService1_ID */
    public static final String COLUMNNAME_AddService1_ID = "AddService1_ID";

	/** Set Zusatzcode-1.
	  * Zusatzcode oder Differenzcode
	  */
	public void setAddService1_ID (int AddService1_ID);

	/** Get Zusatzcode-1.
	  * Zusatzcode oder Differenzcode
	  */
	public int getAddService1_ID();

	public de.metas.dpd.model.I_DPD_ExceptionCode getAddService1() throws RuntimeException;

    /** Column name AddService2_ID */
    public static final String COLUMNNAME_AddService2_ID = "AddService2_ID";

	/** Set Zusatzcode-2.
	  * Zusatzcode oder Differenzcode
	  */
	public void setAddService2_ID (int AddService2_ID);

	/** Get Zusatzcode-2.
	  * Zusatzcode oder Differenzcode
	  */
	public int getAddService2_ID();

	public de.metas.dpd.model.I_DPD_ExceptionCode getAddService2() throws RuntimeException;

    /** Column name AddService3_ID */
    public static final String COLUMNNAME_AddService3_ID = "AddService3_ID";

	/** Set Zusatzcode-3.
	  * Zusatzcode oder Differenzcode
	  */
	public void setAddService3_ID (int AddService3_ID);

	/** Get Zusatzcode-3.
	  * Zusatzcode oder Differenzcode
	  */
	public int getAddService3_ID();

	public de.metas.dpd.model.I_DPD_ExceptionCode getAddService3() throws RuntimeException;

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

    /** Column name ConsigneeCountryCode */
    public static final String COLUMNNAME_ConsigneeCountryCode = "ConsigneeCountryCode";

	/** Set Empfaenger-Laendercode.
	  * 3-stellig numerischer Laendercode nach ISO 3166 des Empfaengers
	  */
	public void setConsigneeCountryCode (String ConsigneeCountryCode);

	/** Get Empfaenger-Laendercode.
	  * 3-stellig numerischer Laendercode nach ISO 3166 des Empfaengers
	  */
	public String getConsigneeCountryCode();

    /** Column name ConsigneeZip */
    public static final String COLUMNNAME_ConsigneeZip = "ConsigneeZip";

	/** Set Empfaenger-PLZ.
	  * Postleitzahl des Empfaengers
	  */
	public void setConsigneeZip (String ConsigneeZip);

	/** Get Empfaenger-PLZ.
	  * Postleitzahl des Empfaengers
	  */
	public String getConsigneeZip();

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

    /** Column name CustomerReference */
    public static final String COLUMNNAME_CustomerReference = "CustomerReference";

	/** Set Kundenreferenz	  */
	public void setCustomerReference (String CustomerReference);

	/** Get Kundenreferenz	  */
	public String getCustomerReference();

    /** Column name DepotCode */
    public static final String COLUMNNAME_DepotCode = "DepotCode";

	/** Set Scannendes Depot	  */
	public void setDepotCode (String DepotCode);

	/** Get Scannendes Depot	  */
	public String getDepotCode();

    /** Column name DepotName */
    public static final String COLUMNNAME_DepotName = "DepotName";

	/** Set Depotname.
	  * Name des Depots laut Routentabelle
	  */
	public void setDepotName (String DepotName);

	/** Get Depotname.
	  * Name des Depots laut Routentabelle
	  */
	public String getDepotName();

    /** Column name DPD_ScanCode_ID */
    public static final String COLUMNNAME_DPD_ScanCode_ID = "DPD_ScanCode_ID";

	/** Set Scanart	  */
	public void setDPD_ScanCode_ID (int DPD_ScanCode_ID);

	/** Get Scanart	  */
	public int getDPD_ScanCode_ID();

	public de.metas.dpd.model.I_DPD_ScanCode getDPD_ScanCode() throws RuntimeException;

    /** Column name DPD_StatusData_ID */
    public static final String COLUMNNAME_DPD_StatusData_ID = "DPD_StatusData_ID";

	/** Set DPD-Statusdaten	  */
	public void setDPD_StatusData_ID (int DPD_StatusData_ID);

	/** Get DPD-Statusdaten	  */
	public int getDPD_StatusData_ID();

    /** Column name EventDateTime */
    public static final String COLUMNNAME_EventDateTime = "EventDateTime";

	/** Set Scanzeitpunkt.
	  * Scandatum und -zeit
	  */
	public void setEventDateTime (Timestamp EventDateTime);

	/** Get Scanzeitpunkt.
	  * Scandatum und -zeit
	  */
	public Timestamp getEventDateTime();

    /** Column name InfoText */
    public static final String COLUMNNAME_InfoText = "InfoText";

	/** Set Infotext.
	  * Infotext fuer Scanart 18
	  */
	public void setInfoText (String InfoText);

	/** Get Infotext.
	  * Infotext fuer Scanart 18
	  */
	public String getInfoText();

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

    /** Column name Location */
    public static final String COLUMNNAME_Location = "Location";

	/** Set Ort	  */
	public void setLocation (String Location);

	/** Get Ort	  */
	public String getLocation();

    /** Column name M_Package_ID */
    public static final String COLUMNNAME_M_Package_ID = "M_Package_ID";

	/** Set Packstueck.
	  * Shipment Package
	  */
	public void setM_Package_ID (int M_Package_ID);

	/** Get Packstueck.
	  * Shipment Package
	  */
	public int getM_Package_ID();

	public I_M_Package getM_Package() throws RuntimeException;

    /** Column name ParcellNo */
    public static final String COLUMNNAME_ParcellNo = "ParcellNo";

	/** Set Paketscheinnummer	  */
	public void setParcellNo (String ParcellNo);

	/** Get Paketscheinnummer	  */
	public String getParcellNo();

    /** Column name PCode */
    public static final String COLUMNNAME_PCode = "PCode";

	/** Set Produkt Code	  */
	public void setPCode (String PCode);

	/** Get Produkt Code	  */
	public String getPCode();

    /** Column name PodImageRef */
    public static final String COLUMNNAME_PodImageRef = "PodImageRef";

	/** Set Ref-Ausrollistennummer.
	  * Referenz zur Ausrollistennummer
	  */
	public void setPodImageRef (String PodImageRef);

	/** Get Ref-Ausrollistennummer.
	  * Referenz zur Ausrollistennummer
	  */
	public String getPodImageRef();

    /** Column name ReceiverName */
    public static final String COLUMNNAME_ReceiverName = "ReceiverName";

	/** Set Unterzeichner-Name.
	  * Name des Unterzeichners/Empfaengers der Lieferung
	  */
	public void setReceiverName (String ReceiverName);

	/** Get Unterzeichner-Name.
	  * Name des Unterzeichners/Empfaengers der Lieferung
	  */
	public String getReceiverName();

    /** Column name Route */
    public static final String COLUMNNAME_Route = "Route";

	/** Set Route	  */
	public void setRoute (String Route);

	/** Get Route	  */
	public String getRoute();

    /** Column name Service */
    public static final String COLUMNNAME_Service = "Service";

	/** Set Servicecode	  */
	public void setService (String Service);

	/** Get Servicecode	  */
	public String getService();

    /** Column name Tour */
    public static final String COLUMNNAME_Tour = "Tour";

	/** Set Tournummer	  */
	public void setTour (String Tour);

	/** Get Tournummer	  */
	public String getTour();

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

    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/** Set Gewicht.
	  * Gewicht eines Produktes
	  */
	public void setWeight (BigDecimal Weight);

	/** Get Gewicht.
	  * Gewicht eines Produktes
	  */
	public BigDecimal getWeight();
}
