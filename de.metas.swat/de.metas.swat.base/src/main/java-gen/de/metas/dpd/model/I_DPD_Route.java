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

import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for DPD_Route
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_DPD_Route 
{

    /** TableName=DPD_Route */
    public static final String Table_Name = "DPD_Route";

    /** AD_Table_ID=540118 */
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

    /** Column name BarcodeID */
    public static final String COLUMNNAME_BarcodeID = "BarcodeID";

	/** Set BarcodeID	  */
	public void setBarcodeID (String BarcodeID);

	/** Get BarcodeID	  */
	public String getBarcodeID();

    /** Column name BeginPostCode */
    public static final String COLUMNNAME_BeginPostCode = "BeginPostCode";

	/** Set BeginPostCode	  */
	public void setBeginPostCode (String BeginPostCode);

	/** Get BeginPostCode	  */
	public String getBeginPostCode();

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

    /** Column name D_Depot */
    public static final String COLUMNNAME_D_Depot = "D_Depot";

	/** Set D_Depot	  */
	public void setD_Depot (String D_Depot);

	/** Get D_Depot	  */
	public String getD_Depot();

    /** Column name DPD_FileInfo_ID */
    public static final String COLUMNNAME_DPD_FileInfo_ID = "DPD_FileInfo_ID";

	/** Set DPD_FileInfo	  */
	public void setDPD_FileInfo_ID (int DPD_FileInfo_ID);

	/** Get DPD_FileInfo	  */
	public int getDPD_FileInfo_ID();

	public de.metas.dpd.model.I_DPD_FileInfo getDPD_FileInfo() throws RuntimeException;

    /** Column name DPD_Route_ID */
    public static final String COLUMNNAME_DPD_Route_ID = "DPD_Route_ID";

	/** Set DPD_Route	  */
	public void setDPD_Route_ID (int DPD_Route_ID);

	/** Get DPD_Route	  */
	public int getDPD_Route_ID();

    /** Column name D_Sort */
    public static final String COLUMNNAME_D_Sort = "D_Sort";

	/** Set Destination Sort	  */
	public void setD_Sort (String D_Sort);

	/** Get Destination Sort	  */
	public String getD_Sort();

    /** Column name EndPostCode */
    public static final String COLUMNNAME_EndPostCode = "EndPostCode";

	/** Set EndPostCode	  */
	public void setEndPostCode (String EndPostCode);

	/** Get EndPostCode	  */
	public String getEndPostCode();

    /** Column name GroupingPriority */
    public static final String COLUMNNAME_GroupingPriority = "GroupingPriority";

	/** Set GroupingPriority	  */
	public void setGroupingPriority (String GroupingPriority);

	/** Get GroupingPriority	  */
	public String getGroupingPriority();

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

    /** Column name O_Sort */
    public static final String COLUMNNAME_O_Sort = "O_Sort";

	/** Set Origin Sort	  */
	public void setO_Sort (String O_Sort);

	/** Get Origin Sort	  */
	public String getO_Sort();

    /** Column name RoutingPlaces */
    public static final String COLUMNNAME_RoutingPlaces = "RoutingPlaces";

	/** Set RoutingPlaces	  */
	public void setRoutingPlaces (String RoutingPlaces);

	/** Get RoutingPlaces	  */
	public String getRoutingPlaces();

    /** Column name SendingDate */
    public static final String COLUMNNAME_SendingDate = "SendingDate";

	/** Set SendingDate	  */
	public void setSendingDate (String SendingDate);

	/** Get SendingDate	  */
	public String getSendingDate();

    /** Column name ServiceCodes */
    public static final String COLUMNNAME_ServiceCodes = "ServiceCodes";

	/** Set ServiceCodes	  */
	public void setServiceCodes (String ServiceCodes);

	/** Get ServiceCodes	  */
	public String getServiceCodes();

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
}
