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

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.I_C_Country;
import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for M_FreightCostDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_M_FreightCostDetail 
{

    /** TableName=M_FreightCostDetail */
    public static final String Table_Name = "M_FreightCostDetail";

    /** AD_Table_ID=540005 */
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

	public I_C_Country getC_Country() throws RuntimeException;

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

    /** Column name FreightAmt */
    public static final String COLUMNNAME_FreightAmt = "FreightAmt";

	/** Set Frachtbetrag.
	  * Frachtbetrag
	  */
	public void setFreightAmt (BigDecimal FreightAmt);

	/** Get Frachtbetrag.
	  * Frachtbetrag
	  */
	public BigDecimal getFreightAmt();

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

    /** Column name M_FreightCostDetail_ID */
    public static final String COLUMNNAME_M_FreightCostDetail_ID = "M_FreightCostDetail_ID";

	/** Set Frachtkostendetail	  */
	public void setM_FreightCostDetail_ID (int M_FreightCostDetail_ID);

	/** Get Frachtkostendetail	  */
	public int getM_FreightCostDetail_ID();

    /** Column name M_FreightCostShipper_ID */
    public static final String COLUMNNAME_M_FreightCostShipper_ID = "M_FreightCostShipper_ID";

	/** Set Lieferweg-Versandkosten	  */
	public void setM_FreightCostShipper_ID (int M_FreightCostShipper_ID);

	/** Get Lieferweg-Versandkosten	  */
	public int getM_FreightCostShipper_ID();

	public I_M_FreightCostShipper getM_FreightCostShipper() throws RuntimeException;

    /** Column name ShipmentValueAmt */
    public static final String COLUMNNAME_ShipmentValueAmt = "ShipmentValueAmt";

	/** Set Lieferwert	  */
	public void setShipmentValueAmt (BigDecimal ShipmentValueAmt);

	/** Get Lieferwert	  */
	public BigDecimal getShipmentValueAmt();

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
