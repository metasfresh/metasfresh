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

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for C_AdvComFact_SalesRepFact
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvComFact_SalesRepFact 
{

    /** TableName=C_AdvComFact_SalesRepFact */
    public static final String Table_Name = "C_AdvComFact_SalesRepFact";

    /** AD_Table_ID=540186 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant für diese Installation.
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

    /** Column name C_AdvComFact_SalesRepFact_ID */
    public static final String COLUMNNAME_C_AdvComFact_SalesRepFact_ID = "C_AdvComFact_SalesRepFact_ID";

	/** Set C_AdvComFact_SalesRepFact_ID	  */
	public void setC_AdvComFact_SalesRepFact_ID (int C_AdvComFact_SalesRepFact_ID);

	/** Get C_AdvComFact_SalesRepFact_ID	  */
	public int getC_AdvComFact_SalesRepFact_ID();

    /** Column name C_AdvCommissionFact_ID */
    public static final String COLUMNNAME_C_AdvCommissionFact_ID = "C_AdvCommissionFact_ID";

	/** Set Vorgangsdatensatz	  */
	public void setC_AdvCommissionFact_ID (int C_AdvCommissionFact_ID);

	/** Get Vorgangsdatensatz	  */
	public int getC_AdvCommissionFact_ID();

	public de.metas.commission.model.I_C_AdvCommissionFact getC_AdvCommissionFact() throws RuntimeException;

    /** Column name C_AdvComSalesRepFact_ID */
    public static final String COLUMNNAME_C_AdvComSalesRepFact_ID = "C_AdvComSalesRepFact_ID";

	/** Set Sponsor-Provisionsdatensatz	  */
	public void setC_AdvComSalesRepFact_ID (int C_AdvComSalesRepFact_ID);

	/** Get Sponsor-Provisionsdatensatz	  */
	public int getC_AdvComSalesRepFact_ID();

	public de.metas.commission.model.I_C_AdvComSalesRepFact getC_AdvComSalesRepFact() throws RuntimeException;

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
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
