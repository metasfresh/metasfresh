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


/** Generated Interface for M_AttributeSetExcludeLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_AttributeSetExcludeLine 
{

    /** TableName=M_AttributeSetExcludeLine */
    public static final String Table_Name = "M_AttributeSetExcludeLine";

    /** AD_Table_ID=540568 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public java.sql.Timestamp getCreated();

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

    /** Column name M_Attribute_ID */
    public static final String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/** Set Merkmal.
	  * Produkt-Merkmal
	  */
	public void setM_Attribute_ID (int M_Attribute_ID);

	/** Get Merkmal.
	  * Produkt-Merkmal
	  */
	public int getM_Attribute_ID();

	public org.compiere.model.I_M_Attribute getM_Attribute() throws RuntimeException;

	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute);

    /** Column name M_AttributeSetExclude_ID */
    public static final String COLUMNNAME_M_AttributeSetExclude_ID = "M_AttributeSetExclude_ID";

	/** Set Exclude Attribute Set.
	  * Exclude the ability to enter Attribute Sets
	  */
	public void setM_AttributeSetExclude_ID (int M_AttributeSetExclude_ID);

	/** Get Exclude Attribute Set.
	  * Exclude the ability to enter Attribute Sets
	  */
	public int getM_AttributeSetExclude_ID();

	public org.compiere.model.I_M_AttributeSetExclude getM_AttributeSetExclude() throws RuntimeException;

	public void setM_AttributeSetExclude(org.compiere.model.I_M_AttributeSetExclude M_AttributeSetExclude);

    /** Column name M_AttributeSetExcludeLine_ID */
    public static final String COLUMNNAME_M_AttributeSetExcludeLine_ID = "M_AttributeSetExcludeLine_ID";

	/** Set Exclude attribute.
	  * Only excludes selected attributes from the attribute set
	  */
	public void setM_AttributeSetExcludeLine_ID (int M_AttributeSetExcludeLine_ID);

	/** Get Exclude attribute.
	  * Only excludes selected attributes from the attribute set
	  */
	public int getM_AttributeSetExcludeLine_ID();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
