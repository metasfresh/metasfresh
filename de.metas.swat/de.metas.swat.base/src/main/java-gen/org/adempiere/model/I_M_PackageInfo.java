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
package org.adempiere.model;

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

/** Generated Interface for M_PackageInfo
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_M_PackageInfo 
{

    /** TableName=M_PackageInfo */
    public static final String Table_Name = "M_PackageInfo";

    /** AD_Table_ID=540122 */
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

    /** Column name BarcodeInfo */
    public static final String COLUMNNAME_BarcodeInfo = "BarcodeInfo";

	/** Set BarcodeInfo	  */
	public void setBarcodeInfo (String BarcodeInfo);

	/** Get BarcodeInfo	  */
	public String getBarcodeInfo();

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

    /** Column name D_Sort */
    public static final String COLUMNNAME_D_Sort = "D_Sort";

	/** Set Destination Sort	  */
	public void setD_Sort (String D_Sort);

	/** Get Destination Sort	  */
	public String getD_Sort();

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

    /** Column name M_PackageInfo_ID */
    public static final String COLUMNNAME_M_PackageInfo_ID = "M_PackageInfo_ID";

	/** Set M_PackageInfo	  */
	public void setM_PackageInfo_ID (int M_PackageInfo_ID);

	/** Get M_PackageInfo	  */
	public int getM_PackageInfo_ID();

    /** Column name O_Sort */
    public static final String COLUMNNAME_O_Sort = "O_Sort";

	/** Set Origin Sort	  */
	public void setO_Sort (String O_Sort);

	/** Get Origin Sort	  */
	public String getO_Sort();

    /** Column name RoutingText */
    public static final String COLUMNNAME_RoutingText = "RoutingText";

	/** Set RoutingText	  */
	public void setRoutingText (String RoutingText);

	/** Get RoutingText	  */
	public String getRoutingText();

    /** Column name ServiceFieldInfo */
    public static final String COLUMNNAME_ServiceFieldInfo = "ServiceFieldInfo";

	/** Set ServiceFieldInfo	  */
	public void setServiceFieldInfo (String ServiceFieldInfo);

	/** Get ServiceFieldInfo	  */
	public String getServiceFieldInfo();

    /** Column name ServiceMark */
    public static final String COLUMNNAME_ServiceMark = "ServiceMark";

	/** Set ServiceMark	  */
	public void setServiceMark (String ServiceMark);

	/** Get ServiceMark	  */
	public String getServiceMark();

    /** Column name ServiceText */
    public static final String COLUMNNAME_ServiceText = "ServiceText";

	/** Set ServiceText	  */
	public void setServiceText (String ServiceText);

	/** Get ServiceText	  */
	public String getServiceText();

    /** Column name ServiceValue */
    public static final String COLUMNNAME_ServiceValue = "ServiceValue";

	/** Set ServiceValue	  */
	public void setServiceValue (String ServiceValue);

	/** Get ServiceValue	  */
	public String getServiceValue();

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

    /** Column name Version */
    public static final String COLUMNNAME_Version = "Version";

	/** Set Preislistenversion.
	  * Version of the table definition
	  */
	public void setVersion (String Version);

	/** Get Preislistenversion.
	  * Version of the table definition
	  */
	public String getVersion();
}
