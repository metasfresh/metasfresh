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
import org.compiere.util.KeyNamePair;

/** Generated Interface for M_BOMProduct
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_M_BOMProduct 
{

    /** TableName=M_BOMProduct */
    public static final String Table_Name = "M_BOMProduct";

    /** AD_Table_ID=801 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name BOMProductType */
    public static final String COLUMNNAME_BOMProductType = "BOMProductType";

	/** Set Component Type.
	  * BOM Product Type
	  */
	public void setBOMProductType (String BOMProductType);

	/** Get Component Type.
	  * BOM Product Type
	  */
	public String getBOMProductType();

    /** Column name BOMQty */
    public static final String COLUMNNAME_BOMQty = "BOMQty";

	/** Set BOM Quantity.
	  * Bill of Materials Quantity
	  */
	public void setBOMQty (BigDecimal BOMQty);

	/** Get BOM Quantity.
	  * Bill of Materials Quantity
	  */
	public BigDecimal getBOMQty();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsPhantom */
    public static final String COLUMNNAME_IsPhantom = "IsPhantom";

	/** Set Phantom.
	  * Phantom Component
	  */
	public void setIsPhantom (boolean IsPhantom);

	/** Get Phantom.
	  * Phantom Component
	  */
	public boolean isPhantom();

    /** Column name LeadTimeOffset */
    public static final String COLUMNNAME_LeadTimeOffset = "LeadTimeOffset";

	/** Set Lead Time Offset.
	  * Optional Lead Time offest before starting production
	  */
	public void setLeadTimeOffset (int LeadTimeOffset);

	/** Get Lead Time Offset.
	  * Optional Lead Time offest before starting production
	  */
	public int getLeadTimeOffset();

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/** Set Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/** Get Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID();

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    /** Column name M_BOMAlternative_ID */
    public static final String COLUMNNAME_M_BOMAlternative_ID = "M_BOMAlternative_ID";

	/** Set Alternative Group.
	  * Product BOM Alternative Group
	  */
	public void setM_BOMAlternative_ID (int M_BOMAlternative_ID);

	/** Get Alternative Group.
	  * Product BOM Alternative Group
	  */
	public int getM_BOMAlternative_ID();

	public I_M_BOMAlternative getM_BOMAlternative() throws RuntimeException;

    /** Column name M_BOM_ID */
    public static final String COLUMNNAME_M_BOM_ID = "M_BOM_ID";

	/** Set BOM.
	  * Bill of Material
	  */
	public void setM_BOM_ID (int M_BOM_ID);

	/** Get BOM.
	  * Bill of Material
	  */
	public int getM_BOM_ID();

	public I_M_BOM getM_BOM() throws RuntimeException;

    /** Column name M_BOMProduct_ID */
    public static final String COLUMNNAME_M_BOMProduct_ID = "M_BOMProduct_ID";

	/** Set BOM Component.
	  * Bill of Material Component (Product)
	  */
	public void setM_BOMProduct_ID (int M_BOMProduct_ID);

	/** Get BOM Component.
	  * Bill of Material Component (Product)
	  */
	public int getM_BOMProduct_ID();

    /** Column name M_ChangeNotice_ID */
    public static final String COLUMNNAME_M_ChangeNotice_ID = "M_ChangeNotice_ID";

	/** Set Change Notice.
	  * Bill of Materials (Engineering) Change Notice (Version)
	  */
	public void setM_ChangeNotice_ID (int M_ChangeNotice_ID);

	/** Get Change Notice.
	  * Bill of Materials (Engineering) Change Notice (Version)
	  */
	public int getM_ChangeNotice_ID();

	public I_M_ChangeNotice getM_ChangeNotice() throws RuntimeException;

    /** Column name M_ProductBOM_ID */
    public static final String COLUMNNAME_M_ProductBOM_ID = "M_ProductBOM_ID";

	/** Set BOM Product.
	  * Bill of Material Component Product
	  */
	public void setM_ProductBOM_ID (int M_ProductBOM_ID);

	/** Get BOM Product.
	  * Bill of Material Component Product
	  */
	public int getM_ProductBOM_ID();

	public I_M_Product getM_ProductBOM() throws RuntimeException;

    /** Column name M_ProductOperation_ID */
    public static final String COLUMNNAME_M_ProductOperation_ID = "M_ProductOperation_ID";

	/** Set Product Operation.
	  * Product Manufacturing Operation
	  */
	public void setM_ProductOperation_ID (int M_ProductOperation_ID);

	/** Get Product Operation.
	  * Product Manufacturing Operation
	  */
	public int getM_ProductOperation_ID();

	public I_M_ProductOperation getM_ProductOperation() throws RuntimeException;

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
