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

/** Generated Interface for C_AdvComRankForecast
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvComRankForecast 
{

    /** TableName=C_AdvComRankForecast */
    public static final String Table_Name = "C_AdvComRankForecast";

    /** AD_Table_ID=540251 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

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

    /** Column name C_AdvCommissionSalaryGroup_ID */
    public static final String COLUMNNAME_C_AdvCommissionSalaryGroup_ID = "C_AdvCommissionSalaryGroup_ID";

	/** Set Vergütungsgruppe	  */
	public void setC_AdvCommissionSalaryGroup_ID (int C_AdvCommissionSalaryGroup_ID);

	/** Get Vergütungsgruppe	  */
	public int getC_AdvCommissionSalaryGroup_ID();

	public de.metas.commission.model.I_C_AdvCommissionSalaryGroup getC_AdvCommissionSalaryGroup() throws RuntimeException;

    /** Column name C_AdvComRankForecast_ID */
    public static final String COLUMNNAME_C_AdvComRankForecast_ID = "C_AdvComRankForecast_ID";

	/** Set VG-Prognose	  */
	public void setC_AdvComRankForecast_ID (int C_AdvComRankForecast_ID);

	/** Get VG-Prognose	  */
	public int getC_AdvComRankForecast_ID();

    /** Column name C_AdvComRank_Next_ID */
    public static final String COLUMNNAME_C_AdvComRank_Next_ID = "C_AdvComRank_Next_ID";

	/** Set nächste VG	  */
	public void setC_AdvComRank_Next_ID (int C_AdvComRank_Next_ID);

	/** Get nächste VG	  */
	public int getC_AdvComRank_Next_ID();

	public de.metas.commission.model.I_C_AdvCommissionSalaryGroup getC_AdvComRank_Next() throws RuntimeException;

    /** Column name C_AdvComSystem_ID */
    public static final String COLUMNNAME_C_AdvComSystem_ID = "C_AdvComSystem_ID";

	/** Set Vergütungsplan	  */
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID);

	/** Get Vergütungsplan	  */
	public int getC_AdvComSystem_ID();

	public de.metas.commission.model.I_C_AdvComSystem getC_AdvComSystem() throws RuntimeException;

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

    /** Column name C_Period_Since_ID */
    public static final String COLUMNNAME_C_Period_Since_ID = "C_Period_Since_ID";

	/** Set gültig seit einschl. Periode	  */
	public void setC_Period_Since_ID (int C_Period_Since_ID);

	/** Get gültig seit einschl. Periode	  */
	public int getC_Period_Since_ID();

	public org.compiere.model.I_C_Period getC_Period_Since() throws RuntimeException;

    /** Column name C_Period_Until_ID */
    public static final String COLUMNNAME_C_Period_Until_ID = "C_Period_Until_ID";

	/** Set gültig bis einschl. Periode	  */
	public void setC_Period_Until_ID (int C_Period_Until_ID);

	/** Get gültig bis einschl. Periode	  */
	public int getC_Period_Until_ID();

	public org.compiere.model.I_C_Period getC_Period_Until() throws RuntimeException;

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

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor() throws RuntimeException;

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
	  * US1026: Aenderung Vergütungsplan (2011010610000028)  R01A06
	  */
	public void setIsManualRank (boolean IsManualRank);

	/** Get fixierte VG.
	  * US1026: Aenderung Vergütungsplan (2011010610000028)  R01A06
	  */
	public boolean isManualRank();

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
