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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.model.I_M_PackagingContainer;
import org.compiere.util.KeyNamePair;

/** Generated Interface for M_PackagingTreeItem
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_M_PackagingTreeItem 
{

    /** TableName=M_PackagingTreeItem */
    public static final String Table_Name = "M_PackagingTreeItem";

    /** AD_Table_ID=540259 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung	  */
	public void setDescription (String Description);

	/** Get Beschreibung	  */
	public String getDescription();

    /** Column name GroupID */
    public static final String COLUMNNAME_GroupID = "GroupID";

	/** Set GroupID	  */
	public void setGroupID (int GroupID);

	/** Get GroupID	  */
	public int getGroupID();

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

    /** Column name M_PackageTree_ID */
    public static final String COLUMNNAME_M_PackageTree_ID = "M_PackageTree_ID";

	/** Set Virtual Package	  */
	public void setM_PackageTree_ID (int M_PackageTree_ID);

	/** Get Virtual Package	  */
	public int getM_PackageTree_ID();

	public I_M_PackageTree getM_PackageTree() throws RuntimeException;

    /** Column name M_PackagingContainer_ID */
    public static final String COLUMNNAME_M_PackagingContainer_ID = "M_PackagingContainer_ID";

	/** Set Verpackung	  */
	public void setM_PackagingContainer_ID (int M_PackagingContainer_ID);

	/** Get Verpackung	  */
	public int getM_PackagingContainer_ID();

	public I_M_PackagingContainer getM_PackagingContainer() throws RuntimeException;

    /** Column name M_PackagingTree_ID */
    public static final String COLUMNNAME_M_PackagingTree_ID = "M_PackagingTree_ID";

	/** Set Packaging Tree	  */
	public void setM_PackagingTree_ID (int M_PackagingTree_ID);

	/** Get Packaging Tree	  */
	public int getM_PackagingTree_ID();

	public I_M_PackagingTree getM_PackagingTree() throws RuntimeException;

    /** Column name M_PackagingTreeItem_ID */
    public static final String COLUMNNAME_M_PackagingTreeItem_ID = "M_PackagingTreeItem_ID";

	/** Set Packaging Tree Item	  */
	public void setM_PackagingTreeItem_ID (int M_PackagingTreeItem_ID);

	/** Get Packaging Tree Item	  */
	public int getM_PackagingTreeItem_ID();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Produkt.
	  * Produkt, Leistung, Artikel
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Produkt.
	  * Produkt, Leistung, Artikel
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Menge.
	  * Menge
	  */
	public void setQty (BigDecimal Qty);

	/** Get Menge.
	  * Menge
	  */
	public BigDecimal getQty();

    /** Column name Ref_M_PackagingTreeItem_ID */
    public static final String COLUMNNAME_Ref_M_PackagingTreeItem_ID = "Ref_M_PackagingTreeItem_ID";

	/** Set Ref Packaging Tree Item	  */
	public void setRef_M_PackagingTreeItem_ID (int Ref_M_PackagingTreeItem_ID);

	/** Get Ref Packaging Tree Item	  */
	public int getRef_M_PackagingTreeItem_ID();

	public I_M_PackagingTreeItem getRef_M_PackagingTreeItem() throws RuntimeException;

    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/** Set Status	  */
	public void setStatus (String Status);

	/** Get Status	  */
	public String getStatus();

    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/** Set Art	  */
	public void setType (String Type);

	/** Get Art	  */
	public String getType();

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
