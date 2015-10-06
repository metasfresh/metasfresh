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
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for PP_Order_Node_Product
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_PP_Order_Node_Product 
{

    /** TableName=PP_Order_Node_Product */
    public static final String Table_Name = "PP_Order_Node_Product";

    /** AD_Table_ID=53030 */
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

    /** Column name IsSubcontracting */
    public static final String COLUMNNAME_IsSubcontracting = "IsSubcontracting";

	/** Set Is Subcontracting	  */
	public void setIsSubcontracting (boolean IsSubcontracting);

	/** Get Is Subcontracting	  */
	public boolean isSubcontracting();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public I_M_Product getM_Product() throws RuntimeException;

    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/** Set Manufacturing Order	  */
	public void setPP_Order_ID (int PP_Order_ID);

	/** Get Manufacturing Order	  */
	public int getPP_Order_ID();

	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException;

    /** Column name PP_Order_Node_ID */
    public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";

	/** Set Manufacturing Order Activity	  */
	public void setPP_Order_Node_ID (int PP_Order_Node_ID);

	/** Get Manufacturing Order Activity	  */
	public int getPP_Order_Node_ID();

	public org.eevolution.model.I_PP_Order_Node getPP_Order_Node() throws RuntimeException;

    /** Column name PP_Order_Node_Product_ID */
    public static final String COLUMNNAME_PP_Order_Node_Product_ID = "PP_Order_Node_Product_ID";

	/** Set Manufacturing Order Activity Product	  */
	public void setPP_Order_Node_Product_ID (int PP_Order_Node_Product_ID);

	/** Get Manufacturing Order Activity Product	  */
	public int getPP_Order_Node_Product_ID();

    /** Column name PP_Order_Workflow_ID */
    public static final String COLUMNNAME_PP_Order_Workflow_ID = "PP_Order_Workflow_ID";

	/** Set Manufacturing Order Workflow	  */
	public void setPP_Order_Workflow_ID (int PP_Order_Workflow_ID);

	/** Get Manufacturing Order Workflow	  */
	public int getPP_Order_Workflow_ID();

	public org.eevolution.model.I_PP_Order_Workflow getPP_Order_Workflow() throws RuntimeException;

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

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
