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

/** Generated Interface for C_AdvCommission_Info_V
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_AdvCommission_Info_V 
{

    /** TableName=C_AdvCommission_Info_V */
    public static final String Table_Name = "C_AdvCommission_Info_V";

    /** AD_Table_ID=540098 */
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

    /** Column name C_AdvCommission_Info_V_ID */
    public static final String COLUMNNAME_C_AdvCommission_Info_V_ID = "C_AdvCommission_Info_V_ID";

	/** Set Provisions-Info	  */
	public void setC_AdvCommission_Info_V_ID (int C_AdvCommission_Info_V_ID);

	/** Get Provisions-Info	  */
	public int getC_AdvCommission_Info_V_ID();

    /** Column name C_AdvCommissionTerm_ID */
    public static final String COLUMNNAME_C_AdvCommissionTerm_ID = "C_AdvCommissionTerm_ID";

	/** Set Provisionsart	  */
	public void setC_AdvCommissionTerm_ID (int C_AdvCommissionTerm_ID);

	/** Get Provisionsart	  */
	public int getC_AdvCommissionTerm_ID();

	public de.metas.commission.model.I_C_AdvCommissionTerm getC_AdvCommissionTerm() throws RuntimeException;

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

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

    /** Column name Level */
    public static final String COLUMNNAME_Level = "Level";

	/** Set Ebene	  */
	public void setLevel (int Level);

	/** Get Ebene	  */
	public int getLevel();

    /** Column name Points_Calculated */
    public static final String COLUMNNAME_Points_Calculated = "Points_Calculated";

	/** Set Punkte-Berechnet	  */
	public void setPoints_Calculated (BigDecimal Points_Calculated);

	/** Get Punkte-Berechnet	  */
	public BigDecimal getPoints_Calculated();

    /** Column name Points_Paid */
    public static final String COLUMNNAME_Points_Paid = "Points_Paid";

	/** Set Punkte-Ausgezahlt	  */
	public void setPoints_Paid (BigDecimal Points_Paid);

	/** Get Punkte-Ausgezahlt	  */
	public BigDecimal getPoints_Paid();

    /** Column name Points_Predicted */
    public static final String COLUMNNAME_Points_Predicted = "Points_Predicted";

	/** Set Punkte-Prognostiziert	  */
	public void setPoints_Predicted (BigDecimal Points_Predicted);

	/** Get Punkte-Prognostiziert	  */
	public BigDecimal getPoints_Predicted();

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

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
