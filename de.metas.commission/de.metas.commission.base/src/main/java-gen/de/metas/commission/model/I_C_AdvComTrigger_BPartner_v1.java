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

/** Generated Interface for C_AdvComTrigger_BPartner_v1
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvComTrigger_BPartner_v1 
{

    /** TableName=C_AdvComTrigger_BPartner_v1 */
    public static final String Table_Name = "C_AdvComTrigger_BPartner_v1";

    /** AD_Table_ID=540289 */
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

    /** Column name C_AdvComDoc_ID */
    public static final String COLUMNNAME_C_AdvComDoc_ID = "C_AdvComDoc_ID";

	/** Set Provisionsauslöser	  */
	public void setC_AdvComDoc_ID (int C_AdvComDoc_ID);

	/** Get Provisionsauslöser	  */
	public int getC_AdvComDoc_ID();

	public de.metas.commission.model.I_C_AdvComDoc getC_AdvComDoc() throws RuntimeException;

    /** Column name C_AdvCommissionInstance_ID */
    public static final String COLUMNNAME_C_AdvCommissionInstance_ID = "C_AdvCommissionInstance_ID";

	/** Set Provisionsvorgang	  */
	public void setC_AdvCommissionInstance_ID (int C_AdvCommissionInstance_ID);

	/** Get Provisionsvorgang	  */
	public int getC_AdvCommissionInstance_ID();

	public de.metas.commission.model.I_C_AdvCommissionInstance getC_AdvCommissionInstance() throws RuntimeException;

    /** Column name C_AdvCommissionTerm_ID */
    public static final String COLUMNNAME_C_AdvCommissionTerm_ID = "C_AdvCommissionTerm_ID";

	/** Set Provisionsart	  */
	public void setC_AdvCommissionTerm_ID (int C_AdvCommissionTerm_ID);

	/** Get Provisionsart	  */
	public int getC_AdvCommissionTerm_ID();

	public de.metas.commission.model.I_C_AdvCommissionTerm getC_AdvCommissionTerm() throws RuntimeException;

    /** Column name C_AdvComTrigger_BPartner_v1_ID */
    public static final String COLUMNNAME_C_AdvComTrigger_BPartner_v1_ID = "C_AdvComTrigger_BPartner_v1_ID";

	/** Set Provisionsauslöser - Geschäftspartner	  */
	public void setC_AdvComTrigger_BPartner_v1_ID (int C_AdvComTrigger_BPartner_v1_ID);

	/** Get Provisionsauslöser - Geschäftspartner	  */
	public int getC_AdvComTrigger_BPartner_v1_ID();

    /** Column name C_BPartner_Customer_ID */
    public static final String COLUMNNAME_C_BPartner_Customer_ID = "C_BPartner_Customer_ID";

	/** Set Kunde	  */
	public void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID);

	/** Get Kunde	  */
	public int getC_BPartner_Customer_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner_Customer() throws RuntimeException;

    /** Column name C_BPartner_SalesRep_ID */
    public static final String COLUMNNAME_C_BPartner_SalesRep_ID = "C_BPartner_SalesRep_ID";

	/** Set Vertriebsperson	  */
	public void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID);

	/** Get Vertriebsperson	  */
	public int getC_BPartner_SalesRep_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner_SalesRep() throws RuntimeException;

    /** Column name CommissionDateFact */
    public static final String COLUMNNAME_CommissionDateFact = "CommissionDateFact";

	/** Set Provisionsauslöserdatum	  */
	public void setCommissionDateFact (Timestamp CommissionDateFact);

	/** Get Provisionsauslöserdatum	  */
	public Timestamp getCommissionDateFact();

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

    /** Column name DateInstanceTrigger */
    public static final String COLUMNNAME_DateInstanceTrigger = "DateInstanceTrigger";

	/** Set Datum Vorgangsauslöser	  */
	public void setDateInstanceTrigger (Timestamp DateInstanceTrigger);

	/** Get Datum Vorgangsauslöser	  */
	public Timestamp getDateInstanceTrigger();

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
