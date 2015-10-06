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

/** Generated Interface for C_Sponsor
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_Sponsor 
{

    /** TableName=C_Sponsor */
    public static final String Table_Name = "C_Sponsor";

    /** AD_Table_ID=504620 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant f�r diese Installation.
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

    /** Column name C_AdvCommissionSalaryGroup_ID */
    public static final String COLUMNNAME_C_AdvCommissionSalaryGroup_ID = "C_AdvCommissionSalaryGroup_ID";

	/** Set Vergütungsgruppe.
	  *  US1026:  Änderung Vergütungsplan (2011010610000028) R01A06
	  */
	public void setC_AdvCommissionSalaryGroup_ID (int C_AdvCommissionSalaryGroup_ID);

	/** Get Vergütungsgruppe.
	  *  US1026:  Änderung Vergütungsplan (2011010610000028) R01A06
	  */
	public int getC_AdvCommissionSalaryGroup_ID();

	public de.metas.commission.model.I_C_AdvCommissionSalaryGroup getC_AdvCommissionSalaryGroup() throws RuntimeException;

    /** Column name C_AdvComRank_System_ID */
    public static final String COLUMNNAME_C_AdvComRank_System_ID = "C_AdvComRank_System_ID";

	/** Set Vergütungsgruppe (System).
	  *  US1026:  Änderung Vergütungsplan (2011010610000028) R01A06
	  */
	public void setC_AdvComRank_System_ID (int C_AdvComRank_System_ID);

	/** Get Vergütungsgruppe (System).
	  *  US1026:  Änderung Vergütungsplan (2011010610000028) R01A06
	  */
	public int getC_AdvComRank_System_ID();

	public de.metas.commission.model.I_C_AdvCommissionSalaryGroup getC_AdvComRank_System() throws RuntimeException;

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Geschäftspartner.
	  * Bezeichnet einen Geschäftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Geschäftspartner.
	  * Bezeichnet einen Geschäftspartner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name C_Sponsor_ID */
    public static final String COLUMNNAME_C_Sponsor_ID = "C_Sponsor_ID";

	/** Set Sponsor	  */
	public void setC_Sponsor_ID (int C_Sponsor_ID);

	/** Get Sponsor	  */
	public int getC_Sponsor_ID();

    /** Column name C_Sponsor_Kunde_includedTab */
    public static final String COLUMNNAME_C_Sponsor_Kunde_includedTab = "C_Sponsor_Kunde_includedTab";

	/** Set C_Sponsor_Kunde_includedTab	  */
	public void setC_Sponsor_Kunde_includedTab (String C_Sponsor_Kunde_includedTab);

	/** Get C_Sponsor_Kunde_includedTab	  */
	public String getC_Sponsor_Kunde_includedTab();

    /** Column name C_Sponsor_VP_includedTab */
    public static final String COLUMNNAME_C_Sponsor_VP_includedTab = "C_Sponsor_VP_includedTab";

	/** Set C_Sponsor_VP_includedTab	  */
	public void setC_Sponsor_VP_includedTab (String C_Sponsor_VP_includedTab);

	/** Get C_Sponsor_VP_includedTab	  */
	public String getC_Sponsor_VP_includedTab();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Beschreibung.
	  * Optional short description of the record
	  */
	public String getDescription();

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

    /** Column name IsManualRank */
    public static final String COLUMNNAME_IsManualRank = "IsManualRank";

	/** Set fixierte VG.
	  *  US1026:  Änderung Vergütungsplan (2011010610000028)  R01A06
	  */
	public void setIsManualRank (boolean IsManualRank);

	/** Get fixierte VG.
	  *  US1026:  Änderung Vergütungsplan (2011010610000028)  R01A06
	  */
	public boolean isManualRank();

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

    /** Column name SponsorNo */
    public static final String COLUMNNAME_SponsorNo = "SponsorNo";

	/** Set Sponsornummer	  */
	public void setSponsorNo (String SponsorNo);

	/** Get Sponsornummer	  */
	public String getSponsorNo();

    /** Column name StatsDLCustomers */
    public static final String COLUMNNAME_StatsDLCustomers = "StatsDLCustomers";

	/** Set Downline ENDKn	  */
	public void setStatsDLCustomers (int StatsDLCustomers);

	/** Get Downline ENDKn	  */
	public int getStatsDLCustomers();

    /** Column name StatsDLSalesReps */
    public static final String COLUMNNAME_StatsDLSalesReps = "StatsDLSalesReps";

	/** Set Downline VPs	  */
	public void setStatsDLSalesReps (int StatsDLSalesReps);

	/** Get Downline VPs	  */
	public int getStatsDLSalesReps();

    /** Column name StatsLastUpdate */
    public static final String COLUMNNAME_StatsLastUpdate = "StatsLastUpdate";

	/** Set Statistik aktualisiert am	  */
	public void setStatsLastUpdate (Timestamp StatsLastUpdate);

	/** Get Statistik aktualisiert am	  */
	public Timestamp getStatsLastUpdate();

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
